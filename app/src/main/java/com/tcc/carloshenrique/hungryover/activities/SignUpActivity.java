package com.tcc.carloshenrique.hungryover.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tcc.carloshenrique.hungryover.R;
import com.tcc.carloshenrique.hungryover.dialogs.MaterialSimpleDialog;
import com.tcc.carloshenrique.hungryover.models.UserModel;
import com.tcc.carloshenrique.hungryover.network.UserService;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @BindView(R.id.input_name)
    EditText _nameText;
    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.confirm_password)
    EditText _confirmPassword;
    @BindView(R.id.btnSignUp)
    Button _signupButton;
    @BindView(R.id.link_login)
    TextView _loginLink;
    @BindView(R.id.pgbSignUp)
    ProgressBar _pgbSignUp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeactivateFields();
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void DeactivateFields() {
        _signupButton.setText(null);
        _signupButton.setEnabled(false);
        _pgbSignUp.setVisibility(View.VISIBLE);

        _nameText.setEnabled(false);
        _emailText.setEnabled(false);
        _passwordText.setEnabled(false);
        _confirmPassword.setEnabled(false);

        _loginLink.setEnabled(false);
    }

    private void ActivateFields() {
        _signupButton.setText(R.string.register);
        _signupButton.setEnabled(true);
        _pgbSignUp.setVisibility(View.INVISIBLE);

        _nameText.setEnabled(true);
        _emailText.setEnabled(true);
        _passwordText.setEnabled(true);
        _confirmPassword.setEnabled(true);

        _loginLink.setEnabled(true);
    }

    public void signup() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.url))
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        final UserService userService = retrofit.create(UserService.class);

        if (!validate()) {
            onSignupFailed(false);
            return;
        }

        String name = _nameText.getText().toString();
        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();
        String image = "";

        UserModel user = new UserModel(name, email, password, image);

        Call<UserModel> userCall = userService.create(user);
        userCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                onSignupSuccess(email, password);
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                onSignupFailed(false);
                t.printStackTrace();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onSignupFailed(true);
            }
        }, 10000);

    }

    public void onSignupSuccess(String email, String password) {
        Intent loginIntent = new Intent(SignUpActivity.this, LoginActivity.class);
        loginIntent.putExtra("email", email);
        loginIntent.putExtra("password", password);
        startActivity(loginIntent);
        finish();
    }

    public void onSignupFailed(boolean timeout) {
        ActivateFields();

        if(timeout) {
            Snackbar.make(_signupButton, "Verifique sua conexão e tente novamente.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            Snackbar.make(_signupButton, "Por favor, verifique os dados e tente novamente.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty()) {
            _nameText.setError("Preencha o seu nome!");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Preencha com um email válido!");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            _passwordText.setError("Senha muito curta!");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (_passwordText.toString().equalsIgnoreCase(_confirmPassword.toString())) {
            _passwordText.setError("As senhas precisam ser iguais!");
            _confirmPassword.setError("As senhas precisam ser iguais!");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}