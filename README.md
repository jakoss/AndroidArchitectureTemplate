# Modern Android App Architecture Template

## Introduction

Highly opinionated architectural template for Android application, presenting patterns around modularization, navigation, UI, MVI enforcement or Dependency Injection.

Whole solution is created with consideration of how gradle works, so we are trying to take advantage of incremental builds, build cache or compilation avoidance as much as we can. More around that in [modularization documentation](./MODULARIZATION.md).

## Goals/Non-goals

### Goals

- Provide an easy starting point for new android projects with most things set up to start development
- Provide a place to search for patterns around certain areas (like navigation, or UI)
- Create patterns that are easy to use, scalable and gradle performance friendly

### Non-goals

- This project does not aim to provide a final and full solution for any project. It should be always review and modified according to the needs of particular project
- Library enforcement or flame wars. Since the goal is to provide patterns and starting point - pretty much all libraries used can be replace with something similar or even custom solution
- Provide patterns that are tighly coupled - you can take anything you need from this template and leave out everything that does not apply to your use-case
- It's never final. The template will evolve with my knowledge and the ecosystem

## Libraries and Patterns used:

- Gradle Version Catalogs for libraries management
- Gradle conventions inside separate compiled plugin to share build.gradle logic between modules
- .editorconfig file to share code style conventions
- Ktlint to check for style errors and for automatic formatting
- Jetpack Compose for UI (but View system can be used or even mixed with current approach)
- Anvil for dependency injection (can be swapped with Hilt or Koin, the patterns of usage are what's necessary here)
- Tangle as android components injection solution (Anvil extension)
- Orbit MVI as an MVI pattern view model (but can be easily swapped for any other ViewModel implementation)
- Custom navigation framework that work on injectable NavigationController interface. Using this
  interface developer can navigate from any point in code (mostly it will be ViewModel)
- Navigation is built around standard Fragments which gives us few benefits
  - Full interoperability between compose and view system. Developer can create one screen in compose and second one in view system without any issue
  - Stable ecosystem of navigation libraries (navigation around compose is still in it's infancy)
  - A lot of issues was solved using Fragment-based systems (similar set of issues still have to be solved in compose world)
- Async class that greatly simplifies working with asynchronous data in MVI style

## Documentation

I'll complement the documentation in the future. Every module should have it's own README file and some non-trivial classes should be fully documented.

- [Modularization](./MODULARIZATION.md)
- [Navigation](./core/navigation/README.md)
- [Networking](./core/networking/README.md)

## Sample implementations:

- Passing arguments and result during navigation - modules [features/dashboard](./features/dashboard/impl/src/main/java/pl/jsyty/architecturetemplate/feature/dashboard/impl)
and [features/message](./features/message/impl/src/main/java/pl/jsyty/architecturetemplate/feature/message/impl)
- Asynchronous resource loading - module [features/longaction](./features/longaction/impl/src/main/java/pl/jsyty/architecturetemplate/feature/longaction/impl)
- Clean architecture and networking = module [features/weather](./features/weather/impl/src/main/java/pl/jsyty/architecturetemplate/feature/weather/impl)

## Build performance

[Modularization](./MODULARIZATION.md) was architected around gradle cache and compilation avoidance, which should greatly benefit the overall build performance.

We are using Anvil as our DI framework which is much faster than Hilt due to usage of kotlin compiler plugin instead kapt. We are trying to avoid kapt as much as possible since the performance hit from it is pretty much always significant.

We are using [Android Cache Fix Gradle Plugin](https://github.com/gradle/android-cache-fix-gradle-plugin) to fix some AGP cache issues.

## To be done

- Network calls setup (weather page)
- Object mapping (probably some solution around MapStruct library)
- CI setup
- Internal qa build with debugging tools for testing purposes
- Documentation of more complex solutions
- Clean Architecture showcase (usage of ViewModel/UseCase/Repo/DataSource chains)
- Testing setup and patterns!
- Dokka setup
- Detekt setup

## Mentions

[Weather data by Open-Meteo.com](https://open-meteo.com/)
