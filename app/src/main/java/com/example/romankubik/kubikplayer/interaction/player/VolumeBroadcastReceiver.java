package com.example.romankubik.kubikplayer.interaction.player;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;

import com.example.romankubik.kubikplayer.general.Constants;

/**
 * Created by roman.kubik on 9/25/17.
 */

public class VolumeBroadcastReceiver extends BroadcastReceiver {

    private IntentFilter volumeReceiverIntentFilter;

    private VolumeListener volumeListener;


    public interface VolumeListener {
        void onVolumeChanged();

        void onAudioBecomingNoisy();
    }

    public VolumeBroadcastReceiver() {
        super();
        volumeReceiverIntentFilter = new IntentFilter();
        volumeReceiverIntentFilter.addAction(Constants.Intent.ACTION_VOLUME_CHANGE);
        volumeReceiverIntentFilter.addAction(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Constants.Intent.ACTION_VOLUME_CHANGE)) {
            if (volumeListener != null)
                volumeListener.onVolumeChanged();
        } else if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
            if (volumeListener != null)
                volumeListener.onAudioBecomingNoisy();
        }
    }

    public IntentFilter getIntentFilter() {
        return volumeReceiverIntentFilter;
    }

    public void setVolumeListener(VolumeListener volumeListener) {
        this.volumeListener = volumeListener;
    }
}
