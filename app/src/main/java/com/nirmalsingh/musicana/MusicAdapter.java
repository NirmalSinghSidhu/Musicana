package com.nirmalsingh.musicana;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {
    ArrayList<AudioModel> songList;
    Context context;

    public MusicAdapter(ArrayList<AudioModel> songList, Context context) {
        this.songList = songList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.item_view,parent,false);
       return new MusicAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
     AudioModel songData=songList.get(position);
     holder.titleTextView.setText(songData.getTitle());

        if(MediaPlayerSongs.currentIndex==position){
            holder.titleTextView.setTextColor(Color.parseColor("#D5678C"));
        }else{
            holder.titleTextView.setTextColor(Color.parseColor("#7477A0"));
        }

     holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             //navigate to another activity

             MediaPlayerSongs.getInstance().reset();
             MediaPlayerSongs.currentIndex=position;
             Intent intent= new Intent(context,MusicPlayerActivity.class);
             intent.putExtra("LIST",songList);
             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             context.startActivity(intent);


         }
     });
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView;
        ImageView iconImageView;

        public ViewHolder( View itemView) {
            super(itemView);
            titleTextView=itemView.findViewById(R.id.music_title_text);
            iconImageView=itemView.findViewById(R.id.icon_view);
        }
    }
}
