package com.example.hp.alert;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SendMessage extends Activity implements GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener {

    ImageButton sendMsg;
    SQLiteDatabase database;
    String db, table;
    ImageView setting;
    GPS_tracker gps_tracker;
    double latitude, longitude;
    LinearLayout list, modify;
    GestureDetectorCompat gestureDetectorCompat;
    RelativeLayout relativeLayout;
    Dialog dialog = new Dialog();


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        try {
            if (requestCode == 1) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (
                            ContextCompat.checkSelfPermission(SendMessage.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(SendMessage.this, "Permission is Granted", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("Permission", "denied");
                    }

                }
            }
        } catch (Exception e) {
            Log.d("error_perm_sendMsg", String.valueOf(e));
        }
    }

    @Override
    public void onBackPressed() {
        //dialog.show(getFragmentManager(), "there");
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        try {
            if (ContextCompat.checkSelfPermission(SendMessage.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(SendMessage.this, Manifest.permission.SEND_SMS)) {
                    ActivityCompat.requestPermissions(SendMessage.this, new String[]{Manifest.permission.SEND_SMS}, 1);
                } else {
                    ActivityCompat.requestPermissions(SendMessage.this, new String[]{Manifest.permission.SEND_SMS}, 1);
                }
            } else {
                //do this ... .   ....  . .
            }
        } catch (Exception e) {
            Log.d("error_perm_sms", String.valueOf(e));
        }
        try {
            Bundle bundle = getIntent().getExtras();
            if (bundle == null) {
                db = null;
                table = null;
            } else {
                db = bundle.getString("databaseName");
                Log.d("sendMsg", "got db name");
                table = bundle.getString("tableName");
                Log.d("sendMsg", "got table name");
            }
        } catch (Exception e) {
            Log.d("error_bundle", String.valueOf(e));
        }

        sendMsg = (ImageButton) findViewById(R.id.SendMsg);
        setting = (ImageView) findViewById(R.id.settings);
        list = (LinearLayout) findViewById(R.id.list);
        relativeLayout = (RelativeLayout) findViewById(R.id.main_rel);
        modify = (LinearLayout) findViewById(R.id.modify_contacts);


        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SendMessage.this, Modify.class);
                startActivity(intent);
            }
        });


        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (list.getVisibility() == View.VISIBLE) {
                    list.setVisibility(View.GONE);
                }
                return true;
            }
        });
            /*sendMsg.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (list.getVisibility() == View.VISIBLE) {
                        list.setVisibility(View.GONE);
                    }
                    return true;
                }
            });*/

        this.gestureDetectorCompat = new GestureDetectorCompat(this, this);
        gestureDetectorCompat.setOnDoubleTapListener(this);

        list.setVisibility(View.GONE);

        database = openOrCreateDatabase("DB_contacts", Context.MODE_PRIVATE, null);
        try {
            sendMsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Log.d("sendMsg", "inside click Listener ");
                        Cursor c = database.rawQuery("SELECT number from '" + table + "';", null);
                        if (c.getCount() > 0) {
                            while (c.moveToNext()) {
                                Log.d("sendMsg", "inside the cursor");
                                String num = c.getString(0);
                                sendMessage(num);
                            }
                        } else {
                            Toast.makeText(SendMessage.this, "error", Toast.LENGTH_SHORT).show();
                        }
                        c.close();
                    } catch (Exception e) {
                        Log.d("sendMsg", String.valueOf(e));
                    }
                }

                private void sendMessage(String num) {
                    try {
                        String location = "http://maps.google.com/?q=" + latitude + "," + longitude;

                        String message = "Please help me.I am in trouble.I am at this location.Please be quick.  :" + location;

                        String sent = "Message Sent", delievered = "Message Delievered";

                        PendingIntent sendPI = PendingIntent.getBroadcast(SendMessage.this, 0, new Intent(sent), 0);
                        PendingIntent delieverdPI = PendingIntent.getBroadcast(SendMessage.this, 0, new Intent(delievered), 0);

                        SmsManager smsManager = SmsManager.getDefault();


                        smsManager.sendTextMessage(num, null, message, sendPI, delieverdPI);
                        Log.d("sendMsg", "Message Sent");
                    } catch (Exception e) {
                        Log.d("error_sendMsg", String.valueOf(e));
                    }
                }
            });
        } catch (Exception e) {
            Log.d("error", String.valueOf(e));
        }
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.setVisibility(View.VISIBLE);
            }
        });

        //getting location
        try {
            gps_tracker = new GPS_tracker(SendMessage.this);

            if (gps_tracker.canGetLocation()) {
                latitude = gps_tracker.getLatitude();
                longitude = gps_tracker.getLongitude();
            } else {
                gps_tracker.showSettingAlert();
            }
        } catch (Exception e) {
            Log.d("error_gps", String.valueOf(e));
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        if (list.getVisibility() == View.VISIBLE) {
            list.setVisibility(View.GONE);
        }
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        if (list.getVisibility() == View.VISIBLE) {
            list.setVisibility(View.GONE);
        }
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        if (list.getVisibility() == View.VISIBLE) {
            list.setVisibility(View.GONE);
        }
        return true;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        if (list.getVisibility() == View.VISIBLE) {
            list.setVisibility(View.GONE);
        }
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        if (list.getVisibility() == View.VISIBLE) {
            list.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        if (list.getVisibility() == View.VISIBLE) {
            list.setVisibility(View.GONE);
        }
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        if (list.getVisibility() == View.VISIBLE) {
            list.setVisibility(View.GONE);
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        if (list.getVisibility() == View.VISIBLE) {
            list.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        if (list.getVisibility() == View.VISIBLE) {
            list.setVisibility(View.GONE);
        }
        return true;
    }


}
