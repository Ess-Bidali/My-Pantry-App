package com.essbidali.pantrywatcher.ui.homepagefragments.shoppinglist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.essbidali.pantrywatcher.R;
import com.essbidali.pantrywatcher.businesslogic.InventoryProduct;
import com.essbidali.pantrywatcher.ui.DeleteDialogFragment;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {

    List<InventoryProduct> inventoryProducts;
    Context context;
    DeleteDialogFragment.DialogListener listener;

    public ShoppingListAdapter(List<InventoryProduct> inventoryProducts, Context context, DeleteDialogFragment.DialogListener listener){
        this.inventoryProducts = inventoryProducts;
        this.context = context;
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView itemNumberTextView, shoppingItemTextView, quantityTextView, unitsTextView;
        Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemNumberTextView = itemView.findViewById(R.id.itemNumberTextView);
            shoppingItemTextView = itemView.findViewById(R.id.shoppingItemTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            unitsTextView = itemView.findViewById(R.id.unitsTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.shopping_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final InventoryProduct product = inventoryProducts.get(position);

        String itemNumber = position + 1 + ".";
        holder.itemNumberTextView.setText(itemNumber);
        holder.shoppingItemTextView.setText(product.getName());
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteDialogFragment deleteDialog = DeleteDialogFragment.newInstance (product.getName(), Integer.toString(position));
                deleteDialog.setMyListener(listener);
                FragmentManager fManager = ((AppCompatActivity)context).getSupportFragmentManager();
                deleteDialog.show(fManager, "EditDialogFragment");
            }
        });
        String last_stocked_amount = "10";
        String units = "packets";
        String quantity = last_stocked_amount + " ";
        holder.quantityTextView.setText(last_stocked_amount);
        holder.unitsTextView.setText(units);
    }

    @Override
    public int getItemCount() {
        return inventoryProducts.size();
    }


}
