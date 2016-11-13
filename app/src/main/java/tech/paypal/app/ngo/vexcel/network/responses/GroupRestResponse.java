package tech.paypal.app.ngo.vexcel.network.responses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import tech.paypal.app.ngo.vexcel.network.responses.group.Result;

/**
 * Created by Ravikumar on 11/7/2016.
 */
public class GroupRestResponse {

    @SerializedName("count")
    private Integer count;
    @SerializedName("next")
    private Object next;
    @SerializedName("previous")
    private Object previous;
    @SerializedName("results")
    private ArrayList<Result> results = new ArrayList<Result>();

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
     * The results
     */
    public ArrayList<Result> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "GroupRestResponse{" +
                "count=" + count +
                ", next=" + next +
                ", previous=" + previous +
                ", results=" + results +
                '}';
    }
}