# Lumora Policy Compliance Checklist

> **Purpose:** Ensure the Lumora Android application complies with global privacy regulations (GDPR, CCPA/CPRA, COPPA), Google Play Store Developer Policies, and internal governance requirements prior to distribution.

---

## 1. Google Play Store Policies

### 1.1 Play Console Data Safety Section
- [ ] **Accurate Declarations:** All data types collected or shared by Lumora (and its integrated SDKs) are accurately declared in the Play Console Data Safety form.
- [ ] **Data Encryption in Transit:** Verified that all off-device user data transmission uses HTTPS/TLS.
- [ ] **Account Deletion Requirement:** In-app mechanism and web-based URL provided allowing users to request account and data deletion as required by Google Play policy.

### 1.2 Target SDK & Behavioral Policies
- [ ] **Target API Level:** Application targets Google Play's required Android API level (Target SDK Version).
- [ ] **Prominent Disclosures:** Clear in-app privacy disclosures presented *before* prompting for sensitive runtime permissions (e.g., Location, Camera, Audio).
- [ ] **Background Restrictions:** No unauthorized background services or foreground services running without user awareness and appropriate `foregroundServiceType` declarations.

---

## 2. Data Privacy & Regulations

### 2.1 GDPR & CCPA/CPRA
- [ ] **Legal Basis:** Explicit consent or legitimate interest documented for all collected user attributes.
- [ ] **Data Export / SAR:** Mechanism in place for users to request an export of their personal data.
- [ ] **Right to Erasure:** Automated or manual process active to wipe user records from backend databases within 30 days.
- [ ] **Data Minimization:** Audited to ensure Lumora requests only essential Android permissions and user attributes.

### 2.2 COPPA & Age Restrictions
- [ ] **Target Audience:** Target age group accurately configured in Google Play Console.
- [ ] **Child Data Protections:** If accessible to users under 13/16, ensure no tracking SDKs or personalized advertising SDKs are loaded.

---

## 3. Open Source & Licensing

- [ ] **Third-Party Android Libraries:** All Gradle dependencies cataloged and verified for license compliance (e.g., MIT, Apache 2.0).
- [ ] **Asset Rights:** Licenses secured for all embedded fonts, audio files, icons, and graphic assets.

---

## Sign-Off Matrix

| Role | Signee Name | Date | Status |
| :--- | :--- | :--- | :--- |
| **Privacy / Legal Lead** | ____________________ | YYYY-MM-DD | [ ] Approved |
| **Security Engineer** | ____________________ | YYYY-MM-DD | [ ] Approved |
| **Android Lead** | ____________________ | YYYY-MM-DD | [ ] Approved |