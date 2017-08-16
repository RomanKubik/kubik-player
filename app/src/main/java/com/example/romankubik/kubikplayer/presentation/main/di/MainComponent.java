package com.example.romankubik.kubikplayer.presentation.main.di;

import com.example.romankubik.kubikplayer.general.di.ActivityScope;
import com.example.romankubik.kubikplayer.presentation.main.MainActivity;

import dagger.Subcomponent;

/**
 * Created by roman.kubik on 8/16/17.
 */

@ActivityScope
@Subcomponent(modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
