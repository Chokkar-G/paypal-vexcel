package tech.paypal.app.ngo.vexcel.model.profile;

/**
 * Created by Ravikumar on 11/4/2016.
 */

public class UserProfile {
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String lastLogin;

    public UserProfile(String firstName, String lastName, String mobileNumber, String lastLogin, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = mobileNumber;
        this.lastLogin = lastLogin;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }
}
