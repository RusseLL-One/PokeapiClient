package com.one.russell.pokeapiclient.presenter;

import android.widget.CompoundButton;

import com.one.russell.pokeapiclient.view.ViewInterface;

import java.util.List;

public interface PresenterInterface<V extends ViewInterface> {
    void attachView(V mvpView);

    void viewIsReady();

    void initModel(boolean randomStartPosition);

    void detachView();

    void setList(List<?> list);

    CompoundButton.OnCheckedChangeListener getCheckBoxListener();
}
