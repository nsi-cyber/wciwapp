package com.nsicyber.wciwapp.data.remote.response.providersList


data class ProviderItemData(
    val buy: List<Flatrate?>? = null,
    val flatrate: List<Flatrate?>? = null,
    val link: String? = null,
    val rent: List<Flatrate?>? = null
)