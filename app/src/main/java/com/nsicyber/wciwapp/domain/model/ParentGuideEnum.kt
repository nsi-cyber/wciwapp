package com.nsicyber.wciwapp.domain.model

import androidx.compose.ui.graphics.Color
import com.nsicyber.wciwapp.ui.theme.MildColor
import com.nsicyber.wciwapp.ui.theme.ModerateColor
import com.nsicyber.wciwapp.ui.theme.NoneColor
import com.nsicyber.wciwapp.ui.theme.SevereColor


enum class ParentGuideSeverityType {
    MILD, MODERATE, NONE, SEVERE
}


fun ParentGuideSeverityType.toColor(): Color? {
    return when (this) {
        ParentGuideSeverityType.MILD -> MildColor
        ParentGuideSeverityType.MODERATE -> ModerateColor
        ParentGuideSeverityType.NONE -> NoneColor
        ParentGuideSeverityType.SEVERE -> SevereColor
        else -> null
    }
}


fun String.toParentGuideSeverityType(): ParentGuideSeverityType? {
    return when (this) {
        "mildVotes" -> ParentGuideSeverityType.MILD
        "moderateVotes" -> ParentGuideSeverityType.MODERATE
        "noneVotes" -> ParentGuideSeverityType.NONE
        "severeVotes" -> ParentGuideSeverityType.SEVERE
        else -> null
    }
}


fun ParentGuideSeverityType.toText(): String? {
    return when (this) {
        ParentGuideSeverityType.MILD -> "Mild"
        ParentGuideSeverityType.MODERATE -> "Moderate"
        ParentGuideSeverityType.NONE -> "None"
        ParentGuideSeverityType.SEVERE -> "Severe"
        else -> null
    }
}

enum class ParentGuideCategoryType {
    PROFANITY, VIOLENCE, NUDITY, ALCOHOL, FRIGHTENING,
}

fun String.toParentGuideCategoryType(): ParentGuideCategoryType? {
    return when (this) {
        "FRIGHTENING" -> ParentGuideCategoryType.FRIGHTENING
        "ALCOHOL" -> ParentGuideCategoryType.ALCOHOL
        "NUDITY" -> ParentGuideCategoryType.NUDITY
        "VIOLENCE" -> ParentGuideCategoryType.VIOLENCE
        "PROFANITY" -> ParentGuideCategoryType.PROFANITY
        else -> null
    }
}

fun ParentGuideCategoryType.toText(): String? {
    return when (this) {
        ParentGuideCategoryType.FRIGHTENING -> "Frightening & Intense Scenes"
        ParentGuideCategoryType.ALCOHOL -> "Alcohol, Drugs & Smoking"
        ParentGuideCategoryType.NUDITY -> "Sex & Nudity"
        ParentGuideCategoryType.VIOLENCE -> "Violence & Gore"
        ParentGuideCategoryType.PROFANITY -> "Profanity"
        else -> null
    }
}
