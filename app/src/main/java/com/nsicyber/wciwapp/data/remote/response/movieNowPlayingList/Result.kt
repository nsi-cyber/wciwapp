package com.nsicyber.wciwapp.data.remote.response.movieNowPlayingList

import com.nsicyber.wciwapp.domain.model.CardViewData

data class Result(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

fun Result?.toCardViewData(): CardViewData {
    return CardViewData(
        id = this?.id,
        media_type = "movie",
        title = this?.title,
        poster_path = this?.poster_path,
        vote_average = this?.vote_average,
        date = this?.release_date
    )
}