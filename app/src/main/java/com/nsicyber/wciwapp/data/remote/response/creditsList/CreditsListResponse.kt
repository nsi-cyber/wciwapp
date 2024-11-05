package com.nsicyber.wciwapp.data.remote.response.creditsList


data class CreditsListResponse(
    val cast: List<Cast?>? = null,
    val crew: List<Crew?>? = null,
    val id: Int? = null
)