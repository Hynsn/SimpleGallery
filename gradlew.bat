package com.ut.smartcook.frag;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ut.smartcook.R;
import com.ut.smartcook.view.CategoryAdapter;
import com.ut.smartcook.view.RecipeCardAdapter;
import com.ut.smartcook.view.RecipeTxtAdapter;
import com.ut.smartcook.view.TaskAdapter;
import com.ut.smartcook.view.entity.CategoryInfo;
import com.ut.smartcook.view.entity.RecipeInfo;
import com.ut.smartcook.view.entity.TaskInfo;

import java.util.ArrayList;
import java.util.List;

public class RecipeCategoryHeadFrag extends Fragment {
    List<CategoryInfo> categoryInfos;

    String[] categoryInfosTest = new String[]{"辣不怕","招牌菜","田园时蔬","无肉不欢","快手菜","爆炒菜","酸酸辣辣","湘菜","粤菜","推荐","新菜系","台湾菜"};

    public static RecipeCategoryHeadFrag newIntance(){
        RecipeCategoryHeadFrag categoryHeadFrag = new RecipeCategoryHeadFrag();
        return categoryHeadFrag;
    }
        
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_category_head,null);

        RecyclerView recycler = view.findViewById(R.id.category_list_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler.setLayoutManager(layoutManager);
        initCategoryInfos();
        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryInfos);
        recycler.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

        return view;
    }
    private void initCategoryInfos(){
        categoryInfos = new ArrayList<>();
        f