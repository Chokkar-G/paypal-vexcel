package tech.paypal.app.ngo.vexcel.model.errorhandler;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ravikumar on 11/4/2016.
 */

public class Details {

    @SerializedName("username")

    private List<String> username = new ArrayList<String>();

    /**
     *
     * @return
     * The username
     */
    public List<String> getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(List<String> username) {
        this.username = username;
    }
}
