import { useState } from 'react'
import './App.css'
import { fetchFibonacci } from './services/fibonacciApi'
import type { FibonacciResponse } from './services/fibonacciApi'

function App() {
  const [input, setInput] = useState('')
  const [loading, setLoading] = useState(false)
  const [result, setResult] = useState<FibonacciResponse | null>(null)
  const [error, setError] = useState<string | null>(null)

  function validate(value: string) {
    if (!value) return 'Please enter a Fibonacci index.'
    if (!/^\d+$/.test(value)) {
      // contains non-digit or decimal
      if (/^[0-9]+\.[0-9]+$/.test(value)) return 'The index must be an integer.'
      return 'Please enter a valid number.'
    }
    const n = Number(value)
    if (!Number.isFinite(n)) return 'Please enter a valid number.'
    if (n < 0 || n > 10000) return 'The index must be a positive integer not exceeding 10000.'
    return null
  }

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault()
    setError(null)
    setResult(null)

    const v = validate(input.trim())
    if (v) {
      setError(v)
      return
    }

    const n = Number(input.trim())
    setLoading(true)
    try {
      const res = await fetchFibonacci(n)
      setResult(res)
    } catch (err: any) {
      setError(err?.message || 'Could not connect to the server. Please try again.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <main id="root">
      <section id="center" className="calculator">
        <h1>Fibonacci Calculator</h1>
        <p>Enter a non-negative integer to compute Fibonacci(n).</p>

        <form onSubmit={handleSubmit} className="form" noValidate>
          <label htmlFor="index">Enter Fibonacci index</label>
          <input
            id="index"
            name="index"
            inputMode="numeric"
            pattern="\\d*"
            value={input}
            onChange={(e) => setInput(e.target.value)}
            aria-describedby="error"
          />

          <div className="actions">
            <button type="submit" disabled={loading}>
              {loading ? 'Calculating...' : 'Calculate'}
            </button>
          </div>
        </form>

        <div className="output">
          {error && (
            <div id="error" className="error" role="alert">
              {error}
            </div>
          )}

          {result && (
            <div className="result">
              <h2>Fibonacci({result.n})</h2>
              <pre className="value" aria-live="polite">{result.value}</pre>
              <p className="cached">{result.cached ? 'Retrieved from cache' : 'Calculated and cached'}</p>
            </div>
          )}
        </div>
      </section>
    </main>
  )
}

export default App
