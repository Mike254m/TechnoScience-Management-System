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
public class SecretaryHome extends javax.swing.JFrame {
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
    
    public SecretaryHome() {
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
           Logger.getLogger(SecretaryHome.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SQLException ex) {
            Logger.getLogger(SecretaryHome.class.getName()).log(Level.SEVERE, null, ex);
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
        private void retrievemembers() {
          try {
            String sql = "SELECT * FROM members";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            model = (DefaultTableModel) memberstable.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String idm = rs.getString(1);
                String reg = rs.getString(2);
                String nam = rs.getString(3);
                String phonen = rs.getString(4);
                String crs = rs.getString(5);
                String spex = rs.getString(6);
                String ys = rs.getString(7);
                String datess = rs.getString(8);
                model.addRow(new String[]{idm, reg, nam, phonen, crs, spex,ys, datess});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

        public Boolean updatemembers(String id, String rexm, String namz, String pnx, String crz, String arx, String yrm, String datemem) {
        String query = "UPDATE members SET regno='" + rexm + "', names='" + namz + "',phonenumbers='" + pnx + "',course='" + crz+ "', area_specialization ='"+ arx+"', Year_semester = '" +yrm+"', date='" + datemem + "' WHERE members_id = '" + id + "'";
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

        public Boolean deletemembers(String id) {
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
    private void filtermembers(String query) {
        DefaultTableModel model2 =  (DefaultTableModel) memberstable.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model2);
        memberstable.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }
         
        private void totalmembers() {
        try {
            String sql = "SELECT COUNT(members_id)totmem FROM members";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
             if (rs.next()){
              int total =Integer.parseInt(rs.getString("totmem")) ;
              totalms.setText(String.valueOf(total));
           }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        }
     private void retrieveattends() {
        try {
            String sql = "SELECT * FROM members_attendance";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) tableattendance.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String id = rs.getString(1);
                String regnumber = rs.getString(2);
                String name = rs.getString(3);
                String phoneatt = rs.getString(4);
                String courst = rs.getString(5);
                String yrst = rs.getString(6);
                String statz = rs.getString(7);
                model.addRow(new String[]{id, regnumber, name, phoneatt, courst, yrst, statz});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
   
    public Boolean deleteattendance(String id) {
        String sql = "DELETE FROM members_attendance WHERE Attendance_id  ='" + id + "'";
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
        private void filterattendance(String query) {
        DefaultTableModel model3 =  (DefaultTableModel) tableattendance.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model3);
        tableattendance.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
          }
         private void totalmattend() {
        try {
            String sql = "SELECT COUNT(Attendance_id)totalattens FROM members_attendance";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
             if (rs.next()){
              int total =Integer.parseInt(rs.getString("totalattens")) ;
              totalzattend.setText(String.valueOf(total));
           }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
         }
       private void retrievefacilitators() {
        try {
            String sql = "SELECT * FROM facilitators";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            model = (DefaultTableModel) tblfacilitators.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String id = rs.getString(1);
                String namefx = rs.getString(2);
                String phonezs = rs.getString(3);
                String nmevtds = rs.getString(4);
                String dtsevn = rs.getString(5);
               String date_employed = rs.getString(6);
                model.addRow(new String[]{id, namefx, phonezs, nmevtds, dtsevn, date_employed});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
   public Boolean updatefacilitators(String id, String nmf, String phnf, String eventf, String dtf, String datab) {
        String query = "UPDATE facilitators SET names='" + nmf + "', phonenumber='" + phnf + "' , event_name ='" + eventf + "', event_date='" + dtf + "', bio='" + datab + "', WHERE number_id= '" + id + "'";
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
     public Boolean deletefacilitators(String id) {
        String sql = "DELETE FROM facilitators WHERE number_id  ='" + id + "'";
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
        private void filterfacilitators(String query) {
        DefaultTableModel model3 =  (DefaultTableModel) tblfacilitators.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model3);
        tblfacilitators.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }
        private void totalfacilitators() {
        try {
            String sql = "SELECT COUNT(number_id)fca FROM facilitators";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
             if (rs.next()){
              int total =Integer.parseInt(rs.getString("fca")) ;
              totalfacl.setText(String.valueOf(total));
           }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
   
      private void retreaveattendevents() {
        try {
            String query = "SELECT * FROM eventattendance";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            model = (DefaultTableModel) eventAttendanceTable.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String provad_id = rs.getString(1);
                String renatd = rs.getString(2);
                String protad_name = rs.getString(3);
                String cont = rs.getString(4);
                String mail = rs.getString(5);
                String evtdre = rs.getString(6);
                String evrtd = rs.getString(7);
                String revs = rs.getString(8);
                model.addRow(new String[]{provad_id,renatd, protad_name, cont, mail, evtdre,evrtd, revs});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public Boolean deleteattendevents(String attser_id) {
        String sql = "DELETE FROM eventattendance WHERE id='" + attser_id + "'";
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
    private void filterattendevents(String query) {
        DefaultTableModel model9 = (DefaultTableModel) eventAttendanceTable.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model9);
        eventAttendanceTable.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }

    public Boolean updateattendevents(String p_id, String pareg, String panem, String pnamt, String pemail, String panam, String pasdt, String pastus) {
        String query = "UPDATE eventattendance  SET regnumber='" + pareg + "',names='" + panem+ "', phonenumber='" + pnamt + "', email='" + pemail + "', eventname='" + panam + "', eventdate='" + pasdt + "', status='" + pastus + "' WHERE id= '" + p_id + "'";
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
          
        
               private void totalattendeventss() {
        try {
            String sql = "SELECT COUNT(Event_id)totalaven FROM eventattendance";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
             if (rs.next()){
              int totalattr =Integer.parseInt(rs.getString("totalaven")) ;
              totattevtd.setText(String.valueOf(totalattr));
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
            DefaultTableModel model = (DefaultTableModel) proprents.getModel();
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
        DefaultTableModel model2 =  (DefaultTableModel) proprents.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model2);
        proprents.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    } 
        
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        displaypanel = new javax.swing.JPanel();
        RegisterMembers = new javax.swing.JPanel();
        addusrlabel = new javax.swing.JLabel();
        usernamelabel = new javax.swing.JLabel();
        regntxts = new javax.swing.JTextField();
        passwordlabel = new javax.swing.JLabel();
        btnAddUser = new javax.swing.JButton();
        rolelabel = new javax.swing.JLabel();
        confpasslabel = new javax.swing.JLabel();
        btnClearUser = new javax.swing.JButton();
        btnCloseNewUser = new javax.swing.JButton();
        crxs = new javax.swing.JComboBox();
        phonelabel = new javax.swing.JLabel();
        namzs = new javax.swing.JTextField();
        securitylabel = new javax.swing.JLabel();
        arsps = new javax.swing.JComboBox<>();
        answerlabel = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        phnts = new javax.swing.JTextField();
        yerss = new javax.swing.JComboBox<>();
        dateregs = new com.toedter.calendar.JDateChooser();
        manageMembers = new javax.swing.JPanel();
        retrieve = new javax.swing.JButton();
        lblusername = new javax.swing.JLabel();
        txtmreg = new javax.swing.JTextField();
        pass = new javax.swing.JLabel();
        txtphonem = new javax.swing.JTextField();
        btnUpdateUserInfo = new javax.swing.JButton();
        btnDeleteUser = new javax.swing.JButton();
        btnCloseMaintainUsers = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        memberstable = new javax.swing.JTable();
        num = new javax.swing.JLabel();
        namentxts = new javax.swing.JTextField();
        position = new javax.swing.JLabel();
        coursemtxts = new javax.swing.JTextField();
        security = new javax.swing.JLabel();
        ans = new javax.swing.JLabel();
        speztxts = new javax.swing.JTextField();
        ysstxt = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        memberssrch = new javax.swing.JTextField();
        ans1 = new javax.swing.JLabel();
        rgss = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        totalms = new javax.swing.JTextField();
        AddAttendance = new javax.swing.JPanel();
        proddetails = new javax.swing.JLabel();
        fname = new javax.swing.JLabel();
        sregtxt = new javax.swing.JTextField();
        lname = new javax.swing.JLabel();
        snametx = new javax.swing.JTextField();
        plabel = new javax.swing.JLabel();
        snumtx = new javax.swing.JTextField();
        occlabel = new javax.swing.JLabel();
        scrst = new javax.swing.JTextField();
        btnClosen = new javax.swing.JButton();
        btnsclers = new javax.swing.JButton();
        registerbtn = new javax.swing.JButton();
        statw = new javax.swing.JComboBox<>();
        roomno1 = new javax.swing.JLabel();
        occlabel1 = new javax.swing.JLabel();
        ysrs = new javax.swing.JTextField();
        update = new javax.swing.JButton();
        manageattendance = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableattendance = new javax.swing.JTable();
        btnGetTenants = new javax.swing.JButton();
        lblprodname = new javax.swing.JLabel();
        regas = new javax.swing.JTextField();
        lblavailablestock = new javax.swing.JLabel();
        nmesa = new javax.swing.JTextField();
        lblbuyingprice = new javax.swing.JLabel();
        phantxt = new javax.swing.JTextField();
        lblsellingprice = new javax.swing.JLabel();
        crsza = new javax.swing.JTextField();
        delete = new javax.swing.JButton();
        btnClosetenant = new javax.swing.JButton();
        lblsellingprice1 = new javax.swing.JLabel();
        yrsav = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        totalzattend = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        attedsrch = new javax.swing.JTextField();
        lblsellingprice2 = new javax.swing.JLabel();
        stresz = new javax.swing.JTextField();
        managefacilitators = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblfacilitators = new javax.swing.JTable();
        lblEmpfname = new javax.swing.JLabel();
        lblEmplname = new javax.swing.JLabel();
        lblEmppno = new javax.swing.JLabel();
        lblEmpsal = new javax.swing.JLabel();
        txtfnamfc = new javax.swing.JTextField();
        txtpnof = new javax.swing.JTextField();
        dtrevz = new javax.swing.JTextField();
        btnReloademp = new javax.swing.JButton();
        btnDeleteemp = new javax.swing.JButton();
        btnUpdateemp = new javax.swing.JButton();
        txtevtnz = new javax.swing.JTextField();
        lblEusername = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        empsearch = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        totalfacl = new javax.swing.JTextField();
        btncloseemp = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        txtbioz = new javax.swing.JTextArea();
        ManageEventAttendance = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        eventAttendanceTable = new javax.swing.JTable();
        btnCloseprov = new javax.swing.JButton();
        btnupdateprov = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        manageevy = new javax.swing.JTextField();
        mangatt = new javax.swing.JTextField();
        managnams = new javax.swing.JTextField();
        managnum = new javax.swing.JTextField();
        btnretrieveprov = new javax.swing.JButton();
        btndeleteProv = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        eventatsearch = new javax.swing.JTextField();
        managemail = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        managests = new javax.swing.JTextField();
        managedte = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        retdates = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        totattevtd = new javax.swing.JTextField();
        AddFacilitators = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        evftxt = new javax.swing.JTextField();
        pftxt = new javax.swing.JTextField();
        namtfxt = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        dateevts = new com.toedter.calendar.JDateChooser();
        jScrollPane7 = new javax.swing.JScrollPane();
        bioft = new javax.swing.JTextArea();
        AddEventAttendance = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        evtnb = new javax.swing.JTextField();
        provreset = new javax.swing.JButton();
        recordprovider = new javax.swing.JButton();
        closeprov = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        strevetxt = new javax.swing.JComboBox<>();
        extmail = new javax.swing.JTextField();
        regevend = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        evtpt = new javax.swing.JTextField();
        evenyp = new javax.swing.JTextField();
        dtechoose = new com.toedter.calendar.JDateChooser();
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
        manageproperties = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        proprents = new javax.swing.JTable();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        propntxt = new javax.swing.JTextField();
        propptxt = new javax.swing.JTextField();
        propqtxt = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel38 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        propertytots = new javax.swing.JTextField();
        AttendanceReport = new javax.swing.JPanel();
        lblread = new javax.swing.JLabel();
        genrent = new javax.swing.JButton();
        closerent = new javax.swing.JButton();
        lblrent = new javax.swing.JLabel();
        MembersReport = new javax.swing.JPanel();
        gentenant = new javax.swing.JButton();
        closetenant = new javax.swing.JButton();
        lbltenant = new javax.swing.JLabel();
        lblread2 = new javax.swing.JLabel();
        homepanel = new javax.swing.JPanel();
        softwarename = new javax.swing.JLabel();
        softwaredesc = new javax.swing.JLabel();
        softwarename1 = new javax.swing.JLabel();
        softwaredesc1 = new javax.swing.JLabel();
        CalendarActivities = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        calendactv = new javax.swing.JTextArea();
        jLabel44 = new javax.swing.JLabel();
        saveregulations = new javax.swing.JButton();
        clsrules = new javax.swing.JButton();
        saveregulations2 = new javax.swing.JButton();
        ViewCalendarActivities = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        calactvs = new javax.swing.JTextArea();
        jLabel45 = new javax.swing.JLabel();
        viewregulations = new javax.swing.JButton();
        printregulations = new javax.swing.JButton();
        closeregulations = new javax.swing.JButton();
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
        secuploads = new javax.swing.JTextArea();
        jLabel54 = new javax.swing.JLabel();
        saveregulations1 = new javax.swing.JButton();
        clsrules1 = new javax.swing.JButton();
        saveregulations3 = new javax.swing.JButton();
        ViewNotices = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        viewsecnotice = new javax.swing.JTextArea();
        jLabel55 = new javax.swing.JLabel();
        btnsecotice = new javax.swing.JButton();
        clsnotices = new javax.swing.JButton();
        EventAttendanceReport = new javax.swing.JPanel();
        gentenant1 = new javax.swing.JButton();
        closetenant1 = new javax.swing.JButton();
        lbltenant1 = new javax.swing.JLabel();
        lblread3 = new javax.swing.JLabel();
        loginlabel = new javax.swing.JLabel();
        secusername = new javax.swing.JLabel();
        btnLogOut = new javax.swing.JButton();
        softwareName = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        btnGroup = new javax.swing.JPanel();
        btnAddusers = new javax.swing.JButton();
        usrmgmt = new javax.swing.JLabel();
        btnaddemployees = new javax.swing.JButton();
        lblwritting = new javax.swing.JLabel();
        btnmanageusers = new javax.swing.JButton();
        btnmanagetenants = new javax.swing.JButton();
        btnmanageemp = new javax.swing.JButton();
        mbreports = new javax.swing.JButton();
        viewcals = new javax.swing.JButton();
        lblReports2 = new javax.swing.JLabel();
        repatts = new javax.swing.JButton();
        stockmgmt1 = new javax.swing.JLabel();
        btnaddserviceprovider1 = new javax.swing.JButton();
        lblReports3 = new javax.swing.JLabel();
        viewcals1 = new javax.swing.JButton();
        viewcals2 = new javax.swing.JButton();
        btnmanageusers1 = new javax.swing.JButton();

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
        addusrlabel.setText("REGISTER TECHNO SCIENCE MEMBERS");

        usernamelabel.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        usernamelabel.setText("REG NO. :");

        regntxts.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        passwordlabel.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        passwordlabel.setText("Phonenumber:");

        btnAddUser.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btnAddUser.setForeground(new java.awt.Color(0, 51, 255));
        btnAddUser.setText("Register");
        btnAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUserActionPerformed(evt);
            }
        });

        rolelabel.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        rolelabel.setText("Area of specialization:");

        confpasslabel.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        confpasslabel.setText("course:");

        btnClearUser.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btnClearUser.setText("Clear");
        btnClearUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearUserActionPerformed(evt);
            }
        });

        btnCloseNewUser.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btnCloseNewUser.setForeground(new java.awt.Color(51, 0, 51));
        btnCloseNewUser.setText("Close");
        btnCloseNewUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseNewUserActionPerformed(evt);
            }
        });

        crxs.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        crxs.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Course", "Applied Computer Science", "Computer Science" }));
        crxs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crxsActionPerformed(evt);
            }
        });

        phonelabel.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        phonelabel.setText("NAME:");

        namzs.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        securitylabel.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        securitylabel.setText("Year of Study $ Semester:");

        arsps.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        arsps.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose Area of Specialization", "Artficial Intellingence", "BlockChain", "Machine Learning", "Data Science", "Dgital Forensic", "Software Engineering", "HPC", "Android Development", "Cloud Computing" }));

        answerlabel.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        answerlabel.setText("Registration date:");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 255));
        jLabel13.setText("Please fill in the fields below to register members in the system");

        phnts.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        yerss.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        yerss.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose Current Year and Sem of Study", "Y1S1", "Y1S2", "Y2 S1", "Y2S2", "Y3S1", "Y3S2", "Y4S1", "Y4S2" }));

        dateregs.setDateFormatString("dd - MM - YYYY");

        javax.swing.GroupLayout RegisterMembersLayout = new javax.swing.GroupLayout(RegisterMembers);
        RegisterMembers.setLayout(RegisterMembersLayout);
        RegisterMembersLayout.setHorizontalGroup(
            RegisterMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RegisterMembersLayout.createSequentialGroup()
                .addGroup(RegisterMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RegisterMembersLayout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(RegisterMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(confpasslabel)
                            .addComponent(phonelabel)
                            .addComponent(usernamelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(answerlabel)
                            .addComponent(btnAddUser)
                            .addComponent(passwordlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rolelabel))
                        .addGroup(RegisterMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(RegisterMembersLayout.createSequentialGroup()
                                .addGap(114, 114, 114)
                                .addComponent(btnClearUser)
                                .addGap(78, 78, 78)
                                .addComponent(btnCloseNewUser))
                            .addGroup(RegisterMembersLayout.createSequentialGroup()
                                .addGap(96, 96, 96)
                                .addGroup(RegisterMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(arsps, javax.swing.GroupLayout.Alignment.LEADING, 0, 1, Short.MAX_VALUE)
                                    .addComponent(crxs, javax.swing.GroupLayout.Alignment.LEADING, 0, 301, Short.MAX_VALUE)
                                    .addComponent(namzs, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(regntxts, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(phnts)
                                    .addComponent(yerss, javax.swing.GroupLayout.Alignment.LEADING, 0, 1, Short.MAX_VALUE)
                                    .addComponent(dateregs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(RegisterMembersLayout.createSequentialGroup()
                        .addGap(146, 146, 146)
                        .addGroup(RegisterMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(addusrlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(RegisterMembersLayout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(securitylabel)))
                .addContainerGap(236, Short.MAX_VALUE))
        );
        RegisterMembersLayout.setVerticalGroup(
            RegisterMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RegisterMembersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addusrlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addGap(37, 37, 37)
                .addGroup(RegisterMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernamelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(regntxts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(RegisterMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phonelabel)
                    .addComponent(namzs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(RegisterMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phnts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(RegisterMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(confpasslabel)
                    .addComponent(crxs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(RegisterMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rolelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(arsps, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(RegisterMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(securitylabel)
                    .addComponent(yerss, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(RegisterMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(answerlabel)
                    .addComponent(dateregs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(RegisterMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddUser)
                    .addComponent(btnClearUser)
                    .addComponent(btnCloseNewUser))
                .addContainerGap(181, Short.MAX_VALUE))
        );

        displaypanel.add(RegisterMembers, "card2");

        manageMembers.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Manage Techno Science Members", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Engravers MT", 1, 24), new java.awt.Color(0, 0, 255))); // NOI18N

        retrieve.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        retrieve.setForeground(new java.awt.Color(0, 0, 255));
        retrieve.setText("Retrieve");
        retrieve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retrieveActionPerformed(evt);
            }
        });

        lblusername.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblusername.setText("Reg Number:");

        txtmreg.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        pass.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        pass.setText("Phonenumber:");

        btnUpdateUserInfo.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btnUpdateUserInfo.setForeground(new java.awt.Color(153, 0, 51));
        btnUpdateUserInfo.setText("Update Details");
        btnUpdateUserInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateUserInfoActionPerformed(evt);
            }
        });

        btnDeleteUser.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btnDeleteUser.setForeground(new java.awt.Color(102, 51, 0));
        btnDeleteUser.setText("Delete");
        btnDeleteUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteUserActionPerformed(evt);
            }
        });

        btnCloseMaintainUsers.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btnCloseMaintainUsers.setForeground(new java.awt.Color(0, 255, 51));
        btnCloseMaintainUsers.setText("Close");
        btnCloseMaintainUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseMaintainUsersActionPerformed(evt);
            }
        });

        memberstable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                " ID", "Reg No.", "Name", "Phonenumber", "Course", "Area of Spez", "Year $ Sem", "Date Reg."
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        memberstable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                memberstableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(memberstable);

        num.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        num.setText("Name:");

        position.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        position.setText("Course:");

        coursemtxts.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        security.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        security.setText("Specialization:");

        ans.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        ans.setText("Reg. Date:");

        speztxts.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        ysstxt.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        jLabel39.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel39.setText("Search:");

        memberssrch.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        memberssrch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                memberssrchKeyReleased(evt);
            }
        });

        ans1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        ans1.setText("Year $Sem:");

        rgss.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        jLabel56.setFont(new java.awt.Font("Elephant", 1, 14)); // NOI18N
        jLabel56.setText("Total Members:");

        totalms.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        javax.swing.GroupLayout manageMembersLayout = new javax.swing.GroupLayout(manageMembers);
        manageMembers.setLayout(manageMembersLayout);
        manageMembersLayout.setHorizontalGroup(
            manageMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageMembersLayout.createSequentialGroup()
                .addComponent(jLabel39)
                .addGap(60, 60, 60)
                .addComponent(memberssrch, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(manageMembersLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(manageMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manageMembersLayout.createSequentialGroup()
                        .addGroup(manageMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(security)
                            .addComponent(ans1)
                            .addComponent(ans))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(manageMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ysstxt, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(speztxts, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rgss, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(manageMembersLayout.createSequentialGroup()
                        .addGroup(manageMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(position))
                        .addGap(18, 18, 18)
                        .addGroup(manageMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtphonem, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(coursemtxts, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(manageMembersLayout.createSequentialGroup()
                        .addGroup(manageMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblusername, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(num))
                        .addGap(18, 18, 18)
                        .addGroup(manageMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(namentxts, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtmreg, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(73, 73, 73))
            .addGroup(manageMembersLayout.createSequentialGroup()
                .addGroup(manageMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manageMembersLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(retrieve)
                        .addGap(33, 33, 33)
                        .addComponent(btnUpdateUserInfo)
                        .addGap(48, 48, 48)
                        .addComponent(btnDeleteUser, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(btnCloseMaintainUsers))
                    .addGroup(manageMembersLayout.createSequentialGroup()
                        .addComponent(jLabel56)
                        .addGap(60, 60, 60)
                        .addComponent(totalms, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        manageMembersLayout.setVerticalGroup(
            manageMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageMembersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(manageMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(memberssrch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(manageMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(manageMembersLayout.createSequentialGroup()
                        .addGroup(manageMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblusername)
                            .addComponent(txtmreg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(manageMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(num)
                            .addComponent(namentxts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(manageMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pass)
                            .addComponent(txtphonem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(manageMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(position)
                            .addComponent(coursemtxts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(manageMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(security)
                            .addComponent(speztxts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(manageMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ysstxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ans1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                        .addGroup(manageMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ans)
                            .addComponent(rgss, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(manageMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(totalms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(manageMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(retrieve)
                    .addComponent(btnUpdateUserInfo)
                    .addComponent(btnDeleteUser)
                    .addComponent(btnCloseMaintainUsers))
                .addContainerGap(168, Short.MAX_VALUE))
        );

        displaypanel.add(manageMembers, "card3");

        AddAttendance.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "REGISTER MEMBERS ATTENDANCE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18), new java.awt.Color(0, 102, 102))); // NOI18N

        proddetails.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        proddetails.setForeground(new java.awt.Color(102, 51, 255));
        proddetails.setText("Fill in the Form below by Filling the Registration Number and press enter to Auto retrieve members details");

        fname.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        fname.setText("REG NO. :");

        sregtxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        sregtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sregtxtKeyPressed(evt);
            }
        });

        lname.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lname.setText("Names:");

        snametx.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        plabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        plabel.setText("PhoneNumber:");

        snumtx.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        occlabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        occlabel.setText("Course:");

        scrst.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        btnClosen.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btnClosen.setForeground(new java.awt.Color(0, 51, 0));
        btnClosen.setText("Close");
        btnClosen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClosenActionPerformed(evt);
            }
        });

        btnsclers.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btnsclers.setForeground(new java.awt.Color(153, 51, 255));
        btnsclers.setText("Clear");
        btnsclers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsclersActionPerformed(evt);
            }
        });

        registerbtn.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        registerbtn.setForeground(new java.awt.Color(102, 153, 0));
        registerbtn.setText("ADD");
        registerbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerbtnActionPerformed(evt);
            }
        });

        statw.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        statw.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Status", "Absent", "Present", " " }));

        roomno1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        roomno1.setText("Status:");

        occlabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        occlabel1.setText("Year $ Sem:");

        ysrs.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        update.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        update.setForeground(new java.awt.Color(102, 153, 0));
        update.setText("update");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddAttendanceLayout = new javax.swing.GroupLayout(AddAttendance);
        AddAttendance.setLayout(AddAttendanceLayout);
        AddAttendanceLayout.setHorizontalGroup(
            AddAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddAttendanceLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(proddetails, javax.swing.GroupLayout.PREFERRED_SIZE, 673, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(168, Short.MAX_VALUE))
            .addGroup(AddAttendanceLayout.createSequentialGroup()
                .addGroup(AddAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddAttendanceLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(AddAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(plabel, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lname, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fname, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(occlabel)
                            .addComponent(roomno1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(occlabel1))
                        .addGap(80, 80, 80))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddAttendanceLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(registerbtn)
                        .addGap(55, 55, 55)))
                .addGroup(AddAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(statw, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sregtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(snametx, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(snumtx, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scrst, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(AddAttendanceLayout.createSequentialGroup()
                        .addComponent(update)
                        .addGap(56, 56, 56)
                        .addComponent(btnsclers)
                        .addGap(18, 18, 18)
                        .addComponent(btnClosen))
                    .addComponent(ysrs, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 229, Short.MAX_VALUE))
        );
        AddAttendanceLayout.setVerticalGroup(
            AddAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddAttendanceLayout.createSequentialGroup()
                .addComponent(proddetails, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AddAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fname)
                    .addComponent(sregtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lname, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(snametx, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(plabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(snumtx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(occlabel)
                    .addComponent(scrst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(occlabel1)
                    .addComponent(ysrs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(AddAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roomno1)
                    .addComponent(statw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(registerbtn)
                    .addComponent(btnsclers)
                    .addComponent(btnClosen)
                    .addComponent(update))
                .addGap(0, 343, Short.MAX_VALUE))
        );

        displaypanel.add(AddAttendance, "card5");

        manageattendance.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "MANAGE MEMBERS ATTENDANCE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N

        tableattendance.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Attend Id", "Reg Num", "Name", "Phonenumber", "Course", "Year$Sem", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableattendance.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableattendanceMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableattendance);

        btnGetTenants.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnGetTenants.setForeground(new java.awt.Color(102, 0, 255));
        btnGetTenants.setText("Retrieve Data");
        btnGetTenants.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetTenantsActionPerformed(evt);
            }
        });

        lblprodname.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblprodname.setText("Reg No.");

        regas.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        lblavailablestock.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblavailablestock.setText("Name:");

        nmesa.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        lblbuyingprice.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblbuyingprice.setText("Phonenumber:");

        phantxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        lblsellingprice.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblsellingprice.setText("Course:");

        crsza.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

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
        lblsellingprice1.setText("Year $ Sem:");

        yrsav.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jLabel42.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(0, 102, 102));
        jLabel42.setText("Total  member attendance:");

        totalzattend.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel43.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(102, 51, 255));
        jLabel43.setText("Search:");

        attedsrch.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        attedsrch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                attedsrchKeyReleased(evt);
            }
        });

        lblsellingprice2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblsellingprice2.setText("Status:");

        stresz.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        javax.swing.GroupLayout manageattendanceLayout = new javax.swing.GroupLayout(manageattendance);
        manageattendance.setLayout(manageattendanceLayout);
        manageattendanceLayout.setHorizontalGroup(
            manageattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageattendanceLayout.createSequentialGroup()
                .addGroup(manageattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(manageattendanceLayout.createSequentialGroup()
                        .addComponent(jLabel42)
                        .addGap(40, 40, 40)
                        .addComponent(totalzattend, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(manageattendanceLayout.createSequentialGroup()
                        .addComponent(btnGetTenants)
                        .addGap(96, 96, 96)
                        .addComponent(delete))
                    .addGroup(manageattendanceLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel43)
                        .addGap(102, 102, 102)
                        .addComponent(attedsrch, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(manageattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manageattendanceLayout.createSequentialGroup()
                        .addComponent(btnClosetenant)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(manageattendanceLayout.createSequentialGroup()
                        .addGroup(manageattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblavailablestock)
                            .addComponent(lblprodname, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblbuyingprice)
                            .addComponent(lblsellingprice)
                            .addComponent(lblsellingprice1)
                            .addComponent(lblsellingprice2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(manageattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(stresz)
                            .addComponent(yrsav, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                            .addComponent(crsza, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(phantxt, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(nmesa, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(regas, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(57, 57, 57))))
        );
        manageattendanceLayout.setVerticalGroup(
            manageattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageattendanceLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(manageattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(attedsrch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(manageattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manageattendanceLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(manageattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel42)
                            .addComponent(totalzattend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(manageattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGetTenants)
                            .addComponent(delete)
                            .addComponent(btnClosetenant)))
                    .addGroup(manageattendanceLayout.createSequentialGroup()
                        .addGroup(manageattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblprodname)
                            .addComponent(regas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(manageattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblavailablestock)
                            .addComponent(nmesa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(manageattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblbuyingprice)
                            .addComponent(phantxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(manageattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblsellingprice)
                            .addComponent(crsza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(manageattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblsellingprice1)
                            .addComponent(yrsav, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(manageattendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblsellingprice2)
                            .addComponent(stresz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(210, Short.MAX_VALUE))
        );

        displaypanel.add(manageattendance, "card6");

        managefacilitators.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.MatteBorder(null), "MANAGE FACILITATORS DETAILS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18), new java.awt.Color(102, 0, 204))); // NOI18N
        managefacilitators.setPreferredSize(new java.awt.Dimension(520, 0));

        tblfacilitators.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Facilitator Id", "Name", "Phonenumber", "Event Name", "Event Date", "BIO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblfacilitators.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblfacilitatorsMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblfacilitators);

        lblEmpfname.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblEmpfname.setText("Name:");

        lblEmplname.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblEmplname.setText("Phonenumber:");

        lblEmppno.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblEmppno.setText("Event Name:");

        lblEmpsal.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblEmpsal.setText("Event Date:");

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
        btnDeleteemp.setText("Delete FACILITATOR");
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
        lblEusername.setText("Bio:");

        jLabel22.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        jLabel22.setText("search");

        empsearch.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        empsearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                empsearchKeyReleased(evt);
            }
        });

        jLabel40.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(0, 153, 255));
        jLabel40.setText("Total Facilitators:");

        totalfacl.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        btncloseemp.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btncloseemp.setForeground(new java.awt.Color(0, 0, 102));
        btncloseemp.setText("close");
        btncloseemp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncloseempActionPerformed(evt);
            }
        });

        txtbioz.setColumns(20);
        txtbioz.setRows(5);
        jScrollPane8.setViewportView(txtbioz);

        javax.swing.GroupLayout managefacilitatorsLayout = new javax.swing.GroupLayout(managefacilitators);
        managefacilitators.setLayout(managefacilitatorsLayout);
        managefacilitatorsLayout.setHorizontalGroup(
            managefacilitatorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, managefacilitatorsLayout.createSequentialGroup()
                .addGroup(managefacilitatorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, managefacilitatorsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(managefacilitatorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(managefacilitatorsLayout.createSequentialGroup()
                                .addComponent(btnReloademp)
                                .addGap(26, 26, 26)
                                .addComponent(btnUpdateemp)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnDeleteemp)
                                .addGap(18, 18, 18)
                                .addComponent(btncloseemp)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 99, Short.MAX_VALUE))
                            .addGroup(managefacilitatorsLayout.createSequentialGroup()
                                .addComponent(jLabel40)
                                .addGap(125, 125, 125)
                                .addComponent(totalfacl, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(194, 194, 194)
                                .addComponent(jScrollPane8))))
                    .addGroup(managefacilitatorsLayout.createSequentialGroup()
                        .addGroup(managefacilitatorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(managefacilitatorsLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel22)
                                .addGap(54, 54, 54)
                                .addComponent(empsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(managefacilitatorsLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 577, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(managefacilitatorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEmplname)
                                    .addComponent(lblEmppno)
                                    .addComponent(lblEmpsal)
                                    .addComponent(lblEusername)
                                    .addComponent(lblEmpfname))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(managefacilitatorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtfnamfc, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                            .addComponent(txtpnof)
                            .addComponent(txtevtnz)
                            .addComponent(dtrevz))))
                .addGap(29, 29, 29))
        );
        managefacilitatorsLayout.setVerticalGroup(
            managefacilitatorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(managefacilitatorsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(managefacilitatorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(empsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(managefacilitatorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(managefacilitatorsLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(managefacilitatorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel40)
                            .addComponent(totalfacl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(managefacilitatorsLayout.createSequentialGroup()
                        .addGroup(managefacilitatorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEmpfname)
                            .addComponent(txtfnamfc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(managefacilitatorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtpnof, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEmplname))
                        .addGap(18, 18, 18)
                        .addGroup(managefacilitatorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtevtnz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEmppno))
                        .addGap(27, 27, 27)
                        .addGroup(managefacilitatorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEmpsal)
                            .addComponent(dtrevz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(managefacilitatorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(managefacilitatorsLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lblEusername))
                            .addGroup(managefacilitatorsLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(28, 28, 28)
                .addGroup(managefacilitatorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReloademp)
                    .addComponent(btnUpdateemp)
                    .addComponent(btnDeleteemp)
                    .addComponent(btncloseemp))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        displaypanel.add(managefacilitators, "card8");

        ManageEventAttendance.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "MANAGE EVENT ATTENDANCE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N
        ManageEventAttendance.setPreferredSize(new java.awt.Dimension(525, 0));

        eventAttendanceTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Event  ID", "REG NO.", "Names", "Phonenumber", "Email", "Event Name", "Event Date", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        eventAttendanceTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eventAttendanceTableMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(eventAttendanceTable);

        btnCloseprov.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnCloseprov.setText("Close");
        btnCloseprov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseprovActionPerformed(evt);
            }
        });

        btnupdateprov.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnupdateprov.setText("UpdATE dETAILS");
        btnupdateprov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateprovActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel28.setText("REG NO.:");

        jLabel29.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel29.setText("Phonenumber:");

        jLabel30.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel30.setText("Names:");

        jLabel31.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel31.setText("Event Name:");

        manageevy.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        mangatt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        managnams.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        managnum.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        btnretrieveprov.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnretrieveprov.setText("Retrieve Data");
        btnretrieveprov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnretrieveprovActionPerformed(evt);
            }
        });

        btndeleteProv.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btndeleteProv.setText("dELETE DATA");
        btndeleteProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteProvActionPerformed(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel32.setText("SEARCH:");

        eventatsearch.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        eventatsearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                eventatsearchKeyReleased(evt);
            }
        });

        managemail.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jLabel46.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel46.setText("Email:");

        jLabel47.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel47.setText("Event Date:");

        jLabel49.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel49.setText("Status:");

        managests.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        managedte.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 18)); // NOI18N
        jLabel7.setText("Retrieve Date:");

        retdates.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("Total Members:");

        totattevtd.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        javax.swing.GroupLayout ManageEventAttendanceLayout = new javax.swing.GroupLayout(ManageEventAttendance);
        ManageEventAttendance.setLayout(ManageEventAttendanceLayout);
        ManageEventAttendanceLayout.setHorizontalGroup(
            ManageEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ManageEventAttendanceLayout.createSequentialGroup()
                .addGroup(ManageEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ManageEventAttendanceLayout.createSequentialGroup()
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addComponent(eventatsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79)
                        .addComponent(jLabel7)
                        .addGap(56, 56, 56)
                        .addComponent(retdates, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ManageEventAttendanceLayout.createSequentialGroup()
                        .addComponent(btnretrieveprov)
                        .addGap(43, 43, 43)
                        .addComponent(btnupdateprov)
                        .addGap(41, 41, 41)
                        .addComponent(btndeleteProv)
                        .addGap(57, 57, 57)
                        .addComponent(btnCloseprov)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(ManageEventAttendanceLayout.createSequentialGroup()
                .addGroup(ManageEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ManageEventAttendanceLayout.createSequentialGroup()
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ManageEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ManageEventAttendanceLayout.createSequentialGroup()
                                .addGroup(ManageEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel28)
                                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel29)
                                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(ManageEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(managnum, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                                    .addComponent(managnams, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(mangatt)
                                    .addComponent(managemail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                                    .addComponent(manageevy, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(ManageEventAttendanceLayout.createSequentialGroup()
                                .addGroup(ManageEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(ManageEventAttendanceLayout.createSequentialGroup()
                                        .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(managedte, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(ManageEventAttendanceLayout.createSequentialGroup()
                                        .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(managests, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(ManageEventAttendanceLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(131, 131, 131)
                        .addComponent(totattevtd, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        ManageEventAttendanceLayout.setVerticalGroup(
            ManageEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ManageEventAttendanceLayout.createSequentialGroup()
                .addGroup(ManageEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eventatsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(retdates, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(ManageEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ManageEventAttendanceLayout.createSequentialGroup()
                        .addGroup(ManageEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mangatt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ManageEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(managnams, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ManageEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(managnum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ManageEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(managemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ManageEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(manageevy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ManageEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(managedte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(ManageEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(managests, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ManageEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(totattevtd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(ManageEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnretrieveprov)
                    .addComponent(btnupdateprov)
                    .addComponent(btndeleteProv)
                    .addComponent(btnCloseprov))
                .addContainerGap(190, Short.MAX_VALUE))
        );

        displaypanel.add(ManageEventAttendance, "card12");

        AddFacilitators.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "ADD FACILITATORS FORM", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Perpetua Titling MT", 1, 18))); // NOI18N
        AddFacilitators.setPreferredSize(new java.awt.Dimension(609, 390));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setText("Name:");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("Phonenumber:");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setText("Event Date:");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setText("Event Name:");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setText("BIO:");

        evftxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        pftxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        namtfxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

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

        bioft.setColumns(20);
        bioft.setRows(5);
        jScrollPane7.setViewportView(bioft);

        javax.swing.GroupLayout AddFacilitatorsLayout = new javax.swing.GroupLayout(AddFacilitators);
        AddFacilitators.setLayout(AddFacilitatorsLayout);
        AddFacilitatorsLayout.setHorizontalGroup(
            AddFacilitatorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddFacilitatorsLayout.createSequentialGroup()
                .addGroup(AddFacilitatorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddFacilitatorsLayout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(jLabel8))
                    .addComponent(jLabel5)
                    .addGroup(AddFacilitatorsLayout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(63, 63, 63)
                        .addComponent(jButton1)
                        .addGap(49, 49, 49)
                        .addComponent(jButton3))
                    .addGroup(AddFacilitatorsLayout.createSequentialGroup()
                        .addGroup(AddFacilitatorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3))
                        .addGap(95, 95, 95)
                        .addGroup(AddFacilitatorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dateevts, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                            .addComponent(namtfxt, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                            .addComponent(pftxt, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                            .addComponent(evftxt, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                            .addComponent(jScrollPane7))))
                .addContainerGap(415, Short.MAX_VALUE))
        );
        AddFacilitatorsLayout.setVerticalGroup(
            AddFacilitatorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddFacilitatorsLayout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(12, 12, 12)
                .addGroup(AddFacilitatorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(namtfxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(AddFacilitatorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(pftxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddFacilitatorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(evftxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(AddFacilitatorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(dateevts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(AddFacilitatorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddFacilitatorsLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel6))
                    .addGroup(AddFacilitatorsLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addGroup(AddFacilitatorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        displaypanel.add(AddFacilitators, "card13");

        AddEventAttendance.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.MatteBorder(null), "RECORD EVENT ATTENDANCE FORM", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N
        AddEventAttendance.setPreferredSize(new java.awt.Dimension(669, 350));

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel17.setText("Event Date:");

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel18.setText("Event Name:");

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel19.setText("REG NO.");

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel20.setText("Name:");

        evtnb.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

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
        jLabel21.setText("Please Fill in Details To Record Member Event Attendance:");

        strevetxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        strevetxt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose the Status", "Present", "Absent" }));

        extmail.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        regevend.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        regevend.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                regevendKeyPressed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel23.setText("Phonenumber:");

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel24.setText("Email:");

        jLabel25.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel25.setText("Status:");

        evtpt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        evenyp.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        dtechoose.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dtechooseKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout AddEventAttendanceLayout = new javax.swing.GroupLayout(AddEventAttendance);
        AddEventAttendance.setLayout(AddEventAttendanceLayout);
        AddEventAttendanceLayout.setHorizontalGroup(
            AddEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddEventAttendanceLayout.createSequentialGroup()
                .addComponent(recordprovider)
                .addGap(86, 86, 86)
                .addComponent(provreset)
                .addGap(81, 81, 81)
                .addComponent(closeprov)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(AddEventAttendanceLayout.createSequentialGroup()
                .addGroup(AddEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddEventAttendanceLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel21))
                    .addGroup(AddEventAttendanceLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(AddEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AddEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel17)
                                .addComponent(jLabel19)
                                .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(jLabel20)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25))
                        .addGap(131, 131, 131)
                        .addGroup(AddEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dtechoose, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(strevetxt, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(extmail, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(evenyp, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(evtpt, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(evtnb, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(regevend, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(259, Short.MAX_VALUE))
        );
        AddEventAttendanceLayout.setVerticalGroup(
            AddEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddEventAttendanceLayout.createSequentialGroup()
                .addComponent(jLabel21)
                .addGap(18, 18, 18)
                .addGroup(AddEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17)
                    .addComponent(dtechoose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(evtnb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(regevend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(evtpt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(evenyp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(AddEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(extmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(AddEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(strevetxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(AddEventAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(recordprovider)
                    .addComponent(provreset)
                    .addComponent(closeprov))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        displaypanel.add(AddEventAttendance, "card15");

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
                .addContainerGap(372, Short.MAX_VALUE))
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
                .addContainerGap(389, Short.MAX_VALUE))
        );

        displaypanel.add(Addrentalproperties, "card15");

        manageproperties.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.MatteBorder(null), "MANAGE RENTAL PROPERTIES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N

        proprents.setModel(new javax.swing.table.DefaultTableModel(
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
        proprents.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                proprentsMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(proprents);

        jLabel35.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel35.setText("Price:");

        jLabel36.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel36.setText("Name:");

        jLabel37.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel37.setText("Quantity:");

        propntxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        propptxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        propqtxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jButton5.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jButton5.setText("retrieve data");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jButton6.setText("update details");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jButton7.setText("delete data");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
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

        jTextField5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jLabel52.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel52.setText("Total Prices:");

        propertytots.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        javax.swing.GroupLayout managepropertiesLayout = new javax.swing.GroupLayout(manageproperties);
        manageproperties.setLayout(managepropertiesLayout);
        managepropertiesLayout.setHorizontalGroup(
            managepropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(managepropertiesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(managepropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(managepropertiesLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(managepropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(managepropertiesLayout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addGap(74, 74, 74)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton7)))
                .addGroup(managepropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(managepropertiesLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(managepropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(propntxt, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(propqtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(propptxt, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(managepropertiesLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jButton8)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(managepropertiesLayout.createSequentialGroup()
                .addGroup(managepropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(managepropertiesLayout.createSequentialGroup()
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(managepropertiesLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel52)
                        .addGap(130, 130, 130)
                        .addComponent(propertytots, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        managepropertiesLayout.setVerticalGroup(
            managepropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, managepropertiesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(managepropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(managepropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(managepropertiesLayout.createSequentialGroup()
                        .addGroup(managepropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(propntxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(70, 70, 70)
                        .addGroup(managepropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(propqtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(67, 67, 67)
                        .addGroup(managepropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(propptxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(managepropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(managepropertiesLayout.createSequentialGroup()
                        .addComponent(jLabel52)
                        .addGap(30, 30, 30)
                        .addComponent(jButton5))
                    .addGroup(managepropertiesLayout.createSequentialGroup()
                        .addComponent(propertytots, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addGroup(managepropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton6)
                            .addComponent(jButton7)
                            .addComponent(jButton8))))
                .addContainerGap(257, Short.MAX_VALUE))
        );

        displaypanel.add(manageproperties, "card16");

        AttendanceReport.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "ATTENDANCE REPORT GENERATION", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N
        AttendanceReport.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblread.setBackground(new java.awt.Color(255, 255, 204));
        lblread.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblread.setForeground(new java.awt.Color(0, 0, 255));
        lblread.setText("Report is used to quickly analyse data and can be generated periodicaly");
        AttendanceReport.add(lblread, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 88, -1, 34));

        genrent.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        genrent.setForeground(new java.awt.Color(153, 0, 102));
        genrent.setText("ATTENDANCE report");
        genrent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genrentActionPerformed(evt);
            }
        });
        AttendanceReport.add(genrent, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 260, -1));

        closerent.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        closerent.setForeground(new java.awt.Color(0, 51, 153));
        closerent.setText("close");
        closerent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closerentActionPerformed(evt);
            }
        });
        AttendanceReport.add(closerent, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 200, -1, -1));

        lblrent.setBackground(new java.awt.Color(255, 255, 204));
        lblrent.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblrent.setForeground(new java.awt.Color(0, 0, 255));
        lblrent.setText("Click once the button Attendance Report to generate Club AttendanceReport ");
        AttendanceReport.add(lblrent, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 690, 34));

        displaypanel.add(AttendanceReport, "card17");

        MembersReport.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "TECHNOSCIENCE MEMBERS REPORT GENERATION", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N
        MembersReport.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        gentenant.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        gentenant.setForeground(new java.awt.Color(102, 102, 255));
        gentenant.setText("MEMBERS Report");
        gentenant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gentenantActionPerformed(evt);
            }
        });
        MembersReport.add(gentenant, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        closetenant.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        closetenant.setForeground(new java.awt.Color(51, 51, 0));
        closetenant.setText("CLOSE");
        closetenant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closetenantActionPerformed(evt);
            }
        });
        MembersReport.add(closetenant, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 200, -1, -1));

        lbltenant.setBackground(new java.awt.Color(255, 255, 204));
        lbltenant.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbltenant.setForeground(new java.awt.Color(0, 0, 255));
        lbltenant.setText("Click once Members Report button Generate to generate members Attendance report");
        MembersReport.add(lbltenant, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 680, 40));

        lblread2.setBackground(new java.awt.Color(255, 255, 204));
        lblread2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblread2.setForeground(new java.awt.Color(0, 0, 255));
        lblread2.setText("Report is used to quickly analyse data and can be generated periodicaly");
        MembersReport.add(lblread2, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 88, -1, 34));

        displaypanel.add(MembersReport, "card19");

        homepanel.setBackground(new java.awt.Color(51, 255, 51));
        homepanel.setForeground(new java.awt.Color(255, 255, 255));

        softwarename.setFont(new java.awt.Font("Perpetua Titling MT", 1, 24)); // NOI18N
        softwarename.setText("WELCOME TO TECHNO SCIENCE MANAGEMENT SYSTEM");

        softwaredesc.setFont(new java.awt.Font("Tempus Sans ITC", 3, 24)); // NOI18N
        softwaredesc.setText("Technology is a useful servant but a dangerous master");

        softwarename1.setFont(new java.awt.Font("Perpetua Titling MT", 1, 24)); // NOI18N
        softwarename1.setText("SECRETARY DASHBOARD");

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
                        .addGap(48, 48, 48)
                        .addGroup(homepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(softwaredesc1, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(softwarename)))
                    .addGroup(homepanelLayout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(softwaredesc, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(128, Short.MAX_VALUE))
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
                .addGap(77, 77, 77)
                .addComponent(softwaredesc, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(372, Short.MAX_VALUE))
        );

        displaypanel.add(homepanel, "card4");

        CalendarActivities.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "RECORDING CALENDAR OF ACTIVITIES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18), new java.awt.Color(0, 0, 255))); // NOI18N

        calendactv.setColumns(20);
        calendactv.setRows(5);
        jScrollPane11.setViewportView(calendactv);

        jLabel44.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel44.setText("Click Retrieve to IF there is Existing recorded activites and incase of Change made Click Save");

        saveregulations.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        saveregulations.setForeground(new java.awt.Color(0, 0, 102));
        saveregulations.setText("SAVE");
        saveregulations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveregulationsActionPerformed(evt);
            }
        });

        clsrules.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        clsrules.setForeground(new java.awt.Color(0, 51, 102));
        clsrules.setText("CLOSE");
        clsrules.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clsrulesActionPerformed(evt);
            }
        });

        saveregulations2.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        saveregulations2.setForeground(new java.awt.Color(0, 0, 102));
        saveregulations2.setText("view");
        saveregulations2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveregulations2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CalendarActivitiesLayout = new javax.swing.GroupLayout(CalendarActivities);
        CalendarActivities.setLayout(CalendarActivitiesLayout);
        CalendarActivitiesLayout.setHorizontalGroup(
            CalendarActivitiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CalendarActivitiesLayout.createSequentialGroup()
                .addGroup(CalendarActivitiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CalendarActivitiesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel44))
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(CalendarActivitiesLayout.createSequentialGroup()
                .addComponent(saveregulations2)
                .addGap(60, 60, 60)
                .addComponent(saveregulations)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(clsrules)
                .addGap(57, 57, 57))
        );
        CalendarActivitiesLayout.setVerticalGroup(
            CalendarActivitiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CalendarActivitiesLayout.createSequentialGroup()
                .addComponent(jLabel44)
                .addGap(17, 17, 17)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(CalendarActivitiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveregulations)
                    .addComponent(clsrules)
                    .addComponent(saveregulations2))
                .addContainerGap(277, Short.MAX_VALUE))
        );

        displaypanel.add(CalendarActivities, "card20");

        ViewCalendarActivities.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "VIEW CALENDAR OF ACTIVITIES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Engravers MT", 1, 18))); // NOI18N

        calactvs.setEditable(false);
        calactvs.setColumns(20);
        calactvs.setLineWrap(true);
        calactvs.setRows(5);
        jScrollPane12.setViewportView(calactvs);

        jLabel45.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(0, 51, 51));
        jLabel45.setText("Click button view to display Recorded Calendar of Activities");

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

        javax.swing.GroupLayout ViewCalendarActivitiesLayout = new javax.swing.GroupLayout(ViewCalendarActivities);
        ViewCalendarActivities.setLayout(ViewCalendarActivitiesLayout);
        ViewCalendarActivitiesLayout.setHorizontalGroup(
            ViewCalendarActivitiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewCalendarActivitiesLayout.createSequentialGroup()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(ViewCalendarActivitiesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ViewCalendarActivitiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel45)
                    .addGroup(ViewCalendarActivitiesLayout.createSequentialGroup()
                        .addComponent(viewregulations)
                        .addGap(94, 94, 94)
                        .addComponent(printregulations)
                        .addGap(136, 136, 136)
                        .addComponent(closeregulations)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ViewCalendarActivitiesLayout.setVerticalGroup(
            ViewCalendarActivitiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewCalendarActivitiesLayout.createSequentialGroup()
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(ViewCalendarActivitiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(viewregulations)
                    .addComponent(printregulations)
                    .addComponent(closeregulations))
                .addContainerGap(287, Short.MAX_VALUE))
        );

        displaypanel.add(ViewCalendarActivities, "card21");

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
                .addContainerGap(342, Short.MAX_VALUE))
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
                .addContainerGap(53, Short.MAX_VALUE))
        );

        displaypanel.add(UpdateRooms, "card13");

        UploadNotices.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "UPLOAD NOTICES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18), new java.awt.Color(0, 0, 255))); // NOI18N

        secuploads.setColumns(20);
        secuploads.setRows(5);
        jScrollPane17.setViewportView(secuploads);

        jLabel54.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel54.setText("In Below File Type Your Notice  and Click Button save to upload ");

        saveregulations1.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        saveregulations1.setForeground(new java.awt.Color(0, 0, 102));
        saveregulations1.setText("save");
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

        saveregulations3.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        saveregulations3.setForeground(new java.awt.Color(0, 0, 102));
        saveregulations3.setText("reset");
        saveregulations3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveregulations3ActionPerformed(evt);
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
                .addGap(0, 235, Short.MAX_VALUE))
            .addGroup(UploadNoticesLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(saveregulations1)
                .addGap(95, 95, 95)
                .addComponent(saveregulations3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(clsrules1)
                .addGap(288, 288, 288))
        );
        UploadNoticesLayout.setVerticalGroup(
            UploadNoticesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UploadNoticesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel54)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(UploadNoticesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveregulations1)
                    .addComponent(clsrules1)
                    .addComponent(saveregulations3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        displaypanel.add(UploadNotices, "card20");

        ViewNotices.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "VIEW NOTICES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18), new java.awt.Color(0, 0, 255))); // NOI18N

        viewsecnotice.setColumns(20);
        viewsecnotice.setRows(5);
        jScrollPane18.setViewportView(viewsecnotice);

        jLabel55.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel55.setText("Click Button View to retrieve the Available Notices");

        btnsecotice.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btnsecotice.setForeground(new java.awt.Color(0, 0, 102));
        btnsecotice.setText("View");
        btnsecotice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsecoticeActionPerformed(evt);
            }
        });

        clsnotices.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        clsnotices.setForeground(new java.awt.Color(0, 51, 102));
        clsnotices.setText("CLOSE");
        clsnotices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clsnoticesActionPerformed(evt);
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
                .addGap(0, 235, Short.MAX_VALUE))
            .addGroup(ViewNoticesLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(btnsecotice)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(clsnotices)
                .addGap(318, 318, 318))
        );
        ViewNoticesLayout.setVerticalGroup(
            ViewNoticesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewNoticesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel55)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ViewNoticesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(clsnotices)
                    .addComponent(btnsecotice))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        displaypanel.add(ViewNotices, "card20");

        EventAttendanceReport.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "TECHNOSCIENCE MEMBERS ATTENDANCE REPORT GENERATION", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N
        EventAttendanceReport.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        gentenant1.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        gentenant1.setForeground(new java.awt.Color(102, 102, 255));
        gentenant1.setText("Member Report");
        gentenant1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gentenant1ActionPerformed(evt);
            }
        });
        EventAttendanceReport.add(gentenant1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        closetenant1.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        closetenant1.setForeground(new java.awt.Color(51, 51, 0));
        closetenant1.setText("CLOSE");
        closetenant1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closetenant1ActionPerformed(evt);
            }
        });
        EventAttendanceReport.add(closetenant1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 190, -1, -1));

        lbltenant1.setBackground(new java.awt.Color(255, 255, 204));
        lbltenant1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbltenant1.setForeground(new java.awt.Color(0, 0, 255));
        lbltenant1.setText("Click once Members Report button Generate to generate Member Attendance report");
        EventAttendanceReport.add(lbltenant1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 630, 40));

        lblread3.setBackground(new java.awt.Color(255, 255, 204));
        lblread3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblread3.setForeground(new java.awt.Color(0, 0, 255));
        lblread3.setText("Report is used to quickly analyse data and can be generated periodicaly");
        EventAttendanceReport.add(lblread3, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 88, -1, 34));

        displaypanel.add(EventAttendanceReport, "card19");

        loginlabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        loginlabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        loginlabel.setText("Logged in as:");

        secusername.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        secusername.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        secusername.setText("myusername");

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

        btnAddusers.setFont(new java.awt.Font("Engravers MT", 1, 12)); // NOI18N
        btnAddusers.setText("Add Members");
        btnAddusers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddusersActionPerformed(evt);
            }
        });

        usrmgmt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        usrmgmt.setForeground(new java.awt.Color(204, 153, 0));
        usrmgmt.setText("Member Registration");

        btnaddemployees.setFont(new java.awt.Font("Engravers MT", 1, 12)); // NOI18N
        btnaddemployees.setText("Add Facilitator");
        btnaddemployees.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddemployeesActionPerformed(evt);
            }
        });

        lblwritting.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblwritting.setForeground(new java.awt.Color(102, 0, 51));
        lblwritting.setText("Calendar of Activities");

        btnmanageusers.setFont(new java.awt.Font("Engravers MT", 1, 12)); // NOI18N
        btnmanageusers.setText("Manage Members");
        btnmanageusers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmanageusersActionPerformed(evt);
            }
        });

        btnmanagetenants.setFont(new java.awt.Font("Engravers MT", 1, 12)); // NOI18N
        btnmanagetenants.setText("Manage Attendance");
        btnmanagetenants.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmanagetenantsActionPerformed(evt);
            }
        });

        btnmanageemp.setFont(new java.awt.Font("Engravers MT", 1, 12)); // NOI18N
        btnmanageemp.setText("Manage Facilitators");
        btnmanageemp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmanageempActionPerformed(evt);
            }
        });

        mbreports.setFont(new java.awt.Font("Engravers MT", 1, 12)); // NOI18N
        mbreports.setText("members");
        mbreports.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mbreportsActionPerformed(evt);
            }
        });

        viewcals.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        viewcals.setText("View");
        viewcals.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewcalsActionPerformed(evt);
            }
        });

        lblReports2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblReports2.setText("Generate Reports");

        repatts.setFont(new java.awt.Font("Engravers MT", 1, 12)); // NOI18N
        repatts.setText("CLUB Attendance");
        repatts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                repattsActionPerformed(evt);
            }
        });

        stockmgmt1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        stockmgmt1.setForeground(new java.awt.Color(0, 204, 0));
        stockmgmt1.setText("Facilitators Details");

        btnaddserviceprovider1.setFont(new java.awt.Font("Engravers MT", 1, 12)); // NOI18N
        btnaddserviceprovider1.setText("Add EVent AttendANCE");
        btnaddserviceprovider1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddserviceprovider1ActionPerformed(evt);
            }
        });

        lblReports3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblReports3.setForeground(new java.awt.Color(51, 0, 153));
        lblReports3.setText("Notice");

        viewcals1.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        viewcals1.setText("View notice");
        viewcals1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewcals1ActionPerformed(evt);
            }
        });

        viewcals2.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        viewcals2.setText("Upload notice");
        viewcals2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewcals2ActionPerformed(evt);
            }
        });

        btnmanageusers1.setFont(new java.awt.Font("Engravers MT", 1, 12)); // NOI18N
        btnmanageusers1.setText("ADD Attendance");
        btnmanageusers1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmanageusers1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout btnGroupLayout = new javax.swing.GroupLayout(btnGroup);
        btnGroup.setLayout(btnGroupLayout);
        btnGroupLayout.setHorizontalGroup(
            btnGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnGroupLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnGroupLayout.createSequentialGroup()
                        .addComponent(btnAddusers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(23, 23, 23))
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addComponent(btnmanagetenants)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(btnGroupLayout.createSequentialGroup()
                .addGroup(btnGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(usrmgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnaddemployees)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(btnGroupLayout.createSequentialGroup()
                .addGroup(btnGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(mbreports, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(repatts, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnaddserviceprovider1))
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(lblwritting))
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(viewcals, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(viewcals1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(lblReports2))
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(viewcals2, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(lblReports3))
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(stockmgmt1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnmanageemp)
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(btnGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnmanageusers1)
                            .addComponent(btnmanageusers))))
                .addGap(0, 20, Short.MAX_VALUE))
        );
        btnGroupLayout.setVerticalGroup(
            btnGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnGroupLayout.createSequentialGroup()
                .addComponent(usrmgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddusers)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnmanageusers)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnmanageusers1)
                .addGap(7, 7, 7)
                .addComponent(btnmanagetenants)
                .addGap(28, 28, 28)
                .addComponent(btnaddserviceprovider1)
                .addGap(18, 18, 18)
                .addComponent(stockmgmt1)
                .addGap(18, 18, 18)
                .addComponent(btnaddemployees)
                .addGap(18, 18, 18)
                .addComponent(btnmanageemp)
                .addGap(39, 39, 39)
                .addComponent(lblwritting)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viewcals)
                .addGap(2, 2, 2)
                .addComponent(lblReports3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viewcals2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viewcals1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblReports2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(mbreports)
                .addGap(18, 18, 18)
                .addComponent(repatts)
                .addContainerGap(324, Short.MAX_VALUE))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(displaypanel, javax.swing.GroupLayout.PREFERRED_SIZE, 892, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(softwareName, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89)
                        .addComponent(loginlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(137, 137, 137)
                        .addComponent(secusername, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(secusername)
                    .addComponent(btnLogOut))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(displaypanel, javax.swing.GroupLayout.PREFERRED_SIZE, 690, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addComponent(softwareName, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void crxsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crxsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_crxsActionPerformed

    private void btnAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUserActionPerformed
        try{
        String sql = " INSERT INTO members (regno, names, phonenumbers, course, area_specialization, year_semester, date  )  VALUES (?,?,?,?,?,?,?)";
        con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
        pst = con.prepareStatement(sql);
        pst.setString(1, regntxts.getText());
        pst.setString(2, namzs.getText());
        pst.setString(3, phnts.getText());
        pst.setString(4, crxs.getSelectedItem().toString());
        pst.setString(5, arsps.getSelectedItem().toString());
        pst.setString(6, yerss.getSelectedItem().toString());
        String regsdate = ((JTextField) dateregs.getDateEditor().getUiComponent()).getText();
            pst.setString(7, regsdate);
        
         if(regntxts.getText().equals("") || namzs.getText().equals("") || phnts.getText().equals("") || crxs.getSelectedIndex()==0 || arsps.getSelectedIndex()==0 || yerss.getSelectedIndex()==0) 
         {
             JOptionPane.showMessageDialog(null,"Some fields not filled in..!!\n Please Fill them to continue.","Warning",JOptionPane.WARNING_MESSAGE);
          }   
       
        else {
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Registration of Techno Science Members is Successful");
            namzs.setText("");
            regntxts.setText("");
            phnts.setText("");
            crxs.setSelectedIndex(0);
            arsps.setSelectedIndex(0);
            yerss.setSelectedIndex(0);
            dateregs.setCalendar(null);       
        }
        } 
        catch(HeadlessException | SQLException ex){
        JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_btnAddUserActionPerformed

    private void btnClearUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearUserActionPerformed
            namzs.setText("");
            regntxts.setText("");
            phnts.setText("");
            crxs.setSelectedIndex(0);
            arsps.setSelectedIndex(0);
            yerss.setSelectedIndex(0);
            dateregs.setCalendar(null);
    }//GEN-LAST:event_btnClearUserActionPerformed

    private void btnCloseNewUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseNewUserActionPerformed
        load_home();
    }//GEN-LAST:event_btnCloseNewUserActionPerformed

    private void memberstableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_memberstableMouseClicked
        String name = memberstable.getValueAt(memberstable.getSelectedRow(), 1).toString();
        String pno = memberstable.getValueAt(memberstable.getSelectedRow(), 2).toString();
        String pass = memberstable.getValueAt(memberstable.getSelectedRow(), 3).toString();
        String roles = memberstable.getValueAt(memberstable.getSelectedRow(), 4).toString();
        String security = memberstable.getValueAt(memberstable.getSelectedRow(), 5).toString();
        String asn = memberstable.getValueAt(memberstable.getSelectedRow(), 6).toString();
        String yep = memberstable.getValueAt(memberstable.getSelectedRow(), 7).toString();

        txtmreg.setText(name);
        namentxts.setText(pno);
        txtphonem.setText(pass);
        coursemtxts.setText(roles);
        speztxts.setText(security);
        ysstxt.setText(asn);
        rgss.setText(yep);
        
    }//GEN-LAST:event_memberstableMouseClicked

    private void retrieveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retrieveActionPerformed
      retrievemembers();
      totalmembers();
    }//GEN-LAST:event_retrieveActionPerformed

    private void btnUpdateUserInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateUserInfoActionPerformed
      try {
            if (txtmreg.getText().isEmpty() || namentxts.getText().isEmpty() || txtphonem.getText().isEmpty()|| coursemtxts.getText().isEmpty()|| speztxts.getText().isEmpty()|| ysstxt.getText().isEmpty() || rgss.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No row is selected..!!\nPlease select a row to update...");
            } else {
                int index = memberstable.getSelectedRow();
                String id = memberstable.getValueAt(index, 0).toString();

                if (updatemembers(id, txtmreg.getText(), namentxts.getText(), txtphonem.getText(), coursemtxts.getText(), speztxts.getText(),ysstxt.getText(), rgss.getText())){
                    JOptionPane.showMessageDialog(null, "Techno Science Members Data Updated Successfully");
                    txtmreg.setText("");
                    namentxts.setText("");
                    txtphonem.setText("");
                    coursemtxts.setText("");
                    speztxts.setText("");
                    ysstxt.setText("");
                    rgss.setText("");
                    retrievemembers();
                    totalmembers();
                } else {
                    JOptionPane.showMessageDialog(null, "Techno Science Members Data is Not Updated ");
                }
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }           
    }//GEN-LAST:event_btnUpdateUserInfoActionPerformed

    private void btnDeleteUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteUserActionPerformed
        try {
            String[] options = {"Yes", "No"};
            int answer = JOptionPane.showOptionDialog(null, " Are you Sure to delete ??", "Delete Options", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

            if (answer == 0) {
              String id = memberstable.getValueAt(memberstable.getSelectedRow(), 0).toString();
                if (deletemembers(id)) {
                    JOptionPane.showMessageDialog(null, "Techno Science Members Data Deleted Successfully");
                    txtmreg.setText("");
                    namentxts.setText("");
                    txtphonem.setText("");
                    coursemtxts.setText("");
                    speztxts.setText("");
                    ysstxt.setText("");
                    retrievemembers();
                    totalmembers();
                } else {
                    JOptionPane.showMessageDialog(null, " Techno Science Members Data is Not Deleted ");
                }
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, e);
        }        
    }//GEN-LAST:event_btnDeleteUserActionPerformed

    private void btnCloseMaintainUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseMaintainUsersActionPerformed
         load_home();
    }//GEN-LAST:event_btnCloseMaintainUsersActionPerformed

    private void btnAddusersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddusersActionPerformed
        displaypanel.removeAll();
        displaypanel.add(RegisterMembers);
        RegisterMembers.setVisible(true);
        RegisterMembers.repaint();
        RegisterMembers.revalidate();
    }//GEN-LAST:event_btnAddusersActionPerformed

    private void btnsclersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsclersActionPerformed
        sregtxt.setText(null);
        snametx.setText(null);
        snumtx.setText(null);
        statw.setSelectedIndex(0);
        scrst.setText(null);
        ysrs.setText("");
    }//GEN-LAST:event_btnsclersActionPerformed

    private void btnClosenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClosenActionPerformed
            load_home();
    }//GEN-LAST:event_btnClosenActionPerformed

    private void btnGetTenantsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetTenantsActionPerformed
        retrieveattends();
        totalmattend();
    }//GEN-LAST:event_btnGetTenantsActionPerformed

    private void btnaddemployeesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddemployeesActionPerformed
        displaypanel.removeAll();
        displaypanel.add(AddFacilitators);
        AddFacilitators.setVisible(true);
        AddFacilitators.repaint();
        AddFacilitators.revalidate();
    }//GEN-LAST:event_btnaddemployeesActionPerformed

    private void tableattendanceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableattendanceMouseClicked
        String nam = tableattendance.getValueAt(tableattendance.getSelectedRow(), 1).toString();
        String lnam = tableattendance.getValueAt(tableattendance.getSelectedRow(), 2).toString();
        String numbet = tableattendance.getValueAt(tableattendance.getSelectedRow(), 3).toString();
        String cg = tableattendance.getValueAt(tableattendance.getSelectedRow(), 4).toString();
        String db = tableattendance.getValueAt(tableattendance.getSelectedRow(), 5).toString();
        String str = tableattendance.getValueAt(tableattendance.getSelectedRow(), 6).toString();
        regas.setText(nam);
        nmesa.setText(lnam);
        phantxt.setText(numbet);
        crsza.setText(cg);
        yrsav.setText(db); 
        stresz.setText(str);
    }//GEN-LAST:event_tableattendanceMouseClicked

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
       try {
            String[] options = {"Yes", "No"};
            int answer = JOptionPane.showOptionDialog(null, " Are you Sure to delete ??", "Delete Options", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            if (answer == 0) {
                String id = tableattendance.getValueAt(tableattendance.getSelectedRow(), 0).toString();
                if (deleteattendance(id)) {
                    JOptionPane.showMessageDialog(null, "Members Attendance Data Deleted Successfully");
                    regas.setText("");
                    nmesa.setText("");
                    phantxt.setText("");
                    crsza.setText("");
                    yrsav.setText("");
                    stresz.setText("");
                    retrieveattends();
                    totalmattend();
                } else {
                    JOptionPane.showMessageDialog(null, " Members Attendance Data is Not Deleted ");
                }
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, e);
        }       
    }//GEN-LAST:event_deleteActionPerformed

    private void btnClosetenantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClosetenantActionPerformed
          load_home();
    }//GEN-LAST:event_btnClosetenantActionPerformed

    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogOutActionPerformed
        int dialog_button=JOptionPane.YES_NO_OPTION;
        int logoutrequest=JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?","Log Out",dialog_button);
        if (logoutrequest==0){
            this.dispose();
            LoginForm lo=new LoginForm();
            lo.setVisible(true);
        }
        else {   
        }
    }//GEN-LAST:event_btnLogOutActionPerformed

    private void btnCloseprovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseprovActionPerformed
          load_home();
    }//GEN-LAST:event_btnCloseprovActionPerformed

    private void registerbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerbtnActionPerformed
        try {
            
             String sql = " INSERT INTO members_attendance (regno, name,phonenumber,course, year_semester, status ) VALUES (?,?,?,?,?,?)";
             con = DriverManager.getConnection("jdbc:mysql://localhost/tms","root","");
            pst = con.prepareStatement(sql);
            pst.setString(1, sregtxt.getText());
            pst.setString(2, snametx.getText());
            pst.setString(3, snumtx.getText());
            pst.setString(4, scrst.getText());
            pst.setString(5, ysrs.getText());
            pst.setString(6, statw.getSelectedItem().toString());
          
            if (sregtxt.getText().equals("") || snametx.getText().equals("") || snumtx.getText().equals("") || scrst.getText().equals("") || statw.getSelectedIndex() == 0  ) {
                JOptionPane.showMessageDialog(null, "Some fields not filled in..!!\n Please Fill them to continue.", "Warning", JOptionPane.WARNING_MESSAGE);
                
            } else {
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Members Attendance Recording is Successful");
                   sregtxt.setText(null);
                   snametx.setText(null);
                   snumtx.setText(null);
                   statw.setSelectedIndex(0);
                   scrst.setText(null);
                   ysrs.setText("");
            }
        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }                               
    }//GEN-LAST:event_registerbtnActionPerformed

    private void btnReloadempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadempActionPerformed
        retrievefacilitators();
        totalfacilitators();
    }//GEN-LAST:event_btnReloadempActionPerformed

    private void tblfacilitatorsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblfacilitatorsMouseClicked
        DefaultTableModel model=(DefaultTableModel) tblfacilitators.getModel();
        String selectedfname=(String) model.getValueAt(tblfacilitators.getSelectedRow(), 1);
        String selectedlname=(String) model.getValueAt(tblfacilitators.getSelectedRow(), 2);
        String selectedphone=(String) model.getValueAt(tblfacilitators.getSelectedRow(), 3);
        String selectedsalary=(String) model.getValueAt(tblfacilitators.getSelectedRow(), 4);
        String selectedeusr=(String) model.getValueAt(tblfacilitators.getSelectedRow(), 5);
        txtfnamfc.setText(selectedfname);
        txtpnof.setText(selectedlname);
        txtevtnz.setText(selectedphone);
        dtrevz.setText(selectedsalary);
        txtbioz.setText(selectedeusr);
    }//GEN-LAST:event_tblfacilitatorsMouseClicked

    private void btnDeleteempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteempActionPerformed
          try {
            String[] options = {"Yes", "No"};
            int answer = JOptionPane.showOptionDialog(null, " Are you Sure to delete Facilitators data ??", "Delete Options", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            if (answer == 0) {
                String id = tblfacilitators.getValueAt(tblfacilitators.getSelectedRow(), 0).toString();
                if (deletefacilitators(id)) {
                    JOptionPane.showMessageDialog(null, "Facilitators Data Deleted Successfully");
                    txtfnamfc.setText("");
                    txtpnof.setText("");
                    txtevtnz.setText("");
                    dtrevz.setText("");
                    txtbioz.setText("");
                    retrievefacilitators();
                    totalfacilitators();
                } else {
                    JOptionPane.showMessageDialog(null, " Facilitators Data Not Deleted ");
                }
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, e);
        }        
    }//GEN-LAST:event_btnDeleteempActionPerformed

    private void btnUpdateempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateempActionPerformed
          try {
            if (txtfnamfc.getText().isEmpty() || txtpnof.getText().isEmpty() || dtrevz.getText().isEmpty() || txtbioz.getText().isEmpty() || txtevtnz.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No row is selected..!!\nPlease select a row to update...");
            } else {
                int index = tblfacilitators.getSelectedRow();
                String id = tblfacilitators.getValueAt(index, 0).toString();  //index=rownumber and 0 represents first column..

                if (updatefacilitators(id, txtfnamfc.getText(), txtpnof.getText(), txtevtnz.getText() ,dtrevz.getText(), txtbioz.getText())) {
                    JOptionPane.showMessageDialog(null, "Facilitators Data Updated Successfully");
                    txtfnamfc.setText("");
                    txtpnof.setText("");
                    txtevtnz.setText("");
                    dtrevz.setText("");
                    txtbioz.setText("");
                    retrievefacilitators();
                    totalfacilitators();

                } else {
                    JOptionPane.showMessageDialog(null, "Facilitators Data Not Updated ");
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
        displaypanel.add(manageMembers);
        manageMembers.setVisible(true);
        manageMembers.repaint();
        manageMembers.revalidate();   
    }//GEN-LAST:event_btnmanageusersActionPerformed

    private void btnmanagetenantsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmanagetenantsActionPerformed
         displaypanel.removeAll();
        displaypanel.add(manageattendance);
        manageattendance.setVisible(true);
        manageattendance.repaint();
        manageattendance.revalidate(); 
    }//GEN-LAST:event_btnmanagetenantsActionPerformed

    private void btnmanageempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmanageempActionPerformed
        displaypanel.removeAll();
        displaypanel.add(managefacilitators);
        managefacilitators.setVisible(true);
        managefacilitators.repaint();
        managefacilitators.revalidate();  
    }//GEN-LAST:event_btnmanageempActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
          try {
            String sql = " INSERT INTO facilitators (names, phonenumber, event_name, event_date, Bio )  VALUES (?,?,?,?,?)";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            pst.setString(1, namtfxt.getText());
            pst.setString(2, pftxt.getText());
            pst.setString(3, evftxt.getText());
            String datefevt = ((JTextField) dateevts.getDateEditor().getUiComponent()).getText();
            pst.setString(4, datefevt);
            pst.setString(5, bioft.getText());
            if (namtfxt.getText().equals("") || pftxt.getText().equals("") || evftxt.getText().equals("") || bioft.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Some fields not filled in..!!\n Please Fill them to continue.", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Registration of Facilittors is  Successful");
                namtfxt.setText("");
                pftxt.setText("");
                evftxt.setText("");
                bioft.setText("");
                dateevts.setCalendar(null);
            }
        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                namtfxt.setText("");
                pftxt.setText("");
                evftxt.setText("");
                bioft.setText("");
                dateevts.setCalendar(null);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        load_home();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void recordproviderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recordproviderActionPerformed
         try{
        String sql = " INSERT INTO eventattendance (regnumber, names, phonenumber, email, eventname, eventdate, status ) VALUES (?,?,?,?,?,?,?)";
        con = DriverManager.getConnection("jdbc:mysql://localhost/tms","root","");
        pst = con.prepareStatement(sql);
        pst.setString(1,regevend.getText());
        pst.setString(2, evtpt.getText());
        pst.setString(3, evenyp.getText());
        pst.setString(4, extmail.getText());
        pst.setString(5, evtnb.getText());
        String evtrdate = ((JTextField) dtechoose.getDateEditor().getUiComponent()).getText();
            pst.setString(6, evtrdate);
        pst.setString(7, strevetxt.getSelectedItem().toString());
         if(evtpt.getText().equals("") || evtnb.getText().equals("") || strevetxt.getSelectedIndex()==0 || regevend.getText().equals("") || extmail.getText().equals("")|| evenyp.getText().equals("")){
             JOptionPane.showMessageDialog(null,"Some fields not filled in..!!\n Please Fill them to continue.","Warning",JOptionPane.WARNING_MESSAGE);
         } 
         else
         {
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Recording of Event is Successful");
            regevend.setText("");
            evtpt.setText(" ");
            evtnb.setText(" ");
            strevetxt.setSelectedIndex(0);
            extmail.setText("");
            evenyp.setText("");
            dtechoose.setCalendar(null);
                   }
        } 
        catch(HeadlessException | SQLException ex){
        JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_recordproviderActionPerformed

    private void provresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_provresetActionPerformed
            regevend.setText("");
            evtpt.setText(" ");
            evtnb.setText(" ");
            strevetxt.setSelectedIndex(0);
            extmail.setText("");
            evenyp.setText("");
            dtechoose.setCalendar(null);
    }//GEN-LAST:event_provresetActionPerformed

    private void clearpropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearpropActionPerformed
                propnam.setText(" ");
                quanam.setText("");
                pricep.setText("");
    }//GEN-LAST:event_clearpropActionPerformed

    private void btncloseempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncloseempActionPerformed
             load_home();
    }//GEN-LAST:event_btncloseempActionPerformed

    private void memberssrchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_memberssrchKeyReleased
         String query1 = memberssrch.getText();
        filtermembers(query1);
    }//GEN-LAST:event_memberssrchKeyReleased

    private void attedsrchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_attedsrchKeyReleased
           String query1 = attedsrch.getText();
           filterattendance(query1);
    }//GEN-LAST:event_attedsrchKeyReleased

    private void empsearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_empsearchKeyReleased
       String query1 = empsearch.getText();
       filterfacilitators(query1);
    }//GEN-LAST:event_empsearchKeyReleased

    private void btnretrieveprovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnretrieveprovActionPerformed
         retreaveattendevents();
         totalattendeventss();
    }//GEN-LAST:event_btnretrieveprovActionPerformed

    private void btnupdateprovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateprovActionPerformed
      try {
            if (mangatt.getText().isEmpty() || managnams.getText().isEmpty() || managnum.getText().isEmpty() || managemail.getText().isEmpty() || manageevy.getText().isEmpty()|| managedte.getText().isEmpty()|| managests.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No row is selected..!!\nPlease select a row to update...");
            } else {
                int index = eventAttendanceTable.getSelectedRow();
                String id = eventAttendanceTable.getValueAt(index, 0).toString();
                if (updateattendevents(id, mangatt.getText(), managnams.getText(), managnum.getText(), managemail.getText(),managedte.getText(), manageevy.getText(),managests.getText())) {
                    JOptionPane.showMessageDialog(null, "Service Providers Data updated successifully");
                    mangatt.setText("");
                    managnams.setText("");
                    managnum.setText("");
                    managemail.setText("");
                    manageevy.setText("");
                    managedte.setText("");
                    managests.setText("");
                    retdates.setText("");
                    retreaveattendevents();
                    totalattendeventss();
                } else {
                    JOptionPane.showMessageDialog(null, "Service Providers Data Not updated ");
                }
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btnupdateprovActionPerformed

    private void btndeleteProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteProvActionPerformed
         try {
            String[] options = {"Yes", "No"};
            int answer = JOptionPane.showOptionDialog(null, " Are you Sure to delete Event Attendance Data ??", "Delete Options", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            if (answer == 0) {
                String atts_id = eventAttendanceTable.getValueAt(eventAttendanceTable.getSelectedRow(), 0).toString();   
                if (deleteattendevents(atts_id)) {
                    JOptionPane.showMessageDialog(null, "Event Attendance Data deleted successfully");
                    mangatt.setText("");
                    managnams.setText("");
                    managnum.setText("");
                    managemail.setText("");
                    manageevy.setText("");
                    managedte.setText("");
                    managests.setText("");
                    retdates.setText("");
                    retreaveattendevents();
                    totalattendeventss();
                } else {
                    JOptionPane.showMessageDialog(null, "Event Attendance Data is Not deleted ");
                }
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_btndeleteProvActionPerformed

    private void eventatsearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_eventatsearchKeyReleased
        String query1 = eventatsearch.getText();
        filterattendevents(query1);
    }//GEN-LAST:event_eventatsearchKeyReleased

    private void saveregulationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveregulationsActionPerformed
       try {
           Class.forName("com.mysql.jdbc.Driver");
           String sql ="UPDATE `rules_regulations` SET `rules`=? WHERE id=1";
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalmanagementsystem","root","");
           pst = con.prepareStatement(sql);
           pst.setString(1,calendactv.getText());
           pst.executeUpdate();
           calendactv.setText("");
           JOptionPane.showMessageDialog(null,"Rules and Regulation Successfully Saved");
       } catch (ClassNotFoundException | SQLException ex) {
           Logger.getLogger(SecretaryHome.class.getName()).log(Level.SEVERE, null, ex);
       }
    }//GEN-LAST:event_saveregulationsActionPerformed

    private void clsrulesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clsrulesActionPerformed
         load_home();
    }//GEN-LAST:event_clsrulesActionPerformed

    private void viewregulationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewregulationsActionPerformed
        
       try {
           Class.forName("com.mysql.jdbc.Driver");
           String sql ="SELECT `Event` FROM `calendar_of_activities` WHERE id=1";
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms","root","");
           pst = con.prepareStatement(sql);
           rs = pst.executeQuery();
           if (rs.next()){
           String secactv = rs.getString("Event");
          calactvs.setText("*******************************************\n");
          calactvs.setText(calactvs.getText()+"    CALENDAR OF ACTIVITES \n");
          calactvs.setText(calactvs.getText()+"*******************************************\n");
           calactvs.setText(calactvs.getText()+ secactv);
           }
       } catch (ClassNotFoundException | SQLException ex) {
           Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
       }
    }//GEN-LAST:event_viewregulationsActionPerformed

    private void printregulationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printregulationsActionPerformed
          try {
            calactvs.print();
            calactvs.setText("");
            load_home();
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }  
    }//GEN-LAST:event_printregulationsActionPerformed

    private void closeregulationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeregulationsActionPerformed
        load_home();
    }//GEN-LAST:event_closeregulationsActionPerformed

