/**
 * Shape returned by both /login and /register on success.
 */
export type UserRole = 'USER' | 'ADMIN';

export interface AuthResponse {
  token: string;
  userId: number;
  name: string;
  email: string;
  role: UserRole;
}
