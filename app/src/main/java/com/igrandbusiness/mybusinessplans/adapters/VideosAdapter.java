package com.igrandbusiness.mybusinessplans.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.igrandbusiness.mybusinessplans.R;
import com.igrandbusiness.mybusinessplans.VideoPlayer;
import com.igrandbusiness.mybusinessplans.models.ReceiveData;
import com.igrandbusiness.mybusinessplans.utils.Constants;

import java.io.File;
import java.util.ArrayList;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoHolder> {
    private final Context mContext;
    private final ArrayList<ReceiveData> mContentArray;
    private final LayoutInflater mLayoutInflator;
    public VideosAdapter(Context context, ArrayList<ReceiveData>videosArray){
        mContext = context;
        mContentArray = videosArray;
        mLayoutInflator = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.videos_layout,parent,false);
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VideoHolder holder, int position) {
        ReceiveData receiveData = mContentArray.get(position);
        holder.title.setText(receiveData.getTitle());
        holder.uri = receiveData.getUrl();
        Glide.with(mContext)
                .load(Constants.BASE_URL+"public/videos/"+receiveData.getImageurl())
                .into(holder.image);
    }
    @Override
    public int getItemCount() {
        return mContentArray.size();
    }

    public class VideoHolder extends RecyclerView.ViewHolder {
        TextView title;
        String uri;
        ImageView image;
        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, VideoPlayer.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("VIDEO",uri);
                    mContext.startActivity(intent);
                    ((Activity)mContext).finish();
                    //MKPlayerActivity.configPlayer((Activity) mContext).play(uri);

                }
            });
        }
    }
}
