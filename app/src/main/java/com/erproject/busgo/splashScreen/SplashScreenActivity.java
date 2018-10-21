package com.erproject.busgo.splashScreen;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.erproject.busgo.IntroActivity;
import com.erproject.busgo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity {

    @BindView(R.id.activity_splash_logo)
    ImageView mLogoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
    }

    private void animateLogo() {
        AnimatorSet logoAnimatorSet = new AnimatorSet();
        logoAnimatorSet.play(ObjectAnimator.ofFloat(mLogoImage,
                "alpha", 0, 0.7f));
        logoAnimatorSet.setDuration(2000);
        logoAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                goToMain();
            }
        });
        logoAnimatorSet.start();
    }

    public void goToMain() {
        startActivity(IntroActivity.newInstance(this));
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
        animateLogo();
    }
}

