package com.chanta.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddedParticipantActivity extends AppCompatActivity {
    private EditText firstNameEtxt;
    private EditText lastNameEtxt;
    private EditText middleNameEtxt;
    private EditText numbersEtxt;
    private EditText positionEtxt;


    private Button addBtn;
    private Button cancelBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_participant);

        firstNameEtxt = (EditText) findViewById(R.id.first_name);
        lastNameEtxt = (EditText) findViewById(R.id.last_name);
        middleNameEtxt = (EditText) findViewById(R.id.middle_name);
        numbersEtxt = (EditText) findViewById(R.id.numbers);
        positionEtxt = (EditText) findViewById(R.id.position);

        addBtn = (Button) findViewById(R.id.button_added_participant);
        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddedActivity.class);
                intent.putExtra("newParticipant", true);
                intent.putExtra("isNew", "true");
                intent.putExtra("firstName", firstNameEtxt.getText().toString());
                intent.putExtra("lastName", lastNameEtxt.getText().toString());
                intent.putExtra("middleName", middleNameEtxt.getText().toString());
                intent.putExtra("numbers", numbersEtxt.getText().toString());
                intent.putExtra("position", positionEtxt.getText().toString());

                startActivity(intent);
            }
        });

        cancelBtn = (Button) findViewById(R.id.button_cancel_added);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddedActivity.class);
                startActivity(intent);
            }
        });

    }

}
