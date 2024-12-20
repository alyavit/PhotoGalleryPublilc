package com.example.photogallery.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photogallery.data.PhotoEntity
import com.example.photogallery.model.Photo
import com.example.photogallery.usecase.GetFavoritePhotoUseCase
import com.example.photogallery.usecase.GetRemotePhotoGalleryUseCase
import com.example.photogallery.usecase.RemoveFavoritePhotoUseCase
import com.example.photogallery.usecase.SaveFavoriteUseCase
import kotlinx.coroutines.launch

// View Model for Main Activity
class MainViewModel(
    // Use case for Getting photos and metadata from remote REST-API
    private val getRemotePhotoGalleryUseCase: GetRemotePhotoGalleryUseCase,
    private val getFavoritePhotoUseCase: GetFavoritePhotoUseCase,
    private val saveFavoriteUseCase: SaveFavoriteUseCase,
    private val removeFavoritePhotoUseCase: RemoveFavoritePhotoUseCase

) : ViewModel() {

    // List of photos
    private val _photos = MutableLiveData<MutableList<Photo>>()
    val photos: LiveData<MutableList<Photo>> get() = _photos

    // List of photos
    private val _favoritePhotos = MutableLiveData<MutableList<PhotoEntity>>()
    val favoritePhotos: LiveData<MutableList<PhotoEntity>> get() = _favoritePhotos

    fun loadPhotos() {
        loadFavoritePhotos()
        viewModelScope.launch {
            _photos.value = getRemotePhotoGalleryUseCase.invoke().map {
                val isFavorite = _favoritePhotos.value?.any { favoritePhoto -> favoritePhoto.id == it.id } ?: false
                it.favorite = isFavorite
                return@map it
            }.toMutableList()
        }
    }
    private fun loadFavoritePhotos() {
        viewModelScope.launch {
            _favoritePhotos.value = getFavoritePhotoUseCase.invoke().toMutableList()
        }
    }

    private fun saveFavoritePhotos(photos: List<Photo>) {
        viewModelScope.launch {
            saveFavoriteUseCase.invoke(photos.map { photo ->
                val entity = PhotoEntity(
                    id = photo.id!!,
                    author = photo.photographer,
                    url = photo.src!!.original,
                    favorite = photo.favorite)
                _favoritePhotos.value?.add(entity)
                entity
            })
        }
    }

    private fun removeFavoritePhotos(photos: List<Photo>) {
        viewModelScope.launch {
            val a = _favoritePhotos.value?.filter { entity ->
                photos.any { movie -> movie.id == entity.id }
            }
            _favoritePhotos.value?.removeAll(a!!)
            removeFavoritePhotoUseCase.invoke(a!!)
        }
    }

    fun onFavouriteClicked(photo: Photo) {
        viewModelScope.launch {
            photo.favorite = !photo.favorite
            if (photo.favorite) {
                saveFavoritePhotos(listOf(photo))
            } else {
                removeFavoritePhotos(listOf(photo))
            }
        }
    }
}