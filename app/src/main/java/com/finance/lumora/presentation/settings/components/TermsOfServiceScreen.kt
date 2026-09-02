package com.finance.lumora.presentation.settings.components



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
fun TermsOfServiceScreen(
    lastUpdated: String = "July 21, 2026",
    onBackClick: () -> Unit = {},
    onContactSupportClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Terms of Service", fontWeight = FontWeight.Bold) },
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
                        imageVector = Icons.Outlined.Gavel,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Terms & Conditions",
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
                text = "Welcome to Lumora. By downloading, accessing, or using our app, you agree to be bound by these Terms of Service. Please read them carefully to understand your rights and responsibilities when using our services.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- TERMS SECTIONS ---
            TermsSection(
                icon = Icons.Outlined.Person,
                title = "1. Account Responsibility",
                content = listOf(
                    "Account Security: You are responsible for maintaining the confidentiality of your credentials and account activity.",
                    "Eligibility: You must be at least 18 years old or possess legal parental consent to use Lumora.",
                    "Accuracy: You agree to provide accurate registration information and keep your details up to date."
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            TermsSection(
                icon = Icons.Outlined.AccountBalanceWallet,
                title = "2. Financial Disclaimer",
                content = listOf(
                    "Informational Purpose: Lumora provides personal expense tracking and financial tools for informational purposes only.",
                    "Not Financial Advice: Content provided within the app does not constitute professional financial, investment, or legal advice.",
                    "User Input: You acknowledge that analytics and budget reports are based directly on data you input or connect."
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            TermsSection(
                icon = Icons.Outlined.CheckCircle,
                title = "3. Acceptable Use",
                content = listOf(
                    "Prohibited Activities: You may not attempt to reverse engineer, exploit bugs, or interfere with Lumora's services.",
                    "Lawful Use: You agree to use the application solely for lawful personal finance management.",
                    "Termination: We reserve the right to suspend accounts that violate our terms or engage in fraudulent activity."
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            TermsSection(
                icon = Icons.Outlined.Verified,
                title = "4. Intellectual Property & Updates",
                content = listOf(
                    "Ownership: All logos, trademarks, code, and UI elements remain the exclusive property of Lumora.",
                    "App Modifications: We reserve the right to modify features, push software updates, or adjust pricing plans with prior notice."
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
                        text = "Questions About Terms?",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "If you have questions regarding these terms or need further clarification, our support team is available to assist.",
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
                        Text("Contact Legal Team")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// --- SUB-COMPONENT FOR SECTIONS ---

@Composable
private fun TermsSection(
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