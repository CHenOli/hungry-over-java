package com.tcc.carloshenrique.hungryover.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tcc.carloshenrique.hungryover.R;
import com.tcc.carloshenrique.hungryover.activities.CartActivity;
import com.tcc.carloshenrique.hungryover.components.Session;
import com.tcc.carloshenrique.hungryover.holders.CartItemHolder;
import com.tcc.carloshenrique.hungryover.holders.ItemHolder;
import com.tcc.carloshenrique.hungryover.models.ItemModel;
import com.tcc.carloshenrique.hungryover.models.OrderModel;
import com.tcc.carloshenrique.hungryover.network.OrderService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemHolder> {
    private List<ItemModel> OrderItems;
    private Context Context;
    private int Position;

    private EditText txtAmount;

    public CartItemAdapter(List<ItemModel> items, Context context) {
        Context = context;
        OrderItems = items;
    }

    @Override
    public CartItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context = parent.getContext();
        return new CartItemHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_items, parent, false), 1);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemHolder holder, final int position) {
        Position = position;

        holder.txtItemName.setText(OrderItems.get(position).getNome());
        holder.txtItemPrice.setText("R$ " + String.format("%.2f", OrderItems.get(position)
                .getValor()));
        int amount = OrderItems.get(position).getAmount();
        holder.txtItemAmount.setText(String.valueOf(amount));
        holder.txtItemCategory.setText("Pizzas");
        double valorFinal = OrderItems.get(position).getValor() * amount;
        holder.txtItemTotal.setText("R$: " + String.format("%.2f", valorFinal));

        holder.btnItemAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                LayoutInflater inflater = (LayoutInflater) Context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.dialog_input, null);

                txtAmount = view.findViewById(R.id.inputAmount);
                txtAmount.setText("1");

                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(Context);
                builder.setTitle("Quantidade")
                        .setMessage("Escolha a quantidade do item: ")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                int amount = 0;
                                if (txtAmount.getText() != null)
                                    amount = Integer.parseInt(txtAmount.getText().toString());

                                if (amount < 1) {
                                    Snackbar.make(v, "Quantidade não pode ser menor que 1.",
                                            Snackbar.LENGTH_SHORT).setAction("Action",
                                            null).show();
                                }

                                int newAmount = OrderItems.get(position).getAmount();
                                OrderItems.get(position).setAmount(newAmount + amount);

                                Snackbar.make(v, "Itens adicionados com sucesso.",
                                        Snackbar.LENGTH_SHORT)
                                        .setAction("Action", null).show();

                                notifyDataSetChanged();

                                ((CartActivity) Context).CalculateValues();
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

        holder.btnItemRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                LayoutInflater inflater = (LayoutInflater) Context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.dialog_input, null);

                txtAmount = view.findViewById(R.id.inputAmount);
                txtAmount.setText("1");

                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(Context);
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

                                int newAmount = OrderItems.get(position).getAmount() - amount;
                                if (newAmount < 1) {
                                    OrderItems.remove(position);
                                } else {
                                    OrderItems.get(position).setAmount(newAmount);
                                }

                                Snackbar.make(v, "Itens removidos com sucesso.",
                                        Snackbar.LENGTH_SHORT)
                                        .setAction("Action", null).show();

                                notifyDataSetChanged();

                                ((CartActivity) Context).CalculateValues();
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