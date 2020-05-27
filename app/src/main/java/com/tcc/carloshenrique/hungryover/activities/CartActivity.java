package com.tcc.carloshenrique.hungryover.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tcc.carloshenrique.hungryover.R;
import com.tcc.carloshenrique.hungryover.adapters.CartItemAdapter;
import com.tcc.carloshenrique.hungryover.components.Session;
import com.tcc.carloshenrique.hungryover.models.ItemModel;
import com.tcc.carloshenrique.hungryover.models.OrderModel;
import com.tcc.carloshenrique.hungryover.network.OrderService;
import com.tcc.carloshenrique.hungryover.network.RetrofitInstance;
import com.tcc.carloshenrique.hungryover.network.SessionService;
import com.tcc.carloshenrique.hungryover.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

import static com.tcc.carloshenrique.hungryover.utils.Constants.URL;

public class CartActivity extends AppCompatActivity {
    private List<ItemModel> items = new ArrayList<>();
    private CartItemAdapter cartItemAdapter;

    private String voucher = "";
    private Context context;

    private OrderModel order = new OrderModel();

    @BindView(R.id.rvwOrderItems)
    RecyclerView rvwOrderItems;
    @BindView(R.id.mainToolbar)
    Toolbar mainToolbar;
    @BindView(R.id.txtTotal)
    TextView txtTotal;
    @BindView(R.id.btnPay)
    Button btnPay;
    @BindView(R.id.btnDivide)
    Button btnDivide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setContentView(R.layout.activity_cart);

        ButterKnife.bind(this);

        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("Meu Pedido");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        context = getApplicationContext();

        Intent intent = getIntent();
        order.setId(intent.getIntExtra("orderId", 0));

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!items.isEmpty()) {
                    SendPayment(false);
                } else {
                    Snackbar.make(mainToolbar, "O carrinho está vazio.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                /*android.app.AlertDialog.Builder builder;
                builder = new android.app.AlertDialog.Builder(context);
                builder.setTitle("Dividir Pagamento")
                        .setMessage("Deseja dividir o pagamento?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SendPayment(true);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SendPayment(false);
                            }
                        }).show();*/
            }
        });

        btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!items.isEmpty()) {
                    SendPayment(true);
                } else {
                    Snackbar.make(mainToolbar, "O carrinho está vazio.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        Configure();
    }

    private void SendPayment(boolean divide) {
        int divideNumber = 0;

        final SessionService sessionService = RetrofitInstance.getRetrofitInstance()
                .create(SessionService.class);

        if (divide)
            divideNumber = 1;

        Call<Session> call = sessionService.Pay(order.getSessionId(), divideNumber);
        call.enqueue(new Callback<Session>() {
            @Override
            public void onResponse(Call<Session> call, Response<Session> response) {
                Intent intent = new Intent(context, PaymentActivity.class);
                if (voucher.isEmpty()) {
                    voucher = UUID.randomUUID().toString();
                }

                intent.putExtra("voucher", voucher);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Session> call, Throwable t) {

            }
        });
    }

    private void Configure() {
        items = new ArrayList<>();
        getOrderData();
    }

    private void getOrderData() {
        final OrderService orderService = RetrofitInstance.getRetrofitInstance()
                .create(OrderService.class);

        Call<OrderModel> call = orderService.getOrder(order.getId());
        call.enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                order = response.body();
                items = order.getOrderItems();
                setupItemRecycler();
            }

            @Override
            public void onFailure(Call<OrderModel> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void setupItemRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvwOrderItems.setLayoutManager(layoutManager);
        cartItemAdapter = new CartItemAdapter(items, this);
        rvwOrderItems.setAdapter(cartItemAdapter);

        CalculateValues();
    }

    public void CalculateValues() {
        double valor = 0;

        for (ItemModel item : items) {
            valor += item.getValor() * item.getAmount();
        }

        txtTotal.setText("R$: " + String.format("%.2f", valor));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("idOrder", order.getId());
        startActivity(intent);
        finish();
    }
}
