package com.freeways.slogoapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {

    EditText editTextSettings;
    SharedPreferences sharedPref ;
    SharedPreferences.Editor editor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editTextSettings = (EditText) findViewById(R.id.settings_edit_text);
        sharedPref = getSharedPreferences("settings", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editTextSettings.setText(sharedPref.getString("serverAddress",""));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String serverAddress = editTextSettings.getText().toString();

                editor.putString("serverAddress",serverAddress);
                editor.commit();


                Snackbar.make(view, "Updated", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
