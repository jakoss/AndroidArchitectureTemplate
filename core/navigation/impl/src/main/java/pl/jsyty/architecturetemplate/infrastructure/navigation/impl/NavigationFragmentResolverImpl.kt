package pl.jsyty.architecturetemplate.infrastructure.navigation.impl

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.deliveryhero.whetstone.app.ApplicationScope
import com.squareup.anvil.annotations.ContributesBinding
import dagger.assisted.*
import pl.jsyty.architecturetemplate.infrastructure.navigation.*

class NavigationFragmentResolverImpl
    @AssistedInject
    constructor(
        @Assisted private val parentFragment: Fragment,
        private val handlers: Map<String, @JvmSuppressWildcards DirectionBinding>,
    ) : NavigationFragmentResolver {
        /**
         * We have [handlers] map which represents which [Direction] has been bound to which [Fragment].
         * This funtion resolves proper [Fragment] for given [direction] and (if exists) attach arguments to this fragment.
         *
         * @param direction Direction that points to a [Fragment] we want in return
         * @return Resolved [Fragment] that was bound to [direction]
         *
         * @see Direction
         * @throws IllegalStateException if given [direction] was not bound to any [Fragment]
         */
        override fun resolveFragment(direction: Direction): Fragment {
            val binding =
                handlers[direction.javaClass.canonicalName ?: error("No canonical name for direction")]
            check(binding != null) {
                "Direction not registered"
            }
            val fragmentManager = parentFragment.parentFragmentManager
            val classLoader = parentFragment.requireContext().classLoader
            val fragment = fragmentManager.fragmentFactory.instantiate(classLoader, binding.bind())
            fragment.arguments = bundleOf(ARGUMENT_KEY to direction)
            return fragment
        }

        @AssistedFactory
        @ContributesBinding(ApplicationScope::class)
        interface Factory : NavigationFragmentResolver.Factory {
            override fun create(parentFragment: Fragment): NavigationFragmentResolverImpl
        }
    }
