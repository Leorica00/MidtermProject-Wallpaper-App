package com.example.shualeduri.presentation.screen.wallpapers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.shualeduri.R
import com.example.shualeduri.databinding.ImageRecyclerItemBinding
import com.example.shualeduri.presentation.model.Image
import com.example.shualeduri.presentation.screen.wallpapers.listener.OnWallpaperClickListener

class WallpapersRecyclerViewAdapter :
    PagingDataAdapter<Image, WallpapersRecyclerViewAdapter.ImageViewHolder>(
        WallpaperItemDiffCallback
    ) {

    private var listener: OnWallpaperClickListener? = null

    fun setOnWallpaperClickListener(listener: OnWallpaperClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ImageRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind()
    }

    inner class ImageViewHolder(private val binding: ImageRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val image = getItem(bindingAdapterPosition)
            image?.let { img ->
                Glide.with(itemView.context).load(img.webformatURL).apply(
                        RequestOptions().placeholder(R.drawable.ic_loader).error(R.drawable.ic_reject)
                    ).transition(DrawableTransitionOptions.withCrossFade())
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding.imageViewItem)
                binding.root.setOnClickListener {
                    listener?.onWallpaperClick(image = img)
                }
            }
        }
    }

    companion object {
        private val WallpaperItemDiffCallback = object : DiffUtil.ItemCallback<Image>() {

            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem == newItem
            }
        }
    }
}