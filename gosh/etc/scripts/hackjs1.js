/*

addcommand context ${.context} (${.context} class)
thefactoryclass = ((context:bundle 0) loadclass jdk.nashorn.api.scripting.NashornScriptEngineFactory)
theinstance = ($thefactoryclass newInstance)
engine = ($theinstance getScriptEngine -scripting)
$engine put "gogo" (( context:bundle 0 ) bundleContext)

$engine eval "load('/Users/jhf/Documents/hackjs1.js')"

*/

var modelext = Java.extend(Java.type('com.liferay.portal.model.BaseModelListener'));

var listener = new modelext() {
  onAfterCreate: function(asset) {
    print("asset " + asset.title + "[" + asset.className + "] created by userid " + asset.userId);
  }
}

var persist =
Packages.com.liferay.portal.kernel.bean.PortalBeanLocatorUtil.locate(
  'com.liferay.portlet.asset.service.persistence.AssetEntryPersistence');

var all = persist.getListeners();

try {
  persist.registerListener(listener);
} catch (ex) {
  print("uh oh");
  print(ex);
}

print("registered new listener");



