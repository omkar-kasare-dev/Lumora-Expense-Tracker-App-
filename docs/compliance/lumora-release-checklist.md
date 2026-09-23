# Lumora Android Release Checklist

> **Purpose:** Pre-deployment operational checklist for building, signing, testing, and submitting the Lumora Android application to the Google Play Store.

---

## 1. Pre-Build Gates

### 1.1 Quality & Automated Tests
- [ ] Unit tests passing (`./gradlew testDebugUnitTest`).
- [ ] Android UI / Instrumentation tests passing (`./gradlew connectedAndroidTest`).
- [ ] Lint inspection clean with zero fatal issues (`./gradlew lintRelease`).
- [ ] Dependency vulnerabilities checked (`./gradlew dependencyCheckAnalyze` / Snyk).

### 1.2 Versioning & Configuration (`build.gradle.kts`)
- [ ] `versionCode` incremented sequentially.
- [ ] `versionName` updated following Semantic Versioning (e.g., `1.2.0`).
- [ ] `debuggable` flag set to `false`.
- [ ] Unnecessary logging (`Log.d`, `Log.v`) stripped or disabled via ProGuard/Timber.

---

## 2. Build, Obfuscation & Signing

### 2.1 ProGuard / R8 Rules
- [ ] R8 shrinking and obfuscation enabled (`minifyEnabled true`, `shrinkResources true`).
- [ ] ProGuard rules verified (`proguard-rules.pro`) to prevent stripping required data classes, reflection calls, or SDK models.

### 2.2 Signing & Artifact Generation
- [ ] Build signed using the official production Release Keystore (Play App Signing or Upload Key).
- [ ] Generated Android App Bundle (`.aab`) via `./gradlew bundleRelease`.
- [ ] Keystore passwords and aliases kept secure and never committed to version control.

---

## 3. Google Play Console Submission

- [ ] Upload `.aab` to target track (Internal Testing / Alpha / Beta / Production).
- [ ] De-obfuscation mapping file (`mapping.txt`) uploaded to Play Console / Sentry for crash trace readable symbolication.
- [ ] Play Store release notes (What's New) populated in all target languages.

---

## 4. Post-Release Verification

- [ ] Download build from Play Store Internal Track / Production and execute smoke tests:
    - [ ] App launches without immediate crash
    - [ ] User login / authentication flow
    - [ ] Primary features operational
    - [ ] In-App Purchases / Subscriptions working
- [ ] Sentry / Google Play Vitals monitored for spike in crashes or ANRs (App Not Responding).

---

## Deployment Sign-Off

| Milestone | Verified By | Date / Time | Pass/Fail |
| :--- | :--- | :--- | :--- |
| **Build & Test Verification** | ____________________ | YYYY-MM-DD HH:MM | [ ] PASS |
| **Play Policy Compliance** | ____________________ | YYYY-MM-DD HH:MM | [ ] PASS |
| **Play Store Upload** | ____________________ | YYYY-MM-DD HH:MM | [ ] PASS |