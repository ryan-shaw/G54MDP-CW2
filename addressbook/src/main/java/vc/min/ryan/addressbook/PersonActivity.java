package vc.min.ryan.addressbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Ryan on 26/03/2015.
 */
public class PersonActivity extends Activity {

    private final String TAG = "PersonActivity";
    private BookManager mBookManager;
    private ImageView mPicture;


    @Override
    public void onCreate(Bundle instance){
        super.onCreate(instance);
        setContentView(R.layout.person_info);
        Intent intent = getIntent();
        int id = intent.getIntExtra("personId", -1);
        if(id == -1){
            Toast.makeText(this, "Something has gone wrong", Toast.LENGTH_LONG).show();
            return;
        }

        mPicture = (ImageView) findViewById(R.id.pI_photo);

        mBookManager = new BookManager(this);
        Person person = mBookManager.getPerson(id);

        mPicture.setImageBitmap(person.getPhotoBM());

        Log.d(TAG, ""+ person.getPhoto());
    }
}
