package com.example.learning1.ui.HomePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.learning1.repository.noteRepository


class HomeViewModelFactory(private val repository: noteRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return (HomeViewModel(repository) as T)
    }
}