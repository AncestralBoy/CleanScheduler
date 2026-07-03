import { createContext, useContext, useState } from 'react'
import type { ReactNode } from 'react'
import type { AuthUser } from '../types'

interface AuthContextType {
  user: AuthUser | null
  token: string | null
  login: (token: string) => void
  logout: () => void
  isAuthenticated: boolean
  isAdmin: boolean
}

function parseJwt(token: string): AuthUser {
  const payload = JSON.parse(atob(token.split('.')[1]))
  return { nickname: payload.sub, role: payload.role }
}

const AuthContext = createContext<AuthContextType | null>(null)

export function AuthProvider({ children }: { children: ReactNode }) {
  const stored = localStorage.getItem('token')
  const [token, setToken] = useState<string | null>(stored)
  const [user, setUser] = useState<AuthUser | null>(stored ? parseJwt(stored) : null)

  const login = (newToken: string) => {
    localStorage.setItem('token', newToken)
    setToken(newToken)
    setUser(parseJwt(newToken))
  }

  const logout = () => {
    localStorage.removeItem('token')
    setToken(null)
    setUser(null)
  }

  return (
    <AuthContext.Provider value={{
      user, token, login, logout,
      isAuthenticated: !!token,
      isAdmin: user?.role === 'ADMIN',
    }}>
      {children}
    </AuthContext.Provider>
  )
}

export function useAuth() {
  const ctx = useContext(AuthContext)
  if (!ctx) throw new Error('useAuth must be used within AuthProvider')
  return ctx
}
