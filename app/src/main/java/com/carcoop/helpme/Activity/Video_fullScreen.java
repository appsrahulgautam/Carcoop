package com.carcoop.helpme.Activity;

import static com.carcoop.helpme.Constance.CLICK_VIDEO_POSITION;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;

import androidx.viewpager2.widget.ViewPager2;

import com.carcoop.helpme.Constance;
import com.carcoop.helpme.R;
import com.carcoop.helpme.adapters.VideoViewpagerAdapter;
import com.carcoop.helpme.utils.DepthPageTransformer;

import java.util.ArrayList;

public class Video_fullScreen extends BaseActivity {

    private ViewPager2 videoPager;
    private VideoViewpagerAdapter videoViewpagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_video_full_screen);
        fetchdatafromintent();
        initviews();

    }

    private void fetchdatafromintent() {


    }


    private void initviews() {
        videoPager = findViewById(R.id.imagePager);
        intiviewpager();
    }

    private void intiviewpager() {
        videoPager.setPageTransformer(new DepthPageTransformer());

        videoViewpagerAdapter = new VideoViewpagerAdapter(this, (ArrayList<String>) getIntent().getSerializableExtra(Constance.VIDEO_ARRAY), videoPager);
        videoPager.setAdapter(videoViewpagerAdapter);

        videoPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                videoPager.setCurrentItem(getIntent().getIntExtra(CLICK_VIDEO_POSITION, 0), true);
                videoPager.getViewTreeObserver().removeOnGlobalLayoutListener(this::onGlobalLayout);
            }
        });

//        Log.e("sushilfile ", "intiviewpager: "+videoPager.getCurrentItem() );

        videoPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("pagersushil", "onPageScrolled:position " + position);

                videoViewpagerAdapter.notified(position);
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.e("pagersushil", "onPageSelected:position " + position);
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("pagersushil", "onPageScrollStateChanged:state " + state);
                super.onPageScrollStateChanged(state);
            }
        });

    }


}