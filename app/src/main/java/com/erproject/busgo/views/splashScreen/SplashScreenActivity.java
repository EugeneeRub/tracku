package com.erproject.busgo.views.splashScreen;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.erproject.busgo.R;
import com.erproject.busgo.data.data.simpleData.InterestingPhrase;
import com.erproject.busgo.services.authManager.AuthController;
import com.erproject.busgo.views.login.LoginActivity;
import com.erproject.busgo.views.registration.RegistrationActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity {

    @BindView(R.id.back_image)
    ImageView ivBackImage;

    @BindView(R.id.text_common)
    TextView tvMainText;

    @BindView(R.id.text_author)
    TextView tvAuthorText;
    @BindView(R.id.text_layout)
    LinearLayout mainLayout;
    private AnimatorSet logoAnimatorSet;
    private List<InterestingPhrase> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);

        loadInterstingPhrase();
        loadText();

        int random = new Random().nextInt(7) + 1;
        switch (random) {
            case 1:
                ivBackImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bus1));
                break;
            case 2:
                ivBackImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bus2));
                break;
            case 3:
                ivBackImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bus3));
                break;
            case 4:
                ivBackImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bus4));
                break;
            case 5:
                ivBackImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bus5));
                break;
            case 6:
                ivBackImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bus6));
                break;
            case 7:
                ivBackImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bus7));
                break;
        }
    }

    private void loadText() {
        if (list == null || list.isEmpty()) return;
        int random = new Random().nextInt(20);
        InterestingPhrase interestingPhrase = list.get(random);
        tvMainText.setText(interestingPhrase.getTitle());
        tvAuthorText.setText(interestingPhrase.getAuthor());
    }

    private void animateLogo() {
        logoAnimatorSet = new AnimatorSet();
        logoAnimatorSet.play(ObjectAnimator.ofFloat(mainLayout,
                "alpha", 0, 0.7f));
        logoAnimatorSet.setDuration(3000);
        logoAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (AuthController.getToken(getApplicationContext()) != null) {
                    goToMain();
                } else {
                    goToLogin();
                }
            }
        });
        logoAnimatorSet.start();
        overridePendingTransition(R.anim.fade_in, 0);
    }

    public void goToMain() {
        if (logoAnimatorSet != null && logoAnimatorSet.isRunning())
            logoAnimatorSet.cancel();
        startActivity(RegistrationActivity.newInstance(this));
        finish();
    }

    public void goToLogin() {
        if (logoAnimatorSet != null && logoAnimatorSet.isRunning())
            logoAnimatorSet.cancel();
        startActivity(LoginActivity.newInstance(this));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        animateLogo();
    }

    private void loadInterstingPhrase() {
        InputStream is = getResources().openRawResource(R.raw.interesting_phrases);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Gson gson = new Gson();
        String jsonString = writer.toString();
        Type listType = new TypeToken<List<InterestingPhrase>>() {
        }.getType();
        list = gson.fromJson(jsonString, listType);
    }
}

