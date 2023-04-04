package com.example.learning1.ui.AddNotePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learning1.data.Note
import com.example.learning1.repository.noteRepository
import com.example.learning1.utils.NoteEvent
import com.example.learning1.utils.OrderType
import kotlinx.coroutines.launch

class AddNoteViewModel(private val repository: noteRepository): ViewModel() {

    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }

    fun update(note: Note) = viewModelScope.launch {
        repository.update(note)
    }
}