package com.nsicyber.wciwapp.data.remote.response.trendingList


data class TrendingListItem(


    val id: Int,

    val media_type: String?,

    val name: String? = null,

    val title: String? = null,


    val overview: String,

    val popularity: Double,

    val poster_path: String,

    val vote_average: Double,

    val vote_count: Int,

    val release_date: String? = null,

    val first_air_date: String? = null,


    )


