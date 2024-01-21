package com.example.shualeduri.presentation.screen.categories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shualeduri.presentation.model.category.Category
import com.example.shualeduri.databinding.CategoryRecyclerItemBinding
import com.example.shualeduri.presentation.extension.loadImage

class CategoriesRecyclerViewAdapter: ListAdapter<Category, CategoriesRecyclerViewAdapter.CategoryViewHolder>(
    CategoriesItemDiffCallback
) {

    var onCategoryClickListener: ((category: Category) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind()
    }

    inner class CategoryViewHolder(private val binding: CategoryRecyclerItemBinding): ViewHolder(binding.root){
        fun bind() {
            val category = currentList[bindingAdapterPosition]
            with(binding) {
                tvCategory.text = currentList[bindingAdapterPosition].categories.category.replaceFirstChar { it.uppercase() }
                imageViewCategoryBackground.loadImage(category.categories.imgUrl)
                root.setOnClickListener {
                    onCategoryClickListener?.invoke(category)
                }
            }

        }
    }

    companion object {
        private val CategoriesItemDiffCallback = object : DiffUtil.ItemCallback<Category>() {

            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem.categories.name == newItem.categories.name
            }

            override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem == newItem
            }
        }
    }
}