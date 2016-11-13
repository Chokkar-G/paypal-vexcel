package tech.paypal.app.ngo.vexcel.model.errorhandler;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ravikumar on 11/4/2016.
 */

public class CommonBaseError {

    @SerializedName("error")
    private CommonError error;

    /**
     *
     * @return
     * The error
     */
    public CommonError getError() {
        return error;
    }

    /**
     *
     * @param error
     * The error
     */
    public void setError(CommonError error) {
        this.error = error;
    }
}
