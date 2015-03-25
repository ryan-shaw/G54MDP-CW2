package vc.min.ryan.addressbook;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Ryan on 25/03/2015.
 */
public class PersonAdapter extends RecyclerView.Adapter<PersonViewHolder> {
    private List<Person> mDataset;

    public PersonAdapter(List<Person> data){
        this.mDataset = data;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, final int position){
        // Set content etc here
        Person person = mDataset.get(position);
        holder.name.setText(person.getFirstName() + " " + person.getLastName());
        holder.phone.setText(person.getPhoneNumber());
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
