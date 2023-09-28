package com.hynson.gallery.view;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hynson.gallery.R;
import com.hynson.gallery.entity.SearchTipItem;

import java.util.ArrayList;
import java.util.List;

public class AutoSearchAdapter extends BaseAdapter implements Filterable {

    final static String TAG = "Auto";
    private Context context;

    private ArrayFilter mFilter;
    private List<SearchTipItem> mShowList;
    private ArrayList<SearchTipItem> mUnfilteredData;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onClick(SearchTipItem item);

        void onLongClick(SearchTipItem item);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public AutoSearchAdapter(List<SearchTipItem> nameList, Context context) {
        if (mShowList == null)
            mShowList = new ArrayList<>();
        mShowList.addAll(nameList);
        this.context = context;
    }

    public void setData(List<SearchTipItem> list) {
        mShowList.clear();
        mShowList.addAll(list);
        mUnfilteredData.clear();
        mUnfilteredData.addAll(list);
    }

    @Override
    public int getCount() {
        return mShowList == null ? 0 : mShowList.size();
    }

    @Override
    public Object getItem(int position) {
        return mShowList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if (convertView == null) {
            view = View.inflate(context, R.layout.smart_search_item, null);
            holder = new ViewHolder();
            holder.mName = view.findViewById(R.id.smart_search_name_tv);
            holder.mLayout = view.findViewById(R.id.search_item_ll);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        SearchTipItem item = mShowList.get(position);
        //item的点击事件
        if (mOnItemClickListener != null) {
            holder.mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(item);
                }
            });
            holder.mLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(item);
                    return false;
                }
            });
        }
        if (item.type == SearchTipItem.NEW) {
            holder.mName.setText("search " + item.name);
        } else {
            String name = item.name;
            holder.mName.setText(name);
        }
        return view;
    }

    static class ViewHolder {
        public TextView mName;
        public LinearLayout mLayout;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    private class ArrayFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (mUnfilteredData == null) {
                mUnfilteredData = new ArrayList<>(mShowList);
            }

            if (prefix == null || prefix.length() == 0) {
                ArrayList<SearchTipItem> list = mUnfilteredData;
                results.values = list;
                results.count = list.size();
                Log.i(TAG, "performFiltering: " + list.size());
            } else {
                String prefixString = prefix.toString().toLowerCase();

                Log.i(TAG, "performFiltering: " + prefixString);

                ArrayList<SearchTipItem> unfilteredValues = mUnfilteredData;
                int count = unfilteredValues.size();

                ArrayList<SearchTipItem> newValues = new ArrayList<>(count);

                for (int i = 0; i < count; i++) {
                    String pc = unfilteredValues.get(i).name;
                    if (pc != null && pc.contains(prefixString)) {
                        newValues.add(new SearchTipItem(pc));
                    }
                }
                if (newValues.isEmpty()) {
                    newValues.add(new SearchTipItem(SearchTipItem.NEW, prefixString));
                }
                results.values = newValues;
                results.count = newValues.size();
            }
            Log.i(TAG, "publishResults:1 " + System.identityHashCode(results));

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            Log.i(TAG, "publishResults:2 " + System.identityHashCode(results));
            mShowList.clear();
            mShowList.addAll((List<SearchTipItem>) results.values);
            //mShowList = (List<String>) results.values;
            if (results.count > 0) {
                Log.i(TAG, "publishResults: " + results.count);
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
