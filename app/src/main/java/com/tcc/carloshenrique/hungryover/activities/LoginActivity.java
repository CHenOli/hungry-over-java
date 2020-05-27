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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.tcc.carloshenrique.hungryover.dialogs.MaterialSimpleDialog;
import com.tcc.carloshenrique.hungryover.R;
import com.tcc.carloshenrique.hungryover.models.UserModel;
import com.tcc.carloshenrique.hungryover.network.RetrofitInstance;
import com.tcc.carloshenrique.hungryover.network.UserService;

import butterknife.ButterKnife;
import butterknife.BindView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

import static com.tcc.carloshenrique.hungryover.utils.Constants.URL;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private UserModel user;

    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.btn_login)
    Button _loginButton;
    @BindView(R.id.link_signup)
    TextView _signupLink;
    @BindView(R.id.loginSpinner)
    ProgressBar _pgbLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Essas linhas escondem a Barra de Notificações
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        // WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //Background pelo código, pode ser útil no fim
        //getWindow().setBackgroundDrawableResource(R.drawable.login_bg);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        if (intent.getStringExtra("email") != null &&
                intent.getStringExtra("password") != null) {
            _emailText.setText(intent.getStringExtra("email"));
            _passwordText.setText(intent.getStringExtra("password"));
            DeactivateFields();
            login();
        }

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = _emailText.getText().toString();
                String password = _passwordText.getText().toString();

                DeactivateFields();
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed(false);
            return;
        }

        user = new UserModel();

        user.setEmail(_emailText.getText().toString());
        user.setPassword(_passwordText.getText().toString());

        final UserService clienteService = RetrofitInstance.getRetrofitInstance()
                .create(UserService.class);

        Call<UserModel> call = clienteService.login(user);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                int statusCode = response.code();
                user = response.body();
                if (statusCode == 200) {
                    onLoginSuccess();
                } else {
                    onLoginFailed(false);
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                call.cancel();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoginFailed(true);
            }
        }, 10000);
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
        Intent codeIntent = new Intent(LoginActivity.this, CodeReaderActivity.class);
        codeIntent.putExtra("idUser", user.getId());
        startActivity(codeIntent);
        finish();
    }

    public void onLoginFailed(boolean timeout) {
        ActivateFields();

        if (timeout) {
            Snackbar.make(_loginButton, "Verifique sua conexão e tente novamente.",
                    Snackbar.LENGTH_LONG).setAction("Action", null).show();
        } else {
            Snackbar.make(_loginButton, "Login ou senha incorretos.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
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

    private void DeactivateFields() {
        _loginButton.setText(null);
        _loginButton.setEnabled(false);
        _pgbLogin.setVisibility(View.VISIBLE);

        _emailText.setEnabled(false);
        _passwordText.setEnabled(false);

        _signupLink.setEnabled(false);
    }

    private void ActivateFields() {
        _loginButton.setText(R.string.action_login);
        _loginButton.setEnabled(true);
        _pgbLogin.setVisibility(View.INVISIBLE);

        _emailText.setEnabled(true);
        _passwordText.setEnabled(true);

        _signupLink.setEnabled(true);
    }
}