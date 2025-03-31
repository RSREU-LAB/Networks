package task_4;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Worker {
    private static final int BUFF_SIZE = 256;

    public static void main(String[] args) {
        try {
            InputStream in = System.in;
            OutputStream out = System.out;
            byte[] buffer = new byte[BUFF_SIZE];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                out.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
