package com.chanta.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class AddedActivity extends AppCompatActivity implements View.OnClickListener {

    private int mYear, mMonth, mDay, mHour, mMinute;
    private TextView txtDateTo;
    private TextView txtTimeTo;
    private TextView txtDateFrom;
    private TextView txtTimeFrom;

    private EditText nameEditText;
    private EditText describeEditText;
    private ListView participantslistView;
    private TextView participantsTextView;
    private Spinner prioritySpinner;

    private String dateTo;
    private String dateFrom;

    private String name;
    private String describe;
    private String participants;
    private String priority;

    private ArrayList<String> firstName;
    private ArrayList<String> lastName;
    private ArrayList<String> middleName;
    private ArrayList<String> number;
    private ArrayList<String> position;

    private Button button;
    private ArrayList<String> liste;
    private ArrayAdapter<String> adapter;
    private Button btnParticipantAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added);

        firstName = new ArrayList<>();
        lastName = new ArrayList<>();
        middleName = new ArrayList<>();
        number = new ArrayList<>();
        position = new ArrayList<>();


        nameEditText = (EditText) findViewById(R.id.name_editText);
        describeEditText = (EditText) findViewById(R.id.describe_editText);
        txtDateTo = (TextView) findViewById(R.id.to_date_textView);
        txtTimeTo = (TextView) findViewById(R.id.to_time_textView);
        txtDateFrom = (TextView) findViewById(R.id.from_date_textView);
        txtTimeFrom = (TextView) findViewById(R.id.from_time_textView);
        prioritySpinner = (Spinner) findViewById(R.id.priority_spinner);

        button = (Button) findViewById(R.id.added_button);
        btnParticipantAdd = (Button) findViewById(R.id.add_button);

        final Intent intent = getIntent();

        participantslistView = (ListView) findViewById(R.id.participants_textView);
        ArrayList<String> values = new ArrayList<>();

        if (intent.getStringExtra("isNew").contains("false")) {
            firstName.addAll(intent.getStringArrayListExtra("firstName"));
            lastName.addAll(intent.getStringArrayListExtra("lastName"));
            middleName.addAll(intent.getStringArrayListExtra("middleName"));
            number.addAll(intent.getStringArrayListExtra("numbers"));
            position.addAll(intent.getStringArrayListExtra("position"));
            System.out.println("false is new");
        }

        if (intent.hasExtra("newParticipant")) {
            Intent intent1 = getIntent();
            firstName.add(intent1.getStringExtra("firstName"));
            lastName.add(intent1.getStringExtra("lastName"));
            middleName.add(intent1.getStringExtra("middleName"));
            number.add(intent1.getStringExtra("numbers"));
            position.add(intent1.getStringExtra("position"));
            values.addAll(lastName);
        }
        liste = new ArrayList<String>();

        liste.addAll(values);
        System.out.println("liste " + liste.toString());
        adapter = new ArrayAdapter<String>(getApplication(),
                R.layout.list_for_partision, R.id.nameParticipate, liste);

        participantslistView.setAdapter(adapter);
        participantslistView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddedActivity.this);
                final int positionToRemove = pos;
                builder.setTitle(R.string.choice_delete)
                        .setMessage(R.string.choice_delete_text)
                        .setPositiveButton(R.string.choice_ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                liste.remove(positionToRemove);
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
                return true;
            }
        });
        btnParticipantAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), AddedParticipantActivity.class);
                startActivity(intent);
            }
        });


        if (intent.getStringExtra("isNew").contains("true")) {

            nameEditText.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View viewForKey, int keyCode, KeyEvent keyEvent) {
                    if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN)
                            && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                        name = nameEditText.getText().toString();
//                        ScrollView scrollView = (ScrollView) view.findViewById(R.id.scrollViewLanguage);
//                        scrollView.setVisibility(View.VISIBLE);
//                        InputMethodManager manager = (InputMethodManager) getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
//                        manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
//                    Toast.makeText(getContext(), editText.getText(), Toast.LENGTH_LONG).show();
                        return true;
                    }
                    return false;
                }
            });

            describeEditText.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View viewForKey, int keyCode, KeyEvent keyEvent) {
                    if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN)
                            && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                        describe = describeEditText.getText().toString();
                        return true;
                    }
                    return false;
                }
            });


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentNew = new Intent(getApplication(), ListActivity.class);
                    intentNew.putExtra("isNewAdd", true);

                    intentNew.putExtra("name", nameEditText.getText().toString());
                    intentNew.putExtra("describe", describeEditText.getText().toString());
                    intentNew.putExtra("dateTo", txtDateTo.getText().toString() + " " + txtTimeTo.getText().toString());
                    intentNew.putExtra("dateFrom", txtDateFrom.getText().toString() + " " + txtTimeFrom.getText().toString());
