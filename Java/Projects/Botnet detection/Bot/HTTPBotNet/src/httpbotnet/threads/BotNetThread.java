/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package httpbotnet.threads;

import httpbotnet.db.DBUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.jpcap.net.TCPPacket;

/**
 *
 * @author Student
 */
public class BotNetThread {
    private BotCallback callback;

    private List<String> blackList = new ArrayList<String>();
    private List<String> whiteList = new ArrayList<String>();
    private List<String> grayList = new ArrayList<String>();

    public BotNetThread(BotCallback callback) {
        this.callback = callback;
    }

    public void start() {
        Thread thread = new Thread(new Runnable() {
            public void run() {

                while(true) {
                    Hashtable<String,Vector<TCPPacket>> packets = callback.getPackets();
                    Hashtable<String,Vector<Long>> frequencies = callback.getFrequencies();

                    Hashtable<String,Socket> clients = callback.getClients();

                    Enumeration<String> ips = packets.keys();

                    Vector<Double> standarddeviations = new Vector<Double>();

                    while(ips.hasMoreElements()) {
                        String ip = ips.nextElement();
                        Vector<TCPPacket> tcpPackets = tcpPackets = packets.get(ip);
                        Vector<Long> frequencie = frequencies.get(ip);

                        double standarddeviation = getStandardDeviation(tcpPackets, frequencie, tcpPackets.size()/2);
                        standarddeviations.add(standarddeviation);

                        double stdeviation = standarddeviation / frequencie.size();
                        if((stdeviation) < 0.5 && !blackList.contains(ip)) {
                            blackList.add(ip);
                            DBUtils.connect();
                            DBUtils.updateBlackList(blackList);
                        } else if(!blackList.contains(ip) && !grayList.contains(ip)) {
                            grayList.add(ip);
                            DBUtils.connect();
                            DBUtils.updateGrayList(grayList);
                        }

                        Enumeration<String> clients1 = clients.keys();

                        while(clients1.hasMoreElements()) {

                            try {
                                String clientIP = clients1.nextElement();
                                Socket client = clients.get(clientIP);

                                PrintWriter pw = new PrintWriter(client.getOutputStream());

                                for(String blackListIP : blackList) {
                                    pw.println("blacklist:"+blackListIP);
                                    pw.flush();
                                }

                                pw.println("---end of blacklist---");
                                pw.flush();

                            } catch (IOException ex) {
                                Logger.getLogger(BotNetThread.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                            }
                        }
                    }

                    double sigmaStandardDeviation= 0.0d;

                    for(Double standarddeviation : standarddeviations) {
                        sigmaStandardDeviation += standarddeviation;
                    }

                    sigmaStandardDeviation = sigmaStandardDeviation / standarddeviations.size();

                    System.out.println("Sigma Standard Deviation:"+sigmaStandardDeviation);
                    
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(BotNetThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            }
        });

        thread.start();
    }

    public double getStandardDeviation(Vector<TCPPacket> httpPackets,Vector<Long> packetFrequencies,int P) {

        

        Vector<Double> Si = new Vector<Double>();


        for(int i=0;i<httpPackets.size();i++) {
            double sigma = 0.0d;

            if(i!=(httpPackets.size()-1)) {
                double average = average(packetFrequencies,i);
                average=average*average;
                double deference = packetFrequencies.get(i+1) - packetFrequencies.get(i);
                double sqrt = Math.sqrt(deference - average/(httpPackets.size()-1));
                if(sqrt!=Double.NaN)
                    Si.add(sqrt);
            }
        }

        Vector<Double> Si2 = new Vector<Double>();

        for(int i=0;i<Si.size();i++) {
            if(!new Double(Si.get(i)).isNaN())
                Si2.add(Si.get(i));
        }

        Si = Si2;

        Vector<Double> Sr = new Vector<Double>();

        for(int i=0;i<Si.size();i++) {
            double sigma = 0.0d;
            if(P<Si.size())
            for(int j=0;j<P;j++) {
                sigma += Si.get(j)*Si.get(j);
            }
            Sr.add(Math.sqrt(sigma/P));
        }

        double sigmaSum = 0.0d;
        double average = 0.0d;

        for(int i=0;i<Sr.size();i++) {
            sigmaSum += Sr.get(i);
        }

        average = sigmaSum / Sr.size();

        return average;
    }

    private double average(Vector<Long> frequencies,int i) {
        double average = 0.0d;

        double sigma = 0.0d;

        for(int j=0;j<(i-1);j++) {
            double timeDeference = frequencies.get(j+1) - frequencies.get(j);
            sigma += timeDeference;
        }

        average = sigma / i;

        return average;
    }
}
