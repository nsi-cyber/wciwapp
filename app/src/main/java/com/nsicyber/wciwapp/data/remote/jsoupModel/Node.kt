package com.nsicyber.wciwapp.data.remote.jsoupModel

data class Node(
    val __typename: String,
    val id: String,
    val inIframeEditLink: İnIframeEditLink,
    val inIframeReportLink: İnIframeReportLink,
    val isSpoiler: Boolean,
    val notInIframeEditLink: NotInIframeEditLink,
    val notInIframeReportLink: NotInIframeReportLink,
    val text: Text
)