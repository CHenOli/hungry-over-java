package com.tcc.carloshenrique.hungryover.adapters;

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

    public ItemAdapter(ArrayList items) {
        ApiItems = items;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_items, parent, false), 1);
    }

    @Override
    public void onBindViewHolder(final ItemHolder holder, int position) {

        //Adicionar no onClick para abrir a activity correspondete ao item clicado
        //holder.imgCategory.setOnClickListener(view -> ));
        //holder.txtCategory.setOnClickListener(view -> ));
    }

    @Override
    public int getItemCount() {
        return ApiItems != null ? ApiItems.size() : 0;
    }

    public void updateList(ItemModel items) {
        insertItem(items);
    }

    // Método responsável por inserir um novo usuário na lista e notificar que há novos itens.
    private void insertItem(ItemModel items) {
        ApiItems.add(items);
        notifyItemInserted(getItemCount());
    }

    // Método responsável por atualizar um usuário já existente na lista.
    private void updateItem(int position) {
        ItemModel itemModel = ApiItems.get(position);
        notifyItemChanged(position);
    }

    // Método responsável por remover um usuário da lista.
    private void removerItem(int position) {
        ApiItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, ApiItems.size());
    }
}
