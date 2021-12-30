public class Device
{
    // Attributes
    private String name;
    private Boolean isExtreme;
    private Boolean isWorking;
    private int[] restrictions;
    private int price;

    // Attributes from association
    private SystemManager systemManager;

    /**
     * Constructor for device object
     * @param newName - String representing the name of the device
     * @param newIsExtreme - Boolean representing if the device is extreme or not
     * @param newRestrictions - Int array representing the minimum measurements required to use the device
     * @param newPrice - Int representing the price of the device entry
     * @param newSystemManager - SystemManager object representing the system of the E-Park the device belongs to
     */
    public Device(String newName, Boolean newIsExtreme, int[] newRestrictions, int newPrice, SystemManager newSystemManager)
    {
        name = newName;
        isExtreme = newIsExtreme;
        restrictions = newRestrictions;
        price = newPrice;

        systemManager = newSystemManager;
    }

    /**
     * Returns the price of the device
     * @return - int representing the price of the device
     */
    public int getPrice()
    {
      return price;
    }

    /**
     *  Returns the name of the device
     * @return - string representing the name of the device
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the working status of the device
     * @return - boolean representing the working status of the device
     */
    public Boolean getIsWorking()
    {
        return isWorking;
    }

    /**
     *  Returns if the device is extreme
     * @return - boolean representing if the device is extreme
     */
    public Boolean getIsExtreme()
    {
        return isExtreme;
    }

    /**
     * Sets the working status of the device
     * @param status - boolean representing if the device is working
     */
    public void setIsWorking(Boolean status)
    {
        isWorking = status;
    }

    /**
     * Checks if the child is allowed to enter the device
     * @param kidMeasurements - int[] representing the child's measurements
     * Order is : [0] = weight, [1] = height, [2] = age
     * @return - boolean if the child is allowed on the device or not
     */
    public Boolean checkIfAllowed(int[] kidMeasurements)
    {
        // Check if weight is under limit, height and age are above limit
        return kidMeasurements[0] <= restrictions[0] && kidMeasurements[1] >= restrictions[1] && kidMeasurements[2] >= restrictions[2];
    }

}
