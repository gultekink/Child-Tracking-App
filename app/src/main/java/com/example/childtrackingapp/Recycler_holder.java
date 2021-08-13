package com.example.childtrackingapp;




import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;


public class Recycler_holder extends RecyclerView.Adapter<Recycler_holder.PostHolder>{

    private ArrayList<String> adres;
    private ArrayList<String> date;



    public Recycler_holder(ArrayList<String> adres,ArrayList<String> date) {

        this.adres = adres;
        this.date = date;
    }

                 @NonNull
                 @Override
                 public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                     LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
                     View view = layoutInflater.inflate(R.layout.recycler_row,parent,false);


                     return new PostHolder(view);
                 }

                 @Override
                 public void onBindViewHolder(@NonNull PostHolder holder, int position) {

                holder.adresText.setText(adres.get(position));
                holder.dateText.setText(date.get(position));

                 }

                 @Override
                 public int getItemCount() {

                  return adres.size();

                 }

                 class PostHolder extends RecyclerView.ViewHolder{

                       TextView adresText ;
                       TextView dateText;

                     public PostHolder(@NonNull View itemView) {
                         super(itemView);
                           adresText =itemView.findViewById(R.id.adresText);
                           dateText = itemView.findViewById(R.id.dateText);


                     }
                 }

             }
