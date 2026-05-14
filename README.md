# Siri Dhanya Hub - Crop Price Recommendation App

![App Logo](app/src/main/res/drawable/app_logo_main.png)

## 🌾 Overview

**Siri Dhanya Hub** is a comprehensive Android application designed to empower farmers and agriculture enthusiasts with real-time crop information, mandi (market) price monitoring, millet-based recipes, health benefits awareness, and direct farmer support services. The app bridges the gap between traditional agriculture and modern digital technology, providing a centralized platform for all agricultural needs.

This project was developed as part of the **Android App Development using Generative AI** internship program at **MindMatrix**, showcasing the integration of AI-assisted development workflows in modern Android application development.

---

## ✨ Key Features

### 🔐 Authentication & User Management
- Secure Firebase Authentication (Email/Password)
- User Registration with validation
- Session persistence
- Profile management
- Logout functionality

### 📊 Mandi Price Monitoring
- Real-time crop price updates from multiple mandis
- Price comparison across different markets
- Historical price trends and analysis
- Price alerts and notifications
- Farmer-friendly price visualization

### 🌾 Crop Information Database
- Comprehensive crop details and specifications
- Seasonal crop information
- Growing techniques and best practices
- Crop disease identification
- Pesticide and fertilizer recommendations

### 🍲 Millet Recipes Laboratory
- Diverse collection of millet-based recipes
- Nutritional information per recipe
- Step-by-step cooking instructions
- Ingredient sourcing guide
- Health benefits associated with each recipe

### ❤️ Health Benefits Module
- Millet nutritional content overview
- Health benefits of different millet varieties
- Medical research insights
- Wellness tips and dietary recommendations
- FAQ section on health-related queries

### 🤝 Farmer Support & Contact Services
- Direct contact with agricultural experts
- Issue reporting and resolution system
- Community support network
- Resource availability information
- Government scheme details and applications

### 📱 User Interface & Experience
- Modern Material Design 3 UI
- Responsive layout for all screen sizes
- Smooth navigation and transitions
- Dark and Light theme support
- Accessibility features

---

## 🛠️ Technology Stack

### **Frontend Development**
- **Language**: Kotlin (100% Kotlin-based)
- **UI Framework**: Jetpack Compose (Latest Alpha)
- **Design System**: Material Design 3
- **Navigation**: Compose Navigation with deep linking
- **State Management**: ViewModel & LiveData

### **Backend & Database**
- **Authentication**: Firebase Authentication
- **Real-time Database**: Firebase Firestore
- **Data Persistence**: Room Database (Local caching)
- **Cloud Storage**: Firebase Cloud Storage (for images)

### **Libraries & Dependencies**
```kotlin
// Core AndroidX
androidx.core:core-ktx:1.13.1
androidx.appcompat:appcompat:1.7.0

// Jetpack Compose
androidx.compose:compose-bom:2024.06.00
androidx.compose.ui:ui
androidx.compose.material3:material3
androidx.compose.runtime:runtime

// Firebase
com.google.firebase:firebase-auth
com.google.firebase:firebase-firestore
com.google.firebase:firebase-storage

// Room Database
androidx.room:room-runtime
androidx.room:room-ktx

// ViewModel & LiveData
androidx.lifecycle:lifecycle-viewmodel-compose
androidx.lifecycle:lifecycle-runtime-compose

// Networking (if applicable)
com.squareup.okhttp3:okhttp
com.squareup.retrofit2:retrofit
```

### **Development Tools & Environment**
- **IDE**: Android Studio (2024.1 or higher)
- **Build System**: Gradle with Kotlin DSL
- **Version Control**: Git & GitHub
- **Target SDK**: Android 14 (API Level 34)
- **Minimum SDK**: Android 7.0 (API Level 24)
- **Compile Version**: Android 14

### **AI-Assisted Development**
- GitHub Copilot for code generation
- ChatGPT for architecture planning
- Generative AI for documentation and testing

---

## 📁 Project Structure

