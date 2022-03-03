package technosciencemanagementsystem;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javax.swing.JOptionPane;
import java.sql.*;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Dell
 */
public class Admin_Home extends javax.swing.JFrame {
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    private JFrame frame;
    DefaultTableModel model;
    public static String phone;
    public static String fnames;
    public static String lnames;
    public static String amounts;
    public static String arrearst;
    public static String rnums;
    
    public Admin_Home() {
        initComponents();
        connect();
        load_home();
    }
 SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd"); 
    
    public void connect(){
       try {
           Class.forName("com.mysql.jdbc.Driver");
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms","root","");
           
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(Admin_Home.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SQLException ex) {
            Logger.getLogger(Admin_Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
           private void load_home(){
           displaypanel.removeAll();
           displaypanel.add(homepanel);
           homepanel.setVisible(true);
           homepanel.repaint();
           homepanel.revalidate();   
       }
    
  //The below classes are used to retrieve and Delete Data to the Database
        private void retrieveusers() {
        try {
            String sql = "SELECT user_id, names, username, phonenumber, role, password, Security_question, answer FROM account";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) usertable.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String id = rs.getString(1);
                String nam = rs.getString(2);
                String userna = rs.getString(3);
                String phonenum = rs.getString(4);
                String position = rs.getString(5);
                String passt = rs.getString(6);
                String quest = rs.getString(7);
                String answers = rs.getString(8);
                model.addRow(new String[]{id, nam, userna, phonenum, position, passt, quest, answers});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public Boolean updateusers(String id, String name, String usedn, String phns, String passt, String usrole, String userquery, String ansquest) {
        String query = "UPDATE account SET names='" + name + "', username='" + usedn + "',phonenumber='" + phns + "', role ='" + usrole + "', password ='" + passt + "', confirmpassword='" + passt + "',  Security_question='" + userquery + "', Answer='" + ansquest + "' WHERE user_id= '" + id + "'";
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(query);
            pst.execute(query);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public Boolean deleteusers(String id) {

        String sql = "DELETE FROM account WHERE user_id ='" + id + "'";
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            pst.execute(sql);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
    private void filterusers(String query) {
        DefaultTableModel model2 =  (DefaultTableModel) usertable.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model2);
        usertable.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }
    
             private void totalsysusers() {
        try {
            String sql = "SELECT COUNT(user_id)totalusrds FROM account";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
             if (rs.next()){
              int totalsyd =Integer.parseInt(rs.getString("totalusrds")) ;
              totalsystem.setText(String.valueOf(totalsyd));
           }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
         }
       private void retrieveadsmembers() {
          try {
            String sql = "SELECT * FROM members";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            model = (DefaultTableModel) advmems.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String idmt = rs.getString(1);
                String rega = rs.getString(2);
                String nam = rs.getString(3);
                String phonena = rs.getString(4);
                String crse = rs.getString(5);
                String spexw = rs.getString(6);
                String ysf = rs.getString(7);
                String datese = rs.getString(8);
                model.addRow(new String[]{idmt, rega, nam, phonena, crse, spexw,ysf, datese});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

        public Boolean updateadsmembers(String id, String rexam, String namsz, String pnax, String craz, String arxs, String yrme) {
        String query = "UPDATE members SET regno='" + rexam + "', names='" + namsz + "',phonenumbers='" + pnax + "',course='" + craz+ "', area_specialization ='"+ arxs+"', Year_semester = '" +yrme+"' WHERE members_id = '" + id + "'";
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(query);
            pst.execute(query);
            return true;
            } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

        public Boolean deleteadsmembers(String id) {
        String sql = "DELETE FROM members WHERE members_id ='" + id + "'";
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            pst.execute(sql);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
    private void filteradsmembers(String query) {
        DefaultTableModel model2 =  (DefaultTableModel) advmems.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model2);
        advmems.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }
         
        private void totaladsmembers() {
        try {
            String sql = "SELECT COUNT(members_id)totdsf FROM members";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
             if (rs.next()){
              int total =Integer.parseInt(rs.getString("totdsf")) ;
              totvads.setText(String.valueOf(total));
           }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        }
        
          private void retrieveattendsasd() {
        try {
            String sql = "SELECT * FROM members_attendance";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) admattendce.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String id = rs.getString(1);
                String regnumbera = rs.getString(2);
                String namex = rs.getString(3);
                String phoneatth = rs.getString(4);
                String coursts = rs.getString(5);
                String yrstb = rs.getString(6);
                String statzd = rs.getString(7);
                String dwq = rs.getString(8);
                
                model.addRow(new String[]{id, regnumbera, namex, phoneatth, coursts, yrstb, statzd, dwq});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
          
         private void filterartds(String query) {
        DefaultTableModel model3 =  (DefaultTableModel)admattendce.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model3);
        admattendce.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }   
      private void totalmembats() {
        try {
            String sql = "SELECT COUNT(attendance_id)attdstotal FROM members_attendance";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
             if (rs.next()){
              int total =Integer.parseInt(rs.getString("attdstotal")) ;
              totalrms.setText(String.valueOf(total));
           }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
      }
      
       private void retrievepropd() {
        try {
            String sql = "SELECT * FROM clubproperties";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            model = (DefaultTableModel) clubptable.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String id = rs.getString(1);
                String regname = rs.getString(2);
                String yname = rs.getString(3);
                String phonenumber = rs.getString(4);
                String ited = rs.getString(5);
                String deisd = rs.getString(6);
               String retdtes = rs.getString(7);
                model.addRow(new String[]{id, regname, yname, phonenumber, ited, retdtes, });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public Boolean updatepropd(String id, String fnamed, String lnamer, String pnumberd, String wtypey, String salaryh, String rtdf) {
        String query = "UPDATE clubproperties SET regno='" + fnamed + "',name='" + lnamer + "',phonenumber='" + pnumberd+ "', item_name='"+ wtypey+"', Dateissued = '" +salaryh+"', Returndate = '" + rtdf+"'WHERE property_id= '" + id + "'";
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(query);
            pst.execute(query);
            return true;
            } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
     public Boolean deletepropd(String id) {
        String sql = "DELETE FROM clubproperties WHERE property_id ='" + id + "'";
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            pst.execute(sql);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
        private void filterpropd(String query) {
        DefaultTableModel model3 =  (DefaultTableModel) clubptable.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model3);
        clubptable.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }
         private void totalpropd() {
        try {
            String sql = "SELECT COUNT(property_id)ptts FROM clubproperties";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
             if (rs.next()){
              int total =Integer.parseInt(rs.getString("ptts")) ;
              totalprts.setText(String.valueOf(total));
           }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
         }
        private void retreaveattendeventsad() {
        try {
            String query = "SELECT * FROM eventattendance";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            model = (DefaultTableModel) evrattend.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String provad_id = rs.getString(1);
                String renatda = rs.getString(2);
                String protad_nameq = rs.getString(3);
                String contt = rs.getString(4);
                String mailj = rs.getString(5);
                String evtdrea = rs.getString(6);
                String evrtdq = rs.getString(7);
                String revsr = rs.getString(8);
                model.addRow(new String[]{provad_id,renatda, protad_nameq, contt, mailj, evtdrea,evrtdq, revsr});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
         private void filterrentsad(String query) {
        DefaultTableModel model3 =  (DefaultTableModel) evrattend.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model3);
        evrattend.setRowSorter(tr);

        tr.setRowFilter(RowFilter.regexFilter(query));
    }
         
            private void totalevtsattds() {
        try {
            String sql = "SELECT COUNT(event_id)tottalevr FROM eventattendance";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
             if (rs.next()){
              int total =Integer.parseInt(rs.getString("tottalevr")) ;
              totalrtsk.setText(String.valueOf(total));
           }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
      }
         
      private void retreaveprovs() {
        try {
            String query = "SELECT * FROM service_providers";
            con = DriverManager.getConnection("jdbc:mysql://localhost/rentalmanagementsystem", "root", "");
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            model = (DefaultTableModel) providerstbl.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String prov_id = rs.getString(1);
                String prov_name = rs.getString(2);
                String cont = rs.getString(3);
                String type = rs.getString(4);
                String loc = rs.getString(5);
                model.addRow(new String[]{prov_id, prov_name, cont, type, loc});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public Boolean deleteprovs(String serv_id) {
        String sql = "DELETE FROM service_providers WHERE id='" + serv_id + "'";
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/rentalmanagementsystem", "root", "");
            pst = con.prepareStatement(sql);
            pst.execute(sql);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
    private void filterprovs(String query) {
        DefaultTableModel model9 = (DefaultTableModel) providerstbl.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model9);
        providerstbl.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }

    public Boolean updateprovs(String p_id, String pname, String pcon, String pserv, String pdress) {
        String query = "UPDATE service_providers SET company_name='" + pname + "',contacts='" + pcon + "', service_type='" + pserv + "', Address='" + pdress + "' WHERE id= '" + p_id + "'";
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/rentalmanagementsystem", "root", "");
            pst = con.prepareStatement(query);
            pst.execute(query);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
            
         private void totalotherincome() {
        try {
            String sql = "SELECT SUM(Amount)amtinc FROM incomeexpenditures WHERE type = 'Incomes' ";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
             int totalicmonies = Integer.parseInt(rs.getString("amtinc"));
      //      clinictots.setText(String.valueOf(totalicmonies));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
         }
       private void totalexpenses() {
        try {
          //  String Exp = "Expenses";
            String sql = "SELECT SUM(Amount)amter FROM incomeexpenditures WHERE Type ='Expenses' ";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                int totalmonexp = Integer.parseInt(rs.getString("amter"));
           //     baltotalexp.setText(String.valueOf(totalmonexp));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
                
                private void totalcontribution() {
        try {
            String sql = "SELECT SUM(Amount)amt FROM Finance ";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
             int totalicmer = Integer.parseInt(rs.getString("amt"));
        //    balsr.setText(String.valueOf(totalicmer));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        } 
                
                 private void totalclinicmoney() {
        try {
            String sql = "SELECT Amount, (SUM(hardware_installation)+SUM(windows_installation)+SUM(dust_cleaning) +SUM(software_updatiom))) AS Total FROM clinicevents ";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
             int totalicmer = Integer.parseInt(rs.getString("Total"));
      //      clinictots.setText(String.valueOf(totalicmer));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        }  
          
              private void retrieverentprop() {
        try {
            String sql = "SELECT * FROM rental_properties";
            con = DriverManager.getConnection("jdbc:mysql://localhost/rentalmanagementsystem", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) evrattend.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);
                String quantity = rs.getString(3);
                String price = rs.getString(4);
                model.addRow(new String[]{id, name, quantity, price});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
         }
           public Boolean updateprops(String id, String name, String quant, String prices) {
        String query = "UPDATE rental_properties SET name='" + name + "',quantity='" + quant + "',price='" + prices + "' WHERE property_id= '" + id + "'";
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/rentalmanagementsystem", "root", "");
            pst = con.prepareStatement(query);
            pst.execute(query);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public Boolean delete(String id) {

        String sql = "DELETE FROM rental_properties WHERE property_id ='" + id + "'";

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/rentalmanagementsystem", "root", "");

            pst = con.prepareStatement(sql);

            pst.execute(sql);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    private void filterprop(String query) {
        DefaultTableModel model2 =  (DefaultTableModel) evrattend.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model2);
        evrattend.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }
          private void viewadmnoticets(){
           try {
           Class.forName("com.mysql.jdbc.Driver");
           String sql ="SELECT * FROM `notices`";
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms","root","");
           pst = con.prepareStatement(sql);
           rs = pst.executeQuery();
           admviewntc.setText("***********************************************************************\n");
           admviewntc.setText(admviewntc.getText()+" TECHNO SCIENCE VIEW UPLOADED NOTICES \n");
           admviewntc.setText(admviewntc.getText()+"********************************************************\n");
           admviewntc.setText(admviewntc.getText()+"NO.    NOTICE\n");
           while (rs.next()){
           String id = rs.getString("notice_id");
           String notesify = rs.getString("noticename");
           String date = rs.getString("date_recorded");
           admviewntc.setText("\n"+admviewntc.getText()+ id+"    "+ notesify +" DATE---->> "+date+"\n");
           }
       } catch (ClassNotFoundException | SQLException ex) {
           Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
       }
          }
    
    
        
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        displaypanel = new javax.swing.JPanel();
        newusr = new javax.swing.JPanel();
        addusrlabel = new javax.swing.JLabel();
        usernamelabel = new javax.swing.JLabel();
        nameadmtxt = new javax.swing.JTextField();
        passwordlabel = new javax.swing.JLabel();
        passadtxt = new javax.swing.JPasswordField();
        btnAddUserad = new javax.swing.JButton();
        rolelabel = new javax.swing.JLabel();
        confpasslabel = new javax.swing.JLabel();
        confirmadtxt = new javax.swing.JPasswordField();
        btnClearadUser = new javax.swing.JButton();
        btnCloseadNewUser = new javax.swing.JButton();
        roleadtxt = new javax.swing.JComboBox();
        phonelabel = new javax.swing.JLabel();
        phoneadtxt = new javax.swing.JTextField();
        securitylabel = new javax.swing.JLabel();
        securityboxad = new javax.swing.JComboBox<>();
        answerlabel = new javax.swing.JLabel();
        ansadtxt = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        usernamelabel1 = new javax.swing.JLabel();
        usernameadmtxt = new javax.swing.JTextField();
        manageusr = new javax.swing.JPanel();
        retrievebtna = new javax.swing.JButton();
        lblusername = new javax.swing.JLabel();
        txtnameadmins = new javax.swing.JTextField();
        pass = new javax.swing.JLabel();
        txtpasswordads = new javax.swing.JTextField();
        btnUpdateUserInfoas = new javax.swing.JButton();
        btnDeleteUserasd = new javax.swing.JButton();
        btnCloseMaintainUsersasd = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        usertable = new javax.swing.JTable();
        num = new javax.swing.JLabel();
        txtusernameadmn = new javax.swing.JTextField();
        position = new javax.swing.JLabel();
        positiontxtads = new javax.swing.JTextField();
        security = new javax.swing.JLabel();
        ans = new javax.swing.JLabel();
        questiontxtad = new javax.swing.JTextField();
        answertxtad = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        usersrch = new javax.swing.JTextField();
        num1 = new javax.swing.JLabel();
        numbertxtads = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        totalsystem = new javax.swing.JTextField();
        Assignclubproperty = new javax.swing.JPanel();
        proddetails = new javax.swing.JLabel();
        fname = new javax.swing.JLabel();
        fanametxt = new javax.swing.JTextField();
        lname = new javax.swing.JLabel();
        lasatntxt = new javax.swing.JTextField();
        plabel = new javax.swing.JLabel();
        pnatxt = new javax.swing.JTextField();
        occlabel = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        registerbtn = new javax.swing.JButton();
        roomno = new javax.swing.JLabel();
        occtxt = new javax.swing.JComboBox<>();
        roomno1 = new javax.swing.JLabel();
        issuedat = new com.toedter.calendar.JDateChooser();
        returndat = new com.toedter.calendar.JDateChooser();
        managemembers = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        advmems = new javax.swing.JTable();
        btnGetTenants = new javax.swing.JButton();
        lblprodname = new javax.swing.JLabel();
        ftxt = new javax.swing.JTextField();
        lblavailablestock = new javax.swing.JLabel();
        ltxt = new javax.swing.JTextField();
        lblbuyingprice = new javax.swing.JLabel();
        phtxt = new javax.swing.JTextField();
        lblsellingprice = new javax.swing.JLabel();
        datetxt = new javax.swing.JTextField();
        update = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        btnClosetenant = new javax.swing.JButton();
        lblsellingprice1 = new javax.swing.JLabel();
        typetxt = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        totvads = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        adrsrch = new javax.swing.JTextField();
        lblsellingprice2 = new javax.swing.JLabel();
        ytr = new javax.swing.JTextField();
        manageclubproperties = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        clubptable = new javax.swing.JTable();
        lblEmpfname = new javax.swing.JLabel();
        lblEmplname = new javax.swing.JLabel();
        lblEmppno = new javax.swing.JLabel();
        lblEmpsal = new javax.swing.JLabel();
        reght = new javax.swing.JTextField();
        naper = new javax.swing.JTextField();
        nates = new javax.swing.JTextField();
        btnReloademp = new javax.swing.JButton();
        btnDeleteemp = new javax.swing.JButton();
        btnUpdateemp = new javax.swing.JButton();
        ytsf = new javax.swing.JTextField();
        lblEusername = new javax.swing.JLabel();
        isrdte = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        prtserchd = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        totalprts = new javax.swing.JTextField();
        btncloseemp = new javax.swing.JButton();
        lblEusername1 = new javax.swing.JLabel();
        rtrdte = new javax.swing.JTextField();
        Viewclubattendance = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        admattendce = new javax.swing.JTable();
        btnroomdata = new javax.swing.JButton();
        buttoncls = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        attsrch = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        totalrms = new javax.swing.JTextField();
        managerent = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblrent = new javax.swing.JTable();
        btndeletepay = new javax.swing.JButton();
        btnCloserange = new javax.swing.JButton();
        numrent = new javax.swing.JLabel();
        paynum = new javax.swing.JTextField();
        numrent1 = new javax.swing.JLabel();
        numrent2 = new javax.swing.JLabel();
        numrent3 = new javax.swing.JLabel();
        numrent4 = new javax.swing.JLabel();
        numrent6 = new javax.swing.JLabel();
        fpay = new javax.swing.JTextField();
        lpay = new javax.swing.JTextField();
        rpay = new javax.swing.JTextField();
        ampay = new javax.swing.JTextField();
        arrpay = new javax.swing.JTextField();
        btnretrvpay = new javax.swing.JButton();
        btnupdatepay = new javax.swing.JButton();
        numrent9 = new javax.swing.JLabel();
        searchrent = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        arar = new javax.swing.JTextField();
        amtr = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        mnthp = new javax.swing.JTextField();
        filtdtes = new com.toedter.calendar.JDateChooser();
        jLabel57 = new javax.swing.JLabel();
        btnroomdata1 = new javax.swing.JButton();
        Viewmembersconbasedtype = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        providerstbl = new javax.swing.JTable();
        btnCloseprov = new javax.swing.JButton();
        btnretrieveprov = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        providerssearch = new javax.swing.JTextField();
        btnretrieveprov1 = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        Addrooms = new javax.swing.JPanel();
        blrm = new javax.swing.JLabel();
        lbnum = new javax.swing.JLabel();
        lblstat = new javax.swing.JLabel();
        btnCloserm = new javax.swing.JButton();
        btnClearrm = new javax.swing.JButton();
        btnaddrm = new javax.swing.JButton();
        txtnum = new javax.swing.JTextField();
        lblFilldet = new javax.swing.JLabel();
        rmtype = new javax.swing.JComboBox<>();
        txtstat = new javax.swing.JComboBox<>();
        jLabel51 = new javax.swing.JLabel();
        txtnumber = new javax.swing.JTextField();
        Addemployees = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        numtext = new javax.swing.JTextField();
        lnamtxt = new javax.swing.JTextField();
        salarytxt = new javax.swing.JTextField();
        namtxt = new javax.swing.JTextField();
        typetext = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        datechooser = new com.toedter.calendar.JDateChooser();
        Paymentform = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        ntxt = new javax.swing.JTextField();
        notxt = new javax.swing.JTextField();
        ltext = new javax.swing.JTextField();
        paytxt = new javax.swing.JTextField();
        arrtxt = new javax.swing.JTextField();
        paybtn = new javax.swing.JButton();
        exitbtn = new javax.swing.JButton();
        rbtn = new javax.swing.JButton();
        receiptbtn = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        rmtyt = new javax.swing.JTextField();
        AddserviceProviders = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        comptxt = new javax.swing.JTextField();
        context = new javax.swing.JTextField();
        jScrollPane10 = new javax.swing.JScrollPane();
        address = new javax.swing.JTextArea();
        provreset = new javax.swing.JButton();
        recordprovider = new javax.swing.JButton();
        closeprov = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        servtxt = new javax.swing.JComboBox<>();
        Addrentalproperties = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        proplbl = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        propnam = new javax.swing.JTextField();
        quanam = new javax.swing.JTextField();
        pricep = new javax.swing.JTextField();
        recordprop = new javax.swing.JButton();
        clearprop = new javax.swing.JButton();
        closeprop = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        manageeventattendance = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        evrattend = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel38 = new javax.swing.JLabel();
        evrsrch = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        totalrtsk = new javax.swing.JTextField();
        jButton13 = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        filterattend = new com.toedter.calendar.JDateChooser();
        AttendanceReport = new javax.swing.JPanel();
        lblread = new javax.swing.JLabel();
        genrent = new javax.swing.JButton();
        closerent = new javax.swing.JButton();
        lblrent = new javax.swing.JLabel();
        ContributionReport = new javax.swing.JPanel();
        lblroom = new javax.swing.JLabel();
        genroom = new javax.swing.JButton();
        closeroom = new javax.swing.JButton();
        lblread1 = new javax.swing.JLabel();
        MemberReport = new javax.swing.JPanel();
        gentenant = new javax.swing.JButton();
        closetenant = new javax.swing.JButton();
        lbltenant = new javax.swing.JLabel();
        lblread2 = new javax.swing.JLabel();
        homepanel = new javax.swing.JPanel();
        softwarename = new javax.swing.JLabel();
        softwaredesc = new javax.swing.JLabel();
        softwarename1 = new javax.swing.JLabel();
        softwaredesc1 = new javax.swing.JLabel();
        writtingrrules = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        rulesarea = new javax.swing.JTextArea();
        jLabel44 = new javax.swing.JLabel();
        saveregulations = new javax.swing.JButton();
        clsrules = new javax.swing.JButton();
        saveregulations2 = new javax.swing.JButton();
        ViewRules = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        rlsarea = new javax.swing.JTextArea();
        jLabel45 = new javax.swing.JLabel();
        viewregulations = new javax.swing.JButton();
        printregulations = new javax.swing.JButton();
        closeregulations = new javax.swing.JButton();
        writeduedates = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        rentdts = new javax.swing.JTextArea();
        jLabel46 = new javax.swing.JLabel();
        saverls = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        retrievedts = new javax.swing.JButton();
        ViewCalendarActivites = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        adminclds = new javax.swing.JTextArea();
        jButton4 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jLabel47 = new javax.swing.JLabel();
        Receipt = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        recpt = new javax.swing.JTextArea();
        jButton12 = new javax.swing.JButton();
        printbutton = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        ViewComplains = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        complainsview = new javax.swing.JTextArea();
        viewcomp = new javax.swing.JButton();
        clsbtns = new javax.swing.JButton();
        jLabel48 = new javax.swing.JLabel();
        UpdateRooms = new javax.swing.JPanel();
        blrm1 = new javax.swing.JLabel();
        lbnum1 = new javax.swing.JLabel();
        lblstat1 = new javax.swing.JLabel();
        btnCloserm1 = new javax.swing.JButton();
        btnClearrm1 = new javax.swing.JButton();
        btnaddrm1 = new javax.swing.JButton();
        phup = new javax.swing.JTextField();
        lblFilldet1 = new javax.swing.JLabel();
        txtstatup = new javax.swing.JComboBox<>();
        rmupd = new javax.swing.JTextField();
        UploadNotices = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        adminnotic = new javax.swing.JTextArea();
        jLabel54 = new javax.swing.JLabel();
        saveregulations1 = new javax.swing.JButton();
        clsrules1 = new javax.swing.JButton();
        ViewNotices = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        admviewntc = new javax.swing.JTextArea();
        jLabel55 = new javax.swing.JLabel();
        savfeedb = new javax.swing.JButton();
        clsfeedbc = new javax.swing.JButton();
        Viewmemberscontyear = new javax.swing.JPanel();
        jScrollPane19 = new javax.swing.JScrollPane();
        providerstbl1 = new javax.swing.JTable();
        btnCloseprov1 = new javax.swing.JButton();
        btnretrieveprov2 = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        providerssearch1 = new javax.swing.JTextField();
        btnretrieveprov3 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        selyrs = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        ManageIncomeExp = new javax.swing.JPanel();
        retrievebtna1 = new javax.swing.JButton();
        btnCloseMaintainUsersasd1 = new javax.swing.JButton();
        jScrollPane20 = new javax.swing.JScrollPane();
        incexpds = new javax.swing.JTable();
        jLabel58 = new javax.swing.JLabel();
        searchicm = new javax.swing.JTextField();
        retrievebtna2 = new javax.swing.JButton();
        manageservices = new javax.swing.JPanel();
        insser = new javax.swing.JButton();
        lblusername1 = new javax.swing.JLabel();
        txtstr = new javax.swing.JTextField();
        btnUpdatesser = new javax.swing.JButton();
        btnDeletessert = new javax.swing.JButton();
        btnClosessert = new javax.swing.JButton();
        jScrollPane21 = new javax.swing.JScrollPane();
        eventtables = new javax.swing.JTable();
        num2 = new javax.swing.JLabel();
        txtamrd = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        cmpservices = new javax.swing.JTextField();
        retrieveser = new javax.swing.JButton();
        jLabel63 = new javax.swing.JLabel();
        totalclinc = new javax.swing.JTextField();
        btnUpdatesser1 = new javax.swing.JButton();
        loginlabel = new javax.swing.JLabel();
        myusername = new javax.swing.JLabel();
        btnLogOut = new javax.swing.JButton();
        softwareName = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        btnGroup = new javax.swing.JPanel();
        btnAddusers = new javax.swing.JButton();
        usrmgmt = new javax.swing.JLabel();
        stockmgmt = new javax.swing.JLabel();
        btnaddrentalproperties = new javax.swing.JButton();
        btnmanageusers = new javax.swing.JButton();
        btnmanagetenants = new javax.swing.JButton();
        roomsreportbtn = new javax.swing.JButton();
        lblReports2 = new javax.swing.JLabel();
        reptenant = new javax.swing.JButton();
        btnaddrentalproperties1 = new javax.swing.JButton();
        reppayment1 = new javax.swing.JButton();

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        displaypanel.setBackground(new java.awt.Color(153, 153, 153));
        displaypanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.black, java.awt.Color.gray));
        displaypanel.setPreferredSize(new java.awt.Dimension(1100, 690));
        displaypanel.setLayout(new java.awt.CardLayout());

        addusrlabel.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        addusrlabel.setForeground(new java.awt.Color(0, 102, 255));
        addusrlabel.setText("REGISTER SYSTEM USERS");

        usernamelabel.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        usernamelabel.setText("Name:");

        nameadmtxt.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N

        passwordlabel.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        passwordlabel.setText("Password:");

        passadtxt.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N

        btnAddUserad.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btnAddUserad.setForeground(new java.awt.Color(0, 51, 255));
        btnAddUserad.setText("Register");
        btnAddUserad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUseradActionPerformed(evt);
            }
        });

        rolelabel.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        rolelabel.setText("Role:");

        confpasslabel.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        confpasslabel.setText("Confirm Password:");

        confirmadtxt.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N

        btnClearadUser.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btnClearadUser.setText("Clear");
        btnClearadUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearadUserActionPerformed(evt);
            }
        });

        btnCloseadNewUser.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btnCloseadNewUser.setForeground(new java.awt.Color(51, 0, 51));
        btnCloseadNewUser.setText("Close");
        btnCloseadNewUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseadNewUserActionPerformed(evt);
            }
        });

        roleadtxt.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        roleadtxt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select User Role", "Admin", "Secretary", "Treasurer", "ProjectManager" }));
        roleadtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roleadtxtActionPerformed(evt);
            }
        });

        phonelabel.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        phonelabel.setText("PHONENUMBER:");

        phoneadtxt.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N

        securitylabel.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        securitylabel.setText("Security Question:");

        securityboxad.setFont(new java.awt.Font("Engravers MT", 0, 12)); // NOI18N
        securityboxad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose Security Question", "What is  your Favourite Pet?", "What is your Favorite Dish?", "What is your Favorite Dream Country?" }));

        answerlabel.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        answerlabel.setText("Answer:");

        ansadtxt.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 255));
        jLabel13.setText("Please fill in the fields below to register user for the system");

        usernamelabel1.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        usernamelabel1.setText("Username:");

        usernameadmtxt.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N

        javax.swing.GroupLayout newusrLayout = new javax.swing.GroupLayout(newusr);
        newusr.setLayout(newusrLayout);
        newusrLayout.setHorizontalGroup(
            newusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newusrLayout.createSequentialGroup()
                .addGroup(newusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newusrLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(newusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usernamelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(phonelabel)
                            .addComponent(usernamelabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(passwordlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(confpasslabel)
                            .addComponent(rolelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(securitylabel)
                            .addComponent(answerlabel)
                            .addComponent(btnAddUserad))
                        .addGroup(newusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(newusrLayout.createSequentialGroup()
                                .addGap(99, 99, 99)
                                .addGroup(newusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(newusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(roleadtxt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(newusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(phoneadtxt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                                            .addComponent(nameadmtxt, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(usernameadmtxt)))
                                    .addComponent(passadtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(confirmadtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(securityboxad, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ansadtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(newusrLayout.createSequentialGroup()
                                .addGap(79, 79, 79)
                                .addComponent(btnClearadUser)
                                .addGap(113, 113, 113)
                                .addComponent(btnCloseadNewUser))))
                    .addGroup(newusrLayout.createSequentialGroup()
                        .addGap(218, 218, 218)
                        .addGroup(newusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(addusrlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(321, Short.MAX_VALUE))
        );
        newusrLayout.setVerticalGroup(
            newusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newusrLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addusrlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addGap(39, 39, 39)
                .addGroup(newusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernamelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameadmtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(newusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernamelabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usernameadmtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(newusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phonelabel)
                    .addComponent(phoneadtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(newusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roleadtxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rolelabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(newusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passadtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(newusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confpasslabel)
                    .addComponent(confirmadtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(newusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(securitylabel)
                    .addComponent(securityboxad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(newusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(answerlabel)
                    .addComponent(ansadtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(newusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddUserad)
                    .addComponent(btnClearadUser)
                    .addComponent(btnCloseadNewUser))
                .addGap(211, 211, 211))
        );

        displaypanel.add(newusr, "card2");

        manageusr.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Manage System Users", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Engravers MT", 1, 24), new java.awt.Color(0, 0, 255))); // NOI18N

        retrievebtna.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        retrievebtna.setForeground(new java.awt.Color(0, 0, 255));
        retrievebtna.setText("Retrieve");
        retrievebtna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retrievebtnaActionPerformed(evt);
            }
        });

        lblusername.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblusername.setText("Name:");

        txtnameadmins.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        pass.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        pass.setText("Password:");

        btnUpdateUserInfoas.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btnUpdateUserInfoas.setForeground(new java.awt.Color(153, 0, 51));
        btnUpdateUserInfoas.setText("Update Details");
        btnUpdateUserInfoas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateUserInfoasActionPerformed(evt);
            }
        });

        btnDeleteUserasd.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btnDeleteUserasd.setForeground(new java.awt.Color(102, 51, 0));
        btnDeleteUserasd.setText("Delete");
        btnDeleteUserasd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteUserasdActionPerformed(evt);
            }
        });

        btnCloseMaintainUsersasd.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btnCloseMaintainUsersasd.setForeground(new java.awt.Color(0, 255, 51));
        btnCloseMaintainUsersasd.setText("Close");
        btnCloseMaintainUsersasd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseMaintainUsersasdActionPerformed(evt);
            }
        });

        usertable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "User ID", "Name", "Username", "Phonenumber", "Role", "Password", "SecurityQuestion", "Answer"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        usertable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usertableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(usertable);
        if (usertable.getColumnModel().getColumnCount() > 0) {
            usertable.getColumnModel().getColumn(5).setHeaderValue("Password");
            usertable.getColumnModel().getColumn(6).setHeaderValue("SecurityQuestion");
            usertable.getColumnModel().getColumn(7).setHeaderValue("Answer");
        }

        num.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        num.setText("Username:");

        position.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        position.setText("Role");

        positiontxtads.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        security.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        security.setText("Security Question:");

        ans.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        ans.setText("Answer:");

        questiontxtad.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        answertxtad.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        jLabel39.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel39.setText("Search:");

        usersrch.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        usersrch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                usersrchKeyReleased(evt);
            }
        });

        num1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        num1.setText("Phonenumber:");

        jLabel56.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 18)); // NOI18N
        jLabel56.setText("No. of Users:");

        totalsystem.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        javax.swing.GroupLayout manageusrLayout = new javax.swing.GroupLayout(manageusr);
        manageusr.setLayout(manageusrLayout);
        manageusrLayout.setHorizontalGroup(
            manageusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageusrLayout.createSequentialGroup()
                .addComponent(jLabel39)
                .addGap(60, 60, 60)
                .addComponent(usersrch, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(manageusrLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manageusrLayout.createSequentialGroup()
                        .addGroup(manageusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(num)
                            .addComponent(lblusername, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(manageusrLayout.createSequentialGroup()
                        .addGroup(manageusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(manageusrLayout.createSequentialGroup()
                                .addGroup(manageusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(manageusrLayout.createSequentialGroup()
                                        .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(manageusrLayout.createSequentialGroup()
                                        .addGroup(manageusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(security)
                                            .addGroup(manageusrLayout.createSequentialGroup()
                                                .addGap(8, 8, 8)
                                                .addComponent(ans)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(manageusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(answertxtad, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(questiontxtad, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtpasswordads, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(manageusrLayout.createSequentialGroup()
                                .addGroup(manageusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(num1)
                                    .addComponent(position))
                                .addGap(51, 51, 51)
                                .addGroup(manageusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(positiontxtads, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(numbertxtads, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtusernameadmn, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtnameadmins, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(79, 79, 79))))
            .addGroup(manageusrLayout.createSequentialGroup()
                .addGroup(manageusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manageusrLayout.createSequentialGroup()
                        .addComponent(retrievebtna)
                        .addGap(69, 69, 69)
                        .addComponent(btnUpdateUserInfoas)
                        .addGap(53, 53, 53)
                        .addComponent(btnDeleteUserasd, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67)
                        .addComponent(btnCloseMaintainUsersasd))
                    .addGroup(manageusrLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel56)
                        .addGap(101, 101, 101)
                        .addComponent(totalsystem, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        manageusrLayout.setVerticalGroup(
            manageusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageusrLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(manageusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(usersrch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(manageusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(manageusrLayout.createSequentialGroup()
                        .addGroup(manageusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblusername)
                            .addComponent(txtnameadmins, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(manageusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(num)
                            .addComponent(txtusernameadmn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(manageusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(num1)
                            .addComponent(numbertxtads, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(manageusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(position)
                            .addComponent(positiontxtads, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(manageusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pass)
                            .addComponent(txtpasswordads, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(manageusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(questiontxtad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(security))
                        .addGap(30, 30, 30)
                        .addGroup(manageusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(answertxtad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ans)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(manageusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel56)
                    .addComponent(totalsystem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(manageusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manageusrLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(manageusrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnUpdateUserInfoas)
                            .addComponent(btnDeleteUserasd)
                            .addComponent(btnCloseMaintainUsersasd)))
                    .addGroup(manageusrLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(retrievebtna)))
                .addContainerGap(219, Short.MAX_VALUE))
        );

        displaypanel.add(manageusr, "card3");

        Assignclubproperty.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "CLUB PROPERTIES ASSGNING FORM", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18), new java.awt.Color(0, 102, 102))); // NOI18N

        proddetails.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        proddetails.setForeground(new java.awt.Color(102, 51, 255));
        proddetails.setText("Enter FOLLOWING Details Below AND PRESS ENTER TO AFTER INSERTING REG NO.:");

        fname.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        fname.setText("Reg NO.:");

        fanametxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        fanametxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fanametxtKeyPressed(evt);
            }
        });

        lname.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lname.setText("Name:");

        lasatntxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        plabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        plabel.setText("Phonenumber:");

        pnatxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        occlabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        occlabel.setText("Item Name:");

        btnClose.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btnClose.setForeground(new java.awt.Color(0, 51, 0));
        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnClear.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btnClear.setForeground(new java.awt.Color(153, 51, 255));
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        registerbtn.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        registerbtn.setForeground(new java.awt.Color(102, 153, 0));
        registerbtn.setText("Register");
        registerbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerbtnActionPerformed(evt);
            }
        });

        roomno.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        roomno.setText("Date Issued:");

        occtxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        occtxt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose property to Assign", "Projector", "Network Switch", "Copper Wires", "Extension", "Ethernet Cables" }));

        roomno1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        roomno1.setText("Return Date:");

        issuedat.setDateFormatString("dd - MM - YYYY");

        returndat.setDateFormatString("dd - MM - YYYY");

        javax.swing.GroupLayout AssignclubpropertyLayout = new javax.swing.GroupLayout(Assignclubproperty);
        Assignclubproperty.setLayout(AssignclubpropertyLayout);
        AssignclubpropertyLayout.setHorizontalGroup(
            AssignclubpropertyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AssignclubpropertyLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(registerbtn)
                .addGap(54, 54, 54)
                .addComponent(btnClear)
                .addGap(66, 66, 66)
                .addComponent(btnClose)
                .addContainerGap(434, Short.MAX_VALUE))
            .addGroup(AssignclubpropertyLayout.createSequentialGroup()
                .addGroup(AssignclubpropertyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AssignclubpropertyLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(proddetails))
                    .addGroup(AssignclubpropertyLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(AssignclubpropertyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(plabel, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lname, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fname, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(occlabel)
                            .addComponent(roomno, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(roomno1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(80, 80, 80)
                        .addGroup(AssignclubpropertyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(fanametxt, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                            .addComponent(lasatntxt, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                            .addComponent(pnatxt, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                            .addComponent(occtxt, 0, 247, Short.MAX_VALUE)
                            .addComponent(issuedat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(returndat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        AssignclubpropertyLayout.setVerticalGroup(
            AssignclubpropertyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AssignclubpropertyLayout.createSequentialGroup()
                .addComponent(proddetails, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AssignclubpropertyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fname)
                    .addComponent(fanametxt, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AssignclubpropertyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lname, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lasatntxt, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AssignclubpropertyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(plabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnatxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AssignclubpropertyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(occtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(occlabel, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AssignclubpropertyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roomno)
                    .addComponent(issuedat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AssignclubpropertyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roomno1)
                    .addComponent(returndat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(AssignclubpropertyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(registerbtn)
                    .addComponent(btnClear)
                    .addComponent(btnClose))
                .addGap(0, 365, Short.MAX_VALUE))
        );

        displaypanel.add(Assignclubproperty, "card5");

        managemembers.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "MANAGE TECHNOSCIENCE MEMBERS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N

        advmems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Members ID", "Reg NO.", "Names", "PhoneNumber", "Course", "Spec. Area", "Year_Sem"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        advmems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                advmemsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(advmems);

        btnGetTenants.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnGetTenants.setForeground(new java.awt.Color(102, 0, 255));
        btnGetTenants.setText("Retrieve Data");
        btnGetTenants.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetTenantsActionPerformed(evt);
            }
        });

        lblprodname.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblprodname.setText("REG NO.");

        ftxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        lblavailablestock.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblavailablestock.setText("Names:");

        ltxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        lblbuyingprice.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblbuyingprice.setText("PhoneNumber:");

        phtxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        lblsellingprice.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblsellingprice.setText("Course:");

        datetxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        update.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        update.setForeground(new java.awt.Color(51, 51, 0));
        update.setText("Update Details");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });

        delete.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        delete.setText("Delete");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        btnClosetenant.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnClosetenant.setForeground(new java.awt.Color(0, 204, 51));
        btnClosetenant.setText("Close");
        btnClosetenant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClosetenantActionPerformed(evt);
            }
        });

        lblsellingprice1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblsellingprice1.setText("Spez area:");

        typetxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jLabel42.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(0, 102, 102));
        jLabel42.setText("Total members:");

        totvads.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel43.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(102, 51, 255));
        jLabel43.setText("Search:");

        adrsrch.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        adrsrch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                adrsrchKeyReleased(evt);
            }
        });

        lblsellingprice2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblsellingprice2.setText("Year $ Sem:");

        ytr.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        javax.swing.GroupLayout managemembersLayout = new javax.swing.GroupLayout(managemembers);
        managemembers.setLayout(managemembersLayout);
        managemembersLayout.setHorizontalGroup(
            managemembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(managemembersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(managemembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblavailablestock)
                    .addComponent(lblprodname, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblbuyingprice)
                    .addComponent(lblsellingprice)
                    .addComponent(lblsellingprice1)
                    .addComponent(lblsellingprice2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(managemembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(managemembersLayout.createSequentialGroup()
                        .addGroup(managemembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(typetxt, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                            .addComponent(datetxt, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(phtxt, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ltxt, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ftxt, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addContainerGap(104, Short.MAX_VALUE))
                    .addGroup(managemembersLayout.createSequentialGroup()
                        .addComponent(ytr, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                        .addGap(57, 57, 57))))
            .addGroup(managemembersLayout.createSequentialGroup()
                .addGroup(managemembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(managemembersLayout.createSequentialGroup()
                        .addGroup(managemembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42)
                            .addComponent(btnGetTenants))
                        .addGap(40, 40, 40)
                        .addGroup(managemembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(managemembersLayout.createSequentialGroup()
                                .addComponent(update)
                                .addGap(43, 43, 43)
                                .addComponent(delete)
                                .addGap(84, 84, 84)
                                .addComponent(btnClosetenant))
                            .addComponent(totvads, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(managemembersLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel43)
                        .addGap(102, 102, 102)
                        .addComponent(adrsrch, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(215, Short.MAX_VALUE))
        );
        managemembersLayout.setVerticalGroup(
            managemembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, managemembersLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(managemembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(adrsrch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(managemembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(managemembersLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(managemembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel42)
                            .addComponent(totvads, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(managemembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGetTenants)
                            .addComponent(update)
                            .addComponent(delete)
                            .addComponent(btnClosetenant)))
                    .addGroup(managemembersLayout.createSequentialGroup()
                        .addGroup(managemembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblprodname)
                            .addComponent(ftxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(managemembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblavailablestock)
                            .addComponent(ltxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(managemembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblbuyingprice)
                            .addComponent(phtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(managemembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblsellingprice)
                            .addComponent(datetxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(managemembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblsellingprice1)
                            .addComponent(typetxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(managemembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblsellingprice2)
                            .addComponent(ytr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        displaypanel.add(managemembers, "card6");

        manageclubproperties.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.MatteBorder(null), "MANAGE CLUB PROPERTIES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18), new java.awt.Color(102, 0, 204))); // NOI18N
        manageclubproperties.setPreferredSize(new java.awt.Dimension(520, 0));

        clubptable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Property ID", "Reg NO.", "Name", "Phonenumber", "Item Name", "Issue Date", "Return Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        clubptable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clubptableMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(clubptable);

        lblEmpfname.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblEmpfname.setText("Reg NO.:");

        lblEmplname.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblEmplname.setText("Name:");

        lblEmppno.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblEmppno.setText("Year $ Sem:");

        lblEmpsal.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblEmpsal.setText("Item Name:");

        btnReloademp.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnReloademp.setForeground(new java.awt.Color(255, 102, 204));
        btnReloademp.setText("Retrieve Data");
        btnReloademp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadempActionPerformed(evt);
            }
        });

        btnDeleteemp.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnDeleteemp.setForeground(new java.awt.Color(102, 102, 255));
        btnDeleteemp.setText("Delete Employee");
        btnDeleteemp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteempActionPerformed(evt);
            }
        });

        btnUpdateemp.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnUpdateemp.setText("Update Details");
        btnUpdateemp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateempActionPerformed(evt);
            }
        });

        lblEusername.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblEusername.setText("Issue Date:");

        jLabel22.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        jLabel22.setText("search");

        prtserchd.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        prtserchd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                prtserchdKeyReleased(evt);
            }
        });

        jLabel40.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(0, 153, 255));
        jLabel40.setText("Total Properties:");

        totalprts.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        btncloseemp.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btncloseemp.setForeground(new java.awt.Color(0, 0, 102));
        btncloseemp.setText("close");
        btncloseemp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncloseempActionPerformed(evt);
            }
        });

        lblEusername1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblEusername1.setText("Return Date:");

        javax.swing.GroupLayout manageclubpropertiesLayout = new javax.swing.GroupLayout(manageclubproperties);
        manageclubproperties.setLayout(manageclubpropertiesLayout);
        manageclubpropertiesLayout.setHorizontalGroup(
            manageclubpropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageclubpropertiesLayout.createSequentialGroup()
                .addGroup(manageclubpropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manageclubpropertiesLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel22)
                        .addGap(54, 54, 54)
                        .addComponent(prtserchd, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(manageclubpropertiesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 577, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(manageclubpropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEmplname)
                            .addComponent(lblEmppno)
                            .addComponent(lblEmpsal)
                            .addComponent(lblEusername)
                            .addComponent(lblEmpfname)
                            .addComponent(lblEusername1))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(manageclubpropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(reght, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                    .addComponent(naper)
                    .addComponent(ytsf)
                    .addComponent(nates)
                    .addComponent(isrdte)
                    .addComponent(rtrdte))
                .addGap(29, 29, 29))
            .addGroup(manageclubpropertiesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(manageclubpropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manageclubpropertiesLayout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addGap(40, 40, 40)
                        .addComponent(totalprts, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(manageclubpropertiesLayout.createSequentialGroup()
                        .addComponent(btnReloademp)
                        .addGap(26, 26, 26)
                        .addComponent(btnUpdateemp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDeleteemp)
                        .addGap(18, 18, 18)
                        .addComponent(btncloseemp)))
                .addContainerGap(210, Short.MAX_VALUE))
        );
        manageclubpropertiesLayout.setVerticalGroup(
            manageclubpropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageclubpropertiesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(manageclubpropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(prtserchd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(manageclubpropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(manageclubpropertiesLayout.createSequentialGroup()
                        .addGroup(manageclubpropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEmpfname)
                            .addComponent(reght, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(manageclubpropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(naper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEmplname))
                        .addGap(18, 18, 18)
                        .addGroup(manageclubpropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ytsf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEmppno))
                        .addGap(27, 27, 27)
                        .addGroup(manageclubpropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEmpsal)
                            .addComponent(nates, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(manageclubpropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEusername)
                            .addComponent(isrdte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(manageclubpropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEusername1)
                            .addComponent(rtrdte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(manageclubpropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(totalprts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(manageclubpropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReloademp)
                    .addComponent(btnUpdateemp)
                    .addComponent(btnDeleteemp)
                    .addComponent(btncloseemp))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        displaypanel.add(manageclubproperties, "card8");

        Viewclubattendance.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "VIEW CLUB ATTENDANCE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18), new java.awt.Color(153, 153, 0))); // NOI18N
        Viewclubattendance.setPreferredSize(new java.awt.Dimension(525, 0));

        admattendce.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Attendance ID", "Reg NO.", "Name", "Phonenumber", "Course", "Year $ Seml", "Status", "Date Time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        admattendce.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                admattendceMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(admattendce);

        btnroomdata.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnroomdata.setText("Retrieve Data");
        btnroomdata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnroomdataActionPerformed(evt);
            }
        });

        buttoncls.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        buttoncls.setText("Close");
        buttoncls.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonclsActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Yu Gothic Medium", 1, 18)); // NOI18N
        jLabel27.setText("SEARCH");

        attsrch.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        attsrch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                attsrchKeyReleased(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 51, 255));
        jLabel41.setText("Total ATTENDANCE:");

        totalrms.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        managerent.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "MANAGE PAYMENT RECORDS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N
        managerent.setPreferredSize(new java.awt.Dimension(520, 0));
        managerent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblrent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Rent ID", "Phonenumber", "Firstname", "Lastname", "Room no.", "Amount Paid", "Arrears", "Month Paid"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblrent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblrentMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tblrent);

        managerent.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 54, 587, 315));

        btndeletepay.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btndeletepay.setText("delete Data");
        btndeletepay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeletepayActionPerformed(evt);
            }
        });
        managerent.add(btndeletepay, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 460, -1, -1));

        btnCloserange.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnCloserange.setText("Close");
        btnCloserange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloserangeActionPerformed(evt);
            }
        });
        managerent.add(btnCloserange, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 460, -1, -1));

        numrent.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        numrent.setText("Firstname:");
        managerent.add(numrent, new org.netbeans.lib.awtextra.AbsoluteConstraints(598, 106, -1, -1));

        paynum.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        managerent.add(paynum, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 60, 170, 20));

        numrent1.setFont(new java.awt.Font("Yu Gothic Medium", 1, 14)); // NOI18N
        numrent1.setText("Search:");
        managerent.add(numrent1, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 28, -1, -1));

        numrent2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        numrent2.setText("Room Number:");
        managerent.add(numrent2, new org.netbeans.lib.awtextra.AbsoluteConstraints(598, 204, -1, -1));

        numrent3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        numrent3.setText("Lastname:");
        managerent.add(numrent3, new org.netbeans.lib.awtextra.AbsoluteConstraints(598, 153, -1, -1));

        numrent4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        numrent4.setText("Amount Paid:");
        managerent.add(numrent4, new org.netbeans.lib.awtextra.AbsoluteConstraints(598, 250, -1, -1));

        numrent6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        numrent6.setText("Arrears:");
        managerent.add(numrent6, new org.netbeans.lib.awtextra.AbsoluteConstraints(598, 291, -1, -1));

        fpay.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        managerent.add(fpay, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 100, 170, -1));

        lpay.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        managerent.add(lpay, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 150, 170, -1));

        rpay.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        managerent.add(rpay, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 200, 170, -1));

        ampay.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        managerent.add(ampay, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 250, 170, -1));

        arrpay.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        managerent.add(arrpay, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 290, 170, -1));

        btnretrvpay.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnretrvpay.setText("Retrieve Data");
        btnretrvpay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnretrvpayActionPerformed(evt);
            }
        });
        managerent.add(btnretrvpay, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, -1, -1));

        btnupdatepay.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnupdatepay.setText("update Data");
        btnupdatepay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdatepayActionPerformed(evt);
            }
        });
        managerent.add(btnupdatepay, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 460, -1, -1));

        numrent9.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        numrent9.setText("Phonenumber:");
        managerent.add(numrent9, new org.netbeans.lib.awtextra.AbsoluteConstraints(598, 60, -1, -1));

        searchrent.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        searchrent.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchrentKeyReleased(evt);
            }
        });
        managerent.add(searchrent, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 25, 270, -1));

        jLabel49.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        jLabel49.setText("Total arrears:");
        managerent.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, -1, -1));

        jLabel50.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        jLabel50.setText("Total amount paid:");
        managerent.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, -1, -1));

        arar.setFont(new java.awt.Font("Elephant", 1, 14)); // NOI18N
        managerent.add(arar, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 430, 200, -1));

        amtr.setFont(new java.awt.Font("Elephant", 1, 14)); // NOI18N
        managerent.add(amtr, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 390, 200, -1));

        jLabel53.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel53.setText("Month Paid:");
        managerent.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 340, -1, -1));

        mnthp.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        managerent.add(mnthp, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 330, 170, -1));

        filtdtes.setDateFormatString("dd- MM - YYYY");

        jLabel57.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel57.setText("Filter by:");

        btnroomdata1.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnroomdata1.setText("Print table");
        btnroomdata1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnroomdata1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ViewclubattendanceLayout = new javax.swing.GroupLayout(Viewclubattendance);
        Viewclubattendance.setLayout(ViewclubattendanceLayout);
        ViewclubattendanceLayout.setHorizontalGroup(
            ViewclubattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewclubattendanceLayout.createSequentialGroup()
                .addComponent(jLabel27)
                .addGap(42, 42, 42)
                .addComponent(attsrch, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel57)
                .addGap(61, 61, 61)
                .addComponent(filtdtes, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(ViewclubattendanceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ViewclubattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ViewclubattendanceLayout.createSequentialGroup()
                        .addComponent(jScrollPane7)
                        .addContainerGap())
                    .addGroup(ViewclubattendanceLayout.createSequentialGroup()
                        .addGroup(ViewclubattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ViewclubattendanceLayout.createSequentialGroup()
                                .addComponent(jLabel41)
                                .addGap(43, 43, 43)
                                .addComponent(totalrms, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ViewclubattendanceLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(btnroomdata)
                                .addGap(107, 107, 107)
                                .addComponent(btnroomdata1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 176, Short.MAX_VALUE)
                        .addComponent(buttoncls)
                        .addGap(204, 204, 204))))
            .addGroup(ViewclubattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ViewclubattendanceLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(managerent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        ViewclubattendanceLayout.setVerticalGroup(
            ViewclubattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewclubattendanceLayout.createSequentialGroup()
                .addGroup(ViewclubattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ViewclubattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel27)
                        .addComponent(attsrch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel57))
                    .addComponent(filtdtes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(ViewclubattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel41)
                    .addComponent(totalrms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ViewclubattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttoncls)
                    .addComponent(btnroomdata)
                    .addComponent(btnroomdata1))
                .addContainerGap(299, Short.MAX_VALUE))
            .addGroup(ViewclubattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ViewclubattendanceLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(managerent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        displaypanel.add(Viewclubattendance, "card10");

        Viewmembersconbasedtype.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "VIEW MEMBERS CONTRIBUTION BASED ON TYPE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N
        Viewmembersconbasedtype.setPreferredSize(new java.awt.Dimension(525, 0));

        providerstbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Finance ID", "Reg NO.", "Name", "Phonenumber", "Year $ Sem", "Purpose", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        providerstbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                providerstblMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(providerstbl);

        btnCloseprov.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnCloseprov.setText("Close");
        btnCloseprov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseprovActionPerformed(evt);
            }
        });

        btnretrieveprov.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnretrieveprov.setText("Retrieve Data");
        btnretrieveprov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnretrieveprovActionPerformed(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel32.setText("SEARCH:");

        providerssearch.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        providerssearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                providerssearchKeyReleased(evt);
            }
        });

        btnretrieveprov1.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnretrieveprov1.setText("print data");
        btnretrieveprov1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnretrieveprov1ActionPerformed(evt);
            }
        });

        jLabel24.setText("Filter by Type:");

        jComboBox1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Type", "registration", "subscription" }));

        jLabel25.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel25.setText("Total Members:");

        jTextField1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        javax.swing.GroupLayout ViewmembersconbasedtypeLayout = new javax.swing.GroupLayout(Viewmembersconbasedtype);
        Viewmembersconbasedtype.setLayout(ViewmembersconbasedtypeLayout);
        ViewmembersconbasedtypeLayout.setHorizontalGroup(
            ViewmembersconbasedtypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewmembersconbasedtypeLayout.createSequentialGroup()
                .addGroup(ViewmembersconbasedtypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9)
                    .addGroup(ViewmembersconbasedtypeLayout.createSequentialGroup()
                        .addGroup(ViewmembersconbasedtypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ViewmembersconbasedtypeLayout.createSequentialGroup()
                                .addComponent(btnretrieveprov)
                                .addGap(135, 135, 135)
                                .addComponent(btnretrieveprov1)
                                .addGap(196, 196, 196)
                                .addComponent(btnCloseprov))
                            .addGroup(ViewmembersconbasedtypeLayout.createSequentialGroup()
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(63, 63, 63)
                                .addComponent(providerssearch, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(98, 98, 98)
                                .addComponent(jLabel24)
                                .addGap(35, 35, 35)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(ViewmembersconbasedtypeLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel25)
                .addGap(234, 234, 234)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ViewmembersconbasedtypeLayout.setVerticalGroup(
            ViewmembersconbasedtypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ViewmembersconbasedtypeLayout.createSequentialGroup()
                .addGroup(ViewmembersconbasedtypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(providerssearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(ViewmembersconbasedtypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(ViewmembersconbasedtypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnretrieveprov)
                    .addComponent(btnretrieveprov1)
                    .addComponent(btnCloseprov))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        displaypanel.add(Viewmembersconbasedtype, "card12");

        Addrooms.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ADD ROOMS FORM", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N
        Addrooms.setPreferredSize(new java.awt.Dimension(667, 400));

        blrm.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        blrm.setText("Room Type:");

        lbnum.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbnum.setText("Block Number:");

        lblstat.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblstat.setText("Status:");

        btnCloserm.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnCloserm.setText("Close");
        btnCloserm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClosermActionPerformed(evt);
            }
        });

        btnClearrm.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnClearrm.setForeground(new java.awt.Color(0, 51, 51));
        btnClearrm.setText("Clear");
        btnClearrm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearrmActionPerformed(evt);
            }
        });

        btnaddrm.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnaddrm.setForeground(new java.awt.Color(0, 102, 204));
        btnaddrm.setText("add room");
        btnaddrm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddrmActionPerformed(evt);
            }
        });

        txtnum.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        lblFilldet.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        lblFilldet.setForeground(new java.awt.Color(0, 102, 102));
        lblFilldet.setText("Please Fill in Rooms  details Below:");

        rmtype.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        rmtype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose Room Type", "Single", "Self Contained", "Bedsitter", "One Bedroom", " " }));

        txtstat.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txtstat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose Room Status", "Occupied", "Not Occupied", " " }));
        txtstat.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                txtstatItemStateChanged(evt);
            }
        });

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel51.setText("Occupant Phonenumber:");

        txtnumber.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        javax.swing.GroupLayout AddroomsLayout = new javax.swing.GroupLayout(Addrooms);
        Addrooms.setLayout(AddroomsLayout);
        AddroomsLayout.setHorizontalGroup(
            AddroomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddroomsLayout.createSequentialGroup()
                .addGroup(AddroomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddroomsLayout.createSequentialGroup()
                        .addGroup(AddroomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(blrm)
                            .addComponent(jLabel51)
                            .addComponent(lblstat))
                        .addGap(110, 110, 110)
                        .addGroup(AddroomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(rmtype, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtnum, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                            .addComponent(txtstat, 0, 267, Short.MAX_VALUE)
                            .addComponent(txtnumber)))
                    .addComponent(lbnum)
                    .addGroup(AddroomsLayout.createSequentialGroup()
                        .addComponent(btnaddrm)
                        .addGap(69, 69, 69)
                        .addComponent(btnClearrm)
                        .addGap(92, 92, 92)
                        .addComponent(btnCloserm))
                    .addGroup(AddroomsLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(lblFilldet, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(408, Short.MAX_VALUE))
        );
        AddroomsLayout.setVerticalGroup(
            AddroomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddroomsLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(lblFilldet)
                .addGap(18, 18, 18)
                .addGroup(AddroomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(blrm)
                    .addComponent(rmtype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(AddroomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbnum)
                    .addComponent(txtnum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(AddroomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtstat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblstat))
                .addGap(47, 47, 47)
                .addGroup(AddroomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel51)
                    .addComponent(txtnumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(AddroomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnaddrm)
                    .addComponent(btnClearrm)
                    .addComponent(btnCloserm))
                .addContainerGap(354, Short.MAX_VALUE))
        );

        displaypanel.add(Addrooms, "card13");

        Addemployees.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "ADD EMPLOYEES FORM", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Perpetua Titling MT", 1, 18))); // NOI18N
        Addemployees.setPreferredSize(new java.awt.Dimension(609, 390));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setText("Firstname:");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("Lastname:");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setText("Work Type:");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setText("PhoneNumber:");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setText("Monthly Salary:");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel7.setText("Date Employed:");

        numtext.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        lnamtxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        salarytxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        namtxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        typetext.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        typetext.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose Work type", "Cleaner", "Security Guard", "CareTaker", "" }));

        jButton1.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jButton1.setText("clear");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jButton2.setText("add record");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jButton3.setText("close");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 0, 255));
        jLabel8.setText("Please Fill in the Following Employees Details:");

        javax.swing.GroupLayout AddemployeesLayout = new javax.swing.GroupLayout(Addemployees);
        Addemployees.setLayout(AddemployeesLayout);
        AddemployeesLayout.setHorizontalGroup(
            AddemployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddemployeesLayout.createSequentialGroup()
                .addGroup(AddemployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddemployeesLayout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(jLabel8))
                    .addComponent(jLabel5)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addGroup(AddemployeesLayout.createSequentialGroup()
                        .addGroup(AddemployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel7))
                        .addGap(91, 91, 91)
                        .addGroup(AddemployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(namtxt, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                            .addComponent(lnamtxt, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                            .addComponent(numtext, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                            .addComponent(typetext, 0, 237, Short.MAX_VALUE)
                            .addComponent(salarytxt, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                            .addComponent(datechooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(AddemployeesLayout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(63, 63, 63)
                        .addComponent(jButton1)
                        .addGap(49, 49, 49)
                        .addComponent(jButton3)))
                .addContainerGap(497, Short.MAX_VALUE))
        );
        AddemployeesLayout.setVerticalGroup(
            AddemployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddemployeesLayout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(12, 12, 12)
                .addGroup(AddemployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(namtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(AddemployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lnamtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddemployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(numtext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(AddemployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(typetext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(AddemployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(salarytxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(AddemployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(datechooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddemployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1)
                    .addComponent(jButton3))
                .addContainerGap(326, Short.MAX_VALUE))
        );

        displaypanel.add(Addemployees, "card13");

        Paymentform.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "RECORD PAYMENT DETAILS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel9.setText("PhoneNumber:");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel10.setText("Lastname:");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel11.setText("Firstname:");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel12.setText("Amount Paid:");

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel14.setText("Arrears:");

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel15.setText("Room Number:");

        ntxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        notxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        notxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                notxtKeyPressed(evt);
            }
        });

        ltext.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        paytxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        arrtxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        paybtn.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        paybtn.setForeground(new java.awt.Color(102, 0, 255));
        paybtn.setText("Add Payment");
        paybtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paybtnActionPerformed(evt);
            }
        });

        exitbtn.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        exitbtn.setForeground(new java.awt.Color(0, 51, 51));
        exitbtn.setText("Close");
        exitbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitbtnActionPerformed(evt);
            }
        });

        rbtn.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        rbtn.setForeground(new java.awt.Color(0, 204, 153));
        rbtn.setText("Reset");
        rbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnActionPerformed(evt);
            }
        });

        receiptbtn.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        receiptbtn.setForeground(new java.awt.Color(0, 51, 153));
        receiptbtn.setText("Print receipt");
        receiptbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                receiptbtnActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(204, 102, 0));
        jLabel16.setText("Please fill  in Rent Payment details below:");

        rmtyt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        javax.swing.GroupLayout PaymentformLayout = new javax.swing.GroupLayout(Paymentform);
        Paymentform.setLayout(PaymentformLayout);
        PaymentformLayout.setHorizontalGroup(
            PaymentformLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PaymentformLayout.createSequentialGroup()
                .addGroup(PaymentformLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PaymentformLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel16))
                    .addGroup(PaymentformLayout.createSequentialGroup()
                        .addComponent(paybtn)
                        .addGap(42, 42, 42)
                        .addComponent(rbtn)
                        .addGap(35, 35, 35)
                        .addComponent(receiptbtn)
                        .addGap(29, 29, 29)
                        .addComponent(exitbtn))
                    .addGroup(PaymentformLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(PaymentformLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10)
                            .addGroup(PaymentformLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PaymentformLayout.createSequentialGroup()
                                    .addComponent(jLabel14)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(arrtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PaymentformLayout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addGap(178, 178, 178)
                                    .addGroup(PaymentformLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(ntxt, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(notxt, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(ltext, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(PaymentformLayout.createSequentialGroup()
                                    .addGroup(PaymentformLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel12)
                                        .addComponent(jLabel15))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(PaymentformLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(paytxt, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                                        .addComponent(rmtyt)))))))
                .addContainerGap(316, Short.MAX_VALUE))
        );
        PaymentformLayout.setVerticalGroup(
            PaymentformLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PaymentformLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addGap(23, 23, 23)
                .addGroup(PaymentformLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(notxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PaymentformLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(ntxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PaymentformLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(ltext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(PaymentformLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15)
                    .addComponent(rmtyt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PaymentformLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(paytxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(PaymentformLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(arrtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(PaymentformLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(paybtn)
                    .addComponent(rbtn)
                    .addComponent(receiptbtn)
                    .addComponent(exitbtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        displaypanel.add(Paymentform, "card14");

        AddserviceProviders.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.MatteBorder(null), "RECORD LOCAL SERVICE PROVIDER FORM", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N
        AddserviceProviders.setPreferredSize(new java.awt.Dimension(669, 350));

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel17.setText("Company Name:");

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel18.setText("Company Contact:");

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel19.setText("Service Offered:");

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel20.setText("Address");

        comptxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        context.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        address.setColumns(20);
        address.setRows(5);
        jScrollPane10.setViewportView(address);

        provreset.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        provreset.setText("Reset");
        provreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                provresetActionPerformed(evt);
            }
        });

        recordprovider.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        recordprovider.setText("Add Record");
        recordprovider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recordproviderActionPerformed(evt);
            }
        });

        closeprov.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        closeprov.setText("Close");
        closeprov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeprovActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(102, 153, 0));
        jLabel21.setText("Please Fill in Details of Local Service providers below:");

        servtxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        servtxt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose the Service Type", "Gas Delivery", "WIFI", "Transport Services", "Electricty Services", "Egg Supplier", "Dry Cleaner", "Jumia Agent", "Kopia Agent" }));

        javax.swing.GroupLayout AddserviceProvidersLayout = new javax.swing.GroupLayout(AddserviceProviders);
        AddserviceProviders.setLayout(AddserviceProvidersLayout);
        AddserviceProvidersLayout.setHorizontalGroup(
            AddserviceProvidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddserviceProvidersLayout.createSequentialGroup()
                .addGroup(AddserviceProvidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddserviceProvidersLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(AddserviceProvidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AddserviceProvidersLayout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(137, 137, 137)
                                .addGroup(AddserviceProvidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(context, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                                    .addComponent(comptxt, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                                    .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                                    .addComponent(servtxt, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jLabel20)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19)))
                    .addGroup(AddserviceProvidersLayout.createSequentialGroup()
                        .addComponent(recordprovider)
                        .addGap(57, 57, 57)
                        .addComponent(provreset)
                        .addGap(93, 93, 93)
                        .addComponent(closeprov))
                    .addGroup(AddserviceProvidersLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel21)))
                .addContainerGap(373, Short.MAX_VALUE))
        );
        AddserviceProvidersLayout.setVerticalGroup(
            AddserviceProvidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddserviceProvidersLayout.createSequentialGroup()
                .addComponent(jLabel21)
                .addGap(18, 18, 18)
                .addGroup(AddserviceProvidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(comptxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(AddserviceProvidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(context, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(AddserviceProvidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(servtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(AddserviceProvidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddserviceProvidersLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel20))
                    .addGroup(AddserviceProvidersLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AddserviceProvidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddserviceProvidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(recordprovider)
                        .addComponent(closeprov))
                    .addComponent(provreset))
                .addContainerGap(368, Short.MAX_VALUE))
        );

        displaypanel.add(AddserviceProviders, "card15");

        Addrentalproperties.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.MatteBorder(null), "RECORD RENTAL PROPERTIES FORM", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N
        Addrentalproperties.setPreferredSize(new java.awt.Dimension(593, 230));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setText("Price:");

        proplbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        proplbl.setText("Property name:");

        jLabel34.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel34.setText("Quantity:");

        propnam.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        quanam.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        pricep.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        recordprop.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        recordprop.setText("add record");
        recordprop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recordpropActionPerformed(evt);
            }
        });

        clearprop.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        clearprop.setText("cleAR");
        clearprop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearpropActionPerformed(evt);
            }
        });

        closeprop.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        closeprop.setText("close");
        closeprop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closepropActionPerformed(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(102, 102, 0));
        jLabel33.setText("PLEASE FILL IN RENTAL PROPERTIES DETAILS");

        javax.swing.GroupLayout AddrentalpropertiesLayout = new javax.swing.GroupLayout(Addrentalproperties);
        Addrentalproperties.setLayout(AddrentalpropertiesLayout);
        AddrentalpropertiesLayout.setHorizontalGroup(
            AddrentalpropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddrentalpropertiesLayout.createSequentialGroup()
                .addGroup(AddrentalpropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddrentalpropertiesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AddrentalpropertiesLayout.createSequentialGroup()
                        .addGroup(AddrentalpropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(proplbl, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(94, 94, 94)
                        .addGroup(AddrentalpropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pricep, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(quanam, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(propnam, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(AddrentalpropertiesLayout.createSequentialGroup()
                        .addComponent(recordprop)
                        .addGap(66, 66, 66)
                        .addComponent(clearprop)
                        .addGap(41, 41, 41)
                        .addComponent(closeprop)))
                .addContainerGap(454, Short.MAX_VALUE))
        );
        AddrentalpropertiesLayout.setVerticalGroup(
            AddrentalpropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddrentalpropertiesLayout.createSequentialGroup()
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AddrentalpropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proplbl, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(propnam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(AddrentalpropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quanam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddrentalpropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pricep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddrentalpropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(recordprop)
                    .addComponent(clearprop)
                    .addComponent(closeprop))
                .addContainerGap(409, Short.MAX_VALUE))
        );

        displaypanel.add(Addrentalproperties, "card15");

        manageeventattendance.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.MatteBorder(null), "MANAGE EVENT ATTENDANCE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N

        evrattend.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Property Id", "Name", "Quantity", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        evrattend.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                evrattendMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(evrattend);

        jButton5.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jButton5.setText("retrieve data");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jButton8.setText("close");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jLabel38.setBackground(new java.awt.Color(255, 255, 204));
        jLabel38.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(0, 0, 255));
        jLabel38.setText("Search");

        evrsrch.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        evrsrch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                evrsrchKeyReleased(evt);
            }
        });

        jLabel52.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel52.setText("Total Attendance:");

        totalrtsk.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jButton13.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jButton13.setText("Print data");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel23.setText("Filter By:");

        javax.swing.GroupLayout manageeventattendanceLayout = new javax.swing.GroupLayout(manageeventattendance);
        manageeventattendance.setLayout(manageeventattendanceLayout);
        manageeventattendanceLayout.setHorizontalGroup(
            manageeventattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageeventattendanceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton8)
                .addGap(156, 156, 156))
            .addGroup(manageeventattendanceLayout.createSequentialGroup()
                .addGroup(manageeventattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manageeventattendanceLayout.createSequentialGroup()
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(evrsrch, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(99, 99, 99)
                        .addComponent(jLabel23)
                        .addGap(83, 83, 83)
                        .addComponent(filterattend, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(manageeventattendanceLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel52)
                        .addGap(130, 130, 130)
                        .addGroup(manageeventattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton13)
                            .addComponent(totalrtsk, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageeventattendanceLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 859, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        manageeventattendanceLayout.setVerticalGroup(
            manageeventattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageeventattendanceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(manageeventattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manageeventattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(evrsrch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel23))
                    .addComponent(filterattend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageeventattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manageeventattendanceLayout.createSequentialGroup()
                        .addComponent(jLabel52)
                        .addGap(30, 30, 30)
                        .addGroup(manageeventattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton5)
                            .addComponent(jButton8)
                            .addComponent(jButton13)))
                    .addComponent(totalrtsk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(281, Short.MAX_VALUE))
        );

        displaypanel.add(manageeventattendance, "card16");

        AttendanceReport.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "MEMBER ATTENDANCE  REPORT GENERATION", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N
        AttendanceReport.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblread.setBackground(new java.awt.Color(255, 255, 204));
        lblread.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblread.setForeground(new java.awt.Color(0, 0, 255));
        lblread.setText("Report is used to quickly analyse data and can be generated periodicaly");
        AttendanceReport.add(lblread, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 88, -1, 34));

        genrent.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        genrent.setForeground(new java.awt.Color(153, 0, 102));
        genrent.setText("Attendance report");
        genrent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genrentActionPerformed(evt);
            }
        });
        AttendanceReport.add(genrent, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, -1));

