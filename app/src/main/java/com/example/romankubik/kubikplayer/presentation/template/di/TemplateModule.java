package com.example.romankubik.kubikplayer.presentation.template.di;

import android.app.Activity;

import com.example.romankubik.kubikplayer.general.di.ActivityScope;
import com.example.romankubik.kubikplayer.presentation.template.TemplateActivity;
import com.example.romankubik.kubikplayer.presentation.template.TemplatePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roman.kubik on 8/16/17.
 */

@Module
public class TemplateModule {

    private TemplateActivity activity;

    public TemplateModule(TemplateActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    public TemplatePresenter.View getView() {
        return activity;
    }

    @Provides
    @ActivityScope
    public Activity getActivity() {
        return activity;
    }
}
