package org.devlee.example.mvvm.notes

import android.app.Application
import android.content.Context
import org.devlee.example.mvvm.notes.locator.ServiceLocator
import org.devlee.example.mvvm.notes.locator.locate
import org.devlee.example.mvvm.notes.repository.Repository
import org.devlee.example.mvvm.notes.repository.room.NotesDatabase

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        ServiceLocator.register<Context>(this)
        ServiceLocator.register(NotesDatabase.create(locate()))
        ServiceLocator.register(Repository(locate()))
    }
}

