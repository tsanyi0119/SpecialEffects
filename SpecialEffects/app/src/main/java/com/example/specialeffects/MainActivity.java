package com.example.specialeffects;

import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;



//下往中
public class MainActivity extends AppCompatActivity {

    private static final int SCROLL_DELAY = 25;
    private static final int TOTAL_SCROLL_TIME = 60000;

    private Handler handler;
    private boolean isScrolling = true;
    private long startTime;
    private float lastProgress = 0.0f;

    private NestedScrollView scrollView;
    private int pausedScrollY = 0;
    private long pausedTime = 0;
    private float pausedProgress = 0.0f;
    private int totalIterations;
    private int currentIteration = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrollView = findViewById(R.id.scrollView);

        Button button_stop = findViewById(R.id.button_stop);
        button_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseAutoScroll();
            }
        });

        Button button_resume = findViewById(R.id.button_resume);
        button_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resumeAutoScroll();
            }
        });

        handler = new Handler();
        startTime = System.currentTimeMillis();
        totalIterations = TOTAL_SCROLL_TIME / SCROLL_DELAY;

        // 移動到底部
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);

                // 開始捲動
                startAutoScroll();
            }
        });
    }

    private void startAutoScroll() {
        handler.removeCallbacksAndMessages(null);
        currentIteration = 0;
        // 開始捲動時，先確保 NestedScrollView 在底部
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
        // 開始捲動
        startAutoScroll(scrollView);
    }

    private void startAutoScroll(final NestedScrollView scrollView) {
        startTime = System.currentTimeMillis(); // 更新起始時間
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isScrolling && currentIteration < totalIterations) {
                    long currentTime = System.currentTimeMillis();
                    long elapsedTime = currentTime - startTime;
                    float progress = (float) elapsedTime / TOTAL_SCROLL_TIME;
                    Log.d("ScrollTest", "Elapsed Time: " + elapsedTime);

                    if (progress <= 1.0) {
                        int maxScrollY = scrollView.getChildAt(0).getHeight() - scrollView.getHeight();
                        int scrollY = (int) (progress * maxScrollY);

                        // 將捲動位置設定為底部減去一半的可見高度
                        scrollView.smoothScrollTo(0, maxScrollY - scrollY / 2);
                    } else {
                        isScrolling = false;
                    }

                    lastProgress = progress;
                    currentIteration++;

                    // 在這裡觸發下一次的捲動
                    handler.postDelayed(this, SCROLL_DELAY);
                } else {
                    isScrolling = false;
                }
            }
        }, SCROLL_DELAY);
    }



    private void pauseAutoScroll() {
        isScrolling = false;
        handler.removeCallbacksAndMessages(null);

        pausedScrollY = scrollView.getScrollY();
        pausedTime = System.currentTimeMillis();
        pausedProgress = lastProgress;
    }

    private void resumeAutoScroll() {
        isScrolling = true;
        startTime = System.currentTimeMillis();

        long remainingTime = TOTAL_SCROLL_TIME - (long) (pausedProgress * TOTAL_SCROLL_TIME);
        remainingTime = Math.max(remainingTime, 0);

        int maxScrollRange = scrollView.getChildAt(0).getHeight() - scrollView.getHeight();
        float speed = (float) maxScrollRange / TOTAL_SCROLL_TIME;

        long finalRemainingTime = remainingTime;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isScrolling) {
                    long currentTime = System.currentTimeMillis();
                    long elapsedTime = currentTime - startTime;

                    if (elapsedTime <= finalRemainingTime) {
                        int scrollY = (int) (pausedScrollY - speed * elapsedTime);
                        scrollView.smoothScrollTo(0, scrollY);
                        handler.postDelayed(this, SCROLL_DELAY);
                    } else {
                        startAutoScroll();
                    }
                }
            }
        }, SCROLL_DELAY);
    }
}

