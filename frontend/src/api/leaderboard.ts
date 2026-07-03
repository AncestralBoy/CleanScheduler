import client from './client'
import type { RoommateDTO } from '../types'

export const getLeaderboard = () =>
  client.get<RoommateDTO[]>('/leaderboard').then((r) => r.data)
