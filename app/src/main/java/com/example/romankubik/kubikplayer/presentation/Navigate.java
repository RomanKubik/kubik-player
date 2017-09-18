package com.example.romankubik.kubikplayer.presentation;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import com.example.romankubik.kubikplayer.general.Constants;
import com.example.romankubik.kubikplayer.interaction.entity.Track;
import com.example.romankubik.kubikplayer.presentation.player.PlayerActivity;

/**
 * Created by roman.kubik on 9/4/17.
 */

public class Navigate {

    @SafeVarargs
    public static void toPlayerActivity(@NonNull Activity activity, @NonNull Track track, Pair<View, String>... pairs) {
        Intent intent = new Intent(activity, PlayerActivity.class);
        intent.putExtra(Constants.Intent.TRACK_EXTRA, track.getId());
        if (pairs.length != 0) {
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(activity, pairs);
            activity.startActivity(intent, options.toBundle());
            return;
        }
        activity.startActivity(intent);
    }
}
