package com.logotet.totochecker.data.model

import kotlinx.serialization.Serializable

@Serializable
data class NumbersDto(
    val numbers: List<String>
)