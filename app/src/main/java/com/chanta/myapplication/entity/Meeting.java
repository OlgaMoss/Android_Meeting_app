package com.chanta.myapplication.entity;


import com.chanta.myapplication.entity.utilsEntity.Priority;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Calendar;

/**
 * Created by chanta on 06.11.17.
 */

@IgnoreExtraProperties
public class Meeting {

//    private String id;
    private String name;
    private String description;
    private String dateTo;
    private String dateFrom;
    private String participant;
    private String priority;

//    private Calendar dateTo;
//    private Calendar dateFrom;
//    private Participant participant;
//    private Priority priority;

    public Meeting() {}

    public Meeting(String name, String description, String dateTo, String dateFrom, String participant, String priority) {
        this.name = name;
        this.description = description;
        this.dateTo = dateTo;
        this.dateFrom = dateFrom;
        this.participant = participant;
        this.priority = priority;
    }

    //    public Meeting(String id, String name, String description, Calendar dateTo, Calendar dateFrom, Participant participant, Priority priority) {
//        this.name = name;
//        this.description = description;
//        this.dateTo = dateTo;
//        this.dateFrom = dateFrom;
//        this.participant = participant;
//        this.priority = priority;
//    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public Calendar getDateTo() {
//        return dateTo;
//    }
//
//    public void setDateTo(Calendar dateTo) {
//        this.dateTo = dateTo;
//    }
//
//    public Calendar getDateFrom() {
//        return dateFrom;
//    }
//
//    public void setDateFrom(Calendar dateFrom) {
//        this.dateFrom = dateFrom;
//    }
//
//    public Participant getParticipant() {
//        return participant;
//    }
//
//    public void setParticipant(Participant participant) {
//        this.participant = participant;
//    }
//
//    public Priority getPriority() {
//        return priority;
//    }
//
//    public void setPriority(Priority priority) {
//        this.priority = priority;
//    }


    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
