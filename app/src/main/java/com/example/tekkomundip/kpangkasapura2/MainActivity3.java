package com.example.tekkomundip.kpangkasapura2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity3 extends AppCompatActivity {
    private Button btnLihat;
    private Button btnLaporanKerusakan;
    private Button btnlaporPerbaikan;
    private Button btnHarian;
    private Button btnAbout;
    private Button btnKeluar;
    String terminaltext;
    TextView UsernameLogin3;
    String usernameLogin3 = Login3Activity.getNama();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        initView();
        btnlapor();
        btnabout();
        keluar();
        btndaftarlaporan();
    }

    private void initView()
    {
        btnLaporanKerusakan = findViewById(R.id.btndaftarlaporan);
        btnAbout = findViewById( R.id.btnabout);
        btnKeluar= findViewById(R.id.btnlogout);
        btnlaporPerbaikan = findViewById(R.id.btnlaporperbaikan);
        btnKeluar = findViewById(R.id.btnlogout);
        UsernameLogin3 = findViewById(R.id.tv_getNamaLogin3);
        UsernameLogin3.setText("Selamat Datang, "+ usernameLogin3);
    }
    private void btnlapor()
    {
        btnlaporPerbaikan.setOnClickListener(V -> {
            Intent reg = new Intent(this, LaporPerbaikanActivity.class);
            startActivity(reg);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();}
        );
    }
    private void btnabout()
    {
        btnAbout.setOnClickListener(V -> {
            Intent reg = new Intent(this, AboutActivity3.class);
            startActivity(reg);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();}
        );
    }
    private void btndaftarlaporan()
    {
        btnLaporanKerusakan.setOnClickListener(V -> {
            Intent reg = new Intent(this, LaporanUnitActivity.class);
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
                        //Intent login = new Intent(MainActivity3.this, Login1Activity.class);
                        //startActivity(login);
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    private void exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Yakin mau keluar?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })

                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();

    }
    private void keluar()
    {
        btnKeluar.setOnClickListener(V -> {
            exit();}
        );
    }
}
