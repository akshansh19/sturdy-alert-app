package com.example.hp.alert;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;


import static android.content.Context.LOCATION_SERVICE;


class GPS_tracker implements LocationListener {

    private LocationManager locationManager;
    private Context context;
    private Location location;


    private boolean canGetLocation = false;

    private double latitude, longitude;

    private static final long MIN_DISTANCE = 10;
    private static final long MIN_TIME = 6000;


    GPS_tracker(Context context) {
        this.context = context;
        getLocation();
    }

    private void setCriteria() {
        try {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setCostAllowed(true);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
        } catch (Exception e) {
            Log.d("error_criteria", String.valueOf(e));
        }
    }

    private Location getLocation() {
        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

            boolean isGPSenabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSenabled && !isNetworkEnabled) {
                Log.d("", "");
            } else {
                this.canGetLocation = true;

                if (isNetworkEnabled) {
                    setCriteria();
                    location = null;
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);

                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                        }

                    }
                }

                if (isGPSenabled) {
                    setCriteria();
                    if (location != null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);

                        if (locationManager != null) {

                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();

                            }
                        }
                    }
                }
            }


        } catch (Exception e) {
            Log.d("error_", String.valueOf(e));
        }
        return location;
    }


    @Override
    public void onLocationChanged(Location location) {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        return latitude;

    }

    double getLongitude() {
        if (location != null) {
            latitude = location.getLongitude();
        }
        return longitude;

    }

    boolean canGetLocation() {
        return this.canGetLocation;
    }

    void showSettingAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setTitle("GPS is setting");

        alertDialog.setMessage("GPS not enabled!!!");

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alertDialog.show();
    }
}
