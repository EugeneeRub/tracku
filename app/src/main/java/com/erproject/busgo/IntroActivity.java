package com.erproject.busgo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntroActivity extends AppCompatActivity {

    @BindView(R.id.back_image)
    ImageView ivBackImage;

    @OnClick(R.id.btn_walker)
    public void onWalkerClicked() {
        Toast.makeText(this, "you are walker", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_driver)
    public void onDriverClicked() {
        Toast.makeText(this, "you are driver", Toast.LENGTH_SHORT).show();
    }

    public static Intent newInstance(Context context) {
        return new Intent(context, IntroActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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
}
