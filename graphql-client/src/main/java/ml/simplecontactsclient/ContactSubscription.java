package ml.simplecontactsclient;

import java.net.URI;
import java.util.concurrent.LinkedBlockingDeque;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ApplicationScoped
public class ContactSubscription {

    // private static final LinkedBlockingDeque<String> MESSAGES = new
    // LinkedBlockingDeque<>();

    ContactSubscription() {
        try {
            URI graphqlSubscriptionURL = URI.create("http://localhost:8080/graphql");

            Session session = ContainerProvider.getWebSocketContainer().connectToServer(Client.class,
                    graphqlSubscriptionURL);

            // session.getAsyncRemote()
            // System.out.println("WebSocket opened: " + session.getId());

        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ClientEndpoint
    public static class Client {
        /*
         * @OnOpen public void open(Session session) { // Send a message to indicate
         * that we are ready, // as the message handler may not be registered
         * immediately after this callback.
         * session.getAsyncRemote().sendText("_ready_");
         *
         * System.out.println(session.getId()); }
         */

        @OnOpen
        public void helloOnOpen(Session session) {
            System.out.println("WebSocket opened: " + session.getId());
        }

        @OnMessage
        void message(String msg) {
            System.out.println(msg);
        }

        @OnClose
        public void helloOnClose(CloseReason reason) {
            System.out.println("WebSocket connection closed with CloseCode: " + reason.getCloseCode());
        }
    }

}
