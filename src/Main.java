import java.util.*;

public class Main
{

    private static SystemManager systemManager;
    private static Guardian guardian;

    public static List<Object> systemObjects; //TODO: add/remove all objects to list


    public static void getUSerInput()
    {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object

        String choice;

        do
        {
            System.out.println("Welcome to ePark ! \n" +
                    "1.    Register child \n" +
                    "2.    Manage ticket \n" +
                    "3.    Add ride \n" +
                    "4.    Remove ride \n" +
                    "5.    Exit park \n" +
                    "6.    exit");
            System.out.println("Please write the number or the followed text");

            choice = myObj.nextLine();
            switch (choice)
            {
                case "Register child":
                case "1":
                {
                    System.out.println("Welcome!\nPlease enter your kid's name:\n");
                    String kidName = myObj.nextLine();
                    System.out.println("Please enter your kid's age:\n");
                    String kidAge = myObj.nextLine();
                    System.out.println("checking details...\n");
                    Kid newKid = fill_SignupForm(kidName, Integer.parseInt(kidAge), guardian);
                    System.out.println("Please enter your creditCard number:\n");
                    String creditCard = myObj.nextLine();
                    System.out.println("Please enter your topLimit:\n");
                    String topLimit = myObj.nextLine();
                    System.out.println("checking details...\n");
                    creditInfo(Integer.parseInt(creditCard), Integer.parseInt(topLimit));
                    systemManager.create_gAccount(guardian); //TODO: needs to be static?
                    systemManager.setKidID(newKid);
                    ElectronicBracelet newElectronicBracelet = systemManager.create_electronicBracelet();
                    String password = systemManager.getNewPassword(guardian);
                    guardian.addKidPassward(newKid.getID(),password);
                    System.out.println("Your id is: "+ newKid.getID());
                    System.out.println("Your password is: "+ password);
                    eTicket ticket = systemManager.create_eTicket(password, newElectronicBracelet, systemManager);
                    ticket.setElectronicBracelet(newElectronicBracelet);
                    connectEbToKid(newElectronicBracelet, newKid);
                    System.out.println("Now measure your kid please with the measure scale\n");
                    System.out.println("waiting...\n");
                    System.out.println("Enter kid's weight (kg): \n");
                    String kidWeight = myObj.nextLine();
                    System.out.println("Enter kid's height (cm): \n");
                    String kidHeight = myObj.nextLine();
                    guardian.setWeightAndHeight(Integer.parseInt(kidWeight), Integer.parseInt(kidHeight), newKid.getID());
                    System.out.println(kidName + " was successfully registered !");
                    break;
                }

                /**
                 * Manage ticket
                 */
                case "2":
                {
                    System.out.println("Please enter your kid's id:\n");
                    String kidId = myObj.nextLine();

                    // checking if the kid exist
                    if (!systemManager.checkForKid(kidId))
                    {
                        System.out.println("there's no kid register with that id." +
                                "you can register you kid by pressing 1");
                        break;
                    }

                    System.out.println("Please enter password:\n");
                    String password = myObj.nextLine();

                    if (systemManager.connectToTicket(kidId, password))
                    {
                        System.out.printf("Successfully connected to %s's ticket",kidId);
                    }
                    else
                    {
                        System.out.printf("Wrong password for %s's ticket",kidId);
                    }

                }

                case "Add ride":
                case "3":
                {
                    myObj = new Scanner(System.in);
                    System.out.println("please enter the kid's id that you wish to add ride to");
                    String kidID = myObj.nextLine();

                    // checking if the kid exist
                    Boolean kidRegistered = systemManager.checkForKid(kidID);
                    if (!kidRegistered)
                    {
                        System.out.println("there's no kid register with that id." +
                                "you can register you kid by pressing 1");
                        break;
                    }

                    // check the allowed devices for a kid
                    ArrayList<Device> allowedDevices = systemManager.getAllowedDevices(kidID);

                    ArrayList<Device> selectedDevices = new ArrayList<>();
                    if (allowedDevices == null)
                    {
                        System.out.println("there are no devices suitable for you kid");
                        break;
                    }


                    System.out.println("chose the wanted devices \n" +
                            "please chose only one device at a time \n" +
                            "write 'i'm done' or 0 when you're done choosing"
                    );

                    // selecting devices
                    while (true)
                    {
                        // print names
                        for (Device device : allowedDevices)
                        {
                            System.out.println("device: " + device.getName() + " price: " + device.getPrice());
                        }

                        // choice
                        String device_choice = myObj.nextLine();

                        // checking if they want to finish
                        if (Objects.equals(device_choice, "i'm done") || Objects.equals(device_choice, "0"))
                        {
                            break;
                        }

                        Boolean isDevice = false;
                        // checking if the answer matches a device
                        for (Device device : allowedDevices)
                        {
                            if (Objects.equals(device.getName(), device_choice))
                            {
                                isDevice = true;
                                // the device has been chosen already
                                if (selectedDevices.contains(device))
                                {
                                    System.out.println("the deceive has already been selected");
                                }
                                selectedDevices.add(device);
                            }
                        }
                        if (!isDevice)
                        {
                            System.out.println("we couldn't recognize the device. please look at the list, or check your spelling");
                        }
                    }

                    // checking if the devices are extreme
                    ArrayList<Device> extremeDevices = systemManager.addEntryToTicketForDevices(kidID, selectedDevices, guardian.getAccount());
                    if (extremeDevices != null)
                    {
                        ArrayList<Device> approvedDevice = new ArrayList<>();
                        System.out.println("for each extreme device- please enter yes if you approve");
                        for (Device device : extremeDevices)
                        {
                            System.out.println(device.getName());
                            String answer = myObj.nextLine();
                            if (Objects.equals(answer, "yes"))
                            {
                                approvedDevice.add(device);
                            }
                        }
                        systemManager.addApprovedDevices(kidID, approvedDevice, guardian.getAccount());
                    }
                }

                case "Remove ride":
                case "4":
                {
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
                    for (Map.Entry<Device, Integer> entry : entries.entrySet())
                    {
                        String mes = entry.getKey().getName() + " number of entries: " + entry.getValue();
                        System.out.println(mes);
                    }

                    String device_choice = myObj.nextLine();
                    while (!(Objects.equals(device_choice, "i'm done") || Objects.equals(device_choice, "0")))
                    {
                        for (Device device : entries.keySet())
                        {
                            // wanting to remove said device
                            if (Objects.equals(device_choice, device.getName()))
                            {
                                selectedDevices.add(device);
                            }
                        }
                    }
                    systemManager.removeEntryFromDevices(kidID, selectedDevices, guardian.getAccount());
                }

                case "Exit park":
                case "5":
                    System.out.println("Please enter the kid's ID");
                    String kidID = myObj.nextLine();
                    System.out.println("Please confirm that you returned the bracelet: yes/no");
                    String returned = myObj.nextLine();
                    while (!(Objects.equals(returned, "yes")))
                    {
                        System.out.println("Please confirm that you returned the bracelet: yes/no");
                        returned = myObj.nextLine();
                        System.out.println("thank you, goodbye");
                        // TODO- remove from the bracelet list

                    }

                case "exit":
                case "6":
                {
                    break;
                }
            }
        } while (true);
    }

    private static void connectEbToKid(ElectronicBracelet newElectronicBracelet, Kid newKid)
    {
        newElectronicBracelet.setKid(newKid);
        newKid.setElectronicBracelet(newElectronicBracelet);
    }


    public static Kid fill_SignupForm(String name, int age, Guardian guard)
    {
        if(name!=null && age!=0) {
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

    public static void creditInfo(int creditCard1, int topLimit1){
        systemManager.isValidCredit(creditCard1, topLimit1);
        guardian.setCreditCard(creditCard1);
        guardian.setTopLimit(topLimit1);
        //TODO: if not ok?
    }

    public static void main(String[] args)
    {
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
