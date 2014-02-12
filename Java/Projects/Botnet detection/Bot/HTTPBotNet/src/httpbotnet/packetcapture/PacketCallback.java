/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package httpbotnet.packetcapture;

import net.sourceforge.jpcap.net.Packet;

/**
 *
 * @author Student
 */
public interface PacketCallback {
    public void update(Packet packet);
}
