package com.attestation_finale_work.domain

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subreddits")
data class SubredditThing(
    @PrimaryKey
    override val id: String,
    val displayNamePrefixed: String,
    val displayName: String,
    val subscribers: String,
    @Embedded
    var userIsSubscriber: IsSubscriber,
    val publicDescription: String,
    val bannerImage: String,
    val selfUrl: String,
    val bannerColor: String,
    val subredditIcon: String
): DiscreteReddit()

data class IsSubscriber(
    val isSubscribed: Boolean,
    var isLoading: Boolean
)
