# Modern Android App Architecture Template

## Introduction

Highly opinionated architectural template for Android application, presenting patterns around modularization, navigation, UI, MVI enforcement or Dependency Injection.

Whole solution is created with consideration of how gradle works, so we are trying to take advantage of incremental builds, build cache or compilation avoidance as much as we can. More around that in [modularization documentation](./MODULARIZATION.md).

## Goals/Non-goals

### Goals

- Provide an easy starting point for new android projects with most things set up to start development
- Provide a place to search for patterns around certain areas (like navigation, or UI)
- Create patterns that are easy to use, scalable and gradle performance friendly
- Create the most overkill and over-engineered mini application in existence

### Non-goals

- This project does not aim to provide a final and full solution for any project. It should be always reviewed and modified according to the needs of particular project
- Library enforcement or flame wars. Since the goal is to provide patterns and starting point - pretty much all libraries used can be replaced with something similar or even custom solution
- Provide patterns that are tighly coupled - you can take anything you need from this template and leave out everything that does not apply to your use-case
- It's never final. The template will evolve along my knowledge and the ecosystem

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
- Pluto and Niddler as debugging tools
- MapStruct as object mapping library
- Resilience4j as resilience library (used for retry logic)
- Kotlinx.Serialization as json serialization library
- OkHttp and Retrofit as network stack

## Documentation

- [Modularization](./MODULARIZATION.md)
- [Navigation](./core/navigation/README.md)
- [Networking](./core/networking/README.md)
- [Object Mapping](#object-mapping)
- [Internal debug build](#internal-debug-build)

## Sample implementations:

- Passing arguments and result during navigation - modules [features/dashboard](./features/dashboard/impl/src/main/java/pl/jsyty/architecturetemplate/feature/dashboard/impl)
and [features/message](./features/message/impl/src/main/java/pl/jsyty/architecturetemplate/feature/message/impl)
- Asynchronous resource loading - module [features/longaction](./features/longaction/impl/src/main/java/pl/jsyty/architecturetemplate/feature/longaction/impl)
- Clean architecture and networking = module [features/weather](./features/weather/impl/src/main/java/pl/jsyty/architecturetemplate/feature/weather/impl)

## Build performance

[Modularization](./MODULARIZATION.md) was architected around gradle cache and compilation avoidance, which should greatly benefit the overall build performance.

We are using Anvil as our DI framework which is much faster than Hilt due to usage of kotlin compiler plugin instead kapt. We are trying to avoid kapt as much as possible since the performance hit from it is pretty much always significant.

We are using [Android Cache Fix Gradle Plugin](https://github.com/gradle/android-cache-fix-gradle-plugin) to fix some AGP cache issues.

## Internal debug build

At my company we have highly technical QA team that is doing a lot of manual testing on our apps. We wanted to provide them with
some build-in debugging tools so they can take a look at logs, network calls, preferences, databases and so on. They can share
whatever they find with developers to shorten feedback loop.

For this to happen we wanted to provide QA team with some special build that will include those tools, but on the other side those
tools should never made their way to production build. We could use `debug` build type for that, but downside of that is that
debug builds have problems with performance (especially with compose as UI framework) and that way QA couldn't evaluate app performance
using the internal debug build.

To work around that we introduced the `internal` build type. It is basically the same build as `release` but with added debug tools.
This way QA team is testing exact same build as clients with only few additional benefits to make their work easier.

## Object Mapping

This is somewhat controversial topic. A lot of people argue that we can just write out mappers as a simple extension method. And yes, we can,
but this have a few disadvantages:

- You have to maintain the code, like any other
- Any change in fields of mapped object might brake the app because of out-of-date mapping code (and i found it to be the case too many times already). This can happen because:
  - New field has default value which **should** be mapped as well. Compiler won't argue about that
  - Some new field was introduced in the middle of data class with the same type of field nearby. This can be solved by always using named parameters, but this is one more thing to remember and enforce
- You have to write this boring code, which can be really long at times
- You have to share the type conversion logic if it's necessary

All of those issues can be solved by using code generation library. In this pattern I'm using [MapStruct](https://mapstruct.org/).
Disadvantage here is that this library is designed to be used for Java code, so i had to implement a few workarounds for annoying issues.
Those can be found [here](./libraries/mapstructspi/src/main/java/pl/jsyty/architecturetemplate/libraries/mapstructspi/CustomAccessorNamingStrategy.kt).
Another disadvantage is that this library is using kapt, which might lower the build speed of a module that it is used in.
So it's not a clear win and **you should always consider if it is worth doing in your use-case**.

## To be done

- Testing setup and patterns!
- Dokka setup
- Detekt setup

## Mentions

For network calls showcase we are using api provided by: [Weather data by Open-Meteo.com](https://open-meteo.com/).
