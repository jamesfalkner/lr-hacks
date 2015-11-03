import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.social.model.SocialActivityCounter;
import com.liferay.portlet.social.model.SocialActivityCounterConstants;
import com.liferay.portlet.social.service.SocialActivityCounterLocalServiceUtil;
import com.liferay.portlet.social.util.SocialCounterPeriodUtil;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class SocialDriver extends MVCPortlet {


    private WikiPoster wikiThread = null;
    private BlogPoster blogThread = null;
    private MBPoster mbThread = null;
    private final SocialDriverContentUtil contentContainer = new
        SocialDriverContentUtil();

    @Override
    public void serveResource(ResourceRequest request,
                              ResourceResponse response)
        throws IOException {


        long companyId = PortalUtil.getCompanyId(request);
        long groupId;
        User user;
        try {
            groupId = PortalUtil.getScopeGroupId(request);
            long userId = PortalUtil.getUserId(request);
            int count = MBMessageLocalServiceUtil.getGroupMessagesCount(groupId, userId, WorkflowConstants.STATUS_APPROVED);

            user = PortalUtil.getUser(request);
//            System.out.println("Operating on group: " + GroupLocalServiceUtil
//                .getGroup(groupId));
        } catch (PortalException e) {
            e.printStackTrace();  //To change body of catch statement use
            // File | Settings | File Templates.
            return;
        } catch (SystemException e) {
            e.printStackTrace();  //To change body of catch statement use
            // File | Settings | File Templates.
            return;
        }
        try {
            String themeId = GetterUtil.getString(request.getParameter
                ("themeId"));
            
            boolean profileFlag = true;
            // boolean profileFlag = GetterUtil.getBoolean(request.getParameter
             //   ("profileFlag"));

            if (!GetterUtil.getString(request.getParameter("startBlog"))
                .isEmpty()) {
                if (blogThread != null) {
                    if (blogThread.getState() == Thread.State.TERMINATED) {
                        blogThread = new BlogPoster(companyId, groupId,
                            themeId, profileFlag, contentContainer);
                        blogThread.turnOn();
                        blogThread.start();
                    } else {
                        blogThread.turnOn();
                    }
                } else {
                    blogThread = new BlogPoster(companyId, groupId, themeId,
                        profileFlag, contentContainer);
                    blogThread.turnOn();
                    blogThread.start();
                }
System.out.println("Started blog generator");
            }

            if (!GetterUtil.getString(request.getParameter("stopBlog"))
                .isEmpty()) {
                blogThread.turnOff();
            }
            if (!GetterUtil.getString(request.getParameter("clearBlog"))
                .isEmpty()) {
                SocialDriverUtil.clearBlogs(groupId);
            }
            if (!GetterUtil.getString(request.getParameter("startWiki"))
                .isEmpty()) {
                if (wikiThread != null) {
                    if (wikiThread.getState() == Thread.State.TERMINATED) {
                        wikiThread = new WikiPoster(companyId, groupId,
                            themeId, profileFlag, contentContainer);
                        wikiThread.turnOn();
                        wikiThread.start();
                    } else {
                        wikiThread.turnOn();
                    }
                } else {
                    wikiThread = new WikiPoster(companyId, groupId, themeId,
                        profileFlag, contentContainer);
                    wikiThread.turnOn();
                    wikiThread.start();
                }
            }

            if (!GetterUtil.getString(request.getParameter("stopMB")).isEmpty
                ()) {
                mbThread.turnOff();
            }
            if (!GetterUtil.getString(request.getParameter("clearMB"))
                .isEmpty()) {
                SocialDriverUtil.clearMB(companyId);
            }
            if (!GetterUtil.getString(request.getParameter("clearUsers"))
                .isEmpty()) {
                SocialDriverUtil.clearUsers(companyId, user.getUserId());
            }
            if (!GetterUtil.getString(request.getParameter("startMB"))
                .isEmpty()) {
                if (mbThread != null) {
                    if (mbThread.getState() == Thread.State.TERMINATED) {
                        mbThread = new MBPoster(companyId, groupId, themeId,
                            profileFlag, contentContainer);
                        mbThread.turnOn();
                        mbThread.start();
                    } else {
                        mbThread.turnOn();
                    }
                } else {
                    mbThread = new MBPoster(companyId, groupId, themeId,
                        profileFlag, contentContainer);
                    mbThread.turnOn();
                    mbThread.start();
                }
            }


            if (!GetterUtil.getString(request.getParameter("stopWiki"))
                .isEmpty()) {
                wikiThread.turnOff();
            }
            if (!GetterUtil.getString(request.getParameter("clearBlog"))
                .isEmpty()) {
                SocialDriverUtil.clearWikis(groupId);
            }
            if (!GetterUtil.getString(request.getParameter("genStats"))
                .isEmpty()) {
                generateSocialActivity(groupId);
            }

        } catch (Exception ex) {
            PrintWriter writer = response.getWriter();
            ex.printStackTrace(writer);
        }
    }

    // Create fake random historical social activity data to make the graphs
    // look good
    private void generateSocialActivity(long groupId) throws Exception {
        List<SocialActivityCounter> counters =
            SocialActivityCounterLocalServiceUtil.getSocialActivityCounters
            (QueryUtil.ALL_POS, QueryUtil.ALL_POS);
        for (SocialActivityCounter counter : counters) {
            if (counter.getGroupId() == groupId) {
                SocialActivityCounterLocalServiceUtil
                    .deleteSocialActivityCounter(counter.getActivityCounterId
                        ());
            }
        }

        long userClassId = PortalUtil.getClassNameId(User.class.getName());
        long blogClassId = PortalUtil.getClassNameId(BlogsEntry.class.getName
            ());
        long mbClassId = PortalUtil.getClassNameId(MBMessage.class.getName());
        long wikiClassId = PortalUtil.getClassNameId(WikiPage.class.getName());
		SocialActivityCounter c;

        for (User user : UserLocalServiceUtil.getUsers(QueryUtil.ALL_POS,
            QueryUtil.ALL_POS)) {
            if (user.isDefaultUser()) continue;
            int pub = 0;
            int puw = 0;
            int pubu = 0;
            int puwu = 0;
            int pump = 0;
            int pua = 0;
            int puc = 0;
            int puv = 0;
            int pp = 0;
            int pc = 0;
            int pcs = 0;
            int pcc = 0;

            for (int off = -12; off <= 0; off++) {
                int startPeriod = SocialCounterPeriodUtil.getStartPeriod(off);
                int endPeriod = -1;
                if (off != 0) {
                    endPeriod = SocialCounterPeriodUtil.getEndPeriod(off);
                }

                int nub = (int) (Math.random() * 12.0);
                int nuw = (int) (Math.random() * 12.0);
                int nubu = (int) (Math.random() * 12.0);
                int nuwu = (int) (Math.random() * 12.0);
                int nump = (int) (Math.random() * 50.0);
                int nua = (int) (Math.random() * 12.0);
                int nuc = (int) (Math.random() * 6.0);
                int nuv = (int) (Math.random() * 52.0);
                int np = (int) (Math.random() * 56.0);
                int nc = (int) (Math.random() * 18.0);
                int ncs = (int) (Math.random() * 22.0);
                int ncc = (int) (Math.random() * 72.0);

                nub = (int) ((double) nub * (1.0 + ((double) off / 12.0)));
                nuw = (int) ((double) nuw * (1.0 + ((double) off / 12.0)));
                nubu = (int) ((double) nubu * (1.0 + ((double) off / 12.0)));
                nuwu = (int) ((double) nuwu * (1.0 + ((double) off / 12.0)));
                nump = (int) ((double) nump * (1.0 + ((double) off / 12.0)));
                nua = (int) ((double) nua * (1.0 + ((double) off / 12.0)));
                nuc = (int) ((double) nuc * (1.0 + ((double) off / 12.0)));
                nuv = (int) ((double) nuv * (1.0 + ((double) off / 12.0)));
                np = (int) ((double) np * (1.0 + ((double) off / 12.0)));
                nc = (int) ((double) nc * (1.0 + ((double) off / 12.0)));
                ncs = (int) ((double) ncs * (1.0 + ((double) off / 12.0)));
                ncc = (int) ((double) ncc * (1.0 + ((double) off / 12.0)));

                c = SocialActivityCounterLocalServiceUtil.addActivityCounter(
                    groupId, userClassId, user.getUserId(),
                    "user.blogs", SocialActivityCounterConstants.TYPE_ACTOR,
                     pub + nub, 0, SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM);
				c.setStartPeriod(startPeriod);
				c.setEndPeriod(endPeriod);
				c.setTotalValue(pub+nub);
				c.setCurrentValue(nub);
				SocialActivityCounterLocalServiceUtil.updateSocialActivityCounter(c);

				c =  SocialActivityCounterLocalServiceUtil.addActivityCounter(
                    groupId, userClassId, user.getUserId(),
                    "user.wikis", SocialActivityCounterConstants.TYPE_ACTOR,
                     puw + nuw, 0, SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM);
				c.setStartPeriod(startPeriod);
				c.setEndPeriod(endPeriod);
				c.setTotalValue(puw + nuw);
				c.setCurrentValue(nuw);
				SocialActivityCounterLocalServiceUtil.updateSocialActivityCounter(c);

				c = SocialActivityCounterLocalServiceUtil.addActivityCounter(
                    groupId, userClassId, user.getUserId(),
                    "user.blog-updates", SocialActivityCounterConstants
                    .TYPE_ACTOR, nubu + pubu, 0, SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM);
				c.setStartPeriod(startPeriod);
				c.setEndPeriod(endPeriod);
				c.setTotalValue(nubu + pubu);
				c.setCurrentValue(nubu);
				SocialActivityCounterLocalServiceUtil.updateSocialActivityCounter(c);
				c = SocialActivityCounterLocalServiceUtil.addActivityCounter(
                    groupId, userClassId, user.getUserId(),
                    "user.wiki-updates", SocialActivityCounterConstants
                    .TYPE_ACTOR, puwu + nuwu, 0, SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM);
				c.setStartPeriod(startPeriod);
				c.setEndPeriod(endPeriod);
				c.setTotalValue(puwu + nuwu);
				c.setCurrentValue(nuwu);
				SocialActivityCounterLocalServiceUtil.updateSocialActivityCounter(c);
				c = SocialActivityCounterLocalServiceUtil.addActivityCounter(
                    groupId, userClassId, user.getUserId(),
                    "user.message-posts", SocialActivityCounterConstants
                    .TYPE_ACTOR, pump + nump, 0, SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM);
				c.setStartPeriod(startPeriod);
				c.setEndPeriod(endPeriod);
				c.setTotalValue(pump + nump);
				c.setCurrentValue(nump);
				SocialActivityCounterLocalServiceUtil.updateSocialActivityCounter(c);
				c =   SocialActivityCounterLocalServiceUtil.addActivityCounter(
                    groupId, userClassId, user.getUserId(),
                    "user.activities", SocialActivityCounterConstants
                    .TYPE_ACTOR, pua + nua, 0, SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM);

				c.setStartPeriod(startPeriod);
				c.setEndPeriod(endPeriod);
				c.setTotalValue(pua + nua);
				c.setCurrentValue(nua);
				SocialActivityCounterLocalServiceUtil.updateSocialActivityCounter(c);
				c =  SocialActivityCounterLocalServiceUtil.addActivityCounter(
                    groupId, userClassId, user.getUserId(),
                    "user.comments", SocialActivityCounterConstants
                    .TYPE_ACTOR, puc + nuc, 0, SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM);

				c.setStartPeriod(startPeriod);
				c.setEndPeriod(endPeriod);
				c.setTotalValue(puc + nuc);
				c.setCurrentValue(nuc);
				SocialActivityCounterLocalServiceUtil.updateSocialActivityCounter(c);
				c =  SocialActivityCounterLocalServiceUtil.addActivityCounter(
                    groupId, userClassId, user.getUserId(),
                    "user.votes", SocialActivityCounterConstants.TYPE_ACTOR,
                     puv + nuv, 0, SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM);


				c.setStartPeriod(startPeriod);
				c.setEndPeriod(endPeriod);
				c.setTotalValue(puv + nuv);
				c.setCurrentValue(nuv);
				SocialActivityCounterLocalServiceUtil.updateSocialActivityCounter(c);
				c =  SocialActivityCounterLocalServiceUtil.addActivityCounter(
                    groupId, userClassId, user.getUserId(),
                    "participation", SocialActivityCounterConstants
                    .TYPE_ACTOR, pp + np, 0, SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM);

				c.setStartPeriod(startPeriod);
				c.setEndPeriod(endPeriod);
				c.setTotalValue(pp + np);
				c.setCurrentValue(np);
				SocialActivityCounterLocalServiceUtil.updateSocialActivityCounter(c);
				c =  SocialActivityCounterLocalServiceUtil.addActivityCounter(
                    groupId, userClassId, user.getUserId(),
                    "contribution", SocialActivityCounterConstants
                    .TYPE_CREATOR, pc + nc, 0, SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM);
				c.setStartPeriod(startPeriod);
				c.setEndPeriod(endPeriod);
				c.setTotalValue(pc + nc);
				c.setCurrentValue(nc);
				SocialActivityCounterLocalServiceUtil.updateSocialActivityCounter(c);
				c =  SocialActivityCounterLocalServiceUtil.addActivityCounter(
                    groupId, userClassId, user.getUserId(),
                    "creator.subscriptions", SocialActivityCounterConstants
                    .TYPE_CREATOR, pcs + ncs, 0, SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM);
				c.setStartPeriod(startPeriod);
				c.setEndPeriod(endPeriod);
				c.setTotalValue(pcs + ncs);
				c.setCurrentValue(ncs);
				SocialActivityCounterLocalServiceUtil.updateSocialActivityCounter(c);
				c =  SocialActivityCounterLocalServiceUtil.addActivityCounter(
                    groupId, userClassId, user.getUserId(),
                    "creator.comments", SocialActivityCounterConstants
                    .TYPE_CREATOR, pcc + ncc, 0, SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM);
				c.setStartPeriod(startPeriod);
				c.setEndPeriod(endPeriod);
				c.setTotalValue(pcc + ncc);
				c.setCurrentValue(ncc);
				SocialActivityCounterLocalServiceUtil.updateSocialActivityCounter(c);

                pub += nub;
                puw += nuw;
                pubu += nubu;
                puwu += nuwu;
                pump += nump;
                pua += nua;
                puc += nuc;
                puv += nuv;
                pp += np;
                pc += nc;
                pcs += ncs;
                pcc += ncc;

            }
        }

        for (BlogsEntry blog : BlogsEntryLocalServiceUtil.getBlogsEntries
            (QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

            int paa = 0;
            int pac = 0;
            int par = 0;
            int pas = 0;
            int pp = 0;
            int pav = 0;
			long[] cids = new long[] {0,0,0,0,0,0};

            for (int off = -12; off <= 0; off++) {
                int naa = (int) (Math.random() * 19.0);
                int nac = (int) (Math.random() * 19.0);
                int nar = (int) (Math.random() * 19.0);
                int nas = (int) (Math.random() * 14.0);
                int np = (int) (Math.random() * 14.0);
                int nav = (int) (Math.random() * 14.0);

                naa = (int) ((double) naa * (1.0 + ((double) off / 12.0)));
                nac = (int) ((double) nac * (1.0 + ((double) off / 12.0)));
                nar = (int) ((double) nar * (1.0 + ((double) off / 12.0)));
                nas = (int) ((double) nas * (1.0 + ((double) off / 12.0)));
                np = (int) ((double) np * (1.0 + ((double) off / 12.0)));
                nav = (int) ((double) nav * (1.0 + ((double) off / 12.0)));

                int startPeriod = SocialCounterPeriodUtil.getStartPeriod(off);
                int endPeriod = -1;
                if (off != 0) {
                    endPeriod = SocialCounterPeriodUtil.getEndPeriod(off);
                }


                c = SocialActivityCounterLocalServiceUtil.addActivityCounter(
                    groupId, blogClassId, blog.getEntryId(),
                    "asset.activities", SocialActivityCounterConstants
                    .TYPE_ASSET, naa + paa, 0, SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM);

				c.setStartPeriod(startPeriod);
				c.setEndPeriod(endPeriod);
				c.setTotalValue(naa+paa);
				c.setCurrentValue(naa);
				SocialActivityCounterLocalServiceUtil.updateSocialActivityCounter(c);

                c = SocialActivityCounterLocalServiceUtil.addActivityCounter(
                    groupId, blogClassId, blog.getEntryId(),
                    "asset.comments", SocialActivityCounterConstants
                    .TYPE_ASSET, nac + pac, 0, SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM);
				c.setStartPeriod(startPeriod);
				c.setEndPeriod(endPeriod);
				c.setTotalValue(nac+pac);
				c.setCurrentValue(nac);
				SocialActivityCounterLocalServiceUtil.updateSocialActivityCounter(c);
                c = SocialActivityCounterLocalServiceUtil.addActivityCounter(
                    groupId, blogClassId, blog.getEntryId(),
                    "asset.replies", SocialActivityCounterConstants
                    .TYPE_ASSET, nar + par, 0, SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM);
				c.setStartPeriod(startPeriod);
				c.setEndPeriod(endPeriod);
				c.setTotalValue(nar+par);
				c.setCurrentValue(nar);
				SocialActivityCounterLocalServiceUtil.updateSocialActivityCounter(c);
                c = SocialActivityCounterLocalServiceUtil.addActivityCounter(
                    groupId, blogClassId, blog.getEntryId(),
                    "asset.subscriptions", SocialActivityCounterConstants
                    .TYPE_ASSET, pas + nas, 0, SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM);
				c.setStartPeriod(startPeriod);
				c.setEndPeriod(endPeriod);
				c.setTotalValue(pas+nas);
				c.setCurrentValue(nas);
				SocialActivityCounterLocalServiceUtil.updateSocialActivityCounter(c);

				c = SocialActivityCounterLocalServiceUtil.addActivityCounter(
                    groupId, blogClassId, blog.getEntryId(),
                    "popularity", SocialActivityCounterConstants.TYPE_ASSET,
                     pp + np, 0, SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM);
				c.setStartPeriod(startPeriod);
				c.setEndPeriod(endPeriod);
				c.setTotalValue(pp+np);
				c.setCurrentValue(np);
				SocialActivityCounterLocalServiceUtil.updateSocialActivityCounter(c);

				c = SocialActivityCounterLocalServiceUtil.addActivityCounter(
                    groupId, blogClassId, blog.getEntryId(),
                    "asset.votes", SocialActivityCounterConstants.TYPE_ASSET, pav + nav, 0, SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM);
				c.setStartPeriod(startPeriod);
				c.setEndPeriod(endPeriod);
				c.setTotalValue(pav+nav);
				c.setCurrentValue(nav);
				SocialActivityCounterLocalServiceUtil.updateSocialActivityCounter(c);
                paa += naa;
                pac += nac;
                par += nar;
                pas += nas;
                pp += np;
                pav += nav;

            }
        }

        for (WikiPage wiki : WikiPageLocalServiceUtil.getWikiPages(QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

            int paa = 0;
            int pac = 0;
            int pas = 0;
            int pp = 0;
            int pav = 0;
            for (int off = -12; off <= 0; off++) {
                int naa = (int) (Math.random() * 19.0);
                int nac = (int) (Math.random() * 19.0);
                int nas = (int) (Math.random() * 14.0);
                int np = (int) (Math.random() * 14.0);
                int nav = (int) (Math.random() * 14.0);

                naa = (int) ((double) naa * (1.0 + ((double) off / 12.0)));
                nac = (int) ((double) nac * (1.0 + ((double) off / 12.0)));
                nas = (int) ((double) nas * (1.0 + ((double) off / 12.0)));
                np = (int) ((double) np * (1.0 + ((double) off / 12.0)));
                nav = (int) ((double) nav * (1.0 + ((double) off / 12.0)));

                int startPeriod = SocialCounterPeriodUtil.getStartPeriod(off);
                int endPeriod = -1;
                if (off != 0) {
                    endPeriod = SocialCounterPeriodUtil.getEndPeriod(off);
                }
                c = SocialActivityCounterLocalServiceUtil.addActivityCounter(
                    groupId, wikiClassId, wiki.getPrimaryKey(),
                    "asset.activities", SocialActivityCounterConstants.TYPE_ASSET, naa + paa, 0, SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM);
				c.setStartPeriod(startPeriod);
				c.setEndPeriod(endPeriod);
				c.setTotalValue(naa+paa);
				c.setCurrentValue(naa);
				SocialActivityCounterLocalServiceUtil.updateSocialActivityCounter(c);
				c = SocialActivityCounterLocalServiceUtil.addActivityCounter(
                    groupId, wikiClassId, wiki.getPrimaryKey(),
                    "asset.comments", SocialActivityCounterConstants.TYPE_ASSET, pac + nac, 0, SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM);
				c.setStartPeriod(startPeriod);
				c.setEndPeriod(endPeriod);
				c.setTotalValue(pac+nac);
				c.setCurrentValue(nac);
				SocialActivityCounterLocalServiceUtil.updateSocialActivityCounter(c);
				c = SocialActivityCounterLocalServiceUtil.addActivityCounter(
                    groupId, wikiClassId, wiki.getPrimaryKey(),
                    "asset.subscriptions", SocialActivityCounterConstants.TYPE_ASSET, pas + nas, 0, SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM);
				c.setStartPeriod(startPeriod);
				c.setEndPeriod(endPeriod);
				c.setTotalValue(pas+nas);
				c.setCurrentValue(nas);

				SocialActivityCounterLocalServiceUtil.updateSocialActivityCounter(c);
				c = SocialActivityCounterLocalServiceUtil.addActivityCounter(
                    groupId, wikiClassId, wiki.getPrimaryKey(),
                    "popularity", SocialActivityCounterConstants.TYPE_ASSET, pp + np, 0, SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM);
				c.setStartPeriod(startPeriod);
				c.setEndPeriod(endPeriod);
				c.setTotalValue(pp+np);
				c.setCurrentValue(np);
				SocialActivityCounterLocalServiceUtil.updateSocialActivityCounter(c);
				c = SocialActivityCounterLocalServiceUtil.addActivityCounter(
                    groupId, wikiClassId, wiki.getPrimaryKey(),
                    "asset.votes", SocialActivityCounterConstants.TYPE_ASSET, pav + nav, 0, SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM);
				c.setStartPeriod(startPeriod);
				c.setEndPeriod(endPeriod);
				c.setTotalValue(pav+nav);
				c.setCurrentValue(nav);

				SocialActivityCounterLocalServiceUtil.updateSocialActivityCounter(c);
                paa += naa;
                pac += nac;
                pas += nas;
                pp += np;
                pav += nav;

            }
        }

//        for (MBMessage message : MBMessageLocalServiceUtil.getGroupMessages(groupId, WorkflowConstants.STATUS_ANY,
//            QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {
//            int paa = 0;
//            int nar = 0;
//            int pp = 0;
//            int pav = 0;
//
//            for (int off = -12; off <= 0; off++) {
//                int naa = (int) (Math.random() * 19.0);
//                int par = (int) (Math.random() * 19.0);
//                int np = (int) (Math.random() * 14.0);
//                int nav = (int) (Math.random() * 14.0);
//
//                naa = (int) ((double) naa * (1.0 + ((double) off / 12.0)));
//                par = (int) ((double) par * (1.0 + ((double) off / 12.0)));
//                np = (int) ((double) np * (1.0 + ((double) off / 12.0)));
//                nav = (int) ((double) nav * (1.0 + ((double) off / 12.0)));
//
//                int startPeriod = SocialCounterPeriodUtil.getStartPeriod(off);
//                int endPeriod = -1;
//                if (off != 0) {
//                    endPeriod = SocialCounterPeriodUtil.getEndPeriod(off);
//                }
//                SocialActivityCounterLocalServiceUtil.addActivityCounter(
//                    groupId, mbClassId, message.getPrimaryKey(),
//                    "asset.activities", SocialActivityCounterConstants.TYPE_ASSET, naa, naa + paa,
//                    startPeriod,
//                    endPeriod);
//                SocialActivityCounterLocalServiceUtil.addActivityCounter(
//                    groupId, mbClassId, message.getPrimaryKey(),
//                    "popularity", SocialActivityCounterConstants.TYPE_ASSET, np, pp + np,
//                    startPeriod,
//                    endPeriod);
//                SocialActivityCounterLocalServiceUtil.addActivityCounter(
//                    groupId, mbClassId, message.getPrimaryKey(),
//                    "asset.votes", SocialActivityCounterConstants.TYPE_ASSET, nav, pav + nav,
//                    startPeriod,
//                    endPeriod);
//                SocialActivityCounterLocalServiceUtil.addActivityCounter(
//                    groupId, mbClassId, message.getPrimaryKey(),
//                    "asset.replies", SocialActivityCounterConstants.TYPE_ASSET, nar, par + nar,
//                    startPeriod,
//                    endPeriod);
//                paa += naa;
//                par += nar;
//                pp += np;
//                pav += nav;
//
//            }
//        }

    }


}


