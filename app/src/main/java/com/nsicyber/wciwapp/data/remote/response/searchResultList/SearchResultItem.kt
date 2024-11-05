package com.nsicyber.wciwapp.data.remote.response.searchResultList

import com.nsicyber.wciwapp.domain.model.CardViewData


data class SearchResultItem(
    val first_air_date: String? = null,
    val id: Int?,
    val media_type: String? = null,
    val name: String? = null,
    val original_name: String? = null,
    val original_title: String? = null,
    val poster_path: String? = null,
    val release_date: String? = null,
    val vote_average: Double? = null,
    val title: String? = null,

    )

fun SearchResultItem.toCardViewData(): CardViewData {
    return CardViewData(
        id = id,
        media_type = media_type,
        title = when (media_type) {
            "movie" -> title
            "tv" -> name
            else -> null
        },
        poster_path = poster_path,
        vote_average = vote_average,
        date = when (media_type) {
            "movie" -> release_date
            "tv" -> first_air_date
            else -> null
        }
    )
}
