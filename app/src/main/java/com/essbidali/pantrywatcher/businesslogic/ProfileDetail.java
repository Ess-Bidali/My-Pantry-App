package com.essbidali.pantrywatcher.businesslogic;

public class ProfileDetail {
    private String title, value, type;

    public ProfileDetail(String title, String value, String type){
        this.title = title;
        this.value = value;
        this.type = type;

    }

    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
