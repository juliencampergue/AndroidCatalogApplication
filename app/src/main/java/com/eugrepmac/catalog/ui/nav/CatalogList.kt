package com.eugrepmac.catalog.ui.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.eugrepmac.catalog.R

/**
 * Describes all the catalog items accessible from the catalog list, along with all necessary
 * elements display and interact with.
 *
 * @param route The navigation route to access the given catalog element
 * @param titleId The ID of the String resource that will be displayed in the list for this item.
 * @param iconId The ID of the drawable resource that will be displayed in the list for this item.
 * @param selectedIconId The ID of the drawable resource that will be displayed in the list for
 * this item when it will be the currently selected screen/route.
 */
sealed class AccessibleCatalogItems(
    val route: String,
    @StringRes val titleId: Int,
    @DrawableRes val iconId: Int? = null,
    @DrawableRes val selectedIconId: Int? = null,
) {
    object COMPASS: AccessibleCatalogItems(
        COMPASS_ROUTE,
        R.string.compass_route_label,
    )
}

/**
 * The list of screens that must be accessible from the catalog list.
 */
val catalogListAccessibleScreens = listOf(
    AccessibleCatalogItems.COMPASS,
)

/**
 * The Catalog List that will display a list of all the accessible destinations of the catalog app.
 * This is a simple list with elements described above and that will control the app's navigation.
 * This is some kind of equivalent to a bottom bar or to a drawer, but displayed as a custom list.
 *
 * @param navigationController The NavHostController used to change current route when clicking on
 * an item of the list.
 * @param modifier The modifier to apply to this list. Optional
 */
@Composable
fun CatalogList(
    navigationController: NavController,
    modifier: Modifier = Modifier
) {
    // The LazyListState is used to keep track of the scroll position of the list.
    val lazyListState = rememberLazyListState()

    // Get the currently selected navigation destination
    val navBackStackEntry by navigationController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    LazyColumn(
        state = lazyListState,
        modifier = modifier,
    ) {
        items(catalogListAccessibleScreens) {screen ->
            CatalogListItem(
                label = {Text(text = stringResource(id = screen.titleId))},
                selected = currentDestination?.hierarchy?.any {it.route == screen.route} == true,
                onClick = {
                    navigationController.navigate(screen.route) {
                        // When clicking on a catalog item, popup to the start of the current nav
                        // destination first, but save the state so that, if user clicks on this
                        // root destination again, it will display the actual screen the user was on.
                        popUpTo(navigationController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        // Then navigate to the requested destination, launching it as SingleTop,
                        // and restore state if any has previously been saved for the selected
                        // destination.
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}

/**
 * A list item of the catalog list. It will display a top level destination of the application.
 * It needs a label, and can have an optional icon. This icon may be different when the item is
 * selected.
 *
 * @param selected Is the current item the selected one
 * @param onClick The action to perform when user clicks on this item
 * @param label The Label to use to display the item's title
 * @param icon The optional icon to display alongside the label
 * @param selectedIcon The optional selected icon to display when item is selected instead of the
 * [icon]. Won't be displayed if [icon] is not set either.
 */
@Composable
fun CatalogListItem(
    selected: Boolean,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    icon: @Composable (() -> Unit)? = null,
    selectedIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    // TODO : Use a Custom/Themed list item instead.
    Box(modifier = modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)
    ) {
        label()
    }
}