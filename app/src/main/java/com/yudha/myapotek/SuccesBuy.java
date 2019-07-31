package com.yudha.myapotek;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SuccesBuy extends AppCompatActivity {
    Button btn_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succes_buy);
        btn_finish=findViewById(R.id.btn_finish);

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent godashboard=new Intent(SuccesBuy.this,DashboardActivity.class);
                startActivity(godashboard);
            }
        });
    }
}
