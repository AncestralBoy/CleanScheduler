import client from './client'
import type { CleaningStatusDTO } from '../types'

export const getCleaningStatus = () =>
  client.get<CleaningStatusDTO>('/me/cleaning-status').then((r) => r.data)

export const updateAssignmentStatus = (id: number, status: 'COMPLETED' | 'ABANDONED') =>
  client.patch(`/me/assignments/${id}/status`, { status })

export const updateProfile = (data: { name: string; surname: string; nickname: string }) =>
  client.patch('/me/profile', data)

export const updatePassword = (data: { currentPassword: string; newPassword: string }) =>
  client.patch('/me/password', data)
