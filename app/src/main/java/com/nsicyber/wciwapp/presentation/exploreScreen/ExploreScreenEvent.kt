package com.nsicyber.wciwapp.presentation.exploreScreen


// Olay sınıflarını tanımlayın
sealed class ExploreScreenEvent {
    object LoadExploreScreen : ExploreScreenEvent()
    object LoadPopularMovies : ExploreScreenEvent()
    object LoadTopRatedShows : ExploreScreenEvent()
    object LoadTrending : ExploreScreenEvent()
    object LoadTopMovies : ExploreScreenEvent()
}