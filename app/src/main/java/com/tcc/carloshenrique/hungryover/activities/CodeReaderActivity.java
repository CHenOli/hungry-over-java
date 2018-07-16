package com.tcc.carloshenrique.hungryover.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tcc.carloshenrique.hungryover.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CodeReaderActivity extends AppCompatActivity {

    @BindView(R.id.btn_prosseguir) Button _btnProsseguir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_code_reader);

        ButterKnife.bind(this);

        _btnProsseguir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent menuIntent = new Intent(CodeReaderActivity.this, MenuActivity.class);
                startActivity(menuIntent);
                finish();
            }
        });
    }
}
