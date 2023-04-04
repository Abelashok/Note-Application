package com.example.learning1

import android.app.Application
import com.example.learning1.data.NoteDatabase
import com.example.learning1.repository.noteRepository


class NoteApplication: Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: NoteApplication? = null
        val database by lazy { NoteDatabase.getDatabase(instance!!.applicationContext) }
        val repository by lazy {
            noteRepository(database.noteDao())
            noteRepository(NoteDatabase.getDatabase(instance!!.applicationContext).noteDao())
        }
    }

}