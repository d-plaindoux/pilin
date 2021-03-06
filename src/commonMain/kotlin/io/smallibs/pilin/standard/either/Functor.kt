package io.smallibs.pilin.standard.either

import io.smallibs.pilin.abstractions.Functor
import io.smallibs.pilin.core.Standard.Infix.then
import io.smallibs.pilin.standard.either.Either.Companion.left
import io.smallibs.pilin.standard.either.Either.Companion.right
import io.smallibs.pilin.standard.either.Either.EitherK
import io.smallibs.pilin.standard.either.Either.EitherK.Companion.fold
import io.smallibs.pilin.type.App
import io.smallibs.pilin.type.Fun

object Functor {
    private class FunctorImpl<L> : Functor.API<EitherK<L>> {
        override suspend fun <A, B> map(f: Fun<A, B>): Fun<App<EitherK<L>, A>, App<EitherK<L>, B>> =
            { ma -> ma.fold(::left, f then ::right) }
    }

    fun <L> functor(): Functor.API<EitherK<L>> = FunctorImpl()
}