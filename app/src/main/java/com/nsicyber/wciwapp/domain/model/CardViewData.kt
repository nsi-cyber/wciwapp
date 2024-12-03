package com.nsicyber.wciwapp.domain.model

data class CardViewData(
    val id: Int? = null,
    val media_type: String? = null,
    val title: String? = null,
    val poster_path: String="",
    val vote_average: Double? = null,
    val date: String? = null,
)
