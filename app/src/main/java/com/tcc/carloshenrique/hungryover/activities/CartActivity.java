package com.tcc.carloshenrique.hungryover.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.tcc.carloshenrique.hungryover.R;
import com.tcc.carloshenrique.hungryover.adapters.CartItemAdapter;
import com.tcc.carloshenrique.hungryover.models.ItemModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity {

    private List<ItemModel> items = new ArrayList<>();
    private CartItemAdapter cartItemAdapter;

    @BindView(R.id.rvwOrderItems) RecyclerView rvwOrderItems;
    @BindView(R.id.mainToolbar) Toolbar mainToolbar;
    @BindView(R.id.fab_payment) FloatingActionButton fab;

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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setupItemRecycler(items);
    }

    private void setupItemRecycler(List<ItemModel> items) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvwOrderItems.setLayoutManager(layoutManager);

        cartItemAdapter = new CartItemAdapter(items);

        rvwOrderItems.setAdapter(cartItemAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_my_cart) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
