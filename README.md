# UnoCard 🎮

A cross-platform UNO-style card game built with **Kotlin Multiplatform** and **Jetpack Compose Multiplatform**.  
This project demonstrates how to build a modern card game that runs on **Android**, **iOS**, and **Desktop (Steam-ready)** with a shared game logic layer.

---

## ✨ Features
- ♠️ **Cross-platform support**: Android, iOS, Desktop
- 🎨 **Compose Multiplatform UI** for a unified and responsive interface
- 🔊 **Audio system** with platform-specific implementations
- 🌍 **Internationalization (i18n)** with resource-based translation
- 🎲 **Full UNO game logic**: card dealing, turn-based gameplay, special action cards, and win detection
- 🚀 **Extensible architecture**: easy to add new features, themes, or platforms

---

## 📂 Project Structure

UnoCard/
├── androidApp/        # Android-specific launcher
├── iosApp/            # iOS-specific launcher (Xcode integration)
├── desktopApp/        # Desktop launcher (Steam-ready)
├── shared/            # Shared game logic + UI (KMM + Compose Multiplatform)
│   ├── commonMain/    # Cross-platform core logic
│   ├── androidMain/   # Android-specific implementations
│   ├── iosMain/       # iOS-specific implementations
│   └── desktopMain/   # Desktop-specific implementations

---

## 🛠️ Tech Stack
- **Kotlin Multiplatform (KMM)**
- **Jetpack Compose Multiplatform**
- **Voyager Navigator** for screen navigation
- **AVFoundation / Android MediaPlayer / Desktop audio APIs** for cross-platform sound
- **Gradle Version Catalog (libs.versions.toml)** for dependency management

---

## 🚀 Getting Started

### Android
1. Open project in **Android Studio** (latest KMP support version recommended)
2. Select `androidApp` and run on device or emulator

### iOS
1. Run Gradle task to generate CocoaPods framework:
   ```bash
   ./gradlew :shared:podInstall

	2.	Open iosApp/iosApp.xcworkspace in Xcode
	3.	Select an iOS Simulator (e.g., iPhone 16 Pro) and run

Desktop
1.	Run:

./gradlew :desktopApp:run



⸻

📦 Build Release

Android (Google Play)

./gradlew :androidApp:bundleRelease

Output: androidApp/build/outputs/bundle/release/app-release.aab

iOS

Build directly with Xcode (Debug/Release schemes)

Desktop

./gradlew :desktopApp:packageDistributionForCurrentOS


⸻

🙌 Acknowledgements
•	JetBrains for Kotlin Multiplatform & Compose Multiplatform
•	Voyager Navigator
•	Open-source contributors and the Kotlin community

---
