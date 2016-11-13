package tech.paypal.app.ngo.vexcel.model.group;

/**
 * Created by Ravikumar on 11/8/2016.
 */

public class Groups {
    private String groupId;
    private String groupName;
    private String desc;
    private String memberCount;
    private String latitude;
    private String longitude;
    private String addr1;
    private String addr2;
    private String city;
    private String state;
    private String country;
    private String zipcode;
    private String isPublic;
    private String isActive;

    public Groups(String groupId, String groupName, String desc, String memberCount, String latitude, String longitude,
                  String addr1, String addr2, String city, String state, String country, String zipcode, String isPublic, String isActive) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.desc = desc;
        this.memberCount = memberCount;
        this.latitude = latitude;
        this.longitude = longitude;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipcode = zipcode;
        this.isPublic = isPublic;
        this.isActive = isActive;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(String memberCount) {
        this.memberCount = memberCount;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
