package com.eugrepmac.catalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.eugrepmac.catalog.ui.nav.CatalogList
import com.eugrepmac.catalog.ui.nav.CatalogNavHost
import com.eugrepmac.catalog.ui.nav.ROOT_ROUTE
import com.eugrepmac.catalog.ui.theme.CatalogTheme

// TODO : Put those in the theme
/**
 * The minimum portion of the screen that the Catalog List will take whenever we navigated to a
 * destination (other than root) in an expanded screen.
 */
const val CATALOG_LIST_MIN_WIDTH_FRACTION = 0.3f

/**
 * The duration of the animation that will reduce (or augment) the catalog list width.
 */
const val CATALOG_LIST_WIDTH_ANIMATION_DURATION_MILLIS = 1000 // 1s

/**
 * The single Activity that will handle all user interactions.
 */
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatalogTheme {
                // Compose entry point, the CatalogApp will display all necessary elements
                // depending on the current state.
                CatalogApp(
                    widthSizeClass = calculateWindowSizeClass(this).widthSizeClass,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                )
            }
        }
    }
}

/**
 * The main compose entry point. It will contain all the screens, navigations, etc... But not the
 * theme se that a custom theme might be applied before calling a function.
 *
 * @param widthSizeClass The current width size class, describing how we are currently displayed
 * in order to maybe display some things differently depending, for exemple, on the screen's
 * orientation.
 * @param modifier The modifier to apply to the whole app screen.
 */
@Composable
fun CatalogApp(
    widthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    val navigationController = rememberNavController()
    val isExpandedScreen = remember(widthSizeClass) {
        widthSizeClass != WindowWidthSizeClass.Compact
    }

    Row(modifier = modifier) {
        if (isExpandedScreen) {
            // Get the currently selected navigation destination for the animation described below.
            val navBackStackEntry by navigationController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            // Describe the selection animation. This animation will run when a specific route
            // is selected from the Catalog List in expanded screen mode.
            // By default, If no route is selected, the list will display on the whole screen width,
            // but if we navigate to any route, then the list width will reduce to make room to the
            // NavHost displaying the actual content. This animation allows it to be done smoothly.
            val catalogListWidthAnimatedFraction: Float by animateFloatAsState(
                targetValue = if (currentDestination == null || currentDestination.route == ROOT_ROUTE) 1f else CATALOG_LIST_MIN_WIDTH_FRACTION,
                animationSpec = tween(CATALOG_LIST_WIDTH_ANIMATION_DURATION_MILLIS)
            )

            // If we are on an expanded screen, we want to display two things. First thing will
            // be the list of catalog items, the other one will be the associated content.
            CatalogList(
                navigationController = navigationController,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(catalogListWidthAnimatedFraction)
            )
        }
        // The content associated to the current route will be displayed into this NavHost.
        // The Nav Host is the same in all situations, which prevents errors due to different
        // possible start destinations. But it is up to it to display the proper root destination
        // depending on the current screen expansion.
        CatalogNavHost(
            navController = navigationController,
            isExpandedScreen = isExpandedScreen,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}
