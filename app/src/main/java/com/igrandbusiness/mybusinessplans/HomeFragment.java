package com.igrandbusiness.mybusinessplans;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.igrandbusiness.mybusinessplans.adapters.ContentAdapter;
import com.igrandbusiness.mybusinessplans.adapters.NewsAdapter;
import com.igrandbusiness.mybusinessplans.models.CategoriesModel;
import com.igrandbusiness.mybusinessplans.models.LatestNewsModel;
import com.igrandbusiness.mybusinessplans.models.NewsModel;
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
    TextView novideos,network_error,latestNewsCategory,latestNewsTitle,learnMore;
    String category;
    ImageView latestNewsImage,search;
    String latestUrl;
    private final List<CategoriesModel> categories = new ArrayList<>();
    TabLayout tabLayout;
    View act;
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
        latestNewsCategory = view.findViewById(R.id.latest_news_category);
        latestNewsTitle = view.findViewById(R.id.latest_news_title);
        learnMore = view.findViewById(R.id.learn_more);
        search = view.findViewById(R.id.search);
        latestNewsImage = view.findViewById(R.id.latest_news_image);
        tabLayout = view.findViewById(R.id.tablayout);
        newsAdapter = new NewsAdapter(getActivity(),mNewsArray);
        recyclerView.setAdapter(newsAdapter);
        act = view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getCategoryList();
        fetchLatestNews();
        reload.setOnClickListener(view1 -> {
            getCategoryList();
            fetchLatestNews();
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),SearchActivity.class));
            }
        });
        learnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(latestUrl);
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(latestUrl)));
                }
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                category = tabLayout.getTabAt(tab.getPosition()).getText().toString();
                mNewsArray.clear();
                newsAdapter.notifyDataSetChanged();
                fetchNews();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }
    private void getCategoryList() {
        categories.clear();
        tabLayout.removeAllTabs();
        mNewsArray.clear();
        newsAdapter.notifyDataSetChanged();
        progress.setVisibility(View.VISIBLE);
        network_error_card.setVisibility(View.GONE);
        Call<List<CategoriesModel>> call = RetrofitClient.getInstance(getActivity())
                .getApiConnector()
                .getCategories();
        call.enqueue(new Callback<List<CategoriesModel>>() {
            @Override
            public void onResponse(Call<List<CategoriesModel>> call, Response<List<CategoriesModel>> response) {
                progress.setVisibility(View.GONE);
                if (response.code() == 200) {
                    categories.addAll(response.body());
                    if (categories.size()>0) {
                        filltabs(tabLayout);
                    }else {
                        novideos.setVisibility(View.VISIBLE);
                    }
                    //fetchGroup();
                } else {
                    progress.setVisibility(View.GONE);
                    network_error.setText("Oh no!\nA server error has occurred.Please retry.");
                    network_error_card.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Call<List<CategoriesModel>> call, Throwable t) {
                progress.setVisibility(View.GONE);
                network_error.setText("Oh no!\nA network error has occurred.Ensure you are connected then retry.");
                network_error_card.setVisibility(View.VISIBLE);
            }
        });
    }
    private void filltabs(TabLayout tabLayout) {
        if (!categories.isEmpty()) {
            for (int index = 0; index < categories.size(); index++) {
                String fragmentname = categories.get(index).getCategory();
                tabLayout.addTab(tabLayout.newTab().setText(fragmentname));
            }
        }
    }
    private void fetchLatestNews() {
        progress.setVisibility(View.VISIBLE);
        network_error_card.setVisibility(View.GONE);
        mNewsArray.clear();
        learnMore.setVisibility(View.GONE);
        latestNewsTitle.setVisibility(View.GONE);
        //latestNewsImage.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.igrandlogo));
        latestNewsCategory.setVisibility(View.GONE);
        Call<LatestNewsModel> call = RetrofitClient.getInstance(getActivity())
                .getApiConnector()
                .getLatestNews();
        call.enqueue(new Callback<LatestNewsModel>() {
            @Override
            public void onResponse(Call<LatestNewsModel> call, Response<LatestNewsModel> response) {
                progress.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    latestNewsCategory.setText(response.body().getCategory().getCategory());
                    latestNewsTitle.setText(response.body().getTitle());
                    Glide.with(act).load(Constants.BASE_URL+"public/editorials/"+response.body().getImageurl())
                            .into(latestNewsImage);
                    learnMore.setVisibility(View.VISIBLE);
                    latestNewsTitle.setVisibility(View.VISIBLE);
                    latestNewsCategory.setVisibility(View.VISIBLE);
                    latestUrl = response.body().getLink();

                } else {
                    network_error.setText("Oh no!\nA server error has occurred.Please retry.");
                    network_error_card.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Call<LatestNewsModel> call, Throwable t) {
                progress.setVisibility(View.GONE);
                network_error.setText("Oh no!\nA network error has occurred.Ensure you are connected then retry.");
                network_error_card.setVisibility(View.VISIBLE);
            }
        });
    }
    private void fetchNews() {
        progress.setVisibility(View.VISIBLE);
        network_error_card.setVisibility(View.GONE);
        mNewsArray.clear();
        Call<List<NewsModel>> call = RetrofitClient.getInstance(getActivity())
                .getApiConnector()
                .getNews(category);
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
                    network_error.setText("Oh no!\nA server error has occurred.Please retry.");
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