package com.chanta.myapplication.fragment;

import android.content.Intent;

import com.chanta.myapplication.activity.StartActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;

/**
 * Created by chanta on 08.12.17.
 */

public class MyMeetingsListFragment extends MeetingListFragment {

    public MyMeetingsListFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        return databaseReference.child("participant-meetings").child(getUid());
    }


    @Override
    public String getUid() {
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null)
            return currentUser.getUid();
        else {
            startActivity(new Intent(this.getContext(), StartActivity.class));
            return null;
        }
    }
}
