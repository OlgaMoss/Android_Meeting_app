package com.chanta.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chanta.myapplication.entity.Meeting;

/**
 * Created by chanta on 09.11.17.
 */

public class MeetingViewHolder extends RecyclerView.ViewHolder {

    public TextView nameView;
    public TextView describeView;
    public TextView toDateView;
    public TextView toTimeView;
    public TextView fromDateView;
    public TextView fromTimeView;
    public TextView participantsView;
    public TextView priorityView;


    public MeetingViewHolder(View itemView) {
        super(itemView);

        nameView = itemView.findViewById(R.id.name_textView);
        describeView = itemView.findViewById(R.id.describe_textView);
        toDateView = itemView.findViewById(R.id.to_date_textView);
        toTimeView = itemView.findViewById(R.id.to_time_textView);
        fromDateView = itemView.findViewById(R.id.from_date_textView);
        fromTimeView = itemView.findViewById(R.id.from_time_textView);
        participantsView = itemView.findViewById(R.id.participants_textView);
        priorityView = itemView.findViewById(R.id.priority_spinner);

    }

    public void bindToPost(Meeting meeting) {
//        nameView.setText(meeting.getName());
//        describeView.setText(meeting.getDescription());
////        toDateView.setText(meeting.getDateTo().get);
////        toTimeView.setText(meeting.getDateTo());
////        fromDateView.setText(meeting.getDateFrom().toString());
////        fromTimeView.setText(meeting.getDateFrom().toString());
//        participantsView.setText(meeting.getParticipant().getName());
//        priorityView.setText(meeting.getPriority().toString());



    }
}
