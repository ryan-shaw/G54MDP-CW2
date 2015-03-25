package vc.min.ryan.addressbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

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
        data.add(new Person("Ryan", "Shaw", "0797227977", "ryan.shaw@min.vc"));

        Cursor c = mContext.getContentResolver().query(PersonContentProvider.CONTENT_URI, null, null, null, "firstName");
        c.moveToFirst();
        while(!c.isAfterLast()){
            data.add(getPersonFromCursor(c));
            c.moveToNext();
        }
        return data;
    }

    public Person getPersonFromCursor(Cursor c){
        Person person = new Person(
                c.getString(c.getColumnIndex(PersonContentProvider.FIRST_NAME)),
                c.getString(c.getColumnIndex(PersonContentProvider.LAST_NAME)),
                c.getString(c.getColumnIndex(PersonContentProvider.PHONE_NUMBER)),
                c.getString(c.getColumnIndex(PersonContentProvider.EMAIL)));
        return person;
    }

    public Uri addContact(String firstName, String lastName, String phone, String email){
        ContentValues values = new ContentValues();
        values.put(PersonContentProvider.FIRST_NAME, firstName);
        values.put(PersonContentProvider.LAST_NAME, lastName);
        values.put(PersonContentProvider.PHONE_NUMBER, phone);
        values.put(PersonContentProvider.EMAIL, email);

        Uri uri = mContext.getContentResolver().insert(PersonContentProvider.CONTENT_URI, values);
        return uri;
    }
}
