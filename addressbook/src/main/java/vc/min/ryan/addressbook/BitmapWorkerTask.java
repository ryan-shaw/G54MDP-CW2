package vc.min.ryan.addressbook;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * http://developer.android.com/training/displaying-bitmaps/process-bitmap.html
 */
class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    private int data = 0;
    private Person person;

    public BitmapWorkerTask(ImageView imageView, Person person) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        imageViewReference = new WeakReference<ImageView>(imageView);
        this.person = person;
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(Integer... params) {
        Bitmap photo = person.getPhotoBM();
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