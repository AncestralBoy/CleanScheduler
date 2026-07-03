import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { register } from '../api/auth'

export default function RegisterPage() {
  const [form, setForm] = useState({ name: '', surname: '', nickname: '', password: '' })
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)
  const navigate = useNavigate()

  const set = (k: keyof typeof form) => (e: React.ChangeEvent<HTMLInputElement>) =>
    setForm(f => ({ ...f, [k]: e.target.value }))

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError('')
    setLoading(true)
    try {
      await register(form)
      navigate('/login')
    } catch (err: any) {
      setError(err.response?.data?.message ?? 'Registrazione fallita.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="auth-container">
      <div className="auth-card">
        <h1>🧹 CleanScheduler</h1>
        <h2>Registrati</h2>
        <form onSubmit={handleSubmit}>
          <div className="form-row">
            <div className="form-group">
              <label>Nome</label>
              <input value={form.name} onChange={set('name')} required />
            </div>
            <div className="form-group">
              <label>Cognome</label>
              <input value={form.surname} onChange={set('surname')} required />
            </div>
          </div>
          <div className="form-group">
            <label>Nickname</label>
            <input value={form.nickname} onChange={set('nickname')} required />
          </div>
          <div className="form-group">
            <label>Password</label>
            <input type="password" value={form.password} onChange={set('password')} required />
          </div>
          {error && <p className="error">{error}</p>}
          <button type="submit" className="btn btn-primary btn-full" disabled={loading}>
            {loading ? 'Registrazione...' : 'Registrati'}
          </button>
        </form>
        <p className="auth-link">Hai già un account? <Link to="/login">Accedi</Link></p>
      </div>
    </div>
  )
}
