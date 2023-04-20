package com.eugrepmac.catalog.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

/**
 * @param navController The navController that must be associated with this navHost.
 * @param isExpandedScreen is the screen currently considered wide or expanded, or is it considered
 * narrow or small?
 * @param modifier The modifier that will be applied to the content of this NavHost.
 * @param startDestination The destination at which to navigate first when this NavHost comes into
 * the composition.
 */
@Composable
fun CatalogNavHost(
    navController: NavHostController,
    isExpandedScreen: Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = ROOT_ROUTE
) {
    // This composable is basically only a NavHost describing all the destinations it can display.
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        root(
            isExpandedScreen=isExpandedScreen,
        )
    }
}