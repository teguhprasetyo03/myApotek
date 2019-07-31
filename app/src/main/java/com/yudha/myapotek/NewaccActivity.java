package com.yudha.myapotek;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewaccActivity extends AppCompatActivity {
    Button btn_finish;
    LinearLayout btn_back;
    EditText username,nama_lengkap,password;

    DatabaseReference reference,reference_username;

    String USERNAME_KEY="usernamekey";
    String username_key="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newacc);


        btn_finish=findViewById(R.id.btn_finish);
        btn_back=findViewById(R.id.btn_back);
        username=findViewById(R.id.username);
        nama_lengkap=findViewById(R.id.nama_lengkap);
        password=findViewById(R.id.password);



        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ubah state menjadi loading
                btn_finish.setEnabled(false);
                btn_finish.setText("Loading...");
                final String xusername=username.getText().toString();
                final String xnamalengkap=nama_lengkap.getText().toString();
                final String xpassword=password.getText().toString();

                if (TextUtils.isEmpty(xusername)) {
                    username.setError("Username Tidak Boleh Kosong");
                    //ubah state menjadi active
                    btn_finish.setEnabled(true);
                    btn_finish.setText("FINISH");
                }else if (TextUtils.isEmpty(xnamalengkap)) {
                    nama_lengkap.setError("Nama Lengkap Tidak Boleh Kosong");
                    //ubah state menjadi active
                    btn_finish.setEnabled(true);
                    btn_finish.setText("FINISH");
                    return;
                }else if (TextUtils.isEmpty(xpassword)){
                    password.setError("Password Tidak Boleh Kosong");
                    //ubah state menjadi active
                    btn_finish.setEnabled(true);
                    btn_finish.setText("FINISH");
                }else {

                    //Menyimpan data kepada local/ (Handphone)
                    SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(username_key, username.getText().toString());
                    editor.apply();


                    //simpan kepada firebase
                    reference_username = FirebaseDatabase.getInstance().getReference()
                            .child("Users").child(username.getText().toString());
                    reference_username.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()) {
                                Toast.makeText(getApplicationContext(), "Username Sudah Ada", Toast.LENGTH_SHORT).show();
                                //ubah state menjadi active
                                btn_finish.setEnabled(true);
                                btn_finish.setText("FINISH");
                            } else {
                                //menyimpan data kepada local storage (Handphone)
                                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(username_key, username.getText().toString());
                                editor.apply();

                                //simpan kepada firebase
                                reference = FirebaseDatabase.getInstance().getReference()
                                        .child("Users").child(username.getText().toString());
                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        dataSnapshot.getRef().child("username").setValue(username.getText().toString());
                                        dataSnapshot.getRef().child("nama_lengkap").setValue(nama_lengkap.getText().toString());
                                        dataSnapshot.getRef().child("password").setValue(password.getText().toString());
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                Intent gotohome = new Intent(NewaccActivity.this, DashboardActivity.class);
                                startActivity(gotohome);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
