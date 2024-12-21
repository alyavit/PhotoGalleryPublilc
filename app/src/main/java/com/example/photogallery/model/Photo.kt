package com.example.photogallery.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//Class represent photo and it's metadata from remote REST-API
data class Photo(
    //Remote ID
    @SerializedName("id")
    @Expose
    val id: Int?,

    //Photographer nickname
    @SerializedName("photographer")
    @Expose
    val photographer: String?,

    //Photo URL
    @SerializedName("src")
    @Expose
    val src: Src?,

    var favorite: Boolean
)
