package vc.min.ryan.addressbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Ryan on 26/03/2015.
 */
public class PersonActivity extends Activity {

    private final String TAG = "PersonActivity";

    @Override
    public void onCreate(Bundle instance){
        super.onCreate(instance);
        setContentView(R.layout.person_info);
        Intent intent = getIntent();
        int id = intent.getIntExtra("personId", -1);
        Log.d(TAG, ""+ id);
    }
}
