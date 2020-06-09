package com.essbidali.pantrywatcher.businesslogic;

public class SummaryItem {
    private int quantity;
    private String title;

    public SummaryItem(String title, int quantity){
        this.quantity = quantity;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
