package com.example.romankubik.kubikplayer.presentation.template;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.romankubik.kubikplayer.R;
import com.example.romankubik.kubikplayer.presentation.template.di.TemplateModule;

import javax.inject.Inject;

import static com.example.romankubik.kubikplayer.general.android.PlayerApplication.component;

/**
 * Created by roman.kubik on 8/16/17.
 */

public class TemplateActivity extends AppCompatActivity implements TemplatePresenter.View {

    @Inject
    TemplatePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
        component.templateComponent(new TemplateModule(this)).inject(this);
    }
}
