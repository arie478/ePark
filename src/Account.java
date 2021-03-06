import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Account
{
    // Attributes
    private Map<String, Integer> totalPurchases;

    // Attributes from association
    private SystemManager systemManager;
    private Guardian guardian;

    /**
     * Constructor for Account object
     * @param newSystemManagers - System manager Object representing the system of the E-Park the account is park of
     * @param newGuardian - Guardian Object representing the guardian this account belongs to
     */
    public Account(SystemManager newSystemManagers, Guardian newGuardian)
    {
        totalPurchases = new HashMap<>();
        systemManager = newSystemManagers;
        guardian = newGuardian;
    }

    /**
     * Returns how much the guardian owes to pay for the chosen kid
     * @param kidId - Int representing the id of the kid
     * @return - Int representing how much money the guardian needs to pay for the kids entry to the devices
     */
    public int getBalance(String kidId)
    {
        if(totalPurchases.isEmpty())
        {
            return 0;
        }
        return totalPurchases.get(kidId);
    }

    /**
     * Updates
     * @param kidId - Int representing the id of the kid
     * @param price - Int representing the price of the device we want to add entry to
     */
    public void updateEntries(String kidId, int price)
    {
       if(totalPurchases.containsKey(kidId)) {
           totalPurchases.put(kidId, totalPurchases.get(kidId) + price);
           return;
       }
       totalPurchases.put(kidId,price);
    }

    /**
     * Adds a kid to the account history to keep track of the balance
     * @param kidId - Int representing the id of the kid
     */
    public void addKid(String kidId)
    {
        totalPurchases.put(kidId, 0);
    }

    /**
     * Remove a kid from the account history
     * @param kidId - Int representing the id of the kid
     */
    public void removeKid(String kidId)
    {
        totalPurchases.remove(kidId);
    }

    public void addEntries(String kidId, ArrayList<Device> devices){
        Integer sum_to_add = 0;
        for (Device device: devices){
            sum_to_add += device.getPrice();
        }
        Integer current_sum = totalPurchases.get(kidId);
        if (current_sum == null){
            totalPurchases.put(kidId,sum_to_add);
        }
        else {
            totalPurchases.put(kidId, current_sum + sum_to_add);
        }

    }
    public void removeEntries(String kidId, ArrayList<Device> devices){
        Integer sum_to_remove = 0;
        for (Device device: devices){
            sum_to_remove += device.getPrice();
        }
        Integer current_sum = totalPurchases.get(kidId);
        if (current_sum == null){
            totalPurchases.put(kidId,0);
        }
        else {
            totalPurchases.put(kidId,current_sum -  sum_to_remove);

        }

    }

    public Guardian getGuardian(){
        return guardian;
    }
}
