package com.example.hp.alert;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Display_once extends Activity {

    EditText name, number;
    ImageView toInfo;
    ImageButton addContact, add, done;
    String con_number, con_name, table_name = "myTable", db_name = "DB_contacts";
    SQLiteDatabase myDB;
    int index = 0, count = 0;
    TextView counter;
    Button clear;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

            if (requestCode == 1) {
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String proj[] = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER};
                    Cursor cursor = getContentResolver().query(uri, proj, null, null, null);

                    if (cursor.moveToFirst()) {
                        int num_index = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        con_number = cursor.getString(num_index);

                        int name_index = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                        con_name = cursor.getString(name_index);

                        name.setText(con_name);
                        number.setText(con_number);
                    }
                    cursor.close();
                }
            }

        } catch (Exception e) {
            Log.d("error_browse_phonebook", String.valueOf(e));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_once);
        try {
            name = (EditText) findViewById(R.id.name);
            number = (EditText) findViewById(R.id.number);
            toInfo = (ImageView) findViewById(R.id.toInfo);
            addContact = (ImageButton) findViewById(R.id.add_con_img);
            add = (ImageButton) findViewById(R.id.add_btn);
            done = (ImageButton) findViewById(R.id.btn_done);
            counter = (TextView) findViewById(R.id.counter);

            done.setVisibility(View.GONE);

            myDB = openOrCreateDatabase("DB_contacts", Context.MODE_PRIVATE, null);
            Log.d("db", "db created");

            myDB.execSQL("CREATE TABLE IF NOT EXISTS " + table_name + "( name VARCHAR,number INTEGER ,flag INTEGER)");
            Log.d("db", "table created");

        } catch (Exception e) {
            Log.d("error_declarations", String.valueOf(e));
        }
        //for executing this page only once
        try {
            SharedPreferences preferences = getSharedPreferences("prefer", Context.MODE_PRIVATE);
            if (preferences.getBoolean("executed", false)) {
                Intent intent = new Intent(Display_once.this, SendMessage.class);
                startActivity(intent);
                finish();
            } else {
                SharedPreferences.Editor ed = preferences.edit();
                ed.putBoolean("executed", true);
                ed.commit();
            }
        } catch (Exception e) {
            Log.d("error_shared_pref", String.valueOf(e));
        }

        toInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Display_once.this, Info_Contacts.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.d("error_toInfo", String.valueOf(e));
                }
            }
        });

        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent();
                    intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                    intent.setAction(intent.ACTION_PICK);
                    startActivityForResult(intent.createChooser(intent, "Select Contact"), 1);
                } catch (Exception e) {
                    Log.d("error_addContact", String.valueOf(e));
                }
            }
        });

        //temporary ... . . . ...
       /* clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDB.execSQL("DELETE FROM myTable;");
                Toast.makeText(Display_once.this, "Data cleared", Toast.LENGTH_SHORT).show();
            }
        });
*/
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Display_once.this, SendMessage.class);
                    intent.putExtra("databaseName", db_name);
                    intent.putExtra("tableName", table_name);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.d("error_done", String.valueOf(e));
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (count == 5) {
                        Toast.makeText(Display_once.this, "Cannot insert more than 5 contacts", Toast.LENGTH_SHORT).show();
                        add.setOnClickListener(null);
                        done.setVisibility(View.VISIBLE);
                        add.setVisibility(View.INVISIBLE);
                    } else {
                        String con_name = name.getText().toString();
                        String con_number = number.getText().toString();
                        //increment counter for displaying no of contacts inserted.
                        count++;
                        myDB.execSQL("INSERT INTO myTable VALUES('" + con_name + "','" + con_number + "','" + index + "');");
                        Log.d("db", "value inserted in table");
                        index++;
                        //adding text in TextView.
                        counter.setText(count + "of 5");
                    }
                    Log.d("main3", "data inserted in db");

                } catch (Exception e) {
                    Log.d("error_Add", String.valueOf(e));
                }
            }
        });

    }
}