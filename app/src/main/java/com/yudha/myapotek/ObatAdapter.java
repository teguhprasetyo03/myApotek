package com.yudha.myapotek;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;

public class ObatAdapter extends RecyclerView.Adapter<ObatAdapter.MyViewHolder> {
    DatabaseReference reference;

    private Context context;
    ArrayList<MyObat> myObat;

    //Membuat Interfece
    public interface obatListener{
        void onDeleteData(MyObat myObat, int position);
    }

    //Deklarasi objek dari Interfece
    obatListener listener;


    public ObatAdapter(Context context,ArrayList<MyObat> myObat){
        this.context=context;
        this.myObat = myObat;
        listener=(DaftarObatAct)context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dataobat,parent,false);
        MyViewHolder holder =new MyViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder,final int position) {

        myViewHolder.xnama_obat.setText(myObat.get(position).getNama_obat());
        myViewHolder.xharga.setText("Rp. "+myObat.get(position).getHarga());
        myViewHolder.xsatuan.setText(myObat.get(position).getSatuan());
        myViewHolder.xvarian.setText(myObat.get(position).getVarian());
        myViewHolder.xukuran.setText(myObat.get(position).getUkuran());
        myViewHolder.xstock.setText(String.valueOf(myObat.get(position).getStock()));
        myViewHolder.xid_obat.setText(myObat.get(position).getId_obat());
        myViewHolder.xkode_rak.setText(myObat.get(position).getKode_rak());



        final String getNamaObat = myObat.get(position).getNama_obat();



        myViewHolder.add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotocart=new Intent(context,KeranjangActivity.class);
                gotocart.putExtra("nama_obat",getNamaObat);
                context.startActivity(gotocart);
                FancyToast.makeText(context,"Memproses Obat "+getNamaObat,FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
                Log.d("Obat :","Ini keranjang");
            }
        });


        myViewHolder.btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Display Option menu
                PopupMenu popupMenu = new PopupMenu(context,myViewHolder.btn_menu);
                popupMenu.inflate(R.menu.option_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.item_update:
                                Bundle bundle = new Bundle();
                                bundle.putString("harga", myObat.get(position).getHarga());
                                bundle.putString("id_obat", myObat.get(position).getId_obat());
                                bundle.putString("kode_rak", myObat.get(position).getKode_rak());
                                bundle.putString("nama_obat", myObat.get(position).getNama_obat());
                                bundle.putString("stock", myObat.get(position).getStock());
                                bundle.putString("satuan", myObat.get(position).getSatuan());
                                bundle.putString("varian", myObat.get(position).getVarian());
                                bundle.putString("ukuran", myObat.get(position).getUkuran());
                                bundle.putString("getPrimaryKey", myObat.get(position).getKey());
                                Intent intent = new Intent(context, UpdateAct.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;
                            case R.id.item_delete:
                                reference=FirebaseDatabase.getInstance().getReference("DaftarObat").child(getNamaObat);
                                reference.removeValue();
                                Intent gohome=new Intent(context,DashboardActivity.class);
                                context.startActivity(gohome);
                                FancyToast.makeText(context,"Data Berhasil Di Hapus",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                                break;
                                default:
                                    break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });


    }



    @Override
    public int getItemCount() {

        return myObat.size();
    }

    public void filtersearch(ArrayList<MyObat>searched){
        myObat=searched;
        this.notifyDataSetChanged();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView xnama_obat,xharga,xsatuan,xvarian,xukuran,xstock,xkode_rak,xid_obat;
        public Button add_cart,btn_menu;

        public MyViewHolder(View itemView) {
            super(itemView);

            xnama_obat=itemView.findViewById(R.id.xnama_obat);
            xharga=itemView.findViewById(R.id.xharga);
            xsatuan=itemView.findViewById(R.id.xsatuan);
            xvarian=itemView.findViewById(R.id.xvarian);
            xukuran=itemView.findViewById(R.id.xukuran);
            xstock=itemView.findViewById(R.id.xstock);
            xkode_rak=itemView.findViewById(R.id.xkode_rak);
            xid_obat=itemView.findViewById(R.id.xid_obat);
            add_cart=itemView.findViewById(R.id.btn_add_cart);
            btn_menu=itemView.findViewById(R.id.btn_menu);


        }

    }


}


