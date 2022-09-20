package com.carcoop.helpme.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.carcoop.helpme.R;
import com.carcoop.helpme.pojos.UserDetailsPojo;
import com.carcoop.helpme.utils.SharedPreferenceManager;

public class ProfileFragment extends Fragment {

    private TextView carRegnumber, carbrand, cardmodel;
    private TextView name;


    public ProfileFragment() {
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initviews(view);
        setAllviewdetails();
    }


    //init all views
    private void initviews(View view) {
        carRegnumber = view.findViewById(R.id.carRegnumber);
        carbrand = view.findViewById(R.id.carbrand);
        cardmodel = view.findViewById(R.id.cardmodel);
        name = view.findViewById(R.id.name);
    }

    //set all field detail
    private void setAllviewdetails() {

        UserDetailsPojo userDetailsPojo = SharedPreferenceManager.getInstance().getUserDetails();

        name.setText(userDetailsPojo.getFullname());
        carRegnumber.setText(userDetailsPojo.getCarRegNumber());
        carbrand.setText(userDetailsPojo.getCarBrandName());
        cardmodel.setText(userDetailsPojo.getCarModel());

    }

}