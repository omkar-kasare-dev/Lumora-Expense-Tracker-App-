# Lumora Permission Audit Log

> **Purpose:** Document every Android permission requested in `AndroidManifest.xml`, providing technical justification and user-facing features for Google Play review and internal security audits.

---

## 1. Android Manifest Permission Matrix (`AndroidManifest.xml`)

| Permission Name | Protection Level | Technical Justification | User Facing Feature | Risk Level |
| :--- | :--- | :--- | :--- | :--- |
| `android.permission.INTERNET` | Normal | API communication with Lumora backend servers. | All online features | Low |
| `android.permission.ACCESS_NETWORK_STATE` | Normal | Checks network state to switch to offline mode gracefully. | Network connectivity indicator | Low |
| `android.permission.CAMERA` | Dangerous (Runtime) | Captures images and video directly inside the app. | Media upload & scanner | Medium |
| `android.permission.READ_MEDIA_IMAGES` | Dangerous (Runtime) | Allows selection of photos from local storage (Android 13+). | Gallery media picker | Medium |
| `android.permission.RECORD_AUDIO` | Dangerous (Runtime) | Records audio clips or voice inputs. | Audio features | Medium |
| `android.permission.POST_NOTIFICATIONS` | Dangerous (Runtime) | Sends transactional updates and activity alerts (Android 13+). | Push notifications | Low |

---

## 2. Permission Handling Rules

- [x] **Runtime Prompting:** All `Dangerous` permissions are requested in context when the user attempts an action, never on cold boot.
- [x] **Fallback Pathways:** If a user denies a permission (or selects "Don't ask again"), the app remains functional and displays an explanatory UI with an option to open System Settings.
- [x] **No Unnecessary Permissions:** Unused permissions (e.g., `READ_PHONE_STATE`, `FINE_LOCATION`) are strictly excluded from `AndroidManifest.xml`.

---

## Audit Certification

**Audited By:** ____________________  
**Date:** YYYY-MM-DD