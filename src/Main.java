import java.util.*;

public class Main {

    private static SystemManager systemManager;

    public static void main(String[] args) {
        SystemManager systemManager = new SystemManager();
    }

    public void getUSerInput() {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object

        System.out.println("Welcome to ePark ! \n" +
                "1.    Register child \n" +
                "2.    Manage ticket \n" +
                "3.    Add ride \n" +
                "4.    Remove ride \n" +
                "5.    Exit park \n" +
                "6.    exit");
        System.out.println("Please write the number or the followed text");

        String choice;
        while (true) {
            choice = myObj.nextLine();
            switch (choice) {
                case "Add ride":
                case "3": {
                    myObj = new Scanner(System.in);
                    System.out.println("please enter the kid's id that you wish to add ride to");
                    String kidID = myObj.nextLine();

                    // check the allowed devices for a kid
                    ArrayList<Device> allowedDevices = systemManager.getAllowedDevices(kidID);
                    ArrayList<Device> selectedDevices = new ArrayList<>();

                    System.out.println("chose the wanted devices \n" +
                            "please chose only one device at a time \n" +
                            "write 'i'm done' or 0 when you're done choosing"
                    );

                    // selecting devices
                    while (true) {
                        // print names
                        for (Device device : allowedDevices) {
                            System.out.println(device.getName());
                        }

                        // choice
                        String device_choice = myObj.nextLine();

                        // checking if they want to finish
                        if (Objects.equals(device_choice, "i'm done") || Objects.equals(device_choice, "0")) {
                            break;
                        }

                        // checking if the answer matches a device
                        for (Device device : allowedDevices) {
                            if (Objects.equals(device.getName(), device_choice)) {
                                // the device has been chosen already
                                if (selectedDevices.contains(device)) {
                                    System.out.println("the deceive has already been selected");
                                }
                                selectedDevices.add(device);
                            }
                        }
                    }

                    // checking if the devices are extreme
                    ArrayList<Device> extremeDevices = systemManager.addEntryToTicketForDevices(kidID, selectedDevices, getAccountFromKidId(kidID));
                    if (extremeDevices != null) {
                        ArrayList<Device> approvedDevice = new ArrayList<>();
                        System.out.println("for each extreme device- please enter yes if you approve");
                        for (Device device : extremeDevices) {
                            System.out.println(device.getName());
                            String answer = myObj.nextLine();
                            if (Objects.equals(answer, "yes")) {
                                approvedDevice.add(device);
                            }
                        }
                        systemManager.addApprovedDevices(kidID, approvedDevice, getAccountFromKidId(kidID));
                    }
                }

                case "Remove ride":
                case "4": {
                    System.out.println("please enter the kid's id that you wish to remove ride from");
                    String kidID = myObj.nextLine();

                    // get all the kid's entries
                    Hashtable<Device, Integer> entries = systemManager.getEntries(kidID);

                    System.out.println("chose the wanted devices \n" +
                            "please chose only one device at a time \n" +
                            "write 'i'm done' or 0 when you're done choosing"
                    );
                    ArrayList<Device> selectedDevices = new ArrayList<>();

                    // get the entries dictionary
                    for (Map.Entry<Device, Integer> entry : entries.entrySet()) {
                        String mes = entry.getKey().getName() + " number of entries: " + entry.getValue();
                        System.out.println(mes);
                    }

                    String device_choice = myObj.nextLine();
                    while (!(Objects.equals(device_choice, "i'm done") || Objects.equals(device_choice, "0"))) {
                        for (Device device : entries.keySet()) {
                            // wanting to remove said device
                            if (Objects.equals(device_choice, device.getName())) {
                                selectedDevices.add(device);
                            }
                        }
                    }
                    systemManager.removeEntryFromDevices(kidID, selectedDevices, getAccountFromKidId(kidID));
                }

                case "Exit park":
                case "5":
                    System.out.println("Please enter the kid's ID");
                    String kidID = myObj.nextLine();
                    System.out.println("Please confirm that you returned the bracelet: yes/no");
                    String returned = myObj.nextLine();
                    while (!(Objects.equals(returned, "yes"))) {
                        System.out.println("Please confirm that you returned the bracelet: yes/no");
                        returned = myObj.nextLine();
                        System.out.println("thank you, goodbye");
                        // TODO- remove from the bracelet list

                    }

                case "exit":
                case "6": {
                    break;
                }
            }
        }
    }


    // TODO- todo
    public Account getAccountFromKidId(String kid_id) {
        return null;
    }

    public void view_SignupForm() {
        //TODO: implement
    }

    public void view_paymentForm() {
        //TODO: implement
    }
}
