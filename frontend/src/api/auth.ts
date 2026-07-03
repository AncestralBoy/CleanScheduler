import client from './client'

export const register = (data: {
  name: string
  surname: string
  nickname: string
  password: string
}) => client.post('/auth/register', data)

export const login = (data: { nickname: string; password: string }) =>
  client.post<{ token: string }>('/auth/login', data).then((r) => r.data)
