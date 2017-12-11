package com.chanta.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.chanta.myapplication.activity.BaseActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class DescribedActivity extends BaseActivity {

    private static final String TAG = "DescribedActivity";

    public static final String EXTRA_MEETING_KEY = "meeting_key";

    private DatabaseReference mDatabase, mMeetingRef, mMeetingGlobalRef;
    private ValueEventListener mMeetingListener;



    private TextView nameTextView;
    private TextView describeTextView;
    private TextView toDateTextView;
    private TextView toTimeTextView;
    private TextView fromDateTextView;
    private TextView fromTimeTextView;
    private TextView participantsTextView;
    private TextView priorityTextView;

    private Button button;

    private ArrayList<String> name;
    private ArrayList<String> middle;
    private ArrayList<String> lastName;
    private ArrayList<String> position;
    private ArrayList<String> number;

    public DescribedActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_described);
        final Intent intent = getIntent();

        String mMeetingKey = getIntent().getStringExtra(EXTRA_MEETING_KEY);
        if (mMeetingKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_MEETING_KEY");
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mMeetingRef = mDatabase.child("meetings").child(mMeetingKey);
        mMeetingGlobalRef = mDatabase.child("participant-meetings").child(getUid()).child(mMeetingKey);


        nameTextView = findViewById(R.id.name_textView);
        describeTextView = findViewById(R.id.describe_textView);
        toDateTextView = findViewById(R.id.to_date_textView);
        toTimeTextView = findViewById(R.id.to_time_textView);
        fromDateTextView = findViewById(R.id.from_date_textView);
        fromTimeTextView = findViewById(R.id.from_time_textView);
        participantsTextView = findViewById(R.id.participants_textView);
        priorityTextView = findViewById(R.id.priority_textView);

        button = findViewById(R.id.edit_button);

        nameTextView.setText(intent.getStringExtra("name"));
        describeTextView.setText(intent.getStringExtra("description"));
        toDateTextView.setText(intent.getStringExtra("toDate").split(" ")[0]);
        toTimeTextView.setText(intent.getStringExtra("toDate").split(" ")[1]);
        fromDateTextView.setText(intent.getStringExtra("fromDate").split(" ")[0]);
        fromTimeTextView.setText(intent.getStringExtra("fromDate").split(" ")[1]);
        priorityTextView.setText(intent.getStringExtra("priority"));

        name.addAll(intent.getStringArrayListExtra("firstName"));
        middle.addAll(intent.getStringArrayListExtra("lastName"));
        lastName.addAll(intent.getStringArrayListExtra("middleName"));
        position.addAll(intent.getStringArrayListExtra("numbers"));
        number.addAll(intent.getStringArrayListExtra("position"));

        participantsTextView.setText(lastName.toString());

        button.setOnClickListener(view -> {
           /* Intent intent = new Intent(this, NewMeetingActivity.class);
            meeting.setKey(mMeetingRef.getKey());
            intent.putExtra(NewMeetingActivity.SOURCE_MEETING_KEY, meeting);
            startActivity(intent);*/
            Intent intentNew = new Intent(getApplication(), AddedActivity.class);

            intentNew.putExtra("isNew", "false");

            intentNew.putExtra("key", intent.getStringExtra("key"));
            intentNew.putExtra("name", intent.getStringExtra("name"));
            intentNew.putExtra("description", intent.getStringExtra("description"));
            intentNew.putExtra("toDate", intent.getStringExtra("toDate").split(" ")[0]);
            intentNew.putExtra("toTime", intent.getStringExtra("toDate").split(" ")[1]);
            intentNew.putExtra("fromDate", intent.getStringExtra("fromDate").split(" ")[0]);
            intentNew.putExtra("fromTime", intent.getStringExtra("fromDate").split(" ")[1]);
//                intentNew.putExtra("participant", intent.getStringExtra("participant"));
            intentNew.putExtra("priority", intent.getStringExtra("priority"));

            intentNew.putStringArrayListExtra("firstName", name);
            intentNew.putStringArrayListExtra("lastName", lastName);
            intentNew.putStringArrayListExtra("middleName", middle);
            intentNew.putStringArrayListExtra("numbers", number);
            intentNew.putStringArrayListExtra("position", position);
            startActivity(intentNew);
        });
    }

}



