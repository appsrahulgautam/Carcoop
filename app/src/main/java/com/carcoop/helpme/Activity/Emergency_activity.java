package com.carcoop.helpme.Activity;

import static com.carcoop.helpme.Constance.IMAGE;
import static com.carcoop.helpme.Constance.REQUEST_CAMERA_CODE;
import static com.carcoop.helpme.Constance.REQUEST_VIDEO_CODE;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.FileProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.carcoop.helpme.BuildConfig;
import com.carcoop.helpme.Constance;
import com.carcoop.helpme.R;
import com.carcoop.helpme.adapters.ImagesUploadedAdapter;
import com.carcoop.helpme.adapters.VideosUplodedAdapter;
import com.carcoop.helpme.interfaces.ItemOnclickListerner;
import com.carcoop.helpme.pojos.Emergency_detailPOJO;
import com.carcoop.helpme.pojos.UserDetailsPojo;
import com.carcoop.helpme.pojos.UserLocationPojo;
import com.carcoop.helpme.utils.SharedPreferenceManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.paperdb.Paper;

public class Emergency_activity extends BaseActivity implements ItemOnclickListerner {

    private static final String TAG = "Emergency_actvity";
    private static final String CARCOOP_HELP_EMAIL1 = "finance@carcoop.com.au";
    private static final String CARCOOP_HELP_EMAIL2 = "hello@carcoop.com.au";


