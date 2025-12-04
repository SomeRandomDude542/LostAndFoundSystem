package laf;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import java.io.File;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.Insets;

public class AdminPanel extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AdminPanel frame = new AdminPanel();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    


    public AdminPanel() {
        setFont(new Font("Dialog", Font.BOLD, 12));
        setTitle("Admin Panel");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 548, 358);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 142, 319);
        panel.setBackground(new Color(90, 14, 36));

        contentPane.add(panel);

        JLabel lblAdminLabel = new JLabel("Admin Panel");
        lblAdminLabel.setForeground(new Color(255, 255, 255));
        lblAdminLabel.setBackground(new Color(90, 14, 36));
        lblAdminLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblAdminLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblAdminLabel.setBounds(0, 11, 142, 30);
        panel.add(lblAdminLabel);

     // TAB PANE
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(143, -25, 389, 344);
        contentPane.add(tabbedPane);

        // TAB 1 - USERS 
        JPanel usersPanel = createViewUsersPanel();  
        tabbedPane.addTab("Users", usersPanel);

        // TAB 2 - REPORTS 
        JPanel reportsPanel = createReportsPanel();  
        tabbedPane.addTab("Reports", reportsPanel);      

     // TAB 3 - LOGS 
        JPanel logsPanel = createViewLogsPanel();  
        tabbedPane.addTab("Logs", logsPanel);

        // SIDE NAVIGATION BUTTONS
        JButton btnUsers = RoundedComponents.createRoundedButtonWithHover(
        	    "View Users",
        	    new Color(90, 14, 36),       // background
	    	    new Color(255, 200, 200),       // hover color
	    	    Color.WHITE,                    // text color
	    	    1,                              // radius
	    	    RoundedComponents.ALL_CORNERS
        	);
        	btnUsers.setFont(new Font("Tahoma", Font.BOLD, 15));
        	btnUsers.setBounds(0, 80, 145, 30);
        	btnUsers.addActionListener(e -> tabbedPane.setSelectedIndex(0));
        	panel.add(btnUsers);

        	JButton btnReports = RoundedComponents.createRoundedButtonWithHover(
        	    "View Reports",
        	    new Color(90, 14, 36),       // background
	    	    new Color(255, 200, 200),       // hover color
	    	    Color.WHITE,                    // text color
	    	    1,                              // radius
	    	    RoundedComponents.ALL_CORNERS
        	);
        	btnReports.setFont(new Font("Tahoma", Font.BOLD, 15));
        	btnReports.setBounds(0, 150, 145, 30);
        	btnReports.addActionListener(e -> tabbedPane.setSelectedIndex(1));
        	panel.add(btnReports);

        	JButton btnViewLogs = RoundedComponents.createRoundedButtonWithHover(
        	    "View Logs",
        	    new Color(90, 14, 36),       // background
	    	    new Color(255, 200, 200),       // hover color
	    	    Color.WHITE,                    // text color
	    	    1,                              // radius
	    	    RoundedComponents.ALL_CORNERS
        	);
        	btnViewLogs.setFont(new Font("Tahoma", Font.BOLD, 15));
        	btnViewLogs.setBounds(0, 220, 145, 30);
        	btnViewLogs.addActionListener(e -> tabbedPane.setSelectedIndex(2));
        	panel.add(btnViewLogs);

        	JButton btnReturn = RoundedComponents.createRoundedButtonWithHover(
        		    "Return",
        		    new Color(90, 14, 36),       // background
        		    new Color(255, 200, 200),      // cornflower blue hover color
        		    new Color(0, 123, 255),           // text
        		    1,                             // radius
        		    RoundedComponents.ALL_CORNERS
        		);
        		btnReturn.setFont(new Font("Tahoma", Font.BOLD, 14));
        		btnReturn.setBounds(0, 288, 145, 31);
        		btnReturn.addActionListener(e -> {
        		    new AdminDashboardWindow("admin").setVisible(true);
        		    dispose();
        		});
        		panel.add(btnReturn);
    }
    

    private JPanel createViewUsersPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("User Management");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(100, 10, 200, 30);
        panel.add(lblTitle);

        // Scroll pane for users list
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 50, 369, 240);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane);

        JPanel usersListPanel = new JPanel();
        usersListPanel.setLayout(new BoxLayout(usersListPanel, BoxLayout.Y_AXIS));
        usersListPanel.setBackground(Color.WHITE);
        scrollPane.setViewportView(usersListPanel);

        // Load users
        loadAllUsers(usersListPanel);

        // Refresh button
        JButton btnRefresh = RoundedComponents.createRoundedButton(
        	    "Refresh",
        	    new Color(90, 14, 36),
        	    Color.WHITE,                // text
        	     Color.BLACK,            // border
        	     10,                         // radius
        	     RoundedComponents.ALL_CORNERS
      	);
         btnRefresh.setFont(new Font("Tahoma", Font.BOLD, 12));  
         btnRefresh.setBounds(10, 290, 100, 25);
        btnRefresh.addActionListener(e -> loadAllUsers(usersListPanel));
        panel.add(btnRefresh);

        return panel;
    }

    private void loadAllUsers(JPanel containerPanel) {
        containerPanel.removeAll();

        try (Connection conn = SQLiteConnection.connect()) {
            String sql = "SELECT id, username, email, role, account_status FROM users ORDER BY id DESC";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pstmt.executeQuery();

            boolean hasUsers = false;
            while (rs.next()) {
                hasUsers = true;
                int userId = rs.getInt("id");
                String username = rs.getString("username");
                String email = rs.getString("email");
                String role = rs.getString("role");
                String status = rs.getString("account_status");            
                

                // Create user panel
                JPanel userPanel = new JPanel();
                userPanel.setLayout(null);
                userPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
                userPanel.setPreferredSize(new Dimension(340, 50));
                userPanel.setBackground(Color.WHITE);
                userPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

                // Username
                JLabel lblUsername = new JLabel(username);
                lblUsername.setFont(new Font("Tahoma", Font.BOLD, 12));
                lblUsername.setBounds(10, 5, 120, 20);
                userPanel.add(lblUsername);

                // Email
                JLabel lblEmail = new JLabel(email);
                lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 10));
                lblEmail.setBounds(10, 25, 150, 15);
                userPanel.add(lblEmail);

                // Role badge
                JLabel lblRole = RoundedComponents.createRoundedLabel(
                	    role.toUpperCase(),
                	    Color.WHITE,  // temporary color, will be changed below
                	    Color.WHITE,  // temporary color, will be changed below
                	    10  // radius
                	);
                	lblRole.setFont(new Font("Tahoma", Font.BOLD, 9));
                	lblRole.setBounds(165, 16, 50, 18);

                if (role.equals("admin")) {
                    lblRole.setBackground(new Color(220, 53, 69));
                    lblRole.setForeground(Color.WHITE);
                } else {
                    lblRole.setBackground(new Color(40, 167, 69));
                    lblRole.setForeground(Color.WHITE);
                }
                userPanel.add(lblRole);

                // Status indicator
                JLabel lblStatus = new JLabel("●");
                lblStatus.setFont(new Font("Tahoma", Font.BOLD, 16));
                lblStatus.setBounds(220, 13, 20, 20);
                switch (status) {
                    case "active":
                        lblStatus.setForeground(new Color(40, 167, 69));
                        lblStatus.setToolTipText("Active");
                        break;
                    case "suspended":
                        lblStatus.setForeground(new Color(255, 193, 7));
                        lblStatus.setToolTipText("Suspended");
                        break;
                    case "banned":
                        lblStatus.setForeground(new Color(220, 53, 69));
                        lblStatus.setToolTipText("Banned");
                        break;
                }
                userPanel.add(lblStatus);

                // Actions dropdown button
                JButton btnActions = RoundedComponents.createRoundedButton(
                	    "Actions ▼",
                	    new Color(90, 14, 36),
                	    Color.WHITE,                // text
                	     Color.BLACK,            // border
                	     10,                         // radius
                	     RoundedComponents.ALL_CORNERS
              	);
                btnActions.setFont(new Font("Tahoma", Font.BOLD, 10));  
                btnActions.setBounds(245, 12, 85, 25);
                
                btnActions.addActionListener(e -> {
                    showUserActionsMenu(btnActions, userId, username, email, role, status, containerPanel);
                });
                userPanel.add(btnActions);

                containerPanel.add(userPanel);
            }

            if (!hasUsers) {
                JLabel lblNoUsers = new JLabel("No users found");
                lblNoUsers.setFont(new Font("Tahoma", Font.ITALIC, 12));
                lblNoUsers.setForeground(Color.GRAY);
                containerPanel.add(lblNoUsers);
            }

        } catch (Exception ex) {
            System.err.println("Error loading users: " + ex.getMessage());
            ex.printStackTrace();
        }

        containerPanel.revalidate();
        containerPanel.repaint();
    }

    private void showUserActionsMenu(JButton sourceButton, int userId, String username, 
                                      String email, String role, String status, JPanel containerPanel) {
        JPopupMenu popup = new JPopupMenu();

        // Ban User
        JMenuItem itemBan = new JMenuItem("Ban User");
        itemBan.setFont(new Font("Tahoma", Font.PLAIN, 12));
        itemBan.addActionListener(e -> showBanUserDialog(userId, username, containerPanel));
        popup.add(itemBan);

        // Suspend User
        JMenuItem itemSuspend = new JMenuItem("Suspend User");
        itemSuspend.setFont(new Font("Tahoma", Font.PLAIN, 12));
        itemSuspend.addActionListener(e -> showSuspendUserDialog(userId, username, containerPanel));
        popup.add(itemSuspend);

        // Unban/Unsuspend
        if (!status.equals("active")) {
            JMenuItem itemRestore = new JMenuItem("Restore Account");
            itemRestore.setFont(new Font("Tahoma", Font.PLAIN, 12));
            itemRestore.addActionListener(e -> restoreUserAccount(userId, username, containerPanel));
            popup.add(itemRestore);
        }

        popup.addSeparator();

        // Change Role
        JMenuItem itemChangeRole = new JMenuItem("Change Role");
        itemChangeRole.setFont(new Font("Tahoma", Font.PLAIN, 12));
        itemChangeRole.addActionListener(e -> showChangeRoleDialog(userId, username, role, containerPanel));
        popup.add(itemChangeRole);

        popup.addSeparator();

        // View Details
        JMenuItem itemDetails = new JMenuItem("View Details");
        itemDetails.setFont(new Font("Tahoma", Font.PLAIN, 12));
        itemDetails.addActionListener(e -> showUserDetailsDialog(userId, username, email, role, status));
        popup.add(itemDetails);

        popup.show(sourceButton, 0, sourceButton.getHeight());
    }

    private void showBanUserDialog(int userId, String username, JPanel containerPanel) {
        JDialog dialog = new JDialog((Frame) null, "Ban User", true);
        dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(null);
        dialog.getContentPane().setLayout(null);

        JLabel lblWarning = new JLabel("<html><b>Ban user: " + username + "?</b></html>");
        lblWarning.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblWarning.setBounds(20, 20, 300, 25);
        dialog.getContentPane().add(lblWarning);

        JLabel lblReason = new JLabel("Reason:");
        lblReason.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblReason.setBounds(20, 55, 100, 20);
        dialog.getContentPane().add(lblReason);

        JTextField txtReason = RoundedComponents.createRoundedTextField(
        	    Color.WHITE,      // Background
        	    Color.BLACK,      // Text color
        	    Color.BLACK,       // Border color 
        	    10                // Radius
        	);
        	txtReason.setBounds(20, 75, 300, 25);
        	txtReason.setFont(new Font("Tahoma", Font.PLAIN, 12));
        dialog.getContentPane().add(txtReason);

        JButton btnConfirm = RoundedComponents.createRoundedButton(
        	    "Confirm Ban",
        	    new Color(220, 53, 69),
        	    Color.BLACK,                // text
        	     Color.BLACK,            // border
        	     10,                         // radius
        	     RoundedComponents.ALL_CORNERS
      	);
				btnConfirm.setFont(new Font("Tahoma", Font.BOLD, 12));  
        		btnConfirm.setBounds(20, 115, 140, 30);
        btnConfirm.addActionListener(e -> {
            String reason = txtReason.getText().trim();
            if (reason.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please provide a reason", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            banUser(userId, username, reason, containerPanel);
            dialog.dispose();
        });
        dialog.getContentPane().add(btnConfirm);

        JButton btnCancel = RoundedComponents.createRoundedButton(
        	    "Cancel",
        	    new Color(0, 0, 0),
        	    Color.WHITE,                // text
        	     Color.BLACK,            // border
        	     10,                         // radius
        	     RoundedComponents.ALL_CORNERS
      	);
        		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 12));  
        		btnCancel.setBounds(180, 115, 140, 30);
        btnCancel.addActionListener(e -> dialog.dispose());
        dialog.getContentPane().add(btnCancel);

        dialog.setVisible(true);
    }

    private void showSuspendUserDialog(int userId, String username, JPanel containerPanel) {
        JDialog dialog = new JDialog((Frame) null, "Suspend User", true);
        dialog.setSize(350, 220);
        dialog.setLocationRelativeTo(null);
        dialog.getContentPane().setLayout(null);

        JLabel lblWarning = new JLabel("<html><b>Suspend user: " + username + "?</b></html>");
        lblWarning.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblWarning.setBounds(20, 20, 300, 25);
        dialog.getContentPane().add(lblWarning);

        JLabel lblDays = new JLabel("Suspension Days:");
        lblDays.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblDays.setBounds(20, 55, 120, 20);
        dialog.getContentPane().add(lblDays);

        JComboBox<String> comboDays = new JComboBox<>(new String[]{"1", "3", "7", "14", "30"});
        comboDays.setBounds(140, 55, 80, 25);
        dialog.getContentPane().add(comboDays);

        JLabel lblReason = new JLabel("Reason:");
        lblReason.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblReason.setBounds(20, 80, 100, 20);
        dialog.getContentPane().add(lblReason);

        JTextField txtReason = RoundedComponents.createRoundedTextField(
        	    Color.WHITE,      // Background
        	    Color.BLACK,      // Text color
        	    Color.BLACK,       // Border color 
        	    10                // Radius
        	);
        	txtReason.setBounds(20, 100, 300, 25);
        	txtReason.setFont(new Font("Tahoma", Font.PLAIN, 12));
        dialog.getContentPane().add(txtReason);

        JButton btnConfirm = RoundedComponents.createRoundedButton(
        	    "Confirm Suspend",
        	    new Color(255, 193, 7),
        	    Color.WHITE,                // text
        	     Color.BLACK,            // border
        	     10,                         // radius
        	     RoundedComponents.ALL_CORNERS
      	);
        btnConfirm.setFont(new Font("Tahoma", Font.BOLD, 12));  
        btnConfirm.setBounds(20, 145, 140, 30);
        btnConfirm.addActionListener(e -> {
            String reason = txtReason.getText().trim();
            if (reason.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please provide a reason", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int days = Integer.parseInt((String) comboDays.getSelectedItem());
            suspendUser(userId, username, days, reason, containerPanel);
            dialog.dispose();
        });
        dialog.getContentPane().add(btnConfirm);

        JButton btnCancel = RoundedComponents.createRoundedButton(
        	    "Cancel",
        	    new Color(220, 53, 69),
        	    Color.WHITE,                // text
        	     Color.BLACK,            // border
        	     10,                         // radius
        	     RoundedComponents.ALL_CORNERS
      	);
        		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 12));  
        		btnCancel.setBounds(180, 145, 140, 30);
        btnCancel.addActionListener(e -> dialog.dispose());
        dialog.getContentPane().add(btnCancel);

        dialog.setVisible(true);
    }

    private void showChangeRoleDialog(int userId, String username, String currentRole, JPanel containerPanel) {
        JDialog dialog = new JDialog((Frame) null, "Change Role", true);
        dialog.setSize(350, 180);
        dialog.setLocationRelativeTo(null);
        dialog.getContentPane().setLayout(null);

        JLabel lblInfo = new JLabel("<html><b>Change role for: " + username + "</b></html>");
        lblInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblInfo.setBounds(20, 20, 300, 25);
        dialog.getContentPane().add(lblInfo);

        JLabel lblRole = new JLabel("New Role:");
        lblRole.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblRole.setBounds(20, 60, 100, 20);
        dialog.getContentPane().add(lblRole);

        JComboBox<String> comboRole = new JComboBox<>(new String[]{"user", "admin"});
        comboRole.setSelectedItem(currentRole);
        comboRole.setBounds(120, 60, 100, 25);
        dialog.getContentPane().add(comboRole);

        JButton btnConfirm = RoundedComponents.createRoundedButton(
        	    "Confirm",
        	    new Color(0, 123, 255),
        	    Color.WHITE,                // text
        	     Color.BLACK,            // border
        	     10,                         // radius
        	     RoundedComponents.ALL_CORNERS
      	);
        btnConfirm.setFont(new Font("Tahoma", Font.BOLD, 12));  
        		btnConfirm.setBounds(20, 100, 140, 30);
        btnConfirm.addActionListener(e -> {
            String newRole = (String) comboRole.getSelectedItem();
            changeUserRole(userId, username, newRole, containerPanel);
            dialog.dispose();
        });
        dialog.getContentPane().add(btnConfirm);

        JButton btnCancel = RoundedComponents.createRoundedButton(
        	    "Cancel",
        	    new Color(220, 53, 69),
        	    Color.BLACK,
        	    8
        		);
        		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 12));  
        		btnCancel.setBounds(180, 100, 140, 30);
        btnCancel.addActionListener(e -> dialog.dispose());
        dialog.getContentPane().add(btnCancel);

        dialog.setVisible(true);
    }

    private void showUserDetailsDialog(int userId, String username, String email, String role, String status) {
        JDialog dialog = new JDialog((Frame) null, "User Details", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(null);
        dialog.getContentPane().setLayout(null);

        JLabel lblTitle = new JLabel("User Information");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitle.setBounds(20, 20, 200, 25);
        lblTitle.setForeground(Color.BLACK);
        dialog.getContentPane().add(lblTitle);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setBounds(20, 60, 350, 150);
        
        StringBuilder details = new StringBuilder();
        details.append("User ID: ").append(userId).append("\n\n");
        details.append("Username: ").append(username).append("\n\n");
        details.append("Email: ").append(email).append("\n\n");
        details.append("Role: ").append(role.toUpperCase()).append("\n\n");
        details.append("Status: ").append(status.toUpperCase()).append("\n\n");
        
        // Get additional info from database
        try (Connection conn = SQLiteConnection.connect()) {
            String sql = "SELECT ban_reason, suspension_end_date FROM users WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            java.sql.ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String banReason = rs.getString("ban_reason");
                String suspensionEnd = rs.getString("suspension_end_date");
                
                if (banReason != null && !banReason.isEmpty()) {
                    details.append("Ban Reason: ").append(banReason).append("\n\n");
                }
                if (suspensionEnd != null && !suspensionEnd.isEmpty()) {
                    details.append("Suspension Ends: ").append(suspensionEnd).append("\n");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        textArea.setText(details.toString());
        dialog.getContentPane().add(textArea);

        JButton btnClose = RoundedComponents.createRoundedButton(
        	    "Close",
        	    new Color(128, 0, 0),
        	    Color.WHITE,                // text
        	     Color.BLACK,            // border
        	     10,                         // radius
        	     RoundedComponents.ALL_CORNERS
      	);
        		btnClose.setFont(new Font("Tahoma", Font.BOLD, 15));  
        		btnClose.setBounds(140, 220, 100, 30);
        btnClose.addActionListener(e -> dialog.dispose());
        dialog.getContentPane().add(btnClose);
        

        dialog.setVisible(true);
    }

    // Database operations
    private void banUser(int userId, String username, String reason, JPanel containerPanel) {
        try (Connection conn = SQLiteConnection.connect()) {
            String sql = "UPDATE users SET account_status = 'banned', ban_reason = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, reason);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, username + " has been banned.", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadAllUsers(containerPanel);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void suspendUser(int userId, String username, int days, String reason, JPanel containerPanel) {
        try (Connection conn = SQLiteConnection.connect()) {
            java.time.LocalDateTime endDate = java.time.LocalDateTime.now().plusDays(days);
            String endDateStr = endDate.toString();
            
            String sql = "UPDATE users SET account_status = 'suspended', ban_reason = ?, suspension_end_date = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, reason);
            pstmt.setString(2, endDateStr);
            pstmt.setInt(3, userId);
            pstmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, username + " has been suspended for " + days + " days.", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadAllUsers(containerPanel);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void restoreUserAccount(int userId, String username, JPanel containerPanel) {
        int confirm = JOptionPane.showConfirmDialog(null, 
            "Restore account for " + username + "?", 
            "Confirm Restore", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = SQLiteConnection.connect()) {
                String sql = "UPDATE users SET account_status = 'active', ban_reason = NULL, suspension_end_date = NULL WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, userId);
                pstmt.executeUpdate();
                
                JOptionPane.showMessageDialog(null, username + "'s account has been restored.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadAllUsers(containerPanel);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private void changeUserRole(int userId, String username, String newRole, JPanel containerPanel) {
        int confirm = JOptionPane.showConfirmDialog(null, 
            "Change " + username + "'s role to " + newRole + "?", 
            "Confirm Role Change", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = SQLiteConnection.connect()) {
                String sql = "UPDATE users SET role = ? WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, newRole);
                pstmt.setInt(2, userId);
                pstmt.executeUpdate();
                
                JOptionPane.showMessageDialog(null, username + " is now a " + newRole + ".", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadAllUsers(containerPanel);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
    
 // VIEW REPORTS CODE

    private JPanel createReportsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Reports & Items Management");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(60, 10, 270, 30);
        panel.add(lblTitle);

        // Filter buttons
        JButton btnShowAll = RoundedComponents.createRoundedButton(
        	    "All",
        	    new Color(90, 14, 36),
        	    Color.WHITE,                // text
        	     Color.BLACK,            // border
        	     10,                         // radius
        	     RoundedComponents.ALL_CORNERS
      	);
        		btnShowAll.setFont(new Font("Tahoma", Font.BOLD, 12));  
        		btnShowAll.setBounds(10, 45, 65, 25);
                panel.add(btnShowAll);

        JButton btnShowLost = RoundedComponents.createRoundedButton(
        	    "Lost",
        	    new Color(220, 53, 69),
        	    Color.WHITE,                // text
        	     Color.BLACK,            // border
        	     10,                         // radius
        	     RoundedComponents.ALL_CORNERS
      	);
        		btnShowLost.setFont(new Font("Tahoma", Font.BOLD, 12));  
        		btnShowLost.setBounds(80, 45, 70, 25);
                panel.add(btnShowLost);
                
        JButton btnShowFound = RoundedComponents.createRoundedButton(
        	    "Found",
        	    new Color(50, 205, 50),
        	    Color.WHITE,                // text
         	     Color.BLACK,            // border
         	     10,                         // radius
         	     RoundedComponents.ALL_CORNERS
       	);
        		btnShowFound.setFont(new Font("Tahoma", Font.BOLD, 12));  
        		btnShowFound.setBounds(155, 45, 80, 25);
            	panel.add(btnShowFound);


        JButton btnShowDeleted = RoundedComponents.createRoundedButton(
        	    "",  // Empty text
        	    new Color(128, 0, 0),
        	    Color.WHITE,                // text
         	     Color.BLACK,            // border
         	     10,                         // radius
         	     RoundedComponents.ALL_CORNERS
       	);
        	btnShowDeleted.setBounds(240, 45, 60, 25);

        	// Add image icon
        	ImageIcon icon = new ImageIcon(getClass().getResource("/image/Binlogo.png"));
        	Image scaledImage = icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        	btnShowDeleted.setIcon(new ImageIcon(scaledImage));

        	panel.add(btnShowDeleted);

        	JButton btnRefresh = RoundedComponents.createRoundedButton(
        		    "",  // Empty text
        		    new Color(0, 0, 0),
        		    Color.WHITE,                // text
             	     Color.BLACK,            // border
             	     10,                         // radius
             	     RoundedComponents.ALL_CORNERS
           	);
        		btnRefresh.setBounds(305, 45, 55, 25);

        		// Add image icon for REFRESH button
        		ImageIcon refreshIcon = new ImageIcon(getClass().getResource("/image/Refreshlogos.png"));
        		Image refreshScaledImage = refreshIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        		btnRefresh.setIcon(new ImageIcon(refreshScaledImage));

        		panel.add(btnRefresh);

        // Scroll pane for items
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 75, 369, 240);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane);

        JPanel itemsListPanel = new JPanel();
        itemsListPanel.setLayout(new BoxLayout(itemsListPanel, BoxLayout.Y_AXIS));
        itemsListPanel.setBackground(Color.WHITE);
        scrollPane.setViewportView(itemsListPanel);

        // Load all items by default
        loadAllReports(itemsListPanel, "all");

        // Button actions
        btnShowAll.addActionListener(e -> {
            btnShowAll.setBackground(new Color(0, 123, 255));
            btnShowAll.setForeground(Color.WHITE);
            btnShowLost.setBackground(null);
            btnShowLost.setForeground(Color.BLACK);
            btnShowFound.setBackground(null);
            btnShowFound.setForeground(Color.BLACK);
            btnShowDeleted.setBackground(null);
            btnShowDeleted.setForeground(Color.BLACK);
            loadAllReports(itemsListPanel, "all");
        });

        btnShowLost.addActionListener(e -> {
            btnShowLost.setBackground(new Color(0, 123, 255));
            btnShowLost.setForeground(Color.WHITE);
            btnShowAll.setBackground(null);
            btnShowAll.setForeground(Color.BLACK);
            btnShowFound.setBackground(null);
            btnShowFound.setForeground(Color.BLACK);
            btnShowDeleted.setBackground(null);
            btnShowDeleted.setForeground(Color.BLACK);
            loadAllReports(itemsListPanel, "lost");
        });

        btnShowFound.addActionListener(e -> {
            btnShowFound.setBackground(new Color(0, 123, 255));
            btnShowFound.setForeground(Color.WHITE);
            btnShowAll.setBackground(null);
            btnShowAll.setForeground(Color.BLACK);
            btnShowLost.setBackground(null);
            btnShowLost.setForeground(Color.BLACK);
            btnShowDeleted.setBackground(null);
            btnShowDeleted.setForeground(Color.BLACK);
            loadAllReports(itemsListPanel, "found");
        });

        btnShowDeleted.addActionListener(e -> {
            btnShowDeleted.setBackground(new Color(220, 53, 69));
            btnShowDeleted.setForeground(Color.WHITE);
            btnShowAll.setBackground(null);
            btnShowAll.setForeground(Color.BLACK);
            btnShowLost.setBackground(null);
            btnShowLost.setForeground(Color.BLACK);
            btnShowFound.setBackground(null);
            btnShowFound.setForeground(Color.BLACK);
            loadDeletedItems(itemsListPanel);
        });

        btnRefresh.addActionListener(e -> loadAllReports(itemsListPanel, "all"));

        // Set default button colors
        btnShowAll.setBackground(new Color(0, 123, 255));
        btnShowAll.setForeground(Color.WHITE);

        return panel;
    }

    private void loadAllReports(JPanel containerPanel, String filter) {
        containerPanel.removeAll();

        try (Connection conn = SQLiteConnection.connect()) {
            // Load lost items
            if (filter.equals("all") || filter.equals("lost")) {
                String sqlLost = "SELECT id, item_name, location_lost as location, date_lost as date, " +
                               "description, image_path, reported_by, status FROM lost_items " +
                               "WHERE (deleted_date IS NULL OR deleted_date = '') AND status != 'Resolved' " +
                               "ORDER BY date_reported DESC";
                PreparedStatement pstmt = conn.prepareStatement(sqlLost);
                java.sql.ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    JPanel itemPanel = createItemPanel(
                        "lost",
                        rs.getInt("id"),
                        rs.getString("item_name"),
                        rs.getString("location"),
                        rs.getString("date"),
                        rs.getString("description"),
                        rs.getString("image_path"),
                        rs.getString("reported_by"),
                        rs.getString("status"),
                        containerPanel
                    );
                    containerPanel.add(itemPanel);
                }
            }

            // Load found items
            if (filter.equals("all") || filter.equals("found")) {
                String sqlFound = "SELECT id, item_name, location_found as location, date_found as date, " +
                                "description, image_path, reported_by, status FROM found_items " +
                                "WHERE (deleted_date IS NULL OR deleted_date = '') AND status != 'Resolved' " +
                                "ORDER BY date_reported DESC";
                PreparedStatement pstmt = conn.prepareStatement(sqlFound);
                java.sql.ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    JPanel itemPanel = createItemPanel(
                        "found",
                        rs.getInt("id"),
                        rs.getString("item_name"),
                        rs.getString("location"),
                        rs.getString("date"),
                        rs.getString("description"),
                        rs.getString("image_path"),
                        rs.getString("reported_by"),
                        rs.getString("status"),
                        containerPanel
                    );
                    containerPanel.add(itemPanel);
                }
            }

            if (containerPanel.getComponentCount() == 0) {
                JLabel lblNoItems = new JLabel("No items found");
                lblNoItems.setFont(new Font("Tahoma", Font.ITALIC, 12));
                lblNoItems.setForeground(Color.GRAY);
                containerPanel.add(lblNoItems);
            }

        } catch (Exception ex) {
            System.err.println("Error loading reports: " + ex.getMessage());
            ex.printStackTrace();
        }

        containerPanel.revalidate();
        containerPanel.repaint();
    }

    private JPanel createItemPanel(String type, int id, String itemName, String location, 
                                   String date, String description, String imagePath, 
                                   String reportedBy, String status, JPanel parentPanel) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        panel.setPreferredSize(new Dimension(350, 120));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        // Image preview
        JLabel lblImage = new JLabel();
        lblImage.setBounds(5, 5, 80, 80);
        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        lblImage.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                File imgFile = new File(imagePath);
                if (imgFile.exists()) {
                    ImageIcon icon = new ImageIcon(imagePath);
                    Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                    lblImage.setIcon(new ImageIcon(img));
                } else {
                    lblImage.setText("No Image");
                    lblImage.setFont(new Font("Tahoma", Font.PLAIN, 9));
                }
            } catch (Exception e) {
                lblImage.setText("Error");
                lblImage.setFont(new Font("Tahoma", Font.PLAIN, 9));
            }
        } else {
            lblImage.setText("No Image");
            lblImage.setFont(new Font("Tahoma", Font.PLAIN, 9));
            lblImage.setBackground(new Color(245, 245, 245));
            lblImage.setOpaque(true);
        }
        panel.add(lblImage);

        // Type badge
        JLabel lblType = new JLabel(type.toUpperCase());
        lblType.setFont(new Font("Tahoma", Font.BOLD, 9));
        lblType.setHorizontalAlignment(SwingConstants.CENTER);
        lblType.setBounds(90, 5, 50, 15);
        lblType.setOpaque(true);
        lblType.setForeground(Color.WHITE);
        if (type.equals("lost")) {
            lblType.setBackground(new Color(220, 53, 69));
        } else {
            lblType.setBackground(new Color(40, 167, 69));
        }
        panel.add(lblType);

        // Item name
        JLabel lblItemName = new JLabel(itemName.length() > 20 ? itemName.substring(0, 20) + "..." : itemName);
        lblItemName.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblItemName.setBounds(90, 22, 180, 18);
        lblItemName.setToolTipText(itemName);
        panel.add(lblItemName);

        // Location
        JLabel lblLocation = new JLabel("📍 " + (location.length() > 18 ? location.substring(0, 18) + "..." : location));
        lblLocation.setFont(getEmojiFont(Font.PLAIN, 10));
        lblLocation.setBounds(90, 42, 180, 15);
        lblLocation.setToolTipText(location);
        panel.add(lblLocation);

        // Date
        JLabel lblDate = new JLabel("📅 " + date);
        lblDate.setFont(getEmojiFont(Font.PLAIN, 10));
        lblDate.setBounds(90, 58, 180, 15);
        panel.add(lblDate);

        // Reporter
        JLabel lblReporter = new JLabel("👤 " + reportedBy);
        lblReporter.setFont(getEmojiFont(Font.PLAIN, 9));
        lblReporter.setBounds(90, 74, 180, 15);
        panel.add(lblReporter);

        // View Description button
        JButton btnViewDesc = RoundedComponents.createRoundedButton(
        	    "Description",
        	    new Color(90, 14, 36),
        	    Color.WHITE,                // text
         	     Color.BLACK,            // border
         	     8,                         // radius
         	     RoundedComponents.ALL_CORNERS
       	);
        		btnViewDesc.setFont(new Font("Tahoma", Font.BOLD, 11));  // Add this line
        		btnViewDesc.setBounds(240, 5, 100, 28);
        btnViewDesc.setToolTipText("View Description");
        btnViewDesc.addActionListener(e -> showDescriptionDialog(itemName, description));
        panel.add(btnViewDesc);

     // Message button - ADD THIS NEW BUTTON
        JButton btnMessage = RoundedComponents.createRoundedButton(
        	    "Message",
        	    new Color(0, 123, 255),
        	    Color.WHITE,                // text
         	    Color.BLACK,            // border
         	     8,                         // radius
         	     RoundedComponents.ALL_CORNERS
       	);
        		btnMessage.setFont(new Font("Tahoma", Font.BOLD, 11));  
        		btnMessage.setBounds(240, 87, 100, 28);
        btnMessage.setToolTipText("Message Reporter");
        btnMessage.addActionListener(e -> {
            ChatWindow chatWindow = new ChatWindow("admin", id, type);
            chatWindow.setVisible(true);
        });
        panel.add(btnMessage);

        // Actions button - REPLACE THE OLD ONE WITH THIS (adjusted position)
        JButton btnActions = RoundedComponents.createRoundedButton(
        	    "Actions ▼",
        	    new Color(108, 117, 125),
        	    Color.WHITE,                // text
          	     Color.BLACK,            // border
          	     8,                         // radius
          	     RoundedComponents.ALL_CORNERS
        	);
        	btnActions.setBounds(240, 37, 100, 28);
        btnActions.addActionListener(e -> {
            showItemActionsMenu(btnActions, type, id, itemName, parentPanel);
        });
        panel.add(btnActions);

        // Status label
        JLabel lblStatus = new JLabel(status);
        lblStatus.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblStatus.setBounds(267, 68, 75, 15);
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
        if (status.equals("Pending")) {
            lblStatus.setForeground(new Color(255, 193, 7));
        } else if (status.equals("Resolved")) {
            lblStatus.setForeground(new Color(40, 167, 69));
        }
        panel.add(lblStatus);

        return panel;
    }

    private void showDescriptionDialog(String itemName, String description) {
        JDialog dialog = new JDialog((Frame) null, "Description - " + itemName, true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(null);
        dialog.getContentPane().setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea(description);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Tahoma", Font.PLAIN, 12));
        textArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(textArea);
        dialog.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JButton btnClose = RoundedComponents.createRoundedButton(
       	     "Close",
       	     new Color(220, 53, 69),    // background
       	     Color.WHITE,                // text
       	     Color.BLACK,            // border
       	     10,                         // radius
       	     RoundedComponents.ALL_CORNERS
       	 );        btnClose.addActionListener(e -> dialog.dispose());
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(btnClose);
        dialog.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void showItemActionsMenu(JButton sourceButton, String type, int id, String itemName, JPanel parentPanel) {
        JPopupMenu popup = new JPopupMenu();

        // Mark as Resolved
        JMenuItem itemResolve = new JMenuItem("✓ Mark as Resolved");
        itemResolve.setFont(getEmojiFont(Font.PLAIN, 12));
        itemResolve.addActionListener(e -> markItemResolved(type, id, itemName, parentPanel));
        popup.add(itemResolve);

        popup.addSeparator();

        // Delete Report
        JMenuItem itemDelete = new JMenuItem("🗑 Delete Report");
        itemDelete.setFont(getEmojiFont(Font.PLAIN, 12));
        itemDelete.setForeground(new Color(220, 53, 69));
        itemDelete.addActionListener(e -> deleteReport(type, id, itemName, parentPanel));
        popup.add(itemDelete);

        popup.show(sourceButton, 0, sourceButton.getHeight());
    }

    private void markItemResolved(String type, int id, String itemName, JPanel parentPanel) {
        int confirm = JOptionPane.showConfirmDialog(null,
            "Mark '" + itemName + "' as resolved?",
            "Confirm Resolution",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = SQLiteConnection.connect()) {
                String table = type.equals("lost") ? "lost_items" : "found_items";
                String sql = "UPDATE " + table + " SET status = 'Resolved' WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, id);
                pstmt.executeUpdate();

                // ✅ ADD THIS - Get the reporter's username to send notification
                String sqlGetReporter = "SELECT reported_by FROM " + table + " WHERE id = ?";
                PreparedStatement reporterStmt = conn.prepareStatement(sqlGetReporter);
                reporterStmt.setInt(1, id);
                java.sql.ResultSet rsReporter = reporterStmt.executeQuery();

                if (rsReporter.next()) {
                    String reporter = rsReporter.getString("reported_by");
                    
                    // Send notification to reporter
                    NotificationManager.createNotification(
                        reporter,
                        "Item Marked as Resolved",
                        "Your " + type + " item report '" + itemName + "' has been marked as resolved by admin.",
                        "resolved",
                        id
                    );
                }

                JOptionPane.showMessageDialog(null, "Item marked as resolved!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadAllReports(parentPanel, "all");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private void deleteReport(String type, int id, String itemName, JPanel parentPanel) {
        int confirm = JOptionPane.showConfirmDialog(null,
            "Delete report for '" + itemName + "'?\n\nThis will move it to the recycle bin for 3 days.",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = SQLiteConnection.connect()) {
                String table = type.equals("lost") ? "lost_items" : "found_items";
                String deletedDate = java.time.LocalDateTime.now().toString();
                String sql = "UPDATE " + table + " SET deleted_date = ? WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, deletedDate);
                pstmt.setInt(2, id);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Report moved to recycle bin!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadAllReports(parentPanel, "all");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private void loadDeletedItems(JPanel containerPanel) {
        containerPanel.removeAll();

        try (Connection conn = SQLiteConnection.connect()) {
            // Load deleted lost items
            String sqlLost = "SELECT id, item_name, deleted_date FROM lost_items WHERE deleted_date IS NOT NULL AND deleted_date != ''";
            PreparedStatement pstmt = conn.prepareStatement(sqlLost);
            java.sql.ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                JPanel binPanel = createBinItemPanel(
                    "lost",
                    rs.getInt("id"),
                    rs.getString("item_name"),
                    rs.getString("deleted_date"),
                    containerPanel
                );
                containerPanel.add(binPanel);
            }

            // Load deleted found items
            String sqlFound = "SELECT id, item_name, deleted_date FROM found_items WHERE deleted_date IS NOT NULL AND deleted_date != ''";
            pstmt = conn.prepareStatement(sqlFound);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                JPanel binPanel = createBinItemPanel(
                    "found",
                    rs.getInt("id"),
                    rs.getString("item_name"),
                    rs.getString("deleted_date"),
                    containerPanel
                );
                containerPanel.add(binPanel);
            }

            if (containerPanel.getComponentCount() == 0) {
                JLabel lblEmpty = new JLabel("Recycle bin is empty");
                lblEmpty.setFont(new Font("Tahoma", Font.ITALIC, 12));
                lblEmpty.setForeground(Color.GRAY);
                containerPanel.add(lblEmpty);
            }

        } catch (Exception ex) {
            System.err.println("Error loading deleted items: " + ex.getMessage());
            ex.printStackTrace();
        }

        containerPanel.revalidate();
        containerPanel.repaint();
    }

    private JPanel createBinItemPanel(String type, int id, String itemName, String deletedDate, JPanel parentPanel) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        panel.setPreferredSize(new Dimension(350, 50));
        panel.setBackground(new Color(255, 240, 240));
        panel.setBorder(BorderFactory.createLineBorder(new Color(220, 53, 69), 1));

        // Type & Name
        JLabel lblInfo = new JLabel(type.toUpperCase() + " - " + itemName);
        lblInfo.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblInfo.setBounds(10, 10, 200, 20);
        panel.add(lblInfo);

        // Deleted date
        try {
            java.time.LocalDateTime deleted = java.time.LocalDateTime.parse(deletedDate);
            java.time.LocalDateTime expiry = deleted.plusDays(3);
            long daysLeft = java.time.Duration.between(java.time.LocalDateTime.now(), expiry).toDays();
            
            JLabel lblExpiry = new JLabel("Expires in " + daysLeft + " days");
            lblExpiry.setFont(new Font("Tahoma", Font.ITALIC, 9));
            lblExpiry.setBounds(10, 30, 150, 15);
            lblExpiry.setForeground(Color.RED);
            panel.add(lblExpiry);

            // Auto-delete if expired
            if (daysLeft < 0) {
                permanentlyDeleteItem(type, id);
                return panel;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Restore button
        JButton btnRestore = RoundedComponents.createRoundedButton(
         	    "Restore",
         	    new Color(40, 167, 69),       
                 new Color(255, 255, 255),    
                 new Color(0, 0, 0),
         	    15,                         
         	     RoundedComponents.ALL_CORNERS
         	     );
        btnRestore.setFont(new Font("Tahoma", Font.BOLD, 10));
        btnRestore.setBounds(190, 12, 80, 25);      
        btnRestore.addActionListener(e -> restoreItem(type, id, itemName, parentPanel));
        panel.add(btnRestore);

        // Permanent delete button
        JButton btnDeletePerm = RoundedComponents.createRoundedButton(
         	    "Delete",
         	    new Color(220, 53, 69),       
                 new Color(255, 255, 255),    
                 new Color(0, 0, 0),
         	    15,                         
         	     RoundedComponents.ALL_CORNERS
         	     );
        btnDeletePerm.setFont(getEmojiFont("Tahoma", Font.BOLD, 10));
        btnDeletePerm.setBounds(275, 12, 75, 25);
        btnDeletePerm.setToolTipText("Delete Permanently");
        btnDeletePerm.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null,
                "PERMANENTLY delete '" + itemName + "'?\n\nThis cannot be undone!",
                "Confirm Permanent Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                permanentlyDeleteItem(type, id);
                loadDeletedItems(parentPanel);
            }
        });
        panel.add(btnDeletePerm);

        return panel;
    }

    private void restoreItem(String type, int id, String itemName, JPanel parentPanel) {
        try (Connection conn = SQLiteConnection.connect()) {
            String table = type.equals("lost") ? "lost_items" : "found_items";
            String sql = "UPDATE " + table + " SET deleted_date = NULL WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "'" + itemName + "' has been restored!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadDeletedItems(parentPanel);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void permanentlyDeleteItem(String type, int id) {
        try (Connection conn = SQLiteConnection.connect()) {
            String table = type.equals("lost") ? "lost_items" : "found_items";
            String sql = "DELETE FROM " + table + " WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Permanently deleted " + type + " item #" + id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
 // ADD THIS METHOD TO YOUR AdminPanel.java CLASS (before the last bracket)

    private JPanel createViewLogsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Activity Logs");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(120, 10, 150, 30);
        panel.add(lblTitle);

        // Online Users Section
        JLabel lblOnlineUsers = new JLabel("Online Users");
        lblOnlineUsers.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblOnlineUsers.setBounds(10, 50, 120, 20);
        panel.add(lblOnlineUsers);

      
        JPanel onlineUsersPanel = new JPanel();
        onlineUsersPanel.setLayout(new BoxLayout(onlineUsersPanel, BoxLayout.Y_AXIS));
        onlineUsersPanel.setBackground(Color.WHITE);

        // Create rounded scroll pane with color
        JScrollPane scrollOnline = RoundedComponents.createRoundedScrollPane(
        	    onlineUsersPanel,
        	    Color.WHITE,      // background
        	    Color.BLACK,       // border color (or null for no border)
        	    10                // radius
        	);
        scrollOnline.setBounds(10, 75, 180, 110);
        scrollOnline.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollOnline);
        
        // Statistics Section
        JLabel lblStats = new JLabel("System Statistics");
        lblStats.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblStats.setBounds(200, 50, 150, 20);
        panel.add(lblStats);

        JPanel statsPanel = RoundedComponents.createRoundedPanel(
        	    Color.WHITE,            // Background color
        	    Color.BLACK,       // Border color
        	    10,                     // Radius
        	    RoundedComponents.ALL_CORNERS
        	);
        	statsPanel.setBounds(200, 75, 170, 110);
        	statsPanel.setLayout(null);

        	panel.add(statsPanel);

        // Recent Activity Log
        JLabel lblRecentActivity = new JLabel("Recent Activity");
        lblRecentActivity.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblRecentActivity.setBounds(10, 195, 150, 20);
        panel.add(lblRecentActivity);

        JPanel activityPanel = new JPanel();
        activityPanel.setLayout(new BoxLayout(activityPanel, BoxLayout.Y_AXIS));
        activityPanel.setBackground(Color.WHITE);

        // Create rounded scroll pane with the panel
        JScrollPane scrollActivity = RoundedComponents.createRoundedScrollPane(
            activityPanel,
            Color.WHITE,      // background color
            Color.GRAY,       // border color (or null for no border)
            10                // radius
        );
        scrollActivity.setBounds(10, 220, 360, 95);
        scrollActivity.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollActivity);

        // Load data
        loadOnlineUsers(onlineUsersPanel);
        loadSystemStats(statsPanel);
        loadRecentActivity(activityPanel);

        // Refresh button
        JButton btnRefresh = RoundedComponents.createRoundedButton(
       	     "Refresh",
       	     new Color(90, 14, 36),    // background
       	     Color.WHITE,                // text
       	     Color.BLACK,            // border
       	     10,                         // radius
       	     RoundedComponents.ALL_CORNERS
       	 );
       	 btnRefresh.setFont(new Font("Tahoma", Font.BOLD, 12));  
			 btnRefresh.setBounds(280, 190, 90, 25);
        btnRefresh.addActionListener(e -> {
            loadOnlineUsers(onlineUsersPanel);
            loadSystemStats(statsPanel);
            loadRecentActivity(activityPanel);
        });
        panel.add(btnRefresh);

        return panel;
    }

    private void loadOnlineUsers(JPanel containerPanel) {
        containerPanel.removeAll();

        try (Connection conn = SQLiteConnection.connect()) {
            String sql = "SELECT username, account_status FROM users ORDER BY username";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                String accountStatus = rs.getString("account_status");
                if (accountStatus == null) accountStatus = "active";

                JPanel userPanel = new JPanel();
                userPanel.setLayout(null);
                userPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
                userPanel.setPreferredSize(new Dimension(160, 25));
                userPanel.setBackground(Color.WHITE);

                JLabel lblStatusDot = new JLabel("●");
                lblStatusDot.setFont(getEmojiFont("Tahoma", Font.PLAIN, 14));  // 3 parameters
                lblStatusDot.setBounds(5, 5, 15, 15);
                lblStatusDot.setForeground(Color.GRAY); 
                lblStatusDot.setToolTipText("Offline");
                userPanel.add(lblStatusDot);

                // Username
                JLabel lblUsername = new JLabel(username);
                lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 11));
                lblUsername.setBounds(25, 5, 130, 15);
                userPanel.add(lblUsername);

                containerPanel.add(userPanel);
            }

        } catch (Exception ex) {
            System.err.println("Error loading online users: " + ex.getMessage());
            ex.printStackTrace();
        }

        containerPanel.revalidate();
        containerPanel.repaint();
    }
    
    private void loadSystemStats(JPanel statsPanel) {
        statsPanel.removeAll();

        try (Connection conn = SQLiteConnection.connect()) {
            // Total Users
            String sqlUsers = "SELECT COUNT(*) as count FROM users";
            PreparedStatement pstmt = conn.prepareStatement(sqlUsers);
            java.sql.ResultSet rs = pstmt.executeQuery();
            int totalUsers = rs.getInt("count");

            // Total Lost Items
            String sqlLost = "SELECT COUNT(*) as count FROM lost_items WHERE (deleted_date IS NULL OR deleted_date = '')";
            pstmt = conn.prepareStatement(sqlLost);
            rs = pstmt.executeQuery();
            int totalLost = rs.getInt("count");

            // Total Found Items
            String sqlFound = "SELECT COUNT(*) as count FROM found_items WHERE (deleted_date IS NULL OR deleted_date = '')";
            pstmt = conn.prepareStatement(sqlFound);
            rs = pstmt.executeQuery();
            int totalFound = rs.getInt("count");

            // Pending Items
            String sqlPending = "SELECT " +
                "(SELECT COUNT(*) FROM lost_items WHERE status = 'Pending' AND (deleted_date IS NULL OR deleted_date = '')) + " +
                "(SELECT COUNT(*) FROM found_items WHERE status = 'Pending' AND (deleted_date IS NULL OR deleted_date = '')) as count";
            pstmt = conn.prepareStatement(sqlPending);
            rs = pstmt.executeQuery();
            int totalPending = rs.getInt("count");

            // Resolved Items
            String sqlResolved = "SELECT " +
                "(SELECT COUNT(*) FROM lost_items WHERE status = 'Resolved') + " +
                "(SELECT COUNT(*) FROM found_items WHERE status = 'Resolved') as count";
            pstmt = conn.prepareStatement(sqlResolved);
            rs = pstmt.executeQuery();
            int totalResolved = rs.getInt("count");

            // Create stat labels
            JLabel lblTotalUsers = new JLabel("Total Users: " + totalUsers);
            lblTotalUsers.setFont(new Font("Tahoma", Font.PLAIN, 11));
            lblTotalUsers.setBounds(10, 10, 150, 15);
            statsPanel.add(lblTotalUsers);

            JLabel lblLostItems = new JLabel("Lost Items: " + totalLost);
            lblLostItems.setFont(new Font("Tahoma", Font.PLAIN, 11));
            lblLostItems.setBounds(10, 30, 150, 15);
            statsPanel.add(lblLostItems);

            JLabel lblFoundItems = new JLabel("Found Items: " + totalFound);
            lblFoundItems.setFont(new Font("Tahoma", Font.PLAIN, 11));
            lblFoundItems.setBounds(10, 50, 150, 15);
            statsPanel.add(lblFoundItems);

            JLabel lblPending = new JLabel("Pending: " + totalPending);
            lblPending.setFont(new Font("Tahoma", Font.BOLD, 11));
            lblPending.setForeground(new Color(255, 193, 7));
            lblPending.setBounds(10, 70, 150, 15);
            statsPanel.add(lblPending);

            JLabel lblResolved = new JLabel("Resolved: " + totalResolved);
            lblResolved.setFont(new Font("Tahoma", Font.BOLD, 11));
            lblResolved.setForeground(new Color(40, 167, 69));
            lblResolved.setBounds(10, 90, 150, 15);
            statsPanel.add(lblResolved);

        } catch (Exception ex) {
            System.err.println("Error loading stats: " + ex.getMessage());
            ex.printStackTrace();
        }

        statsPanel.revalidate();
        statsPanel.repaint();
    }

    private void loadRecentActivity(JPanel containerPanel) {
        containerPanel.removeAll();

        try (Connection conn = SQLiteConnection.connect()) {
            // Get recent lost items submissions
            String sqlLost = "SELECT 'Lost Item' as type, item_name, reported_by, date_reported " +
                            "FROM lost_items ORDER BY date_reported DESC LIMIT 10";
            PreparedStatement pstmt = conn.prepareStatement(sqlLost);
            java.sql.ResultSet rs = pstmt.executeQuery();

            java.util.List<ActivityLog> activities = new java.util.ArrayList<>();
            
            while (rs.next()) {
                activities.add(new ActivityLog(
                    rs.getString("type"),
                    rs.getString("item_name"),
                    rs.getString("reported_by"),
                    rs.getString("date_reported")
                ));
            }

            // Get recent found items submissions
            String sqlFound = "SELECT 'Found Item' as type, item_name, reported_by, date_reported " +
                             "FROM found_items ORDER BY date_reported DESC LIMIT 10";
            pstmt = conn.prepareStatement(sqlFound);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                activities.add(new ActivityLog(
                    rs.getString("type"),
                    rs.getString("item_name"),
                    rs.getString("reported_by"),
                    rs.getString("date_reported")
                ));
            }

            // Sort by date (most recent first)
            activities.sort((a, b) -> b.date.compareTo(a.date));

            // Display activities (limit to 15)
            int count = 0;
            for (ActivityLog activity : activities) {
                if (count >= 15) break;

                JPanel activityPanel = new JPanel();
                activityPanel.setLayout(null);
                activityPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
                activityPanel.setPreferredSize(new Dimension(340, 30));
                activityPanel.setBackground(Color.WHITE);
                activityPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

                // Activity icon
                JLabel lblIcon = new JLabel(activity.type.contains("Lost") ? "🔴" : "🟢");
                lblIcon.setFont(getEmojiFont(Font.PLAIN, 12));
                lblIcon.setBounds(5, 7, 20, 15);
                activityPanel.add(lblIcon);

                // Activity text
                String activityText = activity.user + " reported: " + 
                    (activity.itemName.length() > 20 ? activity.itemName.substring(0, 20) + "..." : activity.itemName);
                JLabel lblActivity = new JLabel(activityText);
                lblActivity.setFont(new Font("Tahoma", Font.PLAIN, 10));
                lblActivity.setBounds(30, 5, 250, 15);
                activityPanel.add(lblActivity);

                // Time
                String timeStr = formatTimeAgo(activity.date);
                JLabel lblTime = new JLabel(timeStr);
                lblTime.setFont(new Font("Tahoma", Font.ITALIC, 9));
                lblTime.setForeground(Color.GRAY);
                lblTime.setBounds(30, 18, 250, 10);
                activityPanel.add(lblTime);

                containerPanel.add(activityPanel);
                count++;
            }

            if (activities.isEmpty()) {
                JLabel lblNoActivity = new JLabel("No recent activity");
                lblNoActivity.setFont(new Font("Tahoma", Font.ITALIC, 12));
                lblNoActivity.setForeground(Color.GRAY);
                containerPanel.add(lblNoActivity);
            }

        } catch (Exception ex) {
            System.err.println("Error loading activity: " + ex.getMessage());
            ex.printStackTrace();
        }

        containerPanel.revalidate();
        containerPanel.repaint();
    }

    private String formatTimeAgo(String dateStr) {
        try {
            java.time.LocalDateTime date = java.time.LocalDateTime.parse(dateStr.replace(" ", "T"));
            java.time.Duration duration = java.time.Duration.between(date, java.time.LocalDateTime.now());
            
            long minutes = duration.toMinutes();
            if (minutes < 1) return "just now";
            if (minutes < 60) return minutes + " min ago";
            
            long hours = duration.toHours();
            if (hours < 24) return hours + " hour" + (hours > 1 ? "s" : "") + " ago";
            
            long days = duration.toDays();
            if (days < 30) return days + " day" + (days > 1 ? "s" : "") + " ago";
            
            return dateStr.substring(0, 10); // Return date
        } catch (Exception e) {
            return dateStr;
        }
    }

    // Helper class for activity logs
    class ActivityLog {
        String type;
        String itemName;
        String user;
        String date;
        
        ActivityLog(String type, String itemName, String user, String date) {
            this.type = type;
            this.itemName = itemName;
            this.user = user;
            this.date = date;
        }
    }
    
 // Version 1: With base font parameter (3 parameters)
    private Font getEmojiFont(String baseFont, int style, int size) {
        Font font = new Font(baseFont, style, size);
        
        if (font.canDisplayUpTo("📍📅👤🔴⟲●▼🟢") == -1) {
            return font; 
        }
        
        String[] emojiFonts = {
            "Segoe UI Emoji", "Apple Color Emoji", "Noto Color Emoji", "Segoe UI Symbol"
        };
        for (String fontName : emojiFonts) {
            Font emojiFont = new Font(fontName, style, size);
            if (emojiFont.getFamily().equals(fontName)) {
                return emojiFont;
            }
        }
        return new Font("Dialog", style, size);
    }

    private Font getEmojiFont(int style, int size) {
        return getEmojiFont("Tahoma", style, size); // Now this works!
    
    }
}