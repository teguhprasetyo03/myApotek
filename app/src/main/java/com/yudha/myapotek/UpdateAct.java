package com.yudha.myapotek;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import static android.text.TextUtils.isEmpty;

public class UpdateAct extends AppCompatActivity {
    Button btn_back,btn_update;
    EditText nama_obat,harga,stock,satuan,varian,ukuran,kd_rak,idObat;

    DatabaseReference reference;

    String cekHarga,cekId,cekKode,cekNama,cekSatuan,cekStock,cekVarian,cekUkuran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        btn_back=findViewById(R.id.btn_back);
        nama_obat=findViewById(R.id.nama_obat);
        harga=findViewById(R.id.harga);
        stock=findViewById(R.id.stock);
        satuan=findViewById(R.id.satuan);
        varian=findViewById(R.id.varian);
        ukuran=findViewById(R.id.ukuran);
        kd_rak=findViewById(R.id.kd_rak);
        idObat=findViewById(R.id.idObat);
        btn_update=findViewById(R.id.btn_update);
        getData();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference=FirebaseDatabase.getInstance().getReference().child("DaftarObat")
                        .child(nama_obat.getText().toString());
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        cekHarga=harga.getText().toString();
                        cekId=idObat.getText().toString();
                        cekKode=kd_rak.getText().toString();
                        cekNama=nama_obat.getText().toString();
                        cekSatuan=satuan.getText().toString();
                        cekStock=stock.getText().toString();
                        cekUkuran=ukuran.getText().toString();
                        cekVarian=varian.getText().toString();

                        //Mengecek agar tidak ada data yang kosong, saat proses update
                        if(isEmpty(cekHarga) || isEmpty(cekId) || isEmpty(cekKode)||isEmpty(cekNama)||isEmpty(cekSatuan)||isEmpty(cekStock)){
                            Toast.makeText(UpdateAct.this, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
                        }else {
                            MyObat myObat = new MyObat();
                            myObat.setId_obat(idObat.getText().toString());
                            myObat.setHarga(harga.getText().toString());
                            myObat.setNama_obat(nama_obat.getText().toString());
                            myObat.setKode_rak(kd_rak.getText().toString());
                            myObat.setSatuan(satuan.getText().toString());
                            myObat.setStock(stock.getText().toString());
                            myObat.setStock(ukuran.getText().toString());
                            myObat.setStock(varian.getText().toString());

                            dataSnapshot.getRef().child("id_obat").setValue(idObat.getText().toString());
                            dataSnapshot.getRef().child("nama_obat").setValue(nama_obat.getText().toString());
                            dataSnapshot.getRef().child("harga").setValue(harga.getText().toString());
                            dataSnapshot.getRef().child("stock").setValue(stock.getText().toString());
                            dataSnapshot.getRef().child("kode_rak").setValue(kd_rak.getText().toString());
                            dataSnapshot.getRef().child("satuan").setValue(satuan.getText().toString());
                            dataSnapshot.getRef().child("varian").setValue(varian.getText().toString());
                            dataSnapshot.getRef().child("ukuran").setValue(ukuran.getText().toString());

                            idObat.setText("");
                            nama_obat.setText("");
                            harga.setText("");
                            stock.setText("");
                            kd_rak.setText("");
                            satuan.setText("");
                            varian.setText("");
                            ukuran.setText("");

                            FancyToast.makeText(getApplicationContext(),"Obat Berhasil Di Proses",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();

                            Intent gohome=new Intent(UpdateAct.this,DashboardActivity.class);
                            startActivity(gohome);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
    private void getData(){
        final String getId =getIntent().getExtras().getString("id_obat");
        final String getHarga =getIntent().getExtras().getString("harga");
        final String getNama =getIntent().getExtras().getString("nama_obat");
        final String getKode =getIntent().getExtras().getString("kode_rak");
        final String getSatuan=getIntent().getExtras().getString("satuan");
        final String getStock=getIntent().getExtras().getString("stock");
        final String getVarian=getIntent().getExtras().getString("varian");
        final String getUkuran=getIntent().getExtras().getString("ukuran");
        idObat.setText(getId);
        harga.setText(getHarga);
        nama_obat.setText(getNama);
        kd_rak.setText(getKode);
        satuan.setText(getSatuan);
        stock.setText(getStock);
        varian.setText(getVarian);
        ukuran.setText(getUkuran);
    }
}
