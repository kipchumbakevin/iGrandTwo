


package com.igrandbusiness.mybusinessplans.networking;

import com.igrandbusiness.mybusinessplans.models.Author;
import com.igrandbusiness.mybusinessplans.models.CategoriesModel;
import com.igrandbusiness.mybusinessplans.models.Feature;
import com.igrandbusiness.mybusinessplans.models.LatestNewsModel;
import com.igrandbusiness.mybusinessplans.models.NewsDetailsModel;
import com.igrandbusiness.mybusinessplans.models.NewsModel;
import com.igrandbusiness.mybusinessplans.models.ReceiveData;
import com.igrandbusiness.mybusinessplans.models.Result;

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
    @POST("api/createstat")
    Call<Result> saveStat(
            @Field("deviceId")String seviceId,
            @Field("contentId")int contentId,
            @Field("contentType")int contentType
    );
    @FormUrlEncoded
    @POST("api/getsearchednews")
    Call<List<NewsModel>>getSearched(
            @Field("category")String query
    );
//    @GET("api/getlatestnews")
//    Call<LatestNewsModel> getLatestNews();
    @GET("api/getlatestfeature")
    Call<Feature> getLatestFeature();
//    @GET("api/getlatestmag")
//    Call<LatestNewsModel> getLatestMag();
//    @GET("api/getlatestpod")
//    Call<LatestNewsModel> getLatestPod();
    @GET("api/getcategories")
    Call<List<CategoriesModel>>getCategories();

    @FormUrlEncoded
    @POST("api/getnewsdetails")
    Call<List<NewsDetailsModel>> get(
            @Field("id")int details_id
    );
    @FormUrlEncoded
    @POST("api/getauthor")
    Call<List<Author>> getA(
            @Field("id")int id
    );
}
