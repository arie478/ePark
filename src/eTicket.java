import java.util.ArrayList;
import java.util.*;

public class eTicket {
    private String password;
    private ElectronicBracelet electronicBracelet;
    private SystemManager systemManager;

    Hashtable<String, Integer> my_dict; //new Hashtable<String, Integer>()

    public eTicket(String password, ElectronicBracelet electronicBracelet, SystemManager systemManager, ArrayList<Device> deviceList, Hashtable<String, Integer> my_dict) {
        this.password = password;
        this.electronicBracelet = electronicBracelet;
        this.systemManager = systemManager;
        this.my_dict = my_dict;
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


    public ArrayList getKidInformation(){
        ArrayList arr = electronicBracelet.getKidInformation();
        return arr;
    }
    public void addEntry(ArrayList<Device> list){
        for (Device device : list)
        {
            if(!my_dict.containsKey(device.getName())){
                my_dict.put(device.getName(),1);
            }
            else {
                int previous_value = my_dict.get(device.getName());
                my_dict.put(device.getName(), previous_value + 1);
            }
        }
    }
    public void removeEntry(ArrayList<Device> list){
        for (Device device : list)
        {
            if(!my_dict.containsKey(device.getName())){
                System.out.println("There is no such ride as: " +  device.getName() + " in our system");
                continue;
            }
            int previous_value = my_dict.get(device.getName());
            if(previous_value == 0)
            {
                System.out.println("No rides left on device: " +  device.getName());
                continue;
            }
            my_dict.put(device.getName(),previous_value- 1);
        }
    }
    public void printMap()
    {
        System.out.println(my_dict);
    }
}
