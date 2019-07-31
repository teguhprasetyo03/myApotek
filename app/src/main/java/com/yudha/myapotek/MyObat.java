package com.yudha.myapotek;

public class MyObat {
    private String nama_obat, satuan, ukuran, varian,stock,harga,kode_rak,id_obat,key;

    public MyObat() {
    }

    public MyObat(String nama_obat, String satuan, String ukuran, String varian, String stock, String harga, String kode_rak, String id_obat) {
        this.nama_obat = nama_obat;
        this.satuan = satuan;
        this.ukuran = ukuran;
        this.varian = varian;
        this.stock = stock;
        this.harga = harga;
        this.kode_rak = kode_rak;
        this.id_obat = id_obat;

    }

    public String getNama_obat() {
        return nama_obat;
    }

    public void setNama_obat(String nama_obat) {
        this.nama_obat = nama_obat;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }

    public String getVarian() {
        return varian;
    }

    public void setVarian(String varian) {
        this.varian = varian;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getKode_rak() {
        return kode_rak;
    }

    public void setKode_rak(String kode_rak) {
        this.kode_rak = kode_rak;
    }

    public String getId_obat() {
        return id_obat;
    }

    public void setId_obat(String id_obat) {
        this.id_obat = id_obat;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
