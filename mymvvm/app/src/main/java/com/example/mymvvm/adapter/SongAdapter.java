package com.example.mymvvm.adapter;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymvvm.Interface.ISongClick;
import com.example.mymvvm.R;
import com.example.mymvvm.model.Song;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder>{
    List<Song> songs;
    ISongClick iSongClick;
    public SongAdapter(ISongClick listener){
        iSongClick = listener;
    }
    public void setData(List<Song> data){
        this.songs = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
                Song song = songs.get(position);
                holder.tvName.setText(song.getTitle());
                holder.tvSinger.setText(song.getSinger());
                holder.imgSong.setImageResource(R.drawable.songlogo);
    }

    @Override
    public int getItemCount() {
        if(songs!=null){
            return songs.size();
        }
        return 0;
    }

    public class SongViewHolder extends RecyclerView.ViewHolder{
        ImageView imgSong;
        TextView tvName, tvSinger;
        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSong = itemView.findViewById(R.id.img_song);
            tvName =itemView.findViewById(R.id.title_song);
            tvSinger = itemView.findViewById(R.id.singer_song);
        }
    }
}
