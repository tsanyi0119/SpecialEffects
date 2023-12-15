package com.example.specialeffects;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ScrollView;

import androidx.core.widget.NestedScrollView;

public class ScrollAnimation {

    NestedScrollView scrollView;
    private ValueAnimator animator , reverseAnimator; // 用來存儲當前運行的動畫

    public final int S1_Animation = 1;
    public final int S2_Animation = 2;
    public final int S3_Animation = 3;
    public final int S4_Animation = 4;
    public final int S5_Animation = 5;

    public void setScrollView(NestedScrollView scrollView) {
        this.scrollView = scrollView;
    }

    public void addOnGlobalLayoutListener(int animationSelect, int time) {
        // 監聽ScrollView內容的佈局完成事件
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // 在佈局完成後執行相關邏輯
                switch (animationSelect) {
                    case S1_Animation:
                        scroll_S1_Animation(time);
                        break;
                    case S2_Animation:
                        scroll_S2_Animation(time);
                        break;
                    case S3_Animation:
                        scroll_S3_Animation(time);
                        break;
                    case S4_Animation:
                        scroll_S4_Animation(time);
                        break;
                    case S5_Animation:
                        scroll_S5_Animation(time);
                        break;
                    default:
                }
            }
        });
    }

    public void startAnimation(int animationSelect, int time) {
        switch (animationSelect) {
            case S1_Animation:
                scroll_S1_Animation(time);
                break;
            case S2_Animation:
                scroll_S2_Animation(time);
                break;
            case S3_Animation:
                scroll_S3_Animation(time);
                break;
            case S4_Animation:
                scroll_S4_Animation(time);
                break;
            case S5_Animation:
                scroll_S5_Animation(time);
                break;
            default:
        }
    }

    // 暫停動畫
    public void pauseAnimation() {
        if (animator != null && animator.isRunning()) {
            animator.pause();
        }

        if (reverseAnimator != null && reverseAnimator.isRunning()) {
            reverseAnimator.pause();
        }
    }

    // 繼續動畫
    public void resumeAnimation() {
        if (animator != null && animator.isPaused()) {
            animator.resume();
        }

        if (reverseAnimator != null && reverseAnimator.isPaused()) {
            reverseAnimator.resume();
        }
    }

    // C>B>C
    private void scroll_S1_Animation(int time) {
        // 獲取ScrollView的內容高度
        final int scrollRange = scrollView.getChildAt(0).getHeight() - scrollView.getHeight();

        // 設置ScrollView一開始在中間
        scrollView.scrollTo(0, scrollRange / 2);

        // 創建ValueAnimator實例，用於模擬滾動動畫
        animator = ValueAnimator.ofInt(scrollRange / 2, scrollRange);
        animator.setDuration((time/2)*1000);
        animator.setInterpolator(new LinearInterpolator()); // 使用線性插值器

        // 設置動畫更新的監聽器
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 在動畫更新時，將ScrollView滾動到指定位置
                int value = (int) animation.getAnimatedValue();
                scrollView.scrollTo(0, value);
            }
        });

        // 設置動畫結束的監聽器
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // 在第一個動畫結束時開始第二個動畫
                start_S1_ReverseAnimation(time);

                // 移除監聽器
                animator.removeAllUpdateListeners();
                animator.removeAllListeners();
            }
        });

        // 開始動畫
        animator.start();
    }

    // 開始反向動畫
    private void start_S1_ReverseAnimation(int time) {
        // 獲取ScrollView的內容高度
        final int scrollRange = scrollView.getChildAt(0).getHeight() - scrollView.getHeight();

        // 創建ValueAnimator實例，用於模擬滾動動畫
        reverseAnimator = ValueAnimator.ofInt(scrollRange, scrollRange / 2);
        reverseAnimator.setDuration((time/2)*1000);
        reverseAnimator.setInterpolator(new LinearInterpolator()); // 使用線性插值器

        // 設置動畫更新的監聽器
        reverseAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 在動畫更新時，將ScrollView滾動到指定位置
                int value = (int) animation.getAnimatedValue();
                scrollView.scrollTo(0, value);
            }
        });

        // 設置動畫結束的監聽器
        reverseAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // 移除監聽器
                reverseAnimator.removeAllUpdateListeners();
                reverseAnimator.removeAllListeners();
            }
        });

        // 開始反向動畫
        reverseAnimator.start();
    }

    //--------------------------------------------------------------------------

    // C>B>C>T
    private void scroll_S2_Animation(int time) {
        // 獲取ScrollView的內容高度
        final int scrollRange = scrollView.getChildAt(0).getHeight() - scrollView.getHeight();

        // 設置ScrollView一開始在中間
        scrollView.scrollTo(0, scrollRange / 2);

        // 創建ValueAnimator實例，用於模擬滾動動畫
        final ValueAnimator animator = ValueAnimator.ofInt(scrollRange / 2, scrollRange);
        animator.setDuration((time/3)*1000);
        animator.setInterpolator(new LinearInterpolator()); // 使用線性插值器

        // 設置動畫更新的監聽器
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 在動畫更新時，將ScrollView滾動到指定位置
                int value = (int) animation.getAnimatedValue();
                scrollView.scrollTo(0, value);
            }
        });

        // 設置動畫結束的監聽器
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // 在第一個動畫結束時開始第二個動畫
                start_S2_ReverseAnimation(time);
            }
        });

        // 開始動畫
        animator.start();
