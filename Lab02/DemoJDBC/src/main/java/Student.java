public class Student {
    private String sID;
    private String name;
    private String gender;

    public Student(String sID, String name, String gender) {
        this.sID = sID;
        this.name = name;
        this.gender = gender;
    }

    public String getsID() {return sID;}
    public String getName() {return name;}
    public String getGender() {return gender;}

    public void setsID(String sID) {this.sID = sID;}
    public void setName(String name) {this.name = name;}
    public void setGender(String gender) {this.gender = gender;}

    public String toString() {
        return String.format("Student[sID=%s, name=%s, gender=%s]", sID, name, gender);
    }
}
