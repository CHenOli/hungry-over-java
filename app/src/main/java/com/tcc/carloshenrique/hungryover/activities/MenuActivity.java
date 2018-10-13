package com.tcc.carloshenrique.hungryover.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;

import com.tcc.carloshenrique.hungryover.R;
import com.tcc.carloshenrique.hungryover.adapters.CategoryAdapter;
import com.tcc.carloshenrique.hungryover.adapters.ItemAdapter;
import com.tcc.carloshenrique.hungryover.listeners.RecyclerTouchListener;
import com.tcc.carloshenrique.hungryover.models.CategoryModel;
import com.tcc.carloshenrique.hungryover.models.ItemModel;
import com.tcc.carloshenrique.hungryover.models.RestaurantModel;
import com.tcc.carloshenrique.hungryover.models.UserModel;
import com.tcc.carloshenrique.hungryover.network.CategoryService;
import com.tcc.carloshenrique.hungryover.network.ItemService;
import com.tcc.carloshenrique.hungryover.network.RestaurantService;
import com.tcc.carloshenrique.hungryover.network.UserService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //@BindView(R.id.img_profile) ImageView _imgProfile;
    //@BindView(R.id.txt_name) TextView _txtName;
    //@BindView(R.id.txt_email) TextView _txtEmail;

    private ItemAdapter itemAdapter;

    private UserModel User;
    private List<CategoryModel> Categories;
    private List<ItemModel> Items;
    private List<ItemModel> OrderItems;

    private int idRestaurant = 0;
    private int idTable = 0;

    @BindView(R.id.rvwCategories) RecyclerView rvwCategory;
    @BindView(R.id.rvwItems) RecyclerView rvwItems;
    @BindView(R.id.scrollItems) ScrollView scrollItems;
    @BindView(R.id.scrollCategories) ScrollView scrollCategories;
    @BindView(R.id.mainToolbar) Toolbar mainToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Essa linha esconde a Barra de Notificações
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_menu);

        ButterKnife.bind(this);

        mainToolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(mainToolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Aqui será implementada a função de Pedidos Rápidos", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mainToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        OrderItems = new ArrayList<>();
        Categories = new ArrayList<>();
        Items = new ArrayList<>();

        Intent intent = getIntent();
        try{
            idTable = Integer.parseInt(intent.getStringExtra("idMesa"));
        }
        catch (Exception e)
        {
            Snackbar.make(mainToolbar, "Falha no login.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        getRestaurant(idTable);
        getCategoryData(idRestaurant);

        rvwCategory.addOnItemTouchListener(new RecyclerTouchListener(this, rvwCategory, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                scrollCategories.setVisibility(View.GONE);
                scrollItems.setVisibility(View.VISIBLE);

                int id = Categories.get(position).getId();
                getItemData(id);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        rvwItems.addOnItemTouchListener(new RecyclerTouchListener(this, rvwCategory, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void getRestaurant(int idMesa) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.url))
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        final RestaurantService restaurantService = retrofit.create(RestaurantService.class);

        Call<RestaurantModel> call = restaurantService.getId(idMesa);
        call.enqueue((new Callback<RestaurantModel>() {
            @Override
            public void onResponse(Call<RestaurantModel> call, Response<RestaurantModel> response) {
                if(response.body() != null)
                    idRestaurant = response.body().getId();
            }

            @Override
            public void onFailure(Call<RestaurantModel> call, Throwable t) {

            }
        }));
    }

    public UserModel getUserData(int idUser) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.url))
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        final UserService clientService = retrofit.create(UserService.class);

        Call<UserModel> call = clientService.getId(idUser);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                int statusCode = response.code();
                User = response.body();
            }
            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                call.cancel();
                User = null;
            }
        });

        return User;
    }

    public void getCategoryData(int idRestaurant) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.url))
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        final CategoryService categoryService = retrofit.create(CategoryService.class);

        Call<List<CategoryModel>> call = categoryService.getCategories(idRestaurant);
        call.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                int statusCode = response.code();
                if(response.body() != null)
                {
                    Categories.addAll(response.body());
                    setupCategoryRecycler(Categories);
                }
            }
            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public void getItemData(int idCategory) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.url))
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        final ItemService itemService = retrofit.create(ItemService.class);

        Call<List<ItemModel>> call = itemService.all(idCategory);
        call.enqueue(new Callback<List<ItemModel>>() {
            @Override
            public void onResponse(Call<List<ItemModel>> call, Response<List<ItemModel>> response) {
                int statusCode = response.code();
                Items.addAll(response.body());
                setupItemRecycler(Items);
            }
            @Override
            public void onFailure(Call<List<ItemModel>> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void setupCategoryRecycler(List<CategoryModel> categories) {
        // Configurando o gerenciador de layout para ser uma lista.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvwCategory.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        // Está sendo criado com lista vazia, pois será preenchida posteriormente.

        //List<CategoryModel> listCategories = getCategoryData(1);

        rvwCategory.setAdapter(new CategoryAdapter(categories));

        //rvwAdapter = new CategoryAdapter(listCategories);

        //if(categories != null)
          //  rvwAdapter.updateList(listCategories);
    }

    private void setupItemRecycler(List<ItemModel> items) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvwItems.setLayoutManager(layoutManager);

        itemAdapter = new ItemAdapter((items));

        rvwItems.setAdapter(itemAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            if(itemAdapter == null) {
                Snackbar.make(mainToolbar, R.string.account_no, Snackbar.LENGTH_LONG);
            }
            else {
                OrderItems.addAll(itemAdapter.getCartItems());

                if(OrderItems.size() > 1) {
                    Intent intent = new Intent(this, CartActivity.class);
                    startActivity(intent);
                } else {
                    Snackbar.make(mainToolbar, R.string.account_no, Snackbar.LENGTH_LONG);
                }
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_pagamento) {
            // Handle the camera action
        } else if (id == R.id.nav_cardapio) {

        } else if (id == R.id.nav_pedidos) {

        } else if (id == R.id.nav_favoritos) {

        } else if (id == R.id.nav_conta) {

        } else if (id == R.id.nav_config) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
