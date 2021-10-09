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
    TextView tvNama, tvLatin, tvKingdom, tvClade, tvOrder, tvFamily, tvGenus, tvSpecies;
    ImageView ivImageUrl;
    ImageButton btnBack;
    String temp_latin, temp_nama,temp_imageUrl,temp_kingdom, temp_clade, temp_order, temp_family, temp_genus, temp_species;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tnmn);
        Intent intent = getIntent();
        String nama = intent.getStringExtra("nama");
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        tvNama = findViewById(R.id.tv_nama);
        tvLatin = findViewById(R.id.tv_latin);
        ivImageUrl = findViewById(R.id.iv_imageUrl);
        tvKingdom = findViewById(R.id.tv_kingdom);
        tvClade = findViewById(R.id.tv_clade);
        tvOrder = findViewById(R.id.tv_order);
        tvFamily = findViewById(R.id.tv_family);
        tvGenus = findViewById(R.id.tv_genus);
        tvSpecies = findViewById(R.id.tv_species);
        btnBack = findViewById(R.id.btn_back);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("tnmn").child(nama);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                temp_latin = dataSnapshot.child("latin").getValue().toString();
                temp_nama = dataSnapshot.child("nama").getValue().toString();
                temp_imageUrl = dataSnapshot.child("imageUrl").getValue().toString();
                temp_kingdom = dataSnapshot.child("kingdom").getValue().toString();
                temp_clade = dataSnapshot.child("clade").getValue().toString();
                temp_order = dataSnapshot.child("order").getValue().toString();
                temp_family = dataSnapshot.child("family").getValue().toString();
                temp_genus = dataSnapshot.child("genus").getValue().toString();
                temp_species= dataSnapshot.child("species").getValue().toString();

                storageRef.child(temp_imageUrl).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(ivImageUrl.getContext())
                                .load(uri.toString())
                                .into(ivImageUrl);
                    }
                });
                temp_clade = temp_clade.replace(", ", "\n");
                tvLatin.setText(temp_latin);
                tvNama.setText(temp_nama);
                tvKingdom.setText(temp_kingdom);
                tvClade.setText(temp_clade);
                tvOrder.setText(temp_order);
                tvFamily.setText(temp_family);
                tvGenus.setText(temp_genus);
                tvSpecies.setText(temp_species);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
/**Halo yud iki munir gitu*/
