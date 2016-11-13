package tech.paypal.app.ngo.vexcel.model.inventory;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Chokkar G on 11/13/2016.
 */

public class Inventory {

    @SerializedName("id")
    private Integer id;
    @SerializedName("quantity")
    private Integer quantity;
    @SerializedName("entry_timestamp")
    private String entryTimestamp;
    @SerializedName("update_timestamp")
    private String updateTimestamp;
    @SerializedName("raw_material")
    private Integer rawMaterial;

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
     * The quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     * The quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return
     * The entryTimestamp
     */
    public String getEntryTimestamp() {
        return entryTimestamp;
    }

    /**
     *
     * @param entryTimestamp
     * The entry_timestamp
     */
    public void setEntryTimestamp(String entryTimestamp) {
        this.entryTimestamp = entryTimestamp;
    }

    /**
     *
     * @return
     * The updateTimestamp
     */
    public String getUpdateTimestamp() {
        return updateTimestamp;
    }

    /**
     *
     * @param updateTimestamp
     * The update_timestamp
     */
    public void setUpdateTimestamp(String updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    /**
     *
     * @return
     * The rawMaterial
     */
    public Integer getRawMaterial() {
        return rawMaterial;
    }

    /**
     *
     * @param rawMaterial
     * The raw_material
     */
    public void setRawMaterial(Integer rawMaterial) {
        this.rawMaterial = rawMaterial;
    }
}
