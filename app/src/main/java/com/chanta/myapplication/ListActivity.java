package com.chanta.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.chanta.myapplication.model.Meeting;
import com.chanta.myapplication.model.Participant;
import com.chanta.myapplication.viewHolder.MeetingViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private static final String TAG = "ListActivity";

    public static final String MEETINGS = "meetings";

    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Meeting, MeetingViewHolder> mAdapter;
    private LinearLayoutManager mManager;

    private RecyclerView mRecycler;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        final Intent intent = getIntent();
//        if (intent.hasExtra("isNewAdd")) {
//            Toast toast = Toast.makeText(getApplicationContext(), R.string.meetings_added, Toast.LENGTH_SHORT);
//            toast.show();
//            Meeting meeting = new Meeting();
//
//            meeting.setName(intent.getStringExtra("name"));
//            meeting.setDescription(intent.getStringExtra("describe"));
//            meeting.setDateTo(intent.getStringExtra("dateTo"));
//            meeting.setDateFrom(intent.getStringExtra("dateFrom"));
//            meeting.setPriority(intent.getStringExtra("priority"));
//
//            ArrayList <Participant> participants = new ArrayList<>();
//            for (int i = 0; i < intent.getStringArrayListExtra("firstName").size(); i++) {
//                Participant participant = new Participant();
//                participant.setName(intent.getStringArrayListExtra("firstName").get(i));
//                participant.setLastName(intent.getStringArrayListExtra("lastName").get(i));
//                participant.setMiddle(intent.getStringArrayListExtra("middleName").get(i));
//                participant.setNumber(intent.getStringArrayListExtra("numbers").get(i));
//                participant.setPosition(intent.getStringArrayListExtra("position").get(i));
//                participants.add(participant);
//                mDatabase.child("participant").push().setValue(participant);
//
//            }
//
//            DatabaseReference newRef = mDatabase.child(MEETINGS).push();
//            newRef.setValue(meeting);
//            //todo сюда добавить для участника .child(Participant)
//        }

        if (intent.hasExtra("isChange")) {
            Meeting meeting = new Meeting();

            meeting.setName(intent.getStringExtra("name"));
            meeting.setDescription(intent.getStringExtra("describe"));
            meeting.setDateTo(intent.getStringExtra("dateTo"));
            meeting.setDateFrom(intent.getStringExtra("dateFrom"));
            meeting.setPriority(intent.getStringExtra("priority"));

            ArrayList <Participant> participants = new ArrayList<>();
            for (int i = 0; i < intent.getStringArrayListExtra("firstName").size(); i++) {
                Participant participant = new Participant();
                participant.setName(intent.getStringArrayListExtra("firstName").get(i));
                participant.setLastName(intent.getStringArrayListExtra("lastName").get(i));
                participant.setMiddle(intent.getStringArrayListExtra("middleName").get(i));
                participant.setNumber(intent.getStringArrayListExtra("numbers").get(i));
                participant.setPosition(intent.getStringArrayListExtra("position").get(i));
                participants.add(participant);
                mDatabase.child("participant").push().setValue(participant);
            }
//            meeting.setParticipant(participants);// FIXME: 25.11.17 fix structure

            System.out.println("key " +intent.getStringExtra("key"));
            mDatabase.child(MEETINGS).child(intent.getStringExtra("key")).setValue(meeting);
        }

        //сервис при старте системы
        //массив айдишников встреч
        //
        //
        //

        mRecycler = (RecyclerView) findViewById(R.id.listMeetingView);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mManager = new LinearLayoutManager(this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

//        mAdapter = new FirebaseRecyclerAdapter<Meeting, MeetingViewHolder>(
//                Meeting.class,
//                R.layout.meetigs,
//                MeetingViewHolder.class,
//                mDatabase.child(MEETINGS)) {
//            @Override
//            protected void populateViewHolder(MeetingViewHolder viewHolder, final Meeting model, final int position) {
//                viewHolder.nameTextView.setText(model.getName());
//                viewHolder.describeTextView.setText(model.getDescription());
//                final String key = this.getRef(viewHolder.getAdapterPosition()).getKey();//todo прописать сортировку по сегоднешней дате atStart
//                //todo logd
//
//                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View view) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
////                                final int positionToRemove = (Integer) view.getTag();
//                        builder.setTitle(R.string.choice_delete)
//                                .setMessage(R.string.choice_delete_text)
//                                .setPositiveButton(R.string.choice_ok, new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        DatabaseReference itemRef = getRef(position);
//                                        itemRef.removeValue();
//                                    }
//                                })
//                                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
//                                    }
//                                });
//
//                        AlertDialog dialog = builder.create();
//                        dialog.show();
//
//                        return false;
//                    }
//                });
//                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
////                        ArrayList<String> name = new ArrayList<>();
////                        ArrayList<String> middle = new ArrayList<>();
////                        ArrayList<String> lastName = new ArrayList<>();
////                        ArrayList<String> position = new ArrayList<>();
////                        ArrayList<String> number = new ArrayList<>();
////                        for (int i = 0; i < model.getParticipant().size(); i++) {
////                            name.add(model.getParticipant().get(i).getName());
////                            middle.add(model.getParticipant().get(i).getMiddle());
////                            lastName.add(model.getParticipant().get(i).getLastName());
////                            position.add(model.getParticipant().get(i).getPosition());
////                            number.add(model.getParticipant().get(i).getNumber());
////                        }
//                        //put serialized
//                        // FIXME: 25.11.17 fix structure
//
//                        Intent intent = new Intent(getApplication(), DescribedActivity.class);
//                        intent.putExtra("key", key);
//                        intent.putExtra("name", model.getName());
//                        intent.putExtra("description", model.getDescription());
//                        intent.putExtra("toDate", model.getDateTo());
//                        intent.putExtra("fromDate", model.getDateFrom());
////                        intent.putExtra("participant", model.getParticipant());
//                        intent.putExtra("priority", model.getPriority());
//
////                        intent.putStringArrayListExtra("firstName", name);
////                        intent.putStringArrayListExtra("lastName", lastName);
////                        intent.putStringArrayListExtra("middleName", middle);
////                        intent.putStringArrayListExtra("numbers", number);
////                        intent.putStringArrayListExtra("position", position);
//
//                        startActivity(intent);
//
//                    }
//                });
//            }

//        };

        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int roomCount = mAdapter.getItemCount();
                int lastVisiblePosition = mManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 || (positionStart >= (roomCount - 1) && lastVisiblePosition == (positionStart - 1))) {
                    mRecycler.scrollToPosition(positionStart);
                }
            }
        });
        mRecycler.setLayoutManager(mManager);
        mRecycler.setAdapter(mAdapter);


