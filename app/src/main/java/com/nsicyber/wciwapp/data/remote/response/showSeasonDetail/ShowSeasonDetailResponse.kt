package com.nsicyber.wciwapp.data.remote.response.showSeasonDetail

import com.nsicyber.wciwapp.prese.EpisodeModel
import com.nsicyber.wciwapp.prese.SeasonModel


data class ShowSeasonDetailResponse(
    val _id: String,
    val air_date: Any,
    val episodes: List<Episode>,
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String,
    val season_number: Int,
    val vote_average: Double
)


fun ShowSeasonDetailResponse?.toSeasonModel(): SeasonModel {

    var episodeListTemp: ArrayList<EpisodeModel> = arrayListOf()

    this?.episodes?.forEachIndexed { index, episode ->
        episodeListTemp.add(
            EpisodeModel(
                episodeName = episode.name,
                imageUrl = episode.still_path,
                episodeOverview = episode.overview,
                episodeNumber = index + 1
            )
        )
    }

    val temp = SeasonModel(
        imageUrl = this?.poster_path,
        seasonOverview = this?.overview,
        seasonName = this?.overview,
        episodes = episodeListTemp
    )
    return temp
}