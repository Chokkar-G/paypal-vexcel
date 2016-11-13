package tech.paypal.app.ngo.vexcel.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import tech.paypal.app.ngo.vexcel.database.model.GroupContract;
import tech.paypal.app.ngo.vexcel.database.model.MemberContract;
import tech.paypal.app.ngo.vexcel.database.model.UsersContract;
import tech.paypal.app.ngo.vexcel.model.group.Groups;
import tech.paypal.app.ngo.vexcel.model.member.MemberData;
import tech.paypal.app.ngo.vexcel.model.profile.UserProfile;

/**
 * Created by Chokkar G on 1/24/2016.
 */
public class DatabaseHandler {

    private static final String TAG = "DatabaseHandler";
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private boolean isDataBaseOpen = false;

    public DatabaseHandler(Context context) {
        databaseHelper = DatabaseHelper.getInstance(context);
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public boolean isDataBaseopen() {
        if (database != null) {
            isDataBaseOpen = database.isOpen();
        }
        return isDataBaseOpen;
    }

    public void close() {
        databaseHelper.close();
    }

    // User Data handler
    public void saveUserData(String firstName, String lastName, String userName, String lastLogin, String emailId) {
        ContentValues values = new ContentValues();
        values.put(UsersContract.Columns.FIRST_NAME, firstName);
        values.put(UsersContract.Columns.LAST_NAME, lastName);
        values.put(UsersContract.Columns.USERNAME, userName);
        values.put(UsersContract.Columns.LAST_LOGIN, lastLogin);
        values.put(UsersContract.Columns.EMAIL_ID, emailId);
        database.insertWithOnConflict(UsersContract.TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Boolean checkUserAvailable() {
        String query = "SELECT * FROM " + UsersContract.TABLE;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public UserProfile getUserData() {
        UserProfile profile = null;
        String query = "SELECT * FROM " + UsersContract.TABLE;
        Cursor cur = database.rawQuery(query, null);
        if (cur.moveToFirst()) {
            profile = new UserProfile(cur.getString(0), cur.getString(1), cur.getString(2), cur.getString(3), cur.getString(4));
        }
        cur.close();
        return profile;
    }

    public void updateUserData(String firstName, String lastName, String emailId) {
        ContentValues values = new ContentValues();
        values.put(UsersContract.Columns.FIRST_NAME, firstName);
        values.put(UsersContract.Columns.LAST_NAME, lastName);
        database.update(UsersContract.TABLE, values, UsersContract.Columns.EMAIL_ID + " = '" + emailId + "'", null);
    }

    // Group Data Handler
    public void saveGroupData(String groupId, String groupName, String desc, String memberCount, String latitude, String longitude,
                              String addr1, String addr2, String city, String state, String country, String zipcode, String isPublic, String isActive) {
        ContentValues values = new ContentValues();
        values.put(GroupContract.Columns.GROUP_ID, groupId);
        values.put(GroupContract.Columns.GROUP_NAME, groupName);
        values.put(GroupContract.Columns.DESCRIPTION, desc);
        values.put(GroupContract.Columns.MEMBER_COUNT, memberCount);
        values.put(GroupContract.Columns.LATITUDE, latitude);
        values.put(GroupContract.Columns.LONGITUDE, longitude);
        values.put(GroupContract.Columns.ADDRESS1, addr1);
        values.put(GroupContract.Columns.ADDRESS2, addr2);
        values.put(GroupContract.Columns.CITY, city);
        values.put(GroupContract.Columns.STATE, state);
        values.put(GroupContract.Columns.COUNTRY, country);
        values.put(GroupContract.Columns.ZIPCODE, zipcode);
        values.put(GroupContract.Columns.IS_PUBLIC, isPublic);
        values.put(GroupContract.Columns.IS_ACTIVE, isActive);
        database.insertWithOnConflict(GroupContract.TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void updateGroupData(String groupId, String groupName, String desc, String memberCount, String latitude, String longitude,
                                String addr1, String addr2, String city, String state, String country, String zipcode, String isPublic, String isActive) {
        ContentValues values = new ContentValues();
        values.put(GroupContract.Columns.GROUP_NAME, groupName);
        values.put(GroupContract.Columns.DESCRIPTION, desc);
        values.put(GroupContract.Columns.MEMBER_COUNT, memberCount);
        values.put(GroupContract.Columns.LATITUDE, latitude);
        values.put(GroupContract.Columns.LONGITUDE, longitude);
        values.put(GroupContract.Columns.ADDRESS1, addr1);
        values.put(GroupContract.Columns.ADDRESS2, addr2);
        values.put(GroupContract.Columns.CITY, city);
        values.put(GroupContract.Columns.STATE, state);
        values.put(GroupContract.Columns.COUNTRY, country);
        values.put(GroupContract.Columns.ZIPCODE, zipcode);
        values.put(GroupContract.Columns.IS_PUBLIC, isPublic);
        values.put(GroupContract.Columns.IS_ACTIVE, isActive);
        database.update(GroupContract.TABLE, values, GroupContract.Columns.GROUP_ID + " = '" + groupId + "'", null);
    }

    public ArrayList<Groups> getGroupDataList() {
        ArrayList<Groups> groupList = new ArrayList<>();
        Groups groups = null;
        String query = "SELECT * FROM " + GroupContract.TABLE;
        Cursor cur = database.rawQuery(query, null);
        if (cur.moveToFirst()) {
            do {
                groups = new Groups(cur.getString(0), cur.getString(1), cur.getString(2), cur.getString(3), cur.getString(4), cur.getString(5),
                        cur.getString(6), cur.getString(7), cur.getString(8), cur.getString(9), cur.getString(10), cur.getString(11),
                        cur.getString(12), cur.getString(13));
                groupList.add(groups);
            } while (cur.moveToNext());
        }
        cur.close();
        return groupList;
    }

    /*public Groups getGroupDataList() {
        ArrayList<Groups> groupList = new ArrayList<>();
        Groups groups = null;
        String query = "SELECT * FROM " + GroupContract.TABLE;
        Cursor cur = database.rawQuery(query, null);
        if (cur.moveToFirst()) {
            do {
                groups = new Groups(cur.getString(0), cur.getString(1), cur.getString(2), cur.getString(3), cur.getString(4), cur.getString(5),
                        cur.getString(6), cur.getString(7), cur.getString(8), cur.getString(9), cur.getString(10), cur.getString(11),
                        cur.getString(12), cur.getString(13));
                groupList.add(groups);
            } while (cur.moveToNext());
        }
        cur.close();
        return groupList;
    }*/

    public void deleteGroup(int groupId) {
        database.delete(GroupContract.TABLE, GroupContract.Columns.GROUP_ID + "=?", new String[]{Integer.toString(groupId)});
    }

    // Member Data handler
    public void saveMemberData(String memberId, String groupId, String member, String isAdmin, String role, String status) {
        ContentValues values = new ContentValues();
        values.put(MemberContract.Columns.MEMBER_ID, memberId);
        values.put(MemberContract.Columns.GROUP_ID, groupId);
        values.put(MemberContract.Columns.MEMBER, member);
        values.put(MemberContract.Columns.IS_ADMIN, isAdmin);
        values.put(MemberContract.Columns.ROLE, role);
        values.put(MemberContract.Columns.STATUS, status);
        database.insertWithOnConflict(MemberContract.TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public ArrayList<MemberData> getMemberData(String groupId) {
        ArrayList<MemberData> memberDataList = new ArrayList<>();
        MemberData memberData = null;
        String query = "SELECT * FROM " + MemberContract.TABLE + " Where " + MemberContract.Columns.GROUP_ID + " = '" + groupId +"'";
        Cursor cur = database.rawQuery(query, null);
        if (cur.moveToFirst()) {
            do {
                memberData = new MemberData(cur.getString(0), cur.getString(1), cur.getString(2), cur.getString(3), cur.getString(4),
                        cur.getString(5));
                memberDataList.add(memberData);
            } while (cur.moveToNext());
        }
        cur.close();
        return memberDataList;
    }


    public void clearTableDatas(String tableName) {
        database.execSQL("DELETE FROM " + tableName);
    }

    public boolean checkIsDataAlreadyInDBorNot(String TableName, String dbfield, String fieldValue) {
        String Query = "Select * from " + TableName + " where " + dbfield + " = " + fieldValue;
        Cursor cursor = database.rawQuery(Query, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public int getTableEntryCount(String TableName) {
        String Query = "Select * from " + TableName;
        Cursor cursor = database.rawQuery(Query, null);
        return cursor.getCount();
    }

    public boolean clearTables() {
        String Query = "delete from " + UsersContract.TABLE;
        Cursor cursor = database.rawQuery(Query, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }
}