//上往中
//public class MainActivity extends AppCompatActivity {
//
//    private static final int SCROLL_DELAY = 25;
//    private static final int TOTAL_SCROLL_TIME = 60000;
//
//    private Handler handler;
//    private boolean isScrolling = true;
//    private long startTime;
//    private float lastProgress = 0.0f;
//
//    private NestedScrollView scrollView;
//    private int totalIterations;
//    private int currentIteration = 0;
//    private int pausedScrollY = 0;
//    private float pausedProgress = 0.0f;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        scrollView = findViewById(R.id.scrollView);
//
//        Button button_stop = findViewById(R.id.button_stop);
//        button_stop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pauseAutoScroll();
//            }
//        });
//
//        Button button_resume = findViewById(R.id.button_resume);
//        button_resume.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                resumeAutoScroll();
//            }
//        });
//
//        handler = new Handler();
//        startTime = System.currentTimeMillis();
//        totalIterations = TOTAL_SCROLL_TIME / SCROLL_DELAY;
//
//        scrollView.post(new Runnable() {
//            @Override
//            public void run() {
//                // 捲動到頂部
//                scrollView.scrollTo(0, 0);
//
//                // 開始捲動
//                startAutoScroll();
//            }
//        });
//    }
//
//    private void startAutoScroll() {
//        handler.removeCallbacksAndMessages(null);
//        currentIteration = 0;
//
//        // 移動到頂部
//        int startY = 0;
//        scrollView.smoothScrollTo(0, startY);
//
//        // 開始捲動到中間
//        startAutoScroll(scrollView, startY);
//    }
//
//    private void startAutoScroll(final NestedScrollView scrollView, final int startY) {
//        startTime = System.currentTimeMillis(); // 更新起始時間
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (isScrolling && currentIteration < totalIterations) {
//                    long currentTime = System.currentTimeMillis();
//                    long elapsedTime = currentTime - startTime;
//                    float progress = (float) elapsedTime / TOTAL_SCROLL_TIME;
//                    Log.d("ScrollTest", "Elapsed Time: " + elapsedTime);
//
//                    if (progress <= 1.0) {
//                        int maxScrollY = scrollView.getChildAt(0).getHeight() - scrollView.getHeight();
//                        int scrollY = (int) (progress * maxScrollY);
//
//                        // 調整這裡的計算方式，使其由頂部捲動到中間
//                        scrollView.smoothScrollTo(0, startY + scrollY / 2); // 修改此行以改變捲動方向
//                    } else {
//                        isScrolling = false;
//                    }
//
//                    lastProgress = progress;
//                    currentIteration++;
//
//                    // 在這裡觸發下一次的捲動
//                    handler.postDelayed(this, SCROLL_DELAY);
//                } else {
//                    isScrolling = false;
//                }
//            }
//        }, SCROLL_DELAY);
//    }
//
//    private void pauseAutoScroll() {
//        isScrolling = false;
//        handler.removeCallbacksAndMessages(null);
//
//        // 记录暂停前的滚动位置
//        pausedScrollY = scrollView.getScrollY();
//        // 记录暂停前的滚动进度
//        pausedProgress = (float) pausedScrollY / (scrollView.getChildAt(0).getHeight() - scrollView.getHeight());
//    }
//
//    private void resumeAutoScroll() {
//        isScrolling = true;
//        startTime = System.currentTimeMillis();
//
//        long remainingTime = TOTAL_SCROLL_TIME - (long) (pausedProgress * TOTAL_SCROLL_TIME);
//        remainingTime = Math.max(remainingTime, 0);
//
//        int maxScrollRange = scrollView.getChildAt(0).getHeight() - scrollView.getHeight();
//        float speed = (float) maxScrollRange / TOTAL_SCROLL_TIME;
//
//        long finalRemainingTime = remainingTime;
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (isScrolling) {
//                    long currentTime = System.currentTimeMillis();
//                    long elapsedTime = currentTime - startTime;
//
//                    if (elapsedTime <= finalRemainingTime) {
//                        // 使用固定速度來計算滾動的距離，保持速度一致
//                        int scrollY = (int) (pausedScrollY + speed * elapsedTime);
//
//                        // 修正 scrollY，確保不超過底部
//                        scrollY = Math.min(scrollY, maxScrollRange);
//
//                        scrollView.smoothScrollTo(0, scrollY);
//                        handler.postDelayed(this, SCROLL_DELAY);
//                    } else {
//                        // 不再調用 startAutoScroll
//                    }
//                }
//            }
//        }, SCROLL_DELAY);
//    }
//}

//中往下
//public class MainActivity extends AppCompatActivity {
//
//    private static final int SCROLL_DELAY = 25;
//    private static final int TOTAL_SCROLL_TIME = 60000;
//
//    private Handler handler;
//    private boolean isScrolling = true;
//    private long startTime;
//    private float lastProgress = 0.0f;
//
//    private NestedScrollView scrollView;
//    private int totalIterations;
//    private int currentIteration = 0;
//    private int pausedScrollY = 0;
//    private float pausedProgress = 0.0f;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        scrollView = findViewById(R.id.scrollView);
//
//        Button button_stop = findViewById(R.id.button_stop);
//        button_stop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pauseAutoScroll();
//            }
//        });
//
//        Button button_resume = findViewById(R.id.button_resume);
//        button_resume.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                resumeAutoScroll();
//            }
//        });
//
//        handler = new Handler();
//        startTime = System.currentTimeMillis();
//        totalIterations = TOTAL_SCROLL_TIME / SCROLL_DELAY;
//
//         //移動到中間
//        scrollView.post(new Runnable() {
//            @Override
//            public void run() {
//                int middleY = scrollView.getChildAt(0).getHeight() / 2 - scrollView.getHeight() / 2;
//                scrollView.smoothScrollTo(0, middleY);
//
//                // 開始捲動
//                startAutoScroll();
//            }
//        });
//    }
//
//    private void startAutoScroll() {
//        handler.removeCallbacksAndMessages(null);
//        currentIteration = 0;
//
//        // 移動到中間
//        int middleY = (scrollView.getChildAt(0).getHeight() - scrollView.getHeight()) / 2;
//        scrollView.smoothScrollTo(0, middleY);
//
//        // 開始捲動
//        startAutoScroll(scrollView, middleY);
//    }
//
//    private void startAutoScroll(final NestedScrollView scrollView, final int startY) {
//        startTime = System.currentTimeMillis(); // 更新起始時間
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (isScrolling && currentIteration < totalIterations) {
//                    long currentTime = System.currentTimeMillis();
//                    long elapsedTime = currentTime - startTime;
//                    float progress = (float) elapsedTime / TOTAL_SCROLL_TIME;
//                    Log.d("ScrollTest", "Elapsed Time: " + elapsedTime);
//
//                    if (progress <= 1.0) {
//                        int maxScrollY = scrollView.getChildAt(0).getHeight() - scrollView.getHeight();
//                        int scrollY = (int) (progress * maxScrollY);
//
//                        // 調整這裡的計算方式
//                        scrollView.smoothScrollTo(0, startY + scrollY); // 修改此行以改變捲動方向
//                    } else {
//                        isScrolling = false;
//                    }
//
//                    lastProgress = progress;
//                    currentIteration++;
//
//                    // 在這裡觸發下一次的捲動
//                    handler.postDelayed(this, SCROLL_DELAY);
//                } else {
//                    isScrolling = false;
//                }
//            }
//        }, SCROLL_DELAY);
//    }
//
//    private void pauseAutoScroll() {
//        isScrolling = false;
//        handler.removeCallbacksAndMessages(null);
//
//        // 记录暂停前的滚动位置
//        pausedScrollY = scrollView.getScrollY();
//        // 记录暂停前的滚动进度
//        pausedProgress = (float) pausedScrollY / (scrollView.getChildAt(0).getHeight() - scrollView.getHeight());
//    }
//
//    private void resumeAutoScroll() {
//        isScrolling = true;
//        startTime = System.currentTimeMillis();
//
//        long remainingTime = TOTAL_SCROLL_TIME - (long) (pausedProgress * TOTAL_SCROLL_TIME);
//        remainingTime = Math.max(remainingTime, 0);
//
//        int maxScrollRange = scrollView.getChildAt(0).getHeight() - scrollView.getHeight();
//        float speed = (float) maxScrollRange / TOTAL_SCROLL_TIME;
//
//        long finalRemainingTime = remainingTime;
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (isScrolling) {
//                    long currentTime = System.currentTimeMillis();
//                    long elapsedTime = currentTime - startTime;
//
//                    if (elapsedTime <= finalRemainingTime) {
//                        // 使用固定速度來計算滾動的距離，保持速度一致
//                        int scrollY = (int) (pausedScrollY + speed * elapsedTime);
//
//                        // 修正 scrollY，確保不超過底部
//                        scrollY = Math.min(scrollY, maxScrollRange);
//
//                        scrollView.smoothScrollTo(0, scrollY);
//                        handler.postDelayed(this, SCROLL_DELAY);
//                    } else {
//                        // 不再調用 startAutoScroll
//                    }
//                }
//            }
//        }, SCROLL_DELAY);
//    }
//}

//中往上
//public class MainActivity extends AppCompatActivity {
//
//    private static final int SCROLL_DELAY = 25;
//    private static final int TOTAL_SCROLL_TIME = 60000;
//
//    private Handler handler;
//    private boolean isScrolling = true;
//    private long startTime;
//    private float lastProgress = 0.0f;
//
//    private NestedScrollView scrollView;
//    private int totalIterations;
//    private int currentIteration = 0;
//    private int pausedScrollY = 0;
//    private float pausedProgress = 0.0f;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        scrollView = findViewById(R.id.scrollView);
//
//        Button button_stop = findViewById(R.id.button_stop);
//        button_stop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pauseAutoScroll();
//            }
//        });
//
//        Button button_resume = findViewById(R.id.button_resume);
//        button_resume.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                resumeAutoScroll();
//            }
//        });
//
//        handler = new Handler();
//        startTime = System.currentTimeMillis();
//        totalIterations = TOTAL_SCROLL_TIME / SCROLL_DELAY;
//
//        // 移動到中間
//        scrollView.post(new Runnable() {
//            @Override
//            public void run() {
//                int middleY = scrollView.getChildAt(0).getHeight() / 2 - scrollView.getHeight() / 2;
//                scrollView.smoothScrollTo(0, middleY);
//
//                // 開始捲動
//                startAutoScroll();
//            }
//        });
//    }
//
//    private void startAutoScroll() {
//        handler.removeCallbacksAndMessages(null);
//        currentIteration = 0;
//
//        // 移動到中間
//        int middleY = (scrollView.getChildAt(0).getHeight() - scrollView.getHeight()) / 2;
//        scrollView.smoothScrollTo(0, middleY);
//
//        // 開始捲動
//        startAutoScroll(scrollView, middleY);
//    }
//
//    private void startAutoScroll(final NestedScrollView scrollView, final int startY) {
//        startTime = System.currentTimeMillis(); // 更新起始時間
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (isScrolling && currentIteration < totalIterations) {
//                    long currentTime = System.currentTimeMillis();
//                    long elapsedTime = currentTime - startTime;
//                    float progress = (float) elapsedTime / TOTAL_SCROLL_TIME;
//                    Log.d("ScrollTest", "Elapsed Time: " + elapsedTime);
//
//                    if (progress <= 1.0) {
//                        int maxScrollY = scrollView.getChildAt(0).getHeight() - scrollView.getHeight();
//                        int scrollY = (int) (progress * maxScrollY);
//
//                        // 調整這裡的計算方式
//                        scrollView.smoothScrollTo(0, startY - scrollY);
//                    } else {
//                        isScrolling = false;
//                    }
//
//                    lastProgress = progress;
//                    currentIteration++;
//
//                    // 在這裡觸發下一次的捲動
//                    handler.postDelayed(this, SCROLL_DELAY);
//                } else {
//                    isScrolling = false;
//                }
//            }
//        }, SCROLL_DELAY);
//    }
//
//    private void pauseAutoScroll() {
//        isScrolling = false;
//        handler.removeCallbacksAndMessages(null);
//
//        // 记录暂停前的滚动位置
//        pausedScrollY = scrollView.getScrollY();
//        // 记录暂停前的滚动进度
//        pausedProgress = (float) pausedScrollY / (scrollView.getChildAt(0).getHeight() - scrollView.getHeight());
//    }
//
//    private void resumeAutoScroll() {
//        isScrolling = true;
//        startTime = System.currentTimeMillis();
//
//        long remainingTime = TOTAL_SCROLL_TIME - (long) (pausedProgress * TOTAL_SCROLL_TIME);
//        remainingTime = Math.max(remainingTime, 0);
//
//        int maxScrollRange = scrollView.getChildAt(0).getHeight() - scrollView.getHeight();
//        float speed = (float) maxScrollRange / TOTAL_SCROLL_TIME;
//
//        long finalRemainingTime = remainingTime;
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (isScrolling) {
//                    long currentTime = System.currentTimeMillis();
//                    long elapsedTime = currentTime - startTime;
//
//                    if (elapsedTime <= finalRemainingTime) {
//                        // 使用固定速度来计算滚动的距离，保持速度一致
//                        int scrollY = (int) (pausedScrollY - speed * elapsedTime);
//
//                        // 修正 scrollY，确保不小于 0
//                        scrollY = Math.max(scrollY, 0);
//
//                        scrollView.smoothScrollTo(0, scrollY);
//                        handler.postDelayed(this, SCROLL_DELAY);
//                    } else {
//                        // 不再调用 startAutoScroll
//                    }
//                }
//            }
//        }, SCROLL_DELAY);
//    }
//
//
//}


//下往上
//public class MainActivity extends AppCompatActivity {
//
//    private static final int SCROLL_DELAY = 25;
//    private static final int TOTAL_SCROLL_TIME = 60000;
//
//    private Handler handler;
//    private boolean isScrolling = true;
//    private long startTime;
//    private float lastProgress = 0.0f;
//
//    private NestedScrollView scrollView;
//    private int pausedScrollY = 0;
//    private long pausedTime = 0;
//    private float pausedProgress = 0.0f;
//    private int totalIterations;
//    private int currentIteration = 0;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        scrollView = findViewById(R.id.scrollView);
//
//        Button button_stop = findViewById(R.id.button_stop);
//        button_stop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pauseAutoScroll();
//            }
//        });
//
//        Button button_resume = findViewById(R.id.button_resume);
//        button_resume.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                resumeAutoScroll();
//            }
//        });
//
//        handler = new Handler();
//        startTime = System.currentTimeMillis();
//        totalIterations = TOTAL_SCROLL_TIME / SCROLL_DELAY;
//
//        // 移動到底部
//        scrollView.post(new Runnable() {
//            @Override
//            public void run() {
//                scrollView.fullScroll(View.FOCUS_DOWN);
//
//                // 開始捲動
//                startAutoScroll();
//            }
//        });
//    }
//
//    private void startAutoScroll() {
//        handler.removeCallbacksAndMessages(null);
//        currentIteration = 0;
//        // 開始捲動時，先確保 NestedScrollView 在底部
//        scrollView.post(new Runnable() {
//            @Override
//            public void run() {
//                scrollView.fullScroll(View.FOCUS_DOWN);
//            }
//        });
//        // 開始捲動
//        startAutoScroll(scrollView,scrollView.getScrollY());
//    }
//
//    private void startAutoScroll(final NestedScrollView scrollView, final int initialScrollY) {
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (isScrolling && currentIteration < totalIterations) {
//                    long currentTime = System.currentTimeMillis();
//                    long elapsedTime = currentTime - startTime;
//                    float progress = (float) elapsedTime / TOTAL_SCROLL_TIME;
//                    Log.d("ScrollTest", "Elapsed Time: " + elapsedTime);
//                    if (progress <= 1.0) {
//                        int maxScrollY = scrollView.getChildAt(0).getHeight() - scrollView.getHeight();
//                        int scrollY = (int) ((1.0 - progress) * maxScrollY);
//
//                        scrollView.smoothScrollTo(0, initialScrollY + scrollY);
//                        handler.postDelayed(this, SCROLL_DELAY);
//                    } else {
//                        isScrolling = false;
//                    }
//
//                    lastProgress = progress;
//                    currentIteration++;
//                } else {
//                    isScrolling = false;
//                }
//            }
//        }, SCROLL_DELAY);
//
//    }
//
//    private void pauseAutoScroll() {
//        isScrolling = false;
//        handler.removeCallbacksAndMessages(null);
//
//        pausedScrollY = scrollView.getScrollY();
//        pausedTime = System.currentTimeMillis();
//        pausedProgress = lastProgress;
//    }
//
//    private void resumeAutoScroll() {
//        isScrolling = true;
//        startTime = System.currentTimeMillis();
//
//        long remainingTime = TOTAL_SCROLL_TIME - (long) (pausedProgress * TOTAL_SCROLL_TIME);
//        remainingTime = Math.max(remainingTime, 0);
//
//        int maxScrollRange = scrollView.getChildAt(0).getHeight() - scrollView.getHeight();
//        float speed = (float) maxScrollRange / TOTAL_SCROLL_TIME;
//
//        long finalRemainingTime = remainingTime;
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (isScrolling) {
//                    long currentTime = System.currentTimeMillis();
//                    long elapsedTime = currentTime - startTime;
//
//                    if (elapsedTime <= finalRemainingTime) {
//                        int scrollY = (int) (pausedScrollY - speed * elapsedTime);
//                        scrollView.smoothScrollTo(0, scrollY);
//                        handler.postDelayed(this, SCROLL_DELAY);
//                    } else {
//                        startAutoScroll();
//                    }
//                }
//            }
//        }, SCROLL_DELAY);
//    }
//}

//上往下
//public class MainActivity extends AppCompatActivity {
//
//    private static final int SCROLL_DELAY = 25;
//    private static final int TOTAL_SCROLL_TIME = 60000;
//
//    private Handler handler;
//    private boolean isScrolling = true;
//    private long startTime;
//    private float lastProgress = 0.0f;
//
//    private NestedScrollView scrollView;
//    private int pausedScrollY = 0;
//    private long pausedTime = 0;
//    private float pausedProgress = 0.0f;
//    private int totalIterations;
//    private int currentIteration = 0;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        scrollView = findViewById(R.id.scrollView);
//
//        Button button_stop = findViewById(R.id.button_stop);
//        button_stop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pauseAutoScroll();
//            }
//        });
//
//        Button button_resume = findViewById(R.id.button_resume);
//        button_resume.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                resumeAutoScroll();
//            }
//        });
//
//        handler = new Handler();
//        startTime = System.currentTimeMillis();
//        totalIterations = TOTAL_SCROLL_TIME / SCROLL_DELAY;
//        startAutoScroll();
//    }
//
//    private void startAutoScroll() {
//        handler.removeCallbacksAndMessages(null);
//        currentIteration = 0;
//        startAutoScroll(scrollView, scrollView.getScrollY());
//    }
//
//    private void startAutoScroll(final NestedScrollView scrollView, final int initialScrollY) {
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (isScrolling && currentIteration < totalIterations) {
//                    long currentTime = System.currentTimeMillis();
//                    long elapsedTime = currentTime - startTime;
//                    float progress = (float) elapsedTime / TOTAL_SCROLL_TIME;
//                    Log.d("ScrollTest", "Elapsed Time: " + elapsedTime);
//                    if (progress <= 1.0) {
//                        int maxScrollY = scrollView.getChildAt(0).getHeight() - scrollView.getHeight();
//                        int scrollY = (int) (progress * maxScrollY);
//
//                        if (progress < lastProgress) {
//                            scrollY = maxScrollY - scrollY;
//                        }
//
//                        scrollView.smoothScrollTo(0, initialScrollY + scrollY);
//                        handler.postDelayed(this, SCROLL_DELAY);
//                    } else {
//                        isScrolling = false;
//                    }
//
//                    lastProgress = progress;
//                    currentIteration++;
//                } else {
//                    isScrolling = false;
//                }
//            }
//        }, SCROLL_DELAY);
//
//    }
//
//    private void pauseAutoScroll() {
//        isScrolling = false;
//        handler.removeCallbacksAndMessages(null);
//
//        pausedScrollY = scrollView.getScrollY();
//        pausedTime = System.currentTimeMillis();
//        pausedProgress = lastProgress;
//    }
//
//    private void resumeAutoScroll() {
//        isScrolling = true;
//        startTime = System.currentTimeMillis();
//
//        long remainingTime = TOTAL_SCROLL_TIME - (long) (pausedProgress * TOTAL_SCROLL_TIME);
//        remainingTime = Math.max(remainingTime, 0);
//
//        int maxScrollY = scrollView.getChildAt(0).getHeight() - scrollView.getHeight();
//        float speed = 1.0f / TOTAL_SCROLL_TIME;
//
//        long finalRemainingTime = remainingTime;
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (isScrolling) {
//                    long currentTime = System.currentTimeMillis();
//                    long elapsedTime = currentTime - startTime;
//                    float progress = (float) elapsedTime / finalRemainingTime;
//
//                    if (progress <= 1.0) {
//                        int scrollY = (int) (pausedScrollY + speed * elapsedTime * maxScrollY);
//                        scrollView.smoothScrollTo(0, scrollY);
//                        handler.postDelayed(this, SCROLL_DELAY);
//                    } else {
//                        startAutoScroll();
//                    }
//                }
//            }
//        }, SCROLL_DELAY);
//    }
//}
