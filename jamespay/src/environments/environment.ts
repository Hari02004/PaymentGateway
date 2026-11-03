/**
 * Environment configuration for local development.
 * `apiUrl` points at the Spring Boot backend that exposes the
 * authentication endpoints consumed by AuthService.
 */
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api/auth',
};
