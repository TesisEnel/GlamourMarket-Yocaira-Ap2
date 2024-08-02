package com.ucne.glamourmarket.presentation.screams.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.ucne.glamourmarket.data.dto.UsuarioDTO
import com.ucne.glamourmarket.data.repository.UsuariosRepository
import com.ucne.glamourmarket.data.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UsuarioListState(
    val isLoading: Boolean = false,
    val usuarios: List<UsuarioDTO> = emptyList(),
    val usuario: UsuarioDTO? = null,
    val error: String = "",
)
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val usuariosRepository: UsuariosRepository
) : ViewModel() {
    var id by mutableIntStateOf(0)
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var emailError by mutableStateOf(true)
    var passwordError by mutableStateOf(true)

    var loginError by mutableStateOf(false)
    var loginErrorMessage by mutableStateOf("")

    val auth: FirebaseAuth = Firebase.auth

    fun ValidarLogin(): Boolean {

        emailError = email.isNotEmpty()
        passwordError = password.isNotEmpty()

        return emailError && passwordError
    }

    private val _uiState = MutableStateFlow(UsuarioListState())
    val uiState: StateFlow<UsuarioListState> = _uiState.asStateFlow()

    val usuarios: StateFlow<Resource<List<UsuarioDTO>>> = usuariosRepository.getUsuarios().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = Resource.Loading()
    )

    fun singInWithEmailAndPassword(correo: String, clave: String, home: () -> Unit) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(correo, clave)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("Se ejecuto el Login", "singInWithEmailAndPassword logueado!!")
                            home()
                        } else {
                            Log.d("Se ejecuto el Login", "singInWithEmailAndPassword: ${task.exception?.message}")
                            loginErrorMessage = task.exception?.message ?: "Error desconocido"
                            loginError = true
                        }
                    }
            } catch (ex: Exception) {
                Log.d("Se ejecuto el Login", "singInWithEmailAndPassword: ${ex.message}")
                loginErrorMessage = ex.message ?: "Error desconocido"
                loginError = true
            }
        }
    }

    fun singOut(login: () -> Unit){
        Firebase.auth.signOut()
        login()
    }
    init {
        cargar()
    }

    fun cargar() {
        usuariosRepository.getUsuarios().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }

                is Resource.Success -> {
                    _uiState.update { it.copy(usuarios = result.data ?: emptyList()) }

                }

                is Resource.Error -> {
                    _uiState.update { it.copy(error = result.message ?: "Error desconocido") }
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }
}