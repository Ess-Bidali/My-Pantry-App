package com.essbidali.pantrywatcher.businesslogic;

import android.os.Parcel;
import android.os.Parcelable;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class InventoryProduct extends ExpandableGroup<InventoryProduct.ProductDetail> {
    private String name;
    private int quantity;
    public boolean isSelected;


    public InventoryProduct(String productName, List<ProductDetail> details){
        super(productName, details);
        this.name = productName;
        for(ProductDetail d: details){
            if(d.getName().equals("quantity")){
                this.quantity = Integer.parseInt(d.getValue());
            }
        }
        isSelected = false;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setSelected(Boolean isSelected){
        this.isSelected = isSelected;
    }

    @Override
    public String toString(){
        return getTitle();
    }

    public static class ProductDetail implements Parcelable {
        private String name, value;

        public ProductDetail(String name, String value){
            this.name = name;
            this.value = value;
        }

        protected ProductDetail(Parcel in) {
            name = in.readString();
            value = in.readString();
        }

        public static final Creator<ProductDetail> CREATOR = new Creator<ProductDetail>() {
            @Override
            public ProductDetail createFromParcel(Parcel in) {
                return new ProductDetail(in);
            }

            @Override
            public ProductDetail[] newArray(int size) {
                return new ProductDetail[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(value);
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }
    }
}
