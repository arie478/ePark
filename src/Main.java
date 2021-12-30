import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

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

        String choice = myObj.nextLine();  // Read user input
        switch (choice) {
            case "Add ride":
            case "3": {
                addRide();
            }

            case "Remove ride":
            case "4": {
                System.out.println("please enter the kid's id that you wish to remove ride from");
                String kidID = myObj.nextLine();
                ArrayList<Device> selectedDevices = getSelectedDevices(myObj);
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
                    // TODO- remove from list

                }
        }
    }

    public void addRide() {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("please enter the kid's id that you wish to add ride to");
        String kidID = myObj.nextLine();
        ArrayList<Device> selectedDevices = getSelectedDevices(myObj);
        ArrayList<Device> extremeDevices = systemManager.addEntryToTicketForDevices(kidID, selectedDevices);
        ArrayList<Device> approvedDevice = new ArrayList<>();
        System.out.println("for each device please write if you approve- please enter yes/no");
        for (Device device : extremeDevices) {
            System.out.println(device.getName());
            String answer = myObj.nextLine();
            if (Objects.equals(answer, "yes")) {
                approvedDevice.add(device);
            }
        }
        systemManager.addApprovedDevices(kidID, approvedDevice, getAccountFromKidId(kidID));
    }

    public ArrayList<Device> getSelectedDevices(Scanner myObj) {

        System.out.println("chose the wanted devices \n" +
                "please chose only one device at a time \n" +
                "write 'i'm done' or 0 when you're done choosing"
        );
        ArrayList<Device> selectedDevices = new ArrayList<>();
        String device_choice = myObj.nextLine();
        while (!(Objects.equals(device_choice, "i'm done") || Objects.equals(device_choice, "0"))) {
            // TODO- add devices names
            switch (device_choice) {
                case "Mamba":
                    Device mamba = new Device();
                    selectedDevices.add(mamba);
            }
        }
        return selectedDevices;
    }

    // TODO- todo
    public Account getAccountFromKidId(String kid_id) {
        return null;
    }

    public void view_SignupForm(){
        //TODO: implement
    }

    public void view_paymentForm(){
        //TODO: implement
    }
}
