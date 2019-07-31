package com.yudha.myapotek;

import android.app.Activity;
import android.content.Context;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DaftarObatAct extends AppCompatActivity implements ObatAdapter.obatListener{
    Button btn_back;

    LinearLayout item_data_obat;

    DatabaseReference reference2;

    RecyclerView dataobat_place;
    ArrayList<MyObat> myObat;
    ObatAdapter obatAdapter;
    SearchView searcview;

    String USERNAME_KEY="usernamekey";
    String username_key="";
    String username_key_new="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_obat);
        getUsernameLocal();

        btn_back = findViewById(R.id.btn_back);
        item_data_obat = findViewById(R.id.item_data_obat);
        searcview = findViewById(R.id.searcview);

        dataobat_place = (findViewById(R.id.dataobat_place));
        dataobat_place.setLayoutManager(new LinearLayoutManager(this));
        myObat = new ArrayList<MyObat>();


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



            reference2 = FirebaseDatabase.getInstance().getReference().child("DaftarObat").child(username_key);
            reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    myObat = new ArrayList<>();

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        MyObat p = dataSnapshot1.getValue(MyObat.class);
                        p.setKey(dataSnapshot1.getKey());
                        myObat.add(p);

                    }
                    obatAdapter = new ObatAdapter(DaftarObatAct.this, myObat);
                    dataobat_place.setAdapter(obatAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        if (searcview != null) {
            searcview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        ArrayList<MyObat>filter=new ArrayList<>();
        for (MyObat object : myObat)
        {
            if (object.getNama_obat().toLowerCase().contains(str.toLowerCase())
                    ||object.getStock().toLowerCase().contains(str.toLowerCase()))
            {
                filter.add(object);

            }
        }

        obatAdapter.filtersearch(filter);
    }




    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        username_key_new=sharedPreferences.getString(username_key,"");
    }


    @Override
    public void onDeleteData(MyObat myObat, int position) {
            reference2.child("DaftarObat").child("nama_obat").removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(DaftarObatAct.this, "Berhasil", Toast.LENGTH_SHORT).show();
                        }
                    });


    }
}
