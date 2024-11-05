package com.nsicyber.wciwapp.data.remote.jsoupModel

data class ParentsGuide(
    val __typename: String,
    val categories: List<Category>,
    val nonSpoilerCategories: List<NonSpoilerCategory>,
    val spoilerCategories: List<SpoilerCategory>
)