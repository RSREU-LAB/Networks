package task_4;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        if (args.length < 2 || args.length > 3) {
            throw new IllegalArgumentException("Usage: <Server> <Message> [Port]");
        }

        String server = args[0];
        String message = args[1];
        int serverPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7;

        try (Socket socket = new Socket(server, serverPort);
             InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream()) {

            System.out.println("Connected to server. Sending echo...");

            byte[] data = message.getBytes();
            out.write(data);
            out.flush();
            socket.shutdownOutput();

            ByteArrayOutputStream responseBuffer = new ByteArrayOutputStream();
            byte[] buffer = new byte[256];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                responseBuffer.write(buffer, 0, bytesRead);
            }

            String response = new String(responseBuffer.toByteArray());
            System.out.println("Received echo: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
