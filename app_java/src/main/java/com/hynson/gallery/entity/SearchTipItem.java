package com.hynson.gallery.entity;

/**
 * Author: Hynsonhou
 * Date: 2023/9/25 13:42
 * Description: 搜索提示
 * History:
 * <author> <time> <version> <desc>
 * Hynsonhou 2023/9/25 1.0 首次创建
 */
public class SearchTipItem {
    public int type = 0;
    public String name;

    public SearchTipItem(String name) {
        this.type = MATCH;
        this.name = name;
    }

    public SearchTipItem(int type, String name) {
        this.type = type;
        this.name = name;
    }

    private static final int MATCH = 0;
    public static final int NEW = 1;
}
