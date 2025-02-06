package com.example.cupcake.ui.brooke

import androidx.annotation.StringRes

data class CupcakeDataState(
    val step: CupcakeStep = CupcakeStep.Start,
    val quantityOptions: List<Pair<Int, Int>> = emptyList(),
    val quantity: Int = 0,
    val flavors: List<String> = emptyList(),
    val price: Float = 0.0f,
)

data class CupcakeUiState(
    val step: CupcakeStep = CupcakeStep.Start,
    val quantityOptions: List<Pair<Int, Int>> = emptyList(),
    val quantity: Int = 0,
    val flavors: List<String> = emptyList(),
    val price: Float = 0.0f,
)

enum class CupcakeStep {
    Start,
    Flavor,
    Summary,
}

fun CupcakeDataState.toUiState() = CupcakeUiState(
    step = step,
    quantityOptions = quantityOptions,
    quantity = quantity,
    flavors = flavors,
    price = price,
)