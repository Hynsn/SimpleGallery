package com.hynson.gallery.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.hynson.gallery.R;

public class SettingDialog extends DialogFragment implements View.OnClickListener {
    final static String TAG = "SettingDialog";

    View mView;
    EditTextWithClear pwdET;
    Button cancelBtn;
    NumberPicker perpageNP,categoryNP,typeNP;
    String[] imageCategory = {"all","fashion", "nature", "backgrounds", "science", "education", "people", "feelings", "religion", "health", "places", "animals", "industry", "food", "computer", "sports", "transportation", "travel", "buildings", "business", "music"};
    String[] imageTypes = {"all", "photo", "illustration", "vector"};

    @Override
    public void onStart() {
        super.onStart();
        // 窗口中不同参数测试
        // 1、MATCH_PARENT
        getDialog().getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,-2);
        /*// 2、WRAP_CONTENT
        getDialog().getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,-2);*/
        /*// 3、根据屏幕大小缩放
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        getDialog().getWindow().setLayout((int)(metrics.widthPixels *0.8),(int)(metrics.heightPixels *0.8));*/

        getDialog().setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        int resId = getArguments().getInt("dialog_resid");
        String key = getArguments().getString("dialog_key");
        mView = inflater.inflate(resId,container,false);
        pwdET = mView.findViewById(R.id.pwd_et);
        pwdET.setClearIcon(R.drawable.auto_clear_icon30);
        pwdET.setText(key);
        cancelBtn = mView.findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(this);

        perpageNP = mView.findViewById(R.id.change_perpage_np);
        categoryNP = mView.findViewById(R.id.change_category_np);
        typeNP = mView.findViewById(R.id.change_type_np);
        perpageNP.setMaxValue(200);
        perpageNP.setMinValue(20);
        perpageNP.setValue(20);
        perpageNP.setWrapSelectorWheel(true);

        categoryNP.setDisplayedValues(imageCategory);
        categoryNP.setMinValue(0);
        categoryNP.setMaxValue(imageCategory.length - 1);
        categoryNP.setValue(0);
        categoryNP.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);

        typeNP.setDisplayedValues(imageTypes);
        typeNP.setMinValue(0);
        typeNP.setMaxValue(imageTypes.length - 1);
        typeNP.setValue(0);
        typeNP.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);

        return mView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancel_btn:
                updateCallBack.update(pwdET.getText().toString().trim(),perpageNP.getValue()+"",imageCategory[categoryNP.getValue()],imageTypes[typeNP.getValue()]);
                dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        super.show(manager,tag);
        manager.executePendingTransactions();
    }

    public void showDialog(int resid,String key,FragmentManager manager){
        Bundle args = new Bundle();
        args.putInt("dialog_resid", resid);
        args.putString("dialog_key",key);
        setArguments(args);
        if (!isAdded()) {
            show(manager, TAG);
        }
        else {
            dismiss();
            show(manager, TAG);
        }
        args.clear();
    }
    private UpdateCallBack updateCallBack;

    public void setUpdateCallBack(UpdateCallBack updateCallBack) {
        this.updateCallBack = updateCallBack;
    }

    public interface UpdateCallBack {
        void update(String... params);
    }
}
