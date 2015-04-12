package vc.min.ryan.addressbook;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.nfc.Tag;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ryan on 25/03/2015.
 * AsyncDrawable taken from http://developer.android.com/training/displaying-bitmaps/process-bitmap.html
 * As it was taking a long to time load bitmaps on the main thread, caused hangs.
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonViewHolder> {
    static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources res, Bitmap bitmap,
                             BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference =
                    new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }
    private List<Person> mDataset;
    private final String TAG = "PersonAdapter";
    private Context mContext;

    public PersonAdapter(List<Person> data, Context context){
        this.mDataset = data;
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder holder, final int position){
        final Person person = mDataset.get(position);
        holder.name.setText(person.getFirstName() + " " + person.getLastName());
        holder.phone.setText(person.getPhoneNumber());
        // Set ImageView via AsyncTask
        BitmapWorkerTask task = new BitmapWorkerTask((ImageView) holder.photo, person);
        task.execute(0);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item, parent, false);

        return new PersonViewHolder(v);
    }

    public int getItemCount(){
        return mDataset.size();
    }

    public void updateData(List<Person> data){
        this.mDataset = data;
    }

    public List<Person> getData(){
        return mDataset;
    }

}
