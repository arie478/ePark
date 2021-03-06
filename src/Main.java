import java.util.*;

public class Main {

    private static SystemManager systemManager;
    private static Guardian guardian;
    private static Account account;

    public static List<Object> systemObjects;


    public static void getUSerInput() {

        addDefaultDevices();

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object

        String choice;


        do {
            try {


                System.out.println("Welcome to ePark ! \n" +
                        "1.    Register child \n" +
                        "2.    Manage ticket \n" +
                        "3.    Add ride \n" +
                        "4.    Remove ride \n" +
                        "5.    Exit park \n" +
                        "6.    exit");
                System.out.println("Please write the number or the followed text");

                choice = myObj.nextLine();
                switch (choice) {
                    case "Register child":
                    case "1": {
                        System.out.println("Welcome!\nPlease enter your kid's name:\n");
                        String kidName = myObj.nextLine();
                        if (!((kidName.matches(".*[a-z].*")) || kidName.matches(".*[A-Z].*"))) {
                            System.out.println("please enter letters only");
                            break;
                        }
                        System.out.println("Please enter your kid's age:\n");
                        String kidAge = myObj.nextLine();
                        if (!(kidAge.matches(".*\\d.*"))) {
                            System.out.println("please enter numbers only");
                            break;
                        }

                        int age = 0;
                        try
                        {
                            age = Integer.parseInt(kidAge);
                        }
                        catch (Exception exc)
                        {
                            System.out.println("please enter numbers only");
                            break;
                        }

                        if (age <= 0) {
                            System.out.println("please enter age above 0");
                            break;
                        }

                        System.out.println("checking details...\n");
                        Kid newKid = fill_SignupForm(kidName, age, guardian);
                        if (newKid == null) {
                            break;
                        }
                        if (account == null) {
                            systemObjects.add(newKid);
                            System.out.println("Please enter your creditCard number:\n");
                            String creditCard = myObj.nextLine();
                            if (!(creditCard.matches(".*\\d.*"))) {
                                System.out.println("creditCard cannot have characters");
                                break;
                            }
                            System.out.println("Please enter your topLimit:\n");
                            String topLimit = myObj.nextLine();
                            if (!(topLimit.matches(".*\\d.*"))) {
                                System.out.println("topLimit cannot have characters");
                                break;
                            }

                            System.out.println("checking details...\n");

                            int creditCardNum = 0;
                            try
                            {
                                creditCardNum = Integer.parseInt(creditCard);
                            }
                            catch (Exception exc)
                            {
                                System.out.println("please enter numbers only");
                                break;
                            }

                            int topLimitInt = 0;
                            try
                            {
                                topLimitInt = Integer.parseInt(topLimit);
                            }
                            catch (Exception exc)
                            {
                                System.out.println("please enter numbers only");
                                break;
                            }


                            boolean stat = creditInfo(creditCardNum, topLimitInt);
                            if (!stat) {
                                systemObjects.remove(newKid);
                                break;
                            }


                            account = new Account(systemManager, guardian);
                            systemObjects.add(account);
                        }

                        // john
                        guardian.setAccount(account);
                        //
                        systemManager.setKidID(newKid);
                        ElectronicBracelet newElectronicBracelet = systemManager.create_electronicBracelet();
                        systemObjects.add(newElectronicBracelet);
                        String password = systemManager.getNewPassword(guardian);
                        guardian.addKidPassward(newKid.getID(), password);
                        System.out.println("Your id is: " + newKid.getID());
                        System.out.println("Your password is: " + password);
                        eTicket ticket = systemManager.create_eTicket(password, newElectronicBracelet, systemManager);
                        systemObjects.add(ticket);
                        ticket.setElectronicBracelet(newElectronicBracelet);
                        connectEbToKid(newElectronicBracelet, newKid);
                        //john
                        newElectronicBracelet.seteTicket(ticket);
                        //

                        System.out.println("Now measure your kid please with the measure scale\n");
                        System.out.println("waiting...\n");
                        System.out.println("Enter kid's weight (kg): \n");
                        String kidWeight = myObj.nextLine();
                        if (!(kidWeight.matches(".*\\d.*"))) {
                            System.out.println("kidWeight cannot have characters");
                            break;
                        }
                        System.out.println("Enter kid's height (cm): \n");
                        String kidHeight = myObj.nextLine();
                        if (!(kidHeight.matches(".*\\d.*"))) {
                            System.out.println("kidHeight cannot have characters");
                            break;
                        }

                        int kidWeightInt = 1;
                        try
                        {
                            kidWeightInt = Integer.parseInt(kidWeight);
                        }
                        catch (Exception exc)
                        {
                            System.out.println("please enter numbers only");
                            break;
                        }

                        int kidHeightInt = 1;
                        try
                        {
                            kidHeightInt = Integer.parseInt(kidHeight);
                        }
                        catch (Exception exc)
                        {
                            System.out.println("please enter numbers only");
                            break;
                        }


                        guardian.setWeightAndHeight(kidWeightInt, kidHeightInt, newKid.getID());
                        System.out.println(kidName + " was successfully registered !");
                        break;
                    }

                    /**
                     * Manage ticket
                     */
                    case "Manage ticket":
                    case "2": {
                        System.out.println("Please enter your kid's id:\n");
                        String kidId = myObj.nextLine();

                        // checking if the kid exist
                        if (!systemManager.checkForKid(kidId)) {
                            System.out.println("there's no kid register with that id." +
                                    "you can register you kid by pressing 1");
                            break;
                        }

                        System.out.println("Please enter password:\n");
                        String password = myObj.nextLine();

                        if (systemManager.connectToTicket(kidId, password)) {
                            System.out.printf("Successfully connected to %s's ticket\n", kidId);
                            systemManager.showTicket(kidId);
                        } else {
                            System.out.printf("Wrong password for %s's ticket\n", kidId);
                        }

                        break;
                    }

                    case "Add ride":
                    case "3": {
                        myObj = new Scanner(System.in);
                        System.out.println("please enter the kid's id that you wish to add ride to");
                        String kidID = myObj.nextLine();

                        // checking if the kid exist
                        Boolean kidRegistered = systemManager.checkForKid(kidID);
                        if (!kidRegistered) {
                            System.out.println("there's no kid register with that id." +
                                    "you can register you kid by pressing 1");
                            break;
                        }

                        // check the allowed devices for a kid
                        ArrayList<Device> allowedDevices = systemManager.getAllowedDevices(kidID);

                        ArrayList<Device> selectedDevices = new ArrayList<>();
                        if (allowedDevices == null) {
                            System.out.println("there are no devices suitable for you kid");
                            break;
                        }


                        System.out.println("chose the wanted devices \n" +
                                "please chose only one device at a time \n" +
                                "write 'i'm done' or 0 when you're done choosing"
                        );

                        // selecting devices
                        while (true) {
                            // print names
                            for (Device device : allowedDevices) {
                                System.out.println("device: " + device.getName() + " | price: " + device.getPrice());
                            }

                            // choice
                            String device_choice = myObj.nextLine();

                            // checking if they want to finish
                            if (Objects.equals(device_choice, "i'm done") || Objects.equals(device_choice, "0")) {
                                break;
                            }

                            Boolean isDevice = false;
                            Boolean printFinalMessage = true;
                            // checking if the answer matches a device
                            for (Device device : allowedDevices) {
                                if (device.getName().equalsIgnoreCase(device_choice.toLowerCase(Locale.ROOT)))
//                            if (Objects.equalsIgnoreCase(device.getName(), device_choice.))
                                {
                                    isDevice = true;
                                    // the device has been chosen already
                                    if (selectedDevices.contains(device)) {
                                        String msg = device_choice + " has already been selected";
                                        System.out.println(msg);


                                        printFinalMessage = false;
                                        continue;

                                    }
                                    selectedDevices.add(device);
                                    //john
                                    Account a = guardian.getAccount();
//                                    a.updateEntries(kidID, device.getPrice());
                                    //
                                }
                            }
                            if (printFinalMessage) {
                                if (!isDevice) {
                                    System.out.println("we couldn't recognize the device. please look at the list, or check your spelling");
                                } else {
                                    String msg = device_choice + " has been added to your selected rides";
                                    System.out.println(msg);

                                }
                            }
                        }

                        // checking if the devices are extreme
                        ArrayList<Device> extremeDevices = systemManager.addEntryToTicketForDevices(kidID, selectedDevices, account);
                        if (extremeDevices != null) {
                            ArrayList<Device> approvedDevice = new ArrayList<>();
                            System.out.println("for each extreme device- please enter yes if you approve");
                            for (Device device : extremeDevices) {
                                System.out.println(device.getName());
                                String answer = myObj.nextLine();
                                if (Objects.equals(answer, "yes")) {
                                    approvedDevice.add(device);
                                    System.out.println("entry has been added to " + device.getName());

                                }
                            }
                            systemManager.addApprovedDevices(kidID, approvedDevice, account);

                            break;

                        }

                        //john
                        break;
                    }

                    case "Remove ride":
                    case "4": {
                        System.out.println("please enter the kid's id that you wish to remove ride from");
                        String kidID = myObj.nextLine();

                        // checking if the kid exist
                        Boolean kidRegistered = systemManager.checkForKid(kidID);
                        if (!kidRegistered) {
                            System.out.println("there's no kid registered with that id." +
                                    "you can register you kid by pressing 1");
                            break;
                        }
                        // get all the kid's entries
                        Hashtable<Device, Integer> entries = systemManager.getEntries(kidID);

                        System.out.println("choose the wanted devices \n" +
                                "please chose only one device at a time \n" +
                                "write 'i'm done' or 0 when you're done choosing"
                        );
                        ArrayList<Device> selectedDevices = new ArrayList<>();
                        while (true) {

                            // get the entries dictionary
                            for (Map.Entry<Device, Integer> entry : entries.entrySet()) {
                                String mes = entry.getKey().getName() + " number of entries: " + entry.getValue();
                                System.out.println(mes);
                            }

                            String device_choice = myObj.nextLine();
                            if (Objects.equals(device_choice, "i'm done") || Objects.equals(device_choice, "0")) {
                                break;
                            }

                            for (Device device : entries.keySet()) {
                                // wanting to remove said device
                                if (device.getName().equalsIgnoreCase(device_choice.toLowerCase(Locale.ROOT))) {
                                    selectedDevices.add(device);
                                    System.out.println("entry has been remove from " + device.getName());
                                    systemManager.removeEntryFromDevices(kidID, selectedDevices, account);
                                    selectedDevices = new ArrayList<>();
                                }
                            }
                            device_choice = null;
//                        entries = systemManager.getEntries(kidID);
//                        if ()
                        }

                        break;
                    }

                    case "Exit park":
                    case "5":


                        System.out.println("Please enter the kid's ID");
                        String kidID = myObj.nextLine();

                        // checking if the kid exist
                        Boolean kidRegistered = systemManager.checkForKid(kidID);
                        if (!kidRegistered) {
                            System.out.println("there's no kid register with that id." +
                                    "you can register you kid by pressing 1");
                            break;
                        }

                        //get the kid to remove
                        Kid removedKid = guardian.getKidByID(kidID);


                        //check the balance
                        System.out.println("checking balance...");
                        Account account5 = guardian.getAccount();
                        int balance = account5.getBalance(kidID);
                        System.out.println("you have: " + guardian.getTopLimit());
                        System.out.println("and you owe: " + balance);
                        //you the balance is greater then the top limit

                        System.out.println("succsess, deducing your total limit to: ");
                        //update the top limit
                        guardian.setTopLimit(guardian.getTopLimit() - balance);
                        System.out.println(guardian.getTopLimit());
                        // remove kid from guaridan list
                        guardian.removeKid(removedKid);

                        //remove from kid from SystemManger
                        systemManager.removeKidsID(kidID);


                        ElectronicBracelet eb = removedKid.getElectronicBracelet();

                        // remove ticket for systemManager
                        eTicket ticket = eb.geteTicket();
                        systemManager.removeETicket(ticket);

                        // remove bracelet from systemManager
                        systemManager.removeBracelet(eb);

                        //remove from all objects

                        //remove kid
                        if (systemObjects.contains(removedKid)) {
                            systemObjects.remove(removedKid);
                        }

                        //remove bracelet
                        if (systemObjects.contains(eb)) {
                            systemObjects.remove(eb);
                        }

                        //remove ticket
                        if (systemObjects.contains(ticket)) {
                            systemObjects.remove(ticket);
                        }
                        System.out.println("The kid has left the park");

                        break;


                    case "exit":
                    case "6": {
                        System.out.println("bye bye...");
                        System.exit(0);

                    }

                }

            } catch (Exception exception) {
                System.out.println((exception.fillInStackTrace()));
                continue;
            }
        } while (true);
    }

