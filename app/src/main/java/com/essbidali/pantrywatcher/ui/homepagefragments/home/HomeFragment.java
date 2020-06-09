package com.essbidali.pantrywatcher.ui.homepagefragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.essbidali.pantrywatcher.R;
import com.essbidali.pantrywatcher.businesslogic.SampleDataSupplier;
import com.essbidali.pantrywatcher.businesslogic.SummaryItem;
import com.essbidali.pantrywatcher.ui.MainActivity;
import com.essbidali.pantrywatcher.ui.homepagefragments.MainSectionFragment;

import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment implements MainSectionFragment {
    private List<SummaryItem> summaryItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Hooks
        RecyclerView summaryRecyclerView = view.findViewById(R.id.summaryRecyclerView);

        //Add items and display in recyclerView
        summaryItems = fetchSummaryItems();
        SummaryAdapter adapter = new SummaryAdapter(summaryItems);
        summaryRecyclerView.setAdapter(adapter);
        summaryRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

    List<SummaryItem> fetchSummaryItems(){
        SampleDataSupplier supplier = ((MainActivity) Objects.requireNonNull(getActivity())).getSupplier();
        return supplier.supplySummaryItems();
    }

    @Override
    public int getTitleId() {
        return R.string.home_page_title;
    }
}
