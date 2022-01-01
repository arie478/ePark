public class Kid {

    private String ID;
    private int height;
    private int weight;
    private int age;
    private Guardian guardian;
    private ElectronicBracelet electronicBracelet;
    private String name;

    public Kid(int age, String name,Guardian guardian) {
        this.age = age;
        this.guardian = guardian;
        this.name = name;
    }

    public ElectronicBracelet getElectronicBracelet() {
        return electronicBracelet;
    }

    public void setGuardian(Guardian guardian) {
        this.guardian = guardian;
    }

    public void setElectronicBracelet(ElectronicBracelet electronicBracelet) {
        this.electronicBracelet = electronicBracelet;
    }



    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
