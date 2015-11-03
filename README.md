# A set of random hacks for Liferay 6.2 and Liferay 7

This repository contains random hacks that may be useful for Liferay developers. They are not intended to be used
in production, have not been heavily tested, and are probably not very performant! So use at your own risk.

## Hack 1 - WCM Template As an App Basics

See `lr-hacks/wcm`. Simple examples of how to use WCM Templates to access Liferay APIs through AJAX, including a simple CRUD app for browsing/editing values in Liferay Expando.

To enable certain variables to be available to scripts, you'll need this in your `portal-ext.properties`:

```
velocity.engine.restricted.classes=
velocity.engine.restricted.variables=
freemarker.engine.restricted.classes=
freemarker.engine.restricted.variables=
```
You can paste these into Liferay's Template editor.

## Hack 2 - Dynamic Query in Templates

See `lr-hacks/wcm`. Examples of executing DynamicQuery in FreeMarker and Velocity templates.

## Hack 3 - Easy Script Project Management

See [DDMTool](https://www.npmjs.com/package/liferay-ddmtool).
Not really a _hack_ - this is a demo of Monator's excellent DDM Tool for managing Liferay WCM structures & templates, ADTs, and more.

## Hack 4 - Realtime rendering of templates

See `lr-hacks/fmconsole`. This portlet does realtime rendering of WCM Templates using the ACE Editor as template editor, and Liferay APIs to do incremental rendering of scripts in realtime.

## Hack 5 - Securing insecure services

See `lr-hacks/fmconsole`. This includes a simple 'Hello World' remote JSON web service, with commented-out code showing how one would secure the API even when CSRF checking is disabled and the API does not require authentication. Useful for mobile apps, and other secured endpoints that require simple web services.

## Hack 6 - OSGi and Web Sockets

Derived from [Gustav Carlson's excellent WebSockets+Liferay 7 demo](https://github.com/GustavCarlson/wsbridge-lr7), this hack injects a ModelListener into Liferay and listens for Assets being created, and plots them on a google map in realtime. See the _bonus_ section below for a useful tool to create fake Liferay social data (users, blogs, comments, friend requests, wiki pages, ratings, etc).

## Hack 7 - Nashorn interactive shell

This demo shows how to instantiate and interactively issue JavaScript using the built-in Nashorn javascript bridge in Java 8, and OSGi's Gogo shell. Useful for devops.

To see this demo, simply run Liferay 7, `telnet localhost 11311` to start the Gogo shell, then issue these commands:

```
addcommand context ${.context} (${.context} class)
thefactoryclass = ((context:bundle 0) loadclass jdk.nashorn.api.scripting.NashornScriptEngineFactory)
theinstance = ($thefactoryclass newInstance)
engine = ($theinstance getScriptEngine -scripting)
$engine put "gogo" (( context:bundle 0 ) bundleContext)
$engine eval "var x = 1; var y = x+2; print(y);"
```

To interactively issue successive commands, try this:

```
while { result = ( $engine eval "var line = readLine('js$ '); line;" ) } { $engine eval $result }
```

To export the `javax.script` APIs from the JDK, you'll need this in your portal-ext.properties:

```
module.framework.system.packages.extra=\
        javax.faces.convert,\
        javax.faces.webapp,\
        \
        #
        # Dynamic References
        #
        \
        com.mysql.jdbc,\
        com.sun.security.auth.module,\
        org.apache.naming.java,\
        sun.misc,\
        sun.security.provider,\
        javax.script, \
        javax.servlet;version="3.1.0", \
        javax.servlet.http;version="3.1.0"
```
## Hack 8 - JS Script management for DevOps

See `lr-hacks/gosh`. Building on the previous hack, this one creates several files in the 'gogo home' directory, causing them to be executed whenever the gogo shell is started. It will look for and scripts in the `scripts/` directory and create gogo commands based on the filename that when invoked will invoke the script. Devops can build a library of useful JavaScript tools for use whenever invoking gogo.

To use this, simply create an `etc` directory in your _Liferay home_, and drop these files in, and define the directory in Liferay's `portal-ext.properties` file such as:

```
module.framework.properties.gosh.home=${liferay.home}
module.framework.auto.deploy.dirs=\
    ${module.framework.base.dir}/modules,\
    ${liferay.home}/etc/config
```

## Hack 9 - Integrating Liferay APIs

See `lr-hacks/gosh/etc/scripts` for three JavaScript scripts which do several unique hacks in Liferay:

- `hackjs1.js` - Dynamically create and inject a Liferay ModelListener (output of callbacks go to your Portal log file, e.g. `tomcat-xx.xx.xx/logs/catalina.out`).

- `hackjs2.js` - Dynamically create and inject a Portlet into the Liferay runtime.

- `hackjs3.js` - Shows Liferay realtime performance statistics using Liferay's JMX MBeans. You must add several JVM command line options to your `CATALINA_OPTS` when using Tomcat (and other app servers will require the same options to the JVM). For example, in `tomcat-xx.xx.xx/bin/setenv.sh`:

```
CATALINA_OPTS="$CATALINA_OPTS -Dfile.encoding=UTF8 -Djava.net.preferIPv4Stack=true  -Dorg.apache.catalina.loader.WebappClassLoader.ENABLE_CLEAR_REFERENCES=false -Duser.timezone=GMT -Xmx2g -XX:MaxPermSize=768m -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=12345 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Djava.rmi.server.useCodebaseOnly=false"
```
## Hack 10 - Visualizing module dependencies

See `lr-hacks/dep-vis`. There are two components here:

- `dependency-graph-rest` - A JSON WS service (no, not really RESTful) for generating JSON object representing Liferay bundle and service dependencies.

- `amdatu` - Deriving from [Paul Bakker's D3 Apache Felix Dependency Manager demo](https://bitbucket.org/paul_bakker/dependency-graph/src/92725c1a4094dd00f98105bf70ef552d16c30f96/org.amdatu.dependencygraph/src/org/amdatu/dependencygraph/?at=master), this Liferay plugin visualizes the result of the above web service using a [D3 Force Layout](https://github.com/mbostock/d3/wiki/Force-Layout). 

## Bonus - Social Driver for Liferay 7

See `lr-hacks/social-driver`. This portlet will generate a bunch of fake data on Liferay, by creating fake users (with real looking profile pictures, job titles, locations, etc) and creating fake content (blogs, wikis, forum posts, comments, rankings, friend requests) using real data from Wikipedia. It makes for a nicer looking demo site!

There are two portlets here:

- `social-driver-portlet` - This is suitable for use with the classic Liferay Plugins SDK for Liferay 7.
- `social.driver.web` - This is a standalone buildable OSGi module which can be dropped into Liferay's OSGi runtime. It produces the same as above but has been ported (thanks to [Jamie Sammons](https://www.liferay.com/web/jamie.sammons/profile)!) to Liferay 7's module-based plugin architecture.

Just install this portlet, drop it onto any page, and click the buttons to start and stop the generator threads for various content types. Don't leave it running for too long - it will fill up your DB quickly (a max of 50 users are created, but an unlimited number of contents are created).


