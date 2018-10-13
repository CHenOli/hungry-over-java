package com.tcc.carloshenrique.hungryover.adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.tcc.carloshenrique.hungryover.R;
import com.tcc.carloshenrique.hungryover.holders.ItemHolder;
import com.tcc.carloshenrique.hungryover.models.ItemModel;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.PSource;

public class ItemAdapter extends  RecyclerView.Adapter<ItemHolder> {
    private List<ItemModel> OrderItems;
    private final List<ItemModel> ApiItems;
    private Context Context;
    private int Position;

    public ItemAdapter(List<ItemModel> items) {
        OrderItems = new ArrayList<>();
        ApiItems = items;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context = parent.getContext();
        return new ItemHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_items_card, parent, false), 1);
    }

    @Override
    public void onBindViewHolder(final ItemHolder holder, final int position) {
        Position = position;

        holder.txtItemName.setText(ApiItems.get(position).getNome());
        holder.txtItemPrice.setText("R$ " + String.format("%.2f", ApiItems.get(position).getValor()));

        //Adicionar no onClick para abrir a activity correspondete ao item clicado
        holder.btnItemAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                OrderItems.add(ApiItems.get(Position));
            }
        });
        holder.btnItemInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return ApiItems != null ? ApiItems.size() : 0;
    }

    public void updateList(List<ItemModel> items) {
        insertItem(items);
    }

    // Método responsável por inserir um novo usuário na lista e notificar que há novos itens.
    private void insertItem(List<ItemModel> items) {
        ApiItems.addAll(items);

        notifyItemInserted(getItemCount());
    }

    public List<ItemModel> getCartItems(){
        return  OrderItems;
    }
}