package vc.min.ryan.addressbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vc.min.ryan.addressbook.content.AddressBookContract;

/**
 * Created by Ryan on 25/03/2015.
 */
public class BookManager {
    private Context mContext;
    private static final String TAG = "BookManager";

    public BookManager(Context context){
        this.mContext = context;
    }

    /**
     * Get an individual
     * @param id
     * @return A Person object
     */
    public Person getPerson(int id){
        Cursor c = mContext.getContentResolver().query(AddressBookContract.CONTENT_URI, null, AddressBookContract._ID + "=" + id, null, null);
        if(!c.moveToFirst())
            return null;
        Person person = getPersonFromCursor(c);
        c.close();
        return person;
    }

    /**
     * Get a list of Person objects
     * @return List of Person objects
     */
    public List<Person> getData(){
        ArrayList<Person> data = new ArrayList<Person>();
        Cursor c = mContext.getContentResolver().query(AddressBookContract.CONTENT_URI, null, null, null, "firstName");

        // Convert the list an ArrayList of Person objects
        c.moveToFirst();
        while(!c.isAfterLast()){
            data.add(getPersonFromCursor(c));
            c.moveToNext();
        }
        c.close();
        return data;
    }

    /**
     * Get a person from a cursor
     * @param c, cursor object
     * @return A Person object from the cursor's current position
     */
    public Person getPersonFromCursor(Cursor c){
        Person person = new Person(
                c.getInt(c.getColumnIndex("_id")),
                c.getString(c.getColumnIndex(AddressBookContract.FIRST_NAME)),
                c.getString(c.getColumnIndex(AddressBookContract.LAST_NAME)),
                c.getString(c.getColumnIndex(AddressBookContract.PHONE_NUMBER)),
                c.getString(c.getColumnIndex(AddressBookContract.EMAIL)),
                c.getString(c.getColumnIndex(AddressBookContract.PHOTO)));
        return person;
    }

    /**
     * Add a contact to the ContentProvider
     * @param firstName
     * @param lastName
     * @param phone
     * @param email
     * @param photoPath
     * @return true if success, false if not successful
     */
    public boolean addContact(String firstName, String lastName, String phone, String email, String photoPath){

        // Verification checks
        if(firstName.length() == 0)
            return false;
        if(lastName.length() == 0)
            return false;
        if(phone.length() == 0)
            return false;

        // Set ContentValues from parameters
        ContentValues values = new ContentValues();
        values.put(AddressBookContract.FIRST_NAME, firstName);
        values.put(AddressBookContract.LAST_NAME, lastName);
        values.put(AddressBookContract.PHONE_NUMBER, phone);
        values.put(AddressBookContract.EMAIL, email);
        values.put(AddressBookContract.PHOTO, photoPath);

        // Insert
        Uri uri = mContext.getContentResolver().insert(AddressBookContract.CONTENT_URI, values);
        // If URI is null it failed
        return uri != null;
    }

    /**
     * Edit a contact
     * @param id
     * @param firstName
     * @param lastName
     * @param phone
     * @param email
     * @param photoPath
     * @return true if success, false if not successful
     */
    public boolean editContact(int id, String firstName, String lastName, String phone, String email, String photoPath){
        // Verification checks
        if(firstName.length() == 0)
            return false;
        if(lastName.length() == 0)
            return false;
        if(phone.length() == 0)
            return false;

        // Set ContentValues
        ContentValues values = new ContentValues();
        values.put(AddressBookContract.FIRST_NAME, firstName);
        values.put(AddressBookContract.LAST_NAME, lastName);
        values.put(AddressBookContract.PHONE_NUMBER, phone);
        values.put(AddressBookContract.EMAIL, email);
        values.put(AddressBookContract.PHOTO, photoPath);

        // Update record in database
        int res = mContext.getContentResolver().update(AddressBookContract.CONTENT_URI, values, AddressBookContract._ID+"="+id, null);
        return res != -1;
    }

    /**
     * Delete a contact
     * @param id, delete by ID
     * @return true if success, false if not successful
     */
    public boolean deleteContact(int id){
        int res = mContext.getContentResolver().delete(AddressBookContract.CONTENT_URI, AddressBookContract._ID+"="+id, null);
        return res != -1;
    }
}
