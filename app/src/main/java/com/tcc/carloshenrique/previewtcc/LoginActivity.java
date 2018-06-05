package com.tcc.carloshenrique.previewtcc;

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
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.BindView;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Essas linhas esconde a Barra de Notificações
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //Background pelo código, pode ser útil no fim
        //getWindow().setBackgroundDrawableResource(R.drawable.login_bg);
        ButterKnife.bind(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
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

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            //onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final MaterialLoginDialog dialog = new MaterialLoginDialog(LoginActivity.this);
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

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if(email.equalsIgnoreCase("oliveira.caco.h@gmail.com") ||
                email.equalsIgnoreCase("eduardojpilla@gmail.com") ||
                email.equalsIgnoreCase("italoox96@gmail.com")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onLoginSuccess();
                    dialog.dismiss();
                }
            }, 3000);

        }else {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    onLoginFailed();
                    dialog.dismiss();
                }
            }, 3000);
        }
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

        if (password.isEmpty() || password.length() < 8) {
            _passwordText.setError("Senha muito curta");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}