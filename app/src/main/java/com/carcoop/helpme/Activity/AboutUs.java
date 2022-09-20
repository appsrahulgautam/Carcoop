package com.carcoop.helpme.Activity;

import android.os.Bundle;
import android.view.View;

import com.carcoop.helpme.R;

public class AboutUs extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_about_us);
    }

    public void backpress(View view) {
        finish();
    }
}