package com.example.romankubik.kubikplayer.presentation.main.di;

import com.example.romankubik.kubikplayer.general.di.ActivityScope;
import com.example.romankubik.kubikplayer.presentation.main.MainActivity;
import com.example.romankubik.kubikplayer.presentation.main.MainPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roman.kubik on 8/16/17.
 */

@Module
public class MainModule {

    private MainActivity activity;

    public MainModule(MainActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    public MainPresenter.View getView() {
        return activity;
    }

    @Provides
    @ActivityScope
    public MainActivity getActivity() {
        return activity;
    }
}
