package com.qsoft.pilotproject.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created with IntelliJ IDEA.
 * User: thanhtd
 * Date: 11/1/13
 * Time: 2:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class Database extends SQLiteOpenHelper {
    private static final String DB_NAME = "online_dio";
    public static final String TABLE_ONLINE_HOME = "home";
    private static final int DB_VERSION = 1;

    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String TITLE = "title";
    public static final String THUMBNAIL = "thumbnail";
    public static final String DESCRIPTION = "description";
    public static final String SOUND_PATH = "sound_path";
    public static final String DURATION = "duration";
    public static final String PLAYED = "played";
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";
    public static final String LIKE = "likes";
    public static final String VIEWED = "viewed";
    public static final String COMMENTS = "comments";
    public static final String USERNAME = "username";
    public static final String DISPLAY_NAME = "display_name";
    public static final String AVATAR = "avatar";

    private static final String CREATE_TABLE_HOME ="create table"+TABLE_ONLINE_HOME
            +"(" + ID + "integer primary key autoincrement, "+
            USER_ID + "text, "+
            TITLE + "text, "+
            THUMBNAIL + "text, "+
            DESCRIPTION + "text, "+
            SOUND_PATH + "text, "+
            DURATION + "text, "+
            PLAYED + "text, "+
            CREATED_AT + "text, "+
            UPDATED_AT + "text, "+
            LIKE + "text, "+
            VIEWED + "text, "+
            COMMENTS + "text, "+
            USERNAME + "text, "+
            DISPLAY_NAME + "text, "+
            AVATAR + "text, ";

    private static final String DB_SCHEMA = CREATE_TABLE_HOME;

    public Database(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_SCHEMA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_ONLINE_HOME);
        onCreate(db);
    }
}
