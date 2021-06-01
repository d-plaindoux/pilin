package io.smallibs.pilin.standard.either

import io.smallibs.pilin.laws.Applicative.`(pure id) apply v = v`
import io.smallibs.pilin.laws.Applicative.`apply (pure f) (pure x) = pure (f x)`
import io.smallibs.pilin.laws.Applicative.`apply f (apply g x) == apply (apply (apply (pure compose) f) g) x`
import io.smallibs.pilin.laws.Applicative.`apply f (pure x) = apply (pure ($ y)) f`
import io.smallibs.pilin.laws.Applicative.`map f x = apply (pure f) x`
import io.smallibs.pilin.standard.either.Either
import io.smallibs.pilin.standard.identity.Identity
import io.smallibs.pilin.standard.option.Option
import io.smallibs.pilin.standard.support.constant
import io.smallibs.pilin.standard.support.either
import io.smallibs.pilin.standard.support.identity
import io.smallibs.pilin.standard.support.option
import io.smallibs.pilin.type.Fun
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.quicktheories.WithQuickTheories

internal class ApplicativeTest : WithQuickTheories {

    private val str: Fun<Int, String> = { i -> i.toString() }
    private val int: Fun<String, Int> = { i -> i.toInt() }

    @Test
    fun `(Either) map f x = apply (pure f) x`() {
        qt().forAll(either<Unit, Int>(constant(Unit))(integers().all())).check { a ->
            runBlocking { Either.applicative<Unit>().`map f x = apply (pure f) x`(str, a) }
        }
    }

    @Test
    fun `(Either) (pure id) apply v = v`() {
        qt().forAll(either<Unit, Int>(constant(Unit))(integers().all())).check { a ->
            runBlocking { Either.applicative<Unit>().`(pure id) apply v = v`(a) }
        }
    }

    @Test
    fun `(Either) apply (pure f) (pure x) = pure (f x)`() {
        qt().forAll(integers().all()).check { a ->
            runBlocking { Either.applicative<Unit>().`apply (pure f) (pure x) = pure (f x)`(str, a) }
        }
    }

    @Test
    fun `(Either) apply f (pure x) = apply (pure ($ y)) f`() {
        qt().forAll(integers().all(), either<Unit, Fun<Int, String>>(constant(Unit))(constant(str))).check { a, f ->
            runBlocking { Either.applicative<Unit>().`apply f (pure x) = apply (pure ($ y)) f`(f, a) }
        }
    }

    @Test
    fun `(Either) apply f (apply g x) == apply (apply (apply (pure compose) f) g) x`() {
        qt().forAll(
            either<Unit, Int>(constant(Unit))(integers().all()),
            either<Unit, Fun<String, Int>>(constant(Unit))(constant(int)),
            either<Unit, Fun<Int, String>>(constant(Unit))(constant(str)))
            .check { a, f, g ->
                runBlocking {
                    Either.applicative<Unit>()
                        .`apply f (apply g x) == apply (apply (apply (pure compose) f) g) x`(f, g, a)
                }
            }
    }

}
