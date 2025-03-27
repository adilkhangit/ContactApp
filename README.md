# Contact Manager App

## 📌 Overview
This is a **Contact Manager** Android application that allows users to **add, edit, delete, and view contacts**. It follows the **MVVM (Model-View-ViewModel)** architecture and integrates essential Android components such as **Room Database, Retrofit for API calls, and Firebase Analytics & Crashlytics**.

## 🚀 Features
- **Add, Edit & Delete Contacts**: Users can manage their contacts seamlessly.
- **Swipe-to-Delete**: Allows deleting contacts with a smooth swipe gesture.
- **Database Persistence**: Data is stored locally using **Room Database**.
- **API Integration**: Fetches random user data from an external API using **Retrofit**.
- **MVVM Architecture**: Ensures separation of concerns and better testability.
- **Firebase Analytics & Crashlytics**: Tracks user interactions and crashes.
- **Network Check**: Ensures offline functionality with connectivity checks.

## 🚀 App Presentation
https://drive.google.com/file/d/1VPqTYiV3Rxr6Yh1l59GrctN5NvFaFTcr/view?usp=sharing

## Firebase Crashlytics/Analytics/Performace
https://drive.google.com/file/d/1Ce2MRcrQ2w4T8sryVaJP8F3sKVvgPQ3R/view?usp=sharing
![Screenshot 2025-03-27 062505](https://github.com/user-attachments/assets/90365e93-cbaf-4232-8f56-b9b038adb345)
![Screenshot 2025-03-27 062520](https://github.com/user-attachments/assets/e1bcbb66-e1be-4e1d-af90-9207efb52efc)
![Screenshot 2025-03-27 063854](https://github.com/user-attachments/assets/3b37b5d7-f4a7-45f4-9783-c4cf8d97bfcc)
![Screenshot 2025-03-27 071631](https://github.com/user-attachments/assets/aecbc543-22ff-42a9-9bd6-1cdc86a83d62)



---

## 🏗 Architecture: MVVM

The app follows the **MVVM (Model-View-ViewModel)** architecture for a clear separation of concerns:

- **Model**: Represents the data layer. This includes the **Room Database** for local storage and **Retrofit API service**.
- **View**: The UI layer using **Jetpack Compose**.
- **ViewModel**: Manages UI-related data and communicates with the repository asynchronously.

---

## 🗄 Database: Room Persistence Library
We use **Room Database** to store contacts locally. It provides:
- **Entity** (`Contact`): Defines the contact structure.
- **DAO (Data Access Object)**: Defines queries for adding, editing, deleting, and retrieving contacts.
- **Repository**: Acts as a single source of truth for data, fetching from **local storage** or **network** as needed.

---

## 🌐 Networking: Retrofit
To fetch random user contacts, we use **Retrofit**, a powerful HTTP client for Android.
- **Why Retrofit?**
  - Simplifies API calls and parsing JSON data.
  - Supports coroutines for asynchronous execution.
  - Built-in error handling with response models.

---

## 📊 Firebase Analytics & Crashlytics
- **Firebase Analytics**: Tracks user interactions (e.g., "Contact Added", "Contact Deleted").
- **Firebase Crashlytics**: Captures crashes and logs errors in real-time.

**Implementation:**
1. Added **google-services.json** for Firebase configuration.
2. Integrated **Firebase Analytics** to log key events.
3. Implemented **Crashlytics** to monitor app crashes.

---

## 📦 Third-Party Libraries Used
| Library         | Purpose |
|---------------|---------|
| **Retrofit**  | For API calls (fetch random contacts) |
| **Room DB**   | Local database for storing contacts |
| **Firebase Analytics** | Tracking user events |
| **Firebase Crashlytics** | Logging app crashes |
| **Coroutines** | Handling background tasks efficiently |
| **Jetpack Compose** | Building modern UI |

---

## 🔧 Setup & Run Instructions
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/ContactManager.git
   ```
2. Open the project in **Android Studio**.
3. Add the **google-services.json** file inside `app/` for Firebase.
4. Sync the project with Gradle files.
5. Run the app on an emulator or a real device.

---

## 🛠 Future Improvements
- **Cloud Sync**: Sync contacts with Firebase Firestore.
- **Dark Mode**: UI enhancement for better user experience.
- **Search Contacts**: Implement filtering/search functionality.

---

## 👨‍💻 Author
**Adil Khan** - Android Developer & Tech Enthusiast

Feel free to contribute, report bugs, or suggest improvements! 😊

