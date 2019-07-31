package com.yudha.myapotek;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.zip.DataFormatException;

public class KeranjangActivity extends AppCompatActivity {

    Button btn_back,btn_bayar,btnplus,btnmines;
    TextView xnama_obat,xsatuan,xvarian,xukuran,xharga,xstock,textjumlahobat,tv_date,xid_obat;
    ImageView notice_uang;
    DatabaseReference reference,reference2,reference3,reference4;

    Integer valuejumlahobat = 1;
    Integer sisa_stock = 0;
    Integer valuetotalharga = 0;
    Integer valuehargaobat=0;
    Integer mystock=0;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    // generate nomor integer secara random
    // karena kita ingin membuat transaksi secara unik
    Integer nomor_transaksi = new Random().nextInt();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);

        getUsernameLocal();
        btn_back=findViewById(R.id.btn_back);
        xnama_obat=findViewById(R.id.xnama_obat);
        xsatuan=findViewById(R.id.xsatuan);
        xvarian=findViewById(R.id.xvarian);
        xukuran=findViewById(R.id.xukuran);
        xharga=findViewById(R.id.xharga);
        xstock=findViewById(R.id.xstock);
        xid_obat=findViewById(R.id.xid_obat);
        btn_bayar=findViewById(R.id.btn_bayar);
        textjumlahobat=findViewById(R.id.textjumlahobat);
        btnplus=findViewById(R.id.btnplus);
        btnmines=findViewById(R.id.btnmines);
        notice_uang=findViewById(R.id.notice_uang);
        tv_date=findViewById(R.id.tv_date);

        Calendar calendar=Calendar.getInstance();
        String currentDate= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        TextView textViewDate =findViewById(R.id.tv_date);
        textViewDate.setText(currentDate);



        // setting value baru untuk beberapa komponen
        textjumlahobat.setText(valuejumlahobat.toString());

        // secara default, kita hide btnmines
        btnmines.animate().alpha(0).setDuration(300).start();
        btnmines.setEnabled(false);
        notice_uang.setVisibility(View.GONE);

        //Mengambil data dari intent
        Bundle bundle=getIntent().getExtras();
        final String nama_obat_baru = bundle.getString("nama_obat");

        // mengambil data user dari firebase
        reference2=FirebaseDatabase.getInstance().getReference().child("DaftarObat").child(nama_obat_baru);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mystock=Integer.valueOf(dataSnapshot.child("stock").getValue().toString());
                xstock.setText(mystock+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //mengambil data dari firebase
        reference= FirebaseDatabase.getInstance().getReference().child("DaftarObat").child(nama_obat_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // menimpa data yang ada dengan data yang baru
                xid_obat.setText(dataSnapshot.child("id_obat").getValue().toString());
                xnama_obat.setText(dataSnapshot.child("nama_obat").getValue().toString());
                xsatuan.setText(dataSnapshot.child("satuan").getValue().toString());
                xukuran.setText(dataSnapshot.child("ukuran").getValue().toString());
                xvarian.setText(dataSnapshot.child("varian").getValue().toString());
                valuehargaobat=Integer.valueOf(dataSnapshot.child("harga").getValue().toString());
                xstock.setText(dataSnapshot.child("stock").getValue().toString());

                valuetotalharga=valuehargaobat * valuejumlahobat;
                xharga.setText(valuetotalharga+"");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // menyimpan data user kepada firebase dan membuat tabel baru "LaporanPembelian"
                reference3=FirebaseDatabase.getInstance().getReference().child("LaporanPembelian")
                        .child(xnama_obat.getText().toString()+ nomor_transaksi);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference3.getRef().child("tanggal_beli").setValue(tv_date.getText().toString());
                        reference3.getRef().child("id_obat").setValue(xid_obat.getText().toString());
                        reference3.getRef().child("nama_obat").setValue(xnama_obat.getText().toString());
                        reference3.getRef().child("varian").setValue(xvarian.getText().toString());
                        reference3.getRef().child("ukuran").setValue(xukuran.getText().toString());
                        reference3.getRef().child("satuan").setValue(xsatuan.getText().toString());
                        reference3.getRef().child("total_harga").setValue(valuetotalharga.toString());
                        reference3.getRef().child("jumlah_obat").setValue(valuejumlahobat.toString());

                        Intent succesbuy=new Intent(KeranjangActivity.this,SuccesBuy.class);
                        startActivity(succesbuy);

                        FancyToast.makeText(getApplicationContext(),"Obat Sudah Di Proses",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                reference4=FirebaseDatabase.getInstance().getReference().child("DaftarObat").child(nama_obat_baru);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sisa_stock = mystock - valuejumlahobat;
                        reference4.getRef().child("stock").setValue(sisa_stock+"");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valuejumlahobat+=1;
                textjumlahobat.setText(valuejumlahobat.toString());
                if (valuejumlahobat > 1) {
                    btnmines.animate().alpha(1).setDuration(300).start();
                    btnmines.setEnabled(true);
                }
                valuetotalharga = valuehargaobat * valuejumlahobat;
                xharga.setText(valuetotalharga+"");
                if(valuejumlahobat > mystock){
                    btn_bayar.animate().translationY(250)
                            .alpha(0).setDuration(350).start();
                    btn_bayar.setEnabled(false);

                    notice_uang.setVisibility(View.VISIBLE);
                    xstock.setTextColor(Color.parseColor("#D1206B"));
                    FancyToast.makeText(getApplicationContext(),"Stock Tidak Cukup!!",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                }
            }
        });
        btnmines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valuejumlahobat-=1;
                textjumlahobat.setText(valuejumlahobat.toString());
                if (valuejumlahobat < 2) {
                    btnmines.animate().alpha(0).setDuration(300).start();
                    btnmines.setEnabled(false);
                }
                valuetotalharga = valuehargaobat * valuejumlahobat;
                xharga.setText(valuetotalharga+"");
                if(valuejumlahobat <= mystock){
                    btn_bayar.animate().translationY(0)
                            .alpha(1).setDuration(350).start();
                    btn_bayar.setEnabled(true);
                    xstock.setTextColor(Color.parseColor("#000000"));
                    notice_uang.setVisibility(View.GONE);

                }
            }
        });
    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}