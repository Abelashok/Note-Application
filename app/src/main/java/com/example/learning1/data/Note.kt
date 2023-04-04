package com.example.learning1.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var note_name: String,
    val note_description: String,
    var color: String,
    var date: String? = null,
    var completed: Boolean = false
    ): java.io.Serializable