```
SiriDhanyaHubApp/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/siri/dhanyahub/
│   │       │   ├── MainActivity.kt                 # Entry point
│   │       │   ├── SiriDhanyaApp.kt               # Application class
│   │       │   ├── ui/
│   │       │   │   ├── AppRoot.kt                 # Root navigation
│   │       │   │   ├── screens/
│   │       │   │   │   ├── OnboardingScreen.kt    # Onboarding flow
│   │       │   │   │   ├── ConnectScreen.kt       # Connection status
│   │       │   │   │   ├── DashboardScreen.kt     # Main dashboard
│   │       │   │   │   ├── HealthScreen.kt        # Health benefits
│   │       │   │   │   └── RecipesScreen.kt       # Millet recipes
│   │       │   │   ├── components/
│   │       │   │   │   └── PriceForecastCard.kt   # Reusable components
│   │       │   │   └── theme/
│   │       │   │       ├── Color.kt               # Color palette
│   │       │   │       └── Theme.kt               # Theme configuration
│   │       │   ├── data/
│   │       │   │   ├── local/
│   │       │   │   │   ├── AppDatabase.kt         # Room database
│   │       │   │   │   ├── Dao.kt                 # Data access objects
│   │       │   │   │   └── Entities.kt            # Database entities
│   │       │   │   ├── repository/
│   │       │   │   │   └── AppRepository.kt       # Repository pattern
│   │       │   │   └── model/
│   │       │   │       └── Models.kt              # Data models
│   │       │   └── viewmodel/
│   │       │       └── MainViewModel.kt           # ViewModel logic
│   │       ├── res/
│   │       │   ├── drawable/                      # Drawable resources
│   │       │   ├── mipmap/                        # App icons
│   │       │   └── values/                        # Strings, colors, themes
│   │       └── AndroidManifest.xml                # Manifest file
│   ├── build.gradle.kts                           # App-level build config
│   └── proguard-rules.pro                         # ProGuard rules
├── gradle/
│   └── wrapper/                                   # Gradle wrapper
├── build.gradle.kts                               # Project-level build config
├── settings.gradle.kts                            # Gradle settings
├── gradle.properties                              # Gradle properties
├── local.properties                               # Local SDK path
└── README.md                                      # This file
```

---

## 🚀 Installation & Setup

### **Prerequisites**
- Android Studio (2024.1 or later)
- Android SDK (API Level 34)
- Java Development Kit (JDK 17+)
- Git installed on your system
- Firebase account

### **Step 1: Clone the Repository**
```bash
git clone https://github.com/Rickeybyn/Siri_Dhanaya_Hub_crop_price_recommendation_App.git
cd SiriDhanyaHubApp
```

### **Step 2: Open in Android Studio**
1. Launch Android Studio
2. Click **File** → **Open**
3. Navigate to the cloned project folder
4. Click **Open**
5. Wait for Gradle sync to complete

