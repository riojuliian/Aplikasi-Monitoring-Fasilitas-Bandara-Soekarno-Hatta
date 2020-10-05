package com.example.tekkomundip.kpangkasapura2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity2 extends AppCompatActivity {

    private Button btnLihat;
    private Button btnTahunan;
    private Button btnBulanan;
    private Button btnHarian;
    private Button btnAbout;
    private Button btnKeluar;
    TextView tvusername;
    String UsernameLogin1 = Login3Activity.getNama();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        lihatbarang();
        tahunan();
        bulanan();
        harian();
        about();
        keluar();
    }
    private void initView() {

        btnLihat = findViewById(R.id.btnlihatbarang);
        btnTahunan = findViewById(R.id.btntahunan);
        btnBulanan = findViewById(R.id.btnbulanan);
        btnHarian = findViewById(R.id.btnharian);
        btnAbout = findViewById(R.id.btnaboutmain2);
        btnKeluar = findViewById(R.id.btnlogout);
        tvusername = findViewById(R.id.tv_getNamaLogin2);
        tvusername.setText("Selamat Datang, "+UsernameLogin1);
    }

    private void lihatbarang(){
        btnLihat.setOnClickListener(V -> {
            Intent reg = new Intent(this, LihatBarangSummary.class);
            startActivity(reg);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();}
        );
    }
    private void tahunan(){
        btnTahunan.setOnClickListener(V -> {
            Intent reg = new Intent(this, TahunanActivity.class);
            startActivity(reg);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();}
        );
    }

    private void bulanan(){
        btnBulanan.setOnClickListener(V -> {
            Intent reg = new Intent(this, BulananActivity.class);
            startActivity(reg);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();}
        );
    }

    private void harian(){
        btnHarian.setOnClickListener(V -> {
            Intent reg = new Intent(this, HarianActivity.class);
            startActivity(reg);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();}
        );
    }
    private void about(){
        btnAbout.setOnClickListener(V -> {
            Intent reg = new Intent(this, AboutActivity2.class);
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
                        //Intent login = new Intent(MainActivity2.this, Login1Activity.class);
                        //startActivity(login);
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    private void exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Yakin mau keluar? ")
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
    private void keluar(){
        btnKeluar.setOnClickListener(V -> {
            exit();
        }
        );
    }
}
