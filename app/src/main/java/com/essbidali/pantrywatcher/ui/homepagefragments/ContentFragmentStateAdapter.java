package com.essbidali.pantrywatcher.ui.homepagefragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class ContentFragmentStateAdapter extends FragmentStateAdapter {

    private List<MainSectionFragment> content = new ArrayList<>();

    public ContentFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public void addMainSectionFragment(MainSectionFragment fragment){
        content.add(fragment);
    }

    public int getTitle(int position){
        return content.get(position).getTitleId();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return (Fragment)content.get(position);
    }

    @Override
    public int getItemCount() {
        return content.size();
    }
}
