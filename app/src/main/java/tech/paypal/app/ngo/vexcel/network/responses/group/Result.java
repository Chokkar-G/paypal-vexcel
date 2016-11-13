package tech.paypal.app.ngo.vexcel.network.responses.group;

import com.google.gson.annotations.SerializedName;


public class Result {

    @SerializedName("id")
    private Integer id;
    @SerializedName("membersCount")
    private Integer membersCount;
    @SerializedName("address_line_1")
    private String addressLine1;
    @SerializedName("address_line_2")
    private String addressLine2;
    @SerializedName("longitude")
    private Double longitude;
    @SerializedName("latitude")
    private Double latitude;
    @SerializedName("city")
    private String city;
    @SerializedName("state")
    private String state;
    @SerializedName("country")
    private String country;
    @SerializedName("zip_code")
    private Integer zipCode;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("is_public")
    private Boolean isPublic;
    @SerializedName("is_active")
    private Boolean isActive;

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The membersCount
     */
    public Integer getMembersCount() {
        return membersCount;
    }

    /**
     * @param membersCount The membersCount
     */
    public void setMembersCount(Integer membersCount) {
        this.membersCount = membersCount;
    }

    /**
     * @return The addressLine1
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * @param addressLine1 The address_line_1
     */
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    /**
     * @return The addressLine2
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * @param addressLine2 The address_line_2
     */
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    /**
     * @return The longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude The longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return The latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude The latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return The city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return The state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return The zipCode
     */
    public Integer getZipCode() {
        return zipCode;
    }

    /**
     * @param zipCode The zip_code
     */
    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The isPublic
     */
    public Boolean getIsPublic() {
        return isPublic;
    }

    /**
     * @param isPublic The is_public
     */
    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    /**
     * @return The isActive
     */
    public Boolean getIsActive() {
        return isActive;
    }

    /**
     * @param isActive The is_active
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}