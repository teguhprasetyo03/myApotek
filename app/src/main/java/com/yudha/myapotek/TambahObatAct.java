package com.yudha.myapotek;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.text.TextUtils.isEmpty;


public class TambahObatAct extends AppCompatActivity {
    Button btn_back,btn_tambah;
    EditText nama_obat,harga,stock,satuan,varian,ukuran,kd_rak,idObat;

    DatabaseReference reference,reference_namaobat;

    String USERNAME_KEY="usernamekey";
    String username_key="";
    String username_key_new="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_obat);

        getUsernameLocal();


        btn_back=findViewById(R.id.btn_back);
        nama_obat=findViewById(R.id.nama_obat);
        harga=findViewById(R.id.harga);
        stock=findViewById(R.id.stock);
        satuan=findViewById(R.id.satuan);
        varian=findViewById(R.id.varian);
        ukuran=findViewById(R.id.ukuran);
        kd_rak=findViewById(R.id.kd_rak);
        idObat=findViewById(R.id.idObat);
        btn_tambah=findViewById(R.id.btn_tambah);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String namaObat = nama_obat.getText().toString();
                final String hargaObat=harga.getText().toString();
                final String idobat=idObat.getText().toString();
                final String stockObat=stock.getText().toString();
                final String kodeRak=kd_rak.getText().toString();
                final String Satuan = satuan.getText().toString();
                if (isEmpty(namaObat)) {
                    nama_obat.setError("Nama Obat Tidak Boleh Kosong");
                }else if (isEmpty(hargaObat)){
                    harga.setError("Harga Obat Tidak Boleh Kosong");
                    return;
                }else if (isEmpty(idobat)){
                    idObat.setError("Id Obat Tidak Boleh Kosong");
                    return;
                }else if (isEmpty(stockObat)){
                    stock.setError("Stock Obat Tidak Boleh Kosong");
                    return;
                }else if (isEmpty(kodeRak)){
                    kd_rak.setError("Kode Rak Tidak Boleh Kosong");
                    return;
                }else if (isEmpty(Satuan)){
                    satuan.setError("Satuan Tidak Boleh Kosong");
                }else {

                    //Menyimpan data kepada local/ (Handphone)
                    SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(username_key, nama_obat.getText().toString());
                    editor.apply();

                    reference_namaobat=FirebaseDatabase.getInstance().getReference().child("DaftarObat")
                            .child(nama_obat.getText().toString());
                    reference_namaobat.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                //Menyimpan data kepada local/ (Handphone)
                                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(username_key, nama_obat.getText().toString());
                                editor.apply();

                                //Mengambil Nama dari firebase
                                reference = FirebaseDatabase.getInstance().getReference().child("DaftarObat").child(nama_obat.getText().toString());
                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            Toast.makeText(getApplicationContext(), "Nama Obat Sudah Ada", Toast.LENGTH_SHORT).show();
                                        } else {
                                            dataSnapshot.getRef().child("id_obat").setValue(idObat.getText().toString());
                                            dataSnapshot.getRef().child("nama_obat").setValue(nama_obat.getText().toString());
                                            dataSnapshot.getRef().child("harga").setValue(harga.getText().toString());
                                            dataSnapshot.getRef().child("stock").setValue(stock.getText().toString());
                                            dataSnapshot.getRef().child("kode_rak").setValue(kd_rak.getText().toString());
                                            dataSnapshot.getRef().child("satuan").setValue(satuan.getText().toString());
                                            dataSnapshot.getRef().child("varian").setValue(varian.getText().toString());
                                            dataSnapshot.getRef().child("ukuran").setValue(ukuran.getText().toString());

                                            Toast.makeText(getApplicationContext(), "Data Berhasil Di Tambahkan", Toast.LENGTH_SHORT).show();

                                            idObat.setText("");
                                            nama_obat.setText("");
                                            harga.setText("");
                                            stock.setText("");
                                            kd_rak.setText("");
                                            satuan.setText("");
                                            varian.setText("");
                                            ukuran.setText("");

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            }
        });

    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        username_key_new=sharedPreferences.getString(username_key,"");
    }
}
