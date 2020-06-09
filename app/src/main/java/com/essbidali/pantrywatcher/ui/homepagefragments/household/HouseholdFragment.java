package com.essbidali.pantrywatcher.ui.homepagefragments.household;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.essbidali.pantrywatcher.R;
import com.essbidali.pantrywatcher.businesslogic.ProfileDetail;
import com.essbidali.pantrywatcher.businesslogic.SampleDataSupplier;
import com.essbidali.pantrywatcher.ui.ProfileDetailAdapter;
import com.essbidali.pantrywatcher.ui.homepagefragments.MainSectionFragment;

import java.util.List;

public class HouseholdFragment extends Fragment implements MainSectionFragment{
    List<ProfileDetail> details;
    public ProfileDetailAdapter adapter;
    RecyclerView detailView;

    public HouseholdFragment() {
        // Required empty public constructor
    }

    @Override
    public int getTitleId(){
        return R.string.household_item;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_household, null);
        //Hooks
        detailView = view.findViewById(R.id.detailRecyclerView);

        //Populate list with data
        details = fetchDetails();

        //Set adapter and layout to display items in recycler view
        adapter = new ProfileDetailAdapter(details, getActivity());
        detailView.setAdapter(adapter);
        detailView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;
    }

    List<ProfileDetail> fetchDetails(){
        return new SampleDataSupplier().supplyHouseholdDetails();
    }


}
