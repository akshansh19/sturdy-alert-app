package com.example.hp.alert;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Modify extends Activity {

    SQLiteDatabase database;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        try {
            database = openOrCreateDatabase("DB_contacts", Context.MODE_PRIVATE, null);

            listView = (ListView) findViewById(R.id.contact_list);

            ArrayList<String> arrayList = new ArrayList<>();

            Cursor c = database.rawQuery("SELECT * FROM myTable ", null);

            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    String data = c.getString(0) + " " + c.getString(1);
                    arrayList.add(data);
                    ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
                    listView.setAdapter(listAdapter);
                }
            } else {
                Toast.makeText(Modify.this, "Table Empty", Toast.LENGTH_SHORT).show();
            }
            c.close();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(Modify.this, Modify2.class);
                    intent.putExtra("index", i);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            Log.d("Error", String.valueOf(e));
        }
    }
}
