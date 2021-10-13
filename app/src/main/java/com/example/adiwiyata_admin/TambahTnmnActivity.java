package com.example.adiwiyata_admin;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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


public class TambahTnmnActivity extends AppCompatActivity{
    EditText etLatin;
    EditText etNama;
    EditText etKingdom;
    EditText etClade;
    EditText etOrder;
    EditText etFamily;
    EditText etGenus;
    EditText etSpecies;
    EditText etDeskripsi;
    ImageView IvImageUrl;
    String etImageUrl = "";
    String[] dataInput;
    Integer Total;
    Button btnTambah;
    ImageButton btnBack;
    Uri imageUri;

    FirebaseDatabase database;
    DatabaseReference myref;
    DatabaseReference myref2;
    DatabaseReference myref3;
    StorageReference mystor;



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!= null && data.getData()!=null)
        {
            imageUri = data.getData();
            IvImageUrl.setImageURI(imageUri);
            long rand = System.currentTimeMillis();
            // Create a storage reference from our app
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
                    /**Method yang disengaja kosong. Tidak suatu perintah*/
                   }
               })
               .addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                    /**Method yang disengaja kosong. Tidak suatu perintah*/
                   }
               });

   }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_mhs);

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
        btnTambah = findViewById(R.id.btn_tambah);
        btnBack = findViewById(R.id.btn_back);

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

        insertData();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        myref3 = database.getReference().child("Total");
        myref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Total = Integer.valueOf(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            /**Method yang disengaja kosong. Tidak suatu perintah*/
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

    private void insertData(){
        database = FirebaseDatabase.getInstance();
        myref = database.getReference("tnmn");
        mystor = FirebaseStorage.getInstance().getReference();
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                final String order  = etOrder.getText().toString();
                final String family = etFamily.getText().toString();
                final String genus = etGenus.getText().toString();
                final String species  = etSpecies.getText().toString();
                final String deskripsi = etDeskripsi.getText().toString();
                
                final String nama = etNama.getText().toString();
                final String latin = etLatin.getText().toString();
                final String imageUrl = etImageUrl;
                final String kingdom = etKingdom.getText().toString();
                final String clade  = etClade.getText().toString();
               
                Total = Total + 1;
                final String id = Total.toString();
                if (latin.equals("") || nama.equals("") || imageUrl.equals("") || kingdom.equals("") || clade.equals("") || order.equals("") || family.equals("") || genus.equals("") || species.equals("") || deskripsi.equals("")){
                    Toast.makeText(TambahTnmnActivity.this, "Data Harus diisi semua!!", Toast.LENGTH_SHORT).show();
                }else {
                    myref2 = FirebaseDatabase.getInstance().getReference().child("tnmn");
                    myref2.orderByChild("nama").equalTo(nama).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) {
                                Toast.makeText(TambahTnmnActivity.this, "Maaf, data sudah ada pada database", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                uploadPicture();
                                dataInput = {id, nama, latin, imageUrl, kingdom, clade, order, family, genus, species, deskripsi};
                                myref.child(id).setValue(tnmn).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(TambahTnmnActivity.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(TambahTnmnActivity.this, "Data Gagal ditambahkan", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                myref3.setValue(Total).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                    /**Method yang disengaja kosong. Tidak suatu perintah*/
                                    }
                                });
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            /**Method sengaja dibiarkan tanpa adanya perintah dengan diberikan statement kosong*/
                        }
                    });

                }
            }
        });
    }


}
