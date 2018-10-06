package com.tcc.carloshenrique.hungryover.holders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcc.carloshenrique.hungryover.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.txtCategory) public TextView txtCategory;
    @BindView(R.id.imgCategory) public ImageView imgCategory;

    private RecyclerView.RecyclerListener recycleListener;

    public int categoryId;

    public CategoryHolder(View itemView, int id) {
        super(itemView);
        categoryId = id;

        ButterKnife.bind(this, itemView);
    }
}