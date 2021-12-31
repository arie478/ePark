import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Guardian {
    // Attributes
    //private String creditCard;
    private int creditCard;
    private int topLimit;
    private Map<String, String> allPasswards;

    // Attributes from association
    private SystemManager systemManager;
    private Account account;
    private ArrayList<Kid> kids;


    public Guardian() {
        this.kids = new ArrayList<>();
        allPasswards = new HashMap<>();
    }

    /**
     * Adds a kid to the password dictionary
     *
     * @param kidId - Int representing the id of the kid
     */
    public void addKidPassward(String kidId, String password) {
        allPasswards.put(kidId, password);
    }

    /**
     * Remove a kid from the  password dictionary
     *
     * @param kidId - Int representing the id of the kid
     */
    public void deleteChildPassward(String kidId) {
        allPasswards.remove(kidId);
    }

    /**
     * Returns the credit info
     *
     * @return - [creditCard,topLimit]
     */
    public ArrayList<Integer> getCreditInfo() {
        ArrayList<Integer> cardInfo = new ArrayList<>();
        cardInfo.add(creditCard);
        cardInfo.add(topLimit);
        return cardInfo;
    }

    public void requestElectronicBracletFromChild(String kidId) {
        // Kid kid = kids.get(kidId);
    }

    public void setWeightAndHeight(int weight, int height, String kidID) {
        for (Kid kid :
                kids) {
            if (kidID.equals(kid.getID())) {
                kid.setHeight(height);
                kid.setWeight(weight);
            }
        }
    }

    public Kid createKid(int age, String name) {
        Kid kid = new Kid(age, name, this);
        kids.add(kid);
        return kid;
    }

    public void setCreditCard(int creditCard) {
        this.creditCard = creditCard;
    }

    public void setTopLimit(int topLimit) {
        this.topLimit = topLimit;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setSystemManager(SystemManager systemManager) {
        this.systemManager = systemManager;
    }
}
