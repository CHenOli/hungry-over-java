package com.tcc.carloshenrique.hungryover.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.widget.TextView;

import com.tcc.carloshenrique.hungryover.R;

public class MaterialInputDialog extends AlertDialog {
    private Context Context;
    private TextView Title;
    private AppCompatButton btnAdd;
    private AppCompatButton btnCancel;
    private TextInputLayout txtAmount;

    protected MaterialInputDialog(Context context) {
        super(context);
        this.Context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_layout_input);
        Title = findViewById(R.id.txtTextInputTitle);
        btnAdd = findViewById(R.id.btn_itemAdd);
        btnCancel = findViewById(R.id.btn_cancel);
        txtAmount = findViewById(R.id.txt_amount);

        Title.setTypeface(Typeface.createFromAsset(Context.getAssets(), "fonts/roboto_medium.ttf"));
    }
}
