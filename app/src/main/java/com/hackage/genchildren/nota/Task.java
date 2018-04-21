package com.hackage.genchildren.nota;

import io.realm.RealmObject;

public class Task extends RealmObject {
    private String name;
    
    private int icon;
    
    private String fromId;
    
    private int priority;
    
    private boolean deadline;
    
    private String content;
    private int time;
    private int date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isDeadline() {
        return deadline;
    }

    public void setDeadline(boolean deadline) {
        this.deadline = deadline;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}
