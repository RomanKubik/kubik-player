package com.example.romankubik.kubikplayer.presentation.audiolist;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by roman.kubik on 8/17/17.
 */

public class AudioColumnDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public AudioColumnDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {

        if (parent.getChildLayoutPosition(view) % 2 == 0) {
            outRect.right = space;
        } else {
            outRect.left = space;
        }

        if (parent.getChildLayoutPosition(view) == 0 || parent.getChildLayoutPosition(view) == 1) {
            outRect.top = 0;
        } else {
            outRect.top = 2 * space;
        }
    }
}
