package tech.paypal.app.ngo.vexcel.model.member;

/**
 * Created by Ravikumar on 11/9/2016.
 */

public class MemberData {
    private String memberId;
    private String groupId;
    private String member;
    private String role;
    private String status;
    private String isAdmin;

    public MemberData(String memberId, String groupId, String member, String isAdmin, String role, String status) {
        this.memberId = memberId;
        this.groupId = groupId;
        this.member = member;
        this.isAdmin = isAdmin;
        this.role = role;
        this.status = status;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }
}
