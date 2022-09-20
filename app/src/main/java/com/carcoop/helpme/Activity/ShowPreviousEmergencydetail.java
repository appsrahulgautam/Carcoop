package com.carcoop.helpme.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.carcoop.helpme.Constance;
import com.carcoop.helpme.R;
import com.carcoop.helpme.adapters.ImageEmergPrvRV_adapter;
import com.carcoop.helpme.pojos.Emergency_detailPOJO;
import com.carcoop.helpme.utils.TimeUtils;

import java.io.File;
import java.util.ArrayList;

import io.paperdb.Paper;

public class ShowPreviousEmergencydetail extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final String TAG = "showPerviousEmerg";
    private static final int DELETE_FILE_ID = 1101;
    private static final String FILE_NAME = "filename";
    private static Activity context;
    private Emergency_detailPOJO detailPOJO;
    private TextView vehicleRegnoTV, carMakeTV, carmodelTV, insurancetv;
    private TextView location_TV, date_ofEmegergency, time_ofEmegergency;
    private RecyclerView emgprevImage_rv, emgprevVideo_rv;
    private Emergency_detailPOJO emergency_detailPOJO;
    //    other Driver Detail
    private TextView od_usernameTV, od_licenceNO_TV, insuranceET, odvehicleRegnoET, odcarMakeET, odcarmodelET;
    private TextView od_MobileNO, od_HomeAddress;
    private LinearLayout containerdetail;
    private int position;
    private Dialog dialog;


