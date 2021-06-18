package com.igrandbusiness.mybusinessplans;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.igrandbusiness.mybusinessplans.adapters.ContentAdapter;
import com.igrandbusiness.mybusinessplans.adapters.NewsAdapter;
import com.igrandbusiness.mybusinessplans.models.NewsModel;
import com.igrandbusiness.mybusinessplans.models.ReceiveData;
import com.igrandbusiness.mybusinessplans.networking.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    NewsAdapter newsAdapter;
    TextView novideos,network_error;
    CardView progress, reload, network_error_card;
    RecyclerView recyclerView;
    boolean active = false;
    private ArrayList<NewsModel> mNewsArray = new ArrayList<>();
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        reload = view.findViewById(R.id.reload);
        network_error = view.findViewById(R.id.network_error);
        network_error_card = view.findViewById(R.id.network_error_card);
        progress = view.findViewById(R.id.progress);
        novideos = view.findViewById(R.id.novideos);
        newsAdapter = new NewsAdapter(getActivity(),mNewsArray);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fetchNews();
        reload.setOnClickListener(view1 -> {
            fetchNews();
        });
        return view;
    }
    private void fetchNews() {
        progress.setVisibility(View.VISIBLE);
        network_error_card.setVisibility(View.GONE);
        mNewsArray.clear();
        Call<List<NewsModel>> call = RetrofitClient.getInstance(getActivity())
                .getApiConnector()
                .getNews();
        call.enqueue(new Callback<List<NewsModel>>() {
            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                progress.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    if (response.body().size()>0){
                        mNewsArray.addAll(response.body());
                        newsAdapter.notifyDataSetChanged();
                    }else {
                        novideos.setVisibility(View.VISIBLE);
                    }
                } else {
                    network_error.setText("Oh no!\nA server error has occurred.Ensure you are connected then retry.");
                    network_error_card.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable t) {
                progress.setVisibility(View.GONE);
                network_error.setText("Oh no!\nA network error has occurred.Ensure you are connected then retry.");
                network_error_card.setVisibility(View.VISIBLE);
            }

        });
    }
}