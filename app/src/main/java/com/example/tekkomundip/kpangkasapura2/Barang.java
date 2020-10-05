package com.example.tekkomundip.kpangkasapura2;

public class Barang {String Lokasi, LokasiSpesifik, Nama, Status;

    public Barang(String Lokasi, String LokasiSpesifik, String Nama, String Status) {
        this.Lokasi = Lokasi;
        this.LokasiSpesifik = LokasiSpesifik;
        this.Nama = Nama;
        this.Status = Status;
    }

    public String getLokasi() {
        return Lokasi;
    }

    public String getLokasiSpesifik() {
        return LokasiSpesifik;
    }

    public String getNama() {
        return Nama;
    }

    public String getStatus() {
        return Status;
    }

    public Barang(){

    }

}