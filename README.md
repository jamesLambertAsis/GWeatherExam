# GWeather 🌦️

A  MVI+Clean-architecture Android weather application built with Jetpack Compose.

## 🚀 Tech Stack
- **UI:** [Jetpack Compose](https://developer.android.com/compose) (Material 3)
- **Navigation:** Type-safe Navigation Compose
- **Dependency Injection:** [Koin](https://insert-koin.io/)
- **Local Database:** [Room](https://developer.android.com/training/data-storage/room) (SQLite)
- **Networking:** [Retrofit](https://square.github.io/retrofit/) with OkHttp
- **Serialization:** Kotlinx Serialization
- **Image Loading:** [Coil](https://coil-kt.github.io/coil/)
- **Location:** Google Play Services Location

## 🛠️ Setup Instructions

### 1. Get an API Key
Sign up at [OpenWeatherMap](https://openweathermap.org/api) to get your free API key.

### 2. Configure Secrets
To keep the API key safe, this project uses `local.properties`. Create or open `local.properties` in your root directory and add:
properties= 
OPENWEATHER_API_KEY=your_api_key_here

NOTE: Weather icons is from [OpenWeatherMap](https://openweathermap.org/weather-conditions) api and displayed through coil.