    private static void addDefaultDevices() {
        ArrayList<Integer> mambaRestrictions = new ArrayList<>();
        mambaRestrictions.add(2147483647);
        mambaRestrictions.add(140);
        mambaRestrictions.add(12);
        int mambaRPrice = 50;
        Device mamba = new Device("Mamba Ride", true, mambaRestrictions, mambaRPrice, systemManager );
        systemManager.addDevice(mamba);
        systemObjects.add(mamba);

        ArrayList<Integer> GiantWheelRestrictions = new ArrayList<>();
        GiantWheelRestrictions.add(2147483647);
        GiantWheelRestrictions.add(0);
        GiantWheelRestrictions.add(0);
        int wheelRPrice = 30;
        Device wheel = new Device("Giant Wheel", false, GiantWheelRestrictions, wheelRPrice, systemManager );
        systemManager.addDevice(wheel);
        systemObjects.add(wheel);


        ArrayList<Integer> CarrouselRestrictions = new ArrayList<>();
        CarrouselRestrictions.add(2147483647);
        CarrouselRestrictions.add(0);
        CarrouselRestrictions.add(8);
        int CarrouselPrice = 10;
        Device carrousel = new Device("Carrousel", false, CarrouselRestrictions, CarrouselPrice, systemManager );
        systemManager.addDevice(carrousel);
        systemObjects.add(carrousel);

    }

