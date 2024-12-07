package com.nsicyber.wciwapp.presentation.detailScreen.showDetailScreen


sealed class ShowDetailEvent {
    data class LoadShowDetailScreen(val showId: Int?) : ShowDetailEvent()
    data class GetShowSeasonDetail(val seasonCount: Int?) : ShowDetailEvent()
}