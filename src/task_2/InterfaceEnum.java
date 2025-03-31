package task_2;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

import java.util.Enumeration;

public class InterfaceEnum {

    public static void main(String[] args) throws SocketException {

        Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();

        while (ifaces.hasMoreElements()) {

            NetworkInterface iface = ifaces.nextElement();
            System.out.println("\n" + iface.getName() + " " + iface.getDisplayName() + ":");
            Enumeration<InetAddress> addrs = iface.getInetAddresses();

            if (!addrs.hasMoreElements()) {
                System.out.println("\tNo addresses in list");
            }

            while (addrs.hasMoreElements()) {
                InetAddress addr = addrs.nextElement();
                System.out.println("\taddress: " + addr.getHostAddress());
            }

            //Вывод MAC адресов
            printMacAddress(iface);
        }
    }


    private static void printMacAddress(NetworkInterface iface)
    {
        try {

            // Получаем массив байтов MAC-адреса интерфейса
            byte[] macAddress = iface.getHardwareAddress();

            if (macAddress != null) {
                System.out.print("\t MAC address: ");
                for (int i = 0; i < macAddress.length; i++) {

                    // Вывод байта 16-ным числом
                    System.out.format("%02X", macAddress[i]);

                    // Убирается последний дефис
                    if (i != macAddress.length - 1) {
                        System.out.print("-");
                    }
                }
                System.out.print("\n");
            } else {
                System.out.println("No MAC address");
            }
        } catch (SocketException e) {
            System.err.println("Error getting MAC address: " + e.getMessage());
        }
    }
}
