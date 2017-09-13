package com.example.romankubik.kubikplayer.general.di;

import android.content.Context;

import com.example.romankubik.kubikplayer.interaction.media.di.MediaModule;
import com.example.romankubik.kubikplayer.interaction.player.MusicPlayerService;
import com.example.romankubik.kubikplayer.interaction.player.di.PlayerModule;
import com.example.romankubik.kubikplayer.presentation.audiolist.di.AudioListComponent;
import com.example.romankubik.kubikplayer.presentation.audiolist.di.AudioListModule;
import com.example.romankubik.kubikplayer.presentation.player.di.PlayerComponent;
import com.example.romankubik.kubikplayer.presentation.template.di.TemplateComponent;
import com.example.romankubik.kubikplayer.presentation.template.di.TemplateModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by roman.kubik on 8/16/17.
 */

@Singleton
@Component(modules = {ApplicationModule.class, PlayerModule.class, MediaModule.class})
public interface ApplicationComponent {
    Context getApplicationContext();

    void inject(MusicPlayerService service);

    AudioListComponent audioListComponent(AudioListModule audioListModule);

    PlayerComponent playerComponent(com.example.romankubik.kubikplayer.presentation.player.di.PlayerModule playerModule);

    TemplateComponent templateComponent(TemplateModule templateModule);
}
