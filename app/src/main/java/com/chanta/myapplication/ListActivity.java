package com.chanta.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chanta.myapplication.entity.Meeting;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ListActivity extends AppCompatActivity {

    public static class MeetingViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView describeTextView;
//        private TextView toDateTextView;
////        private TextView toTimeTextView;
//        private TextView fromDateTextView;
////        private TextView fromTimeTextView;
//        private TextView participantsTextView;
//        private TextView priorityTextView;

        public MeetingViewHolder(View v) {
            super(v);

            nameTextView = (TextView) itemView.findViewById(R.id.name);
            describeTextView = (TextView) itemView.findViewById(R.id.description);
//            toDateTextView = (TextView) itemView.findViewById(R.id.to_date);
////            toTimeTextView = (TextView) itemView.findViewById(R.id.to_time_textView);
//            fromDateTextView = (TextView) itemView.findViewById(R.id.from_date);
////            fromTimeTextView = (TextView) itemView.findViewById(R.id.from_time_textView);
//            participantsTextView = (TextView) itemView.findViewById(R.id.participant);
//            priorityTextView = (TextView) itemView.findViewById(R.id.priority);

//            roomName = (TextView) itemView.findViewById(R.id.name);
//            roomAddress = (TextView) itemView.findViewById(R.id.address);
//            roomUrl = (TextView) itemView.findViewById(R.id.url);
        }


    }

    public static final String MEETINGS = "meetings";
    private static final String TAG = "ListActivity";
    private RecyclerView recyclerView;
    private SearchView searchView;
    private LinearLayoutManager mLinearLayoutManager;
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<Meeting, MeetingViewHolder> mFirebaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = (RecyclerView) findViewById(R.id.roomRecyclerView);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        final Intent intent = getIntent();
        if (intent.hasExtra("isNewAdd")) {
            Toast toast = Toast.makeText(getApplicationContext(), R.string.meetings_added, Toast.LENGTH_SHORT);
            toast.show();
            Meeting meeting = new Meeting();

            meeting.setName(intent.getStringExtra("name"));
            meeting.setDescription(intent.getStringExtra("describe"));
            meeting.setDateTo(intent.getStringExtra("dateTo"));
            meeting.setDateFrom(intent.getStringExtra("dateFrom"));
            meeting.setParticipant(intent.getStringExtra("participants"));
            meeting.setPriority(intent.getStringExtra("priority"));

            DatabaseReference newRef = mFirebaseDatabaseReference.child(MEETINGS).push();
            newRef.setValue(meeting);
        }

        if (intent.hasExtra("isChange")) {
            Meeting meeting = new Meeting();

            meeting.setName(intent.getStringExtra("name"));
            meeting.setDescription(intent.getStringExtra("describe"));
            meeting.setDateTo(intent.getStringExtra("dateTo"));
            meeting.setDateFrom(intent.getStringExtra("dateFrom"));
            meeting.setParticipant(intent.getStringExtra("participants"));
            meeting.setPriority(intent.getStringExtra("priority"));

            System.out.println("key " +intent.getStringExtra("key"));
            mFirebaseDatabaseReference.child(MEETINGS).child(intent.getStringExtra("key")).setValue(meeting);

//            runTransaction(new Transaction.Handler() {
//                @Override
//                public Transaction.Result doTransaction(MutableData mutableData) {
//                    Student s = mutableData.getValue(Student.class);
//                    if (s == null) {
//                        return Transaction.success(mutableData);
//                    }
//
//                    s.setAge(20);
//                    // Set value and report transaction success
//                    mutableData.setValue(s);
//                    return Transaction.success(mutableData);
//                }
//
//                @Override
//                public void onComplete(DatabaseError databaseError, boolean b,
//                                       DataSnapshot dataSnapshot) {
//                    // Transaction completed
//                    Log.d(TAG, "postTransaction:onComplete:" + databaseError);
//                }
//            });

        }


        mFirebaseAdapter = new FirebaseRecyclerAdapter<Meeting, MeetingViewHolder>(
                Meeting.class,
                R.layout.meetigs,
                MeetingViewHolder.class,
                mFirebaseDatabaseReference.child(MEETINGS)) {
            @Override
            protected void populateViewHolder(MeetingViewHolder viewHolder, final Meeting model, final int position) {
                viewHolder.nameTextView.setText(model.getName());
                viewHolder.describeTextView.setText(model.getDescription());
//                viewHolder.toDateTextView.setText(model.getDateTo());
//                viewHolder.fromDateTextView.setText(model.getDateFrom());
//                viewHolder.participantsTextView.setText(model.getParticipant());
//                viewHolder.priorityTextView.setText(model.getPriority());
                final String key = this.getRef(viewHolder.getAdapterPosition()).getKey();
                System.out.println("KEY "+ key);

                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
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
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getApplication(), DescribedActivity.class);
                        intent.putExtra("key", key);
                        intent.putExtra("name", model.getName());
                        intent.putExtra("description", model.getDescription());
                        intent.putExtra("toDate", model.getDateTo());
                        intent.putExtra("fromDate", model.getDateFrom());
                        intent.putExtra("participant", model.getParticipant());
                        intent.putExtra("priority", model.getPriority());
                        startActivity(intent);

                    }
                });
            }

        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int roomCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 || (positionStart >= (roomCount - 1) && lastVisiblePosition == (positionStart - 1))) {
                    recyclerView.scrollToPosition(positionStart);
                }
            }
        });
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(mFirebaseAdapter);


        searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Query query = mFirebaseDatabaseReference.child(MEETINGS).orderByChild("description").startAt(newText);
                query.keepSynced(true);
                mFirebaseAdapter = new FirebaseRecyclerAdapter<Meeting, MeetingViewHolder>(
                        Meeting.class,
                        R.layout.meetigs,
                        MeetingViewHolder.class,
                        query) {
                    @Override
                    protected void populateViewHolder(MeetingViewHolder viewHolder, final Meeting model, final int position) {
                        viewHolder.getAdapterPosition();
                        viewHolder.nameTextView.setText(model.getName());
                        viewHolder.describeTextView.setText(model.getDescription());

                        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
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
                        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(getApplication(), DescribedActivity.class);
                                intent.putExtra("name", model.getName());
                                intent.putExtra("description", model.getDescription());
                                intent.putExtra("toDate", model.getDateTo());
                                intent.putExtra("fromDate", model.getDateFrom());
                                intent.putExtra("participant", model.getParticipant());
                                intent.putExtra("priority", model.getPriority());
                                startActivity(intent);

                            }
                        });
                    }
                };
                recyclerView.setAdapter(mFirebaseAdapter);

//                    FirebaseRecyclerAdapter<Getting_Friends, Search.SearchViewHolder> firebaseRecyclerAdapter22 = new FirebaseRecyclerAdapter<Getting_Friends, Search.SearchViewHolder>(
//                        Getting_Friends.class, R.layout.my_friends_card, Search.SearchViewHolder.class, Q) {
//                    @Override
//                    protected void populateViewHolder(final Search.SearchViewHolder viewHolder, final Getting_Friends model, int position) {
//
//                        viewHolder.setUsername(model.getUsername());
//                        viewHolder.setProfile(getApplicationContext(), model.getProfile());
//
//                    }
//                };
//                mFriendsRecyclerView.setAdapter(firebaseRecyclerAdapter22);
//                mFirebaseDatabaseReference.child(MEETINGS).orderByChild("description").equalTo(newText).endAt("~");;
//                Toast toast = Toast.makeText(getApplicationContext(), "загрузилась", Toast.LENGTH_SHORT);
//                toast.show();
                return false;
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.standart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            Intent intent = new Intent(this, AddedActivity.class);
            intent.putExtra("isNew", "true");
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_update) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}