# What Can I Watch? (WCIW)

**What Can I Watch?** (WCIW) is a mobile application designed to help users discover movies and TV series, explore parental guides, and find streaming providers available in their country. The project is developed using **Clean Architecture** and **MVVM (Model-View-ViewModel)** principles to ensure scalability, maintainability, and testability.

---

## 🚀 Features

- **Discover Movies & Series**: Browse through a vast library of movies and TV shows.
- **Parental Guide**: Detailed parental guidance to avoid surprises.
- **Providers by Country**: Check streaming providers available in your region.
- **Interactive Design**: Clean and modern UI powered by Jetpack Compose.
- **Localization**: Available in multiple languages, including English, Turkish, German, and more.

---

## 🛠️ Technologies Used

### Architectural Patterns
- **Clean Architecture**: Separation of concerns with well-defined layers (Data, Domain, and Presentation).
- **MVVM (Model-View-ViewModel)**: For efficient state management and UI updates.

### Core Technologies
- **Kotlin**: Programming language for modern, type-safe Android development.
- **Jetpack Compose**: Declarative UI toolkit for building dynamic user interfaces.
- **Firebase Realtime Database**: Used for caching parental guide data.
- **Jsoup**: HTML parser for scraping IMDb parental guide information.
- **Coroutines & Flows**: Asynchronous programming with structured concurrency.
- **Hilt (Dependency Injection)**: Simplifies dependency injection in Android projects.

---

## 📚 Libraries Used

| Library               | Purpose                                   |
|-----------------------|-------------------------------------------|
| **Jetpack Compose**   | UI development                           |
| **Jsoup**             | Web scraping for IMDb data               |
| **Firebase Realtime Database** | Storing and caching parental guide data |
| **Hilt**              | Dependency Injection                     |
| **Navigation Component** | Manage app navigation                 |
| **Coil**              | Image loading and caching                |
| **Kotlin Coroutines** | Asynchronous programming                 |
| **Flow**              | Reactive streams for UI and data layers  |

---


🔍 How It Works
Clean Architecture Layers
Data Layer: Handles data sources like Firebase and IMDb via repositories.
Domain Layer: Contains business logic in use cases, ensuring single responsibility.
Presentation Layer: Manages UI using Jetpack Compose and ViewModels.
Parental Guide
IMDb parental guide data is scraped using Jsoup.
The data is cached in Firebase Realtime Database to reduce redundant web requests.
The app prioritizes cached data for better performance.
Localization
The app supports multiple languages, including:

English (en-US)
German (de-DE)
Spanish (es-ES)
French (fr-FR)
Turkish (tr-TR), and more.
Streaming Providers
Users can discover streaming providers based on their location.
Links are dynamically generated for each movie or show.
🧩 Project Structure
📂 wciwapp
├── 📂 app
│   ├── 📂 data       # Data sources, repositories, and models
│   ├── 📂 domain     # Use cases and business logic
│   ├── 📂 presentation # ViewModels and Compose screens
│   ├── 📂 di         # Dependency Injection setup
│   ├── 📂 utils      # Utility classes and extensions

💡 Future Plans
Dark Mode Support.
Integration with more streaming services.
Advanced filtering and sorting options.
Push notifications for new movie/show releases.
🤝 Contribution
Contributions are welcome! Please follow these steps:

Fork the repository.
Create a new branch (git checkout -b feature-branch).
Commit your changes (git commit -m 'Add feature').
Push to the branch (git push origin feature-branch).
Open a Pull Request.
📧 Contact
For inquiries, feel free to reach out:

Email: support@wciwapp.com
GitHub Issues: Report Bugs
📜 License
This project is licensed under the MIT License. See the LICENSE file for details.
