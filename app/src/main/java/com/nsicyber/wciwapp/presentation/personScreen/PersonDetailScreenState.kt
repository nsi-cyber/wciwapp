package com.nsicyber.wciwapp.presentation.personScreen

import com.nsicyber.wciwapp.data.remote.response.personDetail.PersonDetailResponse
import com.nsicyber.wciwapp.data.remote.response.personImageList.Profile
import com.nsicyber.wciwapp.data.remote.response.personMovieCreditList.PersonMovieCreditListResponse


data class PersonDetailScreenState(
    val isLoading: Boolean = true,
    val images: List<Profile?>? = null,
    val shows: PersonMovieCreditListResponse? = null,
    val movies: PersonMovieCreditListResponse? = null,
    val personDetail: PersonDetailResponse? = null,
)



