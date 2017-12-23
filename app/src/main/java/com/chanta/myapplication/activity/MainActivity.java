package com.chanta.myapplication.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.chanta.myapplication.R;
import com.chanta.myapplication.fragment.RecentMeetingFragment;
import com.chanta.myapplication.model.Meeting;
import com.chanta.myapplication.notification.BackgroundNotificationService;
import com.chanta.myapplication.notification.NotificationBroadcastReceiver;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * Created by chanta on 08.12.17.
 */

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    public static String UPLOAD_MEETINGS_KEY = "upload_meetings";
    public static String RECENT_MEETINGS_KEY = "recent_meeting";
    private OnUpdateReceiver mOnUpdateReceiver;
    private List<Meeting> myMeetings;
    private List<Meeting> recentMeetings;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment myMeetingsListFragment = new RecentMeetingFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentMeeting, myMeetingsListFragment);
        ft.commit();
        Intent intent = new Intent(this, NotificationBroadcastReceiver.class);
        intent.setAction("com.chanta.myapplication.notification.action.STARTED");
        sendBroadcast(intent);

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
        } else if (id == R.id.action_add) {
            Intent intent = new Intent(MainActivity.this, NewMeetingActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_update) {
            mOnUpdateReceiver = new OnUpdateReceiver();//todo action???
            IntentFilter intentFilterTime = new IntentFilter(BackgroundNotificationService.UPLOAD);
            intentFilterTime.addCategory(Intent.CATEGORY_DEFAULT);
            registerReceiver(mOnUpdateReceiver, intentFilterTime);

            Intent intent = new Intent(getApplicationContext(), NotificationBroadcastReceiver.class);
            intent.setAction("com.chanta.myapplication.notification.action.STARTED");
            sendBroadcast(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class OnUpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            myMeetings = (List<Meeting>) intent.getSerializableExtra(MainActivity.UPLOAD_MEETINGS_KEY);
            recentMeetings = (List<Meeting>) intent.getSerializableExtra(MainActivity.RECENT_MEETINGS_KEY);
            Toast.makeText(getApplicationContext(), R.string.load_msg, Toast.LENGTH_LONG).show();
        }
    }

    public List<Meeting> getMyMeetings() {
        return myMeetings;
    }

    public List<Meeting> getRecentMeetings() {
        return recentMeetings;
    }

}
