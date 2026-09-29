# Lumora Permission Audit Log

> **Purpose:** Document every Android permission requested in `AndroidManifest.xml`, with technical justification and the user-facing feature it supports. Lumora is a solo, self-developed project with no backend server — every permission below is used strictly on-device or to reach Firebase / third-party APIs directly.

---

## 1. Android Manifest Permission Matrix (`AndroidManifest.xml`)

| Permission Name | Protection Level | Technical Justification | User-Facing Feature | Risk Level |
| :--- | :--- | :--- | :--- | :--- |
| `android.permission.INTERNET` | Normal | Required for Firebase Authentication, Cloud Firestore, Firebase AI Logic (Aurix/Gemini), and the Marketaux/Finnhub news APIs. Lumora has no backend server of its own. | Sign-in, Aurix AI assistant, News feed | Low |
| `android.permission.ACCESS_NETWORK_STATE` | Normal | Checks connectivity so network-dependent features (News, Aurix, sign-in) can show a clear offline state instead of hanging or silently failing. | Network-aware error states | Low |
| `android.permission.CAMERA` | Dangerous (Runtime) | Captures a photo of a receipt for Aurix's OCR-based transaction capture (CameraX). | Aurix receipt scanning | Medium |
| `android.permission.RECORD_AUDIO` | Dangerous (Runtime) | Captures voice input for Aurix's voice-based transaction capture and voice financial queries (Android `SpeechRecognizer`). | Aurix voice capture | Medium |
| `android.permission.POST_NOTIFICATIONS` | Dangerous (Runtime), Android 13+ | Displays system-tray alerts for budget warnings/exceeded and, when enabled, transaction-added and large-expense alerts. | Push notifications, budget alerts | Low |
| `android.permission.USE_BIOMETRIC` | Normal | Enables optional fingerprint/face-unlock app lock via AndroidX Biometric. Never used to gate financial data access beyond the local app-lock screen. | Biometric app lock (opt-in, Settings) | Low |

**Note on photo selection:** Lumora's profile-photo picker uses Android's system Photo Picker (`ActivityResultContracts.GetContent()`), which does **not** require `READ_MEDIA_IMAGES` or any storage permission on the OS versions Lumora targets. No such permission is declared or requested.

**Not requested, and not needed by any current feature:** `ACCESS_FINE_LOCATION` / `ACCESS_COARSE_LOCATION`, `READ_PHONE_STATE`, `READ_MEDIA_IMAGES`, `READ_EXTERNAL_STORAGE`, contacts, or SMS permissions.

---

## 2. Permission Handling Rules

- [x] **Runtime prompting in context:** Camera, microphone, and notification permissions are requested only when the user takes the corresponding action (tapping Aurix's camera/mic buttons, or enabling notifications in Settings) — never on cold boot.
- [x] **Graceful denial handling:** If a permission is denied, the relevant feature (Aurix capture, notifications) is skipped or disabled with an in-app message; the rest of the app remains fully usable. Biometric lock similarly falls back to no-op if biometrics aren't enrolled or available on the device.
- [x] **No unnecessary permissions:** Every permission above maps to a real, currently-shipped feature. Nothing is requested speculatively for future features.

---

## Audit Certification

**Audited by:** Omkar Kasare (solo developer)
**Date:** _____________