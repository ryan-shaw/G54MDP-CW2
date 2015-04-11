package vc.min.ryan.addressbook;

import android.content.Context;
import android.content.Intent;
import android.provider.LiveFolders;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends ActionBarActivity {

    private RecyclerView mRecyclerView;
    private BookManager mBookManager;
    private PersonAdapter mAdapter;
    private ImageButton mAddButton;
    private Context mContext;

    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mAddButton = (ImageButton) findViewById(R.id.add_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddActivity.class);
                startActivity(intent);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.people_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mBookManager = new BookManager(this); // Init the addressbook manager
        mAdapter = new PersonAdapter(mBookManager.getData(), this);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override public void onItemClick(View v, int pos) {
                Log.d(TAG, "Short click");
                Person person = mBookManager.getData().get(pos);
                Intent intent = new Intent(mContext, PersonActivity.class);
                intent.putExtra("personId", person.getId());
                Log.d(TAG, "Starting new person activity: " + person.getId());
                mContext.startActivity(intent);
            }
            @Override public void onItemLongClick(View v, int pos){
                Log.d(TAG, "Long click");
            }
        }));
    }

    @Override
    public void onResume(){
        super.onResume();
        mAdapter.updateData(mBookManager.getData());
        mAdapter.notifyDataSetChanged();
    }

}
