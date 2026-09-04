# Lumora — Smart Expense Tracker

Lumora is a modern Android expense tracker built with **Kotlin, Jetpack Compose, Room, DataStore, Hilt, Firebase and Clean Architecture**.

The goal of Lumora is simple: make everyday expense tracking useful, clear and easy to understand — not just another app where you enter an amount and forget about it.

The project has grown from a basic expense tracker into a complete personal finance application with analytics, budgets, notifications, security features, data management and an AI assistant called **AURIX**.

---

## ✨ What Lumora Can Do

### 💰 Expense & Income Management
- Add income and expense transactions
- Edit and delete transactions
- Organize transactions using categories
- View transaction details
- Search transactions
- Track total income, expenses and balance

### 🗂️ Categories
- Create custom categories
- Edit and delete categories
- Select category icons
- Select category colors
- Use categories throughout transaction and analytics features

### 📊 Analytics & Reports
- Monthly financial summary
- Income vs Expense analysis
- Category-wise spending analysis
- Spending percentages
- Transaction count
- Monthly financial statistics
- Visual analytics dashboard

### 💳 Budget Management
- Set monthly budget
- Track budget usage
- Calculate remaining budget
- Budget warning levels
- Critical budget alerts
- Budget exceeded alerts

### 🔔 Notifications
- Push notification support
- Budget alert notifications
- Notification permission handling
- Budget alert testing
- Notification preferences from Settings

### 🔐 Security
- Biometric app lock
- Authentication/session handling
- Change password
- Protected application access
- Firebase App Check for AI services

### ⚙️ Settings & Profile
- Profile and account information
- Primary currency selection
- App theme selection
- Monthly budget settings
- Notification preferences
- Budget alert preferences
- Biometric lock settings
- About Lumora
- Privacy and terms sections

### 💾 Data Management
- Export transaction data to CSV
- Share exported data using Android Share Sheet
- Clear temporary/cache data
- Confirmation before destructive actions

### 🤖 AURIX — AI Finance Assistant
- Ask questions about monthly spending
- Understand category-wise spending
- Check budget status
- Get practical expense-saving suggestions
- Ask multiple financial questions in one message
- Use conversation history
- Currency-aware answers
- Friendly error handling
- Retry failed requests
- Copy AURIX responses
- Clear conversations

---

# 🧱 Tech Stack

**Language:** Kotlin

**UI:** Jetpack Compose + Material 3

**Architecture:** Clean Architecture + MVVM

**Database:** Room

**Preferences:** DataStore

**DI:** Hilt

**Async:** Coroutines + Flow/StateFlow

**Backend:** Firebase Authentication + Firestore

**AI:** Firebase AI Logic + Gemini

**Security:** Biometric Authentication + Firebase App Check

**Version Control:** Git + GitHub

---

# 🏗️ Architecture

Lumora follows a layered architecture:

```text
Presentation
     ↓
Domain
     ↓
Data
     ↓
Room / DataStore / Firebase
```

The main idea is to keep UI code separate from business logic and data access.

For AURIX, the flow is:

```text
AURIX UI
   ↓
AurixViewModel
   ↓
AskAurixUseCase
   ↓
FinanceContextBuilder
   ↓
Existing Lumora Finance Logic
   ↓
Gemini
```

AURIX does **not** directly access Room or modify financial data.

> **Lumora owns the financial data. AURIX explains it.**

---

# 🤖 AURIX

AURIX is not a generic chatbot added on top of the application.

It is designed specifically around Lumora's financial data.

For example:

```text
User:
"How much did I spend this month?"

        ↓

Lumora calculates the actual data

        ↓

FinanceContext

        ↓

AURIX + Gemini

        ↓

Human-readable answer
```

This means the AI doesn't need to guess the user's spending.

## Finance Context

AURIX currently receives information such as:

- Current month
- Currency
- Total income
- Total expense
- Balance
- Transaction count
- Monthly budget
- Budget remaining
- Budget usage
- Category-wise expenses

