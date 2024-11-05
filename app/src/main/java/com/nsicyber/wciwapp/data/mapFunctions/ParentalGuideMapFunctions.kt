package com.nsicyber.wciwapp.data.mapFunctions

import com.google.gson.Gson
import com.nsicyber.wciwapp.data.remote.jsoupModel.ParentalGuideResponse
import com.nsicyber.wciwapp.domain.model.ParentalGuideCategoryItem
import com.nsicyber.wciwapp.domain.model.ParentalGuideCategoryList
import com.nsicyber.wciwapp.domain.model.toParentGuideCategoryType
import com.nsicyber.wciwapp.domain.model.toParentGuideSeverityType
import com.nsicyber.wciwapp.presentation.detailScreen.movieDetailScreen.fromHtmlString

fun parseParentalData(jsonData: String): ParentalGuideCategoryList {
    val parentalData = Gson().fromJson(jsonData, ParentalGuideResponse::class.java)

    val categoryList =
        parentalData.props.pageProps.contentData.data.title.parentsGuide.categories.map { category ->
            val categoryType = category.category.id.toParentGuideCategoryType()
            val severityType = category.severity.id.toParentGuideSeverityType()
            val severityPercentage =
                ((category.severity.votedFor.toDouble() / category.totalSeverityVotes) * 100).toInt()

            ParentalGuideCategoryItem(
                categoryType = categoryType,
                severityType = severityType,
                severityPercentage = severityPercentage,
                userComments = emptyList()
            )
        }.toMutableList()
    updateCategoryListWithComments(categoryList, parentalData)

    return ParentalGuideCategoryList(categoryList)
}

private fun updateCategoryListWithComments(
    categoryList: MutableList<ParentalGuideCategoryItem>,
    parentalData: ParentalGuideResponse
) {
    parentalData.props.pageProps.contentData.data.title.parentsGuide.nonSpoilerCategories.forEach { nonSpoilerCategory ->
        val categoryItem =
            categoryList.find { it.categoryType == nonSpoilerCategory.category.id.toParentGuideCategoryType() }
        categoryItem?.userComments =
            nonSpoilerCategory.guideItems.edges.map { it.node.text.plaidHtml.fromHtmlString() }
    }
}