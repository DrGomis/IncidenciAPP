package com.example.issuemanager.db.model;

import android.content.Context;

import com.example.issuemanager.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Issue {
    protected String title;
    protected String priority;
    protected String id;

    protected int statusID;

    protected String desc;
    protected long unixDate;



    public Issue(String title, String priority){
        this.title = title;
        this.priority = priority;
    }

    public String getTitle(){
        return title;
    }

    public String getPriority(){
        return priority;
    }

    public String getID(){
        return id;
    }

    public void setID(int id){
        this.id = "JDA-" + id;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getUnixDate() {
        return this.unixDate;
    }

    public void setUnixDate(long unixDate) {
        this.unixDate = unixDate;
    }

    public String getDate() {
        Date date = new java.util.Date(this.unixDate * 1000);
        String formatDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date);

        return formatDate;
    }
}
