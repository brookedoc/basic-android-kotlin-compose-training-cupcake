package com.example.cupcake.ui.brooke

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cupcake.data.DataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class CupcakeViewModel : ViewModel() {
    private val state = MutableStateFlow(CupcakeDataState())
    val currentState : StateFlow<CupcakeDataState> = state.asStateFlow()

    val uiState: StateFlow<CupcakeUiState> = state.map {
        it.toUiState()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = state.value.toUiState()
    )

    fun onInitialize() {
        state.update { it.copy(
            quantityOptions = DataSource.quantityOptions
        ) }
    }
    fun setQuantity(quantity: Int) {
        state.update {
            it.copy(
                quantity = quantity
            )
        }
    }

    fun nextClicked() {
        when (currentState.value.step) {
            CupcakeStep.Start -> state.update { it.copy(step = CupcakeStep.Flavor) }
            CupcakeStep.Flavor -> state.update { it.copy(step = CupcakeStep.Summary) }
            CupcakeStep.Summary -> state.update { it.copy(step = CupcakeStep.Start) }
        }
    }

}

