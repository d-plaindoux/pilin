package io.smallibs.pilin.standard.option

import io.smallibs.pilin.control.Selective
import io.smallibs.pilin.standard.option.Option.TK

object Selective {
    private class SelectiveImpl(monad: io.smallibs.pilin.control.Monad.API<TK>) :
        Selective.API<TK>,
        Selective.ViaMonad<TK>(monad) {
    }

    val selective: Selective.API<TK> = SelectiveImpl(Monad.monad)
}