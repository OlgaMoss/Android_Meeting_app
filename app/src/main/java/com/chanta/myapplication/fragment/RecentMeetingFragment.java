package com.chanta.myapplication.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by chanta on 08.12.17.
 */

public class RecentMeetingFragment extends MeetingListFragment{

    public RecentMeetingFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        Query recentMeetingsQuery = databaseReference.child("meetings").limitToFirst(100);

        return recentMeetingsQuery;
    }
}
