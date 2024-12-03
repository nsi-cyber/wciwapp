package com.nsicyber.wciwapp.data.remote.response.personMovieCreditList

data class PersonMovieCreditListResponse(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)