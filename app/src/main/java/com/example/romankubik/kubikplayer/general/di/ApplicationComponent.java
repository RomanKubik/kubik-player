package com.example.romankubik.kubikplayer.general.di;

import android.content.Context;

import com.example.romankubik.kubikplayer.interaction.finder.di.FinderModule;
import com.example.romankubik.kubikplayer.interaction.player.MusicPlayerService;
import com.example.romankubik.kubikplayer.interaction.player.di.PlayerModule;
import com.example.romankubik.kubikplayer.presentation.audiolist.di.AudioListComponent;
import com.example.romankubik.kubikplayer.presentation.audiolist.di.AudioListModule;
import com.example.romankubik.kubikplayer.presentation.template.di.TemplateComponent;
import com.example.romankubik.kubikplayer.presentation.template.di.TemplateModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by roman.kubik on 8/16/17.
 */

@Singleton
@Component(modules = {ApplicationModule.class, PlayerModule.class, FinderModule.class})
public interface ApplicationComponent {
    Context getApplicationContext();

    void inject(MusicPlayerService service);

    AudioListComponent audioListComponent(AudioListModule audioListModule);

    TemplateComponent templateComponent(TemplateModule templateModule);
}
