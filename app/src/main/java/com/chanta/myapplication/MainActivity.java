package com.chanta.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> list;
    private ImageButton deleteBtn;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_translate);
        String[] values = getResources().getStringArray(R.array.list_meetings);
        list = new ArrayList<String>();
        Collections.addAll(list, values);

        adapter = new ArrayAdapter<String>(getBaseContext(),
                R.layout.list_for_meeting, R.id.txt, list);

        listView.setAdapter(adapter);

        deleteBtn = (ImageButton) findViewById(R.id.delete_imageButton);

        //instantiate custom adapter
        MyCustomAdapter adapter1 = new MyCustomAdapter(list, this);

        //handle listview and assign adapter
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

    }

//    public void onDeleteClick(View view) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//        final int positionToRemove = (Integer)view.ge;
//        builder.setTitle(R.string.choice_delete)
//                .setMessage(R.string.choice_delete_text)
//                .setPositiveButton(R.string.choice_ok, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        list.remove(positionToRemove);
//                        adapter.notifyDataSetChanged();
//                    }
//                })
//                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.standart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
