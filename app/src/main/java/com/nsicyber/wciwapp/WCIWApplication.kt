package com.nsicyber.wciwapp

import android.app.Application
import androidx.compose.ui.graphics.Color
import dagger.hilt.android.HiltAndroidApp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale


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


fun String?.parseDateFromAnyFormat(): Date? {
    val formats = listOf(
        "yyyy-MM-dd",
        "MM/dd/yyyy",
        "dd-MM-yyyy",
        "yyyy/MM/dd",
        "dd/MM/yyyy",
        "dd MMM yyyy",
        "MMM dd, yyyy",
        "yyyyMMdd",
        "EEE, dd MMM yyyy HH:mm:ss z",
        "yyyy-MM-dd'T'HH:mm:ss'Z'"
    )

    for (format in formats) {
        try {
            val formatter = SimpleDateFormat(format, Locale.getDefault())
            return formatter.parse(this)
        } catch (e: Exception) {
            // Geçerli format değilse bir şey yapma, diğer formatları dene
        }
    }
    return null // Hiçbir format uygun değilse null döner
}


@HiltAndroidApp
class WCIWApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }

}