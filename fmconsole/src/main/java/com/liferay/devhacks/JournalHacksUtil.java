package com.liferay.devhacks;

import com.liferay.portal.kernel.audit.AuditMessageFactoryUtil;
import com.liferay.portal.kernel.audit.AuditRouterUtil;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.language.UnicodeLanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletModeFactory_IW;
import com.liferay.portal.kernel.portlet.WindowStateFactory_IW;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.templateparser.TransformException;
import com.liferay.portal.kernel.util.*;
import com.liferay.portal.kernel.xml.*;
import com.liferay.portal.model.*;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.*;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.SessionClicks_IW;
import com.liferay.portal.webserver.WebServerServletTokenUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMUtil;
import com.liferay.portlet.expando.service.ExpandoColumnLocalService;
import com.liferay.portlet.expando.service.ExpandoRowLocalService;
import com.liferay.portlet.expando.service.ExpandoTableLocalService;
import com.liferay.portlet.expando.service.ExpandoValueLocalService;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalStructureConstants;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;
import com.liferay.taglib.util.VelocityTaglibImpl;
import com.liferay.util.portlet.PortletRequestUtil;
import freemarker.ext.beans.BeansWrapper;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.lang.reflect.Method;
import java.util.*;

public class JournalHacksUtil {

	public static void populateTokens(
			Map<String, Object> tokens, long articleGroupId, String articleId,
			ThemeDisplay themeDisplay, String languageId, PortletRequest request,
			PortletResponse response)
			throws Exception {

		Layout layout = themeDisplay.getLayout();

		Group group = layout.getGroup();

		LayoutSet layoutSet = layout.getLayoutSet();

		String friendlyUrlCurrent = null;

		if (layout.isPublicLayout()) {
			friendlyUrlCurrent = themeDisplay.getPathFriendlyURLPublic();
		} else if (group.isUserGroup()) {
			friendlyUrlCurrent = themeDisplay.getPathFriendlyURLPrivateUser();
		} else {
			friendlyUrlCurrent = themeDisplay.getPathFriendlyURLPrivateGroup();
		}

		String layoutSetFriendlyUrl = themeDisplay.getI18nPath();

		String virtualHostname = layoutSet.getVirtualHostname();

		if (Validator.isNull(virtualHostname) ||
				!virtualHostname.equals(themeDisplay.getServerName())) {

			layoutSetFriendlyUrl = friendlyUrlCurrent + group.getFriendlyURL();
		}

		tokens.put("article_group_id", String.valueOf(articleGroupId));
		tokens.put("cdn_host", themeDisplay.getCDNHost());
		tokens.put("company_id", String.valueOf(themeDisplay.getCompanyId()));
		tokens.put("friendly_url_current", friendlyUrlCurrent);
		tokens.put(
				"friendly_url_private_group",
				themeDisplay.getPathFriendlyURLPrivateGroup());
		tokens.put(
				"friendly_url_private_user",
				themeDisplay.getPathFriendlyURLPrivateUser());
		tokens.put(
				"friendly_url_public", themeDisplay.getPathFriendlyURLPublic());
		tokens.put("group_friendly_url", group.getFriendlyURL());
		tokens.put("image_path", themeDisplay.getPathImage());
		tokens.put("layout_set_friendly_url", layoutSetFriendlyUrl);
		tokens.put("main_path", themeDisplay.getPathMain());
		tokens.put("portal_ctx", themeDisplay.getPathContext());
		tokens.put(
				"portal_url", HttpUtil.removeProtocol(themeDisplay.getURLPortal()));
		tokens.put(
				"protocol", HttpUtil.getProtocol(themeDisplay.getURLPortal()));
		tokens.put("root_path", themeDisplay.getPathContext());
		tokens.put(
				"site_group_id", String.valueOf(themeDisplay.getSiteGroupId()));
		tokens.put(
				"scope_group_id", String.valueOf(themeDisplay.getScopeGroupId()));
		tokens.put("theme_image_path", themeDisplay.getPathThemeImages());

		_populateCustomTokens(tokens);

		// Deprecated tokens

		tokens.put("friendly_url", themeDisplay.getPathFriendlyURLPublic());
		tokens.put(
				"friendly_url_private",
				themeDisplay.getPathFriendlyURLPrivateGroup());
		tokens.put("group_id", String.valueOf(articleGroupId));
		tokens.put("page_url", themeDisplay.getPathFriendlyURLPublic());


		long companyId = 0;
		long companyGroupId = 0;
		long classNameId = 0;

		if (tokens != null) {
			companyId = GetterUtil.getLong(tokens.get("company_id"));
			companyGroupId = GetterUtil.getLong(
					tokens.get("company_group_id"));
			articleGroupId = GetterUtil.getLong(
					tokens.get("article_group_id"));
			classNameId = GetterUtil.getLong(
					tokens.get(TemplateConstants.CLASS_NAME_ID));
		}

		long scopeGroupId = 0;
		long siteGroupId = 0;

		if (themeDisplay != null) {
			companyId = themeDisplay.getCompanyId();
			companyGroupId = themeDisplay.getCompanyGroupId();
			scopeGroupId = themeDisplay.getScopeGroupId();
			siteGroupId = themeDisplay.getSiteGroupId();
		}

		tokens.put("articleGroupId", articleGroupId);
		tokens.put("company", themeDisplay.getCompany());
		tokens.put("companyId", companyId);
		tokens.put("device", themeDisplay.getDevice());

			String templatesPath = getTemplatesPath(
					companyId, articleGroupId, classNameId);

		tokens.put("journalTemplatesPath", templatesPath);


		tokens.put(
					"permissionChecker",
					PermissionThreadLocal.getPermissionChecker());
		tokens.put(
					"randomNamespace",
					StringUtil.randomId() + StringPool.UNDERLINE);
		tokens.put("scopeGroupId", scopeGroupId);
		tokens.put("siteGroupId", siteGroupId);
		tokens.put("templatesPath", templatesPath);
		tokens.put("viewMode", "view");

			// Deprecated variables

		tokens.put("groupId", articleGroupId);

			populateUtils(tokens, themeDisplay);


		// do article stuff
		JournalArticle article = JournalArticleLocalServiceUtil.getArticle(scopeGroupId, articleId);

		Document document;

		Element rootElement;

		document = SAXReaderUtil.read(article.getContent());

		rootElement = document.getRootElement();

		String xmlRequest = PortletRequestUtil.toXML(
				request, response);

		if (Validator.isNotNull(xmlRequest)) {
			Document reqDoc = SAXReaderUtil.read(xmlRequest);

			Element reqRootEl = reqDoc.getRootElement();

			tokens.put(
					"request", insertRequestVariables(reqRootEl));

			tokens.put("xmlRequest", reqRootEl.asXML());
		}

		Document requestDocument = SAXReaderUtil.read(xmlRequest);

		rootElement.add(requestDocument.getRootElement().createCopy());

		JournalHacksUtil.addAllReservedEls(rootElement, tokens, article, languageId, themeDisplay);


		List<TemplateNode> templateNodes = getTemplateNodes(
				themeDisplay, rootElement);

		if (templateNodes != null) {
			for (TemplateNode templateNode : templateNodes) {
				tokens.put(templateNode.getName(), templateNode);
			}
		}

	}

