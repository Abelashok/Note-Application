package com.example.learning1.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_table ORDER BY note_name ASC")
    fun getAllNotes(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: Note)

    @Update(entity = Note::class)
    suspend fun updateNote(note: Note)

//    @Delete
//    suspend fun deleteAllNote()

    @Delete
    suspend fun deleteOneNode(note: Note)

}


