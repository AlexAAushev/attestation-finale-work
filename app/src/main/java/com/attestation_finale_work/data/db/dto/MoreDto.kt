package com.attestation_finale_work.data.db.dto

import com.attestation_finale_work.data.db.data.MapData
import com.attestation_finale_work.domain.DiscreteReddit
import com.attestation_finale_work.domain.StillThing
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoreDto(
    @Json(name = "count")
    val count: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "parent_id")
    val parentId: String,
    @Json(name = "children")
    val children: List<String>
) : MapData {
    override fun map(): DiscreteReddit {
        return StillThing(
            id = id,
            name = name,
            children = children
        )
    }
}