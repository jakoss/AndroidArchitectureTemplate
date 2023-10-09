package pl.jsyty.architecturetemplate.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.deliveryhero.whetstone.compose.injectedViewModel

@Composable
public inline fun <reified VM : ViewModel> composeViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = requireNotNull(LocalViewModelStoreOwner.current),
    key: String? = null,
    extras: CreationExtras = (viewModelStoreOwner as? HasDefaultViewModelProviderFactory)
        ?.defaultViewModelCreationExtras
        ?: CreationExtras.Empty,
): VM = injectedViewModel(viewModelStoreOwner, key, extras)
