package com.example.personalmap;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataManage extends SQLiteOpenHelper{
    private static final String TAG = "DataManager";
    private static final String DB_NAME = "data";
    private static final String TABLE_USER_NAME = "users";
    public static final String USER_ID = "uid";
    public static final String USER_NAME = "name";
    public static final String USER_PIC = "pic";
    public static final String USER_PWD = "pwd";
    public static final String USER_SEX = "sex";
    public static final String USER_DES = "udes";
    public static final String USER_EMAIL = "email";
    private static final String TABLE_LOG_NAME = "logs";
    public static final String LOG_ID = "rid";
    public static final String LOG_CITY = "city";
    public static final String LOG_SCENERY = "scenery";
    public static final String LOG_TIME = "time";
    public static final String LOG_DES = "des";
    public static final String LOG_PICS = "pics";
    private static final String TABLE_DRAFT_NAME = "drafts";
    public static final String DRAFT_ID = "did";
    public static final String DRAFT_CITY = "city";
    public static final String DRAFT_SCENERY = "scenery";
    public static final String DRAFT_TIME = "time";
    public static final String DRAFT_DES = "des";
    public static final String DRAFT_PICS = "pics";
    private static final String TABLE_CITY_NAME = "city";
    public static final String CITY_ID = "cid";
    public static final String CITY_COLOR = "color";
    public static final String CITY_NAME = "city";
    private static final int DB_VERSION = 2;
    private Context mContext = null;
    private SQLiteDatabase db = null;

    private static final String TABLE_USER_CREATE = "CREATE TABLE " + TABLE_USER_NAME + " ("
            +  USER_ID + " varchar,"
            +  USER_NAME + " varchar,"
            +  USER_PIC + " varchar,"
            +  USER_PWD + " varchar,"
            +  USER_SEX + " varchar,"
            +  USER_DES + " varchar,"
            +  USER_EMAIL + " varchar" + ");";

    private static final String TABLE_LOG_CREATE = "CREATE TABLE " + TABLE_LOG_NAME + " ("
            +  LOG_ID + " varchar,"
            +  LOG_CITY + " varchar,"
            +  LOG_SCENERY + " varchar,"
            +  LOG_TIME + " varchar,"
            +  USER_ID + " varchar,"
            +  LOG_DES + " varchar,"
            +  LOG_PICS + " varchar" + ");";

    private static final String TABLE_DRAFT_CREATE = "CREATE TABLE " + TABLE_DRAFT_NAME + " ("
            +  DRAFT_ID + " varchar,"
            +  DRAFT_CITY + " varchar,"
            +  DRAFT_SCENERY + " varchar,"
            +  DRAFT_TIME + " varchar,"
            +  USER_ID + " varchar,"
            +  DRAFT_DES + " varchar,"
            +  DRAFT_PICS + " varchar" + ");";

    private static final String TABLE_CITY_CREATE = "CREATE TABLE " + TABLE_CITY_NAME + " ("
            +  CITY_ID + " varchar,"
            +  CITY_COLOR + " varchar,"
            +  CITY_NAME + " varchar" + ");";

    public DataManage(Context context){
        super(context,DB_NAME,null,1);
        db=getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void createTable(){

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_NAME + ";");
        //db.execSQL(TABLE_USER_CREATE);
        db.execSQL(TABLE_USER_CREATE);
        db.execSQL(TABLE_LOG_CREATE);
        db.execSQL(TABLE_DRAFT_CREATE);
        db.execSQL(TABLE_CITY_CREATE);
    }
    public void insertData(String sql) {

        db.execSQL(sql);
    }

    public int findUserByNameAndPwd(String userName,String pwd){

        int result=0;
        //Cursor mCursor=db.query(TABLE_NAME, null, USER_NAME+"="+userName,
        //        null, null, null, null);
        Cursor mCursor=db.query(TABLE_USER_NAME, null, "user_name=? and user_pwd=?", new String[]{userName,pwd}, null, null, null);
        if(mCursor!=null){
            result=mCursor.getCount();
            mCursor.close();
        }
        return result;
    }


}
