import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.ratings.service.RatingsEntryServiceUtil;
import com.liferay.wiki.exception.DuplicatePageException;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;

import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;

public class WikiPoster extends Thread {

    private long companyId;
    private long groupId;
    private String themeId;
    private boolean profileFlag;

    private SocialDriverContentUtil contentContainer;

    // Controls for turning on/off content generators
    private AtomicBoolean wikiThreadOn = new AtomicBoolean(false);

    public WikiPoster(long companyId, long groupId, String themeId,
                      boolean profileFlag, SocialDriverContentUtil
        contentContainer) {
        this.companyId = companyId;
        this.themeId = themeId;
        this.groupId = groupId;
        this.contentContainer = contentContainer;
        this.profileFlag = profileFlag;
    }

    public void turnOn() {
        wikiThreadOn.set(true);
    }

    public boolean isRunning() {
        return wikiThreadOn.get();
    }

    public void turnOff() {
        wikiThreadOn.set(false);
    }

    private SocialDriverConstants.WIKI_ACTION getRandomAction() {
        return SocialDriverConstants.WIKI_ACTION.values()[(int) (Math.random
            () * ((double) SocialDriverConstants.WIKI_ACTION.values().length))];
    }

    public void run() {
        try {
            while (true) {
                while (!wikiThreadOn.get()) {
                    Thread.sleep(SocialDriverConstants.WAITTIME);
                }

                switch (getRandomAction()) {

                    case ADD_ENTRY:
                        addEntry();
                        break;
                    case ADD_COMMENT:
                        commentEntry();
                        break;
                    case READ:
                        break;
                    case SUBSCRIBE:
                        break;
                    case UNSUBSCRIBE:
                        break;
                    case UPDATE:
                        break;
                    case VOTE:
                        voteEntry();
                        break;
                }
//                System.out.println("sleeping for " + SocialDriverConstants
//                    .WAITTIME);
                Thread.sleep(SocialDriverConstants.WAITTIME);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addEntry() throws Exception {
        Calendar rCal = SocialDriverUtil.getCal();


        ServiceContext context = new ServiceContext();
        context.setCreateDate(rCal.getTime());
        context.setModifiedDate(rCal.getTime());
        String cid = contentContainer.getRandomId();
        String title = contentContainer.getContentTitle(cid);
        String content = contentContainer.getContentBody(cid);
        String[] tags = contentContainer.getContentTags(cid);
        context.setAssetTagNames(tags);
        context.setCompanyId(companyId);
        context.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
        context.setAddGroupPermissions(true);
        context.setAddGuestPermissions(true);

        context.setScopeGroupId(groupId);

        WikiNode def;

        long defId = UserLocalServiceUtil.getDefaultUserId(companyId);
        try {
            def = WikiNodeLocalServiceUtil.getNode(groupId, "Main");
 //           System.out.println("got main node for group " + groupId + ": " +
 //               def);
        } catch (Exception ex) {
            def = WikiNodeLocalServiceUtil.addDefaultNode(defId, context);
 //           System.out.println("created main node for group " + groupId + ": " +
 //               "" + def);
        }

        try {
            WikiPageLocalServiceUtil.addPage(SocialDriverUtil.getUserId
                (companyId, themeId, profileFlag), def.getNodeId(), title,
                content, "",
                false, context);
 //           System.out.println("Added Wiki " + title + "node:" + def + " " +
 //               "tags: " + Arrays.asList(tags));
        } catch (DuplicatePageException ignored) {

        }
    }

    private void commentEntry() throws Exception {

        if (WikiPageLocalServiceUtil.getWikiPagesCount() <= 0) return;
        int rand = (int) (Math.random() * (double) WikiPageLocalServiceUtil
            .getWikiPagesCount());
        WikiPage entry = WikiPageLocalServiceUtil.getWikiPages(rand,
            rand + 1).get(0);
        Calendar rCal = SocialDriverUtil.getCal();

        long userId = SocialDriverUtil.getUserId(companyId, themeId,
            profileFlag);
        ServiceContext context = new ServiceContext();
        context.setCreateDate(rCal.getTime());
        context.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
        context.setModifiedDate(rCal.getTime());
        context.setCompanyId(companyId);
        context.setScopeGroupId(groupId);
        context.setAddGroupPermissions(true);
        context.setAddGuestPermissions(true);

 //       System.out.println("Commenting on wiki \"" + entry.getTitle() + "\"");

        MBDiscussion disc = MBDiscussionLocalServiceUtil.getDiscussion
            (WikiPage.class.getName(),
            entry.getResourcePrimKey());
        MBMessageLocalServiceUtil.addDiscussionMessage(userId, "Joe Schmoe",
            context.getScopeGroupId(), WikiPage.class.getName(),
            entry.getResourcePrimKey(),
            disc.getThreadId(), MBThreadLocalServiceUtil.getMBThread(disc
            .getThreadId()).getRootMessageId(),
            "Subject of comment", "This is great", context);
    }

    private void voteEntry() throws Exception {

        if (WikiPageLocalServiceUtil.getWikiPagesCount() <= 0) return;
        int rand = (int) (Math.random() * (double) WikiPageLocalServiceUtil.getWikiPagesCount());
        WikiPage entry = WikiPageLocalServiceUtil.getWikiPages(rand, rand + 1).get(0);
        Calendar rCal = SocialDriverUtil.getCal();

        long userId = SocialDriverUtil.getUserId(companyId, themeId, profileFlag);
        ServiceContext context = new ServiceContext();
        context.setCreateDate(rCal.getTime());
        context.setModifiedDate(rCal.getTime());
        context.setCompanyId(companyId);
        context.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
        context.setAddGroupPermissions(true);
        context.setAddGuestPermissions(true);

        context.setScopeGroupId(groupId);

        PrincipalThreadLocal.setName(userId);
        RatingsEntryServiceUtil.updateEntry(WikiPage.class.getName(), entry.getResourcePrimKey(),
            (int) (Math.random() * 0.0) + 1);

 //       System.out.println("Voted on Wiki entry " + entry.getTitle());
    }

}
