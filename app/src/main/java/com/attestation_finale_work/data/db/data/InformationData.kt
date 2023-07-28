package com.attestation_finale_work.data.db.data

import com.attestation_finale_work.data.db.dto.*
import com.attestation_finale_work.data.db.entity.EntityData
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommentsData(
    @Json(name = "kind")
    override val kind: String,
    @Json(name = "data")
    override val data: CommentDto
) : EntityData

@JsonClass(generateAdapter = true)
data class MoreData(
    @Json(name = "kind")
    override val kind: String,
    @Json(name = "data")
    override val data: MoreDto
) : EntityData

@JsonClass(generateAdapter = true)
data class PostsData(
    @Json(name = "kind")
    override val kind: String,
    @Json(name = "data")
    override val data: PostDto
) : EntityData

@JsonClass(generateAdapter = true)
data class SubredditsData(
    @Json(name = "kind")
    override val kind: String,
    @Json(name = "data")
    override val data: SubredditDto
) : EntityData

@JsonClass(generateAdapter = true)
data class UserData(
    @Json(name = "kind")
    override val kind: String,
    @Json(name = "data")
    override val data: UserDto
) : EntityData

