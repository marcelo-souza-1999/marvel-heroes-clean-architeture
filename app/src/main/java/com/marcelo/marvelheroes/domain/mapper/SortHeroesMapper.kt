package com.marcelo.marvelheroes.domain.mapper

import com.marcelo.marvelheroes.utils.constants.ASCENDING
import com.marcelo.marvelheroes.utils.constants.DESCENDING
import com.marcelo.marvelheroes.utils.constants.MODIFIED
import com.marcelo.marvelheroes.utils.constants.NAME
import com.marcelo.marvelheroes.utils.constants.ORDER_BY_MODIFIED_ASCENDING
import com.marcelo.marvelheroes.utils.constants.ORDER_BY_MODIFIED_DESCENDING
import com.marcelo.marvelheroes.utils.constants.ORDER_BY_NAME_ASCENDING
import com.marcelo.marvelheroes.utils.constants.ORDER_BY_NAME_DESCENDING

object SortHeroesMapper {
    fun mapToPair(sorting: String): Pair<String, String> {
        val orderBy = when (sorting) {
            ORDER_BY_NAME_ASCENDING, ORDER_BY_NAME_DESCENDING -> NAME
            ORDER_BY_MODIFIED_ASCENDING, ORDER_BY_MODIFIED_DESCENDING -> MODIFIED
            else -> NAME
        }
        val order = when (sorting) {
            ORDER_BY_NAME_ASCENDING, ORDER_BY_MODIFIED_ASCENDING -> ASCENDING
            ORDER_BY_NAME_DESCENDING, ORDER_BY_MODIFIED_DESCENDING -> DESCENDING
            else -> ASCENDING
        }
        return orderBy to order
    }

    fun mapToString(sortingPair: Pair<String, String>): String {
        val (orderBy, order) = sortingPair
        return when (orderBy) {
            NAME -> when (order) {
                ASCENDING -> ORDER_BY_NAME_ASCENDING
                DESCENDING -> ORDER_BY_NAME_DESCENDING
                else -> ORDER_BY_NAME_ASCENDING
            }

            MODIFIED -> when (order) {
                ASCENDING -> ORDER_BY_MODIFIED_ASCENDING
                DESCENDING -> ORDER_BY_MODIFIED_DESCENDING
                else -> ORDER_BY_MODIFIED_ASCENDING
            }

            else -> ORDER_BY_NAME_ASCENDING
        }
    }
}