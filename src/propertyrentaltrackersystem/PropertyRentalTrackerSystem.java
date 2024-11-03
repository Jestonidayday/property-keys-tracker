package propertyrentaltrackersystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class PropertyRentalTrackerSystem {
    static Config conf = new Config();
    static Scanner scan = new Scanner(System.in);
    static Property property = new Property();
    static Key key = new Key();
    static KeyLog keyLog = new KeyLog();

    public static void main(String[] args) {
        int choice;
        do {
            try {
                System.out.println("\n--- Property Rental Keys Tracker System ---");
                System.out.println("1. Manage Properties");
                System.out.println("2. Manage Keys");
                System.out.println("3. Manage Key Logs");
                System.out.println("4. Generate Reports");
                System.out.println("5. Exit");

                System.out.print("Enter option: ");
                choice = scan.nextInt();
                scan.nextLine();

                switch (choice) {
                    case 1:
                        property.propertyConfig();
                        break;
                    case 2:
                        key.keyConfig();
                        break;
                    case 3:
                        keyLog.keyLogConfig();
                        break;
                    case 4:
                        generateReports();
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scan.nextLine();
                choice = -1;
            }
        } while (choice != 5);
    }
    
    private static void generateReports() {
        System.out.println("\n\t\t--- PROPERTY KEY USAGE REPORT ---");
        property.viewProperties();

        int propId;
        do {
            System.out.print("\nEnter Property ID for the report: ");
            propId = scan.nextInt();
            if (!conf.doesIDExist("property", propId)) {
                System.out.println("Property ID not found. Please try again.");
            }
        } while (!conf.doesIDExist("property", propId));

        System.out.println("\n=================================");
        System.out.println("       PROPERTY DETAILS          ");
        System.out.println("=================================");
        System.out.printf("Property ID  : %d%n", propId);
        System.out.printf("Name         : %s%n", conf.getDataFromID("property", propId, "name"));
        System.out.printf("Address      : %s%n", conf.getDataFromID("property", propId, "address"));
        System.out.printf("Type         : %s%n", conf.getDataFromID("property", propId, "type"));
        System.out.println("---------------------------------");

        if (conf.isTableEmpty("key_log WHERE key_id IN (SELECT id FROM key WHERE property_id = " + propId + ")")) {
            System.out.println("No key usage records found for this property.");
        } else {
            System.out.println("Key Log Details:");
            String sql = "SELECT key_log.id, key.key_code, key_log.issued_to, key_log.date, key_log.action " +
                         "FROM key_log " +
                         "JOIN key ON key_log.key_id = key.id " +
                         "WHERE key.property_id = " + propId;

            String[] headers = {"Log ID", "Key Code", "Issued To", "Date", "Action"};
            String[] columns = {"id", "key_code", "issued_to", "date", "action"};

            conf.viewRecords(sql, headers, columns);
            System.out.println("\n=================================");
        }
    }

}
