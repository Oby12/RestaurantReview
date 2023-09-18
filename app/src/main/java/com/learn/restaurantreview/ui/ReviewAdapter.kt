package com.learn.restaurantreview.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.learn.restaurantreview.data.response.CustomerReviewsItem
import com.learn.restaurantreview.databinding.ItemReviewBinding


class ReviewAdapter : ListAdapter<CustomerReviewsItem, ReviewAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }
    class MyViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: CustomerReviewsItem){
            binding.tvItem.text = "${review.review}\n- ${review.name}"
        }
    }
    companion object {
        //Hal tersebut bisa terimplementasikan karena adanya DiffUtil yang berguna untuk memeriksa apakah suatu data masih sama atau tidak.
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CustomerReviewsItem>() {
            //areItemsTheSame() digunakan untuk memeriksa id atau key yang unik. Biasanya digunakan untuk mengetahui
            // apakah ada perubahan posisi dan
            override fun areItemsTheSame(oldItem: CustomerReviewsItem, newItem: CustomerReviewsItem): Boolean {
                return oldItem == newItem
            }
            //Sedangkan areContentsTheSame() digunakan untuk memeriksa apakah konten dari dua item sama atau tidak. Fungsi ini digunakan untuk
            //mengetahui ada pembaruan konten pada suatu item.
            override fun areContentsTheSame(oldItem: CustomerReviewsItem, newItem: CustomerReviewsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}