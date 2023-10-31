package com.hynson.gallery.view;

import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.Arrays;

public abstract class LoadMoreListener extends RecyclerView.OnScrollListener {
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        int lastPosition = -1;

        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof LinearLayoutManager) {
                lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                int[] outs = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                Log.i("TAG", "onScrollStateChanged: " + Arrays.toString(lastPositions) + "outs:" + Arrays.toString(outs));
                lastPosition = findMax(lastPositions);
            }
            if (lastPosition >= recyclerView.getLayoutManager().getItemCount() - 1) {
                onLoadMore(lastPosition);
            }
        }
    }

    private int findMax(int[] into) {
        int max = into[0];
        for (int i = 1; i < into.length; i++) {
            if (into[i] > max) {
                max = into[i];
            }
        }
        return max;
    }

    public abstract void onLoadMore(int lastPosition);
}