        closerent.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        closerent.setForeground(new java.awt.Color(0, 51, 153));
        closerent.setText("close");
        closerent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closerentActionPerformed(evt);
            }
        });
        AttendanceReport.add(closerent, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 200, -1, -1));

        lblrent.setBackground(new java.awt.Color(255, 255, 204));
        lblrent.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblrent.setForeground(new java.awt.Color(0, 0, 255));
        lblrent.setText("Click once the button Rent Report to generate Rent Payment Report ");
        AttendanceReport.add(lblrent, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 630, 34));

        displaypanel.add(AttendanceReport, "card17");

        ContributionReport.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "INCOME EXPENDITURE REPORT", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N
        ContributionReport.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblroom.setBackground(new java.awt.Color(255, 255, 204));
        lblroom.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblroom.setForeground(new java.awt.Color(0, 0, 255));
        lblroom.setText("Click once button Income Expenditure Report to generate  Report ");
        ContributionReport.add(lblroom, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 580, 34));

        genroom.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        genroom.setForeground(new java.awt.Color(0, 153, 153));
        genroom.setText("inc $ exp report");
        genroom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genroomActionPerformed(evt);
            }
        });
        ContributionReport.add(genroom, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

        closeroom.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        closeroom.setText("close");
        closeroom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeroomActionPerformed(evt);
            }
        });
        ContributionReport.add(closeroom, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 200, -1, -1));

        lblread1.setBackground(new java.awt.Color(255, 255, 204));
        lblread1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblread1.setForeground(new java.awt.Color(0, 0, 255));
        lblread1.setText("Report is used to quickly analyse data and can be generated periodicaly");
        ContributionReport.add(lblread1, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 88, -1, 34));

        displaypanel.add(ContributionReport, "card18");

        MemberReport.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "TECHNO SCIENCE MEMBERS  REPORT GENERATION", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N
        MemberReport.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        gentenant.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        gentenant.setForeground(new java.awt.Color(102, 102, 255));
        gentenant.setText("MEMBERS Report");
        gentenant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gentenantActionPerformed(evt);
            }
        });
        MemberReport.add(gentenant, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        closetenant.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        closetenant.setForeground(new java.awt.Color(51, 51, 0));
        closetenant.setText("CLOSE");
        closetenant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closetenantActionPerformed(evt);
            }
        });
        MemberReport.add(closetenant, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 210, -1, -1));

        lbltenant.setBackground(new java.awt.Color(255, 255, 204));
        lbltenant.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbltenant.setForeground(new java.awt.Color(0, 0, 255));
        lbltenant.setText("Click once Members Report button Generate to generate Members report");
        MemberReport.add(lbltenant, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 580, 40));

        lblread2.setBackground(new java.awt.Color(255, 255, 204));
        lblread2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblread2.setForeground(new java.awt.Color(0, 0, 255));
        lblread2.setText("Report is used to quickly analyse data and can be generated periodicaly");
        MemberReport.add(lblread2, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 88, -1, 34));

        displaypanel.add(MemberReport, "card19");

        homepanel.setBackground(new java.awt.Color(0, 204, 204));

        softwarename.setFont(new java.awt.Font("Perpetua Titling MT", 1, 24)); // NOI18N
        softwarename.setText("WELCOME TO TECHNO SCIENCE MANAGEMENT SYSTEM");

        softwaredesc.setFont(new java.awt.Font("Tempus Sans ITC", 3, 24)); // NOI18N
        softwaredesc.setText("Technology is best when it brings people together");

        softwarename1.setFont(new java.awt.Font("Perpetua Titling MT", 1, 24)); // NOI18N
        softwarename1.setText("ADMIN DASHBOARD");

        softwaredesc1.setFont(new java.awt.Font("Tempus Sans ITC", 3, 24)); // NOI18N
        softwaredesc1.setForeground(new java.awt.Color(0, 0, 153));
        softwaredesc1.setText("Choose an Activity to perform in the Dashboard");

        javax.swing.GroupLayout homepanelLayout = new javax.swing.GroupLayout(homepanel);
        homepanel.setLayout(homepanelLayout);
        homepanelLayout.setHorizontalGroup(
            homepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homepanelLayout.createSequentialGroup()
                .addGroup(homepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(homepanelLayout.createSequentialGroup()
                        .addGap(208, 208, 208)
                        .addComponent(softwarename1))
                    .addGroup(homepanelLayout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(softwaredesc, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(homepanelLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(homepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(softwaredesc1, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(softwarename))))
                .addContainerGap(210, Short.MAX_VALUE))
        );
        homepanelLayout.setVerticalGroup(
            homepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homepanelLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(softwarename1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(softwarename, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(softwaredesc1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78)
                .addComponent(softwaredesc, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(391, Short.MAX_VALUE))
        );

        displaypanel.add(homepanel, "card4");

        writtingrrules.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "WRITING RENTAl RULES AND REGULATIONS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18), new java.awt.Color(0, 0, 255))); // NOI18N

        rulesarea.setColumns(20);
        rulesarea.setRows(5);
        jScrollPane11.setViewportView(rulesarea);

        jLabel44.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel44.setText("Click Retrieve to First view Existing rules and incase of Change made Click Save");

        saveregulations.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        saveregulations.setForeground(new java.awt.Color(0, 0, 102));
        saveregulations.setText("SAVE");
        saveregulations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveregulationsActionPerformed(evt);
            }
        });

        clsrules.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        clsrules.setForeground(new java.awt.Color(0, 51, 102));
        clsrules.setText("CLOSE");
        clsrules.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clsrulesActionPerformed(evt);
            }
        });

        saveregulations2.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        saveregulations2.setForeground(new java.awt.Color(0, 0, 102));
        saveregulations2.setText("Retrieve rules");
        saveregulations2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveregulations2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout writtingrrulesLayout = new javax.swing.GroupLayout(writtingrrules);
        writtingrrules.setLayout(writtingrrulesLayout);
        writtingrrulesLayout.setHorizontalGroup(
            writtingrrulesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(writtingrrulesLayout.createSequentialGroup()
                .addGroup(writtingrrulesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(writtingrrulesLayout.createSequentialGroup()
                        .addComponent(saveregulations2)
                        .addGap(60, 60, 60)
                        .addComponent(saveregulations)
                        .addGap(116, 116, 116)
                        .addComponent(clsrules))
                    .addGroup(writtingrrulesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel44))
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        writtingrrulesLayout.setVerticalGroup(
            writtingrrulesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(writtingrrulesLayout.createSequentialGroup()
                .addComponent(jLabel44)
                .addGap(17, 17, 17)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(writtingrrulesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveregulations)
                    .addComponent(clsrules)
                    .addComponent(saveregulations2))
                .addContainerGap(301, Short.MAX_VALUE))
        );

        displaypanel.add(writtingrrules, "card20");

        ViewRules.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "VIEW RULES AND REGULATIONS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Engravers MT", 1, 18))); // NOI18N

        rlsarea.setEditable(false);
        rlsarea.setColumns(20);
        rlsarea.setLineWrap(true);
        rlsarea.setRows(5);
        jScrollPane12.setViewportView(rlsarea);

        jLabel45.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(0, 51, 51));
        jLabel45.setText("Click button view to display rent due dates and related payment Information");

        viewregulations.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        viewregulations.setText("view");
        viewregulations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewregulationsActionPerformed(evt);
            }
        });

        printregulations.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        printregulations.setText("print");
        printregulations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printregulationsActionPerformed(evt);
            }
        });

        closeregulations.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        closeregulations.setText("close");
        closeregulations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeregulationsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ViewRulesLayout = new javax.swing.GroupLayout(ViewRules);
        ViewRules.setLayout(ViewRulesLayout);
        ViewRulesLayout.setHorizontalGroup(
            ViewRulesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewRulesLayout.createSequentialGroup()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(ViewRulesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ViewRulesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel45)
                    .addGroup(ViewRulesLayout.createSequentialGroup()
                        .addComponent(viewregulations)
                        .addGap(94, 94, 94)
                        .addComponent(printregulations)
                        .addGap(136, 136, 136)
                        .addComponent(closeregulations)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ViewRulesLayout.setVerticalGroup(
            ViewRulesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewRulesLayout.createSequentialGroup()
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(ViewRulesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(viewregulations)
                    .addComponent(printregulations)
                    .addComponent(closeregulations))
                .addContainerGap(307, Short.MAX_VALUE))
        );

        displaypanel.add(ViewRules, "card21");

        writeduedates.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "RECORDING RENT DUE DATES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Engravers MT", 1, 18))); // NOI18N
        writeduedates.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rentdts.setColumns(20);
        rentdts.setRows(5);
        jScrollPane13.setViewportView(rentdts);

        writeduedates.add(jScrollPane13, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 52, 630, 301));

        jLabel46.setFont(new java.awt.Font("Eras Demi ITC", 1, 18)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(0, 51, 0));
        jLabel46.setText("Click Retrieve button and click Update button to save the Notices ");
        writeduedates.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 24, -1, -1));

        saverls.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        saverls.setText("Update");
        saverls.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saverlsActionPerformed(evt);
            }
        });
        writeduedates.add(saverls, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 360, -1, -1));

        jButton11.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jButton11.setText("close");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        writeduedates.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 360, -1, -1));

        retrievedts.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        retrievedts.setText("Retrieve Dates");
        retrievedts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retrievedtsActionPerformed(evt);
            }
        });
        writeduedates.add(retrievedts, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, -1, -1));

        displaypanel.add(writeduedates, "card22");

        ViewCalendarActivites.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "View Rent Due Dates", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Engravers MT", 1, 18), new java.awt.Color(102, 153, 0))); // NOI18N

        adminclds.setEditable(false);
        adminclds.setColumns(20);
        adminclds.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        adminclds.setLineWrap(true);
        adminclds.setRows(5);
        jScrollPane14.setViewportView(adminclds);

        jButton4.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jButton4.setText("view");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton9.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jButton9.setText("Print");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jButton10.setText("close");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jLabel47.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel47.setText("Click button View to Display the Rent Due Dates");

        javax.swing.GroupLayout ViewCalendarActivitesLayout = new javax.swing.GroupLayout(ViewCalendarActivites);
        ViewCalendarActivites.setLayout(ViewCalendarActivitesLayout);
        ViewCalendarActivitesLayout.setHorizontalGroup(
            ViewCalendarActivitesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewCalendarActivitesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ViewCalendarActivitesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel47)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ViewCalendarActivitesLayout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addGap(139, 139, 139)
                        .addComponent(jButton9)
                        .addGap(146, 146, 146)
                        .addComponent(jButton10)))
                .addContainerGap(368, Short.MAX_VALUE))
        );
        ViewCalendarActivitesLayout.setVerticalGroup(
            ViewCalendarActivitesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewCalendarActivitesLayout.createSequentialGroup()
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ViewCalendarActivitesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton9)
                    .addComponent(jButton10))
                .addContainerGap(281, Short.MAX_VALUE))
        );

        displaypanel.add(ViewCalendarActivites, "card23");

        Receipt.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Rental Receipt", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Perpetua Titling MT", 1, 18), new java.awt.Color(0, 51, 51))); // NOI18N

        recpt.setColumns(20);
        recpt.setRows(5);
        jScrollPane15.setViewportView(recpt);

        jButton12.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jButton12.setText("view");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        printbutton.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        printbutton.setText("print");
        printbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printbuttonActionPerformed(evt);
            }
        });

        jButton14.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jButton14.setText("close");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ReceiptLayout = new javax.swing.GroupLayout(Receipt);
        Receipt.setLayout(ReceiptLayout);
        ReceiptLayout.setHorizontalGroup(
            ReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane15)
            .addGroup(ReceiptLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jButton12)
                .addGap(123, 123, 123)
                .addComponent(printbutton)
                .addGap(125, 125, 125)
                .addComponent(jButton14)
                .addContainerGap(412, Short.MAX_VALUE))
        );
        ReceiptLayout.setVerticalGroup(
            ReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReceiptLayout.createSequentialGroup()
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(printbutton)
                    .addComponent(jButton12)
                    .addComponent(jButton14))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        displaypanel.add(Receipt, "card24");

        ViewComplains.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "VIEW TENANT COMPLAINS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18)))); // NOI18N
        ViewComplains.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        complainsview.setColumns(20);
        complainsview.setLineWrap(true);
        complainsview.setRows(5);
        jScrollPane16.setViewportView(complainsview);

        ViewComplains.add(jScrollPane16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 480, 300));

        viewcomp.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        viewcomp.setText("VIEW");
        viewcomp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewcompActionPerformed(evt);
            }
        });
        ViewComplains.add(viewcomp, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, -1, -1));

        clsbtns.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        clsbtns.setText("CLOSE");
        clsbtns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clsbtnsActionPerformed(evt);
            }
        });
        ViewComplains.add(clsbtns, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 370, -1, -1));

        jLabel48.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(51, 51, 0));
        jLabel48.setText("Click View to Retrieve sent Complains");
        ViewComplains.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 330, -1));

        displaypanel.add(ViewComplains, "card25");

        UpdateRooms.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "UPDATE ROOMS FORM", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N
        UpdateRooms.setPreferredSize(new java.awt.Dimension(667, 400));

        blrm1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        blrm1.setText("Room Number:");

        lbnum1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbnum1.setText("Occupant Phonenumber:");

        lblstat1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblstat1.setText("Status:");

        btnCloserm1.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnCloserm1.setText("Close");
        btnCloserm1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloserm1ActionPerformed(evt);
            }
        });

        btnClearrm1.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnClearrm1.setForeground(new java.awt.Color(0, 51, 51));
        btnClearrm1.setText("Clear");
        btnClearrm1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearrm1ActionPerformed(evt);
            }
        });

        btnaddrm1.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnaddrm1.setForeground(new java.awt.Color(0, 102, 204));
        btnaddrm1.setText("Update Rooms");
        btnaddrm1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddrm1ActionPerformed(evt);
            }
        });

        phup.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        lblFilldet1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        lblFilldet1.setForeground(new java.awt.Color(0, 102, 102));
        lblFilldet1.setText("Please Fill in Rooms  details Below:");

        txtstatup.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txtstatup.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose Room Status", "Occupied", "Not Occupied", " " }));
        txtstatup.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                txtstatupItemStateChanged(evt);
            }
        });

        rmupd.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        rmupd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rmupdKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout UpdateRoomsLayout = new javax.swing.GroupLayout(UpdateRooms);
        UpdateRooms.setLayout(UpdateRoomsLayout);
        UpdateRoomsLayout.setHorizontalGroup(
            UpdateRoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UpdateRoomsLayout.createSequentialGroup()
                .addGroup(UpdateRoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(blrm1)
                    .addGroup(UpdateRoomsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblstat1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(UpdateRoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(phup, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                    .addComponent(txtstatup, 0, 267, Short.MAX_VALUE)
                    .addComponent(rmupd))
                .addGap(149, 149, 149))
            .addGroup(UpdateRoomsLayout.createSequentialGroup()
                .addGroup(UpdateRoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbnum1)
                    .addGroup(UpdateRoomsLayout.createSequentialGroup()
                        .addComponent(btnaddrm1)
                        .addGap(69, 69, 69)
                        .addComponent(btnClearrm1)
                        .addGap(92, 92, 92)
                        .addComponent(btnCloserm1))
                    .addGroup(UpdateRoomsLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(lblFilldet1, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(424, Short.MAX_VALUE))
        );
        UpdateRoomsLayout.setVerticalGroup(
            UpdateRoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UpdateRoomsLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(lblFilldet1)
                .addGap(18, 18, 18)
                .addGroup(UpdateRoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(blrm1)
                    .addComponent(rmupd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(UpdateRoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbnum1)
                    .addComponent(phup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(UpdateRoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtstatup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblstat1))
                .addGap(96, 96, 96)
                .addGroup(UpdateRoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnaddrm1)
                    .addComponent(btnClearrm1)
                    .addComponent(btnCloserm1))
                .addContainerGap(359, Short.MAX_VALUE))
        );

        displaypanel.add(UpdateRooms, "card13");

        UploadNotices.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "UPLOAD NOTICES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18), new java.awt.Color(0, 0, 255))); // NOI18N

        adminnotic.setColumns(20);
        adminnotic.setRows(5);
        jScrollPane17.setViewportView(adminnotic);

        jLabel54.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel54.setText("In the Below Upload Button click to it to upload your Notice");

        saveregulations1.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        saveregulations1.setForeground(new java.awt.Color(0, 0, 102));
        saveregulations1.setText("Upload");
        saveregulations1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveregulations1ActionPerformed(evt);
            }
        });

        clsrules1.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        clsrules1.setForeground(new java.awt.Color(0, 51, 102));
        clsrules1.setText("CLOSE");
        clsrules1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clsrules1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout UploadNoticesLayout = new javax.swing.GroupLayout(UploadNotices);
        UploadNotices.setLayout(UploadNoticesLayout);
        UploadNoticesLayout.setHorizontalGroup(
            UploadNoticesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UploadNoticesLayout.createSequentialGroup()
                .addGroup(UploadNoticesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel54)
                    .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 317, Short.MAX_VALUE))
            .addGroup(UploadNoticesLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(saveregulations1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(clsrules1)
                .addGap(32, 32, 32))
        );
        UploadNoticesLayout.setVerticalGroup(
            UploadNoticesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UploadNoticesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel54)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(UploadNoticesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saveregulations1)
                    .addComponent(clsrules1))
                .addContainerGap(299, Short.MAX_VALUE))
        );

        displaypanel.add(UploadNotices, "card20");

        ViewNotices.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "VIEW NOTICES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18), new java.awt.Color(0, 0, 255))); // NOI18N

        admviewntc.setColumns(20);
        admviewntc.setRows(5);
        jScrollPane18.setViewportView(admviewntc);

        jLabel55.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel55.setText("Click button View to Display the Ulploaded Notices");

        savfeedb.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        savfeedb.setForeground(new java.awt.Color(0, 0, 102));
        savfeedb.setText("View");
        savfeedb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savfeedbActionPerformed(evt);
            }
        });

        clsfeedbc.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        clsfeedbc.setForeground(new java.awt.Color(0, 51, 102));
        clsfeedbc.setText("CLOSE");
        clsfeedbc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clsfeedbcActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ViewNoticesLayout = new javax.swing.GroupLayout(ViewNotices);
        ViewNotices.setLayout(ViewNoticesLayout);
        ViewNoticesLayout.setHorizontalGroup(
            ViewNoticesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewNoticesLayout.createSequentialGroup()
                .addGroup(ViewNoticesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel55)
                    .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 317, Short.MAX_VALUE))
            .addGroup(ViewNoticesLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(savfeedb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(clsfeedbc)
                .addGap(63, 63, 63))
        );
        ViewNoticesLayout.setVerticalGroup(
            ViewNoticesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewNoticesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel55)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ViewNoticesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(savfeedb)
                    .addComponent(clsfeedbc))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        displaypanel.add(ViewNotices, "card20");

        Viewmemberscontyear.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "VIEW MEMBERS CONTRIBUTION  BASED ON YEARS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N
        Viewmemberscontyear.setPreferredSize(new java.awt.Dimension(525, 0));

        providerstbl1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Finance ID", "Reg NO.", "Name", "Phonenumber", "Year $ Sem", "Purpose", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        providerstbl1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                providerstbl1MouseClicked(evt);
            }
        });
        jScrollPane19.setViewportView(providerstbl1);

        btnCloseprov1.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnCloseprov1.setText("Close");
        btnCloseprov1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseprov1ActionPerformed(evt);
            }
        });

        btnretrieveprov2.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnretrieveprov2.setText("Retrieve Data");
        btnretrieveprov2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnretrieveprov2ActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel35.setText("SEARCH:");

        providerssearch1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        providerssearch1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                providerssearch1KeyReleased(evt);
            }
        });

        btnretrieveprov3.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnretrieveprov3.setText("print data");
        btnretrieveprov3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnretrieveprov3ActionPerformed(evt);
            }
        });

        jLabel26.setText("Filter by Year:");

        selyrs.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        selyrs.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Year", "Y1S1", "Y1S2", "Y2S1", "Y2S2", "Y3S1", "Y3S2", "Y4S1", "Y4S2", " " }));

        jLabel28.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel28.setText("Total Members:");

        jTextField2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        javax.swing.GroupLayout ViewmemberscontyearLayout = new javax.swing.GroupLayout(Viewmemberscontyear);
        Viewmemberscontyear.setLayout(ViewmemberscontyearLayout);
        ViewmemberscontyearLayout.setHorizontalGroup(
            ViewmemberscontyearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewmemberscontyearLayout.createSequentialGroup()
                .addGroup(ViewmemberscontyearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane19)
                    .addGroup(ViewmemberscontyearLayout.createSequentialGroup()
                        .addGroup(ViewmemberscontyearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ViewmemberscontyearLayout.createSequentialGroup()
                                .addComponent(btnretrieveprov2)
                                .addGap(135, 135, 135)
                                .addComponent(btnretrieveprov3)
                                .addGap(196, 196, 196)
                                .addComponent(btnCloseprov1))
                            .addGroup(ViewmemberscontyearLayout.createSequentialGroup()
                                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(63, 63, 63)
                                .addComponent(providerssearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(98, 98, 98)
                                .addComponent(jLabel26)
                                .addGap(35, 35, 35)
                                .addComponent(selyrs, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(ViewmemberscontyearLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel28)
                .addGap(234, 234, 234)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ViewmemberscontyearLayout.setVerticalGroup(
            ViewmemberscontyearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ViewmemberscontyearLayout.createSequentialGroup()
                .addGroup(ViewmemberscontyearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(providerssearch1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(selyrs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(ViewmemberscontyearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(ViewmemberscontyearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnretrieveprov2)
                    .addComponent(btnretrieveprov3)
                    .addComponent(btnCloseprov1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        displaypanel.add(Viewmemberscontyear, "card12");

        ManageIncomeExp.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "VIEW INCOMES AND EXPENDITURES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Engravers MT", 1, 24), new java.awt.Color(0, 0, 255))); // NOI18N

        retrievebtna1.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        retrievebtna1.setForeground(new java.awt.Color(0, 0, 255));
        retrievebtna1.setText("Retrieve");
        retrievebtna1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retrievebtna1ActionPerformed(evt);
            }
        });

        btnCloseMaintainUsersasd1.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btnCloseMaintainUsersasd1.setForeground(new java.awt.Color(0, 255, 51));
        btnCloseMaintainUsersasd1.setText("Close");
        btnCloseMaintainUsersasd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseMaintainUsersasd1ActionPerformed(evt);
            }
        });

        incexpds.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Financial ID", "Type", "Source", "Amount", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        incexpds.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                incexpdsMouseClicked(evt);
            }
        });
        jScrollPane20.setViewportView(incexpds);

        jLabel58.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel58.setText("Search:");

        searchicm.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        searchicm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchicmKeyReleased(evt);
            }
        });

        retrievebtna2.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        retrievebtna2.setForeground(new java.awt.Color(0, 0, 255));
        retrievebtna2.setText("print data");
        retrievebtna2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retrievebtna2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ManageIncomeExpLayout = new javax.swing.GroupLayout(ManageIncomeExp);
        ManageIncomeExp.setLayout(ManageIncomeExpLayout);
        ManageIncomeExpLayout.setHorizontalGroup(
            ManageIncomeExpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ManageIncomeExpLayout.createSequentialGroup()
                .addComponent(jLabel58)
                .addGap(60, 60, 60)
                .addComponent(searchicm, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane20)
            .addGroup(ManageIncomeExpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(retrievebtna1)
                .addGap(111, 111, 111)
                .addComponent(retrievebtna2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 166, Short.MAX_VALUE)
                .addComponent(btnCloseMaintainUsersasd1)
                .addGap(123, 123, 123))
        );
        ManageIncomeExpLayout.setVerticalGroup(
            ManageIncomeExpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ManageIncomeExpLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ManageIncomeExpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(searchicm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(ManageIncomeExpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(retrievebtna1)
                    .addComponent(btnCloseMaintainUsersasd1)
                    .addComponent(retrievebtna2))
                .addContainerGap(266, Short.MAX_VALUE))
        );

        displaypanel.add(ManageIncomeExp, "card3");

        manageservices.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Manage Techno Science Computer Clinics", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Engravers MT", 1, 24), new java.awt.Color(0, 0, 255))); // NOI18N

        insser.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        insser.setForeground(new java.awt.Color(0, 0, 255));
        insser.setText("insert");
        insser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insserActionPerformed(evt);
            }
        });

        lblusername1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblusername1.setText("Service Type:");

        txtstr.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        btnUpdatesser.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btnUpdatesser.setForeground(new java.awt.Color(153, 0, 51));
        btnUpdatesser.setText("Update");
        btnUpdatesser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdatesserActionPerformed(evt);
            }
        });

        btnDeletessert.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btnDeletessert.setForeground(new java.awt.Color(102, 51, 0));
        btnDeletessert.setText("Delete");
        btnDeletessert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletessertActionPerformed(evt);
            }
        });

        btnClosessert.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btnClosessert.setForeground(new java.awt.Color(0, 255, 51));
        btnClosessert.setText("Close");
        btnClosessert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClosessertActionPerformed(evt);
            }
        });

        eventtables.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Service ID", "Service Name", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        eventtables.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eventtablesMouseClicked(evt);
            }
        });
        jScrollPane21.setViewportView(eventtables);

        num2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        num2.setText("Amount:");

        jLabel62.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel62.setText("Search:");

        cmpservices.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        cmpservices.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cmpservicesKeyReleased(evt);
            }
        });

        retrieveser.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        retrieveser.setForeground(new java.awt.Color(0, 0, 255));
        retrieveser.setText("Retrieve");
        retrieveser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retrieveserActionPerformed(evt);
            }
        });

        jLabel63.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(51, 51, 255));
        jLabel63.setText("Total Services:");

        totalclinc.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N

        btnUpdatesser1.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btnUpdatesser1.setForeground(new java.awt.Color(153, 0, 51));
        btnUpdatesser1.setText("print");
        btnUpdatesser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdatesser1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout manageservicesLayout = new javax.swing.GroupLayout(manageservices);
        manageservices.setLayout(manageservicesLayout);
        manageservicesLayout.setHorizontalGroup(
            manageservicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageservicesLayout.createSequentialGroup()
                .addComponent(jLabel62)
                .addGap(60, 60, 60)
                .addComponent(cmpservices, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(manageservicesLayout.createSequentialGroup()
                .addComponent(insser)
                .addGap(18, 18, 18)
                .addComponent(retrieveser)
                .addGap(33, 33, 33)
                .addComponent(btnUpdatesser)
                .addGap(85, 85, 85)
                .addComponent(btnUpdatesser1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDeletessert, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnClosessert)
                .addGap(42, 42, 42))
            .addGroup(manageservicesLayout.createSequentialGroup()
                .addComponent(jLabel63)
                .addGap(107, 107, 107)
                .addComponent(totalclinc, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(manageservicesLayout.createSequentialGroup()
                .addComponent(jScrollPane21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(manageservicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageservicesLayout.createSequentialGroup()
                        .addComponent(num2)
                        .addGap(65, 65, 65)
                        .addComponent(txtamrd, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageservicesLayout.createSequentialGroup()
                        .addComponent(lblusername1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(txtstr, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(79, 79, 79))
        );
        manageservicesLayout.setVerticalGroup(
            manageservicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageservicesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(manageservicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel62)
                    .addComponent(cmpservices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(manageservicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(manageservicesLayout.createSequentialGroup()
                        .addGroup(manageservicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblusername1)
                            .addComponent(txtstr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(167, 167, 167)
                        .addGroup(manageservicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtamrd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(num2))))
                .addGap(35, 35, 35)
                .addGroup(manageservicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel63)
                    .addComponent(totalclinc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(manageservicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manageservicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(insser)
                        .addComponent(retrieveser)
                        .addComponent(btnUpdatesser)
                        .addComponent(btnUpdatesser1))
                    .addGroup(manageservicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnDeletessert)
                        .addComponent(btnClosessert)))
                .addContainerGap(222, Short.MAX_VALUE))
        );

        displaypanel.add(manageservices, "card3");

        loginlabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        loginlabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        loginlabel.setText("Logged in as:");

        myusername.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        myusername.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        myusername.setText("myusername");

        btnLogOut.setFont(new java.awt.Font("Perpetua Titling MT", 1, 14)); // NOI18N
        btnLogOut.setText("Log Out");
        btnLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogOutActionPerformed(evt);
            }
        });

        softwareName.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        softwareName.setForeground(new java.awt.Color(0, 0, 255));
        softwareName.setText("TECHNOSCIENCE MANAGEMENT SYSTEM");

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        btnGroup.setBackground(new java.awt.Color(255, 255, 255));
        btnGroup.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Available Actions", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 2, 11))); // NOI18N

        btnAddusers.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnAddusers.setText("Add Leaders");
        btnAddusers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddusersActionPerformed(evt);
            }
        });

        usrmgmt.setBackground(new java.awt.Color(255, 255, 255));
        usrmgmt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        usrmgmt.setForeground(new java.awt.Color(0, 255, 255));
        usrmgmt.setText("Recording Actions");

        stockmgmt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        stockmgmt.setForeground(new java.awt.Color(255, 51, 102));
        stockmgmt.setText("Manage Actions");

        btnaddrentalproperties.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnaddrentalproperties.setText("Assign Club Properties");
        btnaddrentalproperties.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddrentalpropertiesActionPerformed(evt);
            }
        });

        btnmanageusers.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnmanageusers.setText("Manage Leaders");
        btnmanageusers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmanageusersActionPerformed(evt);
            }
        });

        btnmanagetenants.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnmanagetenants.setText("Manage Club Properties");
        btnmanagetenants.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmanagetenantsActionPerformed(evt);
            }
        });

        roomsreportbtn.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        roomsreportbtn.setText("Members Report");
        roomsreportbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomsreportbtnActionPerformed(evt);
            }
        });

        lblReports2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblReports2.setText("Generate Reports");

        reptenant.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        reptenant.setText("Members Attendance Report");
        reptenant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reptenantActionPerformed(evt);
            }
        });

        btnaddrentalproperties1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnaddrentalproperties1.setText("Upload Notice");
        btnaddrentalproperties1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddrentalproperties1ActionPerformed(evt);
            }
        });

        reppayment1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        reppayment1.setText("Contribution Report");
        reppayment1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reppayment1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout btnGroupLayout = new javax.swing.GroupLayout(btnGroup);
        btnGroup.setLayout(btnGroupLayout);
        btnGroupLayout.setHorizontalGroup(
            btnGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnGroupLayout.createSequentialGroup()
                .addGroup(btnGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnaddrentalproperties))
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(stockmgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnmanageusers))
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnmanagetenants))
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnaddrentalproperties1)))
                .addGap(0, 72, Short.MAX_VALUE))
            .addGroup(btnGroupLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(usrmgmt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addGroup(btnGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAddusers, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(roomsreportbtn)
                            .addGroup(btnGroupLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(lblReports2))
                            .addComponent(reptenant, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(reppayment1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        btnGroupLayout.setVerticalGroup(
            btnGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnGroupLayout.createSequentialGroup()
                .addComponent(usrmgmt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddusers)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnaddrentalproperties)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnaddrentalproperties1)
                .addGap(10, 10, 10)
                .addComponent(stockmgmt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnmanageusers)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnmanagetenants)
                .addGap(18, 18, 18)
                .addComponent(lblReports2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(roomsreportbtn)
                .addGap(27, 27, 27)
                .addComponent(reptenant)
                .addGap(18, 18, 18)
                .addComponent(reppayment1)
                .addContainerGap(535, Short.MAX_VALUE))
        );

        jScrollPane3.setViewportView(btnGroup);

        jScrollPane4.setViewportView(jScrollPane3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(displaypanel, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 49, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(softwareName, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89)
                        .addComponent(loginlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(137, 137, 137)
                        .addComponent(myusername, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLogOut)
                        .addGap(18, 18, 18))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myusername)
                    .addComponent(btnLogOut))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(displaypanel, javax.swing.GroupLayout.PREFERRED_SIZE, 690, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addComponent(softwareName, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void roleadtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roleadtxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roleadtxtActionPerformed

    private void btnAddUseradActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUseradActionPerformed
        try{
        String sql = " INSERT INTO account (names, username, phonenumber, role, Password, confirmpassword, security_question, answer )  VALUES (?,?,?,?,?,?,?,?)";
        con = DriverManager.getConnection("jdbc:mysql://localhost/tms","root","");
        pst = con.prepareStatement(sql);
        pst.setString(1, nameadmtxt.getText());
        pst.setString(2, usernameadmtxt.getText());
        pst.setString(3, phoneadtxt.getText());
        pst.setString(4, passadtxt.getText());
        pst.setString(5, confirmadtxt.getText());
        pst.setString(6, roleadtxt.getSelectedItem().toString());
        pst.setString(7, securityboxad.getSelectedItem().toString());
        pst.setString(8, ansadtxt.getText()); 
         if(nameadmtxt.getText().equals("") || usernameadmtxt.getText().equals("") || phoneadtxt.getText().equals("") || passadtxt.getText().equals("") || confirmadtxt.getText().equals("") || roleadtxt.getSelectedIndex()==0 || ansadtxt.getText().equals("") || securityboxad.getSelectedIndex()==0) 
         {
             JOptionPane.showMessageDialog(null,"Some fields not filled in..!!\n Please Fill them to continue.","Warning",JOptionPane.WARNING_MESSAGE);
          }   
        if( !passadtxt.getText().equals (confirmadtxt.getText())){
            JOptionPane.showMessageDialog(null,"Passwords are not the same!!!");
        }
        else {
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Registration of System user is Successful");
            phoneadtxt.setText("");
            usernameadmtxt.setText("");
            nameadmtxt.setText("");
            passadtxt.setText("");
            roleadtxt.setSelectedIndex(0);
            confirmadtxt.setText("");
            securityboxad.setSelectedIndex(0);
            ansadtxt.setText("");    
        }
        } 
        catch(HeadlessException | SQLException ex){
        JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_btnAddUseradActionPerformed

    private void btnClearadUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearadUserActionPerformed
        nameadmtxt.setText("");
        phoneadtxt.setText("");
        usernameadmtxt.setText("");
        passadtxt.setText("");
        confirmadtxt.setText("");
        roleadtxt.setSelectedIndex(0);
        securityboxad.setSelectedIndex(0);
        ansadtxt.setText("");
    }//GEN-LAST:event_btnClearadUserActionPerformed

    private void btnCloseadNewUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseadNewUserActionPerformed
        load_home();
    }//GEN-LAST:event_btnCloseadNewUserActionPerformed

    private void usertableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usertableMouseClicked
        String name = usertable.getValueAt(usertable.getSelectedRow(), 1).toString();
        String userds = usertable.getValueAt(usertable.getSelectedRow(), 2).toString();
        String pno = usertable.getValueAt(usertable.getSelectedRow(), 3).toString();
        String pass = usertable.getValueAt(usertable.getSelectedRow(), 4).toString();
        String roles = usertable.getValueAt(usertable.getSelectedRow(), 5).toString();
        String security = usertable.getValueAt(usertable.getSelectedRow(), 6).toString();
        String asn = usertable.getValueAt(usertable.getSelectedRow(), 7).toString();
        

        txtnameadmins.setText(name);
        txtusernameadmn.setText(userds);
        numbertxtads.setText(pno);
        txtpasswordads.setText(pass);
        positiontxtads.setText(roles);
        questiontxtad.setText(security);
        answertxtad.setText(asn);
    }//GEN-LAST:event_usertableMouseClicked

    private void retrievebtnaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retrievebtnaActionPerformed
      retrieveusers();
      totalsysusers();
    }//GEN-LAST:event_retrievebtnaActionPerformed

    private void btnUpdateUserInfoasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateUserInfoasActionPerformed
      try {
            if (txtnameadmins.getText().isEmpty() || txtusernameadmn.getText().isEmpty() || numbertxtads.getText().isEmpty() || txtpasswordads.getText().isEmpty()|| positiontxtads.getText().isEmpty()|| questiontxtad.getText().isEmpty()|| answertxtad.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No row is selected..!!\nPlease select a row to update...");
            } else {
                int index = usertable.getSelectedRow();
                String id = usertable.getValueAt(index, 0).toString();  //index=rownumber and 0 represents first column..

                if (updateusers(id, txtnameadmins.getText(), txtusernameadmn.getText(),numbertxtads.getText(), txtpasswordads.getText(), positiontxtads.getText(), questiontxtad.getText(),answertxtad.getText())){
                    JOptionPane.showMessageDialog(null, "System Users Data Updated Successfully");
                    txtnameadmins.setText("");
                    txtusernameadmn.setText("");
                    numbertxtads.setText("");
                    txtpasswordads.setText("");
                    positiontxtads.setText("");
                    questiontxtad.setText("");
                    answertxtad.setText("");
                    retrieveusers();
                    totalsysusers();
                } else {
                    JOptionPane.showMessageDialog(null, "System User Data is Not Updated ");
                }
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }           
    }//GEN-LAST:event_btnUpdateUserInfoasActionPerformed

    private void btnDeleteUserasdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteUserasdActionPerformed
        try {
            String[] options = {"Yes", "No"};
            int answer = JOptionPane.showOptionDialog(null, " Are you Sure to delete ??", "Delete Options", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

            if (answer == 0) {
              String id = usertable.getValueAt(usertable.getSelectedRow(), 0).toString();
                if (deleteusers(id)) {
                    JOptionPane.showMessageDialog(null, "System Data Deleted Successfully");
                    txtnameadmins.setText("");
                    txtusernameadmn.setText("");
                    numbertxtads.setText("");
                    txtpasswordads.setText("");
                    positiontxtads.setText("");
                    questiontxtad.setText("");
                    answertxtad.setText("");
                    retrieveusers();
                    totalsysusers();
                } else {
                    JOptionPane.showMessageDialog(null, " System Users Data is Not Deleted ");
                }
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, e);
        }        
    }//GEN-LAST:event_btnDeleteUserasdActionPerformed

    private void btnCloseMaintainUsersasdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseMaintainUsersasdActionPerformed
         load_home();
    }//GEN-LAST:event_btnCloseMaintainUsersasdActionPerformed

    private void btnAddusersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddusersActionPerformed
        displaypanel.removeAll();
        displaypanel.add(newusr);
        newusr.setVisible(true);
        newusr.repaint();
        newusr.revalidate();
    }//GEN-LAST:event_btnAddusersActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        fanametxt.setText(null);
        lasatntxt.setText(null);
        pnatxt.setText(null);
        occtxt.setSelectedIndex(0);
        issuedat.setCalendar(null);
        returndat.setCalendar(null);
        
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
            load_home();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnGetTenantsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetTenantsActionPerformed
        retrieveadsmembers();
        totaladsmembers();
    }//GEN-LAST:event_btnGetTenantsActionPerformed

    private void advmemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_advmemsMouseClicked
        String nam = advmems.getValueAt(advmems.getSelectedRow(), 1).toString();
        String lnam = advmems.getValueAt(advmems.getSelectedRow(), 2).toString();
        String numbet = advmems.getValueAt(advmems.getSelectedRow(), 3).toString();
        String cg = advmems.getValueAt(advmems.getSelectedRow(), 4).toString();
        String db = advmems.getValueAt(advmems.getSelectedRow(), 5).toString();
        String lres = advmems.getValueAt(advmems.getSelectedRow(), 5).toString();
        ftxt.setText(nam);
        ltxt.setText(lnam);
        phtxt.setText(numbet);
        datetxt.setText(cg);
        typetxt.setText(db); 
        ytr.setText(lres);
    }//GEN-LAST:event_advmemsMouseClicked

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
       try {
            String[] options = {"Yes", "No"};
            int answer = JOptionPane.showOptionDialog(null, " Are you Sure to delete ??", "Delete Options", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            if (answer == 0) {
                String id = advmems.getValueAt(advmems.getSelectedRow(), 0).toString();
                if (deleteadsmembers(id)) {
                    JOptionPane.showMessageDialog(null, "Techno Science Members Data Deleted Successfully");
                    ftxt.setText("");
                    ltxt.setText("");
                    phtxt.setText("");
                    datetxt.setText("");
                    typetxt.setText("");
                    ytr.setText("");
                    retrieveadsmembers();
                    totaladsmembers();
                } else {
                    JOptionPane.showMessageDialog(null, " Techno Science Members Data is Not Deleted ");
                }
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, e);
        }       
    }//GEN-LAST:event_deleteActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
         try {
            if (ftxt.getText().isEmpty() || ltxt.getText().isEmpty() || phtxt.getText().isEmpty() || datetxt.getText().isEmpty()|| typetxt.getText().isEmpty()
                    ){
                JOptionPane.showMessageDialog(null, "No row is selected..!!\nPlease select a row to update...");
            } else {
                int index = advmems.getSelectedRow();
                String id = advmems.getValueAt(index, 0).toString();
                if (updateadsmembers(id, ftxt.getText(), ltxt.getText(), phtxt.getText() ,datetxt.getText(),typetxt.getText(),ytr.getText())) {
                    JOptionPane.showMessageDialog(null, "Techno Science Members Data Updated Successfully");
                    ftxt.setText("");
                    ltxt.setText("");
                    phtxt.setText("");
                    datetxt.setText("");
                    typetxt.setText("");
                    ytr.setText("");
                    retrieveadsmembers();
                    totaladsmembers();;
                } else {
                    JOptionPane.showMessageDialog(null, "Techno Science Members Data is Not Updated ");
                }
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }      
    }//GEN-LAST:event_updateActionPerformed

    private void btnClosetenantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClosetenantActionPerformed
          load_home();
    }//GEN-LAST:event_btnClosetenantActionPerformed

    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogOutActionPerformed
        int dialog_button=JOptionPane.YES_NO_OPTION;
        int logoutrequest=JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?","Log Out",dialog_button);
        if (logoutrequest==0){
            this.dispose();
            LoginForm admlo=new LoginForm();
            admlo.setVisible(true);
        }
        else {   
        }
    }//GEN-LAST:event_btnLogOutActionPerformed

    private void buttonclsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonclsActionPerformed
           load_home();
    }//GEN-LAST:event_buttonclsActionPerformed

    private void btnCloserangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloserangeActionPerformed
        load_home();
    }//GEN-LAST:event_btnCloserangeActionPerformed

    private void btnClosermActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClosermActionPerformed
         load_home();
    }//GEN-LAST:event_btnClosermActionPerformed

    private void registerbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerbtnActionPerformed
        try {
            String sql = " INSERT INTO clubproperties (regno, name, phonenumber, item_name, dateissued, returndate)  VALUES (?,?,?,?,?,?)";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            pst.setString(1, fanametxt.getText());
            pst.setString(2, lasatntxt.getText());
            pst.setString(3, pnatxt.getText());
            pst.setString(4, occtxt.getSelectedItem().toString());
            String isddate = ((JTextField) issuedat.getDateEditor().getUiComponent()).getText();
            pst.setString(5, isddate);
            String retddate = ((JTextField) returndat.getDateEditor().getUiComponent()).getText();
            pst.setString(6, retddate);
            
            if (fanametxt.getText().equals("") || lasatntxt.getText().equals("") || pnatxt.getText().equals("") || occtxt.getSelectedIndex() == 0  ) {
                JOptionPane.showMessageDialog(null, "Some fields not filled in..!!\n Please Fill them to continue.", "Warning", JOptionPane.WARNING_MESSAGE);
                
            } else {
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Registration of Techno Science Property is Successful");
                   fanametxt.setText(null);
                   lasatntxt.setText(null);
                   pnatxt.setText(null);
                   occtxt.setSelectedIndex(0);
                   issuedat.setCalendar(null);
                   returndat.setCalendar(null);
                   
            }
        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }                               
    }//GEN-LAST:event_registerbtnActionPerformed

    private void btnReloadempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadempActionPerformed
       retrievepropd();
       totalpropd();
    }//GEN-LAST:event_btnReloadempActionPerformed

    private void clubptableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clubptableMouseClicked
        DefaultTableModel model=(DefaultTableModel) clubptable.getModel();
        String selectedfname=(String) model.getValueAt(clubptable.getSelectedRow(), 1);
        String selectedlname=(String) model.getValueAt(clubptable.getSelectedRow(), 2);
        String selectedphone=(String) model.getValueAt(clubptable.getSelectedRow(), 3);
        String selectedsalary=(String) model.getValueAt(clubptable.getSelectedRow(), 4);
        String selectedeusr=(String) model.getValueAt(clubptable.getSelectedRow(), 5);
        String selectededfrw=(String) model.getValueAt(clubptable.getSelectedRow(), 6);
        
        reght.setText(selectedfname);
        naper.setText(selectedlname);
        ytsf.setText(selectedphone);
        nates.setText(selectedsalary);
        isrdte.setText(selectedeusr);
        rtrdte.setText(selectededfrw);
    }//GEN-LAST:event_clubptableMouseClicked

    private void btnDeleteempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteempActionPerformed
          try {
            String[] options = {"Yes", "No"};
            int answer = JOptionPane.showOptionDialog(null, " Are you Sure to delete employees data ??", "Delete Options", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            if (answer == 0) {
                String id = clubptable.getValueAt(clubptable.getSelectedRow(), 0).toString();
                if (deletepropd(id)) {
                    JOptionPane.showMessageDialog(null, "Club Property Data Deleted Successfully");
                    reght.setText("");
                    naper.setText("");
                    ytsf.setText("");
                    nates.setText("");
                    isrdte.setText("");
                    retrievepropd();
                    totalpropd();
                } else {
                    JOptionPane.showMessageDialog(null, " Club Property Data Not Deleted ");
                }
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, e);
        }        
    }//GEN-LAST:event_btnDeleteempActionPerformed

    private void btnUpdateempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateempActionPerformed
          try {
            if (reght.getText().isEmpty() || naper.getText().isEmpty() || nates.getText().isEmpty() || isrdte.getText().isEmpty() || rtrdte.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No row is selected..!!\nPlease select a row to update...");
            } else {
                int index = clubptable.getSelectedRow();
                String id = clubptable.getValueAt(index, 0).toString();  //index=rownumber and 0 represents first column..

                if (updatepropd(id, reght.getText(), naper.getText(), ytsf.getText() ,nates.getText(), isrdte.getText(), rtrdte.getText())) {
                    JOptionPane.showMessageDialog(null, "Club Property Data Updated Successfully");
                    reght.setText("");
                    naper.setText("");
                    ytsf.setText("");
                    nates.setText("");
                    isrdte.setText("");
                    retrievepropd();
                    totalpropd();

                } else {
                    JOptionPane.showMessageDialog(null, "Club Property Data Not Updated ");
                }

            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }
       
    }//GEN-LAST:event_btnUpdateempActionPerformed

    private void recordpropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recordpropActionPerformed
        try {

            String sql = " INSERT INTO rental_properties (name, quantity, price)  VALUES (?,?,?)";
            con = DriverManager.getConnection("jdbc:mysql://localhost/rentalmanagementsystem", "root", "");
            pst = con.prepareStatement(sql);
            pst.setString(1, propnam.getText());
            pst.setString(2, quanam.getText());
            pst.setString(3, pricep.getText());
            
            if (propnam.getText().equals("") || quanam.getText().equals("") || pricep.getText().equals("")  ) {
                JOptionPane.showMessageDialog(null, "Some fields not filled in..!!\n Please Fill them to continue.", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Record of Rental Properties have been Successful");
                propnam.setText(" ");
                quanam.setText("");
                pricep.setText("");
            }
        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_recordpropActionPerformed

    private void btnmanageusersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmanageusersActionPerformed
         displaypanel.removeAll();
        displaypanel.add(manageusr);
        manageusr.setVisible(true);
        manageusr.repaint();
        manageusr.revalidate();   
    }//GEN-LAST:event_btnmanageusersActionPerformed

    private void btnmanagetenantsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmanagetenantsActionPerformed
         displaypanel.removeAll();
        displaypanel.add(manageclubproperties);
        manageclubproperties.setVisible(true);
        manageclubproperties.repaint();
        manageclubproperties.revalidate(); 
       
    }//GEN-LAST:event_btnmanagetenantsActionPerformed

    private void btnaddrmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddrmActionPerformed
        try{
        String sql = " INSERT INTO room (room_type, block_number, status, phonenumber)  VALUES (?,?,?,?)";
        con = DriverManager.getConnection("jdbc:mysql://localhost/rentalmanagementsystem","root","");
        pst = con.prepareStatement(sql);
        pst.setString(1,rmtype.getSelectedItem().toString());
        pst.setString(2, txtnum.getText());
        pst.setString(3, txtstat.getSelectedItem().toString());
        pst.setString(4, txtnumber.getText());
        
         if(rmtype.getSelectedIndex()==0 || txtnum.getText().equals("") || txtstat.getSelectedIndex()==0){
             JOptionPane.showMessageDialog(null,"Some fields not filled in..!!\n Please Fill them to continue.","Warning",JOptionPane.WARNING_MESSAGE);
          }       
       else {
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Recording of Rooms is Successful");
              rmtype.setSelectedIndex(0); 
              txtnum.setText(" ");
              txtstat.setSelectedIndex(0); 
              txtnumber.setText("");
        }   
        } 
        catch(HeadlessException | SQLException ex){
        JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_btnaddrmActionPerformed

    private void btnClearrmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearrmActionPerformed
              rmtype.setSelectedIndex(0); 
              txtnum.setText(" ");
              txtstat.setSelectedIndex(0);
              txtnumber.setText("");
    }//GEN-LAST:event_btnClearrmActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
          try {
            String sql = " INSERT INTO employees (Firstname, Lastname, phonenumber, worktype, salary, date_employed  )  VALUES (?,?,?,?,?,?)";
            con = DriverManager.getConnection("jdbc:mysql://localhost/rentalmanagementsystem", "root", "");
            pst = con.prepareStatement(sql);
            pst.setString(1, namtxt.getText());
            pst.setString(2, lnamtxt.getText());
            pst.setString(3, numtext.getText());
            pst.setString(4, typetext.getSelectedItem().toString());
            pst.setString(5, salarytxt.getText());
            String employedDate = ((JTextField) datechooser.getDateEditor().getUiComponent()).getText();
            pst.setString(6, employedDate);
            if (namtxt.getText().equals("") || lnamtxt.getText().equals("") || numtext.getText().equals("") || typetext.getSelectedIndex() == 0 || salarytxt.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Some fields not filled in..!!\n Please Fill them to continue.", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Registration of Employees is  Successful");
                namtxt.setText("");
                lnamtxt.setText("");
                numtext.setText("");
                salarytxt.setText("");
                typetext.setSelectedIndex(0);
                datechooser.setCalendar(null);
            }
        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                namtxt.setText("");
                lnamtxt.setText("");
                numtext.setText("");
                salarytxt.setText("");
                typetext.setSelectedIndex(0);
                datechooser.setCalendar(null);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void notxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_notxtKeyPressed
       if( evt.getKeyCode() == KeyEvent.VK_ENTER){ 
        try {
                String txtphone = notxt.getText();
                pst = con.prepareStatement("Select * from tenants where phonenumber = ?");
                pst.setString(1, txtphone);
                rs = pst.executeQuery();
                if(rs.next() == false)
                {
                    JOptionPane.showMessageDialog(this, "Tenant Phonenumber is not found");
                }
                else{
                    String fnname = rs.getString("firstname");
                    ntxt.setText(fnname.trim());
                    String lnname = rs.getString("lastname");
                    ltxt.setText(lnname.trim());
                    String rmnos = rs.getString("roomnumber");
                    rmtyt.setText(rmnos.trim());
                    paytxt.requestFocus();  
                }
            } catch (SQLException ex) {
               Logger.getLogger(Admin_Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
    }//GEN-LAST:event_notxtKeyPressed

    private void paybtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paybtnActionPerformed
        try{
        String sql = " INSERT INTO rent (phonenumber, firstname, lastName, roomnumber, amount_paid, arrears, date_paid )  VALUES (?,?,?,?,?,?,?)";
      con = DriverManager.getConnection("jdbc:mysql://localhost/rentalmanagementsystem","root","");
        pst = con.prepareStatement(sql);
        pst.setString(1,notxt.getText());
        pst.setString(2, ntxt.getText());
        pst.setString(3, ltxt.getText());
        pst.setString(4, rmtyt.getText());
        pst.setString(5, paytxt.getText());
        pst.setString(6, arrtxt.getText());
         if(notxt.getText().equals("") || ntxt.getText().equals("") || ltxt.getText().equals("")   || paytxt.getText().equals("")  || arrtxt.getText().equals("") || rmtyt.getText().equals("") ){
             JOptionPane.showMessageDialog(null,"Some fields not filled in..!!\n Please Fill them to continue.","Warning",JOptionPane.WARNING_MESSAGE);
          }
        else {
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Recording of Payment is Successful");
               notxt.setText(" ");
               ntxt.setText(" ");
               ltxt.setText("");
               paytxt.setText("");
               arrtxt.setText("");
               rmtyt.setText("");
             this.dispose();
        }
        }   
      catch(HeadlessException | SQLException ex){
       JOptionPane.showMessageDialog(null,ex);   
               }
    }//GEN-LAST:event_paybtnActionPerformed

    private void rbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnActionPerformed
               notxt.setText(" ");
               ntxt.setText(" ");
               ltxt.setText("");
               paytxt.setText("");
               arrtxt.setText("");
               rmtyt.setText("");
    }//GEN-LAST:event_rbtnActionPerformed

    private void exitbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitbtnActionPerformed
          load_home();        
    }//GEN-LAST:event_exitbtnActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        load_home();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void recordproviderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recordproviderActionPerformed
         try{
        String sql = " INSERT INTO service_providers (company_name, contacts, service_type, address ) VALUES (?,?,?,?)";
        con = DriverManager.getConnection("jdbc:mysql://localhost/rentalmanagementsystem","root","");
        pst = con.prepareStatement(sql);
        pst.setString(1,comptxt.getText());
        pst.setString(2, context.getText());
        pst.setString(3, servtxt.getSelectedItem().toString());
        pst.setString(4, address.getText());
         if(comptxt.getText().equals("") || context.getText().equals("") || servtxt.getSelectedIndex()==0 || address.getText().equals("") ){
             JOptionPane.showMessageDialog(null,"Some fields not filled in..!!\n Please Fill them to continue.","Warning",JOptionPane.WARNING_MESSAGE);
         } 
         else
         {
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Recording of Other Service Providers is Successful");
             comptxt.setText(" ");
             context.setText(" ");
             servtxt.setSelectedIndex(0);
             address.setText("");
                   }
        } 
        catch(HeadlessException | SQLException ex){
        JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_recordproviderActionPerformed

    private void provresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_provresetActionPerformed
             comptxt.setText(" ");
             context.setText(" ");
             servtxt.setSelectedIndex(0);
             address.setText("");
    }//GEN-LAST:event_provresetActionPerformed

    private void clearpropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearpropActionPerformed
                propnam.setText(" ");
                quanam.setText("");
                pricep.setText("");
    }//GEN-LAST:event_clearpropActionPerformed

    private void btncloseempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncloseempActionPerformed
             load_home();
    }//GEN-LAST:event_btncloseempActionPerformed

    private void txtstatItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_txtstatItemStateChanged
      switch (txtstat.getSelectedIndex()) {
             case 1:
                 txtnumber.setEditable(true);
                 break;
             case 2:
                 txtnumber.setEditable(false);
                 break;
             case 0:
                 txtnumber.setEditable(false);
                 break;
             default:
                 break;
         }    
    }//GEN-LAST:event_txtstatItemStateChanged

    private void usersrchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usersrchKeyReleased
         String query1 = usersrch.getText();
        filterusers(query1);
    }//GEN-LAST:event_usersrchKeyReleased

    private void adrsrchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_adrsrchKeyReleased
           String query1 = adrsrch.getText();
           filteradsmembers(query1);
    }//GEN-LAST:event_adrsrchKeyReleased

    private void btnroomdataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnroomdataActionPerformed
        retrieveattendsasd();
        totalmembats();
    }//GEN-LAST:event_btnroomdataActionPerformed

    private void attsrchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_attsrchKeyReleased
       String query1 = totalrms.getText();
           filterartds(query1); 
    }//GEN-LAST:event_attsrchKeyReleased

    private void admattendceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_admattendceMouseClicked
        
    }//GEN-LAST:event_admattendceMouseClicked

    private void prtserchdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_prtserchdKeyReleased
       String query1 = prtserchd.getText();
       filterpropd(query1);
    }//GEN-LAST:event_prtserchdKeyReleased

    private void btnretrvpayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnretrvpayActionPerformed
       
    }//GEN-LAST:event_btnretrvpayActionPerformed

    private void btnupdatepayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdatepayActionPerformed
         
    }//GEN-LAST:event_btnupdatepayActionPerformed

    private void btndeletepayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeletepayActionPerformed
      
    }//GEN-LAST:event_btndeletepayActionPerformed

    private void searchrentKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchrentKeyReleased
       String query1 = searchrent.getText();
        filterrentsad(query1);
    }//GEN-LAST:event_searchrentKeyReleased

    private void tblrentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblrentMouseClicked
        String fnam = tblrent.getValueAt(tblrent.getSelectedRow(), 1).toString();
        String mnam = tblrent.getValueAt(tblrent.getSelectedRow(), 2).toString();
        String lnam = tblrent.getValueAt(tblrent.getSelectedRow(), 3).toString();
        String datep = tblrent.getValueAt(tblrent.getSelectedRow(), 4).toString();
        String type = tblrent.getValueAt(tblrent.getSelectedRow(), 5).toString();
        String date = tblrent.getValueAt(tblrent.getSelectedRow(), 6).toString();
        String mths = tblrent.getValueAt(tblrent.getSelectedRow(), 7).toString();
        
        paynum.setText(fnam);
        fpay.setText(mnam);
        lpay.setText(lnam);
        rpay.setText(datep);
        ampay.setText(type);
        arrpay.setText(date);
        mnthp.setText(mths);
    }//GEN-LAST:event_tblrentMouseClicked

    private void providerssearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_providerssearchKeyReleased
        String query1 = providerssearch.getText();
        filterprovs(query1);
    }//GEN-LAST:event_providerssearchKeyReleased

    private void saveregulationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveregulationsActionPerformed
       try {
           Class.forName("com.mysql.jdbc.Driver");
           String sql ="UPDATE `rules_regulations` SET `rules`=? WHERE id=1";
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalmanagementsystem","root","");
           pst = con.prepareStatement(sql);
           pst.setString(1,rulesarea.getText());
           pst.executeUpdate();
           rulesarea.setText("");
           JOptionPane.showMessageDialog(null,"Rules and Regulation Successfully Saved");
       } catch (ClassNotFoundException | SQLException ex) {
           Logger.getLogger(Admin_Home.class.getName()).log(Level.SEVERE, null, ex);
       }
    }//GEN-LAST:event_saveregulationsActionPerformed

    private void clsrulesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clsrulesActionPerformed
         load_home();
    }//GEN-LAST:event_clsrulesActionPerformed

    private void viewregulationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewregulationsActionPerformed
      try {
           Class.forName("com.mysql.jdbc.Driver");
           String sql ="SELECT `rules` FROM `rules_regulations` WHERE id=1";
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalmanagementsystem","root","");
           pst = con.prepareStatement(sql);
           rs = pst.executeQuery();
           if (rs.next()){
           String rules = rs.getString("rules");
         //  rlsarea.setText("*******************************************\n");
         //  rlsarea.setText(rlsarea.getText()+"           RULES AND REGULATIONS \n");
         //  rlsarea.setText(rlsarea.getText()+"*******************************************\n");
           rlsarea.setText(rlsarea.getText()+rules);
           }
       } catch (ClassNotFoundException | SQLException ex) {
           Logger.getLogger(Admin_Home.class.getName()).log(Level.SEVERE, null, ex);
       }
    }//GEN-LAST:event_viewregulationsActionPerformed

    private void saverlsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saverlsActionPerformed
        try {
           Class.forName("com.mysql.jdbc.Driver");
           String sql ="UPDATE `rules_regulations` SET `duedates`=? WHERE id=1";
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalmanagementsystem","root","");
           pst = con.prepareStatement(sql);
           pst.setString(1,rentdts.getText());
           pst.executeUpdate();
           rentdts.setText("");
           JOptionPane.showMessageDialog(null,"Successfully Saved");
       } catch (ClassNotFoundException | SQLException ex) {
           Logger.getLogger(Admin_Home.class.getName()).log(Level.SEVERE, null, ex);
       }
    }//GEN-LAST:event_saverlsActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       try {
           Class.forName("com.mysql.jdbc.Driver");
           String sql ="SELECT `Event` FROM `calendar_of_activities` WHERE id=1";
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms","root","");
           pst = con.prepareStatement(sql);
           rs = pst.executeQuery();
           if (rs.next()){
           String rulesd = rs.getString("Event");
          adminclds.setText("******************************************************\n");
          adminclds.setText(adminclds.getText()+"   TECHNOSCIENCE CALENDAR OF ACTIVITIES \n");
          adminclds.setText(adminclds.getText()+"*******************************************\n");
          adminclds.setText(adminclds.getText()+rulesd);
           }
       } catch (ClassNotFoundException | SQLException ex) {
           Logger.getLogger(SecretaryHome.class.getName()).log(Level.SEVERE, null, ex);
       }        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
     try {
            adminclds.print();
            load_home();
            //this.dispose();
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void printregulationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printregulationsActionPerformed
        try {
            adminclds.print();
            adminclds.setText("");
            load_home();
            //this.dispose();
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }//GEN-LAST:event_printregulationsActionPerformed

    private void closeregulationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeregulationsActionPerformed
        load_home();
    }//GEN-LAST:event_closeregulationsActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        load_home();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
         load_home();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void printbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printbuttonActionPerformed
        try {
            recpt.print();
            load_home();
           // this.dispose();
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }        
    }//GEN-LAST:event_printbuttonActionPerformed

    private void receiptbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_receiptbtnActionPerformed

    }//GEN-LAST:event_receiptbtnActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
      
    }//GEN-LAST:event_jButton12ActionPerformed

    private void closepropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closepropActionPerformed
         load_home();         
    }//GEN-LAST:event_closepropActionPerformed

    private void providerstblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_providerstblMouseClicked
        
    }//GEN-LAST:event_providerstblMouseClicked

    private void genrentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genrentActionPerformed
        String FILE_NAME = "C:\\Users\\Dell\\Documents\\NetBeansProjects\\RentalHouseManagementSystem\\RentPayment.pdf";
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(new File(FILE_NAME)));
            document.open();

            document.add(new Paragraph(new Date().toString()));

            document.add(new Paragraph("RENT PAYMENT FINANCIAL REPORT"));

            document.add(new Paragraph("------------------------------------------------------------------------------------------------------------"));
            PdfPTable tablesup = new PdfPTable(7);
            tablesup.setWidthPercentage(100);
            tablesup.setSpacingBefore(10f);
            tablesup.setSpacingAfter(10f);

            tablesup.addCell("Rent id");
            tablesup.addCell("Phonenumber");
            tablesup.addCell("Firstname");
            tablesup.addCell("Lastname");
            tablesup.addCell("Amount paid");
            tablesup.addCell("Arrears");
            tablesup.addCell("Date Paid");

            Connection con;
            Statement st;
            ResultSet rs;

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalmanagementsystem", "root", "");
            st = con.createStatement();
            String query = "Select * from rent";
            rs = st.executeQuery(query);
            while (rs.next()) {
                String v1 = rs.getString("rent_id");
                String v2 = rs.getString("phonenumber");
                String v3 = rs.getString("firstname");
                String v4 = rs.getString("lastname");
                String v5 = rs.getString("amount_paid");
                String v6 = rs.getString("arrears");
                String v7 = rs.getString("date_paid");

                tablesup.addCell(v1);
                tablesup.addCell(v2);
                tablesup.addCell(v3);
                tablesup.addCell(v4);
                tablesup.addCell(v5);
                tablesup.addCell(v6);
                tablesup.addCell(v7);
                document.add(tablesup);
            }
            document.close();
            System.out.println("Done");
            JOptionPane.showMessageDialog(null, "Rent Payment Report Saved");
            JOptionPane.showMessageDialog(null, "You can Now view Report in your System Directory");
             load_home();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_genrentActionPerformed

    private void closerentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closerentActionPerformed
         load_home();       
    }//GEN-LAST:event_closerentActionPerformed

    private void genroomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genroomActionPerformed
    String FILE_NAME = "C:\\Users\\MICHAEL\\Documents\\NetBeansProjects\\TechnoScienceManagementSystem\\OtherIncomeExpenditure.pdf";
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(new File(FILE_NAME)));
            document.open();

            document.add(new Paragraph(new Date().toString()));

            document.add(new Paragraph("OTHER INCOME AND EXPENDITURE REPORT"));

            document.add(new Paragraph("------------------------------------------------------------------------------------------------------------"));
            PdfPTable tablesup = new PdfPTable(5);
            tablesup.setWidthPercentage(100);
            tablesup.setSpacingBefore(10f);
            tablesup.setSpacingAfter(10f);

            tablesup.addCell("Finacial id");
            tablesup.addCell("Record Type");
            tablesup.addCell("Source");
            tablesup.addCell("Amount");
            tablesup.addCell("Date");

            Connection con;
            Statement st;
            ResultSet rs;

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms", "root", "");
            st = con.createStatement();
            String query = "Select * from incomeexpenditures";
            rs = st.executeQuery(query);
            while (rs.next()) {
                String v1 = rs.getString("Financial_id");
                String v2 = rs.getString("type");
                String v3 = rs.getString("source");
                String v4 = rs.getString("amount");
                String v5 = rs.getString("Date");

                tablesup.addCell(v1);
                tablesup.addCell(v2);
                tablesup.addCell(v3);
                tablesup.addCell(v4);
                tablesup.addCell(v5);
                document.add(tablesup);
            }
            document.close();
            System.out.println("Done");
            JOptionPane.showMessageDialog(null, "Other Income and Expenditure Report Saved");
            JOptionPane.showMessageDialog(null, "You can Now view Report in your System Directory");
             load_home();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_genroomActionPerformed

    private void gentenantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gentenantActionPerformed
                String FILE_NAME = "C:\\Users\\MICHAEL\\Documents\\NetBeansProjects\\TechnoScienceManagementSystem\\Members.pdf";
            Document document = new Document();
     try{
         PdfWriter.getInstance(document, new FileOutputStream(new File(FILE_NAME)));
         document.open();
     
         document.add(new Paragraph(new Date().toString()));
         
         document.add(new Paragraph("TECHNOSCIENCE MEMBERS REPORT"));
          
          document.add(new Paragraph("------------------------------------------------------------------------------------------------------------"));
          PdfPTable tablesup = new PdfPTable(8);
          tablesup.setWidthPercentage(100);
          tablesup.setSpacingBefore(10f);
          tablesup.setSpacingAfter(10f);
         
         tablesup.addCell("Member id");
         tablesup.addCell("Registration Number");
         tablesup.addCell("Names");
         tablesup.addCell("Phonenumbers");
         tablesup.addCell("Course");
         tablesup.addCell("Area of Specialization");
         tablesup.addCell("Year and Semester");
         tablesup.addCell("Registration Date");
         
         Connection con;
         Statement st;
         ResultSet rs;
         Class.forName("com.mysql.jdbc.Driver");
         con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms","root","");
         st = con.createStatement();
         String query = "Select * from Members";
         rs = st.executeQuery(query);
         while(rs.next()){
           String v1 = rs.getString("Members_id");
           String v2 = rs.getString("regno");
           String v3 = rs.getString("names");
           String v4 = rs.getString("phonenumbers");
           String v5 = rs.getString("course");
           String v6 = rs.getString("area_specialization");
           String v7 = rs.getString("Year_semester");
           String v8 = rs.getString("date");
           
           tablesup.addCell(v1);
           tablesup.addCell(v2);
           tablesup.addCell(v3);
           tablesup.addCell(v4);
           tablesup.addCell(v5);
           tablesup.addCell(v6);
           tablesup.addCell(v7);
           tablesup.addCell(v8);
           document.add(tablesup);
         }
  document.close();
         System.out.println("Done");
         JOptionPane.showMessageDialog(null, "Members Report Saved"); 
         JOptionPane.showMessageDialog(null, "You can now view Report in your System Directory");
          load_home();
     } catch(Exception e){
         e.printStackTrace();
     }
    }//GEN-LAST:event_gentenantActionPerformed

    private void closetenantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closetenantActionPerformed
        load_home();
    }//GEN-LAST:event_closetenantActionPerformed

    private void roomsreportbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomsreportbtnActionPerformed
        displaypanel.removeAll();
        displaypanel.add(ContributionReport);
        ContributionReport.setVisible(true);
        ContributionReport.repaint();
       ContributionReport.revalidate();        
    }//GEN-LAST:event_roomsreportbtnActionPerformed

    private void reptenantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reptenantActionPerformed
         displaypanel.removeAll();
         displaypanel.add(MemberReport);
         MemberReport.setVisible(true);
         MemberReport.repaint();
         MemberReport.revalidate();  
    }//GEN-LAST:event_reptenantActionPerformed

    private void viewcompActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewcompActionPerformed
        try {
           Class.forName("com.mysql.jdbc.Driver");
           String sql ="SELECT * FROM `complains`";
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalmanagementsystem","root","");
           pst = con.prepareStatement(sql);
           rs = pst.executeQuery();
           complainsview.setText("*******************************************\n");
           complainsview.setText(complainsview.getText()+"          SENT COMPLAINS \n");
           complainsview.setText(complainsview.getText()+"*******************************************\n");
           complainsview.setText(complainsview.getText()+"NO.    COMPLAIN\n");
           while (rs.next()){
           String id = rs.getString("complain_id");
           String compl = rs.getString("compl");
           String date = rs.getString("date");
           complainsview.setText("\n"+complainsview.getText()+ id+"    "+compl+" DATE---->> "+date+"\n");
           }
       } catch (ClassNotFoundException | SQLException ex) {
           Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
       }
    }//GEN-LAST:event_viewcompActionPerformed

    private void clsbtnsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clsbtnsActionPerformed
         load_home();
    }//GEN-LAST:event_clsbtnsActionPerformed

    private void btnCloserm1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloserm1ActionPerformed
          load_home();
    }//GEN-LAST:event_btnCloserm1ActionPerformed

    private void btnClearrm1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearrm1ActionPerformed
           rmupd.setText("");
           phup.setText("");
           txtstatup.setSelectedIndex(0);        
    }//GEN-LAST:event_btnClearrm1ActionPerformed

    private void btnaddrm1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddrm1ActionPerformed
           try {
           Class.forName("com.mysql.jdbc.Driver");
           String sql6 ="UPDATE `room` SET `phonenumber`=?, `status`=?  WHERE room_number=?";
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalmanagementsystem","root","");
           pst = con.prepareStatement(sql6);
           pst.setString(1, phup.getText());
           pst.setString(2, txtstatup.getSelectedItem().toString());
           pst.setString(3, rmupd.getText());
           pst.executeUpdate();
           
           rmupd.setText("");
           phup.setText("");
           txtstatup.setSelectedIndex(0);
           JOptionPane.showMessageDialog(null,"Room Details Successfully Updated");
       } catch (ClassNotFoundException | SQLException ex) {
           Logger.getLogger(Admin_Home.class.getName()).log(Level.SEVERE, null, ex);
       }               
    }//GEN-LAST:event_btnaddrm1ActionPerformed

    private void txtstatupItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_txtstatupItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtstatupItemStateChanged

    private void rmupdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rmupdKeyPressed
          if( evt.getKeyCode() == KeyEvent.VK_ENTER){ 
        try {
                String txtrms = rmupd.getText();
                pst = con.prepareStatement("Select * from tenants where roomnumber = ?");
                pst.setString(1, txtrms);
                rs = pst.executeQuery();
                if(rs.next() == false)
                {
                    JOptionPane.showMessageDialog(this, "Room Number is not found");
                    JOptionPane.showMessageDialog(this, "Or Tenant Has Occupied the Room");
                }
                else{
                    String phoneupd = rs.getString("phonenumber");
                    phup.setText(phoneupd.trim());
                    txtstatup.setSelectedIndex(1);
                    
                     
                }
            } catch (SQLException ex) {
               Logger.getLogger(Admin_Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        }          
    }//GEN-LAST:event_rmupdKeyPressed

    private void closeprovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeprovActionPerformed
         load_home();
    }//GEN-LAST:event_closeprovActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
         load_home();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void closeroomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeroomActionPerformed
         load_home();
    }//GEN-LAST:event_closeroomActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        load_home();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        retreaveattendeventsad();
        totalevtsattds();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void evrattendMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_evrattendMouseClicked
        
    }//GEN-LAST:event_evrattendMouseClicked

    private void saveregulations1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveregulations1ActionPerformed
       try {
           Class.forName("com.mysql.jdbc.Driver");
           String sql ="INSERT INTO notices (noticename)  VALUES(?)";
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms","root","");
           pst = con.prepareStatement(sql);
           pst.setString(1,adminnotic.getText());
             if (adminnotic.getText().isEmpty() ){
             JOptionPane.showMessageDialog(null,"Please Enter Notice to Upload");    
             }
             else{ 
           pst.executeUpdate();
           adminnotic.setText("");
           JOptionPane.showMessageDialog(null,"Notice Successfully Uploaded");
             }
       } catch (ClassNotFoundException | SQLException ex) {
           Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
       }
    }//GEN-LAST:event_saveregulations1ActionPerformed

    private void clsrules1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clsrules1ActionPerformed
        load_home();
    }//GEN-LAST:event_clsrules1ActionPerformed

    private void saveregulations2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveregulations2ActionPerformed
          try {
           Class.forName("com.mysql.jdbc.Driver");
           String sql ="SELECT `rules` FROM `rules_regulations` WHERE id=1";
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalmanagementsystem","root","");
           pst = con.prepareStatement(sql);
           rs = pst.executeQuery();
           if (rs.next()){
           String rules = rs.getString("rules");
        //   rulesarea.setText("******************************************************\n");
         //  rulesarea.setText(rulesarea.getText()+"           RULES AND REGULATIONS \n");
        //   rulesarea.setText(rulesarea.getText()+"*******************************************\n");
           rulesarea.setText(rulesarea.getText()+rules);
           }
       } catch (ClassNotFoundException | SQLException ex) {
           Logger.getLogger(Admin_Home.class.getName()).log(Level.SEVERE, null, ex);
       }        
    }//GEN-LAST:event_saveregulations2ActionPerformed

    private void retrievedtsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retrievedtsActionPerformed
         try {
           Class.forName("com.mysql.jdbc.Driver");
           String sql ="SELECT `duedates` FROM `rules_regulations` WHERE id=1";
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalmanagementsystem","root","");
           pst = con.prepareStatement(sql);
           rs = pst.executeQuery();
           if (rs.next()){
           String rules = rs.getString("duedates");
          // rentdts.setText("*******************************************\n");
          // rentdts.setText(rentdts.getText()+"           PAYMENTS AND DUE DATES  \n");
          // rentdts.setText(rentdts.getText()+"*******************************************\n");
           rentdts.setText(rentdts.getText()+rules);
           }
       } catch (ClassNotFoundException | SQLException ex) {
           Logger.getLogger(Admin_Home.class.getName()).log(Level.SEVERE, null, ex);
       }       
    }//GEN-LAST:event_retrievedtsActionPerformed

    private void savfeedbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savfeedbActionPerformed
         viewadmnoticets();                        
    }//GEN-LAST:event_savfeedbActionPerformed

    private void clsfeedbcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clsfeedbcActionPerformed
        load_home();
    }//GEN-LAST:event_clsfeedbcActionPerformed

    private void btnaddrentalpropertiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddrentalpropertiesActionPerformed
        displaypanel.removeAll();
        displaypanel.add(Assignclubproperty);
        Assignclubproperty.setVisible(true);
        Assignclubproperty.repaint();
        Assignclubproperty.revalidate();
        
    }//GEN-LAST:event_btnaddrentalpropertiesActionPerformed

    private void btnaddrentalproperties1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddrentalproperties1ActionPerformed
         displaypanel.removeAll();
        displaypanel.add(Assignclubproperty);
        Assignclubproperty.setVisible(true);
        Assignclubproperty.repaint();
        Assignclubproperty.revalidate(); 
    }//GEN-LAST:event_btnaddrentalproperties1ActionPerformed

    private void reppayment1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reppayment1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_reppayment1ActionPerformed

    private void fanametxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fanametxtKeyPressed
           if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                String txtregv = fanametxt.getText();
                pst = con.prepareStatement("Select * from members where regno = ?");
                pst.setString(1, txtregv);
                rs = pst.executeQuery();
                if (rs.next() == false) {
                    JOptionPane.showMessageDialog(this, "The Registration Number You Entered is not found try Again");
                } else {
                    String name = rs.getString("names");
                     lasatntxt.setText(name.trim());
                     String regist = rs.getString("phonenumbers");
                     pnatxt.setText(regist.trim());
                     occtxt.getFocusListeners();
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }                  
    }//GEN-LAST:event_fanametxtKeyPressed

    private void btnroomdata1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnroomdata1ActionPerformed
        try {
            admattendce.print();
        } catch (PrinterException ex) {
            Logger.getLogger(Admin_Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnroomdata1ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        try {
            evrattend.print();
        } catch (PrinterException ex) {
            Logger.getLogger(Admin_Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void evrsrchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_evrsrchKeyReleased
        
        String query1 = evrsrch.getText();
        filterrentsad(query1);
    }//GEN-LAST:event_evrsrchKeyReleased

    private void btnretrieveprovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnretrieveprovActionPerformed
        retreaveprovs();
    }//GEN-LAST:event_btnretrieveprovActionPerformed

    private void btnCloseprovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseprovActionPerformed
        load_home();
    }//GEN-LAST:event_btnCloseprovActionPerformed

    private void btnretrieveprov1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnretrieveprov1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnretrieveprov1ActionPerformed

    private void providerstbl1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_providerstbl1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_providerstbl1MouseClicked

    private void btnCloseprov1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseprov1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCloseprov1ActionPerformed

    private void btnretrieveprov2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnretrieveprov2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnretrieveprov2ActionPerformed

    private void providerssearch1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_providerssearch1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_providerssearch1KeyReleased

    private void btnretrieveprov3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnretrieveprov3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnretrieveprov3ActionPerformed

    private void retrievebtna1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retrievebtna1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_retrievebtna1ActionPerformed

    private void btnCloseMaintainUsersasd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseMaintainUsersasd1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCloseMaintainUsersasd1ActionPerformed

    private void incexpdsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_incexpdsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_incexpdsMouseClicked

    private void searchicmKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchicmKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_searchicmKeyReleased

    private void retrievebtna2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retrievebtna2ActionPerformed
        
    }//GEN-LAST:event_retrievebtna2ActionPerformed

    private void insserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insserActionPerformed
