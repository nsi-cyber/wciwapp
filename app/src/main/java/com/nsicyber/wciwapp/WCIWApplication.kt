package com.nsicyber.wciwapp

import android.app.Application
import androidx.compose.ui.graphics.Color
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.HiltAndroidApp
import java.time.LocalDate
import java.util.Locale
import java.time.format.TextStyle


val primaryColor: Color = Color(0xFF0A071D)

//val primaryColor: Color = Color(0xFF0A071D)
val secondaryColor: Color = Color(0xFF2C2654)
//val secondaryColor: Color = Color(0xFF2C2654)


fun String?.formatDate(): String? {
    if (!this.isNullOrEmpty()) {
        val inputDate = LocalDate.parse(this)
        val currentLocale = Locale.getDefault()
        val monthName = inputDate.month.getDisplayName(TextStyle.FULL, currentLocale)

        return "${inputDate.dayOfMonth} $monthName ${inputDate.year}"
    } else return null
}



@HiltAndroidApp
class WCIWApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }

}