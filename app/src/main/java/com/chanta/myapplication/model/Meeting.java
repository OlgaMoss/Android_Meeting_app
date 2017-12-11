package com.chanta.myapplication.entity;


import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chanta on 06.11.17.
 */

@IgnoreExtraProperties
public class Meeting  {

    private String name;
    private String description;
    private String dateTo;
    private String dateFrom;
    public Map<String, Boolean> participant = new HashMap<>();
    private String priority;

    public Meeting() {
    }

    public Meeting(String name, String description, String dateTo, String dateFrom, Map<String, Boolean> participant, String priority) {
        this.name = name;
        this.description = description;
        this.dateTo = dateTo;
        this.dateFrom = dateFrom;
        this.participant = participant;
        this.priority = priority;
    }

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

    public Map<String, Boolean> getParticipant() {
        return participant;
    }

    public void setParticipant(Map<String, Boolean> participant) {
        this.participant = participant;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("description", description);
        result.put("dateTo", dateTo);
        result.put("dateFrom", dateFrom);
        result.put("participant", participant);
        result.put("priority", priority);

        return result;
    }
}
