# Lumora Policy Compliance Checklist

> **Purpose:** Confirm the Lumora Android app is ready for Google Play submission and reasonably aligned with GDPR/CCPA principles. Lumora is a solo, self-developed project — this checklist is completed by one person, not a compliance team, and is scoped to what the app actually does: no backend server, no ad SDKs, no analytics SDKs, and financial data stored only on-device.

---

## 1. Google Play Store Policies

### 1.1 Play Console Data Safety Section
- [ ] **Accurate declarations:** Data Safety form reflects reality — financial data (transactions, categories, budgets) is **not collected** by Lumora (device-only, Room database); account data (name, email, profile photo) **is** collected via Firebase; Aurix sends receipt/voice text and spending summaries to Google's Gemini via Firebase AI Logic for processing only, not storage.
- [ ] **Data encryption in transit:** All network calls (Firebase SDKs, Marketaux, Finnhub) use HTTPS/TLS by default via their respective SDKs/Retrofit config. No custom cleartext endpoints exist.
- [ ] **Account deletion:** No in-app self-service deletion exists yet. Until it does, the Privacy Policy directs users to request deletion via email, and that request is honored manually. *(If Google requires an in-app or web-based deletion flow before publishing, this needs to be built before submission — flag as a real gap, not assumed done.)*

### 1.2 Target SDK & Behavioral Policies
- [ ] **Target API level:** Confirm `targetSdk` in `app/build.gradle.kts` meets Play's current minimum requirement at time of submission (was 36 as of this session — verify against Play's current policy, since this requirement changes yearly).
- [ ] **Prominent disclosure before sensitive permissions:** Camera and microphone permissions are requested only when the user taps Aurix's camera/mic action, with the action's purpose visible in the UI at that moment (no separate disclosure screen currently exists — the in-context request itself is the disclosure; confirm this meets Play's bar or add an explicit rationale dialog if not).
- [ ] **Background/foreground services:** The only background work is a WorkManager periodic job (`BudgetAlertWorkScheduler`) that re-evaluates budget alerts roughly every 6 hours. It is not a foreground service, requires no `foregroundServiceType`, and only runs when the user has enabled Budget Alerts in Settings.

---

## 2. Data Privacy & Regulations

### 2.1 GDPR & CCPA/CPRA
- [ ] **Legal basis:** Account data (name, email, photo) is collected under consent given at sign-up. Financial data is never collected by Lumora at all — it exists solely on the user's device, so no legal basis question applies to it.
- [ ] **Data export:** CSV export of transaction history exists in Settings today — this satisfies a reasonable portability request for financial data. Account data (name/email) export would need to be handled manually on request until a self-service option exists.
- [ ] **Right to erasure:** For financial data — uninstalling the app achieves this, since nothing is stored server-side. For account data in Firebase — currently manual, handled via the privacy contact email (see 1.1). No automated 30-day server-side wipe process exists yet, since account records are minimal (name, email, photo) and deletion is a direct Firebase Auth/Firestore document removal once requested.
- [ ] **Data minimization:** Confirmed — see Permission Audit Log. Only account identity fields and the minimum permissions needed for Aurix/notifications are requested.

### 2.2 COPPA & Age Restrictions
- [ ] **Target audience:** Confirm Play Console's target-age configuration matches the Terms of Service, which currently states users must be 18+ or have parental consent.
- [ ] **Child data protections:** Not applicable in the sense of ad/tracking SDKs, since Lumora integrates none. If the target audience configuration in Play Console is ever set to include under-13 users, this section would need real re-evaluation (it currently is not, per the Terms).

---

## 3. Open Source & Licensing

- [ ] **Third-party Android libraries:** Gradle dependencies (Jetpack Compose, Hilt, Room, Retrofit, Coil, ML Kit, CameraX, Firebase SDKs, AndroidX Biometric) are all standard Apache 2.0 / MIT-licensed libraries. No unusual or restrictively-licensed dependencies are in use as of this session.
- [ ] **Asset rights:** Confirm any custom app icon, splash logo (`lumora1_logo`), and fonts used are either originally created or properly licensed for commercial app distribution.

---

## Sign-Off

| Role | Name | Date | Status |
| :--- | :--- | :--- | :--- |
| **Developer (all roles)** | Omkar Kasare | __________ | [ ] Reviewed |