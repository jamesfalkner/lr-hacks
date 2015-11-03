/*

addcommand context ${.context} (${.context} class)
thefactoryclass = ((context:bundle 0) loadclass jdk.nashorn.api.scripting.NashornScriptEngineFactory)
theinstance = ($thefactoryclass newInstance)
engine = ($theinstance getScriptEngine -scripting)
$engine put "gogo" (( context:bundle 0 ) bundleContext)

$engine eval "load('/Users/jhf/Documents/hackjs2.js')"

*/

var portlettype = Java.type('javax.portlet.Portlet');
var genericportlettype = Java.type('javax.portlet.GenericPortlet');
var dicttype = Java.type('java.util.Hashtable');

var portlet = Java.extend(Java.type('javax.portlet.GenericPortlet'));

var impl = new portlet() {
	render: function(req, resp) {
		var users = Packages.com.liferay.portal.service.UserLocalServiceUtil
		resp.getWriter().write("Hello from Gogo and Nashorn!");
	}
}

var dict = new dicttype();
dict.put("javax.portlet.display-name", "Nashorn Portlet");
dict.put("com.liferay.portlet.instanceable", true);
dict.put("com.liferay.portlet.display-category", "category.sample");

var registration = gogo.registerService(portlettype.class, impl, dict);


