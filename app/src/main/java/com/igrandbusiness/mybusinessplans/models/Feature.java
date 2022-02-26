package com.igrandbusiness.mybusinessplans.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feature {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("video")
    @Expose
    private String video;
    @SerializedName("magazine")
    @Expose
    private String magazine;
    @SerializedName("podcast")
    @Expose
    private String podcast;
    @SerializedName("editorial")
    @Expose
    private String editorial;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getMagazine() {
        return magazine;
    }

    public void setMagazine(String magazine) {
        this.magazine = magazine;
    }

    public String getPodcast() {
        return podcast;
    }

    public void setPodcast(String podcast) {
        this.podcast = podcast;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
