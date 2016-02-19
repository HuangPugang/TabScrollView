package com.hpg.wheelviewdialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MyHorScrollView scrollView;
    List<String> list = new ArrayList<>();
    public static int width;
    public int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list.add("First1");
        list.add("Second2");
        list.add("Third3");
        list.add("Forth4");
        list.add("Fifth5");
        list.add("Sixth6");
        list.add("Seventh7");
        list.add("Eighth8");
        list.add("Ninth9");
        scrollView = (MyHorScrollView) findViewById(R.id.scroll);
        scrollView.setData(list);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.setSelection(list.size()-3);
            }
        });
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        width = metric.widthPixels;     // 屏幕宽度（像素）
        height = metric.heightPixels;   // 屏幕高度（像素）
        float density = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
    }


}
