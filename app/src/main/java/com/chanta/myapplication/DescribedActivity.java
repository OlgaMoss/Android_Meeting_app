package com.chanta.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class DescribedActivity extends AppCompatActivity {


    private TextView nameTextView;
    private TextView describeTextView;
    private TextView toDateTextView;
    private TextView toTimeTextView;
    private TextView fromDateTextView;
    private TextView fromTimeTextView;
    private TextView participantsTextView;
    private TextView priorityTextView;

    private Button button;

    public DescribedActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_described);
        final Intent intent = getIntent();


        nameTextView = (TextView) findViewById(R.id.name_textView);
        describeTextView = (TextView) findViewById(R.id.describe_textView);
        toDateTextView = (TextView) findViewById(R.id.to_date_textView);
        toTimeTextView = (TextView) findViewById(R.id.to_time_textView);
        fromDateTextView = (TextView) findViewById(R.id.from_date_textView);
        fromTimeTextView = (TextView) findViewById(R.id.from_time_textView);
        participantsTextView = (TextView) findViewById(R.id.participants_textView);
        priorityTextView = (TextView) findViewById(R.id.priority_textView);

        nameTextView.setText(intent.getStringExtra("name"));
        describeTextView.setText(intent.getStringExtra("description"));
        toDateTextView.setText(intent.getStringExtra("toDate").split(" ")[0]);
        toTimeTextView.setText(intent.getStringExtra("toDate").split(" ")[1]);
        fromDateTextView.setText(intent.getStringExtra("fromDate").split(" ")[0]);
        fromTimeTextView.setText(intent.getStringExtra("fromDate").split(" ")[1]);
        participantsTextView.setText(intent.getStringExtra("participant"));
        priorityTextView.setText(intent.getStringExtra("priority"));

        button = (Button) findViewById(R.id.edit_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentNew = new Intent(getApplication(), AddedActivity.class);

                intentNew.putExtra("isNew", "false");

                intentNew.putExtra("name", intent.getStringExtra("name"));
                intentNew.putExtra("description", intent.getStringExtra("description"));
                intentNew.putExtra("toDate", intent.getStringExtra("toDate").split(" ")[0]);
                intentNew.putExtra("toTime", intent.getStringExtra("toDate").split(" ")[1]);
                intentNew.putExtra("fromDate", intent.getStringExtra("fromDate").split(" ")[0]);
                intentNew.putExtra("fromTime", intent.getStringExtra("fromDate").split(" ")[1]);
                intentNew.putExtra("participant", intent.getStringExtra("participant"));
                intentNew.putExtra("priority", intent.getStringExtra("priority"));
                startActivity(intentNew);
            }
        });

    }


}
