package com.carcoop.helpme;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.carcoop.helpme.Activity.BaseActivity;
import com.carcoop.helpme.Activity.GetLocationOfUser;
import com.carcoop.helpme.Activity.Home;
import com.carcoop.helpme.Activity.MainRegistration;
import com.carcoop.helpme.utils.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sushil 1-13-2021
 */

public class SplashScreen extends BaseActivity {

    private static final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private static final String TAG = "SplashScreen";
    private static final int SPLASH_TIME_OUT = 2000;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    private ImageView splash_logo;

    public static void restartapp(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        context.startActivity(mainIntent);
        Runtime.getRuntime().exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_splash_screen);
        initview();

//        permissionAsk();
        splashloading();
    }

    private void permissionAsk() {


//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if( !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ) {
//            new AlertDialog.Builder(this)
//                    .setTitle(R.string.gps_not_found_title)  // GPS not found
//                    .setMessage(R.string.gps_not_found_message) // Want to enable?
//                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//                        }
//                    })
//                    .setNegativeButton(R.string.no, null)
//                    .show();
//        }
//        else{
        permissioncheck();
//        }
    }

    //initview
    private void initview() {
        splash_logo = findViewById(R.id.splash_logo);

        startAnimation();
    }

    //Splash screen animation
    private void startAnimation() {

        Animation splashAnim = AnimationUtils.loadAnimation(App.getInstance(), R.anim.zoom_out);
        splash_logo.setAnimation(splashAnim);
    }

    //checeking for login
    private void splashloading() {
        mainThreadHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                permissionAsk();

            }
        }, SPLASH_TIME_OUT);

    }

    public boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "Permission is granted1");
                return true;
            } else {

                Log.e(TAG, "Permission is revoked1");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.e(TAG, "Permission is granted1");
            return true;
        }
    }

    public boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "Permission is granted2");
                return true;
            } else {

                Log.e(TAG, "Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.e(TAG, "Permission is granted2");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        Log.e(TAG, "onRequestPermissionsResult: requestcode-> " + requestCode);
        if (requestCode == 23) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Displaying a toast
                Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Permission Needed To Run The App", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {
            Map<String, Integer> perms = new HashMap<String, Integer>();
            // Initial
            perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.CALL_PHONE, PackageManager.PERMISSION_GRANTED);
            //Toast.makeText(SplashScreen.this, " Permissions are jddddd", Toast.LENGTH_SHORT).show();
            // Fill with results
            for (int i = 0; i < permissions.length; i++)
                perms.put(permissions[i], grantResults[i]);
            // Check for ACCESS_FINE_LOCATION
            if (perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && perms.get(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    perms.get(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                // All Permissions Granted
                // insertDummyContact();

                Log.e(TAG, "onRequestPermissionsResult: all permission is allow");
                //Toast.makeText(SplashScreen.this, " Permissions are l", Toast.LENGTH_SHORT).show();
                LaunchApp();

            } else {
                // Permission Denied
                Toast.makeText(this, "Some Permission is Denied Restarting app", Toast.LENGTH_SHORT)
                        .show();


                mainThreadHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        restartapp(SplashScreen.this);
                    }
                }, 1000);
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //Do something after 100
//                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                        Uri uri = Uri.fromParts("package", getPackageName(), null);
//                        intent.setData(uri);
//                        startActivityForResult(intent, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
//                        finish();
//                    }
//                }, 3000);
            }

        }
    }

    private void permissioncheck() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
            permissionsNeeded.add("READ");
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_COARSE_LOCATION))
            permissionsNeeded.add("COURLOC");
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("FINELOC");


        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (Build.VERSION.SDK_INT >= 23) {
                                    // Marshmallow+
                                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                            REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);


                                } else {
                                    // Pre-Marshmallow
                                }

                            }
                        });
                return;
            }

            if (Build.VERSION.SDK_INT >= 23) {
                // Marshmallow+
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);


            } else {
                // Pre-Marshmallow
                Log.e(TAG, "permissioncheck: pre_marshmallow");
            }

            return;
        } else {
            // Toast.makeText(this,"Permission",Toast.LENGTH_LONG).show();
            LaunchApp();
        }

        //insertDummyContact();
    }


    private boolean addPermission(List<String> permissionsList, String permission) {

        Boolean cond;
        if (Build.VERSION.SDK_INT >= 23) {
            // Marshmallow+
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                // Check for Rationale Option
                if (!shouldShowRequestPermissionRationale(permission))
                    //  return false;

                    cond = false;
            }
            //  return true;

            cond = true;


        } else {
            // Pre-Marshmallow
            cond = true;
        }

        return cond;

    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    private void LaunchApp() {


        if (!SharedPreferenceManager.getInstance().isLogin()) {

            Intent intent = new Intent(SplashScreen.this, MainRegistration.class);
            startActivity(intent);


        } else if (!SharedPreferenceManager.getInstance().haslocation()) {
            Intent intent = new Intent(SplashScreen.this, GetLocationOfUser.class);
            intent.putExtra(Constance.SAVE_IN_PREF, true);
            startActivity(intent);
        } else {
            startActivity(new Intent(SplashScreen.this, Home.class));
        }

        finish();

    }


}