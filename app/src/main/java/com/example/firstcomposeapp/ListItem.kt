package com.example.firstcomposeapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


@Serializable
data class Product(
    val image: String,
    val title: String,
    val id: String,
    val price: String,
    val description: String,
    val category: String,
    val rating: String
)


sealed class ViewState {
    object Empty : ViewState()
    object Loading : ViewState()
    data class Success(val data: List<Product>) : ViewState()
    data class Error(val exception: Throwable) : ViewState()
}

class MainViewModel : ViewModel() {

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)


    private val _detailViewState = MutableStateFlow<DetailViewState>(DetailViewState.Loading)

    val books = _viewState.asStateFlow()
    val bookDetails = _detailViewState.asStateFlow()

    // Helps to format the JSON
    private val format = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        isLenient = true
    }


    // get all the data from the Book.json
    fun getAllProducts(context: Context) = viewModelScope.launch {
        try {

            // read JSON File
            val myJson = context.assets.open("Data.json").bufferedReader().use {
                it.readText()
            }

            // format JSON
            val bookList = format.decodeFromString<List<Product>>(myJson)
            _viewState.value = ViewState.Success(bookList)

        } catch (e: Exception) {
            _viewState.value = ViewState.Error(e)
        }
    }


    // get book by ID
    fun getProductId(context: Context, id: String) = viewModelScope.launch {
        try {

            // read JSON File
            val myJson = context.assets.open("books.json").bufferedReader().use {
                it.readText()
            }

            // format JSON
            val bookList =
                format.decodeFromString<List<Product>>(myJson).first { it.id.contentEquals(id) }
            _detailViewState.value = DetailViewState.Success(bookList)

        } catch (e: Exception) {
            _detailViewState.value = DetailViewState.Error(e)
        }
    }

}

sealed class DetailViewState {
    object Empty : DetailViewState()
    object Loading : DetailViewState()
    data class Success(val data: Product) : DetailViewState()
    data class Error(val exception: Throwable) : DetailViewState()
}

