package com.chanta.myapplication.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by chanta on 08.12.17.
 */

public class BaseActivity extends AppCompatActivity {

    public static final String REQUIRED_ERROR = "Required";
    private ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading...");
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public String getUid() {
        final FirebaseUser currentUser = getCurrentUser();
        if (currentUser != null)
            return currentUser.getUid();
        else {
            startActivity(new Intent(this, StartActivity.class));
            finish();
            return null;
        }
    }

    public FirebaseUser getCurrentUser() {
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null)
            return currentUser;
        else {
            startActivity(new Intent(this, StartActivity.class));
            finish();
            return null;
        }
    }

}
