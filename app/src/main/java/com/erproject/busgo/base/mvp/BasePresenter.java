package com.erproject.busgo.base.mvp;

public interface BasePresenter<T> {
    void takeView(T view);
    void dropView();
}
