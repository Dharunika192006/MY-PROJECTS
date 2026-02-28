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
import java.util.ArrayList;
import java.util.Collections;

public class TakeTestPage extends JFrame {

    private String username, selected, type;
    private ArrayList<Integer> questionIds;
    private int currentIndex = 0, score = 0;

    private JLabel lblQuestion, lblTitle;
    private JRadioButton rbA, rbB, rbC, rbD;
    private ButtonGroup group;
    private JButton btnNext;

    private Connection conn;

    public TakeTestPage(String username, String selected, String type) {
        this.username = username;
        this.selected = selected;
        this.type = type;

        setupUI();
        connectDatabase();
        loadQuestions();

        setSize(950, 650);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupUI() {
        setTitle("Take Test - " + type + ": " + selected);
        setLayout(new BorderLayout());

        // Header
        JPanel header = new JPanel();
        header.setBackground(new Color(0, 102, 204));
        lblTitle = new JLabel("🧠 Take Test: " + type + " - " + selected);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        header.add(lblTitle);
        add(header, BorderLayout.NORTH);

        // Question panel
        JPanel questionPanel = new JPanel(new BorderLayout());
        questionPanel.setBackground(new Color(235, 245, 255));
        questionPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        lblQuestion = new JLabel("Question appears here...");
        lblQuestion.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblQuestion.setForeground(new Color(0, 51, 102));
        lblQuestion.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        questionPanel.add(lblQuestion, BorderLayout.NORTH);

        // Options
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 15, 15));
        optionsPanel.setBackground(new Color(235, 245, 255));
        rbA = new JRadioButton(); rbB = new JRadioButton(); rbC = new JRadioButton(); rbD = new JRadioButton();
        rbA.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        rbB.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        rbC.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        rbD.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        rbA.setBackground(new Color(235, 245, 255));
        rbB.setBackground(new Color(235, 245, 255));
        rbC.setBackground(new Color(235, 245, 255));
        rbD.setBackground(new Color(235, 245, 255));

        group = new ButtonGroup();
        group.add(rbA); group.add(rbB); group.add(rbC); group.add(rbD);
        optionsPanel.add(rbA); optionsPanel.add(rbB); optionsPanel.add(rbC); optionsPanel.add(rbD);

        questionPanel.add(optionsPanel, BorderLayout.CENTER);
        add(questionPanel, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel();
        footer.setBackground(new Color(0, 102, 204));
        btnNext = new JButton("Next ➡");
        btnNext.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btnNext.setBackground(Color.WHITE); btnNext.setForeground(new Color(0, 102, 204));
        btnNext.setFocusPainted(false);
        btnNext.addActionListener(evt -> checkAnswer());
        footer.add(btnNext);
        add(footer, BorderLayout.SOUTH);
    }

    private void connectDatabase() {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/InterviewQuestion_Generator", "root", "Admin#");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Database connection failed!");
        }
    }

    private void loadQuestions() {
        questionIds = new ArrayList<>();
        String tableName = type.equalsIgnoreCase("Domain") ? "domain_questions" : "company_questions";
        String columnName = type.equalsIgnoreCase("Domain") ? "domain" : "company";

        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT id FROM " + tableName + " WHERE " + columnName + " = ?")) {
            ps.setString(1, selected);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) questionIds.add(rs.getInt("id"));

            if (questionIds.isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠ No questions available for " + selected);
                dispose();
                new UserHomePage(username).setVisible(true);
                return;
            }

            Collections.shuffle(questionIds);
            showQuestion(currentIndex);

        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void showQuestion(int index) {
        String tableName = type.equalsIgnoreCase("Domain") ? "domain_questions" : "company_questions";

        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE id=?")) {
            ps.setInt(1, questionIds.get(index));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                lblQuestion.setText("<html><b>Q" + (index + 1) + ":</b> " + rs.getString("question") + "</html>");
                rbA.setText("A. " + rs.getString("option_a"));
                rbB.setText("B. " + rs.getString("option_b"));
                rbC.setText("C. " + rs.getString("option_c"));
                rbD.setText("D. " + rs.getString("option_d"));

                rbA.setActionCommand("A"); rbB.setActionCommand("B");
                rbC.setActionCommand("C"); rbD.setActionCommand("D");

                btnNext.putClientProperty("correctAnswer", rs.getString("correct_option"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void checkAnswer() {
        String correct = (String) btnNext.getClientProperty("correctAnswer");
        String selectedOption = group.getSelection() != null ? group.getSelection().getActionCommand() : "";
        if (selectedOption.equals(correct)) score++;

        currentIndex++;
        group.clearSelection();

        if (currentIndex < questionIds.size()) showQuestion(currentIndex);
        else {
            saveScore();
            JOptionPane.showMessageDialog(this, "🎉 Test Completed!\nYour Score: " + score + "/" + questionIds.size());
            dispose();
            new UserHomePage(username).setVisible(true);
        }
    }

    private void saveScore() {
        try {
            // Check user exists
            int userId = 0;
            PreparedStatement psUser = conn.prepareStatement("SELECT id FROM users WHERE username=?");
            psUser.setString(1, username);
            ResultSet rs = psUser.executeQuery();
            if (rs.next()) userId = rs.getInt("id");
            else { JOptionPane.showMessageDialog(this, "User not found!"); return; }

            PreparedStatement psInsert = conn.prepareStatement(
                    "INSERT INTO tests(user_id, selected, type, score) VALUES(?,?,?,?)");
            psInsert.setInt(1, userId);
            psInsert.setString(2, selected);
            psInsert.setString(3, type);
            psInsert.setInt(4, score);
            psInsert.executeUpdate();

        } catch (SQLException e) { e.printStackTrace(); }
    }

   

    

  
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

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
            java.util.logging.Logger.getLogger(TakeTestPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TakeTestPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TakeTestPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TakeTestPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              SwingUtilities.invokeLater(() -> new TakeTestPage("Dharunika", "Java", "Domain"));
            }
        });
    }

    // Variables declaration - do not modify                     
    // End of variables declaration                   
}
