package com.chanta.myapplication.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chanta.myapplication.R;
import com.chanta.myapplication.activity.MeetingDetailActivity;
import com.chanta.myapplication.activity.StartActivity;
import com.chanta.myapplication.model.Meeting;
import com.chanta.myapplication.viewHolder.MeetingViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by chanta on 25.11.17.
 */

public abstract class MeetingListFragment extends Fragment {
    private static final String TAG = "MeetingListFragment";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Meeting, MeetingViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    public MeetingListFragment() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_list_meeting, container, false);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = rootView.findViewById(R.id.listMeetingView);
        mRecycler.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query meetingsQuery = getQuery(mDatabase);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Meeting>()
                .setQuery(meetingsQuery, Meeting.class)
                .build();

        mAdapter = new FirebaseRecyclerAdapter<Meeting, MeetingViewHolder>(options) {

            @Override
            public MeetingViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new MeetingViewHolder(inflater.inflate(R.layout.item_meeting, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(MeetingViewHolder viewHolder, int position, final Meeting model) {
                final DatabaseReference meetingRef = getRef(position);

                // Set click listener for the whole post view
                final String meetingKey = meetingRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch PostDetailActivity
                        Intent intent = new Intent(getActivity(), MeetingDetailActivity.class);
                        intent.putExtra(MeetingDetailActivity.EXTRA_MEETING_KEY, meetingKey);
                        startActivity(intent);
                    }
                });
                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                                final int positionToRemove = (Integer) view.getTag();
                                builder.setTitle(R.string.choice_delete)
                                        .setMessage(R.string.choice_delete_text)
                                        .setPositiveButton(R.string.choice_ok, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                DatabaseReference itemRef = getRef(position);
                                                itemRef.removeValue();
                                            }
                                        })
                                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog dialog = builder.create();
                                dialog.show();

                                return false;
                            }
                        });
                // Bind Post to ViewHolder, setting OnClickListener for the star button
                viewHolder.bindToMeeting(model);
            }
        };
        mRecycler.setAdapter(mAdapter);
    }


    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }

    public String getUid() {
        final FirebaseUser currentUser = getCurrentUser();
        if (currentUser != null)
            return currentUser.getUid();
        else {
            getActivity().startActivity(new Intent(this.getContext(), StartActivity.class));
            getActivity().finish();
            return null;
        }
    }

    public FirebaseUser getCurrentUser() {
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null)
            return currentUser;
        else {
            getActivity().startActivity(new Intent(this.getContext(), StartActivity.class));
            getActivity().finish();
            return null;
        }
    }

    public abstract Query getQuery(DatabaseReference databaseReference);

}
