# Navigation

Navigation is build around [Direction](./src/main/java/pl/jsyty/architecturetemplate/infrastructure/navigation/Direction.kt) class. This class is an representation of navigation to some place. It might have some parameters if we want to pass data to another screen.

## Example

We have two screens, `ScreenOne` and `ScreenTwo`. We want to navigation from the first to the latter while passing a name parameter. To do that we have to create a direction pointing to `ScreenTwo`.

```kotlin
@Parcelize
data class ScreenTwoDirection(val name: String): Direction
```

`ScreenTwo` is represented as `Fragment`. So to bind a fragment with direction all we have to do is to use proper base class.

```kotlin
class ScreenTwoFragment: BaseDirectableComposeFragment<ScreenTwoDirection>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // This way we can access the parameters passed to our screen
        Timber.d("Name passed via argument: ${direction.name}")
    }
}
```

[Custom Anvil plugin](./processor/src/main/java/pl/jsyty/architecturetemplate/infrastructure/navigation/processor/NavigationCodeGenerator.kt) will automatically bind this fragment to direction, so you don't have to do anything more.

Benefit of separating direction from fragment is that direction can be defined in some shared module (public for another modules) and fragment can be enclosed in impl module that other modules won't depend on.
This gives us great benefits of flattening the module graph which will drastically reduce build times in bigger apps.

So, when we want to navigate to `ScreenTwo`:
```kotlin
val navigationController = // get this from somewhere, may be LocalNavigationController.current in compose or it might be injected anywhere in the app
navigationController.push(ScreenTwoDirection(name = "Jakub"))
```

And that's it, the framework will handle the navigation for us!

We can do another standard navigation action like `replace` or `pop` from the [NavigationController](./src/main/java/pl/jsyty/architecturetemplate/infrastructure/navigation/NavigationController.kt) interface.

## Retrieving the result

We can retrieve the result from one screen in other places. The mechanism is built opon [Fragment Results api](https://developer.android.com/guide/fragments/communicate)
using type safe helpers to simplify whole process.

To handle a result we have to create a [NavigationResult](./src/main/java/pl/jsyty/architecturetemplate/infrastructure/navigation/NavigationResult.kt). For example to pass
string we have to create it:

```kotlin
class MessageNavigationResult : NavigationResult<String>()
```

Then in class we want to **receive** result we have to register for a result (this is based on [compose helper](../ui/src/main/java/pl/jsyty/architecturetemplate/ui/helpers/RegisterForFragmentResult.kt))
but can be easily ported to view system.

```kotlin
RegisterForFragmentResult(MessageNavigationResult()) { fullMessage ->
    // we have result from some another screen!
}
```

Lastly, we have to send result back from the target screen, using [helper on fragment class](../ui/src/main/java/pl/jsyty/architecturetemplate/ui/helpers/RegisterForFragmentResult.kt)

```kotlin
setNavigationResult(MessageNavigationResult(), it.fullMessage)
navigationController.pop()
```

> It's important to remember that setting navigation result **won't** do a pop, we have to do that ourselves!
