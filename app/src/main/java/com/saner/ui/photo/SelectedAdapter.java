package com.saner.ui.photo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.saner.R;
import com.saner.util.SelPhotoUtil;

import java.util.List;

/**
 * Created by sunset on 2018/3/21.
 */

public class SelectedAdapter extends RecyclerView.Adapter<SelectedAdapter.ViewHolder> {
    private List<PhotoModel> mDatas;
    private int mColumns;
    private OnItemClickListener listener;

    public SelectedAdapter(List<PhotoModel> mDatas, int columns) {
        this.mDatas = mDatas;
        this.mColumns = columns;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_selected, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final PhotoModel data = mDatas.get(position);
        SelPhotoUtil.showImageLayoutMeasure(holder.mImage, mColumns);
        SelPhotoUtil.load(holder.mImage, data.getUrl());
        setImageRes(data.isSelected(), holder.mCheckBox);
        setDisplay(data.isMulti(),holder.mCheckBox);
        if (data.isSelected()) {
            holder.mImage.setAlpha(0.5f);
        } else {
            holder.mImage.setAlpha(1.0f);
        }

        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSelected(data, position);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onShowPhoto(data, position);
            }
        });
    }

    /**
     *设置选中和未选中使用的图片
     */
    private void setImageRes(boolean isShow, ImageView view) {
        int resId = isShow ? R.mipmap.ic_checked : R.mipmap.ic_uncheck;
        view.setImageResource(resId);
    }

    /**
     *设置多选时显示选择框，单选时隐藏
     */
    private void setDisplay(boolean isMulti, ImageView view) {
        int vis = isMulti ? View.VISIBLE : View.GONE ;
        view.setVisibility(vis);
    }
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImage;
        ImageView mCheckBox;

        ViewHolder(View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.item_image);
            mCheckBox = itemView.findViewById(R.id.item_check);
        }


    }


    void setOnItemClickListener(OnItemClickListener onItemLongClickListener) {
        this.listener = onItemLongClickListener;
    }

    interface OnItemClickListener {
        void onSelected(PhotoModel data, int pos);

        void onShowPhoto(PhotoModel data, int pos);
    }
}
