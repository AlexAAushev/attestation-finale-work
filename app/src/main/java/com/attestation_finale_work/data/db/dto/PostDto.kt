package com.attestation_finale_work.data.db.dto

import com.attestation_finale_work.data.db.data.MapData
import com.attestation_finale_work.domain.IsLikedByUser
import com.attestation_finale_work.domain.PostThing
import com.attestation_finale_work.domain.toDateTimeFormat
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostDto(
    @Json(name = "author")
    val author: String,
    @Json(name = "created")
    val created: Long,
    @Json(name = "id")
    val id: String,
    @Json(name = "likes")
    val likes: Boolean?,
    @Json(name = "num_comments")
    val numComments: Int,
    @Json(name = "saved")
    val saved: Boolean,
    @Json(name = "score")
    val score: Int,
    @Json(name = "selftext")
    val selftext: String,
    @Json(name = "subreddit_name_prefixed")
    val subredditNamePrefixed: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "url")
    val url: String,
) : MapData {

    override fun map(): PostThing {
        val post = PostThing(
            subreddit = subredditNamePrefixed,
            author = author,
            date = created.toDateTimeFormat(),
            title = title,
            score = when (score) {
                in 0..999 -> score.toString()
                in 1_000..999_999 -> "${score / 1_000}K"
                else -> "${score / 1_000_000}M"
            },
            numComments = numComments,
            saved = saved,
            selfUrl = url,
            id = id,
            isLikedByUser = IsLikedByUser(isLiked = likes)
        )
        if (url.endsWith(".jpg") || url.endsWith(".png")) return post.toImagePostEntity(url)
        if (selftext.isNotBlank()) return post.toTextPostEntity(selftext)
        return post
    }
}