/*
 * HTTPBotNetView.java
 */

package httpbotnet;

import httpbotnet.db.DBUtils;
import httpbotnet.packetcapture.PacketCallback;
import httpbotnet.packetcapture.PacketCaptureThread;
import httpbotnet.threads.BotCallback;
import httpbotnet.threads.BotNetThread;
import httpbotnet.threads.ServerThread;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.net.Socket;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.DefaultCaret;
import net.sourceforge.jpcap.capture.CaptureDeviceLookupException;
import net.sourceforge.jpcap.net.ARPPacket;
import net.sourceforge.jpcap.net.ICMPPacket;
import net.sourceforge.jpcap.net.Packet;
import net.sourceforge.jpcap.net.TCPPacket;
import net.sourceforge.jpcap.net.UDPPacket;

/**
 * The application's main frame.
 */
public class HTTPBotNetView extends FrameView {

    public HTTPBotNetView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });

        try {
            ConsoleRedirect redirect = new ConsoleRedirect(this.jTextAreaConsole, System.out);
            System.setOut(redirect);
         
            DefaultCaret caret = (DefaultCaret) jTextAreaConsole.getCaret();
            caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

            System.load("c:\\jpcap\\Jpcap.dll");


        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = HTTPBotNetApp.getApplication().getMainFrame();
            aboutBox = new HTTPBotNetAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        HTTPBotNetApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jInternalFrameConsole = new javax.swing.JInternalFrame();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaConsole = new javax.swing.JTextArea();
        jInternalFramePackets = new javax.swing.JInternalFrame();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanelPackets = new javax.swing.JPanel();
        jInternalFrameViewBlackLists = new javax.swing.JInternalFrame();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableLists = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemViewBlackList = new javax.swing.JMenuItem();
        jMenuItemViewGrayList = new javax.swing.JMenuItem();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        jMenuItemPacketCapture = new javax.swing.JMenuItem();
        jMenuItemSampleCalculateStandardDeviation = new javax.swing.JMenuItem();
        jMenuItemViewBlackLists = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jDesktopPane1.setName("jDesktopPane1"); // NOI18N

        jInternalFrameConsole.setClosable(true);
        jInternalFrameConsole.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jInternalFrameConsole.setIconifiable(true);
        jInternalFrameConsole.setMaximizable(true);
        jInternalFrameConsole.setResizable(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(httpbotnet.HTTPBotNetApp.class).getContext().getResourceMap(HTTPBotNetView.class);
        jInternalFrameConsole.setTitle(resourceMap.getString("jInternalFrameConsole.title")); // NOI18N
        jInternalFrameConsole.setName("jInternalFrameConsole"); // NOI18N
        jInternalFrameConsole.setVisible(true);

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jTextAreaConsole.setBackground(resourceMap.getColor("jTextAreaConsole.background")); // NOI18N
        jTextAreaConsole.setColumns(20);
        jTextAreaConsole.setFont(resourceMap.getFont("jTextAreaConsole.font")); // NOI18N
        jTextAreaConsole.setForeground(resourceMap.getColor("jTextAreaConsole.foreground")); // NOI18N
        jTextAreaConsole.setRows(5);
        jTextAreaConsole.setText(resourceMap.getString("jTextAreaConsole.text")); // NOI18N
        jTextAreaConsole.setName("jTextAreaConsole"); // NOI18N
        jScrollPane3.setViewportView(jTextAreaConsole);

        javax.swing.GroupLayout jInternalFrameConsoleLayout = new javax.swing.GroupLayout(jInternalFrameConsole.getContentPane());
        jInternalFrameConsole.getContentPane().setLayout(jInternalFrameConsoleLayout);
        jInternalFrameConsoleLayout.setHorizontalGroup(
            jInternalFrameConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 520, Short.MAX_VALUE)
            .addGroup(jInternalFrameConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE))
        );
        jInternalFrameConsoleLayout.setVerticalGroup(
            jInternalFrameConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 275, Short.MAX_VALUE)
            .addGroup(jInternalFrameConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE))
        );

        jInternalFrameConsole.setBounds(520, 50, 530, 310);
        jDesktopPane1.add(jInternalFrameConsole, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jInternalFramePackets.setClosable(true);
        jInternalFramePackets.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jInternalFramePackets.setIconifiable(true);
        jInternalFramePackets.setMaximizable(true);
        jInternalFramePackets.setResizable(true);
        jInternalFramePackets.setTitle(resourceMap.getString("jInternalFramePackets.title")); // NOI18N
        jInternalFramePackets.setName("jInternalFramePackets"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jPanelPackets.setName("jPanelPackets"); // NOI18N
        jPanelPackets.setLayout(new java.awt.GridLayout(10, 1));
        jScrollPane2.setViewportView(jPanelPackets);

        javax.swing.GroupLayout jInternalFramePacketsLayout = new javax.swing.GroupLayout(jInternalFramePackets.getContentPane());
        jInternalFramePackets.getContentPane().setLayout(jInternalFramePacketsLayout);
        jInternalFramePacketsLayout.setHorizontalGroup(
            jInternalFramePacketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
            .addGroup(jInternalFramePacketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE))
        );
        jInternalFramePacketsLayout.setVerticalGroup(
            jInternalFramePacketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 315, Short.MAX_VALUE)
            .addGroup(jInternalFramePacketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE))
        );

        jInternalFramePackets.setBounds(20, 420, 590, 350);
        jDesktopPane1.add(jInternalFramePackets, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jInternalFrameViewBlackLists.setClosable(true);
        jInternalFrameViewBlackLists.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jInternalFrameViewBlackLists.setIconifiable(true);
        jInternalFrameViewBlackLists.setMaximizable(true);
        jInternalFrameViewBlackLists.setResizable(true);
        jInternalFrameViewBlackLists.setTitle(resourceMap.getString("jInternalFrameViewBlackLists.title")); // NOI18N
        jInternalFrameViewBlackLists.setName("jInternalFrameViewBlackLists"); // NOI18N

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        jTableLists.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableLists.setName("jTableLists"); // NOI18N
        jScrollPane4.setViewportView(jTableLists);

        jMenuBar1.setName("jMenuBar1"); // NOI18N

        jMenu1.setText(resourceMap.getString("jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N

        jMenuItemViewBlackList.setText(resourceMap.getString("jMenuItemViewBlackList.text")); // NOI18N
        jMenuItemViewBlackList.setName("jMenuItemViewBlackList"); // NOI18N
        jMenuItemViewBlackList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemViewBlackListActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemViewBlackList);

        jMenuItemViewGrayList.setText(resourceMap.getString("jMenuItemViewGrayList.text")); // NOI18N
        jMenuItemViewGrayList.setName("jMenuItemViewGrayList"); // NOI18N
        jMenuItemViewGrayList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemViewGrayListActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemViewGrayList);

        jMenuBar1.add(jMenu1);

        jInternalFrameViewBlackLists.setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout jInternalFrameViewBlackListsLayout = new javax.swing.GroupLayout(jInternalFrameViewBlackLists.getContentPane());
        jInternalFrameViewBlackLists.getContentPane().setLayout(jInternalFrameViewBlackListsLayout);
        jInternalFrameViewBlackListsLayout.setHorizontalGroup(
            jInternalFrameViewBlackListsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
            .addGroup(jInternalFrameViewBlackListsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))
        );
        jInternalFrameViewBlackListsLayout.setVerticalGroup(
            jInternalFrameViewBlackListsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 274, Short.MAX_VALUE)
            .addGroup(jInternalFrameViewBlackListsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE))
        );

        jInternalFrameViewBlackLists.setBounds(10, 150, 510, 330);
        jDesktopPane1.add(jInternalFrameViewBlackLists, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jScrollPane1.setViewportView(jDesktopPane1);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 934, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 934, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 561, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        jMenuItemPacketCapture.setText(resourceMap.getString("jMenuItemPacketCapture.text")); // NOI18N
        jMenuItemPacketCapture.setName("jMenuItemPacketCapture"); // NOI18N
        jMenuItemPacketCapture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPacketCaptureActionPerformed1(evt);
            }
        });
        fileMenu.add(jMenuItemPacketCapture);

        jMenuItemSampleCalculateStandardDeviation.setText(resourceMap.getString("jMenuItemSampleCalculateStandardDeviation.text")); // NOI18N
        jMenuItemSampleCalculateStandardDeviation.setName("jMenuItemSampleCalculateStandardDeviation"); // NOI18N
        jMenuItemSampleCalculateStandardDeviation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSampleCalculateStandardDeviationActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItemSampleCalculateStandardDeviation);

        jMenuItemViewBlackLists.setText(resourceMap.getString("jMenuItemViewBlackLists.text")); // NOI18N
        jMenuItemViewBlackLists.setName("jMenuItemViewBlackLists"); // NOI18N
        jMenuItemViewBlackLists.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemViewBlackListsActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItemViewBlackLists);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(httpbotnet.HTTPBotNetApp.class).getContext().getActionMap(HTTPBotNetView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 934, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 764, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private Vector<TCPPacket> httpPackets = new Vector<TCPPacket>();
    private Vector<Long> packetFrequencies = new Vector<Long>();

    private Hashtable<String,Vector<TCPPacket>> httpIPPackets = new Hashtable<String,Vector<TCPPacket>>();
    private Hashtable<String,Vector<Long>> ipPacketFrequencies = new Hashtable<String,Vector<Long>>();

    private ServerThread serverThread;

    private void jMenuItemPacketCaptureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPacketCaptureActionPerformed

        try {

            this.serverThread = new ServerThread();
            this.serverThread.start();
            
            String[] interfaces = net.sourceforge.jpcap.capture.PacketCapture.lookupDevices();

            for(String interface1 : interfaces) {
                System.out.println("Interface:"+interface1);
            }

            this.jInternalFramePackets.setVisible(true);

            String interface2 = interfaces[0].substring(interfaces[0].indexOf("\\"),interfaces[0].lastIndexOf("}")+1);
            System.out.println("Interface:"+interface2);
            PacketCaptureThread thread = new PacketCaptureThread(interface2);
            thread.setCallback(new PacketCallback() {
                public void update(Packet packet) {
                    JPanel panel = new JPanel();
                    panel.setLayout(new BorderLayout());
                    JLabel labelPacketInfo = new JLabel();
                    JLabel labelPacket = new JLabel();
                    labelPacket.setOpaque(true);
                    labelPacketInfo.setOpaque(true);
                    labelPacketInfo.setBorder(new EtchedBorder(EtchedBorder.RAISED));
                    String packetType = "";

                    if(packet instanceof TCPPacket) {
                        packetType = "TCP Packet";
                        labelPacket.setBackground(Color.green);
                        labelPacketInfo.setBackground(Color.green);

                        TCPPacket tcpPacket = (TCPPacket) packet;
                        String content = new String(tcpPacket.getTCPData());

                        if(tcpPacket.getDestinationPort()==80) {
                            Vector<TCPPacket> ipPackets = new Vector<TCPPacket>();
                            Vector<Long> packetFrequencies = new Vector<Long>();

                            if(httpIPPackets.containsKey(tcpPacket.getSourceAddress())) {
                                ipPackets = httpIPPackets.get(tcpPacket.getSourceAddress());
                                packetFrequencies = ipPacketFrequencies.get(tcpPacket.getSourceAddress());
                            } else {
                                ipPackets = new Vector<TCPPacket>();
                                packetFrequencies = new Vector<Long>();
                            }

                            ipPackets.add(tcpPacket);
                            packetFrequencies.add(new Date().getTime());
                            httpIPPackets.put(tcpPacket.getSourceAddress(), ipPackets);
                            ipPacketFrequencies.put(tcpPacket.getSourceAddress(), packetFrequencies);
                        }

                    } else if(packet instanceof UDPPacket) {
                        packetType = "UDP Packet";
                        labelPacket.setBackground(Color.CYAN);
                        labelPacketInfo.setBackground(Color.CYAN);

                        UDPPacket udpPacket = (UDPPacket) packet;

                    } else if(packet instanceof ARPPacket) {
                        packetType = "ARP Packet";
                        labelPacket.setBackground(Color.BLACK);
                        labelPacket.setForeground(Color.white);
                        labelPacketInfo.setBackground(Color.BLACK);
                        labelPacketInfo.setForeground(Color.white);
                    } else if(packet instanceof ICMPPacket) {
                        packetType = "ICMP Packet";
                        labelPacket.setBackground(Color.ORANGE);
                        labelPacketInfo.setBackground(Color.ORANGE);
                    }

                    labelPacket.setText(packetType);
                    labelPacketInfo.setText(packet.toString());
                    panel.add(labelPacket,BorderLayout.WEST);
                    panel.add(labelPacketInfo);

                    final Packet packet1 = packet;

                    panel.addMouseListener(new MouseListener() {

                        public void mouseClicked(MouseEvent e) {
                            try {
                                PacketInformationInternalFrame packetInformation = new PacketInformationInternalFrame(packet1);
                                jDesktopPane1.add(packetInformation);
                                packetInformation.setVisible(true);

                                PacketSender.init();
                                PacketSender.sendPacket("TCP Hack Packet!".getBytes());
                            } catch (Exception ex) {
                                Logger.getLogger(HTTPBotNetView.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        public void mousePressed(MouseEvent e) {

                        }

                        public void mouseReleased(MouseEvent e) {

                        }

                        public void mouseEntered(MouseEvent e) {
                            JPanel panel = (JPanel) e.getSource();
                            panel.setBorder(new BevelBorder(BevelBorder.LOWERED));
                        }

                        public void mouseExited(MouseEvent e) {
                            JPanel panel = (JPanel) e.getSource();
                            panel.setBorder(null);
                        }
                    });

                    ((GridLayout) jPanelPackets.getLayout()).setRows(((GridLayout) jPanelPackets.getLayout()).getRows()+1);
                    jPanelPackets.add(panel);
                    jPanelPackets.updateUI();
                }
            });

            thread.start();

            BotNetThread botNetThread = new BotNetThread(new BotCallback() {

                public Hashtable<String, Vector<TCPPacket>> getPackets() {
                    return httpIPPackets;
                }

                public Hashtable<String, Vector<Long>> getFrequencies() {
                    return ipPacketFrequencies;
                }

                public Hashtable<String, Socket> getClients() {
                    return serverThread.getClients();
                }
            });

            botNetThread.start();

        } catch (CaptureDeviceLookupException ex) {
            Logger.getLogger(HTTPBotNetView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jMenuItemPacketCaptureActionPerformed

    private void jMenuItemPacketCaptureActionPerformed1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPacketCaptureActionPerformed1
        this.jMenuItemPacketCaptureActionPerformed(evt);
    }//GEN-LAST:event_jMenuItemPacketCaptureActionPerformed1

    private void jMenuItemSampleCalculateStandardDeviationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSampleCalculateStandardDeviationActionPerformed

        int P = Integer.parseInt(JOptionPane.showInputDialog("Enter Sample Number:"));

        Vector<Double> Si = new Vector<Double>();


        for(int i=0;i<this.httpPackets.size();i++) {
            double sigma = 0.0d;

            if(i!=(this.httpPackets.size()-1)) {
                double average = average(this.packetFrequencies,i);
                double deference = this.packetFrequencies.get(i+1) - this.packetFrequencies.get(i);
                double sqrt = Math.sqrt(deference - average/(this.httpPackets.size()-1));
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
            for(int j=0;j<P;j++) {
                sigma += Math.sqrt(Si.get(j));
            }
            Sr.add(Math.sqrt(sigma/P));
        }

        double sigmaSum = 0.0d;
        double average = 0.0d;

        for(int i=0;i<Sr.size();i++) {
            sigmaSum += Sr.get(i);
        }

        average = sigmaSum / Sr.size();

        DBUtils.connect();
        DBUtils.insertStandardDeviation(average);

        JOptionPane.showMessageDialog(this.getFrame(), "Standard Deviation: " + average);
    }//GEN-LAST:event_jMenuItemSampleCalculateStandardDeviationActionPerformed

    private void jMenuItemViewBlackListsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemViewBlackListsActionPerformed

        this.jInternalFrameViewBlackLists.setVisible(true);

    }//GEN-LAST:event_jMenuItemViewBlackListsActionPerformed

    private void jMenuItemViewBlackListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemViewBlackListActionPerformed

        final String[] blackLists = DBUtils.getBlackList();

        final String columnName = "Black Lists";

        this.jTableLists.setModel(new AbstractTableModel() {

            public int getRowCount() {
                return blackLists.length;
            }

            public int getColumnCount() {
                return 1;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                return blackLists[rowIndex];
            }

            @Override
            public String getColumnName(int column) {
                return columnName;
            }
        });

    }//GEN-LAST:event_jMenuItemViewBlackListActionPerformed

    private void jMenuItemViewGrayListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemViewGrayListActionPerformed

        final String[] grayLists = DBUtils.getGrayList();

        final String columnName = "Gray Lists";

        this.jTableLists.setModel(new AbstractTableModel() {

            public int getRowCount() {
                return grayLists.length;
            }

            public int getColumnCount() {
                return 1;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                return grayLists[rowIndex];
            }

            @Override
            public String getColumnName(int column) {
                return columnName;
            }
        });


    }//GEN-LAST:event_jMenuItemViewGrayListActionPerformed

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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JInternalFrame jInternalFrameConsole;
    private javax.swing.JInternalFrame jInternalFramePackets;
    private javax.swing.JInternalFrame jInternalFrameViewBlackLists;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemPacketCapture;
    private javax.swing.JMenuItem jMenuItemSampleCalculateStandardDeviation;
    private javax.swing.JMenuItem jMenuItemViewBlackList;
    private javax.swing.JMenuItem jMenuItemViewBlackLists;
    private javax.swing.JMenuItem jMenuItemViewGrayList;
    private javax.swing.JPanel jPanelPackets;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTableLists;
    private javax.swing.JTextArea jTextAreaConsole;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
}
