package com.carcoop.helpme.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.carcoop.helpme.R;
import com.carcoop.helpme.pojos.UserDetailsPojo;
import com.carcoop.helpme.utils.SharedPreferenceManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Edit_Deatails extends BaseActivity {

    private static final String TAG = "Editdeatails";
    private EditText name, carRegnumber, carbrand, cardmodel;
    private EditText email_ET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_edit__deatails);
        intiviews();
        setdetails();
        setEmaildetail();
    }

    /**
     * SET EMAIL DETAILS
     */
    private void setEmaildetail() {
//        String email = SharedPreferenceManager.getInstance().getEmail_detail();
//
//        if(!TextUtils.isEmpty(email)){
//            email_ET.setText(email);
//        }
//        else{
//            email_ET.setText("");
//        }

    }

    private void setdetails() {
//        UserLocationPojo userLocationPojo = getIntent().getParcelableExtra(Constance.SEND_LOCATION_DETAIL);
        UserDetailsPojo userDetailsPojo = SharedPreferenceManager.getInstance().getUserDetails();
//
//        if(TextUtils.isEmpty(userLocationPojo.getCountry()) ){
//            userLocationPojo = SharedPreferenceManager.getInstance().getUserLocations();
//        }
//        else{
//            Log.e(TAG, "setdatainview: not empty "+ TextUtils.isEmpty(userLocationPojo.getCountry()));
//        }

        name.setText(userDetailsPojo.getFullname());
        carRegnumber.setText(userDetailsPojo.getCarRegNumber());
        carbrand.setText(userDetailsPojo.getCarBrandName());
        cardmodel.setText(userDetailsPojo.getCarModel());
    }

    private void intiviews() {

        name = findViewById(R.id.name);
        carRegnumber = findViewById(R.id.carRegnumber);
        carbrand = findViewById(R.id.carbrand);
        cardmodel = findViewById(R.id.cardmodel);

        email_ET = findViewById(R.id.email_ET);
    }

    public void backpress(View view) {
        finish();
    }

    public void savebtnclick(View view) {

        if (validationCheck()) {
            if (isOnline()) {
                savedetailtoShareprefer();
                finish();
            } else {
                showSnackBar(getResources().getString(R.string.errornoInternet));
            }
        }
    }


    private void savedetailtoShareprefer() {

        UserDetailsPojo userDetailsPojo = new UserDetailsPojo();
        userDetailsPojo.setFullname(name.getText().toString().trim());
        userDetailsPojo.setCarRegNumber(carRegnumber.getText().toString().trim());
        userDetailsPojo.setCarModel(carbrand.getText().toString().trim());
        userDetailsPojo.setCarBrandName(cardmodel.getText().toString().trim());

        SharedPreferenceManager.getInstance().loginUserDetails(userDetailsPojo);
        SharedPreferenceManager.getInstance().saveEmaildetail(email_ET.getText().toString().trim());

        Toast.makeText(this, getResources().getString(R.string.updatedetail), Toast.LENGTH_SHORT).show();
    }


    private boolean validationCheck() {


        if (TextUtils.isEmpty(name.getText())) {
            name.setError(getResources().getString(R.string.errorFullname));
            return false;
        }
        if (name.getText().toString().length() < 3) {
            name.setError(getResources().getString(R.string.errornamesmall));
            return false;
        }
        if (TextUtils.isEmpty(carRegnumber.getText())) {

            carRegnumber.setError(getResources().getString(R.string.errorcarReg_number));
            return false;
        }


        if (TextUtils.isEmpty(carbrand.getText())) {

            carbrand.setError(getResources().getString(R.string.errorcarBrand));
            return false;
        }

//        if (TextUtils.isEmpty(cardmodel.getText())) {
//            cardmodel.setError(getResources().getString(R.string.errorcarModel));
//            return false;
//        }
//        if(TextUtils.isEmpty(email_ET.getText().toString().trim()) || !isEmailValid(email_ET.getText().toString().trim()) ){
//            email_ET.setError(getResources().getString(R.string.erroremail));
//            return false;
//        }

        return true;
    }


    private boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

}