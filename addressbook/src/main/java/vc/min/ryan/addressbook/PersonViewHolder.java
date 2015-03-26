package vc.min.ryan.addressbook;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Ryan on 25/03/2015.
 */
public class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private ClickListener clickListener;

    public TextView name;
    public TextView phone;
    public ImageView photo;

    public PersonViewHolder(View v){
        super(v);
        name = (TextView) v.findViewById(R.id.name);
        phone = (TextView) v.findViewById(R.id.phone);
        photo = (ImageView) v.findViewById(R.id.mA_photo);
    }

    public interface ClickListener{
        public void onClick(View v, int position, boolean isLongClick);
    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View v){
        clickListener.onClick(v, getPosition(), false);
    }
}
