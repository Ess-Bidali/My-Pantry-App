package com.essbidali.pantrywatcher.ui.homepagefragments.shoppinglist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.essbidali.pantrywatcher.R;
import com.essbidali.pantrywatcher.businesslogic.InventoryProduct;
import com.essbidali.pantrywatcher.businesslogic.SampleDataSupplier;
import com.essbidali.pantrywatcher.ui.DeleteDialogFragment;
import com.essbidali.pantrywatcher.ui.homepagefragments.MainSectionFragment;

import java.util.List;


public class ShoppingListFragment extends Fragment implements MainSectionFragment, DeleteDialogFragment.DialogListener {

    private List<InventoryProduct> shoppingProducts;
    RecyclerView listRecyclerView;
    ShoppingListAdapter adapter;
    TextView totalTextView;

    public ShoppingListFragment() {
        // Required empty public constructor
    }

    public static ShoppingListFragment newInstance(String param1, String param2) {
        ShoppingListFragment fragment = new ShoppingListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        //Hooks
        listRecyclerView = view.findViewById(R.id.listRecyclerView);
        totalTextView = view.findViewById(R.id.totalTextView);

        //Populate with data
        shoppingProducts = fetchItems();

        adapter = new ShoppingListAdapter(shoppingProducts, this.getContext(), this);
        listRecyclerView.setAdapter(adapter);
        listRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        String total = adapter.getItemCount() + "";
        totalTextView.setText(total);
        return view;
    }

    private List<InventoryProduct> fetchItems() {
        return new SampleDataSupplier().supplyInventoryProducts();
    }

    @Override
    public int getTitleId() {
        return R.string.shopping_list_page_title;
    }

    @Override
    public void onDialogPositiveClick(DeleteDialogFragment dialog) {
        int pos = dialog.getPosition();
        shoppingProducts.remove(pos);
        adapter.notifyDataSetChanged();
        String total = adapter.getItemCount() + "";
        totalTextView.setText(total);
        Toast.makeText(this.getContext(), "Item removed", Toast.LENGTH_SHORT).show();
    }
}
