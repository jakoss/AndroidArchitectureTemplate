# Networking

This module should contain everything required to setup networking infrastructure. This would include:

- Networking monitoring setup
- Interceptors
- Authentication solutions
- Retrofit setup
- Json converters setup

## Libraries

In this project we're using:

- [OkHttp](https://square.github.io/okhttp/) - can be swapped with [Ktor Client](https://ktor.io/docs/create-client.html)
- [Retrofit](https://square.github.io/retrofit/) - can be swapped with [Ktorfit](https://github.com/Foso/Ktorfit)
- [Kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) - can be swapped with [Moshi](https://github.com/square/moshi)

Every single component is used as a showcase and should be individually considered for your project needs.
