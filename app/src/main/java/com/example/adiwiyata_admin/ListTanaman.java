package com.example.adiwiyata_admin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListTanaman extends AppCompatActivity {
    List<Tanaman> fetchData, fetchData2;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    TextView tvJumlah;
    Integer Jumlah = 0;
    DatabaseReference databaseReference;
    EditText etSearch;
    ImageButton btnBack;
    static ListTanaman activityA;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tanaman);
        activityA = this;
        recyclerView = findViewById(R.id.recyclerview);
        etSearch = findViewById(R.id.et_search);
        tvJumlah = findViewById(R.id.tv_jumlah);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchData = new ArrayList<>();
        fetchData2 = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("tnmn");
        databaseReference.orderByChild("nama").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String id = ds.child("id").getValue().toString();
                    String nama = ds.child("nama").getValue().toString();
                    String latin = ds.child("latin").getValue().toString();
                    String imageUrl = ds.child("imageUrl").getValue().toString();
                    Tanaman data = new Tanaman(id,nama,latin,imageUrl);
                    fetchData.add(data);
                    Jumlah = Jumlah + 1;
                }
                myAdapter = new MyAdapter(fetchData);
                tvJumlah.setText("Jumlah Tanaman : " + Jumlah.toString());
                recyclerView.setAdapter(myAdapter);
                myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        String id = fetchData.get(position).getId();
                        //Toast.makeText(getApplicationContext(), "Your ID is " + id,  Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ListTanaman.this, EditTnmnActivity.class).putExtra("id",id));
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ListTanaman.this, "Maaf Terjadi kesalahan... coba beberapa saat lagi!", Toast.LENGTH_SHORT).show();
            }
        });
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            /**Method yang disengaja kosong. Tidak suatu perintah*/
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            /**Method yang disengaja kosong. Tidak suatu perintah*/
            }

            @Override
            public void afterTextChanged(Editable s) {
                databaseReference.orderByChild("nama").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        fetchData2.clear();
                        myAdapter.notifyDataSetChanged();
                        Jumlah = 0;
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            String id = ds.child("id").getValue().toString();
                            String nama = ds.child("nama").getValue().toString();
                            String latin = ds.child("latin").getValue().toString();
                            String imageUrl = ds.child("imageUrl").getValue().toString();
                            Tanaman data = new Tanaman(id,nama,latin,imageUrl);
                            if(nama.toLowerCase().contains(etSearch.getText().toString().toLowerCase())||latin.toLowerCase().contains(etSearch.getText().toString().toLowerCase()))
                            {
                                fetchData2.add(data);
                                Jumlah = Jumlah + 1;
                            }
                        }
                        myAdapter = new MyAdapter(fetchData2);
                        tvJumlah.setText("Jumlah Tanaman : " + Jumlah.toString());
                        recyclerView.setAdapter(myAdapter);
                        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                String id = fetchData2.get(position).getId();
                                //Toast.makeText(getApplicationContext(), "Your ID is " + id,  Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ListTanaman.this, EditTnmnActivity.class).putExtra("id",id));
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(ListTanaman.this, "Maaf Terjadi kesalahan... coba beberapa saat lagi!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    public static ListTanaman getInstance()
    {
        return activityA;
    };
}
/** Hello namaku Yudha */
