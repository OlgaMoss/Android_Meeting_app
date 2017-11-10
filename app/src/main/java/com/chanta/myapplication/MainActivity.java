package com.chanta.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chanta.myapplication.entity.Meeting;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> list;
    private ImageButton deleteBtn;
    private ArrayAdapter<String> adapter;
    private String[] values;


    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView describeTextView;
//        private TextView toDateTextView;
////        private TextView toTimeTextView;
//        private TextView fromDateTextView;
////        private TextView fromTimeTextView;
//        private TextView participantsTextView;
//        private TextView priorityTextView;

        public RoomViewHolder(View v) {
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


//    public static class MessageViewHolder extends RecyclerView.ViewHolder {
//        TextView messageTextView;
//        ImageView messageImageView;
//        TextView messengerTextView;
//
//        public MessageViewHolder(View v) {
//            super(v);
//            messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
//            messageImageView = (ImageView) itemView.findViewById(R.id.messageImageView);
//            messengerTextView = (TextView) itemView.findViewById(R.id.messengerTextView);
//        }
//    }

    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_translate);
        //values = getResources().getStringArray(R.array.list_meetings);
        list = new ArrayList<String>();
        Collections.addAll(list, values);

        adapter = new ArrayAdapter<String>(getBaseContext(),
                R.layout.list_for_meeting, R.id.txt, list);

        listView.setAdapter(adapter);

        deleteBtn = (ImageButton) findViewById(R.id.delete_imageButton);

        MyCustomAdapter adapter1 = new MyCustomAdapter(list, this);
        ListView lView = (ListView) findViewById(R.id.list_translate);
        lView.setAdapter(adapter1);


//        deleteBtn = new setOnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                final int positionToRemove = (Integer)view.getTag();
//                builder.setTitle(R.string.choice_delete)
//                        .setMessage(R.string.choice_delete_text)
//                        .setPositiveButton(R.string.choice_ok, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                list.remove(positionToRemove);
//                                adapter.notifyDataSetChanged();
//                            }
//                        })
//                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.cancel();
//                            }
//                        });
//
//                AlertDialog dialog = builder.create();
//                dialog.show();
//            }
//        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (view.getId() == R.id.delete_imageButton) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    final int positionToRemove = (Integer) view.getTag();
                    builder.setTitle(R.string.choice_delete)
                            .setMessage(R.string.choice_delete_text)
                            .setPositiveButton(R.string.choice_ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    list.remove(positionToRemove);
                                    adapter.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    Intent intent = new Intent(MainActivity.this, DescribedActivity.class);
                    startActivity(intent);
                }
            }
        });


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
    }


    //
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
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_update) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
