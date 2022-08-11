# ArchitectureTemplate

## Libraries and Patterns used:

- Gradle Version Catalogs for libraries management
- Gradle conventions inside separate compiled plugin to share build.gradle logic between modules
- .editorconfig file to share code style conventions
- Jetpack Compose for UI
- Anvil for dependency injection
- Tangle as android components injection solution (Anvil extension)
- Orbit MVI as an MVI pattern view model
- Custom navigation framework that work on injectable NavigationController interface. Using this
  interface developer can navigate from any point in code (mostly it will be ViewModel)
