/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Dharunika
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

public class SignupPage extends javax.swing.JFrame {

    private Connection conn;

    public SignupPage() {
        initComponents();        // NetBeans GUI auto-generated
        connectToDatabase();     // Connect to DB
        setupActions();          // Button & checkbox actions

        lblPhoneOptional.setText("Optional");
        lblPhoneOptional.setForeground(java.awt.Color.RED);
    }

    //  Connect to database
    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/InterviewQuestion_Generator?useSSL=false&allowPublicKeyRetrieval=true";
            String user = "root";
            String password = "Admin#";
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Database connected successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database connection failed!\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    //  Signup logic
    private void signupUser() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String confirmPassword = new String(txtConfirmPassword.getPassword()).trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();

        Border redBorder = BorderFactory.createLineBorder(java.awt.Color.RED, 2);
        Border greenBorder = BorderFactory.createLineBorder(java.awt.Color.GREEN, 2);

        boolean valid = true;
        StringBuilder errorMessage = new StringBuilder();

        // --- Validations ---
        if (username.isEmpty()) { txtUsername.setBorder(redBorder); errorMessage.append("- Enter username\n"); valid = false; } else { txtUsername.setBorder(greenBorder); }
        if (password.isEmpty()) { txtPassword.setBorder(redBorder); errorMessage.append("- Enter password\n"); valid = false; } else { txtPassword.setBorder(greenBorder); }
        if (confirmPassword.isEmpty() || !password.equals(confirmPassword)) { txtConfirmPassword.setBorder(redBorder); errorMessage.append("- Passwords do not match\n"); valid = false; } else { txtConfirmPassword.setBorder(greenBorder); }
        if (email.isEmpty() || !Pattern.matches("^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", email)) { txtEmail.setBorder(redBorder); errorMessage.append("- Enter valid email\n"); valid = false; } else { txtEmail.setBorder(greenBorder); }
        if (!phone.isEmpty() && !Pattern.matches("\\d{10}", phone)) { txtPhone.setBorder(redBorder); errorMessage.append("- Phone must be 10 digits\n"); valid = false; } else { txtPhone.setBorder(greenBorder); }
        if (!chkTerms.isSelected()) { errorMessage.append("- Accept Terms & Conditions\n"); valid = false; }

        if (!valid) {
            JOptionPane.showMessageDialog(this, errorMessage.toString(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            return; // Stop signup
        }

        // --- Check for duplicate username ONLY during signup ---
        try {
            String checkQuery = "SELECT 1 FROM usersignup WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Username '" + username + "' already exists. Choose another one.", "Duplicate Username", JOptionPane.WARNING_MESSAGE);
                txtUsername.setBorder(redBorder);
                return;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error checking username!\n" + ex.getMessage());
            ex.printStackTrace();
            return;
        }

        // --- Insert into DB ---
        try {
            String query = "INSERT INTO usersignup(username, password, email, phone) VALUES(?, ?, ?, ?)";
            PreparedStatement pt = conn.prepareStatement(query);
            pt.setString(1, username);
            pt.setString(2, password);
            pt.setString(3, email);
            pt.setString(4, phone.isEmpty() ? null : phone);

            pt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Signup successful! Please login.");
            dispose();  // Close signup page
            new LoginPage().setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Signup failed!\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    //  Reset fields
    private void resetFields() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtConfirmPassword.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        chkTerms.setSelected(false);
        chkShowPassword.setSelected(false);

        Border defaultBorder = BorderFactory.createLineBorder(java.awt.Color.GRAY, 1);
        txtUsername.setBorder(defaultBorder);
        txtPassword.setBorder(defaultBorder);
        txtConfirmPassword.setBorder(defaultBorder);
        txtEmail.setBorder(defaultBorder);
        txtPhone.setBorder(defaultBorder);

        txtPassword.setEchoChar('*');
        txtConfirmPassword.setEchoChar('*');
    }

    //  Show password toggle
    private void toggleShowPassword() {
        if (chkShowPassword.isSelected()) {
            txtPassword.setEchoChar((char)0);
            txtConfirmPassword.setEchoChar((char)0);
        } else {
            txtPassword.setEchoChar('*');
            txtConfirmPassword.setEchoChar('*');
        }
    }

    //  Setup actions
    private void setupActions() {
        btnSignup.addActionListener(evt -> signupUser());
        btnReset.addActionListener(evt -> resetFields());
        chkShowPassword.addActionListener(evt -> toggleShowPassword());
    }





    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        btnSignup = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        chkTerms = new javax.swing.JCheckBox();
        chkShowPassword = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        lblPhoneOptional = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        lblPhone = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        txtConfirmPassword = new javax.swing.JPasswordField();
        lblConfirmPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        lblPassword = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblUsername = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSignup.setText("Signup");
        btnSignup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignupActionPerformed(evt);
            }
        });
        getContentPane().add(btnSignup, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 640, -1, -1));

        btnReset.setText("Reset all fields");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        getContentPane().add(btnReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 640, -1, -1));

        chkTerms.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        chkTerms.setText("I agree to Terms & Conditions");
        chkTerms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTermsActionPerformed(evt);
            }
        });
        getContentPane().add(chkTerms, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 540, -1, -1));

        chkShowPassword.setText("Show password");
        chkShowPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkShowPasswordActionPerformed(evt);
            }
        });
        getContentPane().add(chkShowPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 250, -1, -1));

        jButton1.setText("Back to login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

        lblPhoneOptional.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lblPhoneOptional.setForeground(new java.awt.Color(255, 0, 0));
        lblPhoneOptional.setText("Optional*");
        getContentPane().add(lblPhoneOptional, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 470, 70, -1));
        getContentPane().add(txtPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 430, 150, 30));

        lblPhone.setText("Phone number");
        getContentPane().add(lblPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 430, -1, -1));

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });
        getContentPane().add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 362, 150, 30));

        lblEmail.setText("Email-ID");
        getContentPane().add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 370, -1, -1));

        txtConfirmPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtConfirmPasswordActionPerformed(evt);
            }
        });
        getContentPane().add(txtConfirmPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 300, 150, 30));

        lblConfirmPassword.setText("Confirm password");
        getContentPane().add(lblConfirmPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 310, -1, -1));

        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });
        getContentPane().add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 240, 150, 30));

        lblPassword.setText("Password");
        getContentPane().add(lblPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 250, -1, -1));
        getContentPane().add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 190, 150, 30));

        lblUsername.setText("Username");
        getContentPane().add(lblUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 200, -1, -1));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitle.setText("Create Your Account");
        getContentPane().add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 90, 240, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/trendy-soft-retro-gradient-background-design-minimalistic-soft-gradient-in-pastel-colors-elegant-soft-blur-texture-colorful-mesh-gradient-background-vector.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 770));

        pack();
    }// </editor-fold>                        

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void txtConfirmPasswordActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        // TODO add your handling code here:
    }                                                  

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        new LoginPage().setVisible(true);
    }                                        

    private void chkTermsActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void btnSignupActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        signupUser();
    }                                         

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        resetFields();
    }                                        

    private void chkShowPasswordActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // TODO add your handling code here:
        toggleShowPassword();
    }                                               

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
            java.util.logging.Logger.getLogger(SignupPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SignupPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SignupPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SignupPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SignupPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSignup;
    private javax.swing.JCheckBox chkShowPassword;
    private javax.swing.JCheckBox chkTerms;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblConfirmPassword;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblPhoneOptional;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPasswordField txtConfirmPassword;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration                   
}
