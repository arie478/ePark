import java.security.spec.RSAOtherPrimeInfo;
import java.util.*;

public class SystemManager {
    private ArrayList<eTicket> allETickets; //TODO- needs to add when adding kid
    private ArrayList<ElectronicBracelet> allBracelets; //TODO: connect to kid
    private ArrayList<Device> allDevices; //TODO- add
    private ArrayList<Account> allAccounts; //TODO: add
    private ArrayList<Guardian> allGuards; //TODO: add
    private ArrayList<String> allKidsID;
    private HashMap<Guardian,ArrayList<String> >allPassword;


    public SystemManager() {
        this.allBracelets = new ArrayList<>();
        this.allAccounts = new ArrayList<>();
        this.allETickets = new ArrayList<>();
        this.allDevices = new ArrayList<>();
        this.allGuards = new ArrayList<>();
        this.allKidsID = new ArrayList<>();
        this.allPassword = new HashMap<>();

    }

    public boolean removeETicket(eTicket ticket){
        if(allETickets.contains(ticket))
        {
            allETickets.remove(ticket);
            return true;
        }
        return false;
    }
    public boolean removeBracelet(ElectronicBracelet eb){
        if(allBracelets.contains(eb))
        {
            allBracelets.remove(eb);
            return true;
        }
        return false;
    }

    public boolean removeKidsID(String id){
        if(allKidsID.contains(id))
        {
            allKidsID.remove(id);
            return true;
        }
        return false;
    }

    public ArrayList<Device> addEntryToTicket(String kid_id) {
        return getAllowedDevices(kid_id);
    }


    /**
     * checks the allowed devices based on the kid's personal information
     */
    public ArrayList<Device> getAllowedDevices(String kid_id) {
        eTicket ticket = getTicketFromId(kid_id);
        ArrayList info = ticket.getKidInformation();
        ArrayList<Device> allowedDevices = new ArrayList<>();
        for (Device device : allDevices) {
            if (device.checkIfAllowed(info)) {
                allowedDevices.add(device);
            }
        }
        return allowedDevices;
    }


    /**
     * checks if extreme devices were chosen
     */
    public ArrayList<Device> addEntryToTicketForDevices(String kidID, ArrayList<Device> selectedDevices, Account account) {
        ArrayList<Device> extremeDevices = new ArrayList<>();
        for (Device device : selectedDevices) {
            if (checkIfExtreme(device)) {
                extremeDevices.add(device);
            }
        }
        if (extremeDevices.size() != 0) {
            return extremeDevices;
        }
        // there are no extreme devices
        addApprovedDevices(kidID, selectedDevices, account);
        return null;
    }


    /**
     * adding entry to the approved devices
     */
    public void addApprovedDevices(String kid_id, ArrayList<Device> devices, Account account) {
        eTicket ticket = getTicketFromId(kid_id);
        ArrayList<Device> devicesToAdd = new ArrayList<>();
        for(Device device : devices)
        {
            if(account.getBalance(kid_id) + device.getPrice() > account.getGuardian().getTopLimit())
            {
                System.out.println("Adding the device " + device.getName() +" excceds your top limit");
            }
            else
            {
                devicesToAdd.add(device);
            }
        }
        ticket.addEntries(devicesToAdd);
        account.addEntries(devicesToAdd);
    }


    /**
     * removing entry from devices
     */
    public void removeEntryFromDevices(String kid_id, ArrayList<Device> devices, Account account) {
        eTicket ticket = getTicketFromId(kid_id);
        ticket.removeEntries(devices);
        account.removeEntries(devices);
    }


    /**
     * checks if the given device is extreme
     */
    public boolean checkIfExtreme(Device device) {
        return device.getIsExtreme();
    }


    /**
     * returns the entry dictionary
     */
    public Hashtable<Device, Integer> getEntries(String kidID) {
        eTicket ticket = getTicketFromId(kidID);
        return ticket.getEntriesDictionary();
    }

    /**
     * getting eTicket object from the eTicket list
     */
    private eTicket getTicketFromId(String kid_id) {
        for (eTicket ticket : allETickets) {
            if (Objects.equals(ticket.getKidId(), kid_id)) {
                return ticket;
            }
        }
        return null;
    }

