package com.one.russell.pokeapiclient.presenter;

import com.one.russell.pokeapiclient.view.ViewInterface;

public abstract class BasePresenter<T extends ViewInterface> implements PresenterInterface<T> {

    private T view;

    @Override
    public void attachView(T mvpView) {
        view = mvpView;
    }

    @Override
    public void detachView() {
        view = null;
    }

    public T getView() {
        return view;
    }
}
