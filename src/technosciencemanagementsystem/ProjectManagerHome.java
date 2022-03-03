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
public class ProjectManagerHome extends javax.swing.JFrame {

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

    public ProjectManagerHome() {
        initComponents();
        connect();
        load_home();
    }
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms", "root", "");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProjectManagerHome.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProjectManagerHome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void load_home() {
        displaypanel.removeAll();
        displaypanel.add(homepanel);
        homepanel.setVisible(true);
        homepanel.repaint();
        homepanel.revalidate();
    }

    //The below classes are used to retrieve and Delete Data to the Database
    private void retrieveservices() {
        try {
            String sql = "SELECT * FROM clinicservices";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            model = (DefaultTableModel) eventtables.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String id = rs.getString(1);
                String stypes = rs.getString(2);
                String amountsr = rs.getString(3);

                model.addRow(new String[]{id, stypes, amountsr});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public Boolean updateservices(String id, String nametypes, String phnamt) {
        String query = "UPDATE clinicservices SET servtype='" + nametypes + "',Amount='" + phnamt + "' WHERE servid= '" + id + "'";
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

    public Boolean deleteservices(String id) {

        String sql = "DELETE FROM clinicservices WHERE servid ='" + id + "'";
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

    private void filterservices(String query) {
        DefaultTableModel model2 = (DefaultTableModel) eventtables.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model2);
        eventtables.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }

    private void totalservices() {
        try {
            String sql = "SELECT COUNT(servid)totalssert FROM clinicservices";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                int total = Integer.parseInt(rs.getString("totalssert"));
                totalclinc.setText(String.valueOf(total));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void retrieveclinicactivities() {
        try {
            String sql = "SELECT * FROM clinicevents";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            model = (DefaultTableModel) tabletenants.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String clidn = rs.getString(1);
                String hwtrbl = rs.getString(2);
                String winstal = rs.getString(3);
                String dustc = rs.getString(4);
                String softinst = rs.getString(5);
                String antivirusinst = rs.getString(6);
                String winups = rs.getString(7);
                model.addRow(new String[]{clidn, hwtrbl, winstal, dustc, softinst, antivirusinst, winups});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void retrieveprojmem() {
        try {
            String sql = "SELECT * FROM members";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            model = (DefaultTableModel) tableviewmembers.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String midx = rs.getString(1);
                String prreg = rs.getString(2);
                String pnm = rs.getString(3);
                String pcrd = rs.getString(4);
                String pares = rs.getString(5);
                String pyrs = rs.getString(6);
                String phr = rs.getString(7);
                model.addRow(new String[]{midx, prreg, pnm, pcrd, pares, pyrs, phr});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    
    private void filterempproj(String query) {
        DefaultTableModel model3 = (DefaultTableModel) tableviewmembers.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model3);
        tableviewmembers.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }

    private void totalprojmembers() {
        try {
            String sql = "SELECT COUNT(member_id)projmembers FROM members";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                int total = Integer.parseInt(rs.getString("projmembers"));
                totalviewproj.setText(String.valueOf(total));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void retrieverent() {
        try {
            String sql = "SELECT * FROM rent";
            con = DriverManager.getConnection("jdbc:mysql://localhost/rentalmanagementsystem", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            model = (DefaultTableModel) tblrent.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String id = rs.getString(1);
                String phonenumber = rs.getString(2);
                String firstname = rs.getString(3);
                String lastname = rs.getString(4);
                String rm = rs.getString(5);
                String amtpaid = rs.getString(6);
                String arrears = rs.getString(7);
                String paiddate = rs.getString(8);
                model.addRow(new String[]{id, phonenumber, firstname, lastname, rm, amtpaid, arrears, paiddate});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public Boolean updaterent(String id, String phone, String fnam, String lnam, String rms, String amt, String arrear, String mnth) {
        String query = "UPDATE rent SET phonenumber='" + phone + "',firstname='" + fnam + "',lastname='" + lnam + "', roomnumber='" + rms + "', amount_paid='" + amt + "', arrears = '" + arrear + "', monthpaid = '" + mnth + "'  WHERE rent_id = '" + id + "'";
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

    public Boolean deleterent(String id) {
        String sql = "DELETE FROM rent WHERE rent_id ='" + id + "'";
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

    private void filterrent(String query) {
        DefaultTableModel model3 = (DefaultTableModel) tblrent.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model3);
        tblrent.setRowSorter(tr);

        tr.setRowFilter(RowFilter.regexFilter(query));
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

    private void totalrentpaid() {
        try {
            String sql = "SELECT SUM(amount_paid)amts FROM rent ";
            con = DriverManager.getConnection("jdbc:mysql://localhost/rentalmanagementsystem", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                int totalrents = Integer.parseInt(rs.getString("amts"));
                amtr.setText(String.valueOf(totalrents));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void totalarrears() {
        try {
            String sql = "SELECT SUM(arrears)arents FROM rent ";
            con = DriverManager.getConnection("jdbc:mysql://localhost/rentalmanagementsystem", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                int totalars = Integer.parseInt(rs.getString("arents"));
                arar.setText(String.valueOf(totalars));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void totalprices() {
        try {
            String sql = "SELECT SUM(price)prct FROM rental_properties ";
            con = DriverManager.getConnection("jdbc:mysql://localhost/rentalmanagementsystem", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                int totalrentprop = Integer.parseInt(rs.getString("prct"));
                propertytots.setText(String.valueOf(totalrentprop));
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
        DefaultTableModel model2 = (DefaultTableModel) proprents.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model2);
        proprents.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }

    private void viewnoticets() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String sql = "SELECT * FROM `notices`";
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            noticesproj.setText("***********************************************************************\n");
            noticesproj.setText(noticesproj.getText() + " VIEW UPLOADED NOTICES \n");
            noticesproj.setText(noticesproj.getText() + "********************************************************\n");
            noticesproj.setText(noticesproj.getText() + "NO.    NOTICE\n");
            while (rs.next()) {
                String id = rs.getString("notice_id");
                String notes = rs.getString("noticename");
                String date = rs.getString("date_recorded");
                noticesproj.setText("\n" + noticesproj.getText() + id + "    " + notes + " DATE---->> " + date + "\n");
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
        services = new javax.swing.JPanel();
        addusrlabel = new javax.swing.JLabel();
        usernamelabel = new javax.swing.JLabel();
        hardtxt = new javax.swing.JTextField();
        passwordlabel = new javax.swing.JLabel();
        rolelabel = new javax.swing.JLabel();
        confpasslabel = new javax.swing.JLabel();
        phonelabel = new javax.swing.JLabel();
        wintxt = new javax.swing.JTextField();
        securitylabel = new javax.swing.JLabel();
        upstxt = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        swtxt = new javax.swing.JTextField();
        antitxt = new javax.swing.JTextField();
        dustxt = new javax.swing.JTextField();
        manageservices = new javax.swing.JPanel();
        insser = new javax.swing.JButton();
        lblusername = new javax.swing.JLabel();
        txtstr = new javax.swing.JTextField();
        btnUpdatesser = new javax.swing.JButton();
        btnDeletessert = new javax.swing.JButton();
        btnClosessert = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        eventtables = new javax.swing.JTable();
        num = new javax.swing.JLabel();
        txtamrd = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        cmpservices = new javax.swing.JTextField();
        retrieveser = new javax.swing.JButton();
        jLabel56 = new javax.swing.JLabel();
        totalclinc = new javax.swing.JTextField();
        btnUpdatesser1 = new javax.swing.JButton();
        addClinicEvents = new javax.swing.JPanel();
        proddetails = new javax.swing.JLabel();
        fname = new javax.swing.JLabel();
        amtservs = new javax.swing.JTextField();
        plabel = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        saveservices = new javax.swing.JButton();
        servtypes = new javax.swing.JComboBox<>();
        managecomputerclinics = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabletenants = new javax.swing.JTable();
        btnGetTenants = new javax.swing.JButton();
        btnClosetenant = new javax.swing.JButton();
        btnGetTenants1 = new javax.swing.JButton();
        managerooms = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblrooms = new javax.swing.JTable();
        btnroomdata = new javax.swing.JButton();
        buttoncls = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        rttxt = new javax.swing.JTextField();
        btext = new javax.swing.JTextField();
        ocptxt = new javax.swing.JTextField();
        stxt = new javax.swing.JTextField();
        btndel = new javax.swing.JButton();
        btnupdroom = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
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
        ViewMembers = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableviewmembers = new javax.swing.JTable();
        btnReloademp = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        membprojsrch = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        totalviewproj = new javax.swing.JTextField();
        btncloseemp = new javax.swing.JButton();
        btnprinttbl = new javax.swing.JButton();
        manageserviceproviders = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        providerstbl = new javax.swing.JTable();
        btnCloseprov = new javax.swing.JButton();
        btnupdateprov = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        provaddress = new javax.swing.JTextField();
        provname = new javax.swing.JTextField();
        provcont = new javax.swing.JTextField();
        provtype = new javax.swing.JTextField();
        btnretrieveprov = new javax.swing.JButton();
        btndeleteProv = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        providerssearch = new javax.swing.JTextField();
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
        ClinicservicesReport = new javax.swing.JPanel();
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
        calendarreord = new javax.swing.JTextArea();
        jLabel44 = new javax.swing.JLabel();
        savecals = new javax.swing.JButton();
        clscalsw = new javax.swing.JButton();
        retrcal = new javax.swing.JButton();
        ViewRules = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        rlsarea = new javax.swing.JTextArea();
        jLabel45 = new javax.swing.JLabel();
        viewregulations = new javax.swing.JButton();
        printregulations = new javax.swing.JButton();
        closeregulations = new javax.swing.JButton();
        viewcalactivity = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        calprojview = new javax.swing.JTextArea();
        jLabel46 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        retrievedts = new javax.swing.JButton();
        viewnotices = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        noticearea = new javax.swing.JTextArea();
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
        uploadnotices = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        noticesendtxt = new javax.swing.JTextArea();
        jLabel54 = new javax.swing.JLabel();
        saveregulations1 = new javax.swing.JButton();
        clsrules1 = new javax.swing.JButton();
        ViewNotices = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        noticesproj = new javax.swing.JTextArea();
        jLabel55 = new javax.swing.JLabel();
        savfeedb = new javax.swing.JButton();
        clsfeedbc = new javax.swing.JButton();
        loginlabel = new javax.swing.JLabel();
        projsusername = new javax.swing.JLabel();
        btnLogOut = new javax.swing.JButton();
        softwareName = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        btnGroup = new javax.swing.JPanel();
        btnAddstrs = new javax.swing.JButton();
        usrmgmt = new javax.swing.JLabel();
        stockmgmt = new javax.swing.JLabel();
        btnaddemployees = new javax.swing.JButton();
        btnaddserviceprovider = new javax.swing.JButton();
        btnaddrentalproperties = new javax.swing.JButton();
        btnmanagesserts = new javax.swing.JButton();
        btnmanagetenants = new javax.swing.JButton();
        lblReports1 = new javax.swing.JLabel();
        viewrentdates = new javax.swing.JButton();
        viewrentdates1 = new javax.swing.JButton();
        viewrentdates2 = new javax.swing.JButton();

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
        addusrlabel.setText("TECHNO SCIENCE CLINICS SERVICES");

        usernamelabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        usernamelabel.setText("Hardware troubleshooting:");

        hardtxt.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        hardtxt.setText("200");

        passwordlabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        passwordlabel.setText("Dust cleaning:");

        rolelabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        rolelabel.setText("Antivirus installation:");

        confpasslabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        confpasslabel.setText("Software installation:");

        phonelabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        phonelabel.setText("Windows installation:");

        wintxt.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        wintxt.setText("100");

        securitylabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        securitylabel.setText("Windows updation:");

        upstxt.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        upstxt.setText("200");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 255));
        jLabel13.setText("Techno Science Services with there Prices:");

        swtxt.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        swtxt.setText("100");

        antitxt.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        antitxt.setText("150");

        dustxt.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        dustxt.setText("300");

        javax.swing.GroupLayout servicesLayout = new javax.swing.GroupLayout(services);
        services.setLayout(servicesLayout);
        servicesLayout.setHorizontalGroup(
            servicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(servicesLayout.createSequentialGroup()
                .addGroup(servicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(servicesLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(servicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(confpasslabel)
                            .addComponent(phonelabel)
                            .addComponent(securitylabel)
                            .addComponent(usernamelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(passwordlabel)
                            .addComponent(rolelabel))
                        .addGap(17, 17, 17)
                        .addGroup(servicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(wintxt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                            .addComponent(hardtxt, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(upstxt)
                            .addComponent(swtxt, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dustxt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                            .addComponent(antitxt, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(servicesLayout.createSequentialGroup()
                        .addGap(232, 232, 232)
                        .addGroup(servicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(addusrlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(194, Short.MAX_VALUE))
        );
        servicesLayout.setVerticalGroup(
            servicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(servicesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addusrlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addGap(37, 37, 37)
                .addGroup(servicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernamelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hardtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(servicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phonelabel)
                    .addComponent(wintxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(servicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dustxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(servicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confpasslabel)
                    .addComponent(swtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(servicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(servicesLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(rolelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, servicesLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(antitxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(servicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(securitylabel)
                    .addComponent(upstxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(295, Short.MAX_VALUE))
        );

        displaypanel.add(services, "card2");

        manageservices.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Manage Techno Science Computer Clinics", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Engravers MT", 1, 24), new java.awt.Color(0, 0, 255))); // NOI18N

        insser.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        insser.setForeground(new java.awt.Color(0, 0, 255));
        insser.setText("insert");
        insser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insserActionPerformed(evt);
            }
        });

        lblusername.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblusername.setText("Service Type:");

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
        jScrollPane1.setViewportView(eventtables);

        num.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        num.setText("Amount:");

        jLabel39.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel39.setText("Search:");

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

        jLabel56.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(51, 51, 255));
        jLabel56.setText("Total Services:");

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
                .addComponent(jLabel39)
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
                .addComponent(jLabel56)
                .addGap(107, 107, 107)
                .addComponent(totalclinc, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(manageservicesLayout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(manageservicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageservicesLayout.createSequentialGroup()
                        .addComponent(num)
                        .addGap(65, 65, 65)
                        .addComponent(txtamrd, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageservicesLayout.createSequentialGroup()
                        .addComponent(lblusername, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(txtstr, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(79, 79, 79))
        );
        manageservicesLayout.setVerticalGroup(
            manageservicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageservicesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(manageservicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(cmpservices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(manageservicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(manageservicesLayout.createSequentialGroup()
                        .addGroup(manageservicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblusername)
                            .addComponent(txtstr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(167, 167, 167)
                        .addGroup(manageservicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtamrd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(num))))
                .addGap(35, 35, 35)
                .addGroup(manageservicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
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

        addClinicEvents.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "CLINIC FORM", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18), new java.awt.Color(0, 102, 102))); // NOI18N

        proddetails.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        proddetails.setForeground(new java.awt.Color(102, 51, 255));
        proddetails.setText("Enter  Details Below:");

        fname.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        fname.setText("Select Service Type:");

        amtservs.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        plabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        plabel.setText("Amount:");

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

        saveservices.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        saveservices.setForeground(new java.awt.Color(102, 153, 0));
        saveservices.setText("ADD REcord");
        saveservices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveservicesActionPerformed(evt);
            }
        });

        servtypes.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        servtypes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Service Type", "hardware_troubleshooting", "windows_installation", "dust_cleaning", "software_installation", "antivirus_installation", "windows_updation" }));
        servtypes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                servtypesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addClinicEventsLayout = new javax.swing.GroupLayout(addClinicEvents);
        addClinicEvents.setLayout(addClinicEventsLayout);
        addClinicEventsLayout.setHorizontalGroup(
            addClinicEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addClinicEventsLayout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(proddetails, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(355, Short.MAX_VALUE))
            .addGroup(addClinicEventsLayout.createSequentialGroup()
                .addGroup(addClinicEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addClinicEventsLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(addClinicEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(plabel, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(addClinicEventsLayout.createSequentialGroup()
                                .addComponent(saveservices)
                                .addGap(56, 56, 56)
                                .addComponent(btnClear)
                                .addGap(66, 66, 66)
                                .addComponent(btnClose))))
                    .addGroup(addClinicEventsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(fname, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(80, 80, 80)
                        .addGroup(addClinicEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(amtservs, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(servtypes, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        addClinicEventsLayout.setVerticalGroup(
            addClinicEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addClinicEventsLayout.createSequentialGroup()
                .addGroup(addClinicEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(addClinicEventsLayout.createSequentialGroup()
                        .addComponent(proddetails, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(addClinicEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fname)
                            .addComponent(servtypes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(amtservs, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(plabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(88, 88, 88)
                .addGroup(addClinicEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveservices)
                    .addComponent(btnClear)
                    .addComponent(btnClose))
                .addGap(0, 434, Short.MAX_VALUE))
        );

        displaypanel.add(addClinicEvents, "card5");

        managecomputerclinics.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "VIEW COMPUTER CLINICS SERVICES OFFERED AND AMOUNT COLLECTED", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N

        tabletenants.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Service id", "Hardware Installation", "Windows Installation", "Dust Cleaning", "Software Installation", "AntiVirus installation", "Windows Update"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabletenants.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabletenantsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabletenants);

        btnGetTenants.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnGetTenants.setForeground(new java.awt.Color(102, 0, 255));
        btnGetTenants.setText("Retrieve Data");
        btnGetTenants.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetTenantsActionPerformed(evt);
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

        btnGetTenants1.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnGetTenants1.setForeground(new java.awt.Color(102, 0, 255));
        btnGetTenants1.setText("Print table");
        btnGetTenants1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetTenants1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout managecomputerclinicsLayout = new javax.swing.GroupLayout(managecomputerclinics);
        managecomputerclinics.setLayout(managecomputerclinicsLayout);
        managecomputerclinicsLayout.setHorizontalGroup(
            managecomputerclinicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(managecomputerclinicsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(managecomputerclinicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(managecomputerclinicsLayout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addGroup(managecomputerclinicsLayout.createSequentialGroup()
                        .addComponent(btnGetTenants)
                        .addGap(123, 123, 123)
                        .addComponent(btnGetTenants1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 196, Short.MAX_VALUE)
                        .addComponent(btnClosetenant)
                        .addGap(98, 98, 98))))
        );
        managecomputerclinicsLayout.setVerticalGroup(
            managecomputerclinicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, managecomputerclinicsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(managecomputerclinicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGetTenants)
                    .addComponent(btnClosetenant)
                    .addComponent(btnGetTenants1))
                .addContainerGap(408, Short.MAX_VALUE))
        );

        displaypanel.add(managecomputerclinics, "card6");

        managerooms.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "MANAGE ROOMS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18), new java.awt.Color(153, 153, 0))); // NOI18N
        managerooms.setPreferredSize(new java.awt.Dimension(525, 0));

        tblrooms.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Room Number", "Room Type", "Block Number", "Status", "Occupant Phone"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblrooms.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblroomsMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblrooms);

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

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel23.setText("Status:");

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel24.setText("Room Type:");

        jLabel25.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel25.setText("Occ Phonenumber:");

        jLabel26.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel26.setText("Block Number:");

        rttxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        btext.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        ocptxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        stxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        btndel.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btndel.setText("Delete data");
        btndel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndelActionPerformed(evt);
            }
        });

        btnupdroom.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnupdroom.setText("Update details");
        btnupdroom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdroomActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Yu Gothic Medium", 1, 18)); // NOI18N
        jLabel27.setText("SEARCH");

        jTextField8.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jTextField8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField8KeyReleased(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 51, 255));
        jLabel41.setText("Total Rooms:");

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

        javax.swing.GroupLayout manageroomsLayout = new javax.swing.GroupLayout(managerooms);
        managerooms.setLayout(manageroomsLayout);
        manageroomsLayout.setHorizontalGroup(
            manageroomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageroomsLayout.createSequentialGroup()
                .addComponent(jLabel27)
                .addGap(42, 42, 42)
                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(manageroomsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(manageroomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manageroomsLayout.createSequentialGroup()
                        .addGroup(manageroomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(manageroomsLayout.createSequentialGroup()
                                .addComponent(btnroomdata)
                                .addGap(27, 27, 27)
                                .addComponent(btnupdroom)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageroomsLayout.createSequentialGroup()
                                .addComponent(btndel)
                                .addGap(34, 34, 34)))
                        .addComponent(buttoncls)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(manageroomsLayout.createSequentialGroup()
                        .addComponent(jLabel41)
                        .addGap(43, 43, 43)
                        .addComponent(totalrms, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(manageroomsLayout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(manageroomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jLabel24)
                            .addComponent(jLabel23)
                            .addComponent(jLabel25))
                        .addGroup(manageroomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageroomsLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rttxt, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38))
                            .addGroup(manageroomsLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(manageroomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btext, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(stxt, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ocptxt, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
            .addGroup(manageroomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(manageroomsLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(managerent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        manageroomsLayout.setVerticalGroup(
            manageroomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageroomsLayout.createSequentialGroup()
                .addGroup(manageroomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(manageroomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(manageroomsLayout.createSequentialGroup()
                        .addGroup(manageroomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(rttxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(manageroomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(ocptxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(manageroomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(btext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(manageroomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(stxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(manageroomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel41)
                    .addComponent(totalrms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageroomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnroomdata)
                    .addComponent(btnupdroom)
                    .addComponent(btndel)
                    .addComponent(buttoncls))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(manageroomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(manageroomsLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(managerent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        displaypanel.add(managerooms, "card10");

        ViewMembers.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.MatteBorder(null), "VIEW TECHNO SCIENCE MEMBERS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18), new java.awt.Color(102, 0, 204))); // NOI18N
        ViewMembers.setPreferredSize(new java.awt.Dimension(520, 0));

        tableviewmembers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Members ID", "Reg NO.", "Names", "Phonenumber", "Course", "Area of Specialization", "Year And Semester"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableviewmembers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableviewmembersMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tableviewmembers);

        btnReloademp.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnReloademp.setForeground(new java.awt.Color(255, 102, 204));
        btnReloademp.setText("Retrieve Data");
        btnReloademp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadempActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        jLabel22.setText("search");

        membprojsrch.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        membprojsrch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                membprojsrchKeyReleased(evt);
            }
        });

        jLabel40.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(0, 153, 255));
        jLabel40.setText("Total Members: ");

        totalviewproj.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        btncloseemp.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btncloseemp.setForeground(new java.awt.Color(0, 0, 102));
        btncloseemp.setText("close");
        btncloseemp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncloseempActionPerformed(evt);
            }
        });

        btnprinttbl.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnprinttbl.setForeground(new java.awt.Color(0, 102, 102));
        btnprinttbl.setText("PRINT MEMBERS");
        btnprinttbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprinttblActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ViewMembersLayout = new javax.swing.GroupLayout(ViewMembers);
        ViewMembers.setLayout(ViewMembersLayout);
        ViewMembersLayout.setHorizontalGroup(
            ViewMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewMembersLayout.createSequentialGroup()
                .addGroup(ViewMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ViewMembersLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel22)
                        .addGap(54, 54, 54)
                        .addComponent(membprojsrch, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ViewMembersLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(ViewMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ViewMembersLayout.createSequentialGroup()
                                .addComponent(jLabel40)
                                .addGap(40, 40, 40)
                                .addComponent(totalviewproj, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ViewMembersLayout.createSequentialGroup()
                                .addComponent(btnReloademp)
                                .addGap(111, 111, 111)
                                .addComponent(btnprinttbl)
                                .addGap(164, 164, 164)
                                .addComponent(btncloseemp)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ViewMembersLayout.createSequentialGroup()
                .addComponent(jScrollPane5)
                .addContainerGap())
        );
        ViewMembersLayout.setVerticalGroup(
            ViewMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewMembersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ViewMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(membprojsrch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(ViewMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(totalviewproj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(ViewMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReloademp)
                    .addComponent(btncloseemp)
                    .addComponent(btnprinttbl))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        displaypanel.add(ViewMembers, "card8");

        manageserviceproviders.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "MANAGE LOCAL SERVICE PROVIDERS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N
        manageserviceproviders.setPreferredSize(new java.awt.Dimension(525, 0));

        providerstbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Company ID", "Company Name", "Contact", "Service type", "Address"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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

        btnupdateprov.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        btnupdateprov.setText("UpdATE dETAILS");
        btnupdateprov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateprovActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel28.setText("Company Name:");

        jLabel29.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel29.setText("Service Type:");

        jLabel30.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel30.setText("Contact:");

        jLabel31.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel31.setText("Address:");

        provaddress.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

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

        providerssearch.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        providerssearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                providerssearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout manageserviceprovidersLayout = new javax.swing.GroupLayout(manageserviceproviders);
        manageserviceproviders.setLayout(manageserviceprovidersLayout);
        manageserviceprovidersLayout.setHorizontalGroup(
            manageserviceprovidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageserviceprovidersLayout.createSequentialGroup()
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(providerssearch, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(manageserviceprovidersLayout.createSequentialGroup()
                .addGroup(manageserviceprovidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manageserviceprovidersLayout.createSequentialGroup()
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(manageserviceprovidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(manageserviceprovidersLayout.createSequentialGroup()
                        .addComponent(btnretrieveprov)
                        .addGap(53, 53, 53)
                        .addComponent(btnupdateprov)
                        .addGap(33, 33, 33)
                        .addComponent(btndeleteProv)))
                .addGroup(manageserviceprovidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manageserviceprovidersLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCloseprov))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageserviceprovidersLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(manageserviceprovidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(provaddress, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                            .addComponent(provtype, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                            .addComponent(provcont, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(provname))
                        .addContainerGap())))
        );
        manageserviceprovidersLayout.setVerticalGroup(
            manageserviceprovidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageserviceprovidersLayout.createSequentialGroup()
                .addGroup(manageserviceprovidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(providerssearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(manageserviceprovidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manageserviceprovidersLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(manageserviceprovidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(provname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(manageserviceprovidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(provcont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(manageserviceprovidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(provtype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(manageserviceprovidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(provaddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(manageserviceprovidersLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(33, 33, 33)
                .addGroup(manageserviceprovidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnretrieveprov)
                    .addComponent(btnupdateprov)
                    .addComponent(btndeleteProv)
                    .addComponent(btnCloseprov))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        displaypanel.add(manageserviceproviders, "card12");

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
                .addContainerGap(314, Short.MAX_VALUE))
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
                .addContainerGap(334, Short.MAX_VALUE))
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
                .addContainerGap(403, Short.MAX_VALUE))
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
                .addContainerGap(306, Short.MAX_VALUE))
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
                .addContainerGap(222, Short.MAX_VALUE))
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
                .addContainerGap(279, Short.MAX_VALUE))
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
                .addContainerGap(12, Short.MAX_VALUE))
        );

        displaypanel.add(AddserviceProviders, "card15");

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

        ClinicservicesReport.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "TENANT REPORT GENERATION", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18))); // NOI18N
        ClinicservicesReport.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        gentenant.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        gentenant.setForeground(new java.awt.Color(102, 102, 255));
        gentenant.setText("Report");
        gentenant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gentenantActionPerformed(evt);
            }
        });
        ClinicservicesReport.add(gentenant, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        closetenant.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        closetenant.setForeground(new java.awt.Color(51, 51, 0));
        closetenant.setText("CLOSE");
        closetenant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closetenantActionPerformed(evt);
            }
        });
        ClinicservicesReport.add(closetenant, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 210, -1, -1));

        lbltenant.setBackground(new java.awt.Color(255, 255, 204));
        lbltenant.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbltenant.setForeground(new java.awt.Color(0, 0, 255));
        lbltenant.setText("Click once button Report to generate Computer Clinics Services report");
        ClinicservicesReport.add(lbltenant, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 580, 40));

        lblread2.setBackground(new java.awt.Color(255, 255, 204));
        lblread2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblread2.setForeground(new java.awt.Color(0, 0, 255));
        lblread2.setText("Report is used to quickly analyse data and can be generated periodicaly");
        ClinicservicesReport.add(lblread2, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 88, -1, 34));

        displaypanel.add(ClinicservicesReport, "card19");

        homepanel.setBackground(new java.awt.Color(51, 255, 204));

        softwarename.setFont(new java.awt.Font("Perpetua Titling MT", 1, 24)); // NOI18N
        softwarename.setText("WELCOME TO TECHNOSCIENCE MANAGEMENT SYSTEM");

        softwaredesc.setFont(new java.awt.Font("Tempus Sans ITC", 3, 24)); // NOI18N
        softwaredesc.setText("It's not that we use Technology, we live Technology");

        softwarename1.setFont(new java.awt.Font("Perpetua Titling MT", 1, 24)); // NOI18N
        softwarename1.setText("PROJECT MANAGER DASHBOARD");

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
                .addContainerGap(124, Short.MAX_VALUE))
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
                .addContainerGap(371, Short.MAX_VALUE))
        );

        displaypanel.add(homepanel, "card4");

        CalendarActivities.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TECHNOSCIENCE CALENDAR OF ACTIVITIES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18), new java.awt.Color(0, 0, 255))); // NOI18N

        calendarreord.setColumns(20);
        calendarreord.setRows(5);
        jScrollPane11.setViewportView(calendarreord);

        jLabel44.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel44.setText("Click Retrieve to First view Existing rules and incase of Change made Click Save");

        savecals.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        savecals.setForeground(new java.awt.Color(0, 0, 102));
        savecals.setText("SAVE");
        savecals.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savecalsActionPerformed(evt);
            }
        });

        clscalsw.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        clscalsw.setForeground(new java.awt.Color(0, 51, 102));
        clscalsw.setText("CLOSE");
        clscalsw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clscalswActionPerformed(evt);
            }
        });

        retrcal.setFont(new java.awt.Font("Engravers MT", 1, 14)); // NOI18N
        retrcal.setForeground(new java.awt.Color(0, 0, 102));
        retrcal.setText("Retrieve");
        retrcal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retrcalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CalendarActivitiesLayout = new javax.swing.GroupLayout(CalendarActivities);
        CalendarActivities.setLayout(CalendarActivitiesLayout);
        CalendarActivitiesLayout.setHorizontalGroup(
            CalendarActivitiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CalendarActivitiesLayout.createSequentialGroup()
                .addGroup(CalendarActivitiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CalendarActivitiesLayout.createSequentialGroup()
                        .addComponent(retrcal)
                        .addGap(60, 60, 60)
                        .addComponent(savecals)
                        .addGap(116, 116, 116)
                        .addComponent(clscalsw))
                    .addGroup(CalendarActivitiesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel44))
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        CalendarActivitiesLayout.setVerticalGroup(
            CalendarActivitiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CalendarActivitiesLayout.createSequentialGroup()
                .addComponent(jLabel44)
                .addGap(17, 17, 17)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(CalendarActivitiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(savecals)
                    .addComponent(clscalsw)
                    .addComponent(retrcal))
                .addContainerGap(281, Short.MAX_VALUE))
        );

        displaypanel.add(CalendarActivities, "card20");

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
                .addContainerGap(287, Short.MAX_VALUE))
        );

        displaypanel.add(ViewRules, "card21");

        viewcalactivity.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "VIEW TECHNO SCIENCECALENDAR OF ACTIVITIES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Engravers MT", 1, 18))); // NOI18N
        viewcalactivity.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        calprojview.setColumns(20);
        calprojview.setRows(5);
        jScrollPane13.setViewportView(calprojview);

        viewcalactivity.add(jScrollPane13, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 52, 630, 301));

        jLabel46.setFont(new java.awt.Font("Eras Demi ITC", 1, 18)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(0, 51, 0));
        jLabel46.setText("Click Retrieve button to view Calendar of Activities ");
        viewcalactivity.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 24, -1, -1));

        jButton11.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jButton11.setText("close");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        viewcalactivity.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 360, -1, -1));

        retrievedts.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        retrievedts.setText("Retrieve");
        retrievedts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retrievedtsActionPerformed(evt);
            }
        });
        viewcalactivity.add(retrievedts, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, -1, -1));

        displaypanel.add(viewcalactivity, "card22");

        viewnotices.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "View notices", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Engravers MT", 1, 18), new java.awt.Color(102, 153, 0))); // NOI18N

        noticearea.setEditable(false);
        noticearea.setColumns(20);
        noticearea.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        noticearea.setLineWrap(true);
        noticearea.setRows(5);
        jScrollPane14.setViewportView(noticearea);

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
        jLabel47.setText("Click button View to Display the Upoaded Notices");

        javax.swing.GroupLayout viewnoticesLayout = new javax.swing.GroupLayout(viewnotices);
        viewnotices.setLayout(viewnoticesLayout);
        viewnoticesLayout.setHorizontalGroup(
            viewnoticesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewnoticesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(viewnoticesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel47)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(viewnoticesLayout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addGap(66, 66, 66)
                        .addComponent(jButton9)
                        .addGap(118, 118, 118)
                        .addComponent(jButton10)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        viewnoticesLayout.setVerticalGroup(
            viewnoticesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewnoticesLayout.createSequentialGroup()
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(viewnoticesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton9)
                    .addComponent(jButton10))
                .addContainerGap(261, Short.MAX_VALUE))
        );

        displaypanel.add(viewnotices, "card23");

        Receipt.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "TECHNO SCIENCE RECEIPT", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Perpetua Titling MT", 1, 18), new java.awt.Color(0, 51, 51))); // NOI18N

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
                .addContainerGap(318, Short.MAX_VALUE))
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
                .addContainerGap(330, Short.MAX_VALUE))
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

        uploadnotices.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Upload Notice", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18), new java.awt.Color(0, 0, 255))); // NOI18N

        noticesendtxt.setColumns(20);
        noticesendtxt.setRows(5);
        jScrollPane17.setViewportView(noticesendtxt);

        jLabel54.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel54.setText("In the below File record notice and Click Button upload to save ");

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

        javax.swing.GroupLayout uploadnoticesLayout = new javax.swing.GroupLayout(uploadnotices);
        uploadnotices.setLayout(uploadnoticesLayout);
        uploadnoticesLayout.setHorizontalGroup(
            uploadnoticesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uploadnoticesLayout.createSequentialGroup()
                .addGroup(uploadnoticesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel54)
                    .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 12, Short.MAX_VALUE))
            .addGroup(uploadnoticesLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(saveregulations1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(clsrules1)
                .addGap(32, 32, 32))
        );
        uploadnoticesLayout.setVerticalGroup(
            uploadnoticesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uploadnoticesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel54)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(uploadnoticesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saveregulations1)
                    .addComponent(clsrules1))
                .addContainerGap(279, Short.MAX_VALUE))
        );

        displaypanel.add(uploadnotices, "card20");

        ViewNotices.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "FEEDBACK  BASED ON COMPLAINS AND REQUESTS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Elephant", 1, 18), new java.awt.Color(0, 0, 255))); // NOI18N

        noticesproj.setColumns(20);
        noticesproj.setRows(5);
        jScrollPane18.setViewportView(noticesproj);

        jLabel55.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel55.setText("In the below File you type the Rent Payment Details and Click Button Save ");

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
                .addGap(0, 223, Short.MAX_VALUE))
            .addGroup(ViewNoticesLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(savfeedb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(clsfeedbc)
                .addGap(262, 262, 262))
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

        loginlabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        loginlabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        loginlabel.setText("Logged in as:");

        projsusername.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        projsusername.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        projsusername.setText("myusername");

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

        btnAddstrs.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnAddstrs.setText("Computer Clinic services");
        btnAddstrs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddstrsActionPerformed(evt);
            }
        });

        usrmgmt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        usrmgmt.setForeground(new java.awt.Color(0, 255, 255));
        usrmgmt.setText("Recording Actions");

        stockmgmt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        stockmgmt.setForeground(new java.awt.Color(0, 255, 0));
        stockmgmt.setText("Manage Actions");

        btnaddemployees.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnaddemployees.setText("Computer Clinic Event");
        btnaddemployees.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddemployeesActionPerformed(evt);
            }
        });

        btnaddserviceprovider.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnaddserviceprovider.setText("Calendar Of Activities");
        btnaddserviceprovider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddserviceproviderActionPerformed(evt);
            }
        });

        btnaddrentalproperties.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnaddrentalproperties.setText("Upload Notice");
        btnaddrentalproperties.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddrentalpropertiesActionPerformed(evt);
            }
        });

        btnmanagesserts.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnmanagesserts.setText(" Computer Clinics Services");
        btnmanagesserts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmanagessertsActionPerformed(evt);
            }
        });

        btnmanagetenants.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnmanagetenants.setText("Computer Clinics Events");
        btnmanagetenants.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmanagetenantsActionPerformed(evt);
            }
        });

        lblReports1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblReports1.setForeground(new java.awt.Color(51, 51, 255));
        lblReports1.setText("View Actions");

        viewrentdates.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        viewrentdates.setText("Calendar of Activites");
        viewrentdates.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewrentdatesActionPerformed(evt);
            }
        });

        viewrentdates1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        viewrentdates1.setText("View Notices");
        viewrentdates1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewrentdates1ActionPerformed(evt);
            }
        });

        viewrentdates2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        viewrentdates2.setText("Club Members");
        viewrentdates2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewrentdates2ActionPerformed(evt);
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
                        .addGroup(btnGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usrmgmt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAddstrs, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(23, 23, 23))
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnaddemployees)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnGroupLayout.createSequentialGroup()
                        .addComponent(btnaddserviceprovider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(viewrentdates2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(btnGroupLayout.createSequentialGroup()
                .addGroup(btnGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnmanagesserts)
                    .addComponent(btnmanagetenants)
                    .addComponent(viewrentdates)
                    .addGroup(btnGroupLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(btnGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnaddrentalproperties)
                            .addComponent(stockmgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblReports1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(viewrentdates1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        btnGroupLayout.setVerticalGroup(
            btnGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnGroupLayout.createSequentialGroup()
                .addComponent(usrmgmt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddstrs)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnaddemployees)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnaddserviceprovider)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnaddrentalproperties)
                .addGap(18, 18, 18)
                .addComponent(stockmgmt)
                .addGap(19, 19, 19)
                .addComponent(btnmanagesserts)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnmanagetenants)
                .addGap(18, 18, 18)
                .addComponent(lblReports1)
                .addGap(18, 18, 18)
                .addComponent(viewrentdates2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(viewrentdates1)
                .addGap(13, 13, 13)
                .addComponent(viewrentdates)
                .addContainerGap(474, Short.MAX_VALUE))
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
                        .addGap(0, 69, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(softwareName, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89)
                        .addComponent(loginlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(137, 137, 137)
                        .addComponent(projsusername, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(projsusername)
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

    private void eventtablesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eventtablesMouseClicked
        String name = eventtables.getValueAt(eventtables.getSelectedRow(), 1).toString();
        String pno = eventtables.getValueAt(eventtables.getSelectedRow(), 2).toString();

        txtstr.setText(name);
        txtamrd.setText(pno);


    }//GEN-LAST:event_eventtablesMouseClicked

    private void insserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insserActionPerformed
        try {
            String query = "INSERT INTO clinicservices (servtype,Amount) VALUES(?,?)";
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(query);
            pst.setString(1, txtstr.getText());
            pst.setString(2, txtamrd.getText());

            if (txtamrd.getText().isEmpty() || txtstr.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Some fields are empty..!!\n Please fill them to continue");
            } else {
                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, "Computer Clinic Data Recorded Successifully");
                txtstr.setText("");
                txtamrd.setText("");
                retrieveservices();
                totalservices();
            }

        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_insserActionPerformed

    private void btnUpdatesserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdatesserActionPerformed
        try {
            if (txtstr.getText().isEmpty() || txtamrd.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No row is selected..!!\nPlease select a row to update...");
            } else {
                int index = eventtables.getSelectedRow();
                String id = eventtables.getValueAt(index, 0).toString();  //index=rownumber and 0 represents first column..

                if (updateservices(id, txtstr.getText(), txtamrd.getText())) {
                    JOptionPane.showMessageDialog(null, "Computer Clinics Data Updated Successfully");
                    txtstr.setText("");
                    txtamrd.setText("");
                    retrieveservices();
                    totalservices();
                } else {
                    JOptionPane.showMessageDialog(null, "Computer Clinic Data is Not Updated ");
                }
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btnUpdatesserActionPerformed

    private void btnDeletessertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletessertActionPerformed
        try {
            String[] options = {"Yes", "No"};
            int answer = JOptionPane.showOptionDialog(null, " Are you Sure to delete ??", "Delete Options", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

            if (answer == 0) {
                String id = eventtables.getValueAt(eventtables.getSelectedRow(), 0).toString();
                if (deleteservices(id)) {
                    JOptionPane.showMessageDialog(null, "Computer Services Data Deleted Successfully");
                    txtstr.setText("");
                    txtamrd.setText("");
                    retrieveservices();
                    totalservices();
                } else {
                    JOptionPane.showMessageDialog(null, " Computer Services Data is Not Deleted ");
                }
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_btnDeletessertActionPerformed

    private void btnClosessertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClosessertActionPerformed
        load_home();
    }//GEN-LAST:event_btnClosessertActionPerformed

    private void btnAddstrsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddstrsActionPerformed
        displaypanel.removeAll();
        displaypanel.add(manageservices);
        manageservices.setVisible(true);
        manageservices.repaint();
        manageservices.revalidate();
    }//GEN-LAST:event_btnAddstrsActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        amtservs.setText(null);
        servtypes.setSelectedIndex(0);
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        load_home();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnGetTenantsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetTenantsActionPerformed
        retrieveclinicactivities();
        
    }//GEN-LAST:event_btnGetTenantsActionPerformed

    private void btnaddemployeesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddemployeesActionPerformed
        displaypanel.removeAll();
        displaypanel.add(addClinicEvents);
        addClinicEvents.setVisible(true);
        addClinicEvents.repaint();
        addClinicEvents.revalidate();
    }//GEN-LAST:event_btnaddemployeesActionPerformed

    private void tabletenantsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabletenantsMouseClicked
        
    }//GEN-LAST:event_tabletenantsMouseClicked

    private void btnClosetenantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClosetenantActionPerformed
        load_home();
    }//GEN-LAST:event_btnClosetenantActionPerformed

    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogOutActionPerformed
        int dialog_button = JOptionPane.YES_NO_OPTION;
        int logoutrequest = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Log Out", dialog_button);
        if (logoutrequest == 0) {
            this.dispose();
            LoginForm lo = new LoginForm();
            lo.setVisible(true);
        } else {
        }
    }//GEN-LAST:event_btnLogOutActionPerformed

    private void btnaddserviceproviderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddserviceproviderActionPerformed
        displaypanel.removeAll();
        displaypanel.add(CalendarActivities);
        CalendarActivities.setVisible(true);
        CalendarActivities.repaint();
        CalendarActivities.revalidate();
    }//GEN-LAST:event_btnaddserviceproviderActionPerformed

    private void buttonclsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonclsActionPerformed
        load_home();
    }//GEN-LAST:event_buttonclsActionPerformed

    private void btnCloserangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloserangeActionPerformed
        load_home();
    }//GEN-LAST:event_btnCloserangeActionPerformed

    private void btnCloseprovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseprovActionPerformed
        load_home();
    }//GEN-LAST:event_btnCloseprovActionPerformed

    private void btnClosermActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClosermActionPerformed
        load_home();
    }//GEN-LAST:event_btnClosermActionPerformed

    private void saveservicesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveservicesActionPerformed
        updateCount();
    }//GEN-LAST:event_saveservicesActionPerformed

    private void updateCount() {
        try {
            String column = servtypes.getSelectedItem().toString();
            String sql = "Select * from clinicevents where clid ='service_count'";
            
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
              int initialCount = Integer.parseInt(rs.getString(column));
              int newCount = initialCount +1;
              String sql1 = "UPDATE clinicevents SET "+column+" = "+newCount+" where clid ='service_count'";
              pst = con.prepareStatement(sql1);
              pst.executeUpdate();
              updateAmount();
              JOptionPane.showMessageDialog(null, "Action Successiful..");
              amtservs.setText(null);
              servtypes.setSelectedIndex(0);
            }
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error Occured..!!");
        }
    }

    private void updateAmount() {
        try {
            String column = servtypes.getSelectedItem().toString();
            String sql = "Select * from clinicevents where clid ='Amount'";
            
            con = DriverManager.getConnection("jdbc:mysql://localhost/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
              int initialAmount = Integer.parseInt(rs.getString(column));
              int newAmount = initialAmount +Integer.parseInt(amtservs.getText()) ;
              String sql1 = "UPDATE clinicevents SET "+column+" = "+newAmount+" where clid ='Amount'";
              pst= con.prepareStatement(sql1);
              pst.executeUpdate();
              
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Occured..!!");
        }
    }

    private void btnReloadempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadempActionPerformed
        retrieveprojmem();
        totalprojmembers();
    }//GEN-LAST:event_btnReloadempActionPerformed

    private void tableviewmembersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableviewmembersMouseClicked
        
    }//GEN-LAST:event_tableviewmembersMouseClicked

    private void btnaddrentalpropertiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddrentalpropertiesActionPerformed
        displaypanel.removeAll();
        displaypanel.add(uploadnotices);
        uploadnotices.setVisible(true);
        uploadnotices.repaint();
        uploadnotices.revalidate();
    }//GEN-LAST:event_btnaddrentalpropertiesActionPerformed

    private void btnmanagessertsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmanagessertsActionPerformed
        displaypanel.removeAll();
        displaypanel.add(manageservices);
        manageservices.setVisible(true);
        manageservices.repaint();
        manageservices.revalidate();
    }//GEN-LAST:event_btnmanagessertsActionPerformed

    private void btnmanagetenantsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmanagetenantsActionPerformed
        displaypanel.removeAll();
        displaypanel.add(managecomputerclinics);
        managecomputerclinics.setVisible(true);
        managecomputerclinics.repaint();
        managecomputerclinics.revalidate();
    }//GEN-LAST:event_btnmanagetenantsActionPerformed

    private void btnaddrmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddrmActionPerformed
        try {
            String sql = " INSERT INTO room (room_type, block_number, status, phonenumber)  VALUES (?,?,?,?)";
            con = DriverManager.getConnection("jdbc:mysql://localhost/rentalmanagementsystem", "root", "");
            pst = con.prepareStatement(sql);
            pst.setString(1, rmtype.getSelectedItem().toString());
            pst.setString(2, txtnum.getText());
            pst.setString(3, txtstat.getSelectedItem().toString());
            pst.setString(4, txtnumber.getText());

            if (rmtype.getSelectedIndex() == 0 || txtnum.getText().equals("") || txtstat.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(null, "Some fields not filled in..!!\n Please Fill them to continue.", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Recording of Rooms is Successful");
                rmtype.setSelectedIndex(0);
                txtnum.setText(" ");
                txtstat.setSelectedIndex(0);
                txtnumber.setText("");
            }
        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_btnaddrmActionPerformed

    private void btnClearrmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearrmActionPerformed
        rmtype.setSelectedIndex(0);
        txtnum.setText(" ");
        txtstat.setSelectedIndex(0);
        txtnumber.setText("");
    }//GEN-LAST:event_btnClearrmActionPerformed

    private void btnupdroomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdroomActionPerformed
    
    }//GEN-LAST:event_btnupdroomActionPerformed

    private void btndelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndelActionPerformed
        
    }//GEN-LAST:event_btndelActionPerformed

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
//        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//            try {
//                String txtphone = notxt.getText();
//                pst = con.prepareStatement("Select * from tenants where phonenumber = ?");
//                pst.setString(1, txtphone);
//                rs = pst.executeQuery();
//                if (rs.next() == false) {
//                    JOptionPane.showMessageDialog(this, "Tenant Phonenumber is not found");
//                } else {
//                    String fnname = rs.getString("firstname");
//                    ntxt.setText(fnname.trim());
//                    String lnname = rs.getString("lastname");
//                    ltxt.setText(lnname.trim());
//                    String rmnos = rs.getString("roomnumber");
//                    rmtyt.setText(rmnos.trim());
//                    paytxt.requestFocus();
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(ProjectManagerHome.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }//GEN-LAST:event_notxtKeyPressed

    private void paybtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paybtnActionPerformed
