package com.example.adiwiyata_admin;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter {

    //List untuk menampung semua data mahasiswa dari firebase
    List<Tanaman> fetchDataList;
    Context ct;
    private OnItemClickListener mListener;

    public MyAdapter(List<Tanaman> fetchDataList) {
        this.fetchDataList = fetchDataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tnmn,parent,false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view,mListener);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        ViewHolderClass viewHolderClass = (ViewHolderClass)holder;

        Tanaman tanaman = fetchDataList.get(position);
        viewHolderClass.nama.setText(tanaman.getNama());
        viewHolderClass.latin.setText(tanaman.getLatin());
        viewHolderClass.imagePath = tanaman.getImageUrl();
        storageRef.child(viewHolderClass.imagePath).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get()
                        .load(uri.toString())
                        .resize(50, 33)
                        .into(viewHolderClass.imageUrl);


            }
        });
        /*Glide.with(viewHolderClass.imageUrl.getContext())
                .load(tanaman.getImageUrl())
                .into(viewHolderClass.imageUrl);*/

    }

    @Override
    public int getItemCount() {
        return fetchDataList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{
        TextView nama;
        TextView latin;
        String imagePath;
        ImageView imageUrl;

        public ViewHolderClass(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            latin=itemView.findViewById(R.id.tv_latin);
            nama=itemView.findViewById(R.id.tv_nama);
            imageUrl=itemView.findViewById(R.id.tv_imageUrl);

            //Dilakukan agar item pada list dapat berpindah ke activity lain
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    public void changeList(List<Tanaman> fetchDataList) {
        this.fetchDataList = fetchDataList;
    }
}


