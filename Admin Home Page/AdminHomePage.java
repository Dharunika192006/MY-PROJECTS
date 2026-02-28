/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Dharunika
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminHomePage extends javax.swing.JFrame {
  
    // Database connection
    private Connection conn;

    // UI Components
    private JTabbedPane tabbedPane;

    // Companies
    private JTable tblCompanies;
    private DefaultTableModel modelCompanies;
    private JTextField txtNewCompany;
    private JButton btnAddCompany, btnDeleteCompany, btnRefreshCompanies;

    // Domains
    private JTable tblDomains;
    private DefaultTableModel modelDomains;
    private JTextField txtNewDomain;
    private JButton btnAddDomain, btnDeleteDomain, btnRefreshDomains;

    // Questions
    private JTable tblCompanyQuestions;
    private DefaultTableModel modelCompanyQuestions;
    private JComboBox<String> cbCompanyForQuestion;
    private JTextArea txtCompanyQuestion;
    private JButton btnAddCompanyQuestion, btnDeleteCompanyQuestion, btnRefreshCompanyQuestions;

    private JTable tblDomainQuestions;
    private DefaultTableModel modelDomainQuestions;
    private JComboBox<String> cbDomainForQuestion;
    private JTextArea txtDomainQuestion;
    private JButton btnAddDomainQuestion, btnDeleteDomainQuestion, btnRefreshDomainQuestions;

    // Feedbacks
    private JTable tblFeedbacks;
    private DefaultTableModel modelFeedbacks;
    private JButton btnRefreshFeedbacks;

    // Complaints
    private JTable tblComplaints;
    private DefaultTableModel modelComplaints;
    private JButton btnRefreshComplaints;

    // Header / logout
    private JLabel lblHeader;
    private JButton btnLogout;

    public AdminHomePage() {
        setTitle("Admin Dashboard - Interview Question Generator");
        setSize(1200, 760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Connect to DB
        connectToDatabase();
        ensureMetaTables();

        // Initialize UI
        initHeader();
        initTabbedPane();
        loadAllLists();

        setVisible(true);
    }

    // ----------------- Header -------------------
    private void initHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30, 87, 153));

        lblHeader = new JLabel("  Admin Dashboard  — Manage Companies, Domains & Questions");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblHeader.setForeground(Color.WHITE);
        header.add(lblHeader, BorderLayout.WEST);

        btnLogout = new JButton("Logout");
        btnLogout.setBackground(new Color(220, 53, 69));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.addActionListener(e -> {
            int c = JOptionPane.showConfirmDialog(this, "Logout and return to Login?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (c == JOptionPane.YES_OPTION) {
                dispose();
                new LoginPage().setVisible(true);
            }
        });

        JPanel right = new JPanel();
        right.setOpaque(false);
        right.add(btnLogout);
        header.add(right, BorderLayout.EAST);
        header.setPreferredSize(new Dimension(getWidth(), 60));
        add(header, BorderLayout.NORTH);
    }

    // ----------------- Tabs -------------------
    private void initTabbedPane() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        tabbedPane.addTab("Companies", buildCompaniesPanel());
        tabbedPane.addTab("Domains", buildDomainsPanel());
        tabbedPane.addTab("Questions", buildQuestionsPanel());
        tabbedPane.addTab("Feedbacks", buildFeedbacksPanel());
        tabbedPane.addTab("Complaints", buildComplaintsPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    // ----------------- Companies Panel -------------------
    private JPanel buildCompaniesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 247, 250));

        JPanel top = new JPanel();
        top.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        top.setBackground(panel.getBackground());

        txtNewCompany = new JTextField(30);
        txtNewCompany.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnAddCompany = new JButton("Add Company");
        btnDeleteCompany = new JButton("Delete Selected Company");
        btnRefreshCompanies = new JButton("Refresh");

        btnAddCompany.addActionListener(e -> addCompany());
        btnDeleteCompany.addActionListener(e -> deleteSelectedCompany());
        btnRefreshCompanies.addActionListener(e -> loadCompanies());

        top.add(new JLabel("Company Name:"));
        top.add(txtNewCompany);
        top.add(btnAddCompany);
        top.add(btnDeleteCompany);
        top.add(btnRefreshCompanies);

        panel.add(top, BorderLayout.NORTH);

        modelCompanies = new DefaultTableModel(new Object[]{"ID","Company Name"},0) {
            public boolean isCellEditable(int r,int c){return false;}
        };
        tblCompanies = new JTable(modelCompanies);
        tblCompanies.setRowHeight(24);
        tblCompanies.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane sp = new JScrollPane(tblCompanies);
        panel.add(sp, BorderLayout.CENTER);

        return panel;
    }

    // ----------------- Domains Panel -------------------
    private JPanel buildDomainsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(250, 245, 240));

        JPanel top = new JPanel();
        top.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        top.setBackground(panel.getBackground());

        txtNewDomain = new JTextField(30);
        txtNewDomain.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnAddDomain = new JButton("Add Domain");
        btnDeleteDomain = new JButton("Delete Selected Domain");
        btnRefreshDomains = new JButton("Refresh");

        btnAddDomain.addActionListener(e -> addDomain());
        btnDeleteDomain.addActionListener(e -> deleteSelectedDomain());
        btnRefreshDomains.addActionListener(e -> loadDomains());

        top.add(new JLabel("Domain Name:"));
        top.add(txtNewDomain);
        top.add(btnAddDomain);
        top.add(btnDeleteDomain);
        top.add(btnRefreshDomains);

        panel.add(top, BorderLayout.NORTH);

        modelDomains = new DefaultTableModel(new Object[]{"ID","Domain Name"},0) {
            public boolean isCellEditable(int r,int c){return false;}
        };
        tblDomains = new JTable(modelDomains);
        tblDomains.setRowHeight(24);
        tblDomains.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        panel.add(new JScrollPane(tblDomains), BorderLayout.CENTER);

        return panel;
    }

    // ----------------- Questions Panel -------------------
    private JPanel buildQuestionsPanel() {
        JPanel panel = new JPanel(new GridLayout(2,1));

        // Company questions
        JPanel companyPanel = new JPanel(new BorderLayout());
        companyPanel.setBorder(BorderFactory.createTitledBorder("Company Questions"));

        cbCompanyForQuestion = new JComboBox<>();
        txtCompanyQuestion = new JTextArea(3,40);
        txtCompanyQuestion.setLineWrap(true);
        txtCompanyQuestion.setWrapStyleWord(true);
        btnAddCompanyQuestion = new JButton("Add Question to Company");
        btnDeleteCompanyQuestion = new JButton("Delete Selected Question");
        btnRefreshCompanyQuestions = new JButton("Refresh");

        btnAddCompanyQuestion.addActionListener(e -> addCompanyQuestion());
        btnDeleteCompanyQuestion.addActionListener(e -> deleteSelectedCompanyQuestion());
        btnRefreshCompanyQuestions.addActionListener(e -> loadCompanyQuestions());

        JPanel top = new JPanel();
        top.add(new JLabel("Company:"));
        top.add(cbCompanyForQuestion);
        top.add(btnRefreshCompanyQuestions);

        JPanel addArea = new JPanel();
        addArea.add(new JScrollPane(txtCompanyQuestion));
        addArea.add(btnAddCompanyQuestion);

        companyPanel.add(top, BorderLayout.NORTH);
        companyPanel.add(addArea, BorderLayout.SOUTH);

        modelCompanyQuestions = new DefaultTableModel(new Object[]{"ID","Company","Question"},0) {
            public boolean isCellEditable(int r,int c){return false;}
        };
        tblCompanyQuestions = new JTable(modelCompanyQuestions);
        tblCompanyQuestions.setRowHeight(26);
        tblCompanyQuestions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        companyPanel.add(new JScrollPane(tblCompanyQuestions), BorderLayout.CENTER);
        companyPanel.add(btnDeleteCompanyQuestion, BorderLayout.EAST);

        // Domain questions
        JPanel domainPanel = new JPanel(new BorderLayout());
        domainPanel.setBorder(BorderFactory.createTitledBorder("Domain Questions"));

        cbDomainForQuestion = new JComboBox<>();
        txtDomainQuestion = new JTextArea(3,40);
        txtDomainQuestion.setLineWrap(true);
        txtDomainQuestion.setWrapStyleWord(true);
        btnAddDomainQuestion = new JButton("Add Question to Domain");
        btnDeleteDomainQuestion = new JButton("Delete Selected Question");
        btnRefreshDomainQuestions = new JButton("Refresh");

        btnAddDomainQuestion.addActionListener(e -> addDomainQuestion());
        btnDeleteDomainQuestion.addActionListener(e -> deleteSelectedDomainQuestion());
        btnRefreshDomainQuestions.addActionListener(e -> loadDomainQuestions());

        JPanel topD = new JPanel();
        topD.add(new JLabel("Domain:"));
        topD.add(cbDomainForQuestion);
        topD.add(btnRefreshDomainQuestions);

        JPanel addAreaD = new JPanel();
        addAreaD.add(new JScrollPane(txtDomainQuestion));
        addAreaD.add(btnAddDomainQuestion);

        domainPanel.add(topD, BorderLayout.NORTH);
        domainPanel.add(addAreaD, BorderLayout.SOUTH);

        modelDomainQuestions = new DefaultTableModel(new Object[]{"ID","Domain","Question"},0) {
            public boolean isCellEditable(int r,int c){return false;}
        };
        tblDomainQuestions = new JTable(modelDomainQuestions);
        tblDomainQuestions.setRowHeight(26);
        tblDomainQuestions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        domainPanel.add(new JScrollPane(tblDomainQuestions), BorderLayout.CENTER);
        domainPanel.add(btnDeleteDomainQuestion, BorderLayout.EAST);

        panel.add(companyPanel);
        panel.add(domainPanel);

        return panel;
    }

    // ----------------- Feedbacks Panel -------------------
    private JPanel buildFeedbacksPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255,250,240));

        JLabel lblHeading = new JLabel("User Feedbacks");
        lblHeading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblHeading.setForeground(new Color(51,102,255));
        lblHeading.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.add(lblHeading, BorderLayout.NORTH);

        modelFeedbacks = new DefaultTableModel(new Object[]{"ID","Username","Feedback","Submitted On"},0){
            public boolean isCellEditable(int r,int c){return false;}
        };
        tblFeedbacks = new JTable(modelFeedbacks);
        tblFeedbacks.setRowHeight(26);
        tblFeedbacks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblFeedbacks.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblFeedbacks.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblFeedbacks.setForeground(new Color(50,50,50));
        tblFeedbacks.setBackground(new Color(255,255,240));

        panel.add(new JScrollPane(tblFeedbacks), BorderLayout.CENTER);

        btnRefreshFeedbacks = new JButton("Refresh Feedbacks");
        btnRefreshFeedbacks.setBackground(new Color(51,102,255));
        btnRefreshFeedbacks.setForeground(Color.WHITE);
        btnRefreshFeedbacks.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRefreshFeedbacks.addActionListener(e -> loadFeedbacks());

        JPanel bottom = new JPanel();
        bottom.add(btnRefreshFeedbacks);
        panel.add(bottom, BorderLayout.SOUTH);

        loadFeedbacks();
        return panel;
    }

    // ----------------- Complaints Panel -------------------
    private JPanel buildComplaintsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245,255,250));

        JLabel lblHeading = new JLabel("User Complaints");
        lblHeading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblHeading.setForeground(new Color(204,0,0));
        lblHeading.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.add(lblHeading, BorderLayout.NORTH);

        modelComplaints = new DefaultTableModel(new Object[]{"ID","Username","Complaint","Submitted On"},0){
            public boolean isCellEditable(int r,int c){return false;}
        };
        tblComplaints = new JTable(modelComplaints);
        tblComplaints.setRowHeight(26);
        tblComplaints.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblComplaints.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblComplaints.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblComplaints.setForeground(new Color(50,50,50));
        tblComplaints.setBackground(new Color(240,255,250));

        panel.add(new JScrollPane(tblComplaints), BorderLayout.CENTER);

        btnRefreshComplaints = new JButton("Refresh Complaints");
        btnRefreshComplaints.setBackground(new Color(204,0,0));
        btnRefreshComplaints.setForeground(Color.WHITE);
        btnRefreshComplaints.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRefreshComplaints.addActionListener(e -> loadComplaints());

        JPanel bottom = new JPanel();
        bottom.add(btnRefreshComplaints);
        panel.add(bottom, BorderLayout.SOUTH);

        loadComplaints();
        return panel;
    }

    // ----------------- DB Connection -------------------
    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/InterviewQuestion_Generator?useSSL=false&allowPublicKeyRetrieval=true",
                    "root","Admin#"
            );
        } catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,"DB Connection failed:\n"+ex.getMessage(),"DB Error",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private void ensureMetaTables() {
        try(Statement st = conn.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS companies (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) UNIQUE NOT NULL)");
            st.execute("CREATE TABLE IF NOT EXISTS domains (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) UNIQUE NOT NULL)");
        } catch(SQLException e) { e.printStackTrace(); }
    }

    private void loadAllLists() {
        loadCompanies();
        loadDomains();
        loadCompanyQuestions();
        loadDomainQuestions();
        populateCompanyComboBoxes();
        populateDomainComboBoxes();
    }

    // ----------------- Load Tables -------------------
    private void loadCompanies() {
        modelCompanies.setRowCount(0);
        try(PreparedStatement ps = conn.prepareStatement("SELECT id,name FROM companies ORDER BY name")) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) modelCompanies.addRow(new Object[]{rs.getInt("id"), rs.getString("name")});
        } catch(SQLException e) { showError("Failed to load companies",e); }
    }

    private void loadDomains() {
        modelDomains.setRowCount(0);
        try(PreparedStatement ps = conn.prepareStatement("SELECT id,name FROM domains ORDER BY name")) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) modelDomains.addRow(new Object[]{rs.getInt("id"), rs.getString("name")});
        } catch(SQLException e) { showError("Failed to load domains",e); }
    }

    private void populateCompanyComboBoxes() {
        if(cbCompanyForQuestion==null) return;
        cbCompanyForQuestion.removeAllItems();
        try(PreparedStatement ps = conn.prepareStatement("SELECT name FROM companies ORDER BY name")) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) cbCompanyForQuestion.addItem(rs.getString("name"));
        } catch(SQLException e) { showError("Failed to populate companies",e); }
    }

    private void populateDomainComboBoxes() {
        if(cbDomainForQuestion==null) return;
        cbDomainForQuestion.removeAllItems();
        try(PreparedStatement ps = conn.prepareStatement("SELECT name FROM domains ORDER BY name")) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) cbDomainForQuestion.addItem(rs.getString("name"));
        } catch(SQLException e) { showError("Failed to populate domains",e); }
    }

    private void loadCompanyQuestions() {
        modelCompanyQuestions.setRowCount(0);
        try(PreparedStatement ps = conn.prepareStatement("SELECT id,company,question FROM company_questions ORDER BY id")) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) modelCompanyQuestions.addRow(new Object[]{rs.getInt("id"),rs.getString("company"),rs.getString("question")});
        } catch(SQLException e){showError("Failed to load company questions",e);}
    }

    private void loadDomainQuestions() {
        modelDomainQuestions.setRowCount(0);
        try(PreparedStatement ps = conn.prepareStatement("SELECT id,domain,question FROM domain_questions ORDER BY id")) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) modelDomainQuestions.addRow(new Object[]{rs.getInt("id"),rs.getString("domain"),rs.getString("question")});
        } catch(SQLException e){showError("Failed to load domain questions",e);}
    }

    private void loadFeedbacks() {
        modelFeedbacks.setRowCount(0);
        try(PreparedStatement ps = conn.prepareStatement("SELECT id,username,feedback_text,created_at FROM feedbacks ORDER BY created_at DESC")) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) modelFeedbacks.addRow(new Object[]{
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("feedback_text"),
                rs.getTimestamp("created_at")
            });
        } catch(SQLException e) { showError("Failed to load feedbacks",e); }
    }

    private void loadComplaints() {
        modelComplaints.setRowCount(0);
        try(PreparedStatement ps = conn.prepareStatement("SELECT id,username,complaint_text,submitted_on FROM complaints ORDER BY submitted_on DESC")) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) modelComplaints.addRow(new Object[]{
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("complaint_text"),
                rs.getTimestamp("submitted_on")
            });
        } catch(SQLException e) { showError("Failed to load complaints",e); }
    }

    // ----------------- CRUD Operations for Companies/Domains/Questions -------------------
    private void addCompany() {
        String name = txtNewCompany.getText().trim();
        if(name.isEmpty()){ JOptionPane.showMessageDialog(this,"Enter company name!","Input",JOptionPane.WARNING_MESSAGE); return;}
        try(PreparedStatement ps = conn.prepareStatement("INSERT INTO companies (name) VALUES (?)")) {
            ps.setString(1,name); ps.executeUpdate();
            txtNewCompany.setText(""); loadCompanies(); populateCompanyComboBoxes();
        } catch(SQLException e){ JOptionPane.showMessageDialog(this,"Company may already exist.","Error",JOptionPane.ERROR_MESSAGE);}
    }

    private void deleteSelectedCompany() {
        int row = tblCompanies.getSelectedRow(); if(row==-1){JOptionPane.showMessageDialog(this,"Select a company","Warning",JOptionPane.WARNING_MESSAGE);return;}
        int id=(int)modelCompanies.getValueAt(row,0); String name=(String)modelCompanies.getValueAt(row,1);
        try{conn.setAutoCommit(false);
            try(PreparedStatement psDelQ = conn.prepareStatement("DELETE FROM company_questions WHERE company=?");
                PreparedStatement psDelC = conn.prepareStatement("DELETE FROM companies WHERE id=?")) {
                psDelQ.setString(1,name); psDelQ.executeUpdate();
                psDelC.setInt(1,id); psDelC.executeUpdate();
                conn.commit();
            } catch(SQLException ex){conn.rollback(); throw ex;} finally{conn.setAutoCommit(true);}
            loadCompanies(); loadCompanyQuestions(); populateCompanyComboBoxes();
        } catch(SQLException e){showError("Failed to delete company",e);}
    }

    private void addDomain() {
        String name = txtNewDomain.getText().trim();
        if(name.isEmpty()){ JOptionPane.showMessageDialog(this,"Enter domain name!","Input",JOptionPane.WARNING_MESSAGE); return;}
        try(PreparedStatement ps = conn.prepareStatement("INSERT INTO domains (name) VALUES (?)")) {
            ps.setString(1,name); ps.executeUpdate();
            txtNewDomain.setText(""); loadDomains(); populateDomainComboBoxes();
        } catch(SQLException e){ JOptionPane.showMessageDialog(this,"Domain may already exist.","Error",JOptionPane.ERROR_MESSAGE);}
    }

    private void deleteSelectedDomain() {
        int row = tblDomains.getSelectedRow(); if(row==-1){JOptionPane.showMessageDialog(this,"Select a domain","Warning",JOptionPane.WARNING_MESSAGE);return;}
        int id=(int)modelDomains.getValueAt(row,0); String name=(String)modelDomains.getValueAt(row,1);
        try{conn.setAutoCommit(false);
            try(PreparedStatement psDelQ = conn.prepareStatement("DELETE FROM domain_questions WHERE domain=?");
                PreparedStatement psDelD = conn.prepareStatement("DELETE FROM domains WHERE id=?")) {
                psDelQ.setString(1,name); psDelQ.executeUpdate();
                psDelD.setInt(1,id); psDelD.executeUpdate();
                conn.commit();
            } catch(SQLException ex){conn.rollback(); throw ex;} finally{conn.setAutoCommit(true);}
            loadDomains(); loadDomainQuestions(); populateDomainComboBoxes();
        } catch(SQLException e){showError("Failed to delete domain",e);}
    }

    private void addCompanyQuestion() {
        String company = (String)cbCompanyForQuestion.getSelectedItem();
        String q = txtCompanyQuestion.getText().trim();
        if(company==null || q.isEmpty()){ JOptionPane.showMessageDialog(this,"Select company and enter question!","Warning",JOptionPane.WARNING_MESSAGE); return;}
        try(PreparedStatement ps = conn.prepareStatement("INSERT INTO company_questions(company,question) VALUES(?,?)")) {
            ps.setString(1,company); ps.setString(2,q); ps.executeUpdate();
            txtCompanyQuestion.setText(""); loadCompanyQuestions();
        } catch(SQLException e){showError("Failed to add question",e);}
    }

    private void deleteSelectedCompanyQuestion() {
        int row = tblCompanyQuestions.getSelectedRow(); if(row==-1){JOptionPane.showMessageDialog(this,"Select a question","Warning",JOptionPane.WARNING_MESSAGE); return;}
        int id=(int)modelCompanyQuestions.getValueAt(row,0);
        try(PreparedStatement ps = conn.prepareStatement("DELETE FROM company_questions WHERE id=?")){ ps.setInt(1,id); ps.executeUpdate(); loadCompanyQuestions();}
        catch(SQLException e){showError("Failed to delete question",e);}
    }

    private void addDomainQuestion() {
        String domain = (String)cbDomainForQuestion.getSelectedItem();
        String q = txtDomainQuestion.getText().trim();
        if(domain==null || q.isEmpty()){ JOptionPane.showMessageDialog(this,"Select domain and enter question!","Warning",JOptionPane.WARNING_MESSAGE); return;}
        try(PreparedStatement ps = conn.prepareStatement("INSERT INTO domain_questions(domain,question) VALUES(?,?)")) {
            ps.setString(1,domain); ps.setString(2,q); ps.executeUpdate();
            txtDomainQuestion.setText(""); loadDomainQuestions();
        } catch(SQLException e){showError("Failed to add question",e);}
    }

    private void deleteSelectedDomainQuestion() {
        int row = tblDomainQuestions.getSelectedRow(); if(row==-1){JOptionPane.showMessageDialog(this,"Select a question","Warning",JOptionPane.WARNING_MESSAGE); return;}
        int id=(int)modelDomainQuestions.getValueAt(row,0);
        try(PreparedStatement ps = conn.prepareStatement("DELETE FROM domain_questions WHERE id=?")){ ps.setInt(1,id); ps.executeUpdate(); loadDomainQuestions();}
        catch(SQLException e){showError("Failed to delete question",e);}
    }

    private void showError(String msg,Exception e){
        JOptionPane.showMessageDialog(this,msg+"\n"+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }

   


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 0, 1370, 770));

        pack();
    }// </editor-fold>                        

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
            java.util.logging.Logger.getLogger(AdminHomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminHomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminHomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminHomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

       SwingUtilities.invokeLater(() -> new AdminHomePage());
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLabel jLabel1;
    // End of variables declaration                   
}
