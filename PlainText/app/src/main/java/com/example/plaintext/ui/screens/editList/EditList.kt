package com.example.plaintext.ui.screens.editList

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.plaintext.data.model.PasswordInfo
import com.example.plaintext.data.repository.FakePasswordDBStore
import com.example.plaintext.ui.screens.Screen
import com.example.plaintext.ui.theme.Lime75
import com.example.plaintext.ui.theme.Orange80
import com.example.plaintext.ui.theme.PlainTextTheme
import com.example.plaintext.ui.viewmodel.EditListViewModel

data class EditListState(
    val nomeState: MutableState<String> = mutableStateOf(""),
    val usuarioState: MutableState<String> = mutableStateOf(""),
    val senhaState: MutableState<String> = mutableStateOf(""),
    val notasState: MutableState<String> = mutableStateOf("")
)

fun isPasswordEmpty(password: PasswordInfo): Boolean {
    return password.name.isEmpty() && password.login.isEmpty() && password.password.isEmpty() && password.notes.isEmpty()
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditList(
    args: Screen.EditList,
    navigateBack: () -> Unit,
    savePassword: (password: PasswordInfo) -> Unit,
    viewModel: EditListViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.loadPassword(args.password)
    }

    val state = viewModel.editListState

    val isEditing = !isPasswordEmpty(args.password)

    val title = if (isEditing) {
        "Atualizar senha"
    } else {
        "Criar nova senha"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        fontWeight = Normal,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Lime75
                ),
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            EditInput(
                textInputLabel = "Nome",
                textInputState = state.nomeState
            )

            EditInput(
                textInputLabel = "Usuário",
                textInputState = state.usuarioState
            )

            EditInput(
                textInputLabel = "Senha",
                textInputState = state.senhaState
            )

            EditInput(
                textInputLabel = "Notas",
                textInputState = state.notasState,
                textInputHeight = 200
            )

            Button(
                onClick = {
                    viewModel.savePassword(
                        id = args.password.id
                    ) { password ->

                        savePassword(password)
                        navigateBack()
                    }

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Orange80
                )
            ) {
                Text("Salvar")
            }
        }
    }
}


@Composable
fun EditInput(
    textInputLabel: String,
    textInputState: MutableState<String> = mutableStateOf(""),
    textInputHeight: Int = 60
) {
    val padding: Int = 30

    var textState by rememberSaveable { textInputState }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(textInputHeight.dp)
            .padding(horizontal = padding.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        OutlinedTextField(
            value = textState,
            onValueChange = { textState = it },
            label = { Text(textInputLabel) },
            modifier = Modifier
                .height(textInputHeight.dp)
                .fillMaxWidth()
        )

    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Preview(showBackground = true)
@Composable
fun EditListPreview() {
    PlainTextTheme(darkTheme = true) {
        EditList(
            Screen.EditList(
                PasswordInfo(1,
                    "Nome",
                    "Usuário",
                    "Senha",
                    "Notas")
            ),
            navigateBack = {},
            savePassword = {},
            viewModel = EditListViewModel(
                FakePasswordDBStore()
            )
        )
    }
}

//@Preview(showBackground = true)
@Composable
fun EditInputPreview() {
    EditInput("Nome")
}