package com.hynson.gallery;

import android.os.AsyncTask;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.hynson.gallery.entity.ImageBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ImageLoadTask extends AsyncTask<Map<String, String>, Void, Integer> {
    final static String TAG = "ImageLoadTask";

    private static OkHttpClient httpClient;

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        httpClient = builder.build();
    }

    @Override
    protected void onPreExecute() {
        loadCallBack.pre();
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(Map... maps) {
        Request.Builder builder = new Request.Builder();
        builder.addHeader("Accept", "text/html,application/json");
        builder.addHeader("Accept-Encoding", "utf-8");
        HttpUrl.Builder httpUrl = HttpUrl.parse("https://pixabay.com/api/?pretty=false").newBuilder();
        Set<Map.Entry<String, String>> entrySet = maps[0].entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            httpUrl.addQueryParameter(entry.getKey(), entry.getValue());
        }
        Request req = builder.url(httpUrl.build()).get().build();
        Log.i(TAG, "search: " + req);
        Call call = httpClient.newCall(req);
        List<ImageBean> images = new ArrayList<>();
        try {
            String body = call.execute().body().string();
            Log.i(TAG, "body: " + body);
            JSONObject root = JSON.parseObject(body);
            JSONArray array = root.getJSONArray("hits");
            for (int i = 0; i < array.size(); i++) {
                String id = array.getJSONObject(i).get("id").toString();
                String tags = array.getJSONObject(i).get("tags").toString();
                String previewURL = array.getJSONObject(i).get("previewURL").toString();
                String likes = array.getJSONObject(i).get("likes").toString();
                String previewWidth = array.getJSONObject(i).get("previewWidth").toString();
                String previewHeight = array.getJSONObject(i).get("previewHeight").toString();
                String webformatURL = array.getJSONObject(i).get("webformatURL").toString();
                String webformatHeight = array.getJSONObject(i).get("webformatHeight").toString();
                String webformatWidth = array.getJSONObject(i).get("webformatWidth").toString();
                ImageBean info = new ImageBean(Long.valueOf(id), tags, Integer.valueOf(likes),
                        previewURL, Integer.valueOf(previewWidth), Integer.valueOf(previewHeight),
                        webformatURL, Integer.valueOf(webformatHeight), Integer.valueOf(webformatWidth));
                images.add(info);
            }
            loadCallBack.result(images, array.size());
        } catch (IOException | JSONException e) {
            loadCallBack.error(e.getMessage());
            e.printStackTrace();
        }
        return images.size();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        if (integer != null)
            loadCallBack.post();
        super.onPostExecute(integer);
    }

    private LoadCallBack loadCallBack;

    public void setLoadCallBack(LoadCallBack loadCallBack) {
        this.loadCallBack = loadCallBack;
    }
}
