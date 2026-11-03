/**
 * Payload sent to POST /api/auth/register.
 * `companyName` and `phone` are collected for enterprise onboarding
 * but are optional from the backend's point of view, so they are
 * typed as required strings here and defaulted to '' in the form.
 */
export interface RegisterRequest {
  name: string;
  email: string;
  phone: string;
  password: string;
}