//        searchView = (SearchView) findViewById(R.id.search_view);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//                return false;
//            }

//            @Override
//            public boolean onQueryTextChange(String newText) {
//                Query query = mDatabase.child(MEETINGS).orderByChild("description").startAt(newText);
//                query.keepSynced(true);
//                mAdapter = new FirebaseRecyclerAdapter<Meeting, MeetingViewHolder>(
//                        Meeting.class,
//                        R.layout.meetigs,
//                        MeetingViewHolder.class,
//                        query) {
//                    @Override
//                    protected void populateViewHolder(MeetingViewHolder viewHolder, final Meeting model, final int position) {
//                        viewHolder.getAdapterPosition();
//                        viewHolder.nameTextView.setText(model.getName());
//                        viewHolder.describeTextView.setText(model.getDescription());
//
//                        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                            @Override
//                            public boolean onLongClick(View view) {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
////                                final int positionToRemove = (Integer) view.getTag();
//                                builder.setTitle(R.string.choice_delete)
//                                        .setMessage(R.string.choice_delete_text)
//                                        .setPositiveButton(R.string.choice_ok, new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                DatabaseReference itemRef = getRef(position);
//                                                itemRef.removeValue();
//                                            }
//                                        })
//                                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                dialog.cancel();
//                                            }
//                                        });
//
//                                AlertDialog dialog = builder.create();
//                                dialog.show();
//
//                                return false;
//                            }
//                        });
//                        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
////                                ArrayList<String> name = new ArrayList<>();
////                                ArrayList<String> middle = new ArrayList<>();
////                                ArrayList<String> lastName = new ArrayList<>();
////                                ArrayList<String> position = new ArrayList<>();
////                                ArrayList<String> number = new ArrayList<>();
////                                for (int i = 0; i < model.getParticipant().size(); i++) {
////                                    name.add(model.getParticipant().get(i).getName());
////                                    middle.add(model.getParticipant().get(i).getMiddle());
////                                    lastName.add(model.getParticipant().get(i).getLastName());
////                                    position.add(model.getParticipant().get(i).getPosition());
////                                    number.add(model.getParticipant().get(i).getNumber());
////                                }
////
////                                Intent intent = new Intent(getApplication(), DescribedActivity.class);
////                                intent.putExtra("name", model.getName());
////                                intent.putExtra("description", model.getDescription());
////                                intent.putExtra("toDate", model.getDateTo());
////                                intent.putExtra("fromDate", model.getDateFrom());
////                                intent.putExtra("priority", model.getPriority());
////
////                                intent.putStringArrayListExtra("firstName", name);
////                                intent.putStringArrayListExtra("lastName", lastName);
////                                intent.putStringArrayListExtra("middleName", middle);
////                                intent.putStringArrayListExtra("numbers", number);
////                                intent.putStringArrayListExtra("position", position);
//
//                                startActivity(intent);
//
//                            }
//                        });
//                    }
//                };
//                mRecycler.setAdapter(mAdapter);

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
//                return false;
//            }
   //      });

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

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

}