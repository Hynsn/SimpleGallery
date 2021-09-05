package com.hynson.gallery.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hynson.gallery.R;
import com.hynson.gallery.entity.ImageBean;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private List<ImageBean> list;

    public void updateData(List<ImageBean> images,int length){
        if(null==list){
            list = new ArrayList<>();
        }
        for (int i = 0; i < length; i++) {
            list.add(images.get(i));
            list.get(i).setSerial(i);
        }
        notifyDataSetChanged();
    }
    public void addData(List<ImageBean>  images,int addSize){
        int start = list.size();
        int end = images.size();

        end = (end -start)<addSize ? end : (start + addSize);
        for (int i = start; i < end; i++) {
            list.add(images.get(i));
            list.get(i).setSerial(i);
        }
        notifyItemRangeInserted(start,end-start);
    }

    public void clearData(){
        if(list!=null&&list.size()>0){
            notifyItemRangeRemoved(0,list.size());
            list.clear();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item,parent,false);
        return new ViewHolder(view);
    }

    // 绑定数据
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageBean image = list.get(position);
        // override解决图片变换的情况，需要指定ImageView宽高
        holder.imageIV.getLayoutParams().height = image.getWebformatHeight();
        Glide.with(holder.imageIV.getContext())
                .load(image.getPreviewUrl())
                .placeholder(R.drawable.dafult_photo)
                .error(R.drawable.dafult_photo)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.imageIV);
        holder.tags.setText(image.getTags());
        //holder.tags.setText(image.getSerial()+"");
        holder.likes.setText(image.getLikes()+"");
    }

    @Override
    public int getItemCount() {
        return null == list ? 0 : list.size();
    }

    //数据的显示视图类
    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageIV;
        TextView tags,likes;

        ViewHolder(View item) {
            super(item);
            imageIV = item.findViewById(R.id.item_image_iv);
            tags = item.findViewById(R.id.item_tags_tv);
            likes = item.findViewById(R.id.item_likes_tv);
        }
    }
}
