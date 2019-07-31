package com.yudha.myapotek;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import com.google.firebase.database.DatabaseReference;

public class DashboardActivity extends AppCompatActivity {
    LinearLayout btn_tambah_obat,btn_daftar_obat,btn_sign_out,btn_laporan;

    String USERNAME_KEY="usernamekey";
    String username_key="";
    String username_key_new="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getUsernameLocal();


        btn_tambah_obat=findViewById(R.id.btn_tambah_obat);
        btn_daftar_obat=findViewById(R.id.btn_daftar_obat);
        btn_sign_out=findViewById(R.id.btn_sign_out);
        btn_laporan=findViewById(R.id.bnt_laporan);


        btn_laporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotolaporan=new Intent(DashboardActivity.this,LaporanAct.class);
                startActivity(gotolaporan);
            }
        });




        btn_tambah_obat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gototambah=new Intent(DashboardActivity.this,TambahObatAct.class);
                startActivity(gototambah);
            }
        });

        btn_daftar_obat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent godaftarobat=new Intent(DashboardActivity.this, DaftarObatAct.class);
                startActivity(godaftarobat);
            }
        });

        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Menghapus isi/nilai/value dari username local
                //menyimpan data kepada local storage (Handphone)
                SharedPreferences sharedPreferences=getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString(username_key,null);
                editor.apply();


                Intent gosignin=new Intent(DashboardActivity.this,SigninActivity.class);
                startActivity(gosignin);
                finish();
            }
        });
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        username_key_new=sharedPreferences.getString(username_key,"");
    }
}