//                    intentNew.putExtra("participants", participantsEditText.getText().toString());
                    intentNew.putExtra("priority", prioritySpinner.getSelectedItem().toString());

                    intentNew.putStringArrayListExtra("firstName", firstName);
                    intentNew.putStringArrayListExtra("lastName", lastName);
                    intentNew.putStringArrayListExtra("middleName", middleName);
                    intentNew.putStringArrayListExtra("numbers", number);
                    intentNew.putStringArrayListExtra("position", position);

                    startActivity(intentNew);
                }
            });


//            nameEditText = (TextView) findViewById(R.id.name_textView);
//            describeEditText = (TextView) findViewById(R.id.describe_textView);
//            txtDateTo = (TextView) findViewById(R.id.to_date_textView);
//            txtTimeTo = (TextView) findViewById(R.id.to_time_textView);
//            txtDateFrom = (TextView) findViewById(R.id.from_date_textView);
//            txtTimeFrom = (TextView) findViewById(R.id.from_time_textView);
//            participantsTextView = (TextView) findViewById(R.id.participants_textView);
//            priorityTextView = (TextView) findViewById(R.id.priority_textView);


            // priorityTextView.setText(intent.getStringExtra("priority"));

        } else {

            button.setText("Изменить");


            nameEditText.setHint(intent.getStringExtra("name"));
            describeEditText.setHint(intent.getStringExtra("description"));
            txtDateTo.setText(intent.getStringExtra("toDate"));
            txtTimeTo.setText(intent.getStringExtra("toTime"));
            txtDateFrom.setText(intent.getStringExtra("fromDate"));
            txtTimeFrom.setText(intent.getStringExtra("fromTime"));
//            participantsTextView.setText(intent.getStringExtra("participant"));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intentNew = new Intent(getApplication(), ListActivity.class);
                    intentNew.putExtra("isChange", true);

                    intentNew.putExtra("key", intent.getStringExtra("key"));
                    intentNew.putExtra("name", nameEditText.getText().toString());
                    intentNew.putExtra("describe", describeEditText.getText().toString());
                    intentNew.putExtra("dateTo", txtDateTo.getText().toString() + " " + txtTimeTo.getText().toString());
                    intentNew.putExtra("dateFrom", txtDateFrom.getText().toString() + " " + txtTimeFrom.getText().toString());
//                    intentNew.putExtra("participants", participantsEditText.getText().toString());
                    intentNew.putExtra("priority", prioritySpinner.getSelectedItem().toString());

                    intentNew.putStringArrayListExtra("firstName", firstName);
                    intentNew.putStringArrayListExtra("lastName", lastName);
                    intentNew.putStringArrayListExtra("middleName", middleName);
                    intentNew.putStringArrayListExtra("numbers", number);
                    intentNew.putStringArrayListExtra("position", position);


//                    intentNew.putExtra("name", nameEditText.getText().toString());
//                    intentNew.putExtra("describe", describeEditText.getText().toString());
//                    intentNew.putExtra("dateTo", txtDateTo.getText().toString());
//                    intentNew.putExtra("timeTo", txtTimeTo.getText().toString());
//                    intentNew.putExtra("dateFrom", txtDateFrom.getText().toString());
//                    intentNew.putExtra("timeFrom",  txtTimeFrom.getText().toString());
//                    intentNew.putExtra("participants", participantsEditText.getText().toString());
//                    intentNew.putExtra("priority", prioritySpinner.getSelectedItem().toString());

                    startActivity(intentNew);
                }
            });


//            txtDateTo = (TextView) findViewById(R.id.to_date_textView);
//            txtTimeTo = (TextView) findViewById(R.id.to_time_textView);
//            txtDateFrom = (TextView) findViewById(R.id.from_date_textView);
//            txtTimeFrom = (TextView) findViewById(R.id.from_time_textView);
        }

//        spinner.getSelectedItem().toString().

    }

    @Override
    public void onClick(final View v) {

        if (v == txtDateTo || v == txtDateFrom) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            final DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            if (v == txtDateTo) {
                                txtDateTo.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            } else {
                                txtDateFrom.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == txtTimeTo || v == txtTimeFrom) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            if (v == txtTimeTo) {
                                txtTimeTo.setText(hourOfDay + ":" + minute);
                            } else {
                                txtTimeFrom.setText(hourOfDay + ":" + minute);

                            }
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

}
