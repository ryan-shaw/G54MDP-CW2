package vc.min.ryan.addressbook;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Ryan on 25/03/2015.
 */
public class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnCreateContextMenuListener{
    private ClickListener clickListener;

    public TextView name;
    public TextView phone;
    public ImageView photo;

    public PersonViewHolder(View v){
        super(v);
        name = (TextView) v.findViewById(R.id.name);
        phone = (TextView) v.findViewById(R.id.phone);
        photo = (ImageView) v.findViewById(R.id.mA_photo);
        v.setOnClickListener(this);
        v.setOnCreateContextMenuListener(this);
    }

    public interface ClickListener{
        public void onClick(View v, int position, boolean isLongClick);
        public boolean onItemLongClick(RecyclerView p, View c, int pos, long id);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        menu.add(Menu.NONE, 0, Menu.NONE, "Edit");//groupId, itemId, order, title
        menu.add(Menu.NONE, 1, Menu.NONE, "Delete");

    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
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
