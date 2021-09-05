package com.hynson.gallery.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;

public class EditTextWithClear extends EditText implements TextWatcher {
    final static String TAG = "EditText";
    private Drawable clearIcon;
    private Context mContext;
    public EditTextWithClear(Context context) {
        super(context);
        mContext = context;
    }

    public EditTextWithClear(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public EditTextWithClear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public void setClearIcon(int id) {
        if(clearIcon==null){
            clearIcon = mContext.getResources().getDrawable(id);
        }
        setCompoundDrawablesWithIntrinsicBounds(null, null, (length()<1) ? null : clearIcon, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (clearIcon != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Rect rect = new Rect();
            int[] locationScreen = new int[2];
            getLocationOnScreen(locationScreen); // 在屏幕中的位置
            int sX = locationScreen[0];
            int sY = locationScreen[1];

            int[] locationWindow = new int[2];
            getLocationInWindow(locationWindow); // 在窗口中的位置
            int wX = locationWindow[0];
            int wY = locationWindow[1];
            if (getGlobalVisibleRect(rect)) {
                Log.i(TAG, "onTouchEvent1 触摸坐标:"+eventX+","+eventY+" 测量坐标: 窗口("+wX+","+wY+"),屏幕("+sX+","+sY+"),矩形 "+rect);
                if (wX == sX) { // 水平方向上，窗口和屏幕中的位置相同时
                    rect.left = rect.right - clearIcon.getIntrinsicWidth()*2;
                    if(wY!=sY){
                        if(sY>rect.top && sY<rect.bottom){ // 屏幕坐标在矩形内
                            rect.top = sY;
                            rect.bottom = sY+clearIcon.getIntrinsicHeight()*2;
                        }
                        else { // Rect不在屏幕内
                            rect.top = sY;
                            rect.bottom = sY-rect.height();
                        }
                    }
                } else { // 不相同时
                    rect.right += sX;
                    rect.left = rect.right - clearIcon.getIntrinsicWidth()*2;
                    rect.bottom = sY + rect.height();
                    rect.top = sY;
                }
                Log.i(TAG, "onTouchEvent2: "+rect);
            }
            if (rect.contains(eventX, eventY)) setText(null);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Log.i(TAG, "beforeTextChanged: ");
    }

    @Override
    public void afterTextChanged(Editable editable) {
        Log.i(TAG, "afterTextChanged: ");
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Log.i(TAG, "onTextChanged: ");
        if(clearIcon!=null)
            setCompoundDrawablesWithIntrinsicBounds(null, null, (length()<1) ? null : clearIcon, null);
    }
}
