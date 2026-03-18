package com.logotet.totochecker.domain.data

enum class LotteryType(val typeName: String, val maxNumber: Int, val maxListSize:Int) {
    SIX_49("6 / 49", 49, 6),
    SIX_42("6 / 42", 42, 6),
    FIVE_35_FIRST("5 / 35", 35, 5)
}