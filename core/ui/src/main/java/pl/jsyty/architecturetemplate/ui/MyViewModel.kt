package pl.jsyty.architecturetemplate.ui

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.fragment.app.*
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.savedstate.SavedStateRegistryOwner
import tangle.inject.InternalTangleApi
import tangle.viewmodel.internal.TangleViewModelFactory

/**
 * FIXME: Temporary workaround until my pr: https://github.com/RBusarow/Tangle/pull/527 get's merged and released
 */
@Composable
@OptIn(InternalTangleApi::class)
inline fun <reified VM : ViewModel> myViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
): VM {
    return when {
        viewModelStoreOwner is ComponentActivity -> {
            val args = viewModelStoreOwner.intent.extras
            val defaultFactory = viewModelStoreOwner.defaultViewModelProviderFactory

            val factory = TangleViewModelFactory(viewModelStoreOwner, args, defaultFactory)
            viewModel(viewModelStoreOwner, factory = factory)
        }
        viewModelStoreOwner is SavedStateRegistryOwner &&
            viewModelStoreOwner is HasDefaultViewModelProviderFactory -> {
            val args = currentFragmentOrNull(viewModelStoreOwner)?.arguments
            val defaultFactory = viewModelStoreOwner.defaultViewModelProviderFactory

            val factory = TangleViewModelFactory(viewModelStoreOwner, args, defaultFactory)
            viewModel(viewModelStoreOwner, factory = factory)
        }
        else -> {
            viewModel()
        }
    }
}

@Composable
@PublishedApi
internal fun currentFragmentOrNull(
    viewModelStoreOwner: ViewModelStoreOwner,
): Fragment? {
    val view = LocalView.current
    return try {
        FragmentManager.findFragment<Fragment>(view).takeIf {
            // let's make sure the fragment for a view is the correct store owner
            it.viewLifecycleOwner == viewModelStoreOwner
        }
    } catch (_: IllegalStateException) {
        // current scope is not a fragment
        null
    }
}