//        currentAnimator = animator; // 記錄當前動畫
    }

    // 開始反向動畫
    private void start_S2_ReverseAnimation(int time) {
        // 獲取ScrollView的內容高度
        final int scrollRange = scrollView.getChildAt(0).getHeight() - scrollView.getHeight();

        // 創建ValueAnimator實例，用於模擬滾動動畫
        ValueAnimator reverseAnimator = ValueAnimator.ofInt(scrollRange, 0);
        reverseAnimator.setDuration((time/3)*2*1000); // 修改為與第一個動畫同樣的時間長度
        reverseAnimator.setInterpolator(new LinearInterpolator()); // 使用線性插值器

        // 設置動畫更新的監聽器
        reverseAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 在動畫更新時，將ScrollView滾動到指定位置
                int value = (int) animation.getAnimatedValue();
                scrollView.scrollTo(0, value);
            }
        });

        // 設置動畫結束的監聽器
        reverseAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // 移除監聽器
                reverseAnimator.removeAllUpdateListeners();
                reverseAnimator.removeAllListeners();
            }
        });

        // 開始反向動畫
        reverseAnimator.start();
//        currentAnimator = reverseAnimator; // 記錄當前動畫
    }

    //--------------------------------------------------------------------------

    // C>B
    private void scroll_S3_Animation(int time) {
        // 獲取ScrollView的內容高度
        final int scrollRange = scrollView.getChildAt(0).getHeight() - scrollView.getHeight();

        // 設置ScrollView一開始在中間
        scrollView.scrollTo(0, 0);

        // 創建ValueAnimator實例，用於模擬滾動動畫
        final ValueAnimator animator = ValueAnimator.ofInt(0, scrollRange / 2);
        animator.setDuration(time*1000);
        animator.setInterpolator(new LinearInterpolator()); // 使用線性插值器

        // 設置動畫更新的監聽器
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 在動畫更新時，將ScrollView滾動到指定位置
                int value = (int) animation.getAnimatedValue();
                scrollView.scrollTo(0, value);
            }
        });

        // 設置動畫結束的監聽器
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // 移除監聽器
                animator.removeAllUpdateListeners();
                animator.removeAllListeners();
            }
        });

        // 開始動畫
        animator.start();
//        currentAnimator = animator; // 記錄當前動畫
    }

    //--------------------------------------------------------------------------

    // T>B
    private void scroll_S4_Animation(int time) {
        // 獲取ScrollView的內容高度
        final int scrollRange = scrollView.getChildAt(0).getHeight() - scrollView.getHeight();

        // 設置ScrollView一開始在頂部
        scrollView.scrollTo(0, 0);

        // 創建ValueAnimator實例，用於模擬滾動動畫
        final ValueAnimator animator = ValueAnimator.ofInt(0, scrollRange);
        animator.setDuration(time*1000);
        animator.setInterpolator(new LinearInterpolator()); // 使用線性插值器

        // 設置動畫更新的監聽器
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 在動畫更新時，將ScrollView滾動到指定位置
                int value = (int) animation.getAnimatedValue();
                scrollView.scrollTo(0, value);
            }
        });

        // 設置動畫結束的監聽器
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // 移除監聽器
                animator.removeAllUpdateListeners();
                animator.removeAllListeners();
            }
        });

        // 暫停和繼續功能
        animator.addPauseListener(new Animator.AnimatorPauseListener() {
            @Override
            public void onAnimationPause(Animator animation) {
                // 在動畫暫停時的邏輯處理
            }

            @Override
            public void onAnimationResume(Animator animation) {
                // 在動畫繼續時的邏輯處理
            }
        });

        // 開始動畫
        animator.start();
