package com.carcoop.helpme.Activity;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.carcoop.helpme.R;
import com.carcoop.helpme.fragments.HelpFragment;
import com.carcoop.helpme.fragments.HistoryFragment;
import com.carcoop.helpme.fragments.ProfileFragment;
import com.carcoop.helpme.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class Home extends BaseActivity {

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            item -> {
                switch (item.getItemId()) {
                    case R.id.help:
                        transact(new HelpFragment());
                        return true;
                    case R.id.profile:

                        transact(new ProfileFragment());

                        return true;
                    case R.id.history:
                        transact(new HistoryFragment());
                        return true;
                    case R.id.settings:
                        transact(new SettingsFragment());
                        return true;
                }
                return false;
            };

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.home);


        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        //Default Fragment
        transact(new HelpFragment());
    }

    private void transact(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentcontainer, fragment);

        ft.commit();
    }


}