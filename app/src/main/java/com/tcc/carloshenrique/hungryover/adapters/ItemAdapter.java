package com.tcc.carloshenrique.hungryover.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tcc.carloshenrique.hungryover.R;
import com.tcc.carloshenrique.hungryover.holders.ItemHolder;
import com.tcc.carloshenrique.hungryover.models.ItemModel;
import com.tcc.carloshenrique.hungryover.network.OrderService;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.PSource;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {
    private List<ItemModel> OrderItems = new ArrayList<>();
    private final List<ItemModel> ApiItems;
    private Context context;
    private int Position;

    private EditText txtAmount;

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
    public void onBindViewHolder(final ItemHolder holder, final int position) {
        RequestOptions requestOption = new RequestOptions();
        requestOption.fitCenter();

        holder.txtItemName.setText(ApiItems.get(position).getNome());
        holder.txtItemPrice.setText("R$ " + String.format("%.2f",
                ApiItems.get(position).getValor()));

        Glide.with(context).load(ApiItems.get(position).getUrlImage())
                .apply(requestOption)
                .into(holder.imgItem);

        holder.btnItemAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.dialog_input, null);

                txtAmount = view.findViewById(R.id.inputAmount);
                txtAmount.setText("1");

                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(context);
                builder.setTitle("Quantidade")
                        .setMessage("Escolha a quantidade do item: ")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                int amount = 0;
                                if (txtAmount.getText() != null)
                                    amount = Integer.parseInt(txtAmount.getText().toString());

                                if (amount < 1) {
                                    Snackbar.make(v, "Quantidade não pode ser menor que 1.",
                                            Snackbar.LENGTH_SHORT)
                                            .setAction("Action", null).show();
                                }

                                OrderItems.add(ApiItems.get(position));
                                OrderItems.get(OrderItems.size() - 1).setAmount(amount);

                                Snackbar.make(v, "Itens adicionados com sucesso.",
                                        Snackbar.LENGTH_SHORT)
                                        .setAction("Action", null).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                        .setView(view)
                        .show();
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

    private void insertItem(List<ItemModel> items) {
        ApiItems.addAll(items);

        notifyItemInserted(getItemCount());
    }

    public List<ItemModel> getCartItems() {
        return OrderItems;
    }

    public void ResetCarItems() {
        OrderItems.removeAll(OrderItems);
    }
}