	public static List<TemplateNode> getTemplateNodes(
			ThemeDisplay themeDisplay, Element element)
			throws Exception {

		List<TemplateNode> templateNodes = new ArrayList<TemplateNode>();

		Map<String, TemplateNode> prototypeTemplateNodes =
				new HashMap<String, TemplateNode>();

		List<Element> dynamicElementElements = element.elements(
				"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			Element dynamicContentElement = dynamicElementElement.element(
					"dynamic-content");

			String data = StringPool.BLANK;

			if (dynamicContentElement != null) {
				data = dynamicContentElement.getText();
			}

			String name = dynamicElementElement.attributeValue(
					"name", StringPool.BLANK);

			if (name.length() == 0) {
				throw new TransformException(
						"Element missing \"name\" attribute");
			}

			String type = dynamicElementElement.attributeValue(
					"type", StringPool.BLANK);

			TemplateNode templateNode = new TemplateNode(
					themeDisplay, name, stripCDATA(data), type);

			if (dynamicElementElement.element("dynamic-element") != null) {
				templateNode.appendChildren(
						getTemplateNodes(themeDisplay, dynamicElementElement));
			}
			else if ((dynamicContentElement != null) &&
					(dynamicContentElement.element("option") != null)) {

				List<Element> optionElements = dynamicContentElement.elements(
						"option");

				for (Element optionElement : optionElements) {
					templateNode.appendOption(
							stripCDATA(optionElement.getText()));
				}
			}

			TemplateNode prototypeTemplateNode = prototypeTemplateNodes.get(
					name);

			if (prototypeTemplateNode == null) {
				prototypeTemplateNode = templateNode;

				prototypeTemplateNodes.put(name, prototypeTemplateNode);

				templateNodes.add(templateNode);
			}

			prototypeTemplateNode.appendSibling(templateNode);
		}

