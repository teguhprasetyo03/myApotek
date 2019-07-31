package com.yudha.myapotek;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    Button btn_new_acc,btn_sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_new_acc=findViewById(R.id.btn_new_acc);
        btn_sign_in=findViewById(R.id.btn_sign_in);


        btn_new_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoregis=new Intent(LoginActivity.this,NewaccActivity.class);
                startActivity(gotoregis);
            }
        });
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gosignin=new Intent(LoginActivity.this,SigninActivity.class);
                startActivity(gosignin);
            }
        });
    }
}
