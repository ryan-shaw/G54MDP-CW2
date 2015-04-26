package vc.min.ryan.addressbook.content;

import android.content.UriMatcher;
import android.net.Uri;

import java.util.HashMap;

/**
 * Created by Ryan on 26/03/2015.
 *
 * Contract class for the content provider
 */
public final class AddressBookContract {
    public static final String      PROVIDER        = "vc.min.ryan.addressbook.Contacts";
    public static final String      URL             = "content://" + PROVIDER + "/contacts";
    public static final Uri         CONTENT_URI     = Uri.parse(URL);

    public static final String      _ID             = "_id";
    public static final String      FIRST_NAME      = "firstName";
    public static final String      LAST_NAME       = "lastName";
    public static final String      PHONE_NUMBER    = "phone";
    public static final String      EMAIL           = "email";
    public static final String      PHOTO           = "photo";

    public static final int         CONTACTS        = 1;
    public static final int         PERSON          = 2;

    public static final UriMatcher  URI_MATCHER;
    static{
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(PROVIDER, "contacts", CONTACTS);
        URI_MATCHER.addURI(PROVIDER, "contacts/#", PERSON);
    }

    public static final String DATABASE_NAME = "Contacts";
    public static final String TABLE_NAME = "contacts";
    public static final int DATABASE_VERSION = 6;
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " firstName TEXT NOT NULL, " +
                    " lastName TEXT NOT NULL, " +
                    " phone TEXT NOT NULL, " +
                    " email TEXT NOT NULL, " +
                    " photo TEXT);";

    public static HashMap<String, String> CONTACTS_MAP;
}
