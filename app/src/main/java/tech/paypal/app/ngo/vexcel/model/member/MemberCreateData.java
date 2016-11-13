package tech.paypal.app.ngo.vexcel.model.member;

/**
 * Created by Ravikumar on 11/10/2016.
 */

public class MemberCreateData {
    public void setRole(String role) {
        this.role = role;
    }

    public void setIs_admin(String is_admin) {
        this.is_admin = is_admin;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String role;
    private String is_admin;
    private String member;
    private String status;

}
