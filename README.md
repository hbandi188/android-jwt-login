# Android Login demo app

Primarily written as an exercise in TDD, this app would communicate with a currently non-existent backend to perform an authorization and
storage of tokens. Upon relaunch of the app, a token refresh is attempted.

Primary tech stack:

- Jetpack ViewModels
- Jetpack Navigation
- Coroutines
- Retrofit
- Moshi
- Kotest
- MockK
- Robolectric

# Building and running

Client Id is hidden in `app/src/main/cpp/native-lib.cpp`.

Project should be simply opened in Android Studio (Arctic Fox or later version).

# Testing, verifying

Run tests with `gradlew test`. Detekt can be run with `gradlew detekt`