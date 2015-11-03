package com.monator.liferay.wsbridge;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Ticket;
import com.liferay.portal.model.User;
import com.liferay.portal.service.TicketLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Über-endpoint that registers established {@link Session}s as OSGi Service,
 * provided that they pass a trivial sanity check.
 */
@ServerEndpoint("/ws/{selector}/{key}")
public class BridgeEndpoint {

    public static final int TICKET_TYPE_WEBSOCKET = 100001;

    private final static Log LOG = LogFactoryUtil.getLog(BridgeEndpoint.class);

    private ServiceRegistration<Session> serviceRegistration;

    @OnOpen
    public void onOpen(Session session, @PathParam("selector") String selector,
                       @PathParam("key") String key)
        throws IOException {

        Ticket ticket = null;

        try {
            ticket = TicketLocalServiceUtil.getTicket(key);

            if (!validateTicket(ticket)) {
                session.close();
                return;
            }

            String extraInfo = ticket.getExtraInfo();
            Map<String, Object> properties = new HashMap<>();

            session.getUserProperties().put("extraInfo", extraInfo);
            properties.put("selector", selector);

            serviceRegistration = RegistryUtil.getRegistry().registerService(
                Session.class, session, properties);

        } catch (PortalException e) {
            session.close(new CloseReason(
                CloseReason.CloseCodes.VIOLATED_POLICY, "Unexpected Connection"));
        } finally {
            if (ticket != null) {
                TicketLocalServiceUtil.deleteTicket(ticket);
            }
        }
    }

    @OnClose
    public void onClose() {
        if (serviceRegistration != null) {
            serviceRegistration.unregister();
        }
    }

    private static boolean validateTicket(Ticket ticket) {
        return
            ticket.getClassNameId() == PortalUtil.getClassNameId(User.class)
            && ticket.getType() == TICKET_TYPE_WEBSOCKET
            && !ticket.isExpired();
    }
}
