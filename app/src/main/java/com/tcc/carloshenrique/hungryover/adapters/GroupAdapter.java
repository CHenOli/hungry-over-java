package com.tcc.carloshenrique.hungryover.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tcc.carloshenrique.hungryover.R;
import com.tcc.carloshenrique.hungryover.holders.GroupHolder;
import com.tcc.carloshenrique.hungryover.models.CategoryModel;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupHolder> {


    private final List<CategoryModel> ApiCategories;

    public GroupAdapter(List<CategoryModel> categories) {
        ApiCategories = categories;
    }

    @Override
    public GroupHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GroupHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_categories, parent, false), 1);
    }

    @Override
    public void onBindViewHolder(final GroupHolder holder, int position) {

        holder.txtCategory.setText(ApiCategories.get(position).getName().toString());
        //holder.imgCategory.setImageURI(ApiCategories.get(position).getImage());
        //Adicionar no onClick para abrir a activity correspondete ao item clicado
        //holder.imgCategory.setOnClickListener(view -> ));
        //holder.txtCategory.setOnClickListener(view -> ));
    }

    @Override
    public int getItemCount() {
        return ApiCategories != null ? ApiCategories.size() : 0;
    }

    public void updateList(List<CategoryModel> categories) {
        insertItem(categories);
    }

    // Método responsável por inserir um novo usuário na lista e notificar que há novos itens.
    private void insertItem(List<CategoryModel> categories) {
        for(int i = 0; i < categories.size(); i++)
        {
            ApiCategories.add(categories.get(i));
        }

        notifyItemInserted(getItemCount());
    }
}
