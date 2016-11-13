package tech.paypal.app.ngo.vexcel.network.responses.member;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ravikumar on 11/9/2016.
 */

public class Flat {
    @SerializedName("id")
    private Integer id;

    @SerializedName("is_owner")
    private Boolean isOwner;

    @SerializedName("member")
    private String member;

    @SerializedName("flat")
    private Integer flat;

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
     * The isOwner
     */
    public Boolean getIsOwner() {
        return isOwner;
    }

    /**
     *
     * @param isOwner
     * The is_owner
     */
    public void setIsOwner(Boolean isOwner) {
        this.isOwner = isOwner;
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
     * The flat
     */
    public Integer getFlat() {
        return flat;
    }

    /**
     *
     * @param flat
     * The flat
     */
    public void setFlat(Integer flat) {
        this.flat = flat;
    }
}
