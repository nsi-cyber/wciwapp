package com.nsicyber.wciwapp.data.mapFunctions

import com.nsicyber.wciwapp.data.remote.response.popularMoviesList.PopularMovieItem
import com.nsicyber.wciwapp.data.remote.response.searchResultList.SearchResultItem
import com.nsicyber.wciwapp.data.remote.response.showSimilarList.Result
import com.nsicyber.wciwapp.data.remote.response.topRatedShowsList.TopRatedShowsItem
import com.nsicyber.wciwapp.data.remote.response.trendingList.TrendingListItem
import com.nsicyber.wciwapp.domain.model.CardViewData
fun Result.toCardViewData(): CardViewData {
    return CardViewData(
        id = id,
        media_type = "tv",
        title = if (name == original_name) name else "$name ($original_name)",
        poster_path = poster_path ?: profile_path ?: "",
        vote_average = vote_average,
        date = this.first_air_date
    )
}

fun PopularMovieItem.toCardViewData(): CardViewData {
    return CardViewData(
        id = id,
        media_type = "movie",
        title = if (title == original_title) title else "$title ($original_title)",
        poster_path = poster_path ?: "",
        vote_average = vote_average,
        date = release_date
    )
}

fun SearchResultItem.toCardViewData(): CardViewData {
    return CardViewData(
        id = id,
        media_type = media_type,
        title = when (media_type) {
            "movie" -> if (title == original_title) title else "$title ($original_title)"
            "tv" -> if (name == original_name) name else "$name ($original_name)"
            "person" -> name
            else -> ""
        },
        poster_path = poster_path ?: profile_path ?: "",
        vote_average = vote_average,
        date = when (media_type) {
            "movie" -> release_date
            "tv" -> first_air_date
            else -> ""
        }
    )
}

fun com.nsicyber.wciwapp.data.remote.response.showOnAirList.Result?.toCardViewData(): CardViewData {
    return CardViewData(
        id = this?.id,
        media_type = "movie",
        title = if (this?.name == this?.original_name) this?.name else "${this?.name} (${this?.original_name})",
        poster_path = this?.poster_path ?: "",
        vote_average = this?.vote_average,
        date = this?.first_air_date
    )
}

fun TopRatedShowsItem.toCardViewData(): CardViewData {
    return CardViewData(
        id = id,
        media_type = "tv",
        title = if (name == original_name) name else "$name ($original_name)",
        poster_path = poster_path ?: "",
        vote_average = vote_average,
        date = first_air_date
    )
}

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

fun com.nsicyber.wciwapp.data.remote.response.movieNowPlayingList.Result?.toCardViewData(): CardViewData {
    return CardViewData(
        id = this?.id,
        media_type = "movie",
        title = if (this?.title == this?.original_title) this?.title else "${this?.title} (${this?.original_title})",
        poster_path = this?.poster_path ?: "",
        vote_average = this?.vote_average,
        date = this?.release_date
    )
}