//        try {
//            String query = "INSERT INTO clinicservices (servtype,Amount) VALUES(?,?)";
//            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
//            pst = con.prepareStatement(query);
//            pst.setString(1, txtstr.getText());
//            pst.setString(2, txtamrd.getText());
//
//            if (txtamrd.getText().isEmpty() || txtstr.getText().isEmpty()) {
//                JOptionPane.showMessageDialog(this, "Some fields are empty..!!\n Please fill them to continue");
//            } else {
//                pst.executeUpdate();
//
//                JOptionPane.showMessageDialog(this, "Computer Clinic Data Recorded Successifully");
//                txtstr.setText("");
//                txtamrd.setText("");
//                retrieveservices();
//                totalservices();
//            }
//
//        } catch (SQLException | HeadlessException e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
    }//GEN-LAST:event_insserActionPerformed

    private void btnUpdatesserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdatesserActionPerformed
//        try {
//            if (txtstr.getText().isEmpty() || txtamrd.getText().isEmpty()) {
//                JOptionPane.showMessageDialog(null, "No row is selected..!!\nPlease select a row to update...");
//            } else {
//                int index = eventtables.getSelectedRow();
//                String id = eventtables.getValueAt(index, 0).toString();  //index=rownumber and 0 represents first column..
//
//                if (updateservices(id, txtstr.getText(), txtamrd.getText())) {
//                    JOptionPane.showMessageDialog(null, "Computer Clinics Data Updated Successfully");
//                    txtstr.setText("");
//                    txtamrd.setText("");
//                    retrieveservices();
//                    totalservices();
//                } else {
//                    JOptionPane.showMessageDialog(null, "Computer Clinic Data is Not Updated ");
//                }
//            }
//        } catch (HeadlessException e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
    }//GEN-LAST:event_btnUpdatesserActionPerformed

    private void btnDeletessertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletessertActionPerformed
