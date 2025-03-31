package example;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ResolveName {
    public static void main(String[] args) {
        try {

            InetAddress addr = InetAddress.getByName("www.rsreu.ru");
            System.out.println(addr);

        } catch (UnknownHostException ex) {

            System.out.println("Couldn't resolve www.rsreu.ru");

        }
    }
}