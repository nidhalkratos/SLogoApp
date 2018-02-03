package com.freeways.slogoapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorChangedListener;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.slider.LightnessSlider;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    int red = 255, green = 255, blue = 255;
    String serverAddress = "http://192.168.1.110";
    TextView redText, greenText, blueText, freeways;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Typeface font = Typeface.createFromAsset(getAssets(), "sawasdee.ttf");
        freeways = (TextView) findViewById(R.id.Freeways);
        freeways.setTypeface(font);

        SharedPreferences sharedPref = getSharedPreferences(
                "settings", Context.MODE_PRIVATE);
        serverAddress = sharedPref.getString("serverAddress",serverAddress);



        redText = (TextView) findViewById(R.id.text_red);
        greenText = (TextView) findViewById(R.id.text_green);
        blueText = (TextView) findViewById(R.id.text_blue);

        final ColorPickerView colorPickerView = (ColorPickerView) findViewById(R.id.color_picker_view);

        final LightnessSlider lightnessSlider = (LightnessSlider) findViewById(R.id.v_lightness_slider);
        colorPickerView.addOnColorChangedListener(new OnColorChangedListener() {
            @Override
            public void onColorChanged(int selectedColor) {
                colorchanged(selectedColor);

                // Handle on color change
                Log.d("ColorPicker", "onColorChanged: 0x" + Integer.toHexString(selectedColor));
            }
        });
        colorPickerView.addOnColorSelectedListener(new OnColorSelectedListener() {
            @Override
            public void onColorSelected(int selectedColor) {
                colorchanged(selectedColor);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = serverAddress + "/api.php?set=0&red=" + red + "&green=" + green + "&blue=" + blue;

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //Snackbar.make(view, "RGB     :    ( " + response.replace(" ", ", ") + " )", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar.make(view, "Error", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                });
                queue.add(stringRequest);


            }
        });

        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent settingsIntent = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(settingsIntent);
                SharedPreferences sharedPref = getSharedPreferences(
                        "settings", Context.MODE_PRIVATE);
                serverAddress = sharedPref.getString("serverAddress",serverAddress);

                return true;
            }
        });
    }

    public void colorchanged(int selectedColor) {
        freeways.setTextColor(selectedColor);
        red = Color.red(selectedColor);
        green = Color.green(selectedColor);
        blue = Color.blue(selectedColor);
        redText.setText("" + red);
        greenText.setText("" + green);
        blueText.setText("" + blue);
    }


}
