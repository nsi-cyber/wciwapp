package com.nsicyber.wciwapp.domain.model

import android.content.Context
import androidx.compose.ui.graphics.Color
import com.nsicyber.wciwapp.R
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


fun ParentGuideSeverityType.toText(context:Context): String? {
    return when (this) {
        ParentGuideSeverityType.MILD -> context.getString(R.string.mild)
        ParentGuideSeverityType.MODERATE -> context.getString(R.string.moderate)
        ParentGuideSeverityType.NONE -> context.getString(R.string.none)
        ParentGuideSeverityType.SEVERE -> context.getString(R.string.severe)
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

fun ParentGuideCategoryType.toText(context:Context): String? {
    return when (this) {
        ParentGuideCategoryType.FRIGHTENING -> context.getString(R.string.frightening_intense_scenes)
        ParentGuideCategoryType.ALCOHOL -> context.getString(R.string.alcohol_drugs_smoking)
        ParentGuideCategoryType.NUDITY -> context.getString(R.string.sex_nudity)
        ParentGuideCategoryType.VIOLENCE -> context.getString(R.string.violence_gore)
        ParentGuideCategoryType.PROFANITY -> context.getString(R.string.profanity)
        else -> null
    }
}