//        try {
//            String[] options = {"Yes", "No"};
//            int answer = JOptionPane.showOptionDialog(null, " Are you Sure to delete ??", "Delete Options", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
//
//            if (answer == 0) {
//                String id = eventtables.getValueAt(eventtables.getSelectedRow(), 0).toString();
//                if (deleteservices(id)) {
//                    JOptionPane.showMessageDialog(null, "Computer Services Data Deleted Successfully");
//                    txtstr.setText("");
//                    txtamrd.setText("");
//                    retrieveservices();
//                    totalservices();
//                } else {
//                    JOptionPane.showMessageDialog(null, " Computer Services Data is Not Deleted ");
//                }
//            }
//        } catch (HeadlessException e) {
//            JOptionPane.showMessageDialog(this, e);
//        }
    }//GEN-LAST:event_btnDeletessertActionPerformed

    private void btnClosessertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClosessertActionPerformed
        load_home();
    }//GEN-LAST:event_btnClosessertActionPerformed

    private void eventtablesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eventtablesMouseClicked
        String name = eventtables.getValueAt(eventtables.getSelectedRow(), 1).toString();
        String pno = eventtables.getValueAt(eventtables.getSelectedRow(), 2).toString();

        txtstr.setText(name);
        txtamrd.setText(pno);

    }//GEN-LAST:event_eventtablesMouseClicked

    private void cmpservicesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmpservicesKeyReleased
