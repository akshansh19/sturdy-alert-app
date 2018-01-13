package com.example.hp.alert;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;

public class Splash_Title extends Activity {

    ImageView logo_here;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        try {
            if (requestCode == 1) {
                if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        //then okay ....
                    } else {
                        //then okay ......
                    }

                }
            }
        } catch (Exception e) {
            Log.d("error_logo_perm1", String.valueOf(e));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__title);

        //for displaying the widget in the list
       /*try {
            sendBroadcast(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME));
        } catch (Exception e) {
            Log.d("widget_broadcast", String.valueOf(e));
        }*/
        //setting imageView ......
        logo_here = (ImageView) findViewById(R.id.logo);
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                Log.d("error_permission_fine", "check Here");
            }
        } catch (Exception e) {
            Log.d("error_logo_per", String.valueOf(e));
        }
        try {
            //setting animation
            //  Animation animation = AnimationUtils.loadAnimation(Splash_Title.this, android.R.anim.fade_in);
            //animation.setDuration(3000);
            //logo_here.startAnimation(animation);
            //setting timer.
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Splash_Title.this, Display_once.class);
                    startActivity(intent);
                }
            }, 4000);
        } catch (Exception e) {
            Log.d("error_logo", String.valueOf(e));
        }

    }
}
