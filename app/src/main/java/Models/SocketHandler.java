package Models;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SocketHandler {
    private static io.socket.client.Socket socket;

    private SocketHandler() {
    }
    static{
        try {
            socket = IO.socket("https://secret-woodland-48792.herokuapp.com");
        } catch (URISyntaxException e) {
            throw new RuntimeException("Exception occurred in creating socket: " + e.toString());
        }
    }
    public static Socket getSocket() {
        return socket;
    }
    public static void establishConnection() {
        socket.connect();
    }
}