//        String query1 = cmpservices.getText();
//        filterservices(query1);
    }//GEN-LAST:event_cmpservicesKeyReleased

    private void retrieveserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retrieveserActionPerformed
//        retrieveservices();
//        totalservices();
    }//GEN-LAST:event_retrieveserActionPerformed

    private void btnUpdatesser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdatesser1ActionPerformed
        try {
            eventtables.print();
        } catch (PrinterException ex) {
            Logger.getLogger(ProjectManagerHome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnUpdatesser1ActionPerformed

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
            java.util.logging.Logger.getLogger(Admin_Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin_Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin_Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin_Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin_Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Addemployees;
    private javax.swing.JPanel Addrentalproperties;
    private javax.swing.JPanel Addrooms;
    private javax.swing.JPanel AddserviceProviders;
    private javax.swing.JPanel Assignclubproperty;
    private javax.swing.JPanel AttendanceReport;
    private javax.swing.JPanel ContributionReport;
    private javax.swing.JPanel ManageIncomeExp;
    private javax.swing.JPanel MemberReport;
    private javax.swing.JPanel Paymentform;
    private javax.swing.JPanel Receipt;
    private javax.swing.JPanel UpdateRooms;
    private javax.swing.JPanel UploadNotices;
    private javax.swing.JPanel ViewCalendarActivites;
    private javax.swing.JPanel ViewComplains;
    private javax.swing.JPanel ViewNotices;
    private javax.swing.JPanel ViewRules;
    private javax.swing.JPanel Viewclubattendance;
    private javax.swing.JPanel Viewmembersconbasedtype;
    private javax.swing.JPanel Viewmemberscontyear;
    private javax.swing.JTextArea address;
    private javax.swing.JLabel addusrlabel;
    private javax.swing.JTable admattendce;
    private javax.swing.JTextArea adminclds;
    private javax.swing.JTextArea adminnotic;
    private javax.swing.JTextArea admviewntc;
    private javax.swing.JTextField adrsrch;
    private javax.swing.JTable advmems;
    private javax.swing.JTextField ampay;
    private javax.swing.JTextField amtr;
    private javax.swing.JLabel ans;
    private javax.swing.JTextField ansadtxt;
    private javax.swing.JLabel answerlabel;
    private javax.swing.JTextField answertxtad;
    private javax.swing.JTextField arar;
    private javax.swing.JTextField arrpay;
    private javax.swing.JTextField arrtxt;
    private javax.swing.JTextField attsrch;
    private javax.swing.JLabel blrm;
    private javax.swing.JLabel blrm1;
    private javax.swing.JButton btnAddUserad;
    private javax.swing.JButton btnAddusers;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnClearadUser;
    private javax.swing.JButton btnClearrm;
    private javax.swing.JButton btnClearrm1;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnCloseMaintainUsersasd;
    private javax.swing.JButton btnCloseMaintainUsersasd1;
    private javax.swing.JButton btnCloseadNewUser;
    private javax.swing.JButton btnCloseprov;
    private javax.swing.JButton btnCloseprov1;
    private javax.swing.JButton btnCloserange;
    private javax.swing.JButton btnCloserm;
    private javax.swing.JButton btnCloserm1;
    private javax.swing.JButton btnClosessert;
    private javax.swing.JButton btnClosetenant;
    private javax.swing.JButton btnDeleteUserasd;
    private javax.swing.JButton btnDeleteemp;
    private javax.swing.JButton btnDeletessert;
    private javax.swing.JButton btnGetTenants;
    private javax.swing.JPanel btnGroup;
    private javax.swing.JButton btnLogOut;
    private javax.swing.JButton btnReloademp;
    private javax.swing.JButton btnUpdateUserInfoas;
    private javax.swing.JButton btnUpdateemp;
    private javax.swing.JButton btnUpdatesser;
    private javax.swing.JButton btnUpdatesser1;
    private javax.swing.JButton btnaddrentalproperties;
    private javax.swing.JButton btnaddrentalproperties1;
    private javax.swing.JButton btnaddrm;
    private javax.swing.JButton btnaddrm1;
    private javax.swing.JButton btncloseemp;
    private javax.swing.JButton btndeletepay;
    private javax.swing.JButton btnmanagetenants;
    private javax.swing.JButton btnmanageusers;
    private javax.swing.JButton btnretrieveprov;
    private javax.swing.JButton btnretrieveprov1;
    private javax.swing.JButton btnretrieveprov2;
    private javax.swing.JButton btnretrieveprov3;
    private javax.swing.JButton btnretrvpay;
    private javax.swing.JButton btnroomdata;
    private javax.swing.JButton btnroomdata1;
    private javax.swing.JButton btnupdatepay;
    private javax.swing.JButton buttoncls;
    private javax.swing.JButton clearprop;
    private javax.swing.JButton closeprop;
    private javax.swing.JButton closeprov;
    private javax.swing.JButton closeregulations;
    private javax.swing.JButton closerent;
    private javax.swing.JButton closeroom;
    private javax.swing.JButton closetenant;
    private javax.swing.JButton clsbtns;
    private javax.swing.JButton clsfeedbc;
    private javax.swing.JButton clsrules;
    private javax.swing.JButton clsrules1;
    private javax.swing.JTable clubptable;
    private javax.swing.JTextField cmpservices;
    private javax.swing.JTextArea complainsview;
    private javax.swing.JTextField comptxt;
    private javax.swing.JPasswordField confirmadtxt;
    private javax.swing.JLabel confpasslabel;
    private javax.swing.JTextField context;
    private com.toedter.calendar.JDateChooser datechooser;
    private javax.swing.JTextField datetxt;
    private javax.swing.JButton delete;
    private javax.swing.JPanel displaypanel;
    private javax.swing.JTable eventtables;
    private javax.swing.JTable evrattend;
    private javax.swing.JTextField evrsrch;
    private javax.swing.JButton exitbtn;
    private javax.swing.JTextField fanametxt;
    private com.toedter.calendar.JDateChooser filtdtes;
    private com.toedter.calendar.JDateChooser filterattend;
    private javax.swing.JLabel fname;
    private javax.swing.JTextField fpay;
    private javax.swing.JTextField ftxt;
    private javax.swing.JButton genrent;
    private javax.swing.JButton genroom;
    private javax.swing.JButton gentenant;
    private javax.swing.JPanel homepanel;
    private javax.swing.JTable incexpds;
    private javax.swing.JButton insser;
    private javax.swing.JTextField isrdte;
    private com.toedter.calendar.JDateChooser issuedat;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField lasatntxt;
    private javax.swing.JLabel lblEmpfname;
    private javax.swing.JLabel lblEmplname;
    private javax.swing.JLabel lblEmppno;
    private javax.swing.JLabel lblEmpsal;
    private javax.swing.JLabel lblEusername;
    private javax.swing.JLabel lblEusername1;
    private javax.swing.JLabel lblFilldet;
    private javax.swing.JLabel lblFilldet1;
    private javax.swing.JLabel lblReports2;
    private javax.swing.JLabel lblavailablestock;
    private javax.swing.JLabel lblbuyingprice;
    private javax.swing.JLabel lblprodname;
    private javax.swing.JLabel lblread;
    private javax.swing.JLabel lblread1;
    private javax.swing.JLabel lblread2;
    private javax.swing.JLabel lblrent;
    private javax.swing.JLabel lblroom;
    private javax.swing.JLabel lblsellingprice;
    private javax.swing.JLabel lblsellingprice1;
    private javax.swing.JLabel lblsellingprice2;
    private javax.swing.JLabel lblstat;
    private javax.swing.JLabel lblstat1;
    private javax.swing.JLabel lbltenant;
    private javax.swing.JLabel lblusername;
    private javax.swing.JLabel lblusername1;
    private javax.swing.JLabel lbnum;
    private javax.swing.JLabel lbnum1;
    private javax.swing.JLabel lname;
    private javax.swing.JTextField lnamtxt;
    private javax.swing.JLabel loginlabel;
    private javax.swing.JTextField lpay;
    private javax.swing.JTextField ltext;
    private javax.swing.JTextField ltxt;
    private javax.swing.JPanel manageclubproperties;
    private javax.swing.JPanel manageeventattendance;
    private javax.swing.JPanel managemembers;
    private javax.swing.JPanel managerent;
    private javax.swing.JPanel manageservices;
    private javax.swing.JPanel manageusr;
    private javax.swing.JTextField mnthp;
    public javax.swing.JLabel myusername;
    private javax.swing.JTextField nameadmtxt;
    private javax.swing.JTextField namtxt;
    private javax.swing.JTextField naper;
    private javax.swing.JTextField nates;
    private javax.swing.JPanel newusr;
    private javax.swing.JTextField notxt;
    private javax.swing.JTextField ntxt;
    private javax.swing.JLabel num;
    private javax.swing.JLabel num1;
    private javax.swing.JLabel num2;
    private javax.swing.JTextField numbertxtads;
    private javax.swing.JLabel numrent;
    private javax.swing.JLabel numrent1;
    private javax.swing.JLabel numrent2;
    private javax.swing.JLabel numrent3;
    private javax.swing.JLabel numrent4;
    private javax.swing.JLabel numrent6;
    private javax.swing.JLabel numrent9;
    private javax.swing.JTextField numtext;
    private javax.swing.JLabel occlabel;
    private javax.swing.JComboBox<String> occtxt;
    private javax.swing.JLabel pass;
    private javax.swing.JPasswordField passadtxt;
    private javax.swing.JLabel passwordlabel;
    private javax.swing.JButton paybtn;
    private javax.swing.JTextField paynum;
    private javax.swing.JTextField paytxt;
    private javax.swing.JTextField phoneadtxt;
    private javax.swing.JLabel phonelabel;
    private javax.swing.JTextField phtxt;
    private javax.swing.JTextField phup;
    private javax.swing.JLabel plabel;
    private javax.swing.JTextField pnatxt;
    private javax.swing.JLabel position;
    private javax.swing.JTextField positiontxtads;
    private javax.swing.JTextField pricep;
    private javax.swing.JButton printbutton;
    private javax.swing.JButton printregulations;
    private javax.swing.JLabel proddetails;
    private javax.swing.JLabel proplbl;
    private javax.swing.JTextField propnam;
    private javax.swing.JTextField providerssearch;
    private javax.swing.JTextField providerssearch1;
    private javax.swing.JTable providerstbl;
    private javax.swing.JTable providerstbl1;
    private javax.swing.JButton provreset;
    private javax.swing.JTextField prtserchd;
    private javax.swing.JTextField quanam;
    private javax.swing.JTextField questiontxtad;
    private javax.swing.JButton rbtn;
    private javax.swing.JButton receiptbtn;
    private javax.swing.JButton recordprop;
    private javax.swing.JButton recordprovider;
    private javax.swing.JTextArea recpt;
    private javax.swing.JTextField reght;
    private javax.swing.JButton registerbtn;
    private javax.swing.JTextArea rentdts;
    private javax.swing.JButton reppayment1;
    private javax.swing.JButton reptenant;
    private javax.swing.JButton retrievebtna;
    private javax.swing.JButton retrievebtna1;
    private javax.swing.JButton retrievebtna2;
    private javax.swing.JButton retrievedts;
    private javax.swing.JButton retrieveser;
    private com.toedter.calendar.JDateChooser returndat;
    private javax.swing.JTextArea rlsarea;
    private javax.swing.JComboBox<String> rmtype;
    private javax.swing.JTextField rmtyt;
    private javax.swing.JTextField rmupd;
    private javax.swing.JComboBox roleadtxt;
    private javax.swing.JLabel rolelabel;
    private javax.swing.JLabel roomno;
    private javax.swing.JLabel roomno1;
    private javax.swing.JButton roomsreportbtn;
    private javax.swing.JTextField rpay;
    private javax.swing.JTextField rtrdte;
    private javax.swing.JTextArea rulesarea;
    private javax.swing.JTextField salarytxt;
    private javax.swing.JButton saveregulations;
    private javax.swing.JButton saveregulations1;
    private javax.swing.JButton saveregulations2;
    private javax.swing.JButton saverls;
    private javax.swing.JButton savfeedb;
    private javax.swing.JTextField searchicm;
    private javax.swing.JTextField searchrent;
    private javax.swing.JLabel security;
    private javax.swing.JComboBox<String> securityboxad;
    private javax.swing.JLabel securitylabel;
    private javax.swing.JComboBox<String> selyrs;
    private javax.swing.JComboBox<String> servtxt;
    private javax.swing.JLabel softwareName;
    private javax.swing.JLabel softwaredesc;
    private javax.swing.JLabel softwaredesc1;
    private javax.swing.JLabel softwarename;
    private javax.swing.JLabel softwarename1;
    private javax.swing.JLabel stockmgmt;
    private javax.swing.JTable tblrent;
    private javax.swing.JTextField totalclinc;
    private javax.swing.JTextField totalprts;
    private javax.swing.JTextField totalrms;
    private javax.swing.JTextField totalrtsk;
    private javax.swing.JTextField totalsystem;
    private javax.swing.JTextField totvads;
    private javax.swing.JTextField txtamrd;
    private javax.swing.JTextField txtnameadmins;
    private javax.swing.JTextField txtnum;
    private javax.swing.JTextField txtnumber;
    private javax.swing.JTextField txtpasswordads;
    private javax.swing.JComboBox<String> txtstat;
    private javax.swing.JComboBox<String> txtstatup;
    private javax.swing.JTextField txtstr;
    private javax.swing.JTextField txtusernameadmn;
    private javax.swing.JComboBox<String> typetext;
    private javax.swing.JTextField typetxt;
    private javax.swing.JButton update;
    private javax.swing.JTextField usernameadmtxt;
    private javax.swing.JLabel usernamelabel;
    private javax.swing.JLabel usernamelabel1;
    private javax.swing.JTextField usersrch;
    private javax.swing.JTable usertable;
    private javax.swing.JLabel usrmgmt;
    private javax.swing.JButton viewcomp;
    private javax.swing.JButton viewregulations;
    private javax.swing.JPanel writeduedates;
    private javax.swing.JPanel writtingrrules;
    private javax.swing.JTextField ytr;
    private javax.swing.JTextField ytsf;
    // End of variables declaration//GEN-END:variables

    
    
}
