package propertyrentaltrackersystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class KeyLog {
    Scanner scan = new Scanner(System.in);
    Config conf = new Config();

    public void keyLogConfig() {
        int option;
        do {
            System.out.println("\n--- Key Log Management ---");
            System.out.println("1. Add Key Log");
            System.out.println("2. View Key Logs");
            System.out.println("3. Edit Key Log");
            System.out.println("4. Delete Key Log");
            System.out.println("5. Exit");

            try {
                System.out.print("Choose an option: ");
                option = scan.nextInt();
                scan.nextLine();
                
                switch (option) {
                    case 1:
                        addKeyLog();
                        break;
                    case 2:
                        viewKeyLogs();
                        break;
                    case 3:
                        viewKeyLogs();
                        editKeyLog();
                        break;
                    case 4:
                        viewKeyLogs();
                        deleteKeyLog();
                        break;
                    case 5:
                        System.out.println("Returning to main menu.");
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scan.nextLine();
                option = -1;
            }
        } while (option != 5);
    }

    private void addKeyLog() {
        System.out.println("Enter Key Log Details:");
        
        Key key = new Key();
        key.viewKeys();
        
        int keyId;
        do {
            System.out.print("Key ID: ");
            keyId = scan.nextInt();
            if (!conf.doesIDExist("tbl_keys", "k_id", keyId)) {
                System.out.println("Key ID doesn't exist.");
            }
        } while (!conf.doesIDExist("tbl_keys", "k_id", keyId));
        scan.nextLine();

        System.out.print("Issued To: ");
        String issuedTo = scan.nextLine();
        System.out.print("Date: ");
        String date = scan.nextLine();
        System.out.print("Action: ");
        String action = scan.nextLine();

        String sql = "INSERT INTO tbl_keylogs (k_id, issued_to, date, action) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, keyId, issuedTo, date, action);
    }

    public void viewKeyLogs() {
        String query = "SELECT * FROM tbl_keylogs";
        String[] headers = {"ID", "Key ID", "Issued To", "Date", "Action"};
        String[] columns = {"keylog_id", "k_id", "issued_to", "date", "action"};

        conf.viewRecords(query, headers, columns);
    }

    private void editKeyLog() {
        int logId;
        do {
            System.out.print("Enter Key Log ID to edit: ");
            logId = scan.nextInt();
            if (!conf.doesIDExist("tbl_keylogs", "keylog_id", logId)) {
                System.out.println("Key Log ID doesn't exist.");
            }
        } while (!conf.doesIDExist("tbl_keylogs", "keylog_id", logId));
        scan.nextLine();

        int keyId;
        do {
            System.out.print("New Key ID: ");
            keyId = scan.nextInt();
            if (!conf.doesIDExist("tbl_keys", "k_id", keyId)) {
                System.out.println("Key ID doesn't exist.");
            }
        } while (!conf.doesIDExist("tbl_keys", "k_id", keyId));
        scan.nextLine();

        System.out.print("New Issued To: ");
        String issuedTo = scan.nextLine();
        System.out.print("New Date (YYYY-MM-DD): ");
        String date = scan.nextLine();
        System.out.print("New Action: ");
        String action = scan.nextLine();

        String sql = "UPDATE tbl_keylogs SET k_id = ?, issued_to = ?, date = ?, action = ? WHERE keylog_id = ?";
        conf.updateRecord(sql, keyId, issuedTo, date, action, logId);
    }

    private void deleteKeyLog() {
        System.out.print("Enter Key Log ID to delete: ");
        int keylog_id = scan.nextInt();
        String sql = "DELETE FROM tbl_keylogs WHERE keylog_id = ?";
        conf.deleteRecord(sql, keylog_id);
    }
}

