package com.example.specialeffects;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.view.Surface;
import android.view.TextureView;

public class EffectsVideoPlayer {

    private MediaPlayer mediaPlayer;
    private TextureView textureView;
    private Context context;
    private int effectSelect;
    public final int video_effect_1 = 1;
    public final int video_effect_2 = 2;
    public final int video_effect_3 = 3;

    public EffectsVideoPlayer(Context context, TextureView textureView) {
        this.textureView = textureView;
        this.context = context;
    }

    public void addEffectsVideoListener(int effectSelect){
        this.effectSelect = effectSelect;
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
                Surface surface = new Surface(surfaceTexture);
                prepareMediaPlayer();
                mediaPlayer.setSurface(surface);
                mediaPlayer.start();
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                // Handle size change if needed
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                mediaPlayer.stop();
                mediaPlayer.release();
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
                // Update if needed
            }
        });
    }

    // 初始化和設定 MediaPlayer，準備播放視頻
    private void prepareMediaPlayer() {
        try {
            mediaPlayer = new MediaPlayer();
            AssetFileDescriptor afd = null;

            switch (effectSelect) {
                case video_effect_1:
                    afd = context.getResources().openRawResourceFd(R.raw.video_effect_1);
                    break;
                case video_effect_2:
                    afd = context.getResources().openRawResourceFd(R.raw.video_effect_2);
                    break;
                case video_effect_3:
                    afd = context.getResources().openRawResourceFd(R.raw.video_effect_3);
                    break;
                default:
                    // 預設的處理邏輯
                    break;
            }

            if (afd != null) {
                mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                afd.close();
                mediaPlayer.setLooping(true);
                mediaPlayer.prepare();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 暫停播放
    public void pauseVideo() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    // 繼續播放
    public void resumeVideo() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    // 停止並釋放 MediaPlayer 資源
    public void releaseResources() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