//    public static void StartActivity(Activity activity){
//        activity = activity;
//        Intent intent = new Intent(activity,ShowPreviousEmergencydetail.class);
//        activity.startActivity(intent);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_show_previous_emergencydetail);
        intiview();
        setdatas();
    }


    //    inti view
    private void intiview() {
        vehicleRegnoTV = findViewById(R.id.vehicleRegnoTV);
        carMakeTV = findViewById(R.id.carMakeTV);
        carmodelTV = findViewById(R.id.carmodelTV);
        insurancetv = findViewById(R.id.insurancetv);
        location_TV = findViewById(R.id.location_TV);
        emgprevImage_rv = findViewById(R.id.emgprevImage_rv);
        emgprevVideo_rv = findViewById(R.id.emgprevVideo_rv);
        containerdetail = findViewById(R.id.containerdetail);
        date_ofEmegergency = findViewById(R.id.date_ofEmegergency);
        time_ofEmegergency = findViewById(R.id.timestamp_ofEmegergency);

        od_usernameTV = findViewById(R.id.od_usernameTV);
        od_licenceNO_TV = findViewById(R.id.od_licenceNO_TV);
        insuranceET = findViewById(R.id.insuranceET);
        odvehicleRegnoET = findViewById(R.id.odvehicleRegnoET);
        odcarMakeET = findViewById(R.id.odcarMakeET);
        odcarmodelET = findViewById(R.id.odcarmodelET);
        od_MobileNO = findViewById(R.id.od_MobileNO);
        od_HomeAddress = findViewById(R.id.od_HomeAddress);


    }

    //    onbackpress
    public void backpress(View view) {
        finish();
    }

    public void dustbinpress(View view) {
        showCustomDialog(position);

    }

    //    set all layout
    @SuppressLint("LongLogTag")
    private void setdatas() {
        emergency_detailPOJO = getIntent().getParcelableExtra(Constance.PRV_EMP_DETAIL);
        Log.e(TAG, "setdatas: " + emergency_detailPOJO);

        if (emergency_detailPOJO == null) {
            Log.e(TAG, "setdatas:  emergency_detailPOJO null");
            return;
        }
        Log.e("detail", "setdatas: " + emergency_detailPOJO);
        Log.e("datail", "setdatas: " + emergency_detailPOJO.getVehicleRegno() + " " + emergency_detailPOJO.getInsurance());

        vehicleRegnoTV.setText(emergency_detailPOJO.getVehicleRegno());
        carMakeTV.setText(emergency_detailPOJO.getCarBrand());
        carmodelTV.setText(emergency_detailPOJO.getCarmodel());
//        insurancetv.setText(emergency_detailPOJO.getInsurance());
        location_TV.setText(emergency_detailPOJO.getLocation());

        ArrayList<String> imagarray = emergency_detailPOJO.getImageuris();
//        ArrayList<String> videoarray = emergency_detailPOJO.getVideouris();

        Log.e(TAG, "setdatas: imagearray-=-=-=-=-> " + imagarray);

        emgprevImage_rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        emgprevImage_rv.setAdapter(new ImageEmergPrvRV_adapter(this, imagarray));


//        emgprevVideo_rv.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
//        emgprevVideo_rv.setAdapter(new VideoEmergPrvRV_adapter(this,videoarray));

        String otherdrivername = emergency_detailPOJO.getOtherdrivername();
        String otherdriverlicence = emergency_detailPOJO.getOtherdriverlicence();
        String timestemp = new TimeUtils().convTxttoTime(emergency_detailPOJO.getTimeandDatestamp().toString());

        Log.e(TAG, "setdatas: timestemp-> " + emergency_detailPOJO.getTimeandDatestamp());
        String[] splitetime = timestemp.split(" ");
        date_ofEmegergency.setText(splitetime[0]);
        time_ofEmegergency.setText(splitetime[1] + " " + splitetime[2]);

        Log.e(TAG, "setdatas: otherdrivername " + otherdrivername);
        if (ishasOtherDriverDetail(emergency_detailPOJO)) {
            containerdetail.setVisibility(View.VISIBLE);
        } else {
            containerdetail.setVisibility(View.GONE);
        }

    }


    private boolean ishasOtherDriverDetail(Emergency_detailPOJO emergency_detailPOJO) {
        boolean isotherdeatail = false;
//        Log.e("otherdatailfirst", "ishasOtherDriverDetail:otherdrivername "+otherdrivername+" isempty-> "+TextUtils.isEmpty(otherdrivername) );
        if (!TextUtils.isEmpty(emergency_detailPOJO.getOtherdrivername())) {
            isotherdeatail = true;
            od_usernameTV.setText(emergency_detailPOJO.getOtherdrivername());

        } else {
            od_usernameTV.setVisibility(View.GONE);
        }


        if (!TextUtils.isEmpty(emergency_detailPOJO.getOtherdrivateVehicleRegno())) {
            isotherdeatail = true;
            odvehicleRegnoET.setText(emergency_detailPOJO.getOtherdrivateVehicleRegno());

        } else {
            odvehicleRegnoET.setVisibility(View.GONE);
        }


        if (!TextUtils.isEmpty(emergency_detailPOJO.getOtherdrivercarBrand())) {
            isotherdeatail = true;
            odcarMakeET.setText(emergency_detailPOJO.getOtherdrivercarBrand());
        } else {
            odcarMakeET.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(emergency_detailPOJO.getOtherdrivercarmodel())) {
            isotherdeatail = true;
            odcarmodelET.setText(emergency_detailPOJO.getOtherdrivercarmodel());
        } else {
            odcarMakeET.setVisibility(View.GONE);
        }


        if (!TextUtils.isEmpty(emergency_detailPOJO.getOtherdriverlicence())) {
            isotherdeatail = true;
            od_licenceNO_TV.setText(emergency_detailPOJO.getOtherdriverlicence());
        } else {
            od_licenceNO_TV.setVisibility(View.GONE);
        }
//
        if (!TextUtils.isEmpty(emergency_detailPOJO.getInsurance())) {
            isotherdeatail = true;
            insuranceET.setText(emergency_detailPOJO.getInsurance());
        } else {
            insuranceET.setVisibility(View.GONE);
        }


        if (!TextUtils.isEmpty(emergency_detailPOJO.getOtherdriverMobilNumber()) &&
                isphonenumbervalidate(emergency_detailPOJO.getOtherdriverMobilNumber())) {
            isotherdeatail = true;
            od_MobileNO.setText(emergency_detailPOJO.getOtherdriverMobilNumber());
        } else {
            od_MobileNO.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(emergency_detailPOJO.getOtherdriverHomeAddress())) {
            isotherdeatail = true;
            od_HomeAddress.setText(emergency_detailPOJO.getOtherdriverHomeAddress());

        } else {
            od_HomeAddress.setVisibility(View.GONE);
        }


        return isotherdeatail;

    }


    /*Validate phone number*/
    private boolean isphonenumbervalidate(String phonenumber) {
        return android.util.Patterns.PHONE.matcher(phonenumber).matches();
    }


    public void showCustomDialog(int position) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.request_submitted_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        FrameLayout frmOk = dialog.findViewById(R.id.frmOk);
        FrameLayout frmNo = dialog.findViewById(R.id.frmNo);

        frmNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        frmOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Emergency_detailPOJO> emergency_detailPOJOS = Paper.book().read(Constance.EMERG_STORE);
                deletefileinbackground(emergency_detailPOJOS.get(position).getImageuris());

                emergency_detailPOJOS.remove(position);

                Paper.book().write(Constance.EMERG_STORE, emergency_detailPOJOS);
                finish();

                dialog.dismiss();
            }
        });


        dialog.show();
    }


    public void deletefileinbackground(ArrayList<String> imageURIS) {
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(FILE_NAME, imageURIS);
        loaderManager.initLoader(DELETE_FILE_ID, bundle, this).forceLoad();

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyntaskHolder(this, args);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {

        Log.e(TAG, "onLoadFinished: loading finished ");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

        Log.e(TAG, "onLoaderReset: loaderReset ");
    }

    private static class AsyntaskHolder extends AsyncTaskLoader<String> {
        private Bundle args;

        public AsyntaskHolder(@NonNull Context context, Bundle args) {
            super(context);
            this.args = args;
        }

        @Nullable
        @Override
        public String loadInBackground() {

            for (String filename : args.getStringArrayList(FILE_NAME)) {
                Log.e(TAG, "loadInBackground: filename-> " + filename);
                File file = new File(filename);
                if (file.exists()) {
                    Log.e(TAG, "loadInBackground: file is deleted " + file.delete());
                } else {
                    Log.e(TAG, "loadInBackground:filename exits " + filename);
                }
            }
//

            return null;
        }

        @Override
        public void onCanceled(@Nullable String data) {
            super.onCanceled(data);
            Log.e(TAG, "onCanceled: ");
        }
    }
}