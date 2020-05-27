package com.tcc.carloshenrique.hungryover.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tcc.carloshenrique.hungryover.R;

public class MaterialSimpleDialog extends AlertDialog {

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

    public MaterialSimpleDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_layout_base);
        mTitle = (TextView) findViewById(android.R.id.text1);
        mContent = (TextView) findViewById(android.R.id.text2);

        mTitle.setTypeface(Typeface.createFromAsset(mContext.getAssets(),
                "fonts/roboto_medium.ttf"));
        mContent.setTypeface(Typeface.createFromAsset(mContext.getAssets(),
                "fonts/roboto_regular.ttf"));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (title != null) {
            mTitle.setText(title);
            mContent.setText(contentText);
        } else {
            mTitle.setVisibility(View.GONE);
        }

        this.setCanceledOnTouchOutside(canDismiss);
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

    }

    public MaterialSimpleDialog setTitle(String t) {
        this.title = t;
        return this;
    }

    public MaterialSimpleDialog setMessage(String m) {
        this.contentText = m;
        return this;
    }

    public MaterialSimpleDialog setCustomView(View v) {
        this.customView = v;
        return this;
    }

    public MaterialSimpleDialog setCustomViewResource(int ResId) {
        this.customResId = ResId;
        return this;
    }

    public MaterialSimpleDialog dismissOnTouchOutside(boolean dismiss) {
        this.canDismiss = dismiss;
        return this;
    }

    public View getCustomView() {
        return this.customView;
    }

}