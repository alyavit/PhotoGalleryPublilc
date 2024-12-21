package com.example.photogallery.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//Class for getting results from remote REST-API
data class Curated(
    //List of photos with metadata
    @SerializedName("photos")
    @Expose
    val photos: List<Photo>
)
