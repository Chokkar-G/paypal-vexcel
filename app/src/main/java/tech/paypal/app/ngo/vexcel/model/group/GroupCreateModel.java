package tech.paypal.app.ngo.vexcel.model.group;

/**
 * Created by Ravikumar on 11/8/2016.
 */

public class GroupCreateModel {
    private String name;
    private String description;
    private String address_line_1;
    private String latitude;
    private String longitude;
    private String city;
    private String state;
    private String country;
    private String zip_code;
    private String is_public;

    public void setIs_public(String is_public) {
        this.is_public = is_public;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress_line_1(String address_line_1) {
        this.address_line_1 = address_line_1;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
