package com.attestation_finale_work.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.attestation_finale_work.R
import com.attestation_finale_work.databinding.SubredditItemBinding
import com.attestation_finale_work.oauth_data.ApiResult
import com.attestation_finale_work.domain.IsSubscriber
import com.attestation_finale_work.domain.SubredditThing

class SubredditsPageAdapter(
    private val onItemClick: (SubredditThing) -> Unit,
    private val onSubscribeClick: (String, Boolean, Int) -> Unit
) : PagingDataAdapter<SubredditThing, SubredditViewHolder>(SubredditsDiffUtilCallback()) {

    fun updateElement(data: ApiResult<Int>) {
        data.data?.let { position ->
            snapshot()[position]?.let { subreddit ->
                subreddit.userIsSubscriber = when (data) {
                    is ApiResult.Loading -> IsSubscriber(
                        subreddit.userIsSubscriber.isSubscribed,
                        true
                    )
                    else -> IsSubscriber(!subreddit.userIsSubscriber.isSubscribed, false)
                }
            }
            notifyItemChanged(position)
        }
    }

    override fun onBindViewHolder(holder: SubredditViewHolder, position: Int) {
        val item = getItem(position) ?: return
        with(holder.binding) {
            name.text = item.displayNamePrefixed
            if (item.userIsSubscriber.isLoading) {
                followButton.apply {
                    isEnabled = false
                    setColorFilter(context.getColor(R.color.dark_grey))
                }
            } else {
                if (item.userIsSubscriber.isSubscribed) {
                    followButton.apply {
                        setImageResource(R.drawable.ic_subsubscribed)
                        setColorFilter(context.getColor(R.color.red))
                    }
                } else {
                    followButton.apply {
                        setImageResource(R.drawable.ic_vector_subscribe)
                        setColorFilter(context.getColor(R.color.blue))
                    }
                }
            }

            followButton.setOnClickListener {
                onSubscribeClick(item.displayName, item.userIsSubscriber.isSubscribed, position)
            }
            root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubredditViewHolder {
        return SubredditViewHolder(
            SubredditItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
}

class SubredditViewHolder(val binding: SubredditItemBinding) : RecyclerView.ViewHolder(binding.root)

class SubredditsDiffUtilCallback : DiffUtil.ItemCallback<SubredditThing>() {
    override fun areItemsTheSame(oldItem: SubredditThing, newItem: SubredditThing): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SubredditThing, newItem: SubredditThing): Boolean {
        return oldItem == newItem
    }
}