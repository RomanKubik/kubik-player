package com.example.romankubik.kubikplayer.interaction.media;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.example.romankubik.kubikplayer.interaction.Interactor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Finds music files on device
 * Created by roman.kubik on 8/18/17.
 */

public class MediaFinder implements Interactor.Finder {

    private Context context;

    @Inject
    public MediaFinder(Context context) {
        this.context = context;
    }

    @Override
    public Observable<File> findAllMusicFiles() {
        return Observable.fromIterable(getMusicList());
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

    private List<File> getMusicList() {
        ContentResolver cr = context.getContentResolver();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cur = cr.query(uri, null, selection, null, sortOrder);

        List<File> files = new ArrayList<>();

        if(cur != null)
        {
            if(cur.getCount() > 0)
            {
                while(cur.moveToNext())
                {
                    String data = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DATA));
                    files.add(new File(data));
                }

            }
            cur.close();
        }

        Log.d("MyTag", "getMusicList: " + Thread.currentThread().getName());
        return files;
    }

}
