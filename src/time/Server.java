package time;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.time.Instant;

public class Server {

    private static final int PORT = 12345;
    private static final long OFFSET_1900_TO_1970 = 2208988800L;

    public static void main(String[] args) throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Time Server started on port " + PORT);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     OutputStream out = clientSocket.getOutputStream()) {

                    long secondsSince1900 = Instant.now().getEpochSecond() + OFFSET_1900_TO_1970;
                    byte[] timeResponse = ByteBuffer.allocate(8).putLong(secondsSince1900).array();

                    out.write(timeResponse);
                    System.out.println("Time sent to client: " + secondsSince1900);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
