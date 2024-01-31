package com.akbarfikri.noteAppBattlepassRaionFirst.data.api

import com.akbarfikri.noteAppBattlepassRaionFirst.data.model.notes.Note
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotesAPI {

    @GET("/note")
    suspend fun getNotes(): Response<List<Note>>

    @POST("/note")
    suspend fun createNote(@Body note: Note): Response<Note>

    @PUT("/note/{noteId}")
    suspend fun updateNote(@Path("noteId") noteId: String, @Body note: Note): Response<Note>

    @DELETE("/note/{noteId}")
    suspend fun deleteNote(@Path("noteId") noteId: String): Response<String>
}