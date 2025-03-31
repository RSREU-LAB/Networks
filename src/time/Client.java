package time;

import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class Client {

    private static final String SERVER = "localhost";
    private static final int PORT = 12345;
    private static final long OFFSET_1900_TO_1970 = 2208988800L;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER, PORT);
             InputStream in = socket.getInputStream()) {

            byte[] timeResponse = new byte[8];

            if (in.read(timeResponse) != 8) {
                throw new RuntimeException("Ошибка при получении времени");
            }

            long secondsSince1900 = ByteBuffer.wrap(timeResponse).getLong();
            long timestamp = secondsSince1900 - OFFSET_1900_TO_1970;

            // Преобразование время в Instant
            Instant instant = Instant.ofEpochSecond(timestamp);

            // Преобразование в ZonedDateTime с учетом UTC
            ZonedDateTime utcDateTime = instant.atZone(ZoneOffset.UTC);

            // Преобразование в местное время, например, для временной зоны UTC+3
            ZonedDateTime localDateTime = utcDateTime.withZoneSameInstant(ZoneOffset.ofHours(3));

            System.out.println("Местное время (UTC+3): " + localDateTime.toLocalDateTime());

            System.out.println("Текущее время на сервере: " + Instant.ofEpochSecond(timestamp));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
