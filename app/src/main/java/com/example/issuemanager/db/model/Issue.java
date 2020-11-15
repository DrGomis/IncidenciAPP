package com.example.issuemanager.db.model;

public class Issue {
    protected String title;
    protected String priority;
    protected String id;

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
}
