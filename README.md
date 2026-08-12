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

## Architecture Decisions (service & API)

### Spring-managed `FibonacciService`

Decision:
`FibonacciService` is annotated with `@Service` and managed by Spring. The
controller receives it via constructor injection.

Reason:
Dependency injection reduces coupling, keeps the controller thin, and makes
testing easier (the service can be instantiated directly in unit tests).

### REST endpoint

Decision:
Expose a simple GET endpoint: `GET /api/fibonacci/{n}` implemented in
`FibonacciController`.

Contract:
- Success (200): returns JSON with `n` (int), `value` (String), and
	`cached` (boolean).
- Error (400): returns an `ApiErrorResponse` JSON with `status`, `error`,
	and `message`.

Reason:
The operation is a read-only query; GET is the appropriate HTTP verb. The
endpoint delegates calculation and cache logic to the service layer.

### DTOs and number format

Decision:
The public response DTO is `FibonacciResponse` with fields: `n` (int),
`value` (String), `cached` (boolean). The `value` is serialized as a String.

Reason:
The backend uses `BigInteger` for correctness. Serializing the value as a
String in JSON preserves exact precision for clients (JavaScript `Number`
cannot safely represent very large integers).

### Cache semantics

Decision:
The service maintains an in-memory `List<BigInteger>` cache initialized with
`[0, 1]`. `isCached(n)` returns whether `n` is already stored. The
controller checks `isCached(n)` before calling `calcular(n)` to populate the
`cached` flag in the response.

Behavior:
- Requesting a value already in cache returns it immediately without extra
	computation.
- Requesting a larger `n` computes only the missing Fibonacci numbers and
	appends them to the cache.

### Global exception handling

Decision:
`GlobalExceptionHandler` (annotated with `@RestControllerAdvice`) maps
`IllegalArgumentException` to HTTP 400 responses with an `ApiErrorResponse`
body containing `status`, `error`, and `message`.

Reason:
Centralizes error mapping and keeps controllers free of try/catch blocks for
expected validation errors.
