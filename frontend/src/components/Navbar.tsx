import { NavLink, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

export default function Navbar() {
  const { user, isAdmin, logout } = useAuth()
  const navigate = useNavigate()

  const handleLogout = () => {
    logout()
    navigate('/login')
  }

  return (
    <nav className="navbar">
      <NavLink to="/" className="navbar-brand">🧹 CleanScheduler</NavLink>
      <div className="navbar-links">
        <NavLink to="/">Dashboard</NavLink>
        <NavLink to="/leaderboard">Classifica</NavLink>
        <NavLink to="/profile">Profilo</NavLink>
        {isAdmin && <NavLink to="/admin">Admin</NavLink>}
      </div>
      <div className="navbar-user">
        <span>@{user?.nickname}</span>
        <button className="btn btn-ghost btn-sm" onClick={handleLogout}>Esci</button>
      </div>
    </nav>
  )
}
