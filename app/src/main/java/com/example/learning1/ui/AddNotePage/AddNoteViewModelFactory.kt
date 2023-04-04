package com.example.learning1.ui.AddNotePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.learning1.repository.noteRepository

class AddNoteViewModelFactory(private val repository: noteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return (AddNoteViewModel(repository) as T)
    }
}