<%@ page import="com.liferay.portal.model.User" %>
<%@ page import="com.liferay.portal.service.UserLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.messageboards.model.MBThread" %>
<%@ page import="com.liferay.portlet.messageboards.service.MBStatsUserLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil" %>
<%@ page import="com.liferay.wiki.model.WikiPage" %>
<%@ page import="com.liferay.wiki.model.WikiPageConstants" %>
<%@ page import="com.liferay.wiki.service.WikiPageLocalServiceUtil" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.util.List" %>
<% /**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>

<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib prefix="liferay-ui" uri="http://liferay.com/tld/theme" %>
<%@ taglib prefix="liferay-theme" uri="http://liferay.com/tld/theme" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>

<portlet:defineObjects/> <liferay-theme:defineObjects/> <liferay-ui:defineObjects/>


<body>

<portlet:resourceURL var="testURL"/>

<script type="text/javascript">


function startBlog() {

	AUI().use('aui-io-request', function (A) {
		A.io.request('<%= testURL %>', {
			method:'POST',
			data:{
				startBlog:"foo",
				themeId:document.<portlet:namespace />blogForm.themeId,
				profileFlag:document.<portlet:namespace />blogForm.profileFlag

			},
			on:{
				success:function () {
					var div = document.getElementById("mycontent");
					div.innerHTML = "Started Blog";
				},
				failure:function () {
					alert("failure:" + this.get('responseData'));
				}
			}
		});
	});
}

function stopBlog() {
	var div = document.getElementById("mycontent");
	div.innerHTML = "Stopping Blog";

	AUI().use('aui-io-request', function (A) {
		A.io.request('<%= testURL %>', {
			method:'POST',
			data:{
				stopBlog:"foo"
			},
			on:{
				success:function () {
					var div = document.getElementById("mycontent");
					div.innerHTML = "Stopped Blog";
				},
				failure:function () {
					alert("failure:" + this.get('responseData'));
				}
			}
		});
	});
}
function clearBlog() {

	AUI().use('aui-io-request', function (A) {
		A.io.request('<%= testURL %>', {
			method:'POST',
			data:{
				clearBlog:"foo"
			},
			on:{
				success:function () {
					var div = document.getElementById("mycontent");
					div.innerHTML = "Cleared Blog";
				},
				failure:function () {
					alert("failure:" + this.get('responseData'));
				}
			}
		});
	});
}

function startWiki() {

	AUI().use('aui-io-request', function (A) {
		A.io.request('<%= testURL %>', {
			method:'POST',
			data:{
				startWiki:"foo",
				themeId:document.<portlet:namespace />blogForm.themeId,
				profileFlag:document.<portlet:namespace />blogForm.profileFlag

			},
			on:{
				success:function () {
					var div = document.getElementById("mycontent");
					div.innerHTML = "Started Wiki";
				},
				failure:function () {
					alert("failure:" + this.get('responseData'));
				}
			}
		});
	});
}

function stopWiki() {

	AUI().use('aui-io-request', function (A) {
		A.io.request('<%= testURL %>', {
			method:'POST',
			data:{
				stopWiki:"foo"
			},
			on:{
				success:function () {
					var div = document.getElementById("mycontent");
					div.innerHTML = "Stopped Wiki";
				},
				failure:function () {
					alert("failure:" + this.get('responseData'));
				}
			}
		});
	});
}
function clearWiki() {

	AUI().use('aui-io-request', function (A) {
		A.io.request('<%= testURL %>', {
			method:'POST',
			data:{
				clearWiki:"foo"
			},
			on:{
				success:function () {
					var div = document.getElementById("mycontent");
					div.innerHTML = "Cleared Wiki";
				},
				failure:function () {
					alert("failure:" + this.get('responseData'));
				}
			}
		});
	});
}


function startMB() {

	AUI().use('aui-io-request', function (A) {
		A.io.request('<%= testURL %>', {
			method:'POST',
			data:{
				startMB:"foo",
				themeId:document.<portlet:namespace />blogForm.themeId,
				profileFlag:document.<portlet:namespace />blogForm.profileFlag

			},
			on:{
				success:function () {
					var div = document.getElementById("mycontent");
					div.innerHTML = "Started startMB";
				},
				failure:function () {
					alert("failure:" + this.get('responseData'));
				}
			}
		});
	});
}

function stopMB() {

	AUI().use('aui-io-request', function (A) {
		A.io.request('<%= testURL %>', {
			method:'POST',
			data:{
				stopMB:"foo"
			},
			on:{
				success:function () {
					var div = document.getElementById("mycontent");
					div.innerHTML = "Stopped stopMB";
				},
				failure:function () {
					alert("failure:" + this.get('responseData'));
				}
			}
		});
	});
}
function clearMB() {

	AUI().use('aui-io-request', function (A) {
		A.io.request('<%= testURL %>', {
			method:'POST',
			data:{
				clearMB:"foo"
			},
			on:{
				success:function () {
					var div = document.getElementById("mycontent");
					div.innerHTML = "Cleared clearMB";
				},
				failure:function () {
					alert("failure:" + this.get('responseData'));
				}
			}
		});
	});
}
function clearUsers() {

	AUI().use('aui-io-request', function (A) {
		A.io.request('<%= testURL %>', {
			method:'POST',
			data:{
				clearUsers:"foo"
			},
			on:{
				success:function () {
					var div = document.getElementById("mycontent");
					div.innerHTML = "Cleared users";
				},
				failure:function () {
					alert("failure:" + this.get('responseData'));
				}
			}
		});
	});
}

function genStats() {
	var div = document.getElementById("mycontent");
	div.innerHTML = "Generating Stats, Please Wait...";

	AUI().use('aui-io-request', function (A) {
		A.io.request('<%= testURL %>', {
			method:'POST',
			data:{
				genStats:"foo"
			},
			on:{
				success:function () {
					var div = document.getElementById("mycontent");
					div.innerHTML = "Regenerated Social Stats";
				},
				failure:function () {
					alert("failure:" + this.get('responseData'));
				}
			}
		});
	});
}


</script>

<div id="<portlet:namespace/>blogForm">

	<aui:form name="blogForm">
		<aui:input type="textfield" label="Theme ID (Optional)" id="themeId" name="themeId" value=""
		helpMessage="ID of theme to use for user's public pages (e.g. healthcare_WAR_healthcaretheme)"/>

		<aui:input type="checkbox" label="Create Profile pages" id="profileFlag" name="profileFlag" value="false"
			helpMessage="If checked, when users are created, an attempt will be made to create their public and private profile pages.  If you are using this with Social Office, uncheck this."/>

		<aui:button style="color:blue;" id="startBlog" name="startBlog" value="startBlog"
			onclick="startBlog();"/> <aui:button style="color:orange;" id="stopBlog" name="stopBlog" value="stopBlog" onclick="stopBlog();"/>
		<aui:button id="clearBlog" name="clearBlog" value="clearBlog"
			onclick="clearBlog();"/> <aui:button style="color:blue;" id="startWiki" name="startWiki" value="startWiki" onclick="startWiki();"/>
		<aui:button style="color:orange;" id="stopWiki" name="stopWiki" value="stopWiki"
			onclick="stopWiki();"/> <aui:button id="clearWiki" name="clearWiki" value="clearWiki" onclick="clearWiki();"/>
		<aui:button style="color:blue;" id="startMB" name="startMB" value="startMB"
			onclick="startMB();"/> <aui:button style="color:orange;" id="stopMB" name="stopMB" value="stopMB" onclick="stopMB();"/>
		<aui:button id="clearMB" name="clearMB" value="clearMB" onclick="clearMB();"/>
		<aui:button id="clearUsers" name="clearUsers" value="clearUsers" onclick="clearUsers();"/>
		<aui:button id="genStats" name="genStats" value="genStats" onclick="genStats();"/>
	</aui:form>

</div>

<div id="mycontent">
	<h2>STATUS: IDLE</h2>
</div>

<%--<hr>--%>
<%--<h2>Instructions</h2>--%>

<%--<p>This portlet generates fake historical social activity on a Liferay Site. It uses <a--%>
	<%--href="http://wikipedia.org">Wikipedia</a> content for fake blogs, wikis, and forum threads and categories. It also--%>
   <%--uses <a href="http://gravatar.com">Gravatar</a> to generate fake profile pictures.  There is much hackery and hard coding here - buyer beware!</p>--%>

<%--<p>It will create random content, along with random users (previous versions of this example also created profile pages, but most recently this has been used with Liferay Social Office, which has nice-looking profiles for each user). The users are randomly drawn from a--%>
   <%--fake set of names, and once created, each user has a screenname equal to their first name, a login equal to <em>firstname</em><code>@liferay.com</code>,--%>
   <%--and a password equal to their firstname. For example, for user Joe Bloggs, you can login using--%>
	<%--<code>joe@liferay.com</code> and password <code>joe</code>. Once logged in, you can view their profile using the--%>
   <%--standard links or go to <code>/web/</code><em>firstname</em><code>/profile</code>.</p>--%>

<%--<p>Once created, you can then setup the site's pages by adding standard portlets (e.g. Blogs, Wiki, etc) and--%>
   <%--configuring as needed to show the fake content.  If used with Social Office, sites automatically get pages for Blogs, Forums, etc.</p>--%>

<%--<p>This portlet will also install the necessary Web Content structures, templates, and articles to show interesting--%>
   <%--visualizations of social data. You can augment this by adding the standard <em>Group Statistics</em> and <em>User--%>
																												<%--Statistics</em>--%>
   <%--portlets to visualize the Social Activity (nee Equity) Data.</p>--%>

<%--<h3>Steps to use (tested against Liferay 6.1 CE GA2 and EE GA2):</h3>--%>

<%--<p>1. Download and install your preferred Liferay 6.1 GA2 bundle.  You can try HSQL but issues were found and this was tested with MySQL.</p>--%>
<%--<p>2. If you wish to visualize achievements, you will need to deploy a customized version of the <b>Summary</b>--%>
	 <%--portlet (included inside the social-networking-portlet bundle), as well as modifying the <code>liferay-social.xml</code> file with the achievements--%>
	 <%--modifications included in the same package as this portlet. Follow the instructions in the below <b>Achievements</b> section first if you wish to do this.</p>--%>


<%--<p>3. Be sure to watch the log file (if you're using tomcat it's at <code><%= System.getProperty("catalina.home")%>--%>
	<%--/logs/catalina.out</code>)</p>--%>

<%--<p>4. Ensure that your database is clean(ish) and you are running this as a user with administrator privileges</p>--%>
<%--<p>5.  You will need to up the amount of memory given to Liferay using app-server specific settings.  For tomcat, add <code>-Xmx2G -XX:MaxPermSize=512m</code> to--%>
	 <%--the <code>bin/setenv.sh</code> or <code>setenv.bat</code> script, in the <code>CATALINA_OPTS</code> setting. </p>--%>
<%--<p>6.  If you want to show badges/achievements, ensure that you have deployed the modified <b>Social Networking Portlets</b> package.  Next deploy, the Social Driver portlet to any page on a Site (either the default Guest site, or one that you have created).  Whichever site this portlet is deployed is where the fake data will be generated.--%>

<%--<p>7.  <strong>Next, login as admin, and make sure to enable Social Activity Recording</strong> by visiting Control Panel -> Social Activity and toggle the--%>
	   <%--buttons next to Blogs Entry, Message Boards Message, and Wiki Page, and click <em>Save</em></p>--%>
<%--</p>--%>
<%--<p>8. Also, ensure that Workflow for Web Content is turned OFF (it is on by default when using the sevencogs sample sites) by visiting Control Panel -> Workflow Configuration and--%>
	 <%--turn all settings to "Default: no workflow" and click Save.</p>--%>
<%--<p>9. To begin creating fake blogs, click the "Start Blogs" button. Every 2 seconds this portlet will randomly choose to--%>
   <%--create a new blog, comment on an existing blog, or rate an existing blog.  The first time it chooses to create a blog entry,--%>
<%--it will go out to Wikipedia to fetch some articles to use.</p>--%>

<%--<p>10. To stop the flow of blog activity, click "Stop Blogs". You can click start/stop as necessary to turn on/off the--%>
   <%--blog activity (a thread is maintained and simply paused/restarted for this.  If you re-deploy this portlet, you might get orphan threads, warnings in the log file, or other strange behavior.  Be sure to stop all threads before undeploying or re-deploying this portlet).</p>--%>

<%--<p>11. While the above generators are running, they will also create new users as necessary, and configure their profile--%>
   <%--pages on the fly. If you supply an optional themeId in the box above, each user's public pages will be configured to--%>
   <%--use that theme. Ensure that you have already deployed the theme and understand what the themeId should be set to (for--%>
   <%--example, <code>healthcare_WAR_healthcaretheme</code>). This is not obvious.</p>--%>

<%--<p>12. Once you are satisfied with the amount of fake social content, ensure all generators are STOPPED, and then click--%>
   <%--the "GenStats" button. Depending on how much fake blogs/forums/wikis you have generated,this may take A LONG time to complete (on--%>
   <%--the order of minutes). This action will clear the Social Activity data tables and then generate fake historical--%>
   <%--Social Activity in the Social Activity framework, so that portlets like the <em>Group Statistics</em> and <em>User--%>
																												 <%--Statistics</em>--%>
   <%--portlet will show interesting graphs.  Wait for it to complete before doing anything else. If you click this button again, the tables are wiped again and re-generated.--%>
<%--</p>--%>

<%--<p>13. You can clear the fake data out using "Clear Blogs", "Clear Wikis", etc. Be warned that this is not well tested--%>
   <%--and the best way to reset is to wipe the database completely.</p>--%>
<%--<hr>--%>
<%--<h3>Badges/Achievements</h3>--%>
<%--<p>To enable achievements, you must do the following before starting up (or restart after):</p>--%>
<%--<p>1. Copy the <code>liferay-social.xml</code> from from within this portlet (in the <code>resources/</code> directory) to Liferay's WEB-INF directory (typically--%>
<%--<code>tomcat-xx.xx.xx/webapps/ROOT/WEB-INF</code>.  This should replace the existing file, and it contains additional definitions of achievements. <b>You must restart Liferay for these changes to take effect</b>.</p>--%>
<%--<p>2. Copy the sample png images from the <code>resources/</code> directory of this portlet to the Liferay root HTML directory (on tomcat it's <code>tomcat-xx.xx.xx/webapps/ROOT/html</code>). These are referenced within <code>liferay-social.xml</code> so if the images aren't showing up, check the file paths carefully.</p>--%>
<%--<p>3. Note that before achievements will work, you must first generate some social content and then run the "Gen Stats" function from above, in order to generate the correct counters for the social content (such as blogs, etc).  Achievements only count--%>
<%--against content that is already known to the Social Activity tracking system (via the "Gen Stats" function).</p>--%>
<%--<p>4. Copy the modified <code>view_user_6.1.0GA1.jspf</code> file from this portlet's <code>resources/</code> directory on top of the <code>view_user.jspf</code> file located in the Social Networking Portlet's--%>
<%--webapps folder (on tomcat this will be something like <code>tomcat-xx.xx.xx/webapps/social-networking-portlet/summary/view_user.jspf</code>.  This file has small changes from the 6.1.0 GA2 version of the file, and will--%>
<%--likely not work with any other version, but the changes are small and can easily be applied to other versions by a resourceful developer.  The changes add social rank, achievements, and the ability to click on the usernae to send them email.</p>--%>
<%--<p>5. Startup or restart Liferay.</p>--%>
<%--<p>6. After following the steps for generating social content, users, and social data in the previous section, look for the <code>&lt;achievement&gt;</code> elements inside <code>liferay-social.xml</code> to understand which activities can result in achievements.  For example, if you login--%>
<%--as a test user (after stopping and restarting Liferay to take the new <code>liferay-social.xml</code> file changes), and blog 3 times, then visit the user's profile page (containing the modified Summary portlet from above), the Summary portlet--%>
<%--should show new achievements.  Similarly, in the example achievements, if a user votes on something 4 or 5 times they'll get another achievement.</p>--%>
<%--<hr>--%>
<%--<h3>Notes</h3>--%>
<%--<p>The fake data is retrieved from wikipedia, by searching with a pre-defined set of keywords (look in the <code>SocialDriverConstants</code> class).  So, to get more variety, run the generators for a few minutes, then stop them.  Then re-deploy the portlet to force it to re-retrieve new articles on the next run of the generators.  After--%>
<%--you have a good amount of fake data, then generate stats using "GenStats".</p>--%>
<%--<p>After generating a bunch of social content and social activity stats, you can create pages on the site that show an eye-candy tagcloud along with a computed experts list and list of assets.  Here are the steps:</p>--%>
<%--<p>1. Add a new page to your site</p>--%>
<%--<p>2. populate with 3 <em>Web Content Display</em> portlets (e.g. two in the left column, one in the larger right column, when using the default page layout)</p>--%>
<%--<p>3. In the upper-left portlet,configure it to display the <code>SOCIALDRIVER-CLOUDARTICLE</code> - this will show a rotating tag sphere, and tags can be clicked to filter the other two portlets below</p>--%>
<%--<p>4.In the lower-left portlet, configure it to display the <code>SOCIALDRIVER-EXPERTSLIST</code> article - this will show a computed list of experts, ordered by their rank in the site. Clicking the names will take you to their profile page</p>--%>
<%--<p>5. In the right-side portlet, configure it to display the <code>SOCIALDRIVER-ASSETLIST</code> article.  This article will show a list of all assets in the site, with a popularity score as well.  Clicking the article name will take you to the article, <b>if</b> you also add other pages to the site that contain the associated apps (e.g. a blogs portlet, wiki portlet, and message board portlet</p>--%>
<%--<p>6. You can add other OOTB portlets to display the content, e.g. blogs aggregators, directories, and so forth.  Be creative.</p>--%>
<%--<p>7. When you visit a fake user's profile, several pages have been added - most notably the "Expertise" page which shows a similar view, but scoped to that particular user's expertise.  So a good demo is to build an "experts" page, observe the expertise, click on a tag, then click on the expert in that tag,visit their profile, click on their name to send them an email, etc.</p>--%>
<%--<p>8. The data isn't completely random - there are a few "preferred" tags that over time will grow larger and more popular, tags like liferay, portal, javaee, java, ldap, etc.  These can be found in the source code.</p>--%>
</body>

