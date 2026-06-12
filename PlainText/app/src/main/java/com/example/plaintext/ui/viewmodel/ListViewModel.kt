package com.example.plaintext.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plaintext.data.model.Password
import com.example.plaintext.data.model.PasswordInfo
import com.example.plaintext.data.repository.PasswordDBStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ListViewState(
    var passwordList: List<Password> = emptyList(),
    var isCollected: Boolean = false
)

//Utilize o passwordBDStore para obter a lista de senhas e salva-las
@HiltViewModel
open class ListViewModel @Inject constructor (
    private val passwordDBStore: PasswordDBStore) : ViewModel() {
    var listViewState by mutableStateOf(ListViewState(passwordList = emptyList()))
        private set

    init{
        viewModelScope.launch {
                //execute o metodo getList() do passwordDBStore e colete o resultado
            passwordDBStore.getList().collect { lista ->
                listViewState = listViewState.copy(
                    passwordList = lista,
                    isCollected = true
                )
            }
        }
    }

    fun savePassword(password: PasswordInfo){
        println("ID = ${password.id}")
        println("VIEWMODEL SAVE: ${password.name}")
        viewModelScope.launch {
                passwordDBStore.save(password)
            }
    }
}
