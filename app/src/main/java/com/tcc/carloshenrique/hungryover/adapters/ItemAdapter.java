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

public class ItemAdapter extends  RecyclerView.Adapter<ItemHolder> {
    private final List<ItemModel> ApiItems;
    private Context context;

    public ItemAdapter(List<ItemModel> items) {
        ApiItems = items;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ItemHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_items_card, parent, false), 1);
    }

    @Override
    public void onBindViewHolder(final ItemHolder holder, int position) {
        holder.txtItemName.setText(ApiItems.get(position).getNome());
        holder.txtItemPrice.setText("R$ " + ApiItems.get(position).getValor().toString());

        //Adicionar no onClick para abrir a activity correspondete ao item clicado
        //holder.imgCategory.setOnClickListener(view -> ));
        //holder.txtCategory.setOnClickListener(view -> ));
    }

    @Override
    public int getItemCount() {
        return ApiItems != null ? ApiItems.size() : 0;
    }

    public void updateList(List<ItemModel> items) {
        insertItem(items);
    }

    // Método responsável por inserir um novo usuário na lista e notificar que há novos itens.
    private void insertItem(List<ItemModel> categories) {
        ApiItems.addAll(categories);

        notifyItemInserted(getItemCount());
    }
}