The financial values are calculated by Lumora's existing domain logic.

No duplicate financial calculation system was created for AURIX.

---

# 🔥 Gemini Integration

AURIX uses **Firebase AI Logic** with the Gemini model.

The current integration uses:

```text
Firebase AI Logic
        ↓
Google AI backend
        ↓
Gemini 3.7 Flash
```

The Gemini service is hidden behind a domain interface:

```text
GeminiService
      ↓
GeminiServiceImpl
      ↓
Firebase AI Logic
```

This keeps the rest of the application independent from the Gemini SDK.

---

# 🛡️ App Check

Firebase App Check has been added to protect the AI integration.

The project currently uses:

- Debug App Check provider for local development
- Separate release implementation for production configuration

The debug configuration should **not** be shipped as the production security configuration.

---

# 🧠 AURIX Prompt Design

AURIX has its own prompt builder instead of putting a large prompt directly inside the UI or ViewModel.

The prompt contains:

- AURIX behavior rules
- Conversation history
- Financial context
- Current user question
- Response formatting rules

Important rules include:

- Don't invent financial numbers
- Don't assume missing financial information
- Use the currency supplied by Lumora
- Don't let conversation history override financial context
- Don't claim to modify financial data
- Explain budget overruns correctly
- Answer the actual question first
- Keep responses readable and concise

This also gives AURIX basic protection against prompt injection attempts.

---

# 💬 AURIX Chat

The current chat UI includes:

- Welcome screen
- Suggested questions
- User message cards
- AURIX response cards
- Loading animation
- Error state
- Retry action
- Copy response
- Clear conversation
- Multi-line input
- Send button
- Automatic scrolling

The conversation is currently kept in ViewModel state and is **not permanently stored**.

---

# 🚨 Error Handling

AI errors are converted into application-level exceptions:

```text
Network
PermissionDenied
EmptyResponse
Unknown
```

The UI then shows a simple message instead of exposing raw Firebase/Gemini exceptions.

Example:

```text
Gemini/Firebase error
        ↓
GeminiServiceImpl
        ↓
AurixException
        ↓
AurixViewModel
        ↓
User-friendly message
```

---

# 📊 Real Data Testing

AURIX has already been tested against real Lumora data.

Verified scenarios include:

- Monthly spending
- Highest spending category
- Budget status
- Expense reduction suggestions
- Multi-part financial questions
- Currency handling
- Budget exceeded scenarios
- Missing information

AURIX was also tested with information that does not exist in its financial context, such as credit-score information, and it did not invent a value.

Regression testing for the AURIX flow has passed.

---

# 🔐 Why AURIX Doesn't Directly Use Room

The AI should not become another place where business logic lives.

Instead of:

```text
Room → AURIX
```

Lumora uses:

```text
Room
 ↓
Repository / Use Cases
 ↓
FinanceContext
 ↓
AURIX
```

This keeps calculations such as expenses, budgets and category totals inside the application.

It also means changes to financial calculations can be made once and reused by the Dashboard, Analytics, Reports and AURIX.

---

# 🧩 Project Structure

The project follows the usual Clean Architecture separation:

```text
com.finance.lumora
│
├── core
├── data
├── domain
├── di
├── notifications
└── presentation
```

AURIX is organized inside the existing architecture:

```text
domain/
├── model/ai/
├── repository/
└── usecase/ai/

data/
└── remote/ai/

presentation/
└── ai/
    ├── components/
    ├── screen/
    └── viewmodel/
```

---

# 🧪 Development Approach

One important part of this project was building features without breaking existing functionality.

For larger features, the implementation generally follows:

```text
Domain
 ↓
Data
 ↓
DI
 ↓
ViewModel
 ↓
UI
 ↓
Navigation
 ↓
Real-data testing
```

For AURIX specifically, Gemini connectivity was tested first before adding the complete finance-aware architecture.

