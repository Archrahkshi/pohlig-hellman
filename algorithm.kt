import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO


/**
 * Pohlig-Hellman algorithm for computing discrete logarithm x,
 * assuming aˣ ≡ b (mod p) and ordₚ(a) = qⁿ.
 * @param p: group order;
 * @param base: logarithm base (a);
 * @param arg: logarithm argument (b);
 * @param q: subgroup order base, prime;
 * @param n: subgroup order exponent, n > 1.
 * @return discrete logarithm x.
 * Complexity: O(n²log₂q + n√q).
 */
fun discreteLog(p: BigInteger, base: BigInteger, arg: BigInteger, q: BigInteger, n: Int): BigInteger {
    val a = arrayOfNulls<BigInteger>(n).apply { this[n - 1] = base }
    val b = arrayOfNulls<BigInteger>(n).apply { this[n - 1] = arg }
    for (i in 1 until n) {
        a[n - i - 1] = a[n - i]!!.modPow(q, p) // aₙ₋ᵢ^q
        b[n - i - 1] = b[n - i]!!.modPow(q, p) // bₙ₋ᵢ^q
    }

    val x = arrayOfNulls<BigInteger>(n)
    for (i in 0 until n) {
        x[i] = babyStepGiantStep(
            p,
            a[0]!!,
            b[i]!!.modMul(
                (if (i > 0) {
                    var gamma = a[i]!!.modPow(x[0], p) // aᵢ^x₀
                    for (j in 1 until i)
                        gamma = gamma.modMul(a[i - j]!!.modPow(x[j], p), p)
                    gamma // aᵢ^x₀ * a₋₁^x₁ * ... * a₁^xᵢ₋₁
                } else
                    ONE).modInverse(p),
                p
            ) // bᵢγ⁻¹
        )
    }

    var log = x[0]!!
    for (i in 1 until n)
        log = log.modAdd(x[i]!!.modMul(q.modPow(i.toBigInteger(), p), p), p) // x₀ + x₁q + ... + xₙ₋₁qⁿ⁻¹
    return log
}

/**
 * Baby-step giant-step (BSGS) algorithm for computing discrete logarithm x,
 * assuming αˣ ≡ β (mod n).
 * @param n: group order;
 * @param alpha: logarithm base (α);
 * @param beta: logarithm argument (β).
 * @return discrete logarithm x.
 * Complexity: O(√n).
 */
fun babyStepGiantStep(n: BigInteger, alpha: BigInteger, beta: BigInteger): BigInteger {
    val sqrt = n.sqrtFloor()
    val m = sqrt + ONE
    val table = mutableMapOf<BigInteger, BigInteger>()
    for (j in ZERO..sqrt)
        table[j] = alpha.modPow(j, n) // αʲ
    val alphaMinusM = alpha.modPow(m.modInverse(n), n) // α⁻ᵐ
    var gamma = beta
    for (i in ZERO..sqrt) {
        val j = table.find(gamma)
        if (j != ZERO)
        // The Chinese remainder theorem guarantees there exists a unique solution
            return i.modMul(m, n).modAdd(j, n) // im + j
        else
            gamma = gamma.modMul(alphaMinusM, n) // γα⁻ᵐ
    }
    return ZERO
}
