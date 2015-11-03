/*

 addcommand context ${.context} (${.context} class)

addcommand context ${.context} (${.context} class)
thefactoryclass = ((context:bundle 0) loadclass jdk.nashorn.api.scripting.NashornScriptEngineFactory)
theinstance = ($thefactoryclass newInstance)
engine = ($theinstance getScriptEngine -scripting)
$engine put "gogo" (( context:bundle 0 ) bundleContext)
$engine eval "load('/Users/jhf/Documents/hackjs3.js')"


addcommand context ${.context} (${.context} class)
thefactoryclass = ((context:bundle 0) loadclass java.lang.management.ManagementFactory)
mbs = ($thefactoryclass getPlatformMBeanServer)
$mbs getMBeanCount
objectnameclass = ((context:bundle 0) loadclass javax.management.ObjectName)
stringclass = ((context:bundle 0) loadclass java.lang.String)
on = (($objectnameclass getConstructor $stringclass) newInstance net.sf.ehcache:type=CacheStatistics,CacheManager=MULTI_VM_PORTAL_CACHE_MANAGER,name=com.liferay.portal.kernel.dao.orm.EntityCache.com.liferay.portlet.blogs.model.impl.BlogsEntryImpl)
$mbs getMBeanInfo $on
$mbs getAttribute $on CacheMisses
$mbs getAttribute $on CacheMisses
*/

function makeArrayString(arr) {
	var result = '[';
	for (var i = 0; i < arr.length; i++) {
		result += (arr[i]);
		if (i < (arr.length - 1)) {
			result += ', ';
		}
	}
	result += ']';
	return result;
}


var thefactoryclass = Java.type('java.lang.management.ManagementFactory');
var objectnameclass = Java.type('javax.management.ObjectName');

var mbs = Packages.java.lang.management.ManagementFactory.getPlatformMBeanServer();
var on = new objectnameclass('com.liferay.portal.monitoring:classification=portlet_statistic,name=PortletManager');
var onportal = new objectnameclass('com.liferay.portal.monitoring:classification=portal_statistic,name=PortalManager');
var rrpm = new objectnameclass('com.liferay.portal.monitoring:classification=portlet_statistic,name=RenderRequestPortletManager');
var osgion = new objectnameclass('osgi.core:type=bundleState,version=1.7,framework=org.eclipse.osgi,uuid=d08fb2f2-d67d-0015-1c76-8f59670e2eed');
var portal = Packages.com.liferay.portal.util.PortalUtil.getPortal();
var portletutil = Packages.com.liferay.portal.service.PortletLocalServiceUtil;

print('');
print('======== Companies ===========');
print('');
for each (companyid in Packages.com.liferay.portal.util.PortalUtil.getCompanyIds()) {
	print("Registered company: " + Packages.com.liferay.portal.service.CompanyLocalServiceUtil.getCompany(companyid));
}

// print("MBean Count: " + mbs.getMBeanCount());
// print("Blog Cache Misses: " + mbs.getAttribute(on, 'CacheMisses'));

// var notificationInterface = Java.type('javax.management.NotificationListener');

// var listener = new notificationInterface() {
// 	handleNotification: function(notification, handback) {
// 		print("received notification: " + notification + " handback: " + handback +
// 			"userData: " + notification.getUserData());
// 	}
// }

// mbs.addNotificationListener(osgion, listener, null, "the handback value");

// "added"

print('');
print('======== All Portlets ===========');
print('');

for each (sr in gogo.getServiceReferences('javax.portlet.Portlet', null)) {
	for each (pk in sr.getPropertyKeys()) {
	  print("portlet: " + sr.getBundle().getSymbolicName() + ": " + pk + " = " + sr.getProperty(pk));
	}
}

print('');
print('======== Portal Performance Attributes ===========');
print('');

for each (attr in mbs.getMBeanInfo(onportal).getAttributes()) {
	var val;
	try {
		val = mbs.getAttribute(onportal, attr.getName());
		print(attr.getName() + " = " + (val.length ? makeArrayString(val) : val));
	} catch (ex) {
	}

}

print('');
print('======== Portlet Performance Attributes ===========');
print('');

for each (attr in mbs.getMBeanInfo(on).getAttributes()) {
	var val;
	try {
		val = mbs.getAttribute(on, attr.getName());
		print(attr.getName() + " = " + (val.length ? makeArrayString(val) : val));
	} catch (ex) {
	}

}

print('');
print('======== Slowest Rendering Portlets ===========');
print('');

var pids = Java.from(mbs.getAttribute(on, 'PortletIds'));

var slow_pids = pids.sort(function(a, b) {
	var atime = mbs.invoke(rrpm, "getAverageTimeByPortlet", [a], ['java.lang.String']);
	var btime = mbs.invoke(rrpm, "getAverageTimeByPortlet", [b], ['java.lang.String']);
	return (btime - atime);
});

for each (pid in slow_pids) {
	var maxtime = mbs.invoke(rrpm, "getAverageTimeByPortlet", [pid], ['java.lang.String']);
	print("Portlet " + pid + " max render time: " + maxtime + "ms");
}



