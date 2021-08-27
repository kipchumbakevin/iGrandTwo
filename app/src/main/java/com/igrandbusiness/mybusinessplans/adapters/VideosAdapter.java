package com.igrandbusiness.mybusinessplans.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.igrandbusiness.mybusinessplans.R;
import com.igrandbusiness.mybusinessplans.VideoPlayer;
import com.igrandbusiness.mybusinessplans.models.ReceiveData;

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
    }
    @Override
    public int getItemCount() {
        return mContentArray.size();
    }

    public class VideoHolder extends RecyclerView.ViewHolder {
        TextView title;
        String uri;
        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, VideoPlayer.class);
                    intent.putExtra("VIDEO",uri);
                    mContext.startActivity(intent);
                    ((Activity)mContext).finish();
                    //MKPlayerActivity.configPlayer((Activity) mContext).play(uri);

                }
            });
        }
    }
}