    private static final int VIDEO_FILE_CODE = 1;
    private static final int IMAGE_FILE_CODE = 2;
    private static final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    /**
     * fields details
     */
    String name;
    String vehicleRegno;
    String carBrand;
    String carmodel;
    String insurance;
    String location;
    ArrayList<String> imageURIS;
    ArrayList<String> videosURIS;
    String otherdrivername;
    String otherdriverlicence;
    String otherdrivercarMake;
    String otherdriverCarModel;
    String otherdrivateCarVehicleRegno;
    String otherdriverMobileNumber;
    String otherdriverHomeAddress;
    private String currentPhotoPath;
    private String currentVideoPath;
    private EditText usernameET, vehicleRegnoET, carMakeET,
            carmodelET, insuranceET, location_ET;
    //    Other Driver Detail
    private TextView addother_detail;
    private LinearLayout otherdriverdetail_container;
    private EditText od_usernameET, od_licenceNO, odvehicleRegnoET, odcarMakeET, odcarmodelET;
    private EditText od_MobileNO, od_HomeAddress;
    private RecyclerView emgVideo_rv, emgImage_rv;
    private ImageView add_image, add_video;
    private ImagesUploadedAdapter imagesUploadedAdapter;
    private VideosUplodedAdapter videosUplodedAdapter;
    private FrameLayout processing;
    private AppCompatButton sendbtnbig;
    private UserLocationPojo locationPojo;
    private ArrayList<String> firbaseImageuri = new ArrayList<>();
    private ArrayList<String> firebaseVideouri = new ArrayList<>();
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (processing.getVisibility() == View.VISIBLE) {
                processing.setVisibility(View.GONE);
                saveemergdetail();
                finish();
            }

        }
    };
    private String otherdriverdetail;


    /**
     * firebase contance
     */
    private StorageReference storageReference;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_emergency_activity);
        initviews();
        setdatainview();
        intrecycles();
        onclickListeners();

    }

    @Override
    protected void onStart() {
        super.onStart();
        storageReference = FirebaseStorage.getInstance().getReference();
        IntentFilter intentFilter = new IntentFilter(Constance.PROGRESS_STOP_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);

    }

    //set data
    private void setdatainview() {

        if (Paper.book().contains(Constance.emergency_detailPOJO)) {
            setincompletedata();
        } else {
            locationPojo = getIntent().getParcelableExtra(Constance.SEND_LOCATION_DETAIL);
            UserDetailsPojo userDetailsPojo = SharedPreferenceManager.getInstance().getUserDetails();
            if (TextUtils.isEmpty(locationPojo.getCountry())) {
                locationPojo = SharedPreferenceManager.getInstance().getUserLocations();
            } else {
                Log.e(TAG, "setdatainview: not empty " + TextUtils.isEmpty(locationPojo.getCountry()));
            }

            usernameET.setText(userDetailsPojo.getFullname());
            vehicleRegnoET.setText(userDetailsPojo.getCarRegNumber());
            carMakeET.setText(userDetailsPojo.getCarBrandName());
            carmodelET.setText(userDetailsPojo.getCarModel());
            location_ET.setText(locationPojo.getAddressBaseline());
        }
    }

    //    save Incompletedata
    private void setincompletedata() {

    }


    //init views
    private void initviews() {

        usernameET = findViewById(R.id.usernameET);
        vehicleRegnoET = findViewById(R.id.vehicleRegnoET);
        carMakeET = findViewById(R.id.carMakeET);
        carmodelET = findViewById(R.id.carmodelET);
        insuranceET = findViewById(R.id.insuranceET);
        location_ET = findViewById(R.id.location_ET);

        emgVideo_rv = findViewById(R.id.emgVideo_rv);
        emgImage_rv = findViewById(R.id.emgImage_rv);

        add_image = findViewById(R.id.add_image);
        add_video = findViewById(R.id.add_video);

        processing = findViewById(R.id.processing);
        sendbtnbig = findViewById(R.id.sendbtnbig);

        addother_detail = findViewById(R.id.addother_detail);
        otherdriverdetail_container = findViewById(R.id.otherdriverdetail_container);
        od_usernameET = findViewById(R.id.od_usernameET);
        od_licenceNO = findViewById(R.id.od_licenceNO);
        odvehicleRegnoET = findViewById(R.id.odvehicleRegnoET);
        odcarMakeET = findViewById(R.id.odcarMakeET);
        odcarmodelET = findViewById(R.id.odcarmodelET);

        od_MobileNO = findViewById(R.id.od_MobileNO);
        od_HomeAddress = findViewById(R.id.od_HomeAddress);

    }


    //init recycler views
    private void intrecycles() {


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        emgImage_rv.setLayoutManager(layoutManager);
        imagesUploadedAdapter = new ImagesUploadedAdapter(this, new ArrayList<>());
        imagesUploadedAdapter.setItemOnclickListerner(this::itemOnclick);
        emgImage_rv.setAdapter(imagesUploadedAdapter);


        LinearLayoutManager layoutManagervideo = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        emgVideo_rv.setLayoutManager(layoutManagervideo);
        videosUplodedAdapter = new VideosUplodedAdapter(this, new ArrayList<>());
        videosUplodedAdapter.setOnclicklistener(this::itemOnclick);
        emgVideo_rv.setAdapter(videosUplodedAdapter);


    }


    //    init onclilckListener
    private void onclickListeners() {

        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        add_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        addother_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otherdriverdetail_container.getVisibility() == View.VISIBLE) {
                    otherdriverdetail_container.setVisibility(View.GONE);
                    addother_detail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_add, 0);
                } else {
                    otherdriverdetail_container.setVisibility(View.VISIBLE);
                    addother_detail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_minus, 0);
                }
            }
        });


        sendbtnbig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonhelpclick();
            }
        });

    }

    //    onclick functions
    public void backpress(View view) {
        finish();
    }

    //
    public void helpbtnclick(View view) {

        buttonhelpclick();

    }


    private void buttonhelpclick() {
        Log.e("numbervalidation", "buttonhelpclick: inside");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        if (checkforvalidation()) {
            name = usernameET.getText().toString().trim();
            vehicleRegno = vehicleRegnoET.getText().toString().trim();
            carBrand = carMakeET.getText().toString().trim();
            carmodel = carmodelET.getText().toString().trim();
            insurance = insuranceET.getText().toString().trim();
            location = location_ET.getText().toString().trim();

            otherdrivername = od_usernameET.getText().toString().trim();
            otherdriverlicence = od_licenceNO.getText().toString().trim();
            otherdrivercarMake = odcarMakeET.getText().toString().trim();
            otherdriverCarModel = odcarmodelET.getText().toString().trim();
            otherdrivateCarVehicleRegno = odvehicleRegnoET.getText().toString().trim();
            otherdriverMobileNumber = od_MobileNO.getText().toString().trim();
            otherdriverHomeAddress = od_HomeAddress.getText().toString().trim();


            imageURIS = imagesUploadedAdapter.getImageURIs();
            uploadimgesinFirebase();
        }
    }

    /**
     * upload images in firebase
     */
    private void uploadimgesinFirebase() {

        processing.setVisibility(View.VISIBLE);
        Log.e("imageconverter", "uploadimgesinFirebase:start ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "uploadimgesinFirebase:thread run ");
                ArrayList<String> imageURIS = imagesUploadedAdapter.getImageURIs();
                for (String imageuristring : imageURIS) {
                    Log.e(TAG, "uploadimgesinFirebase: load image -> " + imageuristring);
                    Uri imageuri = Uri.parse(imageuristring);
                    firebaseimageurlconverter(imageuri);
                }
            }
        }).start();
    }


    /**
     * uploaded to file in fire base
     */
    private void firebaseimageurlconverter(Uri imageuri) {
        Bitmap originalImage = null;
        try {
            originalImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),
                    Uri.fromFile(new File(String.valueOf(imageuri))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        assert originalImage != null;
        originalImage.compress(Bitmap.CompressFormat.JPEG, 30, outputStream);
        byte[] data = outputStream.toByteArray();
        StorageReference reference = storageReference.child("profile")
                .child("image_" + String.valueOf(System.currentTimeMillis()) + ".jpg");

        Log.e(TAG, "firebaseimageurlconverter: uri-> " + imageuri + " reference-> " + reference.getPath());

        reference.putBytes(data).addOnSuccessListener(taskSnapshot -> {
            Log.e(TAG, "onSuccess: second");
            Uri downloadUrl = taskSnapshot.getUploadSessionUri();
            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override

                public void onSuccess(Uri uri) {
                    firbaseImageuri.add(uri.toString());
                    Log.e(TAG, "onSuccess: image uri generaed-> " + uri.toString());
                    if (firbaseImageuri.size() == imagesUploadedAdapter.getsize()) {
                        Log.e(TAG, "onSuccess: completed all image upload: " + firbaseImageuri.size());
                        sendEmailjar();
                    }
                }
            }).addOnFailureListener(e -> Log.e(TAG, "onFailure: " + e.getMessage()));
        }).addOnFailureListener(e -> Log.e(TAG, "onFailure: " + e.getMessage()));
        Log.e(TAG, "firebaseimageurlconverter:at the end ");
    }


    private void saveemergdetail() {
        Emergency_detailPOJO emergency_detailPOJO = new Emergency_detailPOJO();
        emergency_detailPOJO.setName(name);
        emergency_detailPOJO.setVehicleRegno(vehicleRegno);
        emergency_detailPOJO.setCarBrand(carBrand);
        emergency_detailPOJO.setCarmodel(carmodel);
        emergency_detailPOJO.setInsurance(insurance);
        emergency_detailPOJO.setLocation(location);
        emergency_detailPOJO.setImageuris(imageURIS);
        emergency_detailPOJO.setVideouris(videosURIS);
        emergency_detailPOJO.setOtherdrivername(otherdrivername);
        emergency_detailPOJO.setOtherdriverlicence(otherdriverlicence);
        emergency_detailPOJO.setOtherdrivercarBrand(otherdrivercarMake);
        emergency_detailPOJO.setOtherdrivercarmodel(otherdriverCarModel);
        emergency_detailPOJO.setOtherdrivateVehicleRegno(otherdrivateCarVehicleRegno);
        emergency_detailPOJO.setOtherdriverMobilNumber(otherdriverMobileNumber);
        emergency_detailPOJO.setOtherdriverHomeAddress(otherdriverHomeAddress);
        emergency_detailPOJO.setTimeandDatestamp((new Date()).toString());

        if (!Paper.book().contains(Constance.EMERG_STORE)) {
            ArrayList<Emergency_detailPOJO> emergency_detailPOJOS = new ArrayList<>();
            emergency_detailPOJOS.add(emergency_detailPOJO);
            savetopagerEmerg(emergency_detailPOJOS);
        } else {
            ArrayList<Emergency_detailPOJO> emergency_detailPOJOS = Paper.book().read(Constance.EMERG_STORE);
            emergency_detailPOJOS.add(emergency_detailPOJO);
            savetopagerEmerg(emergency_detailPOJOS);
        }
    }


    private void savetopagerEmerg(ArrayList<Emergency_detailPOJO> emergency_detailPOJOS) {
        Paper.book().write(Constance.EMERG_STORE, emergency_detailPOJOS);
    }

    /**
     * check for validation
     */
    private boolean checkforvalidation() {

        Log.e("numbervalidation", "checkforvalidation: Checkfor validation");
        boolean ischecked = true;

        String name = usernameET.getText().toString().trim();
        String vehicleRegno = vehicleRegnoET.getText().toString().trim();
        String carBrand = carMakeET.getText().toString().trim();
        String carmodel = carmodelET.getText().toString().trim();
        String mobilenumber = od_MobileNO.getText().toString().trim();
//        String insurance = insuranceET.getText().toString().trim();
        String location = location_ET.getText().toString().trim();
        ArrayList<String> imageURIS = imagesUploadedAdapter.getImageURIs();

        if (TextUtils.isEmpty(name)) {
            usernameET.setError(getResources().getString(R.string.nameerror));
            ischecked = false;
        } else if (TextUtils.isEmpty(vehicleRegno)) {
            vehicleRegnoET.setError(getResources().getString(R.string.vehicleRegnumber));
            ischecked = false;
        } else if (TextUtils.isEmpty(carBrand)) {
            carMakeET.setError(getResources().getString(R.string.carbranderror));
            ischecked = false;
        } else if (TextUtils.isEmpty(carmodel)) {
            carmodelET.setError(getResources().getString(R.string.carmodelerror));
            ischecked = false;
        } else if (TextUtils.isEmpty(location)) {
            location_ET.setError(getResources().getString(R.string.locationerror));
            ischecked = false;
        } else if (imageURIS.size() <= 0) {
            showSimplesnackBar(getResources().getString(R.string.imageuriserror));
            ischecked = false;

        } else if (!ishasOtherDriverDetail()) {
            if (otherdriverdetail_container.getVisibility() == View.GONE) {
                showSimplesnackBar(getResources().getString(R.string.otherdriverdetailcompulsoryerror));
            }
            return false;
        }

        Log.e("numbervalidation", "checkforvalidation: out of the java ");
        return ischecked;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e(TAG, "onActivityResult: currentcamera-> " + currentPhotoPath + " currentVideo-> " + currentVideoPath + " request code " + requestCode);
        if (requestCode == REQUEST_CAMERA_CODE && resultCode == RESULT_OK) {
            Log.e(TAG, "onActivityResult: CAMERA_CODE");
            addImagetoRV(data);
        } else if (requestCode == REQUEST_VIDEO_CODE && resultCode == RESULT_OK) {
            Log.e(TAG, "onActivityResult: VIDEO_CODE");
            addVideotoRV(data);
        }


    }

    /**
     * populate RV
     */
    private void addVideotoRV(Intent data) {
        Log.e(TAG, "addVideotoRV: data");
        videosUplodedAdapter.addURI(currentVideoPath);
        add_video.setVisibility(View.GONE);
        emgVideo_rv.setVisibility(View.VISIBLE);
    }

    private void addImagetoRV(Intent data) {
        Log.e(TAG, "onActivityResult: extena; storage -> " + Environment.getExternalStorageDirectory().getPath());
        imagesUploadedAdapter.addURI(currentPhotoPath);
        add_image.setVisibility(View.GONE);
        emgImage_rv.setVisibility(View.VISIBLE);
        Log.e(TAG, "onActivityResult:---->  " + imagesUploadedAdapter.getsize());
        Log.e(TAG, "onActivityResult: currentphotoPath " + currentPhotoPath);
        Log.e(TAG, "onActivityResult: " + data);
    }


    //    create file name
    private File createImagevideoFile(int codeIV) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File filename;
        if (codeIV == IMAGE_FILE_CODE) {
            imageFileName = "JPEG_" + timeStamp + "_";
            filename = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = filename.getAbsolutePath();
        } else {
            imageFileName = "VID_" + timeStamp + "_";
            filename = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".mp4",         /* suffix */
                    storageDir      /* directory */
            );
            currentVideoPath = filename.getAbsolutePath();
        }

        return filename;
    }


    //
    private void dispatchTakePictureIntent() {

        Log.e(TAG, "dispatchTakePictureIntent: ");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImagevideoFile(IMAGE_FILE_CODE);
//                Log.e(TAG, "dispatchTakePictureIntent: createImageFile-> " + createImageFile().getAbsolutePath().toString());
            } catch (IOException ex) {
                // Error occurred while creating the File

                Log.e(TAG, "dispatchTakePictureIntent: " + ex.toString());
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID + ".provider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CAMERA_CODE);
            } else {

                Log.e(TAG, "dispatchTakePictureIntent: phohtofilesave photo ");
            }
        } else {
            Log.e(TAG, "dispatchTakePictureIntent: else part");
        }
    }


    /**
     * click listeners interfaces
     */
    @Override
    public void itemOnclick(int type) {
        if (type == IMAGE) {
            dispatchTakePictureIntent();
        }

    }


    /**
     * Send Email in usign mail.jar
     */

    protected void sendEmailjar() {
        List<String> toEmailList = new ArrayList<>();
//        toEmailList.add(CARCOOP_HELP_EMAIL1);
//        toEmailList.add(CARCOOP_HELP_EMAIL2);
//        toEmailList.add("sushilchhetri060@gmail.com");
        String emailbody = generateEmail();
        sendEmail(emailbody);
    }


    private String generateEmail() {
        StringBuffer imageBuffer = new StringBuffer();
        for (int i = 0; i < firbaseImageuri.size(); i++) {
            String imageurl = firbaseImageuri.get(i);
            imageBuffer.append("\n\n  ").append(i + 1).append(". ").append(imageurl);
        }

        String emailgenerate = "Carcoop Help:" +
                "\n\n Driver Details:" +
                "\n\n Name:  " + name + "\n\n Vehicle Registration Number: " + vehicleRegno +
                "\n\n Car Make(Brand):  " + carBrand + "\n\n Car Model:  " + carmodel +
                "\n\n Location:  " + location +
                "\n\n Longitude:  " + locationPojo.getLng() + "\n\n Latitude:  " + locationPojo.getLat();
        emailgenerate += "\n\n Images:  " + imageBuffer;
        otherdriverdetail = "\n\n\n\n          Other Driver Details:          ";
        setotherdriverdetails();
        emailgenerate += otherdriverdetail;
        Log.e("otherdatailfirst", "   generateEmail: emailgetnerate -> " + emailgenerate);


        return emailgenerate;
    }

    private void setotherdriverdetails() {
        otherdriverdetail += "\n\nDriver name:  " + otherdrivername;

        otherdriverdetail += "\n\nDriving licence:   " + otherdriverlicence;

        otherdriverdetail += "\n\nMobile Number:   " + otherdriverMobileNumber;

        otherdriverdetail += "\n\nHome Address:   " + otherdriverHomeAddress;

        otherdriverdetail += "\n\nInsurance Number:  " + insurance;

        otherdriverdetail += "\n\nVehicle Registration Number:   " + otherdrivateCarVehicleRegno;

        otherdriverdetail += "\n\nCar Make(Brand):  " + otherdrivercarMake;

        otherdriverdetail += "\n\n Car Model:  " + otherdriverCarModel;
    }


    private boolean ishasOtherDriverDetail() {

        Log.e("otherdatailfirst", "ishasOtherDriverDetail:otherdrivername " + otherdrivername + " isempty-> " + TextUtils.isEmpty(otherdrivername));
        if (TextUtils.isEmpty(od_usernameET.getText().toString().trim())) {

            od_usernameET.setError(getResources().getString(R.string.errorotherdrivername));
            return false;
        }


        if (TextUtils.isEmpty(od_licenceNO.getText().toString().trim())) {

            od_licenceNO.setError(getResources().getString(R.string.errorotherdriverlicence));
            return false;

        }


        if (TextUtils.isEmpty(od_MobileNO.getText().toString().trim()) && isphonenumbervalidate(od_MobileNO.getText().toString().trim())) {
            od_MobileNO.setError(getResources().getString(R.string.errorotherdrivermobilenumber));
            return false;

        } else {
            if (!isphonenumbervalidate(od_MobileNO.getText().toString().trim())) {

//                showSimplesnackBar(getResources().getString(R.string.validmobilenumber));
                od_MobileNO.setError(getResources().getString(R.string.validmobilenumber));
                return false;
            }

        }


        if (TextUtils.isEmpty(od_HomeAddress.getText().toString().trim())) {
            od_HomeAddress.setError(getResources().getString(R.string.errorotherdriverhomeaddress));
            return false;
        }


        if (TextUtils.isEmpty(insuranceET.getText().toString().trim())) {

            insuranceET.setError(getResources().getString(R.string.errorotherdriverinsurance));
            return false;
        }

        if (TextUtils.isEmpty(odvehicleRegnoET.getText().toString().trim())) {

            odvehicleRegnoET.setError(getResources().getString(R.string.errorotherdrivervecRegno));
            return false;
        }


        if (TextUtils.isEmpty(odcarMakeET.getText().toString().trim())) {

            odcarMakeET.setError(getResources().getString(R.string.errorotherdrivercarmake));
            return false;
        }


        if (TextUtils.isEmpty(odcarmodelET.getText().toString().trim())) {

            odcarmodelET.setError(getResources().getString(R.string.errorotherdrivercarmodel));
            return false;
        }
//


        return true;

    }


    /*Validate phone number*/
    private boolean isphonenumbervalidate(String phonenumber) {
        if (android.util.Patterns.PHONE.matcher(phonenumber).matches()) {

            if (phonenumber.length() < 6 || phonenumber.length() > 13) {

                return false;

            } else {
                return true;
            }
        } else {
            return false;
        }

    }

    /**
     * Send Email
     */
    @SuppressLint("IntentReset")
    protected void sendEmail(String helpmail) {
        Log.e("Send email", "");

        String[] TO = {CARCOOP_HELP_EMAIL1};
        String[] CC = {CARCOOP_HELP_EMAIL2};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        final PackageManager pm = getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
        ResolveInfo best = null;
        for (final ResolveInfo info : matches)
            if (info.activityInfo.packageName.endsWith(".gm") ||
                    info.activityInfo.name.toLowerCase().contains("gmail")) best = info;
        if (best != null)
            emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "CarCoop Emergency Help Needed");
        String empstring = helpmail;

        emailIntent.putExtra(Intent.EXTRA_TEXT, empstring);

        try {
            if (processing.getVisibility() == View.VISIBLE) {

                processing.setVisibility(View.GONE);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        saveemergdetail();
//                        storageReference.delete();
//                    }
//                });
                startActivity(Intent.createChooser(emailIntent, "Send mail to CarCoop via.."));
                saveemergdetail();
//                storageReference.delete();
                Toast.makeText(getApplicationContext(), "Help message is send", Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(Emer.this,
//                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
            showSimplesnackBar("There is no email client installed.");
        }


    }
}