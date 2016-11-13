package tech.paypal.app.ngo.vexcel.model.errorhandler;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ravikumar on 11/4/2016.
 */

public class CommonError {

    @SerializedName("code")
    private Integer code;
    @SerializedName("type")
    private String type;
    @SerializedName("message")
    private String message;
    @SerializedName("details")
    private Details details;

    /**
     *
     * @return
     * The code
     */
    public Integer getCode() {
        return code;
    }

    /**
     *
     * @param code
     * The code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The details
     */
    public Details getDetails() {
        return details;
    }

    /**
     *
     * @param details
     * The details
     */
    public void setDetails(Details details) {
        this.details = details;
    }
}
