package com.attestation_finale_work.domain

data class CommentThing(
    override val id: String,
    val author: String,
    val body: String,
    val isLikedByUser: Boolean?,
    val score: String,
    val prefixedId: String,
    val saved: Boolean
) : DiscreteReddit()