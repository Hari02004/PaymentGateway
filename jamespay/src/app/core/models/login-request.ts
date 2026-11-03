/**
 * Payload sent to POST /api/auth/login.
 */
export interface LoginRequest {
  email: string;
  password: string;
}
