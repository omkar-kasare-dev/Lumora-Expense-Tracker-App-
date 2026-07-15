package com.finance.lumora.presentation.home.components

/*

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance.lumora.presentation.drawer.components.DrawerHeader

@Composable
fun NavigationDrawer(

    drawerState: DrawerState,

    currentRoute: String,

    onHomeClick: () -> Unit,

    onTransactionClick: () -> Unit,

    onCategoryClick: () -> Unit,

    onBudgetClick: () -> Unit,

    onReportsClick: () -> Unit,

    onSettingsClick: () -> Unit,

    onAboutClick: () -> Unit,

    onLogoutClick: () -> Unit,

    content: @Composable () -> Unit

) {

    ModalNavigationDrawer(

        drawerState = drawerState,

        drawerContent = {

            ModalDrawerSheet(

                modifier = Modifier.width(300.dp)

            ) {

                DrawerHeader()

                DrawerMenuList(

                    currentRoute = currentRoute,

                    onHomeClick = onHomeClick,

                    onTransactionClick = onTransactionClick,

                    onCategoryClick = onCategoryClick,

                    onBudgetClick = onBudgetClick,

                    onReportsClick = onReportsClick,

                    onSettingsClick = onSettingsClick,

                    onAboutClick = onAboutClick,

                    onLogoutClick = onLogoutClick

                )

            }

        }

    ) {

        androidx.compose.foundation.layout.Box(
            modifier = Modifier.fillMaxSize()
        ) {
            content()
        }

    }

}

 */