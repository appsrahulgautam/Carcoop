package com.carcoop.helpme.Activity;

import static com.carcoop.helpme.Constance.CLICK_IMAGE_POSITION;
import static com.carcoop.helpme.Constance.IMAGE_ARRAY;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;

import androidx.viewpager2.widget.ViewPager2;

import com.carcoop.helpme.R;
import com.carcoop.helpme.adapters.ImageViewpagerAdapter2;
import com.carcoop.helpme.utils.DepthPageTransformer;

import java.util.ArrayList;


public class Image_fullScreen extends BaseActivity {

    private ViewPager2 imagePager;
    //    private ImagesViewpagerAdapter imageViewpagerAdapter;
    private ImageViewpagerAdapter2 imageViewpagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_image_full_screen);
        fetchdatafromintent();
        initviews();
    }

    private void fetchdatafromintent() {


    }

    private void initviews() {
        imagePager = findViewById(R.id.imagePager);
        intiviewpager();
    }

    private void intiviewpager() {

        imagePager.setPageTransformer(new DepthPageTransformer());


        imageViewpagerAdapter = new ImageViewpagerAdapter2(this, (ArrayList<String>) getIntent().getSerializableExtra(IMAGE_ARRAY));
        imageViewpagerAdapter.initposition(getIntent().getIntExtra(CLICK_IMAGE_POSITION, 0));
        Log.e("imageviewpagerAdapter2 ", "intiviewpager: " + getIntent().getIntExtra(CLICK_IMAGE_POSITION, 0));

        imagePager.setAdapter(imageViewpagerAdapter);

        imagePager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imagePager.setCurrentItem(getIntent().getIntExtra(CLICK_IMAGE_POSITION, 0), true);
            }
        });

    }
}