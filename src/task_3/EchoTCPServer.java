package task_3;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class EchoTCPServer {
    private static final int BUFSIZE = 256;
    public static byte[] receiveBuf = new byte[BUFSIZE];

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: <Port>");
        }

        int serverPort = Integer.parseInt(args[0]);
        var serverSock = new ServerSocket(serverPort);

        System.out.println("Сервер запущен и слушает порт " + serverPort + "...");

        while (true) {
            Socket clientSock = serverSock.accept();
            SocketAddress clientAddress = clientSock.getRemoteSocketAddress();
            System.out.println("Accepted client: " + clientAddress);


            // Создание потока для работы с клиентом
            new Thread(() -> connectClient(clientSock)).start();
        }
    }

    private static void connectClient(Socket clientSock)
    {
        try {

            InputStream in = clientSock.getInputStream();
            OutputStream out = clientSock.getOutputStream();

            int recvMsgSize;
            while ((recvMsgSize = in.read(receiveBuf)) != -1) {
                out.write(receiveBuf, 0, recvMsgSize);
            }
        } catch (IOException e) {

            System.err.println("Error: " + e.getMessage());

        } finally {

            try {

                clientSock.close();

            } catch (IOException ex) {

                System.err.println("Error: " + ex.getMessage());

            }
        }
    }
}
