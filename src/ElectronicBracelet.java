import java.util.ArrayList;
public class ElectronicBracelet {
    private String bracelet_id;
    private Kid kid;
    private eTicket eTicket;

//    public ElectronicBracelet(String bracelet_id, Kid kid, eTicket eTicket) {
//        this.bracelet_id = bracelet_id;
//        this.kid = kid;
//        this.eTicket = eTicket;
//    }
    public ElectronicBracelet(){
        //nothing to init
    }

    public eTicket geteTicket() {
        return eTicket;
    }

    public void seteTicket(eTicket eTicket) {
        this.eTicket = eTicket;
    }

    public Kid getKid() {
        return kid;
    }

    public void setKid(Kid kid) {
        this.kid = kid;
    }

    public String getBracelet_id() {
        return bracelet_id;
    }

    public void setBracelet_id(String bracelet_id) {
        this.bracelet_id = bracelet_id;
    }


    public ArrayList getKidInformation() {
        int ID = Integer.parseInt(kid.getID()); // change to int from str
        //String ID = kid.getID();
        int height = kid.getHeight();
        int weight = kid.getWeight();
        int age = kid.getAge();

        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(ID);
        list.add(height);
        list.add(weight);
        list.add(age);
        return list;

    }

}
