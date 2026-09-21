# Lumora — Personal Finance Tracker for Android

Lumora is a local-first personal finance app built for Android with Kotlin and Jetpack Compose. It combines a fully offline transaction and budgeting engine with an on-device AI assistant, receipt/voice-based transaction capture, and a background notification system that keeps users informed about their spending — even when the app is closed.

> Your financial data belongs to you. Lumora stores every transaction, category, and budget locally on-device using Room — nothing about your spending history is ever synced to a server.

---

## Why Lumora

Most expense trackers ask you to manually type every transaction, which is exactly why people stop using them within a week. Lumora is built around removing that friction:

- **Speak or snap a receipt** instead of typing — Aurix, Lumora's built-in AI assistant, extracts the amount, category, and note for you, then asks you to confirm before anything is saved.
- **Ask questions about your own spending** in plain language — "How much did I spend on food this month?" — answered from your real transaction history, not a generic script.
- **Get notified when it matters** — large expenses, budget thresholds, and monthly limits are tracked automatically, both while you're using the app and in the background.

---

## Features

### 📊 Dashboard
A personalized home screen with a friendly greeting, at-a-glance income/expense summaries, and quick access to recent activity.

### 💰 Transactions
Full CRUD for income and expense entries — add, edit, and delete transactions, each tied to a category and an optional note, with amount, type, and date tracked per entry.

### 🗂️ Categories
Custom category management — create, edit, and organize the categories that make sense for your own spending habits, beyond the app's defaults.

### 📈 Analytics
Visual breakdowns of income and expenses using pie and bar charts, with flexible time-range filters: **Today, This Week, This Month, Last Month, This Year,** and a fully **Custom** date range.

### 🔍 Transaction Search
Fast, filterable search across your transaction history — by type, category, amount, and date range — for finding a specific entry without scrolling.

### 🤖 Aurix — AI-Powered Finance Assistant
Built on **Firebase AI Logic** with **Gemini**, Aurix is Lumora's on-device-data-aware assistant:

- **OCR & voice-based transaction capture** — photograph a receipt or speak a transaction aloud, and Aurix drafts the entry automatically. Nothing is committed silently: every AI-drafted transaction is presented back to you for review and confirmation before it's saved.
- **Voice-based financial queries** — ask Aurix questions about your spending and income, answered directly from your own Room-stored transaction history.

### 📰 Finance News Broadcast
Stay current on markets and personal finance with two news feeds, powered by the **Marketaux** and **Finnhub** APIs:
- **Global financial news**
- **Daily local financial news**

### 🔔 Smart Notifications
A fully Room-backed, persistent in-app notification system, paired with real-time system-tray alerts:
- Transaction-added, income-added, and large-expense alerts, generated automatically as you use the app
- Budget warning and budget-exceeded alerts, evaluated both instantly and via a periodic **WorkManager** background job — so you're alerted even if Lumora isn't open
- User-configurable large-expense threshold and monthly budget

### ⚙️ Settings
Full control over the app experience: biometric app lock, light/dark/system theme, currency selection, push notification and budget alert toggles, monthly budget and large-expense threshold configuration, data export, cache clearing, and profile management.

### 🔐 Account & Profile
Secure sign-up and login via **Firebase Authentication**, with user profile data managed through **Cloud Firestore**.

---

## Architecture

Lumora follows **Clean Architecture** with a strict **MVI (Model-View-Intent)** pattern on top of Jetpack Compose:

```
presentation/   → Composable screens, ViewModels, UI state & one-time effects
domain/         → Use cases, repository interfaces, domain models (pure Kotlin, no Android/Firebase types)
data/           → Room entities/DAOs, Firestore data sources, repository implementations, mappers
```

**Key architectural decisions:**

- **Offline-first by design.** All financial data (transactions, categories, budgets) lives in a local **Room** database. Firebase is used *only* where it genuinely needs to be — authentication, the news feed, and the Aurix AI assistant — never for core financial data.
- **Reactive data flow throughout**, using Kotlin `Flow` and `StateFlow` from the DAO layer up through the ViewModel, so the UI stays in sync with the database automatically.
- **A real, tested Room migration**, not a destructive fallback — schema changes preserve existing user data across app updates.
- **Background-safe notifications.** Budget evaluation runs both synchronously (right after a transaction is saved) and periodically via a **Hilt-integrated WorkManager** job, with careful handling of Android's notification permission model and correct behavior across app restarts and user preference changes.
- **Dependency injection with Hilt** across every layer — repositories, use cases, ViewModels, and WorkManager workers.

---

## Tech Stack

| Layer | Technology |
|---|---|
| **Language** | Kotlin |
| **UI** | Jetpack Compose, Material 3 |
| **Architecture** | Clean Architecture + MVI |
| **Local persistence** | Room (with versioned migrations) |
| **Preferences** | Jetpack DataStore |
| **Dependency Injection** | Hilt |
| **Background work** | WorkManager (Hilt-integrated) |
| **Async** | Kotlin Coroutines & Flow |
| **Authentication & Cloud** | Firebase Authentication, Cloud Firestore, Firebase App Check |
| **AI** | Firebase AI Logic (Gemini) |
| **OCR** | ML Kit Text Recognition |
| **Camera** | CameraX |
| **Networking** | Retrofit, Gson |
| **News data** | Marketaux API, Finnhub API |
| **Biometrics** | AndroidX Biometric |
| **Image loading** | Coil |

---

## What Makes This Project Different

This isn't a tutorial-following CRUD app. A few things I deliberately got right, and can speak to in depth:

- **A genuine, tested v2 → v3 Room migration** — hand-written `Migration` SQL verified against the Room-exported schema, with a real device upgrade test (existing transaction data confirmed intact before shipping), rather than relying on `fallbackToDestructiveMigration()`.
- **A dual-surface notification system** — every alert can reach the user both as a system-tray push and as a persistent in-app record, correctly reconciled so a denied OS permission never silently breaks the in-app history.
- **Correct WorkManager lifecycle handling** — the periodic budget-check worker respects the user's notification preference across app restarts (found and fixed a real bug where disabling alerts didn't survive a restart — verified the fix with Android Studio's Background Task Inspector, not just by reading the code).
- **AI output is never auto-committed.** Every transaction Aurix drafts from a receipt or voice note is shown back to the user for confirmation — respecting that ML extraction isn't perfect, without sacrificing the speed benefit of not typing everything by hand.

---

## Screenshots

*(Add screenshots or a short demo GIF here — a Dashboard, Aurix in action, and the Analytics charts are the strongest first impression.)*

---

## Getting Started

```bash
git clone https://github.com/<your-username>/lumora.git
```

1. Open the project in **Android Studio** (Giraffe or newer recommended).
2. Add your `google-services.json` file to the `app/` directory (required for Firebase Auth, Firestore, and Firebase AI).
3. Add your API keys to `local.properties`:
   ```properties
   MARKETAUX_API_KEY=your_key_here
   ```
4. Sync Gradle and run on a physical device or emulator (minSdk 36 / targetSdk 36).

---

## Roadmap

- [ ] Recurring transactions
- [ ] Multi-currency support with live conversion
- [ ] Data export to PDF (CSV already supported)
- [ ] Widget support for at-a-glance balance

---

## License

*(Add your license here — MIT is a common, permissive choice for portfolio projects.)*

---

## Contact

Built by **Kasare Omkar** — [LinkedIn: https://www.linkedin.com/in/omkar-kasare-aa54a6180/](#)· [Portfolio](#) · [kasareomkar77@gmail.com](#)
