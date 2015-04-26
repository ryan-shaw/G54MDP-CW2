package vc.min.ryan.addressbook;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import vc.min.ryan.addressbook.activities.MainActivity;
import vc.min.ryan.addressbook.activities.PersonActivity;

/**
 * Created by Ryan on 25/03/2015.
 *
 * PersonViewHolder, the view holder for the recyclerview in the main activity
 */
public class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

    public TextView mName;
    public TextView mPhone;
    public ImageView mPhoto;
    public ImageButton mBPhone;
    public ImageButton mBSMS;
    private Context mContext;
    private final String TAG = "PersonViewHolder";

    public PersonViewHolder(View v, final BookManager bookManager, final Context context){
        super(v);
        mContext = context;
        mName = (TextView) v.findViewById(R.id.name);
        mPhone = (TextView) v.findViewById(R.id.phone);
        mPhoto = (ImageView) v.findViewById(R.id.maPhoto);
        mBPhone = (ImageButton) v.findViewById(R.id.maBCall);
        mBSMS = (ImageButton) v.findViewById(R.id.maBSMS);

        // Set click listeners
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PersonActivity.class);
                intent.putExtra("personId", bookManager.getData().get(getPosition()).getId());
                // Transition activity + shared elements
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity)mContext,
                        Pair.create((View) mPhoto, "photo"),
                        Pair.create((View) mName, "name"),
                        Pair.create((View) mPhone, "phone"),
                        Pair.create((View) mBPhone, "bPhone"),
                        Pair.create((View) mBSMS, "bSMS"));
                context.startActivity(intent, options.toBundle());
            }
        });
        mBPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the person
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + bookManager.getData().get(getPosition()).getPhoneNumber()));
                context.startActivity(intent);
            }
        });

        mBSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SMS the person
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:" + bookManager.getData().get(getPosition()).getPhoneNumber()));
                context.startActivity(intent);
            }
        });
        v.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // Add the menu items
        ((MainActivity)mContext).sPosition = getPosition(); // Update the position in main activity for the item selected method. It is static so it works across other objects.
        menu.add(Menu.NONE, MainActivity.EDIT, Menu.NONE, "Edit");
        menu.add(Menu.NONE, MainActivity.DELETE, Menu.NONE, "Delete");
    }
}
