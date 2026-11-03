import { Component, computed, inject, signal } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  ReactiveFormsModule,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { finalize } from 'rxjs';
import { AuthService } from '../../core/services/auth.service';
import { RegisterRequest } from '../../core/models/register-request';

/** E.164-ish phone validation: optional leading +, 7-15 digits. */
const PHONE_PATTERN = /^\+?[0-9]{7,15}$/;

/**
 * Cross-field validator that flags the confirmPassword control when it
 * does not match password. Applied at the group level because it needs
 * to read two sibling controls.
 */
function passwordsMatchValidator(): ValidatorFn {
  return (group: AbstractControl): ValidationErrors | null => {
    const password = group.get('password')?.value;
    const confirmPassword = group.get('confirmPassword')?.value;
    if (!password || !confirmPassword) {
      return null;
    }
    return password === confirmPassword ? null : { passwordMismatch: true };
  };
}

type PasswordStrength = 'weak' | 'fair' | 'good' | 'strong';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export class RegisterComponent {
  private readonly fb = inject(FormBuilder);
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  readonly showPassword = signal(false);
  readonly showConfirmPassword = signal(false);
  readonly isLoading = signal(false);
  readonly errorMessage = signal<string | null>(null);
  readonly successMessage = signal<string | null>(null);

  readonly form = this.fb.nonNullable.group(
    {
      fullName: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required, Validators.pattern(PHONE_PATTERN)]],
      companyName: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['', [Validators.required]],
      acceptTerms: [false, [Validators.requiredTrue]],
    },
    { validators: passwordsMatchValidator() },
  );

  get fullName() {
    return this.form.controls.fullName;
  }
  get email() {
    return this.form.controls.email;
  }
  get phone() {
    return this.form.controls.phone;
  }
  get companyName() {
    return this.form.controls.companyName;
  }
  get password() {
    return this.form.controls.password;
  }
  get confirmPassword() {
    return this.form.controls.confirmPassword;
  }
  get acceptTerms() {
    return this.form.controls.acceptTerms;
  }

  /** Recomputed reactively any time the password value changes. */
  readonly passwordStrengthScore = computed(() => this.scorePassword(this.passwordLive()));

  readonly passwordStrengthLabel = computed<PasswordStrength>(() => {
    const score = this.passwordStrengthScore();
    if (score <= 1) return 'weak';
    if (score === 2) return 'fair';
    if (score === 3) return 'good';
    return 'strong';
  });

  /** True only once every validator on the form passes, per the "disable until valid" requirement. */
  readonly canSubmit = computed(() => this.formStatus() === 'VALID' && !this.isLoading());

  /**
   * FormGroup.status is a plain property, not a signal, so it won't trigger
   * template re-evaluation on its own. Mirroring it into a signal via
   * statusChanges keeps `canSubmit` reactive without polling.
   */
  private readonly formStatus = signal(this.form.status);
  private readonly passwordLive = signal('');

  constructor() {
    this.form.statusChanges.subscribe((status) => this.formStatus.set(status));
    this.password.valueChanges.subscribe((value) => this.passwordLive.set(value));
  }

  togglePasswordVisibility(): void {
    this.showPassword.update((v) => !v);
  }

  toggleConfirmPasswordVisibility(): void {
    this.showConfirmPassword.update((v) => !v);
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.errorMessage.set(null);
    this.successMessage.set(null);
    this.isLoading.set(true);

    // Note: `companyName` is captured for enterprise onboarding context but is
    // intentionally omitted from the payload - the backend contract for
    // POST /register only defines name, email, phone, and password.
    const request: RegisterRequest = {
      name: this.fullName.value,
      email: this.email.value,
      phone: this.phone.value,
      password: this.password.value,
    };

    this.authService
      .register(request)
      .pipe(finalize(() => this.isLoading.set(false)))
      .subscribe({
        next: () => {
          this.successMessage.set('Account created. Redirecting to sign in...');
          setTimeout(() => this.router.navigate(['/login']), 1200);
        },
        error: (error: HttpErrorResponse) => this.errorMessage.set(this.resolveErrorMessage(error)),
      });
  }

  private scorePassword(value: string): number {
    let score = 0;
    if (value.length >= 8) score += 1;
    if (/[A-Z]/.test(value) && /[a-z]/.test(value)) score += 1;
    if (/[0-9]/.test(value)) score += 1;
    if (/[^A-Za-z0-9]/.test(value)) score += 1;
    return score;
  }

  private resolveErrorMessage(error: HttpErrorResponse): string {
    if (error.status === 0) {
      return 'Unable to reach the server. Check your connection and try again.';
    }
    if (error.status === 409) {
      return 'An account with this email already exists.';
    }
    return error.error?.message ?? 'Something went wrong while creating your account.';
  }
}