### **Step 3: Configure Firebase**
1. Go to [Firebase Console](https://console.firebase.google.com)
2. Create a new Firebase project
3. Add an Android app to your Firebase project
4. Download the `google-services.json` file
5. Place `google-services.json` in the `app/` directory

### **Step 4: Enable Firebase Services**
In Firebase Console:
- Enable **Authentication** (Email/Password provider)
- Enable **Firestore Database** (Start in test mode)
- Enable **Cloud Storage** (if using image uploads)

### **Step 5: Build & Run the App**
```bash
# Using Android Studio
1. Connect an Android device (or start an emulator)
2. Click Run → Run 'app' or press Shift+F10

# Using command line
./gradlew installDebug
```

### **Step 6: Test the App**
- Create a test account using the registration screen
- Verify login functionality
- Navigate through different screens
- Test all features

---

## 📋 Application Architecture

### **Architecture Pattern: MVVM (Model-View-ViewModel)**

```
┌─────────────────────────────────────────────┐
│          UI Layer (Compose)                 │
│  ┌──────────────────────────────────────┐   │
│  │  Screens & Components                │   │
│  │  (OnboardingScreen, DashboardScreen) │   │
│  └──────────────────────────────────────┘   │
└─────────────────────────────────────────────┘
           ↕ (Observes State)
┌─────────────────────────────────────────────┐
│       ViewModel Layer                       │
│  ┌──────────────────────────────────────┐   │
│  │  MainViewModel (State Management)    │   │
│  │  Business Logic & Data Handling      │   │
│  └──────────────────────────────────────┘   │
└─────────────────────────────────────────────┘
           ↕ (Uses)
┌─────────────────────────────────────────────┐
│       Repository Layer                      │
│  ┌──────────────────────────────────────┐   │
│  │  AppRepository                       │   │
│  │  Data source abstraction             │   │
│  └──────────────────────────────────────┘   │
└─────────────────────────────────────────────┘
     ↕          ↕          ↕
┌─────────┐ ┌──────────┐ ┌─────────────┐
│  Local  │ │ Firebase │ │ API Server  │
│ Database│ │  Backend │ │ (if needed) │
└─────────┘ └──────────┘ └─────────────┘
```

### **Data Flow**
1. **UI Layer**: User interacts with Compose UI
2. **ViewModel**: Manages UI state and business logic
3. **Repository**: Fetches data from local DB or Firebase
4. **Data Sources**: Firebase Firestore, Room Database
5. **Response**: Data flows back to ViewModel, updates UI reactively

---

## 🎯 Core Modules & Functionality

### **1. Authentication Module** 🔐
**File**: `data/repository/AppRepository.kt`, `viewmodel/MainViewModel.kt`

Features:
- Email/Password registration and login
- Input validation (email format, password strength)
- Firebase Authentication integration
- Session management
- Error handling and user feedback

### **2. Dashboard Module** 📊
**File**: `ui/screens/DashboardScreen.kt`

Features:
- Navigation hub to all app features
- Quick access buttons
- User profile display
- Recent activities
- Notification center

### **3. Mandi Price Monitoring** 💹
**File**: `ui/screens/DashboardScreen.kt`, `data/model/Models.kt`

Features:
- Real-time price updates
- Price comparison tables
- Graphical trend analysis
- Historical data
- Price alerts

### **4. Recipes Laboratory** 🍲
**File**: `ui/screens/RecipesScreen.kt`, `data/local/Entities.kt`

Features:
- Recipe database with Firestore
- Search and filter functionality
- Detailed recipes with ingredients
- Cooking instructions
- Nutritional information

### **5. Health Benefits** ❤️
**File**: `ui/screens/HealthScreen.kt`

Features:
- Comprehensive health information
- Nutritional content details
- Research-backed health benefits
- Wellness tips
- FAQ section

### **6. Farmer Support** 🤝
**File**: `ui/screens/ConnectScreen.kt`

Features:
- Contact form submission
- Support ticket tracking
- Direct messaging with experts
- Resource sharing
- Scheme information

---

## 🧪 Testing

### **Unit Testing**
```kotlin
// Test ViewModel logic
@Test
fun testAuthenticationFlow() {
    // Test login, registration, etc.
}
```

### **Integration Testing**
- Firebase Firestore integration tests
- Local Room database tests
- Navigation flow testing

### **UI Testing (Compose)**
```kotlin
@Test
fun testLoginScreenComposable() {
    // Compose UI testing
}
```

---

## 📚 Learning Outcomes

Through this project, developers can learn:

✅ **Kotlin Programming**
- Coroutines and async programming
- Extension functions and DSL
- Kotlin collections and operators

✅ **Jetpack Compose**
- Declarative UI programming
- State management in Compose
- Reusable composable components
- Lazy layouts and performance optimization

✅ **Firebase Integration**
- Firebase Authentication implementation
- Firestore CRUD operations
- Real-time data synchronization
- Security rules configuration

✅ **Android Architecture**
- MVVM design pattern
- Repository pattern
- Dependency injection basics
- Clean code principles

✅ **Modern Development Tools**
- GitHub version control
- AI-assisted code generation
- Debugging techniques
- Performance profiling

✅ **UI/UX Design**
- Material Design 3 principles
- Responsive design for multiple screen sizes
- Accessibility features
- User experience best practices

---

## 🎨 Customization & Configuration

### **Change App Name**
Edit `app/src/main/res/values/strings.xml`:
```xml
<string name="app_name">Your App Name</string>
```

### **Change Color Scheme**
Edit `ui/theme/Color.kt`:
```kotlin
val PrimaryColor = Color(0xFF...)
val SecondaryColor = Color(0xFF...)
```

### **Firebase Configuration**
Ensure `google-services.json` is in `app/` directory and updated with your Firebase project details.

---

## 🚦 Build Variants

### **Debug Build**
- Development build with debugging enabled
- Larger APK size
- Optimized for development

### **Release Build**
```bash
./gradlew assembleRelease
```
- Optimized APK
- ProGuard obfuscation enabled
- Suitable for production

---

## 📦 APK Size & Performance

- **Debug APK**: ~150-200 MB (with build artifacts)
- **Release APK**: ~40-50 MB (after ProGuard optimization)
- **Minimum RAM Required**: 2 GB
- **Supported Architectures**: arm64-v8a, armeabi-v7a

---

## 🔐 Security Features

✅ **Firebase Authentication** - Secure user authentication
✅ **Firestore Security Rules** - Database access control
✅ **ProGuard Obfuscation** - Code protection in release builds
✅ **Input Validation** - Prevents injection attacks
✅ **HTTPS Communication** - Encrypted data transmission

---

## 🌟 Future Enhancements

### **Planned Features**
- 🤖 AI-based crop recommendation engine
- 🌦️ Weather forecasting integration
- 🛒 E-commerce for farm products
- 🗣️ Multilingual support (Hindi, Tamil, Kannada, etc.)
- 💬 Farmer community discussion forums
- 📢 Push notifications for price alerts
- 📍 Location-based services
- 🎥 Video tutorials on farming techniques
- 📊 Advanced analytics dashboard
- 🌐 IoT sensor integration for real-time farm monitoring

---

## 🤝 Contributing

Contributions are welcome! To contribute:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/YourFeature`)
3. Commit your changes (`git commit -m 'Add YourFeature'`)
4. Push to the branch (`git push origin feature/YourFeature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the **MIT License** - see the LICENSE file for details.

---

## 📞 Support & Contact

For questions, bug reports, or suggestions:
- **Email**: support@siridhanyahub.com
- **GitHub Issues**: [Project Issues](https://github.com/Rickeybyn/Siri_Dhanaya_Hub_crop_price_recommendation_App/issues)
- **Documentation**: [Wiki](https://github.com/Rickeybyn/Siri_Dhanaya_Hub_crop_price_recommendation_App/wiki)

---

## 👥 Team & Contributors

**Project Developer**: Pranav Madhusudhan
**Internship Program**: Android App Development using Generative AI @ MindMatrix
**Mentors**: MindMatrix Development Team

---

## 🙏 Acknowledgments

- **Firebase Team** - Cloud infrastructure
- **Jetpack Compose Team** - Modern UI framework
- **MindMatrix** - Mentorship and guidance
- **Open Source Community** - Libraries and tools
- **Farmer Community** - Inspiration and feedback

---

## 📈 Project Statistics

- **Total Files**: 34+
- **Lines of Code**: 5000+
- **Kotlin Code**: 100%
- **Architecture Pattern**: MVVM
- **Target Users**: Farmers, Agriculture Enthusiasts
- **Development Time**: [Project Duration]
- **Current Version**: 1.0.0

---

## 🔗 Useful Links

- [Android Developer Documentation](https://developer.android.com/)
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Firebase Documentation](https://firebase.google.com/docs)
- [Kotlin Official Documentation](https://kotlinlang.org/docs/)
- [Material Design 3](https://m3.material.io/)

---

## 📝 Version History

### **Version 1.0.0** (Current Release)
- ✅ Initial release with core features
- ✅ Firebase integration complete
- ✅ All main screens implemented
- ✅ User authentication system
- ✅ Firestore database integration

---

**Last Updated**: May 14, 2026
**Repository**: [GitHub](https://github.com/Rickeybyn/Siri_Dhanaya_Hub_crop_price_recommendation_App)

---

*Made with ❤️ using Kotlin, Jetpack Compose, and Firebase*

## Author

**Pranav M**

Android App Developer | AI Enthusiast

---

## Organization

Developed during internship at **MindMatrix.io**

---

## License

This project is developed for educational and internship learning purposes.
