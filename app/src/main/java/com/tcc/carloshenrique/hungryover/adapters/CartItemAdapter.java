package com.tcc.carloshenrique.hungryover.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tcc.carloshenrique.hungryover.R;
import com.tcc.carloshenrique.hungryover.holders.ItemHolder;
import com.tcc.carloshenrique.hungryover.models.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class CartItemAdapter extends  RecyclerView.Adapter<ItemHolder> {
    private List<ItemModel> OrderItems;
    private Context Context;
    private int Position;

    public CartItemAdapter(List<ItemModel> items) {
        OrderItems = new ArrayList<>();
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context = parent.getContext();
        return new ItemHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_items, parent, false), 1);
    }

    @Override
    public void onBindViewHolder(final ItemHolder holder, int position) {
        Position = position;

        holder.txtItemName.setText(OrderItems.get(position).getNome());
        holder.txtItemPrice.setText("R$ " + String.format("%.2f", OrderItems.get(position).getValor()));
    }

    @Override
    public int getItemCount() {
        return OrderItems != null ? OrderItems.size() : 0;
    }

    public void updateList(List<ItemModel> items) {
        insertItem(items);
    }

    // Método responsável por inserir um novo usuário na lista e notificar que há novos itens.
    private void insertItem(List<ItemModel> orderItems) {
        OrderItems.addAll(orderItems);

        notifyItemInserted(getItemCount());
    }
}