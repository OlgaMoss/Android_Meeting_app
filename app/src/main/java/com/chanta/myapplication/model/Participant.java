package com.chanta.myapplication.model;



import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by chanta on 06.11.17.
 */
@IgnoreExtraProperties
public class Participant implements Serializable {

    private String name;
    private String middle;
    private String lastName;
    private String position;
    private String number;
    private String email;

    public Participant(){

    }

    public Participant(String name, String middle, String lastName, String position, String number, String email) {
        this.name = name;
        this.middle = middle;
        this.lastName = lastName;
        this.position = position;
        this.number = number;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
