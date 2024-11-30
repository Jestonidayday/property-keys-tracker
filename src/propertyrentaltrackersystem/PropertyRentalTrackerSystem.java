package propertyrentaltrackersystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class PropertyRentalTrackerSystem {
    static Config conf = new Config();
    static Scanner scan = new Scanner(System.in);
    static Property property = new Property();
    static Key tbl_keys = new Key();
    static KeyLog keyLog = new KeyLog();

    public static void main(String[] args) {
        int choice;
        do {
            try {
                System.out.println("\n--- Property Rental Keys Tracker System ---");
                System.out.println("1. Properties");
                System.out.println("2. Keys");
                System.out.println("3. Key Logs");
                System.out.println("4. Reports");
                System.out.println("5. Exit");

                System.out.print("Enter option: ");
                choice = scan.nextInt();
                scan.nextLine();

                switch (choice) {
                    case 1:
                        property.propertyConfig();
                        break;
                    case 2:
                        tbl_keys.keyConfig();
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
            if (!conf.doesIDExist("tbl_property", "p_id", propId)) {
                System.out.println("Property ID not found. Please try again.");
            }
        } while (!conf.doesIDExist("tbl_property", "p_id", propId));

        System.out.println("\n=================================");
        System.out.println("       PROPERTY DETAILS          ");
        System.out.println("=================================");
        System.out.printf("Property ID  : %d%n", propId);
        System.out.printf("Name         : %s%n", conf.getDataFromID("tbl_property", propId, "p_id", "p_name"));
        System.out.printf("Address      : %s%n", conf.getDataFromID("tbl_property", propId, "p_id", "p_address"));
        System.out.printf("Type         : %s%n", conf.getDataFromID("tbl_property", propId, "p_id", "p_type"));
        System.out.println("---------------------------------");

        if (conf.isTableEmpty("tbl_keylogs WHERE k_id IN (SELECT k_id FROM tbl_keys WHERE p_id = " + propId + ")")) {
            System.out.println("No Keys usage records found for this Property.");
        } else {
            System.out.println("Key Log Details:");
            String sql = "SELECT tbl_keylogs.keylog_id, tbl_keys.key_code, tbl_keylogs.issued_to, tbl_keylogs.date, tbl_keylogs.action " +
                         "FROM tbl_keylogs " +
                         "JOIN tbl_keys ON tbl_keylogs.k_id = tbl_keys.k_id " +
                         "WHERE tbl_keys.p_id = " + propId;

            String[] headers = {"Log ID", "Key Code", "Issued To", "Date", "Action"};
            String[] columns = {"keylog_id", "key_code", "issued_to", "date", "action"};

            conf.viewRecords(sql, headers, columns);
            System.out.println("\n=================================");
        }
    }

}
