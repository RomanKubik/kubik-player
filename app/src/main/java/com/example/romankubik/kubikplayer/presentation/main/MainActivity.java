package com.example.romankubik.kubikplayer.presentation.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.romankubik.kubikplayer.R;
import com.example.romankubik.kubikplayer.presentation.main.di.MainModule;

import javax.inject.Inject;

import butterknife.ButterKnife;

import static com.example.romankubik.kubikplayer.general.android.PlayerApplication.component;

public class MainActivity extends AppCompatActivity implements MainPresenter.View{

    @Inject
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        component.mainComponent(new MainModule(this)).inject(this);
        ButterKnife.bind(this);

    }
}