//        try {
//            String sql = " INSERT INTO rent (phonenumber, firstname, lastName, roomnumber, amount_paid, arrears, date_paid )  VALUES (?,?,?,?,?,?,?)";
//            con = DriverManager.getConnection("jdbc:mysql://localhost/rentalmanagementsystem", "root", "");
//            pst = con.prepareStatement(sql);
//            pst.setString(1, notxt.getText());
//            pst.setString(2, ntxt.getText());
//            pst.setString(3, ltxt.getText());
//            pst.setString(4, rmtyt.getText());
//            pst.setString(5, paytxt.getText());
//            pst.setString(6, arrtxt.getText());
//            if (notxt.getText().equals("") || ntxt.getText().equals("") || ltxt.getText().equals("") || paytxt.getText().equals("") || arrtxt.getText().equals("") || rmtyt.getText().equals("")) {
//                JOptionPane.showMessageDialog(null, "Some fields not filled in..!!\n Please Fill them to continue.", "Warning", JOptionPane.WARNING_MESSAGE);
//            } else {
//                pst.executeUpdate();
//                JOptionPane.showMessageDialog(null, "Recording of Payment is Successful");
//                notxt.setText(" ");
//                ntxt.setText(" ");
//                ltxt.setText("");
//                paytxt.setText("");
//                arrtxt.setText("");
//                rmtyt.setText("");
//                this.dispose();
//            }
//        } catch (HeadlessException | SQLException ex) {
//            JOptionPane.showMessageDialog(null, ex);
//        }
    }//GEN-LAST:event_paybtnActionPerformed

    private void rbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnActionPerformed
