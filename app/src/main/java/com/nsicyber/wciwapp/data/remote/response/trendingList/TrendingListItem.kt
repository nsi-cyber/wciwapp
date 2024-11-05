package com.nsicyber.wciwapp.data.remote.response.trendingList

import com.nsicyber.wciwapp.domain.model.CardViewData


data class TrendingListItem(


    val id: Int,

    val media_type: String?,

    val name: String? = null,

    val title: String? = null,


    val overview: String,

    val popularity: Double,

    val poster_path: String,

    val vote_average: Double,

    val vote_count: Int,

    val release_date: String? = null,

    val first_air_date: String? = null,


    )

fun TrendingListItem.toCardViewData(): CardViewData {
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
