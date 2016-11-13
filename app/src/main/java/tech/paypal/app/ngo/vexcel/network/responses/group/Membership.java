package tech.paypal.app.ngo.vexcel.network.responses.group;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import tech.paypal.app.ngo.vexcel.network.responses.member.Flat;

/**
 * Created by Ravikumar on 11/10/2016.
 */
public class Membership {
    @SerializedName("role")
    private Integer role;

    @SerializedName("is_admin")
    private Boolean isAdmin;

    @SerializedName("member")
    private String member;

    @SerializedName("status")
    private Integer status;

    @SerializedName("flats")
    private ArrayList<Flat> flats = new ArrayList<Flat>();
    @SerializedName("id")

    private Integer id;

    /**
     *
     * @return
     * The role
     */
    public Integer getRole() {
        return role;
    }

    /**
     *
     * @param role
     * The role
     */
    public void setRole(Integer role) {
        this.role = role;
    }

    /**
     *
     * @return
     * The isAdmin
     */
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     *
     * @param isAdmin
     * The is_admin
     */
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     *
     * @return
     * The member
     */
    public String getMember() {
        return member;
    }

    /**
     *
     * @param member
     * The member
     */
    public void setMember(String member) {
        this.member = member;
    }

    /**
     *
     * @return
     * The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The flats
     */
    public ArrayList<Flat> getFlats() {
        return flats;
    }

    /**
     *
     * @param flats
     * The flats
     */
    public void setFlats(ArrayList<Flat> flats) {
        this.flats = flats;
    }

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

}
