package com.chanta.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chanta.myapplication.activity.StartActivity;

public class RegistrationActivity extends AppCompatActivity {


    private EditText firstNameEtxt;
    private EditText nameEtxt;
    private EditText middleNameEtxt;
    private EditText numbersEtxt;
    private EditText positionEtxt;

    private EditText loginEtxt;
    private EditText passwordEtxt;

    private Button regBtn;
    private Button canselBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firstNameEtxt = (EditText) findViewById(R.id.edit_first_name);
        nameEtxt = (EditText) findViewById(R.id.edit_last_name);
        middleNameEtxt = (EditText) findViewById(R.id.edit_middle_name);
        numbersEtxt = (EditText) findViewById(R.id.edit_numbers);
        positionEtxt = (EditText) findViewById(R.id.edit_position);
        loginEtxt = (EditText) findViewById(R.id.edit_login);
        passwordEtxt = (EditText) findViewById(R.id.edit_passwords);

        regBtn = (Button) findViewById(R.id.button_signIn);
        canselBtn = (Button) findViewById(R.id.button_cansel_signIn);

        canselBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(intent);
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(intent);
            }
        });

    }
}
