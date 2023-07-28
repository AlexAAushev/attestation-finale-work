package com.attestation_finale_work.domain

data class UserEntity(
    override val id: String,
    val name: String,
    val karma: Int,
    val created: String,
    val iconImg: String
): DiscreteReddit()