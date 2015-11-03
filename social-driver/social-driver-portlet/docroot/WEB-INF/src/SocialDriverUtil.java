import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Country;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.Region;
import com.liferay.portal.model.User;
import com.liferay.portal.service.AddressLocalServiceUtil;
import com.liferay.portal.service.CountryServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.RegionServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.social.model.SocialRequest;
import com.liferay.portlet.social.model.SocialRequestConstants;
import com.liferay.portlet.social.service.SocialRequestLocalServiceUtil;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;

import javax.portlet.PortletPreferences;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SocialDriverUtil {
// Delete all blog entries in the specified group
public static void clearBlogs(long groupId) throws Exception {
    BlogsEntryLocalServiceUtil.deleteEntries(groupId);
}

// Delete all MB entries in the specified company
public static void clearMB(long companyId) throws Exception {

}

// Delete all wiki entries in the specified group
public static void clearWikis(long groupId) throws Exception {
    WikiNode def = WikiNodeLocalServiceUtil.getNode(groupId, "Main");
    WikiPageLocalServiceUtil.deletePages(def.getNodeId());
}

// retrieves a random user from the company.  If the randomly chosen user
// doesn't exist, create them first
public static long getUserId(long companyId, String themeId,
                             boolean profileFlag)
    throws Exception {

    User user;
    String first = getRndStr(SocialDriverConstants.FIRST_NAMES);
    try {
        user = UserLocalServiceUtil.getUserByScreenName(companyId,
            first.toLowerCase());
    } catch (NoSuchUserException ex) {
        user = addUser(first, companyId, themeId, profileFlag);
    }
    return user.getUserId();

}

// generate a friend request and optionally confirm it
public static void addSocialRequest(
    User user, User receiverUser, boolean confirm)
    throws Exception {

    SocialRequest socialRequest = SocialRequestLocalServiceUtil.addRequest(
        user.getUserId(), 0, User.class.getName(), user.getUserId(),
        1, StringPool.BLANK,
        receiverUser.getUserId());

    if (confirm) {
        SocialRequestLocalServiceUtil.updateRequest(
            socialRequest.getRequestId(),
            SocialRequestConstants.STATUS_CONFIRM, new ThemeDisplay());
    }
}

// add a portlet to a layout
public static String addPortletId(
    Layout layout, String portletId, String columnId)
    throws Exception {

    LayoutTypePortlet layoutTypePortlet =
        (LayoutTypePortlet) layout.getLayoutType();

    portletId = layoutTypePortlet.addPortletId(
        0, portletId, columnId, -1, false);

    addResources(layout, portletId);
    updateLayout(layout);

    return portletId;
}

// persist an updated layout entity
public static void updateLayout(Layout layout) throws Exception {
    LayoutLocalServiceUtil.updateLayout(
        layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
        layout.getTypeSettings());
}

// configure a web content display to show a specific article
public static void configureJournalContent(
    Layout layout, Group group, String portletId, String articleId)
    throws Exception {

    PortletPreferences portletSetup =
        PortletPreferencesFactoryUtil.getLayoutPortletSetup(
            layout, portletId);

    if (group == null) {
        portletSetup.setValue("groupId", String.valueOf(layout.getGroupId
            ()));
    } else {
        portletSetup.setValue("groupId", String.valueOf(group.getGroupId
            ()));
    }
    portletSetup.setValue("articleId", articleId);

    portletSetup.store();
}

// set a custom title for a portlet
public static void configurePortletTitle(
    Layout layout, String portletId, String title)
    throws Exception {

    PortletPreferences portletSetup =
        PortletPreferencesFactoryUtil.getLayoutPortletSetup(
            layout, portletId);

    portletSetup.setValue("portletSetupUseCustomTitle",
        String.valueOf(Boolean.TRUE));
    portletSetup.setValue("portletSetupTitle_en_US", title);

    portletSetup.store();
}

public static String getRndStr(String[] ar) {
    return ar[(int) (Math.random() * (double) ar.length)];
}

// add a new user to the DB, using random fake data,
// and set up their profile pages, and add
// them to the right sites, friend a couple of other random users.
public synchronized static User addUser(String firstName, long companyId,
                                        String themeId, boolean profileFlag)
    throws Exception {

    String lastName = getRndStr(SocialDriverConstants.LAST_NAMES);
    String job = getRndStr(SocialDriverConstants.JOB_TITLES);
    Group guestGroup = GroupLocalServiceUtil.getGroup(
        companyId, GroupConstants.GUEST);

    long[] groupIds;
    try {
        // see if there's a demo sales group, and add the user to the
        // group if so
        Group salesGroup = GroupLocalServiceUtil.getGroup(
            companyId, "Sales");
        groupIds = new long[]{guestGroup.getGroupId(),
            salesGroup.getGroupId()};
    } catch (Exception ex) {
        groupIds = new long[]{guestGroup.getGroupId()};
    }
    ServiceContext serviceContext = new ServiceContext();

    long[] roleIds;
    try {
        // see if we're using social office, and add SO's role to the new
        // user if so
        roleIds = new long[]{RoleLocalServiceUtil.getRole(companyId,
            "Social Office User").getRoleId()};
        serviceContext.setScopeGroupId(guestGroup.getGroupId());
    } catch (Exception ignored) {
        roleIds = new long[]{};
    }
    User user = UserLocalServiceUtil.addUser(UserLocalServiceUtil
        .getDefaultUserId(companyId), companyId, false,
        firstName,
        firstName,
        false, firstName.toLowerCase(), firstName.toLowerCase() +
        "@liferay.com", 0, "",
        LocaleUtil.getDefault(), firstName,
        "", lastName, 0, 0, true, 8, 15, 1974, job, groupIds,
        new long[]{}, roleIds, new long[]{},
        false, serviceContext);

    assignAddressTo(user);
    setFirstLogin(firstName, user);
    assignRandomFriends(user);

    /**
    if (profileFlag) {
        setupUserProfile(companyId, themeId, guestGroup, user);
    }
    **/
    return user;

}

// setup a user's public/private page theme, and construct some useful
// profile pages
/**
private static void setupUserProfile(long companyId, String themeId,
                                     Group guestGroup, User user)
    throws Exception {
    Group group = user.getGroup();
    if (themeId != null && !themeId.isEmpty()) {
        LayoutSetLocalServiceUtil.updateLookAndFeel(
            group.getGroupId(), false, themeId, "01", "",
            false);
    }

    // Profile layout

    Layout layout = addLayout(
        group, user.getFullName(), false, "/profile", "2_columns_ii");
    JournalArticle cloudArticle, assetListArticle;

    try {
        cloudArticle = JournalArticleLocalServiceUtil.getLatestArticle
            (guestGroup.getGroupId(),
            SocialDriverConstants.CLOUD_ARTICLE_ID);
    } catch (NoSuchArticleException ex) {
        cloudArticle = addArticle("/cloud-structure.xml",
            "/cloud-template.vm", "cloud-article.xml",
            UserLocalServiceUtil.getDefaultUserId(companyId),
            guestGroup.getGroupId(),
            SocialDriverConstants.CLOUD_ARTICLE_ID);
    }

    try {
        assetListArticle = JournalArticleLocalServiceUtil
            .getLatestArticle(guestGroup.getGroupId(),
            SocialDriverConstants.ASSETLIST_ARTICLE_ID);
    } catch (NoSuchArticleException ex) {
        assetListArticle = addArticle("/assetlist-structure.xml",
            "/assetlist-template.vm", "assetlist-article.xml",
            UserLocalServiceUtil.getDefaultUserId(companyId),
            guestGroup.getGroupId(),
            SocialDriverConstants.ASSETLIST_ARTICLE_ID);
    }
    try {
        JournalArticleLocalServiceUtil.getLatestArticle(guestGroup
            .getGroupId(),
            SocialDriverConstants.EXPERTSLIST_ARTICLE_ID);
    } catch (NoSuchArticleException ex) {
        addArticle("/experts-structure.xml", "/experts-template.vm",
            "experts-article.xml",
            UserLocalServiceUtil.getDefaultUserId(companyId),
            guestGroup.getGroupId(),
            SocialDriverConstants.EXPERTSLIST_ARTICLE_ID);
    }

    addPortletId(layout, "1_WAR_socialnetworkingportlet", "column-1");
    addPortletId(layout, PortletKeys.REQUESTS, "column-1");
    String portletId = addPortletId(layout, PortletKeys.JOURNAL_CONTENT,
        "column-1");
    configureJournalContent(
        layout, guestGroup, portletId, cloudArticle.getArticleId());
    configurePortletTitle(layout, portletId, "Expertise");
    addPortletBorder(layout, portletId);

    addPortletBorder(layout, addPortletId(layout,
        "2_WAR_socialnetworkingportlet", "column-1"));

    addPortletBorder(layout, addPortletId(layout, PortletKeys.ACTIVITIES,
        "column-2"));
    addPortletBorder(layout, addPortletId(layout,
        "3_WAR_socialnetworkingportlet", "column-2"));

    // Expertise layout

    layout = addLayout(group, "Expertise", false, "/expertise",
        "2_columns_ii");

    addPortletId(layout, "1_WAR_socialnetworkingportlet", "column-1");
    addPortletId(layout, PortletKeys.REQUESTS, "column-1");
    portletId = addPortletId(layout, PortletKeys.JOURNAL_CONTENT,
        "column-1");
    configureJournalContent(
        layout, guestGroup, portletId, cloudArticle.getArticleId());
    configurePortletTitle(layout, portletId, "Expertise");
    addPortletBorder(layout, portletId);

    portletId = addPortletId(layout, PortletKeys.JOURNAL_CONTENT,
        "column-2");
    configureJournalContent(
        layout, guestGroup, portletId, assetListArticle.getArticleId());
    configurePortletTitle(layout, portletId, user.getFirstName() + "'s " +
        "Contributions");
    addPortletBorder(layout, portletId);

    // Social layout

    layout = addLayout(group, "Social", false, "/social", "2_columns_ii");

    addPortletId(layout, "1_WAR_socialnetworkingportlet", "column-1");
    addPortletId(layout, PortletKeys.REQUESTS, "column-1");
    addPortletBorder(layout, addPortletId(layout,
        "2_WAR_socialnetworkingportlet", "column-1"));
    addPortletBorder(layout, addPortletId(layout, PortletKeys.ACTIVITIES,
        "column-2"));
    addPortletBorder(layout, addPortletId(layout,
        "3_WAR_socialnetworkingportlet", "column-2"));

    // Blog layout

    layout = addLayout(group, "Blog", false, "/blog", "2_columns_ii");

    addPortletBorder(layout, addPortletId(layout,
        PortletKeys.RECENT_BLOGGERS, "column-1"));
    addPortletBorder(layout, addPortletId(layout, PortletKeys.BLOGS,
        "column-2"));

    // Workspace layout

    layout = addLayout(
        group, "Workspace", false, "/workspace", "2_columns_i");

    addPortletBorder(layout, addPortletId(layout,
        PortletKeys.RECENT_DOCUMENTS, "column-1"));
    addPortletBorder(layout, addPortletId(layout,
        PortletKeys.DOCUMENT_LIBRARY, "column-2"));

    addPortletId(layout, PortletKeys.CALENDAR, "column-2");
}

**/

// make a fake social network of friends by randomly friending some
// people
private static void assignRandomFriends(User user) throws Exception {
    int userCount = UserLocalServiceUtil.getUsersCount();
    int friendCount = (int) (Math.random() * (double) userCount);
    for (int i = 0; i < friendCount; i++) {
        int frienduser = (int) (Math.random() * (double) userCount);
        User randUser = UserLocalServiceUtil.getUsers(frienduser,
            frienduser + 1).get(0);
        if (randUser.getUserId() == user.getUserId()) continue;
        if (randUser.isDefaultUser()) continue;
        boolean confirm = (Math.random() > .4);
        addSocialRequest(user, randUser, confirm);
    }
}

// make it easy to login the first time
private static void setFirstLogin(String password, User user)
    throws Exception {
    UserLocalServiceUtil.updatePortrait(user.getUserId(),
        getPortraitBytes());

    UserLocalServiceUtil.updateLastLogin(
        user.getUserId(), user.getLoginIP());
    UserLocalServiceUtil.updateAgreedToTermsOfUse(user.getUserId(), true);
    UserLocalServiceUtil.updatePassword(user.getUserId(),
        password.toLowerCase(), password.toLowerCase(),
        false, true);
    UserLocalServiceUtil.updatePasswordReset(user.getUserId(), false);


    String[] questions = StringUtil.split(
        PropsUtil.get("users.reminder.queries.questions"));

    String question = questions[0];
    String answer = "1234";

    UserLocalServiceUtil.updateReminderQuery(
        user.getUserId(), question, answer);
}

private static void assignAddressTo(User user)
    throws SystemException, PortalException {
    String street = "123 Anystreet";
    String city = "Doesnt Matter";
    String zip = "12342";
    List<Country> countrys = CountryServiceUtil.getCountries();
    Country country = countrys.get((int) (Math.random() * (double)
        countrys.size()));
    double rnd = Math.random();
    List<Region> regions = RegionServiceUtil.getRegions(country
        .getCountryId());
    long regionId = 0;
    if (regions != null && !regions.isEmpty()) {
        regionId = regions.get((int) (Math.random() * (double) regions
            .size())).getRegionId();
    }

    AddressLocalServiceUtil.addAddress(user.getUserId(),
        Contact.class.getName(), user.getContactId(),
        street, "", "", city, zip, regionId, country.getCountryId(),
        11002, true, true);
}

public static byte[] getBytes(String path) throws Exception {
    return FileUtil.getBytes(getInputStream(path));
}

public static InputStream getInputStream(String path) throws Exception {
    Class<?> clazz = SocialDriverUtil.class;

    return clazz.getResourceAsStream("/resources" + path);
}

public static void setLocalizedValue(Map<Locale, String> map,
                                     String value) {
    Locale locale = LocaleUtil.getDefault();

    map.put(locale, value);

    if (!locale.equals(Locale.US)) {
        map.put(Locale.US, value);
    }
}

public static String getString(String path) throws Exception {
    return new String(getBytes(path));
}
/**
public static JournalArticle addJournalArticle(
    long userId, long groupId, String title, String fileName,
    String structureId, String templateId,
    ServiceContext serviceContext)
    throws Exception {
	
	System.out.println(fileName);

    String content = getString(fileName);

    serviceContext.setAddGroupPermissions(true);
    serviceContext.setAddGuestPermissions(true);
    serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
    Map<Locale, String> titleMap = new HashMap<Locale, String>();

    setLocalizedValue(titleMap, title);
    try {
        JournalArticleLocalServiceUtil.deleteArticle(groupId, title,
            serviceContext);
    } catch (Exception ex) {
        System.out.println("Ignoring " + ex.getMessage());
    }

	//return null;

    return JournalArticleLocalServiceUtil.addArticle(
        userId, groupId, 0, 0, 0, title, false,
        JournalArticleConstants.VERSION_DEFAULT, titleMap, null,
        content, "general", structureId, templateId, StringPool.BLANK,
        1, 1, 2008, 0, 0, 0, 0, 0, 0, 0, true, 0, 0, 0, 0, 0, true,
        true, false, StringPool.BLANK, null, null, StringPool.BLANK,
        serviceContext);
}

public static JournalStructure addJournalStructure(
    long userId, long groupId, String title, String fileName)
    throws Exception {

    Map<Locale, String> nameMap = new HashMap<Locale, String>();

    setLocalizedValue(nameMap, title);

    Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

    setLocalizedValue(descriptionMap, title);

    String xsd = getString(fileName);

    ServiceContext serviceContext = new ServiceContext();
    serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

    serviceContext.setAddGroupPermissions(true);
    serviceContext.setAddGuestPermissions(true);

    try {
        JournalStructureLocalServiceUtil.deleteStructure(groupId, title);
    } catch (Exception ex) {
        System.out.println("Ignoring " + ex.getMessage());
    }
    return JournalStructureLocalServiceUtil.addStructure(
        userId, groupId, title, false, StringPool.BLANK, nameMap,
        descriptionMap, xsd, serviceContext);
}

public static JournalTemplate addJournalTemplate(
    long userId, long groupId, String title, String structureId,
    String fileName)
    throws Exception {

    Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

    setLocalizedValue(descriptionMap, "ATemplate");

    Map<Locale, String> nameMap = new HashMap<Locale, String>();

    setLocalizedValue(nameMap, "ATemplate");

    String xsl = getString(fileName);

    ServiceContext serviceContext = new ServiceContext();
    serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

    serviceContext.setAddGroupPermissions(true);
    serviceContext.setAddGuestPermissions(true);

    try {
        JournalTemplateLocalServiceUtil.deleteTemplate(groupId, title);
    } catch (Exception ex) {
        System.out.println("Ignoring " + ex.getMessage());

    }
    return JournalTemplateLocalServiceUtil.addTemplate(
        userId, groupId, title, false, structureId, nameMap,
        descriptionMap, xsl, true, "vm", false, false, StringPool.BLANK,
        null, serviceContext);
}

public static JournalArticle addArticle(String structPath,
                                        String tmplPath, String articlePath,
                                        long userId, long groupId,
                                        String articleId)
    throws Exception {
    JournalStructure struct = addJournalStructure(userId, groupId,
        articleId, structPath);
    JournalTemplate tmpl = addJournalTemplate(userId, groupId, articleId,
        struct.getStructureId(), tmplPath);
    ServiceContext serviceContext = new ServiceContext();
    serviceContext.setScopeGroupId(groupId);
    serviceContext.setAddGroupPermissions(true);
    serviceContext.setAddGuestPermissions(true);
    return addJournalArticle(
        userId, groupId,
        articleId, articlePath, struct.getStructureId(),
        tmpl.getTemplateId(), serviceContext);

}
**/
// set a portlet to show its borders
public static void addPortletBorder(Layout layout, String portletId)
    throws Exception {

    PortletPreferences portletSetup =
        PortletPreferencesFactoryUtil.getLayoutPortletSetup(
            layout, portletId);

    portletSetup.setValue(
        "portletSetupShowBorders", String.valueOf(Boolean.TRUE));

    portletSetup.store();
}

// create a new (blank) page for the given group/s public or private layoutset
/**
public static Layout addLayout(
    Group group, String name, boolean privateLayout, String friendlyURL,
    String layoutTemplateId)
    throws Exception {

    ServiceContext serviceContext = new ServiceContext();

    Layout layout = LayoutLocalServiceUtil.addLayout(
        group.getCreatorUserId(), group.getGroupId(), privateLayout,
        LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, name, StringPool.BLANK,
        StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false, friendlyURL,
        serviceContext);

    LayoutTypePortlet layoutTypePortlet =
        (LayoutTypePortlet) layout.getLayoutType();

    layoutTypePortlet.setLayoutTemplateId(0, layoutTemplateId, false);

    addResources(layout, PortletKeys.DOCKBAR);

    return layout;
}
**/

// add a resource to a layout with the portletId.  Yes, this comment is completely worthless.
public static void addResources(Layout layout, String portletId)
    throws Exception {

    String rootPortletId = PortletConstants.getRootPortletId(portletId);

    String portletPrimaryKey = PortletPermissionUtil.getPrimaryKey(
        layout.getPlid(), portletId);

    ResourceLocalServiceUtil.addResources(
        layout.getCompanyId(), layout.getGroupId(), 0, rootPortletId,
        portletPrimaryKey, true, true, true);
}

// fetch a random portrait from gravatar and return its bytes
public static byte[] getPortraitBytes() throws Exception {

    String id = SocialDriverConstants.GRAVATAR_IDS[(int) (Math.random() *
        (double) SocialDriverConstants.GRAVATAR_IDS.length)];
    String urlS = "http://2.gravatar.com/avatar/" + id + "?s=80&d=identicon";
    URL url = new URL(urlS);
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    InputStream in = url.openStream();
    byte[] buf = new byte[2048];
    int bytesRead;
    while ((bytesRead = in.read(buf)) != -1) {
        bos.write(buf, 0, bytesRead);
    }
    in.close();
    return bos.toByteArray();
}

// generate a random calendar within the past year
public static Calendar getCal() {
    Calendar now = Calendar.getInstance();
    int rHours = (int) (Math.random() * 8640);
    now.add(Calendar.HOUR, 0 - rHours);
    return now;
}

// remove common tags from the array of passed-in tags
public static String[] removeEnglishStopWords(String[] a) {
    List<String> result = new ArrayList<String>();
    for (String as : a) {
        if (SocialDriverConstants.ENGLISH_STOP_WORDS.contains(as)) continue;
        if (as.length() < 3) continue;
        result.add(as);
    }

    return result.toArray(new String[result.size()]);
}

public static void clearUsers(long companyId, long curUser)
    throws Exception {
    List<User> users = UserLocalServiceUtil.getCompanyUsers(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
    for (User user : users) {
        if (user.isDefaultUser()) {
            System.out.println("skipping default " + user.getScreenName());
            continue;
        }
        if (user.getUserId() == curUser) {
            System.out.println("skipping current " + user.getFullName());
            continue;
        }
        if (user.getScreenName().equals("test")) {
            System.out.println("skipping test " + user.getScreenName());
            continue;
        }
        System.out.println("Deleting user " + user.getFullName());
        UserLocalServiceUtil.deleteUser(user.getUserId());
    }

}
}
