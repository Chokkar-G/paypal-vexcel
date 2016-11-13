package tech.paypal.app.ngo.vexcel.network.responses;


import com.google.gson.annotations.SerializedName;

/**
 * Created by chokkar
 */

public class LoginRestResponse {
    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }
}