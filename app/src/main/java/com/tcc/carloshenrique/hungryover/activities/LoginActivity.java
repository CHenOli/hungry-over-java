package com.tcc.carloshenrique.hungryover.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.content.Intent;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tcc.carloshenrique.hungryover.dialogs.MaterialSimpleDialog;
import com.tcc.carloshenrique.hungryover.R;
import com.tcc.carloshenrique.hungryover.models.UserModel;
import com.tcc.carloshenrique.hungryover.network.UserService;

import butterknife.ButterKnife;
import butterknife.BindView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.btn_login)
    Button _loginButton;
    @BindView(R.id.link_signup)
    TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Essas linhas escondem a Barra de Notificações
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //Background pelo código, pode ser útil no fim
        //getWindow().setBackgroundDrawableResource(R.drawable.login_bg);
        ButterKnife.bind(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.url))
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        final UserService clienteService = retrofit.create(UserService.class);

        _emailText.setText("eduardo@teste.com");
        _passwordText.setText("123456");

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = _emailText.getText().toString();
                String password = _passwordText.getText().toString();
                login(email, password, clienteService);
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login(final String email, String password, UserService clienteService) {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final MaterialSimpleDialog dialog = new MaterialSimpleDialog(LoginActivity.this);
        dialog.setTitle("Autenticando")
                //Use this if you want to set a text message
                .setMessage("Só mais um momentinho...")

                //Use this for a custom layout resource
                //.setCustomViewResource(R.layout.dialog_layout_base)

                //Or pass the View
                //.setCustomView(yourView);

                //Set cancelable on touch outside (default true)
                .dismissOnTouchOutside(false);
        dialog.show();

        UserModel user = new UserModel();

        user.setEmail(_emailText.getText().toString());
        user.setPassword(_passwordText.getText().toString());

        Call<UserModel> call = clienteService.login(user);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                int statusCode = response.code();
                if(response.body().getEmail() != null)
                {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onLoginSuccess();
                            dialog.dismiss();
                        }
                    }, 1000);
                    _loginButton.setEnabled(true);
                }
                else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onLoginFailed();
                            dialog.dismiss();
                            return;
                        }
                    }, 3000);
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                call.cancel();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        Intent menuIntent = new Intent(LoginActivity.this, CodeReaderActivity.class);
        menuIntent.putExtra("email", _emailText.getText().toString());
        startActivity(menuIntent);
        finish();
    }

    public void onLoginFailed() {
        Snackbar.make(_loginButton, "Falha no login.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Esse e-mail é inválido");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            _passwordText.setError("Senha muito curta");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}