package com.example.romankubik.kubikplayer.interaction.media;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.romankubik.kubikplayer.interaction.Interactor;
import com.example.romankubik.kubikplayer.interaction.entity.Track;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

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
    public Observable<Track> findAllMusicFiles() {
        return Observable.create(s -> {
            ContentResolver cr = context.getContentResolver();

            Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
            String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
            Cursor cur = cr.query(uri, null, selection, null, sortOrder);

            if(cur != null)
            {
                if(cur.getCount() > 0)
                {
                    while(cur.moveToNext())
                    {
                        String data = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DATA));
                        File f = new File(data);
                        if (f.length() != 0) {
                            try {
                                s.onNext(MediaMapper.mapFileToTrack(f));
                            } catch (Exception ignored) {
                            }
                        }
                    }

                }
                cur.close();
            }
            s.onComplete();
        });
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
