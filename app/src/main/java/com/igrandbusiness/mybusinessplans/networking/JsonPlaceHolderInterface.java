


package com.igrandbusiness.mybusinessplans.networking;

import com.igrandbusiness.mybusinessplans.models.CategoriesModel;
import com.igrandbusiness.mybusinessplans.models.NewsModel;
import com.igrandbusiness.mybusinessplans.models.ReceiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolderInterface {

    //getvideo
    @GET("api/getvid")
    Call<List<ReceiveData>> getVids();
    //getmagazine
    @GET("api/getmag")
    Call<List<ReceiveData>> getMags();
    //getpod
    @GET("api/getpod")
    Call<List<ReceiveData>> getPod();
    //getpod
    @FormUrlEncoded
    @POST("api/getnews")
    Call<List<NewsModel>> getNews(
            @Field("category")String category
    );
    @FormUrlEncoded
    @POST("api/getlatestnews")
    Call<NewsModel> getLatestNews(
            @Field("category")String category
    );
    @GET("api/getcategories")
    Call<List<CategoriesModel>>getCategories();
}
