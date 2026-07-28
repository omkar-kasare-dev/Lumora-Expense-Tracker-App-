package com.finance.lumora.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.finance.lumora.presentation.auth.session.SessionViewModel
import com.finance.lumora.presentation.profile.viewmodel.ProfileViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.ui.platform.LocalLocale

/*
// Data Class Matching Your Model
data class UserProfile(
    val uid: String,
    val name: String,
    val email: String,
    val photoUrl: String,
    val createdAtMillis: Long,
    val lastLoginMillis: Long,
    val currency: String,
    val theme: String,
    val notificationsEnabled: Boolean
)

 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onEditProfileClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    viewModel: ProfileViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {

        viewModel.loadProfile()

    }

    //-------------------------------------------


    val user = uiState.profile

    if (uiState.isLoading) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        return
    }

    if (uiState.errorMessage != null) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Text(uiState.errorMessage!!)

        }

        return

    }

    if (user == null) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading profile...")
        }

        return
    }



    //------------------------------
    var isNotificationsEnabled by remember(user.notificationsEnabled) {

        mutableStateOf(user.notificationsEnabled)

    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onEditProfileClick) {
                        Icon(Icons.Outlined.Edit, contentDescription = "Edit Profile")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- HEADER SECTION ---
            ProfileHeader(
                name = user.fullName,
                email = user.email
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- ACCOUNT SETTINGS SECTION ---
            SectionHeader(title = "App Preferences")
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column {
                    ProfileItem(
                        icon = Icons.Outlined.AttachMoney,
                        title = "Currency",
                        value = user.currency
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.outlineVariant)
                    ProfileItem(
                        icon = Icons.Outlined.Palette,
                        title = "Theme",
                        value = user.theme.replaceFirstChar {
                            if (it.isLowerCase()) {
                                it.titlecase(LocalLocale.current.platformLocale)
                            } else {
                                it.toString()
                            }
                        }
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.outlineVariant)
                    ProfileToggleItem(
                        icon = Icons.Outlined.Notifications,
                        title = "Push Notifications",
                        checked = isNotificationsEnabled,
                        onCheckedChange = { isNotificationsEnabled = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- ACCOUNT INFORMATION SECTION ---
            SectionHeader(title = "Account Details")
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column {
                    ProfileItem(
                        icon = Icons.Outlined.Fingerprint,
                        title = "User ID",
                        value = user.uid
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.outlineVariant)
                    ProfileItem(
                        icon = Icons.Outlined.CalendarToday,
                        title = "Member Since",
                        value = formatDate(user.createdAt)
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.outlineVariant)
                    ProfileItem(
                        icon = Icons.Outlined.Schedule,
                        title = "Last Active",
                        value = formatDate(user.lastLogin)
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // --- LOGOUT BUTTON ---
            OutlinedButton(
                onClick = onLogoutClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Icon(Icons.AutoMirrored.Outlined.Logout, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Log Out", fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// --- SUB-COMPONENTS ---

@Composable
fun ProfileHeader(name: String, email: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name.take(1).uppercase(),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Text(text = email, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
fun SectionHeader(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, bottom = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ProfileItem(icon: ImageVector, title: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
        }
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ProfileToggleItem(
    icon: ImageVector,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

private fun formatDate(timestampMillis: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return sdf.format(Date(timestampMillis))
}