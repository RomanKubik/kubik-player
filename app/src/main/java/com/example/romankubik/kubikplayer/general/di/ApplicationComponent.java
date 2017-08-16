package com.example.romankubik.kubikplayer.general.di;

import android.content.Context;

import com.example.romankubik.kubikplayer.presentation.template.di.TemplateComponent;
import com.example.romankubik.kubikplayer.presentation.template.di.TemplateModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by roman.kubik on 8/16/17.
 */

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    Context getApplicationContext();

    TemplateComponent templateComponent(TemplateModule templateModule);
}
