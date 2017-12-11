package com.chanta.myapplication.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.List;

/**
 * Created by chanta on 08.12.17.
 */

public class MeetingDetailActivity extends BaseActivity{


    private static final String TAG = "MeetingDetailActivity";

    public static final String EXTRA_MEETING_KEY = "meeting_key";

    private DatabaseReference mMeetingReference;
    private DatabaseReference mParticipatesReference;
    private ValueEventListener mMeetingListener;
    private String mMeetingKey;
    private CommentAdapter mAdapter;

    private TextView mNameView;
    private TextView mDescriptionView;
    private TextView mDateToView;
    private TextView mTimeToView;
    private TextView mDateFromView;
    private TextView mTimeFromView;
    private TextView mPriorityView;
    private Button mEditButton;
    private Button mAddedParticipateButton;
    private RecyclerView mParticipatesRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_detail);

        // Get post key from intent
        mMeetingKey = getIntent().getStringExtra(EXTRA_MEETING_KEY);
        if (mMeetingKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_MEETING_KEY");
        }

        // Initialize Database
        mMeetingReference = FirebaseDatabase.getInstance().getReference()
                .child("meetings").child(mMeetingKey);
        mParticipatesReference = FirebaseDatabase.getInstance().getReference()
                .child("participant-meetings").child(mMeetingKey);

        // Initialize Views
        mNameView = findViewById(R.id.meeting_name_detail);
        mDescriptionView = findViewById(R.id.meeting_description);
        mDateToView = findViewById(R.id.to_date_textView_detail);
        mTimeToView = findViewById(R.id.to_time_textView_detail);
        mDateFromView = findViewById(R.id.from_date_textView_detail);
        mTimeFromView = findViewById(R.id.from_time_textView_detail);
        mPriorityView = findViewById(R.id.meeting_priority);
        mEditButton = findViewById(R.id.edit_button);
        mAddedParticipateButton = findViewById(R.id.added_participate_button);
        mParticipatesRecycler = findViewById(R.id.recycler_participates);

        mEditButton.setOnClickListener(view -> {

                }
        );
        mParticipatesRecycler.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onStart() {
        super.onStart();

        // Add value event listener to the post
        // [START post_value_event_listener]
        ValueEventListener meetingListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Meeting meeting = dataSnapshot.getValue(Meeting.class);
                // [START_EXCLUDE]
                mNameView.setText(meeting.getName());
                mDescriptionView.setText(meeting.getDescription());
                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(MeetingDetailActivity.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mMeetingReference.addValueEventListener(meetingListener);
        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        mMeetingListener = meetingListener;

        // Listen for comments
        mAdapter = new CommentAdapter(this, mParticipatesReference);
        mParticipatesRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();

        // Remove post value event listener
        if (mMeetingListener != null) {
            mMeetingReference.removeEventListener(mMeetingListener);
        }

        // Clean up comments listener
        mAdapter.cleanupListener();
    }


    private static class ParticipateViewHolder extends RecyclerView.ViewHolder {

        public TextView authorView;

        public ParticipateViewHolder(View itemView) {
            super(itemView);

            authorView = itemView.findViewById(R.id.nameParticipate);
        }
    }

    private static class CommentAdapter extends RecyclerView.Adapter<ParticipateViewHolder> {

        private Context mContext;
        private DatabaseReference mDatabaseReference;
        private ChildEventListener mChildEventListener;

        private List<String> mParticipateIds = new ArrayList<>();
        private List<Participant> mParticipates = new ArrayList<>();

        public CommentAdapter(final Context context, DatabaseReference ref) {
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
        public ParticipateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.list_for_partision, parent, false);
            return new ParticipateViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ParticipateViewHolder holder, int position) {
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
