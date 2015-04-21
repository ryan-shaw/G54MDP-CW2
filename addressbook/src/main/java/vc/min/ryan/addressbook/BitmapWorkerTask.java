package vc.min.ryan.addressbook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * http://developer.android.com/training/displaying-bitmaps/process-bitmap.html
 */
class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    private int data = 0;
    private Person mPerson;
    private Context mContext;

    public BitmapWorkerTask(Context context, ImageView imageView, Person mPerson) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        imageViewReference = new WeakReference<ImageView>(imageView);
        this.mContext = context;
        this.mPerson = mPerson;
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(Integer... params) {
        if(mPerson.getPhotoPath() == null)
           return BitmapFactory.decodeResource(mContext.getResources(), R.drawable.select);
        Bitmap photo = mPerson.getPhotoBM();
        return photo;
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}