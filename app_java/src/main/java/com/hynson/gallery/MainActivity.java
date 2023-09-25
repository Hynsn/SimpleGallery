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
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.hynson.gallery.entity.ImageBean;
import com.hynson.gallery.view.AutoSearchAdapter;
import com.hynson.gallery.view.AutoTextView;
import com.hynson.gallery.view.ImageAdapter;
import com.hynson.gallery.view.SettingDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";

    Context mContext;

    // 搜索历史
    AutoTextView autoTV;
    Button dialogBtn, searchBtn;
    AutoSearchAdapter searchAdapter;
    List<String> historyList;

    // 图片加载
    ProgressBar loadPB;
    RecyclerView imageRV;
    ImageAdapter imageAdapter;
    StaggeredGridLayoutManager staggeredManager;
    List<ImageBean> imageList;

    // http请求参数
    String[] mParams = {
            "14413115-ccc6a3f9a9c37f4f850f30517", // apikey
            "200",                                // per page
            "all",                                   // category
            "all",                                 // image type
            ""                                   // search key
    };

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
        public void pre() {
            imageAdapter.clearData();
            loadPB.setVisibility(View.VISIBLE);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(autoTV.getWindowToken(), 0);
            }
        }

        @Override
        public void result(List<ImageBean> list, int total) {
            imageList.clear();
            imageList.addAll(list);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (imageList.size() <= 0) {
                        Toast.makeText(getBaseContext(), "搜索为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    imageAdapter.updateData(imageList, imageList.size());
                    Log.i(TAG, "imageAdapter: " + imageAdapter.getItemCount());
                }
            });
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
            loadPB.setVisibility(View.GONE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();

        autoTV = findViewById(R.id.input_auto_tv);
        dialogBtn = findViewById(R.id.dialog_btn);
        searchBtn = findViewById(R.id.search_btn);
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

        imageRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //super.onScrolled(recyclerView, dx, dy);
                if (imageAdapter.getItemCount() >= imageList.size()) {
                    return;
                }
                if (recyclerView.canScrollVertically(1)) {
                    new Thread() {
                        @Override
                        public void run() {
                            mHandler.post(scrollRunable);
                        }
                    }.start();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        imageRV.setAdapter(imageAdapter);
        historyList = SearchSharedPreferences.getSearchHistoty(mContext);
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
            public void onClick(String str) {
                autoTV.setText(str); //将点击到的item赋值给输入框
                autoTV.setSelection(str.length());//将光标移至文字末尾
                autoTV.dismissDropDown();
            }

            @Override
            public void onLongClick(String str) {
                autoTV.setText(str);
                autoTV.setSelection(str.length());
                autoTV.dismissDropDown();
            }
        });

        dialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingDialog settingDialog = new SettingDialog();
                settingDialog.showDialog(R.layout.setting_dialog, mParams[0], getSupportFragmentManager());
                settingDialog.setUpdateCallBack(new SettingDialog.UpdateCallBack() {

                    @Override
                    public void update(String... params) {
                        System.arraycopy(params, 0, mParams, 0, params.length);
                    }
                });
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mParams[4] = autoTV.getText().toString();
                if (mParams[4].length() < 1) {
                    Toast.makeText(mContext, "输入为空！", Toast.LENGTH_SHORT).show();
                } else {
                    SearchSharedPreferences.addSearchHistoty(mContext, mParams[4]);
                    searchAdapter.setData(SearchSharedPreferences.getSearchHistoty(mContext));
                    imageLoadTask = new ImageLoadTask();
                    imageLoadTask.setLoadCallBack(loadCallBack);
                    imageLoadTask.execute(mParams[0], mParams[1], mParams[2], mParams[3], mParams[4]);
                }
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