That made it easier to separate Firebase/AI integration problems from application logic problems.

---

# 📱 Current State of the Project

## Lumora

**Status: 🟢 Core application is working**

The application currently has a working foundation covering:

- Transactions
- Categories
- Search
- Analytics
- Reports
- Monthly budgeting
- Budget alerts
- Notifications
- Settings
- Profile
- Authentication/session handling
- Biometric lock
- Change password
- Data export
- Cache management
- About Lumora
- AURIX AI assistant

## AURIX

**Status: 🟢 v1 complete and working**

The current AURIX implementation can:

```text
Read Lumora's financial context
        ↓
Understand the user's question
        ↓
Send the controlled context to Gemini
        ↓
Generate a finance-aware response
        ↓
Show it inside the Lumora chat UI
```

The current implementation is intentionally **read-only**.

It does not:

- Add transactions
- Edit transactions
- Delete transactions
- Change budgets
- Change categories
- Perform financial actions

---

# 🚧 What Is Still Planned

AURIX v1 is complete, but there is plenty of room to make it smarter.

Possible future work:

### Finance Intelligence
- Spending trends
- Month-to-month comparisons
- Unusual spending detection
- Better savings insights
- Budget risk detection
- More personalized recommendations

### Controlled AI Actions
A future version could allow requests such as:

```text
"Add ₹500 to my Food expense."
```

But the AI should never directly modify the database.

The safer flow would be:

```text
AURIX
 ↓
Suggest action
 ↓
Application validates
 ↓
User confirms
 ↓
Application performs action
```

### Conversation Memory
- Saved conversations
- Chat history
- Conversation summaries
- Optional AURIX memory

### RAG
RAG is **not currently needed** because Lumora's financial data is structured.

It may become useful later if Lumora includes a large amount of unstructured financial education or reference material.

---

# 🗺️ Roadmap

### Completed

- [x] Expense and income tracking
- [x] Categories
- [x] Transaction search
- [x] Analytics dashboard
- [x] Monthly budget
- [x] Budget alerts
- [x] Notifications
- [x] Settings
- [x] Profile
- [x] Authentication/session handling
- [x] Biometric lock
- [x] Change password
- [x] Data export
- [x] Cache management
- [x] About Lumora
- [x] Firebase AI Logic
- [x] Gemini integration
- [x] AURIX finance context
- [x] AURIX prompt system
- [x] AURIX chat UI
- [x] AURIX error handling
- [x] AURIX retry flow
- [x] AURIX real-data testing

### Next

- [ ] Advanced finance intelligence
- [ ] Persistent AURIX conversations
- [ ] AURIX memory
- [ ] Controlled function calling
- [ ] User-confirmed financial actions
- [ ] Production App Check configuration
- [ ] More extensive automated testing
- [ ] Production polish and monitoring

---

# 🎯 Project Philosophy

Lumora started as an expense tracker, but the bigger goal is to make it a useful personal finance companion.

The project focuses on building features that are actually connected to each other instead of adding isolated demos.

Analytics uses the same financial data as the dashboard.

Budget alerts use the same expense information.

AURIX uses the same domain logic again instead of creating its own calculations.

That gives the project a simple architectural rule:

> **Build the financial logic once, reuse it everywhere, and let AI explain the data instead of owning it.**

---

# 👨‍💻 About the Project

Lumora is being developed as a hands-on Android development project to explore modern Android architecture and real-world application development.

It covers more than UI development, including:

- Architecture
- Local database design
- State management
- Authentication
- Cloud integration
- Notifications
- Security
- Data management
- Analytics
- AI integration
- Error handling
- Git/GitHub workflow

The current version is a working **Android finance application with an integrated AI assistant**, rather than just a collection of individual feature experiments.

---

## 📌 Current Milestone

**Lumora:** 🟢 Active development

**AURIX v1:** 🟢 Complete

**Primary focus now:** Improving the existing application, testing, production readiness, and planning the next AURIX capabilities.
