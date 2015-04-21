package vc.min.ryan.addressbook;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


public class AddActivity extends ActionBarActivity {

    private Button mAddButton;
    private Context mContext;
    private TextView mFirstName;
    private TextView mLastName;
    private TextView mPhone;
    private TextView mEmail;
    private ImageView mPhoto;
    private String mPhotoString;

    private final int RESULT_LOAD_IMAGE = 1;
    private static final String TAG = "AddActivity";

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
        mPhoto = (ImageView) findViewById(R.id.photo);

        mPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG, "Photo selected");
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = bookManager.addContact(mFirstName.getText().toString(),
                        mLastName.getText().toString(), mPhone.getText().toString(),
                        mEmail.getText().toString(), mPhotoString);
                if(!success){
                    Toast.makeText(mContext, "Something went wrong, check data", Toast.LENGTH_LONG).show();
                }else{
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            try {
                Uri uri = data.getData();
                Bitmap bitmap = Util.decodeUri(getContentResolver(), uri);

                mPhotoString = BookManager.saveFile(bitmap);
                mPhoto.setImageBitmap(bitmap);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
