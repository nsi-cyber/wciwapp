package com.nsicyber.wciwapp.data.remote.jsoupModel

data class SeverityBreakdown(
    val __typename: String,
    val id: String,
    val text: String,
    val voteType: String,
    val votedFor: Int
)