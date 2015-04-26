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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ryan on 25/03/2015.
 * Followed AsyncDrawable guide from http://developer.android.com/training/displaying-bitmaps/process-bitmap.html
 * As it was taking a long to time load bitmaps on the main thread, caused hangs.
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonViewHolder> {
    private List<Person> mDataset;
    private final String TAG = "PersonAdapter";
    private Context mContext;
    private BookManager mBookManager;

    public PersonAdapter(List<Person> data, BookManager bookManager, Context context){
        this.mDataset = data;
        this.mContext = context;
        this.mBookManager = bookManager;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder holder, final int position){
        final Person person = mDataset.get(position);
        holder.mName.setText(person.getFirstName() + " " + person.getLastName());
        holder.mPhone.setText(person.getPhoneNumber());
        // Set ImageView via AsyncTask, prevents the UI thread from taking too long, so don't cause ANR
        BitmapWorkerTask task = new BitmapWorkerTask(mContext, holder.mPhoto, person);
        task.execute(0);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item, parent, false);
        return new PersonViewHolder(v, mBookManager, mContext);
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
