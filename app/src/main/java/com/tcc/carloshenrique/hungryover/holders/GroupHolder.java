package com.tcc.carloshenrique.hungryover.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcc.carloshenrique.hungryover.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupHolder extends RecyclerView.ViewHolder {
    /*@BindView(R.id.txtCategory)*/ public TextView txtCategory;
    @BindView(R.id.imgCategory) public ImageView imgCategory;
    public int categoryId;

    public GroupHolder(View itemView, int id) {
        super(itemView);
        txtCategory = itemView.findViewById(R.id.txtCategory);
        categoryId = id;

        ButterKnife.bind(this, itemView);
    }
}