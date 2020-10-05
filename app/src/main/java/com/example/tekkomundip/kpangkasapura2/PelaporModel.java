package com.example.tekkomundip.kpangkasapura2;

public class PelaporModel {
    String namapelapor;
    String barang;
    String area;
    String Jam;
    String HariBulanTahun;
    String Bulan;
    String Tahun;
    String status;

    public PelaporModel(String namapelapor, String barang, String area, String Jam, String HariBulanTahun, String Bulan, String Tahun, String status) {
        this.namapelapor = namapelapor;
        this.barang = barang;
        this.area = area;
        this.Jam = Jam;
        this.status = status;
        this.HariBulanTahun = HariBulanTahun;
        this.Tahun = Tahun;
        this.Bulan = Bulan;
    }

    public String getNamapelapor() {
        return namapelapor;
    }

    public String getBarang() {
        return barang;
    }

    public String getArea() {
        return area;
    }

    public String getJam() {
        return Jam;
    }

    public String getHariBulanTahun() {
        return HariBulanTahun;
    }

    public String getBulan() {
        return Bulan;
    }

    public String getTahun() {
        return Tahun;
    }

    public String getStatus() {
        return status;
    }

    public PelaporModel(){

    }
}
