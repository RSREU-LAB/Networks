package example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

public class EchoTCPClient {

    public static void main(String[] args) throws IOException {
        if (args.length < 2 || args.length > 3) {
            throw new IllegalArgumentException("Usage: <Server> <Word> [Port]");
        }

        String server = args[0];
        byte[] data = args[1].getBytes();
        int serverPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7;

        Socket sock = new Socket(server, serverPort);
        System.out.println("Connected to server. Sending echo");

        InputStream in = sock.getInputStream();
        OutputStream out = sock.getOutputStream();
        out.write(data);

        int totalBytesRecv = 0;
        int bytesRecv;

        while (totalBytesRecv < data.length) {
            if ((bytesRecv = in.read(data, totalBytesRecv, data.length - totalBytesRecv)) == -1) {
                throw new SocketException("Connection terminated");
            }
            totalBytesRecv += bytesRecv;
        }

        System.out.println("Echo received: " + new String(data));
        sock.close();
    }
}
