package com.tcc.carloshenrique.hungryover.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.zxing.ResultPoint;

import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import com.tcc.carloshenrique.hungryover.R;
import com.tcc.carloshenrique.hungryover.components.Session;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CodeReaderActivity extends AppCompatActivity {

    @BindView(R.id.dbvBarcode) DecoratedBarcodeView _qrScanner;
    @BindView(R.id.fabProceed) FloatingActionButton _proceed;

    private boolean permission = false;
    final int PERMISSIONS_REQUEST_CAMERA = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_code_reader_test);

        ButterKnife.bind(this);
        permission = checkPermissions();

        _proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuIntent = new Intent(CodeReaderActivity.this, MenuActivity.class);
                startActivity(menuIntent);
                finish();
            }
        });

        if(permission) {
            scannQR();
        }
    }

    public boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CAMERA);
        } else {
            return true;
        }
        return false;
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permission = true;
                    scannQR();
                } else {

                    Snackbar.make(_qrScanner, R.string.permission_camera_refused, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    checkPermissions();
                }
                return;
            }
        }
    }

    private void scannQR() {
        _qrScanner.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                updateText(result.getText());
                beepSound();
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        });
    }

    private void updateText(String text) {
        Session session = new Session();
        session.setIdMesa(Integer.parseInt(text));

        Intent menuIntent = new Intent(CodeReaderActivity.this, MenuActivity.class);
        menuIntent.putExtra("idMesa", session.getIdMesa().toString());
        startActivity(menuIntent);
        finish();
    }

    protected void beepSound() {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        resumeScanner();
    }

    protected void resumeScanner() {
        boolean isScanDone = false;
        if (!_qrScanner.isActivated())
            _qrScanner.resume();
    }

    protected void pauseScanner() {
        _qrScanner.pause();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseScanner();
    }
}