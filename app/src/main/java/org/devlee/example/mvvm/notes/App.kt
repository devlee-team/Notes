package org.devlee.example.mvvm.notes

import android.app.Application
import android.content.Context
import org.devlee.example.mvvm.notes.locator.ServiceLocator
import org.devlee.example.mvvm.notes.locator.locate
import org.devlee.example.mvvm.notes.factory.NoteCaptionFactory
import org.devlee.example.mvvm.notes.repository.mapper.NotesPayloadMapper
import org.devlee.example.mvvm.notes.repository.Repository
import org.devlee.example.mvvm.notes.repository.api.NotesApi
import org.devlee.example.mvvm.notes.repository.room.NotesDatabase
import org.devlee.example.mvvm.notes.repository.room.NotesDatabaseImpl

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        ServiceLocator.register<Context>(this)
        ServiceLocator.register<NotesDatabase>(NotesDatabaseImpl.create(locate()))
        ServiceLocator.register(NotesApi.create())
        ServiceLocator.register(NoteCaptionFactory())
        ServiceLocator.register(NotesPayloadMapper(locate()))
        ServiceLocator.register(Repository(locate(), locate(), locate()))

    }
}

