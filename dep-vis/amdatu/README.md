Introduction
==========

An Apache Felix Dependency Manager extension that visualises dependencies between components. Note that this project is just an experiment, it is *not* production quality.

For a more in depth introduction see this [video](https://vimeo.com/user17212182/review/77648991/1ca7ec3741)

Usage
==========
The project has the following runtime dependencies:

* Apache Felix Dependency Manager
* [Amdatu Web Resource Handler](http://amdatu.org/components/web.html)
* [Amdatu REST](http://amdatu.org/components/web.html)
* Apache Felix HTTP whiteboard service
* A OSGi HTTP Service (e.g. Jetty)
* jackson-core-asl
* jackson-mapper-asl
* jackson-jaxrs

Drop the generated JARs into your own configuration. Create some components and start the container. You can view the web UI at /ui/index.html.
