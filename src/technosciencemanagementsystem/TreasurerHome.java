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
public class TreasurerHome extends javax.swing.JFrame {
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    private JFrame frame;
    DefaultTableModel model;
    public static String recreg;
    public static String fnames;
    public static String lnames;
    public static String amounts;
    public static String arrearst;
    public static String rnums;
    
    public static String subregs;
    public static String subnames;
    public static String subphone;
    public static String subyr;
    public static String subpurp;
    public static String sabamt;
    
    public TreasurerHome() {
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
           Logger.getLogger(TreasurerHome.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SQLException ex) {
            Logger.getLogger(TreasurerHome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
           private void load_home(){
           displaypanel.removeAll();
           displaypanel.add(homepanel);
           homepanel.setVisible(true);
           homepanel.repaint();
           homepanel.revalidate();  
           btnGroup.setVisible(true);
       }
    
  //The below classes are used to retrieve and Delete Data to the Database
        private void retrievefrecords() {
        try {
            String sql = "SELECT * FROM Finance";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
             model = (DefaultTableModel) tablefinance.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String id = rs.getString(1);
                String fregs = rs.getString(2);
                String nameuser = rs.getString(3);
                String phonenumber = rs.getString(4);
                String fy = rs.getString(5);
                String fpur = rs.getString(6);
                String famtrs = rs.getString(7);
                String fdtr = rs.getString(8);
                model.addRow(new String[]{id, fregs, nameuser, phonenumber, fy, fpur, famtrs, fdtr});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public Boolean updatefrecords(String id, String regft, String nmft, String passt, String semtf, String usrole, String amtf) {
        String query = "UPDATE Finance SET regnumber='" + regft + "',name='" + nmft + "',phonenumber='" + passt + "', year_sem ='" + semtf + "', purpose='" + usrole+ "',Amount='" + amtf + "' WHERE finance_id= '" + id + "'";
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

    public Boolean deletefrecords(String id) {

        String sql = "DELETE FROM Finance WHERE finance_id ='" + id + "'";
        try {
            con = DriverManager.getConnection("jdbc:mysql://tms/tms", "root", "");
            pst = con.prepareStatement(sql);
            pst.execute(sql);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
     private void totalmemberrecs() {
        try {
            String sql = "SELECT SUM(Amount)amtsc FROM Finance ";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
             int totalmonies = Integer.parseInt(rs.getString("amtsc"));
             mebtr.setText(String.valueOf(totalmonies));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        }
    private void filterfrecords(String query) {
        DefaultTableModel model2 =  (DefaultTableModel) tablefinance.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model2);
        tablefinance.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }
     private void retrieveselectedfinaces() {
        try {
            String typesel = selecttype.getSelectedItem().toString();
            String sql = "SELECT * FROM Finance WHERE purpose='"+typesel+"'";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            model = (DefaultTableModel) selectedfinace.getModel();
            model.setRowCount(0);
           while (rs.next()) {
                String idn = rs.getString(1);
                String selreg = rs.getString(2);
                String selname = rs.getString(3);
                String selpn = rs.getString(4);
                String seltyp = rs.getString(5);
                String selno = rs.getString(6);
                String selm = rs.getString(7);
                model.addRow(new String[]{idn, selreg, selname, selpn, seltyp, selno, selm});
            }   
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public Boolean updateselectedfinaces(String id, String fredg, String nmdr,String runyw, String mwks ,  String peza, String pezy) {
         String query = "UPDATE Finance SET regnumber='" + fredg+ "',name='" + nmdr + "',phonenumber='" + runyw + "', year_sem ='" + mwks + "', purpose='" + peza+ "',Amount='" + pezy + "' WHERE finance_id= '" + id + "'";
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(query);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
    public Boolean deleteselectedfinaces(String id) {
        String sql = "DELETE FROM Finance WHERE Finance_id  ='" + id + "'";
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
        private void filterselectedfinaces(String query) {
        DefaultTableModel model3 =  (DefaultTableModel) selectedfinace.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model3);
        selectedfinace.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
          }
         private void totalselectedfinaces() {
        try {
            String sql = "SELECT SUM(Amount)amtsc FROM Finance where purpose = ? ";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            pst.setString(1, selecttype.getSelectedItem().toString());
            rs = pst.executeQuery();
            if (rs.next()) {
             int totalmonies = Integer.parseInt(rs.getString("amtsc"));
             totalsubrmem.setText(String.valueOf(totalmonies));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
         }
         
           private void totaltrememberselctedgr() {
        try {
            String sql = "SELECT COUNT(finance_id)totmemgj FROM Finance WHERE purpose = ?";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            pst.setString(1, selectyrs.getSelectedItem().toString());
            rs = pst.executeQuery();
             if (rs.next()){
              int total =Integer.parseInt(rs.getString("totmemgj")) ;
             totselcy.setText(String.valueOf(total));
           }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
           }
           
       private void retrieveyearselect() {
        try {
            String sql = "SELECT * FROM Finance";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            model = (DefaultTableModel) tblfinacialyrs.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String id = rs.getString(1);
                String regy = rs.getString(2);
                String nmy = rs.getString(3);
                String phmy = rs.getString(4);
                String yumy = rs.getString(5);
                String pupy = rs.getString(6);
                String damy = rs.getString(7);
                String ddtys = rs.getString(8);
                model.addRow(new String[]{id, regy, nmy, phmy, yumy, pupy, damy, ddtys});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
       }
        
         private void retrieveyearsel() {
        try {
            String typeyr = selectyrs.getSelectedItem().toString();
            String sql = "SELECT * FROM Finance WHERE year_sem='"+typeyr+"'";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            model = (DefaultTableModel) tblfinacialyrs.getModel();
            model.setRowCount(0);
           while (rs.next()) {
                String idn = rs.getString(1);
                String selrega = rs.getString(2);
                String selname = rs.getString(3);
                String selpne = rs.getString(4);
                String seltypw = rs.getString(5);
                String selnos = rs.getString(6);
                String selmr = rs.getString(7);
                model.addRow(new String[]{idn, selrega, selname, selpne, seltypw, selnos, selmr});
            }   
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    } 
            private void totalaselectedyrw() {
        try {
            String sql = "SELECT SUM(Amount)amtye FROM Finance where year_sem = ? ";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            pst.setString(1, selectyrs.getSelectedItem().toString());
            rs = pst.executeQuery();
            if (rs.next()) {
             int totalmonies = Integer.parseInt(rs.getString("amtye"));
             totalamwers.setText(String.valueOf(totalmonies));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
         }
            
             private void totaltremebersd() {
        try {
            String sql = "SELECT COUNT(finance_id)totmemg FROM Finance year_sem = ?";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            pst.setString(1, selectyrs.getSelectedItem().toString());
            rs = pst.executeQuery();
             if (rs.next()){
              int total =Integer.parseInt(rs.getString("totmemg")) ;
              totalystd.setText(String.valueOf(total));
           }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        }
                       
    public Boolean updateyearselect(String id, String flegs, String namey, String pfnnumber, String wtype, String rtmpy, String ysamr) {
         String query = "UPDATE Finance SET regnumber='" + flegs+ "',name='" + namey + "',phonenumber='" + pfnnumber + "', year_sem ='" + wtype + "', purpose='" + rtmpy+ "',Amount='" + ysamr + "' WHERE finance_id= '" + id + "'";
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
     public Boolean deleteyearselect(String id) {
        String sql = "DELETE FROM Finance WHERE Finance_id ='" + id + "'";
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
        private void filteryearselect(String query) {
        DefaultTableModel model3 =  (DefaultTableModel) tblfinacialyrs.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model3);
        tblfinacialyrs.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }
        private void totalyearselect() {
        try {
            String sql = "SELECT SUM(Amount)amt FROM Finance ";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
             int totalsalary = Integer.parseInt(rs.getString("amt"));
           //  totalsalry.setText(String.valueOf(totalsalary));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        }
         
         
      private void retreaveexpinc() {
        try {
            String query = "SELECT * FROM incomeexpenditures";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            model = (DefaultTableModel) incomeexpensestbl.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String prov_id = rs.getString(1);
                String prov_typed = rs.getString(2);
                String srcont = rs.getString(3);
                String amtexpsw = rs.getString(4);
                String daterfs = rs.getString(5);
                model.addRow(new String[]{prov_id, prov_typed, srcont, amtexpsw, daterfs });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public Boolean deleteexpinc(String serv_id) {
        String sql = "DELETE FROM incomeexpenditures WHERE id='" + serv_id + "'";
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
    private void filterexpinc(String query) {
        DefaultTableModel model9 = (DefaultTableModel) incomeexpensestbl.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model9);
        incomeexpensestbl.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }

    public Boolean updateexpinc(String p_id, String cinname, String insrfd, String pserv, String dty) {
        String query = "UPDATE incomeexpenditures SET type='" + cinname + "',source='" + insrfd+ "', Amount='" + pserv + "', date='" + dty + "'WHERE Financial_id= '" + p_id + "'";
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
              private void retrievetreasurermembers() {
        try {
            String sql = "SELECT * FROM members";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            model = (DefaultTableModel) viewtresMembers.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String idmt = rs.getString(1);
                String regt = rs.getString(2);
                String namtts = rs.getString(3);
                String phonente = rs.getString(4);
                String crst = rs.getString(5);
                String spext = rs.getString(6);
                String yst = rs.getString(7);
                String datesst = rs.getString(8);
                model.addRow(new String[]{idmt, regt, namtts, phonente, crst, spext,yst, datesst});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
         }
               private void totalyearsmem() {
        try {
            String sql = "SELECT COUNT(members_id)totmem FROM members";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
             if (rs.next()){
              int total =Integer.parseInt(rs.getString("totmem")) ;
              totaltrem.setText(String.valueOf(total));
           }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        }

    private void filtertreasurermembers(String query) {
        DefaultTableModel model2 =  (DefaultTableModel) viewtresMembers.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model2);
        viewtresMembers.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    } 
    
         private void totalotherincome() {
        try {
            String sql = "SELECT SUM(Amount)amtinc FROM incomeexpenditures WHERE type = 'Incomes' ";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
             int totalicmonies = Integer.parseInt(rs.getString("amtinc"));
             baloinc.setText(String.valueOf(totalicmonies));
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
                baltotalexp.setText(String.valueOf(totalmonexp));
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
            balsr.setText(String.valueOf(totalicmer));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        } 
                
                 private void totalclinicmoney() {
        try {
            String sql = "SELECT * FROM clinicevents where clid = 'amount' ";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                int hwtrble = Integer.parseInt(rs.getString("hardware_troubleshooting"));
                int swInts = Integer.parseInt(rs.getString("windows_installation"));
                int dust = Integer.parseInt(rs.getString("dust_cleaning"));
                int winInst = Integer.parseInt(rs.getString("software_installation"));
                int antup = Integer.parseInt(rs.getString("antivirus_installation"));
                int winupd = Integer.parseInt(rs.getString("windows_updation"));
         int total = hwtrble + swInts + dust + winInst + antup + winupd;
         
           clinictots.setText(String.valueOf(total));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        }  
                
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        displaypanel = new javax.swing.JPanel();
        AddRegFee = new javax.swing.JPanel();
        addusrlabel = new javax.swing.JLabel();
        usernamelabel = new javax.swing.JLabel();
        tfreg = new javax.swing.JTextField();
        passwordlabel = new javax.swing.JLabel();
        btnAddregs = new javax.swing.JButton();
        rolelabel = new javax.swing.JLabel();
        confpasslabel = new javax.swing.JLabel();
        btnClearUser = new javax.swing.JButton();
        btnCloseNewUser = new javax.swing.JButton();
        phonelabel = new javax.swing.JLabel();
        tfphone = new javax.swing.JTextField();
        securitylabel = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        tfpurpose = new javax.swing.JTextField();
        tfyear = new javax.swing.JTextField();
        tfname = new javax.swing.JTextField();
        btnClearUser1 = new javax.swing.JButton();
        tfamt = new javax.swing.JFormattedTextField();
        GenManageFee = new javax.swing.JPanel();
        retrieve = new javax.swing.JButton();
        lblusername = new javax.swing.JLabel();
        txtregst = new javax.swing.JTextField();
        pass = new javax.swing.JLabel();
        txtpnts = new javax.swing.JTextField();
        btnUpdaterecin = new javax.swing.JButton();
        btndelrecs = new javax.swing.JButton();
        btnCloseMaintainrls = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablefinance = new javax.swing.JTable();
        num = new javax.swing.JLabel();
        txtnt = new javax.swing.JTextField();
        position = new javax.swing.JLabel();
        txtsmts = new javax.swing.JTextField();
        security = new javax.swing.JLabel();
        ans = new javax.swing.JLabel();
        txtprpt = new javax.swing.JTextField();
        txtamrs = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        rects = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        mebtr = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        AddSubscFee = new javax.swing.JPanel();
        proddetails = new javax.swing.JLabel();
        fname = new javax.swing.JLabel();
        subnm = new javax.swing.JTextField();
        lname = new javax.swing.JLabel();
        subpr = new javax.swing.JTextField();
        plabel = new javax.swing.JLabel();
        subys = new javax.swing.JTextField();
        occlabel = new javax.swing.JLabel();
        subps = new javax.swing.JTextField();
        btnClosesx = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        registersubbtn = new javax.swing.JButton();
        roomno = new javax.swing.JLabel();
        subr = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        roomno1 = new javax.swing.JLabel();
        subams = new javax.swing.JTextField();
        btnresub = new javax.swing.JButton();
        ManageOnType = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        selectedfinace = new javax.swing.JTable();
        btnGetfin = new javax.swing.JButton();
        lblprodname = new javax.swing.JLabel();
        manreg = new javax.swing.JTextField();
        lblavailablestock = new javax.swing.JLabel();
        mannams = new javax.swing.JTextField();
        lblbuyingprice = new javax.swing.JLabel();
        manphone = new javax.swing.JTextField();
        lblsellingprice = new javax.swing.JLabel();
        manysd = new javax.swing.JTextField();
        updatefv = new javax.swing.JButton();
        deletefg = new javax.swing.JButton();
        btnClosefgs = new javax.swing.JButton();
        lblsellingprice1 = new javax.swing.JLabel();
        manpr = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        totalsubrmem = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        tenantsearch = new javax.swing.JTextField();
        lblsellingprice2 = new javax.swing.JLabel();
        manamtrs = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        selecttype = new javax.swing.JComboBox<>();
        jLabel60 = new javax.swing.JLabel();
        totselcy = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        ManageOnYr = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblfinacialyrs = new javax.swing.JTable();
        lblEmpfname = new javax.swing.JLabel();
        lblEmplname = new javax.swing.JLabel();
        lblEmppno = new javax.swing.JLabel();
        lblEmpsal = new javax.swing.JLabel();
        yreg = new javax.swing.JTextField();
        ynms = new javax.swing.JTextField();
        yyst = new javax.swing.JTextField();
        btnReloadyers = new javax.swing.JButton();
        btnDeletefrs = new javax.swing.JButton();
        btnUpdateyts = new javax.swing.JButton();
        ypones = new javax.swing.JTextField();
        lblEusername = new javax.swing.JLabel();
        yamd = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        empsearch = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        totalamwers = new javax.swing.JTextField();
        btnclosefrd = new javax.swing.JButton();
        lblEusername1 = new javax.swing.JLabel();
        yprt = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        totalystd = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        selectyrs = new javax.swing.JComboBox<>();
        ManageIncomeExps = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        incomeexpensestbl = new javax.swing.JTable();
        btnCloseprov = new javax.swing.JButton();
        btnupdateprov = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        provname = new javax.swing.JTextField();
        provcont = new javax.swing.JTextField();
        provtype = new javax.swing.JTextField();
        btnretrieveprov = new javax.swing.JButton();
        btndeleteProv = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        otIncExpsearch = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        dtrinc = new javax.swing.JTextField();
        RecordIncomeExp = new javax.swing.JPanel();
        blrm = new javax.swing.JLabel();
        lbnum = new javax.swing.JLabel();
        lblstat = new javax.swing.JLabel();
        clsis = new javax.swing.JButton();
        clric = new javax.swing.JButton();
        btnaddic = new javax.swing.JButton();
        srexpc = new javax.swing.JTextField();
        lblFilldet = new javax.swing.JLabel();
        fictype = new javax.swing.JComboBox<>();
        txtamexpta = new javax.swing.JTextField();
        ViewBalance = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        balsr = new javax.swing.JTextField();
        baltotalincome = new javax.swing.JTextField();
        btnbalanceret = new javax.swing.JButton();
        btnclosebal = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        clinictots = new javax.swing.JTextField();
        indicators = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        baltotalexp = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        balsnce = new javax.swing.JTextField();
        btnbalreset1 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        baloinc = new javax.swing.JTextField();
        ViewMembers = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        viewtresMembers = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel38 = new javax.swing.JLabel();
        tresearchmem = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        totaltrem = new javax.swing.JTextField();
        IncomeAndExpenditureReports = new javax.swing.JPanel();
        lblread = new javax.swing.JLabel();
        genrent = new javax.swing.JButton();
        closerent = new javax.swing.JButton();
        lblrent = new javax.swing.JLabel();
        MembersContrReport = new javax.swing.JPanel();
        gentenant = new javax.swing.JButton();
        closetenant = new javax.swing.JButton();
        lbltenant = new javax.swing.JLabel();
        lblread2 = new javax.swing.JLabel();
        homepanel = new javax.swing.JPanel();
        softwarename = new javax.swing.JLabel();
        softwaredesc = new javax.swing.JLabel();
        softwarename1 = new javax.swing.JLabel();
        softwaredesc1 = new javax.swing.JLabel();
        ViewCalendarActivites = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        trecalevents = new javax.swing.JTextArea();
        jLabel45 = new javax.swing.JLabel();
        viewregulations = new javax.swing.JButton();
        printregulations = new javax.swing.JButton();
        closeregulations = new javax.swing.JButton();
        TreViewNotices = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        noticetres = new javax.swing.JTextArea();
        jLabel46 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        retrievedts = new javax.swing.JButton();
        TreUploadNotices = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        vwnoticetre = new javax.swing.JTextArea();
        jButton4 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jLabel47 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();
        Receipt = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        area = new javax.swing.JTextArea();
        jButton12 = new javax.swing.JButton();
        setPrintButtonActive = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        ViewIndvMembersContr = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        indvname = new javax.swing.JTextField();
        indvamoutr = new javax.swing.JTextField();
        indvphones = new javax.swing.JTextField();
        indvreg = new javax.swing.JTextField();
        typetext = new javax.swing.JComboBox<>();
        btnindccl = new javax.swing.JButton();
        btnupdates = new javax.swing.JButton();
        btnclsmem = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        indvyers = new javax.swing.JTextField();
        Receiptsubsc = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        areasubsz = new javax.swing.JTextArea();
        jButton15 = new javax.swing.JButton();
        setPrintButtonActive1 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        loginlabel = new javax.swing.JLabel();
        tresusername = new javax.swing.JLabel();
        btnLogOut = new javax.swing.JButton();
        softwareName = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        btnGroup = new javax.swing.JPanel();
        btnAddusers = new javax.swing.JButton();
        usrmgmt = new javax.swing.JLabel();
        stockmgmt = new javax.swing.JLabel();
        btnAddrooms = new javax.swing.JButton();
        lblwritting = new javax.swing.JLabel();
        btnmanageusers = new javax.swing.JButton();
        btnmanagetenants = new javax.swing.JButton();
        btnmanageemp = new javax.swing.JButton();
        reppayment = new javax.swing.JButton();
        lblReports1 = new javax.swing.JLabel();
        viewrentdates = new javax.swing.JButton();
        lblReports2 = new javax.swing.JLabel();
        viewrules = new javax.swing.JButton();
        reptenant = new javax.swing.JButton();
        updroomsd1 = new javax.swing.JButton();
        lblwritting1 = new javax.swing.JLabel();
        lblwritting2 = new javax.swing.JLabel();
        updroomsd2 = new javax.swing.JButton();
        updroomsd3 = new javax.swing.JButton();
        lblReports3 = new javax.swing.JLabel();
        reptenant1 = new javax.swing.JButton();
        updroomsd4 = new javax.swing.JButton();
        updroomsd5 = new javax.swing.JButton();

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
        addusrlabel.setText("TECHNOSCIENCE MEMBER REGISTRATION FEE");

        usernamelabel.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        usernamelabel.setText("reg no.");

        tfreg.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        tfreg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfregKeyPressed(evt);
            }
        });

        passwordlabel.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        passwordlabel.setText("Phonenumber:");

        btnAddregs.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btnAddregs.setForeground(new java.awt.Color(0, 51, 255));
        btnAddregs.setText("Add record");
        btnAddregs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddregsActionPerformed(evt);
            }
        });

        rolelabel.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        rolelabel.setText("Purpose:");

        confpasslabel.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        confpasslabel.setText("year $ semester:");

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

        phonelabel.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        phonelabel.setText("name:");

        tfphone.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        securitylabel.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        securitylabel.setText("Amount:");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 255));
        jLabel13.setText("Please fill in the fields below to record members registration fee");

        jLabel56.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(0, 0, 204));
        jLabel56.setText("Press Enter to Auto Retrieve the Members Details");

        tfpurpose.setBackground(new java.awt.Color(51, 51, 0));
        tfpurpose.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        tfpurpose.setForeground(new java.awt.Color(0, 153, 204));
        tfpurpose.setText("registration");
        tfpurpose.setEnabled(false);

        tfyear.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N

        tfname.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        btnClearUser1.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btnClearUser1.setText("print");
        btnClearUser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearUser1ActionPerformed(evt);
            }
        });

        tfamt.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));

        javax.swing.GroupLayout AddRegFeeLayout = new javax.swing.GroupLayout(AddRegFee);
        AddRegFee.setLayout(AddRegFeeLayout);
        AddRegFeeLayout.setHorizontalGroup(
            AddRegFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddRegFeeLayout.createSequentialGroup()
                .addGroup(AddRegFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddRegFeeLayout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(AddRegFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(confpasslabel)
                            .addComponent(securitylabel)
                            .addComponent(btnAddregs)
                            .addGroup(AddRegFeeLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(AddRegFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(phonelabel)
                                    .addComponent(usernamelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(passwordlabel)))
                            .addComponent(rolelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(66, 66, 66)
                        .addGroup(AddRegFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfname, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                            .addComponent(tfphone, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                            .addComponent(tfreg)
                            .addComponent(tfpurpose, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                            .addComponent(tfyear)
                            .addComponent(tfamt)))
                    .addGroup(AddRegFeeLayout.createSequentialGroup()
                        .addGap(151, 151, 151)
                        .addComponent(addusrlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AddRegFeeLayout.createSequentialGroup()
                        .addGap(172, 172, 172)
                        .addGroup(AddRegFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addGroup(AddRegFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(AddRegFeeLayout.createSequentialGroup()
                                    .addGap(104, 104, 104)
                                    .addComponent(btnClearUser)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnClearUser1)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnCloseNewUser))
                                .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(166, Short.MAX_VALUE))
        );
        AddRegFeeLayout.setVerticalGroup(
            AddRegFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddRegFeeLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(addusrlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(jLabel56)
                .addGap(26, 26, 26)
                .addGroup(AddRegFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernamelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfreg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddRegFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phonelabel)
                    .addComponent(tfname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddRegFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfphone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(AddRegFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confpasslabel)
                    .addComponent(tfyear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddRegFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rolelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfpurpose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(AddRegFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(securitylabel)
                    .addComponent(tfamt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(73, 73, 73)
                .addGroup(AddRegFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddregs)
                    .addComponent(btnClearUser)
                    .addComponent(btnCloseNewUser)
                    .addComponent(btnClearUser1))
                .addContainerGap(189, Short.MAX_VALUE))
        );

        displaypanel.add(AddRegFee, "card2");

        GenManageFee.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "MANAGE MEMBERS FINANCIAL RECORDS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Engravers MT", 1, 24), new java.awt.Color(0, 0, 255))); // NOI18N

        retrieve.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        retrieve.setForeground(new java.awt.Color(0, 0, 255));
        retrieve.setText("Retrieve");
        retrieve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retrieveActionPerformed(evt);
            }
        });

        lblusername.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblusername.setText("Reg Number;");

        txtregst.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        pass.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        pass.setText("Phonenumber:");

        btnUpdaterecin.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btnUpdaterecin.setForeground(new java.awt.Color(153, 0, 51));
        btnUpdaterecin.setText("Update Details");
        btnUpdaterecin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdaterecinActionPerformed(evt);
            }
        });

        btndelrecs.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btndelrecs.setForeground(new java.awt.Color(102, 51, 0));
        btndelrecs.setText("Delete");
        btndelrecs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndelrecsActionPerformed(evt);
            }
        });

        btnCloseMaintainrls.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btnCloseMaintainrls.setForeground(new java.awt.Color(0, 255, 51));
        btnCloseMaintainrls.setText("Close");
        btnCloseMaintainrls.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseMaintainrlsActionPerformed(evt);
            }
        });

        tablefinance.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Finance ID", "Reg No.", "Names", "Phonenumbers", "Year $ Sem", "Purpose", "Amount", "Reg Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablefinance.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablefinanceMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablefinance);

        num.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        num.setText("Name:");

        position.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        position.setText("Year $ Sem:");

        txtsmts.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        security.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        security.setText("Purpose:");

        ans.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        ans.setText("Amount:");

        txtprpt.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        txtamrs.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        jLabel39.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel39.setText("Search:");

        rects.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        rects.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                rectsKeyReleased(evt);
            }
        });

        jLabel57.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel57.setText("Total Amount:");

        mebtr.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jButton1.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        jButton1.setText("print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout GenManageFeeLayout = new javax.swing.GroupLayout(GenManageFee);
        GenManageFee.setLayout(GenManageFeeLayout);
        GenManageFeeLayout.setHorizontalGroup(
            GenManageFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GenManageFeeLayout.createSequentialGroup()
                .addComponent(jLabel39)
                .addGap(60, 60, 60)
                .addComponent(rects, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(GenManageFeeLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(GenManageFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, GenManageFeeLayout.createSequentialGroup()
                        .addGroup(GenManageFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, GenManageFeeLayout.createSequentialGroup()
                                .addComponent(position)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtsmts, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, GenManageFeeLayout.createSequentialGroup()
                                .addComponent(ans)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtamrs, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, GenManageFeeLayout.createSequentialGroup()
                                .addGroup(GenManageFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(GenManageFeeLayout.createSequentialGroup()
                                        .addComponent(num)
                                        .addGap(72, 72, 72)
                                        .addComponent(txtnt, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(security)
                                    .addGroup(GenManageFeeLayout.createSequentialGroup()
                                        .addComponent(lblusername, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtregst, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(GenManageFeeLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(GenManageFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnCloseMaintainrls)
                                    .addComponent(txtprpt, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(87, 87, 87))
                    .addGroup(GenManageFeeLayout.createSequentialGroup()
                        .addGroup(GenManageFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1)
                            .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtpnts, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(GenManageFeeLayout.createSequentialGroup()
                .addGroup(GenManageFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GenManageFeeLayout.createSequentialGroup()
                        .addComponent(retrieve)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdaterecin)
                        .addGap(27, 27, 27)
                        .addComponent(btndelrecs, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(GenManageFeeLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel57)
                        .addGap(165, 165, 165)
                        .addComponent(mebtr, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        GenManageFeeLayout.setVerticalGroup(
            GenManageFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GenManageFeeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(GenManageFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(rects, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(GenManageFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GenManageFeeLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addGroup(GenManageFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel57)
                            .addComponent(mebtr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(GenManageFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(retrieve)
                            .addComponent(btnUpdaterecin)
                            .addComponent(btndelrecs)
                            .addComponent(btnCloseMaintainrls)
                            .addComponent(jButton1)))
                    .addGroup(GenManageFeeLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(GenManageFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblusername)
                            .addComponent(txtregst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(GenManageFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(num)
                            .addComponent(txtnt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(GenManageFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pass)
                            .addComponent(txtpnts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(GenManageFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(position)
                            .addComponent(txtsmts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(GenManageFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(security)
                            .addComponent(txtprpt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(GenManageFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ans)
                            .addComponent(txtamrs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(191, Short.MAX_VALUE))
        );

        displaypanel.add(GenManageFee, "card3");

        AddSubscFee.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "RECORD SUBSCRIPTION FEE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18), new java.awt.Color(0, 102, 102))); // NOI18N

        proddetails.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        proddetails.setForeground(new java.awt.Color(102, 51, 255));
        proddetails.setText("Enter MEMBERS Details Below:");

        fname.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        fname.setText("REG NO.:");

        subnm.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        lname.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lname.setText("Names:");

        subpr.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        subpr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subprActionPerformed(evt);
            }
        });

        plabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        plabel.setText("PhoneNumber:");

        subys.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        occlabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        occlabel.setText("Year $ Semster:");

        subps.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        subps.setText("subscription");
        subps.setEnabled(false);

        btnClosesx.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btnClosesx.setForeground(new java.awt.Color(0, 51, 0));
        btnClosesx.setText("Close");
        btnClosesx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClosesxActionPerformed(evt);
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

        registersubbtn.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        registersubbtn.setForeground(new java.awt.Color(102, 153, 0));
        registersubbtn.setText("Register");
        registersubbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registersubbtnActionPerformed(evt);
            }
        });

        roomno.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        roomno.setText("Purpose:");

        subr.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        subr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subrActionPerformed(evt);
            }
        });
        subr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                subrKeyPressed(evt);
            }
        });

        jLabel58.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(0, 204, 204));
        jLabel58.setText("Enter Registration Number and Press Enter To Check Whether the Member Has Regstered");

        roomno1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        roomno1.setText("Amount:");

        subams.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        btnresub.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        btnresub.setForeground(new java.awt.Color(153, 51, 255));
        btnresub.setText("Print");
        btnresub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnresubActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddSubscFeeLayout = new javax.swing.GroupLayout(AddSubscFee);
        AddSubscFee.setLayout(AddSubscFeeLayout);
        AddSubscFeeLayout.setHorizontalGroup(
            AddSubscFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddSubscFeeLayout.createSequentialGroup()
                .addGroup(AddSubscFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddSubscFeeLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(AddSubscFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(roomno, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(occlabel)
                            .addComponent(plabel, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lname, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fname, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(roomno1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(AddSubscFeeLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(registersubbtn)))
                .addGroup(AddSubscFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddSubscFeeLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(AddSubscFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(subams, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(subr, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(subnm, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(subpr, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(subys, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(subps, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(AddSubscFeeLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(btnClear)
                        .addGap(28, 28, 28)
                        .addComponent(btnresub)
                        .addGap(18, 18, 18)
                        .addComponent(btnClosesx)))
                .addGap(0, 331, Short.MAX_VALUE))
            .addGroup(AddSubscFeeLayout.createSequentialGroup()
                .addGroup(AddSubscFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddSubscFeeLayout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(proddetails, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AddSubscFeeLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel58)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        AddSubscFeeLayout.setVerticalGroup(
            AddSubscFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddSubscFeeLayout.createSequentialGroup()
                .addComponent(proddetails, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jLabel58)
                .addGap(18, 18, 18)
                .addGroup(AddSubscFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fname)
                    .addComponent(subr, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(AddSubscFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lname, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(subnm, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(AddSubscFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(plabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(subpr, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddSubscFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(occlabel)
                    .addComponent(subys, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddSubscFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roomno)
                    .addComponent(subps, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddSubscFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roomno1)
                    .addComponent(subams, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddSubscFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(registersubbtn)
                    .addComponent(btnClosesx)
                    .addComponent(btnresub)
                    .addComponent(btnClear))
                .addGap(0, 304, Short.MAX_VALUE))
        );

        displaypanel.add(AddSubscFee, "card5");

        ManageOnType.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "SELECTED FINANCIAL MANAGEMENT", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N

        selectedfinace.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Finacial  ID", "REG No.", "Names", "Phonenumber", "Year_Sem", "Purpose", "AMount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        selectedfinace.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectedfinaceMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(selectedfinace);

        btnGetfin.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnGetfin.setForeground(new java.awt.Color(102, 0, 255));
        btnGetfin.setText("Retrieve Data");
        btnGetfin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetfinActionPerformed(evt);
            }
        });

        lblprodname.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblprodname.setText("REG NO.:");

        manreg.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        lblavailablestock.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblavailablestock.setText("NAMES:");

        mannams.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        lblbuyingprice.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblbuyingprice.setText("PhoneNumber:");

        manphone.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        lblsellingprice.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblsellingprice.setText("Year $ Sem:");

        manysd.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        updatefv.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        updatefv.setForeground(new java.awt.Color(51, 51, 0));
        updatefv.setText("Update Details");
        updatefv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatefvActionPerformed(evt);
            }
        });

        deletefg.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        deletefg.setText("Delete");
        deletefg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletefgActionPerformed(evt);
            }
        });

        btnClosefgs.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnClosefgs.setForeground(new java.awt.Color(0, 204, 51));
        btnClosefgs.setText("Close");
        btnClosefgs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClosefgsActionPerformed(evt);
            }
        });

        lblsellingprice1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblsellingprice1.setText("Purpose:");

        manpr.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jLabel42.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(0, 102, 102));
        jLabel42.setText("Total Students:");

        totalsubrmem.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        jLabel43.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(102, 51, 255));
        jLabel43.setText("Search:");

        tenantsearch.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        tenantsearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tenantsearchKeyReleased(evt);
            }
        });

        lblsellingprice2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblsellingprice2.setText("Amount:");

        manamtrs.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jLabel59.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel59.setText("Select type:");

        selecttype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose Selected Type", "registration", "subscription", " " }));
        selecttype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selecttypeActionPerformed(evt);
            }
        });

        jLabel60.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(0, 102, 102));
        jLabel60.setText("Total AMOUNT:");

        totselcy.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jButton2.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        jButton2.setText("PRINT");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ManageOnTypeLayout = new javax.swing.GroupLayout(ManageOnType);
        ManageOnType.setLayout(ManageOnTypeLayout);
        ManageOnTypeLayout.setHorizontalGroup(
            ManageOnTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ManageOnTypeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel43)
                .addGap(102, 102, 102)
                .addComponent(tenantsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(jLabel59)
                .addGap(83, 83, 83)
                .addComponent(selecttype, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(ManageOnTypeLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(ManageOnTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ManageOnTypeLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(ManageOnTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblavailablestock)
                            .addComponent(lblprodname, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblbuyingprice)
                            .addComponent(lblsellingprice)
                            .addComponent(lblsellingprice1)
                            .addComponent(lblsellingprice2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(ManageOnTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(manpr, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                            .addComponent(manysd, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(manphone, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(mannams, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(manreg, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(manamtrs))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(ManageOnTypeLayout.createSequentialGroup()
                        .addGroup(ManageOnTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(ManageOnTypeLayout.createSequentialGroup()
                                .addComponent(btnGetfin)
                                .addGap(27, 27, 27)
                                .addComponent(updatefv)
                                .addGap(36, 36, 36)
                                .addComponent(deletefg)
                                .addGap(32, 32, 32)
                                .addComponent(jButton2))
                            .addGroup(ManageOnTypeLayout.createSequentialGroup()
                                .addComponent(jLabel42)
                                .addGap(58, 58, 58)
                                .addComponent(totselcy, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(64, 64, 64)
                                .addComponent(jLabel60)
                                .addGap(34, 34, 34)
                                .addComponent(totalsubrmem, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnClosefgs)
                        .addGap(70, 70, 70))))
        );
        ManageOnTypeLayout.setVerticalGroup(
            ManageOnTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ManageOnTypeLayout.createSequentialGroup()
                .addGroup(ManageOnTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ManageOnTypeLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(ManageOnTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel43)
                            .addComponent(tenantsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(ManageOnTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(selecttype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel59)))
                .addGap(18, 18, 18)
                .addGroup(ManageOnTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ManageOnTypeLayout.createSequentialGroup()
                        .addGroup(ManageOnTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblprodname)
                            .addComponent(manreg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(ManageOnTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblavailablestock)
                            .addComponent(mannams, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(ManageOnTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblbuyingprice)
                            .addComponent(manphone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(ManageOnTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblsellingprice)
                            .addComponent(manysd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(ManageOnTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(manpr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblsellingprice1))
                        .addGap(18, 18, 18)
                        .addGroup(ManageOnTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblsellingprice2)
                            .addComponent(manamtrs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ManageOnTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel42)
                    .addGroup(ManageOnTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(totselcy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel60)
                        .addComponent(totalsubrmem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(ManageOnTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ManageOnTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(deletefg)
                        .addComponent(jButton2))
                    .addGroup(ManageOnTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnGetfin)
                        .addComponent(updatefv))
                    .addComponent(btnClosefgs))
                .addContainerGap(229, Short.MAX_VALUE))
        );

        displaypanel.add(ManageOnType, "card6");

        ManageOnYr.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.MatteBorder(null), "MANAGE FINACIAL RECORDS BASED ON YEAR AND SEMESTER", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18), new java.awt.Color(102, 0, 204))); // NOI18N
        ManageOnYr.setPreferredSize(new java.awt.Dimension(520, 0));

        tblfinacialyrs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Finance ID", "REG NO.", "Names", "Phonenumber", "Year $ Sem", "Purpose", "Amount", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblfinacialyrs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblfinacialyrsMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblfinacialyrs);

        lblEmpfname.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblEmpfname.setText("REG NO.:");

        lblEmplname.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblEmplname.setText("Names:");

        lblEmppno.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblEmppno.setText("Phonenumber:");

        lblEmpsal.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblEmpsal.setText("Year $ Sem");

        btnReloadyers.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnReloadyers.setForeground(new java.awt.Color(255, 102, 204));
        btnReloadyers.setText("Retrieve Data");
        btnReloadyers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadyersActionPerformed(evt);
            }
        });

        btnDeletefrs.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnDeletefrs.setForeground(new java.awt.Color(102, 102, 255));
        btnDeletefrs.setText("Delete Employee");
        btnDeletefrs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletefrsActionPerformed(evt);
            }
        });

        btnUpdateyts.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnUpdateyts.setText("Update Details");
        btnUpdateyts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateytsActionPerformed(evt);
            }
        });

        lblEusername.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblEusername.setText("Amount:");

        jLabel22.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        jLabel22.setText("search");

        empsearch.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        empsearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                empsearchKeyReleased(evt);
            }
        });

        jLabel40.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(0, 153, 255));
        jLabel40.setText("Total Amount:");

        totalamwers.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        btnclosefrd.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnclosefrd.setForeground(new java.awt.Color(0, 0, 102));
        btnclosefrd.setText("close");
        btnclosefrd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclosefrdActionPerformed(evt);
            }
        });

        lblEusername1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblEusername1.setText("Purpose:");

        jLabel41.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 0, 255));
        jLabel41.setText("Total Students:");

        totalystd.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel9.setText("Select Year:");

        selectyrs.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        selectyrs.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Year", "Y1S1", "Y1S2", "Y2S1", "Y2S2", "Y3S1", "Y3S2", "Y4S1", "Y4S2" }));
        selectyrs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectyrsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ManageOnYrLayout = new javax.swing.GroupLayout(ManageOnYr);
        ManageOnYr.setLayout(ManageOnYrLayout);
        ManageOnYrLayout.setHorizontalGroup(
            ManageOnYrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ManageOnYrLayout.createSequentialGroup()
                .addGroup(ManageOnYrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(ManageOnYrLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel22)
                        .addGap(39, 39, 39)
                        .addComponent(empsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jLabel9)
                        .addGap(31, 31, 31)
                        .addComponent(selectyrs, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ManageOnYrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEusername1)
                    .addComponent(lblEmpsal)
                    .addComponent(lblEusername)
                    .addComponent(lblEmplname)
                    .addComponent(lblEmppno)
                    .addComponent(lblEmpfname))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ManageOnYrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(yreg, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ynms, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ypones, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yyst, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yprt, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yamd, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(ManageOnYrLayout.createSequentialGroup()
                .addGroup(ManageOnYrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ManageOnYrLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnReloadyers)
                        .addGap(26, 26, 26)
                        .addComponent(btnUpdateyts)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDeletefrs)
                        .addGap(18, 18, 18)
                        .addComponent(btnclosefrd))
                    .addGroup(ManageOnYrLayout.createSequentialGroup()
                        .addGroup(ManageOnYrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel41)
                            .addComponent(jLabel40))
                        .addGap(40, 40, 40)
                        .addGroup(ManageOnYrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(totalystd, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(totalamwers, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ManageOnYrLayout.setVerticalGroup(
            ManageOnYrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ManageOnYrLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ManageOnYrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(empsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(selectyrs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ManageOnYrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ManageOnYrLayout.createSequentialGroup()
                        .addGroup(ManageOnYrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEmpfname)
                            .addComponent(yreg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(ManageOnYrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEmplname)
                            .addComponent(ynms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(ManageOnYrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEmppno)
                            .addComponent(ypones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(ManageOnYrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEmpsal)
                            .addComponent(yyst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(ManageOnYrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEusername1)
                            .addComponent(yprt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(ManageOnYrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEusername)
                            .addComponent(yamd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ManageOnYrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(totalamwers, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ManageOnYrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(totalystd, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(ManageOnYrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReloadyers)
                    .addComponent(btnUpdateyts)
                    .addComponent(btnDeletefrs)
                    .addComponent(btnclosefrd))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        displaypanel.add(ManageOnYr, "card8");

        ManageIncomeExps.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "MANAGE OTHER INCOME AND EXPENDITURES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N
        ManageIncomeExps.setPreferredSize(new java.awt.Dimension(525, 0));

        incomeexpensestbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Financial  ID", "Type", "Source", "Amount", "DateRecorded"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        incomeexpensestbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                incomeexpensestblMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(incomeexpensestbl);

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
        jLabel28.setText("Type:");

        jLabel29.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel29.setText("Date:");

        jLabel30.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel30.setText("Source:");

        provname.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        provcont.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        provtype.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

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

        otIncExpsearch.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        otIncExpsearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                otIncExpsearchKeyReleased(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel11.setText("Select Type:");

        jComboBox3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose Type", "Income", "Expenditure" }));

        jLabel31.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel31.setText("Amount:");

        dtrinc.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        javax.swing.GroupLayout ManageIncomeExpsLayout = new javax.swing.GroupLayout(ManageIncomeExps);
        ManageIncomeExps.setLayout(ManageIncomeExpsLayout);
        ManageIncomeExpsLayout.setHorizontalGroup(
            ManageIncomeExpsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ManageIncomeExpsLayout.createSequentialGroup()
                .addGroup(ManageIncomeExpsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ManageIncomeExpsLayout.createSequentialGroup()
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ManageIncomeExpsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(ManageIncomeExpsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnretrieveprov)
                        .addGap(53, 53, 53)
                        .addComponent(btnupdateprov)
                        .addGap(31, 31, 31)
                        .addComponent(btndeleteProv)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ManageIncomeExpsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ManageIncomeExpsLayout.createSequentialGroup()
                        .addComponent(provname, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ManageIncomeExpsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(ManageIncomeExpsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCloseprov)
                            .addGroup(ManageIncomeExpsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(provtype, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                                .addComponent(provcont, javax.swing.GroupLayout.Alignment.LEADING))))
                    .addComponent(dtrinc, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(ManageIncomeExpsLayout.createSequentialGroup()
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(otIncExpsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jLabel11)
                .addGap(102, 102, 102)
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        ManageIncomeExpsLayout.setVerticalGroup(
            ManageIncomeExpsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ManageIncomeExpsLayout.createSequentialGroup()
                .addGroup(ManageIncomeExpsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(otIncExpsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(ManageIncomeExpsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ManageIncomeExpsLayout.createSequentialGroup()
                        .addGroup(ManageIncomeExpsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(provname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(ManageIncomeExpsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(provcont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(ManageIncomeExpsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(provtype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(ManageIncomeExpsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dtrinc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(56, 56, 56)
                .addGroup(ManageIncomeExpsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnretrieveprov)
                    .addComponent(btnupdateprov)
                    .addComponent(btndeleteProv)
                    .addComponent(btnCloseprov))
                .addContainerGap(281, Short.MAX_VALUE))
        );

        displaypanel.add(ManageIncomeExps, "card12");

        RecordIncomeExp.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "RECORD OTHER INCOME AND EXPENDITURES MODULE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N
        RecordIncomeExp.setPreferredSize(new java.awt.Dimension(667, 400));

        blrm.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        blrm.setText("Finance Type:");

        lbnum.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbnum.setText("Source:");

        lblstat.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblstat.setText("Amount:");

        clsis.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        clsis.setText("Close");
        clsis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clsisActionPerformed(evt);
            }
        });

        clric.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        clric.setForeground(new java.awt.Color(0, 51, 51));
        clric.setText("Clear");
        clric.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clricActionPerformed(evt);
            }
        });

        btnaddic.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnaddic.setForeground(new java.awt.Color(0, 102, 204));
        btnaddic.setText("add record");
        btnaddic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddicActionPerformed(evt);
            }
        });

        srexpc.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        lblFilldet.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        lblFilldet.setForeground(new java.awt.Color(0, 102, 102));
        lblFilldet.setText("Please Fill in Finance details Below:");

        fictype.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        fictype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose type", "Expenses", "Incomes" }));

        txtamexpta.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        javax.swing.GroupLayout RecordIncomeExpLayout = new javax.swing.GroupLayout(RecordIncomeExp);
        RecordIncomeExp.setLayout(RecordIncomeExpLayout);
        RecordIncomeExpLayout.setHorizontalGroup(
            RecordIncomeExpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RecordIncomeExpLayout.createSequentialGroup()
                .addGroup(RecordIncomeExpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RecordIncomeExpLayout.createSequentialGroup()
                        .addGroup(RecordIncomeExpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(blrm)
                            .addComponent(lblstat))
                        .addGap(197, 197, 197)
                        .addGroup(RecordIncomeExpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(fictype, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(srexpc, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                            .addComponent(txtamexpta)))
                    .addComponent(lbnum)
                    .addGroup(RecordIncomeExpLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(lblFilldet, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(RecordIncomeExpLayout.createSequentialGroup()
                        .addComponent(btnaddic)
                        .addGap(72, 72, 72)
                        .addComponent(clric)
                        .addGap(81, 81, 81)
                        .addComponent(clsis)))
                .addContainerGap(326, Short.MAX_VALUE))
        );
        RecordIncomeExpLayout.setVerticalGroup(
            RecordIncomeExpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RecordIncomeExpLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(lblFilldet)
                .addGap(18, 18, 18)
                .addGroup(RecordIncomeExpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(blrm)
                    .addComponent(fictype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(RecordIncomeExpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbnum)
                    .addComponent(srexpc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(RecordIncomeExpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblstat)
                    .addComponent(txtamexpta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addGroup(RecordIncomeExpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnaddic)
                    .addComponent(clric)
                    .addComponent(clsis))
                .addContainerGap(376, Short.MAX_VALUE))
        );

        displaypanel.add(RecordIncomeExp, "card13");

        ViewBalance.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.MatteBorder(null), "VIEW BALANCE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N
        ViewBalance.setPreferredSize(new java.awt.Dimension(669, 350));

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel17.setText("Income From Subscription and Registration:");

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel18.setText("Other Incomes:");

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel19.setText("Total Incomes:");

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel20.setText("Total Expenses");

        balsr.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        baltotalincome.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        baltotalincome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                baltotalincomeActionPerformed(evt);
            }
        });

        btnbalanceret.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnbalanceret.setText("Show");
        btnbalanceret.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbalanceretActionPerformed(evt);
            }
        });

        btnclosebal.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnclosebal.setText("Close");
        btnclosebal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclosebalActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(102, 153, 0));
        jLabel21.setText("The Following Module shows the Balance From Income And Expenditure:");

        clinictots.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        indicators.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        indicators.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                indicatorsActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel23.setText("Indication:");

        baltotalexp.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        baltotalexp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                baltotalexpActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel24.setText("Balance:");

        balsnce.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        balsnce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                balsnceActionPerformed(evt);
            }
        });

        btnbalreset1.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnbalreset1.setText("Print");
        btnbalreset1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbalreset1ActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel26.setText("Income From Clinics:");

        baloinc.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        javax.swing.GroupLayout ViewBalanceLayout = new javax.swing.GroupLayout(ViewBalance);
        ViewBalance.setLayout(ViewBalanceLayout);
        ViewBalanceLayout.setHorizontalGroup(
            ViewBalanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewBalanceLayout.createSequentialGroup()
                .addGroup(ViewBalanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ViewBalanceLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel21))
                    .addGroup(ViewBalanceLayout.createSequentialGroup()
                        .addComponent(btnbalanceret)
                        .addGap(154, 154, 154)
                        .addComponent(btnbalreset1)
                        .addGap(147, 147, 147)
                        .addComponent(btnclosebal)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(ViewBalanceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ViewBalanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel24)
                    .addComponent(jLabel23)
                    .addComponent(jLabel20)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ViewBalanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ViewBalanceLayout.createSequentialGroup()
                        .addGroup(ViewBalanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(balsnce, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(baltotalincome, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(indicators, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(baltotalexp, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(ViewBalanceLayout.createSequentialGroup()
                        .addGroup(ViewBalanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(clinictots, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(balsr, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(baloinc, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        ViewBalanceLayout.setVerticalGroup(
            ViewBalanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewBalanceLayout.createSequentialGroup()
                .addComponent(jLabel21)
                .addGap(18, 18, 18)
                .addGroup(ViewBalanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(balsr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(ViewBalanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(baloinc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(ViewBalanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(clinictots, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(ViewBalanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(baltotalincome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(ViewBalanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel23)
                    .addGroup(ViewBalanceLayout.createSequentialGroup()
                        .addGroup(ViewBalanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(baltotalexp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))
                        .addGap(22, 22, 22)
                        .addGroup(ViewBalanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(balsnce, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))
                        .addGap(18, 18, 18)
                        .addComponent(indicators, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addGroup(ViewBalanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnbalanceret)
                    .addComponent(btnbalreset1)
                    .addComponent(btnclosebal))
                .addContainerGap(253, Short.MAX_VALUE))
        );

        displaypanel.add(ViewBalance, "card15");

        ViewMembers.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.MatteBorder(null), "VIEW TECHNOSCIENCE MEMBERS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N

        viewtresMembers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Members  Id", "REG NO.", "Names", "Phonenumber", "Course", "Area of Spez.", "Year $ Sem.", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        viewtresMembers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewtresMembersMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(viewtresMembers);

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

        tresearchmem.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jLabel52.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel52.setText("Total Members:");

        totaltrem.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        javax.swing.GroupLayout ViewMembersLayout = new javax.swing.GroupLayout(ViewMembers);
        ViewMembers.setLayout(ViewMembersLayout);
        ViewMembersLayout.setHorizontalGroup(
            ViewMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewMembersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton5)
                .addGap(292, 292, 292)
                .addComponent(jButton8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(ViewMembersLayout.createSequentialGroup()
                .addGroup(ViewMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ViewMembersLayout.createSequentialGroup()
                        .addGroup(ViewMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ViewMembersLayout.createSequentialGroup()
                                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(64, 64, 64)
                                .addComponent(tresearchmem, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ViewMembersLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jLabel52)
                                .addGap(130, 130, 130)
                                .addComponent(totaltrem, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ViewMembersLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 859, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        ViewMembersLayout.setVerticalGroup(
            ViewMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ViewMembersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ViewMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tresearchmem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ViewMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ViewMembersLayout.createSequentialGroup()
                        .addComponent(jLabel52)
                        .addGap(30, 30, 30)
                        .addGroup(ViewMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton5)
                            .addComponent(jButton8)))
                    .addComponent(totaltrem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(261, Short.MAX_VALUE))
        );

        displaypanel.add(ViewMembers, "card16");

        IncomeAndExpenditureReports.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "INCOME AND EXPENDITURE REPORT GENERATION", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N
        IncomeAndExpenditureReports.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblread.setBackground(new java.awt.Color(255, 255, 204));
        lblread.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblread.setForeground(new java.awt.Color(0, 0, 255));
        lblread.setText("Report is used to quickly analyse data and can be generated periodicaly");
        IncomeAndExpenditureReports.add(lblread, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 88, -1, 34));

        genrent.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        genrent.setForeground(new java.awt.Color(153, 0, 102));
        genrent.setText("report");
        genrent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genrentActionPerformed(evt);
            }
        });
        IncomeAndExpenditureReports.add(genrent, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, -1));

        closerent.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        closerent.setForeground(new java.awt.Color(0, 51, 153));
        closerent.setText("close");
        closerent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closerentActionPerformed(evt);
            }
        });
        IncomeAndExpenditureReports.add(closerent, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 200, -1, -1));

        lblrent.setBackground(new java.awt.Color(255, 255, 204));
        lblrent.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblrent.setForeground(new java.awt.Color(0, 0, 255));
        lblrent.setText("Click once the button Report to generate Income And Expenditure Report");
        IncomeAndExpenditureReports.add(lblrent, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 650, 34));

        displaypanel.add(IncomeAndExpenditureReports, "card17");

        MembersContrReport.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "MEMBERS CONTRIBUTION  REPORT GENERATION", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N
        MembersContrReport.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        gentenant.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        gentenant.setForeground(new java.awt.Color(102, 102, 255));
        gentenant.setText("member Report");
        gentenant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gentenantActionPerformed(evt);
            }
        });
        MembersContrReport.add(gentenant, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        closetenant.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        closetenant.setForeground(new java.awt.Color(51, 51, 0));
        closetenant.setText("CLOSE");
        closetenant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closetenantActionPerformed(evt);
            }
        });
        MembersContrReport.add(closetenant, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 200, -1, -1));

        lbltenant.setBackground(new java.awt.Color(255, 255, 204));
        lbltenant.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbltenant.setForeground(new java.awt.Color(0, 0, 255));
        lbltenant.setText("Click once Members Report button Generate to generate Contribution report");
        MembersContrReport.add(lbltenant, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 630, 40));

        lblread2.setBackground(new java.awt.Color(255, 255, 204));
        lblread2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblread2.setForeground(new java.awt.Color(0, 0, 255));
        lblread2.setText("Report is used to quickly analyse data and can be generated periodicaly");
        MembersContrReport.add(lblread2, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 88, -1, 34));

        displaypanel.add(MembersContrReport, "card19");

        homepanel.setBackground(new java.awt.Color(255, 255, 255));
        homepanel.setForeground(new java.awt.Color(255, 255, 255));

        softwarename.setFont(new java.awt.Font("Perpetua Titling MT", 1, 24)); // NOI18N
        softwarename.setText("WELCOME TO TECHNO SCIENCE MANAGEMENT SYSTEM");

        softwaredesc.setFont(new java.awt.Font("Tempus Sans ITC", 3, 24)); // NOI18N
        softwaredesc.setForeground(new java.awt.Color(255, 51, 51));
        softwaredesc.setText("Any Sufficiently advanced technology is indistiguishable from magic");

        softwarename1.setFont(new java.awt.Font("Perpetua Titling MT", 1, 24)); // NOI18N
        softwarename1.setText("TREASURER DASHBOARD");

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
                        .addGap(35, 35, 35)
                        .addComponent(softwaredesc, javax.swing.GroupLayout.PREFERRED_SIZE, 751, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(102, Short.MAX_VALUE))
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
                .addGap(51, 51, 51)
                .addComponent(softwaredesc, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(398, Short.MAX_VALUE))
        );

        displaypanel.add(homepanel, "card4");

        ViewCalendarActivites.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "VIEW CALENDAR OF ACTIVITIES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Engravers MT", 1, 18))); // NOI18N

        trecalevents.setEditable(false);
        trecalevents.setColumns(20);
        trecalevents.setLineWrap(true);
        trecalevents.setRows(5);
        jScrollPane12.setViewportView(trecalevents);

        jLabel45.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(0, 51, 51));
        jLabel45.setText("Click button view Planned TechnoScience Calendar of Activities");

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

        javax.swing.GroupLayout ViewCalendarActivitesLayout = new javax.swing.GroupLayout(ViewCalendarActivites);
        ViewCalendarActivites.setLayout(ViewCalendarActivitesLayout);
        ViewCalendarActivitesLayout.setHorizontalGroup(
            ViewCalendarActivitesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewCalendarActivitesLayout.createSequentialGroup()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(ViewCalendarActivitesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ViewCalendarActivitesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel45)
                    .addGroup(ViewCalendarActivitesLayout.createSequentialGroup()
                        .addComponent(viewregulations)
                        .addGap(94, 94, 94)
                        .addComponent(printregulations)
                        .addGap(136, 136, 136)
                        .addComponent(closeregulations)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ViewCalendarActivitesLayout.setVerticalGroup(
            ViewCalendarActivitesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewCalendarActivitesLayout.createSequentialGroup()
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(ViewCalendarActivitesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(viewregulations)
                    .addComponent(printregulations)
                    .addComponent(closeregulations))
                .addContainerGap(287, Short.MAX_VALUE))
        );

        displaypanel.add(ViewCalendarActivites, "card21");

        TreViewNotices.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "VIEW  NOTICES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Engravers MT", 1, 18))); // NOI18N
        TreViewNotices.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        noticetres.setColumns(20);
        noticetres.setRows(5);
        jScrollPane13.setViewportView(noticetres);

        TreViewNotices.add(jScrollPane13, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 52, 630, 301));

        jLabel46.setFont(new java.awt.Font("Eras Demi ITC", 1, 18)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(0, 51, 0));
        jLabel46.setText("Click View to Retrieve Notices");
        TreViewNotices.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 24, -1, -1));

        jButton11.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jButton11.setText("close");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        TreViewNotices.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 360, -1, -1));

        retrievedts.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        retrievedts.setText("view notice");
        retrievedts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retrievedtsActionPerformed(evt);
            }
        });
        TreViewNotices.add(retrievedts, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, -1, -1));

        displaypanel.add(TreViewNotices, "card22");

        TreUploadNotices.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "UPLOAD  NOTICES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Engravers MT", 1, 18), new java.awt.Color(102, 153, 0))); // NOI18N

        vwnoticetre.setColumns(20);
        vwnoticetre.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        vwnoticetre.setLineWrap(true);
        vwnoticetre.setRows(5);
        jScrollPane14.setViewportView(vwnoticetre);

        jButton4.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jButton4.setText("UPLOAD NOTICE");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
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
        jLabel47.setText("Click Button Upload to Save your Notice");

        jButton13.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jButton13.setText("clear");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout TreUploadNoticesLayout = new javax.swing.GroupLayout(TreUploadNotices);
        TreUploadNotices.setLayout(TreUploadNoticesLayout);
        TreUploadNoticesLayout.setHorizontalGroup(
            TreUploadNoticesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TreUploadNoticesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TreUploadNoticesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel47)
                    .addGroup(TreUploadNoticesLayout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addGap(18, 18, 18)
                        .addComponent(jButton13)
                        .addGap(59, 59, 59)
                        .addComponent(jButton10))))
            .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        TreUploadNoticesLayout.setVerticalGroup(
            TreUploadNoticesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TreUploadNoticesLayout.createSequentialGroup()
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(TreUploadNoticesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton10)
                    .addComponent(jButton13))
                .addContainerGap(261, Short.MAX_VALUE))
        );

        displaypanel.add(TreUploadNotices, "card23");

        Receipt.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "TECHNOSCIENCE RECEIPT", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Perpetua Titling MT", 1, 18), new java.awt.Color(0, 51, 51))); // NOI18N

        area.setColumns(20);
        area.setRows(5);
        jScrollPane15.setViewportView(area);

        jButton12.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jButton12.setText("view");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        setPrintButtonActive.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        setPrintButtonActive.setText("print");
        setPrintButtonActive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setPrintButtonActiveActionPerformed(evt);
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
                .addComponent(setPrintButtonActive)
                .addGap(125, 125, 125)
                .addComponent(jButton14)
                .addContainerGap(330, Short.MAX_VALUE))
        );
        ReceiptLayout.setVerticalGroup(
            ReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReceiptLayout.createSequentialGroup()
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(setPrintButtonActive)
                    .addComponent(jButton12)
                    .addComponent(jButton14))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        displaypanel.add(Receipt, "card24");

        ViewIndvMembersContr.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "VIEW MEMBERS CONTRIBUTION", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Perpetua Titling MT", 1, 18))); // NOI18N
        ViewIndvMembersContr.setPreferredSize(new java.awt.Dimension(609, 390));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel7.setText("REG NO.:");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel12.setText("Name:");

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel14.setText("Year $ Sem");

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel15.setText("PhoneNumber:");

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel16.setText("Amount:");

        indvname.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        indvamoutr.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        indvphones.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        indvreg.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        indvreg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                indvregKeyPressed(evt);
            }
        });

        typetext.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        typetext.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose Purpose", "Registration", "Subscription" }));
        typetext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typetextActionPerformed(evt);
            }
        });

        btnindccl.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnindccl.setText("clear");
        btnindccl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnindcclActionPerformed(evt);
            }
        });

        btnupdates.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnupdates.setText("Update");
        btnupdates.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdatesActionPerformed(evt);
            }
        });

        btnclsmem.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnclsmem.setText("close");
        btnclsmem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclsmemActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(51, 0, 255));
        jLabel25.setText("Enter Registration Number and Finance Type to Auto retrieve the MembersContribution ");

        indvyers.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        javax.swing.GroupLayout ViewIndvMembersContrLayout = new javax.swing.GroupLayout(ViewIndvMembersContr);
        ViewIndvMembersContr.setLayout(ViewIndvMembersContrLayout);
        ViewIndvMembersContrLayout.setHorizontalGroup(
            ViewIndvMembersContrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewIndvMembersContrLayout.createSequentialGroup()
                .addGroup(ViewIndvMembersContrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ViewIndvMembersContrLayout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(typetext, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ViewIndvMembersContrLayout.createSequentialGroup()
                        .addGroup(ViewIndvMembersContrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel7)
                            .addComponent(jLabel14)
                            .addComponent(jLabel12)
                            .addComponent(jLabel16))
                        .addGap(96, 96, 96)
                        .addGroup(ViewIndvMembersContrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(indvamoutr, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ViewIndvMembersContrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(indvname, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                                .addComponent(indvphones, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                                .addComponent(indvreg))
                            .addComponent(indvyers, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(ViewIndvMembersContrLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel25))
                    .addGroup(ViewIndvMembersContrLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnupdates)
                        .addGap(53, 53, 53)
                        .addComponent(btnindccl)
                        .addGap(81, 81, 81)
                        .addComponent(btnclsmem)))
                .addContainerGap(303, Short.MAX_VALUE))
        );
        ViewIndvMembersContrLayout.setVerticalGroup(
            ViewIndvMembersContrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewIndvMembersContrLayout.createSequentialGroup()
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(typetext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(ViewIndvMembersContrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(indvreg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(ViewIndvMembersContrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(indvname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(ViewIndvMembersContrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(indvphones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(ViewIndvMembersContrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(indvyers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(ViewIndvMembersContrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(indvamoutr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addGroup(ViewIndvMembersContrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnupdates)
                    .addComponent(btnindccl)
                    .addComponent(btnclsmem))
                .addContainerGap(308, Short.MAX_VALUE))
        );

        displaypanel.add(ViewIndvMembersContr, "card19");

        Receiptsubsc.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "TECHNOSCIENCE RECEIPT", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Perpetua Titling MT", 1, 18), new java.awt.Color(0, 51, 51))); // NOI18N

        areasubsz.setColumns(20);
        areasubsz.setRows(5);
        jScrollPane16.setViewportView(areasubsz);

        jButton15.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jButton15.setText("view");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        setPrintButtonActive1.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        setPrintButtonActive1.setText("print");
        setPrintButtonActive1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setPrintButtonActive1ActionPerformed(evt);
            }
        });

        jButton16.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jButton16.setText("close");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ReceiptsubscLayout = new javax.swing.GroupLayout(Receiptsubsc);
        Receiptsubsc.setLayout(ReceiptsubscLayout);
        ReceiptsubscLayout.setHorizontalGroup(
            ReceiptsubscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane16)
            .addGroup(ReceiptsubscLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jButton15)
                .addGap(123, 123, 123)
                .addComponent(setPrintButtonActive1)
                .addGap(125, 125, 125)
                .addComponent(jButton16)
                .addContainerGap(330, Short.MAX_VALUE))
        );
        ReceiptsubscLayout.setVerticalGroup(
            ReceiptsubscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReceiptsubscLayout.createSequentialGroup()
                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ReceiptsubscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(setPrintButtonActive1)
                    .addComponent(jButton15)
                    .addComponent(jButton16))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        displaypanel.add(Receiptsubsc, "card24");

        loginlabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        loginlabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        loginlabel.setText("Logged in as:");

        tresusername.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        tresusername.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        tresusername.setText("myusername");

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

        btnAddusers.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnAddusers.setText("Registration Fee");
        btnAddusers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddusersActionPerformed(evt);
            }
        });

        usrmgmt.setFont(new java.awt.Font("Engravers MT", 1, 12)); // NOI18N
        usrmgmt.setForeground(new java.awt.Color(0, 204, 102));
        usrmgmt.setText("Record Registration");

        stockmgmt.setFont(new java.awt.Font("Engravers MT", 1, 12)); // NOI18N
        stockmgmt.setForeground(new java.awt.Color(51, 51, 255));
        stockmgmt.setText("Record Subscription Fee");

        btnAddrooms.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnAddrooms.setText("Subscription Fee");
        btnAddrooms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddroomsActionPerformed(evt);
            }
        });

        lblwritting.setFont(new java.awt.Font("Engravers MT", 1, 12)); // NOI18N
        lblwritting.setForeground(new java.awt.Color(102, 0, 255));
        lblwritting.setText("Income And Expenses");

        btnmanageusers.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnmanageusers.setText("General Records");
        btnmanageusers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmanageusersActionPerformed(evt);
            }
        });

        btnmanagetenants.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnmanagetenants.setText("Selection Type");
        btnmanagetenants.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmanagetenantsActionPerformed(evt);
            }
        });

        btnmanageemp.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnmanageemp.setText("Based On Year And Semester");
        btnmanageemp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmanageempActionPerformed(evt);
            }
        });

        reppayment.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        reppayment.setText("Income and Expenses");
        reppayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reppaymentActionPerformed(evt);
            }
        });

        lblReports1.setFont(new java.awt.Font("Engravers MT", 1, 12)); // NOI18N
        lblReports1.setForeground(new java.awt.Color(153, 0, 51));
        lblReports1.setText("Notices");

        viewrentdates.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        viewrentdates.setText("Upload Notice");
        viewrentdates.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewrentdatesActionPerformed(evt);
            }
        });

        lblReports2.setFont(new java.awt.Font("Engravers MT", 1, 12)); // NOI18N
        lblReports2.setForeground(new java.awt.Color(51, 0, 153));
        lblReports2.setText("Report Generation");

        viewrules.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        viewrules.setText("View Notices");
        viewrules.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewrulesActionPerformed(evt);
            }
        });

        reptenant.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        reptenant.setText("Members Contribution");
        reptenant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reptenantActionPerformed(evt);
            }
        });

        updroomsd1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        updroomsd1.setText("Manage Income and Expenses");
        updroomsd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updroomsd1ActionPerformed(evt);
            }
        });

        lblwritting1.setFont(new java.awt.Font("Engravers MT", 1, 12)); // NOI18N
        lblwritting1.setForeground(new java.awt.Color(153, 153, 0));
        lblwritting1.setText("Manage Financial Records");

        lblwritting2.setFont(new java.awt.Font("Engravers MT", 1, 12)); // NOI18N
        lblwritting2.setForeground(new java.awt.Color(0, 102, 102));
        lblwritting2.setText("View ");

        updroomsd2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        updroomsd2.setText("TSMS Balance");
        updroomsd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updroomsd2ActionPerformed(evt);
            }
        });

        updroomsd3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        updroomsd3.setText("Record Income $ Expenditure");
        updroomsd3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updroomsd3ActionPerformed(evt);
            }
        });

        lblReports3.setFont(new java.awt.Font("Engravers MT", 1, 12)); // NOI18N
        lblReports3.setForeground(new java.awt.Color(51, 51, 255));
        lblReports3.setText("View/ UPDATE INDV. FINANCE");

        reptenant1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        reptenant1.setText("View Indvidual Contribution");
        reptenant1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reptenant1ActionPerformed(evt);
            }
        });

        updroomsd4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        updroomsd4.setText("Calendar of Activites");
        updroomsd4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updroomsd4ActionPerformed(evt);
            }
        });

        updroomsd5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        updroomsd5.setText("Club Members");
        updroomsd5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updroomsd5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout btnGroupLayout = new javax.swing.GroupLayout(btnGroup);
        btnGroup.setLayout(btnGroupLayout);
        btnGroupLayout.setHorizontalGroup(
            btnGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(stockmgmt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(btnGroupLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(updroomsd3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnGroupLayout.createSequentialGroup()
                        .addGroup(btnGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usrmgmt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAddusers, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(23, 23, 23))
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addGroup(btnGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnGroupLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(btnGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblwritting1)
                                    .addGroup(btnGroupLayout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(btnGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnmanagetenants)
                                            .addComponent(btnmanageusers)))))
                            .addGroup(btnGroupLayout.createSequentialGroup()
                                .addComponent(lblwritting)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addGroup(btnGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnmanageemp)
                            .addGroup(btnGroupLayout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(btnAddrooms, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(btnGroupLayout.createSequentialGroup()
                .addGroup(btnGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(btnGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(reptenant)
                            .addComponent(reppayment)))
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(btnGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(updroomsd1)
                            .addComponent(updroomsd2)
                            .addComponent(viewrules)
                            .addComponent(updroomsd4)
                            .addComponent(viewrentdates)
                            .addComponent(updroomsd5)
                            .addGroup(btnGroupLayout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(lblwritting2))))
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblReports2))
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(reptenant1)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(btnGroupLayout.createSequentialGroup()
                .addGroup(btnGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(lblReports1))
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblReports3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnGroupLayout.setVerticalGroup(
            btnGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnGroupLayout.createSequentialGroup()
                .addComponent(usrmgmt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddusers)
                .addGap(18, 18, 18)
                .addComponent(stockmgmt)
                .addGap(18, 18, 18)
                .addComponent(btnAddrooms)
                .addGap(18, 18, 18)
                .addComponent(lblwritting1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnmanageusers)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnmanagetenants)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnmanageemp)
                .addGap(18, 18, 18)
                .addComponent(lblwritting)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(updroomsd3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(updroomsd1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblwritting2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(updroomsd2)
                .addGap(11, 11, 11)
                .addComponent(updroomsd5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(updroomsd4)
                .addGap(9, 9, 9)
                .addComponent(lblReports1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viewrentdates)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viewrules)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblReports3)
                .addGap(4, 4, 4)
                .addComponent(reptenant1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblReports2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(reptenant)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reppayment)
                .addContainerGap(193, Short.MAX_VALUE))
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
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(softwareName, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89)
                        .addComponent(loginlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(137, 137, 137)
                        .addComponent(tresusername, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(tresusername)
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

    private void btnAddregsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddregsActionPerformed
        try{
        String sql = " INSERT INTO finance (regnumber, name, phonenumber, year_sem, purpose, amount )  VALUES (?,?,?,?,?,?)";
        con = DriverManager.getConnection("jdbc:mysql://localhost/tms","root","");
        pst = con.prepareStatement(sql);
        pst.setString(1, tfreg.getText());
        pst.setString(2, tfname.getText());
        pst.setString(3, tfphone.getText());
        pst.setString(4, tfyear.getText());
        pst.setString(5, tfpurpose.getText());
        pst.setString(6, tfamt.getText());
         if(tfreg.getText().equals("") || tfphone.getText().equals("") || tfname.getText().equals("") || tfyear.getText().equals("") || tfamt.getText().equals("") ) 
         {
             JOptionPane.showMessageDialog(null,"Some fields not filled in..!!\n Please Fill them to continue.","Warning",JOptionPane.WARNING_MESSAGE);
          }   
        else {
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Recording of Registration Fee For Technoscience Members is Successful");
            recreg = tfreg.getText();
            fnames = tfname.getText();
            lnames = tfphone.getText();
            amounts = tfamt.getText();
           arrearst = tfyear.getText();
           rnums = tfpurpose.getText();
            
        }
        } 
        catch(HeadlessException | SQLException ex){
        JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_btnAddregsActionPerformed

    private void btnClearUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearUserActionPerformed
            tfreg.setText("");
            tfname.setText("");
            tfphone.setText("");
            tfyear.setText("");
            tfamt.setText("");    
    }//GEN-LAST:event_btnClearUserActionPerformed

    private void btnCloseNewUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseNewUserActionPerformed
        load_home();
    }//GEN-LAST:event_btnCloseNewUserActionPerformed

    private void tablefinanceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablefinanceMouseClicked
        String regit = tablefinance.getValueAt(tablefinance.getSelectedRow(), 1).toString();
        String nemis = tablefinance.getValueAt(tablefinance.getSelectedRow(), 2).toString();
        String phs = tablefinance.getValueAt(tablefinance.getSelectedRow(), 3).toString();
        String yr = tablefinance.getValueAt(tablefinance.getSelectedRow(), 4).toString();
        String ps = tablefinance.getValueAt(tablefinance.getSelectedRow(), 5).toString();
        String ams = tablefinance.getValueAt(tablefinance.getSelectedRow(), 6).toString();

        txtregst.setText(regit);
        txtnt.setText(nemis);
        txtpnts.setText(phs);
        txtsmts.setText(yr);
        txtprpt.setText(ps);
        txtamrs.setText(ams);
    }//GEN-LAST:event_tablefinanceMouseClicked

    private void retrieveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retrieveActionPerformed
      retrievefrecords();
      totalmemberrecs();
    }//GEN-LAST:event_retrieveActionPerformed

    private void btnUpdaterecinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdaterecinActionPerformed
      try {
            if (txtregst.getText().isEmpty() || txtnt.getText().isEmpty() || txtpnts.getText().isEmpty()|| txtsmts.getText().isEmpty()|| txtprpt.getText().isEmpty()|| txtamrs.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No row is selected..!!\nPlease select a row to update...");
            } else {
                int index = tablefinance.getSelectedRow();
                String id = tablefinance.getValueAt(index, 0).toString();  //index=rownumber and 0 represents first column..

                if (updatefrecords(id, txtregst.getText(), txtnt.getText(), txtpnts.getText(), txtsmts.getText(), txtprpt.getText(),txtamrs.getText())){
                    JOptionPane.showMessageDialog(null, "Financial Records Data Updated Successfully");
                    txtregst.setText("");
                    txtnt.setText("");
                    txtpnts.setText("");
                    txtsmts.setText("");
                    txtprpt.setText("");
                    txtamrs.setText("");
                    retrievefrecords();
                    totalmemberrecs();
                } else {
                    JOptionPane.showMessageDialog(null, "Financial Records Data is Not Updated ");
                }
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }           
    }//GEN-LAST:event_btnUpdaterecinActionPerformed

    private void btndelrecsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndelrecsActionPerformed
        try {
            String[] options = {"Yes", "No"};
            int answer = JOptionPane.showOptionDialog(null, " Are you Sure to delete ??", "Delete Options", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

            if (answer == 0) {
              String id = tablefinance.getValueAt(tablefinance.getSelectedRow(), 0).toString();
                if (deletefrecords(id)) {
                    JOptionPane.showMessageDialog(null, "Financial Data Deleted Successfully");
                    txtregst.setText("");
                    txtnt.setText("");
                    txtpnts.setText("");
                    txtsmts.setText("");
                    txtprpt.setText("");
                    txtamrs.setText("");
                    retrievefrecords();
                    totalmemberrecs();
                } else {
                    JOptionPane.showMessageDialog(null, " FInancial Data is Not Deleted ");
                }
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, e);
        }        
    }//GEN-LAST:event_btndelrecsActionPerformed

    private void btnCloseMaintainrlsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseMaintainrlsActionPerformed
         load_home();
    }//GEN-LAST:event_btnCloseMaintainrlsActionPerformed

    private void btnAddusersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddusersActionPerformed
        displaypanel.removeAll();
        displaypanel.add(AddRegFee);
        AddRegFee.setVisible(true);
        AddRegFee.repaint();
        AddRegFee.revalidate();
        btnGroup.setVisible(false);
    }//GEN-LAST:event_btnAddusersActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
            subr.setText("");
            subnm.setText("");
            subpr.setText("");
            subys.setText("");
            subams.setText("");
           
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnClosesxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClosesxActionPerformed
            load_home();
    }//GEN-LAST:event_btnClosesxActionPerformed

    private void btnAddroomsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddroomsActionPerformed
       displaypanel.removeAll();
        displaypanel.add(AddSubscFee);
        AddSubscFee.setVisible(true);
        AddSubscFee.repaint();
        AddSubscFee.validate();
        btnGroup.setVisible(false);
    }//GEN-LAST:event_btnAddroomsActionPerformed

    private void btnGetfinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetfinActionPerformed

        totalselectedfinaces();
    }//GEN-LAST:event_btnGetfinActionPerformed

    private void selectedfinaceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectedfinaceMouseClicked
        String mrs = selectedfinace.getValueAt(selectedfinace.getSelectedRow(), 1).toString();
        String nrs = selectedfinace.getValueAt(selectedfinace.getSelectedRow(), 2).toString();
        String mprs = selectedfinace.getValueAt(selectedfinace.getSelectedRow(), 3).toString();
        String mys = selectedfinace.getValueAt(selectedfinace.getSelectedRow(), 4).toString();
        String mpes = selectedfinace.getValueAt(selectedfinace.getSelectedRow(), 5).toString();
        String amsw = selectedfinace.getValueAt(selectedfinace.getSelectedRow(), 6).toString();
        manreg.setText(mrs);
        mannams.setText(nrs);
        manphone.setText(mprs);
        manysd.setText(mys );
        manpr.setText(mpes); 
        manamtrs.setText(amsw); 
    }//GEN-LAST:event_selectedfinaceMouseClicked

    private void deletefgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletefgActionPerformed
       try {
            String[] options = {"Yes", "No"};
            int answer = JOptionPane.showOptionDialog(null, " Are you Sure to delete ??", "Delete Options", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            if (answer == 0) {
                String id = selectedfinace.getValueAt(selectedfinace.getSelectedRow(), 0).toString();
                if (deleteselectedfinaces(id)) {
                    JOptionPane.showMessageDialog(null, "Finance Record Data Deleted Successfully");
                    manreg.setText("");
                    mannams.setText("");
                    manphone.setText("");
                    manysd.setText("");
                    manpr.setText("");
                    manamtrs.setText("");
                    retrieveselectedfinaces();
                    totalselectedfinaces();
                } else {
                    JOptionPane.showMessageDialog(null, " Finance Record is Not Deleted ");
                }
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, e);
        }       
    }//GEN-LAST:event_deletefgActionPerformed

    private void updatefvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatefvActionPerformed
         try {
            if (manreg.getText().isEmpty() || mannams.getText().isEmpty() || manphone.getText().isEmpty() || manysd.getText().isEmpty()|| manpr.getText().isEmpty()
                    ){
                JOptionPane.showMessageDialog(null, "No row is selected..!!\nPlease select a row to update...");
            } else {
                int index = selectedfinace.getSelectedRow();
                String id = selectedfinace.getValueAt(index, 0).toString();
                if (updateselectedfinaces(id, manreg.getText(), mannams.getText(), manphone.getText() ,manysd.getText(),manpr.getText(), manamtrs.getText())) {
                    JOptionPane.showMessageDialog(null, "Finance Record Data Updated Successfully");
                    manreg.setText("");
                    mannams.setText("");
                    manphone.setText("");
                    manysd.setText("");
                    manpr.setText("");
                    manamtrs.setText("");
                    retrieveselectedfinaces();
                    totalselectedfinaces();
                } else {
                    JOptionPane.showMessageDialog(null, "Finance Record Data is Not Updated ");
                }
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }      
    }//GEN-LAST:event_updatefvActionPerformed

    private void btnClosefgsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClosefgsActionPerformed
          load_home();
    }//GEN-LAST:event_btnClosefgsActionPerformed

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

    private void clsisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clsisActionPerformed
         load_home();
    }//GEN-LAST:event_clsisActionPerformed

    private void registersubbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registersubbtnActionPerformed
       try{
        String sql = " INSERT INTO finance (regnumber, name, phonenumber, year_sem, purpose, amount )  VALUES (?,?,?,?,?,?)";
        con = DriverManager.getConnection("jdbc:mysql://localhost/tms","root","");
        pst = con.prepareStatement(sql);
        pst.setString(1, subr.getText());
        pst.setString(2, subnm.getText());
        pst.setString(3, subpr.getText());
        pst.setString(4, subys.getText());
        pst.setString(5, subps.getText());
        pst.setString(6, subams.getText());
         if(subr.getText().equals("") || subnm.getText().equals("") || subpr.getText().equals("") || subys.getText().equals("") || subams.getText().equals("") ) 
         {
             JOptionPane.showMessageDialog(null,"Some fields not filled in..!!\n Please Fill them to continue.","Warning",JOptionPane.WARNING_MESSAGE);
          }   
        else {
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Recording of Subscription Fee For Technoscience Members is Successful");
             subregs = subr.getText();
             subnames = subnm.getText();
             subphone = subpr.getText();
             subyr = subys.getText();
             subpurp = subps.getText();
             sabamt = subams.getText();

        }
        } 
        catch(HeadlessException | SQLException ex){
        JOptionPane.showMessageDialog(null,ex);
        }          
    }//GEN-LAST:event_registersubbtnActionPerformed

    private void btnReloadyersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadyersActionPerformed
         retrieveyearselect();
         totalyearselect();
    }//GEN-LAST:event_btnReloadyersActionPerformed

    private void tblfinacialyrsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblfinacialyrsMouseClicked
        DefaultTableModel model=(DefaultTableModel) tblfinacialyrs.getModel();
        String selectedregn=(String) model.getValueAt(tblfinacialyrs.getSelectedRow(), 1);
        String lselectedlname=(String) model.getValueAt(tblfinacialyrs.getSelectedRow(), 2);
        String selectedphone=(String) model.getValueAt(tblfinacialyrs.getSelectedRow(), 3);
        String selectedyt=(String) model.getValueAt(tblfinacialyrs.getSelectedRow(), 4);
        String pluf=(String) model.getValueAt(tblfinacialyrs.getSelectedRow(), 5);
        String selecteamtr=(String) model.getValueAt(tblfinacialyrs.getSelectedRow(), 5);
        yreg.setText(selectedregn);
        ynms.setText(lselectedlname);
        ypones.setText(selectedphone);
        yyst.setText(selectedyt);
        yprt.setText(pluf);
        yamd.setText(selecteamtr);
    }//GEN-LAST:event_tblfinacialyrsMouseClicked

    private void btnDeletefrsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletefrsActionPerformed
          try {
            String[] options = {"Yes", "No"};
            int answer = JOptionPane.showOptionDialog(null, " Are you Sure to Financial data ??", "Delete Options", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            if (answer == 0) {
                String id = tblfinacialyrs.getValueAt(tblfinacialyrs.getSelectedRow(), 0).toString();
                if (deleteyearselect(id)) {
                    JOptionPane.showMessageDialog(null, "Finacial Data Deleted Successfully");
                    yreg.setText("");
                    ynms.setText("");
                    ypones.setText("");
                    yyst.setText("");
                    yamd.setText("");
                    yprt.setText("");
                    retrieveyearselect();
                    totalyearselect();
                } else {
                    JOptionPane.showMessageDialog(null, " Finacial Data Not Deleted ");
                }
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, e);
        }        
    }//GEN-LAST:event_btnDeletefrsActionPerformed

    private void btnUpdateytsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateytsActionPerformed
          try {
            if (yreg.getText().isEmpty() || ynms.getText().isEmpty() || yyst.getText().isEmpty() || yamd.getText().isEmpty()|| ypones.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No row is selected..!!\nPlease select a row to update...");
            } else {
                int index = tblfinacialyrs.getSelectedRow();
                String id = tblfinacialyrs.getValueAt(index, 0).toString();

                if (updateyearselect(id, yreg.getText(), ynms.getText(), ypones.getText() ,yyst.getText(), yprt.getText(), yamd.getText())) {
                    JOptionPane.showMessageDialog(null, "Finance Record Data Updated Successfully");
                    yreg.setText("");
                    ynms.setText("");
                    ypones.setText("");
                    yyst.setText("");
                    yamd.setText("");
                    yprt.setText("");
                    retrieveyearselect();
                    totalyearselect();

                } else {
                    JOptionPane.showMessageDialog(null, "Financial Record Data Not Updated ");
                }

            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }
       
    }//GEN-LAST:event_btnUpdateytsActionPerformed

    private void btnmanageusersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmanageusersActionPerformed
         displaypanel.removeAll();
        displaypanel.add(GenManageFee);
        GenManageFee.setVisible(true);
        GenManageFee.repaint();
        GenManageFee.revalidate(); 
        btnGroup.setVisible(false);
    }//GEN-LAST:event_btnmanageusersActionPerformed

    private void btnmanagetenantsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmanagetenantsActionPerformed
         displaypanel.removeAll();
        displaypanel.add(ManageOnType);
        ManageOnType.setVisible(true);
        ManageOnType.repaint();
        ManageOnType.revalidate(); 
        btnGroup.setVisible(false);
    }//GEN-LAST:event_btnmanagetenantsActionPerformed

    private void btnmanageempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmanageempActionPerformed
        displaypanel.removeAll();
        displaypanel.add(ManageOnYr);
        ManageOnYr.setVisible(true);
        ManageOnYr.repaint();
        ManageOnYr.revalidate();  
        btnGroup.setVisible(false);
    }//GEN-LAST:event_btnmanageempActionPerformed

    private void btnaddicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddicActionPerformed
        try{
        String sql = " INSERT INTO Incomeexpenditures (Type, source, Amount)  VALUES (?,?,?)";
        con = DriverManager.getConnection("jdbc:mysql://localhost/tms","root","");
        pst = con.prepareStatement(sql);
        pst.setString(1,fictype.getSelectedItem().toString());
        pst.setString(2, srexpc.getText());
        pst.setString(3, txtamexpta.getText());
        
         if(fictype.getSelectedIndex()==0 || srexpc.getText().equals("") || txtamexpta.getText().equals("")){
             JOptionPane.showMessageDialog(null,"Some fields not filled in..!!\n Please Fill them to continue.","Warning",JOptionPane.WARNING_MESSAGE);
          }       
       else {
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Recording Income And Expenditures is Successful");
              fictype.setSelectedIndex(0); 
              srexpc.setText(" "); 
              txtamexpta.setText("");
        }   
        } 
        catch(HeadlessException | SQLException ex){
        JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_btnaddicActionPerformed

    private void clricActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clricActionPerformed
              fictype.setSelectedIndex(0); 
              srexpc.setText(" ");
              txtamexpta.setText("");
    }//GEN-LAST:event_clricActionPerformed

    private void btnbalanceretActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbalanceretActionPerformed
         totalcontribution();
         totalclinicmoney();
         totalexpenses();
         totalotherincome();
         int srt = Integer.parseInt(balsr.getText());
         int clint = Integer.parseInt(baloinc.getText());
         int balit = Integer.parseInt(clinictots.getText());
         
         
         
     //    int total = Integer.parseInt(balsr.getText())+Integer.parseInt(clinictots.getText() + baloinc.getText());
                 //Integer.parseInt(baltotalincome.getText());
      int total = srt + clint + balit;
         baltotalincome.setText(String.valueOf(total));
         
         int balance = Integer.parseInt(baltotalincome.getText()) - Integer.parseInt(baltotalexp.getText()); 
     //    
         
         if (balance < 0){
            JOptionPane.showMessageDialog(null,"Kindly Recheck Your Recording Transactions");
         }
         else{
              balsnce.setText(String.valueOf(balance)); 
         }
         
    }//GEN-LAST:event_btnbalanceretActionPerformed

    private void btnclosefrdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclosefrdActionPerformed
             load_home();
    }//GEN-LAST:event_btnclosefrdActionPerformed

    private void rectsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rectsKeyReleased
         String query1 = rects.getText();
        filterfrecords(query1);
    }//GEN-LAST:event_rectsKeyReleased

    private void tenantsearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tenantsearchKeyReleased
           String query1 = tenantsearch.getText();
           filterselectedfinaces(query1);
    }//GEN-LAST:event_tenantsearchKeyReleased

    private void empsearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_empsearchKeyReleased
       String query1 = empsearch.getText();
       filteryearselect(query1);
    }//GEN-LAST:event_empsearchKeyReleased

    private void btnretrieveprovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnretrieveprovActionPerformed
         retreaveexpinc();
    }//GEN-LAST:event_btnretrieveprovActionPerformed

    private void btnupdateprovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateprovActionPerformed
      try {
            if (provname.getText().isEmpty() || provcont.getText().isEmpty() || provtype.getText().isEmpty() || dtrinc.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No row is selected..!!\nPlease select a row to update...");
            } else {
                int index = incomeexpensestbl.getSelectedRow();
                String id = incomeexpensestbl.getValueAt(index, 0).toString();
                if (updateexpinc(id, provname.getText(), provcont.getText(), provtype.getText(), dtrinc.getText())) {
                    JOptionPane.showMessageDialog(null, "Income And Expenditures Data updated successifully");
                    provname.setText("");
                    provcont.setText("");
                    provtype.setText("");
                    dtrinc.setText("");
                    retreaveexpinc();
                } else {
                    JOptionPane.showMessageDialog(null, "Income And Expenditures  Data Not updated ");
                }
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btnupdateprovActionPerformed

    private void btndeleteProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteProvActionPerformed
         try {
            String[] options = {"Yes", "No"};
            int answer = JOptionPane.showOptionDialog(null, " Are you Sure to delete Other Incomes And Expenditure Data ??", "Delete Options", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            if (answer == 0) {
                String project_id = incomeexpensestbl.getValueAt(incomeexpensestbl.getSelectedRow(), 0).toString();   
                if (deleteexpinc(project_id)) {
                    JOptionPane.showMessageDialog(null, "Other Income And Expenditure Data deleted successfully");
                    provname.setText("");
                    provcont.setText("");
                    provtype.setText("");
                    dtrinc.setText("");
                    retreaveexpinc();
                } else {
                    JOptionPane.showMessageDialog(null, "Other Income And Expenditure Data is Not deleted ");
                }
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_btndeleteProvActionPerformed

    private void otIncExpsearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_otIncExpsearchKeyReleased
        String query1 = otIncExpsearch.getText();
        filterexpinc(query1);
    }//GEN-LAST:event_otIncExpsearchKeyReleased

    private void viewregulationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewregulationsActionPerformed
      try {
           Class.forName("com.mysql.jdbc.Driver");
           String sql ="SELECT `Event` FROM `calendar_of_activities` WHERE id=1";
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms","root","");
           pst = con.prepareStatement(sql);
           rs = pst.executeQuery();
           if (rs.next()){
           String treaactiv = rs.getString("rules");
           trecalevents.setText("*******************************************\n");
           trecalevents.setText(trecalevents.getText()+"   CALENDAR OF ACTIVITIES                \n");
           trecalevents.setText(trecalevents.getText()+"*******************************************\n");
           trecalevents.setText(trecalevents.getText()+treaactiv);
           }
       } catch (ClassNotFoundException | SQLException ex) {
           Logger.getLogger(TreasurerHome.class.getName()).log(Level.SEVERE, null, ex);
       }
    }//GEN-LAST:event_viewregulationsActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
          try {
           Class.forName("com.mysql.jdbc.Driver");
           String sql ="INSERT INTO notices (noticename)  VALUES(?)";
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms","root","");
           pst = con.prepareStatement(sql);
           pst.setString(1,vwnoticetre.getText());
             if (vwnoticetre.getText().isEmpty() ){
             JOptionPane.showMessageDialog(null,"Please Enter Notice to Upload");    
             }
             else{ 
           pst.executeUpdate();
           vwnoticetre.setText("");
           JOptionPane.showMessageDialog(null,"Successfully Uploaded");
             }
       } catch (ClassNotFoundException | SQLException ex) {
           Logger.getLogger(TreasurerHome.class.getName()).log(Level.SEVERE, null, ex);
       }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void printregulationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printregulationsActionPerformed
        try {
            trecalevents.print();
            trecalevents.setText("");
            load_home();
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

    private void setPrintButtonActiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setPrintButtonActiveActionPerformed
        try {
            area.print();
            load_home();
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }        
    }//GEN-LAST:event_setPrintButtonActiveActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        area.setEditable(false);
        area.setText(area.getText() + "************************************************************************\n");
        area.setText(area.getText() + "            TECHNOSCIENCE MANAGEMENT SYSTEM RECEIPT              \n");
        area.setText(area.getText() + "************************************************************************\n");

        java.util.Date obj = new java.util.Date();
        String date = obj.toString();

        int sereal = 1235 + (int) (Math.random() * 4875);

        area.setText(area.getText() + "\n    Reference No        :  " + sereal);
  
        area.setText(area.getText()+  "\n                APPROVED ");
        
        area.setText(area.getText() + "\n    Regnumber :              " + TreasurerHome.recreg);
        area.setText(area.getText() + "\n    Name:       " + TreasurerHome.fnames );
        area.setText(area.getText() + "\n    Phonenumber:            " + TreasurerHome.lnames);
        area.setText(area.getText() + "\n    Year And Semester:        " + TreasurerHome.arrearst);
        area.setText(area.getText() + "\n    Purpose:        " + TreasurerHome.rnums);
        area.setText(area.getText() + "\n    Amount:        " + TreasurerHome.amounts);
        area.setText(area.getText() + "\n    Date                " + date);
        area.setText(area.getText() + "\n    P.O.Box 312 CHUKA\n    Email : technologysolutionssolution@gmail.com ");
       ///area.setText(area.getText() + "\n    Served by           :  "+Login.Usr);
        area.setText(area.getText() + "\n************************************************************************");
        area.setText(area.getText() + "\n            Keep the Receipt for future reference.");
        area.setText(area.getText() + "\n            Thank you for your COntribution..!!");
        area.setText(area.getText() + "\n************************************************************************");
               
    }//GEN-LAST:event_jButton12ActionPerformed

    private void viewrentdatesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewrentdatesActionPerformed
        displaypanel.removeAll();
        displaypanel.add(TreUploadNotices);
        TreUploadNotices.setVisible(true);
        TreUploadNotices.repaint();
        TreUploadNotices.revalidate();
        btnGroup.setVisible(false);
    }//GEN-LAST:event_viewrentdatesActionPerformed

    private void viewrulesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewrulesActionPerformed
        displaypanel.removeAll();
        displaypanel.add(TreViewNotices);
        TreViewNotices.setVisible(true);
        TreViewNotices.repaint();
        TreViewNotices.revalidate();
        btnGroup.setVisible(false);
    }//GEN-LAST:event_viewrulesActionPerformed

    private void incomeexpensestblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_incomeexpensestblMouseClicked
        String pron = incomeexpensestbl.getValueAt(incomeexpensestbl.getSelectedRow(), 1).toString();
        String proc = incomeexpensestbl.getValueAt(incomeexpensestbl.getSelectedRow(), 2).toString();
        String prot = incomeexpensestbl.getValueAt(incomeexpensestbl.getSelectedRow(), 3).toString();
        String prol = incomeexpensestbl.getValueAt(incomeexpensestbl.getSelectedRow(), 4).toString();

        provname.setText(pron);
        provcont.setText(proc);
        provtype.setText(prot);
        dtrinc.setText(prol);
    }//GEN-LAST:event_incomeexpensestblMouseClicked

    private void genrentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genrentActionPerformed
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
    }//GEN-LAST:event_genrentActionPerformed

    private void closerentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closerentActionPerformed
         load_home();       
    }//GEN-LAST:event_closerentActionPerformed

    private void gentenantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gentenantActionPerformed
            String FILE_NAME = "C:\\Users\\MICHAEL\\Documents\\NetBeansProjects\\TechnoScienceManagementSystem\\MemberContribution.pdf";
            Document document = new Document();
     try{
         PdfWriter.getInstance(document, new FileOutputStream(new File(FILE_NAME)));
         document.open();
     
         document.add(new Paragraph(new Date().toString()));
         
         document.add(new Paragraph("MEMBERS CONTRIBUTION  REPORT"));
          
          document.add(new Paragraph("------------------------------------------------------------------------------------------------------------"));
          PdfPTable tablesup = new PdfPTable(8);
          tablesup.setWidthPercentage(100);
          tablesup.setSpacingBefore(10f);
          tablesup.setSpacingAfter(10f);
         
         tablesup.addCell("Finance id");
         tablesup.addCell("Registration No.");
         tablesup.addCell("Names");
         tablesup.addCell("Phonenumber");
         tablesup.addCell("Year $ Semester");
         tablesup.addCell("Purpose");
         tablesup.addCell("Amount");
         tablesup.addCell("Contribution Date");
         
         Connection con;
         Statement st;
         ResultSet rs;
         Class.forName("com.mysql.jdbc.Driver");
         con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms","root","");
         st = con.createStatement();
         String query = "Select * from Finance";
         rs = st.executeQuery(query);
         while(rs.next()){
           String v1 = rs.getString("Finance_id");
           String v2 = rs.getString("regnumber");
           String v3 = rs.getString("name");
           String v4 = rs.getString("phonenumber");
           String v5 = rs.getString("year_sem");
           String v6 = rs.getString("purpose");
           String v7 = rs.getString("amount");
           String v8 = rs.getString("datetime");
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
         JOptionPane.showMessageDialog(null, "Members Contribution Report Saved"); 
         JOptionPane.showMessageDialog(null, "You can now view Report in your System Directory");
          load_home();
     } catch(Exception e){
         e.printStackTrace();
     }
    }//GEN-LAST:event_gentenantActionPerformed

    private void closetenantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closetenantActionPerformed
        load_home();
    }//GEN-LAST:event_closetenantActionPerformed

    private void reptenantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reptenantActionPerformed
         displaypanel.removeAll();
         displaypanel.add(MembersContrReport);
         MembersContrReport.setVisible(true);
         MembersContrReport.repaint();
         MembersContrReport.revalidate(); 
         btnGroup.setVisible(false);
    }//GEN-LAST:event_reptenantActionPerformed

    private void reppaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reppaymentActionPerformed
        displaypanel.removeAll();
        displaypanel.add(IncomeAndExpenditureReports);
        IncomeAndExpenditureReports.setVisible(true);
        IncomeAndExpenditureReports.repaint();
        IncomeAndExpenditureReports.revalidate();  
        btnGroup.setVisible(false);
    }//GEN-LAST:event_reppaymentActionPerformed

    private void btnclosebalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclosebalActionPerformed
         load_home();
    }//GEN-LAST:event_btnclosebalActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
         load_home();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        load_home();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
         retrievetreasurermembers(); 
         totaltremebersd();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void viewtresMembersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewtresMembersMouseClicked
       
    }//GEN-LAST:event_viewtresMembersMouseClicked

    private void updroomsd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updroomsd1ActionPerformed
         displaypanel.removeAll();
        displaypanel.add(ManageIncomeExps);
        ManageIncomeExps.setVisible(true);
        ManageIncomeExps.repaint();
        ManageIncomeExps.revalidate();  
        btnGroup.setVisible(false);
    }//GEN-LAST:event_updroomsd1ActionPerformed

    private void retrievedtsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retrievedtsActionPerformed
       try {
           Class.forName("com.mysql.jdbc.Driver");
           String sql ="SELECT * FROM `notices`";
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms","root","");
           pst = con.prepareStatement(sql);
           rs = pst.executeQuery();
           noticetres.setText("*******************************************\n");
           noticetres.setText(noticetres.getText()+"     VIEW AVAILABLE NOTICES \n");
           noticetres.setText(noticetres.getText()+"*******************************************\n");
           noticetres.setText(noticetres.getText()+"NO.    NOTICE NAME\n");
           while (rs.next()){
           String id = rs.getString("notice_id");
           String notlst = rs.getString("noticename");
           String date = rs.getString("date_recorded");
           noticetres.setText("\n"+noticetres.getText()+ id+"    "+notlst+" DATE---->> "+date+"\n");
           }
       } catch (ClassNotFoundException | SQLException ex) {
           Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
       }            
    }//GEN-LAST:event_retrievedtsActionPerformed

    private void tfregKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfregKeyPressed
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                String txtregtn = tfreg.getText();
                pst = con.prepareStatement("Select * from members where regno = ?");
                pst.setString(1, txtregtn);
                rs = pst.executeQuery();
                if (rs.next() == false) {
                    JOptionPane.showMessageDialog(this, "The Registration Number You Entered is not found try Again");
                } else {
                    String namt = rs.getString("names");
                     tfname.setText(namt.trim());
                     String tprt = rs.getString("phonenumbers");
                     tfphone.setText(tprt.trim());
                     String ysmtd = rs.getString("year_semester");
                     tfyear.setText(ysmtd.trim());
                     tfamt.requestFocus(); 
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }                  
    }//GEN-LAST:event_tfregKeyPressed

    private void subrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_subrKeyPressed
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                String regnsbv = subr.getText();
                pst = con.prepareStatement("Select * from finance where regnumber = ?");
                pst.setString(1, regnsbv);
                rs = pst.executeQuery();
                if (rs.next() == false) {
                    JOptionPane.showMessageDialog(this, "The Member Should first Contribute Registration Fee");
                } else {
                    String snc = rs.getString("name");
                     subnm.setText(snc.trim());
                     String sprf = rs.getString("phonenumber");
                     subpr.setText(sprf.trim());
                     String srys = rs.getString("year_sem");
                     subys.setText(srys.trim());
                     subams.requestFocus(); 
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }                  
    }//GEN-LAST:event_subrKeyPressed

    private void updroomsd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updroomsd2ActionPerformed
        displaypanel.removeAll();
        displaypanel.add(ViewBalance);
        ViewBalance.setVisible(true);
        ViewBalance.repaint();
        ViewBalance.revalidate();
        btnGroup.setVisible(false);
    }//GEN-LAST:event_updroomsd2ActionPerformed

    private void updroomsd3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updroomsd3ActionPerformed
        displaypanel.removeAll();
        displaypanel.add(RecordIncomeExp);
        RecordIncomeExp.setVisible(true);
        RecordIncomeExp.repaint();
        RecordIncomeExp.revalidate(); 
        btnGroup.setVisible(false);
    }//GEN-LAST:event_updroomsd3ActionPerformed

    private void baltotalincomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_baltotalincomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_baltotalincomeActionPerformed

    private void indicatorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_indicatorsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_indicatorsActionPerformed

    private void baltotalexpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_baltotalexpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_baltotalexpActionPerformed

    private void balsnceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_balsnceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_balsnceActionPerformed

    private void btnbalreset1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbalreset1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnbalreset1ActionPerformed

    private void indvregKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_indvregKeyPressed
         if( evt.getKeyCode() == KeyEvent.VK_ENTER){
            try {
                String txtregd = indvreg.getText();
                String purp = typetext.getSelectedItem().toString();
                pst = con.prepareStatement("Select * from Finance where regnumber = ? and purpose = ?");
                pst.setString(1, txtregd);
                pst.setString(2, purp);
                rs = pst.executeQuery();
                if(rs.next() == false)
                {
                    JOptionPane.showMessageDialog(this, "Registration Number Not Found");
                    JOptionPane.showMessageDialog(this, "Or Incorrect Registration Number,,Please Try Again");
                }
                else{
                    String accn = rs.getString("name");
                    indvname.setText(accn.trim());
                    String accp = rs.getString("phonenumber");
                    indvphones.setText(accp.trim());
                    String accys = rs.getString("year_sem");
                    indvyers.setText(accys.trim());
                    String accamt = rs.getString("Amount");
                    indvamoutr.setText(accamt.trim());
                }
            } catch (SQLException ex) {
                Logger.getLogger(Admin_Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_indvregKeyPressed

    private void btnindcclActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnindcclActionPerformed
        typetext.setSelectedIndex(0);
        indvreg.setText("");
        indvamoutr.setText("");
        indvname.setText("");
        indvphones.setText("");
        indvyers.setText("");

    }//GEN-LAST:event_btnindcclActionPerformed

    private void btnupdatesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdatesActionPerformed
         try {
            String sql6 ="UPDATE `Finance` SET `regnumber`=?, `name`=?, `phonenumber`=?, `year_sem`=?, `purpose`=?, `Amount`=? WHERE Finance_id=?";
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms","root","");
            pst = con.prepareStatement(sql6);
            pst.setString(1, indvreg.getText());
            pst.setString(2, indvname.getText());
            pst.setString(3, indvphones.getText());
            pst.setString(4, indvyers.getText());
            pst.setString(5, typetext.getSelectedItem().toString());
            pst.setString(6,indvamoutr.getText());
            if (indvreg.getText().equals("") || indvamoutr.getText().equals("") || indvname.getText().equals("") || typetext.getSelectedIndex() == 0 || indvphones.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Some fields not filled in..!!\n Please Fill them to continue.", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Updation of Members Contribution Details is  Successful");
                typetext.setSelectedIndex(0);
                indvreg.setText("");
                indvamoutr.setText("");
                indvname.setText("");
                indvphones.setText("");
                indvyers.setText("");
            }
        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_btnupdatesActionPerformed

    private void btnclsmemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclsmemActionPerformed
        load_home();
    }//GEN-LAST:event_btnclsmemActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        vwnoticetre.setText("");
    }//GEN-LAST:event_jButton13ActionPerformed

    private void reptenant1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reptenant1ActionPerformed
        displaypanel.removeAll();
        displaypanel.add(ViewIndvMembersContr);
        ViewIndvMembersContr.setVisible(true);
        ViewIndvMembersContr.repaint();
        ViewIndvMembersContr.revalidate(); 
        btnGroup.setVisible(false);
    }//GEN-LAST:event_reptenant1ActionPerformed

    private void updroomsd4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updroomsd4ActionPerformed
        displaypanel.removeAll();
        displaypanel.add(ViewCalendarActivites);
        ViewCalendarActivites.setVisible(true);
        ViewCalendarActivites.repaint();
        ViewCalendarActivites.revalidate(); 
        btnGroup.setVisible(false);
    }//GEN-LAST:event_updroomsd4ActionPerformed

    private void updroomsd5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updroomsd5ActionPerformed
        displaypanel.removeAll();
        displaypanel.add(ViewMembers);
        ViewMembers.setVisible(true);
        ViewMembers.repaint();
        ViewMembers.revalidate();
        btnGroup.setVisible(false);
    }//GEN-LAST:event_updroomsd5ActionPerformed

    private void selecttypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selecttypeActionPerformed
        // TODO add your handling code here:
        retrieveselectedfinaces();
        totaltrememberselctedgr();
        totalselectedfinaces();
    }//GEN-LAST:event_selecttypeActionPerformed

    private void btnClearUser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearUser1ActionPerformed
        displaypanel.removeAll();
        displaypanel.add(Receipt);
        Receipt.setVisible(true);
        Receipt.repaint();
        Receipt.revalidate(); 
        btnGroup.setVisible(false);
    }//GEN-LAST:event_btnClearUser1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            tablefinance.print();
        } catch (PrinterException ex) {
            Logger.getLogger(TreasurerHome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            selectedfinace.print();
        } catch (PrinterException ex) {
            Logger.getLogger(TreasurerHome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void subrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subrActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_subrActionPerformed

    private void typetextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typetextActionPerformed
       
    }//GEN-LAST:event_typetextActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        area.setEditable(false);
        area.setText(area.getText() + "************************************************************************\n");
        area.setText(area.getText() + "            TECHNOSCIENCE MANAGEMENT SYSTEM RECEIPT              \n");
        area.setText(area.getText() + "************************************************************************\n");

        java.util.Date obj = new java.util.Date();
        String date = obj.toString();

        int sereal = 1235 + (int) (Math.random() * 4875);

        area.setText(area.getText() + "\n    Reference No        :  " + sereal);
  
        area.setText(area.getText()+  "\n                APPROVED ");
        
        areasubsz.setText(areasubsz.getText() + "\n    Regnumber :              " + TreasurerHome.subregs);
        areasubsz.setText(areasubsz.getText() + "\n    Name:       " + TreasurerHome.subnames );
        areasubsz.setText(areasubsz.getText() + "\n    Phonenumber:            " + TreasurerHome.subphone);
        areasubsz.setText(areasubsz.getText() + "\n    Year And Semester:        " + TreasurerHome.subyr);
        areasubsz.setText(areasubsz.getText() + "\n    Purpose:        " + TreasurerHome.subpurp);
        areasubsz.setText(areasubsz.getText() + "\n    Amount:        " + TreasurerHome.sabamt);
        areasubsz.setText(areasubsz.getText() + "\n    Date                " + date);
        areasubsz.setText(areasubsz.getText() + "\n    P.O.Box 312 CHUKA\n    Email : technologysolutionssolution@gmail.com ");
       ///area.setText(area.getText() + "\n    Served by           :  "+Login.Usr);
        areasubsz.setText(areasubsz.getText() + "\n************************************************************************");
        areasubsz.setText(areasubsz.getText() + "\n            Keep the Receipt for future reference.");
        areasubsz.setText(areasubsz.getText() + "\n            Thank you for your COntribution..!!");
        areasubsz.setText(areasubsz.getText() + "\n************************************************************************");
    }//GEN-LAST:event_jButton15ActionPerformed

    private void setPrintButtonActive1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setPrintButtonActive1ActionPerformed
         try {
            areasubsz.print();
            load_home();
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }                
    }//GEN-LAST:event_setPrintButtonActive1ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
       load_home();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void btnresubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnresubActionPerformed
        displaypanel.removeAll();
        displaypanel.add(Receiptsubsc);
        Receiptsubsc.setVisible(true);
        Receiptsubsc.repaint();
        Receiptsubsc.revalidate(); 
        btnGroup.setVisible(false);        
    }//GEN-LAST:event_btnresubActionPerformed

    private void subprActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subprActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_subprActionPerformed

    private void selectyrsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectyrsActionPerformed
      retrieveyearsel();
      totalyearsmem();
      totalaselectedyrw();
    }//GEN-LAST:event_selectyrsActionPerformed

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
            java.util.logging.Logger.getLogger(TreasurerHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TreasurerHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TreasurerHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TreasurerHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TreasurerHome().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddRegFee;
    private javax.swing.JPanel AddSubscFee;
    private javax.swing.JPanel GenManageFee;
    private javax.swing.JPanel IncomeAndExpenditureReports;
    private javax.swing.JPanel ManageIncomeExps;
    private javax.swing.JPanel ManageOnType;
    private javax.swing.JPanel ManageOnYr;
    private javax.swing.JPanel MembersContrReport;
    private javax.swing.JPanel Receipt;
    private javax.swing.JPanel Receiptsubsc;
    private javax.swing.JPanel RecordIncomeExp;
    private javax.swing.JPanel TreUploadNotices;
    private javax.swing.JPanel TreViewNotices;
    private javax.swing.JPanel ViewBalance;
    private javax.swing.JPanel ViewCalendarActivites;
    private javax.swing.JPanel ViewIndvMembersContr;
    private javax.swing.JPanel ViewMembers;
    private javax.swing.JLabel addusrlabel;
    private javax.swing.JLabel ans;
    private javax.swing.JTextArea area;
    private javax.swing.JTextArea areasubsz;
    private javax.swing.JTextField baloinc;
    private javax.swing.JTextField balsnce;
    private javax.swing.JTextField balsr;
    private javax.swing.JTextField baltotalexp;
    private javax.swing.JTextField baltotalincome;
    private javax.swing.JLabel blrm;
    private javax.swing.JButton btnAddregs;
    private javax.swing.JButton btnAddrooms;
    private javax.swing.JButton btnAddusers;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnClearUser;
    private javax.swing.JButton btnClearUser1;
    private javax.swing.JButton btnCloseMaintainrls;
    private javax.swing.JButton btnCloseNewUser;
    private javax.swing.JButton btnClosefgs;
    private javax.swing.JButton btnCloseprov;
    private javax.swing.JButton btnClosesx;
    private javax.swing.JButton btnDeletefrs;
    private javax.swing.JButton btnGetfin;
    private javax.swing.JPanel btnGroup;
    private javax.swing.JButton btnLogOut;
    private javax.swing.JButton btnReloadyers;
    private javax.swing.JButton btnUpdaterecin;
    private javax.swing.JButton btnUpdateyts;
    private javax.swing.JButton btnaddic;
    private javax.swing.JButton btnbalanceret;
    private javax.swing.JButton btnbalreset1;
    private javax.swing.JButton btnclosebal;
    private javax.swing.JButton btnclosefrd;
    private javax.swing.JButton btnclsmem;
    private javax.swing.JButton btndeleteProv;
    private javax.swing.JButton btndelrecs;
    private javax.swing.JButton btnindccl;
    private javax.swing.JButton btnmanageemp;
    private javax.swing.JButton btnmanagetenants;
    private javax.swing.JButton btnmanageusers;
    private javax.swing.JButton btnresub;
    private javax.swing.JButton btnretrieveprov;
    private javax.swing.JButton btnupdateprov;
    private javax.swing.JButton btnupdates;
    private javax.swing.JTextField clinictots;
    private javax.swing.JButton closeregulations;
    private javax.swing.JButton closerent;
    private javax.swing.JButton closetenant;
    private javax.swing.JButton clric;
    private javax.swing.JButton clsis;
    private javax.swing.JLabel confpasslabel;
    private javax.swing.JButton deletefg;
    private javax.swing.JPanel displaypanel;
    private javax.swing.JTextField dtrinc;
    private javax.swing.JTextField empsearch;
    private javax.swing.JComboBox<String> fictype;
    private javax.swing.JLabel fname;
    private javax.swing.JButton genrent;
    private javax.swing.JButton gentenant;
    private javax.swing.JPanel homepanel;
    private javax.swing.JTable incomeexpensestbl;
    private javax.swing.JTextField indicators;
    private javax.swing.JTextField indvamoutr;
    private javax.swing.JTextField indvname;
    private javax.swing.JTextField indvphones;
    private javax.swing.JTextField indvreg;
    private javax.swing.JTextField indvyers;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton8;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel lblEmpfname;
    private javax.swing.JLabel lblEmplname;
    private javax.swing.JLabel lblEmppno;
    private javax.swing.JLabel lblEmpsal;
    private javax.swing.JLabel lblEusername;
    private javax.swing.JLabel lblEusername1;
    private javax.swing.JLabel lblFilldet;
    private javax.swing.JLabel lblReports1;
    private javax.swing.JLabel lblReports2;
    private javax.swing.JLabel lblReports3;
    private javax.swing.JLabel lblavailablestock;
    private javax.swing.JLabel lblbuyingprice;
    private javax.swing.JLabel lblprodname;
    private javax.swing.JLabel lblread;
    private javax.swing.JLabel lblread2;
    private javax.swing.JLabel lblrent;
    private javax.swing.JLabel lblsellingprice;
    private javax.swing.JLabel lblsellingprice1;
    private javax.swing.JLabel lblsellingprice2;
    private javax.swing.JLabel lblstat;
    private javax.swing.JLabel lbltenant;
    private javax.swing.JLabel lblusername;
    private javax.swing.JLabel lblwritting;
    private javax.swing.JLabel lblwritting1;
    private javax.swing.JLabel lblwritting2;
    private javax.swing.JLabel lbnum;
    private javax.swing.JLabel lname;
    private javax.swing.JLabel loginlabel;
    private javax.swing.JTextField manamtrs;
    private javax.swing.JTextField mannams;
    private javax.swing.JTextField manphone;
    private javax.swing.JTextField manpr;
    private javax.swing.JTextField manreg;
    private javax.swing.JTextField manysd;
    private javax.swing.JTextField mebtr;
    private javax.swing.JTextArea noticetres;
    private javax.swing.JLabel num;
    private javax.swing.JLabel occlabel;
    private javax.swing.JTextField otIncExpsearch;
    private javax.swing.JLabel pass;
    private javax.swing.JLabel passwordlabel;
    private javax.swing.JLabel phonelabel;
    private javax.swing.JLabel plabel;
    private javax.swing.JLabel position;
    private javax.swing.JButton printregulations;
    private javax.swing.JLabel proddetails;
    private javax.swing.JTextField provcont;
    private javax.swing.JTextField provname;
    private javax.swing.JTextField provtype;
    private javax.swing.JTextField rects;
    private javax.swing.JButton registersubbtn;
    private javax.swing.JButton reppayment;
    private javax.swing.JButton reptenant;
    private javax.swing.JButton reptenant1;
    private javax.swing.JButton retrieve;
    private javax.swing.JButton retrievedts;
    private javax.swing.JLabel rolelabel;
    private javax.swing.JLabel roomno;
    private javax.swing.JLabel roomno1;
    private javax.swing.JLabel security;
    private javax.swing.JLabel securitylabel;
    private javax.swing.JTable selectedfinace;
    private javax.swing.JComboBox<String> selecttype;
    private javax.swing.JComboBox<String> selectyrs;
    private javax.swing.JButton setPrintButtonActive;
    private javax.swing.JButton setPrintButtonActive1;
    private javax.swing.JLabel softwareName;
    private javax.swing.JLabel softwaredesc;
    private javax.swing.JLabel softwaredesc1;
    private javax.swing.JLabel softwarename;
    private javax.swing.JLabel softwarename1;
    private javax.swing.JTextField srexpc;
    private javax.swing.JLabel stockmgmt;
    private javax.swing.JTextField subams;
    private javax.swing.JTextField subnm;
    private javax.swing.JTextField subpr;
    private javax.swing.JTextField subps;
    private javax.swing.JTextField subr;
    private javax.swing.JTextField subys;
    private javax.swing.JTable tablefinance;
    private javax.swing.JTable tblfinacialyrs;
    private javax.swing.JTextField tenantsearch;
    private javax.swing.JFormattedTextField tfamt;
    private javax.swing.JTextField tfname;
    private javax.swing.JTextField tfphone;
    private javax.swing.JTextField tfpurpose;
    private javax.swing.JTextField tfreg;
    private javax.swing.JTextField tfyear;
    private javax.swing.JTextField totalamwers;
    private javax.swing.JTextField totalsubrmem;
    private javax.swing.JTextField totaltrem;
    private javax.swing.JTextField totalystd;
    private javax.swing.JTextField totselcy;
    private javax.swing.JTextArea trecalevents;
    private javax.swing.JTextField tresearchmem;
    public javax.swing.JLabel tresusername;
    private javax.swing.JTextField txtamexpta;
    private javax.swing.JTextField txtamrs;
    private javax.swing.JTextField txtnt;
    private javax.swing.JTextField txtpnts;
    private javax.swing.JTextField txtprpt;
    private javax.swing.JTextField txtregst;
    private javax.swing.JTextField txtsmts;
    private javax.swing.JComboBox<String> typetext;
    private javax.swing.JButton updatefv;
    private javax.swing.JButton updroomsd1;
    private javax.swing.JButton updroomsd2;
    private javax.swing.JButton updroomsd3;
    private javax.swing.JButton updroomsd4;
    private javax.swing.JButton updroomsd5;
    private javax.swing.JLabel usernamelabel;
    private javax.swing.JLabel usrmgmt;
    private javax.swing.JButton viewregulations;
    private javax.swing.JButton viewrentdates;
    private javax.swing.JButton viewrules;
    private javax.swing.JTable viewtresMembers;
    private javax.swing.JTextArea vwnoticetre;
    private javax.swing.JTextField yamd;
    private javax.swing.JTextField ynms;
    private javax.swing.JTextField ypones;
    private javax.swing.JTextField yprt;
    private javax.swing.JTextField yreg;
    private javax.swing.JTextField yyst;
    // End of variables declaration//GEN-END:variables

    
    
}
