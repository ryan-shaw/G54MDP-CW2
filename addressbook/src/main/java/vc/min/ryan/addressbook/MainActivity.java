package vc.min.ryan.addressbook;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;




public class MainActivity extends ActionBarActivity {

    private RecyclerView mRecyclerView;
    private BookManager mBookManager;
    private PersonAdapter mAdapter;
    private ImageButton mAddButton;
    private Context mContext;
    private int mPosition;
    public static final int CALL = 0;
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

        // Set listeners
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddActivity.class);
                startActivity(intent);
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
        int id = item.getItemId();
        Person person = mAdapter.getData().get(mPosition);
        Intent intent;
        switch (item.getItemId()) {
            case CALL /* Call */:
                intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + person.getPhoneNumber()));
                startActivity(intent);
                break;
            case EDIT /* Edit */:
                intent = new Intent(mContext, EditActivity.class);
                intent.putExtra("personId", person.getId());
                mContext.startActivity(intent);
            break;
            case DELETE /* Delete */:
                mBookManager.deleteContact(person.getId());
            break;
        }
        return super.onContextItemSelected(item);
    }

}
