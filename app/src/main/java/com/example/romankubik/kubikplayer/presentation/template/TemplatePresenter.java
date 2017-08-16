package com.example.romankubik.kubikplayer.presentation.template;

import com.example.romankubik.kubikplayer.general.di.ActivityScope;

import javax.inject.Inject;

/**
 * Created by roman.kubik on 8/16/17.
 */

@ActivityScope
public class TemplatePresenter {

    public interface View {
    }

    private View view;

    @Inject
    public TemplatePresenter(View view) {
        this.view = view;
    }
}
