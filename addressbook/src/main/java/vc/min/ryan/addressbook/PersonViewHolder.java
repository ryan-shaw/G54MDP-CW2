package vc.min.ryan.addressbook;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Ryan on 25/03/2015.
 *
 * PersonViewHolder, the view holder for the recyclerview in the main activity
 */
public class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnCreateContextMenuListener{
    private ClickListener clickListener;

    public TextView mName;
    public TextView mPhone;
    public ImageView mPhoto;

    public PersonViewHolder(View v){
        super(v);
        mName = (TextView) v.findViewById(R.id.name);
        mPhone = (TextView) v.findViewById(R.id.phone);
        mPhoto = (ImageView) v.findViewById(R.id.maPhoto);
        v.setOnCreateContextMenuListener(this);
    }

    public interface ClickListener{
        public void onClick(View v, int position, boolean isLongClick);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        menu.add(Menu.NONE, 0, Menu.NONE, "Call");
        menu.add(Menu.NONE, 1, Menu.NONE, "Edit");//groupId, itemId, order, title
        menu.add(Menu.NONE, 2, Menu.NONE, "Delete");

    }

    @Override
    public void onClick(View v){
        clickListener.onClick(v, getPosition(), false);
    }

    @Override
    public boolean onLongClick(View v) {
        clickListener.onClick(v, getPosition(), true);
        return true;
    }
}
