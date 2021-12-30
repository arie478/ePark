import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class SystemManager {
    private ArrayList<eTicket> allETickets; //TODO- needs to add when adding kid
    private ArrayList<ElectronicBracelet> allBracelets; //TODO: connect to kid
    private ArrayList<Device> allDevices; //TODO- add
    private ArrayList<Account> allAccounts; //TODO: add
    private ArrayList<Guardian> allGuards; //TODO: add

    public ArrayList<Device> addEntryToTicket(String kid_id) {
        return getAllowedDevices(kid_id);
    }

    private ArrayList<Device> getAllowedDevices(String kid_id){
        eTicket ticket = getTicketFromId(kid_id);
        //int[] info = ticket.getPersonalInfo();
        ArrayList info = ticket.getKidInformation();
        ArrayList<Device> allowedDevices = new ArrayList<>();
        for (Device device: allDevices){
            if (device.checkIfAllowed(info)){
                allowedDevices.add(device);
            }
        }
        return allowedDevices;
    }

    public ArrayList<Device> addEntryToTicketForDevices(String kidID, ArrayList<Device> selectedDevices) {
        ArrayList<Device> extremeDevices = new ArrayList<>();
        for (Device device: selectedDevices){
            if (checkIfExtreme(device)){
                extremeDevices.add(device);
            }
        }
        return extremeDevices;
    }

    public void addApprovedDevices(String kid_id, ArrayList<Device> devices, Account account){
        eTicket ticket = getTicketFromId(kid_id);
        ticket.addEntries(devices);
        account.addEntries(devices);
    }

//    public void askForApproval(extremeDevices){
//
//    }

    public void removeEntryFromDevices(String kid_id, ArrayList<Device> devices, Account account){
        eTicket ticket = getTicketFromId(kid_id);
        ticket.removeEntries(devices);
        account.removeEntries(devices);
    }

    public boolean checkIfExtreme(Device device){
        return device.getIsExtreme();
    }



    private eTicket getTicketFromId(String kid_id)
    {
        return null; // TODO: implement
    }

    public boolean isValidCredit(Integer creditCardNum, Integer topLimit){
        System.out.println("Sending credit info to credit company...");
        System.out.println("Checking credit info....");
        System.out.println(".......................................99%");
        boolean ans =creditCardNum != null && topLimit != null;
        if(ans){
            System.out.println("- Credit info was verified successfully -");
        }
        else{
            System.out.println("- Credit info was not verified -");
        }
        return ans;
    }


    /**
     * @param guard
     * @param kidID
     * @return ArrayList<Integer> in the form: [ammount , creditcard, topLimit]
     */
    public ArrayList<Integer> getAmountAndCreditCard(Guardian guard, String kidID){

        int ammount = 0;
        for (Account account:
             allAccounts) {
            if (account.getGuardian() == guard){
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
    public Account create_gAccount(Guardian guardian){
        if (guardian!=null){
            return new Account(this,guardian);
        }
        return null;
    }


    public eTicket create_eTicket(){
        // TODO: implement
        return null;
    }

    public void deleteEticket(eTicket eTicket){
        allETickets.remove(eTicket);
        //TODO: check if more actions needed

    }

    public void pay(int amount, ArrayList<Integer>cardDetails){
        System.out.println("Total amount to pay is: "+amount);
        System.out.println("processing......");
        System.out.println("--- Payment accepted ---");
    }
}
