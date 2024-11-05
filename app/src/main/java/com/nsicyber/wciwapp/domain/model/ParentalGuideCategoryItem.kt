package com.nsicyber.wciwapp.domain.model

data class ParentalGuideCategoryItem(
    val categoryType: ParentGuideCategoryType?,
    val severityType: ParentGuideSeverityType?,
    val severityPercentage: Int?,
    var userComments: List<String?>
)