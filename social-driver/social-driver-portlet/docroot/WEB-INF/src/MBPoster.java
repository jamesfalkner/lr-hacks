import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;

public class MBPoster extends Thread {

    private long companyId;
    private String themeId;
    private long groupId;
    private SocialDriverContentUtil contentContainer;
    private boolean profileFlag;

    // Controls for turning on/off content generators
    private AtomicBoolean MBThreadOn = new AtomicBoolean(false);

    public MBPoster(long companyId, long groupId, String themeId,
                    boolean profileFlag, SocialDriverContentUtil
        contentContainer) {
        this.companyId = companyId;
        this.themeId = themeId;
        this.groupId = groupId;
        this.profileFlag = profileFlag;
        this.contentContainer = contentContainer;
    }


    public void turnOn() {
        MBThreadOn.set(true);
    }

    public boolean isRunning() {
        return MBThreadOn.get();
    }

    public void turnOff() {
        MBThreadOn.set(false);
    }


    private SocialDriverConstants.MB_ACTION getRandomAction() {
        if (Math.random() > 0.6)
            return SocialDriverConstants.MB_ACTION.ADD_REPLY;
        return SocialDriverConstants.MB_ACTION.ADD_MESSAGE;
    }

    public void run() {
        try {
            while (true) {
                while (!MBThreadOn.get()) {
                    Thread.sleep(SocialDriverConstants.WAITTIME);
                }

                switch (getRandomAction()) {

                    case ADD_MESSAGE:
                        addMsg();
                        break;
                    case ADD_REPLY:
                        replyMsg();
                        break;
                }
                Thread.sleep(SocialDriverConstants.WAITTIME);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void replyMsg() throws Exception {
        Calendar rCal = SocialDriverUtil.getCal();


        ServiceContext context = new ServiceContext();
        context.setCreateDate(rCal.getTime());
        context.setModifiedDate(rCal.getTime());
        String cid = contentContainer.getRandomId();
        String content = contentContainer.getContentBody(cid);
        String[] tags = contentContainer.getContentTags(cid);
        context.setAssetTagNames(tags);
        context.setCompanyId(companyId);
        context.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
        context.setAddGroupPermissions(true);
        context.setAddGuestPermissions(true);

        context.setScopeGroupId(groupId);

        if (MBMessageLocalServiceUtil.getGroupMessagesCount(groupId,
            WorkflowConstants.STATUS_APPROVED) <= 0) {
            return;
        }

        int rand = (int) (Math.random() * (double) MBMessageLocalServiceUtil
            .getGroupMessagesCount(groupId,
                WorkflowConstants.STATUS_APPROVED));
        MBMessage msg = MBMessageLocalServiceUtil.getGroupMessages(groupId,
            WorkflowConstants.STATUS_APPROVED,
            rand, rand + 1).get(0);
        long categoryId = msg.getCategoryId();
        long threadId = msg.getThreadId();
        long parentId = msg.getMessageId();
        MBMessageLocalServiceUtil.addMessage(SocialDriverUtil.getUserId
            (companyId, themeId, profileFlag), "Joe Schmoe", groupId,
            categoryId, threadId,
            parentId, "RE: " + msg.getSubject(), content, "html",
            new ArrayList<ObjectValuePair<String,
                InputStream>>(), false, 1.0, true, context);

//        System.out.println("replied to msg " + msg.getSubject() + " in " +
//            "category " + msg.getCategory().getName());
    }

    private void addMsg() throws Exception {

        Calendar rCal = SocialDriverUtil.getCal();

        long userId = SocialDriverUtil.getUserId(companyId, themeId,
            profileFlag);
        String contentId = contentContainer.getRandomId();
        String title = contentContainer.getContentTitle(contentId);
        String content = contentContainer.getContentBody(contentId);
        String[] tags = contentContainer.getContentTags(contentId);

        ServiceContext context = new ServiceContext();
        context.setCreateDate(rCal.getTime());
        context.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
        context.setModifiedDate(rCal.getTime());
        context.setAssetTagNames(tags);
        context.setAddGroupPermissions(true);
        context.setAddGuestPermissions(true);

        context.setCompanyId(companyId);
        context.setScopeGroupId(groupId);

        MBCategory newcat = MBCategoryLocalServiceUtil.addCategory(userId, 0,
            StringUtil.shorten(title, 70),
            "Discussions about " + title, MBCategoryConstants
            .DEFAULT_DISPLAY_STYLE, null, null,
            null, 0, false, null, null, -1, null, false, null, 0, false,
            null, null, false, false, context);

        MBMessageLocalServiceUtil.addMessage(SocialDriverUtil.getUserId
            (companyId, themeId, profileFlag), "Hoe Schmoe", groupId,
            newcat.getCategoryId(), 0, 0,
            title, content, "html", new ArrayList<ObjectValuePair<String,
            InputStream>>(), false, 1.0, true,
            context);
   //     System.out.println("added msg " + title + " in category " + newcat
    //        .getName());

    }

    private void voteEntry() throws Exception {        /* NOTIMPLEMENTED */
    }
}