//        currentAnimator = animator; // 記錄當前動畫
    }

    //--------------------------------------------------------------------------

    // B>T
    private void scroll_S5_Animation(int time) {
        // 獲取ScrollView的內容高度
        final int scrollRange = scrollView.getChildAt(0).getHeight() - scrollView.getHeight();

        // 設置ScrollView一開始在底部
        scrollView.scrollTo(0, scrollRange);

        // 創建ValueAnimator實例，用於模擬滾動動畫
        final ValueAnimator animator = ValueAnimator.ofInt(scrollRange, 0);
        animator.setDuration(time*1000);
        animator.setInterpolator(new LinearInterpolator()); // 使用線性插值器

        // 設置動畫更新的監聽器
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 在動畫更新時，將ScrollView滾動到指定位置
                int value = (int) animation.getAnimatedValue();
                scrollView.scrollTo(0, value);
            }
        });

        // 設置動畫結束的監聽器
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // 在動畫結束時，移除監聽器
                animator.removeAllUpdateListeners();
                animator.removeAllListeners();
            }
        });

        // 開始動畫
        animator.start();
//        currentAnimator = animator; // 記錄當前動畫
    }




//    // C>B>C>T
//    private void scroll_S2_Animation() {
//        // 獲取ScrollView的內容高度
//        final int scrollRange = scrollView.getChildAt(0).getHeight() - scrollView.getHeight();
//
//        // 設置ScrollView一開始在中間
//        scrollView.scrollTo(0, scrollRange / 2);
//
//        // 創建ValueAnimator實例，用於模擬滾動動畫
//        final ValueAnimator animator = ValueAnimator.ofInt(scrollRange / 2, scrollRange);
//        animator.setDuration(10000); // 前10秒內滾動到底部
//        animator.setInterpolator(new LinearInterpolator()); // 使用線性插值器
//
//        // 設置動畫更新的監聽器
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                // 在動畫更新時，將ScrollView滾動到指定位置
//                int value = (int) animation.getAnimatedValue();
//                scrollView.scrollTo(0, value);
//            }
//        });
//
//        // 設置動畫結束的監聽器
//        animator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                // 在第一個動畫結束時開始第二個動畫
//                start_S2_ReverseAnimation();
//            }
//        });
//
//        // 開始動畫
//        animator.start();
//    }
//
//    // 開始反向動畫
//    private void start_S2_ReverseAnimation() {
//        // 獲取ScrollView的內容高度
//        final int scrollRange = scrollView.getChildAt(0).getHeight() - scrollView.getHeight();
//
//        // 創建ValueAnimator實例，用於模擬滾動動畫
//        ValueAnimator reverseAnimator = ValueAnimator.ofInt(scrollRange, scrollRange / 2);
//        reverseAnimator.setDuration(10000); // 接下來的10秒內滾動回中間
//        reverseAnimator.setInterpolator(new LinearInterpolator()); // 使用線性插值器
//
//        // 設置動畫更新的監聽器
//        reverseAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                // 在動畫更新時，將ScrollView滾動到指定位置
//                int value = (int) animation.getAnimatedValue();
//                scrollView.scrollTo(0, value);
//            }
//        });
//
//        // 設置動畫結束的監聽器
//        reverseAnimator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                // 在反向動畫結束時，開始向上滾動的新動畫
//                scroll_S2_TopAnimation();
//            }
//        });
//
//        // 開始反向動畫
//        reverseAnimator.start();
//    }
//
//    // 滾動到頂端的動畫
//    private void scroll_S2_TopAnimation() {
//        // 獲取ScrollView的內容高度
//        final int scrollRange = scrollView.getChildAt(0).getHeight() - scrollView.getHeight();
//
//        // 創建ValueAnimator實例，用於模擬滾動動畫
//        ValueAnimator topAnimator = ValueAnimator.ofInt(scrollRange / 2, 0);
//        topAnimator.setDuration(10000); // 接下來的10秒內滾動到頂端
//        topAnimator.setInterpolator(new LinearInterpolator()); // 使用線性插值器
//
//        // 設置動畫更新的監聽器
//        topAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                // 在動畫更新時，將ScrollView滾動到指定位置
//                int value = (int) animation.getAnimatedValue();
//                scrollView.scrollTo(0, value);
//            }
//        });
//
//        // 開始向上滾動的新動畫
//        topAnimator.start();
//    }


}
