package com.igrandbusiness.mybusinessplans;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.igrandbusiness.mybusinessplans.adapters.MagazineAdapter;
import com.igrandbusiness.mybusinessplans.models.Feature;
import com.igrandbusiness.mybusinessplans.models.LatestNewsModel;
import com.igrandbusiness.mybusinessplans.models.ReceiveData;
import com.igrandbusiness.mybusinessplans.networking.RetrofitClient;
import com.igrandbusiness.mybusinessplans.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MagazinesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MagazinesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    CardView progress,reload,network_error_card;
    TextView network_error,novideos,latestNewsTitle,learnMore;
    ImageView latestNewsImage;
    View act;
    String latestUrl;
    RecyclerView recyclerView;
    MagazineAdapter magazineAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<ReceiveData> mContentArrayList = new ArrayList<>();

    public MagazinesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MagazinesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MagazinesFragment newInstance(String param1, String param2) {
        MagazinesFragment fragment = new MagazinesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_magazines, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        reload = view.findViewById(R.id.reload);
        network_error = view.findViewById(R.id.network_error);
        network_error_card = view.findViewById(R.id.network_error_card);
        progress = view.findViewById(R.id.progress);
        act = view;
        latestNewsTitle = view.findViewById(R.id.latest_news_title);
        learnMore = view.findViewById(R.id.learn_more);
        latestNewsImage = view.findViewById(R.id.latest_news_image);
        novideos = view.findViewById(R.id.novideos);
        magazineAdapter = new MagazineAdapter(getActivity(),mContentArrayList);
        recyclerView.setAdapter(magazineAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),getActivity().getResources().getInteger(R.integer.product_grid_span)));
        fetchMagazines();
        fetchLatest();
        reload.setOnClickListener(view1 -> {
            fetchMagazines();
            fetchLatest();

        });
        learnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ViewPDF.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("URI",latestUrl);
                intent.putExtra("TITLE",latestNewsTitle.getText().toString());
                startActivity(intent);
            }
        });
        return view;
    }
    private void fetchLatest() {
        progress.setVisibility(View.VISIBLE);
        network_error_card.setVisibility(View.GONE);
        mContentArrayList.clear();
//        learnMore.setVisibility(View.GONE);
//        latestNewsTitle.setVisibility(View.GONE);
        //latestNewsImage.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.igrandlogo));
        Call<Feature> call = RetrofitClient.getInstance(getActivity())
                .getApiConnector()
                .getLatestFeature();
        call.enqueue(new Callback<Feature>() {
            @Override
            public void onResponse(Call<Feature> call, Response<Feature> response) {
                progress.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    //latestNewsTitle.setText(response.body().getTitle());
                    Glide.with(act).load(Constants.BASE_URL+"public/features/"+response.body().getMagazine())
                            .placeholder(R.drawable.placeholder)
                            .into(latestNewsImage);
//                    learnMore.setVisibility(View.VISIBLE);
//                    latestNewsTitle.setVisibility(View.VISIBLE);
//                    latestUrl = response.body().getUrl();

                } else {
                    network_error.setText("Oh no!\nA server error has occurred.Please retry.");
                    network_error_card.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Call<Feature> call, Throwable t) {
                progress.setVisibility(View.GONE);
                network_error.setText("Oh no!\nA network error has occurred.Ensure you are connected then retry.");
                network_error_card.setVisibility(View.VISIBLE);
            }
        });
    }
    private void fetchMagazines() {
        progress.setVisibility(View.VISIBLE);
        network_error_card.setVisibility(View.GONE);
        mContentArrayList.clear();
        Call<List<ReceiveData>> call = RetrofitClient.getInstance(getActivity())
                .getApiConnector()
                .getMags();
        call.enqueue(new Callback<List<ReceiveData>>() {
            @Override
            public void onResponse(Call<List<ReceiveData>> call, Response<List<ReceiveData>> response) {
                progress.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    if (response.body().size()>0){
                        mContentArrayList.addAll(response.body());
                        magazineAdapter.notifyDataSetChanged();
                    }else {
                        novideos.setVisibility(View.VISIBLE);
                    }
                } else {
                    network_error.setText("Oh no!\nA server error has occurred.Please retry.");
                    network_error_card.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<ReceiveData>> call, Throwable t) {
                progress.setVisibility(View.GONE);
                network_error.setText("Oh no!\nA network error has occurred.Ensure you are connected then retry.");
                network_error_card.setVisibility(View.VISIBLE);
            }
        });
    }
}