    private void viewcalsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewcalsActionPerformed
        displaypanel.removeAll();
        displaypanel.add(ViewCalendarActivities);
        ViewCalendarActivities.setVisible(true);
        ViewCalendarActivities.repaint();
        ViewCalendarActivities.revalidate();
    }//GEN-LAST:event_viewcalsActionPerformed

    private void closepropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closepropActionPerformed
         load_home();         
    }//GEN-LAST:event_closepropActionPerformed

    private void eventAttendanceTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eventAttendanceTableMouseClicked
        String pron = eventAttendanceTable.getValueAt(eventAttendanceTable.getSelectedRow(), 1).toString();
        String proc = eventAttendanceTable.getValueAt(eventAttendanceTable.getSelectedRow(), 2).toString();
        String prot = eventAttendanceTable.getValueAt(eventAttendanceTable.getSelectedRow(), 3).toString();
        String prol = eventAttendanceTable.getValueAt(eventAttendanceTable.getSelectedRow(), 4).toString();
        String patr = eventAttendanceTable.getValueAt(eventAttendanceTable.getSelectedRow(), 5).toString();
        String atrp = eventAttendanceTable.getValueAt(eventAttendanceTable.getSelectedRow(), 6).toString();
        String pritp = eventAttendanceTable.getValueAt(eventAttendanceTable.getSelectedRow(), 7).toString();

        mangatt.setText(pron);
        managnams.setText(proc);
        managnum.setText(prot);
        manageevy.setText(prol);
        manageevy.setText(patr);
        manageevy.setText(atrp);
        manageevy.setText(pritp);
    }//GEN-LAST:event_eventAttendanceTableMouseClicked

    private void genrentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genrentActionPerformed
      String FILE_NAME = "C:\\Users\\MICHAEL\\Documents\\NetBeansProjects\\TechnoScienceManagementSystem\\Attendance.pdf";
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(new File(FILE_NAME)));
            document.open();

            document.add(new Paragraph(new Date().toString()));

            document.add(new Paragraph("TECHNOSCIENCE ATTENDANCE REPORT"));

            document.add(new Paragraph("------------------------------------------------------------------------------------------------------------"));
            PdfPTable tablesup = new PdfPTable(8);
            tablesup.setWidthPercentage(100);
            tablesup.setSpacingBefore(10f);
            tablesup.setSpacingAfter(10f);

            tablesup.addCell("Attendance id");
            tablesup.addCell("Registration Number");
            tablesup.addCell("Name");
            tablesup.addCell("Phonenumber");
            tablesup.addCell("course");
            tablesup.addCell("Year and Sem");
            tablesup.addCell("Status");
            tablesup.addCell("Event Date");

            Connection con;
            Statement st;
            ResultSet rs;

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms", "root", "");
            st = con.createStatement();
            String query = "Select * from members_attendanc";
            rs = st.executeQuery(query);
            while (rs.next()) {
                String v1 = rs.getString("Attendance_id");
                String v2 = rs.getString("regno");
                String v3 = rs.getString("name");
                String v4 = rs.getString("phonenumber");
                String v5 = rs.getString("course");
                String v6 = rs.getString("year_semester");
                String v7 = rs.getString("status");
                String v8 = rs.getString("date_time");
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
            JOptionPane.showMessageDialog(null, "Attendance Report Saved");
            JOptionPane.showMessageDialog(null, "You can Now view Report in your System Directory");
             load_home();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_genrentActionPerformed

    private void closerentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closerentActionPerformed
         load_home();       
    }//GEN-LAST:event_closerentActionPerformed

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
           Logger.getLogger(SecretaryHome.class.getName()).log(Level.SEVERE, null, ex);
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
               Logger.getLogger(SecretaryHome.class.getName()).log(Level.SEVERE, null, ex);
            }
        }          
    }//GEN-LAST:event_rmupdKeyPressed

    private void closeprovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeprovActionPerformed
         load_home();
    }//GEN-LAST:event_closeprovActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
         load_home();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        retrieverentprop();
        totalattendeventss();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
       try {
            if (propntxt.getText().isEmpty() || propqtxt.getText().isEmpty() || propptxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No row is selected..!!\nPlease select a row to update...");
            } else {
                int index = proprents.getSelectedRow();
                String id = proprents.getValueAt(index, 0).toString();

                if (updateprops(id, propntxt.getText(), propqtxt.getText(), propptxt.getText())) {
                    JOptionPane.showMessageDialog(null, "Rental Data is Updated Successfully");
                    propntxt.setText("");
                    propqtxt.setText("");
                    propptxt.setText("");
                    retrieverentprop();
                    totalattendeventss();
                } else {
                    JOptionPane.showMessageDialog(null, "Data Not Updated ");
                }
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
         try {
            String[] options = {"Yes", "No"};
            int answer = JOptionPane.showOptionDialog(null, " Are you Sure to delete ??", "Delete Options", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            if (answer == 0) {
                String id = proprents.getValueAt(proprents.getSelectedRow(), 0).toString();
                if (delete(id)) {
                    JOptionPane.showMessageDialog(null, "Properties Data Deleted Successfully");
                    propntxt.setText("");
                    propqtxt.setText("");
                    propptxt.setText("");
                    retrieverentprop();
                    totalattendeventss();
                } else {
                    JOptionPane.showMessageDialog(null, " Property Data Not Deleted ");
                }
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void proprentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proprentsMouseClicked
        String name = proprents.getValueAt(proprents.getSelectedRow(), 1).toString();
        String pquant = proprents.getValueAt(proprents.getSelectedRow(), 2).toString();
        String pprice = proprents.getValueAt(proprents.getSelectedRow(), 3).toString();
        propntxt.setText(name);
        propqtxt.setText(pquant);
        propptxt.setText(pprice);
    }//GEN-LAST:event_proprentsMouseClicked

    private void saveregulations1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveregulations1ActionPerformed
        try {
           Class.forName("com.mysql.jdbc.Driver");
           String sql ="INSERT INTO notices (noticename)  VALUES(?)";
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms","root","");
           pst = con.prepareStatement(sql);
           pst.setString(1,secuploads.getText());
             if (secuploads.getText().isEmpty() ){
             JOptionPane.showMessageDialog(null,"Please Enter Notice to Upload");    
             }
             else{ 
           pst.executeUpdate();
           secuploads.setText("");
           JOptionPane.showMessageDialog(null,"Successfully Uploaded");
             }
       } catch (ClassNotFoundException | SQLException ex) {
           Logger.getLogger(TreasurerHome.class.getName()).log(Level.SEVERE, null, ex);
       }
    }//GEN-LAST:event_saveregulations1ActionPerformed

    private void clsrules1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clsrules1ActionPerformed
        load_home();
    }//GEN-LAST:event_clsrules1ActionPerformed

    private void saveregulations2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveregulations2ActionPerformed
          try {
           Class.forName("com.mysql.jdbc.Driver");
           String sql ="SELECT `Event` FROM `calendar_of_activities` WHERE id=1";
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms","root","");
           pst = con.prepareStatement(sql);
           rs = pst.executeQuery();
           if (rs.next()){
           String rules = rs.getString("Event");
          calendactv.setText("******************************************************\n");
          calendactv.setText(calendactv.getText()+"   TECHNOSCIENCE CALENDAR OF ACTIVITIES \n");
         calendactv.setText(calendactv.getText()+"*******************************************\n");
           calendactv.setText(calendactv.getText()+rules);
           }
       } catch (ClassNotFoundException | SQLException ex) {
           Logger.getLogger(SecretaryHome.class.getName()).log(Level.SEVERE, null, ex);
       }        
    }//GEN-LAST:event_saveregulations2ActionPerformed

    private void btnsecoticeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsecoticeActionPerformed
            try {
           Class.forName("com.mysql.jdbc.Driver");
           String sql ="SELECT * FROM `notices`";
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms","root","");
           pst = con.prepareStatement(sql);
           rs = pst.executeQuery();
           viewsecnotice.setText("*******************************************\n");
           viewsecnotice.setText(viewsecnotice.getText()+"     VIEW AVAILABLE NOTICES \n");
           viewsecnotice.setText(viewsecnotice.getText()+"*******************************************\n");
           viewsecnotice.setText(viewsecnotice.getText()+"NO.    NOTICE NAME\n");
           while (rs.next()){
           String id = rs.getString("notice_id");
           String notlst = rs.getString("noticename");
           String date = rs.getString("date_recorded");
           viewsecnotice.setText("\n"+viewsecnotice.getText()+ id+"    "+notlst+" DATE---->> "+date+"\n");
           }
       } catch (ClassNotFoundException | SQLException ex) {
           Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
       }                                 
    }//GEN-LAST:event_btnsecoticeActionPerformed

    private void clsnoticesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clsnoticesActionPerformed
        load_home();
    }//GEN-LAST:event_clsnoticesActionPerformed

    private void sregtxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sregtxtKeyPressed
              if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                String txtregv = sregtxt.getText();
                pst = con.prepareStatement("Select * from members where regno = ?");
                pst.setString(1, txtregv);
                rs = pst.executeQuery();
                if (rs.next() == false) {
                    JOptionPane.showMessageDialog(this, "The Registration Number You Entered is not found try Again");
                } else {
                    String name = rs.getString("names");
                     snametx.setText(name.trim());
                     String regist = rs.getString("phonenumbers");
                     snumtx.setText(regist.trim());
                     String crs = rs.getString("course");
                     scrst.setText(crs.trim());
                     String ysm = rs.getString("year_semester");
                     ysrs.setText(ysm.trim());
                     statw.setSelectedIndex(1);
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }          
    }//GEN-LAST:event_sregtxtKeyPressed

    private void dtechooseKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dtechooseKeyPressed
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                String evtrdate = ((JTextField) dtechoose.getDateEditor().getUiComponent()).getText();
                pst.setString(1, evtrdate);
                pst = con.prepareStatement("Select * from facilitators where Event_date = ?");
                pst.setString(1, evtrdate);
                rs = pst.executeQuery();
                if (rs.next() == false) {
                    JOptionPane.showMessageDialog(this, "The Event Date You Entered is not found try Again");
                } else {
                    String evrname = rs.getString("Event_name");
                     evtnb.setText(evrname.trim());
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }                  
    }//GEN-LAST:event_dtechooseKeyPressed

    private void regevendKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_regevendKeyPressed
         if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                String eventtyr = regevend.getText();
                pst = con.prepareStatement("Select * from members where regno = ?");
                pst.setString(1, eventtyr);
                rs = pst.executeQuery();
                if (rs.next() == false) {
                    JOptionPane.showMessageDialog(this, "The Registration Number You Entered is not found try Again");
                } else {
                    String attname = rs.getString("names");
                     evtpt.setText(attname.trim());
                     String attregist = rs.getString("phonenumbers");
                     evenyp.setText(attregist.trim());
                     extmail.requestFocus();
                     strevetxt.setSelectedIndex(1);
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }          
    }//GEN-LAST:event_regevendKeyPressed

    private void btnaddserviceprovider1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddserviceprovider1ActionPerformed
        displaypanel.removeAll();
        displaypanel.add(AddEventAttendance);
        AddEventAttendance.setVisible(true);
        AddEventAttendance.repaint();
        AddEventAttendance.revalidate();         
    }//GEN-LAST:event_btnaddserviceprovider1ActionPerformed

    private void viewcals1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewcals1ActionPerformed
        displaypanel.removeAll();
        displaypanel.add(ViewNotices);
        ViewNotices.setVisible(true);
        ViewNotices.repaint();
        ViewNotices.revalidate();  
    }//GEN-LAST:event_viewcals1ActionPerformed

    private void viewcals2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewcals2ActionPerformed
        displaypanel.removeAll();
        displaypanel.add(UploadNotices);
        UploadNotices.setVisible(true);
        UploadNotices.repaint();
        UploadNotices.revalidate();
    }//GEN-LAST:event_viewcals2ActionPerformed

    private void saveregulations3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveregulations3ActionPerformed
        secuploads.setText("");
    }//GEN-LAST:event_saveregulations3ActionPerformed

    private void gentenant1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gentenant1ActionPerformed
          String FILE_NAME = "C:\\Users\\MICHAEL\\Documents\\NetBeansProjects\\TechnoScienceManagementSystem\\MembersAttendance.pdf";
            Document document = new Document();
     try{
         PdfWriter.getInstance(document, new FileOutputStream(new File(FILE_NAME)));
         document.open();
     
         document.add(new Paragraph(new Date().toString()));
         
         document.add(new Paragraph("TECHNOSCIENCE Members ATTENDANCE REPORT"));
          
          document.add(new Paragraph("------------------------------------------------------------------------------------------------------------"));
          PdfPTable tablesup = new PdfPTable(8);
          tablesup.setWidthPercentage(100);
          tablesup.setSpacingBefore(10f);
          tablesup.setSpacingAfter(10f);
         
         tablesup.addCell("Attendance id");
         tablesup.addCell("Registration Number");
         tablesup.addCell("Names");
         tablesup.addCell("Phonenumbers");
         tablesup.addCell("Course");
         tablesup.addCell("Year Sem");
         tablesup.addCell("Status");
         tablesup.addCell("Date");
         
         Connection con;
         Statement st;
         ResultSet rs;
         Class.forName("com.mysql.jdbc.Driver");
         con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms","root","");
         st = con.createStatement();
         String query = "Select * from members_attendance";
         rs = st.executeQuery(query);
         while(rs.next()){
           String v1 = rs.getString("Attendance_id");
           String v2 = rs.getString("regno");
           String v3 = rs.getString("name");
           String v4 = rs.getString("phonenumber");
           String v5 = rs.getString("course");
           String v6 = rs.getString("year_semester");
           String v7 = rs.getString("status");
           String v8 = rs.getString("date_time");
           
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
         JOptionPane.showMessageDialog(null, "Members Attendance Report Saved"); 
         JOptionPane.showMessageDialog(null, "You can now view Report in your System Directory");
          load_home();
     } catch(Exception e){
         e.printStackTrace();
     }        
    }//GEN-LAST:event_gentenant1ActionPerformed

    private void closetenant1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closetenant1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_closetenant1ActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
 try {
            String regNo = sregtxt.getText();
            String sql2 = "SELECT";
            //Connection
            
            
            
            
            if (rs.next()){
                int currentStatus = Integer.parseInt(rs.getString("status"));
                int newStatus = currentStatus +1;
                
                String sql = " UPDATE members_attendance SET status = '"+newStatus+"' WHERE ";
             con = DriverManager.getConnection("jdbc:mysql://localhost/tms","root","");
            pst = con.prepareStatement(sql);
          
            if (sregtxt.getText().equals("") || snametx.getText().equals("") || snumtx.getText().equals("") || scrst.getText().equals("") || statw.getSelectedIndex() == 0  ) {
                JOptionPane.showMessageDialog(null, "Some fields not filled in..!!\n Please Fill them to continue.", "Warning", JOptionPane.WARNING_MESSAGE);
                
            } else {
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Recording of Members Registration is Successful");
                   sregtxt.setText(null);
                   snametx.setText(null);
                   snumtx.setText(null);
                   statw.setSelectedIndex(0);
                   scrst.setText(null);
                   ysrs.setText("");
            }
            }
             
        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }                                // TODO add your handling code here:
    }//GEN-LAST:event_updateActionPerformed

    private void mbreportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mbreportsActionPerformed
        displaypanel.removeAll();
        displaypanel.add(MembersReport);
        MembersReport.setVisible(true);
        MembersReport.repaint();
        MembersReport.revalidate();
    }//GEN-LAST:event_mbreportsActionPerformed

    private void repattsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_repattsActionPerformed
        displaypanel.removeAll();
        displaypanel.add(EventAttendanceReport);
        EventAttendanceReport.setVisible(true);
        EventAttendanceReport.repaint();
        EventAttendanceReport.revalidate();
    }//GEN-LAST:event_repattsActionPerformed

    private void btnmanageusers1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmanageusers1ActionPerformed
        displaypanel.removeAll();
        displaypanel.add(AddAttendance);
        AddAttendance.setVisible(true);
        AddAttendance.repaint();
        AddAttendance.revalidate();
        
    }//GEN-LAST:event_btnmanageusers1ActionPerformed

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
            java.util.logging.Logger.getLogger(SecretaryHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SecretaryHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SecretaryHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SecretaryHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SecretaryHome().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddAttendance;
    private javax.swing.JPanel AddEventAttendance;
    private javax.swing.JPanel AddFacilitators;
    private javax.swing.JPanel Addrentalproperties;
    private javax.swing.JPanel AttendanceReport;
    private javax.swing.JPanel CalendarActivities;
    private javax.swing.JPanel EventAttendanceReport;
    private javax.swing.JPanel ManageEventAttendance;
    private javax.swing.JPanel MembersReport;
    private javax.swing.JPanel RegisterMembers;
    private javax.swing.JPanel UpdateRooms;
    private javax.swing.JPanel UploadNotices;
    private javax.swing.JPanel ViewCalendarActivities;
    private javax.swing.JPanel ViewComplains;
    private javax.swing.JPanel ViewNotices;
    private javax.swing.JLabel addusrlabel;
    private javax.swing.JLabel ans;
    private javax.swing.JLabel ans1;
    private javax.swing.JLabel answerlabel;
    private javax.swing.JComboBox<String> arsps;
    private javax.swing.JTextField attedsrch;
    private javax.swing.JTextArea bioft;
    private javax.swing.JLabel blrm1;
    private javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnAddusers;
    private javax.swing.JButton btnClearUser;
    private javax.swing.JButton btnClearrm1;
    private javax.swing.JButton btnCloseMaintainUsers;
    private javax.swing.JButton btnCloseNewUser;
    private javax.swing.JButton btnClosen;
    private javax.swing.JButton btnCloseprov;
    private javax.swing.JButton btnCloserm1;
    private javax.swing.JButton btnClosetenant;
    private javax.swing.JButton btnDeleteUser;
    private javax.swing.JButton btnDeleteemp;
    private javax.swing.JButton btnGetTenants;
    private javax.swing.JPanel btnGroup;
    private javax.swing.JButton btnLogOut;
    private javax.swing.JButton btnReloademp;
    private javax.swing.JButton btnUpdateUserInfo;
    private javax.swing.JButton btnUpdateemp;
    private javax.swing.JButton btnaddemployees;
    private javax.swing.JButton btnaddrm1;
    private javax.swing.JButton btnaddserviceprovider1;
    private javax.swing.JButton btncloseemp;
    private javax.swing.JButton btndeleteProv;
    private javax.swing.JButton btnmanageemp;
    private javax.swing.JButton btnmanagetenants;
    private javax.swing.JButton btnmanageusers;
    private javax.swing.JButton btnmanageusers1;
    private javax.swing.JButton btnretrieveprov;
    private javax.swing.JButton btnsclers;
    private javax.swing.JButton btnsecotice;
    private javax.swing.JButton btnupdateprov;
    private javax.swing.JTextArea calactvs;
    private javax.swing.JTextArea calendactv;
    private javax.swing.JButton clearprop;
    private javax.swing.JButton closeprop;
    private javax.swing.JButton closeprov;
    private javax.swing.JButton closeregulations;
    private javax.swing.JButton closerent;
    private javax.swing.JButton closetenant;
    private javax.swing.JButton closetenant1;
    private javax.swing.JButton clsbtns;
    private javax.swing.JButton clsnotices;
    private javax.swing.JButton clsrules;
    private javax.swing.JButton clsrules1;
    private javax.swing.JTextArea complainsview;
    private javax.swing.JLabel confpasslabel;
    private javax.swing.JTextField coursemtxts;
    private javax.swing.JTextField crsza;
    private javax.swing.JComboBox crxs;
    private com.toedter.calendar.JDateChooser dateevts;
    private com.toedter.calendar.JDateChooser dateregs;
    private javax.swing.JButton delete;
    private javax.swing.JPanel displaypanel;
    private com.toedter.calendar.JDateChooser dtechoose;
    private javax.swing.JTextField dtrevz;
    private javax.swing.JTextField empsearch;
    private javax.swing.JTable eventAttendanceTable;
    private javax.swing.JTextField eventatsearch;
    private javax.swing.JTextField evenyp;
    private javax.swing.JTextField evftxt;
    private javax.swing.JTextField evtnb;
    private javax.swing.JTextField evtpt;
    private javax.swing.JTextField extmail;
    private javax.swing.JLabel fname;
    private javax.swing.JButton genrent;
    private javax.swing.JButton gentenant;
    private javax.swing.JButton gentenant1;
    private javax.swing.JPanel homepanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JLabel lblEmpfname;
    private javax.swing.JLabel lblEmplname;
    private javax.swing.JLabel lblEmppno;
    private javax.swing.JLabel lblEmpsal;
    private javax.swing.JLabel lblEusername;
    private javax.swing.JLabel lblFilldet1;
    private javax.swing.JLabel lblReports2;
    private javax.swing.JLabel lblReports3;
    private javax.swing.JLabel lblavailablestock;
    private javax.swing.JLabel lblbuyingprice;
    private javax.swing.JLabel lblprodname;
    private javax.swing.JLabel lblread;
    private javax.swing.JLabel lblread2;
    private javax.swing.JLabel lblread3;
    private javax.swing.JLabel lblrent;
    private javax.swing.JLabel lblsellingprice;
    private javax.swing.JLabel lblsellingprice1;
    private javax.swing.JLabel lblsellingprice2;
    private javax.swing.JLabel lblstat1;
    private javax.swing.JLabel lbltenant;
    private javax.swing.JLabel lbltenant1;
    private javax.swing.JLabel lblusername;
    private javax.swing.JLabel lblwritting;
    private javax.swing.JLabel lbnum1;
    private javax.swing.JLabel lname;
    private javax.swing.JLabel loginlabel;
    private javax.swing.JPanel manageMembers;
    private javax.swing.JPanel manageattendance;
    private javax.swing.JTextField managedte;
    private javax.swing.JTextField manageevy;
    private javax.swing.JPanel managefacilitators;
    private javax.swing.JTextField managemail;
    private javax.swing.JPanel manageproperties;
    private javax.swing.JTextField managests;
    private javax.swing.JTextField managnams;
    private javax.swing.JTextField managnum;
    private javax.swing.JTextField mangatt;
    private javax.swing.JButton mbreports;
    private javax.swing.JTextField memberssrch;
    private javax.swing.JTable memberstable;
    private javax.swing.JTextField namentxts;
    private javax.swing.JTextField namtfxt;
    private javax.swing.JTextField namzs;
    private javax.swing.JTextField nmesa;
    private javax.swing.JLabel num;
    private javax.swing.JLabel occlabel;
    private javax.swing.JLabel occlabel1;
    private javax.swing.JLabel pass;
    private javax.swing.JLabel passwordlabel;
    private javax.swing.JTextField pftxt;
    private javax.swing.JTextField phantxt;
    private javax.swing.JTextField phnts;
    private javax.swing.JLabel phonelabel;
    private javax.swing.JTextField phup;
    private javax.swing.JLabel plabel;
    private javax.swing.JLabel position;
    private javax.swing.JTextField pricep;
    private javax.swing.JButton printregulations;
    private javax.swing.JLabel proddetails;
    private javax.swing.JTextField propertytots;
    private javax.swing.JLabel proplbl;
    private javax.swing.JTextField propnam;
    private javax.swing.JTextField propntxt;
    private javax.swing.JTextField propptxt;
    private javax.swing.JTextField propqtxt;
    private javax.swing.JTable proprents;
    private javax.swing.JButton provreset;
    private javax.swing.JTextField quanam;
    private javax.swing.JButton recordprop;
    private javax.swing.JButton recordprovider;
    private javax.swing.JTextField regas;
    private javax.swing.JTextField regevend;
    private javax.swing.JButton registerbtn;
    private javax.swing.JTextField regntxts;
    private javax.swing.JButton repatts;
    private javax.swing.JTextField retdates;
    private javax.swing.JButton retrieve;
    private javax.swing.JTextField rgss;
    private javax.swing.JTextField rmupd;
    private javax.swing.JLabel rolelabel;
    private javax.swing.JLabel roomno1;
    private javax.swing.JButton saveregulations;
    private javax.swing.JButton saveregulations1;
    private javax.swing.JButton saveregulations2;
    private javax.swing.JButton saveregulations3;
    private javax.swing.JTextField scrst;
    private javax.swing.JTextArea secuploads;
    private javax.swing.JLabel security;
    private javax.swing.JLabel securitylabel;
    public javax.swing.JLabel secusername;
    private javax.swing.JTextField snametx;
    private javax.swing.JTextField snumtx;
    private javax.swing.JLabel softwareName;
    private javax.swing.JLabel softwaredesc;
    private javax.swing.JLabel softwaredesc1;
    private javax.swing.JLabel softwarename;
    private javax.swing.JLabel softwarename1;
    private javax.swing.JTextField speztxts;
    private javax.swing.JTextField sregtxt;
    private javax.swing.JComboBox<String> statw;
    private javax.swing.JLabel stockmgmt1;
    private javax.swing.JTextField stresz;
    private javax.swing.JComboBox<String> strevetxt;
    private javax.swing.JTable tableattendance;
    private javax.swing.JTable tblfacilitators;
    private javax.swing.JTextField totalfacl;
    private javax.swing.JTextField totalms;
    private javax.swing.JTextField totalzattend;
    private javax.swing.JTextField totattevtd;
    private javax.swing.JTextArea txtbioz;
    private javax.swing.JTextField txtevtnz;
    private javax.swing.JTextField txtfnamfc;
    private javax.swing.JTextField txtmreg;
    private javax.swing.JTextField txtphonem;
    private javax.swing.JTextField txtpnof;
    private javax.swing.JComboBox<String> txtstatup;
    private javax.swing.JButton update;
    private javax.swing.JLabel usernamelabel;
    private javax.swing.JLabel usrmgmt;
    private javax.swing.JButton viewcals;
    private javax.swing.JButton viewcals1;
    private javax.swing.JButton viewcals2;
    private javax.swing.JButton viewcomp;
    private javax.swing.JButton viewregulations;
    private javax.swing.JTextArea viewsecnotice;
    private javax.swing.JComboBox<String> yerss;
    private javax.swing.JTextField yrsav;
    private javax.swing.JTextField ysrs;
    private javax.swing.JTextField ysstxt;
    // End of variables declaration//GEN-END:variables

    
    
}
