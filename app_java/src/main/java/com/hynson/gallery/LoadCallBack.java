package com.hynson.gallery;

import com.hynson.gallery.entity.ImageBean;

import java.util.List;

public interface LoadCallBack {
    boolean isLoadMore();

    void pre();

    void result(List<ImageBean> list, int total);

    void error(String error);

    void post();
}