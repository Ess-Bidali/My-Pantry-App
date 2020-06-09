package com.essbidali.pantrywatcher.ui.starteractivities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.essbidali.pantrywatcher.R;
import com.essbidali.pantrywatcher.ui.authentication.Register;

public class OnBoardingActivity extends AppCompatActivity {

    ViewPager sliderViewPager;
    LinearLayout linearLayout;
    Button getStartedButton, nextButton, skipButton;
    SliderAdapter sliderAdapter;
    TextView[] dots;
    int currentPos;
    Animation animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_on_boarding);


        //Hooks
        sliderViewPager = findViewById(R.id.sliderViewPager);
        linearLayout = findViewById(R.id.linearLayout);
        getStartedButton = findViewById(R.id.getStartedButton);
        nextButton = findViewById(R.id.nextButton);
        animation = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        animation.setDuration(1000);

        sliderAdapter = new SliderAdapter(this);
        sliderViewPager.setAdapter(sliderAdapter);

        addDots(0);
        sliderViewPager.addOnPageChangeListener(pageChangeListener());

    }

    public void addDots(int position){
        dots = new TextView[4];
        linearLayout.removeAllViewsInLayout();

        for(int i = 0; i < dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(32);
            final int thisPage = i;
            dots[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sliderViewPager.setCurrentItem(thisPage, true);
                }
            });
            linearLayout.addView(dots[i]);
        }

        if(dots.length > 0){
            setCurrentPageDot(0);
        }
    }

    public void setCurrentPageDot(int position){
        if (dots.length > 0){
            ColorStateList oldColours = dots[position].getTextColors();
            for (int i = 0; i < dots.length; i++){
                if(i == position){
                    dots[i].setTextColor(getResources().getColor(R.color.colorText));
                    continue;
                }
                dots[i].setTextColor(oldColours);
            }
        }
    }

    public ViewPager.OnPageChangeListener pageChangeListener(){
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPos = position;
                setCurrentPageDot(position);

                if (position == dots.length -1){
                    getStartedButton.setVisibility(View.VISIBLE);
                    getStartedButton.setAnimation(animation);
                    nextButton.setVisibility(View.GONE);
                }
                else{
                    if (getStartedButton.getVisibility() == View.VISIBLE){
                        getStartedButton.setVisibility(View.GONE);
                        nextButton.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
    }

    public void skip(View view){
        startActivity(new Intent(OnBoardingActivity.this, Register.class));
        finish();
    }

    public void next(View view){
        sliderViewPager.setCurrentItem(currentPos + 1, true);
    }

}
