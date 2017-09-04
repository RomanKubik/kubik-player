package com.example.romankubik.kubikplayer.presentation;

import android.app.Activity;
import android.content.Intent;

import com.example.romankubik.kubikplayer.general.Constants;
import com.example.romankubik.kubikplayer.interaction.entity.Track;
import com.example.romankubik.kubikplayer.presentation.player.PlayerActivity;

import org.parceler.Parcels;

/**
 * Created by roman.kubik on 9/4/17.
 */

public class Navigate {

    public static void toPlayerActivity(Activity activity, Track track) {
        Intent intent = new Intent(activity, PlayerActivity.class);
        intent.putExtra(Constants.Intent.TRACK_EXTRA, Parcels.wrap(track));
        activity.startActivity(intent);
    }
}
