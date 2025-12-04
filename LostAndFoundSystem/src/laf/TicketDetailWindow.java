package laf;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.*;

/**
 * TicketDetailWindow - Shows full ticket details with image
 */
public class TicketDetailWindow extends JDialog {
    
    public TicketDetailWindow(Frame parent, String type, int ticketId) {
        super(parent, "Ticket Details - #" + ticketId, true);
        setSize(600, 500);
        setLocationRelativeTo(parent);
        
        initUI(type, ticketId);
    }
    
    private void initUI(String type, int ticketId) {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);
        
        try (Connection conn = SQLiteConnection.connect()) {
            String table = type.equals("lost") ? "lost_items" : "found_items";
            String locationCol = type.equals("lost") ? "location_lost" : "location_found";
            String dateCol = type.equals("lost") ? "date_lost" : "date_found";
            
            String sql = "SELECT item_name, " + locationCol + " as location, " + dateCol + " as date, " +
                        "description, image_path, reported_by, status, date_reported FROM " + table + 
                        " WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, ticketId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String itemName = rs.getString("item_name");
                String location = rs.getString("location");
                String date = rs.getString("date");
                String description = rs.getString("description");
                String imagePath = rs.getString("image_path");
                String reportedBy = rs.getString("reported_by");
                String status = rs.getString("status");
                String dateReported = rs.getString("date_reported");
                
                // Top panel - Header
                JPanel headerPanel = new JPanel(new BorderLayout());
                headerPanel.setBackground(new Color(64, 0, 0));
                headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                
                JLabel lblHeader = new JLabel(type.toUpperCase() + " ITEM REPORT");
                lblHeader.setFont(new Font("Tahoma", Font.BOLD, 18));
                lblHeader.setForeground(Color.WHITE);
                headerPanel.add(lblHeader, BorderLayout.WEST);
                
                JLabel lblStatus = new JLabel(status.toUpperCase());
                lblStatus.setFont(new Font("Tahoma", Font.BOLD, 14));
                if (status.equals("Pending")) {
                    lblStatus.setForeground(new Color(255, 193, 7));
                } else if (status.equals("Resolved")) {
                    lblStatus.setForeground(new Color(40, 167, 69));
                }
                headerPanel.add(lblStatus, BorderLayout.EAST);
                
                add(headerPanel, BorderLayout.NORTH);
                
                // Center panel - Content
                JPanel contentPanel = new JPanel();
                contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
                contentPanel.setBackground(Color.WHITE);
                contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                
                // Image
             // Image section - REPLACE your current image code with this:
                if (imagePath != null && !imagePath.isEmpty()) {
                    try {
                        File imgFile = new File(imagePath);
                        if (imgFile.exists()) {
                            ImageIcon icon = new ImageIcon(imagePath);
                            Image img = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                            JLabel lblImage = new JLabel(new ImageIcon(img));
                            lblImage.setAlignmentX(Component.CENTER_ALIGNMENT);
                            lblImage.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
                            contentPanel.add(lblImage);
                            contentPanel.add(Box.createVerticalStrut(20));
                        }
                    } catch (Exception ex) {
                        System.err.println("Error loading image: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                } else {
                    // Add placeholder if no image
                    JLabel lblNoImage = new JLabel("No image available");
                    lblNoImage.setAlignmentX(Component.CENTER_ALIGNMENT);
                    lblNoImage.setFont(new Font("Tahoma", Font.ITALIC, 12));
                    lblNoImage.setForeground(Color.GRAY);
                    contentPanel.add(lblNoImage);
                    contentPanel.add(Box.createVerticalStrut(20));
                }            
                
                // Details
                addDetailRow(contentPanel, "Item Name:", itemName);
                addDetailRow(contentPanel, "Location " + (type.equals("lost") ? "Lost:" : "Found:"), location);
                addDetailRow(contentPanel, "Date " + (type.equals("lost") ? "Lost:" : "Found:"), date);
                addDetailRow(contentPanel, "Reported By:", reportedBy);
                addDetailRow(contentPanel, "Date Reported:", dateReported != null ? dateReported.substring(0, 10) : "N/A");
                
                contentPanel.add(Box.createVerticalStrut(10));
                
                // Description
                JLabel lblDescTitle = new JLabel("Description:");
                lblDescTitle.setFont(new Font("Tahoma", Font.BOLD, 13));
                lblDescTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
                contentPanel.add(lblDescTitle);
                
                contentPanel.add(Box.createVerticalStrut(5));
                
                JTextArea txtDescription = RoundedComponents.createRoundedTextArea(
                	    new Color(255, 255 ,255),  // Background
                	    Color.BLACK,                // Text color
                	    Color.WHITE,          // Border color
                	    15,                         // Radius
                	    5,                          // Rows
                	    40                          // Columns
                	);
                	txtDescription.setText(description);
                	txtDescription.setEditable(false);
                	txtDescription.setFont(new Font("Tahoma", Font.PLAIN, 12));
                
                	JScrollPane descScroll = RoundedComponents.createRoundedScrollPane(
                		    txtDescription, 
                		    Color.WHITE,  // Add this color parameter
                		    15
                		);
                		descScroll.setPreferredSize(new Dimension(500, 100));
                		descScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
                		contentPanel.add(descScroll);
                	
                JScrollPane scrollPane = new JScrollPane(contentPanel);
                scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
                scrollPane.getVerticalScrollBar().setUnitIncrement(16);
                add(scrollPane, BorderLayout.CENTER);
                
                // Bottom panel - Close button
                JPanel bottomPanel = new JPanel();
                bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                
                JButton btnClose = new JButton("Close");
                btnClose.setFont(new Font("Tahoma", Font.BOLD, 12));
                btnClose.addActionListener(e -> dispose());
                
                bottomPanel.add(btnClose);          
                add(bottomPanel, BorderLayout.SOUTH);
            }
            
        } catch (Exception ex) {
            System.err.println("Error loading ticket details: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void addDetailRow(JPanel parent, String label, String value) {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        rowPanel.setBackground(Color.WHITE);
        rowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblLabel.setPreferredSize(new Dimension(150, 20));
        rowPanel.add(lblLabel);
        
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Tahoma", Font.PLAIN, 13));
        rowPanel.add(lblValue);
        
        parent.add(rowPanel);
    }
}