package com.illiouchine.mvi.core

//import androidx.lifecycle.repeatOnLifecycle

/**
 *
 * According to this : https://www.youtube.com/watch?v=JnN6EFZ6DO8
 * this is some extension function to reduce boilerplate code around flow
 *
 * this explain that we shouldn't use
 * lifecycle.launchWhenStarted { ....  }
 *
 * because this pause only the collector but not the emitter so that should lead to and waste of resources
 */

/*
inline fun <T> Flow<T>.collectWhenStarted(
    lifecycleOwner: LifecycleOwner,
    crossinline action: suspend (value: T) -> Unit
) {
    lifecycleOwner.addRepeatingJob(Lifecycle.State.STARTED){
        collect(action)
    }
}
*/

/*
fun LifecycleOwner.addRepeatingJob(
    state: Lifecycle.State,
    coroutineContext: CoroutineContext = kotlin.coroutines.EmptyCoroutineContext,
    block: suspend CoroutineScope.()  ->Unit
) : Job {
    return lifecycleScope.launch(coroutineContext){
        lifecycle.repeatOnLifecycle(state, block)
    }
}
 */