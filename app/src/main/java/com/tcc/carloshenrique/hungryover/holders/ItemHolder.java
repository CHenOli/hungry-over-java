package com.tcc.carloshenrique.hungryover.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcc.carloshenrique.hungryover.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.txtNameItem) public TextView txtItemName;
    @BindView(R.id.txtPriceItem) public TextView txtItemPrice;
    @BindView(R.id.imgItem) public ImageView imgItem;
    @BindView(R.id.btn_ItemAdd) public Button btnItemAdd;
    @BindView(R.id.btn_ItemInfo) public Button btnItemInfo;

    public int ItemId;

    public ItemHolder(View itemView, int id) {
        super(itemView);
        ItemId = id;

        ButterKnife.bind(this, itemView);
    }
}