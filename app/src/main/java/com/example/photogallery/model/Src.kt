package com.example.photogallery.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Class represent collection of photo URL's
data class Src(

    // Original size photo URL
    @SerializedName("original")
    @Expose
    val original: String?
)
