@file:OptIn(ExperimentalSerializationApi::class)

package com.logotet.totochecker.data.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

typealias WinningNumbersDto = Map<GameTypeDto, NumbersDto>

@Serializable
@JvmInline
value class NumbersDto(
    val numbers: List<String>
)

@Serializable
enum class GameTypeDto {
    @JsonNames("6x49")
    SIX_49,

    @JsonNames("6x42")
    SIX_42,

    @JsonNames("5x35xA")
    FIVE_35_FIRST_PICK,

    @JsonNames("5x35xB")
    FIVE_35_SECOND_PICK
}