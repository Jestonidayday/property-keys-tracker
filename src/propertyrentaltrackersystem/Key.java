package propertyrentaltrackersystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Key {
    Scanner scan = new Scanner(System.in);
    Config conf = new Config();

    public void keyConfig() {
        int option;
        do {
            System.out.println("\n--- Key Management ---");
            System.out.println("1. Add Key");
            System.out.println("2. View Keys");
            System.out.println("3. Edit Key");
            System.out.println("4. Delete Key");
            System.out.println("5. Exit");

            try {
                System.out.print("Choose an option: ");
                option = scan.nextInt();
                scan.nextLine();

                switch (option) {
                    case 1:
                        addKey();
                        break;
                    case 2:
                        viewKeys();
                        break;
                    case 3:
                        viewKeys();
                        editKey();
                        break;
                    case 4:
                        viewKeys();
                        deleteKey();
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

    private void addKey() {
        System.out.println("\nEnter Key Details:");
        
        Property prop = new Property();
        prop.viewProperties();
        
        int propertyId;
        do {
            System.out.print("Property ID: ");
            propertyId = scan.nextInt();
            if (!conf.doesIDExist("tbl_property", "p_id", propertyId)) {
                System.out.println("Property ID doesn't exist.");
            }
        } while (!conf.doesIDExist("tbl_property", "p_id", propertyId));
        scan.nextLine();

        System.out.print("Key Code: ");
        String keyCode = scan.nextLine();
        System.out.print("Status: ");
        String k_status = scan.nextLine();

        String sql = "INSERT INTO tbl_keys (p_id, key_code, k_status) VALUES (?, ?, ?)";
        conf.addRecord(sql, propertyId, keyCode, k_status);
    }

    public void viewKeys() {
        String query = "SELECT * FROM tbl_keys";
        String[] headers = {"ID", "Property ID", "Key Code", "Status"};
        String[] columns = {"k_id", "p_id", "key_code", "k_status"};

        conf.viewRecords(query, headers, columns);
    }

    private void editKey() {
        int keyId;
        do {
            System.out.print("Enter Key ID to edit: ");
            keyId = scan.nextInt();
            if (!conf.doesIDExist("tbl_keys", "k_id", keyId)) {
                System.out.println("Key ID doesn't exist.");
            }
        } while (!conf.doesIDExist("tbl_keys", "k_id", keyId));
        scan.nextLine();

        int propertyId;
        do {
            System.out.print("New Property ID: ");
            propertyId = scan.nextInt();
            if (!conf.doesIDExist("tbl_property", "p_id", propertyId)) {
                System.out.println("Property ID doesn't exist.");
            }
        } while (!conf.doesIDExist("tbl_property", "p_id", propertyId));
        scan.nextLine();

        System.out.print("New Key Code: ");
        String keyCode = scan.nextLine();
        System.out.print("New Status: ");
        String k_status = scan.nextLine();

        String sql = "UPDATE tbl_keys SET p_id = ?, key_code = ?, k_status = ? WHERE k_id = ?";
        conf.updateRecord(sql, propertyId, keyCode, k_status, keyId);
    }

    private void deleteKey() {
        System.out.print("Enter Key ID to delete: ");
        int k_id = scan.nextInt();
        String sql = "DELETE FROM tbl_keys WHERE k_id = ?";
        conf.deleteRecord(sql, k_id);
    }
}

