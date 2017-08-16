package com.example.romankubik.kubikplayer.presentation.template.di;

import com.example.romankubik.kubikplayer.general.di.ActivityScope;
import com.example.romankubik.kubikplayer.presentation.template.TemplateActivity;

import dagger.Subcomponent;

/**
 * Created by roman.kubik on 8/16/17.
 */

@ActivityScope
@Subcomponent(modules = TemplateModule.class)
public interface TemplateComponent {
    void inject(TemplateActivity templateActivity);
}
