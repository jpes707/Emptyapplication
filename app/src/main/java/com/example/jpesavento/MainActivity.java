package com.example.jpesavento;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import java.lang.Runnable;
import java.util.HashMap;

import android.text.Layout;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private SeekBar mSeekBar;
    private TextView t, name, hello;
    private int[] ids;
    private HashMap<Integer, Integer> cornerCounts, corners;
    private Handler handler;
    private ConstraintLayout lay;
    private EditText box;
    private String[] messages;
    private int count;
    int delay = 100; //milliseconds
    int color = (int) (Math.random() * 16777216);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.btn_hello);
        t = findViewById(R.id.main_label);
        lay = findViewById(R.id.layout);
        name = findViewById(R.id.name_label);
        box = findViewById(R.id.editText);
        hello = findViewById(R.id.hello_label);
        int[] ids = {R.id.corner1, R.id.corner2, R.id.corner3, R.id.corner4};
        cornerCounts = new HashMap<Integer, Integer>();
        corners = new HashMap<Integer, Integer>();
        mSeekBar = findViewById(R.id.seekBar);

        for(int i = 0; i < 4; i++) {
            cornerCounts.put(ids[i], 0);
            corners.put(ids[i], i + 1);
        }

        Log.i("myTag", cornerCounts.toString());

        //{findViewById(R.id.corner1), findViewById(R.id.corner2), findViewById(R.id.corner3), findViewById(R.id.corner4)};

        Resources resources = getResources();
        messages = resources.getStringArray(R.array.user_messages);
        count = 0;

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("myTag", "Button pressed");
                Log.i("myTag", Integer.toHexString(color));
                t.setText("Button clicked " + ++count + " times.");
                if(!box.getText().equals("")) {
                    box.setVisibility(View.GONE);
                    name.setVisibility(View.GONE);
                    hello.setText(messages[count % 5] + ", " + box.getText() + "!");
                }
                else {
                    hello.setText(messages[count % 5] + "!");
                }
                //System.exit(0);
            }
        });

        View.OnClickListener cornersListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentCount = cornerCounts.get(v.getId()) + 1;
                cornerCounts.put(v.getId(), currentCount);
                Toast toast = Toast.makeText(getApplicationContext(), "Button " + corners.get(v.getId()) + " toasted " + currentCount + " times.", Toast.LENGTH_SHORT);
                toast.show();
            }
        };

        for(int c : cornerCounts.keySet()) {
            findViewById(c).setOnClickListener(cornersListener);
        }

        SeekBar.OnSeekBarChangeListener seekListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // Log the progress
                Log.i("myTag", "Progress is: " + progress);
                //set textView's text
                t.setTextSize((int)(progress / 250.0 * 20));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        };

        mSeekBar.setOnSeekBarChangeListener(seekListener);

        handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                //do something
                //int[] rands = {(int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)};
                int[] rgb = {(color / 65536) % 256, (color / 256) % 256, color % 256};
                //Log.i("myTag", rgb[0] + " " + rgb[1] + " " + rgb[2]);
                t.setTextColor(Color.rgb(rgb[0], rgb[1], rgb[2]));
                name.setTextColor(Color.rgb(rgb[0], rgb[1], rgb[2]));
                hello.setTextColor(Color.rgb(rgb[0], rgb[1], rgb[2]));
                mSeekBar.getProgressDrawable().setColorFilter(Color.rgb(rgb[0], rgb[1], rgb[2]), PorterDuff.Mode.MULTIPLY);
                lay.setBackgroundColor(Color.rgb(255 - rgb[0], 255 - rgb[1], 255 - rgb[2]));
                color += 1;
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    /*private int obtainColor(int color) {
        String s = Integer.toHexString(color);
        while(s.length() < 6) {
            s = "0" + s;
        }
        return Color.parseColor("#" + s);
    }*/
}
