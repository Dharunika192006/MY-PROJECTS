/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Dharunika
 */
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UserHomePage extends JFrame {

    private JPanel headerPanel, sideMenuPanel, mainContentPanel, footerPanel;
    private String username;

    public UserHomePage(String username) {
        this.username = username;
        initComponents();
        setupUI();
        setSize(1400, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void setupUI() {
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // ===== HEADER =====
        headerPanel = new JPanel();
        headerPanel.setBackground(new Color(51, 153, 255));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel lblTitle = new JLabel("Welcome, " + username);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle);
        getContentPane().add(headerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1400, 80));

        // ===== SIDE MENU =====
        sideMenuPanel = new JPanel();
        sideMenuPanel.setBackground(new Color(240, 240, 240));
        sideMenuPanel.setLayout(new BoxLayout(sideMenuPanel, BoxLayout.Y_AXIS));

        JButton btnProfile = new JButton("My Profile");
        JButton btnTakeTest = new JButton("Take Test");
        JButton btnHistory = new JButton("View History");
        JButton btnLeaderboard = new JButton("Leaderboard");
        JButton btnFeedback = new JButton("Feedback");
        JButton btnComplaints = new JButton("Complaints");
        JButton btnLogout = new JButton("Logout");

        sideMenuPanel.add(Box.createVerticalStrut(30));
        sideMenuPanel.add(btnProfile);
        sideMenuPanel.add(Box.createVerticalStrut(20));
        sideMenuPanel.add(btnTakeTest);
        sideMenuPanel.add(Box.createVerticalStrut(20));
        sideMenuPanel.add(btnHistory);
        sideMenuPanel.add(Box.createVerticalStrut(20));
        sideMenuPanel.add(btnLeaderboard);
        sideMenuPanel.add(Box.createVerticalStrut(20));
        sideMenuPanel.add(btnFeedback);
        sideMenuPanel.add(Box.createVerticalStrut(20));
        sideMenuPanel.add(btnComplaints);
        sideMenuPanel.add(Box.createVerticalStrut(20));
        sideMenuPanel.add(btnLogout);

        getContentPane().add(sideMenuPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 230, 690));

        // ===== MAIN CONTENT =====
        mainContentPanel = new JPanel();
        mainContentPanel.setBackground(Color.WHITE);
        mainContentPanel.setLayout(null);

        JLabel lblWelcome = new JLabel("Select a domain or company to start");
        lblWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        lblWelcome.setBounds(300, 50, 600, 30);
        mainContentPanel.add(lblWelcome);

        // ===== Domain Selection =====
        JLabel lblDomain = new JLabel("Select Domain:");
        lblDomain.setBounds(300, 120, 150, 25);
        mainContentPanel.add(lblDomain);

        String[] domains = {"Java", "Python", "Aptitude", "SQL", "Data Structures"};
        JComboBox<String> cbDomain = new JComboBox<>(domains);
        cbDomain.setBounds(450, 120, 200, 25);
        mainContentPanel.add(cbDomain);

        JButton btnGoDomain = new JButton("Go");
        btnGoDomain.setBounds(670, 120, 80, 25);
        mainContentPanel.add(btnGoDomain);

        btnGoDomain.addActionListener(evt -> {
            String selectedDomain = (String) cbDomain.getSelectedItem();
            dispose();
            new TakeTestPage(username, selectedDomain, "Domain").setVisible(true);
        });

        // ===== Company Selection =====
        JLabel lblCompany = new JLabel("Select Company:");
        lblCompany.setBounds(300, 180, 150, 25);
        mainContentPanel.add(lblCompany);

        String[] companies = {"Amazon", "Google", "TCS", "Infosys", "Microsoft"};
        JComboBox<String> cbCompany = new JComboBox<>(companies);
        cbCompany.setBounds(450, 180, 200, 25);
        mainContentPanel.add(cbCompany);

        JButton btnGoCompany = new JButton("Go");
        btnGoCompany.setBounds(670, 180, 80, 25);
        mainContentPanel.add(btnGoCompany);

        btnGoCompany.addActionListener(evt -> {
            String selectedCompany = (String) cbCompany.getSelectedItem();
            dispose();
            new TakeTestPage(username, selectedCompany, "Company").setVisible(true);
        });

        // ===== Quick Start Test =====
        JButton btnQuickTest = new JButton("Quick Start Test");
        btnQuickTest.setBounds(300, 250, 200, 40);
        mainContentPanel.add(btnQuickTest);

        btnQuickTest.addActionListener(evt -> {
            String selectedDomain = (String) cbDomain.getSelectedItem();
            dispose();
            new TakeTestPage(username, selectedDomain, "Domain").setVisible(true);
        });

        // ===== Leaderboard Button =====
        JButton btnLeaderboardMain = new JButton("Leaderboard");
        btnLeaderboardMain.setBounds(550, 250, 200, 40);
        mainContentPanel.add(btnLeaderboardMain);
        btnLeaderboardMain.addActionListener(evt -> {
            dispose();
            new LeaderboardPage(username).setVisible(true);
        });

        getContentPane().add(mainContentPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 80, 1170, 690));

        // ===== FOOTER =====
        footerPanel = new JPanel();
        footerPanel.setBackground(new Color(200, 200, 200));
        JLabel lblFooter = new JLabel("© 2025 Interview Question Generator | User Portal");
        footerPanel.add(lblFooter);
        getContentPane().add(footerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 770, 1400, 30));

        // ===== SIDE MENU ACTIONS =====
        btnProfile.addActionListener(evt -> {
            dispose();
            new ProfilePage(username).setVisible(true);
        });

        btnTakeTest.addActionListener(evt -> {
            String[] options = {"Domain", "Company"};
            String typeChoice = (String) JOptionPane.showInputDialog(
                    this, "Select Test Type", "Take Test", JOptionPane.QUESTION_MESSAGE, null, options, options[0]
            );
            if (typeChoice != null) {
                if (typeChoice.equals("Domain")) {
                    String selectedDomain = (String) JOptionPane.showInputDialog(
                            this, "Select Domain:", "Domain Test", JOptionPane.QUESTION_MESSAGE, null, domains, domains[0]
                    );
                    if (selectedDomain != null) {
                        dispose();
                        new TakeTestPage(username, selectedDomain, "Domain").setVisible(true);
                    }
                } else {
                    String selectedCompany = (String) JOptionPane.showInputDialog(
                            this, "Select Company:", "Company Test", JOptionPane.QUESTION_MESSAGE, null, companies, companies[0]
                    );
                    if (selectedCompany != null) {
                        dispose();
                        new TakeTestPage(username, selectedCompany, "Company").setVisible(true);
                    }
                }
            }
        });

        btnHistory.addActionListener(evt -> {
            dispose();
            new HistoryPage(username).setVisible(true);
        });

        btnLeaderboard.addActionListener(evt -> {
            dispose();
            new LeaderboardPage(username).setVisible(true);
        });

        btnFeedback.addActionListener(evt -> {
            dispose();
            new FeedbackPage(username).setVisible(true);
        });

        btnComplaints.addActionListener(evt -> {
            dispose();
            new ComplaintsPage(username).setVisible(true);
        });

        btnLogout.addActionListener(evt -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginPage().setVisible(true);
            }
        });
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 310, -1, -1));
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 770));

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
            java.util.logging.Logger.getLogger(UserHomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserHomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserHomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserHomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                java.awt.EventQueue.invokeLater(() -> new UserHomePage("Kishore").setVisible(true));
                
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration                   
}
