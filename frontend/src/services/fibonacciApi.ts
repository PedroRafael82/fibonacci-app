export type FibonacciResponse = {
  n: number;
  value: string;
  cached: boolean;
};

export async function fetchFibonacci(n: number): Promise<FibonacciResponse> {
  const res = await fetch(`http://localhost:8080/api/fibonacci/${n}`);
  if (!res.ok) {
    // try parse error body
    try {
      const err = await res.json();
      const message = err?.message || 'Server error';
      throw new Error(message);
    } catch (e) {
      throw new Error('Could not connect to the server. Please try again.');
    }
  }

  const body = await res.json();
  return body as FibonacciResponse;
}
