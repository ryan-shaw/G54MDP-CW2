package vc.min.ryan.addressbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ryan on 25/03/2015.
 */
public class BookManager {
    private Context mContext;
    private final String TAG = "BookManager";

    public BookManager(Context context){
        this.mContext = context;
    }

    public Person getPerson(int id){
        Cursor c = mContext.getContentResolver().query(AddressBookContract.CONTENT_URI, null, AddressBookContract._ID + "=" + id, null, null);
        if(!c.moveToFirst())
            return null;
        return getPersonFromCursor(c);
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
                c.getString(c.getColumnIndex(AddressBookContract.PHOTO)));
        return person;
    }

    public boolean addContact(String firstName, String lastName, String phone, String email, Bitmap photo){

        if(firstName.length() == 0)
            return false;
        if(lastName.length() == 0)
            return false;
        if(phone.length() == 0)
            return false;

        String photoPath = saveFile(photo);

        ContentValues values = new ContentValues();
        values.put(AddressBookContract.FIRST_NAME, firstName);
        values.put(AddressBookContract.LAST_NAME, lastName);
        values.put(AddressBookContract.PHONE_NUMBER, phone);
        values.put(AddressBookContract.EMAIL, email);
        values.put(AddressBookContract.PHOTO, photoPath);

        Uri uri = mContext.getContentResolver().insert(AddressBookContract.CONTENT_URI, values);
        return uri != null;
    }

    public boolean editContact(int id, String firstName, String lastName, String phone, String email,
                               Bitmap photo){
        if(firstName.length() == 0)
            return false;
        if(lastName.length() == 0)
            return false;
        if(phone.length() == 0)
            return false;

        String photoPath = saveFile(photo);
        ContentValues values = new ContentValues();
        values.put(AddressBookContract.FIRST_NAME, firstName);
        values.put(AddressBookContract.LAST_NAME, lastName);
        values.put(AddressBookContract.PHONE_NUMBER, phone);
        values.put(AddressBookContract.EMAIL, email);
        values.put(AddressBookContract.PHOTO, photoPath);

        int res = mContext.getContentResolver().update(AddressBookContract.CONTENT_URI, values, "_id="+id, null);
        return res != -1;
    }


    public void deleteContact(int id){
        mContext.getContentResolver().delete(AddressBookContract.CONTENT_URI, "_id="+id, null);
    }

    /**
     * Save file to external storage
     * @param photo
     * @return
     *      File path to the photo
     */
    private String saveFile(Bitmap photo) {
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/media";
        File dir = new File(file_path);
        String path =  "pp" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + ".png";

        if (!dir.exists())
            dir.mkdirs();
        try {
            File file = new File(dir, path);
            FileOutputStream fOut = new FileOutputStream(file);

            photo.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        }catch(Exception e){
            Log.e(TAG, "err", e);

            return null;
        }

        return path;
    }
}
