package com.attestation_finale_work.data.db.dto

import com.attestation_finale_work.data.db.data.MapData
import com.attestation_finale_work.domain.UserEntity
import com.attestation_finale_work.domain.toDateTimeFormat
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDto(
    @Json(name = "created")
    val created: Long,
    @Json(name = "icon_img")
    val iconImg: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "total_karma")
    val totalKarma: Int,
) : MapData {

    override fun map(): UserEntity {
        return UserEntity(
            id = id,
            name = name,
            karma = totalKarma,
            created = (created * 1000).toDateTimeFormat(),
            iconImg = iconImg
        )
    }
}