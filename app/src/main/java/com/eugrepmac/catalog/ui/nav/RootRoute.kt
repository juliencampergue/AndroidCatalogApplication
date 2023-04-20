package com.eugrepmac.catalog.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

/**
 * The actual navigation root to access to the root part of the application
 */
const val ROOT_ROUTE = "root_route"

/**
 * The actual composable describing the root destination
 *
 * @param isExpandedScreen is the screen currently considered wide or expanded, or is it considered
 * narrow or small?
 * @param modifier The modifier that will be applied to the root destination.
 */
@Composable
fun RootRoute(
    isExpandedScreen: Boolean,
    modifier: Modifier,
) {
    if (!isExpandedScreen) {
        // TODO: Add the list screen
    } else {
        // TODO: Add an empty root screen
    }
}

/**
 * Describes the composable that will be called and used by the NavGraphBuilder as the composable
 * entry point to call when navigating to the root destination.
 *
 * @param isExpandedScreen is the screen currently considered wide or expanded, or is it considered
 * narrow or small?
 * @param modifier A modifier to apply to the game in progress destination
 */
fun NavGraphBuilder.root(
    isExpandedScreen: Boolean,
    modifier: Modifier = Modifier,
) {
    composable(route = ROOT_ROUTE) {
        RootRoute(isExpandedScreen, modifier)
    }
}

/**
 * A Helper function to manually navigate to the root destination from a NavController instance.
 */
fun NavController.navigateToRoot() {
    this.navigate(ROOT_ROUTE)
}