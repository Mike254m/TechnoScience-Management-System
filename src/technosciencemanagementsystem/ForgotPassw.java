/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package technosciencemanagementsystem;
//import static houserentalmanagementsystem.Login.uname;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author MICHAEL
 */
public class ForgotPassw extends javax.swing.JFrame {
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    public static String pass;
    public static String name;
    JFrame frame;

    /**
     * Creates new form ForgotPassword
     */
    public ForgotPassw() {
        initComponents();
        connect();
    }
    
      public void connect(){
       try {
           Class.forName("com.mysql.jdbc.Driver");
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms","root","");
           
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(ForgotPassw.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SQLException ex) {
            Logger.getLogger(ForgotPassw.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        phontext = new javax.swing.JTextField();
        anstxt = new javax.swing.JTextField();
        retrievebtn = new javax.swing.JButton();
        backbtn = new javax.swing.JButton();
        exitbtn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        questiontxt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "FORGOT PASSWORD", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Engravers MT", 3, 24))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(0, 102, 255));

        jLabel3.setFont(new java.awt.Font("Elephant", 1, 18)); // NOI18N
        jLabel3.setText("Phonenumber:");

        jLabel4.setFont(new java.awt.Font("Elephant", 1, 18)); // NOI18N
        jLabel4.setText("Answer to security Question:");

        phontext.setFont(new java.awt.Font("Elephant", 1, 18)); // NOI18N
        phontext.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                phontextKeyPressed(evt);
            }
        });

        anstxt.setFont(new java.awt.Font("Elephant", 1, 18)); // NOI18N

        retrievebtn.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        retrievebtn.setForeground(new java.awt.Color(102, 204, 0));
        retrievebtn.setText("Retrieve Password");
        retrievebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retrievebtnActionPerformed(evt);
            }
        });

        backbtn.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        backbtn.setForeground(new java.awt.Color(51, 102, 0));
        backbtn.setText("Back");
        backbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backbtnActionPerformed(evt);
            }
        });

        exitbtn.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        exitbtn.setForeground(new java.awt.Color(0, 0, 102));
        exitbtn.setText("Exit");
        exitbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitbtnActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Elephant", 1, 18)); // NOI18N
        jLabel5.setText("Security Question :");

        questiontxt.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 153));
        jLabel1.setText("Type the Phonenumber you were Registered with and Press Enter to retrieve security question");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(retrievebtn)
                    .addComponent(jLabel5))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(questiontxt, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(anstxt, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(phontext, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(backbtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(exitbtn)
                        .addGap(85, 85, 85))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(phontext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(questiontxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(anstxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(retrievebtn)
                    .addComponent(backbtn)
                    .addComponent(exitbtn))
                .addGap(30, 30, 30))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, 400));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitbtnActionPerformed
       frame = new JFrame();
       if(JOptionPane.showConfirmDialog(null, "Confirm You Want To Exit", "Forgot Password Module", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION){
           System.exit(0);
       }
    }//GEN-LAST:event_exitbtnActionPerformed

    private void retrievebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retrievebtnActionPerformed
       try {
            String query = "SELECT * FROM Account WHERE phonenumber=? and security_question = ? and answer = ?";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms","root", "");
            pst = con.prepareStatement(query);
            pst.setString(1, phontext.getText());
            pst.setString(2, questiontxt.getText());
            pst.setString(3, anstxt.getText());
            if (phontext.getText().equals("") || questiontxt.getText().equals("") || anstxt.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Some fields not filled in..!!\n Please Fill them to continue.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            rs = pst.executeQuery();
            if (rs.next()) {
                name = rs.getString("password");
                JOptionPane.showMessageDialog(this, "Your username is: " + rs.getString("username") + " \n And password is: " +  rs.getString("password")  + " \nPlease keep your Username Password Private!!");
                phontext.setText("");
                questiontxt.setText("");
                anstxt.setText("");
                
                this.dispose();
                LoginForm logs = new LoginForm();
                logs.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "You have Entered Wrong Answer for your Security Question\n Try Again..");
                phontext.setText("");
                questiontxt.setText("");
                anstxt.setText("");
                con.close();
            }

        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_retrievebtnActionPerformed

    private void phontextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_phontextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                String txtphone = phontext.getText();
                pst = con.prepareStatement("Select * from account where phonenumber = ?");
                pst.setString(1, txtphone);
                rs = pst.executeQuery();
                if (rs.next() == false) {
                    JOptionPane.showMessageDialog(this, "The Phonenumber You Entered is not found");
                } else {
                    String quest = rs.getString("Security_question");
                    questiontxt.setText(quest.trim());
                    anstxt.requestFocus();
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
                                    
    }//GEN-LAST:event_phontextKeyPressed

    private void backbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backbtnActionPerformed
         this.dispose();
         LoginForm log = new LoginForm();
         log.setVisible(true);
    }//GEN-LAST:event_backbtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ForgotPassw.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ForgotPassw.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ForgotPassw.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ForgotPassw.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ForgotPassw().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField anstxt;
    private javax.swing.JButton backbtn;
    private javax.swing.JButton exitbtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField phontext;
    private javax.swing.JTextField questiontxt;
    private javax.swing.JButton retrievebtn;
    // End of variables declaration//GEN-END:variables
}
