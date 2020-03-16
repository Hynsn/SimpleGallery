# TestAuto 
安卓搜索输入提示实现

## 2019.11.6
1. 实现输入搜索功能
2. 带删除功能

## 演示
<img src="https://github.com/Hynsn/TestAuto/blob/master/device-2019-11-06-114907.gif?raw=true" width="400" height="800">

## 2019.11.27 
1. 修复EditTextWithClear中Icon横竖屏切换和DialogFragment中无效的问题（AutoTextView中同样的原理）
2. 新增DialogFragment使用EditTextWithClear实例
```
注: 
//1.MATCH_PARENT  
getDialog().getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,-1);
//2.WRAP_CONTENT  
getDialog().getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,-2);
//3.根据屏幕大小缩放  
DisplayMetrics metrics = new DisplayMetrics();
getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
getDialog().getWindow().setLayout((int)(metrics.widthPixels *0.8),(int)(metrics.heightPixels *0.8));
```
## 2019.12.9 
1. 增加图片搜索功能和图片搜索筛选对话框
2. 修复EditTextWithClear控件父布局为(MATCH_PARENT,-2)、(WRAP_CONTENT,-1)删除无效的问题
3. 记录搜索历史，搜索关键字存入SharedPreferences完善本项目
## 演示
<img src="https://github.com/Hynsn/TestAuto/blob/master/device-2019-12-11-161934.gif?raw=true" width="400" height="800">

## 2020.1.14 
1. 修复图片下滑过程中重新测量排版显示