package com.chanta.myapplication.entity;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by chanta on 06.11.17.
 */
@IgnoreExtraProperties
public class Participant {

    private String id;
    private String name;
    private String position;

    public Participant(){}

    public Participant(String id, String name, String position) {

        this.name = name;
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
