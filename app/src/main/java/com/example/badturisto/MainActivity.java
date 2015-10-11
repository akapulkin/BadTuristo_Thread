package com.example.badturisto;


import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class MainActivity extends Activity {
    private Camera camera;
    private static TextView outputMorze;
    private EditText inputText;
    private Boolean lightCheck = false;
    private Boolean soundCheck = false;
    private Boolean displayCheck = false;
    private static Boolean repeatCheck = false;
    MediaPlayer mediaPlayer ;
    private String outputDirty;
    final static int DOT = 200;
    final static int DASH = 600;
    final static int SPACE = 1400;
    final static int BETWEEN_LATER = 600;
    final static int BETWEEN_SIGN = 200;
    SoundPlaying soundPlayer = new SoundPlaying();
    FlashLightning flafLight = new FlashLightning();
    public static TextView getOutputMorze() {
        return outputMorze;
    }


    public static Boolean getRepeatCheck() {
        return repeatCheck;
    }


    final String LOG_TAG = "myLogs";
    private volatile boolean mIsInterupt = false;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToggleButton mSoundCheck = (ToggleButton) findViewById(R.id.soundToggleButton);
        ToggleButton mLightCheck = (ToggleButton) findViewById(R.id.flashToggleButton);
        ToggleButton mScreenCheck = (ToggleButton) findViewById(R.id.displayToggleButton);
        ToggleButton mRepeatCheck = (ToggleButton) findViewById(R.id.repeatToggleButton);
        inputText = (EditText) this.findViewById(R.id.editText);
        outputMorze = (TextView) this.findViewById(R.id.textView);
        ImageButton mMorseButton = (ImageButton) this.findViewById(R.id.morseButton);
        ImageButton mPlayButton = (ImageButton) this.findViewById(R.id.playButton);
        ImageButton mStopButton = (ImageButton) this.findViewById(R.id.stopButton);
        ImageButton mLayout2Button = (ImageButton) this.findViewById(R.id.layout2);
        //mLayout2Button.setOnClickListener(onClickLayoutListener);
        mMorseButton.setOnClickListener(onClickMorseListener);
        mPlayButton.setOnClickListener(onClickPlayListener);
        mStopButton.setOnClickListener(onClickStopListener);

        mSoundCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                soundCheck = isChecked;
            }
        });
        mLightCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                lightCheck = isChecked;
            }
        });
        mScreenCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                displayCheck = isChecked;
            }
        });
        mRepeatCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                repeatCheck = isChecked;
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMP();
        //camera.release();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener onClickPlayListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(soundPlayer.isAlive()|flafLight.isAlive()){
                mIsInterupt=true;
            }
            if (v.getId() == R.id.playButton) {
                if (soundCheck == true) {
                    soundPlayer.start();
                    //releaseMP();
                }
                if (lightCheck == true) {
                    flafLight.start();
                }
/*                if (displayCheck == true) {
                    Intent intent = new Intent(MainActivity.this, DarkActivity.class);
                    startActivity(intent);
                }*/
            }
        }
    };
    View.OnClickListener onClickStopListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.stopButton) {
                mIsInterupt=true;
                //soundPlayer.interrupt();
                //flafLight.interrupt();

  /*              if(mediaPlayer!=null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }*/
            }
        }
    };
