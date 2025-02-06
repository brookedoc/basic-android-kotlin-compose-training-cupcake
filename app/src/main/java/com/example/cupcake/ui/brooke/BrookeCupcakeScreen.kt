package com.example.cupcake.ui.brooke

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cupcake.CupcakeAppBar
import com.example.cupcake.CupcakeScreen
import com.example.cupcake.R
import com.example.cupcake.ui.StartOrderScreen

/**
 * enum values that represent the screens in the app
 */
enum class BrookeCupcakeScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Flavor(title = R.string.choose_flavor),
    Summary(title = R.string.order_summary)
}


@Composable
fun BrookeCupcakeApp(
    viewModel: CupcakeViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    LaunchedEffect(viewModel) {
        viewModel.onInitialize()
    }
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = CupcakeScreen.valueOf(
        backStackEntry?.destination?.route ?: CupcakeScreen.Start.name
    )
    Scaffold(
        topBar = {
            CupcakeAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = CupcakeScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            when (uiState.step) {
                CupcakeStep.Start -> StartOrderScreen(
                    quantityOptions = uiState.quantityOptions,
                    onNextButtonClicked = viewModel.nextClicked()
                )
            }
        }
    }
}