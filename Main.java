import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HardwareRepository repository = new HardwareRepository();

        int choice;

        do {
            System.out.println("\n=== HARDWARE LOGIC INTERPRETER ===");
            System.out.println("1. Show Hardware Masterlist");
            System.out.println("2. Search by Spec");
            System.out.println("3. Add New Item");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    showMasterlist(repository);
                    break;

                case 2:
                    searchBySpec(repository, scanner);
                    break;

                case 3:
                    addNewItem(repository, scanner);
                    break;

                case 4:
                    System.out.println("Program exited.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 4);

        scanner.close();
    }

    public static void showMasterlist(HardwareRepository repository) {
        List<Hardware> hardwareList = repository.getAllHardware();

        int laptop16Count = 0;
        int laptop32Count = 0;
        int phone50Count = 0;

        System.out.println("\n=== HARDWARE MASTERLIST ===");

        for (Hardware hardware : hardwareList) {
            System.out.println("ID: " + hardware.getId()
                    + " | Brand: " + hardware.getBrand()
                    + " | Type: " + getType(hardware)
                    + " | Spec: " + hardware.interpretSpec());

            if (hardware instanceof Laptop && hardware.getSpec() == 16) {
                laptop16Count++;
            } else if (hardware instanceof Laptop && hardware.getSpec() == 32) {
                laptop32Count++;
            } else if (hardware instanceof Phone && hardware.getSpec() == 50) {
                phone50Count++;
            }
        }

        System.out.println("\n=== INVENTORY COUNT ===");
        System.out.println("Total number of 16GB Laptops: " + laptop16Count);
        System.out.println("Total number of 32GB Laptops: " + laptop32Count);
        System.out.println("Total number of 50MP Phones: " + phone50Count);
    }

    public static void searchBySpec(HardwareRepository repository, Scanner scanner) {
        List<Hardware> hardwareList = repository.getAllHardware();

        System.out.print("\nEnter spec: ");
        int inputSpec = scanner.nextInt();

        boolean found = false;

        System.out.println("\n=== SEARCH RESULT ===");

        for (Hardware hardware : hardwareList) {
            if (hardware.getSpec() == inputSpec) {
                System.out.println("ID: " + hardware.getId()
                        + " | Brand: " + hardware.getBrand()
                        + " | Type: " + getType(hardware)
                        + " | Spec: " + hardware.interpretSpec());

                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching hardware found.");
        }
    }

    public static void addNewItem(HardwareRepository repository, Scanner scanner) {
        scanner.nextLine();

        System.out.print("\nEnter brand: ");
        String brand = scanner.nextLine();

        System.out.print("Enter spec: ");
        int spec = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Enter type (Laptop/Phone): ");
        String type = scanner.nextLine();

        repository.addHardware(brand, spec);

        System.out.println("\nAdded item:");
        System.out.println("Brand: " + brand);
        System.out.println("Spec: " + spec);
        System.out.println("Type: " + type + " (not stored in database)");
    }

    public static String getType(Hardware hardware) {
        if (hardware instanceof Laptop) {
            return "Laptop";
        } else if (hardware instanceof Phone) {
            return "Phone";
        } else {
            return "Unknown";
        }
    }
}