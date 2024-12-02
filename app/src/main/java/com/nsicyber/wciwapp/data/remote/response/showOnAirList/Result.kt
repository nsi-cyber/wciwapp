package com.nsicyber.wciwapp.data.remote.response.showOnAirList

import com.nsicyber.wciwapp.domain.model.CardViewData

data class Result(
    val adult: Boolean,
    val backdrop_path: String,
    val first_air_date: String,
    val genre_ids: List<Int>,
    val id: Int,
    val name: String,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val vote_average: Double,
    val vote_count: Int
)

fun Result?.toCardViewData(): CardViewData {
    return CardViewData(
        id = this?.id,
        media_type = "movie",
        title = this?.name,
        poster_path = this?.poster_path?:"",
        vote_average = this?.vote_average,
        date = this?.first_air_date
    )
}