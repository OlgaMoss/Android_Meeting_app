package com.chanta.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chanta.myapplication.R;
import com.chanta.myapplication.model.Participant;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddedParticipantActivity extends BaseActivity {

    private static final String TAG = "AddedParticipantAct";

    private EditText firstNameEtxt;
    private EditText lastNameEtxt;
    private EditText middleNameEtxt;
    private EditText numbersEtxt;
    private EditText positionEtxt;
    private EditText emailEtxt;

    private Button addBtn;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_participant);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        firstNameEtxt = findViewById(R.id.first_name);
        lastNameEtxt = findViewById(R.id.last_name);
        middleNameEtxt = findViewById(R.id.middle_name);
        numbersEtxt = findViewById(R.id.numbers);
        positionEtxt = findViewById(R.id.position);
        emailEtxt = findViewById(R.id.email);

        addBtn = findViewById(R.id.button_added_participant);
        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final Participant participant = new Participant();
                participant.setName(firstNameEtxt.getText().toString());
                participant.setMiddle(middleNameEtxt.getText().toString());
                participant.setLastName(lastNameEtxt.getText().toString());
                participant.setNumber(numbersEtxt.getText().toString());
                participant.setPosition(positionEtxt.getText().toString());
                participant.setEmail(emailEtxt.getText().toString());

                if (participant == null) {
                    Log.e(TAG, "Participant is unexpectedly null");
                    return;
                }

                final String participateId = mDatabase.child("meetings").push().getKey();

                Map<String, Object> participateValues = participant.toMap();
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/participants/" + participateId, participateValues);

                mDatabase.updateChildren(childUpdates);

                Intent intent = new Intent(getApplicationContext(), NewMeetingActivity.class);
                intent.putExtra("participateId", participateId);
                startActivity(intent);
            }
        });


    }

}
