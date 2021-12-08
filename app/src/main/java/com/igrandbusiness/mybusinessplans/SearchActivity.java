package com.igrandbusiness.mybusinessplans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.igrandbusiness.mybusinessplans.adapters.NewsAdapter;
import com.igrandbusiness.mybusinessplans.models.NewsModel;
import com.igrandbusiness.mybusinessplans.networking.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    SearchView searchView;
    CardView progress;
    NewsAdapter newsAdapter;
    RecyclerView recyclerView;
    private ArrayList<NewsModel> mNewsArray = new ArrayList<>();
    TextView noItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView = findViewById(R.id.search_view);
        progress = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recycler);
        noItem = findViewById(R.id.empty);
        newsAdapter = new NewsAdapter(this,mNewsArray);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    getSearchedNews(query);
                } else {
                    Toast.makeText(SearchActivity.this, "Enter a category to search", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void getSearchedNews(String query) {
        progress.setVisibility(View.VISIBLE);
        mNewsArray.clear();
        Call<List<NewsModel>> call = RetrofitClient.getInstance(SearchActivity.this)
                .getApiConnector()
                .getSearched(query);
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
                        noItem.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(SearchActivity.this, "Oh no! A server error has occurred.Please retry.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(SearchActivity.this, "Oh no!\nA network error has occurred.Ensure you are connected then retry.", Toast.LENGTH_SHORT).show();
            }

        });
    }
}