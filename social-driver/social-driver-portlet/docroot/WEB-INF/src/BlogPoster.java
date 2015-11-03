import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.ratings.service.RatingsEntryServiceUtil;

import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;

public class BlogPoster extends Thread {

private SocialDriverContentUtil contentContainer;
private String themeId;
private long companyId;
private long groupId;
private boolean profileFlag;

// Controls for turning on/off content generators
private AtomicBoolean blogThreadOn = new AtomicBoolean(false);

public BlogPoster(long companyId, long groupId, String themeId,
                  boolean profileFlag, SocialDriverContentUtil
    contentContainer) {
    this.companyId = companyId;
    this.groupId = groupId;
    this.themeId = themeId;
    this.contentContainer = contentContainer;
    this.profileFlag = profileFlag;
}

public void turnOn() {
    blogThreadOn.set(true);
}

public boolean isRunning() {
    return blogThreadOn.get();
}

public void turnOff() {
    blogThreadOn.set(false);
}

private SocialDriverConstants.BLOG_ACTION getRandomAction() {
    return SocialDriverConstants.BLOG_ACTION.values()[(int) (Math.random
        () * ((double) SocialDriverConstants.BLOG_ACTION.values().length))];
}

public void run() {
    try {
        while (true) {
            while (!blogThreadOn.get()) {
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
    context.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
    context.setAddGroupPermissions(true);
    context.setAddGuestPermissions(true);

    context.setAssetTagNames(tags);
    context.setCompanyId(companyId);
    context.setScopeGroupId(groupId);

    BlogsEntry newEntry = BlogsEntryLocalServiceUtil.addEntry
        (SocialDriverUtil.getUserId(companyId, themeId, profileFlag),
            title, "",
        content,
        rCal.get(Calendar.MONTH), rCal.get(Calendar.DAY_OF_MONTH),
        rCal.get(Calendar.YEAR),
        rCal.get(Calendar.HOUR_OF_DAY), rCal.get(Calendar.MINUTE), false,
            false, null, false, null,
        null, null, context);

//    System.out.println("Added blog " + newEntry.getTitle() + " Tags: " +
//        Arrays.asList(tags));
}

private void commentEntry() throws Exception {

    if (BlogsEntryLocalServiceUtil.getBlogsEntriesCount() <= 0) return;
    int rand = (int) (Math.random() * (double) BlogsEntryLocalServiceUtil
        .getBlogsEntriesCount());
    BlogsEntry entry = BlogsEntryLocalServiceUtil.getBlogsEntries(rand,
        rand + 1).get(0);
    Calendar rCal = SocialDriverUtil.getCal();

    long userId = SocialDriverUtil.getUserId(companyId, themeId,
        profileFlag);
    ServiceContext context = new ServiceContext();
    context.setCreateDate(rCal.getTime());
    context.setModifiedDate(rCal.getTime());
    context.setCompanyId(companyId);
    context.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
    context.setAddGroupPermissions(true);
    context.setAddGuestPermissions(true);

    context.setScopeGroupId(groupId);

    MBDiscussion disc = MBDiscussionLocalServiceUtil.getDiscussion
        (BlogsEntry.class.getName(),
        entry.getPrimaryKey());
    MBMessageLocalServiceUtil.addDiscussionMessage(userId, "Joe Schmoe",
        context.getScopeGroupId(), BlogsEntry.class.getName(),
        entry.getPrimaryKey(),
        disc.getThreadId(), MBThreadLocalServiceUtil.getMBThread(disc
        .getThreadId()).getRootMessageId(),
        "Subject of comment", "This is great", context);

  //  System.out.println("Commented on Blog " + entry.getTitle());
}

private void voteEntry() throws Exception {

    if (BlogsEntryLocalServiceUtil.getBlogsEntriesCount() <= 0) return;
    int rand = (int) (Math.random() * (double) BlogsEntryLocalServiceUtil.getBlogsEntriesCount());
    BlogsEntry entry = BlogsEntryLocalServiceUtil.getBlogsEntries(rand, rand + 1).get(0);
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
    RatingsEntryServiceUtil.updateEntry(BlogsEntry.class.getName(), entry.getEntryId(),
        (int) (Math.random() * 0.0) + 1);
 //   System.out.println("Voted on Blog " + entry.getTitle());
}

}
