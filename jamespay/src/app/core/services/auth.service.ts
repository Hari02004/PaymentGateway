import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { LoginRequest } from '../models/login-request';
import { RegisterRequest } from '../models/register-request';
import { AuthResponse } from '../models/auth-response';

/** localStorage keys are centralised here so a rename only happens in one place. */
const TOKEN_KEY = 'jamespay_token';
const USER_KEY = 'jamespay_user';

/**
 * AuthService owns every interaction with the authentication API and is the
 * single source of truth for the current session (token + user profile).
 * Components should never touch localStorage directly - they go through
 * the methods below so the storage strategy can change without touching the UI.
 */
@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = environment.apiUrl;

  /**
   * Reactive, read-only snapshot of the signed-in user.
   * Populated from localStorage on service construction so a page refresh
   * does not lose the session, and updated on every successful login/register.
   */
  readonly currentUser = signal<AuthResponse | null>(this.readStoredUser());

  /**
   * POST /login
   * On success the token and user profile are persisted so the session
   * survives a page reload, and the `currentUser` signal is updated for
   * any component (navbar, dashboard) that reads it.
   */
  login(request: LoginRequest): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${this.baseUrl}/login`, request)
      .pipe(tap((response) => this.persistSession(response)));
  }

  /**
   * POST /register
   * Registration does not automatically sign the user in; the caller
   * navigates to the login page afterwards per the product requirements.
   */
  register(request: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.baseUrl}/register`, request);
  }

  /**
   * Clears the stored session. Does not call the backend because the API
   * contract only defines /login and /register; if a /logout endpoint is
   * added later this is the only place that needs to change.
   */
  logout(): void {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
    this.currentUser.set(null);
  }

  /** True when a JWT is present in storage. Does not validate expiry. */
  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  /** Returns the raw JWT, or null if the user is signed out. */
  getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  }

  private persistSession(response: AuthResponse): void {
    localStorage.setItem(TOKEN_KEY, response.token);
    localStorage.setItem(USER_KEY, JSON.stringify(response));
    this.currentUser.set(response);
  }

  private readStoredUser(): AuthResponse | null {
    const raw = localStorage.getItem(USER_KEY);
    if (!raw) {
      return null;
    }
    try {
      return JSON.parse(raw) as AuthResponse;
    } catch {
      // Corrupted storage should never crash the app - treat as signed out.
      return null;
    }
  }
}
