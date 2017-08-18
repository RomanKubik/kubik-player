package com.example.romankubik.kubikplayer.interaction.media;

import android.os.Environment;

import com.example.romankubik.kubikplayer.interaction.Interactor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by roman.kubik on 8/18/17.
 */

public class MediaFinder implements Interactor.Finder {

    @Override
    public Observable<File> findAllMusicFiles() {
        return Observable.just(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC))
                .subscribeOn(Schedulers.io())
                .map(this::getListFiles)
                .flatMapIterable(l -> l);
    }

    private List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                if(file.getName().endsWith(".mp3") || file.getName().endsWith(".wav")){
                    inFiles.add(file);
                }
            }
        }
        return inFiles;
    }


}
