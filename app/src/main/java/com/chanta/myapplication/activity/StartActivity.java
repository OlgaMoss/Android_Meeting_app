package com.chanta.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chanta.myapplication.R;
import com.chanta.myapplication.model.Participant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StartActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "StartActivity";
    private EditText login, password;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        login = findViewById(R.id.login_editText);
        password = findViewById(R.id.password_editText);

        findViewById(R.id.enter_button).setOnClickListener(this);
        findViewById(R.id.registration_button).setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            onAuthSuccess(mAuth.getCurrentUser());
        }
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());

        writeNewUser(user.getUid(), username, user.getEmail());

        startActivity(new Intent(StartActivity.this, MainActivity.class));
        finish();
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(login.getText().toString())) {
            login.setError(BaseActivity.REQUIRED_ERROR);
            result = false;
        } else {
            login.setError(null);
        }

        if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError(BaseActivity.REQUIRED_ERROR);
            result = false;
        } else {
            password.setError(null);
        }

        return result;
    }

    private void writeNewUser(String userId, String name, String email) {
        Participant user = new Participant(name,"","","","", email);
        mDatabase.child("participants").child(userId).setValue(user);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.enter_button) {
            signIn(login.getText().toString(), password.getText().toString());
        } else if (view.getId() == R.id.registration_button) {
            registration(login.getText().toString(), password.getText().toString());
        }
    }

    public void signIn(String login, String password) {
        Log.d(TAG, "signIn");
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        mAuth.signInWithEmailAndPassword(login, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideProgressDialog();
                Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
                if (task.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.signIn, Toast.LENGTH_SHORT);
                    toast.show();
                    onAuthSuccess(task.getResult().getUser());
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.signOut, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    public void registration(String login, String password) {
        Log.d(TAG, "signUp");
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        mAuth.createUserWithEmailAndPassword(login, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                hideProgressDialog();
                if (task.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.registrationIn, Toast.LENGTH_SHORT);
                    toast.show();
                    onAuthSuccess(task.getResult().getUser());
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.registrationOut, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}
