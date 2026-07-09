import client from './client'
import type { CleaningStatusDTO, UserDTO } from '../types'

export const getMe = () =>
  client.get<UserDTO>('/me').then((r) => r.data)

export const getCleaningStatus = () =>
  client.get<CleaningStatusDTO>('/me/cleaning-status').then((r) => r.data)

export const updateActive = (active: boolean) =>
  client.patch('/me/active', { active })

export const updateAssignmentStatus = (id: number, status: 'COMPLETED' | 'ABANDONED') =>
  client.patch(`/me/assignments/${id}/status`, { status })

export const updateProfile = (data: { name: string; surname: string; nickname: string }) =>
  client.patch('/me/profile', data)

export const updatePassword = (data: { currentPassword: string; newPassword: string }) =>
  client.patch('/me/password', data)
