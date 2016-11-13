package tech.paypal.app.ngo.vexcel.database.model;

/**
 * Created by Ravikumar on 11/8/2016.
 */

public class GroupContract {
    public static final String TABLE = "group_table";

    public class Columns {
        public static final String GROUP_ID = "group_id";
        public static final String GROUP_NAME = "group_name";
        public static final String DESCRIPTION = "description";
        public static final String MEMBER_COUNT = "members_count";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String ADDRESS1 = "address1";
        public static final String ADDRESS2 = "address2";
        public static final String CITY = "city";
        public static final String STATE = "state";
        public static final String COUNTRY = "country";
        public static final String ZIPCODE = "zipcode";
        public static final String IS_PUBLIC = "is_public";
        public static final String IS_ACTIVE = "is_active";
    }
}
