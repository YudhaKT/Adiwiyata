package com.example.adiwiyata_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class TnmnActivity extends AppCompatActivity {
    TextView tvNama;
    TextView tvLatin;
    TextView tvKingdom;
    TextView tvClade;
    TextView tvOrder;
    TextView tvFamily;
    TextView tvGenus;
    TextView tvSpecies;
    
    ImageView ivImageUrl;
    ImageButton btnBack;
    String temps_latin;
    String temps_nama;
    String temps_imageUrl;
    String temps_kingdom;
    String temps_clade;
    String temps_order;
    String temps_family;
    String temps_genus;
    String temps_species;
    
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tnmn);
        Intent intent = getIntent();
        String nama = intent.getStringExtra("nama");
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        tvOrder = findViewById(R.id.tv_order);
        tvFamily = findViewById(R.id.tv_family);
        tvGenus = findViewById(R.id.tv_genus);
        tvSpecies = findViewById(R.id.tv_species);
        btnBack = findViewById(R.id.btn_back);
        
        tvNama = findViewById(R.id.tv_nama);
        tvLatin = findViewById(R.id.tv_latin);
        ivImageUrl = findViewById(R.id.iv_imageUrl);
        tvKingdom = findViewById(R.id.tv_kingdom);
        tvClade = findViewById(R.id.tv_clade);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("tnmn").child(nama);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                temps_clade = dataSnapshot.child("clade").getValue().toString();
                temps_order = dataSnapshot.child("order").getValue().toString();
                temps_family = dataSnapshot.child("family").getValue().toString();
                temps_genus = dataSnapshot.child("genus").getValue().toString();
                temps_species= dataSnapshot.child("species").getValue().toString();
                temps_latin = dataSnapshot.child("latin").getValue().toString();
                temps_nama = dataSnapshot.child("nama").getValue().toString();
                temps_imageUrl = dataSnapshot.child("imageUrl").getValue().toString();
                temps_kingdom = dataSnapshot.child("kingdom").getValue().toString();
                storageRef.child(temps_imageUrl).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(ivImageUrl.getContext())
                                .load(uri.toString())
                                .into(ivImageUrl);
                    }
                });
                temps_clade = temps_clade.replace(", ", "\n");
                tvLatin.setText(temps_latin);
                tvNama.setText(temps_nama);
                tvKingdom.setText(temps_kingdom);
                tvClade.setText(temps_clade);
                tvOrder.setText(temps_order);
                tvFamily.setText(temps_family);
                tvGenus.setText(temps_genus);
                tvSpecies.setText(temps_species);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            /**Method sengaja dibiarkan tanpa adanya perintah dengan diberikan statement kosong*/
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
