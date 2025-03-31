package task_1;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class ResolveName {

    // Адрес Яндекса
    static final String INITIAL_ADDRESS_1 = "yandex.ru";

    // Адрес Радика
    static final String INITIAL_ADDRESS_2 = "rsreu.ru";

    // Адрес Радика
    static final String INITIAL_ADDRESS_3 = "google.com";

    public static void main(String[] args) {
        try {

            // Изначальное DNS имя
            InetAddress addr1 = InetAddress.getByName(INITIAL_ADDRESS_3);
            System.out.println("Initial address: " + addr1);

            // Определение IP-адреса
            InetAddress addr2 = InetAddress.getByName(addr1.getHostAddress());

            // Вывод IP-адреса
            System.out.println("IP-address from initial DNS: " + addr2.getHostAddress());

            // Вывод DNS имени по введенному адресу 176.118.219.83
            String hostName = addr2.getHostName();
            System.out.println("Resolved DNS name: " + hostName);

            InetAddress addr3 = InetAddress.getByName(hostName);

            // Вернет по DNS имени IP адрес
            System.out.println("IP-address from DNS: " + addr3.getHostAddress());

            // Проверка хоста
            if (addr2.equals(addr3)) {
                System.out.println("It's the same host!");
            }

        } catch (UnknownHostException ex) {

            System.out.println("Error");

        }
    }
}

/*
    Здесь существует PTR-запись в DNS, поскольку есть обратное разрешение
    IP-адреса.

             For Yandex:
                Initial address: yandex.ru/5.255.255.77
                IP-address from initial DNS: 5.255.255.77
                Resolved DNS name: yandex.ru
                IP-address from DNS: 5.255.255.77

    Здесь нет PTR-записи в DNS, поэтому возвращается просто IP-адрес.

            For RSREU:
                Initial address: rsreu.ru/176.118.219.83
                IP-address from initial DNS: 176.118.219.83
                Resolved DNS name: 176.118.219.83
                IP-address from DNS: 176.118.219.83

     Для больших сервисов может использоваться несколько серверов, поэтому
     IP-адрес для одного DNS могут отличаться.

     Помимо этого, разные домены могут принадлежать одному IP-адресу, поскольку
     PTR-записи могут указывать в целом на весь сервер, а не на домен.

            For Google:
                Initial address: google.com/216.58.211.238
                IP-address from initial DNS: 216.58.211.238
                Resolved DNS name: mad01s24-in-f14.1e100.net
                IP-address from DNS: 216.58.211.238
 */