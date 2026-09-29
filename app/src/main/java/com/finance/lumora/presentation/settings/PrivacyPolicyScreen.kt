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
                    "Account Data: Your name, email address, and profile photo (if provided) when you sign up, stored securely via Firebase Authentication and Cloud Firestore.",
                    "Financial Data: Transactions, categories, and budgets you enter are stored only on your device using a local database. This data is not uploaded to our servers or synced across devices.",
                    "Aurix AI Assistant: When you use Aurix to scan a receipt, speak a transaction, or ask a financial question, the relevant text (receipt text, voice transcript, or a summary of your spending) is sent to Google's Gemini model via Firebase AI Logic to generate a response or draft transaction. This data is processed to answer your request and is not stored by us beyond the session.",
                    "News Content: Financial news shown in the app is retrieved from third-party providers, Marketaux and Finnhub, based on general market and regional data, not your personal financial information.",
                    "Device Data: Basic device model, operating system version, and app version, used for crash reporting and to keep the app stable and secure."
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            PolicySection(
                icon = Icons.Outlined.Lock,
                title = "2. How We Use & Protect Your Data",
                content = listOf(
                    "Your transactions, categories, and budgets stay on your device. We do not have access to this data, and it is not transmitted to Lumora's servers.",
                    "Account data (name, email, profile photo) is stored in Firebase, protected by Firebase Authentication and Firestore security rules, and Firebase App Check to help ensure only the genuine Lumora app can access it.",
                    "We use your account data solely to provide sign-in, keep your profile in sync if you use multiple devices, and personalize app settings such as currency and theme.",
                    "We DO NOT sell, rent, or trade your personal or financial data to third-party advertisers."
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            PolicySection(
                icon = Icons.Outlined.Key,
                title = "3. Your Rights & Data Control",
                content = listOf(
                    "Export Data: You can export a copy of your transaction history as a CSV file at any time via Settings.",
                    "Local Data Removal: Since your financial data lives only on your device, uninstalling the app permanently removes it from that device.",
                    "Account Deletion: To request deletion of your account data (name, email, profile photo) from our servers, contact our privacy team using the button below. We will process your request within a reasonable time.",
                    "Preferences: Control notification permissions and security features, such as biometric app lock, directly from app settings."
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            PolicySection(
                icon = Icons.Outlined.Cloud,
                title = "4. Third-Party Services",
                content = listOf(
                    "Google Firebase: Used for account authentication, storing your profile data, AI-powered features (Aurix, via Firebase AI Logic and Gemini), and app integrity checks (App Check).",
                    "Marketaux and Finnhub: Used to retrieve financial news content shown in the app.",
                    "These providers process data under their own privacy policies and applicable data protection standards."
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