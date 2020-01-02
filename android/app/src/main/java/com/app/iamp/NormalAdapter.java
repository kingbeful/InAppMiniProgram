package com.app.iamp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class NormalAdapter extends RecyclerView.Adapter<NormalAdapter.VH> {
    private String[] mData;
    private static final String TAG = NormalAdapter.class.getSimpleName();
    private OnItemClicked onClick;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public static class VH extends RecyclerView.ViewHolder{
        public final TextView title;
        public VH(View v) {
            super(v);
            title = v.findViewById(R.id.title);
        }
    }

    public NormalAdapter(String[] data) {
        this.mData = data;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.title.setText(mData[position]);
        holder.itemView.setOnClickListener((View v) -> {
            //item 点击事件
            onClick.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new VH(v);
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }
}