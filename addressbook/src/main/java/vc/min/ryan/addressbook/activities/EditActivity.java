package vc.min.ryan.addressbook.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

import vc.min.ryan.addressbook.BookManager;
import vc.min.ryan.addressbook.Person;
import vc.min.ryan.addressbook.R;
import vc.min.ryan.addressbook.Util;

/**
 * Created by Ryan on 12/04/2015.
 */
public class EditActivity extends Activity {

    private Button mEditButton;
    private Context mContext;
    private TextView mFirstName;
    private TextView mLastName;
    private TextView mPhone;
    private TextView mEmail;
    private ImageView mPhoto;
    private String mPhotoString;
    private BookManager mBookManager;

    private final int RESULT_LOAD_IMAGE = 1;
    private final String TAG = "EditActivity";
    private int _id;
    private Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mContext = this;

        Intent intent = getIntent();
        _id = intent.getIntExtra("personId", -1);

        mBookManager = new BookManager(this);

        person = mBookManager.getPerson(_id);

        // Setup views
        mEditButton = (Button) findViewById(R.id.add_contact);
        mFirstName = (TextView) findViewById(R.id.firstName);
        mLastName = (TextView) findViewById(R.id.lastName);
        mPhone = (TextView) findViewById(R.id.phone);
        mEmail = (TextView) findViewById(R.id.email);
        mPhoto = (ImageView) findViewById(R.id.photo);

        mEditButton.setText("Edit"); // Update text as we are reusing add xml

        // Setup click listeners
        mPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });

        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = mBookManager.editContact(_id, mFirstName.getText().toString(),
                        mLastName.getText().toString(), mPhone.getText().toString(),
                        mEmail.getText().toString(), mPhotoString);

                if (!success) {
                    Toast.makeText(mContext, "Something went wrong, check data", Toast.LENGTH_LONG).show();
                } else {
                    finish();
                }
            }
        });
        if (person == null) {
            Toast.makeText(this, "Person not found", Toast.LENGTH_LONG).show();
            return;
        }

        // Set text in TextViews
        mFirstName.setText(person.getFirstName());
        mLastName.setText(person.getLastName());
        mPhone.setText(person.getPhoneNumber());
        mEmail.setText(person.getEmail());
        mPhotoString = person.getPhotoURI();
        if (mPhotoString != null) {
            // If the Person has a photo set the ImageView.
            mPhoto.setImageBitmap(Util.decodeUri(this, mPhotoString));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check we have the expected requestCode and resultCode is ok, and data is available.
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream is = getContentResolver().openInputStream(uri);
                mPhoto.setImageBitmap(BitmapFactory.decodeStream(is)); // Set Image from URI
                mPhotoString = uri.toString(); // Set the photo string to the URI
            }catch(FileNotFoundException e){
                Toast.makeText(this, "File not found", Toast.LENGTH_LONG).show();
            }
        }
    }
}