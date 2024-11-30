package propertyrentaltrackersystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Property {
    Scanner scan = new Scanner(System.in);
    Config conf = new Config();

    public void propertyConfig() {
        int option;
        do {
            System.out.println("\n--- Property Management ---");
            System.out.println("1. Add Property");
            System.out.println("2. View Properties");
            System.out.println("3. Edit Property");
            System.out.println("4. Delete Property");
            System.out.println("5. Exit");

            try {
                System.out.print("Choose an option: ");
                option = scan.nextInt();
                scan.nextLine();

                switch (option) {
                    case 1:
                        addProperty();
                        break;
                    case 2:
                        viewProperties();
                        break;
                    case 3:
                        viewProperties();
                        editProperty();
                        break;
                    case 4:
                        viewProperties();
                        deleteProperty();
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

    private void addProperty() {
        System.out.println("Enter Property Details:");
        System.out.print("Name: ");
        String p_name = scan.nextLine();
        System.out.print("Address: ");
        String p_address = scan.nextLine();
        System.out.print("Type: ");
        String p_type = scan.nextLine();

        String sql = "INSERT INTO tbl_property (p_name, p_address, p_type) VALUES (?, ?, ?)";
        conf.addRecord(sql, p_name, p_address, p_type);
    }

    public void viewProperties() {
        String query = "SELECT * FROM tbl_property";
        String[] headers = {"ID", "Name", "Address", "Type"};
        String[] columns = {"p_id", "p_name", "p_address", "p_type"};

        conf.viewRecords(query, headers, columns);
    }

    private void editProperty() {
        int propertyId;
        do {
            System.out.print("Enter Property ID to edit: ");
            propertyId = scan.nextInt();
            if (!conf.doesIDExist("tbl_property", "p_id", propertyId)) {
                System.out.println("Property ID doesn't exist.");
            }
        } while (!conf.doesIDExist("tbl_property", "p_id", propertyId));
        scan.nextLine();

        System.out.print("New Name: ");
        String p_name = scan.nextLine();
        System.out.print("New Address: ");
        String p_address = scan.nextLine();
        System.out.print("New Type: ");
        String p_type = scan.nextLine();

        String sql = "UPDATE tbl_property SET p_name = ?, p_address = ?, p_type = ? WHERE p_id = ?";
        conf.updateRecord(sql, p_name, p_address, p_type, propertyId);
    }

    private void deleteProperty() {
        System.out.print("Enter Property ID to delete: ");
        int p_id = scan.nextInt();
        String sql = "DELETE FROM tbl_property WHERE p_id = ?";
        conf.deleteRecord(sql, p_id);
    }
}

