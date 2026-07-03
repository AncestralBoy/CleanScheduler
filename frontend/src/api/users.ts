import client from './client'
import type { UserDTO } from '../types'

export const getUsers = () =>
  client.get<UserDTO[]>('/users').then((r) => r.data)

export const deleteUser = (id: number) =>
  client.delete(`/users/${id}`)
