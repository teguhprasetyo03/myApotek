package com.yudha.myapotek;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class LaporanAct extends AppCompatActivity {
    Button btn_back;
    LinearLayout item_laporan;
    DatabaseReference reference;
    RecyclerView laporanobat_place;
    ArrayList<Laporan>laporan;
    LaporanAdaptor laporanAdaptor;
    SearchView xsearchview;

    String USERNAME_KEY="usernamekey";
    String username_key="";
    String username_key_new="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);
        getUsernameLocal();
        btn_back=findViewById(R.id.btn_back_laporan);
        item_laporan=findViewById(R.id.item_laporan);
        xsearchview=findViewById(R.id.xsearcview);

        laporanobat_place=findViewById(R.id.laporanobat_place);
        laporanobat_place.setLayoutManager(new LinearLayoutManager(this));
        laporan = new ArrayList<Laporan>();



        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        reference= FirebaseDatabase.getInstance().getReference().child("LaporanPembelian").child(username_key);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                    Laporan l = dataSnapshot2.getValue(Laporan.class);
                    laporan.add(l);
                }
                laporanAdaptor = new LaporanAdaptor(LaporanAct.this, laporan);
                laporanobat_place.setAdapter(laporanAdaptor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if (xsearchview != null) {
            xsearchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }
    }


    private void search(String str){
        ArrayList<Laporan>filter=new ArrayList<>();
        for (Laporan object : laporan)
        {
            if (object.getNama_obat().toLowerCase().contains(str.toLowerCase()))
            {
                filter.add(object);

            }else if (object.getTanggal_beli().toLowerCase().contains(str.toLowerCase())){
                filter.add(object);
            }
        }

        laporanAdaptor.filtersearch(filter);
    }



    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        username_key_new=sharedPreferences.getString(username_key,"");
    }
}