    private static void connectEbToKid(ElectronicBracelet newElectronicBracelet, Kid newKid) {
        newElectronicBracelet.setKid(newKid);
        newKid.setElectronicBracelet(newElectronicBracelet);
    }


    public static Kid fill_SignupForm(String name, int age, Guardian guard) {
        if (name != null && age != 0) {
            if (age > 0 && !name.isEmpty()) {
                Kid kid = guard.createKid(age, name);
                return kid;
            } else if (age <= 0) {
                System.out.println("please try to register again with valid age\n");
                return null;
            } else if (name.isEmpty()) {
                System.out.println("please try to register again with valid name\n");
                return null;
            }
        }
        return null;
    }

    public static boolean creditInfo(int creditCard1, int topLimit1) {
        boolean ans = systemManager.isValidCredit(creditCard1, topLimit1);
        if (ans) {
            guardian.setCreditCard(creditCard1);
            guardian.setTopLimit(topLimit1);
            return true;
        }
        return false;

    }

    public static void main(String[] args) {
        SystemManager systemManager1 = new SystemManager();
        Guardian guardian1 = new Guardian();
        systemObjects = new ArrayList<>();
        systemObjects.add(systemManager);
        systemObjects.add(guardian);
        systemManager = systemManager1;
        guardian = guardian1;
        guardian.setSystemManager(systemManager);
        systemManager.connectToGuard(guardian);

        getUSerInput();
    }
}
