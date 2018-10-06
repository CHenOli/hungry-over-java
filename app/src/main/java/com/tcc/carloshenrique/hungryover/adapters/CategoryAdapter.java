package com.tcc.carloshenrique.hungryover.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.tcc.carloshenrique.hungryover.R;
import com.tcc.carloshenrique.hungryover.holders.CategoryHolder;
import com.tcc.carloshenrique.hungryover.models.CategoryModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {
    private final List<CategoryModel> ApiCategories;
    private Context context;

    public CategoryAdapter(List<CategoryModel> categories) {
        ApiCategories = categories;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new CategoryHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_categories, parent, false), 1);
    }

    @Override
    public void onBindViewHolder(final CategoryHolder holder, int position) {
        holder.txtCategory.setText(ApiCategories.get(position).getName().toString());

        RequestOptions requestOption = new RequestOptions();
        requestOption.fitCenter();
                //.placeholder(R.drawable.placeholder).centerCrop()

        Glide.with(context).load(ApiCategories.get(position).getImage())
                           .apply(requestOption)
                           .into(holder.imgCategory);

        //holder.imgCategory.setImageURI(Uri.parse(ApiCategories.get(position).getImage()));
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
        ApiCategories.addAll(categories);

        notifyItemInserted(getItemCount());
    }
}
