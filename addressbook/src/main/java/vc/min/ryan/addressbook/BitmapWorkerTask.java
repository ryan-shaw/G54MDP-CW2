package vc.min.ryan.addressbook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Followed http://developer.android.com/training/displaying-bitmaps/process-bitmap.html to process bitmaps off UI thread
 */
public class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
    private final WeakReference<ImageView> mImgRef;
    private Person mPerson;
    private Context mContext;

    public BitmapWorkerTask(Context context, ImageView imageView, Person mPerson) {
        // Weak refernce allows imageView to still be garbage collected
        mImgRef = new WeakReference<>(imageView);
        this.mContext = context;
        this.mPerson = mPerson;
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(Integer... params) {
        Bitmap photo = Util.decodeUri(mContext, mPerson.getPhotoURI());
        return photo;
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (mImgRef != null && bitmap != null) {
            final ImageView imageView = mImgRef.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}