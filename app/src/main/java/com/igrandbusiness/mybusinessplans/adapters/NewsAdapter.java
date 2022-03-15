package com.igrandbusiness.mybusinessplans.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.igrandbusiness.mybusinessplans.NewsActivity;
import com.igrandbusiness.mybusinessplans.R;
import com.igrandbusiness.mybusinessplans.ReadEditorial;
import com.igrandbusiness.mybusinessplans.models.NewsModel;
import com.igrandbusiness.mybusinessplans.utils.Constants;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private final ArrayList<NewsModel> mArrayList;
    private final Context mContext;
    private final LayoutInflater mInflator;

    public NewsAdapter(Context context, ArrayList<NewsModel>arrayList){
        mArrayList = arrayList;
        mContext = context;
        mInflator = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflator.inflate(R.layout.news_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsModel newsModel = mArrayList.get(position);
        holder.title.setText(newsModel.getTitle());
        holder.host.setText(newsModel.getHost());
        holder.date.setText(newsModel.getDate());
        holder.url = newsModel.getUrl();
        holder.imageUrl = Constants.BASE_URL + "public/editorials/"+newsModel.getImageurl();
        holder.c = newsModel.getCategory();
        holder.details_id = Integer.toString(newsModel.getDetailsId());
        Glide.with(mContext).load(holder.imageUrl)
                .placeholder(R.drawable.progress_glide)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,host,date;
        String url,details_id,c,imageUrl;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            host = itemView.findViewById(R.id.host);
            image = itemView.findViewById(R.id.image_view);
            date = itemView.findViewById(R.id.date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ReadEditorial.class);
                    intent.putExtra("ID",details_id);
                    intent.putExtra("IMAGE",imageUrl);
                    intent.putExtra("CAT",c);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
