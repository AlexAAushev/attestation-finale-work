package com.attestation_finale_work.data.db.dto

import com.attestation_finale_work.data.db.data.MapData
import com.attestation_finale_work.domain.formatDecimalSeparator
import com.attestation_finale_work.domain.IsSubscriber
import com.attestation_finale_work.domain.SubredditThing
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SubredditDto(
    @Json(name = "banner_background_color")
    val bannerBackgroundColor: String?,
    @Json(name = "banner_img")
    val bannerImg: String?,
    @Json(name = "display_name")
    val displayName: String?,
    @Json(name = "display_name_prefixed")
    val displayNamePrefixed: String?,
    @Json(name = "icon_img")
    val iconImg: String?,
    @Json(name = "id")
    val id: String,
    @Json(name = "mobile_banner_image")
    val mobileBannerImage: String?,
    @Json(name = "public_description")
    val publicDescription: String?,
    @Json(name = "subscribers")
    val subscribers: Int?,
    @Json(name = "url")
    val url: String?,
    @Json(name = "user_is_subscriber")
    val userIsSubscriber: Boolean?,
) : MapData {

    override fun map(): SubredditThing {
        return SubredditThing(
            id = id,
            displayNamePrefixed = displayNamePrefixed ?: "",
            displayName = displayName ?: "",
            subscribers = (subscribers ?: 0).formatDecimalSeparator(),
            userIsSubscriber = IsSubscriber(
                isSubscribed = userIsSubscriber ?: false,
                isLoading = false
            ),
            publicDescription = publicDescription ?: "",
            bannerImage = bannerImg ?: "",
            bannerColor = bannerBackgroundColor ?: "",
            subredditIcon = iconImg ?: "",
            selfUrl = "www.reddit.com$url"
        )
    }
}