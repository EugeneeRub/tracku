package com.erproject.busgo.base.mvpInterfaces;

public interface BasePresenter<T> {
    void takeView(T view);
    void dropView();
}
