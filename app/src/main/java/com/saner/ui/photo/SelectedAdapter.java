package com.saner.ui.photo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.saner.R;
import com.saner.util.PhotoUtil;

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
        PhotoUtil.showImageLayoutMeasure(holder.mImage, mColumns);
        PhotoUtil.load(holder.mImage, data.getUrl());
        holder.mCheckBox.setChecked(data.isSelected());

        if (data.isSelected()) {
            holder.mImage.setAlpha(0.5f);
        } else {
            holder.mImage.setAlpha(1.0f);
        }

        if (data.isCheckEnabled()){
            holder.mCheckBox.setEnabled(false);
        }else {
            holder.mCheckBox.setEnabled(true);
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


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImage;
        CheckBox mCheckBox;

        ViewHolder(View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.item_photo_image);
            mCheckBox = itemView.findViewById(R.id.item_photo_box);
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
