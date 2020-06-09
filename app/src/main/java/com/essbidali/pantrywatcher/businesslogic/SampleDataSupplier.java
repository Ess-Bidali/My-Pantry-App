package com.essbidali.pantrywatcher.businesslogic;

import com.essbidali.pantrywatcher.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SampleDataSupplier {

    public SampleDataSupplier(){
    }

    public List<InventoryProduct.ProductDetail> addGreenZoneProductDetails() {
        List<InventoryProduct.ProductDetail> greenZoneProductDetails = new ArrayList<>();
        greenZoneProductDetails.add(new InventoryProduct.ProductDetail("quantity", Integer.toString(80)));
        greenZoneProductDetails.add(new InventoryProduct.ProductDetail("date_stocked", new Date().toString()));
        greenZoneProductDetails.add(new InventoryProduct.ProductDetail("stocked_quantity", Integer.toString(10)));
        greenZoneProductDetails.add(new InventoryProduct.ProductDetail("units", "kg"));
        return greenZoneProductDetails;
    }

    public List<InventoryProduct.ProductDetail> addOrangeZoneProductDetails() {
        List<InventoryProduct.ProductDetail> orangeZoneProductDetails = new ArrayList<>();
        orangeZoneProductDetails.add(new InventoryProduct.ProductDetail("quantity", Integer.toString(40)));
        orangeZoneProductDetails.add(new InventoryProduct.ProductDetail("date_stocked", new Date().toString()));
        orangeZoneProductDetails.add(new InventoryProduct.ProductDetail("stocked_quantity", Integer.toString(10)));
        orangeZoneProductDetails.add(new InventoryProduct.ProductDetail("units", "kg"));
        return orangeZoneProductDetails;
    }

    public List<InventoryProduct.ProductDetail> addRedZoneProductDetails() {
        List<InventoryProduct.ProductDetail> redZoneProductDetails = new ArrayList<>();
        redZoneProductDetails.add(new InventoryProduct.ProductDetail("quantity", Integer.toString(20)));
        redZoneProductDetails.add(new InventoryProduct.ProductDetail("date_stocked", new Date().toString()));
        redZoneProductDetails.add(new InventoryProduct.ProductDetail("stocked_quantity", Integer.toString(10)));
        redZoneProductDetails.add(new InventoryProduct.ProductDetail("units", "kg"));
        return redZoneProductDetails;
    }

    public List<InventoryProduct> supplyInventoryProducts() {
        List<InventoryProduct> inventoryProducts = new ArrayList<>();
        inventoryProducts.add(new InventoryProduct("Wheat flour", addGreenZoneProductDetails()));
        inventoryProducts.add(new InventoryProduct("Rice", addOrangeZoneProductDetails()));
        inventoryProducts.add(new InventoryProduct("Diced beef", addRedZoneProductDetails()));
        return inventoryProducts;
    }

    public List<ProfileDetail> supplyUserDetails() {
        List<ProfileDetail> userDetails = new ArrayList<>();
        userDetails.add(new ProfileDetail("Name", "Ess's", "user"));
        userDetails.add(new ProfileDetail("Phone Number", "0712 356 678", "user"));
        userDetails.add(new ProfileDetail("Location", "Nairobi", "user"));
        return userDetails;
    }

    public List<ProfileDetail> supplyHouseholdDetails() {
        List<ProfileDetail> profileDetails = new ArrayList<>();
        profileDetails.add(new ProfileDetail("Name", "Ess's household", "household"));
        profileDetails.add(new ProfileDetail("Members", "6", "household"));
        profileDetails.add(new ProfileDetail("Location", "Nairobi", "household"));
        return profileDetails;
    }

    public List<Notification> supplyOldNotifications() {
        List<Notification> oldNotifications = new ArrayList<>();
        Notification noti = new Notification("Wheat flour was added to inventory", R.string.category_household);
        noti.setViewed(true);
        oldNotifications.add((noti));
        return oldNotifications;
    }

    public List<Notification> supplyNewNotifications() {
        List<Notification> newNotifications = new ArrayList<>();
        newNotifications.add(new Notification("Wheat flour is now in the green zone", R.string.category_inventory));
        newNotifications.add(new Notification("Rice is now in the orange zone", R.string.category_inventory));
        newNotifications.add(new Notification("Diced beef is now in the red zone!", R.string.category_shopping));
        return newNotifications;
    }

    public List<SummaryItem> supplySummaryItems() {
        List<SummaryItem> summaryItems = new ArrayList<>();
        summaryItems.add(new SummaryItem("Red zone items", 0));
        summaryItems.add(new SummaryItem("Orange zone items", 1));
        summaryItems.add(new SummaryItem("Shopping list items", 2));
        summaryItems.add(new SummaryItem("Other notifications", 3));
        return summaryItems;
    }
}
