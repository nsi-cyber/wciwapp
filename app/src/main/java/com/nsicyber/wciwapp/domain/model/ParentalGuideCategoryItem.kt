package com.nsicyber.wciwapp.domain.model

data class ParentalGuideCategoryItem(
    val categoryType: ParentGuideCategoryType?=null,
    val severityType: ParentGuideSeverityType?=null,
    val severityPercentage: Int?=null,
    var userComments: List<String?> = listOf()
)