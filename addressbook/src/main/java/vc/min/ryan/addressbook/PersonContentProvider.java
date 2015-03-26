package vc.min.ryan.addressbook;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by Ryan on 25/03/2015.
 * Uri
 * content://vc.min.ryan.addressbook/contacts/:id
 */
public class PersonContentProvider extends ContentProvider {
    private static final String PROVIDER    = "vc.min.ryan.addressbook.Contacts";
    private static final String URL         = "content://" + PROVIDER + "/contacts";
    public static final Uri CONTENT_URI     = Uri.parse(URL);

    public static final String _ID             = "_id";
    public static final String FIRST_NAME      = "firstName";
    public static final String LAST_NAME       = "lastName";
    public static final String PHONE_NUMBER    = "phone";
    public static final String EMAIL           = "email";
    public static final String PHOTO           = "photo";

    private static final int        CONTACTS    = 1;
    private static final int        PERSON      = 2;
    private static final UriMatcher sUriMatcher;
    static{
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI("vc.min.ryan.addressbook.Contacts", "contacts", CONTACTS);
        sUriMatcher.addURI("vc.min.ryan.addressbook.Contacts", "contacts/#", PERSON);
    }
    private SQLiteDatabase mDb;
    private static final String DATABASE_NAME = "Contacts";
    static final String TABLE_NAME = "contacts";
    static final int DATABASE_VERSION = 2;
    static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME +
            " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " firstName TEXT NOT NULL, " +
            " lastName TEXT NOT NULL, " +
            " phone TEXT NOT NULL, " +
            " email TEXT NOT NULL, " +
            " photo BLOB);";

    private static HashMap<String, String> CONTACTS_MAP;

    private static class DatabaseHelper extends SQLiteOpenHelper{
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate(){
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        mDb = dbHelper.getWritableDatabase();
        return mDb != null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values){
        long insertId = mDb.insert(TABLE_NAME, "", values);
        if(insertId > 0){
            Uri luri = ContentUris.withAppendedId(CONTENT_URI, insertId);
            getContext().getContentResolver().notifyChange(luri, null);
            return uri;
        }
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder){
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(TABLE_NAME);
        switch(sUriMatcher.match(uri)){
            case CONTACTS:
                builder.setProjectionMap(CONTACTS_MAP);
            break;
            case PERSON:
                builder.appendWhere(_ID + "=" + uri.getPathSegments().get(1));
            break;
            default:
                throw new IllegalArgumentException("unknown uri " + uri);
        }
        if(sortOrder == null || sortOrder == ""){
            // Set to order by first name
            sortOrder = FIRST_NAME;
        }
        Cursor c = builder.query(mDb, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs){
        int count = 0;
        switch(sUriMatcher.match(uri)){
            case CONTACTS:
                count = mDb.delete(TABLE_NAME, selection, selectionArgs);
                break;
            case PERSON:
                String id = uri.getPathSegments().get(1);
                count = mDb.delete(TABLE_NAME, _ID + " = " + id + (!TextUtils.isEmpty(selection) ?
                " AND (" + selection + ")" : ""), selectionArgs);
            default:
                throw new IllegalArgumentException("unknown uri " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        int count = 0;
        switch(sUriMatcher.match(uri)){
            case CONTACTS:
                count = mDb.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            case PERSON:
                count = mDb.update(TABLE_NAME, values, _ID + " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : ""),
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("unknown uri " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri){
        switch(sUriMatcher.match(uri)){
            case CONTACTS:
                return "vnd.android.cursor.dir/vnd.vc.min.addressbook." + TABLE_NAME;
            case PERSON:
                return "vnd.android.cursor.item/vnd.vc.min.addressbook."+ TABLE_NAME;
            default:
                throw new IllegalArgumentException("unsupported uri " + uri);
        }
    }

}
