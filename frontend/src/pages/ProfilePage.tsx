import { useEffect, useState } from 'react'
import { getMe, updateActive, updatePassword, updateProfile } from '../api/me'
import { useAuth } from '../context/AuthContext'

export default function ProfilePage() {
  const { user } = useAuth()

  const [profile, setProfile] = useState({ name: '', surname: '', nickname: user?.nickname ?? '' })
  const [profileMsg, setProfileMsg] = useState('')
  const [profileErr, setProfileErr] = useState('')

  const [pwd, setPwd] = useState({ currentPassword: '', newPassword: '' })
  const [pwdMsg, setPwdMsg] = useState('')
  const [pwdErr, setPwdErr] = useState('')

  const [isActive, setIsActive] = useState<boolean | null>(null)
  const [activeLoading, setActiveLoading] = useState(false)

  useEffect(() => {
    getMe().then((me) => setIsActive(me.roommate.isActive))
  }, [])

  const handleToggleActive = async () => {
    if (isActive === null) return
    setActiveLoading(true)
    try {
      await updateActive(!isActive)
      setIsActive(!isActive)
    } finally {
      setActiveLoading(false)
    }
  }

  const setP = (k: keyof typeof profile) => (e: React.ChangeEvent<HTMLInputElement>) =>
    setProfile(f => ({ ...f, [k]: e.target.value }))

  const setPw = (k: keyof typeof pwd) => (e: React.ChangeEvent<HTMLInputElement>) =>
    setPwd(f => ({ ...f, [k]: e.target.value }))

  const handleProfile = async (e: React.FormEvent) => {
    e.preventDefault()
    setProfileMsg(''); setProfileErr('')
    try {
      await updateProfile(profile)
      setProfileMsg('Profilo aggiornato.')
    } catch (err: any) {
      setProfileErr(err.response?.data?.message ?? 'Errore durante l\'aggiornamento.')
    }
  }

  const handlePassword = async (e: React.FormEvent) => {
    e.preventDefault()
    setPwdMsg(''); setPwdErr('')
    try {
      await updatePassword(pwd)
      setPwdMsg('Password aggiornata.')
      setPwd({ currentPassword: '', newPassword: '' })
    } catch {
      setPwdErr('Password attuale non corretta.')
    }
  }

  return (
    <div className="page">
      <div className="page-header"><h1>👤 Profilo</h1></div>

      <div className="card" style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
        <div>
          <p className="section-title" style={{ marginBottom: 0 }}>Disponibilità per le pulizie</p>
          <p style={{ margin: 0, opacity: 0.7 }}>
            {isActive ? 'Sei incluso nella rotazione settimanale.' : 'Sei escluso dalla rotazione settimanale.'}
          </p>
        </div>
        <button
          className={`btn ${isActive ? 'btn-danger' : 'btn-success'}`}
          onClick={handleToggleActive}
          disabled={isActive === null || activeLoading}
        >
          {isActive ? 'Disattiva' : 'Attiva'}
        </button>
      </div>

      <div className="card">
        <p className="section-title">Modifica profilo</p>
        <form onSubmit={handleProfile}>
          <div className="form-row">
            <div className="form-group">
              <label>Nome</label>
              <input value={profile.name} onChange={setP('name')} required />
            </div>
            <div className="form-group">
              <label>Cognome</label>
              <input value={profile.surname} onChange={setP('surname')} required />
            </div>
          </div>
          <div className="form-group">
            <label>Nickname</label>
            <input value={profile.nickname} onChange={setP('nickname')} required />
          </div>
          {profileErr && <p className="error">{profileErr}</p>}
          {profileMsg && <p className="success-msg">{profileMsg}</p>}
          <button type="submit" className="btn btn-primary">Salva modifiche</button>
        </form>
      </div>

      <div className="card">
        <p className="section-title">Cambia password</p>
        <form onSubmit={handlePassword}>
          <div className="form-group">
            <label>Password attuale</label>
            <input type="password" value={pwd.currentPassword} onChange={setPw('currentPassword')} required />
          </div>
          <div className="form-group">
            <label>Nuova password</label>
            <input type="password" value={pwd.newPassword} onChange={setPw('newPassword')} required />
          </div>
          {pwdErr && <p className="error">{pwdErr}</p>}
          {pwdMsg && <p className="success-msg">{pwdMsg}</p>}
          <button type="submit" className="btn btn-primary">Aggiorna password</button>
        </form>
      </div>
    </div>
  )
}
