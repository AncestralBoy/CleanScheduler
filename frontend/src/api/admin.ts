import client from './client'
import type { AssignmentDTO } from '../types'

export const getCurrentWeekAssignments = () =>
  client.get<AssignmentDTO[]>('/admin/assignments/current-week').then((r) => r.data)

export const generateAssignments = () =>
  client.post('/admin/generate-assignments')

export const toggleUserActive = (roommateId: number, active: boolean) =>
  client.patch(`/admin/users/${roommateId}/active`, { active })
