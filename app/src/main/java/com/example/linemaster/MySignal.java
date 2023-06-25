package com.example.linemaster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.linemaster.Data.Merchant;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class MySignal {
    private Context context;
    private Vibrator vibrator;
    private static MySignal mySignal;
    private String userEmail;

    private MySignal(Context context) {
        this.context = context;
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public static void init(Context context) {
        if (mySignal == null) {
            mySignal = new MySignal(context.getApplicationContext());
        }
    }

    public static MySignal getInstance() {
        return mySignal;
    }

    public void vibrate(int milisec) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(milisec, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(milisec);
        }
    }

    public void playSound(Context context, int soundID) {
        MediaPlayer player = MediaPlayer.create(context, soundID);
        player.setVolume(110, 110);
        player.start();
    }

    public void toast(String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                } catch (IllegalStateException ex) {
                }
            }
        });
    }


    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public void playYoyo(View element) {
        YoYo.with(Techniques.Tada)
                .duration(700)
                .repeat(5)
                .playOn(element);
    }

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    public boolean isEmail(String string) {
        return pattern.matcher(string).matches();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public MySignal setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public interface Listener_String {
        void getString(String str);
    }

    public void getAddress(double latitude, double longitude, Listener_String listener_string) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this.context, Locale.getDefault());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(latitude, longitude, 1, new Geocoder.GeocodeListener() {
                @Override
                public void onGeocode(@NonNull List<Address> addresses) {
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();
                    listener_string.getString(address + " ," + city + " ," + state + " ," + country+" ,"+postalCode);
                }
            });
        }else {
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                listener_string.getString(address + " ," + city + " ," + state + " ," + country+" ,"+postalCode);
            } catch (Exception e) {
                listener_string.getString("lat = "+latitude+", lng = "+longitude);
            }
        }
    }
    public String formatTime(int hours, int minutes) {
        // Validate hours and minutes
        if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59) {
            throw new IllegalArgumentException("Invalid hours or minutes");
        }

        // Format hours with leading zeros
        String formattedHours = String.format("%02d", hours);

        // Format minutes with leading zeros
        String formattedMinutes = String.format("%02d", minutes);

        // Return the formatted time as a string
        return formattedHours + ":" + formattedMinutes;
    }
}
