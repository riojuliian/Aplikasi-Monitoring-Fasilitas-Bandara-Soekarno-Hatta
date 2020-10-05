package com.example.tekkomundip.kpangkasapura2;
import android.app.AlertDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.Toast;

import java.security.PublicKey;

public class Upload {
    private String mName, mImageUrl, mKeterangan, mBarang, mTanggal, mJam;



    public Upload() {
        //empty constructor needed
    }

    public Upload(String Teknisi, String imageUrl, String Keterangan, String Barang, String Tanggal, String Jam) {
        if (Teknisi.equals("")&& Keterangan.equals("") && Barang.equals("") && Tanggal.equals("") && Jam.equals(""))  {
            Teknisi = "No Name";
            Keterangan = "No Name";
            Barang = "No Name";
            Tanggal = "No Name";
            Jam = "No Name";
        }

        mName = Teknisi;
        mImageUrl = imageUrl;
        mKeterangan = Keterangan;
        mBarang = Barang;
        mTanggal = Tanggal;
        mJam = Jam;
    }

    public String getNamaTeknisi() {
        return mName;
    }
    public void setNamaTeknisi(String Teknisi) {
        mName = Teknisi;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public  String getKeterangan(){
        return mKeterangan;
    }
    public void setKeterangan(String keterangan){
        mKeterangan = keterangan;
    }

    public String getBarang(){
        return mBarang;
    }

    public void setBarang(String barang) {
        mBarang = barang;
    }

    public String getTanggal(){
        return mTanggal;
    }

    public void setTanggal(String tanggal) {
        mTanggal = tanggal;
    }

    public String getJam(){
        return mJam;
    }

    public void setJam (String Jam) {
        mJam = Jam;
    }
}