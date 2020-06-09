package com.essbidali.pantrywatcher.ui;

import android.os.Bundle;
import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

import com.essbidali.pantrywatcher.R;
import com.essbidali.pantrywatcher.businesslogic.SampleDataSupplier;
import com.essbidali.pantrywatcher.ui.homepagefragments.ContentFragmentStateAdapter;
import com.essbidali.pantrywatcher.ui.homepagefragments.home.HomeFragment;
import com.essbidali.pantrywatcher.ui.homepagefragments.household.HouseholdFragment;
import com.essbidali.pantrywatcher.ui.homepagefragments.inventory.InventoryFragment;
import com.essbidali.pantrywatcher.ui.homepagefragments.notifications.NotificationsFragment;
import com.essbidali.pantrywatcher.ui.homepagefragments.shoppinglist.ShoppingListFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class MainActivity extends BaseActivityWithNav {
    //Variables
    ViewPager2 contentViewPager;
    ContentFragmentStateAdapter fragmentStateAdapter;
    SampleDataSupplier supplier;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setCurrentActivity(this.getClass(), R.id.sideNavHome);

        //Hooks
        contentViewPager = findViewById(R.id.contentViewPager);
        tabs.setVisibility(View.VISIBLE);

        //Initialize data source
        supplier = new SampleDataSupplier();

        //Add fragments
        contentViewPager.setOffscreenPageLimit(4);
        fragmentStateAdapter = new ContentFragmentStateAdapter(this);
        fragmentStateAdapter.addMainSectionFragment(new HomeFragment());
        fragmentStateAdapter.addMainSectionFragment(new InventoryFragment());
        fragmentStateAdapter.addMainSectionFragment(new ShoppingListFragment());
        fragmentStateAdapter.addMainSectionFragment(new NotificationsFragment());
        fragmentStateAdapter.addMainSectionFragment(new HouseholdFragment());
        //Note: wrapping viewPager in a constraint layout helped remove view pager height issues in longer fragments
        contentViewPager.setAdapter(fragmentStateAdapter);
        contentViewPager.registerOnPageChangeCallback(coordinateTabs());
        tabs.addOnTabSelectedListener(tabSelectedListener());
    }


    //Update tabs when page is scrolled
    public ViewPager2.OnPageChangeCallback coordinateTabs(){
        return new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                tabs.setScrollPosition(position, positionOffset, true);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabs.selectTab(tabs.getTabAt(position));
                setPageTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        };
    }

    //Set page title to reflect the selected tab
    public void setPageTitle(int position){
        int title = fragmentStateAdapter.getTitle(position);
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
    }

    //Update ViewPager2 when respective tab is selected
    public TabLayout.OnTabSelectedListener tabSelectedListener(){
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int currentTab = tabs.getSelectedTabPosition();
                contentViewPager.setCurrentItem(currentTab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        };
    }

    public SampleDataSupplier getSupplier() {
        return supplier;
    }
}
