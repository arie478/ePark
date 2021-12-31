import java.util.ArrayList;
import java.util.*;

public class eTicket {
    private String password;
    private ElectronicBracelet electronicBracelet;
    private SystemManager systemManager;

    Hashtable<Device, Integer> my_dict; //new Hashtable<String, Integer>()

    ArrayList<Device> devices;


    public eTicket(String password, ElectronicBracelet electronicBracelet, SystemManager systemManager) {
        this.password = password;
        this.electronicBracelet = electronicBracelet;
        this.systemManager = systemManager;
        this.my_dict = new Hashtable<Device, Integer>();
    }

    public SystemManager getSystemManager() {
        return systemManager;
    }

    public void setSystemManager(SystemManager systemManager) {
        this.systemManager = systemManager;
    }

    public ElectronicBracelet getElectronicBracelet() {
        return electronicBracelet;
    }

    public void setElectronicBracelet(ElectronicBracelet electronicBracelet) {
        this.electronicBracelet = electronicBracelet;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId(){
        return electronicBracelet.getKid().getID();
    }

    public ArrayList getKidInformation(){
        ArrayList arr = electronicBracelet.getKidInformation();
        return arr;
    }
    public void addEntries(ArrayList<Device> list){
        for (Device device : list)
        {
            if(!my_dict.containsKey(device)){
                my_dict.put(device,1);
            }
            else {
                int previous_value = my_dict.get(device);
                my_dict.put(device, previous_value + 1);
            }
        }
    }
    public void removeEntries(ArrayList<Device> list){
        for (Device device : list)
        {
            if(!my_dict.containsKey(device)){
                System.out.println("There is no such ride as: " +  device.getName() + " in our system");
                continue;
            }
            int previous_value = my_dict.get(device);
            if(previous_value == 0)
            {
                System.out.println("No rides left on device: " +  device.getName());
                continue;
            }
            my_dict.put(device,previous_value- 1);
        }
    }
    public void printMap()
    {
        my_dict.entrySet().forEach(entry -> {
            System.out.println("Device : " + entry.getKey().getName() + ", number of entries remaining : " + entry.getValue());
        });
    }

    public String getKidId(){ //TODO: check if needed
        return electronicBracelet.getKid().getID();
    }

    public Hashtable<Device, Integer> getEntriesDictionary() {
        return my_dict;
    }
}
