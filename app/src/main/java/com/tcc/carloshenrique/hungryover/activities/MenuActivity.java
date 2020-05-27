package com.tcc.carloshenrique.hungryover.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tcc.carloshenrique.hungryover.R;
import com.tcc.carloshenrique.hungryover.adapters.CategoryAdapter;
import com.tcc.carloshenrique.hungryover.adapters.ItemAdapter;
import com.tcc.carloshenrique.hungryover.listeners.RecyclerTouchListener;
import com.tcc.carloshenrique.hungryover.models.CategoryModel;
import com.tcc.carloshenrique.hungryover.models.ItemModel;
import com.tcc.carloshenrique.hungryover.models.OrderModel;
import com.tcc.carloshenrique.hungryover.models.RestaurantModel;
import com.tcc.carloshenrique.hungryover.models.UserModel;
import com.tcc.carloshenrique.hungryover.network.CategoryService;
import com.tcc.carloshenrique.hungryover.network.ItemService;
import com.tcc.carloshenrique.hungryover.network.OrderService;
import com.tcc.carloshenrique.hungryover.network.RestaurantService;
import com.tcc.carloshenrique.hungryover.network.RetrofitInstance;
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

import static com.tcc.carloshenrique.hungryover.utils.Constants.URL;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Context context;

    private CategoryAdapter categoryAdapter;
    private ItemAdapter itemAdapter;

    private OrderModel order;
    private UserModel user;
    private RestaurantModel restaurant;

    private List<CategoryModel> categories;
    private List<ItemModel> items;
    private List<ItemModel> orderItems;

    private boolean notScanned = false;
    private boolean firstLaunch = true;
    private boolean resumed = true;

    private int idOrder = 0;
    private int idTable = 0;
    private int idUser = 0;
    private int idSession = 0;

    private TextView txtUserEmail;
    private TextView txtUserName;

    @BindView(R.id.prgMenu)
    ProgressBar prgMenu;
    @BindView(R.id.rvwCategories)
    RecyclerView rvwCategory;
    @BindView(R.id.rvwItems)
    RecyclerView rvwItems;
    @BindView(R.id.scrollItems)
    ScrollView scrollItems;
    @BindView(R.id.scrollCategories)
    ScrollView scrollCategories;
    @BindView(R.id.mainToolbar)
    Toolbar mainToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.txtTapHere)
    TextView txtTapHere;
    @BindView(R.id.txtToStart)
    TextView txtToStart;
    @BindView(R.id.txtScann)
    TextView txtScann;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setContentView(R.layout.activity_menu);

        context = getApplicationContext();

        ButterKnife.bind(this);

        View viewDrawer = navView.getHeaderView(0);
        txtUserEmail = viewDrawer.findViewById(R.id.txtUserEmail);
        txtUserName = viewDrawer.findViewById(R.id.txtUserName);

        setSupportActionBar(mainToolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showQuickOrderDialog();
            }
        });

        scrollCategories.setVisibility(View.GONE);
        scrollItems.setVisibility(View.GONE);

        resumed = false;

        navView.getMenu().getItem(0).setChecked(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mainToolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        orderItems = new ArrayList<>();
        categories = new ArrayList<>();
        items = new ArrayList<>();

        Intent intent = getIntent();
        try {
            idTable = intent.getIntExtra("idTable", 0);
            idUser = intent.getIntExtra("idUser", 0);
            idSession = intent.getIntExtra("idSession", 0);
        } catch (Exception e) {
            Snackbar.make(mainToolbar, "Falha na obtenção dos dados.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        if (idTable != 0) {
            Configure();
            rvwCategory.addOnItemTouchListener(new RecyclerTouchListener(this, rvwCategory,
                    new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            scrollCategories.setVisibility(View.GONE);
                            scrollItems.setVisibility(View.VISIBLE);

                            int id = categories.get(position).getId();
                            getItemData(id);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));
        } else {
            HideFields();
            notScanned = true;
            txtTapHere.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ScannTable();
                }
            });
        }
    }

    private void ScannTable() {
        Intent intent = new Intent(this, CodeReaderActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void showQuickOrderDialog() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Pedido rápido")
                .setMessage("Confirma realização do Pedido Rápido?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, PaymentActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    private void Configure() {
        //if(itemAdapter != null)
        //orderItems = itemAdapter.getCartItems();

        scrollCategories.setVisibility(View.VISIBLE);
        scrollItems.setVisibility(View.GONE);

        getUserData(idUser);
        getRestaurant(idTable);

        items.removeAll(items);
        categories.removeAll(categories);

        //orderItems.removeAll(orderItems);
    }

    private void getRestaurant(int idMesa) {
        final RestaurantService restaurantService = RetrofitInstance.getRetrofitInstance()
                .create(RestaurantService.class);

        Call<RestaurantModel> call = restaurantService.getId(idMesa);
        call.enqueue((new Callback<RestaurantModel>() {
            @Override
            public void onResponse(Call<RestaurantModel> call, Response<RestaurantModel> response) {
                if (response.body() != null) {
                    restaurant = response.body();
                    getCategoryData(restaurant.getId());
                }
            }

            @Override
            public void onFailure(Call<RestaurantModel> call, Throwable t) {
                call.cancel();
            }
        }));
    }

    public void getUserData(int idUser) {
        final UserService clientService = RetrofitInstance.getRetrofitInstance()
                .create(UserService.class);

        Call<UserModel> call = clientService.getUser(idUser);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    user = response.body();
                    txtUserEmail.setText(response.body().getEmail());
                    txtUserName.setText(response.body().getName());
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                call.cancel();
                user = null;
            }
        });
    }

    public void getCategoryData(int idRestaurant) {
        final CategoryService categoryService = RetrofitInstance.getRetrofitInstance()
                .create(CategoryService.class);

        Call<List<CategoryModel>> call = categoryService.getCategories(idRestaurant);
        call.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call,
                                   Response<List<CategoryModel>> response) {
                int statusCode = response.code();
                if (response.body() != null) {
                    categories.addAll(response.body());
                    setupCategoryRecycler();
                }
            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public void getItemData(int idCategory) {
        final ItemService itemService = RetrofitInstance.getRetrofitInstance()
                .create(ItemService.class);

        Call<List<ItemModel>> call = itemService.getAll(idCategory);
        call.enqueue(new Callback<List<ItemModel>>() {
            @Override
            public void onResponse(Call<List<ItemModel>> call, Response<List<ItemModel>> response) {
                int statusCode = response.code();
                items.addAll(response.body());
                setupItemRecycler(items);
            }

            @Override
            public void onFailure(Call<List<ItemModel>> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void setupCategoryRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvwCategory.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(categories);
        rvwCategory.setAdapter(categoryAdapter);
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
        } else if (scrollItems.getVisibility() == View.VISIBLE) {
            scrollItems.setVisibility(View.GONE);
            scrollCategories.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();

            //orderItems = new ArrayList<>();
            categories = new ArrayList<>();
            items = new ArrayList<>();
        }

        Configure();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_my_cart) {
            if (itemAdapter == null) {
                Snackbar.make(mainToolbar, "O carrinho está vazio.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                orderItems.addAll(itemAdapter.getCartItems());

                if (orderItems.size() > 0) {
                    SendOrder();
                    itemAdapter.ResetCarItems();
                } else {
                    Snackbar.make(mainToolbar, "O carrinho está vazio.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void SendOrder() {
        order = new OrderModel(user.getId(), idSession, orderItems, "");

        final OrderService orderService = RetrofitInstance.getRetrofitInstance()
                .create(OrderService.class);

        Call<OrderModel> call = orderService.sendOrder(order);
        call.enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                order.setId(response.body().getId());
                StartCart();
            }

            @Override
            public void onFailure(Call<OrderModel> call, Throwable t) {

            }
        });
    }

    private void StartCart() {
        Intent intent = new Intent(this, CartActivity.class);
        intent.putExtra("orderId", order.getId());
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_cardapio) {
            item.setChecked(true);
            Configure();
        } else if (id == R.id.nav_pedidos) {

        } else if (id == R.id.nav_favoritos) {

        } else if (id == R.id.nav_config) {

        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

        HideFieldsLoading();

        Intent intent = getIntent();
        try {
            idOrder = intent.getIntExtra("idOrder", 0);
        } catch (Exception e) {

        }

        if (!firstLaunch)
            Configure();

        firstLaunch = false;
    }

    public void HideFields() {
        scrollItems.setVisibility(View.GONE);
        scrollCategories.setVisibility(View.GONE);

        txtToStart.setVisibility(View.VISIBLE);
        txtTapHere.setVisibility(View.VISIBLE);
        txtScann.setVisibility(View.VISIBLE);
    }

    public void HideFieldsLoading() {
        if (!notScanned) {
            scrollItems.setVisibility(View.GONE);
            scrollCategories.setEnabled(false);
            scrollCategories.setVisibility(View.GONE);
            prgMenu.setVisibility(View.VISIBLE);

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    prgMenu.setVisibility(View.GONE);
                    scrollCategories.setEnabled(true);
                    scrollCategories.setVisibility(View.VISIBLE);

                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
            }, 2000);

            notScanned = false;
        }
    }
}