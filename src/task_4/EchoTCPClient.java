package task_4;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class EchoTCPClient {
    public static void main(String[] args) throws IOException {
        if (args.length < 2 || args.length > 3) {
            throw new IllegalArgumentException("Usage: <Server> <Word> [Port]");
        }

        String server = args[0];
        int serverPort = (args.length == 2) ? Integer.parseInt(args[1]) : 7;

        Socket sock = new Socket(server, serverPort);
        System.out.println("Connected to server. Sending echo");

        InputStream in = sock.getInputStream();
        OutputStream out = sock.getOutputStream();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите строку (или 'exit' для выхода): ");
            String userInput = scanner.nextLine();

            // Выход "exit" из цикла
            if (userInput.equalsIgnoreCase("exit")) {
                System.out.println("Завершение работы...");
                break;
            }

            byte[] data = userInput.getBytes();

            // Отправляем данные на сервер
            out.write(data);

            // Чтение ответа от сервера
            int totalBytesRecv = 0;
            int bytesRecv;
            byte[] responseBuffer = new byte[256]; // Буфер для ответа от сервера

            while (totalBytesRecv < data.length) {
                bytesRecv = in.read(responseBuffer, totalBytesRecv, data.length - totalBytesRecv);
                if (bytesRecv == -1) {
                    throw new SocketException("Connection terminated");
                }
                totalBytesRecv += bytesRecv;
            }

            // Выводим полученный ответ от сервера
            System.out.println("Echo received: " + new String(responseBuffer, 0, totalBytesRecv));
        }

        sock.close();
    }
}
