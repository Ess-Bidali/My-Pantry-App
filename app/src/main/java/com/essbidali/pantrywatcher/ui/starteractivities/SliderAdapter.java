package com.essbidali.pantrywatcher.ui.starteractivities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.essbidali.pantrywatcher.R;

public class SliderAdapter extends PagerAdapter {

    private Context context;

    private int[] images = {
            R.drawable.select,
            R.drawable.monitor,
            R.drawable.shopping,
            R.drawable.review};

    private int[] titles = {
            R.string.first_slide_title,
            R.string.second_slide_title,
            R.string.third_slide_title,
            R.string.fourth_slide_title};

    private int[] descriptions = {
            R.string.first_slide_desc,
            R.string.second_slide_desc,
            R.string.third_slide_desc,
            R.string.fourth_slide_desc};

    SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        //Hooks
        ImageView sliderImageView = view.findViewById(R.id.sliderImageView);
        TextView sliderHeadingTextView = view.findViewById(R.id.sliderHeadingTextView);
        TextView sliderDescTextView = view.findViewById(R.id.sliderDescTextView);

        sliderImageView.setImageResource(images[position]);
        sliderHeadingTextView.setText(titles[position]);
        sliderDescTextView.setText(descriptions[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }

}
