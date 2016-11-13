package tech.paypal.app.ngo.vexcel.database.model;

/**
 * Created by Ravikumar on 11/9/2016.
 */

public class MemberContract {
    public static final String TABLE = "member_table";

    public class Columns {
        public static final String MEMBER_ID = "member_id";
        public static final String GROUP_ID = "group_id";
        public static final String IS_ADMIN = "is_admin";
        public static final String ROLE = "role";
        public static final String STATUS = "status";
        public static final String MEMBER = "member";
    }
}