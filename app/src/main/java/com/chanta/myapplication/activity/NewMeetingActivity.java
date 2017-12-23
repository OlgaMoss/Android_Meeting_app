package com.chanta.myapplication.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chanta.myapplication.R;
import com.chanta.myapplication.model.Meeting;
import com.chanta.myapplication.model.Participant;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.currentTimeMillis;

/**
 * Created by chanta on 08.12.17.
 */

public class NewMeetingActivity extends BaseActivity {
    private static final String TAG = "NewMeetingActivity";
    private static final String REQUIRED = "Required";
    public static final String MEETING = "meeting";
    public static final String MEETING_KEY = "meeting-key";

    private DatabaseReference mDatabase;
    private DatabaseReference mParticipatesReference;

    private Meeting sourceMeeting = null;

    private ParticipateAdapter mAdapter;

    private int startYear, startMonth, startDay, startHours, startMinutes;
    private int endYear, endMonth, endDay, endHours, endMinutes;

    private EditText mNameMeetingField;
    private EditText mDescriptionField;
    private Spinner mPrioritySpinner;
    private TextView mStartDateTextView, mStartTimeTextView, mEndDateTextView, mEndTimeTextView;
    private RecyclerView mParticipatesRecycler;

    private FloatingActionButton mSubmitButton;
    private Button mAddPartButton;

