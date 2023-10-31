package com.hynson.gallery;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.hynson.gallery.entity.ImageBean;
import com.hynson.gallery.entity.SearchImageReq;
import com.hynson.gallery.entity.SearchTipItem;
import com.hynson.gallery.view.AutoSearchAdapter;
import com.hynson.gallery.view.AutoTextView;
import com.hynson.gallery.view.ImageAdapter;
import com.hynson.gallery.view.LoadMoreListener;
import com.hynson.gallery.view.SettingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";

    Context mContext;

    // 搜索历史
    AutoTextView autoTV;
    Button dialogBtn, searchBtn;
    AutoSearchAdapter searchAdapter;
    List<SearchTipItem> historyList = new ArrayList<SearchTipItem>();

    // 图片加载
    ProgressBar loadPB;
    RecyclerView imageRV;
    ImageAdapter imageAdapter;
    StaggeredGridLayoutManager staggeredManager;
    List<ImageBean> imageList;

    // http请求参数
    private SearchImageReq searchImageReq = new SearchImageReq(20, "all", "all", "");
    private String API_KEY = "14413115-ccc6a3f9a9c37f4f850f30517";
    String[] testArry = {
            "hello", "world"
    };

    ImageLoadTask imageLoadTask;

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            return false;
        }
    });

    Runnable scrollRunable = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG, "run: +++++");
            imageAdapter.addData(imageList, 4);
        }
    };

    LoadCallBack loadCallBack = new LoadCallBack() {
        @Override
        public boolean isLoadMore() {
            return searchImageReq.page != 0;
        }

        @Override
        public void pre() {
            if (!isLoadMore()) {
                imageAdapter.clearData();
                loadPB.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(autoTV.getWindowToken(), 0);
                }
            }
        }

        @Override
        public void result(List<ImageBean> list, int total) {
            if (total == 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "无数据", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                if (!isLoadMore()) {
                    imageList.clear();
                    imageList.addAll(list);
                } else {
                    imageList.addAll(list);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (list.size() <= 0) {
                            Toast.makeText(getBaseContext(), "搜索为空", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        imageAdapter.updateData(list, list.size());
                        Log.i(TAG, "imageAdapter: " + imageAdapter.getItemCount());
                    }
                });
            }
        }

        @Override
        public void error(String error) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getBaseContext(), "Error:" + error, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void post() {
            if (!isLoadMore()) {
                loadPB.setVisibility(View.GONE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();

        autoTV = findViewById(R.id.input_auto_tv);
        dialogBtn = findViewById(R.id.filter_btn);
        //        searchBtn = findViewById(R.id.search_btn);
        imageRV = findViewById(R.id.image_list_rv);
        loadPB = findViewById(R.id.load_pb);
        loadPB.setVisibility(View.GONE);
        autoTV.setClearIcon(R.drawable.auto_clear_icon30);
        for (int i = 0; i < testArry.length; i++) {
            SearchSharedPreferences.addSearchHistoty(mContext, testArry[i]);
        }

        if (null == imageList) {
            imageList = new ArrayList<>();
        }
        if (null == imageAdapter) {
            imageAdapter = new ImageAdapter();
        }
        Configuration config = getResources().getConfiguration(); //获取设置的配置信息
        if (null == staggeredManager) {
            staggeredManager = new StaggeredGridLayoutManager((config.orientation == Configuration.ORIENTATION_LANDSCAPE) ? 3 : 2, StaggeredGridLayoutManager.VERTICAL);
        }
        imageRV.setLayoutManager(staggeredManager);
        staggeredManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//防止item 交换位置
        ((SimpleItemAnimator) Objects.requireNonNull(imageRV.getItemAnimator())).setSupportsChangeAnimations(false);
        imageRV.getItemAnimator().setChangeDuration(0);
        imageRV.addOnScrollListener(new LoadMoreListener() {
            @Override
            public void onLoadMore(int lastPosition) {
                new Thread() {
                    @Override
                    public void run() {
                        searchImageReq.page++;
                        ImageLoadTask imageLoadTask = new ImageLoadTask();
                        imageLoadTask.setLoadCallBack(loadCallBack);
                        imageLoadTask.execute(searchImageReq.getMap(API_KEY));
                    }
                }.start();
            }
        });
        imageRV.setAdapter(imageAdapter);
        List<String> list = SearchSharedPreferences.getSearchHistoty(mContext);
        historyList.clear();
        for (int i = 0; i < list.size(); i++) {
            historyList.add(new SearchTipItem(list.get(i)));
        }
        searchAdapter = new AutoSearchAdapter(historyList, MainActivity.this);
        autoTV.setThreshold(1); // 设置至少输入1个字符开始匹配提示
        autoTV.setAdapter(searchAdapter);
        autoTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoTV.showDropDown();
            }
        });
        autoTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i(TAG, "beforeTextChanged: ");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                autoTV.setClearIcon(R.drawable.auto_clear_icon30); // 更新icon
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i(TAG, "afterTextChanged: ");
            }
        });

        searchAdapter.setOnItemClickListener(new AutoSearchAdapter.OnItemClickListener() {
            @Override
            public void onClick(SearchTipItem item) {
                autoTV.setText(item.name); //将点击到的item赋值给输入框
                autoTV.setSelection(item.name.length());//将光标移至文字末尾
                autoTV.dismissDropDown();
                String key = item.name;
                searchImageReq.key = item.name;
                if (key.length() < 1) {
                    Toast.makeText(mContext, "输入为空！", Toast.LENGTH_SHORT).show();
                } else {
                    SearchSharedPreferences.addSearchHistoty(mContext, key);
                    imageLoadTask = new ImageLoadTask();
                    imageLoadTask.setLoadCallBack(loadCallBack);

                    imageLoadTask.execute(searchImageReq.getMap(API_KEY));
                }
            }

            @Override
            public void onLongClick(SearchTipItem item) {
                autoTV.setText(item.name);
                autoTV.setSelection(item.name.length());
                autoTV.dismissDropDown();
            }
        });

        dialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingDialog settingDialog = new SettingDialog();
                settingDialog.showDialog(R.layout.setting_dialog, API_KEY, getSupportFragmentManager());
                settingDialog.setUpdateCallBack(new SettingDialog.UpdateCallBack() {
                    @Override
                    public void update(String apiKey, int perPage, String category, String imageType) {
                        API_KEY = apiKey;
                        searchImageReq.perPage = perPage;
                        searchImageReq.category = category;
                        searchImageReq.imageType = imageType;
                    }
                });
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        staggeredManager.setSpanCount((newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) ? 3 : 2);
        imageRV.setLayoutManager(staggeredManager);
    }
}
