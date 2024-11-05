package com.nsicyber.wciwapp.data.remote.response.showExternalId

data class ExternalIdResponse(
    val facebook_id: Any,
    val freebase_id: Any,
    val freebase_mid: Any,
    val id: Int,
    val imdb_id: String,
    val instagram_id: Any,
    val tvdb_id: Int,
    val tvrage_id: Any,
    val twitter_id: Any,
    val wikidata_id: Any
)