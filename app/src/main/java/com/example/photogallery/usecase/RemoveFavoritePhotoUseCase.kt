package com.example.photogallery.usecase

import com.example.photogallery.data.PhotoEntity
import com.example.photogallery.service.PhotoGalleryDataService

class RemoveFavoritePhotoUseCase(
    private val photoGalleryDataService: PhotoGalleryDataService
) {
    suspend fun invoke(photos: List<PhotoEntity>){
        photoGalleryDataService.removeFavoritePhotos(photos)
    }
}