package com.finance.lumora.presentation.settings



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(
    lastUpdated: String = "July 21, 2026",
    onBackClick: () -> Unit = {},
    onContactSupportClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Privacy Policy", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // --- HEADER SUMMARY CARD ---
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Shield,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Your Privacy is Our Priority",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Last updated: $lastUpdated",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- INTRO TEXT ---
            Text(
                text = "Welcome to Lumora. We are committed to protecting your personal information and your right to privacy. This Privacy Policy explains how we collect, use, and safeguard your data when you use our daily expense tracking app.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- POLICY SECTIONS ---
            PolicySection(
                icon = Icons.Outlined.Storage,
                title = "1. Information We Collect",
                content = listOf(
                    "Account Data: Name, email address, and authentication credentials when you sign up.",
                    "Financial Logs: Transaction amounts, category labels, timestamps, and currency preferences entered manually or synced via your account.",
                    "Device Data: Basic device model, operating system, and unique device identifiers to ensure secure login and app stability."
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            PolicySection(
                icon = Icons.Outlined.Lock,
                title = "2. How We Use & Protect Your Data",
                content = listOf(
                    "All financial transaction logs are encrypted in transit and at rest using industry-standard AES-256 encryption.",
                    "We use your data solely to provide expense insights, budget tracking alerts, and customized financial analytics.",
                    "We DO NOT sell, rent, or trade your personal or financial data to third-party advertisers."
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            PolicySection(
                icon = Icons.Outlined.Key,
                title = "3. Your Rights & Data Control",
                content = listOf(
                    "Export Data: You can export a copy of your expense data at any time via Settings.",
                    "Account Deletion: You hold the right to permanently delete your Lumora account and wipe all associated data from our servers.",
                    "Preferences: Control notification permissions and security features (such as biometric app lock) directly from app settings."
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            PolicySection(
                icon = Icons.Outlined.Cloud,
                title = "4. Third-Party Services",
                content = listOf(
                    "We utilize secure cloud infrastructure (such as Google Firebase) for authentication, database synchronization, and crash reporting.",
                    "These services comply strictly with modern data protection standards (GDPR / CCPA)."
                )
            )

            Spacer(modifier = Modifier.height(28.dp))

            // --- CONTACT SUPPORT CARD ---
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Questions or Concerns?",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "If you have questions about this policy or wish to exercise your data rights, please contact our privacy team.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedButton(
                        onClick = onContactSupportClick,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Outlined.Email, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Contact Privacy Team")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// --- SUB-COMPONENT FOR SECTIONS ---

@Composable
private fun PolicySection(
    icon: ImageVector,
    title: String,
    content: List<String>
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                content.forEachIndexed { index, point ->
                    Row(
                        modifier = Modifier.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "• ",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = point,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 18.sp
                        )
                    }
                    if (index < content.size - 1) {
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}