package com.ucne.glamourmarket.presentation.screams.formaPago

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ucne.glamourmarket.data.repository.ComprasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FormaPagoViewModel @Inject constructor(
    private val comprasRepository: ComprasRepository
) : ViewModel() {

    var nombreTarjeta = mutableStateOf("")
    var numeroTarjeta = mutableStateOf("")
    var cvc = mutableStateOf("")
    var mesExpiracion = mutableStateOf("")
    var anioExpiracion = mutableStateOf("")

    var nombreTarjetaError = mutableStateOf<String?>(null)
    var numeroTarjetaError = mutableStateOf<String?>(null)
    var cvcError = mutableStateOf<String?>(null)

    fun onNombreTarjetaChange(newValue: String) {
        nombreTarjeta.value = newValue
        nombreTarjetaError.value = if (newValue.isBlank()) "El nombre no puede estar vacío" else null
    }

    fun onNumeroTarjetaChange(newValue: String) {
        numeroTarjeta.value = newValue
        numeroTarjetaError.value = if (newValue.length != 16) "El número de tarjeta debe tener 16 dígitos" else null
    }

    fun onCvcChange(newValue: String) {
        cvc.value = newValue
        cvcError.value = if (newValue.length != 3) "El CVC debe tener 3 dígitos" else null
    }

    fun onMesExpiracionChange(newValue: String) {
        mesExpiracion.value = newValue
    }

    fun onAnioExpiracionChange(newValue: String) {
        anioExpiracion.value = newValue
    }

    fun validarCampos(): Boolean {
        val isNombreValido = !nombreTarjeta.value.isBlank()
        val isNumeroValido = numeroTarjeta.value.length == 16
        val isCvcValido = cvc.value.length == 3
        val isMesValido = mesExpiracion.value != "MM"
        val isAnioValido = anioExpiracion.value != "AAAA"

        nombreTarjetaError.value = if (!isNombreValido) "El nombre no puede estar vacío" else null
        numeroTarjetaError.value = if (!isNumeroValido) "El número de tarjeta debe tener 16 dígitos" else null
        cvcError.value = if (!isCvcValido) "El CVC debe tener 3 dígitos" else null

        return isNombreValido && isNumeroValido && isCvcValido && isMesValido && isAnioValido
    }
}
