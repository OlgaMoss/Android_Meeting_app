package com.chanta.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class AddedActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private TextView txtDateTo, txtTimeTo, txtDateFrom, txtTimeFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added);

        txtDateTo = (TextView) findViewById(R.id.to_date_textView);
        txtTimeTo = (TextView) findViewById(R.id.to_time_textView);
        txtDateFrom = (TextView) findViewById(R.id.from_date_textView);
        txtTimeFrom = (TextView) findViewById(R.id.from_time_textView);

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
