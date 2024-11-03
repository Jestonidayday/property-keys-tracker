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
                        editProperty();
                        break;
                    case 4:
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
        String name = scan.nextLine();
        System.out.print("Address: ");
        String address = scan.nextLine();
        System.out.print("Type: ");
        String type = scan.nextLine();

        String sql = "INSERT INTO property (name, address, type) VALUES (?, ?, ?)";
        conf.addRecord(sql, name, address, type);
    }

    public void viewProperties() {
        String query = "SELECT * FROM property";
        String[] headers = {"ID", "Name", "Address", "Type"};
        String[] columns = {"id", "name", "address", "type"};

        conf.viewRecords(query, headers, columns);
    }

    private void editProperty() {
        int propertyId;
        do {
            System.out.print("Enter Property ID to edit: ");
            propertyId = scan.nextInt();
            if (!conf.doesIDExist("property", propertyId)) {
                System.out.println("Property ID doesn't exist.");
            }
        } while (!conf.doesIDExist("property", propertyId));
        scan.nextLine();

        System.out.print("New Name: ");
        String name = scan.nextLine();
        System.out.print("New Address: ");
        String address = scan.nextLine();
        System.out.print("New Type: ");
        String type = scan.nextLine();

        String sql = "UPDATE property SET name = ?, address = ?, type = ? WHERE id = ?";
        conf.updateRecord(sql, name, address, type, propertyId);
    }

    private void deleteProperty() {
        System.out.print("Enter Property ID to delete: ");
        int id = scan.nextInt();
        String sql = "DELETE FROM property WHERE id = ?";
        conf.deleteRecord(sql, id);
    }
}

