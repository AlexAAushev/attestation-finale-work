package com.attestation_finale_work.domain

open class PostThing(
    override val id: String,
    open val author: String,
    open val date: String,
    open val title: String,
    open val subreddit: String,
    open val score: String,
    open val numComments: Int,
    open val saved: Boolean,
    open val selfUrl: String,
    open var isLikedByUser: IsLikedByUser
) : DiscreteReddit() {
    
    
    override fun equals(other: Any?): Boolean {
        return other is PostThing && id == other.id
    }
    
    fun toImagePostEntity(imageUtl: String): ImagePostEntity {
        return ImagePostEntity(
            id = id,
            author = author,
            date = date,
            title = title,
            subreddit = subreddit,
            score = score,
            numComments = numComments,
            saved = saved,
            selfUrl = selfUrl,
            isLikedByUser = isLikedByUser,
            imageUrl = imageUtl
        )
    }
    
    fun toTextPostEntity(selfText: String): TextPostEntity {
        return TextPostEntity(
            id = id,
            author = author,
            date = date,
            title = title,
            subreddit = subreddit,
            score = score,
            numComments = numComments,
            saved = saved,
            selfUrl = selfUrl,
            isLikedByUser = isLikedByUser,
            selfText = selfText
        )
    }
}

data class IsLikedByUser(
    val isLiked: Boolean?,
    var isLoading: Boolean = false
)

data class ImagePostEntity(
    override val id: String,
    override val author: String,
    override val date: String,
    override val title: String,
    override val subreddit: String,
    override val score: String,
    override val numComments: Int,
    override val saved: Boolean,
    override val selfUrl: String,
    override var isLikedByUser: IsLikedByUser,
    val imageUrl: String
): PostThing(id, author, date, title, subreddit, score, numComments, saved, selfUrl, isLikedByUser)

data class TextPostEntity(
    override val id: String,
    override val author: String,
    override val date: String,
    override val title: String,
    override val subreddit: String,
    override val score: String,
    override val numComments: Int,
    override val saved: Boolean,
    override val selfUrl: String,
    override var isLikedByUser: IsLikedByUser,
    val selfText: String
): PostThing(id, author, date, title, subreddit, score, numComments, saved, selfUrl, isLikedByUser)