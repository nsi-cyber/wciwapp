package com.nsicyber.wciwapp.data.mapFunctions

import com.nsicyber.wciwapp.data.remote.response.showSimilarList.Result
import com.nsicyber.wciwapp.domain.model.CardViewData

fun Result.toCardViewData(): CardViewData {
    return CardViewData(
        id = id,
        media_type = "tv",
        title = this.name,
        poster_path = poster_path,
        vote_average = vote_average,
        date = this.first_air_date
    )
}