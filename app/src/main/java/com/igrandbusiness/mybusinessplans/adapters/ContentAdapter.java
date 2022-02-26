package com.igrandbusiness.mybusinessplans.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.igrandbusiness.mybusinessplans.AudioPlayer;
import com.igrandbusiness.mybusinessplans.R;
import com.igrandbusiness.mybusinessplans.models.ReceiveData;
import com.igrandbusiness.mybusinessplans.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder> {
    private final Context mContext;
    private final ArrayList<ReceiveData> mContentArray;
    private final LayoutInflater mLayoutInflator;

    public ContentAdapter(Context context, ArrayList<ReceiveData>contentArray){
        mContext = context;
        mContentArray = contentArray;
        mLayoutInflator = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public ContentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.audio_layout,parent,false);
        return new ContentHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ContentHolder holder, int position) {
        ReceiveData receiveData = mContentArray.get(position);
        holder.title.setText(receiveData.getTitle());
        holder.uri = receiveData.getUrl();
        holder.titl = receiveData.getTitle();
        Glide.with(mContext)
                .load(Constants.BASE_URL+"public/podcast/"+receiveData.getImageurl())
                .into(holder.image);
        holder.imageurl = Constants.BASE_URL+"public/podcast/"+receiveData.getImageurl();
    }
    @Override
    public int getItemCount() {
        return mContentArray.size();
    }

    public class ContentHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        String uri,titl,imageurl;
        public ContentHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, AudioPlayer.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("URI",uri);
                    intent.putExtra("IMAGEURL",imageurl);
                    intent.putExtra("TITLE",titl);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
