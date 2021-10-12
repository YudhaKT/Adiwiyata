package com.example.adiwiyata_admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class EditTnmnActivity extends AppCompatActivity {
    EditText etLatin;
    EditText etNama;
    EditText etKingdom;
    EditText etClade;
    EditText etOrder;
    EditText etFamily;
    EditText etGenus;
    EditText etSpecies;
    EditText etDeskripsi;
    String id_temp;
    String temp_latin;
    String temp_nama;
    String temp_imageUrl;
    String temp_kingdom;
    String temp_clade;
    String temp_order;
    String temp_family;
    String temp_genus;
    String temp_species;
    String temp_deskripsi;
    
    ImageView IvImageUrl;
    String etImageUrl;
    Integer Total;
    Button btnUpdate;
    Button btnHapus;
    ImageButton btnBack;
    Uri imageUri;
    static EditTnmnActivity ActivityB;
    FirebaseDatabase database;
    DatabaseReference myref;
    DatabaseReference myref2;
    DatabaseReference databaseReference;
    StorageReference mystor;
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    etNama = findViewById(R.id.et_nama);
    etLatin = findViewById(R.id.et_latin);
    IvImageUrl = findViewById(R.id.tv_imageUrl);
    etKingdom = findViewById(R.id.et_kingdom);
    etClade = findViewById(R.id.et_clade);
    etOrder = findViewById(R.id.et_order);
    etFamily = findViewById(R.id.et_family);
    etGenus = findViewById(R.id.et_genus);
    etSpecies = findViewById(R.id.et_species);
    etDeskripsi = findViewById(R.id.et_deskripsi);
    etDeskripsi.setMovementMethod(new ScrollingMovementMethod());
    btnUpdate = findViewById(R.id.btn_update);
    btnHapus = findViewById(R.id.btn_hapus);
    btnBack = findViewById(R.id.btn_back);
    database = FirebaseDatabase.getInstance();
    mystor = FirebaseStorage.getInstance().getReference();
    myref = database.getReference().child("tnmn").child(id);

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!= null && data.getData()!=null)
        {
            imageUri = data.getData();
            IvImageUrl.setImageURI(imageUri);
            long rand = System.currentTimeMillis();
            etImageUrl = "images/" + rand + getExtension(imageUri);
        }
    }

    private String getExtension(Uri uri)
    {
        ContentResolver cr= getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadPicture()
    {
        StorageReference riversRef = mystor.child(etImageUrl);
        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    /**Method sengaja dibiarkan tanpa adanya perintah dengan diberikan statement kosong*/
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    /**Method sengaja dibiarkan tanpa adanya perintah dengan diberikan statement kosong*/
                    }
                });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tnmn);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        
        myref.addValueEventListener(new ValueEventListener() {
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
                temp_species = dataSnapshot.child("species").getValue().toString();
                temp_deskripsi = dataSnapshot.child("deskripsi").getValue().toString();
                id_temp = dataSnapshot.child("id").getValue().toString();
                etImageUrl = temp_imageUrl;
                storageRef.child(temp_imageUrl).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(IvImageUrl.getContext())
                                .load(uri.toString())
                                .into(IvImageUrl);
                    }
                });
                etLatin.setText(temp_latin);
                etNama.setText(temp_nama);
                etKingdom.setText(temp_kingdom);
                etClade.setText(temp_clade);
                etOrder.setText(temp_order);
                etFamily.setText(temp_family);
                etGenus.setText(temp_genus);
                etSpecies.setText(temp_species);
                etDeskripsi.setText(temp_deskripsi);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            /**Method sengaja dibiarkan tanpa adanya perintah dengan diberikan statement kosong*/
            }
        });
        IvImageUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();

            }

            private void choosePicture() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);

            }

        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapusData();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        etDeskripsi.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (etDeskripsi.hasFocus()) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_SCROLL:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                        default:
                            //Tidak melakukan aksi
                    }
                }
                return false;
            }
        });
    }

    private void updateData(){

                final String nama = etNama.getText().toString();
                final String latin = etLatin.getText().toString();
                final String imageUrl = etImageUrl;
                final String kingdom = etKingdom.getText().toString();
                final String clade  = etClade.getText().toString();
                final String order  = etOrder.getText().toString();
                final String family = etFamily.getText().toString();
                final String genus = etGenus.getText().toString();
                final String species  = etSpecies.getText().toString();
                final String deskripsi = etDeskripsi.getText().toString();
                final String id = id_temp;
        if (latin.equals("") || nama.equals("") || imageUrl.equals("") || kingdom.equals("") || clade.equals("") || order.equals("") || family.equals("") || genus.equals("") || species.equals("") || deskripsi.equals("")){
            Toast.makeText(EditTnmnActivity.this, "Data Harus diisi semua!!", Toast.LENGTH_SHORT).show();
        }else {
            myref2 = FirebaseDatabase.getInstance().getReference().child("tnmn");
            myref2.orderByChild("nama").equalTo(nama).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        Toast.makeText(EditTnmnActivity.this, "Maaf, nama tumbuhan sudah ada pada database", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (latin.equals(temp_latin) && nama.equals(temp_nama) && imageUrl.equals(temp_imageUrl) && kingdom.equals(temp_kingdom) && clade.equals(temp_clade) && order.equals(temp_order) && family.equals(temp_family) && genus.equals(temp_genus) && species.equals(temp_species) && deskripsi.equals(temp_deskripsi)){
                            Toast.makeText(EditTnmnActivity.this, "Data Tidak Berubah", Toast.LENGTH_SHORT).show();
                        }else {
                            AlertDialog dialog = new AlertDialog.Builder(EditTnmnActivity.this )
                                    .setTitle("Warning")
                                    .setMessage("Apakah kamu yakin untuk mengubah data ini?")
                                    .setPositiveButton("Yes", null)
                                    .setNegativeButton("No", null)
                                    .show();
                            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                            positiveButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(!imageUrl.equals(temp_imageUrl))
                                    {
                                        mystor.child(temp_imageUrl).delete();
                                        uploadPicture();
                                    }
                                    Tanaman tnmn = new Tanaman(id, nama, latin, imageUrl, kingdom, clade, order, family, genus, species, deskripsi);
                                    myref.setValue(tnmn).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(EditTnmnActivity.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                                            etNama.setText("");
                                            etLatin.setText("");
                                            etKingdom.setText("");
                                            etClade.setText("");
                                            etOrder.setText("");
                                            etFamily.setText("");
                                            etGenus.setText("");
                                            etSpecies.setText("");
                                            etDeskripsi.setText("");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(EditTnmnActivity.this, "Data Gagal ditambahkan", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    ListTanaman.getInstance().finish();
                                    startActivity(new Intent(EditTnmnActivity.this, ListTanaman.class));
                                    EditTnmnActivity.getInstance().finish();
                                }
                            });

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    /**Method sengaja dibiarkan tanpa adanya perintah dengan diberikan statement kosong*/
                }
            });
        }

            }
    private void hapusData(){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Warning")
                .setMessage("Apakah kamu yakin untuk menghapus data ini?")
                .setPositiveButton("Yes", null)
                .setNegativeButton("No", null)
                .show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  myref.removeValue();
                  mystor.child(temp_imageUrl).delete();
                  ListTanaman.getInstance().finish();
                  startActivity(new Intent(EditTnmnActivity.this, ListTanaman.class));
                  EditTnmnActivity.getInstance().finish();
              }
          });

    }
    public static EditTnmnActivity getInstance()
    {
        return ActivityB;
    };
}