    private String newParticipateId;
    private String meetingKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meeting);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mParticipatesReference = FirebaseDatabase.getInstance().getReference();

        mNameMeetingField = findViewById(R.id.field_title);
        mDescriptionField = findViewById(R.id.field_body);
        mPrioritySpinner = findViewById(R.id.prioritySpinner);
        mStartDateTextView = findViewById(R.id.to_date_textView);
        mStartTimeTextView = findViewById(R.id.to_time_textView);
        mEndDateTextView = findViewById(R.id.from_date_textView);
        mEndTimeTextView = findViewById(R.id.from_time_textView);

        mAddPartButton = findViewById(R.id.add_part);
        mAddPartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewMeetingActivity.this, AddedParticipantActivity.class);
                startActivity(intent);
            }
        });

        mSubmitButton = findViewById(R.id.fab_submit_post);
        mSubmitButton.setOnClickListener(v -> submitMeeting());

        if (getIntent().hasExtra("participateId")) {
            newParticipateId = getIntent().getStringExtra("participateId");
            Log.d(TAG, "onCreate: newParticipateId " + newParticipateId);
        }

        Meeting meeting = (Meeting) getIntent().getSerializableExtra(MEETING);

        if (meeting != null) {
            sourceMeeting = meeting;
//todo вставить тексте

            mNameMeetingField.setText(meeting.getName());
            mDescriptionField.setText(meeting.getDescription());
            mPrioritySpinner.setId(getResources().getIdentifier("list_priorities", "array", meeting.getPriority()));
            mStartDateTextView.setText(meeting.getDateTo().split(" ")[0]);
            mStartTimeTextView.setText(meeting.getDateTo().split(" ")[1]);
            mEndDateTextView.setText(meeting.getDateFrom().split(" ")[0]);
            mEndTimeTextView.setText(meeting.getDateFrom().split(" ")[1]);

        } else {

            final Calendar c = Calendar.getInstance();

            startYear = c.get(Calendar.YEAR);
            startMonth = c.get(Calendar.MONTH) + 1;
            startDay = c.get(Calendar.DAY_OF_MONTH);
            startHours = c.get(Calendar.HOUR_OF_DAY);
            startMinutes = c.get(Calendar.MINUTE);

            endYear = c.get(Calendar.YEAR);
            endMonth = c.get(Calendar.MONTH) + 1;
            endDay = c.get(Calendar.DAY_OF_MONTH);
            endHours = c.get(Calendar.HOUR_OF_DAY);
            endMinutes = c.get(Calendar.MINUTE);
            mStartDateTextView.setText(String.format("%s%s%s%s%s", String.valueOf(startDay), "/", String.valueOf(startMonth), "/", String.valueOf(startYear)));
            mStartTimeTextView.setText(String.format("%s%s%s", String.valueOf(startHours), ":", String.valueOf(startMinutes)));
            mEndDateTextView.setText(String.format("%s%s%s%s%s", String.valueOf(endDay), "/", String.valueOf(endMonth), "/", String.valueOf(endYear)));
            mEndTimeTextView.setText(String.format("%s%s%s", String.valueOf(endHours), ":", String.valueOf(endMinutes)));

//        mParticipatesRecycler.setLayoutManager(new LinearLayoutManager(this));

//        mAdapter = new ParticipateAdapter(this, mParticipatesReference);
//        mParticipatesRecycler.setAdapter(mAdapter);
        }

    }

    private void submitMeeting() {
        final String nameMeeting = mNameMeetingField.getText().toString();
        final String descriptionMeeting = mDescriptionField.getText().toString();
        final String startDate = mStartDateTextView.getText().toString();
        final String startTime = mStartTimeTextView.getText().toString();
        final String endDate = mEndDateTextView.getText().toString();
        final String endTime = mEndTimeTextView.getText().toString();
        final String meetingPriority = mPrioritySpinner.getSelectedItem().toString();
        final String start = String.format("%s %s", startDate, startTime);
        final String end = String.format("%s %s", endDate, endTime);
        final Long dateCreated = currentTimeMillis();

        // Title is required
        if (TextUtils.isEmpty(nameMeeting)) {
            mNameMeetingField.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(descriptionMeeting)) {
            mDescriptionField.setError(REQUIRED);
            return;
        }

        Map<String, Boolean> users = new HashMap<>();
        users.put("vdvfd", true);//todo ключ participates
        users.put("use dvvfdr1", true);

        final Meeting meeting = new Meeting();
        meeting.setName(nameMeeting);
        meeting.setDescription(descriptionMeeting);
        meeting.setPriority(meetingPriority);
        meeting.setDateTo(start);
        meeting.setDateFrom(end);
        meeting.setDateCreated(dateCreated.toString());
        meeting.setParticipant(users);

        if (meeting == null) {
            Log.e(TAG, "Meeting is unexpectedly null");
            return;
        }

        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        //Toast.makeText(this, "Meeting...", Toast.LENGTH_SHORT).show();

        // [START single_value_read]
        final String participateId = getUid();
        mParticipatesReference.child("participant-meetings").child(participateId);

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
                            meetingKey = getIntent().getStringExtra(MEETING_KEY) != null ?
                                    getIntent().getStringExtra(MEETING_KEY) :
                                    mDatabase.child("meetings").push().getKey();

                            writeNewMeeting(meetingKey, participateId, meeting);
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
        mNameMeetingField.setEnabled(enabled);
        mDescriptionField.setEnabled(enabled);
        mPrioritySpinner.setEnabled(enabled);
        mStartDateTextView.setEnabled(enabled);
        mStartTimeTextView.setEnabled(enabled);
        mEndDateTextView.setEnabled(enabled);
        mEndTimeTextView.setEnabled(enabled);
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


    public void onChooseStartDate(View view) {
        String startDate = ((TextView) view).getText().toString();
        String[] parts = startDate.split("/");
        startYear = Integer.parseInt(parts[0]);
        startMonth = Integer.parseInt(parts[1]);
        startDay = Integer.parseInt(parts[2]);
        openDateDialog(startDay, startMonth, startYear, mStartDateTextView);
    }

    public void onChooseEndDate(View view) {
        String endDate = ((TextView) view).getText().toString();
        String[] parts = endDate.split("/");
        endYear = Integer.parseInt(parts[0]);
        endMonth = Integer.parseInt(parts[1]);
        endDay = Integer.parseInt(parts[2]);
        openDateDialog(endDay, endMonth, endYear, mEndDateTextView);
    }

    public void openDateDialog(final int d, final int m, final int y,
                               final TextView dateTextView) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    dateTextView.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void onChooseStartTime(View view) {
        String startTime = ((TextView) view).getText().toString();
        String[] parts = startTime.split(":");
        startHours = Integer.parseInt(parts[0]);
        startMinutes = Integer.parseInt(parts[1]);
        openTimeDialog(startHours, startMinutes, mStartTimeTextView);
    }

    public void onChooseEndTime(View view) {
        String endTime = ((TextView) view).getText().toString();
        String[] parts = endTime.split(":");
        endHours = Integer.parseInt(parts[0]);
        endMinutes = Integer.parseInt(parts[1]);
        openTimeDialog(endHours, endMinutes, mEndTimeTextView);
    }


    public void openTimeDialog(final int h, final int m, final TextView timeTextView) {

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> timeTextView.setText(hourOfDay + ":" + minute), h, m, false);
        timePickerDialog.show();
    }

    private static class ParticipateViewHolder extends RecyclerView.ViewHolder {

        public TextView authorView;

        public ParticipateViewHolder(View itemView) {
            super(itemView);

            authorView = itemView.findViewById(R.id.nameParticipate);
        }
    }


    private static class ParticipateAdapter extends RecyclerView.Adapter<NewMeetingActivity.ParticipateViewHolder> {

        private Context mContext;
        private DatabaseReference mDatabaseReference;
        private ChildEventListener mChildEventListener;

        private List<String> mParticipateIds = new ArrayList<>();
        private List<Participant> mParticipates = new ArrayList<>();

        public ParticipateAdapter(final Context context, DatabaseReference ref) {
            mContext = context;
            mDatabaseReference = ref;

            // Create child event listener
            // [START child_event_listener_recycler]
            ChildEventListener childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                    Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                    // A new comment has been added, add it to the displayed list
                    Participant comment = dataSnapshot.getValue(Participant.class);

                    // [START_EXCLUDE]
                    // Update RecyclerView
                    mParticipateIds.add(dataSnapshot.getKey());
                    mParticipates.add(comment);
                    notifyItemInserted(mParticipates.size() - 1);
                    // [END_EXCLUDE]
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                    Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                    // A comment has changed, use the key to determine if we are displaying this
                    // comment and if so displayed the changed comment.
                    Participant newComment = dataSnapshot.getValue(Participant.class);
                    String ParticipateKey = dataSnapshot.getKey();

                    // [START_EXCLUDE]
                    int commentIndex = mParticipateIds.indexOf(ParticipateKey);
                    if (commentIndex > -1) {
                        // Replace with the new data
                        mParticipates.set(commentIndex, newComment);

                        // Update the RecyclerView
                        notifyItemChanged(commentIndex);
                    } else {
                        Log.w(TAG, "onChildChanged:unknown_child:" + ParticipateKey);
                    }
                    // [END_EXCLUDE]
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                    // A comment has changed, use the key to determine if we are displaying this
                    // comment and if so remove it.
                    String participateKey = dataSnapshot.getKey();

                    // [START_EXCLUDE]
                    int participateIndex = mParticipateIds.indexOf(participateKey);
                    if (participateIndex > -1) {
                        // Remove data from the list
                        mParticipateIds.remove(participateIndex);
                        mParticipates.remove(participateIndex);

                        // Update the RecyclerView
                        notifyItemRemoved(participateIndex);
                    } else {
                        Log.w(TAG, "onChildRemoved:unknown_child:" + participateKey);
                    }
                    // [END_EXCLUDE]
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                    Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                    // A comment has changed position, use the key to determine if we are
                    // displaying this comment and if so move it.
                    Participant movedParticipate = dataSnapshot.getValue(Participant.class);
                    String participateKey = dataSnapshot.getKey();

                    // ...
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                    Toast.makeText(mContext, "Failed to load comments.",
                            Toast.LENGTH_SHORT).show();
                }
            };
            ref.addChildEventListener(childEventListener);
            // [END child_event_listener_recycler]

            // Store reference to listener so it can be removed on app stop
            mChildEventListener = childEventListener;
        }

        @Override
        public NewMeetingActivity.ParticipateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.list_for_particpants, parent, false);
            return new NewMeetingActivity.ParticipateViewHolder(view);
        }

        @Override
        public void onBindViewHolder(NewMeetingActivity.ParticipateViewHolder holder, int position) {
            Participant participate = mParticipates.get(position);
            holder.authorView.setText(participate.getLastName());
        }

        @Override
        public int getItemCount() {
            return mParticipates.size();
        }

        public void cleanupListener() {
            if (mChildEventListener != null) {
                mDatabaseReference.removeEventListener(mChildEventListener);
            }
        }

    }
}
