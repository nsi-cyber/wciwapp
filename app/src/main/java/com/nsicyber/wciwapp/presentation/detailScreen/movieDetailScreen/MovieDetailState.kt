package com.nsicyber.wciwapp.presentation.detailScreen.movieDetailScreen

import com.nsicyber.wciwapp.data.remote.response.creditsList.CreditsListResponse
import com.nsicyber.wciwapp.data.remote.response.imageList.ImageListResponseItem
import com.nsicyber.wciwapp.data.remote.response.movieDetail.MovieDetailResponse
import com.nsicyber.wciwapp.data.remote.response.providersList.ProvidersListResponseData
import com.nsicyber.wciwapp.data.remote.response.videosList.VideosListResponseItem
import com.nsicyber.wciwapp.domain.model.CardViewData
import com.nsicyber.wciwapp.domain.model.ParentalGuideCategoryList


data class MovieDetailState(
    val isLoading: Boolean = false,
    val details: MovieDetailResponse? = null,
    val parentalGuide: ParentalGuideCategoryList? = null,
    val videos: List<VideosListResponseItem?>? = null,
    val credits: CreditsListResponse? = null,
    val images: List<ImageListResponseItem?>? = null,
    val watchProviders: ProvidersListResponseData? = null,
    val similars: List<CardViewData?>? = null
)
