package com.tcc.carloshenrique.hungryover.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tcc.carloshenrique.hungryover.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.txtItem) public TextView txtItem;
    //@BindView(R.id.txtPrice) public TextView txtPrice;
    public int ItemId;

    public ItemHolder(View itemView, int id) {
        super(itemView);
        ItemId = id;

        ButterKnife.bind(this, itemView);
    }
}
