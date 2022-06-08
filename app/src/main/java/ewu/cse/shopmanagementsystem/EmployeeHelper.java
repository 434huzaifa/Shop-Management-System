package ewu.cse.shopmanagementsystem;

public class EmployeeHelper {
  private String username="";
  private String name="";
  private String join_date="";
  private String contact="";
  private String password="";
  private String gender="";
  private String position="";

    public EmployeeHelper() {


    }

    public EmployeeHelper(String username, String name, String contact, String join_date, String password, String gender, String position) {
        this.username = username;
        this.name = name;
        this.contact = contact;
        this.join_date = join_date;
        this.password = password;
        this.gender = gender;
        this.position = position;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getJoin_date() {
        return join_date;
    }

    public void setJoin_date(String join_date) {
        this.join_date = join_date;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int dataCount(){
        int c=0;
        if(username.isEmpty()){
            System.out.println("usernameEmpty");
            c++;
        }
        if(name.isEmpty()){
            System.out.println("nameEmpty");
            c++;
        }
        if(contact.isEmpty()){
            System.out.println("contactEmpty");
            c++;
        }
        if(join_date.isEmpty()){
            System.out.println("join_dateEmpty");
            c++;
        }
        if(password.isEmpty()){
            System.out.println("passwordEmpty");
            c++;
        }
        if(gender.isEmpty()){
            System.out.println("genderEmpty");
            c++;
        }
        if(position.isEmpty()){
            System.out.println("positionEmpty");
            c++;
        }
return c;
    }


    @Override
    public String toString() {
        return "Employee{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", join_date='" + join_date + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
