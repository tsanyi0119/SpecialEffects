package com.example.specialeffects;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.TextureView;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private TextureView textureView;
    private EffectsVideoPlayer effectsVideoPlayer;
    private ScrollAnimation scrollAnimation;
    private ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textureView = findViewById(R.id.textureView);
        effectsVideoPlayer = new EffectsVideoPlayer(this,textureView);



        //--- scrollView ---
        scrollView = findViewById(R.id.scrollView);
        scrollAnimation = new ScrollAnimation();
        scrollAnimation.setScrollView(scrollView);

        //初始化scrollAnimation，並且給予第一個動畫
        scrollAnimation.addOnGlobalLayoutListener(scrollAnimation.S5_Animation);

        //有初始化scrollAnimation過後的動畫都用這個方法
//        scrollAnimation.startAnimation(scrollAnimation.S2_Animation);
    }
}
