package com.attestation_finale_work.data.db.dto

import com.attestation_finale_work.data.db.data.MapData
import com.attestation_finale_work.domain.DiscreteReddit
import com.attestation_finale_work.domain.CommentThing
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommentDto(
    @Json(name = "author")
    val author: String,
    @Json(name = "body")
    val body: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "likes")
    val likes: Boolean?,
    @Json(name = "name")
    val name: String,
    @Json(name = "saved")
    val saved: Boolean,
    @Json(name = "score")
    val score: Int
) : MapData {
    override fun map(): DiscreteReddit {
        return CommentThing(
            id = id,
            author = author,
            body = body,
            isLikedByUser = likes,
            score = when (score) {
                in 0..999 -> score.toString()
                in 1_000..999_999 -> "${score / 1_000}K"
                else -> "${score / 1_000_000}M"
            },
            prefixedId = name,
            saved = saved,
        )
    }
}