package vc.min.ryan.addressbook;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class AddActivity extends ActionBarActivity {

    private Button mAddButton;
    private Context mContext;
    private TextView mFirstName;
    private TextView mLastName;
    private TextView mPhone;
    private TextView mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mContext = this;

        final BookManager bookManager = new BookManager(this);

        mAddButton = (Button) findViewById(R.id.add_contact);
        mFirstName = (TextView) findViewById(R.id.firstName);
        mLastName = (TextView) findViewById(R.id.lastName);
        mPhone = (TextView) findViewById(R.id.phone);
        mEmail = (TextView) findViewById(R.id.email);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = bookManager.addContact(mFirstName.getText().toString(),
                        mLastName.getText().toString(), mPhone.getText().toString(),
                        mEmail.getText().toString());
                if(uri == null){
                    Toast.makeText(mContext, "Somethings went wrong", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(mContext, uri.toString(), Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }


}
