package tech.paypal.app.ngo.vexcel.model.customers;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Chokkar G on 11/13/2016.
 */

public class    Customer {

    @SerializedName("id")
    private Integer id;
    @SerializedName("phone_number")
    private String phoneNumber;
    @SerializedName("name")
    private String name;
    @SerializedName("address")
    private String address;
    @SerializedName("email_id")
    private String emailId;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * @param phoneNumber
     * The phone_number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return
     * The emailId
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     *
     * @param emailId
     * The email_id
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

}
