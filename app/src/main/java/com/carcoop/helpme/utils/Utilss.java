package com.carcoop.helpme.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.TimeZone;

public class Utilss {

    public static HashMap<String, Object> globalMap = new HashMap<>();


    public static String getUTCdatetimeAsString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String utcTime = sdf.format(new Date());
        return utcTime;
    }


    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public static void hideAppBar(AppCompatActivity a) {
        showDefaultControls(a);
        if (a.getSupportActionBar() != null) a.getSupportActionBar().hide();
    }

    public static void hideStatusbar(Window w) {
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

    }

    public static void showDefaultControls(@NonNull final AppCompatActivity activity) {
        final Window window = activity.getWindow();

        if (window == null) {
            return;
        }

        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        final View decorView = window.getDecorView();

        if (decorView != null) {
            int uiOptions = decorView.getSystemUiVisibility();

            if (Build.VERSION.SDK_INT >= 14) {
                uiOptions &= ~View.SYSTEM_UI_FLAG_LOW_PROFILE;
            }

            if (Build.VERSION.SDK_INT >= 16) {
                uiOptions &= ~View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }

            if (Build.VERSION.SDK_INT >= 19) {
                uiOptions &= ~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }

            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public static void hideKeyBoard(AppCompatActivity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static int randomNumber() {
        return new Random().nextInt(60000);
    }


    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(),
                inImage, "Title", null);
        return Uri.parse(path);
    }


    public static String getStringSizeLengthFile(long size) {
        DecimalFormat df = new DecimalFormat("0.00");
        float sizeKb = 1024.0f;
        float sizeMb = sizeKb * sizeKb;
        float sizeGb = sizeMb * sizeKb;
        float sizeTerra = sizeGb * sizeKb;


        if (size < sizeMb)
            return df.format(size / sizeKb) + " Kb";
        else if (size < sizeGb)
            return df.format(size / sizeMb) + " Mb";
        else if (size < sizeTerra)
            return df.format(size / sizeGb) + " Gb";

        return "";
    }

    public static String getFileName(AppCompatActivity context, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null,
                    null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public static void vibrate(Context c) {
        Vibrator v = (Vibrator) c.getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }

    private boolean checkWifiOnAndConnected(Context c) {
        WifiManager wifiMgr = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);

        if (wifiMgr.isWifiEnabled()) { // Wi-Fi adapter is ON

            WifiInfo wifiInfo = wifiMgr.getConnectionInfo();

            if (wifiInfo.getNetworkId() == -1) {
                return false; // Not connected to an access point
            }
            return true; // Connected to an access point
        } else {
            return false; // Wi-Fi adapter is OFF
        }
    }

    public class Keystore { //Did you remember to vote up my example?
        private Keystore store;
        private SharedPreferences SP;
        private String filename = "Keys";

        private Keystore(Context context) {
            SP = context.getApplicationContext().getSharedPreferences(filename, 0);
        }

        public Keystore getInstance(Context context) {
            if (store == null) {
                Log.e("Keystore", "NEW STORE");
                store = new Keystore(context);
            }
            return store;
        }

        public void put(String key, String value) {//Log.e("Keystore","PUT "+key+" "+value);
            SharedPreferences.Editor editor = SP.edit();
            editor.putString(key, value);
            editor.commit(); // Stop everything and do an immediate save!
            // editor.apply();//Keep going and save when you are not busy - Available only in APIs 9 and above.  This is the preferred way of saving.
        }


        public String get(String key) {//Log.e("Keystore","GET from "+key);
            return SP.getString(key, null);

        }

        public int getInt(String key) {//Log.e("Keystore","GET INT from "+key);
            return SP.getInt(key, 0);
        }

        public void putInt(String key, int num) {//Log.e("Keystore","PUT INT "+key+" "+String.valueOf(num));
            SharedPreferences.Editor editor = SP.edit();

            editor.putInt(key, num);
            editor.commit();
        }


        public void clear() { // Delete all shared preferences
            SharedPreferences.Editor editor = SP.edit();

            editor.clear();
            editor.commit();
        }

        public void remove() { // Delete only the shared preference that you want
            SharedPreferences.Editor editor = SP.edit();

            editor.remove(filename);
            editor.commit();
        }
    }


    public class YourPreference {
        private YourPreference yourPreference;
        private SharedPreferences sharedPreferences;

        private YourPreference(Context context) {
            sharedPreferences = context.getSharedPreferences("YourCustomNamedPreference", Context.MODE_PRIVATE);
        }

        public YourPreference getInstance(Context context) {
            if (yourPreference == null) {
                yourPreference = new YourPreference(context);
            }
            return yourPreference;
        }

        public void saveData(String key, String value) {
            SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
            prefsEditor.putString(key, value);
            prefsEditor.commit();
        }

        public String getData(String key) {
            if (sharedPreferences != null) {
                return sharedPreferences.getString(key, "");
            }
            return "";
        }
    }


}
