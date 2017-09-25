package com.example.romankubik.kubikplayer.general.utils;

/**
 * Created by roman.kubik on 9/25/17.
 */

public final class SeekbarMapper {

    private static final int PROGRESS_MAX_SIZE = 1000;

    public static int mapTimeToProgress(long progress, long duration) {
        return (int) (progress * PROGRESS_MAX_SIZE / duration);
    }

    public static long mapProgressToTime(int progress, long duration) {
        return progress * duration / PROGRESS_MAX_SIZE;
    }

    public static int mapVolumeToLevel(int volume, int maxVolume) {
        return (int) (PROGRESS_MAX_SIZE * ((float) volume / (float) maxVolume));
    }

    public static int mapLevelToVolume(int level, int maxVolume) {
        float percent = (float) level / PROGRESS_MAX_SIZE;
        return (int) (maxVolume * percent);
    }
}
