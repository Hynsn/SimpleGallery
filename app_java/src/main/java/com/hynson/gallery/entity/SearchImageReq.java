package com.hynson.gallery.entity;

import java.util.HashMap;
import java.util.Map;

public class SearchImageReq {
    public int perPage;
    public String category;
    public String imageType;
    public String key;

    public int page = 0;

    public SearchImageReq(Integer perPage, String category, String imageType, String key) {
        this.perPage = perPage;
        this.category = category;
        this.imageType = imageType;
        this.key = key;
    }

    public Map getMap(String apikey) {
        HashMap map = new HashMap<String, String>();
        map.put("key", apikey);
        map.put("per_page", perPage + "");
        map.put("category", category);
        map.put("image_type", imageType);
        map.put("q", key);
        if (page > 0) {
            map.put("page", page + "");
        }
        return map;
    }
}
