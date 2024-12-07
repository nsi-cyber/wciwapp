package com.nsicyber.wciwapp.presentation.detailScreen.movieDetailScreen


sealed class MovieDetailEvent {
    data class LoadMovieDetailScreen(val movieId: Int?) : MovieDetailEvent()
}