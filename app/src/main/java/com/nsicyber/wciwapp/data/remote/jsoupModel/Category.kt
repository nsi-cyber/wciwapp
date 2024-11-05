package com.nsicyber.wciwapp.data.remote.jsoupModel

data class Category(
    val __typename: String,
    val category: CategoryX,
    val severity: Severity,
    val severityBreakdown: List<SeverityBreakdown>,
    val totalSeverityVotes: Int
)