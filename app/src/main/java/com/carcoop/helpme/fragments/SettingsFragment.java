package com.carcoop.helpme.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.carcoop.helpme.Activity.AboutUs;
import com.carcoop.helpme.Activity.Edit_Deatails;
import com.carcoop.helpme.R;
import com.carcoop.helpme.utils.SharedPreferenceManager;


public class SettingsFragment extends Fragment {


    private RelativeLayout profilesetting, aboutussetting;
    private TextView editprofile_tv, cleardata_tv, findlocaiton_tv;
    private TextView email_TV;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intiviews(view);
        onclicks();
    }

    @Override
    public void onStart() {
        super.onStart();
//        setdata();
    }

    /**
     * set data if have email
     */
    private void setdata() {
        String email = SharedPreferenceManager.getInstance().getEmail_detail();

        if (TextUtils.isEmpty(email)) {
            email_TV.setVisibility(View.GONE);
        } else {
            email_TV.setVisibility(View.VISIBLE);
            email_TV.setText(email);
        }

    }


    private void intiviews(View view) {

        profilesetting = view.findViewById(R.id.profilesetting);
        aboutussetting = view.findViewById(R.id.aboutussetting);
        editprofile_tv = view.findViewById(R.id.editprofile_tv);
//        cleardata_tv = view.findViewById(R.id.cleardata_tv);
//        findlocaiton_tv = view.findViewById(R.id.findlocaiton_tv);
        email_TV = view.findViewById(R.id.email_TV);

    }


    private void onclicks() {


        editprofile_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Edit_Deatails.class);
                getContext().startActivity(intent);
            }
        });


        aboutussetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AboutUs.class);
                getContext().startActivity(intent);
            }
        });
    }


}