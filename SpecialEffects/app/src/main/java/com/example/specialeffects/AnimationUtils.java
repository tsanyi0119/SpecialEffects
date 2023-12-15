package com.example.specialeffects;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ScrollView;

public class AnimationUtils {
    private ScrollView scrollView;
    private ValueAnimator currentAnimator;
    private int scrollRange ;
    private boolean isFirstLayout = true;


    public static final int ANIMATION_1 = 0;
    public static final int ANIMATION_2 = 1;
    public static final int ANIMATION_3 = 2;
    public static final int ANIMATION_4 = 3;
    public static final int ANIMATION_5 = 4;

    public void setScrollView(ScrollView scrollView) {
        this.scrollView = scrollView;
    }

    /**
     * 選擇動畫
     * */
    public void startAnimation(int animationSelect, int time) {
        if(isFirstLayout) {
            scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    scrollRange = scrollView.getChildAt(0).getHeight() - scrollView.getHeight();
                    switch (animationSelect) {
                        case ANIMATION_1:
                            runAnimation(0, scrollRange, scrollRange, 0, time * 1000, 0, 0, 0);           // B>T
                            break;
                        case ANIMATION_2:
                            runAnimation(0, 0, 0, scrollRange, time * 1000, 0, 0, 0);                // T>B
                            break;
                        case ANIMATION_3:
                            runAnimation(0, 0, 0, scrollRange / 2, time * 1000, 0, 0, 0);     // C>B
                            break;
                        case ANIMATION_4:
                            runAnimation(0, scrollRange / 2, scrollRange / 2, scrollRange, (time / 3) * 1000, scrollRange, 0, (time / 3) * 2 * 1000);  // C>B>C>T
                            break;
                        case ANIMATION_5:
                            runAnimation(0, scrollRange / 2, scrollRange / 2, scrollRange, (time / 2) * 1000, scrollRange, scrollRange / 2, (time / 2) * 1000);      // C>B>C
                            break;
                        default:
                    }
                }
            });
        } else {

        } switch (animationSelect) {
            case ANIMATION_1:
                runAnimation(0, scrollRange, scrollRange, 0, time * 1000, 0, 0, 0);           // B>T
                break;
            case ANIMATION_2:
                runAnimation(0, 0, 0, scrollRange, time * 1000, 0, 0, 0);                // T>B
                break;
            case ANIMATION_3:
                runAnimation(0, 0, 0, scrollRange / 2, time * 1000, 0, 0, 0);     // C>B
                break;
            case ANIMATION_4:
                runAnimation(0, scrollRange / 2, scrollRange / 2, scrollRange, (time / 3) * 1000, scrollRange, 0, (time / 3) * 2 * 1000);  // C>B>C>T
                break;
            case ANIMATION_5:
                runAnimation(0, scrollRange / 2, scrollRange / 2, scrollRange, (time / 2) * 1000, scrollRange, scrollRange / 2, (time / 2) * 1000);      // C>B>C
                break;
            default:
        }
    }

    public void pauseAnimation() {
        if (currentAnimator != null) {
            currentAnimator.pause();
            currentAnimator.setIntValues(scrollView.getScrollY());
        }
    }

    public void resumeAnimation() {
        if (currentAnimator != null) {
            currentAnimator.resume();
        }
    }

    /**
     * 播放動畫
     * */
    private void runAnimation(int startX, int startY, int endX ,int endY, int time,int reverseEndX,int reverseEndY ,int reverseTime) {
        scrollView.scrollTo(startX, startY);
        // 創建ValueAnimator實例，用於模擬滾動動畫
        final ValueAnimator animator = createAnimator(endX, endY, time);

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(reverseTime != 0) {
                    createReverseAnimator(reverseEndX, reverseEndY, reverseTime);
                } else {
                    clearAnimatorListeners(animator);
                }
            }
        });
        animator.start();
        currentAnimator = animator;
    }
    /**
     * 建立動畫
     * */
    private ValueAnimator createAnimator(int start, int end, int duration) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(duration);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                scrollView.scrollTo(0, value);
            }
        });
        return animator;
    }

    /**
     * 反轉動畫
     * */
    private void createReverseAnimator(int start, int end, int duration) {
        ValueAnimator reverseAnimator = createAnimator(start, end, duration);
        reverseAnimator.addUpdateListener(animation -> scrollView.scrollTo(0, (int) animation.getAnimatedValue()));
        reverseAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                clearAnimatorListeners(reverseAnimator);
            }
        });
        reverseAnimator.start();
        currentAnimator = reverseAnimator;
    }


    /**
     * 清除監聽
     * */
    private void clearAnimatorListeners(ValueAnimator animator) {
        animator.removeAllUpdateListeners();
        animator.removeAllListeners();
    }
}
