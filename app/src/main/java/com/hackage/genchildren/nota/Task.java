package com.hackage.genchildren.nota;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Task extends RealmObject {
    @Required
    private String name;
    @Required
    private int icon;
    @Required
    private int fromId;
    @Required
    private int priority;
    @Required
    private boolean deadline;
    @Required
    private String content;
    private int time;
    private int data;
    //TODO more stuff

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

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
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

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
