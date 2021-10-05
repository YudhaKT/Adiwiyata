package com.example.adiwiyata_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void pindah(View view){
        switch (view.getId()){
            case R.id.add_tnmn :
                    startActivity(new Intent(MainActivity.this, TambahTnmnActivity.class));
                break;
            case R.id.list_tnmn :
                startActivity(new Intent(MainActivity.this, ListTanaman.class));
                break;
            case R.id.add_qr :
                startActivity(new Intent(MainActivity.this, QRCode.class));
                break;
        }
    }

}