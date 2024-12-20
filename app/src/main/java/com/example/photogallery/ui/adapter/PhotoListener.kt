package com.example.photogallery.ui.adapter

import com.example.photogallery.model.Photo

// Provide handle events of photo items
interface PhotoListener {
    
    // Handle on photo click event
    fun onPhotoClicked(photo: Photo)
}
