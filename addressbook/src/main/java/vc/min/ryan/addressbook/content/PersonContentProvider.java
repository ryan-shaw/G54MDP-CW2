package vc.min.ryan.addressbook.content;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by Ryan on 25/03/2015.
 * Uri
 * content://vc.min.ryan.addressbook/contacts/:id
 */
public class PersonContentProvider extends ContentProvider {

    private static class DatabaseHelper extends SQLiteOpenHelper{
        DatabaseHelper(Context context){
            super(context, AddressBookContract.DATABASE_NAME, null, AddressBookContract.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(AddressBookContract.CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            // Delete data
            db.execSQL("DROP TABLE IF EXISTS " + AddressBookContract.TABLE_NAME);
            // Recreate table
            onCreate(db);
        }
    }

    private SQLiteDatabase mDb;

    @Override
    public boolean onCreate(){
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        mDb = dbHelper.getWritableDatabase();
        return mDb != null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values){
        long insertId = mDb.insert(AddressBookContract.TABLE_NAME, "", values);
        if(insertId > 0){
            Uri luri = ContentUris.withAppendedId(AddressBookContract.CONTENT_URI, insertId);
            getContext().getContentResolver().notifyChange(luri, null);
            return uri;
        }
        // Null if failed
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder){
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(AddressBookContract.TABLE_NAME);
        // The switch case is used to check if we want all contacts or just one
        switch(AddressBookContract.URI_MATCHER.match(uri)){
            case AddressBookContract.CONTACTS:
                builder.setProjectionMap(AddressBookContract.CONTACTS_MAP);
            break;
            case AddressBookContract.PERSON:
                builder.appendWhere(AddressBookContract._ID + "=" + uri.getPathSegments().get(1));
            break;
            default:
                throw new IllegalArgumentException("unknown uri: " + uri);
        }
        if(sortOrder == null || sortOrder == ""){
            // Set to order by first name
            sortOrder = AddressBookContract.FIRST_NAME;
        }
        Cursor c = builder.query(mDb, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs){
        int count;
        switch(AddressBookContract.URI_MATCHER.match(uri)){
            case AddressBookContract.CONTACTS:
                count = mDb.delete(AddressBookContract.TABLE_NAME, selection, selectionArgs);
                break;
            case AddressBookContract.PERSON:
                String id = uri.getPathSegments().get(1);
                count = mDb.delete(AddressBookContract.TABLE_NAME, AddressBookContract._ID + " = " + id + (!TextUtils.isEmpty(selection) ?
                " AND (" + selection + ")" : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        int count = 0;
        switch(AddressBookContract.URI_MATCHER.match(uri)){
            case AddressBookContract.CONTACTS:
                count = mDb.update(AddressBookContract.TABLE_NAME, values, selection, selectionArgs);
                break;
            case AddressBookContract.PERSON:
                count = mDb.update(AddressBookContract.TABLE_NAME, values, AddressBookContract._ID + " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : ""),
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri){
        switch(AddressBookContract.URI_MATCHER.match(uri)){
                        case AddressBookContract.CONTACTS:
                return "vnd.android.cursor.dir/vnd.vc.min.addressbook." + AddressBookContract.TABLE_NAME;
            case AddressBookContract.PERSON:
                return "vnd.android.cursor.item/vnd.vc.min.addressbook."+ AddressBookContract.TABLE_NAME;
            default:
                throw new IllegalArgumentException("unknown uri: " + uri);
        }
    }

}
