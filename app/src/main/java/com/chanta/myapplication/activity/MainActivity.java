package com.chanta.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.chanta.myapplication.R;
import com.chanta.myapplication.fragment.RecentMeetingFragment;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by chanta on 08.12.17.
 */

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment myMeetingsListFragment =  new RecentMeetingFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentMeeting, myMeetingsListFragment);
        ft.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.standart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.log_out) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, StartActivity.class));
            finish();
            return true;
        } else if(id == R.id.action_add) {
            Intent intent = new Intent(MainActivity.this, NewMeetingActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
