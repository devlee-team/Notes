package org.devlee.example.mvvm.notes.repository.api

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import org.devlee.example.mvvm.notes.repository.api.model.NotesResponsePayload
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query


interface NotesApi {

    @GET("facts")
    suspend fun notes(@Query("limit") limit: Int = 100): NotesResponsePayload

    companion object {

        private val contentType = MediaType.parse("application/json")!!

        private val json = Json { ignoreUnknownKeys = true }

        fun create(): NotesApi = Retrofit.Builder()
            .baseUrl("https://catfact.ninja/")
            .addConverterFactory(json.asConverterFactory(contentType = contentType))
            .build()
            .create(NotesApi::class.java)
            .also {
                Log.d("API", "$it")
            }
    }
}