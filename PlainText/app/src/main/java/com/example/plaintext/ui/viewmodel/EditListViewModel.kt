package com.example.plaintext.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plaintext.data.model.PasswordInfo
import com.example.plaintext.data.repository.PasswordDBStore
import com.example.plaintext.ui.screens.editList.EditListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditListViewModel @Inject constructor(
    private val passwordDBStore: PasswordDBStore
) : ViewModel() {

    var editListState by mutableStateOf(EditListState())
        private set

    fun loadPassword(passwordInfo: PasswordInfo?) {

        if (passwordInfo == null) return

        editListState.nomeState.value = passwordInfo.name
        editListState.usuarioState.value = passwordInfo.login
        editListState.senhaState.value = passwordInfo.password
        editListState.notasState.value = passwordInfo.notes
    }

    fun buildPassword(id: Int = 0): PasswordInfo {
        return PasswordInfo(
            id = id,
            name = editListState.nomeState.value,
            login = editListState.usuarioState.value,
            password = editListState.senhaState.value,
            notes = editListState.notasState.value
        )
    }

    fun savePassword(
        id: Int = 0,
        onSave: (PasswordInfo) -> Unit
    ) {
        val password = buildPassword(id)

        viewModelScope.launch {
            passwordDBStore.save(password)
            onSave(password)
        }
    }
}