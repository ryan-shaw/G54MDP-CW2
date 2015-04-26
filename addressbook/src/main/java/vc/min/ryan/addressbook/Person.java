package vc.min.ryan.addressbook;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.InputStream;

/**
 * Created by Ryan on 25/03/2015.
 *
 * This object holds all properties for a Person object
 *
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

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPhotoURI(){
        return photo;
    }

    public void setPhoto(String photo){
        this.photo = photo;
    }

}