/*    View.OnClickListener onClickLayoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, DarkActivity.class);
            startActivity(intent);
        }
    };*/

    class SoundPlaying extends Thread{

        public void sound() {
            if (outputDirty != null) {
                for (int i = 0; i < outputDirty.length(); i++) {
                    String equal = Character.toString(outputDirty.charAt(i));
                    if (!mIsInterupt) {
                        if (equal.equals("-")) {
                            playMediaSound(DASH);
                            try {
                                TimeUnit.MILLISECONDS.sleep(BETWEEN_SIGN);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                        if (equal.equals(".")) {
                            playMediaSound(DOT);
                            try {
                                TimeUnit.MILLISECONDS.sleep(BETWEEN_SIGN);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                        if (equal.equals("/")) {
                            try {
                                TimeUnit.MILLISECONDS.sleep(SPACE);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                        if (equal.equals("|")) {
                            try {
                                TimeUnit.MILLISECONDS.sleep(BETWEEN_LATER);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                    } else
                        return;
                }
                releaseMP();
            }
            return;
        }
        @Override
        public void run() {
                if (repeatCheck == true) {
                    for (; ; ) {
                        if (!SoundPlaying.interrupted()) {
                            sound();
                        }else {
                            return;
                        }
                    }

                }else {
                    if (!SoundPlaying.interrupted()) {
                    sound();
                    } else {
                        return;
                    }
                }
            Log.d(LOG_TAG, "Thread must ended!!!!!!!! MF");
        }
    }
    class FlashLightning extends Thread{
        public void flash(){
            if (camera == null) {
                camera = Camera.open();
            }
            final Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            camera.startPreview();
            if(outputDirty!=null) {
                for (int i = 0; i < outputDirty.length(); i++) {
                    String equal = Character.toString(outputDirty.charAt(i));
                    if (!Thread.interrupted()) {
                        if (equal.equals("-")) {
                            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                            camera.setParameters(parameters);
                            camera.startPreview();
                            try {
                                TimeUnit.MILLISECONDS.sleep(DASH);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return;
                            }
                            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                            camera.setParameters(parameters);
                            camera.startPreview();
                            try {
                                TimeUnit.MILLISECONDS.sleep(BETWEEN_SIGN);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                    } else {
                        return;
                    }
                    if (!mIsInterupt) {
                        if (equal.equals(".")) {
                            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                            camera.setParameters(parameters);
                            camera.startPreview();
                            try {
                                TimeUnit.MILLISECONDS.sleep(DOT);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return;
                            }
                            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                            camera.setParameters(parameters);
                            camera.startPreview();
                            try {
                                TimeUnit.MILLISECONDS.sleep(BETWEEN_SIGN);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                    } else {
                        return;
                    }
                    if (!mIsInterupt) {
                        if (equal.equals("/")) {

                            try {
                                TimeUnit.MILLISECONDS.sleep(SPACE);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                    }else {
                        return;
                    }
                    if (!mIsInterupt) {
                        if (equal.equals("|")) {

                            try {
                                TimeUnit.MILLISECONDS.sleep(BETWEEN_LATER);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                    } else {
                        return;
                    }
                }
            }else
                return;
        }
        @Override
        public void run() {
            if (!Thread.interrupted()) {
                if (repeatCheck == true) {
                    for (; ; ) {
                        flash();
                    }
                } else {
                    flash();
                }
            }else {
                return;
            }
        }
    }



    View.OnClickListener onClickMorseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.morseButton) {
                morzing(inputText);
            }
        }
    };

    private void morzing(EditText iT) {
        Locale.setDefault(Locale.ENGLISH);
        String input = iT.getText().toString();
        String outputDirty1 = "";
        MorzeDictionary dict = new MorzeDictionary();
        HashMap<Character, String> dictionary = dict.getDictionary();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            String b = dictionary.get(Character.toLowerCase(c));
            outputDirty1 = outputDirty1 + b;
            outputDirty1 = outputDirty1 + "|";
        }
        outputDirty=outputDirty1;
        String outputClean = "";
        for (int i = 0; i < outputDirty.length(); i++) {
            char c = outputDirty.charAt(i);
            if (c == '/'){
                outputClean = outputClean + ' ';
            }if (c == '-') {
                outputClean = outputClean + '-';
            }if (c == '.') {
                outputClean = outputClean + '.';
            }
        }
        outputMorze.setText(outputClean);
    }

    private void playMediaSound(int timer) {
       /* if(mediaPlayer!=null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        releaseMP();*/
        if(!soundPlayer.isInterrupted()) {
            Log.d(LOG_TAG, "start Raw");
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, R.raw.censorbeep);
            }
            mediaPlayer.start();
            try {
                TimeUnit.MILLISECONDS.sleep(timer);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.d(LOG_TAG, "Piik aborted");
                return;
            }
            releaseMP();
            Log.d(LOG_TAG, "Piik ended");
        }else{
            return;
        }
    }
    private void releaseMP() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /*class DisplayPlaying extends Thread{
        public void display() {
            for (int i = 0; i < outputMorze.getText().toString().length(); i++) {
                String equal = Character.toString(outputMorze.getText().toString().charAt(i));
                if (equal.equals("-")) {
                    WindowManager.LayoutParams params = getWindow().getAttributes();
                    params.screenBrightness = 0.1f;
                    getWindow().setAttributes(params);
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (equal.equals(".")) {
                    WindowManager.LayoutParams params = getWindow().getAttributes();
                    params.screenBrightness = 0.1f;
                    getWindow().setAttributes(params);
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (equal.equals("/")) {

                    try {
                        TimeUnit.MILLISECONDS.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        @Override
        public void run() {
            if(repeatCheck==true){
                for(;;){
                    display();
                }
            }else{
                display();
            }
        }
    }*/
}





