import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Guardian
{
    // Attributes
    private String creditCard;
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
    public Guardian(String newCreditCard, int newTopLimit)
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
     * Returns the credit card number
     * @return - String representing the credit card number
     */
    public String getCreditInfo()
    {
        return creditCard;
    }

    public void requestElectronicBracletFromChild(String kidId)
    {
        // Kid kid = kids.get(kidId);
    }

}
