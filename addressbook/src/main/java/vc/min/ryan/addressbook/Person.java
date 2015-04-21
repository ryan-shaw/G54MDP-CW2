package vc.min.ryan.addressbook;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ryan on 25/03/2015.
 *
 * This will hold all properties for a person
 */
public class Person {

    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String photo;

    public Person(int id, String firstName, String lastName, String phoneNumber, String email, String photo){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.photo = photo;
    }

    public int getId(){
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoPath(){
        return photo;
    }

    public Bitmap getPhotoBM() {
        String photoPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/media/" + photo;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        final Bitmap b = BitmapFactory.decodeFile(photoPath, options);
        return b;
    }

    public void setPhoto(String photo){
        this.photo = photo;
    }

    public void setPhotoPath(String path){
        this.photo = path;
    }

}
