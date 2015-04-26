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
     * @param context
     * @param uri
     * @return
     * @throws FileNotFoundException
     */
    public static Bitmap decodeUri(Context context, String uri)  {
        if(uri == null) return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_action_person);
        return decodeUri(context, Uri.parse(uri));
    }

    public static Bitmap decodeUri(Context context, Uri uri){
        if(uri == null) return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_action_person);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 10;
        try {
            return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
        }catch(FileNotFoundException e){
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_action_person);
        }
    }
}
