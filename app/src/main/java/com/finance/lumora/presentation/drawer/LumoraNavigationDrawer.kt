package com.finance.lumora.presentation.drawer



import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance.lumora.presentation.drawer.components.DrawerHeader
import com.finance.lumora.presentation.drawer.components.DrawerMenuData
import com.finance.lumora.presentation.drawer.components.DrawerMenuSection

@Composable
fun LumoraNavigationDrawer(

    drawerState: DrawerState,

    selectedRoute: String,

    onHomeClick: () -> Unit,

    onTransactionClick: () -> Unit,

    onCategoryClick: () -> Unit,

    onSettingsClick: () -> Unit,

    onAboutClick: () -> Unit,

    onRateAppClick: () -> Unit,

    content: @Composable () -> Unit

) {

    ModalNavigationDrawer(

        drawerState = drawerState,

        drawerContent = {

            ModalDrawerSheet(

                modifier = Modifier
                    .width(320.dp)
                    .fillMaxHeight(),

                drawerContainerColor = MaterialTheme.colorScheme.surface

            ) {

                Column(

                    modifier = Modifier
                        .fillMaxHeight()
                        .verticalScroll(
                            rememberScrollState()
                        )

                ) {

                    //--------------------------------------------------
                    // Header
                    //--------------------------------------------------

                    DrawerHeader(

                        userName = "Omkar Kasare",

                        userTagLine = "Track every rupee wisely"

                    )

                    //--------------------------------------------------
                    // Main Section
                    //--------------------------------------------------

                    DrawerMenuSection(

                        title = "Main",

                        menuItems = listOf(

                            DrawerMenuData(

                                title = "Home",

                                icon = Icons.Default.Home,

                                selected = selectedRoute == "home",

                                onClick = onHomeClick

                            ),

                            DrawerMenuData(

                                title = "Transactions",

                                icon = Icons.Default.Payments,

                                selected = selectedRoute == "transactions",

                                onClick = onTransactionClick

                            ),

                            DrawerMenuData(

                                title = "Categories",

                                icon = Icons.Default.Category,

                                selected = selectedRoute == "categories",

                                onClick = onCategoryClick

                            )

                        )

                    )

                    //--------------------------------------------------
                    // Preferences
                    //--------------------------------------------------

                    DrawerMenuSection(

                        title = "Preferences",

                        menuItems = listOf(

                            DrawerMenuData(

                                title = "Settings",

                                icon = Icons.Default.Settings,

                                selected = selectedRoute == "settings",

                                onClick = onSettingsClick

                            ),

                            DrawerMenuData(

                                title = "Rate App",

                                icon = Icons.Default.Star,

                                selected = false,

                                onClick = onRateAppClick

                            ),

                            DrawerMenuData(

                                title = "About Lumora",

                                icon = Icons.Default.Info,

                                selected = selectedRoute == "about",

                                onClick = onAboutClick

                            )

                        )

                    )

                    //--------------------------------------------------
                    // Footer
                    //--------------------------------------------------

                    Surface {

                        androidx.compose.material3.Text(

                            text = "Version 1.0.0",

                            modifier = Modifier
                                .width(320.dp)
                                .padding(20.dp),

                            style = MaterialTheme.typography.bodySmall,

                            color = MaterialTheme.colorScheme.onSurfaceVariant

                        )

                    }

                }

            }

        }

    ) {

        content()

    }

}