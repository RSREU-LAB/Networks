package chargen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;

public class CharGenClient {

    private static final String SERVER = "localhost";
    private static final int PORT = 19;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER, PORT);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (ConnectException ce) {
            System.err.println("Unsuccessful connection to server: " + ce.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
