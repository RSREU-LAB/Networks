package chargen;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CharGenServer {

    private static final int PORT = 19;
    private static final int LINE_LEN = 72;
    private static final String CHAR_SEQUENCE = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}˜ ";
    private static final int CHAR_SEQUENCE_LEN = CHAR_SEQUENCE.length();

    public static void main(String[] args) {
        try (var serverSocket = new ServerSocket(PORT)) {
            System.out.println("CharGen Service started on port " + PORT + ". Listening to connections...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     OutputStream os = clientSocket.getOutputStream()) {

                    System.out.println("Accepted client: " + clientSocket.getRemoteSocketAddress());

                    int lineStart = 0;
                    while (!clientSocket.isClosed()) {
                        String line = generateLine(lineStart);
                        os.write(line.getBytes());
                        os.flush();
                        lineStart++;
                    }
                } catch (IOException e) {
                    System.out.println("Client disconnected: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String generateLine(int offset) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < LINE_LEN; i++) {
            char c = CHAR_SEQUENCE.charAt((offset + i) % CHAR_SEQUENCE_LEN);
            sb.append(c);
        }
        sb.append("\r\n");
        return sb.toString();
    }
}
