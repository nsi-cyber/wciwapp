package com.nsicyber.wciwapp.presentation.searchScreen



sealed class SearchScreenEvent {
    data object LoadSearchScreen : SearchScreenEvent()
    data object GetMovieNowPlayingList : SearchScreenEvent()
    data object GetShowOnAirList : SearchScreenEvent()
    data object SetStateEmpty : SearchScreenEvent()
    data class Search(val query: String) : SearchScreenEvent()

}
