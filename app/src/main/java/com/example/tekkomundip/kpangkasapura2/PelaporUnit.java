package com.example.tekkomundip.kpangkasapura2;

public class PelaporUnit {

    String pelaporId;
    String pesan;
    String namapelapor;
    String barang;
    String area;
    String status;
    String jam;

    public PelaporUnit(String pelaporId,String pesan, String namapelapor, String barang, String area, String status
    ) {
        this.pelaporId = pelaporId;
        this.pesan = pesan;
        this.namapelapor = namapelapor;
        this.barang = barang;
        this.area = area;
        this.status = status;

    }

    public String getPelaporId() {
        return pelaporId;
    }

    public String getBarang() {
        return barang;
    }

    public String getPesan() {
        return pesan;
    }

    public String getNamapelapor() {
        return namapelapor;
    }

    public String getArea() {
        return area;
    }

    public String getStatus() {
        return status;
    }



}