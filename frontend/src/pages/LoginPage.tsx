import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { login } from '../api/auth'
import { useAuth } from '../context/AuthContext'

export default function LoginPage() {
  const [nickname, setNickname] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)
  const { login: authLogin } = useAuth()
  const navigate = useNavigate()

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError('')
    setLoading(true)
    try {
      const { token } = await login({ nickname, password })
      authLogin(token)
      navigate('/')
    } catch {
      setError('Nickname o password non validi.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="auth-container">
      <div className="auth-card">
        <h1>🧹 CleanScheduler</h1>
        <h2>Accedi</h2>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Nickname</label>
            <input value={nickname} onChange={e => setNickname(e.target.value)} required autoFocus />
          </div>
          <div className="form-group">
            <label>Password</label>
            <input type="password" value={password} onChange={e => setPassword(e.target.value)} required />
          </div>
          {error && <p className="error">{error}</p>}
          <button type="submit" className="btn btn-primary btn-full" disabled={loading}>
            {loading ? 'Accesso...' : 'Accedi'}
          </button>
        </form>
        <p className="auth-link">Non hai un account? <Link to="/register">Registrati</Link></p>
      </div>
    </div>
  )
}
