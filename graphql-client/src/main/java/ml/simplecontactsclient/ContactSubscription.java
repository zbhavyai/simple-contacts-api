package ml.simplecontactsclient;

import java.net.URI;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

@ApplicationScoped
@ClientEndpoint
public class ContactSubscription {
    private Session userSession = null;
    private MessageHandler messageHandler;
    private final URI _endpointURI = URI.create("ws://localhost:8080/graphql");

    // new URI("wss://localhost:8080/graphql")
    // URI graphqlSubscriptionURL = URI.create("http://localhost:8080/graphql");

    public ContactSubscription() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, this._endpointURI);
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void onOpen(Session userSession) {
        System.out.println("WebSocket opened: " + userSession.getId());
        this.userSession = userSession;
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("WebSocket connection closed with CloseCode: " + reason.getCloseCode());
        this.userSession = null;
    }

    @OnMessage
    public void onMessage(String message) {
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }

    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }

    /*
     * @OnMessage public void onMessage(ByteBuffer bytes) {
     * System.out.println("Handle byte buffer"); }
     */

    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

    public static interface MessageHandler {
        public void handleMessage(String message);
    }
}
