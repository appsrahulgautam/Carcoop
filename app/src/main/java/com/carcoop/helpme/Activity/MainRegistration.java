package com.carcoop.helpme.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;

import com.carcoop.helpme.Constance;
import com.carcoop.helpme.R;
import com.carcoop.helpme.pojos.UserDetailsPojo;
import com.carcoop.helpme.utils.SharedPreferenceManager;
import com.github.florent37.materialtextfield.MaterialTextField;

public class MainRegistration extends BaseActivity {

    //First Name, Last Name, Registration Number, Car Make(Brand company), Car Model
    //
    // Then we would require the same information from the other person involved in the
    // accident plus we would need details of their insurance provider, a photo of their Drivers Licence,
    // Number Plates and photos of any damage
    //
    //If we could locate them using their location settings that would be great as that information could
    // be automatically loaded into the app

    // And the I will provide you with email addresses and phone numbers so that the information
    // can be emailed to our team and the customer can call the numbers within the app for help

    //We require an app that our customers can use in case of emergency
    //This app would collect photos and data from an accident scene


    //It would prompt the user with emergency numbers and the collected photos and data
    // would be emailed to our support team


    private MaterialTextField fullNameET, carRegNumberET, carmodelET, carbrandET;
    private ProgressBar progress_bar;


//    public static void startActivity(Activity activity){
//        Intent intent = new Intent(activity,MainRegistration.class);
//        activity.startActivity(intent);
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        intiview();
    }

    //Onclick views
    public void letsstart(View view) {

        if (validationCheck()) {
            if (isOnline()) {
                savedetailtoShareprefer();
                Intent intent = new Intent(this, GetLocationOfUser.class);
                intent.putExtra(Constance.SAVE_IN_PREF, true);
                startActivity(intent);

                finish();


            } else {
                showSnackBar(getResources().getString(R.string.errornoInternet));
            }

        }

    }


    //    after register save detail to share preference
    private void savedetailtoShareprefer() {

        UserDetailsPojo userDetailsPojo = new UserDetailsPojo();
        userDetailsPojo.setFullname(fullNameET.getEditText().getText().toString().trim());
        userDetailsPojo.setCarRegNumber(carRegNumberET.getEditText().getText().toString().trim());
        userDetailsPojo.setCarModel(carmodelET.getEditText().getText().toString().trim());
        userDetailsPojo.setCarBrandName(carbrandET.getEditText().getText().toString().trim());

        SharedPreferenceManager.getInstance().loginUserDetails(userDetailsPojo);

    }


    //init All views
    private void intiview() {

        fullNameET = findViewById(R.id.fullNameET);
        carRegNumberET = findViewById(R.id.carRegNumberET);
        carmodelET = findViewById(R.id.carmodelET);
        carbrandET = findViewById(R.id.carbrandET);
        progress_bar = findViewById(R.id.progress_bar);
    }


    private boolean validationCheck() {


        if (TextUtils.isEmpty(fullNameET.getEditText().getText())) {
            fullNameET.expand();
            fullNameET.getEditText().setError(getResources().getString(R.string.errorFullname));
            return false;
        }
        if (fullNameET.getEditText().getText().toString().length() < 3) {
            fullNameET.expand();
            fullNameET.getEditText().setError(getResources().getString(R.string.errornamesmall));
            return false;
        }
        if (TextUtils.isEmpty(carRegNumberET.getEditText().getText())) {
            carRegNumberET.expand();
            carRegNumberET.getEditText().setError(getResources().getString(R.string.errorcarReg_number));
            return false;
        }


        if (TextUtils.isEmpty(carbrandET.getEditText().getText())) {
            carbrandET.expand();
            carbrandET.getEditText().setError(getResources().getString(R.string.errorcarBrand));
            return false;
        }

        if (TextUtils.isEmpty(carmodelET.getEditText().getText())) {
            carmodelET.expand();
            carmodelET.getEditText().setError(getResources().getString(R.string.errorcarModel));
            return false;
        }

        return true;
    }


}