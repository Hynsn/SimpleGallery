package com.hynson.gallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SearchSharedPreferences {
    final static String TAG = "SearchSharedPreferences";

    final static String SEARCH_HISTORY = "search_history";

    public static void addSearchHistoty(Context context, String item){
        boolean isContains = false;
        SharedPreferences updaterShared = context.getSharedPreferences("search_history", Context.MODE_PRIVATE);
        Set<String> localSet = updaterShared.getStringSet(SEARCH_HISTORY,null);
        if(localSet==null) localSet = new HashSet<>();
        Iterator<String> it = localSet.iterator();
        while (it.hasNext()) {
            if(it.next().equals(item)) {
                isContains = true;
                break;
            }
        }
        if(!isContains) {
            Log.i(TAG, "isContains: "+localSet.add(item));
            SharedPreferences.Editor editor = updaterShared.edit();
            editor.putStringSet(SEARCH_HISTORY, localSet);
            editor.apply();

            List<String> list = getSearchHistoty(context);
            for (int i = 0; i < list.size(); i++) {
                Log.i(TAG, "addSearchHistoty: "+list.get(i));
            }
        }
    }

    public static List<String> getSearchHistoty(Context context){
        SharedPreferences updaterShared = context.getSharedPreferences("search_history", Context.MODE_PRIVATE);
        Set<String> localSet = updaterShared.getStringSet(SEARCH_HISTORY,null);
        return new ArrayList<>(localSet);
    }
}
