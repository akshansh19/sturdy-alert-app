package com.example.hp.alert;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class Info_Contacts extends Activity {
    EditText about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info__contacts);

        about = (EditText) findViewById(R.id.editText);

    }
}
