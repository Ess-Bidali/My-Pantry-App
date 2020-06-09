package com.essbidali.pantrywatcher.ui.homepagefragments.inventory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.essbidali.pantrywatcher.R;
import com.essbidali.pantrywatcher.businesslogic.InventoryProduct;
import com.essbidali.pantrywatcher.ui.ContextMenuDialogFragment;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.List;

public class InventoryAdapter extends ExpandableRecyclerViewAdapter<InventoryAdapter.ProductViewHolder, InventoryAdapter.ProductDetailViewHolder> {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    List<? extends ExpandableGroup> productList;
    Boolean selectionMode;
    int checkedProducts;

    InventoryAdapter(List<? extends ExpandableGroup> groups, Context context) {
        super(groups);
        InventoryAdapter.context = context;
        productList = groups;
        selectionMode = false;
        checkedProducts = 0;
    }

    public int getItems() {
        return productList.size();
    }

    static class ProductViewHolder extends GroupViewHolder{
        LinearLayout itemLinerLayout;
        TextView productName, itemNumberTextView;
        ProgressBar usageProgressBar;
        CheckBox itemCheckBox;
        Button contextMenuButton;

        ProductViewHolder(View itemView) {
            super(itemView);

            itemLinerLayout = itemView.findViewById(R.id.itemLinerLayout);
            itemNumberTextView = itemView.findViewById(R.id.itemNumberTextView);
            productName = itemView.findViewById(R.id.productNameTextView);
            usageProgressBar = itemView.findViewById(R.id.usageProgressBar);
            itemCheckBox = itemView.findViewById(R.id.itemCheckBox);
            contextMenuButton = itemView.findViewById(R.id.contextMenuButton);
        }
    }

    static class ProductDetailViewHolder extends ChildViewHolder {
        TextView valueTextView, keyTextView;
        Button learnMoreButton;

        ProductDetailViewHolder(View itemView) {
            super(itemView);
            valueTextView = itemView.findViewById(R.id.valueTextView);
            keyTextView = itemView.findViewById(R.id.keyTextView);
            learnMoreButton = itemView.findViewById(R.id.learnMoreButton);
            learnMoreButton.setVisibility(View.GONE);
        }
    }

    @Override
    public ProductViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.inventory_item_layout, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public ProductDetailViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.inventory_item_detail_layout, parent, false);
        return new ProductDetailViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(ProductDetailViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        InventoryProduct.ProductDetail detail = (InventoryProduct.ProductDetail) group.getItems().get(childIndex);
        holder.keyTextView.setText(detail.getName());
        holder.valueTextView.setText(detail.getValue());

        if (childIndex == group.getItemCount()-1){
            holder.learnMoreButton.setVisibility(View.VISIBLE);
        }
        else {
            holder.learnMoreButton.setVisibility(View.GONE);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindGroupViewHolder(final ProductViewHolder holder, int flatPosition, ExpandableGroup group) {

        final InventoryProduct product = (InventoryProduct)group;

        String itemNumber = flatPosition + 1 + ".";
        holder.itemNumberTextView.setText(itemNumber);
        holder.productName.setText(product.getName());
        holder.usageProgressBar.setProgress(product.getQuantity());
        holder.itemCheckBox.setVisibility(View.GONE);

        //Context menu activation mode
        holder.contextMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.isSelected = true;
                ContextMenuDialogFragment contextMenu = ContextMenuDialogFragment.newInstance ();
                FragmentManager fManager = ((AppCompatActivity)context).getSupportFragmentManager();
                contextMenu.show(fManager, "ContextMenuDialogFragment");
            }
        });

        //Checked change listener
        holder.itemCheckBox.setOnCheckedChangeListener(checkActionListener(holder, product));

        //Selection activation mode
        holder.itemLinerLayout.setOnLongClickListener(longClickListener(holder));

        if(selectionMode){
            holder.itemCheckBox.setVisibility(View.VISIBLE);
            holder.contextMenuButton.setVisibility(View.GONE);

            if(product.isSelected){
                holder.itemCheckBox.setChecked(true);
            } else{
                holder.itemCheckBox.setChecked(false);
            }
        } else {
            holder.itemCheckBox.setVisibility(View.GONE);
            holder.contextMenuButton.setVisibility(View.VISIBLE);
        }
        //Set color for progress bar!!!

    }

    private CompoundButton.OnCheckedChangeListener checkActionListener(final ProductViewHolder holder, final InventoryProduct product) {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkedProducts++;
                    holder.itemLinerLayout.setBackgroundColor(Color.LTGRAY);

                } else{
                    holder.itemLinerLayout.setBackgroundColor(Color.WHITE);
                    checkedProducts--;
                    if (checkedProducts == 0){
                        selectionMode = false;
                        notifyDataSetChanged();
                    }
                }
                product.setSelected(isChecked);
            }
        };
    }

    private View.OnLongClickListener longClickListener(final ProductViewHolder holder) {
        if(selectionMode){
            return null;
        }
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.itemCheckBox.setChecked(true);
                selectionMode = true;
                notifyDataSetChanged();
                return true;
            }
        };
    }


}
