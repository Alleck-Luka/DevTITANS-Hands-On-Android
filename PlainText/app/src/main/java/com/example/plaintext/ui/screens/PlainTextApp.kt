package com.example.plaintext.ui.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.plaintext.data.model.PasswordInfo
import com.example.plaintext.ui.screens.editList.EditList
import com.example.plaintext.ui.screens.hello.Hello_screen
import com.example.plaintext.ui.screens.list.ListView
import com.example.plaintext.ui.screens.login.Login_screen
import com.example.plaintext.ui.screens.preferences.SettingsScreen
import com.example.plaintext.ui.viewmodel.ListViewModel
import com.example.plaintext.ui.viewmodel.PreferencesViewModel
import com.example.plaintext.utils.parcelableType
import kotlin.reflect.typeOf

@Composable
fun PlainTextApp(
    appState: JetcasterAppState = rememberJetcasterAppState()
) {
    val preferencesViewModel: PreferencesViewModel = hiltViewModel()
    val listViewModel: ListViewModel = hiltViewModel()

    NavHost(
        navController = appState.navController,
        startDestination = Screen.Login,
    )
    {
        composable<Screen.Hello>{
            var args = it.toRoute<Screen.Hello>()
            Hello_screen(args)
        }
        composable<Screen.Login>{
            Login_screen(
                navigateToSettings = {
                    appState.navController.navigate(Screen.Preferences)
                },
                navigateToList = {
                    appState.navController.navigate(Screen.List)
                },
                viewModel = preferencesViewModel
            )
        }
        composable<Screen.Preferences> {
            SettingsScreen(
                navController = appState.navController,
                viewModel = preferencesViewModel
            )
        }
        composable<Screen.List> {
            ListView(
                navigateToAdd = {
                    appState.navController.navigate(
                        Screen.EditList(
                            PasswordInfo(
                                id = 0,
                                name = "",
                                login = "",
                                password = "",
                                notes = ""
                            )
                        )
                    )
                },
                navigateToEdit = { password ->
                    appState.navController.navigate(Screen.EditList(password))
                },
                navigateToSettings = {
                    appState.navigateToPreferences()
                }

            )
        }
        composable<Screen.EditList>(
            typeMap = mapOf(typeOf<PasswordInfo>() to parcelableType<PasswordInfo>())
        ) {
            val args = it.toRoute<Screen.EditList>()
            EditList(
                args,
                navigateBack = {
                    appState.navController.popBackStack()
                },
                savePassword = { password ->
                    println("CHEGOU NO NAVHOST: ${password.name}")
                    listViewModel.savePassword(password)
                }
            )
        }
    }
}