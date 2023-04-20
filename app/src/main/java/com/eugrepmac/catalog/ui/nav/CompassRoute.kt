package com.eugrepmac.catalog.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

/**
 * The actual navigation root to access to the compass part of the application
 */
const val COMPASS_ROUTE = "compass_route"

/**
 * The actual composable describing the compass destination
 *
 * @param modifier The modifier that will be applied to the compass destination.
 */
@Composable
fun CompassRoute(
    modifier: Modifier,
) {
    // TODO
}

/**
 * Describes the composable that will be called and used by the NavGraphBuilder as the composable
 * entry point to call when navigating to the compass destination.
 *
 * @param modifier A modifier to apply to the game in progress destination
 */
fun NavGraphBuilder.compass(
    modifier: Modifier = Modifier,
) {
    composable(route = COMPASS_ROUTE) {
        CompassRoute(
            modifier
        )
    }
}

/**
 * A Helper function to manually navigate to the compass destination from a NavController instance.
 */
fun NavController.navigateToCompass() {
    this.navigate(ROOT_ROUTE)
}