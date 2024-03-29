package com.igrandbusiness.mybusinessplans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.igrandbusiness.mybusinessplans.models.Author;
import com.igrandbusiness.mybusinessplans.models.NewsDetailsModel;
import com.igrandbusiness.mybusinessplans.networking.RetrofitClient;
import com.igrandbusiness.mybusinessplans.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadEditorial extends AppCompatActivity {
    ScrollView scrollView;
    ImageView image;
    TextView title,date,author,category,content,network_error;
    ShimmerFrameLayout shimmerFrameLayout;
    CardView reload,network_error_card;
    int details_id,cat,author_id,contentId,contentType;
    String categ,imageURL,name;
    LinearLayoutCompat goToProfile;
    private final ArrayList<NewsDetailsModel>arrayList = new ArrayList<>();
    private final ArrayList<Author>authors = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_editorial);
        scrollView = findViewById(R.id.scroll);
        image = findViewById(R.id.image);
        title = findViewById(R.id.text_title);
        date = findViewById(R.id.date);
        author = findViewById(R.id.author);
        category = findViewById(R.id.category);
        content = findViewById(R.id.content);
        goToProfile = findViewById(R.id.go_to_profile);
        shimmerFrameLayout = findViewById(R.id.shimmer);
        network_error_card = findViewById(R.id.network_error_card);
        network_error = findViewById(R.id.network_error);
        reload = findViewById(R.id.refresh);
        cat = Integer.parseInt(getIntent().getExtras().getString("CAT"));
        imageURL = getIntent().getExtras().getString("IMAGE");
        details_id = Integer.parseInt(getIntent().getExtras().getString("ID"));
        contentId = Integer.parseInt(getIntent().getExtras().getString("ID"));
        contentType = 1;
        Constants.saveUsageStat(this,contentId,contentType);
        //details_id = 5385;
        getDetails();
        reload.setOnClickListener(view->getDetails());
        goToProfile.setOnClickListener(view-> startActivity(new Intent(this,ProfileActivity.class)
        .putExtra("NAME",name)));
    }

    private String getCat() {
        categ = "";
        if (cat == 1){
            categ = "Webinar Review";
        }
        else if (cat == 2){
            categ = "Book Review";
        }
        else if (cat == 3){
            categ = "Opinion Column";
        }
        else if (cat == 4){
            categ = "Retail Sector Review";
        }
        else if (cat == 5){
            categ = "Health Column";
        }
        else if (cat == 6){
            categ = "Advertising Feature";
        }
        else if (cat == 7){
            categ = "Business Biographies";
        }
        else if (cat == 8){
            categ = "Financial Markets";
        }
        else if (cat == 9){
            categ = "Industry Review";
        }
        else if (cat == 10){
            categ = "Economy Watch";
        }
        else if (cat == 11){
            categ = "Law Review";
        }
        return "In "+ categ;
    }
    private void getDetails() {
        showProgress();
        network_error_card.setVisibility(View.GONE);
        scrollView.setVisibility(View.GONE);
        Call<List<NewsDetailsModel>> call = RetrofitClient.getInstance(ReadEditorial.this)
                .getApiConnector()
                .get(details_id);
        call.enqueue(new Callback<List<NewsDetailsModel>>() {
            @Override
            public void onResponse(Call<List<NewsDetailsModel>> call, Response<List<NewsDetailsModel>> response) {
                hideProgress();
                if (response.isSuccessful()) {
                    scrollView.setVisibility(View.VISIBLE);
                    arrayList.addAll(response.body());
                    NewsDetailsModel pos = arrayList.get(0);
                    Glide.with(ReadEditorial.this)
                            .load(imageURL)
                            .placeholder(R.drawable.progress_glide)
                            .into(image);
                    //title date author category content
                    title.setText(pos.getPostTitle());
                    String dat = pos.getPostDate().substring(0,11);
                    date.setText("Posted on "+dat);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        content.setText(Html.fromHtml(pos.getPostContent(), Html.FROM_HTML_MODE_LEGACY));
                    } else {
                        content.setText(Html.fromHtml(pos.getPostContent()));
                    }
                    author_id = pos.getPostAuthor();
                    getAuthor(author_id);
                    category.setText(getCat());

                }
                else {
                    Constants.serverError(ReadEditorial.this,network_error_card,network_error);
                }
            }
            @Override
            public void onFailure(Call<List<NewsDetailsModel>> call, Throwable t) {
                hideProgress();
                Constants.networkError(ReadEditorial.this,network_error_card,network_error);
            }
        });
    }
    private void getAuthor(int id) {
        goToProfile.setEnabled(false);
        Call<List<Author>> call = RetrofitClient.getInstance(ReadEditorial.this)
                .getApiConnector()
                .getA(id);
        call.enqueue(new Callback<List<Author>>() {
            @Override
            public void onResponse(Call<List<Author>> call, Response<List<Author>> response) {
                if (response.isSuccessful()) {
                    authors.addAll(response.body());
                    author.setText("By "+authors.get(0).getUserNicename());
                    name = authors.get(0).getUserNicename();
                    goToProfile.setEnabled(true);
                }
            }
            @Override
            public void onFailure(Call<List<Author>> call, Throwable t) {
            }
        });
    }

    private void showProgress() {
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
    }
}