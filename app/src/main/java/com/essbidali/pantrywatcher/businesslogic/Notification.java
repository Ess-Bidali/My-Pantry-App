package com.essbidali.pantrywatcher.businesslogic;

public class Notification {
    public boolean viewed;
    private String text;
    private int category;

    public Notification(String text, int notifCategory){
        this.text = text;
        this.category = notifCategory;
        viewed = false;
    }

    public String getText() {
        return text;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public int getCategory() {
        return category;
    }
}
