package com.example.learning1.utils

import com.example.learning1.data.Note

sealed class NoteEvent {
   data class Order(val orderType: OrderType): NoteEvent()
   data class DeleteNote(val note: Note): NoteEvent()
   data class DeleteAllNote(val note: Note): NoteEvent()

}
