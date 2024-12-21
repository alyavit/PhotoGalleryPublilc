package com.example.photogallery.service

import com.example.photogallery.model.Curated
import retrofit2.http.GET
import retrofit2.http.Headers

// Retrofit implemented interface for remote REST-API
interface PhotoGalleryApiService {

    // Provide getting photos from remote REST-API with Auth-token (I know, it is bad solution)
    @Headers("Authorization: SWz6yRNgL8HrIrZQ1DFFSeiE8OOUffGYUzCjrr1c4TfR35gve6xH1DkE")
    @GET("curated")
    suspend fun getPhotos(): Curated
}