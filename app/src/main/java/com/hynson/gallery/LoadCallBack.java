package com.hynson.gallery;

import com.hynson.gallery.entity.ImageBean;

import java.util.List;

public interface LoadCallBack {
    void pre();
    void result(List<ImageBean> list, int total);
    void error(String error);
    void post();
}