/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package httpbotnet.threads;

import java.net.Socket;
import java.util.Hashtable;
import java.util.Vector;
import net.sourceforge.jpcap.net.TCPPacket;

/**
 *
 * @author Student
 */
public interface BotCallback {
   public Hashtable<String,Vector<TCPPacket>> getPackets();
   public Hashtable<String,Vector<Long>> getFrequencies();

   public Hashtable<String,Socket> getClients();

}
