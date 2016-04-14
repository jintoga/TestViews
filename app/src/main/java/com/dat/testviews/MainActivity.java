package com.dat.testviews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.animatedView)
    protected View animatedView;
    private Animation animShow, animHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initAnimation();
    }

    private void initAnimation() {
        animShow = AnimationUtils.loadAnimation(this, R.anim.view_show);
        animHide = AnimationUtils.loadAnimation(this, R.anim.view_hide);
    }


    @OnClick(R.id.triggerAnimation)
    protected void trigger() {
        if (animatedView.getVisibility() == View.VISIBLE) {
            animatedView.startAnimation(animHide);
            animatedView.setVisibility(View.GONE);
            Log.d("v","GONE");
        } else {
            animatedView.setVisibility(View.VISIBLE);
            animatedView.startAnimation(animShow);
            Log.d("v","VISIBLE");
        }
    }
}
