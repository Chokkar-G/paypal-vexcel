package tech.paypal.app.ngo.vexcel.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import tech.paypal.app.ngo.vexcel.database.model.GroupContract;
import tech.paypal.app.ngo.vexcel.database.model.MemberContract;
import tech.paypal.app.ngo.vexcel.database.model.UsersContract;

/**
 * Created by Chokkar G
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper mInstance = null;

    private static final String TAG = "DatabaseHelper";
    private static final String DB_NAME = "geofencedroid";
    public static final int DATABASE_VERSION = 1;
    private Context mContext;

    /*Creatingtables*/
    private final String CREATE_TABLE_USER = "CREATE TABLE " + UsersContract.TABLE + " ("
            + UsersContract.Columns.FIRST_NAME + " TEXT, "
            + UsersContract.Columns.LAST_NAME + " TEXT, "
            + UsersContract.Columns.USERNAME + " TEXT, "
            + UsersContract.Columns.LAST_LOGIN + " TEXT, "
            + UsersContract.Columns.EMAIL_ID + " TEXT, PRIMARY KEY(" + UsersContract.Columns.EMAIL_ID + "))";

    private final String CREATE_TABLE_GROUP = "CREATE TABLE " + GroupContract.TABLE + " ("
            + GroupContract.Columns.GROUP_ID + " TEXT PRIMARY KEY, "
            + GroupContract.Columns.GROUP_NAME + " TEXT, "
            + GroupContract.Columns.DESCRIPTION + " TEXT, "
            + GroupContract.Columns.MEMBER_COUNT + " TEXT, "
            + GroupContract.Columns.LATITUDE + " TEXT, "
            + GroupContract.Columns.LONGITUDE + " TEXT, "
            + GroupContract.Columns.ADDRESS1 + " TEXT, "
            + GroupContract.Columns.ADDRESS2 + " TEXT, "
            + GroupContract.Columns.CITY + " TEXT, "
            + GroupContract.Columns.STATE + " TEXT, "
            + GroupContract.Columns.COUNTRY + " TEXT, "
            + GroupContract.Columns.ZIPCODE + " TEXT, "
            + GroupContract.Columns.IS_PUBLIC + " TEXT, "
            + GroupContract.Columns.IS_ACTIVE + " TEXT);";

    private final String CREATE_TABLE_MEMBER = "CREATE TABLE " + MemberContract.TABLE + " ("
            + MemberContract.Columns.MEMBER_ID + " TEXT PRIMARY KEY, "
            + MemberContract.Columns.GROUP_ID + " TEXT, "
            + MemberContract.Columns.MEMBER + " TEXT, "
            + MemberContract.Columns.IS_ADMIN + " TEXT, "
            + MemberContract.Columns.ROLE + " TEXT, "
            + MemberContract.Columns.STATUS + " TEXT)";

    /**
     * Constructor Takes and keeps a reference of the passed context in order to
     * access to the application assets and resources.
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    public static DatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_GROUP);
        db.execSQL(CREATE_TABLE_MEMBER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_GROUP);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_MEMBER);
        onCreate(db);
    }
}
