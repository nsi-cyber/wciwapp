package com.nsicyber.wciwapp.domain.useCase

import com.google.firebase.database.FirebaseDatabase
import com.nsicyber.wciwapp.data.mapFunctions.parseParentalData
import com.nsicyber.wciwapp.domain.model.ParentalGuideCategoryList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import javax.inject.Inject

class GetParentalGuideUseCase @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) {

     operator fun invoke(imdbId: String?): Flow<Result<ParentalGuideCategoryList>> = flow {
        if (imdbId.isNullOrEmpty()) {
            emit(Result.failure(Exception("Invalid IMDb ID")))
            return@flow
        }

        // Firebase kontrolü
        val cachedData = getFromFirebase(imdbId)
        if (cachedData != null) {
            emit(Result.success(cachedData))
            return@flow
        }

        // IMDb'den Veri Çekme
        val document = withContext(Dispatchers.IO) {
            Jsoup.connect("https://www.imdb.com/title/$imdbId/parentalguide/")
                .timeout(5000)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36")
                .get()
        }

        val jsonData = document.select("script[type=application/json]").firstOrNull()?.data()
        jsonData?.let {
            val parentalGuideData = parseParentalData(it)
            saveToFirebase(imdbId, parentalGuideData)
            emit(Result.success(parentalGuideData))
        } ?: emit(Result.failure(Exception("Failed to parse IMDb data")))

    }.catch { e ->
        emit(Result.failure(e))
    }

    // Firebase'den veri çekme
    private suspend fun getFromFirebase(imdbId: String): ParentalGuideCategoryList? {
        return withContext(Dispatchers.IO) {
            val reference = firebaseDatabase.reference.child("parentalGuide").child(imdbId)
            val snapshot = reference.get().await()
            snapshot.getValue(ParentalGuideCategoryList::class.java)
        }
    }

    // Firebase'e veri kaydetme
    private suspend fun saveToFirebase(imdbId: String, data: ParentalGuideCategoryList) {
        withContext(Dispatchers.IO) {
            firebaseDatabase.reference.child("parentalGuide").child(imdbId).setValue(data).await()
        }
    }
}
