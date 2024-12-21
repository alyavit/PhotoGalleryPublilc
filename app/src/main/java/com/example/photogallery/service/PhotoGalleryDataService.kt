package com.example.photogallery.service

import com.example.photogallery.data.PhotoDao
import com.example.photogallery.data.PhotoEntity
import com.example.photogallery.model.Photo

// Provide data managing for app
class PhotoGalleryDataService(
    // Retrofit implemented interface for remote REST-API
    private val photoGalleryApiService: PhotoGalleryApiService,
    // Room local data
    private val photoDao: PhotoDao
) {
    // Get photos from remote REST-API
    suspend fun fetchPhotos(): List<Photo>{
        return photoGalleryApiService.getPhotos().photos
    }

    suspend fun getFavoritePhoto(): List<PhotoEntity>{
        return photoDao.getAllPhotos()
    }

    suspend fun saveFavoritePhotos(photos: List<PhotoEntity>){
        photoDao.insertPhotos(photos)
    }

    suspend fun removeFavoritePhotos(photos: List<PhotoEntity>){
        photoDao.deletePhotos(photos)
    }
}