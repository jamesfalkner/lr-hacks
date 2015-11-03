<#-- Show all members of site in nice looking grid -->

<style type="text/css">
	.hoverdark:hover {
		text-decoration: none !important;
		background: rgba(215, 215, 215, 0.4);
	}

	.circle-image {
		height: 100px;
		width: 100px;
		background: none center;
		background-size: cover;
		-webkit-border-radius: 50%;
		-moz-border-radius: 50%;
		-o-border-radius: 50%;
		-ms-border-radius: 50%;
		border-radius: 50%;
		margin: 0 auto 8px;
	}
</style>

<#assign portalURL = "http://" + getterUtil.getString(request['theme-display']['portal-url']) />
<#assign mainPath = getterUtil.getString(request['theme-display']['path-main']) />

<#-- Make a temp themeDisplay object (actual java object) to use later on -->
<#assign themeDisplay = objectUtil("com.liferay.portal.theme.ThemeDisplay") />
<#assign V = themeDisplay.setPathImage(getterUtil.getString(request['theme-display']['path-image'])) />
<#assign V = themeDisplay.setPathMain(getterUtil.getString(request['theme-display']['path-main'])) />
<#assign V = themeDisplay.setPortalURL(portalURL) />
<#assign V = themeDisplay.setPermissionChecker(permissionChecker) />
<#assign V = themeDisplay.setScopeGroupId(getterUtil.getLong(request['theme-display']['scope-group-id'])) />

<#assign userService = utilLocator.findUtil("com.liferay.portal.service.UserLocalService") />
<#assign groupService = utilLocator.findUtil("com.liferay.portal.service.GroupLocalService") />
<#assign group = groupService.getGroup(scopeGroupId) />
<#assign members = userService.getGroupUsers(scopeGroupId) />
<#assign sortBy = 'portraitId' />

<#if (validator.isNull(members) || (members?size <= 0)) >
<div class="portlet-msg">${languageUtil.get(locale, "there-are-no-results")}</div>
<#else>

<#-- can be 1, 2, 3, 4, or 6 -->
	<#assign rowSize = 6 />
	<#assign cellSize = (12 / rowSize)?string />
	<#assign rowCount = (members?size / rowSize)?ceiling />

<div style="text-align: center"><h1>All Explorers</h1></div>
<hr>

	<#list members?sort_by(sortBy)?reverse?chunk(rowSize) as rowList>
  <div class="row-fluid" style="padding-bottom: 50px">
		<#list rowList as member>
			<#assign jobTitle = stringUtil.shorten(htmlUtil.escape(member.getJobTitle()), 25) />
			<#assign profileUrl = "https://liferay.com/web/${htmlUtil.escape(member.getScreenName())}/profile" />
			<#assign profilePic = member.getPortraitURL(themeDisplay) />

	    <div style="text-align: center" class="span${cellSize} hoverdark img-rounded content-animation">
		    <div class="circle-image" style="background-image: url(${profilePic});"></div>
		    <br>
		    <a target="_blank"
		       href="https://liferay.com/web/${htmlUtil.escape(member.getScreenName())}/profile"><strong>${htmlUtil.escape(member.getFullName())}</strong></a>
		    <br>
		    <em>${htmlUtil.escape(member.getJobTitle())}</em>
	    </div>
		</#list>
  </div>
	</#list>
<em>${members?size} total explorers</em>
</#if>

