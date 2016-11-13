package tech.paypal.app.ngo.vexcel.network.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ravikumar on 11/4/2016.
 */

public class UpdateProfileRestResponse {
    @SerializedName("id")
    private String id;

    @SerializedName("email")
    private String emailId;

    @SerializedName("username")
    private String userName;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("is_staff")
    private String isStaff;

    @SerializedName("last_login")
    private String lastLogin;

    public String getId() {
        return id;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getIsStaff() {
        return isStaff;
    }

    public String getLastLogin() {
        return lastLogin;
    }
}
