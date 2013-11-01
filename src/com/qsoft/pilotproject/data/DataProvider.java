package com.qsoft.pilotproject.data;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: thanhtd
 * Date: 11/1/13
 * Time: 2:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataProvider extends ContentProvider {
    private Database mDB;
    private static final String AUTHORITY = "com.qsoft.pilotproject.data.DataProvider";
    public static final int HOME_FEEDS = 200;
    public static final int HOME_FEED_ID = 110;
    private static final String HOME_FEED_BASE_PATH = "home";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/"
            + HOME_FEED_BASE_PATH);
    public static final String CONTENT_TYPE_HOME = ContentResolver.CURSOR_DIR_BASE_TYPE + "/mt-home";
    public static final String CONTENT_ITEM_TYPE_HOME = ContentResolver.CURSOR_DIR_BASE_TYPE + "/mt-home";

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, HOME_FEED_BASE_PATH, HOME_FEEDS);
        sURIMatcher.addURI(AUTHORITY, HOME_FEED_BASE_PATH + "/#", HOME_FEED_ID);
    }


    @Override
    public boolean onCreate() {
        mDB = new Database(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(Database.TABLE_ONLINE_HOME);
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case HOME_FEED_ID:
                queryBuilder.appendWhere(Database.ID + "=" + uri.getLastPathSegment());
                break;
            case HOME_FEEDS:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }
        Cursor cursor = queryBuilder.query(mDB.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case HOME_FEEDS:
                return CONTENT_TYPE_HOME;
            case HOME_FEED_ID:
                return CONTENT_ITEM_TYPE_HOME;
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        if (uriType != HOME_FEEDS) {
            throw new IllegalArgumentException("Invalid URI for insert");
        }
        SQLiteDatabase sqlDB = mDB.getWritableDatabase();
        long newID = sqlDB.insert(Database.TABLE_ONLINE_HOME, null, values);
        if (newID > 0) {
            Uri newUri = ContentUris.withAppendedId(uri, newID);
            getContext().getContentResolver().notifyChange(uri, null);
            return newUri;
        } else {
            return null;//nho cho nay !!!
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = mDB.getWritableDatabase();
        int rowsAffected = 0;
        switch (uriType) {
            case HOME_FEEDS:
                rowsAffected = sqlDB.delete(Database.TABLE_ONLINE_HOME, selection, selectionArgs);
                break;
            case HOME_FEED_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsAffected = sqlDB.delete(Database.TABLE_ONLINE_HOME, Database.ID + "=" + id, null);
                } else {
                    rowsAffected = sqlDB.delete(Database.TABLE_ONLINE_HOME,
                            selection + " and " + Database.ID + "=" + id, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknow or Invalid URI"+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return rowsAffected;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = mDB.getWritableDatabase();
        int rowsAffected;
        switch (uriType) {
            case HOME_FEED_ID:
                String id = uri.getLastPathSegment();
                StringBuilder modSelection = new StringBuilder(Database.ID + "=" + id);
                if (!TextUtils.isEmpty(selection)) {
                    modSelection.append(" AND " + selection);
                }
                rowsAffected = sqlDB.update(Database.TABLE_ONLINE_HOME, values, modSelection.toString(), null);
                break;
            case HOME_FEEDS:
                rowsAffected = sqlDB.update(Database.TABLE_ONLINE_HOME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsAffected;
    }
}
