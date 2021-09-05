package com.hynson.gallery.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.AutoCompleteTextView;

public class AutoTextView extends AutoCompleteTextView {
    final static String TAG = "AutoTextView";
    private Drawable clearIcon;
    private Context mContext;

    public AutoTextView(Context context) {
        super(context);
        mContext = context;
    }

    public AutoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public AutoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        Log.i(TAG, "onTouchEvent: "+getLayoutParams().width);
        if (clearIcon != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Rect rect = new Rect();
            if(getGlobalVisibleRect(rect)){
                rect.left = rect.right - 80;
            }
            if(rect.contains(eventX, eventY)) setText(null);
        }
        return super.onTouchEvent(event);
    }
}
