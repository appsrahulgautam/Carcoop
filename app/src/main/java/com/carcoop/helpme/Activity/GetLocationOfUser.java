package com.carcoop.helpme.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.carcoop.helpme.Constance;
import com.carcoop.helpme.R;
import com.carcoop.helpme.pojos.UserLocationPojo;
import com.carcoop.helpme.utils.GPSTracker;
import com.carcoop.helpme.utils.SharedPreferenceManager;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

/**
 * Created by Sushil 1-13-2021
 */
public class GetLocationOfUser extends BaseActivity {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 201;
    private static final String TAG = "GetLocationOfUser";
    private static final int REQUESTCODE_TURNON_GPS = 1290;
    private static final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private GPSTracker gpsTracker;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


//    private BroadcastReceiver gpsReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            if (intent.getAction().matches(LocationManager.PROVIDERS_CHANGED_ACTION)) {
//               locationfetch();
//                Log.e("locationBroadcast", "onReceive: " );
//            }
//        }
//    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.getuserlocation);

        checkGPS();

        Log.e(TAG, "onCreate:GetLocationOf User ");
    }

    @Override
    protected void onStart() {
        super.onStart();


//        registerReceiver(gpsReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
//        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(LocationOnOff_Similar_To_Google_Maps.this)) {
//            Log.e("keshav","Gps already enabled");
//            Toast.makeText(LocationOnOff_Similar_To_Google_Maps.this,"Gps not enabled",Toast.LENGTH_SHORT).show();
//            enableLoc();
//        }else{
//            Log.e("keshav","Gps already enabled");
//            Toast.makeText(LocationOnOff_Similar_To_Google_Maps.this,"Gps already enabled",Toast.LENGTH_SHORT).show();
//        }


    }


    private void locationfetch() {
//        mainThreadHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
        checklocation();
//            }
//        }, 1000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gpsTracker != null) {

            gpsTracker.stopUsingGPS();
            gpsTracker = null;
        }

//        unregisterReceiver(gpsReceiver);
    }

    private void checklocation() {

        gpsTracker = new GPSTracker(GetLocationOfUser.this);
        gpsTracker.getlocationobject();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            mainThreadHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
            locationfinding();
//                }
//            }, 1000);
        }

    }


    //checking for location permission and setting
    private void locationfinding() {

        Log.e("locationBroadcast", "locationfinding: " + gpsTracker);
        if (gpsTracker.canGetLocation()) {

            if (!checkforGPS()) {
                Log.e("locationBroadcast", "locationfinding: check data change");
                gpsTracker.showSettingsAlert();
            } else if (gpsTracker != null) {
                getlocationfetch();
            } else {
                checklocation();

            }

        } else {
            Log.e(TAG, "locationfinding: can");
        }


    }

    //fetch location and save it in sharepreference
    private void getlocationfetch() {
        Log.e(TAG, "getlocation: getaddressline--> " + gpsTracker.getAddressLine(this) +
                "\n country-> " + gpsTracker.getCountryName(this) +
                "\n locality-> " + gpsTracker.getLocality(this) +
                "\n  postalCode-> " + gpsTracker.getPostalCode(this) +
                "\n latitude-> " + gpsTracker.getLatitude() +
                "\n longitude-> " + gpsTracker.getLongitude() +
                "\n getgeocodeAddress->" + gpsTracker.getGeocoderAddress(this));

        boolean saveinshared = getIntent().getBooleanExtra(Constance.SAVE_IN_PREF, true);

        if (gpsTracker.getLatitude() != 0.0 && gpsTracker.getLongitude() != 0.0) {

            UserLocationPojo userLocationPojo = new UserLocationPojo();
            userLocationPojo.setLat(gpsTracker.getLatitude());
            userLocationPojo.setLng(gpsTracker.getLongitude());
            userLocationPojo.setCountry(gpsTracker.getCountryName(this));
            userLocationPojo.setAddressBaseline(gpsTracker.getAddressLine(this));


            Log.e(TAG, "getlocationfetch:saveinshared->  " + saveinshared);
            if (saveinshared) {
                SaveLocationtoSharedpref(userLocationPojo);
                startActivity(new Intent(GetLocationOfUser.this, Home.class));
                finish();
            } else {
                SaveLocationtoSharedpref(userLocationPojo);
                Intent intent = new Intent(this, Emergency_activity.class);
                intent.putExtra(Constance.SEND_LOCATION_DETAIL, userLocationPojo);
                startActivity(intent);
                finish();
            }

        } else {

//            if (saveinshared) {
//                UserLocationPojo userLocationPojo = new UserLocationPojo();
//                userLocationPojo.setLat(gpsTracker.getLatitude());
//                userLocationPojo.setLng(gpsTracker.getLongitude());
//                userLocationPojo.setCountry(gpsTracker.getCountryName(this));
//                userLocationPojo.setAddressBaseline(gpsTracker.getAddressLine(this));
//                SaveLocationtoSharedpref(userLocationPojo);
//                startActivity(new Intent(GetLocationOfUser.this, Home.class));
//                finish();
//            } else
//            if (!saveinshared) {
//                Intent intent = new Intent(this, Emergency_activity.class);
//                intent.putExtra(Constance.SEND_LOCATION_DETAIL, getLocationFromSharedPref());
//                startActivity(intent);
//                finish();
//            }
            checklocation();

        }


    }

    //Save location to Shared pref
    private void SaveLocationtoSharedpref(UserLocationPojo userLocationPojo) {

        SharedPreferenceManager.getInstance().saveUserLocations(userLocationPojo);

    }

    private UserLocationPojo getLocationFromSharedPref() {
        return SharedPreferenceManager.getInstance().getUserLocations();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.e("GPS_main", "onRequestPermissionsResult: request code-> " + requestCode);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                Log.e(TAG, "onRequestPermissionsResult: " + grantResults.length + " location permission -> " +
                        grantResults[0] + "  permission 2->  " + grantResults[1]);
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED ||
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    Log.e(TAG, "onRequestPermissionsResult: have permission");
                    gpsTracker = null;
                    gpsTracker = new GPSTracker(this);
                    checklocation();

                } else {
                    Log.e(TAG, "onRequestPermissionsResult: permission no Accept");
                    showSnackBar("Location Permission is need");
                }
                return;
            }

        }
    }

    void checkGPS() {
        LocationRequest locationRequest = LocationRequest.create();

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                Log.e("GPS_main", "OnSuccess");

//                restartapp(SplashScreen.this);
                locationfetch();

            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull final Exception e) {
                Log.e("GPS_main", "GPS off");
                // GPS off
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    try {
                        resolvable.startResolutionForResult(GetLocationOfUser.this, REQUESTCODE_TURNON_GPS);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: resuldtcoder-> " + resultCode + " requestcode-> " + requestCode);
        if (resultCode != RESULT_OK) {

            return;
        }

        if (requestCode == REQUESTCODE_TURNON_GPS) {
            locationfetch();
        }
    }
}








