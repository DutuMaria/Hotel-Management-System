package entity.user;

public class Admin extends User {
    private static Admin admin;

    private Admin() {
        super(1, "Florentina", "Constantinescu","admin", "Admin@123", "admin@gmail.com");
    }

    public static Admin getAdminInstance(){
        if(admin == null){
            admin = new Admin();
        }
        return admin;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public String getUserType() {
        return "Admin";
    }

}

