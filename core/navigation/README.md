# Navigation

Navigation is build around Direction class. This class is an representation of navigation to some place. It might have some parameters if we want to pass data to another screen.

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

Anvil plugin will automatically bind this fragment to direction, so you don't have to do anything more.

Benefit of separating direction from fragment is that direction can be defined in some shared module (public for another modules) and fragment can be enclosed in impl module that other modules won't depend on.
This gives us great benefits of flattening the module graph which will drastically reduce build times in bigger apps.

So, when we want to navigate to `ScreenTwo`:
```kotlin
val navigationController = // get this from somewhere, may be LocalNavigationController.current in compose or it might be injected anywhere in the app
navigationController.push(ScreenTwoDirection(name = "Jakub"))
```

And that's it, the framework will handle the navigation for us!

We can do another standard navigation action like `replace` or `pop` from the `NavigationController` interface.
