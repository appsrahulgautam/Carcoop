package com.carcoop.helpme.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.carcoop.helpme.App;
import com.carcoop.helpme.pojos.UserDetailsPojo;
import com.carcoop.helpme.pojos.UserLocationPojo;

public class SharedPreferenceManager {

    private static final String SHARED_PREF_NAME = "car_cooper_pref";
    private static final String USER_DETAILS = "user_details";
    private static final String FULLNAME = "fullname";
    private static final String VEHICLE_REG_NO = "vehicleRegNo";
    private static final String CAR_BRAND = "car_brand";
    private static final String CAR_MODEL = "car_model";
    private static final String EMAIL_SAVE = "email_save";
    //    location Shared data
    private static final String LATITUDE = "lat";
    private static final String LONGITUDE = "lng";
    private static final String COUNTRY = "country";
    private static final String CITY = "city";
    private static final String STATE = "state";
    private static final String MNROAD = "mainRoad";
    private static final String STREET = "street";
    private static final String ADDRESSBASE_LINE = "addressBaseLine";
    private static SharedPreferenceManager instance;
    private final SharedPreferences sharedPreferences;

    private SharedPreferenceManager() {
        sharedPreferences = App.getInstance().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPreferenceManager getInstance() {
        if (instance == null) {
            instance = new SharedPreferenceManager();
        }
        return instance;
    }

    public void loginUserDetails(UserDetailsPojo userDetailsPojo) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FULLNAME, userDetailsPojo.getFullname());
        editor.putString(VEHICLE_REG_NO, userDetailsPojo.getCarRegNumber());
        editor.putString(CAR_MODEL, userDetailsPojo.getCarModel());
        editor.putString(CAR_BRAND, userDetailsPojo.getCarBrandName());
        editor.apply();

    }

    public boolean isLogin() {
        return has(FULLNAME) && has(VEHICLE_REG_NO);
    }

    public boolean has(String key) {
        return sharedPreferences.contains(key);
    }

    public boolean haslocation() {

        return has(LATITUDE) && has(LONGITUDE) && has(COUNTRY);
    }


    //    user location save data
    public void saveUserLocations(UserLocationPojo userLocationPojo) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(LATITUDE, (float) userLocationPojo.getLat());
        editor.putFloat(LONGITUDE, (float) userLocationPojo.getLng());
        editor.putString(COUNTRY, userLocationPojo.getCountry());
//        editor.putString(CITY,userLocationPojo.getLat());
//        editor.putString(LATITUDE,userLocationPojo.getLat());
//        editor.putString(LATITUDE,userLocationPojo.getLat());
//        editor.putString(LATITUDE,userLocationPojo.getLat());
        editor.putString(ADDRESSBASE_LINE, userLocationPojo.getAddressBaseline());
        editor.apply();
    }


    //    email save
    public void saveEmaildetail(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL_SAVE, email);
        editor.apply();
    }


    //    get Email Save
    public String getEmail_detail() {

        return sharedPreferences.getString(EMAIL_SAVE, null);

    }

    //    get user details
    public UserDetailsPojo getUserDetails() {
        UserDetailsPojo userDetailsPojo = new UserDetailsPojo();
        userDetailsPojo.setFullname(sharedPreferences.getString(FULLNAME, null));
        userDetailsPojo.setCarRegNumber(sharedPreferences.getString(VEHICLE_REG_NO, null));
        userDetailsPojo.setCarModel(sharedPreferences.getString(CAR_MODEL, null));
        userDetailsPojo.setCarBrandName(sharedPreferences.getString(CAR_BRAND, null));
        return userDetailsPojo;
    }


//    get User Location Details

    public UserLocationPojo getUserLocations() {

        UserLocationPojo userLocationPojo = new UserLocationPojo();
        userLocationPojo.setLat(sharedPreferences.getFloat(LATITUDE, 0));
        userLocationPojo.setLng(sharedPreferences.getFloat(LONGITUDE, 0));
        userLocationPojo.setCountry(sharedPreferences.getString(COUNTRY, null));
        userLocationPojo.setAddressBaseline(sharedPreferences.getString(ADDRESSBASE_LINE, null));

        return userLocationPojo;
    }
}
