import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { finalize } from 'rxjs';
import { AuthService } from '../../core/services/auth.service';
import { LoginRequest } from '../../core/models/login-request';

/**
 * Login page. Handles credential entry, client-side validation, the
 * loading/error lifecycle of the login call, and redirect-on-success.
 */
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  private readonly fb = inject(FormBuilder);
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  /** Toggles the password field between `password` and `text` input types. */
  readonly showPassword = signal(false);

  /** True while the login HTTP request is in flight - drives the spinner and disables the form. */
  readonly isLoading = signal(false);

  /** Human-readable message from the last failed login attempt, or null when there is none to show. */
  readonly errorMessage = signal<string | null>(null);

  readonly form = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(8)]],
    rememberMe: [false],
  });

  /** Convenience getter so the template can read validation state tersely. */
  get email() {
    return this.form.controls.email;
  }

  get password() {
    return this.form.controls.password;
  }

  togglePasswordVisibility(): void {
    this.showPassword.update((visible) => !visible);
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.errorMessage.set(null);
    this.isLoading.set(true);

    const request: LoginRequest = {
      email: this.email.value,
      password: this.password.value,
    };

    this.authService
      .login(request)
      .pipe(finalize(() => this.isLoading.set(false)))
      .subscribe({
        next: () => this.router.navigate(['/dashboard']),
        error: (error: HttpErrorResponse) => this.errorMessage.set(this.resolveErrorMessage(error)),
      });
  }

  /**
   * Social login is out of scope for the backend contract provided
   * (only /login and /register exist), so these are wired to a clear
   * placeholder message rather than silently doing nothing.
   */
  loginWithGoogle(): void {
    this.errorMessage.set('Google login is not yet configured for this environment.');
  }

  loginWithGitHub(): void {
    this.errorMessage.set('GitHub login is not yet configured for this environment.');
  }

  private resolveErrorMessage(error: HttpErrorResponse): string {
    if (error.status === 0) {
      return 'Unable to reach the server. Check your connection and try again.';
    }
    if (error.status === 401) {
      return 'Incorrect email or password.';
    }
    return error.error?.message ?? 'Something went wrong while signing in. Please try again.';
  }
}
