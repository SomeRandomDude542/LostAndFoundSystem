package laf;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SignupFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    
    private JPanel signupFrame;
    private JTextField txfUsername, txfEmail;
    private JPasswordField txfPassword, txfConfirmPassword;
    private JLabel lblSignup, lblPasswordStrength;
    private JButton btnSignup, btnBack;
    private JCheckBox checkSignup;
    private JPanel panel;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                SignupFrame frame = new SignupFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public SignupFrame() {
        setTitle("Lost And Found");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 734, 520); // Made taller for confirm password field

        signupFrame = new JPanel();
        signupFrame.setBackground(new Color(64, 0, 0));
        signupFrame.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(signupFrame);
        signupFrame.setLayout(null);
        
        JPanel panel_1 = RoundedComponents.createRoundedPanelWithBackground(
            "/image/Signup Design.png",           
            Color.BLACK,              // Border color (or new Color(64, 0, 0) for maroon)
    	    1,                        // Border width in pixels
    	    15,                       // Corner radius
    	    RoundedComponents.LEFT_CORNERS
        );
        panel_1.setBounds(57, 42, 282, 410); 
        panel_1.setLayout(null);        
        signupFrame.add(panel_1);
        signupFrame.setOpaque(true);

        JPanel panel = RoundedComponents.createRoundedPanel(
        		Color.WHITE,               // background
          	     Color.BLACK,           // border
          	     1,
          	     15,  
               RoundedComponents.RIGHT_CORNERS
        );
        panel.setBackground(new Color(255, 255, 255));
        panel.setBounds(339, 42, 322, 410);
        panel.setLayout(null);
        signupFrame.add(panel);
        signupFrame.setOpaque(true);

        // Title
        lblSignup = new JLabel("Sign up");
        lblSignup.setBounds(109, 20, 104, 39);
        lblSignup.setBackground(new Color(255, 255, 255));
        panel.add(lblSignup);
        lblSignup.setForeground(new Color(64, 0, 0));
        lblSignup.setFont(new Font("Tahoma", Font.BOLD, 26));
        lblSignup.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Username input
        txfUsername = RoundedComponents.createRoundedTextField(
            Color.WHITE,
            Color.BLACK,
            Color.BLACK,
            10,
            "Username"
        );
        txfUsername.setBounds(47, 80, 227, 30);
        txfUsername.setFont(new Font("Tahoma", Font.PLAIN, 18));
        panel.add(txfUsername);
        
        // Email input
        txfEmail = RoundedComponents.createRoundedTextField(
            Color.WHITE,
            Color.BLACK,
            Color.BLACK,
            10,
            "Email"
        );
        txfEmail.setBounds(47, 135, 227, 30);
        txfEmail.setFont(new Font("Tahoma", Font.PLAIN, 18));
        panel.add(txfEmail);
                
        // Password input
        txfPassword = RoundedComponents.createRoundedPasswordField(
            Color.WHITE,
            Color.BLACK,
            Color.BLACK,
            10,
            "Password"
        );
        txfPassword.setBounds(47, 190, 227, 30);
        txfPassword.setFont(new Font("Tahoma", Font.PLAIN, 18));
        panel.add(txfPassword);
        
        // Confirm Password input (NEW)
        txfConfirmPassword = RoundedComponents.createRoundedPasswordField(
            Color.WHITE,
            Color.BLACK,
            Color.BLACK,
            10,
            "Confirm Password"
        );
        txfConfirmPassword.setBounds(47, 245, 227, 30);
        txfConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 18));
        panel.add(txfConfirmPassword);
        
        // Password strength indicator (NEW)
        lblPasswordStrength = new JLabel("");
        lblPasswordStrength.setFont(new Font("Tahoma", Font.ITALIC, 10));
        lblPasswordStrength.setBounds(47, 223, 227, 15);
        panel.add(lblPasswordStrength);
        
        // Real-time password strength checker
        txfPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String password = new String(txfPassword.getPassword());
                String strengthError = PasswordHasher.validatePasswordStrength(password);
                if (password.isEmpty()) {
                    lblPasswordStrength.setText("");
                    lblPasswordStrength.setForeground(Color.BLACK);
                } else if (strengthError == null) {
                    lblPasswordStrength.setText("✓ Strong password");
                    lblPasswordStrength.setFont(getEmojiFont(Font.ITALIC, 10)); // Add this
                    lblPasswordStrength.setForeground(new Color(0, 128, 0));
                } else {
                    lblPasswordStrength.setText("⚠ " + strengthError);
                    lblPasswordStrength.setFont(getEmojiFont(Font.ITALIC, 10)); // Add this
                    lblPasswordStrength.setForeground(new Color(220, 53, 69));
                }
            }
        });
                        
        // Eye toggle for password
        JButton eyeToggle = RoundedComponents.createEyeToggleButton(
            Color.WHITE,
            24
        );
        eyeToggle.setBounds(280, 191, 24, 26);
        panel.add(eyeToggle);

        eyeToggle.addActionListener(e -> {
            eyeToggle.setSelected(!eyeToggle.isSelected());
            if(eyeToggle.isSelected()) {
                txfPassword.setEchoChar((char) 0);
                txfConfirmPassword.setEchoChar((char) 0);
            } else {
                txfPassword.setEchoChar('•');
                txfConfirmPassword.setEchoChar('•');
            }
        });
        
        // Back button
        btnBack = RoundedComponents.createRoundedButton(
            "Return",  
            new Color(0, 0, 0),       // background
    	    Color.WHITE,                // text
    	    Color.BLACK,            // border
    	     7,                         // radius
    	     RoundedComponents.ALL_CORNERS
        );  
        btnBack.setBounds(57, 325, 100, 28);
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(btnBack);
        
        // Sign up button
        btnSignup = RoundedComponents.createRoundedButton(
            "Sign Up",
            new Color(122, 14, 26),
            new Color(255, 255, 255),
    	    Color.BLACK,            // border
            7,
   	        RoundedComponents.ALL_CORNERS

        );
        btnSignup.setBounds(167, 325, 100, 28);
        btnSignup.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(btnSignup);
        btnSignup.addActionListener(e -> handleSignup());

        btnBack.addActionListener(e -> {
            SignupFrame.this.dispose();
            new LoginWindow().setVisible(true);
        });
        
        // Icons
        JLabel lblNewLabel_1 = new JLabel();
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBackground(new Color(255, 255, 255));
        lblNewLabel_1.setBounds(20, 137, 25, 25);
        try {
            ImageIcon originalIcon = new ImageIcon(LoginWindow.class.getResource("/image/Email Icon.png"));
            Image scaledImage = originalIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            lblNewLabel_1.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            e.printStackTrace();
            lblNewLabel_1.setText("🔒");
        }
        panel.add(lblNewLabel_1);
        
        JLabel lblNewLabel2 = new JLabel();
        lblNewLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel2.setBackground(new Color(255, 255, 255));
        lblNewLabel2.setBounds(20, 82, 25, 25);
        try {
            ImageIcon originalIcon = new ImageIcon(LoginWindow.class.getResource("/image/UsernameLogo.png"));
            Image scaledImage = originalIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            lblNewLabel2.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            e.printStackTrace();
            lblNewLabel2.setText("👤");
        }
        panel.add(lblNewLabel2);
        
        JLabel lblNewLabel3 = new JLabel();
        lblNewLabel3.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel3.setBackground(new Color(255, 255, 255));
        lblNewLabel3.setBounds(20, 192, 25, 25);
        try {
            ImageIcon originalIcon = new ImageIcon(LoginWindow.class.getResource("/image/Password Logo.png"));
            Image scaledImage = originalIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            lblNewLabel3.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            e.printStackTrace();
            lblNewLabel3.setText("📧");
        }
        panel.add(lblNewLabel3);

        panel.setFocusable(true);
        panel.requestFocusInWindow();
    }

    private void handleSignup() {
        String username = txfUsername.getText().trim();
        String email = txfEmail.getText().trim();
        String password = new String(txfPassword.getPassword()).trim();
        String confirmPassword = new String(txfConfirmPassword.getPassword()).trim();

        // 1. Check empty fields
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Please fill in all fields.",
                    "Signup Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Validate username (no special characters, SQL injection prevention)
        if (!isValidUsername(username)) {
            JOptionPane.showMessageDialog(null,
                    "Username can only contain letters, numbers, and underscores.\n" +
                    "Must be 3-20 characters long.",
                    "Invalid Username",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. Validate email format
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(null,
                    "Please enter a valid email address.\nExample: user@gmail.com, user@yahoo.com",
                    "Invalid Email",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 4. Validate password strength
        String strengthError = PasswordHasher.validatePasswordStrength(password);
        if (strengthError != null) {
            JOptionPane.showMessageDialog(null,
                    strengthError + "\n\nPassword Requirements:\n" +
                    "• At least 8 characters\n" +
                    "• One uppercase letter\n" +
                    "• One lowercase letter\n" +
                    "• One number",
                    "Weak Password",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 5. Check if passwords match
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null,
                    "Passwords do not match!",
                    "Password Mismatch",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 6. Check if username already exists
        try (Connection conn = SQLiteConnection.connect()) {
            String checkSql = "SELECT username FROM users WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                JOptionPane.showMessageDialog(null,
                        "Username already exists. Please choose another one.",
                        "Username Taken",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error checking username: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 7. Hash password and INSERT INTO DATABASE
        try (Connection conn = SQLiteConnection.connect()) {
            String hashedPassword = PasswordHasher.hashPassword(password);
            
            String sql = "INSERT INTO users(username, email, password, account_status) VALUES(?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, email);
            pst.setString(3, hashedPassword); // ✅ SECURE: Hashed password
            pst.setString(4, "active"); // Set account as active by default
            pst.executeUpdate();

            System.out.println("✅ USER ADDED SECURELY: " + username);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error creating account: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // SUCCESS POPUP
        String[] options = {"Go to Login", "Create Another Account"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Account created successfully!\nYour password is securely encrypted.",
                "Signup Success",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            SignupFrame.this.dispose();
            new LoginWindow().setVisible(true);
        } else if (choice == 1) {
            txfUsername.setText("");
            txfEmail.setText("");
            txfPassword.setText("");
            txfConfirmPassword.setText("");
            lblPasswordStrength.setText("");
        }
    }

    private boolean isValidUsername(String username) {
        // Only allow alphanumeric and underscore, 3-20 characters
        return username != null && 
               username.matches("^[a-zA-Z0-9_]{3,20}$");
    }

    private boolean isValidEmail(String email) {
        if (email == null || !email.contains("@")) {
            return false;
        }
        
        String[] validDomains = {
            "@gmail.com",
            "@yahoo.com",
            "@outlook.com",
            "@hotmail.com",
            "@icloud.com",
            "@mail.com"
        };
        
        String emailLower = email.toLowerCase();
        for (String domain : validDomains) {
            if (emailLower.endsWith(domain)) {
                String beforeAt = email.substring(0, email.indexOf("@"));
                return !beforeAt.isEmpty() && beforeAt.length() >= 3;
            }
        }
        
        return false;
    }
    
    private Font getEmojiFont(int style, int size) {
        String[] emojiFont = {
            "Segoe UI Emoji", "Apple Color Emoji", "Noto Color Emoji", "Segoe UI Symbol"
        };
        for (String fontName : emojiFont) {
            Font font = new Font(fontName, style, size);
            if (font.getFamily().equals(fontName)) {
                return font;
            }
        }
        return new Font("Dialog", style, size);
    }
}