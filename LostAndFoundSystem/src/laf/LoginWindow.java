package laf;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.ImageIcon;
import java.awt.Image;

public class LoginWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel LoginFrame;
    private JTextField UsernameInput;
    private JPasswordField PasswordInput;
    
    // Security: Login attempt tracking (prevents brute force)
    private int loginAttempts = 0;
    private static final int MAX_ATTEMPTS = 5;
    private long lockoutEndTime = 0;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LoginWindow frame = new LoginWindow();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public LoginWindow() {
        setForeground(new Color(0, 0, 0));
        setTitle("Lost And Found");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 734, 470);
        LoginFrame = new JPanel();
        LoginFrame.setBackground(new Color(64, 0, 0));
        LoginFrame.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(LoginFrame);
        LoginFrame.setLayout(null);
        
        JPanel panel_1 = RoundedComponents.createRoundedPanelWithBackground(
        	    "/image/Log in Page.png",
        	    Color.BLACK,              // Border color (or new Color(64, 0, 0) for maroon)
        	    1,                        // Border width in pixels
        	    15,                       // Corner radius
        	    RoundedComponents.LEFT_CORNERS  // Which corners to round
        	);
        panel_1.setBounds(57, 42, 282, 361);
        LoginFrame.add(panel_1);
        panel_1.setLayout(null);
        
        JPanel panel = RoundedComponents.createRoundedPanel(
        	 Color.WHITE,               // background
       	     Color.BLACK,           // border
       	     1,
       	     15,  
            RoundedComponents.RIGHT_CORNERS
        );
        panel.setBounds(339, 42, 322, 361);
        panel.setLayout(null);
        LoginFrame.add(panel);
        LoginFrame.setOpaque(true);
        
        JLabel lblNewLabel = new JLabel();
        lblNewLabel.setBackground(new Color(255, 255, 255));
        lblNewLabel.setBounds(34, 126, 25, 25);
        try {
            ImageIcon originalIcon = new ImageIcon(LoginWindow.class.getResource("/image/UsernameLogo.png"));
            Image scaledImage = originalIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            lblNewLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            e.printStackTrace();
            lblNewLabel.setText("👤");
        }
        panel.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel();
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBackground(new Color(255, 255, 255));
        lblNewLabel_1.setBounds(34, 193, 25, 25);
        try {
            ImageIcon originalIcon = new ImageIcon(LoginWindow.class.getResource("/image/Password Logo.png"));
            Image scaledImage = originalIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            lblNewLabel_1.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            e.printStackTrace();
            lblNewLabel_1.setText("🔒");
        }
        panel.add(lblNewLabel_1);

        PasswordInput = RoundedComponents.createRoundedPasswordField(
            Color.WHITE,
            Color.BLACK,
            Color.BLACK,
            10,
            "Password"
        );
        PasswordInput.setBounds(61, 191, 227, 30);
        PasswordInput.setFont(new Font("Tahoma", Font.PLAIN, 18));
        panel.add(PasswordInput);
        
        JButton Loginbtn = RoundedComponents.createRoundedButton(
        	    "Log In",
                new Color(122, 14, 26),       // background
        	    Color.WHITE,                // text
        	    Color.BLACK,            // border
        	     7,                         // radius
        	     RoundedComponents.ALL_CORNERS
        );
        Loginbtn.setBounds(55, 245, 100, 28);
        Loginbtn.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(Loginbtn);
                                                        
        UsernameInput = RoundedComponents.createRoundedTextField(
            Color.WHITE,
            Color.BLACK,
            Color.BLACK,
            10,
            "Username"
        );
        UsernameInput.setBounds(61, 124, 227, 30);
        UsernameInput.setFont(new Font("Tahoma", Font.PLAIN, 18));
        panel.add(UsernameInput);
                                                
        JLabel Login = new JLabel("Log In");
        Login.setBackground(new Color(255, 255, 255));
        Login.setBounds(109, 51, 104, 39);
        panel.add(Login);
        Login.setForeground(new Color(125, 0, 0));
        Login.setHorizontalAlignment(SwingConstants.CENTER);
        Login.setFont(new Font("Tahoma", Font.BOLD, 26));
                                                        
        JButton CreateAccbtn = RoundedComponents.createRoundedButton(
            "Sign Up",
            new Color(0, 0, 0),
    	    Color.WHITE,      
    	    Color.BLACK,
            7,  	 
           RoundedComponents.ALL_CORNERS

        );
        CreateAccbtn.setBounds(174, 245, 100, 28);
        CreateAccbtn.setFont(new Font("Tahoma", Font.BOLD, 16));       
        panel.add(CreateAccbtn);
                                                      
        // Eye toggle button for password visibility
        JButton eyeToggle = RoundedComponents.createEyeToggleButton(
            Color.WHITE,
            24
        );
        eyeToggle.setBounds(290, 192, 24, 26);
        panel.add(eyeToggle);

        eyeToggle.addActionListener(e -> {
            eyeToggle.setSelected(!eyeToggle.isSelected());
            if(eyeToggle.isSelected()) {
                PasswordInput.setEchoChar((char) 0);
            } else {
                PasswordInput.setEchoChar('•');
            }
        });
        
        CreateAccbtn.addActionListener(e -> {
            SignupFrame signup = new SignupFrame(); 
            signup.setVisible(true); 
            LoginWindow.this.dispose(); 
        });
        
        Loginbtn.addActionListener(e -> handleLogin());

        panel.setFocusable(true);
        panel.requestFocusInWindow();
    }
    
    private void handleLogin() {
        // Check if account is locked due to too many failed attempts
        if (System.currentTimeMillis() < lockoutEndTime) {
            long remainingSeconds = (lockoutEndTime - System.currentTimeMillis()) / 1000;
            JOptionPane.showMessageDialog(null, 
                "⚠️ Too many failed login attempts!\n" +
                "Please wait " + remainingSeconds + " seconds before trying again.",
                "Account Temporarily Locked", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String username = UsernameInput.getText().trim();
        String password = new String(PasswordInput.getPassword()).trim();

        // Basic validation
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Please enter both username and password", 
                "Login Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Database connection
        String url = "jdbc:sqlite:db/lostandfound.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            // First, get the user data
            String sql = "SELECT password, role, account_status, ban_reason, suspension_end_date FROM users WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                String role = rs.getString("role");
                String accountStatus = rs.getString("account_status");
                String banReason = rs.getString("ban_reason");
                String suspensionEndDate = rs.getString("suspension_end_date");

                // 1. Check if account is banned
                if ("banned".equalsIgnoreCase(accountStatus)) {
                    JOptionPane.showMessageDialog(null, 
                        "❌ Your account has been permanently banned.\n\n" +
                        "Reason: " + (banReason != null ? banReason : "Violation of terms"),
                        "Account Banned", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 2. Check if account is suspended
                if ("suspended".equalsIgnoreCase(accountStatus)) {
                    // Check if suspension has expired
                    if (suspensionEndDate != null) {
                        try {
                            LocalDateTime suspensionEnd = LocalDateTime.parse(
                                suspensionEndDate, 
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                            );
                            
                            if (LocalDateTime.now().isBefore(suspensionEnd)) {
                                JOptionPane.showMessageDialog(null, 
                                    "⏸️ Your account is temporarily suspended.\n\n" +
                                    "Reason: " + (banReason != null ? banReason : "Violation of terms") + "\n" +
                                    "Suspension ends: " + suspensionEndDate,
                                    "Account Suspended", 
                                    JOptionPane.WARNING_MESSAGE);
                                return;
                            } else {
                                // Suspension expired, reactivate account
                                String updateSql = "UPDATE users SET account_status = 'active', ban_reason = NULL, suspension_end_date = NULL WHERE username = ?";
                                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                                updateStmt.setString(1, username);
                                updateStmt.executeUpdate();
                                System.out.println("✅ Suspension expired, account reactivated: " + username);
                            }
                        } catch (Exception ex) {
                            System.err.println("Error parsing suspension date: " + ex.getMessage());
                        }
                    }
                }

                // 3. Verify password
                boolean isPasswordCorrect = false;
                
                // Check if password is hashed (contains ':') or plain text (for migration)
                if (storedPassword.contains(":")) {
                    // New hashed password
                    isPasswordCorrect = PasswordHasher.verifyPassword(password, storedPassword);
                } else {
                    // Old plain text password (for backward compatibility)
                    isPasswordCorrect = storedPassword.equals(password);
                    
                    // If login successful with plain text, migrate to hashed
                    if (isPasswordCorrect) {
                        String hashedPassword = PasswordHasher.hashPassword(password);
                        String updateSql = "UPDATE users SET password = ? WHERE username = ?";
                        PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                        updateStmt.setString(1, hashedPassword);
                        updateStmt.setString(2, username);
                        updateStmt.executeUpdate();
                        System.out.println("✅ Password migrated to secure hash for: " + username);
                    }
                }

                if (isPasswordCorrect) {
                    // Reset login attempts on successful login
                    loginAttempts = 0;
                    lockoutEndTime = 0;
                    
                    System.out.println("✅ LOGIN SUCCESS: " + username + " (Role: " + role + ")");

                    // Open appropriate dashboard based on role
                    if (role.equalsIgnoreCase("admin")) {
                        AdminDashboardWindow adminDashboard = new AdminDashboardWindow(username);
                        adminDashboard.setVisible(true);
                    } else {
                        DashboardWindow dashboard = new DashboardWindow(username);
                        dashboard.setVisible(true);
                    }

                    LoginWindow.this.dispose();
                } else {
                    handleFailedLogin();
                }
            } else {
                handleFailedLogin();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Database Error: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleFailedLogin() {
        loginAttempts++;
        int remainingAttempts = MAX_ATTEMPTS - loginAttempts;

        if (loginAttempts >= MAX_ATTEMPTS) {
            // Lock account for 5 minutes (300 seconds)
            lockoutEndTime = System.currentTimeMillis() + (5 * 60 * 1000);
            JOptionPane.showMessageDialog(null, 
                "❌ Too many failed login attempts!\n" +
                "Your account is temporarily locked for 5 minutes.\n\n" +
                "This is for your security.",
                "Account Locked", 
                JOptionPane.ERROR_MESSAGE);
            
            // Clear fields
            UsernameInput.setText("");
            PasswordInput.setText("");
        } else {
            JOptionPane.showMessageDialog(null, 
                "❌ Invalid username or password\n\n" +
                "Remaining attempts: " + remainingAttempts,
                "Login Error", 
                JOptionPane.ERROR_MESSAGE);
            
            // Only clear password, keep username
            PasswordInput.setText("");
            PasswordInput.requestFocus();
        }
        
        System.out.println("⚠️ FAILED LOGIN ATTEMPT " + loginAttempts + "/" + MAX_ATTEMPTS);
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