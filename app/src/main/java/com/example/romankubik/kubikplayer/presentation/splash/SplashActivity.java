package com.example.romankubik.kubikplayer.presentation.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.romankubik.kubikplayer.presentation.audiolist.AudioListActivity;

/**
 * Created by roman.kubik on 8/16/17.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, AudioListActivity.class);
        startActivity(intent);
        finish();
    }
}
