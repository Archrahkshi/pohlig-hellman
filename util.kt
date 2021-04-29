import java.math.BigInteger
import java.math.BigInteger.ZERO


/**
 * Find [value] in map [this] and return its key.
 */
fun Map<BigInteger, BigInteger>.find(value: BigInteger): BigInteger {
    forEach {
        if (it.value == value) return it.key
    }
    return ZERO
}

/**
 * Modular multiplication: [this] * [other] mod [mod].
 */
fun BigInteger.modMul(other: BigInteger, mod: BigInteger) = multiply(other).mod(mod)!!

/**
 * Modular addition: [this] + [other] mod [mod].
 */
fun BigInteger.modAdd(other: BigInteger, mod: BigInteger) = add(other).mod(mod)!!

fun BigInteger.sqrtFloor(): BigInteger {
    var half = ZERO.setBit(bitLength() / 2)
    var cur = half
    while (true) {
        val tmp = half.add(divide(half)).shiftRight(1)
        if (tmp == half || tmp == cur) return tmp
        cur = half
        half = tmp
    }
}

/**
 * Range between BigIntegers, inclusive: [this]..[other].
 */
operator fun BigInteger.rangeTo(other: BigInteger) = BigIntegerRange(this, other)

class BigIntegerRange(
    override val start: BigInteger,
    override val endInclusive: BigInteger
) : ClosedRange<BigInteger>, Iterable<BigInteger> {
    override operator fun iterator() = BigIntegerRangeIterator(this)
}

class BigIntegerRangeIterator(private val range: ClosedRange<BigInteger>) : Iterator<BigInteger> {
    private var current = range.start
    override fun hasNext() = current <= range.endInclusive
    override fun next() = current++
}
