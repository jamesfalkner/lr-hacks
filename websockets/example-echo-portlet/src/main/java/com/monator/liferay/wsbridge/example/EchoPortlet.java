package com.monator.liferay.wsbridge.example;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.Ticket;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.TicketLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.registry.RegistryUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.websocket.Session;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component(
    service = Portlet.class,
    property = {
        "com.liferay.portlet.display-category=category.sample",
        "javax.portlet.display-name=Websocket Echo Example",
        "javax.portlet.expiration-cache=0",
        "javax.portlet.init-param.template-path=/",
        "javax.portlet.init-param.view-template=/view.jsp",
        "javax.portlet.name=com_monator_liferay_wsbridge_example_EchoPortlet",
        "javax.portlet.security-role-ref=guest,power-user,user",
        "javax.portlet.supports.mime-type=text/html"
    }
)
public class EchoPortlet extends MVCPortlet {

    public static final int TICKET_TYPE_WEBSOCKET = 100001;

    private static final String WS_SELECTOR = "wsbridge_echo";

    private Map<String, Session> sessions;

    private JSONFactory jsonFactory;

    private Portal portal;

    @Activate
    public void activate() {
        sessions = new ConcurrentHashMap<>();
    }

    @Override
    public void doView(RenderRequest request, RenderResponse response)
        throws PortletException, IOException {

        ThemeDisplay themeDisplay =
            (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

        JSONObject extraInfo = jsonFactory.createJSONObject();
        extraInfo.put("userId", themeDisplay.getUserId());
        extraInfo.put("plid", themeDisplay.getPlid());

        String portalSessionId = portal.getHttpServletRequest(request)
            .getSession().getId();
        extraInfo.put("portalSessionId", portalSessionId);
        extraInfo.put("portletId", request.getAttribute(
            WebKeys.PORTLET_ID));

        Ticket ticket = TicketLocalServiceUtil.addTicket(
            themeDisplay.getCompanyId(), User.class.getName(),
            themeDisplay.getUserId(),
            TICKET_TYPE_WEBSOCKET, extraInfo.toString(),
            Date.from(Instant.now().plusSeconds(10)),
            new ServiceContext());

        String wsUrl = String.format("ws://localhost:8081/wsbridge/ws/%s/%s",
            WS_SELECTOR, ticket.getKey());

        request.setAttribute("websocketURL", wsUrl);
        request.setAttribute("namespace", response.getNamespace());
        super.doView(request, response);
    }

    @Reference
    public void setJSONFactory(JSONFactory jsonFactory) {
        this.jsonFactory = jsonFactory;
    }

    @Reference
    public void setPortal(Portal portal) {
        this.portal = portal;
    }

    @Reference(
        policy = ReferencePolicy.DYNAMIC,
        cardinality = ReferenceCardinality.MULTIPLE,
        unbind = "removeSession",
        target = "(selector=" + WS_SELECTOR + ")"
    )
    public void addSession(Session session, Map<String, Object> properties) {

        try {
            JSONObject extraInfo = jsonFactory.createJSONObject(
                (String) session.getUserProperties().get("extraInfo"));

            sessions.put(
                String.valueOf(extraInfo.getLong("userId")), session);
        } catch (JSONException e) {}

        ModelListener<BlogsEntry> modelListener = new BaseModelListener<BlogsEntry>() {
            @Override
            public void onAfterCreate(BlogsEntry model) throws ModelListenerException {
                System.out.println("CREATED " + model);
                System.out.println("Attempting to send");

                if (session.isOpen()) {
                    try {
                        User user = UserLocalServiceUtil.getUser(model.getUserId());
                        String country = user.getAddresses().get(0).getCountry().getName(Locale.ENGLISH);

                        JSONObject json = JSONFactoryUtil.createJSONObject();
                        json.put("address", country);
                        session.getBasicRemote().sendText(json.toString());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

        };

        RegistryUtil.getRegistry().registerService(ModelListener.class, modelListener, null);

//        session.addMessageHandler(String.class, message -> {
//            try {
//                session.getBasicRemote().sendText(
//                    String.format("Thanks for %s!", message));
//
//                if (message.startsWith("yell:")) {
//                    broadcast("Someone yells " + message.substring(5));
//                }
//            } catch (IOException e) {
//            }
//        });
    }

    public void removeSession(Session session, Map<String, Object> properties) {

        try {
            JSONObject extraInfo = jsonFactory.createJSONObject(
                (String) session.getUserProperties().get("extraInfo"));

            sessions.remove(
                String.valueOf(extraInfo.getLong("userId")), session);
        } catch (JSONException e) {}

    }

    private void broadcast(String message) throws IOException {
        for (Session session : sessions.values()) {
            session.getBasicRemote().sendText(message);
        }
    }
}