		return templateNodes;
	}

	protected static String stripCDATA(String s) {
		if (s.startsWith(StringPool.CDATA_OPEN) &&
				s.endsWith(StringPool.CDATA_CLOSE)) {

			s = s.substring(
					StringPool.CDATA_OPEN.length(),
					s.length() - StringPool.CDATA_CLOSE.length());
		}

		return s;
	}

	private static void _populateCustomTokens(Map<String, Object> tokens) {
		if (_customTokens == null) {
			_customTokens = new HashMap<String, String>();

			for (String customToken :
					PropsUtil.getArray(PropsKeys.JOURNAL_ARTICLE_CUSTOM_TOKENS)) {

				String value = PropsUtil.get(
						PropsKeys.JOURNAL_ARTICLE_CUSTOM_TOKEN_VALUE,
						new Filter(customToken));

				_customTokens.put(customToken, value);
			}
		}

		if (!_customTokens.isEmpty()) {
			tokens.putAll(_customTokens);
		}
	}

	private static void populateUtils(Map<String, Object> variables, ThemeDisplay themeDisplay) {
		// Array util

		variables.put("arrayUtil", ArrayUtil_IW.getInstance());

		// Audit message factory

		try {
			variables.put(
					"auditMessageFactoryUtil",
					AuditMessageFactoryUtil.getAuditMessageFactory());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Audit router util

		try {
			variables.put("auditRouterUtil", AuditRouterUtil.getAuditRouter());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Browser sniffer

		try {
			variables.put(
					"browserSniffer", BrowserSnifferUtil.getBrowserSniffer());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Calendar factory

		try {
			variables.put(
					"calendarFactory", CalendarFactoryUtil.getCalendarFactory());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Date format

		try {
			variables.put(
					"dateFormatFactory",
					FastDateFormatFactoryUtil.getFastDateFormatFactory());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Date util

		variables.put("dateUtil", DateUtil_IW.getInstance());

		// Dynamic data mapping util

		try {
			variables.put("ddmUtil", DDMUtil.getDDM());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Document library util

		try {
			variables.put("dlUtil", DLUtil.getDL());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Expando column service

		try {

			ServiceLocatorHack serviceLocator = ServiceLocatorHack.getInstance();

			// Service locator

			variables.put("serviceLocator", serviceLocator);

			try {
				variables.put(
						"expandoColumnLocalService",
						serviceLocator.findService(
								ExpandoColumnLocalService.class.getName()));
			}
			catch (SecurityException se) {
				_log.error(se, se);
			}

			// Expando row service

			try {
				variables.put(
						"expandoRowLocalService",
						serviceLocator.findService(
								ExpandoRowLocalService.class.getName()));
			}
			catch (SecurityException se) {
				_log.error(se, se);
			}

			// Expando table service

			try {
				variables.put(
						"expandoTableLocalService",
						serviceLocator.findService(
								ExpandoTableLocalService.class.getName()));
			}
			catch (SecurityException se) {
				_log.error(se, se);
			}

			// Expando value service

			try {
				variables.put(
						"expandoValueLocalService",
						serviceLocator.findService(
								ExpandoValueLocalService.class.getName()));
			}
			catch (SecurityException se) {
				_log.error(se, se);
			}
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Getter util

		variables.put("getterUtil", GetterUtil_IW.getInstance());

		// Html util

		try {
			variables.put("htmlUtil", HtmlUtil.getHtml());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Http util

		try {
			variables.put("httpUtil", HttpUtil.getHttp());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Journal content util

		try {
			variables.put(
					"journalContentUtil", JournalContentUtil.getJournalContent());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// JSON factory util

		try {
			variables.put("jsonFactoryUtil", JSONFactoryUtil.getJSONFactory());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Language util

		try {
			variables.put("languageUtil", LanguageUtil.getLanguage());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			variables.put(
					"unicodeLanguageUtil",
					UnicodeLanguageUtil.getUnicodeLanguage());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Locale util

		try {
			variables.put("localeUtil", LocaleUtil.getInstance());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Param util

		variables.put("paramUtil", ParamUtil_IW.getInstance());

		// Portal util

		try {
			variables.put("portalUtil", PortalUtil.getPortal());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			variables.put("portal", PortalUtil.getPortal());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Prefs props util

		try {
			variables.put("prefsPropsUtil", PrefsPropsUtil.getPrefsProps());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Props util

		try {
			variables.put("propsUtil", PropsUtil.getProps());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Portlet mode factory

		variables.put(
				"portletModeFactory", PortletModeFactory_IW.getInstance());

		// Portlet URL factory

		try {
			variables.put(
					"portletURLFactory",
					PortletURLFactoryUtil.getPortletURLFactory());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			UtilLocatorHack utilLocator = UtilLocatorHack.getInstance();

			// Util locator

			variables.put("utilLocator", utilLocator);

			// SAX reader util

			try {
				variables.put(
						"saxReaderUtil",
						utilLocator.findUtil(SAXReader.class.getName()));
			}
			catch (SecurityException se) {
				_log.error(se, se);
			}
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Session clicks

		variables.put("sessionClicks", SessionClicks_IW.getInstance());

		// Static field getter

		variables.put("staticFieldGetter", StaticFieldGetter.getInstance());

		// String util

		variables.put("stringUtil", StringUtil_IW.getInstance());

		// Time zone util

		variables.put("timeZoneUtil", TimeZoneUtil_IW.getInstance());

		// Unicode formatter

		variables.put("unicodeFormatter", UnicodeFormatter_IW.getInstance());

		// Validator

		variables.put("validator", Validator_IW.getInstance());

		// VelocityTaglib methods

		try {
			Class<?> clazz = VelocityTaglibImpl.class;

			Method method = clazz.getMethod(
					"layoutIcon", new Class[] {Layout.class});

			variables.put("velocityTaglib_layoutIcon", method);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		// Web server servlet token

		try {
			variables.put(
					"webServerToken",
					WebServerServletTokenUtil.getWebServerServletToken());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Window state factory

		variables.put(
				"windowStateFactory", WindowStateFactory_IW.getInstance());

		// Permissions

		try {
			variables.put(
					"accountPermission",
					AccountPermissionUtil.getAccountPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			variables.put(
					"commonPermission", CommonPermissionUtil.getCommonPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			variables.put(
					"groupPermission", GroupPermissionUtil.getGroupPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			variables.put(
					"layoutPermission", LayoutPermissionUtil.getLayoutPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			variables.put(
					"organizationPermission",
					OrganizationPermissionUtil.getOrganizationPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			variables.put(
					"passwordPolicyPermission",
					PasswordPolicyPermissionUtil.getPasswordPolicyPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			variables.put(
					"portalPermission", PortalPermissionUtil.getPortalPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			variables.put(
					"portletPermission",
					PortletPermissionUtil.getPortletPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			variables.put(
					"rolePermission", RolePermissionUtil.getRolePermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			variables.put(
					"userGroupPermission",
					UserGroupPermissionUtil.getUserGroupPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			variables.put(
					"userPermission", UserPermissionUtil.getUserPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}


		// other FM stuff
		if (themeDisplay != null) {
			Theme theme = themeDisplay.getTheme();

			// Full css and templates path

			String servletContextName = GetterUtil.getString(
					theme.getServletContextName());

			variables.put(
					"fullCssPath",
					StringPool.SLASH + servletContextName +
							theme.getFreeMarkerTemplateLoader() + theme.getCssPath());

			variables.put(
					"fullTemplatesPath",
					StringPool.SLASH + servletContextName +
							theme.getFreeMarkerTemplateLoader() +
							theme.getTemplatesPath());

			// Init

			variables.put(
					"init",
					StringPool.SLASH + themeDisplay.getPathContext() +
							TemplateConstants.SERVLET_SEPARATOR +
							"/html/themes/_unstyled/templates/init.ftl");
		}

		// Enum util

		variables.put(
				"enumUtil", BeansWrapper.getDefaultInstance().getEnumModels());

		// Object util

		variables.put("objectUtil", new LiferayObjectConstructorHack());

		// Portlet preferences

//		variables.put(
//				"freeMarkerPortletPreferences", new TemplatePortletPreferences());

		// Static class util

		variables.put(
				"staticUtil", BeansWrapper.getDefaultInstance().getStaticModels());



	}

	protected static String getTemplatesPath(
			long companyId, long groupId, long classNameId) {

		StringBundler sb = new StringBundler(7);

		sb.append(TemplateConstants.TEMPLATE_SEPARATOR);
		sb.append(StringPool.SLASH);
		sb.append(companyId);
		sb.append(StringPool.SLASH);
		sb.append(groupId);
		sb.append(StringPool.SLASH);
		sb.append(classNameId);

		return sb.toString();
	}

	protected static Map<String, Object> insertRequestVariables(Element element) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (element == null) {
			return map;
		}

		for (Element childElement : element.elements()) {
			String name = childElement.getName();

			if (name.equals("attribute")) {
				Element nameElement = childElement.element("name");
				Element valueElement = childElement.element("value");

				map.put(nameElement.getText(), valueElement.getText());
			}
			else if (name.equals("parameter")) {
				Element nameElement = childElement.element("name");

				List<Element> valueElements = childElement.elements("value");

				if (valueElements.size() == 1) {
					Element valueElement = valueElements.get(0);

					map.put(nameElement.getText(), valueElement.getText());
				}
				else {
					List<String> values = new ArrayList<String>();

					for (Element valueElement : valueElements) {
						values.add(valueElement.getText());
					}

					map.put(nameElement.getText(), values);
				}
			}
			else {
				List<Element> elements = childElement.elements();

				if (!elements.isEmpty()) {
					map.put(name, insertRequestVariables(childElement));
				}
				else {
					map.put(name, childElement.getText());
				}
			}
		}

		return map;
	}

	public static void addReservedEl(
			Element rootElement, Map<String, Object> tokens, String name,
			Date value) {

		addReservedEl(rootElement, tokens, name, Time.getRFC822(value));
	}

	public static void addReservedEl(
			Element rootElement, Map<String, Object> tokens, String name,
			double value) {

		addReservedEl(rootElement, tokens, name, String.valueOf(value));
	}

	public static void addReservedEl(
			Element rootElement, Map<String, Object> tokens, String name,
			String value) {

		// XML

		if (rootElement != null) {
			Element dynamicElementElement = SAXReaderUtil.createElement(
					"dynamic-element");

			Attribute nameAttribute = SAXReaderUtil.createAttribute(
					dynamicElementElement, "name", name);

			dynamicElementElement.add(nameAttribute);

			Attribute typeAttribute = SAXReaderUtil.createAttribute(
					dynamicElementElement, "type", "text");

			dynamicElementElement.add(typeAttribute);

			Element dynamicContentElement = SAXReaderUtil.createElement(
					"dynamic-content");

			//dynamicContentElement.setText("<![CDATA[" + value + "]]>");
			dynamicContentElement.setText(value);

			dynamicElementElement.add(dynamicContentElement);

			rootElement.add(dynamicElementElement);
		}

		// Tokens

		tokens.put(
				StringUtil.replace(name, CharPool.DASH, CharPool.UNDERLINE), value);
	}



	public static void addAllReservedEls(
			Element rootElement, Map<String, Object> tokens, JournalArticle article,
			String languageId, ThemeDisplay themeDisplay) {

		addReservedEl(
				rootElement, tokens, JournalStructureConstants.RESERVED_ARTICLE_ID,
				article.getArticleId());

		addReservedEl(
				rootElement, tokens,
				JournalStructureConstants.RESERVED_ARTICLE_VERSION,
				article.getVersion());

		addReservedEl(
				rootElement, tokens,
				JournalStructureConstants.RESERVED_ARTICLE_TITLE,
				article.getTitle(languageId));

		addReservedEl(
				rootElement, tokens,
				JournalStructureConstants.RESERVED_ARTICLE_URL_TITLE,
				article.getUrlTitle());

		addReservedEl(
				rootElement, tokens,
				JournalStructureConstants.RESERVED_ARTICLE_DESCRIPTION,
				article.getDescription(languageId));

		addReservedEl(
				rootElement, tokens,
				JournalStructureConstants.RESERVED_ARTICLE_TYPE, article.getType());

		addReservedEl(
				rootElement, tokens,
				JournalStructureConstants.RESERVED_ARTICLE_CREATE_DATE,
				article.getCreateDate());

		addReservedEl(
				rootElement, tokens,
				JournalStructureConstants.RESERVED_ARTICLE_MODIFIED_DATE,
				article.getModifiedDate());

		if (article.getDisplayDate() != null) {
			addReservedEl(
					rootElement, tokens,
					JournalStructureConstants.RESERVED_ARTICLE_DISPLAY_DATE,
					article.getDisplayDate());
		}

		String smallImageURL = StringPool.BLANK;

		if (Validator.isNotNull(article.getSmallImageURL())) {
			smallImageURL = article.getSmallImageURL();
		}
		else if ((themeDisplay != null) && article.isSmallImage()) {
			StringBundler sb = new StringBundler(5);

			sb.append(themeDisplay.getPathImage());
			sb.append("/journal/article?img_id=");
			sb.append(article.getSmallImageId());
			sb.append("&t=");
			sb.append(
					WebServerServletTokenUtil.getToken(article.getSmallImageId()));

			smallImageURL = sb.toString();
		}

		addReservedEl(
				rootElement, tokens,
				JournalStructureConstants.RESERVED_ARTICLE_SMALL_IMAGE_URL,
				smallImageURL);

		String[] assetTagNames = new String[0];

		try {
			assetTagNames = AssetTagLocalServiceUtil.getTagNames(
					JournalArticle.class.getName(), article.getResourcePrimKey());
		}
		catch (SystemException se) {
		}

		addReservedEl(
				rootElement, tokens,
				JournalStructureConstants.RESERVED_ARTICLE_ASSET_TAG_NAMES,
				StringUtil.merge(assetTagNames));

		addReservedEl(
				rootElement, tokens,
				JournalStructureConstants.RESERVED_ARTICLE_AUTHOR_ID,
				String.valueOf(article.getUserId()));

		String userName = StringPool.BLANK;
		String userEmailAddress = StringPool.BLANK;
		String userComments = StringPool.BLANK;
		String userJobTitle = StringPool.BLANK;

		User user = null;

		try {
			user = UserLocalServiceUtil.getUserById(article.getUserId());

			userName = user.getFullName();
			userEmailAddress = user.getEmailAddress();
			userComments = user.getComments();
			userJobTitle = user.getJobTitle();
		}
		catch (PortalException pe) {
		}
		catch (SystemException se) {
		}

		addReservedEl(
				rootElement, tokens,
				JournalStructureConstants.RESERVED_ARTICLE_AUTHOR_NAME, userName);

		addReservedEl(
				rootElement, tokens,
				JournalStructureConstants.RESERVED_ARTICLE_AUTHOR_EMAIL_ADDRESS,
				userEmailAddress);

		addReservedEl(
				rootElement, tokens,
				JournalStructureConstants.RESERVED_ARTICLE_AUTHOR_COMMENTS,
				userComments);

		addReservedEl(
				rootElement, tokens,
				JournalStructureConstants.RESERVED_ARTICLE_AUTHOR_JOB_TITLE,
				userJobTitle);
	}

	private static Map<String, String> _customTokens;

	private static Log _log = LogFactoryUtil.getLog(
			JournalHacksUtil.class);

}
