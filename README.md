<a id="top"></a>

* [English](#enu)
* [简体中文](#chs)
***

<a id="enu"></a>

# UnoCard 🎮

A modern **cross-platform UNO-style card game** built with **Kotlin Multiplatform (KMM)** and **Jetpack Compose Multiplatform**.  
UnoCard delivers a consistent experience across **Android**, **iOS**, and **Desktop (Steam-ready)**, with fully shared game logic and modular design.

---

## ✨ Features
- ♠️ **Cross-platform support**: Android, iOS, Desktop (Steam integration ready)
- 🎨 **Compose Multiplatform UI** for responsive, unified design
- 🔊 **Audio system**: expect/actual with platform-specific backends
- 🌍 **Internationalization (i18n)**: JSON resource-based translations
- 🎲 **Full UNO game logic**: shuffling, dealing, turn handling, special cards, and win detection
- 🚀 **Extensible architecture**: themes, animations, and new platforms can be added with minimal effort
   
---
<p align="center">
<img src="https://raw.githubusercontent.com/xinggen-guo/UnoCard/main/images/image.png" />
</p>
<p align="center">
<img src="https://raw.githubusercontent.com/xinggen-guo/UnoCard/main/images/screenshot1.jpg"/>
</p>
<p align="center">
<img src="https://raw.githubusercontent.com/xinggen-guo/UnoCard/main/images/screenshot2.jpg"/>
</p>
## 📂 Project Structure

```
UnoCard/
├── androidApp/
├── iosApp/
├── desktopApp/
├── shared/
│   ├── commonMain/
│   ├── androidMain/
│   ├── iosMain/
│   └── desktopMain/
```

---

## 🛠️ Tech Stack
- **Kotlin Multiplatform (KMM)**
- **Jetpack Compose Multiplatform**
- **Voyager Navigator**
- **Audio APIs**
- **Gradle Version Catalog (`libs.versions.toml`)**

---

## 🚀 Getting Started
### Android
1. Open project in **Android Studio**
2. Run `androidApp`

### iOS
```bash
./gradlew :shared:podInstall
```
Open `iosApp/iosApp.xcworkspace` in Xcode

### Desktop
```bash
./gradlew :desktopApp:run
```

---

<a id="chs"></a>

# UnoCard 🎮

一款基于 **Kotlin Multiplatform (KMM)** 和 **Jetpack Compose Multiplatform** 开发的现代化 **UNO 风格跨平台纸牌游戏**。

---

## ✨ 功能特性
- ♠️ **跨平台支持**：Android、iOS、桌面（支持 Steam）
- 🎨 **Compose Multiplatform UI**
- 🔊 **音频系统**：expect/actual 平台实现
- 🌍 **国际化 (i18n)**：JSON 翻译资源
- 🎲 **完整 UNO 游戏逻辑**
- 🚀 **可扩展架构**

---

<p align="center">
<img src="https://raw.githubusercontent.com/xinggen-guo/UnoCard/main/images/image.png" />
</p>
<p align="center">
<img src="https://raw.githubusercontent.com/xinggen-guo/UnoCard/main/images/screenshot1.jpg"/>
</p>
<p align="center">
<img src="https://raw.githubusercontent.com/xinggen-guo/UnoCard/main/images/screenshot2.jpg"/>
</p>

## 📂 项目结构

```
UnoCard/
├── androidApp/
├── iosApp/
├── desktopApp/
├── shared/
│   ├── commonMain/
│   ├── androidMain/
│   ├── iosMain/
│   └── desktopMain/
```

---

## 🚀 快速开始
### Android
使用 **Android Studio** 打开并运行 `androidApp`

### iOS
```bash
./gradlew :shared:podInstall
```
在 Xcode 打开 `iosApp/iosApp.xcworkspace`

### 桌面
```bash
./gradlew :desktopApp:run
```

---
