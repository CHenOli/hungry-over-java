package com.tcc.carloshenrique.previewtcc;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MaterialLoginDialog extends AlertDialog {

    private Context mContext;
    private TextView mTitle;
    private TextView mContent;
    private FrameLayout mCustomContainer;
    private ScrollView mScrollText;

    private String title;
    private String contentText;
    private View customView;
    private Integer customResId;
    private boolean canDismiss = true;

    public MaterialLoginDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_layout_base);
        mTitle = (TextView) findViewById(android.R.id.text1);
        mContent = (TextView) findViewById(android.R.id.text2);

        mTitle.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Medium.ttf"));
        mContent.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Regular.ttf"));
    }

    @Override
    public void onStart() {
        super.onStart();
        if(title != null) {
            mTitle.setText(title);
            mContent.setText(contentText);
        }else{
            mTitle.setVisibility(View.GONE);
        }

        this.setCanceledOnTouchOutside(canDismiss);
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

    }

    public MaterialLoginDialog setTitle(String t) {
        this.title = t;
        return this;
    }

    public MaterialLoginDialog setMessage(String m) {
        this.contentText = m;
        return this;
    }

    public MaterialLoginDialog setCustomView(View v) {
        this.customView = v;
        return this;
    }

    public MaterialLoginDialog setCustomViewResource(int ResId) {
        this.customResId = ResId;
        return this;
    }

    public MaterialLoginDialog dismissOnTouchOutside(boolean dismiss) {
        this.canDismiss = dismiss;
        return this;
    }

    public View getCustomView() {
        return this.customView;
    }

}