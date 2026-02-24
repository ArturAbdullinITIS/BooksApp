package com.example.booksapp.ui.screen.details

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.domain.model.Book
import com.example.domain.usecase.GetBookDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getBookDetailsUseCase: GetBookDetailsUseCase
): ViewModel(){
    private val _state = MutableStateFlow(DetailsState())
    val state = _state.asStateFlow()
}


data class DetailsState(
    val isLoading: Boolean = false,
    val bookItem: Book? = null,
    val errorMessage: String? = null
)