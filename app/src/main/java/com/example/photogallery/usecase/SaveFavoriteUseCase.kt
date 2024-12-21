package com.example.photogallery.usecase

import com.example.photogallery.data.PhotoEntity
import com.example.photogallery.service.PhotoGalleryDataService

class SaveFavoriteUseCase(
    // Provide data managing for app
    private val photoGalleryDataService: PhotoGalleryDataService
) {
    // Processing data from remote REST-API
    suspend fun invoke(photos: List<PhotoEntity>){
        return photoGalleryDataService.saveFavoritePhotos(photos)
    }
}