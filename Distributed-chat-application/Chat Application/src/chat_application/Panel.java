package chat_application;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import javax.swing.JOptionPane;

public class Panel extends javax.swing.JFrame {
    
    ArrayList<String> clients  = new ArrayList<String>();
    ArrayList<Chat_Client> users = new ArrayList<Chat_Client>();
    
    int sPort = 2222;
    int serverCount = 0;    
    public BlockingQueue<String> queue;
    
    public Panel() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ta_Nodes = new javax.swing.JScrollPane();
        cp_SrvCl = new javax.swing.JTextArea();
        btnServer = new javax.swing.JButton();
        btnConnectClients = new javax.swing.JButton();
        btnSendMsg = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        cp_CurrStat = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        cp_ShowTime = new javax.swing.JTextArea();
        btnConnectUser = new javax.swing.JButton();
        btnShowTime = new javax.swing.JButton();
        label1 = new java.awt.Label();
        label2 = new java.awt.Label();
        label3 = new java.awt.Label();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Control Panel");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        cp_SrvCl.setEditable(false);
        cp_SrvCl.setColumns(20);
        cp_SrvCl.setRows(5);
        cp_SrvCl.setDisabledTextColor(new java.awt.Color(150, 150, 150));
        cp_SrvCl.setEnabled(false);
        ta_Nodes.setViewportView(cp_SrvCl);
        cp_SrvCl.getAccessibleContext().setAccessibleName("");

        btnServer.setText("Start Servers");
        btnServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartServerActionPerformed(evt);
            }
        });

        btnConnectClients.setText("Start Clients");
        btnConnectClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectClientsClientsActionPerformed(evt);
            }
        });

        btnSendMsg.setText("Send Message");
        btnSendMsg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendMsgActionPerformed(evt);
            }
        });

        cp_CurrStat.setEditable(false);
        cp_CurrStat.setColumns(20);
        cp_CurrStat.setRows(5);
        jScrollPane1.setViewportView(cp_CurrStat);

        cp_ShowTime.setEditable(false);
        cp_ShowTime.setColumns(20);
        cp_ShowTime.setRows(5);
        jScrollPane2.setViewportView(cp_ShowTime);

        btnConnectUser.setText("Connect User");
        btnConnectUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectUserActionPerformed(evt);
            }
        });

        btnShowTime.setText("Show Time");
        btnShowTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowTimeActionPerformed(evt);
            }
        });

        label1.setText("Servers and Clients");

        label2.setText("Current Status");

        label3.setText("Elapsed Time");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(97, 97, 97)
                                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnServer)
                                .addGap(37, 37, 37)
                                .addComponent(btnConnectClients)
                                .addGap(33, 33, 33)
                                .addComponent(btnConnectUser)
                                .addGap(40, 40, 40)
                                .addComponent(btnSendMsg)
                                .addGap(16, 16, 16)))
                        .addGap(24, 24, 24)
                        .addComponent(btnShowTime)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ta_Nodes, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(132, 132, 132)
                                .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnServer)
                    .addComponent(btnConnectClients)
                    .addComponent(btnConnectUser)
                    .addComponent(btnSendMsg)
                    .addComponent(btnShowTime))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(ta_Nodes, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        String stringServerCount = JOptionPane.showInputDialog("Please input the number of servers: ");
        cp_SrvCl.append("Number of servers: " + stringServerCount + "\n");
        cp_SrvCl.append("---------------------------------------------------------------------\n");

        serverCount = Integer.parseInt(stringServerCount);
        int total = 0;
        for (int i = 0; i < serverCount; i++){
            String clientCount = JOptionPane.showInputDialog("Please input the number of clients for server" + Integer.toString(i + 1));
            cp_SrvCl.append("Number of clients for server" + Integer.toString(i + 1) + ": " + clientCount + "\n");           
            clients.add(clientCount);
            total += Integer.parseInt(clientCount);
        }
        cp_SrvCl.append("---------------------------------------------------------------------\n");
        queue = new ArrayBlockingQueue<>(total + serverCount);
    }//GEN-LAST:event_formWindowOpened

    private void btnStartServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartServerActionPerformed
        for (int i = 0; i < serverCount; i++){
            Chat_Server server = new Chat_Server(sPort + i, this);
            server.StartServer();
        }
    }//GEN-LAST:event_btnStartServerActionPerformed

    private void btnConnectClientsClientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectClientsClientsActionPerformed
        for (int i = 0; i < serverCount; i++){
            int clients_num = Integer.parseInt(clients.get(i));
            for (int j = 0; j < clients_num; j++){
                Chat_Client client = new Chat_Client(sPort + i, "Client" + Integer.toString(i+1) + "-" + Integer.toString(j+1), this);
                client.Connect();
            }
        }
    }//GEN-LAST:event_btnConnectClientsClientsActionPerformed

    private void btnSendMsgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendMsgActionPerformed
        for (int i = 0; i < serverCount; i++){
            Chat_Client user = users.get(i);
            user.Send();
        }
        btnSendMsg.setEnabled(false);
    }//GEN-LAST:event_btnSendMsgActionPerformed

    private void btnConnectUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectUserActionPerformed
        users.removeAll(users);
        for (int i = 0; i < serverCount; i++){
            
            Chat_Client user = new Chat_Client(sPort + i, "User" + Integer.toString(i+1), this);
            users.add(user);
            user.Connect();  
        }
        btnConnectUser.setEnabled(false);
    }//GEN-LAST:event_btnConnectUserActionPerformed
    
    private void btnShowTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowTimeActionPerformed
        Object[] time = queue.toArray();
        int size = time.length;
        long total = 0;
        int min=100, max = 0;
        double avg = 0.0;
        
        for (int i=0; i<size; i++){
            int x = Integer.parseInt((String) time[i]);
            if (x > max)
            {
                max = x;
            }
            if (x < min)
            {
                min = x;
            }
            total += x;
        }
        avg = total / size;
        DisplayTime("Min: " + Integer.toString(min) + " ms\n");
        DisplayTime("Max: " + Integer.toString(max) + " ms\n");
        DisplayTime("Average: " + Double.toString(avg) + " ms\n");
    }//GEN-LAST:event_btnShowTimeActionPerformed
    
    public void broadProcess(String process){
        cp_CurrStat.append(process);
    }
    
    public void DisplayTime(String time){
        cp_ShowTime.append(time);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Panel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Panel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Panel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Panel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Panel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConnectClients;
    private javax.swing.JButton btnConnectUser;
    private javax.swing.JButton btnSendMsg;
    private javax.swing.JButton btnServer;
    private javax.swing.JButton btnShowTime;
    private javax.swing.JTextArea cp_CurrStat;
    private javax.swing.JTextArea cp_ShowTime;
    private javax.swing.JTextArea cp_SrvCl;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private javax.swing.JScrollPane ta_Nodes;
    // End of variables declaration//GEN-END:variables
}
