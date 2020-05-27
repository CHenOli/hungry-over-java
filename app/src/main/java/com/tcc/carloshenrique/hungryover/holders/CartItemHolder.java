package com.tcc.carloshenrique.hungryover.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcc.carloshenrique.hungryover.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartItemHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.txtItemName)
    public TextView txtItemName;
    @BindView(R.id.txtItemPrice)
    public TextView txtItemPrice;
    @BindView(R.id.txtItemCategory)
    public TextView txtItemCategory;
    @BindView(R.id.txtItemAmount)
    public TextView txtItemAmount;
    @BindView(R.id.txtItemTotal)
    public TextView txtItemTotal;
    @BindView(R.id.btnItemAdd)
    public Button btnItemAdd;
    @BindView(R.id.btnItemRemove)
    public Button btnItemRemove;

    public int ItemId;

    public CartItemHolder(View itemView, int id) {
        super(itemView);
        ItemId = id;

        ButterKnife.bind(this, itemView);
    }
}