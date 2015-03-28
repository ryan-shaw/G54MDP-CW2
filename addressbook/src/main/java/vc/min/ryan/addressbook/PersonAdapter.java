package vc.min.ryan.addressbook;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Ryan on 25/03/2015.
 */
public class PersonAdapter extends RecyclerView.Adapter<PersonViewHolder> {
    private List<Person> mDataset;
    private final String TAG = "PersonAdapter";
    private Context mContext;

    public PersonAdapter(List<Person> data, Context context){
        this.mDataset = data;
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, final int position){
        // Set content etc here
        Person person = mDataset.get(position);
        holder.name.setText(person.getFirstName() + " " + person.getLastName());
        holder.phone.setText(person.getPhoneNumber());
        byte[] photo = person.getPhoto();
        if(photo != null) {
            holder.photo.setImageBitmap(BitmapFactory.decodeByteArray(photo, 0, photo.length));
            Log.d(TAG, "Setting photo");
        }
        holder.setClickListener(new PersonViewHolder.ClickListener() {
            @Override
            public void onClick(View v, int position, boolean isLongClick) {
                Person person = mDataset.get(position);
                Intent intent = new Intent(mContext, PersonActivity.class);
                intent.putExtra("personId", person.getId());
                Log.d(TAG, "Starting new person activity: " + person.getId());
                mContext.startActivity(intent);
            }
        });
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

}
