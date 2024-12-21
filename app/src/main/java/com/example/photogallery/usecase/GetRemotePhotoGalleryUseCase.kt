package com.example.photogallery.usecase

import com.example.photogallery.model.Photo
import com.example.photogallery.service.PhotoGalleryDataService

// Use case for Getting photos and metadata from remote REST-API
class GetRemotePhotoGalleryUseCase(
    // Provide data managing for app
    private val photoGalleryDataService: PhotoGalleryDataService
) {
    // Processing data from remote REST-API
    suspend fun invoke(): List<Photo>{
        return photoGalleryDataService.fetchPhotos()
    }
}