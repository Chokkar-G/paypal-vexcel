package tech.paypal.app.ngo.vexcel.network.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ravikumar on 11/3/2016.
 */

public class RegisterResponse {
    @SerializedName("email")
    private String email;

    @SerializedName("username")
    private String username;

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    @SerializedName("id")
    private String id;



}
