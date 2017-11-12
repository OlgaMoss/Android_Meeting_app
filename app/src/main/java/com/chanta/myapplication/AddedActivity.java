package com.chanta.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;

public class AddedActivity extends AppCompatActivity implements View.OnClickListener {

    private int mYear, mMonth, mDay, mHour, mMinute;
    private TextView txtDateTo;
    private TextView txtTimeTo;
    private TextView txtDateFrom;
    private TextView txtTimeFrom;

    private EditText nameEditText;
    private EditText describeEditText;
    private EditText participantsEditText;
    private TextView participantsTextView;
    private Spinner prioritySpinner;

    private String dateTo;
    private String dateFrom;

    private String name;
    private String describe;
    private String participants;
    private String priority;


    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added);

        nameEditText = (EditText) findViewById(R.id.name_editText);
        describeEditText = (EditText) findViewById(R.id.describe_editText);
        txtDateTo = (TextView) findViewById(R.id.to_date_textView);
        txtTimeTo = (TextView) findViewById(R.id.to_time_textView);
        txtDateFrom = (TextView) findViewById(R.id.from_date_textView);
        txtTimeFrom = (TextView) findViewById(R.id.from_time_textView);
        participantsTextView = (TextView) findViewById(R.id.participants_textView);
        participantsEditText = (EditText) findViewById(R.id.participant_editText);
        prioritySpinner = (Spinner) findViewById(R.id.priority_spinner);

        button = (Button) findViewById(R.id.added_button);

        final Intent intent = getIntent();
        final Intent intentNew = getIntent();

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
                    intentNew.putExtra("participants", participantsEditText.getText().toString());
                    intentNew.putExtra("priority", prioritySpinner.getSelectedItem().toString());

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
            participantsTextView.setText(intent.getStringExtra("participant"));

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
                    intentNew.putExtra("participants", participantsEditText.getText().toString());
                    intentNew.putExtra("priority", prioritySpinner.getSelectedItem().toString());

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
