package com.example.abhi.jsshndemo.recyclerview.adapters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhi.jsshndemo.R;
import com.example.abhi.jsshndemo.model.InnerData;
import com.example.abhi.jsshndemo.recyclerview.viewholders.InnerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhi on 1/2/18.
 */

public class InnerAdapter extends com.ramotion.garlandview.inner.InnerAdapter<InnerItem> {

    private final List<InnerData> mData = new ArrayList<>();

    @Override
    public InnerItem onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new InnerItem(view);
    }

    @Override
    public void onBindViewHolder(InnerItem holder, int position) {
        holder.setContent(mData.get(position));
    }

    @Override
    public void onViewRecycled(InnerItem holder){
        holder.clearContent();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.inner_item;
    }

    public void addData(@NonNull List<InnerData> innerDataList){
        final int size = mData.size();
        mData.addAll(innerDataList);
        notifyItemRangeInserted(size,innerDataList.size());
    }

    public void clearData(){
        mData.clear();
        notifyDataSetChanged();
    }

}
