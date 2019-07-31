package com.yudha.myapotek;

public class Laporan {
    String jumlah_obat, nama_obat, satuan, total_harga, ukuran, varian, tanggal_beli, id_obat;

    public Laporan() {
    }

    public Laporan(String jumlah_obat, String nama_obat, String satuan, String total_harga, String ukuran, String varian, String tanggal_beli, String id_obat) {
        this.jumlah_obat = jumlah_obat;
        this.nama_obat = nama_obat;
        this.satuan = satuan;
        this.total_harga = total_harga;
        this.ukuran = ukuran;
        this.varian = varian;
        this.tanggal_beli = tanggal_beli;
        this.id_obat = id_obat;
    }

    public String getJumlah_obat() {
        return jumlah_obat;
    }

    public void setJumlah_obat(String jumlah_obat) {
        this.jumlah_obat = jumlah_obat;
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

    public String getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(String total_harga) {
        this.total_harga = total_harga;
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

    public String getTanggal_beli() {
        return tanggal_beli;
    }

    public void setTanggal_beli(String tanggal_beli) {
        this.tanggal_beli = tanggal_beli;
    }

    public String getId_obat() {
        return id_obat;
    }

    public void setId_obat(String id_obat) {
        this.id_obat = id_obat;
    }
}

