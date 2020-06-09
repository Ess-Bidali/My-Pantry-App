package com.essbidali.pantrywatcher.ui.homepagefragments.inventory;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.essbidali.pantrywatcher.R;
import com.essbidali.pantrywatcher.businesslogic.InventoryProduct;
import com.essbidali.pantrywatcher.businesslogic.SampleDataSupplier;
import com.essbidali.pantrywatcher.ui.homepagefragments.MainSectionFragment;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class InventoryFragment extends Fragment implements MainSectionFragment {

    private TextView totalTextView;
    private RecyclerView inventoryRecyclerView;
    private Chip redZoneChip, orangeZoneChip, greenZoneChip;
    private List<InventoryProduct> displayedProducts;
    private Map<Integer, ArrayList<InventoryProduct>> allProducts;
    private String total;
    List<Integer> filter;
    InventoryAdapter adapter;
    Integer redZoneCategory = R.string.red_zone_category;
    Integer orangeZoneCategory = R.string.orange_zone_category;
    Integer greenZoneCategory = R.string.green_zone_category;
    ColorStateList original;


    public InventoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);

        //Hooks
        totalTextView = view.findViewById(R.id.totalTextView);
        inventoryRecyclerView = view.findViewById(R.id.inventoryRecyclerView);
        redZoneChip = view.findViewById(R.id.redZoneChip);
        orangeZoneChip = view.findViewById(R.id.orangeZoneChip);
        greenZoneChip = view.findViewById(R.id.greenZoneChip);

        //Initialize variables
        initializeVariables();

        //Initialize arrayList and products
        addProducts();

        //Set up filters and their listeners
        original = redZoneChip.getChipBackgroundColor();
        redZoneChip.setOnCheckedChangeListener(listener());
        orangeZoneChip.setOnCheckedChangeListener(listener());
        greenZoneChip.setOnCheckedChangeListener(listener());
        //Apply filters
        redZoneChip.setChecked(true);
        orangeZoneChip.setChecked(true);
        greenZoneChip.setChecked(true);

        //Initialize adapter with data and set up recycler view
        //Adapter
        adapter = new InventoryAdapter(displayedProducts, this.getContext());
        inventoryRecyclerView.setAdapter(adapter);
        inventoryRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        total = adapter.getItems() + "";
        totalTextView.setText(total);

        return view;
    }

    //Initialize all collection variables in one place
    void initializeVariables(){
        //Filter list
        filter = new ArrayList<>();
        //Products visible in recyclerview
        displayedProducts = new ArrayList<>();
        //Record of all inventory products, categorized into 3
        allProducts = new HashMap<>();
        //Add categories
        allProducts.put(redZoneCategory, new ArrayList<InventoryProduct>());
        allProducts.put(orangeZoneCategory, new ArrayList<InventoryProduct>());
        allProducts.put(greenZoneCategory, new ArrayList<InventoryProduct>());

    }

    //Fetch inventory products and categorize them
    void addProducts(){
        //List of products with Product details for the three categories(red, orange, greenzone;
        List<InventoryProduct> productsWithDetails = new SampleDataSupplier().supplyInventoryProducts();

        categorize(productsWithDetails);

    }

    void categorize(List<InventoryProduct> list){
        for (InventoryProduct product: list){
            int redZoneLimit = 30;
            int orangeZoneLimit = 70;
            if (product.getQuantity() > orangeZoneLimit){
                Objects.requireNonNull(allProducts.get(greenZoneCategory)).add(product);
            } else if(product.getQuantity() > redZoneLimit){
                Objects.requireNonNull(allProducts.get(orangeZoneCategory)).add(product);
            } else{
                Objects.requireNonNull(allProducts.get(redZoneCategory)).add(product);
            }
        }
    }

    public void filterProducts(){
        displayedProducts.clear();
        for(Integer category: allProducts.keySet()){
            for (Integer filtr : filter){
                if(filtr.equals(category)){
                    displayedProducts.addAll(Objects.requireNonNull(allProducts.get(category)));
                }
            }
        }
        if(adapter != null){
            adapter.notifyDataSetChanged();
            total = adapter.getItems() + "";
            totalTextView.setText(total);
        }
    }

    private CompoundButton.OnCheckedChangeListener listener(){
        return new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (redZoneChip.equals(buttonView)) {
                    if(isChecked){
                        filter.add(redZoneCategory);
                    } else{
                        filter.remove(redZoneCategory);
                    }
                } else if (orangeZoneChip.equals(buttonView)) {
                    if(isChecked){
                        filter.add(orangeZoneCategory);
                    } else{
                        filter.remove(orangeZoneCategory);
                    }

                } else if (greenZoneChip.equals(buttonView)) {
                    if(isChecked){
                        filter.add(greenZoneCategory);
                    } else{
                        filter.remove(greenZoneCategory);
                    }
                } else {
                    filter.clear();
                }
                filterProducts();
            }
        };
    }

    @Override
    public int getTitleId() {
        return R.string.inventory_page_title;
    }
}
