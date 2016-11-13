package tech.paypal.app.ngo.vexcel.model.products;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Chokkar G on 11/13/2016.
 */

public class Product {

    @SerializedName("id")
    private Integer id;
    @SerializedName("label")
    private String label;
    @SerializedName("shelf_life_in_days")
    private Integer shelfLifeInDays;
    @SerializedName("price")
    private String price;
    @SerializedName("tax")
    private String tax;
    @SerializedName("batch_size")
    private Integer batchSize;
    @SerializedName("unit")
    private Integer unit;

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
     * The label
     */
    public String getLabel() {
        return label;
    }

    /**
     *
     * @param label
     * The label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     *
     * @return
     * The shelfLifeInDays
     */
    public Integer getShelfLifeInDays() {
        return shelfLifeInDays;
    }

    /**
     *
     * @param shelfLifeInDays
     * The shelf_life_in_days
     */
    public void setShelfLifeInDays(Integer shelfLifeInDays) {
        this.shelfLifeInDays = shelfLifeInDays;
    }

    /**
     *
     * @return
     * The price
     */
    public String getPrice() {
        return price;
    }

    /**
     *
     * @param price
     * The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     *
     * @return
     * The tax
     */
    public String getTax() {
        return tax;
    }

    /**
     *
     * @param tax
     * The tax
     */
    public void setTax(String tax) {
        this.tax = tax;
    }

    /**
     *
     * @return
     * The batchSize
     */
    public Integer getBatchSize() {
        return batchSize;
    }

    /**
     *
     * @param batchSize
     * The batch_size
     */
    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }

    /**
     *
     * @return
     * The unit
     */
    public Integer getUnit() {
        return unit;
    }

    /**
     *
     * @param unit
     * The unit
     */
    public void setUnit(Integer unit) {
        this.unit = unit;
    }

}
