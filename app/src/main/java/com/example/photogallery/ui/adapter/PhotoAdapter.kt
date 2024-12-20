package com.example.photogallery.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photogallery.databinding.ItemPhotoBinding
import com.example.photogallery.model.Photo
import com.google.android.material.imageview.ShapeableImageView

// Represent adapter for implementation Photo Item in UI
class PhotoAdapter(private var photos: List<Photo>, private var photoListener: PhotoListener) : ListAdapter<Photo, PhotoAdapter.PhotoViewHolder>(
    PhotoDiffCallback()
) {

    // Handle for creation view holder object
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoAdapter.PhotoViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    // Handle for binding action
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photos[position])
        holder.bind(photoListener)
    }

    // Get Item Count
    override fun getItemCount(): Int = photos.size
    // Update photo from list
    fun updatePhotos(newPhotos: List<Photo>){
        photos = newPhotos
        notifyDataSetChanged()
    }

    //Companion object for property adapter define
    companion object{
        @JvmStatic
        @BindingAdapter("setFavoriteCondition")
        fun setFavouriteCondition(imageView: ShapeableImageView, isFavorite: Boolean) {
            imageView.isVisible = isFavorite
        }

        // Set image by Glide library
        @JvmStatic
        @BindingAdapter("setImage")
        fun setImage(imageView: ShapeableImageView, image: String?){
            if (image == null) {
                Glide.with(imageView.context).clear(imageView)
            }
            Glide.with(imageView.context)
                .load(image)
                .into(imageView)
        }
        
        // Set title in photo item
        @JvmStatic
        @BindingAdapter("setTitle")
        fun setTitle(textView: TextView, title: String?) {
            if (title == null) {
                textView.text = "Author unknown"
            } else {
                textView.text = "Author: $title"
            }
        }
    }
    // PhotoView holder provide data binding variables
    inner class PhotoViewHolder(
        private val binding: ItemPhotoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        // Bind function with overload Photo item type
        fun bind(photo: Photo) {
            binding.photo = photo
            binding.executePendingBindings()
        }

        // Bind function with overload Photo event listener type
        fun bind(listener: PhotoListener) {
            binding.listener = listener
            binding.executePendingBindings()
        }
    }
    // Provide methods for photo items comparing
    class PhotoDiffCallback : DiffUtil.ItemCallback<Photo>(){

        // Is photo items same
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean{
            return oldItem.id == newItem.id
        }

        // Is photo item's content same
        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            if (oldItem.id != newItem.id) return false
            if (oldItem.photographer != newItem.photographer) return false
            if (oldItem.src?.original != newItem.src?.original) return false
            return true
        }
    }
}
