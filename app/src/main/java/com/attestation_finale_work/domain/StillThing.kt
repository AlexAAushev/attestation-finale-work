package com.attestation_finale_work.domain

class StillThing(
    override val id: String,
    val name: String,
    val children: List<String>
) : DiscreteReddit()