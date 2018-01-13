package com.example.hp.alert;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Modify2 extends Activity {
    EditText name, number;
    String conName, conNumber;
    ImageButton addContact, update;
    int index;
    SQLiteDatabase database;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try {
            if (requestCode == 1) {
                Log.d("main3", "Inside On activity");
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    String[] proj = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
                    Cursor cursor = getContentResolver().query(uri, proj, null, null, null);

                    if (cursor.moveToFirst()) {
                        int num_index = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        conNumber = cursor.getString(num_index);

                        int name_index = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                        conName = cursor.getString(name_index);

                        name.setText(conName);

                        number.setText(conNumber);
                    }
                    cursor.close();
                }
            }
        } catch (Exception e) {
            Log.d("error_activity_result", String.valueOf(e));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify2);

        name = (EditText) findViewById(R.id.name_mod2);
        number = (EditText) findViewById(R.id.num_mod2);
        addContact = (ImageButton) findViewById(R.id.addContact);
        update = (ImageButton) findViewById(R.id.update_btn);

        conName = name.getText().toString();
        conNumber = number.getText().toString();

        database = openOrCreateDatabase("DB_contacts", Context.MODE_PRIVATE, null);

        Bundle bundle = getIntent().getExtras();

        if (bundle == null) {
            index = Integer.parseInt(null);
        } else {
            index = bundle.getInt("index");
        }

        Cursor d = database.rawQuery("SELECT flag FROM myTable", null);
        Log.d("edit", "Starting cursor");

        if (d.getCount() > 0) {
            Log.d("edit", "inside cursor");
            while (d.moveToNext()) {
                String cursors = d.getString(0);

                Log.d("flags", cursors + " ");

            }
        } else {
            Toast.makeText(Modify2.this, "kch nhi hua ", Toast.LENGTH_SHORT).show();
        }
        d.close();

        switch (index) {
            case 0: {
                try {
                    Cursor c = database.rawQuery("SELECT * FROM myTable WHERE flag=0 ", null);
                    Log.d("edit", "Starting cursor");

                    if (c.getCount() > 0) {
                        Log.d("edit", "inside cursor");
                        while (c.moveToNext()) {
                            String Name = c.getString(0);
                            String Num = c.getString(1);
                            Log.d("edit", "Got the names ");
                            Log.d("Name", Name);
                            Log.d("Num", Num);
                            Log.d("edit", "Sending names ");
                            name.setText(Name);
                            number.setText(Num);
                        }
                    } else {
                        Toast.makeText(Modify2.this, "kch nhi hua ", Toast.LENGTH_SHORT).show();
                    }
                    c.close();

                } catch (Exception e) {
                    Log.d("error", String.valueOf(e));
                }
            }
            break;
            case 1: {
                try {
                    Cursor c = database.rawQuery("SELECT * FROM myTable WHERE flag =1", null);
                    Log.d("edit", "Starting cursor");

                    if (c.getCount() > 0) {
                        Log.d("edit", "inside cursor");
                        while (c.moveToNext()) {
                            String Name = c.getString(0);
                            String Num = c.getString(1);
                            Log.d("edit", "Got the names ");
                            Log.d("Name", Name);
                            Log.d("Num", Num);
                            Log.d("edit", "Sending names ");
                            name.setText(Name);
                            number.setText(Num);
                        }
                    } else {
                        Toast.makeText(Modify2.this, "kch nhi hua ", Toast.LENGTH_SHORT).show();
                    }
                    c.close();

                } catch (Exception e) {
                    Log.d("error", String.valueOf(e));
                }
            }
            break;
            case 2: {
                try {
                    Cursor c = database.rawQuery("SELECT * FROM myTable WHERE flag=2", null);
                    Log.d("edit", "Starting cursor");

                    if (c.getCount() > 0) {
                        Log.d("edit", "inside cursor");
                        while (c.moveToNext()) {
                            String Name = c.getString(0);
                            String Num = c.getString(1);
                            Log.d("edit", "Got the names ");
                            Log.d("Name", Name);
                            Log.d("Num", Num);
                            Log.d("edit", "Sending names ");
                            name.setText(Name);
                            number.setText(Num);
                        }
                    } else {
                        Toast.makeText(Modify2.this, "kch nhi hua ", Toast.LENGTH_SHORT).show();
                    }
                    c.close();

                } catch (Exception e) {
                    Log.d("error", String.valueOf(e));
                }
            }
            break;
            case 3: {
                try {
                    Cursor c = database.rawQuery("SELECT * FROM myTable WHERE flag=3", null);
                    Log.d("edit", "Starting cursor");

                    if (c.getCount() > 0) {
                        Log.d("edit", "inside cursor");
                        while (c.moveToNext()) {
                            String Name = c.getString(0);
                            String Num = c.getString(1);
                            Log.d("edit", "Got the names ");
                            Log.d("Name", Name);
                            Log.d("Num", Num);
                            Log.d("edit", "Sending names ");
                            name.setText(Name);
                            number.setText(Num);
                        }
                    } else {
                        Toast.makeText(Modify2.this, "kch nhi hua ", Toast.LENGTH_SHORT).show();
                    }
                    c.close();

                } catch (Exception e) {
                    Log.d("error", String.valueOf(e));
                }
            }
            break;
            case 4: {
                try {
                    Cursor c = database.rawQuery("SELECT * FROM myTable WHERE flag=4", null);
                    Log.d("edit", "Starting cursor");

                    if (c.getCount() > 0) {
                        Log.d("edit", "inside cursor");
                        while (c.moveToNext()) {
                            String Name = c.getString(0);
                            String Num = c.getString(1);
                            Log.d("edit", "Got the names ");
                            Log.d("Name", Name);
                            Log.d("Num", Num);
                            Log.d("edit", "Sending names ");
                            name.setText(Name);
                            number.setText(Num);
                        }
                    } else {
                        Toast.makeText(Modify2.this, "kch nhi hua ", Toast.LENGTH_SHORT).show();
                    }
                    c.close();

                } catch (Exception e) {
                    Log.d("error", String.valueOf(e));
                }
            }
            break;
        }

        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent ints = new Intent();
                    ints.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                    ints.setAction(ints.ACTION_PICK);
                    startActivityForResult(ints.createChooser(ints, "Select Contact"), 1);
                } catch (Exception e) {
                    Log.d("error_retrieve_contact", String.valueOf(e));
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String conName, conNumber;
                conName = name.getText().toString();
                conNumber = number.getText().toString();
                Log.d("update", "inside on click Listener");
                database.execSQL("UPDATE myTable SET name ='" + conName + "',number='" + conNumber + "'WHERE flag ='" + index + "';");
                Toast.makeText(Modify2.this, "Update Done", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
