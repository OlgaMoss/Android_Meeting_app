package com.chanta.myapplication.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chanta.myapplication.R;
import com.chanta.myapplication.model.Meeting;
import com.chanta.myapplication.model.Participant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chanta on 08.12.17.
 */

public class NewMeetingActivity extends BaseActivity {
    private static final String TAG = "NewMeetingActivity";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private EditText mTitleField;
    private EditText mBodyField;
    private FloatingActionButton mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meeting);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        mTitleField = findViewById(R.id.field_title);
        mBodyField = findViewById(R.id.field_body);
        mSubmitButton = findViewById(R.id.fab_submit_post);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitMeeting();
            }
        });
    }

    private void submitMeeting() {
        final String nameMeeting = mTitleField.getText().toString();
        final String descriptionMeeting = mBodyField.getText().toString();

        // Title is required
        if (TextUtils.isEmpty(nameMeeting)) {
            mTitleField.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(descriptionMeeting)) {
            mBodyField.setError(REQUIRED);
            return;
        }

        final Meeting meeting = new Meeting();
        meeting.setName(nameMeeting);
        meeting.setDescription(descriptionMeeting);

        if (meeting == null) {
            Log.e(TAG, "Meeting is unexpectedly null");
            return;
        }

        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Meeting...", Toast.LENGTH_SHORT).show();

        // [START single_value_read]
        final String participateId = getUid();
        mDatabase.child("participants").child(participateId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Participant user = dataSnapshot.getValue(Participant.class);

                        if (user == null) {
                            Log.e(TAG, "User " + participateId + " is unexpectedly null");
                            Toast.makeText(NewMeetingActivity.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                           // if (sourceMeeting == null) {
                                meeting.setUid(participateId);
                            writeNewMeeting(mDatabase.child("meetings").push().getKey(), participateId, meeting);
                           // } else mergeMeeting(userId, meeting);
                        }

                        setEditingEnabled(true);
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        setEditingEnabled(true);
                        // [END_EXCLUDE]
                    }
                });
        // [END single_value_read]
    }

    private void setEditingEnabled(boolean enabled) {
        mTitleField.setEnabled(enabled);
        mBodyField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }

    // [START write_fan_out]
    private void writeNewMeeting(String meetingKey, String userId, Meeting meeting) {
        Map<String, Object> meetingValues = meeting.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/meetings/" + meetingKey, meetingValues);
        childUpdates.put("/participant-meetings/" + userId + "/" + meetingKey, meetingValues);

        mDatabase.updateChildren(childUpdates);
    }
}
