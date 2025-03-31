package example;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class InterfaceEnum {

    public static void main(String[] args) throws SocketException {
        Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();

        while (ifaces.hasMoreElements()) {
            NetworkInterface iface = ifaces.nextElement();
            System.out.println(iface.getName() + " " + iface.getDisplayName() + ":");
            Enumeration<InetAddress> addrs = iface.getInetAddresses();

            if (!addrs.hasMoreElements()) {
                System.out.println("\tNo addresses in list");
            }

            while (addrs.hasMoreElements()) {
                InetAddress addr = addrs.nextElement();
                System.out.println("\taddress: " + addr.getHostAddress());
            }
        }
    }
}
