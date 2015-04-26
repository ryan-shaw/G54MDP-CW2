package vc.min.ryan.addressbook.activities;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import vc.min.ryan.addressbook.BookManager;
import vc.min.ryan.addressbook.Person;
import vc.min.ryan.addressbook.PersonAdapter;
import vc.min.ryan.addressbook.R;
import vc.min.ryan.addressbook.content.AddressBookContract;


public class MainActivity extends Activity {

    private RecyclerView mRecyclerView;
    private BookManager mBookManager;
    private PersonAdapter mAdapter;
    private ImageButton mAddButton;
    private Context mContext;

    public static int sPosition; // Static variable for selected item in list
    public static final int EDIT = 1;
    public static final int DELETE = 2;

    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mContext = this;

        mAddButton = (ImageButton) findViewById(R.id.add_button);
        mRecyclerView = (RecyclerView) findViewById(R.id.people_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mBookManager = new BookManager(this); // Init the addressbook manager
        mAdapter = new PersonAdapter(mBookManager.getData(), mBookManager, this);

        mRecyclerView.setAdapter(mAdapter);
        Transition exitTrans = new Explode();
        getWindow().setExitTransition(exitTrans);
        Transition reenterTrans = new Slide();
        getWindow().setReenterTransition(reenterTrans);
        // Set listeners
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
        });
        // Gets called when database updated
        getContentResolver().registerContentObserver(AddressBookContract.CONTENT_URI, true, new ContentObserver(new Handler()){
            @Override
            public void onChange(boolean selfChange) {
                // Update data on databsase change
                mAdapter.updateData(mBookManager.getData());
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        mAdapter.updateData(mBookManager.getData());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Person person = mAdapter.getData().get(sPosition);
        Intent intent;
        switch (item.getItemId()) {
            case EDIT:
                // Start edit activity
                intent = new Intent(mContext, EditActivity.class);
                intent.putExtra("personId", person.getId());
                mContext.startActivity(intent);
            break;
            case DELETE:
                mBookManager.deleteContact(person.getId());
            break;
        }
        return super.onContextItemSelected(item);
    }

}