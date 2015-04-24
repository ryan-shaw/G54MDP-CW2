package vc.min.ryan.addressbook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    public ImageButton mBPhone;
    public ImageButton mBSMS;

    private static final String TAG = "PersonViewHolder";

    public PersonViewHolder(View v, final BookManager bookManager, final Context context){
        super(v);
        mName = (TextView) v.findViewById(R.id.name);
        mPhone = (TextView) v.findViewById(R.id.phone);
        mPhoto = (ImageView) v.findViewById(R.id.maPhoto);
        mBPhone = (ImageButton) v.findViewById(R.id.maBCall);
        mBSMS = (ImageButton) v.findViewById(R.id.maBSMS);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PersonActivity.class);
                intent.putExtra("personId", bookManager.getData().get(getPosition()).getId());
                context.startActivity(intent);
            }
        });
        mBPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + bookManager.getData().get(getPosition()).getPhoneNumber()));
                context.startActivity(intent);
            }
        });

        mBSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:" + bookManager.getData().get(getPosition()).getPhoneNumber()));
                context.startActivity(intent);
            }
        });
        v.setOnCreateContextMenuListener(this);
    }

    public interface ClickListener {
        public void onClick(View v, int position, boolean isLongClick);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // Add the menu items
        menu.add(Menu.NONE, MainActivity.CALL, Menu.NONE, "Call");
        menu.add(Menu.NONE, MainActivity.EDIT, Menu.NONE, "Edit");
        menu.add(Menu.NONE, MainActivity.DELETE, Menu.NONE, "Delete");

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
