package com.example.romankubik.kubikplayer.general;

import static android.graphics.Color.parseColor;

/**
 * Created by roman.kubik on 9/4/17.
 */

public final class Constants {

    public static final class Intent {
        public static final String TRACK_EXTRA = "com.example.romankubik.kubikplayer.general.constants.intent.track.extra";
        public static final String ACTION_VOLUME_CHANGE = "android.media.VOLUME_CHANGED_ACTION";
    }

    public static final class Service {
        public static final int SERVICE_ID = 89960;
    }

    public static final class Time {
        public static final long SECOND_IN_MILIS = 1000;
    }

    public static final class Color {
        public static final int WHITE = parseColor("#FFFFFF");
        public static final int GRAY_LIGHT = parseColor("#FAFAFA");
        public static final int GRAY = parseColor("#9E9E9E");
        public static final int GRAY_LIGHT_DARK = parseColor("#424242");
        public static final int GRAY_DARK = parseColor("#212121");
        public static final int BLACK = parseColor("#000000");
    }

    public static final class Dimens {
        public static final int LOGO_DIMENS = 600;
    }
}
