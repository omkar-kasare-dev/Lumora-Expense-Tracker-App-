package com.finance.lumora.presentation.ai.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.finance.lumora.presentation.ai.components.AurixDrawerContent
import com.finance.lumora.presentation.ai.viewmodel.AurixDrawerViewModel
import com.finance.lumora.presentation.ai.viewmodel.AurixViewModel
import kotlinx.coroutines.launch

private sealed interface AurixDrawerDestination {
    data object Main : AurixDrawerDestination
    data object History : AurixDrawerDestination
    data object Export : AurixDrawerDestination
    data object ModelSettings : AurixDrawerDestination
    data object Help : AurixDrawerDestination
    data object About : AurixDrawerDestination
}

@Composable
fun AurixDrawerHost(
    onBackClick: () -> Unit,
    aurixViewModel: AurixViewModel = hiltViewModel(),
    drawerViewModel: AurixDrawerViewModel = hiltViewModel()
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var destination by remember { mutableStateOf<AurixDrawerDestination>(AurixDrawerDestination.Main) }

    val messages by aurixViewModel.messages.collectAsState()
    val drawerUiState by drawerViewModel.uiState.collectAsState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AurixDrawerContent(
                smartInsightsEnabled = drawerUiState.smartInsightsEnabled,
                autoContextEnabled = drawerUiState.autoContextEnabled,
                onSmartInsightsToggle = drawerViewModel::setSmartInsightsEnabled,
                onAutoContextToggle = drawerViewModel::setAutoContextEnabled,
                onNewChatClick = {
                    aurixViewModel.clearConversation()
                    scope.launch { drawerState.close() }
                },
                onHistoryClick = {
                    destination = AurixDrawerDestination.History
                    scope.launch { drawerState.close() }
                },
                onExportChatClick = {
                    destination = AurixDrawerDestination.Export
                    scope.launch { drawerState.close() }
                },
                onSettingsClick = {
                    destination = AurixDrawerDestination.ModelSettings
                    scope.launch { drawerState.close() }
                },
                onHelpClick = {
                    destination = AurixDrawerDestination.Help
                    scope.launch { drawerState.close() }
                },
                onAboutClick = {
                    destination = AurixDrawerDestination.About
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when (destination) {
                AurixDrawerDestination.Main -> AurixScreen(
                    onBackClick = onBackClick,
                    onMenuClick = { scope.launch { drawerState.open() } },
                    smartInsightsEnabled = drawerUiState.smartInsightsEnabled,
                    viewModel = aurixViewModel
                )

                AurixDrawerDestination.History -> AurixHistoryScreen(
                    messages = messages,
                    onBackClick = { destination = AurixDrawerDestination.Main }
                )

                AurixDrawerDestination.Export -> AurixExportScreen(
                    messages = messages,
                    onBackClick = { destination = AurixDrawerDestination.Main }
                )

                AurixDrawerDestination.ModelSettings -> AurixModelSettingsScreen(
                    smartInsightsEnabled = drawerUiState.smartInsightsEnabled,
                    autoContextEnabled = drawerUiState.autoContextEnabled,
                    onSmartInsightsToggle = drawerViewModel::setSmartInsightsEnabled,
                    onAutoContextToggle = drawerViewModel::setAutoContextEnabled,
                    onClearConversation = { aurixViewModel.clearConversation() },
                    onBackClick = { destination = AurixDrawerDestination.Main }
                )

                AurixDrawerDestination.Help -> AurixHelpScreen(
                    onBackClick = { destination = AurixDrawerDestination.Main }
                )

                AurixDrawerDestination.About -> AurixAboutScreen(
                    onBackClick = { destination = AurixDrawerDestination.Main }
                )
            }
        }
    }
}