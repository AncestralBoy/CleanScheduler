import { useEffect, useState } from 'react'
import { getCleaningStatus, updateAssignmentStatus } from '../api/me'
import type { CleaningStatusDTO } from '../types'

const statusLabel = { PENDING: 'In attesa', COMPLETED: 'Completata', ABANDONED: 'Abbandonata' }
const statusClass = { PENDING: 'badge-pending', COMPLETED: 'badge-completed', ABANDONED: 'badge-abandoned' }

export default function DashboardPage() {
  const [data, setData] = useState<CleaningStatusDTO | null>(null)
  const [loading, setLoading] = useState(true)
  const [actionLoading, setActionLoading] = useState(false)

  const load = () => {
    setLoading(true)
    getCleaningStatus().then(setData).finally(() => setLoading(false))
  }

  useEffect(load, [])

  const handleAction = async (status: 'COMPLETED' | 'ABANDONED') => {
    if (!data?.pendingAssignment) return
    setActionLoading(true)
    try {
      await updateAssignmentStatus(data.pendingAssignment.id, status)
      load()
    } finally {
      setActionLoading(false)
    }
  }

  if (loading) return <div className="page"><p>Caricamento...</p></div>
  if (!data) return null

  return (
    <div className="page">
      <div className="page-header"><h1>La mia dashboard</h1></div>

      {data.pendingAssignment ? (
        <div className="pending-card">
          <p className="card-title" style={{ color: 'rgba(255,255,255,0.7)' }}>📋 Pulizia assegnata</p>
          <p className="room-name">{data.pendingAssignment.room.name}</p>
          <p className="week-info">
            Settimana del {new Date(data.pendingAssignment.weekStart).toLocaleDateString('it-IT')}
            &nbsp;· vale {data.pendingAssignment.room.score} punto/i
          </p>
          <div className="pending-actions">
            <button
              className="btn btn-success"
              onClick={() => handleAction('COMPLETED')}
              disabled={actionLoading}
            >✓ Completata</button>
            <button
              className="btn btn-danger"
              onClick={() => handleAction('ABANDONED')}
              disabled={actionLoading}
            >✗ Abbandona</button>
          </div>
        </div>
      ) : (
        <div className="card">
          <div className="empty-state">
            <p style={{ fontSize: '2rem' }}>🎉</p>
            <p>Nessuna pulizia in attesa per questa settimana.</p>
          </div>
        </div>
      )}

      <div className="stats-grid">
        <div className="stat-card">
          <div className="stat-value">{data.completedCount}</div>
          <div className="stat-label">✅ Completate</div>
        </div>
        <div className="stat-card">
          <div className="stat-value">{data.abandonedCount}</div>
          <div className="stat-label">❌ Abbandonate</div>
        </div>
      </div>

      <div className="card">
        <p className="section-title">📜 Storico pulizie</p>
        {data.completedHistory.length === 0 ? (
          <div className="empty-state"><p>Nessuna pulizia completata.</p></div>
        ) : (
          <div className="table-container">
            <table>
              <thead>
                <tr><th>Stanza</th><th>Settimana</th><th>Punti</th><th>Stato</th></tr>
              </thead>
              <tbody>
                {data.completedHistory.map(a => (
                  <tr key={a.id}>
                    <td>{a.room.name}</td>
                    <td>{new Date(a.weekStart).toLocaleDateString('it-IT')}</td>
                    <td>+{a.room.score}</td>
                    <td><span className={`badge ${statusClass[a.status]}`}>{statusLabel[a.status]}</span></td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  )
}
