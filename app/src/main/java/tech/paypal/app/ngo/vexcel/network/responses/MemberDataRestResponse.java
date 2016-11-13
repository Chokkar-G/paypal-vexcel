package tech.paypal.app.ngo.vexcel.network.responses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import tech.paypal.app.ngo.vexcel.network.responses.member.ResultMember;

/**
 * Created by Ravikumar on 11/9/2016.
 */

public class MemberDataRestResponse {
    @SerializedName("count")
    private Integer count;

    @SerializedName("next")
    private Object next;

    @SerializedName("previous")
    private Object previous;

    @SerializedName("results")
    private ArrayList<ResultMember> resultMembers = new ArrayList<ResultMember>();

    /**
     *
     * @return
     * The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     *
     * @param count
     * The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     *
     * @return
     * The next
     */
    public Object getNext() {
        return next;
    }

    /**
     *
     * @param next
     * The next
     */
    public void setNext(Object next) {
        this.next = next;
    }

    /**
     *
     * @return
     * The previous
     */
    public Object getPrevious() {
        return previous;
    }

    /**
     *
     * @param previous
     * The previous
     */
    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    /**
     *
     * @return
     * The resultMembers
     */
    public ArrayList<ResultMember> getResultMembers() {
        return resultMembers;
    }

    /**
     *
     * @param resultMembers
     * The resultMembers
     */
    public void setResultMembers(ArrayList<ResultMember> resultMembers) {
        this.resultMembers = resultMembers;
    }
}