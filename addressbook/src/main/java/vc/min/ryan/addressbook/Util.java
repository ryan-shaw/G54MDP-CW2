package vc.min.ryan.addressbook;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.FileNotFoundException;

/**
 * Created by Ryan on 21/04/2015.
 *
 * This class provides non-important features, they exist mainly for performance reasons
 */
public class Util {
    /**
     * Decode a given URI as a String to a Bitmap
     * @param context
     * @param uri
     * @return Bitmap, returns the requested or image of the 'person' placeholder.
     * @throws FileNotFoundException
     */
    public static Bitmap decodeUri(Context context, String uri)  {
        if(uri == null) return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_action_person);
        return decodeUri(context, Uri.parse(uri));
    }

    /**
     * Decode a given URI into a Bitmap
     * @param context
     * @param uri
     * @return Bitmap, returns the requested or image of the 'person' placeholder.
     */
    public static Bitmap decodeUri(Context context, Uri uri){
        if(uri == null) return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_action_person);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 10; // Get a smaller Bitmap than original
        try {
            return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
        }catch(FileNotFoundException e){
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_action_person);
        }
    }
}
