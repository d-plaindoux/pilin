package io.smallibs.pilin.abstractions.comprehension

import io.smallibs.pilin.abstractions.Monad
import io.smallibs.pilin.abstractions.comprehension.continuation.Reflection
import io.smallibs.pilin.type.App
import io.smallibs.pilin.type.Supplier

class Comprehension<F>(private val monad: Monad.API<F>) : Monad.API<F> by monad {

    private var reflection = Reflection.represents(monad)

    suspend fun <B> App<F, B>.bind(): B =
        reflection.reflect(this)

    companion object {
        suspend fun <F, A> run(monad: Monad.API<F>, f: suspend Comprehension<F>.() -> A): App<F, A> =
            Comprehension(monad).let { comprehension ->
                comprehension.reflection.reify { comprehension.f() }
            }
    }
}