package com.tcc.carloshenrique.hungryover.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.tcc.carloshenrique.hungryover.R;
import com.tcc.carloshenrique.hungryover.adapters.GroupAdapter;
import com.tcc.carloshenrique.hungryover.models.CategoryModel;
import com.tcc.carloshenrique.hungryover.models.UserModel;
import com.tcc.carloshenrique.hungryover.network.CategoryService;
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

    private UserModel user;
    private List<CategoryModel> categories;

    //private GroupAdapter rvwAdapter;
    @BindView(R.id.rvwCategories) RecyclerView rvwCategory;

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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();

        Snackbar.make(fab, intent.getStringExtra("idMesa"), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        categories = new ArrayList<>();


        getCategoryData(1);
    }

    public UserModel getUserData(int idUser) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hungry-over-api.herokuapp.com/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        final UserService clientService = retrofit.create(UserService.class);

        Call<UserModel> call = clientService.getId(idUser);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                int statusCode = response.code();
                user = response.body();
            }
            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                call.cancel();
                user = null;
            }
        });

        return user;
    }

    public void getCategoryData(int idCategory) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hungryover-api.herokuapp.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        final CategoryService categoryService = retrofit.create(CategoryService.class);

        Call<List<CategoryModel>> call = categoryService.all();
        call.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                int statusCode = response.code();
                categories.addAll(response.body());
                setupGroupRecycler(categories) ;
            }
            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
                call.cancel();
            }
        });


    }

    private void setupGroupRecycler(List<CategoryModel> categories) {

        // Configurando o gerenciador de layout para ser uma lista.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvwCategory.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        // Está sendo criado com lista vazia, pois será preenchida posteriormente.

        //List<CategoryModel> listCategories = getCategoryData(1);

        rvwCategory.setAdapter(new GroupAdapter(categories));

        //rvwAdapter = new GroupAdapter(listCategories);

        //if(categories != null)
          //  rvwAdapter.updateList(listCategories);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.config) {
            //return true;
        //}

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
