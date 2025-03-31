package task_4;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Manager {
    private static final int BUFF_SIZE = 256;

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: <Port>");
        }
        int port = Integer.parseInt(args[0]);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Manager started. Listening on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted client: " + clientSocket.getRemoteSocketAddress());
                new Thread(new ConnectionHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ConnectionHandler implements Runnable {
        private Socket clientSocket;

        public ConnectionHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                ProcessBuilder pb = new ProcessBuilder("java", "-cp", ".", "task_4.Worker");
                pb.redirectErrorStream(true);
                Process workerProcess = pb.start();

                new Thread(() -> {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(workerProcess.getErrorStream()))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            System.err.println("Worker output: " + line);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }).start();

                InputStream clientIn = clientSocket.getInputStream();
                OutputStream clientOut = clientSocket.getOutputStream();
                OutputStream workerIn = workerProcess.getOutputStream();
                InputStream workerOut = workerProcess.getInputStream();

                // Поток для передачи данных от клиента в Worker
                Thread forwardClientToWorker = new Thread(() -> {
                    try {
                        byte[] buffer = new byte[BUFF_SIZE];
                        int bytesRead;
                        while ((bytesRead = clientIn.read(buffer)) != -1) {
                            workerIn.write(buffer, 0, bytesRead);
                            workerIn.flush();
                        }
                        workerIn.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                // Поток для передачи данных от Worker клиенту
                Thread forwardWorkerToClient = new Thread(() -> {
                    try {
                        byte[] buffer = new byte[BUFF_SIZE];
                        int bytesRead;
                        while ((bytesRead = workerOut.read(buffer)) != -1) {
                            clientOut.write(buffer, 0, bytesRead);
                            clientOut.flush();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                forwardClientToWorker.start();
                forwardWorkerToClient.start();

                forwardClientToWorker.join();
                forwardWorkerToClient.join();

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
