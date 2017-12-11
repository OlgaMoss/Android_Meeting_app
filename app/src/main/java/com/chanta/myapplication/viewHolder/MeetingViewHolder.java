package com.chanta.myapplication.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chanta.myapplication.R;
import com.chanta.myapplication.model.Meeting;

/**
 * Created by chanta on 25.11.17.
 */

public class MeetingViewHolder extends RecyclerView.ViewHolder {
    public TextView nameTextView;
    public TextView describeTextView;

    public MeetingViewHolder(View itemView) {
        super(itemView);

        nameTextView = itemView.findViewById(R.id.meeting_name);
        describeTextView = itemView.findViewById(R.id.meeting_title);
    }

    public void bindToMeeting(Meeting meeting) {
        nameTextView.setText(meeting.getName());
        describeTextView.setText(meeting.getDescription());
    }

}