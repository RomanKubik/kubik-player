package com.example.romankubik.kubikplayer.presentation.main;

import javax.inject.Inject;

/**
 * Created by roman.kubik on 8/16/17.
 */

public class MainPresenter {

    public interface View {

    }

    private View view;

    @Inject
    public MainPresenter(View view) {
        this.view = view;
    }
}
