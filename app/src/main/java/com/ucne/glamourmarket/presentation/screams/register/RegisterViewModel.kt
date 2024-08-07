package com.ucne.glamourmarket.presentation.screams.register

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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val usuariosRepository: UsuariosRepository
) : ViewModel() {
    var id by mutableIntStateOf(0)
    var nickname by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var nicknameError by mutableStateOf(true)
    var emailError by mutableStateOf(true)
    var passwordError by mutableStateOf(true)
    var passwordLengthError by mutableStateOf(true)
    var registerError by mutableStateOf(false)

    fun ValidarRegistro(): Boolean {
        nicknameError = nickname.isNotEmpty()
        emailError = email.isNotEmpty()
        passwordError = password.isNotEmpty()
        passwordLengthError = password.length >= 6

        return nicknameError && emailError && passwordError && passwordLengthError
    }

    private val _loading = mutableStateOf(false)


    fun createUserWithEmailAndPassword(correo: String, clave: String, home: () -> Unit) {
        if (_loading.value == false) {
            _loading.value = true
            val authNewUser = FirebaseAuth.getInstance()
            authNewUser.createUserWithEmailAndPassword(correo, clave)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Register", "Usuario creado con éxito: ${task.result?.user?.uid}")
                        authNewUser.signOut()
                        home()
                    } else {
                        Log.d("Register", "createUserWithEmailAndPassword: ${task.exception?.message}")
                        registerError = true
                    }
                    _loading.value = false
                }
                .addOnFailureListener {
                    Log.d("Register", "Error: ${it.message}")
                    registerError = true
                    _loading.value = false
                }
        }
    }

    fun send() {
            viewModelScope.launch {
                    val usuario = UsuarioDTO(
                        nickName = nickname,
                        email = email,
                        password = password
                    )
                    usuariosRepository.registrarUsuarios(usuario)
                    clear()
            }
    }

    fun clear(){
        nickname = ""
        email = ""
        password = ""
    }
}