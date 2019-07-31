package com.yudha.myapotek;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class LaporanAdaptor extends RecyclerView.Adapter<LaporanAdaptor.MyViewHolder> {
    private Context context;
    ArrayList<Laporan> laporan;



    public LaporanAdaptor(Context context,ArrayList<Laporan> laporan){
        this.context=context;
        this.laporan = laporan;

    }
    @NonNull
    @Override
    public LaporanAdaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laporan,parent,false);
        MyViewHolder holder =new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LaporanAdaptor.MyViewHolder myViewHolder, int i) {
        myViewHolder.xxnama_obat.setText(laporan.get(i).getNama_obat());
        myViewHolder.xxharga.setText(laporan.get(i).getTotal_harga());
        myViewHolder.xxsatuan.setText(laporan.get(i).getSatuan());
        myViewHolder.xxvarian.setText(laporan.get(i).getVarian());
        myViewHolder.xxukuran.setText(laporan.get(i).getUkuran());
        myViewHolder.xxstock.setText(laporan.get(i).getJumlah_obat());
        myViewHolder.xxtanggal.setText(laporan.get(i).getTanggal_beli());
        myViewHolder.xxid_obat.setText(laporan.get(i).getId_obat());

    }

    @Override
    public int getItemCount() {
        return laporan.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView xxnama_obat,xxharga,xxsatuan,xxvarian,xxukuran,xxstock,xxtanggal,xxid_obat;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            xxnama_obat=itemView.findViewById(R.id.xxnama_obat);
            xxharga=itemView.findViewById(R.id.xxharga);
            xxsatuan=itemView.findViewById(R.id.xxsatuan);
            xxvarian=itemView.findViewById(R.id.xxvarian);
            xxukuran=itemView.findViewById(R.id.xxukuran);
            xxstock=itemView.findViewById(R.id.xxstock);
            xxtanggal=itemView.findViewById(R.id.xxtanggal);
            xxid_obat=itemView.findViewById(R.id.xxid_obat);
        }
    }
    public void filtersearch(ArrayList<Laporan> searched){
        laporan=searched;
        this.notifyDataSetChanged();
    }
}
