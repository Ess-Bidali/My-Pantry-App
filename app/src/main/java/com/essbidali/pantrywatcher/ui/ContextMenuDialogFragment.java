package com.essbidali.pantrywatcher.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.essbidali.pantrywatcher.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;

public class ContextMenuDialogFragment extends BottomSheetDialogFragment implements NavigationView.OnNavigationItemSelectedListener{
    private NavigationView contextNavigationView;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public ContextMenuDialogFragment() {
        // Required empty public constructor
    }


    public static ContextMenuDialogFragment newInstance() {
        ContextMenuDialogFragment fragment = new ContextMenuDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_context_menu_dialog, container, false);

        //Hooks
        contextNavigationView = view.findViewById(R.id.contextNavigationView);

        //Set listener
        contextNavigationView.setNavigationItemSelectedListener(this);

        return view;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        System.out.println("Context menu clicked");
        return false;
    }
}