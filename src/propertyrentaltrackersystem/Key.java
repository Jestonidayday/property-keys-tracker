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
                        editKey();
                        break;
                    case 4:
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
        System.out.println("Enter Key Details:");

        int propertyId;
        do {
            System.out.print("Property ID: ");
            propertyId = scan.nextInt();
            if (!conf.doesIDExist("property", propertyId)) {
                System.out.println("Property ID doesn't exist.");
            }
        } while (!conf.doesIDExist("property", propertyId));
        scan.nextLine();

        System.out.print("Key Code: ");
        String keyCode = scan.nextLine();
        System.out.print("Status: ");
        String status = scan.nextLine();

        String sql = "INSERT INTO key (property_id, key_code, status) VALUES (?, ?, ?)";
        conf.addRecord(sql, propertyId, keyCode, status);
    }

    public void viewKeys() {
        String query = "SELECT * FROM key";
        String[] headers = {"ID", "Property ID", "Key Code", "Status"};
        String[] columns = {"id", "property_id", "key_code", "status"};

        conf.viewRecords(query, headers, columns);
    }

    private void editKey() {
        int keyId;
        do {
            System.out.print("Enter Key ID to edit: ");
            keyId = scan.nextInt();
            if (!conf.doesIDExist("key", keyId)) {
                System.out.println("Key ID doesn't exist.");
            }
        } while (!conf.doesIDExist("key", keyId));
        scan.nextLine();

        int propertyId;
        do {
            System.out.print("New Property ID: ");
            propertyId = scan.nextInt();
            if (!conf.doesIDExist("property", propertyId)) {
                System.out.println("Property ID doesn't exist.");
            }
        } while (!conf.doesIDExist("property", propertyId));
        scan.nextLine();

        System.out.print("New Key Code: ");
        String keyCode = scan.nextLine();
        System.out.print("New Status: ");
        String status = scan.nextLine();

        String sql = "UPDATE key SET property_id = ?, key_code = ?, status = ? WHERE id = ?";
        conf.updateRecord(sql, propertyId, keyCode, status, keyId);
    }

    private void deleteKey() {
        System.out.print("Enter Key ID to delete: ");
        int id = scan.nextInt();
        String sql = "DELETE FROM key WHERE id = ?";
        conf.deleteRecord(sql, id);
    }
}

