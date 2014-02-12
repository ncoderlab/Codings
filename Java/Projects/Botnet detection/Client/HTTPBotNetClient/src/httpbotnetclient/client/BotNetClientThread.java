/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package httpbotnetclient.client;

import com.httpbotnet.BlackList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Student
 */
public class BotNetClientThread {

   private BlackListCallback callback;

    public BotNetClientThread(BlackListCallback callback) {
        this.callback = callback;
    }

   

   public void start() {
        try {

            final Socket client = new Socket("192.168.1.7", 9999);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    ObjectInputStream objectIn = null;
                    {
                        BufferedReader reader = null;
                        try {
                            PrintWriter pw = new PrintWriter(client.getOutputStream());
                            Enumeration<NetworkInterface> nws = NetworkInterface.getNetworkInterfaces();
                            NetworkInterface nw1 = null;
                            while(nws.hasMoreElements()) {
                                NetworkInterface nw = nws.nextElement();
                                System.out.println("Network Interface:"+nw);
                                if(!nw.isLoopback() && nw.getInetAddresses().hasMoreElements() && nw.getSubInterfaces().hasMoreElements()) {
                                    nw1 = nw;
                                    System.out.println("Network Interface:"+nw1.getSubInterfaces().hasMoreElements());
                                    break;
                                }
                            }
                            pw.println("HELLO:192.168.1.6");
                            pw.flush();
                            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                            BlackList blackList = new BlackList();
                            String line = "";
                            while (true) {
                                    line = reader.readLine();
                                    
                                    if(line!=null)
                                    if(line.equals("---end of blacklist---")) {
                                        callback.updateBlackList(blackList);
                                        blackList.getBlackList().clear();
                                    } else if(line.startsWith("blacklist:")) {
                                        String blackListIP = line.substring(line.indexOf(":")+1);
                                        blackList.addBlackList(blackListIP);
                                    } else {
                                        continue;
                                    }
                                }
                            
                        } catch (IOException ex) {
                            Logger.getLogger(BotNetClientThread.class.getName()).log(Level.SEVERE, null, ex);
                        } finally {
                            try {
                                reader.close();
                            } catch (IOException ex) {
                                Logger.getLogger(BotNetClientThread.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    }
            });

            thread.start();

        } catch (UnknownHostException ex) {
            Logger.getLogger(BotNetClientThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BotNetClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }

   }
}
