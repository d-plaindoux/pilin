package io.smallibs.pilin.standard.continuation

import io.smallibs.pilin.control.Selective
import io.smallibs.pilin.standard.continuation.Continuation.TK

object Selective {
    private class SelectiveImpl<O>(monad: io.smallibs.pilin.control.Monad.API<TK<O>>) :
        Selective.API<TK<O>>,
        Selective.ViaMonad<TK<O>>(monad) {
    }

    fun <O> selective(): Selective.API<TK<O>> = SelectiveImpl(Monad.monad())
}