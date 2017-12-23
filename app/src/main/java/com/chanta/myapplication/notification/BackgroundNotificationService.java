package com.chanta.myapplication.notification;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.chanta.myapplication.R;
import com.chanta.myapplication.activity.MainActivity;
import com.chanta.myapplication.model.Meeting;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.System.currentTimeMillis;

/**
 * Created by chanta on 12.12.17.
 */

public class BackgroundNotificationService extends IntentService {
    private static final String TAG = "BGMeetingsUpdateService";
    private static List<Meeting> storedMeetings = null;

    public static final String UPLOAD = "com.chanta.myapplication.notification.action.UPLOAD";
    public static final String LOAD_EVERY_10_MIN = "com.chanta.myapplication.notification.action.LOAD_EVERY_10_MIN";

    DatabaseReference databaseReference;
    MeetingListener meetingListener, uploadMeetingListener;
    Long today;

    List<Meeting> meetings = new ArrayList<>();
    List<String> keys = new ArrayList<>();
    private Handler mHandler;


    public BackgroundNotificationService() {
        super("BackgroundNotificationService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        today = currentTimeMillis();

        mHandler.post(() -> Toast.makeText(BackgroundNotificationService.this, "Test", Toast.LENGTH_LONG).show());

        if (intent != null) {
            final String action = intent.getAction();

            if (UPLOAD.equals(action)) {

                final boolean toNotify = intent.getBooleanExtra("toNotify", false);
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
//                    databaseReference = FirebaseDatabase.getInstance().getReference();
//                    databaseReference.addValueEventListener( new MeetingListener(databaseReference, toNotify));
                    //databaseReference.child("meetings").addValueEventListener(meetingListener);
                }
            } else if (LOAD_EVERY_10_MIN.equals(action)) {
                final boolean toNotify = intent.getBooleanExtra("toNotify", true);
                ConnectivityManager connMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMan.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    Toast.makeText(getApplicationContext(), "Request get all meetings", Toast.LENGTH_LONG).show();
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference.addValueEventListener(new MeetingListener(databaseReference, toNotify));
                }

            }
        }

    }

    public class MeetingListener implements ValueEventListener {

        private DatabaseReference ref;
        private Boolean toNotify;


        public MeetingListener(DatabaseReference r, Boolean toNotify) {
            ref = r;
            this.toNotify = toNotify;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Long dateCreated;
            meetings.clear();
            Iterable<DataSnapshot> meetingsIterable = dataSnapshot.child("meetings").getChildren();
            int cntMeetings = 0;
            try {
                for (DataSnapshot snapshot : meetingsIterable) {
                    Iterable<DataSnapshot> meeting = snapshot.getChildren();
                    for (DataSnapshot props : meeting) {
                        if ("dateCreated".equals(props.getKey())) {
                            dateCreated = Long.valueOf(String.valueOf(props.getValue()));
                            if (today - dateCreated <= 1000 * 60 * 10) {
//                                meetings.add(meeting);
//                                keys.add(child.getKey());
                                cntMeetings++;
                            }
                        }
                    }
                    if (toNotify && cntMeetings > 0) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                                0, intent,
                                PendingIntent.FLAG_CANCEL_CURRENT);

                        Notification.Builder builder = new Notification.Builder(getApplicationContext());

                        builder.setContentIntent(pendingIntent)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setTicker(getString(R.string.notification))
                                .setWhen(System.currentTimeMillis())
                                .setAutoCancel(true)
                                .setContentTitle(getString(R.string.notification))
                                .setContentText("Прочти!");
                        Notification notification = builder.build();

                        NotificationManager manager = (NotificationManager) getApplicationContext().
                                getSystemService(Context.NOTIFICATION_SERVICE);
                        if (manager != null)
                            manager.notify(new Random().nextInt(), notification);
                    }
                }
            } catch (Exception e) {
                Log.e("UpdateMeeting_E", e.getMessage());
            } finally {
                databaseReference.removeEventListener(this);
                stopSelf();
            }

//            if (!toNotify) {
//                Intent intent = new Intent();
//                intent.putExtra(MainActivity.UPLOAD_MEETINGS_KEY, (Serializable) meetings);
//                Toast.makeText(getApplicationContext(), R.string.load_msg, Toast.LENGTH_LONG).show();
//
//            }
//            databaseReference.removeEventListener(this);
//            toNotify = false;

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }

    }


}
