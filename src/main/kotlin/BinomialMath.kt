object BinomialMath {
    val memoizedFactorials = HashMap<Int, Int>()

    private fun factorial(n: Int): Int {
        // <0! is always 1
        return when (n) {
            in Int.MIN_VALUE..1 -> 1
            else -> n * factorial(n - 1)
        }
    }

    private fun factorial_memo(n: Int): Int {
        // Check 1 or less
        if (n <= 1) {
            return 1
        }

        // Check memoized
        if (memoizedFactorials.containsKey(n)) {
            return memoizedFactorials[n]!!
        }

        memoizedFactorials[n] = n * factorial_memo(n - 1)
        return memoizedFactorials[n]!!
    }

    fun nCk(n: Int, k: Int): Int {
        return factorial(n) / (factorial(k) * factorial(n - k))
    }

    fun nCk_memo(n: Int, k: Int): Int {
        return factorial_memo(n) / (factorial_memo(k) * factorial_memo(n - k))
    }


}