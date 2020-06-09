package com.essbidali.pantrywatcher.ui;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.essbidali.pantrywatcher.R;
import com.essbidali.pantrywatcher.businesslogic.ProfileDetail;
import com.essbidali.pantrywatcher.businesslogic.SampleDataSupplier;

import java.util.List;

public class UserProfileActivity extends BaseActivityWithNav {
    List<ProfileDetail> userDetails;
    public ProfileDetailAdapter adapter;
    RecyclerView detailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        setCurrentActivity(this.getClass(), R.id.sideNavProfile);

        //Hooks
        detailView = findViewById(R.id.detailRecyclerView);

        //Populate list with data
        userDetails= fetchDetails();

        //Set adapter and layout to display items in recycler view
        adapter = new ProfileDetailAdapter(userDetails, this);
        detailView.setAdapter(adapter);
        detailView.setLayoutManager(new LinearLayoutManager(this));

    }

    List<ProfileDetail> fetchDetails(){
        return new SampleDataSupplier().supplyUserDetails();
    }

}
