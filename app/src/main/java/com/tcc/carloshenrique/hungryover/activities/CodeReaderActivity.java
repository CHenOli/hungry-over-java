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
import com.tcc.carloshenrique.hungryover.models.UserModel;
import com.tcc.carloshenrique.hungryover.network.SessionService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class CodeReaderActivity extends AppCompatActivity {
    @BindView(R.id.dbvBarcode)
    DecoratedBarcodeView _qrScanner;
    @BindView(R.id.fabProceed)
    FloatingActionButton _btnProceed;

    private int idTable;
    private UserModel user = new UserModel();
    private Session session = new Session();
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

        Intent intent = getIntent();
        user.setId(intent.getIntExtra("idUser", 0));

        _btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateText("1");
            }
        });

        if (permission) {
            scanQR();
        }
    }

    public void Configure()
    {
        InitializeSession();
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
                    scanQR();
                } else {
                    Snackbar.make(_qrScanner, R.string.permission_camera_refused, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    checkPermissions();
                }
            }
        }
    }

    private void scanQR() {
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

    private void updateText(String text){
        beepSound();
        idTable = Integer.parseInt(text);
        pauseScanner();
        Configure();
    }

    public void InitializeSession()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.url))
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        SessionService sessionService = retrofit.create(SessionService.class);

        int idUser = user.getId();

        Call<Session> call = sessionService.Create(idUser, idTable);
        call.enqueue(new Callback<Session>() {
            @Override
            public void onResponse(Call<Session> call, Response<Session> response) {
                if(session != null) {
                    session = response.body();
                    InitializeMenu();
                } else {
                    InitializeSessionFailed();
                }
            }

            @Override
            public void onFailure(Call<Session> call, Throwable t) {
                InitializeSessionFailed();
            }
        });
    }

    private void InitializeMenu() {
        Intent menuIntent = new Intent(CodeReaderActivity.this, MenuActivity.class);
        menuIntent.putExtra("idSession", session.getId());
        menuIntent.putExtra("idTable", idTable);
        menuIntent.putExtra("idUser", user.getId());
        startActivity(menuIntent);
        finish();
    }

    public void InitializeSessionFailed() {
        Snackbar.make(_btnProceed, "Falha ao criar a sessão", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        resumeScanner();
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