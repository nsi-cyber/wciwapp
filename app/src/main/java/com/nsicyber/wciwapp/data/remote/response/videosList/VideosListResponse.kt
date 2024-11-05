package com.nsicyber.wciwapp.data.remote.response.videosList


data class VideosListResponse(
    val id: Int,
    val results: List<VideosListResponseItem>
)