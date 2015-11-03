# A Naïve WebSocket Bridge for Liferay 7

A fiddly but functional sketch of how JSR-356 WebSocket connections may be
bridged into the OSGi Framework in Liferay 7. Only tested on Alpha-1 with bundled
Tomcat and relies on the container to provide the JSR-356 API.

- Build/run with Java 8
- Use a reasonably current browser (Arrow functions, `const`)
- (Liferay needs to run in "developer mode" in order not to choke on the above)

## Repo Contents

- `wsapi-exporter`: Extension Bundle that exports the JSR-356 API
- `wsbridge-webapp`: A plain webapp that contains the WebSocket Endpoint
implementation. (**Deploy to Tomcat directly**)
- `example-echo-portlet`: An example of how to accept Websocket connections in
an OSGi portlet

## Operation

With the bridge webapp deployed, WebSocket connections (or rather, their
corresponding [`Session`](https://docs.oracle.com/javaee/7/api/javax/websocket/Session.html)s)
are registered as OSGi services using `RegistryUtil`. The Endpoint URL will look
like `wss?://host:port/wsbridge/ws/{selector}/{key}`, where `selector` is a
unique key for your application used for retrieving connections inside OSGi, and
`key` is a one-time token used by the bridge to ascertain that the connection is
indeed valid and expected. Liferay's `TicketLocalService` is used for this
since it's conveniently accessible on "both sides".

(Since the bridge app cannot reliably share the Portal's session, you probably
want to add some info about the remote User to `Ticket.extraInfo`. This can then
be forwarded to consumers using either Service properties or WebSocket Session
User Data.)

Service Components running inside the OSGi Framework (including Portlets) can
then interact with these like so:

```
@Reference(
    policy = ReferencePolicy.DYNAMIC,
    cardinality = ReferenceCardinality.MULTIPLE,
    unbind = "removeSession",
    target = "(selector=my_app_identifier)"
)
public void addSession(Session session) {...}
```

(The annotation-driven Endpoint registration mechanism is naturally not
available inside the OSGi runtime so
[`MessageHandler`](https://docs.oracle.com/javaee/7/api/javax/websocket/MessageHandler.html)s)
need to be configured programmatically.)

# See

- [JSR 356, Java API for WebSocket](http://www.oracle.com/technetwork/articles/java/jsr356-1937161.html)
- [`javax.websocket` Javadocs](https://docs.oracle.com/javaee/7/api/javax/websocket/package-summary.html)
