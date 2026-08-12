# fibonacci-app

## Technical Decisions

### BigInteger instead of long

Decision:
Use BigInteger for Fibonacci values.

Reason:
The exercise includes values such as F(100). Java's long cannot represent
Fibonacci values beyond F(92) without overflow.

Trade-off:
BigInteger supports arbitrarily large integers but has higher memory and
computational cost than primitive numeric types.

### Iterative algorithm instead of recursion

Decision:
Use an iterative Fibonacci implementation.

Reason:
Recursion recalculates the same values many times and has exponential
time complexity. The iterative approach calculates each value once.

Complexity:
Time: O(n)
Additional memory without cache: O(1)

### List<BigInteger> for the Fibonacci cache

Decision:
Store calculated Fibonacci values in a List<BigInteger>.

Reason:
Fibonacci indices are continuous. If F(n) has been calculated, all values
between F(0) and F(n) have also been calculated.

This makes the list index naturally correspond to the Fibonacci index:

cache.get(n) = F(n)

Alternative considered:
Map<Integer, BigInteger>

Why it was not selected:
A Map would support sparse keys, but Fibonacci calculations produce a
continuous sequence, so a List is simpler and better represents the problem.