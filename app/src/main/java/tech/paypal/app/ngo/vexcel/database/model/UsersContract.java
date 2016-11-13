package tech.paypal.app.ngo.vexcel.database.model;

public class UsersContract {
    public static final String TABLE = "user_profile";

    public class Columns {
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String USERNAME = "username";
        public static final String EMAIL_ID = "email_id";
        public static final String LAST_LOGIN = "last_login";
    }
}