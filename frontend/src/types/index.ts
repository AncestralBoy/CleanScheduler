export interface RoommateDTO {
  id: number
  name: string
  surname: string
  nickname: string
  cleanPoints: number
  isActive: boolean
}

export interface RoomDTO {
  id: number
  name: string
  score: number
}

export interface AssignmentDTO {
  id: number
  roommateDTO: RoommateDTO
  room: RoomDTO
  weekStart: string
  status: 'PENDING' | 'COMPLETED' | 'ABANDONED'
}

export interface CleaningStatusDTO {
  pendingAssignment: AssignmentDTO | null
  completedHistory: AssignmentDTO[]
  completedCount: number
  abandonedCount: number
}

export interface UserDTO {
  id: number
  role: 'ROOMMATE' | 'ADMIN'
  roommate: RoommateDTO
}

export interface AuthUser {
  nickname: string
  role: 'ROOMMATE' | 'ADMIN'
}
