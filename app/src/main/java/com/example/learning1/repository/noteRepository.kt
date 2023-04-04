package com.example.learning1.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.learning1.data.Note
import com.example.learning1.data.NoteDao


class noteRepository(private val noteDao: NoteDao) {

    val allNotes = noteDao.getAllNotes()

    suspend fun insert(note: Note) {
        noteDao.insertNote(note)
    }

    suspend fun update(note: Note) {
        noteDao.updateNote(note)
    }

//    suspend fun deleteAll() {
//        noteDao.deleteAllNote()
//    }

    suspend fun deleteOne(note: Note) {
        noteDao.deleteOneNode(note)
    }

}