# Lumora Android SDK & Data Audit

> **Purpose:** Inventory all third-party SDKs and dependencies integrated via Gradle in Lumora, tracking data collection and network transmission.

---

## 1. SDK Inventory & Telemetry Map

| SDK / Dependency | Version | Category | Data Collected | Off-Device Destination | Privacy Policy Link |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Lumora Core API** | v1.0 | Primary Backend | User ID, Email, IP Address, Android Device Model | Lumora Servers | `https://lumora.io/privacy` |
| **Firebase Analytics** | 32.x | Analytics | App Events, Session Duration, Android OS Version, GAID | Google LLC | `https://policies.google.com/privacy` |
| **Sentry Android** | 7.x | Crash Reporting | Stack Traces, ANR Reports, Memory Snapshots, Device Model | Functional Software Inc. | `https://sentry.io/privacy` |
| **Google Play Billing** | 6.x | In-App Purchases | Purchase Tokens, Subscription Status, Product IDs | Google LLC | `https://policies.google.com/privacy` |

---

## 2. Security & Data Storage Audit

### 2.1 Network Security (`network_security_config.xml`)
- [ ] **HTTPS Enforcement:** Plaintext HTTP traffic (`cleartextTrafficPermitted="false"`) is disabled for production builds.
- [ ] **TLS Standard:** All API connections enforce TLS 1.2 or 1.3.

### 2.2 Local Storage Security
| Data Type | Storage Mechanism | Encryption Standard | Sensitivity |
| :--- | :--- | :--- | :--- |
| **Auth Tokens** | `EncryptedSharedPreferences` / Android KeyStore | AES-256 GCM | High |
| **User Settings** | Standard `SharedPreferences` / DataStore | Unencrypted (Non-sensitive) | Low |
| **Cached Media** | Application Cache Directory (`Context.cacheDir`) | OS-level isolation | Medium |

---

## 3. Google Play Data Safety Alignment

- [ ] All data collected by third-party SDKs (such as Sentry ANR reports or Firebase IDs) matches the declarations in the Google Play Console Data Safety form.

---

**Audited By:** Security & Android Engineering Team  
**Last Review Date:** YYYY-MM-DD