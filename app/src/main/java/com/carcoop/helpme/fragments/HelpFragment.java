package com.carcoop.helpme.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.carcoop.helpme.Activity.GetLocationOfUser;
import com.carcoop.helpme.Constance;
import com.carcoop.helpme.R;
import com.carcoop.helpme.utils.SharedPreferenceManager;


public class HelpFragment extends Fragment {

    private static final String TAG = "HelpFragment";
    private static final int CALL_REQUEST_CODE = 901;
    //    private ConstraintLayout maincontainer;
    private static final String ROADSIDEASSIST_CALL = "1800651111";
    private static final String CARCOOP_CALL = "0499898983";
    private static final String EMERGENCY_NUMBER = "000";
    public String callno = "";
    private TextView helpbtn;
    private ImageView roadsideassist, carcoophelp_iv, emgNumber000;


    public HelpFragment() {

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
        Log.e(TAG, "onCreateView: " + SharedPreferenceManager.getInstance().getUserLocations().getLng() + " country-> " + SharedPreferenceManager.getInstance().getUserLocations().getCountry());

        return inflater.inflate(R.layout.fragment_help_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initviews(view);
        onclickinit();
    }

    //    inti all views
    private void initviews(View view) {
//        helpbtn = view.findViewById(R.id.helpbtn);
//        maincontainer = view.findViewById(R.id.maincontainer);
        helpbtn = view.findViewById(R.id.helpbtn);
        roadsideassist = view.findViewById(R.id.roadsideassist);
        carcoophelp_iv = view.findViewById(R.id.carcoophelp_iv);
        emgNumber000 = view.findViewById(R.id.emgNumber000);
    }


    //    init on click
    private void onclickinit() {

        helpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(getContext(), Emergency_activity.class);
//                getContext().startActivity(intent);
                Intent intent = new Intent(getContext(), GetLocationOfUser.class);
                intent.putExtra(Constance.SAVE_IN_PREF, false);
                getContext().startActivity(intent);

            }
        });

        roadsideassist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                call_Empergency(ROADSIDEASSIST_CALL);
                callno = ROADSIDEASSIST_CALL;
                onCallBtnClick(callno);
            }
        });

        carcoophelp_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                call_Empergency(CARCOOP_CALL);
                callno = CARCOOP_CALL;
                onCallBtnClick(callno);
            }
        });

        emgNumber000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callno = EMERGENCY_NUMBER;
                onCallBtnClick(callno);
            }
        });

    }

    private void onCallBtnClick(String number) {
        call_Empergency(number);
    }

    private void call_Empergency(String number) {
       try{
           Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null));
           startActivity(intent);
       }catch (Exception e ){
           Toast.makeText(getActivity(),"Unable to find app to handle call",Toast.LENGTH_LONG).show();
       }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean permissionGranted = false;
        Log.e(TAG, "onRequestPermissionsResult: requestcode-> " + requestCode);
        switch (requestCode) {
            case CALL_REQUEST_CODE:
                permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (permissionGranted) {
            call_Empergency(callno);
        } else {
            Toast.makeText(getActivity(), "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }


}







