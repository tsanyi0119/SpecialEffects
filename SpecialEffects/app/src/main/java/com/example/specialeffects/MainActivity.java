package com.example.specialeffects;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.TextureView;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextureView textureView;
    private EffectsVideoPlayer effectsVideoPlayer;
    private ScrollAnimation scrollAnimation;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //--- effectsVideoPlayer ---
        textureView = findViewById(R.id.textureView);
        effectsVideoPlayer = new EffectsVideoPlayer(this,textureView);

        //初始化effectsVideoPlayer，並且給予特效 參數1:EffectsVideoPlayer、參數2:特效風格
        effectsVideoPlayer.addEffectsVideoListener(effectsVideoPlayer.video_effect_1);

        //暫停播放
        effectsVideoPlayer.pauseVideo();

        //繼續播放
        effectsVideoPlayer.resumeVideo();


        //--- scrollView ---
        scrollView = findViewById(R.id.scrollView);
        scrollAnimation = new ScrollAnimation();
        scrollAnimation.setScrollView(scrollView);

        //初始化scrollAnimation，並且給予第一個動畫 參數1:模式、參數2:動畫時間(秒)
        scrollAnimation.addOnGlobalLayoutListener(scrollAnimation.S5_Animation,10);

        //有初始化scrollAnimation過後的動畫都用這個方法 參數1:模式、參數2:動畫時間(秒)
        scrollAnimation.startAnimation(scrollAnimation.S2_Animation,10);

        //停止動畫
        scrollAnimation.pauseAnimation();

        //繼續動畫
        scrollAnimation.resumeAnimation();
    }
}
