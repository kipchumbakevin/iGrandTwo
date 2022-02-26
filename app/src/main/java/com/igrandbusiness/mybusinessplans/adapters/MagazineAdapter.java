package com.igrandbusiness.mybusinessplans.adapters;

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
import com.igrandbusiness.mybusinessplans.R;
import com.igrandbusiness.mybusinessplans.ViewMagazine;
import com.igrandbusiness.mybusinessplans.ViewPDF;
import com.igrandbusiness.mybusinessplans.models.ReceiveData;
import com.igrandbusiness.mybusinessplans.utils.Constants;

import java.util.ArrayList;

public class MagazineAdapter extends RecyclerView.Adapter<MagazineAdapter.MagazineView> {
    private final Context mContext;
    private final ArrayList<ReceiveData> mContentArray;
    private final LayoutInflater mLayoutInflator;
    public MagazineAdapter(Context context, ArrayList<ReceiveData>magazines){
        mContext = context;
        mContentArray = magazines;
        mLayoutInflator = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public MagazineView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.list_layouts,parent,false);
        return new MagazineView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MagazineView holder, int position) {
        ReceiveData receiveData = mContentArray.get(position);
        holder.uri = receiveData.getUrl();
        holder.titl = receiveData.getTitle();
        Glide.with(mContext)
                .load(Constants.BASE_URL+"public/magazine/"+receiveData.getImageurl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mContentArray.size();
    }

    public class MagazineView extends RecyclerView.ViewHolder {
        String uri,titl;
        ImageView imageView;
        public MagazineView(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ViewPDF.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("URI",uri);
                    intent.putExtra("TITLE",titl);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
