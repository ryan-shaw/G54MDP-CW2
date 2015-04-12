package vc.min.ryan.addressbook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Ryan on 12/04/2015.
 */
public class EditActivity extends ActionBarActivity {

    private Button mEditButton;
    private Context mContext;
    private TextView mFirstName;
    private TextView mLastName;
    private TextView mPhone;
    private TextView mEmail;
    private ImageView mPhoto;
    private BookManager mBookManager;

    private final int RESULT_LOAD_IMAGE = 1;
    private final String TAG = "EditActivity";
    private int _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mContext = this;

        Intent intent = getIntent();
        _id = intent.getIntExtra("personId", -1);

        mBookManager = new BookManager(this);


        mEditButton = (Button) findViewById(R.id.add_contact);
        mFirstName = (TextView) findViewById(R.id.firstName);
        mLastName = (TextView) findViewById(R.id.lastName);
        mPhone = (TextView) findViewById(R.id.phone);
        mEmail = (TextView) findViewById(R.id.email);
        mPhoto = (ImageView) findViewById(R.id.photo);

        mEditButton.setText("Edit"); // Update text

        mPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG, "Photo selected");
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });

        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Save");
                boolean success = mBookManager.editContact(_id, mFirstName.getText().toString(),
                      mLastName.getText().toString(), mPhone.getText().toString(),
                      mEmail.getText().toString(), ((BitmapDrawable)mPhoto.getDrawable()).getBitmap());

                if(!success){
                    Toast.makeText(mContext, "Something went wrong, check data", Toast.LENGTH_LONG).show();
                }else{
                    finish();
                }
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        Person person = mBookManager.getPerson(_id);
        if(person == null) {
            Toast.makeText(this, "Person not found", Toast.LENGTH_LONG).show();
            return;
        }
        mFirstName.setText(person.getFirstName());
        mLastName.setText(person.getLastName());
        mPhone.setText(person.getPhoneNumber());
        mEmail.setText(person.getEmail());
        mPhoto.setImageBitmap(person.getPhotoBM());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                mPhoto.setImageBitmap(bitmap);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
