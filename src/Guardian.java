import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Guardian
{
    // Attributes
    //private String creditCard;
    private int creditCard;
    private int topLimit;
    private Map<String, String> allPasswards;

    // Attributes from association
    private SystemManager systemManager;
    private Account account;
    private List<Kid> kids;

    /**
     * Constructor for Guardian object
     * @param newCreditCard - String representing the credit card number of the guardian
     * @param newTopLimit - Int representing the credit limit for the guardian's credit card
     */
    public Guardian(int newCreditCard, int newTopLimit)
    {
        creditCard = newCreditCard;
        topLimit = newTopLimit;
        allPasswards = new HashMap<>();
    }

    /**
     * Adds a kid to the password dictionary
     * @param kidId - Int representing the id of the kid
     */
    public void addKid(String kidId, String password)
    {
        allPasswards.put(kidId, password);
    }

    /**
     * Remove a kid from the  password dictionary
     * @param kidId - Int representing the id of the kid
     */
    public void deleteChild(String kidId)
    {
        allPasswards.remove(kidId);
    }

    /**
     * Returns the credit info
     * @return - [creditCard,topLimit]
     */
    public ArrayList<Integer> getCreditInfo()
    {
        ArrayList<Integer> cardInfo = new ArrayList<>();
        cardInfo.add(creditCard);
        cardInfo.add(topLimit);
        return cardInfo;
    }

    public void requestElectronicBracletFromChild(String kidId)
    {
        // Kid kid = kids.get(kidId);
    }

}
