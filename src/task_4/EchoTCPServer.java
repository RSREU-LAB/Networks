package task_4;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class EchoTCPServer {
    private static final int BUFSIZE = 256;
    public static byte[] receiveBuf = new byte[BUFSIZE];

    static int serverPort;
    static ServerSocket serverSock;

    public static void main(String[] args) throws IOException, InterruptedException {

        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: <Port>");
        }

        serverPort = Integer.parseInt(args[0]);
        serverSock = new ServerSocket(serverPort);

        System.out.println("Сервер запущен и слушает порт " + serverPort + "...");

        while (true) {
            Socket clientSock = serverSock.accept();
            SocketAddress clientAddress = clientSock.getRemoteSocketAddress();
            System.out.println("Accepted client: " + clientAddress);

            // Создание потока для работы с клиентом
            startProcess(clientSock);
        }
    }


    private static void startProcess(Socket clientSock)  {

        int clientPort = clientSock.getPort();


        new Thread(() -> {
            try
            {
                System.out.println("Создание процесса");
                ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", "C:\\Users\\Кирюха Палыч\\Desktop\\Радик\\6 СЕМЕСТР\\ТСиП\\TCP\\out\\production\\TCP", "task_4.ClientProcess", clientSock.getInetAddress().getHostAddress(), String.valueOf(clientPort));
                System.out.println("Запуск процесса");
                Process process = processBuilder.start();

                // Ожидаем завершения процесса
                process.waitFor();
                process.destroy();

            } catch (IOException | InterruptedException e) {
                System.out.println("Error: " + e.getMessage());
            }

        }).start();

    }
}
