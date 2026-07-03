import { useEffect, useState } from 'react'
import { getLeaderboard } from '../api/leaderboard'
import { useAuth } from '../context/AuthContext'
import type { RoommateDTO } from '../types'

const medal: Record<number, string> = { 1: '🥇', 2: '🥈', 3: '🥉' }

export default function LeaderboardPage() {
  const [list, setList] = useState<RoommateDTO[]>([])
  const [loading, setLoading] = useState(true)
  const { user } = useAuth()

  useEffect(() => {
    getLeaderboard().then(setList).finally(() => setLoading(false))
  }, [])

  if (loading) return <div className="page"><p>Caricamento...</p></div>

  return (
    <div className="page">
      <div className="page-header"><h1>🏆 Classifica</h1></div>
      <div className="card">
        <div className="table-container">
          <table>
            <thead>
              <tr><th>#</th><th>Coinquilino</th><th>Nome</th><th>Punti</th></tr>
            </thead>
            <tbody>
              {list.map((r, i) => (
                <tr key={r.id} className={r.nickname === user?.nickname ? 'leaderboard-row current-user' : ''}>
                  <td><span className={`rank rank-${i + 1}`}>{medal[i + 1] ?? i + 1}</span></td>
                  <td>@{r.nickname}</td>
                  <td>{r.name} {r.surname}</td>
                  <td><strong>{r.cleanPoints}</strong></td>
                </tr>
              ))}
              {list.length === 0 && (
                <tr><td colSpan={4}><div className="empty-state"><p>Nessun dato disponibile.</p></div></td></tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  )
}
