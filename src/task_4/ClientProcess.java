package task_4;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientProcess {

    static byte[] receiveBuf = new byte[256];

    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println("Usage: EchoClientHandler <Host> <Port>");
            return; // добавляем выход из программы, если аргументы не корректные
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);

        try {
            // Подключаемся по переданному адресу и порту
            Socket clientSock = new Socket(host, port);
            System.out.println("Connected to client: " + host + ":" + port);

            try {

                // Потоки для чтения и записи
                InputStream in = clientSock.getInputStream();
                OutputStream out = clientSock.getOutputStream();

                int recvMsgSize;

                // Чтение данных от клиента и их отправка обратно
                while ((recvMsgSize = in.read(receiveBuf)) != -1) {
                    out.write(receiveBuf, 0, recvMsgSize);  // Эхо-сервер: отправляем обратно полученные данные
                }
            } catch (IOException e) {
                System.err.println("Error during communication: " + e.getMessage());
            } finally {
                try {
                    clientSock.close();
                } catch (IOException ex) {
                    System.err.println("Error closing socket: " + ex.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        }
    }
}