    /**
     * Try to connect to the ticket with given password
     * @param kid_id - String representing the kid's Id
     * @param password - String representing the kid's password
     * @return - Boolean if the password given is the password of the ticket
     */
    public Boolean connectToTicket(String kid_id, String password)
    {
        eTicket ticket = getTicketFromId(kid_id);
        if (ticket != null)
        {
            return ticket.getPassword().equals(password);
        }
        return false;
    }

    /**
     * Prints the ticket's details
     * @param kid_id - String representing the kid's Id
     */
    public void showTicket(String kid_id)
    {
        eTicket ticket = getTicketFromId(kid_id);
        if (ticket != null)
        {
            ticket.printMap();
        }
    }


    public boolean isValidCredit(Integer creditCardNum, Integer topLimit) {
        System.out.println("Sending credit info to credit company...");
        System.out.println("Checking credit info....");
        System.out.println(".......................................99%");
        boolean ans = creditCardNum != null && topLimit != null;
        if (ans) {
            System.out.println("- Credit info was verified successfully -");
        } else {
            System.out.println("- Credit info was not verified -");
        }
        return ans;
    }


    /**
     * @param guard
     * @param kidID
     * @return ArrayList<Integer> in the form: [ammount , creditcard, topLimit]
     */
    public ArrayList<Integer> getAmountAndCreditCard(Guardian guard, String kidID) {

        int ammount = 0;
        for (Account account :
                allAccounts) {
            if (account.getGuardian() == guard) {
                ammount = account.getBalance(kidID);
            }
        }
        ArrayList<Integer> info = new ArrayList<>();
        info.add(ammount);
        info.add(guard.getCreditInfo().get(0));
        info.add(guard.getCreditInfo().get(1));
        return info;
    }

    /**
     * @param guardian
     * @return guard's account if guardian not null, else returns null
     */
    public Account create_gAccount(Guardian guardian) {
        if (guardian != null) {
            Account account = new Account(this, guardian);
            this.allAccounts.add(account);
            guardian.setAccount(account);
            return account;
        }

        return null;
    }


    public eTicket create_eTicket(String password, ElectronicBracelet electronicBracelet, SystemManager systemManager) {
        eTicket ticket = new eTicket(password, electronicBracelet, systemManager);
        allETickets.add(ticket);
        return ticket;
    }

    public void deleteEticket(eTicket eTicket) {
        allETickets.remove(eTicket);
        //TODO: check if more actions needed

    }

    public void pay(int amount, ArrayList<Integer> cardDetails) {
        System.out.println("Total amount to pay is: " + amount);
        System.out.println("processing......");
        System.out.println("--- Payment accepted ---");
    }

    public ElectronicBracelet create_electronicBracelet() {
        ElectronicBracelet electronicBracelet = new ElectronicBracelet();
        Random random = new Random();
        int id = random.nextInt(800);
        electronicBracelet.setBracelet_id(String.valueOf(id));
        allBracelets.add(electronicBracelet);
        return electronicBracelet;
    }

    public void setKidID(Kid kid) {
        if (kid != null) {
            if (kid.getID() == null) {
                Random random = new Random();
                int id = random.nextInt(800);
                while (allKidsID.contains(String.valueOf(id))) {
                    id = random.nextInt(800);
                }
                kid.setID(String.valueOf(id));
                this.allKidsID.add(String.valueOf(id));
            }
        }
    }

    Boolean checkForKid(String kidsID) {
        eTicket ticket = getTicketFromId(kidsID);
        if (ticket == null) {
            return false;
        }
        return true;
    }

    public void connectToGuard(Guardian guardian) {
        this.allGuards.add(guardian);

    }

    public void addDevice(Device device) {
        allDevices.add(device);
    }

    public String getNewPassword(Guardian guardian) {
        Random random = new Random();
        StringBuilder newPassword = new StringBuilder();
        for (int i=0; i<7; i++){
            int rand = random.nextInt(9);
            newPassword.append(String.valueOf(rand));
        }
        String password = newPassword.toString();
        if (allPassword.get(guardian)==null){
            ArrayList<String> arr = new ArrayList<>();
            allPassword.put(guardian,arr);
        }
        allPassword.get(guardian).add(password);
        return password;
    }
}
