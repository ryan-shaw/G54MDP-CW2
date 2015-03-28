package vc.min.ryan.addressbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan on 25/03/2015.
 */
public class BookManager {
    private Context mContext;
    public BookManager(Context context){
        this.mContext = context;
    }

    public List<Person> getData(){
        ArrayList<Person> data = new ArrayList<Person>();
        //data.add(new Person("Ryan", "Shaw", "0797227977", "ryan.shaw@min.vc", null));

        Cursor c = mContext.getContentResolver().query(AddressBookContract.CONTENT_URI, null, null, null, "firstName");
        c.moveToFirst();
        while(!c.isAfterLast()){
            data.add(getPersonFromCursor(c));
            c.moveToNext();
        }
        return data;
    }

    public Person getPersonFromCursor(Cursor c){
        Person person = new Person(
                c.getInt(c.getColumnIndex("_id")),
                c.getString(c.getColumnIndex(AddressBookContract.FIRST_NAME)),
                c.getString(c.getColumnIndex(AddressBookContract.LAST_NAME)),
                c.getString(c.getColumnIndex(AddressBookContract.PHONE_NUMBER)),
                c.getString(c.getColumnIndex(AddressBookContract.EMAIL)),
                c.getBlob(c.getColumnIndex(AddressBookContract.PHOTO)));
        return person;
    }

    public Uri addContact(String firstName, String lastName, String phone, String email, Bitmap photo){
        ContentValues values = new ContentValues();
        values.put(AddressBookContract.FIRST_NAME, firstName);
        values.put(AddressBookContract.LAST_NAME, lastName);
        values.put(AddressBookContract.PHONE_NUMBER, phone);
        values.put(AddressBookContract.EMAIL, email);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytePhoto = stream.toByteArray();
        values.put(AddressBookContract.PHOTO, bytePhoto);

        Uri uri = mContext.getContentResolver().insert(AddressBookContract.CONTENT_URI, values);
        return uri;
    }
}
