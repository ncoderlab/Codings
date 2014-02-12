/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package httpbotnet.packetcapture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sourceforge.jpcap.capture.CaptureDeviceOpenException;
import net.sourceforge.jpcap.capture.CapturePacketException;
import net.sourceforge.jpcap.capture.PacketCapture;
import net.sourceforge.jpcap.capture.PacketListener;
import net.sourceforge.jpcap.net.Packet;

/**
 *
 * @author Student
 */
public class PacketCaptureThread extends Thread {
    
    private String interfaceName;
    private PacketCallback callback;

    public PacketCallback getCallback() {
        return callback;
    }

    public void setCallback(PacketCallback callback) {
        this.callback = callback;
    }
    
    public PacketCaptureThread(String interfaceName) {
        this.interfaceName = interfaceName;
    }
    
    public void start() {
        super.start();
    }
    
    public void run() {
//        Thread thread = new Thread(new Runnable() {
//            public void run() {
//                try {
//                    ServerSocket socket = new ServerSocket(9999,100);
//                    System.out.println("Socket Server Started!");
//
//                    Socket client = socket.accept();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
//
//                    while(true) {
//                        String line = reader.readLine();
//                        byte[] buff = line.getBytes();
//                        JOptionPane.showMessageDialog(null, "Buffer:"+new String(buff));
//                    }
//
//                } catch (IOException ex) {
//                    Logger.getLogger(PacketCaptureThread.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
//
//        thread.start();

        try {
            PacketCapture capture = new PacketCapture();
            capture.open(interfaceName, true);
            
            capture.addPacketListener(new PacketListener() {
                public void packetArrived(Packet packet) {
                    System.out.println("Packet:"+packet);
                    callback.update(packet);
                }
            });
            
            while(true) {
                capture.capture(100);
            }
            
        } catch (CapturePacketException ex) {
            Logger.getLogger(PacketCaptureThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CaptureDeviceOpenException ex) {
            Logger.getLogger(PacketCaptureThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
