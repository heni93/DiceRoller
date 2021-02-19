package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView imageViewDice;
    public static final Random rng = new Random();
    private SoundPool soundPool;
    private int soundDice;
    boolean loaded = false;


    public static int rollDice() {

        return rng.nextInt(6) + 1;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });

        soundDice = soundPool.load(this, R.raw.rattle, 1);

        imageViewDice = (ImageView) findViewById(R.id.image_view_dice);

        imageViewDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Animation anim1 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);

                final Animation.AnimationListener animationListener = new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        int value = rollDice();
                        int res = getResources().getIdentifier("dice_" + value, "drawable", "com.example.diceroller");

                        if (animation == anim1) {
                            imageViewDice.setImageResource(res);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                };

                anim1.setAnimationListener(animationListener);
                imageViewDice.startAnimation(anim1);

                soundPool.play(soundDice, 1, 1, 1, 0, 1);
            }
        });


    }

    
}