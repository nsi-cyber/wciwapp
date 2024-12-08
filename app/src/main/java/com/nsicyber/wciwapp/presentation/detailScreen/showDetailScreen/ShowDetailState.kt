package com.nsicyber.wciwapp.prese

import com.nsicyber.wciwapp.data.remote.response.creditsList.CreditsListResponse
import com.nsicyber.wciwapp.data.remote.response.imageList.ImageListResponseItem
import com.nsicyber.wciwapp.data.remote.response.providersList.ProvidersListResponseData
import com.nsicyber.wciwapp.data.remote.response.showDetail.ShowDetailResponse
import com.nsicyber.wciwapp.data.remote.response.videosList.VideosListResponseItem
import com.nsicyber.wciwapp.domain.model.CardViewData
import com.nsicyber.wciwapp.domain.model.ParentalGuideCategoryList

data class SeasonModel(
    val seasonNumber: Int? = null,
    val imageUrl: String? = null,
    val seasonName: String? = null,
    val seasonOverview: String? = null,
    val episodes: List<EpisodeModel>? = null,
)


data class EpisodeModel(
    val episodeNumber: Int? = null,
    val episodeName: String? = null,
    val imageUrl: String? = null,
    val episodeOverview: String? = null,
)

data class ShowDetailState(
    val isLoading: Boolean = false,
    val imdbId: String = "",
    val details: ShowDetailResponse? = null,
    val parentalGuide: ParentalGuideCategoryList? = null,
    val credits: CreditsListResponse? = null,
    val images: List<ImageListResponseItem?>? = null,
    val watchProviders: ProvidersListResponseData? = null,
    val similars: List<CardViewData?>? = null,
    val seasonList: List<SeasonModel>? = null,
    val videos: List<VideosListResponseItem?>? = null
)




