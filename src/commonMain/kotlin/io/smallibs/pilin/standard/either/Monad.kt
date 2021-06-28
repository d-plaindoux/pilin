package io.smallibs.pilin.standard.either

import io.smallibs.pilin.control.Monad
import io.smallibs.pilin.standard.either.Either.Companion.left
import io.smallibs.pilin.standard.either.Either.EitherK
import io.smallibs.pilin.standard.either.Either.EitherK.Companion.fold
import io.smallibs.pilin.type.App

object Monad {
    private class MonadImpl<L>(applicative: io.smallibs.pilin.control.Applicative.API<EitherK<L>>) :
        Monad.API<EitherK<L>>,
        Monad.WithReturnsMapAndJoin<EitherK<L>>,
        Monad.ViaApplicative<EitherK<L>>(applicative) {
        override suspend fun <A> join(mma: App<EitherK<L>, App<EitherK<L>, A>>): App<EitherK<L>, A> =
            mma.fold(::left) { it }
    }

    fun <L> monad(): Monad.API<EitherK<L>> = MonadImpl(Applicative.applicative())
}