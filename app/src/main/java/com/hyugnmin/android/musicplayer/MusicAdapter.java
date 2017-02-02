package com.hyugnmin.android.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by besto on 2017-02-01.
 */

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.Holder> {

    ArrayList<Music> datas;
    Context context;
    Intent intent = null;

    public MusicAdapter (ArrayList<Music> datas, Context context) {
        this.datas = datas;
        this.context = context;
        intent = new Intent(context, PlayerActivity.class);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        Holder holder  = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final Music music = datas.get(position);
        holder.textTitle.setText(music.title);
        holder.textArtist.setText(music.artist);

        holder.position = position;
        //holder.imageView2.setImageURI(music.album_image);
                                  //로드 대상 uri               //세팅할 이미지뷰
        Glide.with(context).load(music.album_image).into(holder.imageView2);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView textTitle, textArtist;
        ImageView imageView2;
        CardView cardView;
        int position;

       // int position;

        public Holder(View itemView) {
            super(itemView);

            textTitle = (TextView)itemView.findViewById(R.id.textTitle);
            textArtist = (TextView) itemView.findViewById(R.id.textArtist);
            imageView2 = (ImageView) itemView.findViewById(R.id.imageView2);
            cardView = (CardView) itemView.findViewById(R.id.cardView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });

        }
    }
}
