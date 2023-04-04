package com.example.learning1.ui.HomePage

import android.util.Log
import androidx.lifecycle.*
import com.example.learning1.data.Note
import com.example.learning1.repository.noteRepository
import com.example.learning1.utils.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import kotlinx.coroutines.launch

class HomeViewModel(private val repository: noteRepository):ViewModel() {

   private var updatedNote:MutableList<Note> = ArrayList()



    fun onEvent(event: NoteEvent):LiveData<List<Note>>?{
      return  when(event) {
            is NoteEvent.Order -> {
                getNotes(event.orderType).asLiveData()
            }
            is NoteEvent.DeleteNote -> {
                null
            }
            is NoteEvent.DeleteAllNote -> {
                null
            }
        }
    }
    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }
     private fun getNotes(noteOrderType: OrderType): Flow<List<Note>> {
//         updatedNote.clear()
         Log.e("NoteSize"," " + updatedNote.size)
      return repository.allNotes.map { notes ->
          updatedNote.clear()
          notes.map {
            if(NavEvent.navType == navigatioType.HOME && !it.completed) {

                updatedNote?.add(it)
            }
              if(NavEvent.navType == navigatioType.COMPLETED && it.completed) {
                  updatedNote?.add(it)
              }
          }
          when(noteOrderType) {
              is OrderType.Ascending ->  updatedNote?.sortedBy { it.note_name.lowercase() }
              is OrderType.Descending -> updatedNote?.sortedByDescending { it.note_name.lowercase() }
              else -> notes
          }
      }
    }

//    val getAll = repository.allNotes.asLiveData()

    fun updateNote(note:Note) = viewModelScope.launch {
        repository.update(note)
    }

  fun deleteOne(note: Note) = viewModelScope.launch {
      repository.deleteOne(note)
  }
}