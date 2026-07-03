import { useEffect, useState } from 'react'
import { getCurrentWeekAssignments, generateAssignments, toggleUserActive } from '../api/admin'
import { getUsers } from '../api/users'
import type { AssignmentDTO, UserDTO } from '../types'

const statusClass = { PENDING: 'badge-pending', COMPLETED: 'badge-completed', ABANDONED: 'badge-abandoned' }
const statusLabel = { PENDING: 'In attesa', COMPLETED: 'Completata', ABANDONED: 'Abbandonata' }

export default function AdminPage() {
  const [tab, setTab] = useState<'week' | 'users'>('week')
  const [assignments, setAssignments] = useState<AssignmentDTO[]>([])
  const [users, setUsers] = useState<UserDTO[]>([])
  const [generating, setGenerating] = useState(false)
  const [generated, setGenerated] = useState(false)

  useEffect(() => {
    getCurrentWeekAssignments().then(setAssignments)
    getUsers().then(setUsers)
  }, [])

  const handleGenerate = async () => {
    setGenerating(true)
    try {
      await generateAssignments()
      const updated = await getCurrentWeekAssignments()
      setAssignments(updated)
      setGenerated(true)
    } finally {
      setGenerating(false)
    }
  }

  const handleToggle = async (u: UserDTO) => {
    await toggleUserActive(u.roommate.id, !u.roommate.isActive)
    setUsers(prev => prev.map(x =>
      x.id === u.id ? { ...x, roommate: { ...x.roommate, isActive: !x.roommate.isActive } } : x
    ))
  }

  return (
    <div className="page">
      <div className="page-header"><h1>⚙️ Pannello Admin</h1></div>

      <div className="tabs">
        <button className={`tab-btn ${tab === 'week' ? 'active' : ''}`} onClick={() => setTab('week')}>
          Settimana corrente
        </button>
        <button className={`tab-btn ${tab === 'users' ? 'active' : ''}`} onClick={() => setTab('users')}>
          Gestione utenti
        </button>
      </div>

      {tab === 'week' && (
        <div>
          <div className="card" style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
            <div>
              <p className="section-title" style={{ marginBottom: 0 }}>Assignment della settimana</p>
              {generated && <p className="success-msg">Assignment generati con successo.</p>}
            </div>
            <button className="btn btn-primary" onClick={handleGenerate} disabled={generating}>
              {generating ? 'Generazione...' : '⚡ Genera assignment'}
            </button>
          </div>
          <div className="card">
            {assignments.length === 0 ? (
              <div className="empty-state">
                <p>Nessun assignment per questa settimana.</p>
                <p>Premi "Genera assignment" per crearli.</p>
              </div>
            ) : (
              <div className="table-container">
                <table>
                  <thead>
                    <tr><th>Coinquilino</th><th>Stanza</th><th>Stato</th></tr>
                  </thead>
                  <tbody>
                    {assignments.map(a => (
                      <tr key={a.id}>
                        <td>@{a.roommateDTO?.nickname} — {a.roommateDTO?.name} {a.roommateDTO?.surname}</td>
                        <td>{a.room?.name}</td>
                        <td><span className={`badge ${statusClass[a.status]}`}>{statusLabel[a.status]}</span></td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            )}
          </div>
        </div>
      )}

      {tab === 'users' && (
        <div className="card">
          <div className="table-container">
            <table>
              <thead>
                <tr><th>Nickname</th><th>Nome</th><th>Ruolo</th><th>Stato</th><th></th></tr>
              </thead>
              <tbody>
                {users.map(u => (
                  <tr key={u.id}>
                    <td>@{u.roommate.nickname}</td>
                    <td>{u.roommate.name} {u.roommate.surname}</td>
                    <td>{u.role}</td>
                    <td>
                      <span className={`badge ${u.roommate.isActive ? 'badge-active' : 'badge-inactive'}`}>
                        {u.roommate.isActive ? 'Attivo' : 'Inattivo'}
                      </span>
                    </td>
                    <td>
                      <button
                        className={`btn btn-sm ${u.roommate.isActive ? 'btn-danger' : 'btn-success'}`}
                        onClick={() => handleToggle(u)}
                      >
                        {u.roommate.isActive ? 'Disattiva' : 'Attiva'}
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}
    </div>
  )
}
