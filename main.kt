import java.math.BigInteger

fun main() {
    val p = BigInteger("181").also { println("\nGroup order (p): $it") }
    val a = BigInteger("62").also { println("Logarithm base (a): $it") }
    val b = BigInteger("65").also { println("Logarithm argument (b): $it") }
    val q = BigInteger("3").also { println("Subgroup order base (q): $it") }
    val n = 2.also { println("Subgroup order exponent (n): = $it") }

    assert(n > 1)
    assert(a.modPow(q.modPow(n.toBigInteger(), p), p) == BigInteger.ONE) // ordₚ(a) = qⁿ

    val log = discreteLog(p, a, b, q, n).also { println("\nDiscrete logarithm: $it\n") } // 5
    assert(log == BigInteger("5"))
}