//        notxt.setText(" ");
//        ntxt.setText(" ");
//        ltxt.setText("");
//        paytxt.setText("");
//        arrtxt.setText("");
//        rmtyt.setText("");
    }//GEN-LAST:event_rbtnActionPerformed

    private void exitbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitbtnActionPerformed
        load_home();
    }//GEN-LAST:event_exitbtnActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        load_home();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void recordproviderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recordproviderActionPerformed
        try {
            String sql = " INSERT INTO service_providers (company_name, contacts, service_type, address ) VALUES (?,?,?,?)";
            con = DriverManager.getConnection("jdbc:mysql://localhost/rentalmanagementsystem", "root", "");
            pst = con.prepareStatement(sql);
            pst.setString(1, comptxt.getText());
            pst.setString(2, context.getText());
            pst.setString(3, servtxt.getSelectedItem().toString());
            pst.setString(4, address.getText());
            if (comptxt.getText().equals("") || context.getText().equals("") || servtxt.getSelectedIndex() == 0 || address.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Some fields not filled in..!!\n Please Fill them to continue.", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Recording of Other Service Providers is Successful");
                comptxt.setText(" ");
                context.setText(" ");
                servtxt.setSelectedIndex(0);
                address.setText("");
            }
        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_recordproviderActionPerformed

    private void provresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_provresetActionPerformed
        comptxt.setText(" ");
        context.setText(" ");
        servtxt.setSelectedIndex(0);
        address.setText("");
    }//GEN-LAST:event_provresetActionPerformed

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

    private void cmpservicesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmpservicesKeyReleased
        String query1 = cmpservices.getText();
        filterservices(query1);
    }//GEN-LAST:event_cmpservicesKeyReleased

    private void btnroomdataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnroomdataActionPerformed
        
    }//GEN-LAST:event_btnroomdataActionPerformed

    private void jTextField8KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField8KeyReleased
        
    }//GEN-LAST:event_jTextField8KeyReleased

    private void tblroomsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblroomsMouseClicked
        String typ = tblrooms.getValueAt(tblrooms.getSelectedRow(), 1).toString();
        String ocpn = tblrooms.getValueAt(tblrooms.getSelectedRow(), 2).toString();
        String blc = tblrooms.getValueAt(tblrooms.getSelectedRow(), 3).toString();
        String stt = tblrooms.getValueAt(tblrooms.getSelectedRow(), 4).toString();
        rttxt.setText(typ);
        ocptxt.setText(ocpn);
        btext.setText(blc);
        stxt.setText(stt);
    }//GEN-LAST:event_tblroomsMouseClicked

    private void membprojsrchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_membprojsrchKeyReleased
        String query1 = membprojsrch.getText();
        filterempproj(query1);
    }//GEN-LAST:event_membprojsrchKeyReleased

    private void btnretrvpayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnretrvpayActionPerformed
        retrieverent();
        totalarrears();
        totalrentpaid();
    }//GEN-LAST:event_btnretrvpayActionPerformed

    private void btnupdatepayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdatepayActionPerformed
        try {
            if (paynum.getText().isEmpty() || fpay.getText().isEmpty() || lpay.getText().isEmpty() || rpay.getText().isEmpty() || ampay.getText().isEmpty() || mnthp.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No row is selected..!!\nPlease select a row to update...");
            } else {
                int index = tblrent.getSelectedRow();
                String id = tblrent.getValueAt(index, 0).toString();
                if (updaterent(id, paynum.getText(), fpay.getText(), lpay.getText(), rpay.getText(), ampay.getText(), arrpay.getText(), mnthp.getText())) {
                    JOptionPane.showMessageDialog(null, "Rent Payment Data Updated Successfully");
                    paynum.setText("");
                    fpay.setText("");
                    lpay.setText("");
                    rpay.setText("");
                    ampay.setText("");
                    arrpay.setText("");
                    mnthp.setText("");
                    retrieverent();
                    totalarrears();
                    totalrentpaid();
                } else {
                    JOptionPane.showMessageDialog(null, "Rent Payment Data is  Not Updated ");
                }
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btnupdatepayActionPerformed

    private void btndeletepayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeletepayActionPerformed
        try {
            String[] options = {"Yes", "No"};
            int answer = JOptionPane.showOptionDialog(null, " Are you Sure to delete Rent payment Data ??", "Delete Options", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            if (answer == 0) {
                String id = tblrent.getValueAt(tblrent.getSelectedRow(), 0).toString();
                if (deleterent(id)) {
                    JOptionPane.showMessageDialog(null, "Rent Payment Data Deleted Successfully");
                    paynum.setText("");
                    fpay.setText("");
                    lpay.setText("");
                    rpay.setText("");
                    ampay.setText("");
                    arrpay.setText("");
                    retrieverent();
                    totalarrears();
                    totalrentpaid();
                } else {
                    JOptionPane.showMessageDialog(null, " Rent Payment Data is  Not Deleted ");
                }
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_btndeletepayActionPerformed

    private void searchrentKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchrentKeyReleased
        String query1 = searchrent.getText();
        filterrent(query1);
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

    private void btnretrieveprovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnretrieveprovActionPerformed
        retreaveprovs();
    }//GEN-LAST:event_btnretrieveprovActionPerformed

    private void btnupdateprovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateprovActionPerformed
        try {
            if (provname.getText().isEmpty() || provcont.getText().isEmpty() || provtype.getText().isEmpty() || provaddress.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No row is selected..!!\nPlease select a row to update...");
            } else {
                int index = providerstbl.getSelectedRow();
                String id = providerstbl.getValueAt(index, 0).toString();
                if (updateprovs(id, provname.getText(), provcont.getText(), provtype.getText(), provaddress.getText())) {
                    JOptionPane.showMessageDialog(null, "Service Providers Data updated successifully");
                    provname.setText("");
                    provcont.setText("");
                    provtype.setText("");
                    provaddress.setText("");
                    retreaveprovs();
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
            int answer = JOptionPane.showOptionDialog(null, " Are you Sure to delete service providers Data ??", "Delete Options", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            if (answer == 0) {
                String project_id = providerstbl.getValueAt(providerstbl.getSelectedRow(), 0).toString();
                if (deleteprovs(project_id)) {
                    JOptionPane.showMessageDialog(null, "Local Service Data deleted successfully");
                    provname.setText("");
                    provcont.setText("");
                    provtype.setText("");
                    provaddress.setText("");
                    retreaveprovs();
                } else {
                    JOptionPane.showMessageDialog(null, "Local Service Provider Data is Not deleted ");
                }
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_btndeleteProvActionPerformed

    private void providerssearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_providerssearchKeyReleased
        String query1 = providerssearch.getText();
        filterprovs(query1);
    }//GEN-LAST:event_providerssearchKeyReleased

    private void savecalsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savecalsActionPerformed
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String sql = "UPDATE `calendar_of_activities` SET `Event`=? WHERE id=1";
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms", "root", "");
            pst = con.prepareStatement(sql);
            pst.setString(1, calendarreord.getText());
            pst.executeUpdate();
            calendarreord.setText("");
            JOptionPane.showMessageDialog(null, "Calendar of Activities Successfully Saved");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProjectManagerHome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_savecalsActionPerformed

    private void clscalswActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clscalswActionPerformed
        load_home();
    }//GEN-LAST:event_clscalswActionPerformed

    private void viewregulationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewregulationsActionPerformed
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String sql = "SELECT `rules` FROM `rules_regulations` WHERE id=1";
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalmanagementsystem", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                String rules = rs.getString("rules");
                //  rlsarea.setText("*******************************************\n");
                //  rlsarea.setText(rlsarea.getText()+"           RULES AND REGULATIONS \n");
                //  rlsarea.setText(rlsarea.getText()+"*******************************************\n");
                rlsarea.setText(rlsarea.getText() + rules);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProjectManagerHome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_viewregulationsActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String sql = "SELECT `Event` FROM `calendar_of_activities` WHERE id=1";
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                String rules = rs.getString("Event");
                //   datesarea.setText("****************************************************************\n");
                //    datesarea.setText(datesarea.getText()+"    PAYMENTS AND DUE DATES  \n");
                //    datesarea.setText(datesarea.getText()+"*******************************************\n");
                noticearea.setText(noticearea.getText() + rules);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProjectManagerHome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        try {
            noticearea.print();
            load_home();
            //this.dispose();
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void printregulationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printregulationsActionPerformed
        try {
            noticearea.print();
            noticearea.setText("");
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

    private void viewrentdatesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewrentdatesActionPerformed
        displaypanel.removeAll();
        displaypanel.add(viewcalactivity);
        viewcalactivity.setVisible(true);
        viewcalactivity.repaint();
        viewcalactivity.revalidate();
    }//GEN-LAST:event_viewrentdatesActionPerformed

    private void providerstblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_providerstblMouseClicked
        String pron = providerstbl.getValueAt(providerstbl.getSelectedRow(), 1).toString();
        String proc = providerstbl.getValueAt(providerstbl.getSelectedRow(), 2).toString();
        String prot = providerstbl.getValueAt(providerstbl.getSelectedRow(), 3).toString();
        String prol = providerstbl.getValueAt(providerstbl.getSelectedRow(), 4).toString();

        provname.setText(pron);
        provcont.setText(proc);
        provtype.setText(prot);
        provaddress.setText(prol);
    }//GEN-LAST:event_providerstblMouseClicked

    private void gentenantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gentenantActionPerformed
        String FILE_NAME = "C:\\Users\\MICHAEL\\Documents\\NetBeansProjects\\TechnoScienceManagementSystem\\Computer Clinic Report.pdf";
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(new File(FILE_NAME)));
            document.open();

            document.add(new Paragraph(new Date().toString()));

            document.add(new Paragraph("COMPUTER CLINIC REPORT"));

            document.add(new Paragraph("------------------------------------------------------------------------------------------------------------"));
            PdfPTable tablesup = new PdfPTable(7);
            tablesup.setWidthPercentage(100);
            tablesup.setSpacingBefore(10f);
            tablesup.setSpacingAfter(10f);

            tablesup.addCell("clinic id");
            tablesup.addCell("Hardware troubleshooting");
            tablesup.addCell("Windows Installation");
            tablesup.addCell("Dust Cleaning");
            tablesup.addCell("Software Installation");
            tablesup.addCell("Antivirus Installation");
            tablesup.addCell("Windows Updation");
            
            Connection con;
            Statement st;
            ResultSet rs;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms", "root", "");
            st = con.createStatement();
            String query = "Select * from clinicevents";
            rs = st.executeQuery(query);
            while (rs.next()) {
                String v1 = rs.getString("clid");
                String v2 = rs.getString("hardware_troubleshooting");
                String v3 = rs.getString("windows_installation");
                String v4 = rs.getString("dust_cleaning");
                String v5 = rs.getString("software_installation");
                String v6 = rs.getString("antivirus_installation");
                String v7 = rs.getString("windows_updation");

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
            JOptionPane.showMessageDialog(null, "Computer Clinics Report Saved");
            JOptionPane.showMessageDialog(null, "You can now view Report in your System Directory");
            load_home();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_gentenantActionPerformed

    private void closetenantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closetenantActionPerformed
        load_home();
    }//GEN-LAST:event_closetenantActionPerformed

    private void viewcompActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewcompActionPerformed
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String sql = "SELECT * FROM `complains`";
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalmanagementsystem", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            complainsview.setText("*******************************************\n");
            complainsview.setText(complainsview.getText() + "          SENT COMPLAINS \n");
            complainsview.setText(complainsview.getText() + "*******************************************\n");
            complainsview.setText(complainsview.getText() + "NO.    COMPLAIN\n");
            while (rs.next()) {
                String id = rs.getString("complain_id");
                String compl = rs.getString("compl");
                String date = rs.getString("date");
                complainsview.setText("\n" + complainsview.getText() + id + "    " + compl + " DATE---->> " + date + "\n");
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
            String sql6 = "UPDATE `room` SET `phonenumber`=?, `status`=?  WHERE room_number=?";
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalmanagementsystem", "root", "");
            pst = con.prepareStatement(sql6);
            pst.setString(1, phup.getText());
            pst.setString(2, txtstatup.getSelectedItem().toString());
            pst.setString(3, rmupd.getText());
            pst.executeUpdate();

            rmupd.setText("");
            phup.setText("");
            txtstatup.setSelectedIndex(0);
            JOptionPane.showMessageDialog(null, "Room Details Successfully Updated");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProjectManagerHome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnaddrm1ActionPerformed

    private void txtstatupItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_txtstatupItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtstatupItemStateChanged

    private void rmupdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rmupdKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                String txtrms = rmupd.getText();
                pst = con.prepareStatement("Select * from tenants where roomnumber = ?");
                pst.setString(1, txtrms);
                rs = pst.executeQuery();
                if (rs.next() == false) {
                    JOptionPane.showMessageDialog(this, "Room Number is not found");
                    JOptionPane.showMessageDialog(this, "Or Tenant Has Occupied the Room");
                } else {
                    String phoneupd = rs.getString("phonenumber");
                    phup.setText(phoneupd.trim());
                    txtstatup.setSelectedIndex(1);

                }
            } catch (SQLException ex) {
                Logger.getLogger(ProjectManagerHome.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_rmupdKeyPressed

    private void closeprovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeprovActionPerformed
        load_home();
    }//GEN-LAST:event_closeprovActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        load_home();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        load_home();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        retrieverentprop();
        totalprices();
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
                    totalprices();
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
                    totalprices();
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
            String sql = "INSERT INTO notices (noticename)  VALUES(?)";
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms", "root", "");
            pst = con.prepareStatement(sql);
            pst.setString(1, noticesendtxt.getText());
            if (noticesendtxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please Enter Notice to Upload");
            } else {
                pst.executeUpdate();
                noticesendtxt.setText("");
                JOptionPane.showMessageDialog(null, "Successfully Uploaded");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_saveregulations1ActionPerformed

    private void clsrules1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clsrules1ActionPerformed
        load_home();
    }//GEN-LAST:event_clsrules1ActionPerformed

    private void viewrentdates1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewrentdates1ActionPerformed
        displaypanel.removeAll();
        displaypanel.add(ViewNotices);
        ViewNotices.setVisible(true);
        ViewNotices.repaint();
        ViewNotices.revalidate();
    }//GEN-LAST:event_viewrentdates1ActionPerformed

    private void retrcalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retrcalActionPerformed
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String sql = "SELECT `Event` FROM `calendar_of_activities` WHERE id=1";
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                String rules = rs.getString("Event");
                //   rulesarea.setText("******************************************************\n");
                //  rulesarea.setText(rulesarea.getText()+"           RULES AND REGULATIONS \n");
                //   rulesarea.setText(rulesarea.getText()+"*******************************************\n");
                calendarreord.setText(calendarreord.getText() + rules);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProjectManagerHome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_retrcalActionPerformed

    private void retrievedtsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retrievedtsActionPerformed
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String sql = "SELECT `Event` FROM `calendar_of_activities` WHERE id=1";
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms", "root", "");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                String viewtech = rs.getString("Event");
                calprojview.setText(calprojview.getText() + viewtech);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProjectManagerHome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_retrievedtsActionPerformed

    private void savfeedbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savfeedbActionPerformed
        viewnoticets();
    }//GEN-LAST:event_savfeedbActionPerformed

    private void clsfeedbcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clsfeedbcActionPerformed
        load_home();
    }//GEN-LAST:event_clsfeedbcActionPerformed

    private void retrieveserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retrieveserActionPerformed
        retrieveservices();
        totalservices();
    }//GEN-LAST:event_retrieveserActionPerformed

    private void viewrentdates2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewrentdates2ActionPerformed
         displaypanel.removeAll();
        displaypanel.add(ViewMembers);
        ViewMembers.setVisible(true);
        ViewMembers.repaint();
        ViewMembers.revalidate();
        
    }//GEN-LAST:event_viewrentdates2ActionPerformed

    private void servtypesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_servtypesActionPerformed

        try {
            String txtservs = servtypes.getSelectedItem().toString();
            pst = con.prepareStatement("Select * from clinicservices where servtype = ?");
            pst.setString(1, txtservs);
            rs = pst.executeQuery();
            if (rs.next()) {
                String namta = rs.getString("Amount");
                amtservs.setText(namta.trim());

            } else {
                JOptionPane.showMessageDialog(this, "Service You Entered is not found try Again");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_servtypesActionPerformed

    private void btnGetTenants1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetTenants1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGetTenants1ActionPerformed

    private void btnprinttblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprinttblActionPerformed
        try {
            tableviewmembers.print();
        } catch (PrinterException ex) {
            Logger.getLogger(ProjectManagerHome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnprinttblActionPerformed

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
            java.util.logging.Logger.getLogger(ProjectManagerHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProjectManagerHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProjectManagerHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProjectManagerHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProjectManagerHome().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Addemployees;
    private javax.swing.JPanel Addrooms;
    private javax.swing.JPanel AddserviceProviders;
    private javax.swing.JPanel CalendarActivities;
    private javax.swing.JPanel ClinicservicesReport;
    private javax.swing.JPanel Paymentform;
    private javax.swing.JPanel Receipt;
    private javax.swing.JPanel UpdateRooms;
    private javax.swing.JPanel ViewComplains;
    private javax.swing.JPanel ViewMembers;
    private javax.swing.JPanel ViewNotices;
    private javax.swing.JPanel ViewRules;
    private javax.swing.JPanel addClinicEvents;
    private javax.swing.JTextArea address;
    private javax.swing.JLabel addusrlabel;
    private javax.swing.JTextField ampay;
    private javax.swing.JTextField amtr;
    private javax.swing.JTextField amtservs;
    private javax.swing.JTextField antitxt;
    private javax.swing.JTextField arar;
    private javax.swing.JTextField arrpay;
    private javax.swing.JTextField arrtxt;
    private javax.swing.JLabel blrm;
    private javax.swing.JLabel blrm1;
    private javax.swing.JTextField btext;
    private javax.swing.JButton btnAddstrs;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnClearrm;
    private javax.swing.JButton btnClearrm1;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnCloseprov;
    private javax.swing.JButton btnCloserange;
    private javax.swing.JButton btnCloserm;
    private javax.swing.JButton btnCloserm1;
    private javax.swing.JButton btnClosessert;
    private javax.swing.JButton btnClosetenant;
    private javax.swing.JButton btnDeletessert;
    private javax.swing.JButton btnGetTenants;
    private javax.swing.JButton btnGetTenants1;
    private javax.swing.JPanel btnGroup;
    private javax.swing.JButton btnLogOut;
    private javax.swing.JButton btnReloademp;
    private javax.swing.JButton btnUpdatesser;
    private javax.swing.JButton btnUpdatesser1;
    private javax.swing.JButton btnaddemployees;
    private javax.swing.JButton btnaddrentalproperties;
    private javax.swing.JButton btnaddrm;
    private javax.swing.JButton btnaddrm1;
    private javax.swing.JButton btnaddserviceprovider;
    private javax.swing.JButton btncloseemp;
    private javax.swing.JButton btndel;
    private javax.swing.JButton btndeleteProv;
    private javax.swing.JButton btndeletepay;
    private javax.swing.JButton btnmanagesserts;
    private javax.swing.JButton btnmanagetenants;
    private javax.swing.JButton btnprinttbl;
    private javax.swing.JButton btnretrieveprov;
    private javax.swing.JButton btnretrvpay;
    private javax.swing.JButton btnroomdata;
    private javax.swing.JButton btnupdatepay;
    private javax.swing.JButton btnupdateprov;
    private javax.swing.JButton btnupdroom;
    private javax.swing.JButton buttoncls;
    private javax.swing.JTextArea calendarreord;
    private javax.swing.JTextArea calprojview;
    private javax.swing.JButton closeprov;
    private javax.swing.JButton closeregulations;
    private javax.swing.JButton closetenant;
    private javax.swing.JButton clsbtns;
    private javax.swing.JButton clscalsw;
    private javax.swing.JButton clsfeedbc;
    private javax.swing.JButton clsrules1;
    private javax.swing.JTextField cmpservices;
    private javax.swing.JTextArea complainsview;
    private javax.swing.JTextField comptxt;
    private javax.swing.JLabel confpasslabel;
    private javax.swing.JTextField context;
    private com.toedter.calendar.JDateChooser datechooser;
    private javax.swing.JPanel displaypanel;
    private javax.swing.JTextField dustxt;
    private javax.swing.JTable eventtables;
    private javax.swing.JButton exitbtn;
    private javax.swing.JLabel fname;
    private javax.swing.JTextField fpay;
    private javax.swing.JButton gentenant;
    private javax.swing.JTextField hardtxt;
    private javax.swing.JPanel homepanel;
    private javax.swing.JButton insser;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
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
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
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
    private javax.swing.JLabel jLabel6;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JLabel lblFilldet;
    private javax.swing.JLabel lblFilldet1;
    private javax.swing.JLabel lblReports1;
    private javax.swing.JLabel lblread2;
    private javax.swing.JLabel lblstat;
    private javax.swing.JLabel lblstat1;
    private javax.swing.JLabel lbltenant;
    private javax.swing.JLabel lblusername;
    private javax.swing.JLabel lbnum;
    private javax.swing.JLabel lbnum1;
    private javax.swing.JTextField lnamtxt;
    private javax.swing.JLabel loginlabel;
    private javax.swing.JTextField lpay;
    private javax.swing.JTextField ltext;
    private javax.swing.JPanel managecomputerclinics;
    private javax.swing.JPanel manageproperties;
    private javax.swing.JPanel managerent;
    private javax.swing.JPanel managerooms;
    private javax.swing.JPanel manageserviceproviders;
    private javax.swing.JPanel manageservices;
    private javax.swing.JTextField membprojsrch;
    private javax.swing.JTextField mnthp;
    private javax.swing.JTextField namtxt;
    private javax.swing.JTextArea noticearea;
    private javax.swing.JTextArea noticesendtxt;
    private javax.swing.JTextArea noticesproj;
    private javax.swing.JTextField notxt;
    private javax.swing.JTextField ntxt;
    private javax.swing.JLabel num;
    private javax.swing.JLabel numrent;
    private javax.swing.JLabel numrent1;
    private javax.swing.JLabel numrent2;
    private javax.swing.JLabel numrent3;
    private javax.swing.JLabel numrent4;
    private javax.swing.JLabel numrent6;
    private javax.swing.JLabel numrent9;
    private javax.swing.JTextField numtext;
    private javax.swing.JTextField ocptxt;
    private javax.swing.JLabel passwordlabel;
    private javax.swing.JButton paybtn;
    private javax.swing.JTextField paynum;
    private javax.swing.JTextField paytxt;
    private javax.swing.JLabel phonelabel;
    private javax.swing.JTextField phup;
    private javax.swing.JLabel plabel;
    private javax.swing.JButton printbutton;
    private javax.swing.JButton printregulations;
    private javax.swing.JLabel proddetails;
    public javax.swing.JLabel projsusername;
    private javax.swing.JTextField propertytots;
    private javax.swing.JTextField propntxt;
    private javax.swing.JTextField propptxt;
    private javax.swing.JTextField propqtxt;
    private javax.swing.JTable proprents;
    private javax.swing.JTextField provaddress;
    private javax.swing.JTextField provcont;
    private javax.swing.JTextField providerssearch;
    private javax.swing.JTable providerstbl;
    private javax.swing.JTextField provname;
    private javax.swing.JButton provreset;
    private javax.swing.JTextField provtype;
    private javax.swing.JButton rbtn;
    private javax.swing.JButton receiptbtn;
    private javax.swing.JButton recordprovider;
    private javax.swing.JTextArea recpt;
    private javax.swing.JButton retrcal;
    private javax.swing.JButton retrievedts;
    private javax.swing.JButton retrieveser;
    private javax.swing.JTextArea rlsarea;
    private javax.swing.JComboBox<String> rmtype;
    private javax.swing.JTextField rmtyt;
    private javax.swing.JTextField rmupd;
    private javax.swing.JLabel rolelabel;
    private javax.swing.JTextField rpay;
    private javax.swing.JTextField rttxt;
    private javax.swing.JTextField salarytxt;
    private javax.swing.JButton savecals;
    private javax.swing.JButton saveregulations1;
    private javax.swing.JButton saveservices;
    private javax.swing.JButton savfeedb;
    private javax.swing.JTextField searchrent;
    private javax.swing.JLabel securitylabel;
    private javax.swing.JPanel services;
    private javax.swing.JComboBox<String> servtxt;
    private javax.swing.JComboBox<String> servtypes;
    private javax.swing.JLabel softwareName;
    private javax.swing.JLabel softwaredesc;
    private javax.swing.JLabel softwaredesc1;
    private javax.swing.JLabel softwarename;
    private javax.swing.JLabel softwarename1;
    private javax.swing.JLabel stockmgmt;
    private javax.swing.JTextField stxt;
    private javax.swing.JTextField swtxt;
    private javax.swing.JTable tabletenants;
    private javax.swing.JTable tableviewmembers;
    private javax.swing.JTable tblrent;
    private javax.swing.JTable tblrooms;
    private javax.swing.JTextField totalclinc;
    private javax.swing.JTextField totalrms;
    private javax.swing.JTextField totalviewproj;
    private javax.swing.JTextField txtamrd;
    private javax.swing.JTextField txtnum;
    private javax.swing.JTextField txtnumber;
    private javax.swing.JComboBox<String> txtstat;
    private javax.swing.JComboBox<String> txtstatup;
    private javax.swing.JTextField txtstr;
    private javax.swing.JComboBox<String> typetext;
    private javax.swing.JPanel uploadnotices;
    private javax.swing.JTextField upstxt;
    private javax.swing.JLabel usernamelabel;
    private javax.swing.JLabel usrmgmt;
    private javax.swing.JPanel viewcalactivity;
    private javax.swing.JButton viewcomp;
    private javax.swing.JPanel viewnotices;
    private javax.swing.JButton viewregulations;
    private javax.swing.JButton viewrentdates;
    private javax.swing.JButton viewrentdates1;
    private javax.swing.JButton viewrentdates2;
    private javax.swing.JTextField wintxt;
    // End of variables declaration//GEN-END:variables

}
