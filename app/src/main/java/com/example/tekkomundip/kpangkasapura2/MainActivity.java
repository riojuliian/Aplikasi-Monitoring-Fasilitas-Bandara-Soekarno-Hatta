package com.example.tekkomundip.kpangkasapura2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private Button btnLihat;
    private Button btnLapor;
    private Button btnPerbaikan;
    private Button btnHarian;
    private Button btnAbout;
    private Button btnKeluar;
    String terminaltext;
    TextView tvGetTerminal, tvNamaLogin;
    String UsernameLogin1 = Login3Activity.getNama();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        btnlapor();
        btnabout();
        btnperbaikan();
        btnlihat();
    }

    private void initView()
    {
        btnLapor = findViewById(R.id.btnlapor);
        btnAbout = findViewById( R.id.btnaboutmain);
        btnLihat= findViewById(R.id.btnlihatbarang);
        btnPerbaikan = findViewById(R.id.btnperbaikan);
        tvNamaLogin = findViewById(R.id.tv_getNamaLogin1);
        tvNamaLogin.setText("Selamat Datang, " + UsernameLogin1);
    }
    private void btnlapor()
    {
        btnLapor.setOnClickListener(V -> {
            Intent reg = new Intent(this, LaporActivity.class);
            startActivity(reg);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();}
        );
    }
    private void btnabout()
    {
        btnAbout.setOnClickListener(V -> {
            Intent reg = new Intent(this, AboutActivity.class);
            startActivity(reg);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();}
        );
    }
    private void btnperbaikan()
    {
        btnPerbaikan.setOnClickListener(V -> {
            Intent reg = new Intent(this, PerbaikanActivity.class);
            startActivity(reg);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();}
        );
    }
    private void btnlihat()
    {
        btnLihat.setOnClickListener(V -> {
            Intent reg = new Intent(this, LihatBarangActivity.class);
            startActivity(reg);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();}
        );
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Apa kalian ingin Logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Intent login = new Intent(MainActivity.this, Login1Activity.class);
                        ///startActivity(login);
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
