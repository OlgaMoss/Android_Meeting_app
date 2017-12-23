package com.chanta.myapplication.fragment;

import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;

import com.chanta.myapplication.model.Meeting;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.List;

/**
 * Created by chanta on 08.12.17.
 */

public class RecentMeetingFragment extends MeetingListFragment{

    private RecyclerView mRecycler;
    private List<Meeting> mFilteredMeetings;

    private SearchView searchView;

    public RecentMeetingFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {

//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        String strDate = sdf.format(c.getTime()).split(" ")[0];
//        Query recentMeetingsQuery = databaseReference.child("meetings").orderByChild("dateTo")
//                .startAt(strDate+"\uf8ff").endAt("").limitToFirst(100);

        Query recentMeetingsQuery = databaseReference.child("meetings").limitToFirst(100);

        return recentMeetingsQuery;
    }
}
