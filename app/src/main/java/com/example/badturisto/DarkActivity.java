package com.example.badturisto;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * Created by Mahon on 04.08.2015.
 */
public class DarkActivity extends Activity {
    private LinearLayout mBackgroundColour;
    private String output;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dark_activity);
        output = "..-.";
        mBackgroundColour = (LinearLayout) this.findViewById(R.id.colour_layout);
        View view = new View(this);
        view.getSolidColor();
        //DisplayPlaying displayPlaying = new DisplayPlaying();
        //displayPlaying.start();
        mBackgroundColour.setBackgroundColor(Color.WHITE);
        try {
            TimeUnit.MILLISECONDS.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mBackgroundColour.setBackgroundColor(Color.BLACK);
    }
    class DisplayPlaying extends Thread{
        public void display() {
            for (int i = 0; i < output.length(); i++) {
                String equal = Character.toString(output.charAt(i));
                if (equal.equals("-")) {
                    mBackgroundColour.setBackgroundColor(Color.WHITE);
                    try {
                        TimeUnit.MILLISECONDS.sleep(MainActivity.DASH);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mBackgroundColour.setBackgroundColor(Color.BLACK);
                    try {
                        TimeUnit.MILLISECONDS.sleep(MainActivity.BETWEEN_LATER);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (equal.equals(".")) {
                    mBackgroundColour.setBackgroundColor(Color.WHITE);
                    try {
                        TimeUnit.MILLISECONDS.sleep(MainActivity.DOT);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mBackgroundColour.setBackgroundColor(Color.BLACK);
                    try {
                        TimeUnit.MILLISECONDS.sleep(MainActivity.BETWEEN_LATER);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (equal.equals("/")) {
                    mBackgroundColour.setBackgroundColor(Color.BLACK);
                    try {
                        TimeUnit.MILLISECONDS.sleep(MainActivity.SPACE);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        @Override
        public void run() {
            if(MainActivity.getRepeatCheck()==true){
                for(;;){
                    display();
                }
            }else{
                display();
            }
        }
    }

}