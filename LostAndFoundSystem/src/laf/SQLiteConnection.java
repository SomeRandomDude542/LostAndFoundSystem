package laf;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteConnection {
    private static final String DB_PATH = "db/lostandfound.db";
    
    public static Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:" + DB_PATH;
            System.out.println("USING DB FILE: " + url);
            conn = DriverManager.getConnection(url);
            
            // Enable WAL mode and set busy timeout for better concurrent access
            Statement pragmaStmt = conn.createStatement();
            pragmaStmt.execute("PRAGMA journal_mode=WAL;");
            pragmaStmt.execute("PRAGMA busy_timeout=5000;"); // Wait up to 5 seconds if locked
            pragmaStmt.close();
            
            // Create users table if missing
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "username TEXT NOT NULL, "
                    + "email TEXT NOT NULL, "
                    + "password TEXT NOT NULL, "
                    + "role TEXT DEFAULT 'user'"
                    + ");";
            
         // Update the lost_items table creation:
            String createLostItemsTable = "CREATE TABLE IF NOT EXISTS lost_items ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "item_name TEXT NOT NULL, "
                    + "location_lost TEXT NOT NULL, "
                    + "date_lost TEXT NOT NULL, "
                    + "description TEXT, "
                    + "image_path TEXT, "
                    + "reported_by TEXT NOT NULL, "
                    + "status TEXT DEFAULT 'Pending', "
                    + "date_reported DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ");";

            // Do the same for found_items:
            String createFoundItemsTable = "CREATE TABLE IF NOT EXISTS found_items ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "item_name TEXT NOT NULL, "
                    + "location_found TEXT NOT NULL, "
                    + "date_found TEXT NOT NULL, "
                    + "description TEXT, "
                    + "image_path TEXT, "
                    + "reported_by TEXT NOT NULL, "
                    + "status TEXT DEFAULT 'Pending', "
                    + "date_reported DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ");";
            
            Statement stmt = conn.createStatement();
            stmt.execute(createUsersTable);
            stmt.execute(createLostItemsTable);
            stmt.execute(createFoundItemsTable);
                         
            updateSchemaIfNeeded(conn);

          
            System.out.println("Database ready.");
        } catch (SQLException e) {
            System.out.println("SQLite ERROR: " + e.getMessage());
        }
        return conn;
    }

    private static void updateSchemaIfNeeded(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            
           
            // Add status column to lost_items
            try {
                stmt.execute("ALTER TABLE lost_items ADD COLUMN status TEXT DEFAULT 'Pending'");
                System.out.println("Added status column to lost_items");
            } catch (SQLException e) {
                if (!e.getMessage().contains("duplicate column")) {
                    System.out.println("Note: " + e.getMessage());
                }
            } 
            
            // Add status column to found_items
            try {
                stmt.execute("ALTER TABLE found_items ADD COLUMN status TEXT DEFAULT 'Pending'");
                System.out.println("Added status column to found_items");
            } catch (SQLException e) {
                if (!e.getMessage().contains("duplicate column")) {
                    System.out.println("Note: " + e.getMessage());
                }
            }
            
            // Add user status management columns
            try {
                stmt.execute("ALTER TABLE users ADD COLUMN account_status TEXT DEFAULT 'active'");
                System.out.println("Added account_status column to users");
            } catch (SQLException e) {
                if (!e.getMessage().contains("duplicate column")) {
                    System.out.println("Note: " + e.getMessage());
                }
            }
            
            try {
                stmt.execute("ALTER TABLE users ADD COLUMN ban_reason TEXT");
                System.out.println("Added ban_reason column to users");
            } catch (SQLException e) {
                if (!e.getMessage().contains("duplicate column")) {
                    System.out.println("Note: " + e.getMessage());
                }
            }
            
            try {
                stmt.execute("ALTER TABLE users ADD COLUMN suspension_end_date TEXT");
                System.out.println("Added suspension_end_date column to users");
            } catch (SQLException e) {
                if (!e.getMessage().contains("duplicate column")) {
                    System.out.println("Note: " + e.getMessage());
                }
            }
            
            // Add deleted_date for recycle bin feature
            try {
                stmt.execute("ALTER TABLE lost_items ADD COLUMN deleted_date TEXT");
                System.out.println("Added deleted_date column to lost_items");
            } catch (SQLException e) {
                if (!e.getMessage().contains("duplicate column")) {
                    System.out.println("Note: " + e.getMessage());
                }
            }
            
            try {
                stmt.execute("ALTER TABLE found_items ADD COLUMN deleted_date TEXT");
                System.out.println("Added deleted_date column to found_items");
            } catch (SQLException e) {
                if (!e.getMessage().contains("duplicate column")) {
                    System.out.println("Note: " + e.getMessage());
                }
            }
            try {
                stmt.execute("ALTER TABLE messages ADD COLUMN image_path TEXT");
                System.out.println("Added image_path column to messages");
            } catch (SQLException e) {
                if (!e.getMessage().contains("duplicate column")) {
                    System.out.println("Note: " + e.getMessage());
                }
            }
            
            // Create messages table
            try {
                String createMessagesTable = "CREATE TABLE IF NOT EXISTS messages (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "report_type TEXT NOT NULL, " +
                    "report_id INTEGER NOT NULL, " +
                    "sender TEXT NOT NULL, " +
                    "receiver TEXT NOT NULL, " +
                    "message TEXT NOT NULL, " +
                    "timestamp TEXT NOT NULL, " +
                    "is_read INTEGER DEFAULT 0" +
                    ");";
                stmt.execute(createMessagesTable);
                System.out.println("Messages table created");
            } catch (SQLException e) {
                if (!e.getMessage().contains("already exists")) {
                    System.out.println("Note: " + e.getMessage());
                }
            }

            // Create notifications table
            try {
                String createNotificationsTable = "CREATE TABLE IF NOT EXISTS notifications (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT NOT NULL, " +
                    "title TEXT NOT NULL, " +
                    "message TEXT NOT NULL, " +
                    "type TEXT NOT NULL, " +
                    "timestamp TEXT NOT NULL, " +
                    "is_read INTEGER DEFAULT 0, " +
                    "related_id INTEGER" +
                    ");";
                stmt.execute(createNotificationsTable);
                System.out.println("Notifications table created");
            } catch (SQLException e) {
                if (!e.getMessage().contains("already exists")) {
                    System.out.println("Note: " + e.getMessage());
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Schema update error: " + e.getMessage());
        }
    }
    
    
}